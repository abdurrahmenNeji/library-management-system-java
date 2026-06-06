package projet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the session and userId
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            // User not logged in
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        // List to store favorite books
        List<Book> favoriteBooks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to fetch user's favorite books
            String query = """
                SELECT l.id, l.titre, l.resume, l.annee, c.nom AS categorie, STRING_AGG(a.nom, ', ') AS auteurs, l.format
                FROM favorites f
                JOIN livres l ON f.book_id = l.id
                LEFT JOIN categories c ON l.categorie_id = c.id
                LEFT JOIN livres_auteurs la ON l.id = la.livre_id
                LEFT JOIN auteurs a ON la.auteur_id = a.id
                WHERE f.user_id = ?
                GROUP BY l.id, c.nom, l.format
            """;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId); // Use the userId from the session

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Create a Book object and add it to the list
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("resume"),
                    rs.getInt("annee"),
                    rs.getString("categorie"),
                    rs.getString("auteurs"),
                    rs.getString("format"),
                    null,
                    null,
                    0//categoryId
                );
                favoriteBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving favorites.");
            return;
        }

        // Attach the favoriteBooks to the request
        request.setAttribute("favoriteBooks", favoriteBooks);

        // Forward to favorites.jsp
        request.getRequestDispatcher("favorites.jsp").forward(request, response);
    }
}
