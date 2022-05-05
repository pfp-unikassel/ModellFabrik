package userInterface;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/* 
 * Controller for manual.fxml loaded in Controller.java
 * every Station has a vor and Back Button counted from bottom up 
 * 
 */

public class ManualController implements Initializable {

	private Steuerung s;
	public Button closeButton;

	public Button chargierVor1, chargierVor12, chargierVor13, chargierVor14, chargierVor15, chargierBack1,
			chargierBack12, chargierBack13, chargierBack14, chargierBack15, elevatorVor1, elevatorVor12, elevatorVor13,
			elevatorVor14, elevatorVor15, elevatorBack1, elevatorBack12, elevatorBack13, elevatorBack14, elevatorBack15,
			stockVor1, stockVor12, stockVor13, stockVor14, stockBack1, stockBack12, stockBack13, stockBack14, liftVor1,
			liftVor11, liftVor12, liftVor13, liftVor14, liftBack1, liftBack11, liftBack12, liftBack13, liftBack14,
			cleanerVor1, cleanerVor11, cleanerBack1, cleanerBack11, qualityVor1, qualityVor11, qualityVor12,
			qualityBack1, qualityBack11, qualityBack12, fillVor1, fillBack1, deliveryVor1, deliveryVor11, deliveryVor12,
			deliveryVor13, deliveryVor14, deliveryBack1, deliveryBack11, deliveryBack12, deliveryBack13, deliveryBack14,
			carVor1, carVor12, carVor13, carBack1, carBack12, carBack13, airarmsVor1, airarmsVor12, airarmsVor13,
			airarmsVor14, airarmsVor15, airarmsVor16, airarmsBack1, airarmsBack12, airarmsBack13, airarmsBack14,
			airarmsBack15, airarmsBack16;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void start() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		s = (Steuerung) stage.getUserData();
	}

	public void updateTextfields() {

	}

	public void closeButtonPushed() {

		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}

	// try {
	// s.getChargier().stopLineToTable();
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// --------------------------------------------------------------------Turn
	// Table
	public void chargierVorButtonPressed1() {

		try {
			s.getChargier().getAntriebBandZumDT().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased1() {
		try {
			s.getChargier().getAntriebBandZumDT().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed1() {
		try {
			s.getChargier().getAntriebBandZumDT().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased1() {
		try {
			s.getChargier().getAntriebBandZumDT().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// on
	// Table
	public void chargierVorButtonPressed12() {
		try {
			s.getChargier().getAntriebBandProd().forward();;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased12() {
		try {
			s.getChargier().getAntriebBandProd().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed12() {
		try {
			s.getChargier().getAntriebBandProd().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased12() {
		try {
			s.getChargier().getAntriebBandProd().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// to
	// store
	public void chargierVorButtonPressed13() {
		try {
			s.getChargier().startLineToStore(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased13() {
		try {
			s.getChargier().stopLineToStorer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed13() {
		try {
			s.getChargier().startLineToStore(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased13() {
		try {
			s.getChargier().stopLineToStorer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// to
	// production
	public void chargierVorButtonPressed14() {
		try {
			s.getChargier().getAntriebDrehtisch().forward();;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased14() {
		try {
			s.getChargier().getAntriebDrehtisch().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed14() {
		try {
			s.getChargier().getAntriebDrehtisch().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased14() {
		try {
			s.getChargier().getAntriebDrehtisch().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Line
	// to
	// Table
	public void chargierVorButtonPressed15() {
		try {
			s.getChargier().getDrehtischRotieren().forward();;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierVorButtonReleased15() {
		try {
			s.getChargier().getDrehtischRotieren().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonPressed15() {
		try {
			s.getChargier().getDrehtischRotieren().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void chargierBackButtonReleased15() {
		try {
			s.getChargier().getDrehtischRotieren().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- 
	// Band auf dem Elevator
	public void elevatorVorButtonPressed1() {
		try {
			s.getStock().startLineToStock(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased1() {
		try {
			s.getStock().stopLaneToStock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed1() {
		try {
			s.getStock().startLineToStock(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased1() {
		try {
			s.getStock().stopLaneToStock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}



	// --------------------------------------------------------------------
	// Elevator up/down
	public void elevatorVorButtonPressed15() {
		try {
			s.getStock().getElevatorVerticalleft().forward();
			s.getStock().getElevatorVerticalright().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased15() {
		try {
			s.getStock().getElevatorVerticalleft().flt(false);
			s.getStock().getElevatorVerticalright().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed15() {
		try {
			s.getStock().getElevatorVerticalleft().backward();
			s.getStock().getElevatorVerticalright().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased15() {
		try {
			s.getStock().getElevatorVerticalleft().flt(false);
			s.getStock().getElevatorVerticalright().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Elevator forward/backward
	public void elevatorVorButtonPressed14() {
		try {
			s.getStock().getElevatorHorizontal().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorVorButtonReleased14() {
		try {
			s.getStock().getElevatorHorizontal().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonPressed14() {
		try {
			s.getStock().getElevatorHorizontal().forward();

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void elevatorBackButtonReleased14() {
		try {
			s.getStock().getElevatorHorizontal().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock1
	public void stockVorButtonPressed1() {
		try {
			s.getStock().getStockPlace1().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased1() {
		try {
			s.getStock().getStockPlace1().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed1() {
		try {
			s.getStock().getStockPlace1().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased1() {
		try {
			s.getStock().getStockPlace1().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock2
	public void stockVorButtonPressed12() {
		try {
			s.getStock().getStockPlace2().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased12() {
		try {
			s.getStock().getStockPlace2().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed12() {
		try {
			s.getStock().getStockPlace2().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased12() {
		try {
			s.getStock().getStockPlace2().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock3
	public void stockVorButtonPressed13() {
		try {
			s.getStock().getStockPlace3().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased13() {
		try {
			s.getStock().getStockPlace3().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed13() {
		try {
			s.getStock().getStockPlace3().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased13() {
		try {
			s.getStock().getStockPlace3().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Stock4
	public void stockVorButtonPressed14() {
		try {
			s.getStock().getStockPlace4().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockVorButtonReleased14() {
		try {
			s.getStock().getStockPlace4().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonPressed14() {
		try {
			s.getStock().getStockPlace4().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void stockBackButtonReleased14() {
		try {
			s.getStock().getStockPlace4().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// shaker
	public void liftVorButtonPressed1() {
		try {
			s.getLift().getShaker().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased1() {
		try {
			s.getLift().getShaker().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed1() {
		try {
			s.getLift().getShaker().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased1() {
		try {
			s.getLift().getShaker().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- lift
	// right in out
	public void liftVorButtonPressed11() {
		try {
			s.getLift().getgrabber_left().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased11() {
		try {
			s.getLift().getgrabber_left().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed11() {
		try {
			s.getLift().getgrabber_left().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased11() {
		try {
			s.getLift().getgrabber_left().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// -------------------------------------------------------------------- lift
	// left in out

	public void liftVorButtonPressed12() {
		try {
			s.getLift().getgrabber_right().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased12() {
		try {
			s.getLift().getgrabber_right().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed12() {
		try {
			s.getLift().getgrabber_right().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased12() {
		try {
			s.getLift().getgrabber_right().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}



	// -------------------------------------------------------------------- lift
	// up down
	public void liftVorButtonPressed14() {
		try {
			s.getLift().getHeben().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftVorButtonReleased14() {
		try {
			s.getLift().getHeben().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonPressed14() {
		try {
			s.getLift().getHeben().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void liftBackButtonReleased14() {
		try {
			s.getLift().getHeben().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// liftline
	public void cleanerVorButtonPressed1() {
		try {
			s.getCleaner().startLiftLine(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerVorButtonReleased1() {
		try {
			s.getCleaner().stopLiftLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonPressed1() {
		try {
			s.getCleaner().startLiftLine(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonReleased1() {
		try {
			s.getCleaner().stopLiftLine();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// --------------------------------------------------------------------
	// cleaner

	public void cleanerVorButtonPressed11() {
		try {
			s.getCleaner().startCleaner(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerVorButtonReleased11() {
		try {
			s.getCleaner().stopCleaner();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonPressed11() {
		try {
			s.getCleaner().startCleaner(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void cleanerBackButtonReleased11() {
		try {
			s.getCleaner().stopCleaner();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// colorsensor gate
	public void qualityVorButtonPressed1() {
		try {
			s.getQuality().getGate().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityVorButtonReleased1() {
		try {
			s.getQuality().getGate().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonPressed1() {
		try {
			s.getQuality().getGate().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonReleased1() {
		try {
			s.getQuality().getGate().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// color
	// line
	public void qualityVorButtonPressed11() {
		try {
			s.getQuality().getLine().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityVorButtonReleased11() {
		try {
			s.getQuality().getLine().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonPressed11() {
		try {
			s.getQuality().getLine().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonReleased11() {
		try {
			s.getQuality().getLine().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// counter
	// line
	public void qualityVorButtonPressed12() {
		try {
			s.getQuality().getCounterLine().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityVorButtonReleased12() {
		try {
			s.getQuality().getCounterLine().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonPressed12() {
		try {
			s.getQuality().getCounterLine().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void qualityBackButtonReleased12() {
		try {
			s.getQuality().getCounterLine().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms Schalter 1
	public void airarmsVorButtonPressed1() {
		try {
			s.getAirarms().getMoveArm().forward();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased1() {
		try {
			s.getAirarms().getMoveArm().flt(false);
			System.out.println(s.getAirarms().getMoveArm().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed1() {
		try {
			s.getAirarms().getMoveArm().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased1() {
		try {
			s.getAirarms().getMoveArm().flt(false);
			System.out.println(s.getAirarms().getMoveArm().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms Schalter 2
	public void airarmsVorButtonPressed12() {
		try {
			s.getAirarms().getVerticalArm().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased12() {
		try {
			s.getAirarms().getVerticalArm().flt(false);
			System.out.println(s.getAirarms().getVerticalArm().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed12() {
		try {
			s.getAirarms().getVerticalArm().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased12() {
		try {
			s.getAirarms().getVerticalArm().flt(false);
			System.out.println(s.getAirarms().getVerticalArm().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms Schalter 3
	public void airarmsVorButtonPressed13() {
		try {
			s.getAirarms().getTurnGrab().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased13() {
		try {
			s.getAirarms().getTurnGrab().flt(false);
			System.out.println(s.getAirarms().getTurnGrab().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed13() {
		try {
			s.getAirarms().getTurnGrab().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased13() {
		try {
			s.getAirarms().getTurnGrab().flt(false);
			System.out.println(s.getAirarms().getTurnGrab().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms Schalter 4
	public void airarmsVorButtonPressed14() {
		try {
			s.getAirarms().getOpenCloseGrab().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased14() {
		try {
			s.getAirarms().getOpenCloseGrab().flt(false);
			System.out.println(s.getAirarms().getOpenCloseGrab().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed14() {
		try {
			s.getAirarms().getOpenCloseGrab().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased14() {
		try {
			s.getAirarms().getOpenCloseGrab().flt(false);
			System.out.println(s.getAirarms().getOpenCloseGrab().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// --------------------------------------------------------------------
	// Airarms Schalter 5 TODO
	public void airarmsVorButtonPressed17() {
		try {
			s.getAirarms().getOpenCloseGrab().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased17() {
		try {
			s.getAirarms().getOpenCloseGrab().flt(false);
			System.out.println(s.getAirarms().getOpenCloseGrab().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed17() {
		try {
			s.getAirarms().getOpenCloseGrab().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased17() {
		try {
			s.getAirarms().getOpenCloseGrab().flt(false);
			System.out.println(s.getAirarms().getOpenCloseGrab().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms Dreharm
	public void airarmsVorButtonPressed15() {
		try {
			s.getAirarms().getTurnArm1().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased15() {
		try {
			s.getAirarms().getTurnArm1().flt(false);
			System.out.println(s.getAirarms().getTurnArm1().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed15() {
		try {
			s.getAirarms().getTurnArm1().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased15() {
		try {
			s.getAirarms().getTurnArm1().flt(false);
			System.out.println(s.getAirarms().getTurnArm1().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	// Airarms Dreharm
	public void airarmsVorButtonPressed16() {
		try {
			s.getAirarms().getTurnArm2().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsVorButtonReleased16() {
		try {
			s.getAirarms().getTurnArm2().flt(false);
			System.out.println(s.getAirarms().getTurnArm2().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonPressed16() {
		try {
			s.getAirarms().getTurnArm2().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void airarmsBackButtonReleased16() {
		try {
			s.getAirarms().getTurnArm2().flt(false);
			System.out.println(s.getAirarms().getTurnArm2().getTachoCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- fill
	public void fillVorButtonPressed1() {
		try {
			s.getFillStation().getWheel().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fillVorButtonReleased1() {
		try {
			s.getFillStation().getWheel().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fillBackButtonPressed1() {
		try {
			s.getFillStation().getWheel().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fillBackButtonReleased1() {
		try {
			s.getFillStation().getWheel().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Gate
	// D
	public void deliveryVorButtonPressed1() {
		try {
			s.getDelivery().getGateD().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased1() {
		try {
			s.getDelivery().getGateD().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed1() {
		try {
			s.getDelivery().getGateD().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased1() {
		try {
			s.getDelivery().getGateD().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- gate
	// C
	public void deliveryVorButtonPressed11() {
		try {
			s.getDelivery().getGateC().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased11() {
		try {
			s.getDelivery().getGateC().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed11() {
		try {
			s.getDelivery().getGateC().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased11() {
		try {
			s.getDelivery().getGateC().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- Box
	// door
	public void deliveryVorButtonPressed12() {
		try {
			s.getDelivery().getGateB().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased12() {
		try {
			s.getDelivery().getGateB().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed12() {
		try {
			s.getDelivery().getGateB().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased12() {
		try {
			s.getDelivery().getGateB().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- line
	// to
	// customers
	public void deliveryVorButtonPressed13() {
		try {
			s.getDelivery().getLineToEnd().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased13() {
		try {
			s.getDelivery().getLineToEnd().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed13() {
		try {
			s.getDelivery().getLineToEnd().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased13() {
		try {
			s.getDelivery().getLineToEnd().flt(false);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------- line
	// from puffer to arms
	public void deliveryVorButtonPressed14() {
		try {
			s.getDelivery().getLineToArms().backward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void deliveryVorButtonReleased14() {
		try {
			s.getDelivery().getLineToArms().flt(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonPressed14() {
		try {
			s.getDelivery().getLineToArms().forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deliveryBackButtonReleased14() {
		try {
			s.getDelivery().getLineToArms().flt(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// -------------------------------------------------------------------- CAR
	// Line

	 public void carVorButtonPressed1() {
	 try {
	 Steuerung.getCar().getLineOnCar().forward();
	 } catch (RemoteException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public void carVorButtonReleased1() {
	 try {
	 Steuerung.getCar().getLineOnCar().flt(false);
	 } catch (RemoteException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
	
	 public void carBackButtonPressed1() {
	 try {
	 Steuerung.getCar().getLineOnCar().backward();
	 } catch (RemoteException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
	
	 public void carBackButtonReleased1() {
	 try {
	 Steuerung.getCar().getLineOnCar().flt(false);
	 } catch (RemoteException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }

	// -------------------------------------------------------------------- CAR
	// Lenkung

	public void carVorButtonPressed12() {
		try {
			Steuerung.getCar().getLenkung().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void carVorButtonReleased12() {
		try {
			Steuerung.getCar().getLenkung().flt(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonPressed12() {
		try {
			Steuerung.getCar().getLenkung().backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonReleased12() {
		try {
			Steuerung.getCar().getLenkung().flt(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------- CAR
	// antrieb

	public void carVorButtonPressed13() {
		try {
			Steuerung.getCar().getAntrieb().forward();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void carVorButtonReleased13() {
		try {
			Steuerung.getCar().getAntrieb().flt(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonPressed13() {
		try {
			Steuerung.getCar().getAntrieb().backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carBackButtonReleased13() {
		try {
			Steuerung.getCar().getAntrieb().flt(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
