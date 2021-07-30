package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class DigitalTwin {
	static ExecutorService executor;
	public static Socket socket;
	private static String targetIP = "192.168.0.114";
	private static int targetPort = 33333;

	public static void main(String[] args) {
		executor = Executors.newCachedThreadPool();
		
		try {
			socket = new java.net.Socket(targetIP, targetPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (socket != null) {
			System.out.println("Socket geöffnet");
			
			Runnable socketListener = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						String antwort = leseNachricht();
						System.out.println(antwort);
					} catch (SocketTimeoutException e) {
						System.out.println("Socket Timeout");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			
			executor.execute(socketListener);
			
			
		try {
			schreibeNachricht("ST");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
		}
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shutdown();
	}

	protected static void schreibeNachricht(String nachricht) throws IOException {

		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	
	}
	
	
	
	protected static String leseNachricht() throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		char[] buffer = new char[200];

		socket.setSoTimeout(20000);
		
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); //blockiert bis Nachricht empfangen hat 


		String nachricht = new String(buffer, 0, anzahlZeichen);

		return nachricht;
		
		
		
	}

	public static void shutdown(){
		closeSocket();
		executor.shutdown();
	}
	
	public static void closeSocket() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}

