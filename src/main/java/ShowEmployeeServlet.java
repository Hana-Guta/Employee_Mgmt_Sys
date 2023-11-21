import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showData")
public class ShowEmployeeServlet extends HttpServlet {
    private static final String QUERY = "SELECT id, name, designation, salary FROM employee";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<style>");
        pw.println(".custom-container { display: flex; justify-content: center; align-items: center; height: 100vh; }");
        pw.println(".custom-card { max-width: 800px; }"); // Adjust the max-width as needed
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");

        pw.println("<div class='container custom-container'>"); 
        pw.println("<div class='card custom-card mx-auto'>"); // Start of custom card div
        pw.println("<div class='card-body'>"); // Start of card body

        pw.println("<h5 class='card-title'>Employee Information</h5>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_mgmt", "root", "root");
             PreparedStatement ps = con.prepareStatement(QUERY);
             ResultSet rs = ps.executeQuery();) {

            pw.println("<table class='table table-bordered table-striped'>");
            pw.println("<thead class='thead-dark'>");
            pw.println("<tr>");
            pw.println("<th>Name</th>");
            pw.println("<th>Designation</th>");
            pw.println("<th>Salary</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");
            pw.println("</thead>");
            pw.println("<tbody>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String designation = rs.getString("designation");
                double salary = rs.getDouble("salary");

                pw.println("<tr>");
                pw.println("<td>" + escapeHtml(name) + "</td>");
                pw.println("<td>" + escapeHtml(designation) + "</td>");
                pw.println("<td>" + salary + "</td>");
                pw.println("<td><a href='editEmployee?id=" + id + "'>Edit</a></td>");
                pw.println("<td><a href='deleteEmployee?id=" + id + "'>Delete</a></td>");
                pw.println("</tr>");
            }

            pw.println("</tbody>");
            pw.println("</table>");

        } catch (SQLException se) {
            pw.println("<h2 class='bg-dark text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pw.println("</div>"); // End of card body
        pw.println("</div>"); // End of custom card div
        pw.println("</div>"); // End of custom container

        pw.println("<a href='AddEmployee.html'><button class='btn btn-light text-dark'>Add Employee</button></a>");
        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // Utility method to escape HTML characters
    private String escapeHtml(String input) {
        return input == null ? "" : input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
