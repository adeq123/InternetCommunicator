package archiveTests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import archive.Archive;

public class ArchiveTest {
	
	String archiveOnePath;
	String archiveTwoPath;
	
	@Before
	public void setUp() throws Exception {
		
		archiveOnePath = "";
		archiveTwoPath = "";
		
		Archive testArchiveOne = new Archive(new File(archiveOnePath));
		Archive testArchiveTwo = new Archive(new File(archiveTwoPath));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
