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

@WebServlet("/addCategory")
public class AddCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request: Display the category form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("categoryForm.jsp").forward(request, response);
    }

    // Handle POST request: Insert category into the database
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");

        // Validation: Check if the name is empty
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Category name is required.");
            doGet(request, response);
            return;
        }

        String insertQuery = "INSERT INTO categories (nom) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertQuery)) {

            stmt.setString(1, name);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                request.setAttribute("success", "Category added successfully!");
            } else {
                request.setAttribute("error", "Failed to add the category.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
        }

        doGet(request, response);
    }
}
