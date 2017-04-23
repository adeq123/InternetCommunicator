package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import Communication.Message;

/**
 * This class model sending data from the user;
 * @author RoguskiA
 *
 */
public class SendDataModel extends AbstractModel {

	private ObjectOutputStream out;
	private final long userNo;
	
	public SendDataModel(ObjectOutputStream out, long userNo){
		this.out = out;
		this.userNo = userNo;
	}
	public void sendMessage(String content, String toUser) throws IOException{
		System.out.println("Client is sending a new message");
		out.writeObject(new Message(content, toUser, Long.toString(userNo)));
		
	}
	
}
