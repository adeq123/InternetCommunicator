package contactList;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Writer {

	public abstract void addContact(String name, String ID) throws IOException;
	public abstract void deleteContact(String ID) throws IOException;
	public abstract void updateContact(String newName, String ID) throws IOException;
	public abstract boolean isOnList(String ID) throws IOException;
	
}
