<%@ page import="java.util.List, projet.Book" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Favorite Books</title>
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
            color: white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .navbar a {
            color: white;
            text-decoration: none;
            font-size: 1rem;
            font-weight: bold;
            margin-left: 20px;
            transition: color 0.3s ease;
        }

        .navbar a:hover {
            color: #007BFF;
        }

        /* Page Title */
        h1 {
            text-align: center;
            color: #004080;
            margin: 30px 0;
        }

        /* Book List */
        .book-list {
            max-width: 900px;
            margin: 20px auto;
            list-style-type: none;
            padding: 0;
        }

        .book-item {
            background-color: #fff;
            margin-bottom: 20px;
            padding: 15px 20px;
            border-radius: 10px;
            border: 1px solid #ddd;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            transition: box-shadow 0.3s, transform 0.2s;
        }

        .book-item:hover {
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            transform: translateY(-3px);
        }

        .book-item strong {
            color: #007BFF;
            font-size: 1.2rem;
        }

        .book-item em {
            color: #555;
            display: block;
            margin: 5px 0;
        }

        /* Remove Button */
        .remove-button {
            background-color: #DC3545;
            color: white;
            border: none;
            padding: 8px 12px;
            font-size: 1rem;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s;
        }

        .remove-button:hover {
            background-color: #BD2130;
            transform: translateY(-2px);
        }

        /* No Favorites Message */
        .no-books {
            text-align: center;
            font-size: 1.2rem;
            color: #777;
        }

        /* Back Link */
        .back-link {
            display: block;
            text-align: center;
            margin: 20px 0;
            font-size: 1rem;
            font-weight: bold;
            color: #007BFF;
            text-decoration: none;
            transition: color 0.3s;
        }

        .back-link:hover {
            color: #0056b3;
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <!-- Top Navigation Bar -->
    <div class="navbar">
        <div style="font-size: 1.5rem; font-weight: bold;">Library Management</div>
        <div>
            <a href="books"><i class="fa fa-home"></i> Home</a>
            <a href="borrowedBooks"><i class="fa fa-book"></i> Borrowed Books</a>
            <a href="logout"><i class="fa fa-sign-out"></i> Logout</a>
        </div>
    </div>

    <!-- Page Title -->
    <h1>Your Favorite Books</h1>

    <!-- Book List -->
    <ul class="book-list">
        <%
            List<projet.Book> favoriteBooks = (List<projet.Book>) request.getAttribute("favoriteBooks");
            if (favoriteBooks != null && !favoriteBooks.isEmpty()) {
                for (projet.Book book : favoriteBooks) {
        %>
            <li class="book-item">
                <strong><%= book.getTitle() %></strong>
                <em>Author(s): <%= book.getAuthors() != null ? book.getAuthors() : "No authors" %></em>
                <em>Category: <%= book.getCategory() != null ? book.getCategory() : "Unknown" %></em>
                <em>Year: <%= book.getYear() %> | Format: <%= book.getFormat() %></em>
                <form method="post" action="removeFromFavorites" style="margin-top: 10px;">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button type="submit" class="remove-button">
                        <i class="fa fa-trash"></i> Remove from Favorites
                    </button>
                </form>
            </li>
        <%
                }
            } else {
        %>
            <p class="no-books">No favorite books yet.</p>
        <%
            }
        %>
    </ul>

    <!-- Back Link -->
    <a href="books" class="back-link"><i class="fa fa-arrow-left"></i> Return to Homepage</a>
</body>
</html>
