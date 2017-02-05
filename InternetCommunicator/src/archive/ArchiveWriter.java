package archive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ArchiveWriter implements Writer{

	private File archiveDirectory;
	
	public ArchiveWriter(File archiveDirectory) {

		this.archiveDirectory = archiveDirectory;
		
	}

	/**
	 * adds a new talk to the archive with a contact ID
	 * @param ID, a String, an ID of the contact which archive is assigned to
	 * @param newTalk, a String, an update to the String
	 */
	public void addNewTalk(String ID, String newTalk) throws IOException {
		
		File newArchive = new File(archiveDirectory.getAbsolutePath() + "//" + ID + ".txt");
		
		if(!newArchive.exists())
			newArchive.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(newArchive, true)));
	
		writer.write(newTalk);
		writer.close();
		
	}

	/**
	 * Clears the archive with a contact
	 * @param and ID of the contact which archive will be delted
	 */
	public void clearArchive(String ID) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(archiveDirectory.getAbsolutePath() + "//" + ID + ".txt");
		pw.close();
		
	}

}
