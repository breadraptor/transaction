package transactions;

import base.Base;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Transaction {

	DataOutputStream outToServer;
	
	public static int cubbyNum;
	public int cubby1;
	public int cubby2;
	public int numChange;
	
	public Transaction(int ch) throws UnknownHostException, IOException{
		
		// temporary TCP connection? do we want to do this in main?
		Socket clientSocket = new Socket("localhost", 6789);
	    outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  
		System.out.println("writing to server...");
		  outToServer.writeBytes("hey");
		  String received = inFromServer.readLine();
		  System.out.println("FROM SERVER: " + received);
		  
		
		// TODO set cubbyNum, the amount of cubbys that Base created.
		// send this over TCP?
		numChange = ch; // this is the amount that the transaction will inc/dec
		Random ran = new Random();
		// nextInt returns a value between 0 (inclusive) and cubbyNum (exclusive)
		cubby1 = ran.nextInt(cubbyNum);
		cubby2 = ran.nextInt(cubbyNum);
		increment();
		decrement();
		
		clientSocket.close();
	}
	
	public synchronized void increment() throws IOException{
		outToServer.writeBytes("increment");
		
	}
	
	public synchronized void decrement(){
		// TODO make sure the subtraction won't take the cubby below 0!
		
	}
	
}
