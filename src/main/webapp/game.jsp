<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scifi.adventure.model.Player, com.scifi.adventure.model.StoryNode" %>

<%
    Player player = (Player) session.getAttribute("player");
    StoryNode node = (StoryNode) session.getAttribute("currentNode");
    String saveStatus = (String) session.getAttribute("saveStatus");
    String eventTitle = (String) request.getAttribute("eventTitle");
    String eventMessage = (String) request.getAttribute("eventMessage");

    if (saveStatus != null) session.removeAttribute("saveStatus");

    if (player == null || node == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chronicles of the Lost Starship</title>

<link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400;700&display=swap" rel="stylesheet">

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
    max-width:900px;
    margin:60px auto;
    padding:25px;
    background:rgba(10,20,40,0.85);
    border-radius:14px;
    box-shadow:0 0 40px rgba(0,0,0,0.8);
}

.title {
    color:#9fd3ff;
    font-size:22px;
    margin-bottom:10px;
}

.desc {
    background:#0f1e33;
    padding:15px;
    border-radius:10px;
    margin:15px 0;
    white-space:pre-wrap;
}

.btn {
    padding:10px 16px;
    margin:5px;
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

input {
    padding:8px;
    border-radius:6px;
    border:none;
    margin-right:10px;
}

.stats {
    display:flex;
    justify-content:space-between;
    margin-bottom:10px;
    color:#cde8ff;
    font-size:14px;
}

.hacking-container {
    background:#0f1e33;
    border:2px solid #39FF14;
    padding:20px;
    text-align:center;
    margin-top:20px;
    display:<%= node.isHackingNode() ? "block" : "none" %>;
}

#popup {
    position:fixed;
    top:20%;
    left:50%;
    transform:translate(-50%,-20%);
    background:#000;
    border:3px solid #39FF14;
    padding:40px;
    z-index:1000;
    display:none;
    box-shadow:0 0 20px #39FF14;
    text-align:center;
}
#popup h2 { color:#9fd3ff; }
#popup p { color:#39FF14; }
</style>
</head>

<body>

<div class="stars"></div>

<div class="wrap">

    <!-- PLAYER INFO -->
    <div class="stats">
        <div><b>Captain:</b> <%=player.getName()%></div>
        <div><b>Health:</b> <%=player.getHealth()%></div>
        <div><b>Oxygen:</b> <%=player.getOxygen()%></div>
        <div><b>Threat:</b> <%=player.getThreatLevel()%></div>
        <div><b>Score:</b> <%=player.getScore()%></div>
    </div>

    <hr>

    <!-- STORY TEXT -->
    <div class="title">Mission Log</div>
    <div class="desc">
        <%=node.getDescription()%>
    </div>

    <!-- CHOICES -->
    <div id="story-section" style="display:<%= node.isHackingNode() ? "none" : "block" %>">
        <form action="${pageContext.request.contextPath}/game" method="post">
            <button class="btn" type="submit" name="choice" value="1">
                <%=node.getOption1Text()%>
            </button>
            <button class="btn" type="submit" name="choice" value="2">
                <%=node.getOption2Text()%>
            </button>
        </form>
    </div>

    <!-- HACKING NODE -->
    <div class="hacking-container">
        <h3>[SECURITY OVERRIDE REQUIRED]</h3>
        <p>DECRYPT THE SEQUENCE: <%=node.getHackingAnswer() != null ? "Hint: " + node.getHackingAnswer().substring(0,2) + "..." : "" %></p>
        <form action="${pageContext.request.contextPath}/game" method="post">
            <input type="text" name="hack_input" placeholder="Enter override code">
            <button class="btn">EXECUTE</button>
        </form>
    </div>

    <hr>

    <!-- SAVE GAME -->
    <form action="${pageContext.request.contextPath}/save" method="post">
        <input type="text" name="saveName" placeholder="Save name (optional)">
        <button class="btn" type="submit">Save Game</button>
    </form>

    <!-- LOAD GAME -->
    <form action="${pageContext.request.contextPath}/load" method="get">
        <button class="btn" type="submit">Load Game</button>
    </form>

    <!-- STATUS -->
    <% if(saveStatus != null){ %>
        <p style="color:#9fe6a3; margin-top:10px;">
            <%=saveStatus%>
        </p>
    <% } %>

</div>

<!-- POPUP NOTIFICATION -->
<div id="popup">
    <h2 id="pop-title">NOTIFICATION</h2>
    <p id="pop-msg"></p>
    <button class="btn" onclick="closePopup()">DISMISS</button>
</div>

<script>
    function showPopup(title, msg) {
        document.getElementById('pop-title').innerText = title;
        document.getElementById('pop-msg').innerText = msg;
        document.getElementById('popup').style.display = 'block';
    }
    function closePopup() {
        document.getElementById('popup').style.display = 'none';
    }

    <% if(eventTitle != null && eventMessage != null){ %>
        showPopup("<%=eventTitle%>", "<%=eventMessage%>");
    <% } %>
</script>

</body>
</html>
