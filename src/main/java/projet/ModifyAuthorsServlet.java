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

@WebServlet("/modifyAuthor")
public class ModifyAuthorsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request to load author details for editing
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("manageAuthors"); // Redirect if ID is missing
            return;
        }

        int authorId;
        try {
            authorId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageAuthors"); // Redirect if ID is invalid
            return;
        }

        Author author = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch author details
            String fetchQuery = "SELECT id, nom, date_de_naissance FROM auteurs WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(fetchQuery)) {
                stmt.setInt(1, authorId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    author = new Author(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDate("date_de_naissance").toString()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching author details.");
            return;
        }

        // Pass the author to the JSP
        request.setAttribute("author", author);
        request.getRequestDispatcher("updateAuthor.jsp").forward(request, response);
    }

    // Handle POST request to update author details
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("manageAuthors");
            return;
        }

        int authorId;
        try {
            authorId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageAuthors");
            return;
        }

        String name = request.getParameter("name");
        String dob = request.getParameter("dob");

        if (name == null || dob == null || name.trim().isEmpty() || dob.trim().isEmpty()) {
            request.setAttribute("error", "Name and Date of Birth are required fields.");
            doGet(request, response); // Reload form with error
            return;
        }

        try {
            // Parse the date
            java.sql.Date parsedDob = java.sql.Date.valueOf(dob);

            // Update the author in the database
            String updateQuery = "UPDATE auteurs SET nom = ?, date_de_naissance = ? WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

                stmt.setString(1, name);
                stmt.setDate(2, parsedDob);
                stmt.setInt(3, authorId);

                stmt.executeUpdate();
                response.sendRedirect("manageAuthors"); // Redirect back to author management page
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format. Use YYYY-MM-DD.");
            doGet(request, response); // Reload form with error
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to update the author.");
            doGet(request, response); // Reload form with error
        }
    }
}
