<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession currentSession = request.getSession(false);
    if (currentSession == null || currentSession.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer userId = (Integer) currentSession.getAttribute("userId");
    String userName = (String) currentSession.getAttribute("userName");
    String userEmail = (String) currentSession.getAttribute("userEmail");
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Profile</title>
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

        /* Container Styling */
        .container {
            max-width: 500px;
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
            margin-bottom: 20px;
        }

        /* Form Styling */
        form label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #555;
        }

        form input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
            box-sizing: border-box;
        }

        form input:focus {
            outline: none;
            border-color: #007BFF;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        }

        form button {
            width: 100%;
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 12px;
            font-size: 1rem;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
        }

        form button:hover, form button:focus {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        /* Feedback Messages */
        .message, .error {
            text-align: center;
            font-size: 1rem;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
        }

        .message {
            color: #4F8A10;
            background-color: #DFF2BF;
            border: 1px solid #4F8A10;
        }

        .error {
            color: #D8000C;
            background-color: #FFD2D2;
            border: 1px solid #D8000C;
        }

        /* Back Link */
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
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

        /* Responsive Fix */
        @media (max-width: 600px) {
            .container {
                max-width: 90%;
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><i class="fa fa-user-circle"></i> Your Profile</h1>

        <!-- Feedback Messages -->
        <% if (error != null && !error.isEmpty()) { %>
            <div class="error">
                <%= error %>
            </div>
        <% } %>
        
        <% if (success != null && !success.isEmpty()) { %>
            <div class="message">
                <%= success %>
            </div>
        <% } %>

        <!-- Profile Update Form -->
        <form method="post" action="updateUserProfile">
            <label for="name"><i class="fa fa-user"></i> Name</label>
            <input type="text" id="name" name="name" value="<%= userName %>" required>

            <label for="email"><i class="fa fa-envelope"></i> Email</label>
            <input type="email" id="email" name="email" value="<%= userEmail %>" required>

            <label for="password"><i class="fa fa-lock"></i> Password</label>
            <input type="password" id="password" name="password" placeholder="Enter new password">

            <button type="submit"><i class="fa fa-save"></i> Update Profile</button>
        </form>

        <!-- Back Link -->
        <a href="books" class="back-link"><i class="fa fa-arrow-left"></i> Back to Homepage</a>
    </div>
</body>
</html>
