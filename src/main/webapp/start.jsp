<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sci-Fi Adventure</title>

<link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400;700&display=swap" rel="stylesheet">

<style>
body {
    height:100vh;
    margin:0;
    overflow:hidden;
    background: radial-gradient(circle at bottom, #0b1f3a 0%, #020611 100%);
    font-family: 'Orbitron', sans-serif;
    color:#39FF14;
    display:flex;
    justify-content:center;
    align-items:center;
    flex-direction:column;
}

.stars {
    position:absolute;
    width:200%;height:200%;
    background:url("https://www.transparenttextures.com/patterns/stardust.png");
    animation:move 60s linear infinite;
    opacity:0.6;
    pointer-events:none; /* allow clicks to pass through */
}


@keyframes move {
    from {transform:translate(0,0);}
    to {transform:translate(-50%,-50%);}
}

h1 {
    font-size:2.5em;
    margin-bottom:30px;
    text-shadow:0 0 10px #39FF14;
}

.btn {
    margin:10px;
    padding:12px 24px;
    border-radius:8px;
    border:2px solid #39FF14;
    background:#0f1e33;
    color:#39FF14;
    cursor:pointer;
    text-decoration:none;
    font-size:1.2em;
    transition:0.3s;
}
.btn:hover {
    background:#39FF14;
    color:#0f1e33;
    box-shadow:0 0 15px #39FF14;
}
</style>
</head>
<body>
<div class="stars"></div>

<h1>Welcome to Sci‑Fi Adventure</h1>

<!-- Start Game -->
<form action="start" method="post">
    <button type="submit" class="btn">Start Game</button>
</form>

<!-- Leaderboard -->
<form action="leaderboard.jsp" method="get">
    <button type="submit" class="btn">View Leaderboard</button>
</form>

</body>
</html>
