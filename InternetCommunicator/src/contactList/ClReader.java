package contactList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class ClReader implements Reader {

	private File contactList;
	private HashMap <Long, String> contactListMap;
	
	public ClReader(File contactList){
		this.contactList = contactList;
		contactListMap = new HashMap<Long, String>();
	}
	
	/**
	 * loads the contactList from the file to the HashMap
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 *
	 *	 */
	public void  loadContactList() throws NumberFormatException, IOException {
		contactListMap.clear();
		
		BufferedReader reader = new BufferedReader(new FileReader(contactList.getAbsolutePath()));
		String line = null;
		

		while((line = reader.readLine()) != null)
				contactListMap.put(Long.parseLong(line.split(" ")[1]), line.split(" ")[0]);
		reader.close();
	}
	
	public HashMap <Long, String> getContactList() throws NumberFormatException, IOException{
		loadContactList();
		return contactListMap;
	}
}
