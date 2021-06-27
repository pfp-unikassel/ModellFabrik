package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Car{

	private boolean carPostition = true; // True infront of line, false infront of Fillstation, TODO change to degree later  like 600 degree is the fillstation and 500 the turn table -400 start 
	private Steuerung s;  
	private int carHorizontalDegree = 0;   // TODO: find motor turn degree, to move car left right
	private int carSpeed = 360; // degree per second
	private int lineSpeed = 360;
	
	private int steerDegree = 0;
	private int carDegree = 0;
	
	RMIRegulatedMotor antrieb;
	RMIRegulatedMotor lenkung;
	RMIRegulatedMotor lineOnCar;
	
	public Car(Steuerung s,RMIRegulatedMotor antrieb, RMIRegulatedMotor lenkung, RMIRegulatedMotor lineOnCar){
		
		this.s=s;
		this.lineOnCar = lineOnCar;
		this.lenkung = lenkung;
		this.antrieb = antrieb;
	}
	
	
	public void carToLeftPosition(boolean instantReturn){
		if(carPostition == true){
			// car is allready left
		}else{
			//TODO: move car to left
			moveCarDegree(carHorizontalDegree,instantReturn);
		}
	}
	
	public void carToRightPosition(boolean instantReturn){
		
		if(carPostition == false){
			// car is allready right
		}else{
			//TODO: move car to right
			moveCarDegree(-carHorizontalDegree,instantReturn);
		}
	}
	
	public void moveCarDegree(int degree,boolean instantReturn){
		
		try {
			antrieb.rotate(degree,instantReturn);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startCar() {
		
		try {
			antrieb.forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopCar() {
		
		try {
			antrieb.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startLineOnCar(boolean direction){
		
		// set linespeed
		if(direction= true){
			try {
				lineOnCar.forward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				lineOnCar.backward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void rotateLineOnCar(int degree, boolean instantreturn) {
		
		try {
			lineOnCar.rotate(degree,instantreturn);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopLineOnCar(){
		
		try {
			lineOnCar.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void steerRight() {
		
		try {
			lenkung.forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void steerLeft() {
		
		try {
			lenkung.backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void steerDegree(int degree, boolean instantreturn) {
		
		try {
			lenkung.rotate(degree,instantreturn);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		steerDegree =+ degree;
	}
	
	public void resetSteer() {
		/*
		 * just resets the turn degree commands not the steer Right/Left 
		 */
		steerDegree(-steerDegree,false);
	}


	public boolean isCarPostition() {
		return carPostition;
	}


	public void setCarPostition(boolean carPostition) {
		this.carPostition = carPostition;
	}


	public int getCarHorizontalDegree() {
		return carHorizontalDegree;
	}


	public void setCarHorizontalDegree(int carHorizontalDegree) {
		this.carHorizontalDegree = carHorizontalDegree;
	}


	public int getCarSpeed() {
		return carSpeed;
	}


	public void setCarSpeed(int carSpeed) {
		this.carSpeed = carSpeed;
	}


	public int getLineSpeed() {
		return lineSpeed;
	}


	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}


	public int getSteerDegree() {
		return steerDegree;
	}


	public void setSteerDegree(int steerDegree) {
		this.steerDegree = steerDegree;
	}


	public int getCarDegree() {
		return carDegree;
	}


	public void setCarDegree(int carDegree) {
		this.carDegree = carDegree;
	}


	public RMIRegulatedMotor getAntrieb() {
		return antrieb;
	}


	public void setAntrieb(RMIRegulatedMotor antrieb) {
		this.antrieb = antrieb;
	}


	public RMIRegulatedMotor getLenkung() {
		return lenkung;
	}


	public void setLenkung(RMIRegulatedMotor lenkung) {
		this.lenkung = lenkung;
	}


	public RMIRegulatedMotor getLineOnCar() {
		return lineOnCar;
	}


	public void setLineOnCar(RMIRegulatedMotor lineOnCar) {
		this.lineOnCar = lineOnCar;
	}
	
	
}
