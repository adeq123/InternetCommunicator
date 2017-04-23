package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import Communication.Message;

/**
 * This class model a receiving2| of messages from other clients
 * @author RoguskiA
 *
 */
public class ReceiveDataModel extends AbstractModel implements Runnable{
	
	public static String MSG_RECEIVED = "New msg";
	private ObjectInputStream in;
	private long UserNumber;
	private Message receivedMessage ;
	private boolean clientIsLogged = true;
	public ReceiveDataModel(ObjectInputStream in, long UserNumber){
		this.in =in;
		this.UserNumber = UserNumber;
	}
	
	public void run() {
		try {
			while(clientIsLogged ){
			receivedMessage = null;
			receivedMessage = (Message) in.readObject();
			if(receivedMessage != null){
				System.out.println("User " + UserNumber + "\nNew message received from user: " + receivedMessage.getFromUser()
				+ System.lineSeparator() + "\nContent: "+ receivedMessage.getContent() );
				this.firePropertyChange(MSG_RECEIVED, null, receivedMessage);  // Notify controller about new messeage
			}
			
		}
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}catch(EOFException eOF){
			System.out.println("EOF!!!");
			
		} catch (SocketException e) {
			/*
			 * Do nothing. This exception is thrown when we are killing this thread wile being stucked on in.readObject()
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String setReadText(String readText){
		return readText;
	}
	public void exitThread(){
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientIsLogged = false;
	}
}
