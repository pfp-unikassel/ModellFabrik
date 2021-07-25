package controller;

/**
 *  Der Sensordeamon ist ein eigener Thread welche die Sensoren der Bricks abfragt und die Werte and die Steuerung weitergibt.
 *  Er schliesst sich automatisch beim beenden
 *  Er initialisiert die Sensoren der Bricks als RMISampleProvider, denn man kann sonst nur 4 (Bug in Lego Libary)
 *  normale Sensoren benutzen.
 *  z.B. RFID Sensoren sind externe Sensoren welche nicht als RMISampleProvider angelegt werden koennen.
 */
import java.rmi.RemoteException;

import javafx.application.Platform;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

public class Sensordaemon extends Thread {

	private Steuerung s;
	private int counter = 0;
	private boolean stoper = false;

	private RemoteEV3 b105;
	private RemoteEV3 b106;
	private RemoteEV3 b107;
	private RemoteEV3 b113;
	private RemoteEV3 b115;
	private RMIRegulatedMotor m;

	public Sensordaemon(Steuerung s, RemoteEV3 b105, RemoteEV3 b106, RemoteEV3 b107, RemoteEV3 b113, RemoteEV3 b115) { // ad
		// Motor
		// m
		// vom
		// greifarm
		setDaemon(true); // makes this thread a deamon, closes itself after the
							// main thread
		this.b105 = b105; // turntable
		this.b106 = b106;
		this.b107 = b107;
		this.b113 = b113;
		this.b115 = b115;
		this.s = s;
		// this.m = b;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		/**
		 * legt Sampleprovider der Sensoren an 
		 * fuegt diese zu einer Liste hinzu 
		 * und erstellt für jeden ein Array
		 * 
		 * runs thread welcher in jedem durchlauf:
		 * die Sensoren abfragt und wenn diese ausloesen sender er dies and die Steuerung
		 * Startet außderdem Ui thread welcher das UI updated
		 * 
		 */
		// RMISampleProvider b1061 = b106.createSampleProvider("S1",
		// "lejos.hardware.sensor.EV3UltrasonicSensor", null); // "Distance"
		// mode
		// instead
		// null

		RMISampleProvider b1053 = b105.createSampleProvider("S3", "lejos.hardware.sensor.EV3TouchSensor", null); // drehtisch
		RMISampleProvider b1054 = b105.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); // lift
		RMISampleProvider b1072 = b107.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null); // counter
		RMISampleProvider b1131 = b113.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // kompressor
		RMISampleProvider b1073 = b107.createSampleProvider("S3", "lejos.hardware.sensor.EV3ColorSensor", "ColorID");
		// "lejos.hardware.sensor.EV3ColorSensor", "ColorID");
		
		//NEU:
		RMISampleProvider b1051 = b105.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // lift1
		RMISampleProvider b1052 = b105.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null); // lift2

		
		s.addToSensorList(b1053);
		s.addToSensorList(b1054);
		//Neu
		s.addToSensorList(b1051);
		s.addToSensorList(b1052);
		
		// s.addToSensorList(b1061);
		s.addToSensorList(b1072);
		s.addToSensorList(b1131);
		s.addToSensorList(b1073);
		// s.addToSensorList(b1151);

		
		//HIER NOCH EINFÜGEN
		float[] Sensorarray1 = new float[5];
		float[] Sensorarray2 = new float[5];
		float[] Sensorarray3 = new float[5];
		float[] Sensorarray4 = new float[5];
		float[] Sensorarray5 = new float[5];
		float[] Sensorarray6 = new float[5];
		float[] Sensorarray7 = new float[5];
		float[] Sensorarray8 = new float[5];
		
		//int[] filterArray = new int[15];

		// try {
		// m.setStallThreshold(1, 100); // int fehler in zeit and Motor anpassen
		// } catch (RemoteException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		while (!stoper) { // kontrolliere jederzeit ob einer der Sensoren etwas
						// erkennt
		
			counter++;
			
			try {
				// Sensorarray1 = b1061.fetchSample();
				Sensorarray2 = b1053.fetchSample();
				Sensorarray3 = b1054.fetchSample();
				
				Sensorarray4 = b1072.fetchSample();
				Sensorarray5 = b1073.fetchSample();
				Sensorarray6 = b1131.fetchSample();
								
				Sensorarray7 = b1051.fetchSample();
				Sensorarray8 = b1052.fetchSample();
				

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			if (Sensorarray1[0] == 1) { // wenn schalter gedrueckt wurde dann

				s.b1061Fired();
				waitSek(4);
				Sensorarray1[0] = 0;
				s.resetSensorStatus();

			}
			if (Sensorarray2[0] == 1) {
				s.b1053Fired();
				s.sendMessage("LF");
				waitSek(5);
				Sensorarray2[0] = 0;
				s.resetSensorStatus();

			}
			if (Sensorarray3[0] == 1) {
				s.b1054Fired();
				s.sendMessage("TF");
				waitSek(5);
				Sensorarray3[0] = 0;
				s.resetSensorStatus();
			}
			if (Sensorarray4[0] == 1) { // counter sensor
				s.b1072Fired();
				s.sendMessage("CF");
				Sensorarray4[0] = 0;
				waitSek(3); //test maybe delete later
				s.resetSensorStatus();
			}

			if (Sensorarray5[0] != -1) {
				int coloIndex = (int) Sensorarray5[0];
				String colorString = "";
				switch (coloIndex) {

				case Color.BLACK:
					colorString = "BLACK";
// 				System.out.println("black");
//					s.sendMessage("S0");  bring it back in after the Line is yellow/ not black anymore
					break;
				case Color.BLUE:
					colorString = "BLUE";
					System.out.println("					blue");
					break;
				case Color.GREEN:
					colorString = "GREEN";
					System.out.println("			green");
					break;
				case Color.YELLOW: 
					colorString = "YELLOW";
					System.out.println("				yellow");
					break;
				case Color.RED:
					colorString = "RED";
					System.out.println("		red");
					s.sendMessage("R0");
					break;
				case Color.WHITE:
					colorString = "WHITE";
					System.out.println("	white");
					s.sendMessage("W1");
					break;
				case Color.BROWN:
					colorString = "BROWN";
					System.out.println("brown");
					break;
				}
				s.b1073Fired(colorString);

				if (colorString != "BLACK") {
					//waitSek(5);  // TODO: maybe turn line to sensor slow
					try {
						//System.out.println("Im sleeping now");
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					waitSek(6);
					s.resetSensorStatus();

				}
			}

			if (Sensorarray6[0] == 1) {
				s.b1131Fired(true);
				Sensorarray6[0] = 0;
				//Thread.sleep(100);
			} else {
				s.b1131Fired(false);
			}

			//NEU
			if (Sensorarray7[0] == 1) {
				s.b1051Fired();
				waitSek(2);
				Sensorarray7[0] = 0;
				s.resetSensorStatus();
			}
				
			if (Sensorarray8[0] == 1) {
				s.b1052Fired();
				waitSek(2);
				Sensorarray8[0] = 0;
				s.resetSensorStatus();
			}
			
			

			// -------------UI
			// changes--------------------------------------------------------------------------------------------
			/**
			 * ruft die Labelupdate Methode jedesmal in einem Platformthread auf
			 * Ruft die Update Powerlevel nur bei jedem 100 durchlauf auf
			 */

			Platform.runLater( // rows this in Ui Thread Q, maybe to much actions
					() -> {
						s.updateLabelInController();

						if (counter == 50000) { // ad counter++
							counter = 0;
							s.updatePowerLevel(); // counter Wert 100: alle 5000 nanosekunden sendet er hier ein paket zu jedem brick
						}
					});

			// -------------UI
			// changes----------------------------------------------------------------------------------------------
		}
	}

	public void waitSek(int sekunden) {

		try {
			sleep(sekunden * 100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int filterIds(int[] filterArray) {

		/** Filtert int Array mit 5 werten und gibt den heufigsten wieder
		 * wird bei der verwendung der RFID sensoren gebraucht
		 */
		int firstId = filterArray[0];
		int firstCounter = 0;
		int secondId = 0;
		int secondCounter = 0;

		for (int i = 1; i < 5; i++) { // i< incomming values
			if (filterArray[i] != 0) { // 0 is no value
				if (firstId == filterArray[i]) {
					firstCounter++;
				} else { // if id not the same as first id
					secondId = filterArray[i]; // set second id
					if (secondId == filterArray[i]) {
						secondCounter++;
					}
				}
			}
		}

		if (firstCounter > secondCounter) { // returns most showen id
			return firstId;
		} else {
			return secondId;
		}
	}

	public boolean isStoper() {
		return stoper;
	}

	public void setStoper(boolean stoper) {
		this.stoper = stoper;
	}

}
