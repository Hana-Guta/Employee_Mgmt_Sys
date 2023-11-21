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

@WebServlet("/deleteEmployee")
public class DeleteEmployeeServlet extends HttpServlet {
    private static final String QUERY_BY_ID = "SELECT id, name, designation, salary FROM employee WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM employee WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Delete Employee</title>");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<style>");
        pw.println(".custom-container { display: flex; justify-content: center; align-items: center; height: 100vh; }");
        pw.println(".custom-card { max-width: 400px; }"); // Adjust the max-width as needed
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");

        pw.println("<div class='container custom-container'>"); // Start of custom container
        pw.println("<div class='card custom-card mx-auto'>"); // Start of custom card div
        pw.println("<div class='card-body'>"); // Start of card body

        pw.println("<h5 class='card-title'>Delete Employee</h5>");

        String employeeIdParam = req.getParameter("id");

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdParam);

                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_mgmt", "root", "root");
                     PreparedStatement psQuery = con.prepareStatement(QUERY_BY_ID);
                     PreparedStatement psDelete = con.prepareStatement(DELETE_BY_ID);) {

                    psQuery.setInt(1, employeeId);
                    try (ResultSet rs = psQuery.executeQuery();) {
                        if (rs.next()) {
                            String name = rs.getString("name");
                            String designation = rs.getString("designation");
                            double salary = rs.getDouble("salary");

                            pw.println("<p>Are you sure you want to delete the following employee?</p>");
                            pw.println("<p>ID: " + employeeId + "</p>");
                            pw.println("<p>Name: " + name + "</p>");
                            pw.println("<p>Designation: " + designation + "</p>");
                            pw.println("<p>Salary: " + salary + "</p>");
                            pw.println("<form action='deleteEmployee' method='post'>");
                            pw.println("<input type='hidden' name='id' value='" + employeeId + "'>");
                            pw.println("<input type='submit' value='Delete'>");
                            pw.println("</form>");
                        } else {
                            pw.println("<p>Employee not found</p>");
                        }
                    }

                } catch (SQLException e) {
                    pw.println("<p>Error retrieving/deleting employee data: " + e.getMessage() + "</p>");
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                pw.println("<p>Invalid employee ID format</p>");
            } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else {
            pw.println("<p>No employee ID provided</p>");
        }

        pw.println("</div>"); // End of card body
        pw.println("</div>"); // End of custom card div
        pw.println("</div>"); // End of custom container

        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdParam = req.getParameter("id");

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdParam);

                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_mgmt", "root", "root");
                     PreparedStatement psDelete = con.prepareStatement(DELETE_BY_ID);) {

                    psDelete.setInt(1, employeeId);
                    int rowsAffected = psDelete.executeUpdate();

                    if (rowsAffected > 0) {
                        resp.sendRedirect("showData");
                    } else {
                        PrintWriter pw = resp.getWriter();
                        pw.println("<p>Error deleting employee</p>");
                        pw.close();
                    }

                } catch (SQLException e) {
                    resp.sendRedirect("showData"); // Redirect to the main page in case of an error
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("showData"); // Redirect to the main page in case of an error
            } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else {
            resp.sendRedirect("showData"); // Redirect to the main page if no employee ID provided
        }
    }
}
