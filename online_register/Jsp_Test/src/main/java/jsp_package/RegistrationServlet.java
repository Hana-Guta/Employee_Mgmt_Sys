package jsp_package;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/online_register";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
        rp.setContentType("text/html");

        String name = rq.getParameter("name");
        String email = rq.getParameter("email");
        String password = rq.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
           
                String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);

                    pstmt.executeUpdate();
                }
            }

            rp.sendRedirect("conformation.jsp");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); 

            rp.getWriter().println("Registration failed. Please try again later.");
        }
    }
}
