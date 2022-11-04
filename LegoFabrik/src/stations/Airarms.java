package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Airarms {
	private boolean towerPosition = true; // 1 to QA, 2 to lane
	private int turnDegree = -80;
	private int towerTurnDegree = -200; //default 100
	private Steuerung s;
	private boolean arm1Extended = true; //6A: Arm 2 ist eingezogen
	private boolean arm1Up = 	   true; //8D
	private boolean arm2Up = 	   true; //6C
	private boolean grabber1Parallel = true; //6B: Greifer von Arm 1 ist parallel zur Armrichtung ,Arm 2 ist gedreht
	private boolean arm1GrabOpen = true; //8C
	private boolean arm2GrabOpen = true; //6D
	private int valvespeed = 300;
	
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
			turnArm2.setSpeed(180); 
			turnArm1.setSpeed(180);
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
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void reset () {
		try {
			extend.backward();
			boolean stalled = false;
			while (!stalled) {
				stalled = extend.isStalled();
				Thread.sleep(10);
			}
			extend.stop(true);
			extend.flt(true);
			
			turnsGrabber.forward();
			stalled = false;
			while (!stalled) {
				stalled = turnsGrabber.isStalled();
				Thread.sleep(10);
			}
			turnsGrabber.stop(true);
			turnsGrabber.flt(true);
			
			vertical2.backward();
			stalled = false;
			while (!stalled) {
				stalled = vertical2.isStalled();
				Thread.sleep(10);
			}
			vertical2.stop(true);
			vertical2.flt(true);
			
			grab2.backward();
			stalled = false;
			while (!stalled) {
				stalled = grab2.isStalled();
				Thread.sleep(10);
			}
			grab2.stop(true);
			grab2.flt(true);
			
			grab1.forward();
			stalled = false;
			while (!stalled) {
				stalled = grab1.isStalled();
				Thread.sleep(10);
			}
			grab1.stop(true);
			grab1.flt(true);
			
			vertical1.forward();
			stalled = false;
			while (!stalled) {
				stalled = vertical1.isStalled();
				Thread.sleep(10);
			}
			vertical1.stop(true);
			vertical1.flt(true);
			
			turnArm1.forward();
			stalled = false;
			while (!stalled) {
				stalled = turnArm1.isStalled();
				Thread.sleep(10);
			}
			turnArm1.stop(true);
			turnArm1.flt(true);
			
			turnArm2.backward();
			stalled = false;
			while (!stalled) {
				stalled = turnArm2.isStalled();
				Thread.sleep(10);
			}
			turnArm2.stop(true);
			turnArm2.flt(true);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void retractArm1() {
		if (arm1Extended) {
			try {
				extend.rotate(turnDegree, false); //positiv -> Hebel nach links?
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1Extended = false;	
		}
	}
	
	public void extendArm1() {
		if (!arm1Extended) {
			try {
				extend.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1Extended = true;	
		}
	}
	
	public void moveArm1Up() {
		if (!arm1Up) {
			try {
				vertical1.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1Up = true;	
		}
	}
	
	public void moveArm1Down() {
		if (arm1Up) {
			try {
				vertical1.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1Up = false;	
		}
	}
	public void moveArm2Up() {
		if (!arm2Up) {
			try {
				vertical2.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2Up = true;	
		}
	}
	
	public void moveArm2Down() {
		if (arm2Up) {
			try {
				vertical2.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2Up = false;	
		}
	}
	
	public void turnGrabber() {
		if (grabber1Parallel) {
			try {
				turnsGrabber.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			grabber1Parallel = false;	
		}
		else {
			try {
				turnsGrabber.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			grabber1Parallel = true;
		}
	}
	
	public void openGrabber1() {
		if (!arm1GrabOpen) {
			try {
				grab1.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1GrabOpen = true;	
		}
	}
	
	public void closeGrabber1() {
		if (arm1GrabOpen) {
			try {
				grab1.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm1GrabOpen = false;	
		}
	}
	
	public void openGrabber2() {
		if (!arm2GrabOpen) {
			try {
				grab2.rotate(-turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2GrabOpen = true;	
		}
	}
	
	public void closeGrabber2() {
		if (arm2GrabOpen) {
			try {
				grab2.rotate(turnDegree, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			arm2GrabOpen = false;	
		}
	}
	public void turnTowers() { //Dreht den Turm
		if (towerPosition) {
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
	

	
	public void runAirArms() {
		moveArm1Down();
		closeGrabber1();
		moveArm1Up();
		turnGrabber();
		retractArm1();
		turnTowers();
		extendArm1();
		moveArm1Down();
		openGrabber1();
		moveArm1Up();
		retractArm1();
		turnTowers();
		extendArm1();
		turnGrabber();
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
}
