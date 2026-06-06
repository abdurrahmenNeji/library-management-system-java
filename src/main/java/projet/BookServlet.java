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

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        String search = request.getParameter("search"); // Get the search parameter
        String query = """
            SELECT l.id, l.titre, l.resume, l.annee, l.format, 
                   COALESCE(c.nom, 'Unknown') AS categorie, 
                   COALESCE(STRING_AGG(a.nom, ', '), 'No authors') AS auteurs
            FROM livres l
            LEFT JOIN categories c ON l.categorie_id = c.id
            LEFT JOIN livres_auteurs la ON l.id = la.livre_id
            LEFT JOIN auteurs a ON la.auteur_id = a.id
        """;

        // Modify the query if a search parameter exists
        if (search != null && !search.trim().isEmpty()) {
            query += """
                WHERE LOWER(l.titre) LIKE ? OR LOWER(c.nom) LIKE ? OR LOWER(a.nom) LIKE ?
            """;
        }

        query += " GROUP BY l.id, l.titre, l.resume, l.annee, l.format, c.nom ORDER BY l.id;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            if (search != null && !search.trim().isEmpty()) {
                String searchParam = "%" + search.toLowerCase() + "%";
                stmt.setString(1, searchParam);
                stmt.setString(2, searchParam);
                stmt.setString(3, searchParam);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                        rs.getInt("id"),              // Book ID
                        rs.getString("titre"),        // Title
                        rs.getString("resume"),       // Summary
                        rs.getInt("annee"),           // Year
                        rs.getString("categorie"),    // Category
                        rs.getString("auteurs"),      // Authors
                        rs.getString("format"),       // Format
                        null,
                        null,
                        0//categoryId
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A database error occurred.");
            return;
        }

        // Attach the books list to the request and forward to homepage.jsp
        request.setAttribute("books", books);
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }
}
