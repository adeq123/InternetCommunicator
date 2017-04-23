package Communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import db.DB;

public class ClientComponent implements Runnable{
	
	private Socket connectionHandled;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Message newMessage;
	private int userNumber;
	boolean isLogged;
	private HashMap<Integer, String> messeagesToBeSentMap ;
	private DB database = new DB();
	private String userName;
	private static final HashMap<Integer, ClientComponent> clientsMap = new HashMap<Integer, ClientComponent>(); 
	
	public ClientComponent(Socket socket){
		try {
			connectionHandled = socket;
			createStreams();
			this.isLogged = true;
			messeagesToBeSentMap = new HashMap<Integer, String>();
			database.connect();
			database.createTable();
			//clientsMap = new HashMap<Integer, ClientComponent>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void run(){		
		
		while(isLogged){		
		
				try {
				
					newMessage = (Message) in.readObject();
					System.out.println("New message read!: " + newMessage.getContent() +" from user: " + newMessage.getFromUser());
					this.userNumber = Integer.parseInt(newMessage.getFromUser());
					
					if(newMessage.getContent().contains(Server.REGISTER)){
						userName = newMessage.getContent().split(" ")[1];
						registerUser(this, userNumber);
						checkForMesseagesToUser();
					}else if (newMessage.getContent().equals(Server.EXIT)){
						System.out.println("User: "+userNumber+"is leaving... :(");
						deregisterUser();
						closeClientStreams();	
						isLogged = false;
						
					}else if (newMessage.getContent().contains(Server.SEARCH_BY_NO)){
						System.out.println("Looking for a user...");
						String [] foundUser = database.findUser(newMessage.getContent().split(" ")[1]);
						
						if (foundUser ==null){
							sendMessage(new Message(Server.NO_USER_FOUND,newMessage.getFromUser(), Server.FROM_SERVER));
						}else{
							sendMessage(new Message(Server.USER_FOUND+" "+foundUser[1],newMessage.getFromUser(), Server.FROM_SERVER));
						}
						
					}else{
						sendMessage(newMessage);
					}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(EOFException eOF){
				System.out.println("Server EOF!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates streams from socket int this class
	 * @throws IOException 
	 */
	private void createStreams() throws IOException{
		
		out = new ObjectOutputStream(connectionHandled.getOutputStream());
		in = new ObjectInputStream(connectionHandled.getInputStream());
		System.out.println("Stremas for client " +userNumber+" created");
	}
	
	
	
	/**
	 * Registers new user to the list
	 * @param thisClientComponent
	 * @param userNumber
	 */
	public void registerUser(ClientComponent thisClientComponent, int userNumber){
		synchronized(clientsMap){
			clientsMap.put(userNumber, thisClientComponent);
			System.out.println("New user registered: " + userNumber);
			database.addUser(Integer.toString(userNumber), userName);
			
		}	
	}
	
	/**
	 * Deletes user from the registered users list
	 */
	
	public synchronized void deregisterUser(){
		clientsMap.remove(userNumber);
	}

	/**
  * Sends a message to specific user if connected. Otherwise adds it to list of messages to be sent
  * @param messageToBeSent
  * @throws IOException
  */
	public void sendMessage(Message messageToBeSent) throws IOException{

		int recipient = Integer.parseInt(messageToBeSent.getToUser());
		ClientComponent recipientCC = null;
		String OldMesseage = null;
		System.out.println("Sending method has started.... recipient: " + recipient);
		if(clientsMap.containsKey(recipient)){
			
			recipientCC = clientsMap.get(recipient);
			recipientCC.getOutputStream().writeObject(messageToBeSent);
			recipientCC.getOutputStream().flush();
			
		}else{
			System.out.println("Recipient is not connected. Messeage will be sent later");
			if(messeagesToBeSentMap.containsKey(recipient)){
				OldMesseage = messeagesToBeSentMap.get(recipient);
				messeagesToBeSentMap.remove(recipient);
				messeagesToBeSentMap.put(recipient, OldMesseage + messageToBeSent.getContent());
			}else
				messeagesToBeSentMap.put(recipient, messageToBeSent.getContent());
		}
	}
	
	public void checkForMesseagesToUser() throws IOException{
		if(messeagesToBeSentMap.containsKey(userNumber)){
			ClientComponent recipientCC = null;
			recipientCC = clientsMap.get(userNumber);
			recipientCC.getOutputStream().writeObject(messeagesToBeSentMap.get(userNumber));
			recipientCC.getOutputStream().flush();
			System.out.println("Messeage sent to user:" + userNumber +" Content: "+messeagesToBeSentMap.get(userNumber));
		}else	
			System.out.println("No messeages to be sent to the user");
	}
	
	/**
	 * Closes the connection with a user in humanitarian way
	 * @throws IOException 
	 */
	public void closeClientStreams(){
		try {
			in.close();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
			
	public ObjectInputStream getInputStream(){
		return in;
	}
 
	public ObjectOutputStream getOutputStream(){
		return out;
	}
	
	public int getUserNumber(){
		return userNumber;
	}
	
	
}