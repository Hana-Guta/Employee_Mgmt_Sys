<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
        }

        h1 {
            color: #008080;
        }

        p {
            font-size: 18px;
            color: #333;
        }

        footer {
            margin-top: 20px;
        }

        footer p {
            font-size: 14px;
            color: #777;
        }

        footer a {
            color: #008080;
            text-decoration: none;
        }

        footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>Welcome</h1>
    <p>This is the home page. Feel free to explore.</p>

    <%
        String currentDate = new java.util.Date().toString();
    %>

    <p>Current Date and Time: <%= currentDate %></p>

    <footer>
        <p>&copy; 2023 | <a href="registration.jsp">Register</a> | <a href="login.jsp">Login</a></p>
    </footer>
</body>
</html>
