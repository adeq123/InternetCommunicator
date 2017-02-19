package db;

public class main {

	public static void main(String[] args) {
		DB database = new DB();
		database.addUser("123", "name");
		database.addUser("1233", "nasdame");
		database.deleteUser("123");
		System.out.println(database.findUser("1233")[0]+" "+database.findUser("1233")[1]);
	}

}
