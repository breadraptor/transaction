package transactions;

import base.Base;
import java.util.Random;

public class Transaction {

	public static int cubbyNum;
	public int cubby1;
	public int cubby2;
	public int numChange = 3;
	
	public Transaction(){
		// TODO set cubbyNum
		Random ran = new Random();
		// nextInt returns a value between 0 (inclusive) and cubbyNum (exclusive)
		cubby1 = ran.nextInt(cubbyNum);
		cubby2 = ran.nextInt(cubbyNum);
		increment();
		decrement();
	}
	
	public synchronized void increment(){
		
		
	}
	
	public synchronized void decrement(){
		
	}
	
}
