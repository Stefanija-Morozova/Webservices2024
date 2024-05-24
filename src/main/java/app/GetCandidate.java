package app;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;/**
 * 
 */
public class GetCandidate {
 
	/**
	 * @param args
	 */
	/**
	 * 
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException{
//		makes a connection to the SQL database, and exception for when the connection/login using variables below fails
		
		String driver = "com.mysql.jdbc.Driver";
		String DBpath = "//localhost/";
		String username = " ";
		String password = " ";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
	//exception and sql instance
	    try {
		Class.forName(driver).newInstance();} 
	    catch (Exception ex) {
		Logger.getLogger(GetCandidate.class.getName()).log(Level.SEVERE, "Whoopsie, connection turbulent or disconnected", ex);}
	//solidifies connection
	    con = DriverManager.getConnection("jdbc:mysql:" + DBpath, username, password);
	    stmt = con.createStatement();
	    
	    //makes a sql query via string that can be executed and printed as a statement
	    String sql;
        sql = "Select*From candidates";
        rs = stmt.executeQuery(sql);
        System.out.print(rs);
	}}
