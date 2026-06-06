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
 * Servlet implementation class AddBookFilePageServlet
 */
 @WebServlet("/addBookFilePage")
 public class AddBookFilePageServlet extends HttpServlet {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            // Query to get all books
	            String query = "SELECT id, titre, resume, annee, format, null AS auteurs, null AS categorie FROM livres";
	            PreparedStatement stmt = connection.prepareStatement(query);
	            ResultSet rs = stmt.executeQuery();

	            List<Book> books = new ArrayList<>();
	            while (rs.next()) {
	                Book book = new Book(
	                    rs.getInt("id"),
	                    rs.getString("titre"),
	                    rs.getString("resume"),
	                    rs.getInt("annee"),
	                    rs.getString("categorie"),
	                    rs.getString("auteurs"),
	                    rs.getString("format"),
	                    null,
	                    null,
	                    0//categoryId
	                );
	                books.add(book);
	            }

	            // Set books as a request attribute
	            request.setAttribute("books", books);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving books.");
	            return;
	        }

	        // Forward to the JSP
	        request.getRequestDispatcher("addBookFile.jsp").forward(request, response);
	    }
	}
