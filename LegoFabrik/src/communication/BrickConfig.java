package communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import controller.Steuerung;

public class BrickConfig { // TODO: make a Brickconfig object in steuerung
	static String path = "..\\..\\resources\\";
	private Steuerung s;

	/*
	 * liest Brick Ips bei erstellung ein, falls datei nicht existiert erzeugt
	 * sie die gespeicherten standart Werte
	 */

	public BrickConfig(Steuerung s) {
		this.s = s;

//		try {
//			if (new File(path+"Brickconfig.txt").exists()) {
//				readIps();
//				System.out.println("Ip von datei gelesen");
//			} 
//			if (new File(path+"BrickDefaultconfig.txt").exists()) {
//				readDefaultIps();
//				} else {
//					System.out.println("keine der Dateien existiert,lege default an"); // 
//					SaveInFile.saveInFile("Test.txt", "Hallo Welt");
//				}
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		for (int x = 101; x < 114; x++) {
			brickips.add("192.168.0." + x);
			defaultBrickips.add("192.168.0." + x);
		}

	}

	private ArrayList<String> brickips = new ArrayList<>();
	private ArrayList<String> defaultBrickips = new ArrayList<>();

	public void readIps() throws FileNotFoundException, IOException { 
		File f = new File(path+"Brickconfig.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			while ((line = br.readLine()) != null) {
//				brickips.add(line);
			}
		}
	}

	public void writeIps() {
		return;
//			SaveInFile.deleteFile("Brickconfig.txt");
//			SaveInFile.saveInFile("Brickconfig.txt", brickips);

	}

	public void writeDefaultIps() { // change default Ips in config, not be used
		return;
//		SaveInFile.deleteFile("BrickDefaultconfig.txt");
//		SaveInFile.saveInFile("BrickDefaultconfig.txt", defaultBrickips);

	}

	public void readDefaultIps() {
//		File f = new File(path+"BrickDefaultconfig.txt");
//
//		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//			String line;
//			while ((line = br.readLine()) != null) {
//
//				defaultBrickips.add(line);
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public ArrayList<String> getDefaultBrickips() {
		return defaultBrickips;
	}

	public ArrayList<String> getBrickips() {
		return brickips;
	}

	public void setBrickips(ArrayList<String> brickips) {
		this.brickips = brickips;
	}

}