package user;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import archive.Archive;
import contactList.ClWriter;
import contactList.ContactList;

public class User {
 
	private final long ID;
	private static long nextID = 1;
	private String name;
	private File myUserDirectory;
	private File archiveDirectory;
	private String defaultUserPath;;
	private ContactList myList;
	private Archive myArchive;
	
	public User(String name) throws IOException{	
		this.name = name;
		this.ID = updateID();
		defaultUserPath = "C:\\Users\\Public\\Documents\\Communicator\\" + name + "_" +Long.toString(ID);
		myUserDirectory = new File(defaultUserPath);
		createIfNotExists(myUserDirectory);
		myList = new ContactList(myUserDirectory);
		
		archiveDirectory = new File(defaultUserPath + "\\Archive");
		archiveDirectory.mkdir();
		myArchive = new Archive(archiveDirectory);
		
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
	private static long updateID() {
		nextID++;
		return nextID;
	}
	
	public String getName(){
		return name;
	}
	
	public Archive getArchive(){
		return myArchive;
	}
	
	public ContactList getContactList(){
		return myList;
	}
}
