<%@ page import="projet.Book" %>
<%@ page import="java.util.List" %>
<%
    List<Book> borrowedBooks = (List<Book>) request.getAttribute("borrowedBooks");
    List<Book> downloadedBooks = (List<Book>) request.getAttribute("downloadedBooks");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <!-- Font Awesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        /* General Styles */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f4f8;
            color: #333;
        }

        h1 {
            text-align: center;
            margin: 30px 0;
            font-size: 2rem;
            color: #004080;
        }

        h2 {
            text-align: center;
            color: #007BFF;
            margin-bottom: 20px;
        }

        /* Book List Container */
        .book-list {
            max-width: 800px;
            margin: 20px auto;
            background: #fff;
            border-radius: 10px;
            padding: 20px 25px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            animation: fadeIn 1s ease-in-out;
        }

        .book {
            border-bottom: 1px solid #ddd;
            padding: 15px 0;
            display: flex;
            flex-direction: column;
            gap: 5px;
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        }

        .book:last-child {
            border-bottom: none;
        }

        .book:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 10px rgba(0, 123, 255, 0.2);
        }

        .book p {
            margin: 5px 0;
            font-size: 1rem;
        }

        .book strong {
            color: #004080;
        }

        /* Empty State Message */
        .no-data {
            text-align: center;
            color: #999;
            font-style: italic;
            margin: 20px 0;
        }

        /* Back to Homepage Link */
        .back-link {
            display: block;
            text-align: center;
            margin: 20px 0;
            font-weight: bold;
            color: #007BFF;
            text-decoration: none;
            transition: color 0.3s;
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

        /* Responsive Adjustments */
        @media (max-width: 768px) {
            .book-list {
                width: 95%;
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    <!-- Page Header -->
    <h1><i class="fa fa-user-circle"></i> User Profile</h1>

    <!-- Borrowed Books -->
    <div class="book-list">
        <h2><i class="fa fa-book"></i> Borrowed Books</h2>
        <% if (borrowedBooks != null && !borrowedBooks.isEmpty()) { %>
            <% for (Book book : borrowedBooks) { %>
                <div class="book">
                    <p><strong>Title:</strong> <%= book.getTitle() %></p>
                    <p><strong>Category:</strong> <%= book.getCategory() %></p>
                    <p><strong>Return Date:</strong> <%= book.getDateRetour() %></p>
                </div>
            <% } %>
        <% } else { %>
            <p class="no-data">You have no borrowed books at the moment.</p>
        <% } %>
    </div>

    <!-- Downloaded Books -->
    <div class="book-list">
        <h2><i class="fa fa-download"></i> Downloaded Books</h2>
        <% if (downloadedBooks != null && !downloadedBooks.isEmpty()) { %>
            <% for (Book book : downloadedBooks) { %>
                <div class="book">
                    <p><strong>Title:</strong> <%= book.getTitle() %></p>
                    <p><strong>Category:</strong> <%= book.getCategory() %></p>
                    <p><strong>Download Date:</strong> <%= book.getDateTelechargement() %></p>
                </div>
            <% } %>
        <% } else { %>
            <p class="no-data">You have not downloaded any books yet.</p>
        <% } %>
    </div>

    <!-- Back to Homepage Link -->
    <a href="books" class="back-link"><i class="fa fa-arrow-left"></i> Back to Homepage</a>
</body>
</html>
