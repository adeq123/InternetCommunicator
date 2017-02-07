package archive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ArchiveReader implements Reader {

	private File archiveDirectory;
	
	public ArchiveReader(File archiveDirectory) {
		this.archiveDirectory = archiveDirectory;
	}

	/**
	 * Allows you to read the archive with specific user
	 * @param ID, a String, an ID number of User 
	 * which archive is to be read
	 * @throws IOException 
	 */
	public String getArchiveContent(String ID) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(archiveDirectory.getAbsolutePath() + "//" + ID + ".txt"));
		String archive = "";
		String line;
	
		while((line = reader.readLine()) != null)
			archive += line;
		
		reader.close();
		
		return archive;
	}

}
