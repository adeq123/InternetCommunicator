package fittedExceptions;

/**
 * This exception should be thrown when tried to search for user without entering user number
 * @author RoguskiA
 *
 */

public class UserNumberFieldEmptyException extends Exception{
				
			public UserNumberFieldEmptyException() {
				
				super("Please enter user number if you want to search for it...");
			
			}
			
			public UserNumberFieldEmptyException(String messeage) {
			
				super(messeage);
			
			
		}
	}

