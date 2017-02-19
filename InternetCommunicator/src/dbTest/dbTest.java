package dbTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DB;

public class dbTest {
	
	private String userNumber1;
	private String userName1;
	private String userNumber2;
	private String userName2;
	DB database;
	@Before
	public void setUp() throws Exception {
		
		userNumber1 = "123";
		userName1 = "adam1";
		userNumber2 ="1234";
		userName2 = "adam2";
		
		database = new DB();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddFindUser() {
		database.addUser(userNumber1, userName1);
		database.addUser(userNumber2, userName2);
		
		assertTrue(database.findUser(userNumber1)[1].equals(userName1)
				&& database.findUser(userNumber2)[1].equals(userName2));
	}
	
	@Test
	public void testDeleteFindUser() {
		database.addUser(userNumber1, userName1);
		database.addUser(userNumber2, userName2);
		
		database.deleteUser(userNumber2);
		
		assertTrue(database.findUser(userNumber2) == null);
	}

}
