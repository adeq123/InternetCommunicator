package archive;

import java.io.File;

public class Archive {

	private ArchiveReader myReader;
	private ArchiveWriter myWriter;
	
	public Archive(File archiveDirectory){
		
		archiveDirectory.mkdirs();
		myWriter = new ArchiveWriter(archiveDirectory);
		myReader = new ArchiveReader(archiveDirectory);
		
	}
	
	public ArchiveReader getArchiveReader (){
		return myReader;
	}
	
	public ArchiveWriter getArchiveWriter (){
		return myWriter;
	}
	
}
