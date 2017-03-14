package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;

public class Server implements Runnable{

	ServerSocket echoServer;
	HashMap<Integer, Server.ClientComponent> clientsMap;
	HashMap<Integer, String> messeagesToBeSentMap;
	
	public Server(int port){
		
		clientsMap = new HashMap<Integer, Server.ClientComponent>();
		messeagesToBeSentMap = new HashMap<Integer, String>();
		
		
		try {
			echoServer = new ServerSocket(port);
			System.out.println("Server started on port number: " + port + " and host "  +
	                 InetAddress.getLocalHost().getHostName());
			
		} catch (IOException e) {
            System.out.println("Acceptance failed on port: " + port);
            if (echoServer != null && !echoServer.isClosed()) {
		        try {
		            echoServer.close();
		        } catch (IOException e1)
		        {
		            e1.printStackTrace(System.err);
		        }
		    }
            System.exit(-1);
		}
	}

 
@Override
public void run() {
	while(true){
		Socket newClientSocket = null;
		
		try {
			System.out.println("Waiting for connection with someone...");
			newClientSocket = echoServer.accept();
			System.out.println("New user connected!");
			
			if(newClientSocket == null){
				System.out.println("Server acceptanse timeout");
				continue;
			}
	
			ObjectOutputStream out = new ObjectOutputStream(newClientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(newClientSocket.getInputStream());
			
		
			ClientComponent newClientComponent = new ClientComponent(in, out);
			Thread t1 = new Thread(newClientComponent);
			t1.start();
			
		} catch (IOException e) {
			
	} //catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		//}
	
	}
	
}

public class ClientComponent implements Runnable{

	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Message newMessage;
	private int UserNumber;
	
	public ClientComponent(ObjectInputStream in, ObjectOutputStream out){
		this.in = in;
		this.out = out;
	}
	
	public void run(){
		while(true){
			try {
				newMessage = (Message) in.readObject();
				System.out.println("New message read!" + newMessage.getContent());
				registerUser(this, Integer.parseInt(newMessage.getFromUser()));
				sendMessage(newMessage);
			
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				closeClientStreams();
			}
		}
	}
	
	/**
	 * Registers new user to the list
	 * @param thisClientComponent
	 * @param userNumber
	 */
	public void registerUser(ClientComponent thisClientComponent, int userNumber){
		clientsMap.put(userNumber, thisClientComponent);
		System.out.println("New user registered: " + userNumber);
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
		System.out.println("Sending method has started.... recipient:" + recipient);
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
	
}


	public static void main(String args[]){
		
		Server server = new Server(7779);
		Thread serverT = new Thread(server);
		serverT.start();
	
	}

}
