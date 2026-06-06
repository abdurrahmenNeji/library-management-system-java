<%@ page import="java.util.List, projet.Category, projet.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("book") != null ? "Modify Book" : "Add Book" %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f8;
            margin: 0;
            padding: 0;
            color: #333;
        }

        /* Navigation Bar */
        .navbar {
            background-color: #004080;
            padding: 15px 30px;
            display: flex;
            align-items: center;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .navbar a {
            color: white;
            text-decoration: none;
            font-size: 1.2rem;
            font-weight: bold;
            transition: color 0.3s ease-in-out;
        }

        .navbar a:hover {
            color: #007BFF;
        }

        /* Form Container */
        .container {
            width: 50%;
            margin: 50px auto;
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            animation: fadeIn 0.8s ease-in-out;
        }

        h1 {
            text-align: center;
            color: #004080;
            font-size: 2rem;
            margin-bottom: 20px;
        }

        form label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #555;
        }

        form input, form select, form textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1rem;
        }

        form textarea {
            resize: vertical;
            min-height: 100px;
        }

        form button {
            background-color: #007BFF;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            width: 100%;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s, transform 0.2s;
        }

        form button:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }

        form button:active {
            background-color: #004085;
            transform: translateY(0);
        }

        /* Links */
        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            font-weight: bold;
            color: #007BFF;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .back-link:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        /* Fade-in Animation */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <div class="navbar">
        <a href="adminDashboard.jsp">&#8592; Back to Admin Dashboard</a>
    </div>

    <!-- Form Container -->
    <div class="container">
        <h1><%= request.getAttribute("book") != null ? "Modify a Book" : "Add a Book" %></h1>

        <%
            Book book = (Book) request.getAttribute("book");
            List<Category> categories = (List<Category>) request.getAttribute("categories");
        %>

        <form method="post" action="<%= book != null ? "modifyBook" : "addBook" %>">
            <% if (book != null) { %>
                <input type="hidden" name="id" value="<%= book.getId() %>" />
            <% } %>

            <label for="title">Title</label>
            <input type="text" id="title" name="title" placeholder="Enter the book title" value="<%= book != null ? book.getTitle() : "" %>" required />

            <label for="summary">Summary</label>
            <textarea id="summary" name="summary" placeholder="Enter a brief summary"><%= book != null ? book.getSummary() : "" %></textarea>

            <label for="year">Year</label>
            <input type="number" id="year" name="year" placeholder="Enter the publication year" value="<%= book != null ? book.getYear() : "" %>" required />

            <label for="format">Format</label>
            <input type="text" id="format" name="format" placeholder="e.g., PDF, EPUB" value="<%= book != null ? book.getFormat() : "" %>" />

            <label for="category">Category</label>
            <select id="category" name="category">
                <option value="">Select a category (optional)</option>
                <% if (categories != null) { %>
                    <% for (Category category : categories) { %>
                        <option value="<%= category.getId() %>"
                            <%= (book != null && category.getId() == book.getCategoryId()) ? "selected" : "" %>>
                            <%= category.getNom() %>
                        </option>
                    <% } %>
                <% } %>
            </select>

            <button type="submit"><%= book != null ? "Update Book" : "Add Book" %></button>
        </form>

       
    </div>
</body>
</html>
