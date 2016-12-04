package contactList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

public class ClWriter implements Writer{

	private File contactList;
	
	public ClWriter(File contactList){
		
		this.contactList = contactList;
		
	}
	
	/**
	 * adds a new contact to contacList file
	 * @param name, a String, name of new contact
	 * @param ID, a String, ID of new contact
	 */
	public void addContact(String name, String ID) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(contactList.getAbsolutePath())));
		writer.write(name + " " + ID);
		writer.newLine();
		writer.close();
		
	}

	/**
	 * delete a contact from contactList file
	 * @param ID, a String, ID of contact to be deleted
	 */
	public void deleteContact(String ID) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(contactList.getAbsolutePath()));
		String line = null;
		LinkedList <String> newContactList = new LinkedList <String> ();
		
		//load the contact list into the LinkedList, when the contact to be deleted is found then remove it
		while((line = reader.readLine()) != null){
			if(!line.split(" ")[1].equals(ID)){
				newContactList.add(line);
			}
		}
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(contactList.getAbsolutePath())));
		//write the updated contact list to the file
		
		while(newContactList.iterator().hasNext()){	
			writer.write(newContactList.iterator().next());
			writer.newLine();
		}
		
		writer.close();
		
	}

	/**
	 * update a contact in contactList file
	 * @param ID, a String, ID of contact to be updated
	 * @param newName, a String, new name of updated contact
	 */
	public void updateContact(String newName, String ID) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(contactList.getAbsolutePath()));
		String line = null;
		LinkedList <String> newContactList = new LinkedList <String> ();
		
		//load the contact list into the LinkedList, when the contact to be updated is found then update it
		while((line = reader.readLine()) != null){
			if(line.split(" ")[1].equals(ID)){
				newContactList.add(newName + " " + ID);
			}
			newContactList.add(line);
		}
		reader.close();
		
		deleteListContent();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(contactList.getAbsolutePath())));
		
		//write the updated contact list to the file
		while(newContactList.iterator().hasNext()){
			
			writer.write(newContactList.iterator().next());
			writer.newLine();
		}
		
		writer.close();
		
	}
		

	/**
	 * Checks if given ID is on the list
	 * @param ID
	 * @return
	 * @throws IOException 
	 */
	public boolean isOnList(String ID) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(contactList.getAbsolutePath()));
		String line = null;
		
		while((line = reader.readLine()) != null){
				if(line.split(" ")[1].equals(ID)){
					reader.close();
					return true;
				}	
			}
			return false;

		}
		
	/**
	 * empty the contactList file
	 * @throws FileNotFoundException 
	 */
	public void deleteListContent() throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(contactList.getAbsolutePath());
		pw.close();
	}

}