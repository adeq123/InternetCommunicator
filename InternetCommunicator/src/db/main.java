package db;

public class main {

	public static void main(String[] args) {
		DB database = new DB();
		database.createTable();
		database.addUser("123", "name");
		database.addUser("1233", "nasdame");
		
		for(int i = 5; i < 100; i++){
			database.addUser(Integer.toString(i), "UserX"+Integer.toString(i));
		}
		
		System.out.println(database.findUser("1233")[0]+" "+database.findUser("1233")[1]);
	}

}
