package archiveTests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import archive.ArchiveWriter;

public class ArchiveWriterTest {

	String archiveOnePath;
	File archiveOneFile;
	File testFile;
	File testDir;
	ArchiveWriter testArchiveOne;
	
	String ID;
	String messeageOne;
	String messeageTwo;
	String test;
	String readMesseage;
	
	@Before
	public void setUp() throws Exception {
		testDir = new File("C://test");
		archiveOnePath = testDir.getAbsolutePath() + "//archiveOne";
		
		ID = "GNPI";
		messeageOne = "Siema";
		messeageTwo = "pzdr";		
		
		archiveOneFile = new File(archiveOnePath);
		if(!archiveOneFile.exists())
			archiveOneFile.mkdirs();
		testArchiveOne = new ArchiveWriter(archiveOneFile);	
		testFile = new File(archiveOneFile.getAbsolutePath() + "//" + ID + ".txt");
		
	}

	@Test
	public void testArchiveCreated(){
		try {
			testArchiveOne.addNewTalk(ID, "");
			assertTrue(testFile.exists());
		} catch (IOException e) {
			
			System.out.println("Problem with reading the archive txt file");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddingSingleTalk() {
		try {
			testArchiveOne.addNewTalk(ID, messeageOne);
			BufferedReader czytaj = new BufferedReader(new FileReader(testFile.getAbsolutePath()));
			test = messeageOne;
			assertTrue(test.equals(czytaj.readLine()));
			czytaj.close();
		} catch (IOException e) {
			
			System.out.println("Problem with reading the archive txt file");
			e.printStackTrace();
			
		}
		
	}
	
	
	@Test 
	public void testAddingMultipleTalks() {
		try {
			testArchiveOne.addNewTalk(ID, messeageOne);
			testArchiveOne.addNewTalk(ID, messeageTwo);
			BufferedReader czytaj = new BufferedReader(new FileReader(testFile.getAbsolutePath()));
			test = messeageOne + System.lineSeparator() + messeageTwo+ System.lineSeparator();
			readMesseage = "";
			String oneLine = "";
			while((oneLine=czytaj.readLine())!=null)
				readMesseage = readMesseage + oneLine + System.lineSeparator();	
			
			assertTrue(test.equals(readMesseage));
			czytaj.close();
		} catch (IOException e) {
			
			System.out.println("Problem with reading the archive txt file");
			e.printStackTrace();
			
		}
		
	}
	
	@Test
	public void testClearArchive(){
		try {
			testArchiveOne.addNewTalk(ID, messeageOne);
			testArchiveOne.clearArchive(ID);
			BufferedReader czytaj = new BufferedReader(new FileReader(testFile.getAbsolutePath()));
			readMesseage = "";
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
