<%@ page import="java.util.List" %>
<%@ page import="com.scifi.adventure.model.LeaderboardEntry" %>

<%
    List<LeaderboardEntry> leaderboard = (List<LeaderboardEntry>) request.getAttribute("leaderboard");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Leaderboard</title>
<link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400;700&display=swap" rel="stylesheet">
<style>
body {
    margin:0;
    font-family:'Orbitron',sans-serif;
    background: radial-gradient(circle at bottom, #0b1f3a 0%, #020611 100%);
    color:#39FF14;
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
}

.container {
    background:rgba(10,20,40,0.85);
    border-radius:14px;
    padding:30px;
    box-shadow:0 0 40px rgba(0,255,153,0.8);
    width:500px;
    text-align:center;
}

h2 {
    color:#9fd3ff;
    margin-bottom:20px;
    text-shadow:0 0 10px #39FF14;
}

table {
    width:100%;
    border-collapse:collapse;
    margin-top:20px;
}

th, td {
    border:1px solid #39FF14;
    padding:10px;
    text-align:center;
}

th {
    background:#0f1e33;
    color:#39FF14;
    text-shadow:0 0 5px #39FF14;
}

tr:nth-child(even) {
    background:rgba(20,40,60,0.6);
}

.btn {
    margin-top:20px;
    padding:10px 20px;
    border-radius:8px;
    border:2px solid #39FF14;
    background:#0f1e33;
    color:#39FF14;
    cursor:pointer;
    text-decoration:none;
    display:inline-block;
}
.btn:hover {
    background:#39FF14;
    color:#0f1e33;
}
</style>
</head>
<body>
<div class="container">
    <h2>🚀 Leaderboard 🚀</h2>
    <table>
        <tr>
            <th>Username</th>
            <th>Score</th>
        </tr>
        <%
            if (leaderboard != null && !leaderboard.isEmpty()) {
                for (LeaderboardEntry entry : leaderboard) {
        %>
        <tr>
            <td><%= entry.getUsername() %></td>
            <td><%= entry.getScore() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="2">No scores yet. Be the first adventurer!</td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="start.jsp" class="btn">Back to Game</a>
</div>
</body>
</html>
