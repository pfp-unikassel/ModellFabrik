package stations;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import java.rmi.RemoteException;
import controller.Steuerung;

public class stockdeamon extends Thread {
	private RemoteEV3 b112;
	private Steuerung s;
	private Stock stock;
	private int counter = 0;
	private boolean stopper = false;
	public stockdeamon(Steuerung s, RemoteEV3 b112) {
		System.out.println("Stockdeamon erstellt");
		setDaemon(true); // makes this thread a deamon, closes itself after the main thread
		this.s = s;
		this.b112 = b112; //
	}
	@Override
	public void run() {
		RMISampleProvider b1121 = b112.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null); //
		RMISampleProvider b1124 = b112.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null); //
		s.addToSensorList(b1121);
		s.addToSensorList(b1124);
		float[] Sensorarray1 = new float[5];
		float[] Sensorarray4 = new float[5];
		while (!stopper) { // kontrolliere jederzeit ob einer der Sensoren etwas erkennt	
			counter++;
			try {
				Sensorarray1 = b1121.fetchSample();
				Sensorarray4 = b1124.fetchSample();
				if (Sensorarray1[0] == 1) {
					stock.endstopright = true;
					System.out.println("Stock: Endstop Rechts getriggert");
				}
				if (Sensorarray4[0] == 1) {
					stock.endstopleft = true;
					System.out.println("Stock: Endstop Links getriggert");
				}
				if (Sensorarray1[0] == 1 && Sensorarray4[0] == 1) {
					stopper = true;
				}
				if (counter > 6000) {
					stopper = true;
					System.out.println("Zeitüberschreitung, nicht beide Endstops getriggert!");
					stock.endstopright = true;
					stock.endstopleft = true;
				}
			}
			catch (RemoteException e) {}
			try {
				sleep(50);
			} 
			catch (InterruptedException e) {}
		}
	}

}