package stations;

import java.rmi.RemoteException;
import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Stock {

	RMIRegulatedMotor laneToStock1;
	RMIRegulatedMotor elevatorHorizontal;
	RMIRegulatedMotor elevatorVerticalleft;
	RMIRegulatedMotor elevatorVerticalright;
	RMIRegulatedMotor stockPlace1;
	RMIRegulatedMotor stockPlace2;
	RMIRegulatedMotor stockPlace3;
	RMIRegulatedMotor stockPlace4;
	private Steuerung s;
	private boolean stock1 = true; // topleft false = empty
	private boolean stock2 = true; // topright
	private boolean stock3 = false; // downleft
	private boolean stock4 = false; // downright
	private int bandPosition = 0; // True is startposition, ready to take box from store on line
	private char elevatorPositionHorizontal = 'l'; // l = left r = right
	private char elevatorPositionVertical = 'd'; // d = down u= up
	private int elevatorHorizontalSpeed = 300;
	private int elevatorVerticalSpeed = 720;
	private int storeLineRotateDegree = 550; // motor turn degree from line on Elevator to get Box from store or in store
	private int stockRotationDegree = 140; // motor turn degree from push mechanism
	private int horizontalRotationDegree = 600; // Elevator motor turndegree
	private int lineSpeed = 300;
	private boolean endstopleft = false;
	private boolean endstopright = false;	
	
	public Stock(Steuerung s,  RMIRegulatedMotor laneToStock1,
			RMIRegulatedMotor elevatorHorizontal, RMIRegulatedMotor elevatorVerticalleft, RMIRegulatedMotor elevatorVerticalright, 
			RMIRegulatedMotor stockPlace1, RMIRegulatedMotor stockPlace2, RMIRegulatedMotor stockPlace3, RMIRegulatedMotor stockPlace4) {
		this.s = s;
		this.stockPlace1 = stockPlace1;
		this.stockPlace2 = stockPlace2;
		this.stockPlace3 = stockPlace3;
		this.stockPlace4 = stockPlace4;
		this.laneToStock1 = laneToStock1;
		this.elevatorVerticalleft = elevatorVerticalleft;
		this.elevatorVerticalright = elevatorVerticalright;
		this.elevatorHorizontal = elevatorHorizontal;
	}

	public void pushBoxFromElevatorToStore(boolean instantReturn) { // have to make sure box is on elevator
		rotateLineToStock(1550 - storeLineRotateDegree, instantReturn); // Rotation 1550 - 350o from before is a 360� turn, so it ends where it starts
	}

	public void pushBoxFromElevatorToLine(boolean instantReturn) { // have to make sure Box is on elevator
		rotateLineToStock(-1550 + storeLineRotateDegree, instantReturn);
	}

	public void home () throws RemoteException, InterruptedException { //Nutzt endstops um in definierte position zu kommen
		endstopleft = false;
		endstopright = false;
		while (endstopleft == false || endstopright== false) {
			System.out.println(endstopright);
			if (endstopleft == false) {
				elevatorVerticalleft.rotate(90, true);		
			}
			if (endstopright == false) {
				elevatorVerticalright.rotate(90, false);		
			}
		}
		elevatorPositionVertical = 'd';
		elevatorVerticalleft.rotate(-360, true);
		elevatorVerticalright.rotate(-360, false);
		System.out.println("exit while schleife");
	}
	
	public void pushBoxFromStock(int stock, boolean instantReturn) throws RemoteException, InterruptedException {
		switch (stock) {
		case 1:
			if (isStock1() == true) {
				elevatorUp(false);
				pushStock1(false);
				placeBoxFromStoreOnElevatorline(false);
				// startLineToStock(false); // Change to degree later
				// Thread.sleep(2000);// 2000
				// stopLaneToStock();
				elevatorDown(false);
				setStock1(false);
			}
			break;
		case 2:
			if (isStock2() == true) {
				elevatorToRight(instantReturn);
				elevatorUp(false);
				pushStock2(false);
				placeBoxFromStoreOnElevatorline(false);
				// startLineToStock(false); // Change to degree later
				// Thread.sleep(2000);
				// stopLaneToStock();
				elevatorToLeft(instantReturn);
				elevatorDown(false);
				setStock2(false);
			}
			break;
		case 3:
			if (isStock3() == true) {
				pushStock3(false);
				placeBoxFromStoreOnElevatorline(false);
				// startLineToStock(false); // Change to degree later
				// Thread.sleep(2000);
				// stopLaneToStock();
				setStock3(false);
			}
			break;
		case 4:
			if (isStock4() == true) {
				elevatorToRight(false);
				pushStock4(false);
				placeBoxFromStoreOnElevatorline(false);
				// startLineToStock(false); // Change to degree later
				// Thread.sleep(2000);
				// stopLaneToStock();
				elevatorToLeft(false);
				setStock4(false);
			}
			break;
		default:
			break;
		}
	}
	
	public void placeBoxFromStoreOnElevatorline(boolean instantReturn) {
		rotateLineToStock(-storeLineRotateDegree, instantReturn);
	}

	public void placeBoxFromLineOnElevatorline(boolean instantReturn) {
		rotateLineToStock(storeLineRotateDegree, instantReturn);
	}

	public void rotateLineToStock(int degree, boolean instantReturn) {
		try {
			laneToStock1.setSpeed(getLineSpeed());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		try {
			laneToStock1.rotate(degree, true);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void resetElevatorLinePosition(boolean instantReturn) {
		rotateLineToStock(-bandPosition, instantReturn);
	}

	public void pushStock1(boolean instantReturn) throws RemoteException {
		if (isStock1()) {
			stockPlace1.setSpeed(180);
			stockPlace1.rotate(-stockRotationDegree, false);
			stockPlace1.rotate(stockRotationDegree, instantReturn);
			setStock1(false);
			s.sendMessage("U1");
		} else {
			System.out.println("Stock 1 is empty");
		}
	}

	public void pushStock2(boolean instantReturn) throws RemoteException {
		if (isStock2()) {
			stockPlace2.setSpeed(180);
			stockPlace2.rotate(-stockRotationDegree, false);
			stockPlace2.rotate(stockRotationDegree, instantReturn);
			setStock2(false);
			s.sendMessage("U2");
		} else {
			System.out.println("Stock 2 is empty");
		}
	}

	public void pushStock3(boolean instantReturn) throws RemoteException {
		if (isStock3()) {
			stockPlace3.setSpeed(180);
			stockPlace3.rotate(-stockRotationDegree, false);
			stockPlace3.rotate(stockRotationDegree, instantReturn);
			setStock3(false);
			s.sendMessage("U3");
		} else {
			System.out.println("Stock 3 is empty");
		}
	}

	public void pushStock4(boolean instantReturn) throws RemoteException {

		if (isStock4()) {
			stockPlace4.setSpeed(180);
			stockPlace4.rotate(-stockRotationDegree, false);
			stockPlace4.rotate(stockRotationDegree, instantReturn);
			setStock4(false);
			s.sendMessage("U4");
		} else {
			System.out.println("Stock 4 is empty");
		}
	}

	public void startLineToStock(boolean direction) throws RemoteException {
		laneToStock1.setSpeed(getLineSpeed());
		if (direction) {
			laneToStock1.forward();
			// laneToStock1.rotate(2000,true);
			// laneToStock2.rotate(-2000,false);
		} else {
			laneToStock1.backward();
			// laneToStock1.rotate(-2000,true);
			// laneToStock2.rotate(2000,false);
		}
	}

	public void stopLaneToStock() throws RemoteException {
		laneToStock1.stop(false); // false he waits for the motor to stop until  it really finishes
	}

	public void elevatorUp(boolean instantReturn) throws RemoteException {
		if (getElevatorPositionVertical() == 'u') {
			// its allready up
		} else {
			elevatorVerticalleft.setSpeed(elevatorVerticalSpeed);
			elevatorVerticalright.setSpeed(elevatorVerticalSpeed);
			elevatorVerticalleft.rotate(-11520, true); // move both at the same time 11520 and wait for second
			elevatorVerticalright.rotate(-11520, instantReturn);
			setElevatorPositionVertical('u');
		}
	}

	public void elevatorDown(boolean instantReturn) throws RemoteException {
		if (getElevatorPositionVertical() == 'u') {
			elevatorVerticalleft.setSpeed(elevatorVerticalSpeed);
			elevatorVerticalright.setSpeed(elevatorVerticalSpeed);
			elevatorVerticalleft.rotate(11520, true); // move both at the same time and wait for second
			elevatorVerticalright.rotate(11520, instantReturn);
			setElevatorPositionVertical('d');
		}
	}

	public void elevatorToLeft(boolean instantReturn) throws RemoteException {
		if (getElevatorPositionHorizontal() == 'l') {
			// nothing is allready left
		} else {
			elevatorHorizontal.setSpeed(elevatorHorizontalSpeed);
			elevatorHorizontal.rotate(-horizontalRotationDegree, true);
			setElevatorPositionHorizontal('l');
		}
	}

	public void elevatorToRight(boolean instantReturn) throws RemoteException {
		if (getElevatorPositionHorizontal() == 'r') {
			// nothing is allready right
		} else {
			elevatorHorizontal.setSpeed(elevatorHorizontalSpeed);

			elevatorHorizontal.rotate(horizontalRotationDegree, true);
			setElevatorPositionHorizontal('r');
		}
	}

	public void reset() {
		try {
			elevatorDown(true);
			elevatorToLeft(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void storeBox(boolean instantReturn) throws RemoteException, InterruptedException {
		if (isStock1() == false) {
			storeIn1(instantReturn);
		} else {
			if (isStock2() == false) {
				storeIn2(instantReturn);
			} else {
				if (isStock3() == false) {
					storeIn3(instantReturn);
				} else {
					if (isStock4() == false) {
						storeIn4(instantReturn);
					} else {
						System.out.println("Error Lager voll");
					}
				}
			}
		}
	}

	public void PushBox(boolean instantReturn) throws RemoteException, InterruptedException {
		if (isStock1() == true) {
			pushBoxFromStock(1, instantReturn);
		} else {
			if (isStock2() == true) {
				pushBoxFromStock(2, instantReturn);
			} else {
				if (isStock3() == true) {
					pushBoxFromStock(3, instantReturn);
				} else {
					if (isStock4() == true) {
						pushBoxFromStock(4, instantReturn);
					} else {
						System.out.println("Error Lager leer");
					}
				}
			}
		}
	}

	private void storeIn1(boolean instantReturn) throws RemoteException, InterruptedException {
		if (isStock1() == false) {
			placeBoxFromLineOnElevatorline(false);
			elevatorUp(false);
			pushBoxFromElevatorToStore(instantReturn);
			Thread.sleep(2000);
			elevatorDown(false);
			setStock1(true);
			s.sendMessage("L1");
		}
	}

	private void storeIn2(boolean instantReturn) throws RemoteException, InterruptedException {
		if (isStock2() == false) {
			placeBoxFromLineOnElevatorline(false);
			elevatorToRight(instantReturn);
			elevatorUp(false);
			pushBoxFromElevatorToStore(false);
			Thread.sleep(2000);
			elevatorToLeft(instantReturn);
			elevatorDown(false);
			setStock2(true);
			s.sendMessage("L2");
		}
	}

	private void storeIn3(boolean instantReturn) throws RemoteException, InterruptedException {
		if (isStock3() == false) {
			placeBoxFromLineOnElevatorline(false);
			pushBoxFromElevatorToStore(false);
			Thread.sleep(2000);
			setStock3(true);
			s.sendMessage("L3");
		}
	}

	private void storeIn4(boolean instantReturn) throws RemoteException, InterruptedException {
		if (isStock4() == false) {
			placeBoxFromLineOnElevatorline(false);
			elevatorToRight(false);
			pushBoxFromElevatorToStore(false);
			Thread.sleep(2000);
			elevatorToLeft(instantReturn);
			setStock4(true);
			s.sendMessage("L4");
		}
	}

	public void printElevatorPosition() {
		if (getElevatorPositionHorizontal() == 'l') {
			if (getElevatorPositionVertical() == 'u') {
				System.out.println("Elevator is UP/LEFT");
			} else {
				System.out.println("Elevator is Down/LEFT");
			}
		} else {
			if (getElevatorPositionVertical() == 'u') {
				System.out.println("Elevator is Down/Right");
			} else {
				System.out.println("Elevator is Up/RIGHT");
			}
		}
	}

	public void takeBoxOnElevator() throws InterruptedException, RemoteException {
		startLineToStock(true); // Change to degree later
		Thread.sleep(1500);
		stopLaneToStock();
	}

	public void takeBoxFromElevatorToTable() throws RemoteException, InterruptedException {
		startLineToStock(false); // Change to degree later
		Thread.sleep(2000);
		stopLaneToStock();
	}

	public String getElevatorpositionAsString() {
		if (getElevatorPositionHorizontal() == 'l') {
			if (getElevatorPositionVertical() == 'u') {
				return ("UP/Left");
			} else {
				return ("Down/Left");
			}
		} else {
			if (getElevatorPositionVertical() == 'u') {
				return ("DOWN/Rigth");
			} else {
				return ("DOWN/Right");
			}
		}
	}

	public boolean isStock1() {
		return stock1;
	}
	public void setStock1(boolean stock1) {
		this.stock1 = stock1;
	}
	public boolean isStock2() {
		return stock2;
	}
	public void setStock2(boolean stock2) {
		this.stock2 = stock2;
	}
	public boolean isStock3() {
		return stock3;
	}
	public void setStock3(boolean stock3) {
		this.stock3 = stock3;
	}
	public boolean isStock4() {
		return stock4;
	}
	public void setStock4(boolean stock4) {
		this.stock4 = stock4;
	}
	public int getLineSpeed() {
		return lineSpeed;
	}
	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}
	public char getElevatorPositionHorizontal() {
		return elevatorPositionHorizontal;
	}
	public void setElevatorPositionHorizontal(char elevatorPositionHorizontal) {
		this.elevatorPositionHorizontal = elevatorPositionHorizontal;
	}
	public char getElevatorPositionVertical() {
		return elevatorPositionVertical;
	}
	public void setElevatorPositionVertical(char elevatorPositionVertical) {
		this.elevatorPositionVertical = elevatorPositionVertical;
	}
	public int getElevatorHorizontalSpeed() {
		return elevatorHorizontalSpeed;
	}
	public void setElevatorHorizontalSpeed(int elevatorHorizontalSpeed) {
		this.elevatorHorizontalSpeed = elevatorHorizontalSpeed;
	}
	public int getElevatorVerticalSpeed() {
		return elevatorVerticalSpeed;
	}
	public void setElevatorVerticalSpeed(int elevatorVerticalSpeed) {
		this.elevatorVerticalSpeed = elevatorVerticalSpeed;
	}
	public int getStoreLineRotateDegree() {
		return storeLineRotateDegree;
	}
	public void setStoreLineRotateDegree(int storeLineRotateDegree) {
		this.storeLineRotateDegree = storeLineRotateDegree;
	}
	public int getStockRotationDegree() {
		return stockRotationDegree;
	}
	public void setStockRotationDegree(int stockRotationDegree) {
		this.stockRotationDegree = stockRotationDegree;
	}
	public int getHorizontalRotationDegree() {
		return horizontalRotationDegree;
	}
	public void setHorizontalRotationDegree(int horizontalRotationDegree) {
		this.horizontalRotationDegree = horizontalRotationDegree;
	}
	public RMIRegulatedMotor getStockPlace1() {
		return stockPlace1;
	}
	public void setStockPlace1(RMIRegulatedMotor stockPlace1) {
		this.stockPlace1 = stockPlace1;
	}
	public RMIRegulatedMotor getStockPlace2() {
		return stockPlace2;
	}
	public void setStockPlace2(RMIRegulatedMotor stockPlace2) {
		this.stockPlace2 = stockPlace2;
	}
	public RMIRegulatedMotor getStockPlace3() {
		return stockPlace3;
	}
	public void setStockPlace3(RMIRegulatedMotor stockPlace3) {
		this.stockPlace3 = stockPlace3;
	}
	public RMIRegulatedMotor getStockPlace4() {
		return stockPlace4;
	}
	public void setStockPlace4(RMIRegulatedMotor stockPlace4) {
		this.stockPlace4 = stockPlace4;
	}
	public RMIRegulatedMotor getLaneToStock1() {
		return laneToStock1;
	}
	public void setLaneToStock1(RMIRegulatedMotor laneToStock1) {
		this.laneToStock1 = laneToStock1;
	}
	public RMIRegulatedMotor getelevatorHorizontal() {
		return elevatorHorizontal;
	}
	public void setelevatorHorizontal(RMIRegulatedMotor elevatorHorizontal) {
		this.elevatorHorizontal = elevatorHorizontal;
	}
	public RMIRegulatedMotor getelevatorVerticalleft() {
		return elevatorVerticalleft;
	}
	public void setelevatorVerticalleft(RMIRegulatedMotor elevatorVerticalleft) {
		this.elevatorVerticalleft = elevatorVerticalleft;
	}
	public RMIRegulatedMotor getelevatorVerticalright() {
		return elevatorVerticalright;
	}
	public void setelevatorVerticalright(RMIRegulatedMotor elevatorVerticalright) {
		this.elevatorVerticalright = elevatorVerticalright;
	}
	public void setendstopleft(boolean endstopleft) {
		this.endstopleft = endstopleft;
	}
	public void setendstopright(boolean endstopright) {
		this.endstopright = endstopright;
	}
}