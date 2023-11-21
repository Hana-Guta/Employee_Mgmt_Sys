import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployeeServlet")

public class AddEmployeeServlet extends HttpServlet {
	private static final String QUERY  = "INSERT INTO employee (name, designation, salary) VALUES (?, ?, ?)";
     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	 PrintWriter pw = resp.getWriter();
    	 resp.setContentType("text/html");
    	 
    	
		pw.println( "<link rel='stylesheet' href='css/bootstrap.css'></link>");
    	 
    	 String name = req.getParameter("name");
    	 String designation = req.getParameter("designation");
    	 double salary = Double.parseDouble(req.getParameter("salary"));
    	 
    	 try {
    		 Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_mgmt", "root", "root");
    			 
    			 PreparedStatement ps =con.prepareStatement(QUERY);){
    		 ps.setString(1, name);
             ps.setString(2, designation);
             ps.setDouble(3, salary);
             
             pw.println("<div class='card-body' style = 'margin:auto; width:300px; margin-top:200px;>");
             
             int rowsAffected = ps.executeUpdate();

             // Process the result
             if (rowsAffected > 0) {
                 pw.println("<h2 class='bg-dark text-light text-center'>Employee added successfully!</h2>");
             } else {
                 pw.println("<h2 class='bg-dark text-light text-center'>Error adding employee.</h2>");
             }
    		 
    	 } catch(SQLException se) {
    		 pw.println("<h2 class='bg-dark text-light text-center'>" + se.getMessage()+ "</h2>");
    		 se.printStackTrace();
    	 } catch(Exception e) {
    		 e.printStackTrace();
    	 }
    	 pw.println("<a href='AddEmployee.html'><button class='btn btn-light text-dark'>AddEmployee</button></a>");
    	 pw.println("</div>");
    	 pw.close();
    			 
    }
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req , resp);
    }
}
