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
	ExecutorService executor;
	
	public Socket socket;
	public Socket outputSocket;

	private String ip;
	private int port;
	
	
	private static String targetIP = "192.168.0.114";
	private static int targetPort = 33333;
	
	public DigitalTwin (String targetIP, int targetPort){
		
		if (targetIP == null){
			ip = DigitalTwin.targetIP;
			port = DigitalTwin.targetPort;
		} else {
			ip = targetIP;
			port = targetPort;		
		}	
	}
	
	
	public boolean init(){
		boolean result = true;
		executor = Executors.newCachedThreadPool();
		
		try {
			socket = new java.net.Socket(targetIP, targetPort);
			//outputSocket = new java.net.Socket();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public static void main(String[] args) {
		DigitalTwin twin = new DigitalTwin("80.69.196.188", 333);
		
		if (twin.init()){
			System.out.println("Socket geöffnet");
			
			Runnable socketListener = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						String antwort = twin.leseNachricht();
						System.out.println(antwort);
					} catch (SocketTimeoutException e) {
						System.out.println("Socket Timeout");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			twin.executor.execute(socketListener);
			
			Runnable out = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
				
			};
			
			
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			twin.shutdown();
		}
			

		
			
		try {
			twin.schreibeNachricht("ST");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		}


	protected void schreibeNachricht(String nachricht) throws IOException {

		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	
	}
	
	
	
	protected String leseNachricht() throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		char[] buffer = new char[200];

		socket.setSoTimeout(20000);
		
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); //blockiert bis Nachricht empfangen hat 


		String nachricht = new String(buffer, 0, anzahlZeichen);

		return nachricht;
		
		
		
	}

	public void shutdown(){
		closeSocket();
		executor.shutdown();
	}
	
	public void closeSocket() {
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

