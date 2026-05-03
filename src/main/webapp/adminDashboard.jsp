<%@ page import="java.util.List" %>
<%@ page import="com.scifi.adventure.model.StoryNode" %>

<%
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<StoryNode> nodes = (List<StoryNode>) request.getAttribute("nodes");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
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
    min-height:100vh;
}

.container {
    background:rgba(10,20,40,0.85);
    border-radius:14px;
    padding:30px;
    box-shadow:0 0 40px rgba(0,255,153,0.8);
    width:800px;
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
    <h2>🛠 Admin Dashboard 🛠</h2>
    <a href="addNode.jsp" class="btn">➕ Add New Story Node</a>

    <table>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Option 1</th>
            <th>Option 2</th>
            <th>Hacking Node</th>
            <th>Required Item</th>
        </tr>
        <%
            if (nodes != null && !nodes.isEmpty()) {
                for (StoryNode node : nodes) {
        %>
        <tr>
            <td><%= node.getId() %></td>
            <td><%= node.getDescription() %></td>
            <td><%= node.getOption1Text() %> → <%= node.getOption1Next() %></td>
            <td><%= node.getOption2Text() %> → <%= node.getOption2Next() %></td>
            <td><%= node.isHackingNode() ? "Yes" : "No" %></td>
            <td><%= node.getRequiredItem() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="6">No story nodes yet. Add one above!</td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
