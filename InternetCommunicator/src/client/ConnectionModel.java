package client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class model a connection of the client with a server. It connects to the server and create and manage all the streams
 * @author RoguskiA
 *
 */
public class ConnectionModel {

	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ConnectionModel(String host, int port){
		try {
			this.clientSocket = new Socket(host, port);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			
		} catch (IOException e) {
			System.out.println("Wyjatek klienta 1 " + e);
			e.printStackTrace();
			try {
				closeConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public ObjectInputStream getInputStream(){
		return in;
	}
	
	public ObjectOutputStream getOutputStream(){
		return out;
	}
	
	public void closeConnection() throws IOException{
		in.close(); //close reader always before the writer
		out.close();
		clientSocket.close();
	}
}
