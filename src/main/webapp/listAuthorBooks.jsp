<%@ page import="java.util.List, projet.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Books by <%= request.getAttribute("authorName") %></title>
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

        /* Book List */
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
            transition: transform 0.2s ease-in-out, box-shadow 0.3s ease-in-out;
        }

        li:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        li strong {
            font-size: 1.2rem;
            color: #333;
        }

        li p {
            margin: 5px 0;
            font-size: 1rem;
            color: #555;
        }

        /* Back Link */
        .back-link {
            text-align: center;
            margin-top: 20px;
        }

        .back-link a {
            color: #007BFF;
            font-size: 1rem;
            font-weight: bold;
            text-decoration: none;
            transition: color 0.3s ease-in-out;
        }

        .back-link a:hover {
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
        <a href="manageAuthors">&#8592; Back to Manage Authors</a>
    </div>

    <!-- Main Content -->
    <div class="container">
        <h1>Books by <%= request.getAttribute("authorName") %></h1>
        <ul>
            <%
                List<Book> books = (List<Book>) request.getAttribute("books");
                if (books != null && !books.isEmpty()) {
                    for (Book book : books) {
            %>
                <li>
                    <strong><%= book.getTitle() %></strong>
                    <p><em>Year: <%= book.getYear() %>, Format: <%= book.getFormat() %></em></p>
                    <p><%= book.getSummary() != null ? book.getSummary() : "No summary available." %></p>
                </li>
            <%
                    }
                } else {
            %>
                <li>No books found for this author.</li>
            <%
                }
            %>
        </ul>

        <!-- Back Link -->
        <div class="back-link">
            <a href="manageAuthors">Back to Manage Authors</a>
        </div>
    </div>
</body>
</html>
