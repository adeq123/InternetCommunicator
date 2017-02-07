package contactListTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import contactList.ClWriter;

public class ClWriterTest {
	
	File testDir ;
	File userDir;
	File testFile;
	File testContactList ;
	ClWriter testListWriter;
	
	String contactName1;
	String contactName2;
	String contactName3;
	
	String contactID1;
	String contactID2;
	String contactID3;
	
	String oneRecord;
	String readRecords;
	String testList;
	String updatedContactName;
	
	@Before
	public void setUp() throws Exception {
		
		testDir = new File("C://test");
		userDir = new File(testDir.getAbsolutePath() + "//userDir");
		userDir.mkdirs();
		testContactList = new File(userDir.getAbsolutePath()+ "//ContactList.txt");
		testListWriter = new ClWriter(testContactList);
		
		contactName1="Adrian";
		contactName2="Marek";
		contactName3="Piotr";
		
		contactID1="41234";
		contactID2="42312314";
		contactID3="09870";
		updatedContactName = "Gerwazy";
		
	}

	@Test
	public void testAddingSingleContact() {
		try {
			testContactList.createNewFile();
			testListWriter.addContact(contactName1, contactID1);
			BufferedReader czytaj = new BufferedReader(new FileReader(testContactList.getAbsolutePath()));
			oneRecord = contactName1 + " "+contactID1;
			assertTrue(oneRecord.equals(czytaj.readLine()));
			czytaj.close();
		} catch (IOException e) {
			System.out.println("Problem with reading the test txt file");
			e.printStackTrace();
		}
		
	}
	@Test
	public void testAddingMultipleContacts() {
		try {
			testContactList.createNewFile();
			testListWriter.addContact(contactName1, contactID1);
			testListWriter.addContact(contactName2, contactID2);
			testListWriter.addContact(contactName3, contactID3);
			testList =  contactName1 + " " + contactID1 + System.lineSeparator()
			+ contactName2 + " " + contactID2 + System.lineSeparator()+ contactName3
			+ " " + contactID3 + System.lineSeparator();
			BufferedReader czytaj = new BufferedReader(new FileReader(testContactList.getAbsolutePath()));
			readRecords = "";
			while((oneRecord = czytaj.readLine()) != null)
				readRecords = readRecords + oneRecord + System.lineSeparator();
			assertTrue(readRecords.equals(testList));
			czytaj.close();
		} catch (IOException e) {
			System.out.println("Problem with reading the test txt file");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDeletingContact() {
		try {
			testContactList.createNewFile();
			testListWriter.addContact(contactName1, contactID1);
			testListWriter.addContact(contactName2, contactID2);
			testListWriter.addContact(contactName3, contactID3);
			testListWriter.deleteContact(contactID2);
			testList = contactName1 + " " + contactID1 + System.lineSeparator()+ contactName3
					+ " " + contactID3 + System.lineSeparator();
			
			BufferedReader czytaj = new BufferedReader(new FileReader(testContactList.getAbsolutePath()));
			readRecords = "";
			while((oneRecord = czytaj.readLine()) != null)
				readRecords = readRecords + oneRecord + System.lineSeparator();
			assertTrue(readRecords.equals(testList));
			czytaj.close();
			
		} catch (IOException e) {
			System.out.println("Problem with reading the test txt file");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdatingContact() {
		try {
			testContactList.createNewFile();
			testListWriter.addContact(contactName1, contactID1);
			testListWriter.addContact(contactName2, contactID2);
			testListWriter.addContact(contactName3, contactID3);
			testListWriter.updateContact(updatedContactName, contactID2);
			testList =  contactName1 + " " + contactID1 + System.lineSeparator()
			+ updatedContactName  + " " + contactID2 + System.lineSeparator()+ contactName3
			+ " " + contactID3 + System.lineSeparator();
			
			BufferedReader czytaj = new BufferedReader(new FileReader(testContactList.getAbsolutePath()));
			readRecords = "";
			while((oneRecord = czytaj.readLine()) != null)
				readRecords = readRecords + oneRecord + System.lineSeparator();
			assertTrue(readRecords.equals(testList));
			czytaj.close();
			
		} catch (IOException e) {
			System.out.println("Problem with reading the test txt file");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testIsOnList() {
		try {
			testContactList.createNewFile();
			testListWriter.addContact(contactName1, contactID1);
			testListWriter.addContact(contactName2, contactID2);
			testListWriter.addContact(contactName3, contactID3);
			
			assertTrue(testListWriter.isOnList(contactID2));
			assertFalse(testListWriter.isOnList("123"));
			
		} catch (IOException e) {
			System.out.println("Problem with reading the test txt file");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDeleteListContent(){
		try {
			testContactList.createNewFile();
			testListWriter.addContact(contactName1, contactID1);
			testListWriter.addContact(contactName2, contactID2);
			testListWriter.addContact(contactName3, contactID3);
			testListWriter.deleteListContent();
			BufferedReader czytaj = new BufferedReader(new FileReader(testContactList.getAbsolutePath()));
			assertTrue(czytaj.readLine()== null);
			czytaj.close();
		} catch (IOException e) {
			
			System.out.println("Problem with reading the archive txt file");
			e.printStackTrace();
		}
		
	}
	
	@After
	public void deleteAllTestFiles() throws Exception {
		delete(testDir) ;
		
	}
	
	
	 /** Deletes the folder recursively
	 * @param f, File to be deleted
	 * @throws IOException
	 */
	void delete(File f) throws IOException {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  if (!f.delete())
		    throw new FileNotFoundException("Failed to delete file: " + f);
		}
}
