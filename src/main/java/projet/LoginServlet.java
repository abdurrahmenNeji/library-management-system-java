package projet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to validate user and get role
            String query = "SELECT id, nom, email, role FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // User authenticated
                int userId = rs.getInt("id");
                String userName = rs.getString("nom");
                String userEmail = rs.getString("email"); // Retrieve email
                String role = rs.getString("role"); // Retrieve the role

                // Store user details in session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("userName", userName);
                session.setAttribute("userEmail", userEmail); // Set email in session
                session.setAttribute("userRole", role); // Store role in session

                // Redirect based on role
                if ("Administrateur".equalsIgnoreCase(role)) {
                    response.sendRedirect("adminDashboard.jsp"); // Redirect admin to admin dashboard
                } else {
                    response.sendRedirect("books"); // Redirect regular user to book page
                }
            } else {
                // Authentication failed
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}
