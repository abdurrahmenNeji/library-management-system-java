<%@ page import="java.util.List, projet.Category" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Categories</title>
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

        /* Container */
        .container {
            width: 85%;
            margin: 30px auto;
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

        h2 {
            color: #007BFF;
            font-size: 1.5rem;
            margin-top: 30px;
        }

        /* Buttons */
        .button-container {
            text-align: center;
            margin-bottom: 20px;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            font-size: 1rem;
            color: #fff;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
            transition: background-color 0.3s, transform 0.2s;
        }

        .btn:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }

        /* Category List */
        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        li {
            padding: 15px;
            margin-bottom: 15px;
            background-color: #f8f9ff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: box-shadow 0.3s, transform 0.2s;
        }

        li:hover {
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            transform: translateY(-5px);
        }

        li strong {
            font-size: 1.2rem;
            color: #333;
        }

        /* Action Buttons */
        .actions {
            margin-top: 10px;
        }

        .action-btn {
            display: inline-block;
            padding: 6px 12px;
            margin-right: 10px;
            font-size: 0.9rem;
            font-weight: bold;
            text-decoration: none;
            border-radius: 5px;
            color: white;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s, transform 0.2s;
        }

        .action-btn.modify {
            background-color: #007BFF;
        }

        .action-btn.delete {
            background-color: #dc3545;
        }

        .action-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .action-btn.modify:hover {
            background-color: #0056b3;
        }

        .action-btn.delete:hover {
            background-color: #c82333;
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

    <!-- Main Content -->
    <div class="container">
        <h1>Manage Categories</h1>
        
        <!-- Button for Adding a Category -->
        <div class="button-container">
            <a href="addCategory" class="btn">Add Category</a>
        </div>

        <!-- List of Categories -->
        <h2>Categories:</h2>
        <ul>
            <% 
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            if (categories != null && !categories.isEmpty()) {
                for (Category category : categories) { %>
                    <li>
                        <strong><%= category.getNom() %></strong>
                        <div class="actions">
                            <a href="modifyCategory?id=<%= category.getId() %>" class="action-btn modify">Modify</a>
                            <a href="deleteCategory?id=<%= category.getId() %>" class="action-btn delete" 
                               onclick="return confirm('Are you sure you want to delete this category?');">Delete</a>
                        </div>
                    </li>
            <% } } else { %>
                <li>No categories available.</li>
            <% } %>
        </ul>
    </div>
</body>
</html>
