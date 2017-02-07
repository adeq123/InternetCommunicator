package archive;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Reader {

	public abstract String getArchiveContent(String ID) throws FileNotFoundException, IOException;
}
