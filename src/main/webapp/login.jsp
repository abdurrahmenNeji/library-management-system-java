<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Library Management</title>
    <style>
        /* General Page Styling */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        /* Container Styling */
        .login-container {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 2rem;
            width: 350px;
            text-align: center;
            animation: fadeIn 1s ease-in-out;
        }

        /* Logo Placeholder */
        .logo {
            width: 80px;
            height: 80px;
            margin: 0 auto 1rem auto;
            background: url('book.png') no-repeat center;
            background-size: contain;
        }

        /* Heading */
        h1 {
            font-size: 1.8rem;
            color: #004080;
            margin-bottom: 1rem;
        }

        /* Form Styling */
        .form-group {
            margin-bottom: 1rem;
            text-align: left;
        }

        label {
            display: block;
            font-weight: bold;
            color: #555;
            margin-bottom: 0.3rem;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            outline: none;
            transition: border 0.3s ease-in-out;
        }

        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #004080;
        }

        button {
            background-color: #004080;
            color: #fff;
            border: none;
            padding: 0.8rem 1rem;
            border-radius: 8px;
            font-size: 1rem;
            cursor: pointer;
            width: 100%;
            margin-top: 1rem;
            transition: background 0.3s ease-in-out;
        }

        button:hover {
            background-color: #0066cc;
        }

        /* Error Message */
        .error-message {
            color: red;
            font-size: 0.9rem;
            margin-top: 1rem;
        }

        /* Animation */
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
    <div class="login-container">
        <div class="logo"></div>
        <h1>Library Login</h1>
        <form method="post" action="login">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error-message"><%= error %></p>
        <% 
            }
        %>
    </div>
</body>
</html>
