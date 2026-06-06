<%@ page import="java.util.List, projet.BorrowedBook" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Borrowed Books</title>
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
            font-size: 2rem;
        }

        /* Table Styling */
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
        }

        th, td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007BFF;
            color: white;
            font-size: 1.1rem;
            text-transform: uppercase;
        }

        tr:nth-child(even) {
            background-color: #f8f9ff;
        }

        tr:hover {
            background-color: #e6f0ff;
        }

        td {
            font-size: 1rem;
            color: #555;
        }

        /* No Books Message */
        .no-books {
            text-align: center;
            font-size: 1.2rem;
            color: #777;
            margin: 20px 0;
        }

        /* Back Link */
        .back-link {
            display: block;
            text-align: center;
            margin: 30px 0;
            font-size: 1rem;
            font-weight: bold;
            color: #007BFF;
            text-decoration: none;
            transition: color 0.3s ease;
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
            <a href="favorites"><i class="fa fa-heart"></i> Favorites</a>
            <a href="logout"><i class="fa fa-sign-out"></i> Logout</a>
        </div>
    </div>

    <!-- Page Title -->
    <h1>Your Borrowed Books</h1>

    <!-- Table or Message -->
    <%
        List<BorrowedBook> borrowedBooks = (List<BorrowedBook>) request.getAttribute("borrowedBooks");
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
    %>
        <p class="no-books">You have not borrowed any books yet.</p>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Borrow Date</th>
                    <th>Return Date</th>
                </tr>
            </thead>
            <tbody>
                <% for (BorrowedBook book : borrowedBooks) { %>
                    <tr>
                        <td><%= book.getTitle() %></td>
                        <td><%= book.getDateEmprunt() %></td>
                        <td><%= book.getDateRetour() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>

    <!-- Back Link -->
    <a href="books" class="back-link"><i class="fa fa-arrow-left"></i> Return to Homepage</a>
</body>
</html>
