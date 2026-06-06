package projet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/borrowBook")
public class BorrowBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Book getBookDetails(int bookId, Connection connection) throws SQLException {
        String query = """
            SELECT l.id, l.titre, l.resume, l.annee, c.nom AS categorie, 
                   STRING_AGG(a.nom, ', ') AS auteurs, l.format
            FROM livres l
            LEFT JOIN categories c ON l.categorie_id = c.id
            LEFT JOIN livres_auteurs la ON l.id = la.livre_id
            LEFT JOIN auteurs a ON la.auteur_id = a.id
            WHERE l.id = ?
            GROUP BY l.id, l.titre, l.resume, l.annee, c.nom, l.format;
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("resume"),
                    rs.getInt("annee"),
                    rs.getString("categorie"),  // Category
                    rs.getString("auteurs"),    // Authors
                    rs.getString("format"),      // Format
                    null,
                    null,
                    0//categoryId
                	);
            }
        }
        return null;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        String bookIdParam = request.getParameter("bookId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required.");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Book ID format.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if the user has already borrowed the book
        	String checkQuery = """
        		    SELECT id FROM livres_empruntes
        		    WHERE user_id = ? AND book_id = ? AND date_retour >= CURRENT_DATE;
        		""";
        		try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
        		    checkStmt.setInt(1, userId);
        		    checkStmt.setInt(2, bookId);
        		    ResultSet rs = checkStmt.executeQuery();
        		    if (rs.next()) {
        		        // User already borrowed this book
        		        request.setAttribute("error", "already_borrowed");
        		        request.setAttribute("book", getBookDetails(bookId, connection)); // Fetch book details
        		        request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
        		        return;
        		    }
        		}

            // Insert the new borrowing record
            String insertQuery = """
                INSERT INTO livres_empruntes (user_id, book_id, date_emprunt)
                VALUES (?, ?, ?);
            """;
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    // Borrow successful
                    request.setAttribute("success", "borrowed");
                    request.setAttribute("book", getBookDetails(bookId, connection)); // Fetch book details
                    request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
                    return;
                

                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to borrow the book.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A database error occurred.");
        }
    }
}