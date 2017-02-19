package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class DB{
  
	  private Connection conn = null;
	  private Statement stmt = null;
	  private ResultSet rs = null;
  
  public void connect(){
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      String url = "jdbc:mysql://localhost:3306/usersdb";
      String user ="root" ;
      String pwd = "adrian";
      conn = DriverManager.getConnection(url, user, pwd);
 
      
 
    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }catch(Exception e){e.printStackTrace();}	
  }
  
  public void createTable(){
	  connect();
	    try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(
		            "CREATE TABLE IF NOT EXISTS tabelOfUsers "
		            + "(userNumber VARCHAR(64) NOT NULL,"
		            + "userName VARCHAR(64) NOT NULL,"+
		            "PRIMARY KEY (userNumber))");
		    
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		      System.out.println("SQLState: " + ex.getSQLState());
		      System.out.println("VendorError: " + ex.getErrorCode());
		    }catch(Exception e){e.printStackTrace();}	

	 
	  }
	 
  public void addUser(String userNumber, String userName){
	  connect();
	    try {
			stmt = conn.createStatement();
			stmt = conn.createStatement();
		    stmt.executeUpdate(
		            "INSERT IGNORE INTO tabelOfUsers VALUES ('"+userNumber+"','"+userName+"')");
		    
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		      System.out.println("SQLState: " + ex.getSQLState());
		      System.out.println("VendorError: " + ex.getErrorCode());
		    }catch(Exception e){e.printStackTrace();}	

	 
	  }  
  
  public void deleteUser(String userNumber){
	  
	    try {
			stmt = conn.createStatement();
			stmt = conn.createStatement();
		    stmt.executeUpdate(
		            "DELETE FROM tabelOfUsers WHERE userNumber=" + userNumber);
		    
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		      System.out.println("SQLState: " + ex.getSQLState());
		      System.out.println("VendorError: " + ex.getErrorCode());
		    }catch(Exception e){e.printStackTrace();}	

	 
	  }  
  public String[] findUser(String userNumber){
	
	  try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
	    	rs = stmt.executeQuery(
			        "SELECT * FROM tabelOfUsers WHERE userNumber=" + userNumber);
	    if(rs.next()){	
	    	String [] toReturn = {rs.getString(1), rs.getString(2)};
	    	return toReturn;
	    }else
	    	return null;
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		      // zwalniamy zasoby, które nie bêd¹ potrzebne
		      if (rs != null) {
		        try {
		          rs.close();
		        } catch (SQLException sqlEx) { } // ignore
		        rs = null;
		      }
		 
		      if (stmt != null) {
		        try {
		          stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		 
		        stmt = null;
		      }
		    }
		return null;
	  
  }
}