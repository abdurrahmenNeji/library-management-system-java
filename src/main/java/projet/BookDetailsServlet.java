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

@WebServlet("/bookDetails")
public class BookDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIdParam = request.getParameter("id"); // Book ID from request
        String error = request.getParameter("error");   // Error parameter for messaging

        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required.");
            return;
        }

        Book book = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            int bookId;
            try {
                bookId = Integer.parseInt(bookIdParam); // Parse bookId
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID format.");
                return;
            }

            // SQL Query to fetch book details
            String query = """
                SELECT l.id, l.titre, l.resume, l.annee,
                       COALESCE(c.nom, 'Unknown') AS categorie,
                       COALESCE(STRING_AGG(a.nom, ', '), 'No authors') AS auteurs,
                       l.format
                FROM livres l
                LEFT JOIN categories c ON l.categorie_id = c.id
                LEFT JOIN livres_auteurs la ON l.id = la.livre_id
                LEFT JOIN auteurs a ON la.auteur_id = a.id
                WHERE l.id = ?
                GROUP BY l.id, l.titre, l.resume, l.annee, c.nom, l.format;
            """;

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, bookId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Populate Book object
                        book = new Book(
                            rs.getInt("id"),              // Book ID
                            rs.getString("titre"),        // Title
                            rs.getString("resume"),       // Summary
                            rs.getInt("annee"),           // Year
                            rs.getString("categorie"),    // Category
                            rs.getString("auteurs"),      // Authors
                            rs.getString("format"),        // Format
                            null,
                            null,
                            0//categoryId
                        	);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A database error occurred.");
            return;
        }

        // If no book is found, redirect to homepage with an error
        if (book == null) {
            response.sendRedirect("bookDetails.jsp?error=book_not_found");
            return;
        }

        // Attach the book and error attribute to the request
        request.setAttribute("book", book);
        if (error != null) {
            request.setAttribute("error", error);
        }

        // Forward to bookDetails.jsp
        request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
    }
}
