<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Game Over</title>
    <style>
        body {
            background-color: #0f1e33;
            color: #39FF14;
            font-family: Orbitron, sans-serif;
            text-align: center;
            padding-top: 50px;
        }
        .btn {
            margin-top: 20px;
            padding: 10px 20px;
            border-radius: 8px;
            border: 2px solid #39FF14;
            background: #0f1e33;
            color: #39FF14;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn:hover {
            background: #39FF14;
            color: #0f1e33;
        }
    </style>
</head>
<body>
    <div class="container">
        <%
            // Use request scope instead of session to avoid IllegalStateException
            String endMessage = (String) request.getAttribute("endMessage");
            if (endMessage == null) {
                endMessage = "Game Over";
            }
        %>
        <h2><%= endMessage %></h2>

        <!-- Button to go to leaderboard -->
        <form action="leaderboard.jsp" method="get">
            <button type="submit" class="btn">View Leaderboard</button>
        </form>
    </div>
</body>
</html>
