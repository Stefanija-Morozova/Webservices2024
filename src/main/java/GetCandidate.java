import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
//set up the webservlet for getting a list of all the candidates
@WebServlet(
    name = "getCandidate",
    urlPatterns = {"/getcandidate"})
//public class connected to servlets
public class GetCandidate extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  
	  String driver = "com.mysql.jdbc.Driver";
		String DBpath = "//loaclhost/ELECTIONMACHINE"; //add database path
		String username = "root"; //user name
		String password = "sm7-DMFND"; //add password
		Connection connection = null; //responsible for connection
		Statement statement = null; //responsible for query statement
		ResultSet result = null; //responsible for result of query statement

		try {
			//setting up class instance
			Class.forName(driver).newInstance();
		} catch (Exception ex) {
			Logger.getLogger(GetCandidate.class.getName()).log(Level.SEVERE, "Whopsie, can't do that!", ex);
		}
		//setting up mysql connection 
		try {
			connection = DriverManager.getConnection("jdbc:mysql:" + DBpath, username, password);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//sets up sql statement as a string

		String sql;
        
        sql = "Select*From candidates";
        try {
        	//converts string into query and executes it
			result = statement.executeQuery(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.print(result);
        
        try {
            int i = result.getInt("ID"); 
            // There is also other version for getInt which relies on column index number, BUT WE'RE USING THIS ONE
            System.out.println("i: " + i + "\n");    
            
        } catch (Exception ex) {
            System.out.println("*** Failure ***");
            
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
            System.out.println("Failure");
        }
        
        	response.setContentType("text/plain");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().print("Election Machine\r\n");
        	

        	response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<body>");
            out.println("<ul>");
            
            for (int i = 0; i<first_name.size(); i++) {
            	out.println("<li>" + first_name.get(i) + " " + last_name.get(i) + ", Age : " + age.get(i) + ", Party : " + party.get(i) +  ", Municipality: " + municipality.get(i) + "</li>");
                out.println("<br>");
                }
            out.println("</ul>");
            out.println("</body></html>");
    
        }
  	}

  


        
	}
