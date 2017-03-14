package client;

import java.io.InputStreamReader;

/**
 * This class model a receiving2| of messages from other clients
 * @author RoguskiA
 *
 */
public class ReceiveDataModel implements Runnable{
	
	InputStreamReader in;
	
	public ReceiveDataModel(InputStreamReader in){
		this.in =in;
	}
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
