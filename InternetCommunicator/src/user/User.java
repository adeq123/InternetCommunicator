package user;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import contactList.ClWriter;
import contactList.ContactList;

public class User {
 
	private final long ID;
	private static long nextID = 1;
	private String nazwa;
	private File myUserDirectory;
	private String defaultUserPath;;
	private ContactList myList;
	
	public User(String nazwa) throws IOException{	
		this.nazwa = nazwa;
		this.ID = updateID();
		defaultUserPath = "C:\\Users\\Public\\Documents\\Communicator\\" + nazwa + "_" +Long.toString(ID);
		myUserDirectory = new File(defaultUserPath);
		createIfNotExists(myUserDirectory);
		myList = new ContactList(myUserDirectory);
		
	}
	
	/**
	 * creates the myUserDirectory directory if it doesn't exists
	 * @param myUserDirectory, File, which will be created which doesn't exists
	 * @throws IOException 
	 */
	private void createIfNotExists(File myUserDirectory) throws IOException {
		if(!myUserDirectory.exists())
			myUserDirectory.mkdirs();
		
	}
	
	/**
	 * 
	 * @return, long, next unique user ID
	 */
	public static long updateID() {
		nextID++;
		return nextID;
	}
	
}
