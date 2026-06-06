package projet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updateUserProfile")
public class UpdateUserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String updateQuery;
        boolean updatePassword = (password != null && !password.isEmpty());
        
        // Update query based on whether the password is provided
        if (updatePassword) {
            updateQuery = "UPDATE utilisateurs SET nom = ?, email = ?, mot_de_passe = ? WHERE id = ?";
        } else {
            updateQuery = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            if (updatePassword) {
                stmt.setString(3, password);
                stmt.setInt(4, userId);
            } else {
                stmt.setInt(3, userId);
            }

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                session.setAttribute("userName", name);
                session.setAttribute("userEmail", email);
                request.setAttribute("success", "Profile updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update profile.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "A database error occurred.");
        }

        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }
}
