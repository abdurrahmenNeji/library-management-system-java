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

@WebServlet("/removeFromFavorites")
public class RemoveFromFavoritesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the logged-in user's ID from the session
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        String bookIdParam = request.getParameter("bookId");
        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is missing.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Delete the book from favorites for the logged-in user
            String deleteQuery = "DELETE FROM favorites WHERE user_id = ? AND book_id = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setInt(1, userId);
            stmt.setInt(2, Integer.parseInt(bookIdParam));

            if (stmt.executeUpdate() > 0) {
                // Redirect back to the favorites page
                response.sendRedirect("favorites");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Favorite not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while removing the favorite.");
        }
    }
}
