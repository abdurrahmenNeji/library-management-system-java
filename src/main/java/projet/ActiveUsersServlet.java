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

@WebServlet("/activeUsers")
public class ActiveUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<UserActivity> userActivities = new ArrayList<>();

        String query = """
            SELECT 
                u.id AS user_id, 
                u.nom AS user_name,
                COALESCE(b.borrowed_count, 0) AS borrowed_count,
                COALESCE(d.downloaded_count, 0) AS downloaded_count,
                COALESCE(b.borrowed_count, 0) + COALESCE(d.downloaded_count, 0) AS total_activity
            FROM utilisateurs u
            LEFT JOIN (
                SELECT user_id, COUNT(*) AS borrowed_count
                FROM livres_empruntes
                GROUP BY user_id
            ) b ON u.id = b.user_id
            LEFT JOIN (
                SELECT user_id, COUNT(*) AS downloaded_count
                FROM livres_telecharges
                GROUP BY user_id
            ) d ON u.id = d.user_id
            ORDER BY total_activity DESC;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                userActivities.add(new UserActivity(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getInt("borrowed_count"),
                    rs.getInt("downloaded_count")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching user activities.");
            return;
        }

        request.setAttribute("userActivities", userActivities);
        request.getRequestDispatcher("activeUsers.jsp").forward(request, response);
    }
}
