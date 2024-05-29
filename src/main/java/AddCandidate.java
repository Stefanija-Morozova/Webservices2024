import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.*;

@SuppressWarnings("serial")
@WebServlet(
    name = "addCandidate",
    urlPatterns = {"/addcandidate"})

public class AddCandidate extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		      throws IOException {
		//servlet connection to database
		String driver = "com.mysql.jdbc.Driver";
		String DBpath = "//localhost/electionmachine"; //db path
		String username = "root"; //access name
		String password = "sm7-DMFND"; //password
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		//logging instance setup for connection
		try {
			Class.forName(driver).newInstance();
		} catch (Exception ex) {
			Logger.getLogger(GetCandidate.class.getName()).log(Level.SEVERE, "Whoopsie bastard!", ex);
		}
		try { //connects to database
			connection = DriverManager.getConnection("jdbc:mysql:" + DBpath, username, password);
			statement = connection.createStatement();
			}catch (Exception ex) {
	            System.out.println("Connection turbulent ot unreasonable, can't connect");
			}
		try {// selects all candidates and displays them		
			String sql;
	        sql = "Select*From candidates";
	        result = statement.executeQuery(sql);
	        System.out.print(result);
			}catch (Exception ex1) {
	            System.out.println("Could not select candidates from database");
			}
		 try { //gets the direct ID from the database list
	            int Id = result.getInt("ID"); 
	            System.out.println("ID: " + Id + "\n");    
	            
	        } catch (Exception ex11) {
	            System.out.println("Failure to get ID");
	        }
		 
		 //Gets the ID and candidate details via making arrays and then adding the values into the arrays
		    ArrayList<Integer> candidate_id = new ArrayList<Integer>();
		    ArrayList<String> first_name = new ArrayList<String>();
	        ArrayList<String> last_name = new ArrayList<String>();
	        ArrayList<String> municipality = new ArrayList<String>();
	        ArrayList<String> party = new ArrayList<String>();
	        ArrayList<String> age = new ArrayList<String>();
	        try {
				while (result.next()) {
					  int Cid = result.getInt("CANDIDATE_ID");
				      String name = result.getString("FIRST_NAME");
				      String surname = result.getString("LAST_NAME");
				      String region = result.getString("MUNICIPALITY");
				      String parti = result.getString("PARTY");
				      String vecums = result.getString("AGE");
				      candidate_id.add(Cid);
				      first_name.add(name);
				      last_name.add(surname);
				      municipality.add(region);
				      party.add(parti);
				      age.add(vecums);
				      System.out.println(Cid);
				      System.out.println(name);
				      System.out.println(surname);
				      System.out.println(region);
				      
				      
					}
	     } catch (Exception ex1) {
	         System.out.println("Messed up the arrays somehow, recheck.");
	     }
	        //servlet additions to load page
	        response.setContentType("text/plain");
	     	response.setCharacterEncoding("UTF-8");
	     	response.getWriter().print("Election Machine\r\n");
	     	response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	         

	         //printing page details/forms out, getting params for insteting
	         out.println("<html>");
	         out.println("<body>");
	         out.println("<ul>");
	         out.println("<form action=/addcandidates method=post"); //form setup
	         
	         for (int i = 0; i<candidate_id.size(); i++) {
	        	 out.println("<br>");
	         	 out.println("<li>" + "CANDIDATE ID: " + candidate_id.get(i) + ",  " + first_name.get(i) + " " + last_name.get(i) + " " + age.get(i) + ", PARTY: " + party.get(i) +  ", REGION:" + municipality.get(i) + "</li>");
	             out.println("<br>");
	             }
             out.println("<br>");
	         
	         out.println("<label for=\"fname\">Enter ID:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_id\" name=\"tba_id\"><br>"); //tba stands for to be added
	         
	         out.println("<label for=\"fname\">Enter name:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_name\" name=\"tba_name\"><br>");
	         
	         out.println("<label for=\"fname\">Enter last name:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_lname\" name=\"tba_lname\"><br>");
	         
	         out.println("<label for=\"fname\">Enter Party:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_party\" name=\"tba_party\"><br>");
	         
	         out.println("<label for=\"fname\">Enter Region:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_party\" name=\"tba_party\"><br>");
	         
	         out.println("<label for=\"fname\">Enter Age:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_age\" name=\"tba_age\"><br>");
	         
	         out.println("<label for=\"fname\">Enter Description:</label><br>");
	         out.println("<input type=\"text\" id=\"tba_description\" name=\"tba_description\"><br>");
	         
	         out.println("<input type=\"submit\" value=\"Submit\">");
	         
	         out.println("</form>");
	         out.println("</ul>");
	         out.println("</body></html>");
	}
	//instance, connection, statement
	  public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
      	String driver = "com.mysql.jdbc.Driver";
   		String DBpath = "//localhost/electionmachine";
   		String username = "root";
   		String password = "sm7-DMFND";
   		Connection connection2 = null;
	
   		try {
 			Class.forName(driver).newInstance();
 		} catch (Exception ex) { }
 		
 		
 		try {
 		connection2 = DriverManager.getConnection("jdbc:mysql:" + DBpath, username, password);
 		}catch (Exception ex) {
             System.out.println("Connection to database failed");}
 
 		try { //takes the inserted values/parameters of the form, turns them into a set of values and inserted into the sql statement
     		PreparedStatement st = connection2.prepareStatement("INSERT INTO candidates VALUES(?, ?, ?, ?, ?, ?, ?)");
     		st.setInt(1, Integer.valueOf(request.getParameter("tba_id"))); //since this needs an integer, it gets turned into an integer
     		st.setString(2, request.getParameter("tba_name"));
     		st.setString(3, request.getParameter("tba_lname"));
     		st.setString(4, request.getParameter("tba_party"));
     		st.setString(5, request.getParameter("tba_region"));    		
     		st.setInt(6, Integer.valueOf(request.getParameter("tba_age"))); //same with age, this doesn't apply to strings
     		st.setString(7, request.getParameter("tba_description"));  
     		st.executeUpdate();	
     		 System.out.println("Candidate Added");
	   	        
   	         
	         }catch (Exception ex1) {
		         System.out.println("Could not add Candidate");
		     }
	         
} 
	


	  } 
