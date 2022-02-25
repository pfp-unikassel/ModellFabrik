package userInterface;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.Steuerung;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller implements Initializable {

	/**
	 * controller des main Ui
	 * jedes element muss mit der FX id deklariert werden, java fx initialisiert sie selber
	 * 
	 * startet steuerung und uebergibt sich selber
	 */
	
	public Button startButton;
	public Button stopButton;
	public Button pauseButton;
	public Button emptyButton;
	public Button connectButton;
	public Button szenarioButton1;
	public Button szenarioButton2;
	public Button szenarioButton3;
	public Label topLeftLabel;
	public Label readyLabel;
	public Label leftBottomLabel;
	public Label rightBottomLabel;
	public Circle showBall;

	public Label defaultszenario, defaultlabelbetriebszeit, defaultlabelware, defaultlabelio, defaultlabelnio,
			defaultlabeldurchsatz, defaultlabelpuffer, defaultlabelverbrauch, defaultlabelversand; // Labels
																									// Default
																									// Details

	public Label detaillabel00, detaillabel01, detaillabel02, detaillabel03, detaillabel04, detaillabel05,
			detaillabel06, detaillabel07, detaillabel08, detailslabel10, detailslabel11, detailslabel12, detailslabel13,
			detailslabel14, detailslabel15, detailslabel16, detailslabel17, detailslabel18;

	public Label label00, label01, label02, label03, label04, label10, label11, label12, label13, label14, label20,
			label21, label22, label23, label24, label30, label31, label32, label33, label34, label05, label15, label25,
			label35, label06, label16, label26, label36; // LAbel
	// akku
	// 0X 2X Names 1X 3X Values

	public Circle led1, led2, led3, led4, led5, led6, led7;

	public Button test1, test2, test3, test4, test5, test6, test7,test8;

	public Button details1, details2, details3, details4, details5, details6, details7,details8;

	public Button stop1, stop2, stop3, stop4, stop5, stop6, stop7,stop8;

	public CheckBox box1, box2, box3, box4, box5, box6, box7,box8;

	public MenuItem ipconfig, motorSettings,manual;

	public boolean paused; // True if game paused right now
	public boolean running;
	private boolean connected = false; // connected with bricks  
	private Timeline timeline;
	private float timer = 0;
	long startTime;
	DateFormat timeFormat = new SimpleDateFormat("mm:ss");

	private ArrayList<Label> brickLabels;

	public Steuerung s;

	@Override
	public void initialize(URL location, ResourceBundle resources) { // gets stared with progran

		s = new Steuerung(this);
		brickLabels = new ArrayList<>();
		addBrickLabeltoList();
		paused = false;
		running = false;

		updatePowerLevel();
		startTimer();

		// --------------------------------------------Menu-------------------------------------------
		motorSettings.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Parent root;
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/motorSettings.fxml"));
					root = loader.load(); // FXMLLoader.load(getClass().getClassLoader().getResource("/settings.fxml"),
											// resources);

					Stage stage = new Stage();
					stage.setTitle("Settings");
					stage.setScene(new Scene(root, 600, 650));
					stage.setUserData(s);
					stage.show();

					MotorSettingsController motorController = loader.<MotorSettingsController>getController();
					motorController.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		ipconfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Parent root1;
				try {
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ipconfig.fxml"));
					root1 = loader1.load();

					Stage stage1 = new Stage();
					stage1.setTitle("Ip Config");
					stage1.setScene(new Scene(root1, 600, 650));
					stage1.setUserData(s);
					stage1.show();

					IpconfigController ipController = loader1.<IpconfigController>getController();
					ipController.start();
					// Hide this current window (if this is what you want)
					// ((Node)(event.getSource())).getScene().getWindow().hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		manual.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Parent root2;
				try {
					FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/manual.fxml"));
					root2 = loader2.load();

					Stage stage2 = new Stage();
					stage2.setTitle("Manuel Control");
					stage2.setScene(new Scene(root2, 600, 650));
					stage2.setUserData(s);
					stage2.show();

					ManualController manuelController = loader2.<ManualController>getController();
					manuelController.start();
					// Hide this current window (if this is what you want)
					// ((Node)(event.getSource())).getScene().getWindow().hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
	// --------------------------------------------/Menu---------------------------------------------
	// -----------------------------------------------------Details---------------------------------

	public void setDetailAirarms() {

		detaillabel01.setText("Turmposition:");
		detaillabel02.setText("Armposition:");
		detaillabel03.setText("Armstatus:");
		detaillabel04.setText("Greiferposition:");
		detaillabel05.setText("GreiferStatus:");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getAirarms().getTurnDegree() + "");
		
		if (s.getAirarms().getArmPosition()) {
			detailslabel12.setText("Ausgefahren");
		}else {
			detailslabel12.setText("Eingefahren");
		}
		if (s.getAirarms().getArmStatus()) {
			detailslabel13.setText("Oben");
		}else {
			detailslabel13.setText("Unten");
		}
		if (s.getAirarms().getGrabStatus()) {
			detailslabel14.setText("||");
		}else {
			detailslabel14.setText("=");
		}
		if (s.getAirarms().getGrabPosition()) {
			detailslabel15.setText("Offen");
		}else {
			detailslabel15.setText("Geschlossen");
		}
		
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");

	}

	public void setDetailCar() {
		detaillabel01.setText("");
		detaillabel02.setText("");
		detaillabel03.setText("");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText("");
		detailslabel12.setText("");
		detailslabel13.setText("");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailShaker() {
		detaillabel01.setText("R�ttelgeschwindigkeit:");
		detaillabel02.setText("");
		detaillabel03.setText("");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getLift().getShakerSpeed() + "");
		detailslabel12.setText("");
		detailslabel13.setText("");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailChargier() {
		detaillabel01.setText("Tischposition");
		detaillabel02.setText("Bandgeschwindigkeit");
		detaillabel03.setText("");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getChargier().getTablePostion() + "");
		detailslabel12.setText(s.getChargier().getLineSpeed()+"");
		detailslabel13.setText("");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailCleaner() {
		detaillabel01.setText("Drehgeschwindigkeit");
		detaillabel02.setText("Hebebandgeschindigkeit:");
		detaillabel03.setText("");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getCleaner().getCleanerSpeed() + "");
		detailslabel12.setText(s.getCleaner().getLiftLaneSpeed() + "");
		detailslabel13.setText("");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailCompressor() {
		detaillabel01.setText("Druck:");
		detaillabel02.setText("");
		detaillabel03.setText("");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(String.valueOf(s.getCompressor().getOnPressure()));
		detailslabel12.setText("");
		detailslabel13.setText("");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailDelivery() {
		detaillabel01.setText("Kunden beliefert:");
		detaillabel02.setText("Lieferbandgeschwindigkeit:");
		detaillabel03.setText("Kunde1:");
		detaillabel04.setText("Kunde2:");
		detaillabel05.setText("Kunde3:");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getDelivery().getGatesUsed() + "");
		detailslabel12.setText(s.getDelivery().getLineToEndSpeed() + "");
		detailslabel13.setText(s.getDelivery().getGateCCounter()+"");
		detailslabel14.setText(s.getDelivery().getGateDCounter()+"");
		detailslabel15.setText(s.getDelivery().getGateECounter()+"");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailFillStation() {
		detaillabel01.setText("Drehgeschwindigkeit:");
		detaillabel02.setText("Drehungen:");
		detaillabel03.setText("geladene B�lle:");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getFillStation().getWheelspeed() + "");
		detailslabel12.setText(s.getFillStation().getNumberOfTurns() + "");
		detailslabel13.setText(s.getFillStation().getNumberOfDeliveredBalls() + "");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailLift() {
		detaillabel01.setText("Liftgeschwindigkeit:");
		detaillabel02.setText("");
		detaillabel03.setText("");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getLift().getliftSpeed() + "");
		detailslabel12.setText("");
		detailslabel13.setText("");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailQuality() {
		detaillabel01.setText("IO Farbe:");
		detaillabel02.setText("Farbe:");
		detaillabel03.setText("Bandgeschwindigkeit:");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(s.getQuality().getIoColor());
		detailslabel12.setText(s.getQuality().getColorString());
		detailslabel13.setText(s.getQuality().getCounterLineSpeed() + "");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailQualityStation() {
		detaillabel01.setText("IO Farbe:");
		detaillabel02.setText("Farbe:");
		detaillabel03.setText("Bandgeschwindigkeit:");
		detaillabel04.setText("");
		detaillabel05.setText("");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");// change to quality station
		detailslabel11.setText(s.getQuality().getIoColor());
		detailslabel12.setText(s.getQuality().getColorString());
		detailslabel13.setText(s.getQuality().getCounterLineSpeed() + "");
		detailslabel14.setText("");
		detailslabel15.setText("");
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	public void setDetailStock() {
		detaillabel01.setText("Lager1:");
		detaillabel02.setText("Lager2:");
		detaillabel03.setText("Lager3:");
		detaillabel04.setText("Lager4:");
		detaillabel05.setText("Fahrstuhlposition:");
		detaillabel06.setText("");
		detaillabel07.setText("");
		detaillabel08.setText("");
		detailslabel10.setText(s.getSzenario() + "");
		detailslabel11.setText(String.valueOf(s.getStock().isStock1()));
		detailslabel12.setText(String.valueOf(s.getStock().isStock2()));
		detailslabel13.setText(String.valueOf(s.getStock().isStock3()));
		detailslabel14.setText(String.valueOf(s.getStock().isStock4()));
		detailslabel15.setText(s.getStock().getElevatorpositionAsString());
		detailslabel16.setText("");
		detailslabel17.setText("");
		detailslabel18.setText("");
	}

	// -----------------------------------------------------/Details---------------------------------
	// -------------------------------------------------Steuerung-------------------------------------------

	public void updateLabels() { // should be called by clock later on

		updateTime();
		defaultlabelbetriebszeit.setText("");
		if (s.getQuality() != null) {
			defaultlabelware.setText(s.getQuality().getCountedBalls() + "");
			defaultlabelio.setText(s.getQuality().getGoodBalls() + "");
			defaultlabelnio.setText(s.getQuality().getBadBalls() + "");
		}
		defaultlabeldurchsatz.setText("");
		if (s.getDelivery() != null) {
			defaultlabelpuffer.setText(20 - s.getDelivery().getGatesUsed() + "");
			defaultlabelversand.setText(s.getDelivery().getGatesUsed() + "");
		}

	}

	private void addBrickLabeltoList() { // care add order has to be name into
											// Amper

		brickLabels.add(label00);
		brickLabels.add(label10);
		brickLabels.add(label01);
		brickLabels.add(label11);
		brickLabels.add(label02);
		brickLabels.add(label12);
		brickLabels.add(label03);
		brickLabels.add(label13);
		brickLabels.add(label04);
		brickLabels.add(label14);
		brickLabels.add(label05);
		brickLabels.add(label15);
		brickLabels.add(label06);
		brickLabels.add(label16);
		brickLabels.add(label20);
		brickLabels.add(label30);
		brickLabels.add(label21);
		brickLabels.add(label31);
		brickLabels.add(label22);
		brickLabels.add(label32);
		brickLabels.add(label23);
		brickLabels.add(label33);
		brickLabels.add(label24);
		brickLabels.add(label34);
		brickLabels.add(label25);
		brickLabels.add(label35);
		brickLabels.add(label26);
		brickLabels.add(label36);
	}

	public void updatePowerLevel() {

		int brickCounter = 0;
		float powerLevel;
		String brickName;

		// nicht Genug felder f�r Namen, schmeisst out of bouce index aus wenn zu viele
		// namen
		for (lejos.remote.ev3.RemoteEV3 b : s.getBrickList()) {

			powerLevel = b.getPower().getVoltageMilliVolt();
			brickName = b.getName();
			// TODO update in Ui

			Label l = brickLabels.get(brickCounter);

			l.setText("B" + brickName);
			brickCounter++;
			l = brickLabels.get(brickCounter);
			l.setText(powerLevel + "");
			brickCounter++;
		}
	}

	// -------------------------------------------------/Steuerung-------------------------------------------

	public void startButtonClicked() {
		if (running) { // if game runs allready do nothing

		} else { // start game if it is stoped
			System.out.println("startButtonClicked clicked");
			showBall.setFill(javafx.scene.paint.Color.GREEN);
			readyLabel.setText("Running ");
			// startTimer();
			running = true;
			paused = false;
		}

	}

	public void stopButtonClicked() {
		System.out.println("stopButtonClicked clicked");
		showBall.setFill(javafx.scene.paint.Color.LIGHTBLUE);
		readyLabel.setText("Stopped ");
		// stopTimer();
		running = false;
		paused = true;
		pauseButton.setText("Pause");

	}

	public void pauseButtonClicked() {

		System.out.println("pauseButtonClicked clicked");

		if (running) { // just can pause game if its allready runs
			if (paused) { // if game is paused right now and button get clicked
							// again
				readyLabel.setText("Running");
				// startTimer();
				showBall.setFill(javafx.scene.paint.Color.GREEN);
				pauseButton.setText("Pause");
				paused = false;

			} else {

				showBall.setFill(javafx.scene.paint.Color.YELLOW);
				readyLabel.setText("Paused ");
				// stopTimer();
				pauseButton.setText("Resume");
				paused = true;
			}

		} else { // pushed break without running game

		}

	}

	public void emptyButtonClicked() {
		System.out.println("emptyButtonClicked clicked");
		if(connected) {
			s.disconnectBricks();
			connected = false;			
		}else {
		}

	}

	public void connectButtonClicked() {
		System.out.println("connectClicked clicked");
		if(connected) {
			
		}else {
			s.connectBricks();
			connected =true;
		}

	}

	public void szenarioButton1Clicked() {
		System.out.println("szenarioButton1Clicked clicked");
		s.startSzenario1();

	}

	public void szenarioButton2Clicked() {
		System.out.println("szenarioButton2Clicked clicked");
		s.startSzenario2();
	}

	public void szenarioButton3Clicked() {
		System.out.println("szenarioButton3Clicked clicked");
		s.startSzenario3();
	}

	public void error() {

		showBall.setFill(javafx.scene.paint.Color.RED);
		readyLabel.setText("ERROR !!");

	}

	public void error(String msg) { // error with Message

		showBall.setFill(javafx.scene.paint.Color.RED);
		readyLabel.setText("ERROR !!"); // TODO: change ERROR!! to error code
		System.out.println(msg);

	}

	// ----------------------------------------------TEST&STOP------------------------------------------------------------------
	public void chargierTest() {

		s.runChargier(true);
	}

	public void chargierStop() {

		s.runChargier(false);
	}

	public void cleanerTest() {

		s.runCleaner(true);
	}

	public void cleanerStop() {

		s.runCleaner(false);
	}

	public void shakerTest() {

		s.runShaker(true);
	}

	public void shakerStop() {

		s.runShaker(false);
	}

	public void liftTest() {

		s.runLift(true);
	}

	public void liftStop() {

		s.runLift(false);
	}

	public void qualityTest() {

		s.runQuality(true);
	}

	public void qualityStop() {

		s.runQuality(false);
	}

	public void airarmsTest() {

		s.runAirarms(true);
	}

	public void airarmsStop() {

		s.runAirarms(false);
	}

	public void deliveryTest() {

		s.runDelivery(true);
	}

	public void deliveryStop() {

		s.runDelivery(false);
	}

	public void stockTest() {

		s.runStock(true);
	}

	public void stockStop() {

		s.runStock(false);
	}

	// ----------------------------------------------TEST&STOP------------------------------------------------------------------
	// ------------------------------------------------Timer-------------------------------------------------------------------

	private void startTimer() {
		startTime = System.currentTimeMillis();
		updateTime();
	}

	private void resetTimer() {
		startTime = System.currentTimeMillis();
		updateTime();
	}

	private void updateTime() {

		long time = System.currentTimeMillis() - startTime;
		leftBottomLabel.setText(timeFormat.format(time));
	}

	// ------------------------------------------------Timer-----------------------------------------------------------------------

	public Button getStartButton() {
		return startButton;
	}

	public void setStartButton(Button startButton) {
		this.startButton = startButton;
	}

	public Button getStopButton() {
		return stopButton;
	}

	public void setStopButton(Button stopButton) {
		this.stopButton = stopButton;
	}

	public Button getPauseButton() {
		return pauseButton;
	}

	public void setPauseButton(Button pauseButton) {
		this.pauseButton = pauseButton;
	}

	public Button getEmptyButton() {
		return emptyButton;
	}

	public void setEmptyButton(Button emptyButton) {
		this.emptyButton = emptyButton;
	}

//	public Button getAllButton() {
//		return allButton;
//	}
//
//	public void setAllButton(Button allButton) {
//		this.allButton = allButton;
//	}

	public Button getSzenarioButton1() {
		return szenarioButton1;
	}

	public void setSzenarioButton1(Button szenarioButton1) {
		this.szenarioButton1 = szenarioButton1;
	}

	public Button getSzenarioButton2() {
		return szenarioButton2;
	}

	public void setSzenarioButton2(Button szenarioButton2) {
		this.szenarioButton2 = szenarioButton2;
	}

	public Button getSzenarioButton3() {
		return szenarioButton3;
	}

	public void setSzenarioButton3(Button szenarioButton3) {
		this.szenarioButton3 = szenarioButton3;
	}

	public Label getTopLeftLabel() {
		return topLeftLabel;
	}

	public void setTopLeftLabel(Label topLeftLabel) {
		this.topLeftLabel = topLeftLabel;
	}

	public Label getReadyLabel() {
		return readyLabel;
	}

	public void setReadyLabel(Label readyLabel) {
		this.readyLabel = readyLabel;
	}

	public Label getLeftBottomLabel() {
		return leftBottomLabel;
	}

	public void setLeftBottomLabel(Label leftBottomLabel) {
		this.leftBottomLabel = leftBottomLabel;
	}

	public Label getRightBottomLabel() {
		return rightBottomLabel;
	}

	public void setRightBottomLabel(Label rightBottomLabel) {
		this.rightBottomLabel = rightBottomLabel;
	}

	public Circle getShowBall() {
		return showBall;
	}

	public void setShowBall(Circle showBall) {
		this.showBall = showBall;
	}

	public Label getDefaultlabelbetriebszeit() {
		return defaultlabelbetriebszeit;
	}

	public void setDefaultlabelbetriebszeit(Label defaultlabelbetriebszeit) {
		this.defaultlabelbetriebszeit = defaultlabelbetriebszeit;
	}

	public Label getDefaultlabelware() {
		return defaultlabelware;
	}

	public void setDefaultlabelware(Label defaultlabelware) {
		this.defaultlabelware = defaultlabelware;
	}

	public Label getDefaultlabelio() {
		return defaultlabelio;
	}

	public void setDefaultlabelio(Label defaultlabelio) {
		this.defaultlabelio = defaultlabelio;
	}

	public Label getDefaultlabelnio() {
		return defaultlabelnio;
	}

	public void setDefaultlabelnio(Label defaultlabelnio) {
		this.defaultlabelnio = defaultlabelnio;
	}

	public Label getDefaultlabeldurchsatz() {
		return defaultlabeldurchsatz;
	}

	public void setDefaultlabeldurchsatz(Label defaultlabeldurchsatz) {
		this.defaultlabeldurchsatz = defaultlabeldurchsatz;
	}

	public Label getDefaultlabelpuffer() {
		return defaultlabelpuffer;
	}

	public void setDefaultlabelpuffer(Label defaultlabelpuffer) {
		this.defaultlabelpuffer = defaultlabelpuffer;
	}

	public Label getDefaultlabelverbrauch() {
		return defaultlabelverbrauch;
	}

	public void setDefaultlabelverbrauch(Label defaultlabelverbrauch) {
		this.defaultlabelverbrauch = defaultlabelverbrauch;
	}

	public Label getDefaultlabelversand() {
		return defaultlabelversand;
	}

	public void setDefaultlabelversand(Label defaultlabelversand) {
		this.defaultlabelversand = defaultlabelversand;
	}

	public Label getLabel00() {
		return label00;
	}

	public void setLabel00(Label label00) {
		this.label00 = label00;
	}

	public Label getLabel01() {
		return label01;
	}

	public void setLabel01(Label label01) {
		this.label01 = label01;
	}

	public Label getLabel02() {
		return label02;
	}

	public void setLabel02(Label label02) {
		this.label02 = label02;
	}

	public Label getLabel03() {
		return label03;
	}

	public void setLabel03(Label label03) {
		this.label03 = label03;
	}

	public Label getLabel04() {
		return label04;
	}

	public void setLabel04(Label label04) {
		this.label04 = label04;
	}

	public Label getLabel10() {
		return label10;
	}

	public void setLabel10(Label label10) {
		this.label10 = label10;
	}

	public Label getLabel11() {
		return label11;
	}

	public void setLabel11(Label label11) {
		this.label11 = label11;
	}

	public Label getLabel12() {
		return label12;
	}

	public void setLabel12(Label label12) {
		this.label12 = label12;
	}

	public Label getLabel13() {
		return label13;
	}

	public void setLabel13(Label label13) {
		this.label13 = label13;
	}

	public Label getLabel14() {
		return label14;
	}

	public void setLabel14(Label label14) {
		this.label14 = label14;
	}

	public Label getLabel20() {
		return label20;
	}

	public void setLabel20(Label label20) {
		this.label20 = label20;
	}

	public Label getLabel21() {
		return label21;
	}

	public void setLabel21(Label label21) {
		this.label21 = label21;
	}

	public Label getLabel22() {
		return label22;
	}

	public void setLabel22(Label label22) {
		this.label22 = label22;
	}

	public Label getLabel23() {
		return label23;
	}

	public void setLabel23(Label label23) {
		this.label23 = label23;
	}

	public Label getLabel24() {
		return label24;
	}

	public void setLabel24(Label label24) {
		this.label24 = label24;
	}

	public Label getLabel30() {
		return label30;
	}

	public void setLabel30(Label label30) {
		this.label30 = label30;
	}

	public Label getLabel31() {
		return label31;
	}

	public void setLabel31(Label label31) {
		this.label31 = label31;
	}

	public Label getLabel32() {
		return label32;
	}

	public void setLabel32(Label label32) {
		this.label32 = label32;
	}

	public Label getLabel33() {
		return label33;
	}

	public void setLabel33(Label label33) {
		this.label33 = label33;
	}

	public Label getLabel34() {
		return label34;
	}

	public void setLabel34(Label label34) {
		this.label34 = label34;
	}

	public Circle getLed1() {
		return led1;
	}

	public void setLed1(Circle led1) {
		this.led1 = led1;
	}

	public Circle getLed2() {
		return led2;
	}

	public void setLed2(Circle led2) {
		this.led2 = led2;
	}

	public Circle getLed3() {
		return led3;
	}

	public void setLed3(Circle led3) {
		this.led3 = led3;
	}

	public Circle getLed4() {
		return led4;
	}

	public void setLed4(Circle led4) {
		this.led4 = led4;
	}

	public Circle getLed5() {
		return led5;
	}

	public void setLed5(Circle led5) {
		this.led5 = led5;
	}

	public Circle getLed6() {
		return led6;
	}

	public void setLed6(Circle led6) {
		this.led6 = led6;
	}

	public Circle getLed7() {
		return led7;
	}

	public void setLed7(Circle led7) {
		this.led7 = led7;
	}

	public Button getTest1() {
		return test1;
	}

	public void setTest1(Button test1) {
		this.test1 = test1;
	}

	public Button getTest2() {
		return test2;
	}

	public void setTest2(Button test2) {
		this.test2 = test2;
	}

	public Button getTest3() {
		return test3;
	}

	public void setTest3(Button test3) {
		this.test3 = test3;
	}

	public Button getTest4() {
		return test4;
	}

	public void setTest4(Button test4) {
		this.test4 = test4;
	}

	public Button getTest5() {
		return test5;
	}

	public void setTest5(Button test5) {
		this.test5 = test5;
	}

	public Button getTest6() {
		return test6;
	}

	public void setTest6(Button test6) {
		this.test6 = test6;
	}

	public Button getTest7() {
		return test7;
	}

	public void setTest7(Button test7) {
		this.test7 = test7;
	}

	public Button getDetails1() {
		return details1;
	}

	public void setDetails1(Button details1) {
		this.details1 = details1;
	}

	public Button getDetails2() {
		return details2;
	}

	public void setDetails2(Button details2) {
		this.details2 = details2;
	}

	public Button getDetails3() {
		return details3;
	}

	public void setDetails3(Button details3) {
		this.details3 = details3;
	}

	public Button getDetails4() {
		return details4;
	}

	public void setDetails4(Button details4) {
		this.details4 = details4;
	}

	public Button getDetails5() {
		return details5;
	}

	public void setDetails5(Button details5) {
		this.details5 = details5;
	}

	public Button getDetails6() {
		return details6;
	}

	public void setDetails6(Button details6) {
		this.details6 = details6;
	}

	public Button getDetails7() {
		return details7;
	}

	public void setDetails7(Button details7) {
		this.details7 = details7;
	}

	public Button getStop1() {
		return stop1;
	}

	public void setStop1(Button stop1) {
		this.stop1 = stop1;
	}

	public Button getStop2() {
		return stop2;
	}

	public void setStop2(Button stop2) {
		this.stop2 = stop2;
	}

	public Button getStop3() {
		return stop3;
	}

	public void setStop3(Button stop3) {
		this.stop3 = stop3;
	}

	public Button getStop4() {
		return stop4;
	}

	public void setStop4(Button stop4) {
		this.stop4 = stop4;
	}

	public Button getStop5() {
		return stop5;
	}

	public void setStop5(Button stop5) {
		this.stop5 = stop5;
	}

	public Button getStop6() {
		return stop6;
	}

	public void setStop6(Button stop6) {
		this.stop6 = stop6;
	}

	public Button getStop7() {
		return stop7;
	}

	public void setStop7(Button stop7) {
		this.stop7 = stop7;
	}

	public CheckBox getBox1() {
		return box1;
	}

	public void setBox1(CheckBox box1) {
		this.box1 = box1;
	}

	public CheckBox getBox2() {
		return box2;
	}

	public void setBox2(CheckBox box2) {
		this.box2 = box2;
	}

	public CheckBox getBox3() {
		return box3;
	}

	public void setBox3(CheckBox box3) {
		this.box3 = box3;
	}

	public CheckBox getBox4() {
		return box4;
	}

	public void setBox4(CheckBox box4) {
		this.box4 = box4;
	}

	public CheckBox getBox5() {
		return box5;
	}

	public void setBox5(CheckBox box5) {
		this.box5 = box5;
	}

	public CheckBox getBox6() {
		return box6;
	}

	public void setBox6(CheckBox box6) {
		this.box6 = box6;
	}

	public CheckBox getBox7() {
		return box7;
	}

	public void setBox7(CheckBox box7) {
		this.box7 = box7;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

	public Steuerung getS() {
		return s;
	}

	public void setS(Steuerung s) {
		this.s = s;
	}

	public ArrayList<Label> getBrickLabels() {
		return brickLabels;
	}

	public void setBrickLabels(ArrayList<Label> brickLabels) {
		this.brickLabels = brickLabels;
	}
}
