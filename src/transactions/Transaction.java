package transactions;

import base.Base;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Transaction {

	PrintWriter outToServer;
	
	public static int cubbyNum;
	public int cubby1;
	public int cubby2;
	public int numChange;
	
	public Transaction(int ch) throws UnknownHostException, IOException{
		
		// TCP connection? do we want to do this in main?
		Socket clientSocket = new Socket("localhost", 6789);
		outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
	    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
	    // getting number of cubbys!
		System.out.println("Asking server about cubbys...");
		outToServer.println("query");
		String received = inFromServer.readLine();
		System.out.println("FROM SERVER: " + received);
		cubbyNum = Integer.parseInt(received);
		  
		numChange = ch; // this is the amount that the transaction will inc/dec
		Random ran = new Random();
		// nextInt returns a value between 0 (inclusive) and cubbyNum (exclusive)
		cubby1 = ran.nextInt(cubbyNum);
		cubby2 = ran.nextInt(cubbyNum);
		increment(cubby1, numChange);
		decrement(cubby2, numChange);
		
		clientSocket.close();
	}
	
	public synchronized void increment(int cubbyNum, int amount) throws IOException{
		outToServer.println("increment," + cubbyNum + "," + amount);
	}
	
	public synchronized void decrement(int cubbyNum, int amount){
		outToServer.println("decrement," + cubbyNum + "," + amount);
		// TODO make sure the subtraction won't take the cubby below 0!
	}
	
}
