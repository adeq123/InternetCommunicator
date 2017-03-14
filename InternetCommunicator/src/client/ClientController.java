package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import Communication.Message;

public class ClientController implements Runnable{

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int clientNumber;
	
ClientController(int port, String host, int clientNumber){
		
		try {
			this.clientNumber = clientNumber;
			this.socket = new Socket(host, port);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
            System.out.println("Klient startuje na hoscie " +
                    InetAddress.getLocalHost().getHostName());
            
		} catch (UnknownHostException e) {
			System.out.println("Wyjatek klienta 1 " + e);
		} catch (IOException e) {
			System.out.println("Wyjatek klienta 1 " + e);
		}
		
	}
	
	public void sendMessage(String content, String toUser) throws IOException{
		System.out.println("Client is sending a new message");
		out.writeObject(new Message(content, toUser, Integer.toString(clientNumber)));
		
	}
	
	public Message receiveMessage() throws ClassNotFoundException, IOException{
		Message receivedMessage = (Message) in.readObject();
		return receivedMessage;
	}
	
	public void run() {
		
	}
	
	
	public static void main(String[] args) throws UnknownHostException {
		
		String s = InetAddress.getLocalHost().getHostName();
		ClientController user1 = new ClientController(7779, s, 1);
		Thread cl1 = new Thread(user1);
		cl1.start();
		
		clientModel user2 = new clientModel(7778, s, 2);
		Thread cl2 = new Thread(user2);
		cl2.start();
		
		
	}



}
