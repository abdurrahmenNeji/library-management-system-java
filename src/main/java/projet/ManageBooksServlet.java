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

/**
 * Servlet implementation class ManageBooksServlet
 */
@WebServlet("/manageBooks")
public class ManageBooksServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        
        String query = "SELECT l.id, l.titre, l.annee, l.format FROM livres l ORDER BY l.id";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"), 
                    rs.getString("titre"), 
                    null,  // No summary
                    rs.getInt("annee"),
                    null,  // No category
                    null,  // No authors
                    rs.getString("format"),
                    null,
                    null,
                    0//categoryId
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching books.");
            return;
        }
        
        request.setAttribute("books", books);
        request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
    }
}
