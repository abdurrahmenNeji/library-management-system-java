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

@WebServlet("/modifyBook")
public class ModifyBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request to load book details for editing
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("manageBooks"); // Redirect if ID is missing
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageBooks"); // Redirect if ID is invalid
            return;
        }

        Book book = null;
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch book details
            String fetchQuery = "SELECT id, titre, resume, annee, format, categorie_id FROM livres WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(fetchQuery)) {
                stmt.setInt(1, bookId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    book = new Book(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("resume"),
                        rs.getInt("annee"),
                        null, // Category name not needed
                        null, // Authors not needed
                        rs.getString("format"),
                        null,
                        null,
                        rs.getInt("categorie_id") // Set category ID
                    );
                }
            }

            // Fetch all categories for dropdown
            String categoryQuery = "SELECT id, nom FROM categories ORDER BY nom ASC";
            try (PreparedStatement stmt = connection.prepareStatement(categoryQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(new Category(rs.getInt("id"), rs.getString("nom")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching book details or categories.");
            return;
        }

        // Pass the book and categories to the JSP
        request.setAttribute("book", book);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("bookForm.jsp").forward(request, response);
    }

    // Handle POST request to update book details
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("manageBooks");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageBooks");
            return;
        }

        String title = request.getParameter("title");
        String summary = request.getParameter("summary");
        String yearStr = request.getParameter("year");
        String format = request.getParameter("format");
        String categoryIdStr = request.getParameter("category");

        if (title == null || yearStr == null || title.trim().isEmpty() || yearStr.trim().isEmpty()) {
            request.setAttribute("error", "Title and year are required fields.");
            doGet(request, response); // Reload form with error
            return;
        }

        int year, categoryId = 0;
        try {
            year = Integer.parseInt(yearStr);
            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid year or category.");
            doGet(request, response);
            return;
        }

        // Update the book in the database
        String updateQuery = "UPDATE livres SET titre = ?, resume = ?, annee = ?, format = ?, categorie_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setString(1, title);
            stmt.setString(2, summary);
            stmt.setInt(3, year);
            stmt.setString(4, format != null ? format : "Unknown");
            if (categoryId > 0) {
                stmt.setInt(5, categoryId);
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.setInt(6, bookId);

            stmt.executeUpdate();
            response.sendRedirect("manageBooks"); // Redirect back to book management page
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to update the book.");
            doGet(request, response); // Reload form with error
        }
    }
}
