package test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import stations.FillStation;

public class TachoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Zweiter Brick, Motor a
		//fillStation = new FillStation(this, b102a);
		
		RemoteEV3 brick = null;
		
		
		try {
			brick = new RemoteEV3("192.168.0.102");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (brick != null) {
			System.out.println("Hat funktioniert");
			
			
			RMIRegulatedMotor motor = brick.createRegulatedMotor("A", 'L');
			
			
			try {
				
				int tachoBegin =  motor.getTachoCount();
				motor.setSpeed(90);
				
				
				motor.forward();
				Thread.sleep(5000);
				motor.stop(false);
				
								
				int tachoMitte = motor.getTachoCount();
				
							
				motor.backward();
				Thread.sleep(5000);
				motor.stop(true);
				
				
				int tachoEnde = motor.getTachoCount();
				
				System.out.println("Tachobeginn: " + tachoBegin + " Tachomitte: " + tachoMitte + " Tachoende: " + tachoEnde);
				
				//Er zieht die Drehungen voneinander ab: Tachoende ~0
				


				motor.close();
				
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
			
	}
}
