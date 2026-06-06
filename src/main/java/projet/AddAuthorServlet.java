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
import java.sql.Date;

@WebServlet("/addAuthor")
public class AddAuthorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request: Display the author form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addAuthor.jsp").forward(request, response);
    }

    // Handle POST request: Insert author into the database
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");

        if (name == null || name.trim().isEmpty() || dob == null || dob.trim().isEmpty()) {
            request.setAttribute("error", "Name and Date of Birth are required fields.");
            doGet(request, response);
            return;
        }

        try {
            Date parsedDob = Date.valueOf(dob); // Convert String to java.sql.Date

            String insertQuery = "INSERT INTO auteurs (nom, date_de_naissance) VALUES (?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(insertQuery)) {

                stmt.setString(1, name);
                stmt.setDate(2, parsedDob); // Use parsed java.sql.Date

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    request.setAttribute("success", "Author added successfully!");
                } else {
                    request.setAttribute("error", "Failed to add the author.");
                }
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
        }

        doGet(request, response);
    }
}
