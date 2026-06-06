<%@ page import="projet.Book" %>
<%
    Book book = (Book) request.getAttribute("book");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= (book != null ? book.getTitle() : "Book Not Found") %> - Details</title>
    <!-- Font Awesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            color: white;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-right: 20px;
            font-size: 1rem;
            transition: color 0.3s ease;
        }

        .navbar a:hover {
            color: #007BFF;
        }

        /* Container */
        .container {
            max-width: 900px;
            margin: 30px auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            animation: fadeIn 0.8s ease-in-out;
        }

        h1 {
            text-align: center;
            color: #004080;
            margin-bottom: 20px;
        }

        /* Error and Success Messages */
        .error-message, .success-message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
        }

        .error-message {
            background-color: #FFD2D2;
            color: #D8000C;
            border: 1px solid #D8000C;
        }

        .success-message {
            background-color: #DFF2BF;
            color: #4F8A10;
            border: 1px solid #4F8A10;
        }

        /* Book Details */
        .book-details p {
            font-size: 1.1rem;
            margin: 10px 0;
        }

        .book-details strong {
            color: #007BFF;
        }

        /* Action Buttons */
        .actions {
            text-align: center;
            margin: 20px 0;
        }

        .actions button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
            transition: background-color 0.3s, transform 0.2s;
        }

        .actions button:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }

        /* Links */
        .back-link, .inline-link {
            text-align: center;
            display: block;
            margin: 10px 0;
            font-weight: bold;
            color: #007BFF;
            text-decoration: none;
            transition: color 0.3s;
        }

        .back-link:hover, .inline-link:hover {
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
        <div style="font-size: 1.5rem; font-weight: bold;"> Library Management</div>
        <div>
            <a href="books"><i class="fa fa-home"></i> Home</a>
            <a href="borrowedBooks"><i class="fa fa-book"></i> Borrowed Books</a>
            <a href="logout"><i class="fa fa-sign-out"></i> Logout</a>
        </div>
    </div>

    <!-- Book Details Container -->
    <div class="container">
        <% if (book == null) { %>
            <div class="error-message">
                The requested book details could not be found. Please try again later.
            </div>
            <a href="books" class="back-link">Return to Homepage</a>
        <% return; } %>

        <!-- Success/Error Messages -->
        <% if (error != null) { %>
            <div class="error-message">
                <% if ("already_borrowed".equals(error)) { %>
                    This book has already been borrowed.
                <% } else if ("cannot_download".equals(error)) { %>
                    You are not authorized to download this book.
                <% } else { %>
                    An unexpected error occurred. Please try again.
                <% } %>
            </div>
        <% } %>

        <% String success = (String) request.getAttribute("success"); %>
        <% if ("borrowed".equals(success)) { %>
            <div class="success-message">
                You have successfully borrowed this book!
            </div>
        <% } %>

        <!-- Book Details -->
        <h1><%= book.getTitle() %></h1>
        <div class="book-details">
            <p><strong>Author(s):</strong> <%= book.getAuthors() != null ? book.getAuthors() : "No authors" %></p>
            <p><strong>Category:</strong> <%= book.getCategory() != null ? book.getCategory() : "Unknown" %></p>
            <p><strong>Year:</strong> <%= book.getYear() %></p>
            <p><strong>Description:</strong> <%= book.getSummary() %></p>
        </div>

        <!-- Action Buttons -->
		<div class="actions" style="display: flex; justify-content: center; gap: 15px; margin-top: 20px;">
		    <form method="post" action="addToFavorites" style="margin: 0;">
		        <input type="hidden" name="bookId" value="<%= book.getId() %>">
		        <button type="submit"><i class="fa fa-heart"></i> Add to Favorites</button>
		    </form>
		
		    <form method="get" action="downloadBook" style="margin: 0;">
		        <input type="hidden" name="bookId" value="<%= book.getId() %>">
		        <button type="submit"><i class="fa fa-download"></i> Download</button>
		    </form>
		
		    <form method="post" action="borrowBook" style="margin: 0;">
		        <input type="hidden" name="bookId" value="<%= book.getId() %>">
		        <button type="submit"><i class="fa fa-book"></i> Borrow</button>
		    </form>
		</div>


        <!-- Back Link -->
        <a href="books" class="back-link"><i class="fa fa-arrow-left"></i> Return to Homepage</a>
    </div>
</body>
</html>
