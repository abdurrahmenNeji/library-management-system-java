<%@ page import="java.util.List, projet.Loan" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Loans</title>
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

        /* Table Styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }

        table th, table td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        table th {
            background-color: #004080;
            color: white;
            font-weight: bold;
        }

        table tr:nth-child(even) {
            background-color: #f8f9ff;
        }

        table tr:hover {
            background-color: #e9f5ff;
            transition: background-color 0.3s ease-in-out;
        }

        /* No Data Message */
        .no-data {
            text-align: center;
            font-size: 1.2rem;
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

    <!-- Main Content -->
    <div class="container">
        <h1>Manage Active Loans</h1>
        
        <!-- Table for Loans -->
        <% 
            List<Loan> loans = (List<Loan>) request.getAttribute("loans");
            if (loans != null && !loans.isEmpty()) { 
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User Name</th>
                    <th>Book Title</th>
                    <th>Borrow Date</th>
                    <th>Return Date</th>
                </tr>
            </thead>
            <tbody>
                <% for (Loan loan : loans) { %>
                <tr>
                    <td><%= loan.getId() %></td>
                    <td><%= loan.getUserName() %></td>
                    <td><%= loan.getBookTitle() %></td>
                    <td><%= loan.getDateEmprunt() %></td>
                    <td><%= loan.getDateRetour() %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } else { %>
            <p class="no-data">No active loans available.</p>
        <% } %>
    </div>
</body>
</html>
