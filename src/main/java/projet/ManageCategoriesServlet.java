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

@WebServlet("/manageCategories")
public class ManageCategoriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();

        // SQL query to fetch all categories
        String query = "SELECT id, nom FROM categories ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Populate the categories list
            while (rs.next()) {
                categories.add(new Category(
                    rs.getInt("id"),
                    rs.getString("nom")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching categories.");
            return;
        }

        // Set the categories list as a request attribute and forward to JSP
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("manageCategories.jsp").forward(request, response);
    }
}
