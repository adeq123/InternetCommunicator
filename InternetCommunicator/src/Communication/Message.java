package Communication;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String content;
	private String toUser;
	private String fromUser;
	private Date date;
	
	public Message(String content, String toUser, String fromUser){
		this.content = content;
		this.date = new Date();
		this.toUser = toUser;
	}
	
	public String getToUser(){
		return toUser;
	}
	
	public String getFromUser(){
		return fromUser;
	}
	
	public String getContent(){
		return content;
	}
	
	public Date getDate(){
		return date;
	}
}
