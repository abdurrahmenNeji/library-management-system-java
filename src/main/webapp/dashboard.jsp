<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f8;
            margin: 0;
            padding: 0;
            color: #333;
        }

        /* Navbar Styling */
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

        h2 {
            color: #007BFF;
            font-size: 1.5rem;
            border-bottom: 2px solid #007BFF;
            padding-bottom: 5px;
            margin-bottom: 20px;
        }

        /* Tables */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        table th, table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        table th {
            background-color: #004080;
            color: white;
            font-weight: bold;
        }

        table tr:nth-child(even) {
            background-color: #f2f6fc;
        }

        table tr:hover {
            background-color: #e9f5ff;
        }

        .no-data {
            text-align: center;
            font-weight: bold;
            color: #777;
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

    <div class="container">
        <h1>Books Dashboard</h1>

        <!-- Borrowed Books Statistics -->
        <h2>Books Borrowed Per Month</h2>
        <table>
            <thead>
                <tr>
                    <th>Month</th>
                    <th>Borrowed Count</th>
                </tr>
            </thead>
            <tbody>
                <% Map<String, Integer> borrowedBooks = (Map<String, Integer>) request.getAttribute("borrowedBooks");
                   if (borrowedBooks != null && !borrowedBooks.isEmpty()) {
                       for (Map.Entry<String, Integer> entry : borrowedBooks.entrySet()) { %>
                    <tr>
                        <td><%= entry.getKey() %></td>
                        <td><%= entry.getValue() %></td>
                    </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="2" class="no-data">No data available.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <!-- Downloaded Books Statistics -->
        <h2>Books Downloaded Per Month</h2>
        <table>
            <thead>
                <tr>
                    <th>Month</th>
                    <th>Downloaded Count</th>
                </tr>
            </thead>
            <tbody>
                <% Map<String, Integer> downloadedBooks = (Map<String, Integer>) request.getAttribute("downloadedBooks");
                   if (downloadedBooks != null && !downloadedBooks.isEmpty()) {
                       for (Map.Entry<String, Integer> entry : downloadedBooks.entrySet()) { %>
                    <tr>
                        <td><%= entry.getKey() %></td>
                        <td><%= entry.getValue() %></td>
                    </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="2" class="no-data">No data available.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <!-- Added Books Statistics -->
        <h2>Books Added Per Month</h2>
        <table>
            <thead>
                <tr>
                    <th>Month</th>
                    <th>Added Count</th>
                </tr>
            </thead>
            <tbody>
                <% Map<String, Integer> addedBooks = (Map<String, Integer>) request.getAttribute("addedBooks");
                   if (addedBooks != null && !addedBooks.isEmpty()) {
                       for (Map.Entry<String, Integer> entry : addedBooks.entrySet()) { %>
                    <tr>
                        <td><%= entry.getKey() %></td>
                        <td><%= entry.getValue() %></td>
                    </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="2" class="no-data">No data available.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
