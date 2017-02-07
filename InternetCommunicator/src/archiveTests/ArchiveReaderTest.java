package archiveTests;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import archive.ArchiveReader;

public class ArchiveReaderTest {
	
	File testDir ;
	File archiveFile;
	File testFile;
	String archivePath;
	String ID;
	String messeage;
	String readLine;
	ArchiveReader testArchiveReader;
	
	@Before
	public void setUp() throws Exception {
		testDir = new File("C://test");
		archivePath = testDir.getAbsolutePath() + "//archiveOne";
		archiveFile = new File(archivePath);
		if(!archiveFile.exists())
			archiveFile.mkdirs();
		testArchiveReader = new ArchiveReader(archiveFile);	
		
		
		ID = "GNPI";
		messeage = "Co dzisiaj porabiasz?";
		testFile = new File(archiveFile.getAbsolutePath() + "//" + ID + ".txt");
		

	}


	@Test
	public void testArchiveContent() {
		try {
			testFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testFile)));
			writer.write(messeage);
			writer.close();
			readLine = testArchiveReader.getArchiveContent(ID);
			assertTrue(messeage.equals(readLine));
			
			
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
