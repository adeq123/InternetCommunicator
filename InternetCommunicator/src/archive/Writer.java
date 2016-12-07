package archive;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Writer {
	
	public abstract void addNewTalk(String ID, String newTalk) throws FileNotFoundException, IOException;
	public abstract void clearArchive(String ID) throws FileNotFoundException;
}
