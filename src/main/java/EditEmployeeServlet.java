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

@WebServlet("/editEmployee")
public class EditEmployeeServlet extends HttpServlet {
    private static final String QUERY_BY_ID = "SELECT id, name, designation, salary FROM employee WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Edit Employee</title>");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<style>");
        pw.println(".custom-container { display: flex; justify-content: center; align-items: center; height: 100vh; }");
        pw.println(".custom-card { max-width: 400px; }");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");

        pw.println("<div class='container custom-container'>");
        pw.println("<div class='card custom-card mx-auto'>");
        pw.println("<div class='card-body'>");

        pw.println("<h5 class='card-title'>Edit Employee</h5>");

        String employeeIdParam = req.getParameter("id");

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdParam);

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_mgmt", "root", "root");
                     PreparedStatement ps = con.prepareStatement(QUERY_BY_ID)) {

                    ps.setInt(1, employeeId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String name = rs.getString("name");
                            String designation = rs.getString("designation");
                            double salary = rs.getDouble("salary");

                            pw.println("<form action='editEmployee' method='post'>");
                            pw.println("<input type='hidden' name='id' value='" + employeeId + "'>");
                            pw.println("Name: <input type='text' name='name' value='" + name + "'><br>");
                            pw.println("Designation: <input type='text' name='designation' value='" + designation + "'><br>");
                            pw.println("Salary: <input type='text' name='salary' value='" + salary + "'><br>");
                            pw.println("<input type='submit' value='Update'>");
                            pw.println("</form>");
                        } else {
                            pw.println("<p>Employee not found</p>");
                        }
                    }

                } catch (SQLException e) {
                    pw.println("<p>Error retrieving employee data: " + e.getMessage() + "</p>");
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                pw.println("<p>Invalid employee ID format</p>");
            }
        } else {
            pw.println("<p>No employee ID provided</p>");
        }

        pw.println("</div>");
        pw.println("</div>");
        pw.println("</div>");

        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdParam = req.getParameter("id");
        String name = req.getParameter("name");
        String designation = req.getParameter("designation");
        String salaryParam = req.getParameter("salary");

        if (employeeIdParam != null && !employeeIdParam.isEmpty() && name != null && designation != null && salaryParam != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdParam);
                double salary = Double.parseDouble(salaryParam);

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_mgmt", "root", "root");
                     PreparedStatement psUpdate = con.prepareStatement("UPDATE employee SET name=?, designation=?, salary=? WHERE id=?")) {

                    psUpdate.setString(1, name);
                    psUpdate.setString(2, designation);
                    psUpdate.setDouble(3, salary);
                    psUpdate.setInt(4, employeeId);

                    int rowsAffected = psUpdate.executeUpdate();

                    if (rowsAffected > 0) {
                        resp.sendRedirect("showData");
                    } else {
                        resp.getWriter().println("<p>Error updating employee</p>");
                    }

                } catch (SQLException e) {
                    resp.getWriter().println("<p>Error updating employee: " + e.getMessage() + "</p>");
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                resp.getWriter().println("<p>Invalid employee ID or salary format</p>");
            }
        } else {
            resp.getWriter().println("<p>Invalid parameters provided</p>");
            resp.sendRedirect("showData");
        }
    }
}
