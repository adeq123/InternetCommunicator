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
	ArchiveWriter testArchiveOne;
	
	String ID;
	String messeageOne;
	String messeageTwo;
	
	@Before
	public void setUp() throws Exception {
		
		archiveOnePath = "C:\\test\\archiveOne";
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
		assertTrue(testFile.exists());
	}
	
	@Test
	public void testAddingNewTalks() {
		try {
			testArchiveOne.addNewTalk(ID, messeageOne);
			BufferedReader czytaj = new BufferedReader(new FileReader(testFile.getAbsolutePath()));
			assertTrue(messeageOne.equals(czytaj.readLine()));
			czytaj.close();
		} catch (IOException e) {
			
			System.out.println("Problem with reading the archive txt file");
			e.printStackTrace();
			
		}
		
	}
	
	@Test 
	public void testSecondTalk() {
		try {
			testArchiveOne.addNewTalk(ID, messeageTwo);
			BufferedReader czytaj = new BufferedReader(new FileReader(testFile.getAbsolutePath()));
			messeageTwo += messeageOne;
			System.out.println(czytaj.readLine());
			assertTrue(messeageTwo.equals(czytaj.readLine()));
			czytaj.close();
		} catch (IOException e) {
			
			System.out.println("Problem with reading the archive txt file");
			e.printStackTrace();
			
		}
		
	}
	
	/*@After
	public void tearDown() throws Exception {
		delete(archiveOneFile) ;
	}
	
	/**
	 * Deletes the folder recursively
	 * @param f, File to be deleted
	 * @throws IOException
	 *
	void delete(File f) throws IOException {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  if (!f.delete())
		    throw new FileNotFoundException("Failed to delete file: " + f);
		}
	*/

}
