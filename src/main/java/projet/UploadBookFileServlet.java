package projet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/uploadBookFile")
@MultipartConfig // Enables handling file uploads
public class UploadBookFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int livreId = Integer.parseInt(request.getParameter("livreId")); // Selected book ID
        Part filePart = request.getPart("file"); // Uploaded file
        String fileName = System.currentTimeMillis() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Generate unique name
        String filePath = "D:/uploadedBooks/" + fileName; // Path for the uploaded file

        boolean success = false; // Track success status

        try (InputStream inputStream = filePart.getInputStream()) {
            Path path = Paths.get(filePath);

            // Attempt to delete the existing file (if it exists)
            try {
                if (Files.exists(path)) {
                    Files.deleteIfExists(path); // Try to delete without throwing unhandled errors
                }
            } catch (FileSystemException e) {
                // Log the error and stop processing if the file is locked
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_CONFLICT, "The file is currently in use by another process. Please try again later.");
                return;
            }

            // Copy the new file
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            // Insert file details into the database
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO fichiers_livres (livre_id, chemin_fichier, nom_fichier, format) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, livreId);
                stmt.setString(2, filePath);
                stmt.setString(3, fileName);
                stmt.setString(4, fileName.substring(fileName.lastIndexOf('.') + 1)); // Extract file format
                stmt.executeUpdate();

                success = true; // Set success flag
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while saving the file.");
            return;
        }

        // Redirect only if successful
        if (success) {
            response.sendRedirect("adminDashboard.jsp");
        }
    }
}
