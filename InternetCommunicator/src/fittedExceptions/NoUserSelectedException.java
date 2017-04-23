package fittedExceptions;

/**
 * This exception should be thrown when tried to send the Messeage without user selected
 * @author RoguskiA
 *
 */
public class NoUserSelectedException extends Exception{
	
		
		public NoUserSelectedException() {
			
			super("Please selected the user if you want to send a message....");
		
		}
		
		public NoUserSelectedException(String messeage) {
		
			super(messeage);
		
		
	}
}

