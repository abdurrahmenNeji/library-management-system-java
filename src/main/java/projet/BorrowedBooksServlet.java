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
import javax.servlet.http.HttpSession;

@WebServlet("/borrowedBooks")
public class BorrowedBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        
        // SQL Query to get borrowed books with return date
        String query = """
            SELECT l.id, l.titre, le.date_emprunt, le.date_retour
            FROM livres_empruntes le
            JOIN livres l ON le.book_id = l.id
            WHERE le.user_id = ?;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                borrowedBooks.add(new BorrowedBook(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getDate("date_emprunt"),
                    rs.getDate("date_retour")
                ));
            }

            request.setAttribute("borrowedBooks", borrowedBooks);
            request.getRequestDispatcher("borrowedBooks.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A database error occurred.");
        }
    }
}
