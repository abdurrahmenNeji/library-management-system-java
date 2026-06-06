<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("category") != null ? "Modify a Category" : "Add a Category" %></title>
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
            width: 45%;
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

        /* Form Styling */
        form label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #555;
        }

        form input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
            box-sizing: border-box;
        }

        form button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            font-weight: bold;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
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

        /* Messages */
        .message {
            color: green;
            text-align: center;
            font-weight: bold;
        }

        .error {
            color: red;
            text-align: center;
            font-weight: bold;
        }

        /* Back Link */
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
    <!-- Top Navigation Bar -->
    <div class="navbar">
        <a href="manageCategories">&#8592; Back to Manage Categories</a>
    </div>

    <!-- Form Container -->
    <div class="container">
        <h1><%= request.getAttribute("category") != null ? "Modify a Category" : "Add a Category" %></h1>

        <% 
            projet.Category category = (projet.Category) request.getAttribute("category");
            String error = (String) request.getAttribute("error");
            String success = (String) request.getAttribute("success");
        %>

        <!-- Error Message -->
        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>

        <!-- Success Message -->
        <% if (success != null) { %>
            <p class="message"><%= success %></p>
        <% } %>

        <!-- Form -->
        <form method="post" action="<%= category != null ? "modifyCategory" : "addCategory" %>">
            <% if (category != null) { %>
                <input type="hidden" name="id" value="<%= category.getId() %>" />
            <% } %>

            <label for="name">Category Name</label>
            <input type="text" id="name" name="name" placeholder="Enter the category name" 
                   value="<%= category != null ? category.getNom() : "" %>" required />

            <button type="submit"><%= category != null ? "Update Category" : "Add Category" %></button>
        </form>
    </div>
</body>
</html>
