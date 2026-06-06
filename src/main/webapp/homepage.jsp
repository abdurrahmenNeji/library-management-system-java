<%@ page import="java.util.List, projet.Book" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage - Library Management</title>
    <!-- Font Awesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
    /* General Styles */
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f0f4f8;
        margin: 0;
        padding: 0;
        color: #333;
    }

    /* Top Navigation Bar */
    .navbar {
        background-color: #004080;
        padding: 15px 30px;
        color: white;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .navbar a {
        color: white;
        text-decoration: none;
        font-size: 1rem;
        font-weight: bold;
        margin-right: 20px;
        transition: color 0.3s;
    }

    .navbar a:hover {
        color: #007BFF;
    }

    /* Page Header */
    h1 {
        text-align: center;
        margin: 40px 0 30px;
        color: #004080;
        font-size: 2rem;
    }

    /* Search Bar */
    .search-bar {
        text-align: center;
        margin: 20px auto;
    }

    .search-bar input[type="text"] {
        width: 50%;
        padding: 10px;
        font-size: 1rem;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-right: 10px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .search-bar button {
        padding: 10px 20px;
        font-size: 1rem;
        color: white;
        background-color: #007BFF;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .search-bar button:hover {
        background-color: #0056b3;
    }

    /* Book List - Grid Layout */
    .book-list {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 30px;
        padding: 30px;
        max-width: 1200px;
        margin: 0 auto 60px auto;
    }

    .book-card {
        background: white;
        border-radius: 12px;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .book-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
    }

    .book-card h3 {
        color: #007BFF;
        font-size: 1.4rem;
        margin-bottom: 10px;
        word-wrap: break-word;
    }

    .book-card a {
        text-decoration: none;
        color: #007BFF;
    }

    .book-card p {
        margin: 10px 0;
        color: #555;
        font-size: 0.95rem;
        line-height: 1.6;
    }

    .book-card i {
        color: #004080;
        margin-right: 5px;
    }

    .no-books {
        text-align: center;
        font-size: 1.2rem;
        color: #777;
    }

    /* Footer Links */
    .footer {
        text-align: center;
        margin: 40px auto 20px;
        font-size: 1rem;
    }

    .footer a {
        color: #007BFF;
        text-decoration: none;
        margin: 0 15px;
        font-weight: bold;
    }

    .footer a:hover {
        color: #0056b3;
        text-decoration: underline;
    }

    /* Responsive Fixes */
    @media (max-width: 768px) {
        .navbar {
            flex-direction: column;
            text-align: center;
        }

        .navbar a {
            margin-bottom: 10px;
        }

        .search-bar input[type="text"] {
            width: 90%;
            margin-bottom: 10px;
        }

        .book-list {
            gap: 20px;
            padding: 20px;
        }
    }
</style>
    

    
</head>
<body>
    <!-- Top Navigation Bar -->
    <div class="navbar">
        <div style="font-size: 1.5rem; font-weight: bold;">Library Management</div>
        <div>
            <a href="userprofile.jsp"><i class="fa fa-user-circle"></i> Manage Your Profile</a>
            <a href="borrowedBooks" style="margin-left: 20px;"><i class="fa fa-book"></i> View Borrowed Books</a>
            <a href="logout" style="margin-left: 20px;"><i class="fa fa-sign-out"></i> Logout</a>
        </div>
    </div>

	<!-- Welcome Section -->
    <div class="welcome">
    <%
        HttpSession currentSession = request.getSession(false);
        Integer userId = (currentSession != null) ? (Integer) currentSession.getAttribute("userId") : null;
        String userName = (currentSession != null) ? (String) currentSession.getAttribute("userName") : null;
        String userEmail = (currentSession != null) ? (String) currentSession.getAttribute("userEmail") : null;
        String userRole = (currentSession != null) ? (String) currentSession.getAttribute("userRole") : null;

        if (userId == null) {
    %>
        <p>You are not logged in. <a href="login.jsp">Login here</a>.</p>
    <%
        } else {
    %>
        <p>Welcome, <strong><%= userName %></strong> (<%= userEmail %>)!</p>
        <% if ("Admin".equalsIgnoreCase(userRole)) { %>
            <p>You are logged in as an <strong>Admin</strong>. <a href="adminDashboard.jsp">Go to Admin Dashboard</a></p>
        <% } else { %>
            <p>You are logged in as a regular user.</p>
        <% } %>

        <!-- New Button to Navigate to User Profile -->
        <div style="text-align: center; margin-top: 20px;">
            <a href="userProfile" style="
                background-color: #007BFF;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                font-weight: bold;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
                transition: background-color 0.3s, transform 0.2s;
            ">
                <i class="fa fa-user-circle" style="margin-right: 5px;"></i> Go to Your Profile
            </a>
        </div>
    <%
        }
    %>
</div>


    <!-- Search Bar -->
    <div class="search-bar">
        <form method="get" action="books">
            <input type="text" name="search" placeholder="Search by title, author, or category" />
            <button type="submit">Search</button>
        </form>
    </div>

    <!-- Page Header -->
    <h1>Available Books</h1>
	
    <!-- Book List -->
    <div class="book-list">
        <%
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null && !books.isEmpty()) {
                for (Book book : books) {
        %>
                <div class="book-card">
                    <h3><i class="fa fa-book"></i> <a href="bookDetails?id=<%= book.getId() %>"><%= book.getTitle() %></a></h3>
                    <p><i class="fa fa-user"></i> <strong>Author(s):</strong> <%= book.getAuthors() != null ? book.getAuthors() : "No authors" %></p>
                    <p><i class="fa fa-tag"></i> <strong>Category:</strong> <%= book.getCategory() != null ? book.getCategory() : "Unknown" %></p>
                    <p><i class="fa fa-calendar"></i> <strong>Year:</strong> <%= book.getYear() %></p>
                    <p><i class="fa fa-file"></i> <strong>Format:</strong> <%= book.getFormat() %></p>
                </div>
        <%
                }
            } else {
        %>
            <p class="no-books">No books available at the moment.</p>
        <%
            }
        %>
    </div>

    <!-- Footer -->
    <div class="footer">
        <a href="userprofile.jsp">Manage Your Profile</a> |
        <a href="borrowedBooks">View Borrowed Books</a> |
        <a href="logout">Logout</a>
    </div>
</body>
</html>
