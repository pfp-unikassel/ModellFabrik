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
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

public class Sensordeamon extends Thread {

	private Steuerung s;
	private int counter = 0;
	private boolean stoper = false;
	private RemoteEV3 b105;
	private RemoteEV3 b107;
	private RemoteEV3 b113;

	//In dieser Funktion heißen die Bricks eigentlich anders!
	//TODO Brick Nummern richtig, jedoch auch in der Steuerung dann ändern
	public Sensordeamon(Steuerung s, RemoteEV3 b105, RemoteEV3 b107, RemoteEV3 b113) { // ad
		setDaemon(true); // makes this thread a deamon, closes itself after the main thread
		this.b105 = b105; // turntable
		this.b107 = b107;
		this.b113 = b113;
		this.s = s;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		/**
		 * legt Sampleprovider der Sensoren an, fuegt diese zu einer Liste hinzu und erstellt für jeden ein Array
		 * runs thread welcher in jedem durchlauf:
		 * die Sensoren abfragt und wenn diese ausloesen sender er dies and die Steuerung
		 * Startet außderdem Ui thread welcher das UI updated
		 */
		RMISampleProvider b1053 = b105.createSampleProvider("S3", "lejos.hardware.sensor.EV3TouchSensor", null); // drehtisch
		RMISampleProvider b1054 = b105.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); // lift
		RMISampleProvider b1072 = b107.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null); // counter
		RMISampleProvider b1131 = b113.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // kompressor
		RMISampleProvider b1073 = b107.createSampleProvider("S3", "lejos.hardware.sensor.EV3ColorSensor", "ColorID");
		RMISampleProvider b1051 = b105.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // lift1
		RMISampleProvider b1052 = b105.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null); // lift2

		s.addToSensorList(b1053);
		s.addToSensorList(b1054);
		s.addToSensorList(b1051);
		s.addToSensorList(b1052);
		s.addToSensorList(b1072);
		s.addToSensorList(b1131);
		s.addToSensorList(b1073);
		
		float[] Sensorarray2 = new float[5];
		float[] Sensorarray3 = new float[5];
		float[] Sensorarray4 = new float[5];
		float[] Sensorarray5 = new float[5];
		float[] Sensorarray6 = new float[5];
		float[] Sensorarray7 = new float[5];
		float[] Sensorarray8 = new float[5];
		
		while (!stoper) { // kontrolliere jederzeit ob einer der Sensoren etwas erkennt		
			counter++;
			try {
				Sensorarray2 = b1053.fetchSample();
				Sensorarray3 = b1054.fetchSample();
				Sensorarray4 = b1072.fetchSample();
				Sensorarray5 = b1073.fetchSample();
				Sensorarray6 = b1131.fetchSample();
				Sensorarray7 = b1051.fetchSample();
				Sensorarray8 = b1052.fetchSample();
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (Sensorarray2[0] == 1) {
				s.b1053Fired();
				s.sendMessage("LF");
				Sensorarray2[0] = 0;
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				s.resetSensorStatus();
			}
			if (Sensorarray3[0] == 1) {
				s.b1054Fired();
				s.sendMessage("TF");
				Sensorarray3[0] = 0;
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				s.resetSensorStatus();
			}
			if (Sensorarray4[0] == 1) { // counter sensor
				s.b1072Fired();
				s.sendMessage("CF");
				Sensorarray4[0] = 0;
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
					s.resetSensorStatus();
				}
			}
			if (Sensorarray6[0] == 1) {
				s.b1131Fired(true);
			//	System.out.println("Fired Kompressor Sensor");
				Sensorarray6[0] = 0;
			} else {
				s.b1131Fired(false);
			}
			if (Sensorarray7[0] == 1) {
				s.b1051Fired();
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Sensorarray7[0] = 0;
				s.resetSensorStatus();
			}
			if (Sensorarray8[0] == 1) {
				s.b1052Fired();
				Sensorarray8[0] = 0;
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				s.resetSensorStatus();
			}
			
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
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



	public boolean isStoper() {
		return stoper;
	}

	public void setStoper(boolean stoper) {
		this.stoper = stoper;
	}

}
