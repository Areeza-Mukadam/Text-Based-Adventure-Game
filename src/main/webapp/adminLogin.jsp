<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
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
.wrap {
    background:rgba(10,20,40,0.85);
    border-radius:14px;
    padding:40px;
    box-shadow:0 0 40px rgba(0,0,0,0.8);
    width:350px;
    text-align:center;
}
h2 { color:#9fd3ff; margin-bottom:20px; }
input {
    width:100%;
    padding:10px;
    margin:10px 0;
    border-radius:6px;
    border:2px solid #39FF14;
    background:#0f1e33;
    color:#39FF14;
}
.btn {
    width:100%;
    padding:10px;
    border-radius:8px;
    border:2px solid #39FF14;
    background:#0f1e33;
    color:#39FF14;
    cursor:pointer;
}
.btn:hover {
    background:#39FF14;
    color:#0f1e33;
}
.error {
    color:#ff4d4d;
    margin-top:10px;
}
</style>
</head>
<body>
<div class="wrap">
    <h2>ADMIN LOGIN</h2>
    <form action="AdminLoginServlet" method="post">
        <input type="text" name="adminUser" placeholder="Admin Username" required>
        <input type="password" name="adminPass" placeholder="Password" required>
        <button class="btn" type="submit">Enter Control Room</button>
    </form>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
</div>
</body>
</html>
