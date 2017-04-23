package Communication;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db.DB;


/**
 * This class model a simple multithread server which run an internet communicator. The server implements thread pool model
 * @author RoguskiA
 *Protocol:
 *"@register" - register a new user on server with number 1 in this case
 *"@exit" - deregister user from the server
 *
 */
public class Server implements Runnable{
	
	private int maxConnections;
	private ServerSocket pooledServer;
	private ExecutorService executor;
	private Socket newClientSocket = null;
	//server protocol commands
	public final static String REGISTER = "@register";
	public final static String EXIT = "@exit";
	public final static String SEARCH_BY_NO ="@searchByNo";
	public final static String NO_USER_FOUND ="@noUserFound";
	public final static String USER_FOUND ="@userFound";
	public static final String FROM_SERVER = "@fromServer";
	
	public Server(int port, int maxConnections){
		executor = Executors.newFixedThreadPool(maxConnections);
		try {
			pooledServer = new ServerSocket(port);
			//clientComponentHandler();
			System.out.println("Server started on port number: " + port + " and host "  +
	                 InetAddress.getLocalHost().getHostName());
			
		} catch (IOException e) {
            System.out.println("Acceptance failed on port: " + port);
            if (pooledServer != null && !pooledServer.isClosed()) {
		        try {
		        	pooledServer.close();
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
		
		try {
			System.out.println("Waiting for connection with someone...");
			newClientSocket = pooledServer.accept();
			System.out.println("New user connected!");
			
			if(newClientSocket == null){
				System.out.println("Server acceptanse timeout");
				continue;
			}else{
				executor.execute(new ClientComponent(newClientSocket));
			}
		} catch (IOException e) {
			
		} 
	
	}
	
}

/**
 * Creates threads which afterwards will take care of tasks from the pool. Used in the thread pool model.
 */
	public static void main(String args[]){
		
		Server server = new Server(7778, 5);
		Thread serverT = new Thread(server, "Server thread");
		serverT.start();
	
	}

}
