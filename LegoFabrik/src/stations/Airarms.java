package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Airarms{
	private boolean towerPosition = true; // 1 to QA, 2 to lane
	private int turnDegree = 60;
	private int towerTurnDegree = -210; //default 100
	private Steuerung s;
	private boolean armsExtended = true; //6A: Arme sind ausgefahren
	private boolean arm1Up = 	   true; //8D
	private boolean arm2Up = 	   true; //6C
	private boolean grabber1Parallel = true; //6B: Greifer von Arm 1 ist parallel zur Armrichtung ,Arm 2 ist gedreht
	private boolean arm1GrabOpen = true; //8C
	private boolean arm2GrabOpen = true; //6D
	private int valvespeed = 90;
	private boolean arm1active = true;
	private boolean arm2active = true;
	private boolean armsactive = true;
	
	RMIRegulatedMotor extend;
	RMIRegulatedMotor vertical1;
	RMIRegulatedMotor vertical2;
	RMIRegulatedMotor turnsGrabber;
	RMIRegulatedMotor grab1;	
	RMIRegulatedMotor grab2;
	RMIRegulatedMotor turnArm1;
	RMIRegulatedMotor turnArm2;


	public Airarms(Steuerung s,RMIRegulatedMotor extend, RMIRegulatedMotor turnsGrabber, RMIRegulatedMotor vertical2,
			RMIRegulatedMotor grab2, RMIRegulatedMotor turnArm2, RMIRegulatedMotor turnArm1, RMIRegulatedMotor grab1, RMIRegulatedMotor vertical1) {
		this.s = s;
		this.extend = extend;
		this.vertical1 = vertical1;
		this.vertical2 = vertical2; 
		this.turnsGrabber = turnsGrabber;
		this.grab1 = grab1;
		this.grab2 = grab2;
		this.turnArm1 = turnArm1;
		this.turnArm2 = turnArm2;
		try {
			extend.setSpeed(valvespeed);
			turnsGrabber.setSpeed(valvespeed);
			vertical2.setSpeed(valvespeed);
			grab2.setSpeed(valvespeed);
			turnArm2.setSpeed(250); 
			turnArm1.setSpeed(250);
			grab1.setSpeed(valvespeed);
			vertical1.setSpeed(valvespeed);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
	
	public void motorshutdown () {
		try {
			extend.flt(true);
			turnsGrabber.flt(true);
			vertical2.flt(true);
			grab2.flt(true);
			grab1.flt(true);
			vertical1.flt(true);
			turnArm1.flt(true);
			turnArm2.flt(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void reset () {
		try {
			boolean stalled = false;
			turnsGrabber.backward();
			turnsGrabber.waitComplete();
			turnsGrabber.flt(true);
			grab1.forward();
			grab1.waitComplete();
			grab1.flt(true);
			grab2.forward();
			grab2.waitComplete();
			grab2.flt(true);
			vertical1.backward();
			vertical1.waitComplete();
			vertical1.flt(true);
			vertical2.backward();
			vertical2.waitComplete();
			vertical2.flt(true);
			extend.backward();
			extend.waitComplete();
			extend.flt(true);
			armsExtended = true; //6A: Arm 2 ist eingezogen
			arm1Up = 	   true; //8D
			arm2Up = 	   true; //6C
			grabber1Parallel = true; //6B: Greifer von Arm 1 ist parallel zur Armrichtung ,Arm 2 ist gedreht
			arm1GrabOpen = true; //8C
			arm2GrabOpen = true; //6D
			
			turnArm1.backward();
			stalled = false;
			while (!stalled) {
				stalled = turnArm1.isStalled();
				Thread.sleep(100);
			}
			turnArm1.stop(true);
			turnArm1.flt(true);
			turnArm2.backward();
			stalled = false;
			while (!stalled) {
				stalled = turnArm2.isStalled();
				Thread.sleep(100);
			}
			turnArm2.stop(true);
			turnArm2.flt(true);
			towerPosition = true;
			
			turnsGrabber.resetTachoCount();
			extend.resetTachoCount();
			grab1.resetTachoCount();
			grab2.resetTachoCount();
			vertical1.resetTachoCount();
			vertical2.resetTachoCount();
			turnArm1.resetTachoCount();
			turnArm2.resetTachoCount();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void retractArms() {
		if (armsExtended) {
			try {
				extend.rotate(turnDegree);
				extend.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			armsExtended = false;	
		}
	}
	
	public void extendArms() {
		if (!armsExtended) {
			try {
				extend.rotate(-turnDegree);
				extend.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			armsExtended = true;	
		}
	}
	
	public void moveArm1Up(boolean immediateReturn) {
		if (!arm1Up) {
			try {
				vertical1.rotate(-turnDegree, immediateReturn);
				vertical1.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1Up = true;	
		}
	}
	
	public void moveArm1Down(boolean immediateReturn) {
		if (arm1Up) {
			try {
				vertical1.rotate(turnDegree, immediateReturn);
				vertical1.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1Up = false;	
		}
	}
	public void moveArm2Up(boolean immediateReturn) {
		if (!arm2Up) {
			try {
				vertical2.rotate(-turnDegree, immediateReturn);
				vertical2.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2Up = true;	
		}
	}
	
	public void moveArm2Down(boolean immediateReturn) {
		if (arm2Up) {
			try {
				vertical2.rotate(turnDegree, immediateReturn);
				vertical2.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2Up = false;	
		}
	}
	
	public void turnGrabber(boolean immediateReturn) {
		if (grabber1Parallel) {
			try {
				turnsGrabber.rotate(turnDegree, immediateReturn);
				turnsGrabber.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			grabber1Parallel = false;	
		}
		else {
			try {
				turnsGrabber.rotate(-turnDegree, immediateReturn);
				turnsGrabber.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			grabber1Parallel = true;
		}
	}
	
	public void openGrabber1(boolean immediateReturn) {
		if (!arm1GrabOpen) {
			try {
				grab1.rotate(turnDegree, immediateReturn);
				grab1.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1GrabOpen = true;	
		}
	}
	
	public void closeGrabber1(boolean immediateReturn) {
		if (arm1GrabOpen) {
			try {
				grab1.rotate(-turnDegree, immediateReturn);
				grab1.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1GrabOpen = false;	
		}
	}
	
	public void openGrabber2(boolean immediateReturn) {
		if (!arm2GrabOpen) {
			try {
				grab2.rotate(turnDegree, immediateReturn);
				grab2.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2GrabOpen = true;	
		}
	}
	
	public void closeGrabber2(boolean immediateReturn) {
		if (arm2GrabOpen) {
			try {
				grab2.rotate(-turnDegree, immediateReturn);
				grab2.flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2GrabOpen = false;	
		}
	}
	public void turnTowers() { //Dreht den Turm
		if (towerPosition) {
			try {
				turnArm2.rotate(-towerTurnDegree, false); // turn one after another
				turnArm1.rotate(-towerTurnDegree, false); // 
				towerPosition = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			try {
				turnArm1.rotate(towerTurnDegree, false);
				turnArm2.rotate(towerTurnDegree, false);
				towerPosition = true;
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void test() throws InterruptedException, RemoteException {
//		vertical2.resetTachoCount();
//		vertical1.resetTachoCount();
//		moveArm2Down(false);
//		moveArm1Down(false);
//		vertical1.flt(true);
//		vertical2.flt(true);
//		turnGrabber();
//		System.out.println("vertical 1: "+vertical1.getTachoCount());
//		System.out.println("vertical 2: "+vertical2.getTachoCount());
//		moveArm2Up(false);
//		moveArm1Up(false);
//		vertical1.flt(true);
//		vertical2.flt(true);
//		turnGrabber();
//		System.out.println("vertical 1: "+vertical1.getTachoCount());
//		System.out.println("vertical 2: "+vertical2.getTachoCount());
		vertical1.resetTachoCount();
		vertical1.rotateTo(60);
		vertical1.rotateTo(0);
		vertical1.rotateTo(60);
		vertical1.rotateTo(0);
		vertical1.rotateTo(60);
		vertical1.rotateTo(0);
		vertical1.rotateTo(60);
		vertical1.rotateTo(0);
		vertical1.rotateTo(60);
		vertical1.rotateTo(0);
	}
	
	public void runAirArms() throws InterruptedException{ //move commands mit boolean: false: wartet bis bewegung fertig, true: sofort weiter
		armsactive = true;
		while (armsactive) {
			if (arm1active && arm2active) { //beide Arme runter, 2 lässt los, 1 greift zu
				moveArm2Down(true);
				moveArm1Down(false);
				openGrabber2(true);
				closeGrabber1(false);
				moveArm2Up(true);
				moveArm1Up(false);
				//Arm2 Check fehlt
			}
			else if (arm1active && !arm2active) { //nur arm 1 macht was, 2 bleibt oben über Ablage
				moveArm1Down(false);
				closeGrabber1(false);
				moveArm1Up(false);
			}
			else if (!arm1active && arm2active) { //arm 2 legt ab, arm 1 steht still
				moveArm2Down(false);
				openGrabber2(false);
				moveArm2Up(false);
				//Arm2 Check fehlt
			}
			turnGrabber(true);
			retractArms();
			turnTowers();
			extendArms();
			if (arm1active && arm2active) { //beide Arme runter, 1 lässt los, 2 greift zu
				moveArm1Down(true);
				moveArm2Down(false);
				openGrabber1(true);
				closeGrabber2(false);
				moveArm1Up(true);
				moveArm2Up(false);
				s.getQuality().setStoredBalls(s.getQuality().getStoredBalls()-2); //Arm1 hat Bälle weg transferiert
				s.getQuality().checkStoredBalls(); //prüfen, ob Arm1 weiter aktiv sein soll
			}
			else if (arm1active && !arm2active) { //arm 1 legt ab, arm 2 steht still
				moveArm1Down(false);
				openGrabber1(false);
				moveArm1Up(false);
				s.getQuality().setStoredBalls(s.getQuality().getStoredBalls()-2); //Arm1 hat Bälle weg transferiert
				s.getQuality().checkStoredBalls(); //prüfen, ob Arm1 weiter aktiv sein soll
			}
			else if (!arm1active && arm2active) { //arm 2 nimmt auf
				moveArm2Down(false);
				closeGrabber2(false);
				moveArm2Up(false);
			}
			turnGrabber(true);
			retractArms();
			turnTowers();
			extendArms(); //Zyklus abgeschlossen, kann von vorne beginnen
			armsactive = false; //zur unterbrechung während der programmentwicklung
		}
		s.getQuality().setStoredBalls(s.getQuality().getStoredBalls()-2);
		s.getQuality().checkStoredBalls();
		
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
		return extend;
	}

	public void setMoveArm(RMIRegulatedMotor moveArm) {
		this.extend = moveArm;
	}

	public RMIRegulatedMotor getVerticalArm() {
		return vertical1;
	}

	public void setVerticalArm(RMIRegulatedMotor verticalArm1) {
		this.vertical1 = verticalArm1;
	}

	public RMIRegulatedMotor getTurnGrab() {
		return turnsGrabber;
	}

	public void setTurnGrab(RMIRegulatedMotor turnGrab) {
		this.turnsGrabber = turnGrab;
	}

	public RMIRegulatedMotor getgrab1() {
		return grab1;
	}

	public void setgrab1(RMIRegulatedMotor grab1) {
		this.grab1 = grab1;
	}
	public RMIRegulatedMotor getgrab2() {
		return grab2;
	}

	public void setgrab2(RMIRegulatedMotor grab2) {
		this.grab2 = grab2;
	}
	
	public void stoparms () {
		armsactive = false;
	}
}
