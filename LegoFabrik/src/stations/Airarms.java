package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Airarms { // DEFAULT: Schalter rechts rechts rechts links

	private boolean grabStatus = true; // true is open   just set to 
	private boolean grabPosition = false; // true is || over the line
	private boolean armPosition = true; // true ist ausgefahren
	private boolean armStatus = true; // true is up false down
	private boolean towerPosition = true; // to lane rigth one
	private int turnDegree = -900;  // default 750
	private int towerTurnDegree = -200; //default 100
	private Steuerung s;

	RMIRegulatedMotor moveArm;
	RMIRegulatedMotor verticalArm;
	RMIRegulatedMotor turnGrab;
	RMIRegulatedMotor openCloseGrab;
	RMIRegulatedMotor turnArm1;
	RMIRegulatedMotor turnArm2;

	public Airarms(Steuerung s,RMIRegulatedMotor airLine1, RMIRegulatedMotor airLine2, RMIRegulatedMotor airLine3,
			RMIRegulatedMotor airLine4, RMIRegulatedMotor turnArm1, RMIRegulatedMotor turnArm2) {
		this.s = s;
		this.moveArm = airLine1; // ausfahren A B C D
		this.verticalArm = airLine2; 
		this.turnGrab = airLine3;
		this.openCloseGrab = airLine4;
		this.turnArm1 = turnArm1;
		this.turnArm2 = turnArm2;
		try {
			turnArm1.setSpeed(180);   // set Turnspeed 
			turnArm2.setSpeed(180);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	
	
	public void turnArm() { //A; Arm Extended
		if (getArmPosition()) {
			// turn to get balls
			try {
				moveArm.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			setArmPosition(false);	
		} 
		else {
			setArmPosition(true);
			try {
				moveArm.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// turn to lane
		}
	}
	
	public void armUp() { // B; Arm geht hoch
		if (!getArmStatus()) { // if arm is down do mit der achse gegen den uhrzeigersinn
			setArmStatus(true);
			try {
				verticalArm.rotate(-turnDegree, false); //450 490
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void armDown() { //B; Arm geht runter
		if (getArmStatus()) { // if arm is up do
			setArmStatus(false);
			try {
				verticalArm.rotate(turnDegree, false); //490
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void grabTurn() { //C; ??????
		if (getGrabPosition()) {
			// turn = to lane
			setGrabPosition(false);
			try {
				turnGrab.rotate(-turnDegree, false); 
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			try {
				// || to lane
				setGrabPosition(true);
				turnGrab.rotate(turnDegree, false); 
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	
		}
	}
	
	
	public void grabClose() { //D; Schlie�t den Greifer
		if (getGrabStatus()) {
			setGrabStatus(false);
			try {
				openCloseGrab.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// open
		}
	}

	public void grabOpen() { //D; �ffnet den Greifer
		if (!getGrabStatus()) {
			setGrabStatus(true);
			try {
				openCloseGrab.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// close
		}
	}

	public void turnTower() { //Dreht den Turm
		if (towerPosition) { // true ist ausgefahren
			try {
				turnArm1.rotate(towerTurnDegree, false); // turn one after another
				turnArm2.rotate(towerTurnDegree, false); // falls es probleme gibt heir war vorher true testen
				towerPosition = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			try {
				turnArm2.rotate(-towerTurnDegree, false);
				turnArm1.rotate(-towerTurnDegree, false);
				towerPosition = true;
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}
	}

	public void reset() {
		if (!getGrabPosition()) {
			grabTurn();
		}
		grabOpen();
		if (!getArmPosition()) {
			turnArm();
		}
		if (!isTowerPosition()) {
			turnTower();
		}
		armUp();
	}
	
	public void hardReset() { // turning motors but has no sa
		try {
			turnGrab.rotateTo(-420, false);
			openCloseGrab.rotate(turnDegree, false);
			moveArm.rotate(-turnDegree, false);
			verticalArm.rotateTo(450, false); //490
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void runAirArms() {
		armDown();
		grabClose() ;
		armUp();
		grabTurn();
		turnArm();		//einfahren
		turnTower();	//turm drehen
		turnArm();  	//ausfahren
		armDown();
		grabOpen();
		armUp();
		grabTurn();
		turnArm();		//einfahren
		turnTower();	//turm drehen
		turnArm();
		s.getQuality().setStoredBalls(s.getQuality().getStoredBalls()-2);
		s.getQuality().checkStoredBalls();
		
	}

	public boolean getGrabStatus() {
		return grabStatus;
	}

	public void setGrabStatus(boolean grabStatus) {
		this.grabStatus = grabStatus;
	}

	public boolean getGrabPosition() {
		return grabPosition;
	}

	public void setGrabPosition(boolean grabPosition) {
		this.grabPosition = grabPosition;
	}

	public boolean getArmPosition() {
		return armPosition;
	}

	public void setArmPosition(boolean armPosition) {
		this.armPosition = armPosition;
	}

	public boolean getArmStatus() {
		return armStatus;
	}

	public void setArmStatus(boolean armStatus) {
		this.armStatus = armStatus;
	}

	public boolean isTowerPosition() {
		return towerPosition;
	}

	public void setTowerPosition(boolean towerPosition) {
		this.towerPosition = towerPosition;
	}

	public int getTurnDegree() {
		return turnDegree;
	}

	public void setTurnDegree(int turnDegree) {
		this.turnDegree = turnDegree;
	}

	public int getTowerTurnDegree() {
		return towerTurnDegree;
	}

	public void setTowerTurnDegree(int towerTurnDegree) {
		this.towerTurnDegree = towerTurnDegree;
	}

	public RMIRegulatedMotor getTurnArm1() {
		return turnArm1;
	}

	public void setTurnArm1(RMIRegulatedMotor turnArm1) {
		this.turnArm1 = turnArm1;
	}

	public RMIRegulatedMotor getTurnArm2() {
		return turnArm2;
	}

	public void setTurnArm2(RMIRegulatedMotor turnArm2) {
		this.turnArm2 = turnArm2;
	}

	public RMIRegulatedMotor getMoveArm() {
		return moveArm;
	}

	public void setMoveArm(RMIRegulatedMotor moveArm) {
		this.moveArm = moveArm;
	}

	public RMIRegulatedMotor getVerticalArm() {
		return verticalArm;
	}

	public void setVerticalArm(RMIRegulatedMotor verticalArm) {
		this.verticalArm = verticalArm;
	}

	public RMIRegulatedMotor getTurnGrab() {
		return turnGrab;
	}

	public void setTurnGrab(RMIRegulatedMotor turnGrab) {
		this.turnGrab = turnGrab;
	}

	public RMIRegulatedMotor getOpenCloseGrab() {
		return openCloseGrab;
	}

	public void setOpenCloseGrab(RMIRegulatedMotor openCloseGrab) {
		this.openCloseGrab = openCloseGrab;
	}
}
