package communication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveInFile {
	static String path = "resources\\";

	

	public static void deleteFile(String fileName) {
		
		if(new File(path+fileName).isFile()) {
			new File(path+fileName).delete();
		}else {
			System.out.println("File does not exists");
		}
	}
	
	public static void saveInFile(String fileName, String s) {
		try {

			File recivedMessagesFile = new File(path+fileName); // creates File if its not already there
			System.out.println(recivedMessagesFile);
			System.getProperty("user.dir");
			
			Path currentRelativePath = Paths.get("");
			String s1 = currentRelativePath.toAbsolutePath().toString();
			System.out.println("Current absolute path is: " + s1);

			File file = new File(s1 + "\\" + path);
			System.out.println(file);
			if (file.isDirectory()){
				System.out.println("exists");
			}
			
			try {
				recivedMessagesFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (recivedMessagesFile.isFile()) {
				
				PrintWriter out = new PrintWriter(recivedMessagesFile);
				out.print(s1);
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveInFile(String fileName, ArrayList<String> strings) {
		try {

			File recivedMessagesFile = new File(path+fileName); // creates File if its not already there
			try {
				recivedMessagesFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (recivedMessagesFile.isFile()) {
				
				PrintWriter out = new PrintWriter(recivedMessagesFile);
				for(String s : strings){
					out.println(s);
				}
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}