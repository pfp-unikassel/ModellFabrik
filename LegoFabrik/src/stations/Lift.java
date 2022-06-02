package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Lift {

	RMIRegulatedMotor Heben;
	RMIRegulatedMotor grabber_right;
	RMIRegulatedMotor grabber_left;
	RMIRegulatedMotor shaker;
	
	private int winkelGreifen = 450;
	private int winkelHeben =  3700;
	private int liftSpeed = 740;
	private int shakerSpeed = 1000;
	private boolean running = false;
	private Steuerung s;
	private boolean endstop_left = false;
	private boolean endstop_right = false;
	private boolean endstop_hinge = false;
	
	public Lift(Steuerung s,RMIRegulatedMotor grabber_right,
			RMIRegulatedMotor grabber_left,
			RMIRegulatedMotor Heben, 
			RMIRegulatedMotor shaker) {
		this.s = s;
		this.grabber_right=grabber_right;
		this.grabber_left=grabber_left;
		this.Heben=Heben;
		this.shaker = shaker;
	}
	
	public void startGrab(boolean instantReturn) throws RemoteException {  
		grabber_right.rotate(winkelGreifen, true);
		grabber_left.rotate(winkelGreifen,false);
	}
	
	public void releaseGrab(boolean instantReturn) throws RemoteException { 
		grabber_right.rotate(-winkelGreifen,true);
		grabber_left.rotate(-winkelGreifen,false);
	}
	public void startLiftUp(boolean instantReturn) throws RemoteException {   // start lift/elevator and hold him up	
		Heben.setSpeed(liftSpeed);
		Heben.rotate(-winkelHeben,instantReturn);
	}
	
	public void startLiftDown(boolean instantReturn) throws RemoteException { // brings box and elevator/lift back down 
		Heben.setSpeed(liftSpeed);
		Heben.rotate(winkelHeben,instantReturn);	
	}
	
	public int getliftSpeed() {
		return liftSpeed;
	}
	
	public void setliftSpeedt(int liftSpeed) {
		this.liftSpeed = liftSpeed;
	}
	
	public void startShaker() throws RemoteException {
		shaker.setSpeed(shakerSpeed);
		shaker.forward();
	}
	
	public void stopShaker() throws RemoteException {
		shaker.stop(true);
	}
	public int getShakerSpeed() {
		return shakerSpeed;
	}
	
	public void setShakerSpeed(int shakerSpeed) {
		this.shakerSpeed = shakerSpeed;
	}
	
	public void start(boolean instantReturn) {
		try {
			setRunning(true);
			startGrab(instantReturn);
			startLiftUp(instantReturn);
			Thread.sleep(4000);			
			startLiftDown(instantReturn);
		    releaseGrab(instantReturn);
			setRunning(false);
		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void home () throws RemoteException, InterruptedException {
		endstop_left = false;
		endstop_right = false;
		endstop_hinge = false;
		Heben.forward();
		while (!endstop_hinge) {
			Thread.sleep(10);
		}
		Heben.stop(true);
		grabber_left.backward();
		while (!endstop_left) {
			Thread.sleep(10);
		}
		grabber_left.stop(true);
		grabber_right.backward();
		while (!endstop_right) {
			Thread.sleep(10);
		}
		grabber_right.stop(true);

	}
	
	public void lift_grabber_leftFired () {
		endstop_left = true;
	}
	public void lift_grabber_rightFired () {
		endstop_right = true;
	}
	public void lift_hingeFired () {
		endstop_hinge = true;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public RMIRegulatedMotor getHeben() {
		return Heben;
	}

	public void setHeben(RMIRegulatedMotor Heben) {
		this.Heben = Heben;
	}

	public RMIRegulatedMotor getgrabber_right() {
		return grabber_right;
	}

	public void setgrabber_right(RMIRegulatedMotor grabber_right) {
		this.grabber_right = grabber_right;
	}

	public RMIRegulatedMotor getgrabber_left() {
		return grabber_left;
	}

	public void setgrabber_left(RMIRegulatedMotor grabber_left) {
		this.grabber_left = grabber_left;
	}

	public RMIRegulatedMotor getShaker() {
		return shaker;
	}

	public void setShaker(RMIRegulatedMotor shaker) {
		this.shaker = shaker;
	}
}
