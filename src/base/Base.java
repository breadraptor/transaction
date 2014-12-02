package base;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Base {

	private static final Logger LOGGER = Logger.getLogger( Base.class.getName() );

	//private final Lock lock = new ReentrantLock();
	private static int cubbyNum;

	public static void main(String[] args) throws IOException{


		LOGGER.info("Logging an INFO-level message");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		ServerSocket welcomeSocket = new ServerSocket(6789);//set up a server socket to accept incoming connections
		System.out.println("Initializing cubbyholes.");
		System.out.print("Will we be locking today? ");
		String command = reader.readLine();
		int tNum = 0; //number of transactions that we have handled so far
		if (command.toLowerCase().equals("yes")){
			//intentionally left blank
		}
		else {
			System.out.println("WARNING: there may be collisions.");
		}
		System.out.print("How many cubbyholes would you like? ");
		command = reader.readLine();
		if (Integer.parseInt(command) < 1){
			System.out.println("You must have at least one cubbyhole.");
			System.exit(0);				//exit the program upon receiving invalid input
		}
		cubbyNum = Integer.parseInt(command);
		Cubbyhole[] cubbys = new Cubbyhole[cubbyNum];		//allocate array of cubbyholes

		System.out.print("What number is in each cubbyhole to start? ");
		command = reader.readLine();
		int initVal = Integer.parseInt(command);			//initial value to be placed in cubbyholes

		for (int i = 0; i < cubbyNum; i++){
			// initalize all cubbys and numbers.
			cubbys[i] = new Cubbyhole();
			cubbys[i].number = initVal;	
		}

		boolean done = false;
		System.out.println("Waiting for transactions.");
		Socket connectionSocket;
		BufferedReader inFromClient;
		PrintWriter outToClient;
		String received;
		String cmd;
		int cubby;
		int change;
		int newVal;
		while (!done){		//continually loop through and accept connections/transactions
			connectionSocket = welcomeSocket.accept();	//accept an incoming TCP connection
			System.out.println("Transaction has connected.");
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));	//open input stream from client
			outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);						//open output stream to client
			for(int i = 0; i < 3; i++){
				received = inFromClient.readLine();					//read client's message
				String[] message = received.split(",");				//split the comma delimited string into an array of values
				cmd = message[0];
				System.out.println("Received: " + received);
				if (cmd.equals("query")){							//if the transaction is asking about the number of cubbyholes...
					outToClient.println(cubbyNum);					//send them the number of cubbyholes
				}else if(cmd.equals("increment")){					//if the transaction is trying to increment
					try{
						cubby = Integer.parseInt(message[1]);		//get the number of the cubby they want to inc
						change = Integer.parseInt(message[2]);		//get the number they want to increment by
						newVal = cubbys[cubby].getNumber() + change;//get the new value (READ OPERATION)
						cubbys[cubby].setNumber(newVal);			//set the new value (WRITE OPERATION)
					}catch(NullPointerException e){
						break;
					}
				}else{
					try{
						cubby = Integer.parseInt(message[1]);
						change = Integer.parseInt(message[2]);
						newVal = cubbys[cubby].getNumber() - change;
						cubbys[cubby].setNumber(newVal);
					}catch(NullPointerException e){
						break;
					}
				}

			}
			
			tNum++;
			if(tNum%5 == 0){
				for(int j = 0; j<cubbyNum;j++){
					System.out.println(cubbys[j].getNumber());
				}
			}
			// outToClient.println("Cubbyhole edited.");

		}
	}

	public int getCubbys(){
		return cubbyNum;
	}

}
