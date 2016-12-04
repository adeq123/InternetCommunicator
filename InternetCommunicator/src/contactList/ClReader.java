package contactList;

import java.util.HashMap;

public class ClReader implements Reader {

	HashMap <Long, String> ContactListMap;
	
	public ClReader(){
		ContactListMap = new HashMap <Long, String>();
	}
	
	public void  loadContactList() {
		ContactListMap.clear();
	}
	
}
