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

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check for session and userId
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Lists for books
        List<Book> borrowedBooks = new ArrayList<>();
        List<Book> downloadedBooks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch borrowed books
            String borrowedQuery = """
                SELECT l.id, l.titre, l.resume, l.annee, c.nom AS categorie, 
                       STRING_AGG(a.nom, ', ') AS auteurs, l.format, le.date_retour
                FROM livres_empruntes le
                JOIN livres l ON le.book_id = l.id
                LEFT JOIN categories c ON l.categorie_id = c.id
                LEFT JOIN livres_auteurs la ON l.id = la.livre_id
                LEFT JOIN auteurs a ON la.auteur_id = a.id
                WHERE le.user_id = ?
                GROUP BY l.id, l.titre, l.resume, l.annee, c.nom, l.format, le.date_retour;
            """;

            try (PreparedStatement stmt = connection.prepareStatement(borrowedQuery)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        borrowedBooks.add(new Book(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("resume"),
                            rs.getInt("annee"),
                            rs.getString("categorie"),
                            rs.getString("auteurs"),
                            rs.getString("format"),
                            null, // No download date
                            rs.getString("date_retour"),
                            0//categoryId
                        ));
                    }
                }
            }

            // Fetch downloaded books
            String downloadedQuery = """
                SELECT l.id, l.titre, l.resume, l.annee, c.nom AS categorie, 
                       STRING_AGG(a.nom, ', ') AS auteurs, f.format, t.date_telechargement
                FROM livres_telecharges t
                JOIN livres l ON t.book_id = l.id
                LEFT JOIN fichiers_livres f ON l.id = f.livre_id
                LEFT JOIN categories c ON l.categorie_id = c.id
                LEFT JOIN livres_auteurs la ON l.id = la.livre_id
                LEFT JOIN auteurs a ON la.auteur_id = a.id
                WHERE t.user_id = ?
                GROUP BY l.id, l.titre, l.resume, l.annee, c.nom, f.format, t.date_telechargement;
            """;

            try (PreparedStatement stmt = connection.prepareStatement(downloadedQuery)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        downloadedBooks.add(new Book(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("resume"),
                            rs.getInt("annee"),
                            rs.getString("categorie"),
                            rs.getString("auteurs"),
                            rs.getString("format"),
                            rs.getString("date_Telechargement"),
                            null,
                            0//categoryId
                        ));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pass data to JSP
        request.setAttribute("borrowedBooks", borrowedBooks);
        request.setAttribute("downloadedBooks", downloadedBooks);
        request.getRequestDispatcher("userBooks.jsp").forward(request, response);
    }
}
