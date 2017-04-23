package Communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StreamHolder {
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public StreamHolder(ObjectInputStream in, ObjectOutputStream out){
		
		this.in = in;
		this.out = out;
		
	}
	
	public ObjectInputStream getIn(){
		return in;
	}
	
	public ObjectOutputStream getOut(){
		return out;
	}
}
