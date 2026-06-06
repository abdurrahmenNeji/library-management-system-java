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

@WebServlet("/modifyCategory")
public class ModifyCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request to load category details for editing
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("manageCategories"); // Redirect if ID is missing
            return;
        }

        int categoryId;
        try {
            categoryId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCategories"); // Redirect if ID is invalid
            return;
        }

        Category category = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch category details
            String fetchQuery = "SELECT id, nom FROM categories WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(fetchQuery)) {
                stmt.setInt(1, categoryId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    category = new Category(
                        rs.getInt("id"),
                        rs.getString("nom")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching category details.");
            return;
        }

        // Pass the category to the JSP
        request.setAttribute("category", category);
        request.getRequestDispatcher("categoryForm.jsp").forward(request, response);
    }

    // Handle POST request to update category details
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("manageCategories");
            return;
        }

        int categoryId;
        try {
            categoryId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCategories");
            return;
        }

        String name = request.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Category name is required.");
            doGet(request, response); // Reload form with error
            return;
        }

        // Update the category in the database
        String updateQuery = "UPDATE categories SET nom = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setString(1, name);
            stmt.setInt(2, categoryId);

            stmt.executeUpdate();
            response.sendRedirect("manageCategories"); // Redirect back to category management page
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to update the category.");
            doGet(request, response); // Reload form with error
        }
    }
}
