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

@WebServlet("/listAuthorBooks")
public class ListAuthorBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String authorIdParam = request.getParameter("id"); // Match JSP parameter
        if (authorIdParam == null || authorIdParam.trim().isEmpty()) {
            response.sendRedirect("manageAuthors"); // Redirect if no author ID is provided
            return;
        }
       

        int authorId;
        try {
            authorId = Integer.parseInt(authorIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageAuthors"); // Redirect if the author ID is invalid
            return;
        }

        List<Book> books = new ArrayList<>();
        String authorName = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch author's name
            String authorQuery = "SELECT nom FROM auteurs WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(authorQuery)) {
                stmt.setInt(1, authorId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        authorName = rs.getString("nom");
                    }
                }
            }

            if (authorName == null) {
                response.sendRedirect("manageAuthors"); // Redirect if the author is not found
                return;
            }

            // Fetch books written by the author
            String booksQuery = """
                SELECT l.id, l.titre, l.resume, l.annee, l.format
                FROM livres l
                JOIN livres_auteurs la ON l.id = la.livre_id
                WHERE la.auteur_id = ?
                ORDER BY l.titre;
            """;
            try (PreparedStatement stmt = connection.prepareStatement(booksQuery)) {
                stmt.setInt(1, authorId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("resume"),
                            rs.getInt("annee"),
                            null, // No category
                            null, // No authors
                            rs.getString("format"),
                            null,
                            null,
                            0 // No category ID
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching author's books.");
            return;
        }

        request.setAttribute("books", books);
        request.setAttribute("authorName", authorName);
        request.getRequestDispatcher("listAuthorBooks.jsp").forward(request, response);
    }
}
