<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Form</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
        }

        .card {
            width: 300px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #008080;
        }

        form {
            text-align: left;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #008080;
            color: #fff;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #005454;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="card">
        <h2>Registration Form</h2>
        <form action="RegistrationServlet" method="post">
            Name: <input type="text" name="name" value="<%= request.getParameter("name") %>"><br>
            Email: <input type="text" name="email" value="<%= request.getParameter("email") %>"><br>
            Password: <input type="password" name="password" value="<%= request.getParameter("password") %>"><br>
            <input type="submit" value="Register">
        </form>
    </div>
</body>
</html>
