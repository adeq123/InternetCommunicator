package client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class model a connection of the client with a server. Create and manage all the streams
 * @author RoguskiA
 *
 */
public class ConnectionModel {
 ///ta clasa poiwnna nawiazywac polaczenie tez.....
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ConnectionModel(Socket clientSocket){
		this.clientSocket = clientSocket;
		try {
			in = new ObjectInputStream(clientSocket.getInputStream());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	public ObjectInputStream getInputStream(){
		return in;
	}
		
	}
}
