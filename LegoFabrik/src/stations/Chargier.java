package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Chargier {

//	EV3UltrasonicSensor schranke;
//	EV3TouchSensor touch;
//	EV3TouchSensor touchEnde;
	RMIRegulatedMotor antriebBandZumDT;
	RMIRegulatedMotor antriebBandProd;
	RMIRegulatedMotor antriebBandLeergut;
	RMIRegulatedMotor antriebDrehtisch;
	RMIRegulatedMotor drehtischRotieren;

	private int tablePosition = 0;
	private int lineSpeed = 300; // TODO: change to default speed
	
	private int quarterRotation = 130; // 135; // Vierteldrehung am Drehtisch
	// private int quarterRotation = 180;
	private static int turnTableSpeed = 100;
	
	private Steuerung s;

	public Chargier(

			Steuerung s,RMIRegulatedMotor antriebBandZumDT, RMIRegulatedMotor antriebBandProd, RMIRegulatedMotor antriebBandLeergut,
			RMIRegulatedMotor antriebDrehtisch, RMIRegulatedMotor drehtischRotieren) {

		this.s = s;
		this.antriebBandZumDT = antriebBandZumDT;
		this.antriebBandProd = antriebBandProd;
		this.antriebBandLeergut = antriebBandLeergut;
		this.antriebDrehtisch = antriebDrehtisch;
		this.drehtischRotieren = drehtischRotieren;
		
		
		
	}

	public void startLineToTable(boolean direction) throws RemoteException { // start line from car to table if
																				// direction true turn forword
		antriebBandZumDT.setSpeed(getLineSpeed());
		
		if (direction) {
			antriebBandZumDT.forward();
		} else {
			antriebBandZumDT.backward();
		}

	}

	public void stopLineToTable() throws RemoteException { // start line from Table to lifer

		antriebBandZumDT.stop(false);
	}

	public void startTableLine(boolean direction) throws RemoteException { // start line on table

		System.out.println("Start: " + antriebDrehtisch.getTachoCount());
		antriebDrehtisch.setSpeed(getLineSpeed());

		if (direction) {
			antriebDrehtisch.forward();
		} else {
			antriebDrehtisch.backward();
		}
		System.out.println("Ende: " + antriebDrehtisch.getTachoCount());

	}

	public void stopTableLine() throws RemoteException { // start line from Table

		antriebDrehtisch.stop(false);
	}

	public int turnTable(int degree , boolean instantReturn) throws RemoteException { // turn Table around degree
		drehtischRotieren.setSpeed(turnTableSpeed);
		int ret = drehtischRotieren.getSpeed();
		System.out.println("Drehtischrotator Tachostand Beginn: " + drehtischRotieren.getTachoCount());
		drehtischRotieren.rotate(degree, instantReturn); // maybe - degree depends on motor settings
		tablePosition = tablePosition + degree;
		System.out.println("Drehtischrotator Tachostand Ende: " + drehtischRotieren.getTachoCount());
		return ret;
	}

	public void turnToStock(boolean instantReturn) {
		if(getTablePostion() == -2 * quarterRotation) {
			//is allready in position
		}else {
			
			try {
				turnTable(-2 * quarterRotation, instantReturn); // Drehtisch steht Richtung Einspeisung
				// turnTable(-getTablePostion()+-660, instantReturn);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void turnToCar(boolean instantReturn) {
		
		try {
			resetTable(instantReturn);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int turnToLift(boolean instantReturn) {
		
		int ret = 0;
		
		if(getTablePostion() == quarterRotation) {
			//is allready in position
			System.out.println("Im in the right position already");
		}else {
			
			try {
				ret = turnTable(quarterRotation, instantReturn); // Drehtisch steht Richtung Anlieferung
				// hier ist ein 
				System.out.println("Turntable returned Chargier " + ret); // chh
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public void turnTableToWheel(boolean instantReturn) throws RemoteException {
		drehtischRotieren.rotate(1 * quarterRotation, instantReturn);
		// resetTable(instantReturn);
	}
	
	public void resetTable(boolean instantReturn) throws RemoteException { // turns table back to start position

		drehtischRotieren.rotate(-1 * tablePosition, instantReturn);
		tablePosition = 0;
	}

	public void startLineToLifter(boolean direction) throws RemoteException { // start line from Table to lifer if
																				// direction true turn forword

		antriebBandProd.setSpeed(getLineSpeed());

		if (direction) {
			antriebBandProd.forward();
		} else {
			antriebBandProd.backward();

		}

	}

	public void stopLineToLifter() throws RemoteException { // start line from Table to lifer

		antriebBandProd.stop(false);
	}

	public void startLineToStore(boolean direction) throws RemoteException { // start line from Table to Store if
																				// direction true turn forword
		s.sendMessage("BL");
		antriebBandLeergut.setSpeed(getLineSpeed());

		if (direction) {
			antriebBandLeergut.forward();
		} else {
			antriebBandLeergut.backward();

		}

	}

	public void takeBoxFromElevator(boolean instantReturn){
		try {
			antriebBandLeergut.rotate(720,instantReturn); 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stopLineToStorer() throws RemoteException { // stop line from Table to Store

		antriebBandLeergut.stop(false);

	}

	public void stop() throws RemoteException { // stop all but not turntable

		stopLineToLifter();
		stopLineToStorer();
		stopLineToTable();
		stopTableLine();
		resetTable(true);
	}

	public int getTablePostion() {
		return tablePosition;
	}

	public int getLineSpeed() {
		return lineSpeed;
	}

	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	public void touchLiftfired() {

		System.out.println("Lift Sensor fired");
	}

	public void touchTablefired() {
		System.out.println("Table Sensor fired");

	}

	public void schrankefired() {
		// TODO Auto-generated method stub

	}

	public void setTablePosition(int tablePosition) {
		this.tablePosition = tablePosition;
	}

	public RMIRegulatedMotor getAntriebBandZumDT() {
		return antriebBandZumDT;
	}

	public void setAntriebBandZumDT(RMIRegulatedMotor antriebBandZumDT) {
		this.antriebBandZumDT = antriebBandZumDT;
	}

	public RMIRegulatedMotor getAntriebBandProd() {
		return antriebBandProd;
	}

	public void setAntriebBandProd(RMIRegulatedMotor antriebBandProd) {
		this.antriebBandProd = antriebBandProd;
	}

	public RMIRegulatedMotor getAntriebBandLeergut() {
		return antriebBandLeergut;
	}

	public void setAntriebBandLeergut(RMIRegulatedMotor antriebBandLeergut) {
		this.antriebBandLeergut = antriebBandLeergut;
	}

	public RMIRegulatedMotor getAntriebDrehtisch() {
		return antriebDrehtisch;
	}

	public void setAntriebDrehtisch(RMIRegulatedMotor antriebDrehtisch) {
		this.antriebDrehtisch = antriebDrehtisch;
	}

	public RMIRegulatedMotor getDrehtischRotieren() {
		return drehtischRotieren;
	}

	public void setDrehtischRotieren(RMIRegulatedMotor drehtischRotieren) {
		this.drehtischRotieren = drehtischRotieren;
	}

}
