package projet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addBook")
public class AddBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request: Fetch categories and display the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();
        String error = null;

        // Fetch categories from the database
        String categoryQuery = "SELECT id, nom FROM categories ORDER BY nom ASC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(categoryQuery);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("nom")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error = "Error fetching categories.";
            request.setAttribute("error", error); // Pass error to JSP
        }

        // Pass categories list and any error message to JSP
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("bookForm.jsp").forward(request, response);
    }

    // Handle POST request: Insert book into the database
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String title = request.getParameter("title");
        String summary = request.getParameter("summary");
        String yearStr = request.getParameter("year");
        String format = request.getParameter("format");
        String categoryIdStr = request.getParameter("category");

        // Validation
        if (title == null || title.trim().isEmpty() || yearStr == null || yearStr.trim().isEmpty()) {
            request.setAttribute("error", "Title and year are required fields.");
            doGet(request, response); // Reload form with error
            return;
        }

        int year;
        int categoryId = 0;
        int generatedBookId = 0; // To store the ID of the newly inserted book
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

        // Insert book into the database
        String insertQuery = "INSERT INTO livres (titre, resume, annee, format, categorie_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, title);
            stmt.setString(2, summary);
            stmt.setInt(3, year);
            stmt.setString(4, format != null ? format : "Unknown");
            if (categoryId > 0) {
                stmt.setInt(5, categoryId);
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedBookId = generatedKeys.getInt(1); // Get the auto-generated ID
                }

                // Insert into 'livres_ajoutes' table
                String insertAjoutQuery = "INSERT INTO livres_ajoutes (book_id) VALUES (?)";
                try (PreparedStatement ajoutStmt = connection.prepareStatement(insertAjoutQuery)) {
                    ajoutStmt.setInt(1, generatedBookId);
                    ajoutStmt.executeUpdate();
                }

                request.setAttribute("success", "Book added successfully!");
            } else {
                request.setAttribute("error", "Failed to add the book.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
        }

        // Reload the form with success or error message
        doGet(request, response);
    }
}
