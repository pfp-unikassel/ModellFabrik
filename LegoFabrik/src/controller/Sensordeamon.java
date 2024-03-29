package controller;
/**
 *  Der Sensordeamon ist ein eigener Thread welche die Sensoren der Bricks abfragt und die Werte an die Steuerung weitergibt.
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
	private int i = 0;
	private boolean stopper = false;
	private RemoteEV3 b102;
	private RemoteEV3 b104;
	private RemoteEV3 b107;
	private RemoteEV3 b112;
	public Sensordeamon(Steuerung s, RemoteEV3 b102, RemoteEV3 b104, RemoteEV3 b107, RemoteEV3 b112) { // ad
		setDaemon(true); // makes this thread a deamon, closes itself after the main thread
		this.b102 = b102;
		this.b104 = b104;
		this.b107 = b107;
		this.b112 = b112;
		this.s = s;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		/**
		 * legt Sampleprovider der Sensoren an, fuegt diese zu einer Liste hinzu und erstellt f�r jeden ein Array
		 * runs thread welcher in jedem durchlauf:
		 * die Sensoren abfragt und wenn diese ausloesen sender er dies and die Steuerung
		 * Startet au�derdem Ui thread welcher das UI updated
		 */
		RMISampleProvider turntable_endstop = b102.createSampleProvider("S3", "lejos.hardware.sensor.EV3TouchSensor", null); // endstop auf drehtisch
		RMISampleProvider lift_lane_endstop = b102.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); // lift endstop
		RMISampleProvider counter = b104.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null); // counter
		RMISampleProvider compressor_sensor = b107.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // kompressor
		RMISampleProvider qa_1_color = b104.createSampleProvider("S3", "lejos.hardware.sensor.EV3ColorSensor", "ColorID"); //Farbsensor qa_1
		RMISampleProvider endstop_to_storage = b102.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); // drehttisch endstop to storage lane
		RMISampleProvider endstop_to_lift = b102.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null); // drehtisch endstop to lift lane
		RMISampleProvider storagelift_stop_right = b112.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); //
		RMISampleProvider storagelift_stop_left = b112.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); //
		s.addToSensorList(turntable_endstop);
		s.addToSensorList(lift_lane_endstop);
		s.addToSensorList(endstop_to_storage);
		s.addToSensorList(endstop_to_lift);
		s.addToSensorList(counter);
		s.addToSensorList(compressor_sensor);
		s.addToSensorList(qa_1_color);
		s.addToSensorList(storagelift_stop_right);
		s.addToSensorList(storagelift_stop_left);
		
		float[] turntable_endstop_array = new float[5];
		float[] lift_lane_endstop_array = new float[5];
		float[] counter_array = new float[5];
		float[] qa_1_color_array = new float[5];
		float[] compressor_sensor_array = new float[5];
		float[] endstop_to_storage_array = new float[5];
		float[] endstop_to_lift_array = new float[5];
		float[] storagelift_stop_right_array = new float[5];
		float[] storagelift_stop_left_array = new float[5];
		
		while (!stopper) { // kontrolliere jederzeit ob einer der Sensoren etwas erkennt		
			i++;
			try {
				turntable_endstop_array = turntable_endstop.fetchSample();
				lift_lane_endstop_array = lift_lane_endstop.fetchSample();
				counter_array = counter.fetchSample();
				qa_1_color_array = qa_1_color.fetchSample();
				compressor_sensor_array = compressor_sensor.fetchSample();
				endstop_to_storage_array = endstop_to_storage.fetchSample();
				endstop_to_lift_array = endstop_to_lift.fetchSample();
				storagelift_stop_right_array = storagelift_stop_right.fetchSample();
				storagelift_stop_left_array  = storagelift_stop_left.fetchSample();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (turntable_endstop_array[0] == 1) {
				s.turntable_endstopFired();
			}
			if (lift_lane_endstop_array[0] == 1) {
				s.lift_lane_endstopFired();
			}
			if (counter_array[0] == 1) { // Z�hl-Sensor
				s.counterFired(true);
			}
			if (counter_array[0] == 0) { // Z�hl-Sensor
				s.counterFired(false);
			}
			if (qa_1_color_array[0] != -1) {
				int colorIndex = (int) qa_1_color_array[0];
				String colorString = "";
				switch (colorIndex) {
				case Color.BLACK:
					colorString = "BLACK";
					break;
				case Color.BLUE:
					colorString = "BLUE";
					System.out.println("blue");
					break;
				case Color.GREEN:
					colorString = "GREEN";
					System.out.println("green");
					break;
				case Color.YELLOW: 
					colorString = "YELLOW";
					System.out.println("yellow");
					break;
				case Color.RED:
					colorString = "RED";
					System.out.println("red");
					s.sendMessage("R0");
					break;
				case Color.WHITE:
					colorString = "WHITE";
					System.out.println("white");
					s.sendMessage("W1");
					break;
				case Color.BROWN:
					colorString = "BROWN";
					System.out.println("brown");
					break;
				}
				s.qa_1_colorFired(colorString);
			}
			if (compressor_sensor_array[0] == 1) {
				s.compressor_sensorFired(true);
			} else {
				s.compressor_sensorFired(false);
			}
			if (endstop_to_storage_array[0] == 1) {
				s.endstop_to_storageFired();
			}
			if (endstop_to_lift_array[0] == 1) {
				s.endstop_to_liftFired();
			}
			if (storagelift_stop_right_array[0] == 1 ) {
				s.storagelift_stop_rightFired();
			}
			if (storagelift_stop_left_array[0] == 1 ) {
				s.storagelift_stop_leftFired();
			}
			
			
			try {
				sleep(50);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			/**
			 * ruft die Labelupdate Methode jedesmal in einem Platformthread auf
			 * Ruft die Update Powerlevel nur bei jedem 100 durchlauf auf
			 */

			Platform.runLater( // rows this in Ui Thread Q, maybe to much actions
					() -> {
						s.updateLabelInController();
						if (i == 50000) { // ad i++
							i = 0;
							s.updatePowerLevel(); // i Wert 100: alle 5000 nanosekunden sendet er hier ein paket zu jedem brick
						}
					});
		}
	}

	public boolean isstopper() {
		return stopper;
	}
	public void setstopper(boolean stopper) {
		this.stopper = stopper;
	}
}
