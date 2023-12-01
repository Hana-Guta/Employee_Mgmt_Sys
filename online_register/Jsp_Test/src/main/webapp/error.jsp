<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
        }

        h1 {
            color: #FF0000;
        }

        p {
            font-size: 18px;
            color: #333;
        }
    </style>
</head>
<body>
    <h1>Error Encountered</h1>
    <p>An error occurred while processing your request.</p>

    <%
        // Retrieve error message from request attribute
        String errorMessage = (String) request.getAttribute("errorMessage");

        // Display error message if available
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
            <p><strong>Error Message:</strong> <%= errorMessage %></p>
    <%
        }
    %>

    <footer>
        <p><a href="index.jsp">Go back to Home Page</a></p>
    </footer>
</body>
</html>
