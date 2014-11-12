package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Base {

	private static final Logger LOGGER = Logger.getLogger( Base.class.getName() );
	
	private final Lock lock = new ReentrantLock();
	private static int cubbyNum;
	
	public static void main(String[] args) throws IOException{
		
		LOGGER.info("Logging an INFO-level message");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		
		System.out.println("Initializing cubbyholes.");
		System.out.print("Will we be locking today? ");
		String command = reader.readLine();
		if (command.toLowerCase().equals("yes")){
			
		}
		else {
			System.out.println("WARNING: there may be collisions.");
		}
		System.out.print("How many cubbyholes would you like? ");
		command = reader.readLine();
		cubbyNum = Integer.parseInt(command);
		Cubbyhole[] cubbys = new Cubbyhole[cubbyNum];
		
		System.out.print("What number is in each cubbyhole to start? ");
		command = reader.readLine();
		int initVal = Integer.parseInt(command);
		
		for (int i = 0; i < cubbyNum; i++){
			// initalize all cubbys and numbers.
			cubbys[i] = new Cubbyhole();
			cubbys[i].number = initVal;	
		}
		
		boolean done = false;
		System.out.println("Waiting for transactions.");
		while (!done){
			
		}
	}
	
	public int getCubbys(){
		return cubbyNum;
	}

}
