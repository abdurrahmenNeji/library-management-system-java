package projet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, Integer> borrowedBooks = new LinkedHashMap<>();
        Map<String, Integer> downloadedBooks = new LinkedHashMap<>();
        Map<String, Integer> addedBooks = new LinkedHashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            
            // Borrowed Books Query
            String borrowedQuery = """
                SELECT TO_CHAR(date_emprunt, 'YYYY-MM') AS month, COUNT(*) AS count
                FROM livres_empruntes
                GROUP BY month
                ORDER BY month;
            """;
            try (PreparedStatement stmt = connection.prepareStatement(borrowedQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    borrowedBooks.put(rs.getString("month"), rs.getInt("count"));
                }
            }

            // Downloaded Books Query
            String downloadedQuery = """
                SELECT TO_CHAR(date_telechargement, 'YYYY-MM') AS month, COUNT(*) AS count
                FROM livres_telecharges
                GROUP BY month
                ORDER BY month;
            """;
            try (PreparedStatement stmt = connection.prepareStatement(downloadedQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    downloadedBooks.put(rs.getString("month"), rs.getInt("count"));
                }
            }

            // Added Books Query
            String addedQuery = """
                SELECT TO_CHAR(date_ajout, 'YYYY-MM') AS month, COUNT(*) AS count
                FROM livres_ajoutes
                GROUP BY month
                ORDER BY month;
            """;
            try (PreparedStatement stmt = connection.prepareStatement(addedQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    addedBooks.put(rs.getString("month"), rs.getInt("count"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching dashboard statistics.");
            return;
        }

        // Pass the statistics to the JSP
        request.setAttribute("borrowedBooks", borrowedBooks);
        request.setAttribute("downloadedBooks", downloadedBooks);
        request.setAttribute("addedBooks", addedBooks);

        // Forward to the JSP
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
