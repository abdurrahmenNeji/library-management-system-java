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

@WebServlet("/manageLoans")
public class ManageLoansServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Loan> loans = new ArrayList<>();

        String query = """
            SELECT le.id, u.nom AS user_name, l.titre AS book_title, le.date_emprunt, le.date_retour
            FROM livres_empruntes le
            JOIN utilisateurs u ON le.user_id = u.id
            JOIN livres l ON le.book_id = l.id
            WHERE le.date_retour >= CURRENT_DATE
            ORDER BY le.date_retour ASC;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("book_title"),
                    rs.getDate("date_emprunt").toString(),
                    rs.getDate("date_retour").toString()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching loans.");
            return;
        }

        request.setAttribute("loans", loans);
        request.getRequestDispatcher("manageLoans.jsp").forward(request, response);
    }
}
