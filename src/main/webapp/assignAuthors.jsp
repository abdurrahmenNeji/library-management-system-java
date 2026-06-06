<%@ page import="java.util.List, projet.Category, projet.Author" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Authors and Category</title>
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

        form select, form button {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
            box-sizing: border-box;
        }

        form select {
            background-color: #f8f9fa;
            color: #333;
            cursor: pointer;
        }

        form button {
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
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
    <!-- Top Navigation Bar -->
    <div class="navbar">
        <a href="adminDashboard.jsp">&#8592; Back to Admin Dashboard</a>
    </div>

    <!-- Form Container -->
    <div class="container">
        <h1>Assign Authors and Category</h1>

        <form method="post" action="assignAuthors">
            <!-- Hidden Book ID -->
            <input type="hidden" name="bookId" value="<%= request.getParameter("bookId") %>" />

            <!-- Category Selection -->
            <label for="category">Category</label>
            <select id="category" name="category">
                <option value="">Select a category</option>
                <% 
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category category : categories) { 
                %>
                    <option value="<%= category.getId() %>"><%= category.getNom() %></option>
                <% 
                        }
                    }
                %>
            </select>

            <!-- Multi-select Authors -->
            <label for="authors">Authors</label>
            <select id="authors" name="authors" multiple size="5">
                <% 
                    List<Author> authors = (List<Author>) request.getAttribute("authors");
                    if (authors != null) {
                        for (Author author : authors) { 
                %>
                    <option value="<%= author.getId() %>"><%= author.getNom() %></option>
                <% 
                        }
                    }
                %>
            </select>

            <!-- Submit Button -->
            <button type="submit">Assign</button>
        </form>
    </div>
</body>
</html>
