<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        /* General Page Styling */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f4f8;
            color: #333;
        }

        /* Header Styling */
        .header {
            background-color: #004080;
            color: #fff;
            text-align: center;
            padding: 1.5rem 0;
            font-size: 1.8rem;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        /* Dashboard Container */
        .dashboard-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin: 2rem auto;
            max-width: 1100px;
            padding: 1rem;
        }

        /* Dashboard Card Styling */
        .card {
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
            padding: 2rem;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .card a {
            text-decoration: none;
            font-size: 1.1rem;
            color: #004080;
            font-weight: bold;
            display: block;
            margin-top: 1rem;
            transition: color 0.3s;
        }

        .card a:hover {
            color: #0066cc;
        }

        /* Icons */
        .card-icon {
            font-size: 2.5rem;
            color: #004080;
            margin-bottom: 1rem;
        }

        /* Footer */
        .footer {
            text-align: center;
            padding: 1rem 0;
            background-color: #004080;
            color: #fff;
            margin-top: 2rem;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        Admin Dashboard
    </div>

    <!-- Dashboard Container -->
    <div class="dashboard-container">
        <div class="card">
            <div class="card-icon">📚</div>
            <div>Manage Books</div>
            <a href="manageBooks">Go to Manage Books</a>
        </div>
        <div class="card">
            <div class="card-icon">✍️</div>
            <div>Manage Authors</div>
            <a href="manageAuthors">Go to Manage Authors</a>
        </div>
        <div class="card">
            <div class="card-icon">📂</div>
            <div>Manage Categories</div>
            <a href="manageCategories">Go to Manage Categories</a>
        </div>
        <div class="card">
            <div class="card-icon">🔄</div>
            <div>Manage Loans</div>
            <a href="manageLoans">Go to Manage Loans</a>
        </div>
        <div class="card">
            <div class="card-icon">📊</div>
            <div>Books Statistics</div>
            <a href="dashboard">View Books Statistics</a>
        </div>
        <div class="card">
            <div class="card-icon">👥</div>
            <div>View Active Users</div>
            <a href="activeUsers">View Active Users</a>
        </div>
        <div class="card">
    		<div class="card-icon">&#x1F513;</div> <!-- Lock Emoji -->
    		<div>Logout</div>
    		<a href="logout">Logout</a>
		</div>

    </div>

    <!-- Footer -->
    <div class="footer">
        &copy; 2024 Library Management System | Admin Panel
    </div>
</body>
</html>
