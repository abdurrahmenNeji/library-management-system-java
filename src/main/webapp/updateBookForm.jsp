<%@ page import="projet.Book, projet.Category, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Book</title>
    <style>
       
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 40%;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        h1 {
            text-align: center;
            color: #444;
        }
        form label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }
        form input, form select, form textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        form button {
            background-color: #007BFF;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }
        form button:hover {
            background-color: #0056b3;
        }
        .message {
            color: green;
            text-align: center;
        }
        .error {
            color: red;
            text-align: center;
        }
   
    </style>
</head>
<body>
    <div class="container">
        <h1>Update Book</h1>

        <% 
            Book book = (Book) request.getAttribute("book"); 
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            String error = (String) request.getAttribute("error");
        %>

        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>

        <form method="post" action="updateBook">
            <input type="hidden" name="id" value="<%= book.getId() %>" />

            <label for="title">Title</label>
            <input type="text" id="title" name="title" value="<%= book.getTitle() %>" required />

            <label for="summary">Summary</label>
            <textarea id="summary" name="summary"><%= book.getSummary() %></textarea>

            <label for="year">Year</label>
            <input type="number" id="year" name="year" value="<%= book.getYear() %>" required />

            <label for="format">Format</label>
            <input type="text" id="format" name="format" value="<%= book.getFormat() %>" />

            <label for="category">Category</label>
            <select id="category" name="category">
                <option value="">Select a category</option>
                <% if (categories != null) { %>
                    <% for (Category category : categories) { %>
                        <option value="<%= category.getId() %>"
                            <%= (category.getId() == book.getCategoryId()) ? "selected" : "" %>>
                            <%= category.getNom() %>
                        </option>
                    <% } %>
                <% } %>
            </select>

            <button type="submit">Update Book</button>
        </form>

        <p style="text-align: center;">
            <a href="manageBooks">Back to Manage Books</a>
        </p>
    </div>
</body>
</html>
