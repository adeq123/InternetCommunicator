package contactList;

import java.io.File;
import java.io.IOException;

public class ContactList {
	
	final File mainFolder;
	final File contactList;
	private ClWriter myWriter;
	private ClReader myReader;
	
	public ContactList(File myUserDirectory) throws IOException {

		this.mainFolder = myUserDirectory;
		this.contactList = new File(myUserDirectory.getAbsolutePath() + "ContactList.txt");
		contactList.createNewFile();
		
	}

	
}
