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

@WebServlet("/assignAuthors")
public class AssignAuthorsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Load categories and authors
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch all categories
            String categoryQuery = "SELECT id, nom FROM categories ORDER BY nom ASC";
            try (PreparedStatement stmt = connection.prepareStatement(categoryQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(new Category(rs.getInt("id"), rs.getString("nom")));
                }
            }

            // Fetch all authors
            String authorQuery = "SELECT id, nom FROM auteurs ORDER BY nom ASC";
            try (PreparedStatement stmt = connection.prepareStatement(authorQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    authors.add(new Author(rs.getInt("id"), rs.getString("nom"), null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading data.");
            return;
        }

        // Pass data to JSP
        request.setAttribute("categories", categories);
        request.setAttribute("authors", authors);
        request.getRequestDispatcher("assignAuthors.jsp").forward(request, response);
    }

    // Save selected authors and category
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookIdStr = request.getParameter("bookId");
        String categoryIdStr = request.getParameter("category");
        String[] authorIds = request.getParameterValues("authors");

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            response.sendRedirect("manageBooks");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Update the category in the 'livres' table
            String updateCategoryQuery = "UPDATE livres SET categorie_id = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateCategoryQuery)) {
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    stmt.setInt(1, Integer.parseInt(categoryIdStr));
                } else {
                    stmt.setNull(1, java.sql.Types.INTEGER);
                }
                stmt.setInt(2, bookId);
                stmt.executeUpdate();
            }

            // Delete existing authors for the book
            String deleteAuthorsQuery = "DELETE FROM livres_auteurs WHERE livre_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteAuthorsQuery)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            // Insert selected authors
            if (authorIds != null) {
                String insertAuthorsQuery = "INSERT INTO livres_auteurs (livre_id, auteur_id) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(insertAuthorsQuery)) {
                    for (String authorId : authorIds) {
                        stmt.setInt(1, bookId);
                        stmt.setInt(2, Integer.parseInt(authorId));
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating book authors.");
            return;
        }

        response.sendRedirect("manageBooks"); // Redirect back
    }
}
