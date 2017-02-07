package archiveTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import archive.Archive;

public class ArchiveTest {
	
	File testDir ;
	File archiveFile;
	File testFile;
	String archivePath;
	Archive testArchive;
	@Before
	public void setUp() throws Exception {
		
		testDir = new File("C://test");
		archivePath = testDir.getAbsolutePath() + "//archiveOne";
		archiveFile = new File(archivePath);
		testArchive = new Archive(archiveFile);
	}

	@Test
	public void testIfWriterReaderWereCreated() {
		assertTrue(testArchive.getArchiveReader()!=null && testArchive.getArchiveWriter()!=null);
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
