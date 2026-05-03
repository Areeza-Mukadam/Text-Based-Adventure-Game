<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chronicles of the Lost Starship- Login</title>
    <style>
        body {
            margin:0;
            font-family:'Orbitron',sans-serif;
            background: radial-gradient(circle at bottom, #0b1f3a 0%, #020611 100%);
            color:#39FF14;
            overflow-x:hidden;
        }

        .stars {
            position:absolute;
            width:200%;
            height:200%;
            background:url("https://www.transparenttextures.com/patterns/stardust.png");
            animation:move 60s linear infinite;
            opacity:0.4;
        }
        @keyframes move { to{transform:translateY(-1000px);} }

        .wrap {
            position:relative;
            z-index:2;
            max-width:500px;
            margin:80px auto;
            padding:25px;
            background:rgba(10,20,40,0.85);
            border-radius:14px;
            box-shadow:0 0 40px rgba(0,0,0,0.8);
            text-align:center;
        }

        .title {
            color:#9fd3ff;
            font-size:24px;
            margin-bottom:20px;
            text-shadow:0 0 10px #39FF14;
        }

        input[type=text], input[type=password] {
            width:90%;
            padding:12px;
            margin:12px 0;
            border-radius:8px;
            border:2px solid #39FF14;
            background:#0f1e33;
            color:#39FF14;
            font-family:'Share Tech Mono', monospace;
        }
        input[type=text]:focus, input[type=password]:focus {
            outline:none;
            box-shadow:0 0 10px #39FF14;
        }

        input[type=submit], .btn {
            padding:12px 20px;
            margin-top:15px;
            border-radius:8px;
            border:2px solid #39FF14;
            background:#0f1e33;
            color:#39FF14;
            cursor:pointer;
            text-transform:uppercase;
            font-weight:bold;
            box-shadow:0 0 15px #39FF14;
        }
        input[type=submit]:hover, .btn:hover {
            background:#39FF14;
            color:#0f1e33;
        }

        .error {
            color:#ff4444;
            margin-top:15px;
            font-weight:bold;
            text-shadow:0 0 10px #ff4444;
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@500&family=Share+Tech+Mono&display=swap" rel="stylesheet">
</head>
<body>
    <div class="stars"></div>
    <div class="wrap">
        <div class="title">Login to Adventure Game</div>
        <form action="LoginServlet" method="post">
            <input type="text" name="username" placeholder="Username" required /><br/>
            <input type="password" name="password" placeholder="Password" required /><br/>
            <input type="submit" value="Login" />
        </form>

        <!-- Error message display -->
        <%
            String errorMsg = (String) request.getAttribute("error");
            if (errorMsg != null) {
        %>
            <div class="error"><%= errorMsg %></div>
        <%
            }
        %>

        <!-- Sign Up button -->
        <form action="register.jsp" method="get">
            <button type="submit" class="btn">Sign Up</button>
        </form>
        
        <a href="adminLogin.jsp" class="btn">Admin Login</a>
        
    </div>
</body>
</html>
