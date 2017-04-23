package contactList;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ContactList {
	
	final File mainFolder;
	final File contactList;
	private ClWriter myWriter;
	private ClReader myReader;
	HashMap <Long, String> contactListMap;
	
	public ContactList(File myUserDirectory) throws IOException {

		this.mainFolder = myUserDirectory;
		this.contactList = new File(myUserDirectory.getAbsolutePath() + "\\ContactList.txt");
		contactList.createNewFile();
		myWriter = new ClWriter(this.contactList);
		myReader = new ClReader(this.contactList);
		
	}

	public ClWriter getClWriter(){
		return myWriter;
	}
	
	public ClReader getClReader(){
		return myReader;
	}
}
