package projet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/downloadBook")
public class DownloadBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DownloadBookServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize variables to prevent compile-time errors
        String userIdParam = request.getParameter("userId");
        String bookIdParam = request.getParameter("bookId");
        int userId = -1;
        int bookId = -1;

        // Validate parameters
        try {
            userId = Integer.parseInt(userIdParam);
            bookId = Integer.parseInt(bookIdParam);
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid parameters: userId=" + userIdParam + ", bookId=" + bookIdParam);
            response.sendRedirect("bookDetails?id=" + bookIdParam + "&error=invalid_parameters");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to check download permissions
            String query = """
                SELECT fl.chemin_fichier, fl.nom_fichier 
                FROM livres_empruntes le
                JOIN fichiers_livres fl ON le.book_ID = fl.livre_id
                WHERE le.user_ID = ? AND le.book_ID = ? AND le.date_retour >= NOW();
            """;

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, bookId);

                try (ResultSet rs = stmt.executeQuery()) {
                	// Inside the ResultSet check, after serving the file:
                	if (rs.next()) {
                	    // Get file details
                	    String filePath = rs.getString("chemin_fichier");
                	    String fileName = rs.getString("nom_fichier");

                	    // Check if the file exists
                	    Path path = Paths.get(filePath);
                	    if (!Files.exists(path)) {
                	        LOGGER.warning("File not found: " + filePath);
                	        response.sendRedirect("bookDetails?id=" + bookId + "&error=file_not_found");
                	        return;
                	    }

                	    // Log the download into livres_telecharges table
                	    String logDownloadQuery = """
                	        INSERT INTO livres_telecharges (user_id, book_id, date_telechargement) 
                	        VALUES (?, ?, NOW());
                	    """;
                	    try (PreparedStatement logStmt = connection.prepareStatement(logDownloadQuery)) {
                	        logStmt.setInt(1, userId);
                	        logStmt.setInt(2, bookId);
                	        logStmt.executeUpdate();
                	    } catch (SQLException e) {
                	        LOGGER.warning("Failed to log download: " + e.getMessage());
                	    }

                	    // Serve the file for download
                	    response.setContentType("application/octet-stream");
                	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                	    try (OutputStream out = response.getOutputStream()) {
                	        Files.copy(path, out);
                	    }
                	}
                	else {
                        // User does not have permission or the book is not associated
                        LOGGER.warning("User does not have permission to download the book: userId=" + userId + ", bookId=" + bookId);
                        response.sendRedirect("bookDetails?id=" + bookId + "&error=cannot_download");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Database error: " + e.getMessage());
            response.sendRedirect("bookDetails?id=" + bookId + "&error=database_error");
        }
    }
}
