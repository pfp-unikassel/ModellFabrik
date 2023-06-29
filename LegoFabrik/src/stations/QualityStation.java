package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class QualityStation {

	private boolean towerStatus = false; // false off
	private boolean armPositionVertical = false; // false up
	private boolean armIsStalled = false;
	private boolean armWorking=false;
	private boolean sortStatus = false;
	
	private char armPositionHorizontal = 's'; // s sort, g good, b bad
	private char tablePosition = 'd'; //d down, c color detection, u up
	private String colorString = "";
	private int countedBalls = 0;
	private int goodBalls = 0;
	private int badBalls = 0;
	private int towerSpeed =200;

	private RMIRegulatedMotor table;
	private RMIRegulatedMotor armVertical;
	private RMIRegulatedMotor armHorizontal;
	private RMIRegulatedMotor tower;
	
	private Steuerung s;

	public QualityStation(Steuerung s,RMIRegulatedMotor table, RMIRegulatedMotor armVertical, RMIRegulatedMotor armHorizontal,
			RMIRegulatedMotor tower) {

		this.s = s;
		this.table = table;
		this.armHorizontal = armHorizontal;
		this.armVertical = armVertical;
		this.tower = tower;
		try {
			tower.setSpeed(towerSpeed);
			armHorizontal.setSpeed(600);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tachotest() {
		try {
			table.rotate(10, false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void colorSensorFired(String colorString) {
		this.colorString = colorString;
		if (colorString != "NONE") {
			System.out.println("Station Farbe ist " + colorString);
		}
		
	}

	public void startTower() {
		if (!getTowerStatus()) {
			try {
				tower.backward();
				towerStatus = !towerStatus;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // TODO: check if forward is right
		}
	}

	public void stopTower() {
		if (getTowerStatus()) {
			try {
				tower.stop(false);
				towerStatus = !towerStatus;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void resetArm() throws RemoteException { // run to wall until resistance gets to big, move back to mid
													// afterwards


	}

	public void moveTable(char position) { //7,5 Zähne über ganze Bewegung, 16 Zähne Zahnrad, übersetzung 20->12
		try {
			table.resetTachoCount();
			if (tablePosition == 'd') {
				if (position == 'c') {
					table.rotate(120, false);
				}
				if (position == 'u') {
					table.rotate(158, false);
				}
			}
			if (tablePosition == 'c') {
				if (position == 'd') {
					table.rotate(-120, false);
				}
				if (position == 'u') {
					table.rotate(38, false);
				}
			}
			if (tablePosition == 'u') {
				if (position == 'c') {
					table.rotate(-38, false);
				}
				if (position == 'd') {
					table.rotate(-158, false);
				}
			}
			tablePosition = position;
		} catch (RemoteException e) {
			
		}
	}
	
	public void moveArmHorizontal(char position) { // s sort, g good, b bad, 112,5° Umdrehung pro Kästchen, s->g 8 kästchen, g->b 5 kästchen
		try {
			if (armPositionHorizontal == 's') {
				if (position == 'g') {
					armHorizontal.rotate(900, false);
				}
				if (position == 'b') {
					armHorizontal.rotate(1350, false);
				}
			}
			if (armPositionHorizontal == 'g') {
				if (position == 's') {
					armHorizontal.rotate(-900, false);
				}
				if (position == 'b') {
					armHorizontal.rotate(562, false);
				}
			}
			if (armPositionHorizontal == 'b') {
				if (position == 's') {
					armHorizontal.rotate(-1350, false);
				}
				if (position == 'g') {
					armHorizontal.rotate(-562, false);
				}
			}
			armPositionHorizontal = position;
		} catch (RemoteException e) {
			
		}
	}
	
	public void lowerArm() { //armPositionVertical: false = up
		try {
			if (!armPositionVertical) {
				armVertical.rotate(360, false);
				armPositionVertical = true;
			}
		} catch (RemoteException e) {
		}
	}
	
	public void raiseArm() {
		try {
			if (armPositionVertical) {
				armVertical.rotate(-360, false);
				armPositionVertical = false;
			}
		} catch (RemoteException e) {
		}
	}
	
	public void freeBall() throws RemoteException {
		
		if (!getArmPositionVertical()) {
			// move arm down
			armVertical.rotate(200, false);
			armVertical.rotate(-200, false);// rotate without true or fals should wait until it finished
														// rotation
		}
	}

	public void reset() throws RemoteException {

	}
	
	public void startSort() {
		sortStatus = true;
		new java.util.Timer().schedule(new java.util.TimerTask() {
			 @Override
			 public void run() {
					while (sortStatus) {
						try {
							moveTable('c');
							Thread.sleep(500);
							if (colorString == "BLACK") {  //Farbbedingung für schlecht, Grüne Kugeln werden als schwarz erkannt
								moveTable('u');
								lowerArm();
								raiseArm();
								moveTable('d');
								moveArmHorizontal('b');
								lowerArm();
								raiseArm();
								moveArmHorizontal('s');
							}
							if (colorString == "RED") {  //Farbbedingung für schlecht
								moveTable('u');
								lowerArm();
								raiseArm();
								moveTable('d');
								moveArmHorizontal('g');
								lowerArm();
								raiseArm();
								moveArmHorizontal('s');
							}
							else {
								moveTable('d');
							}
						} catch (InterruptedException e) {
							
						}
						
						
					}
			 	}
			 }, 1000);
	}

//	public void takeBallToGood() {
//
//		setArmWorking(true);
//		try {
//			liftTable();
//			takeBall();
//			moveArmToGood();
//			freeBall();
//			moveArmToSort();
//			sinkTable();
//			goodBalls++;
//			countedBalls++;
//
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		setArmWorking(false);
//	}
//
//	public void takeBallToBad() {
//		setArmWorking(true);
//		try {
//			liftTable();
//			takeBall();
//			moveArmToBad();
//			freeBall();
//			moveArmToSort();
//			sinkTable();
//			badBalls++;
//			countedBalls++;
//
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		setArmWorking(false);
//	}

	public boolean getTowerStatus() {
		return towerStatus;
	}

	public void setTowerStatus(boolean towerStatus) {
		this.towerStatus = towerStatus;
	}

	public boolean getArmPositionVertical() {
		return armPositionVertical;
	}

	public void setArmPositionVertical(boolean armPositionVertical) {
		this.armPositionVertical = armPositionVertical;
	}

	public char getArmPositionHorizontal() {
		return armPositionHorizontal;
	}

	public void setArmPositionHorizontal(char armPositionHorizontal) {
		this.armPositionHorizontal = armPositionHorizontal;
	}

	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	public int getCountedBalls() {
		return countedBalls;
	}

	public void setCountedBalls(int countedBalls) {
		this.countedBalls = countedBalls;
	}

	public int getGoodBalls() {
		return goodBalls;
	}

	public void setGoodBalls(int goodBalls) {
		this.goodBalls = goodBalls;
	}

	public int getBadBalls() {
		return badBalls;
	}

	public void setBadBalls(int badBalls) {
		this.badBalls = badBalls;
	}

	public boolean getArmIsStalled() {
		return armIsStalled;
	}

	public void setArmIsStalled(boolean armIsStalled) {
		this.armIsStalled = armIsStalled;
	}

	public boolean isArmWorking() {
		return armWorking;
	}

	public void setArmWorking(boolean armWorking) {
		this.armWorking = armWorking;
	}
	
	public void setSortStatus(boolean sortStatus) {
		this.sortStatus = sortStatus;
	}

}
