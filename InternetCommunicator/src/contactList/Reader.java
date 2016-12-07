package contactList;

import java.io.IOException;
import java.util.HashMap;

public interface Reader {
	
	/**
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 *
	 */
	
	public abstract void loadContactList() throws NumberFormatException, IOException;
}
