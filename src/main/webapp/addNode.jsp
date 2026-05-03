<%
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Story Node</title>
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
    width:600px;
    text-align:center;
}

h2 {
    color:#9fd3ff;
    margin-bottom:20px;
    text-shadow:0 0 10px #39FF14;
}

label {
    display:block;
    margin-top:10px;
    text-align:left;
    color:#39FF14;
    text-shadow:0 0 5px #39FF14;
}

input, textarea {
    width:100%;
    padding:10px;
    margin-top:5px;
    border-radius:8px;
    border:2px solid #39FF14;
    background:#0f1e33;
    color:#39FF14;
    font-family:'Orbitron',sans-serif;
}

input[type="checkbox"] {
    width:auto;
    margin-left:10px;
}

button {
    margin-top:20px;
    padding:10px 20px;
    border-radius:8px;
    border:2px solid #39FF14;
    background:#0f1e33;
    color:#39FF14;
    cursor:pointer;
    font-family:'Orbitron',sans-serif;
}
button:hover {
    background:#39FF14;
    color:#0f1e33;
}
</style>
</head>
<body>
<div class="container">
    <h2>➕ Add New Story Node</h2>
    <form action="AddStoryNodeServlet" method="post">
        <label>ID:</label>
        <input type="number" name="id" required>

        <label>Description:</label>
        <textarea name="description" required></textarea>

        <label>Option 1 Text:</label>
        <input type="text" name="option1Text">
        <label>Option 1 Next:</label>
        <input type="number" name="option1Next">

        <label>Option 2 Text:</label>
        <input type="text" name="option2Text">
        <label>Option 2 Next:</label>
        <input type="number" name="option2Next">

        <label>Is Hacking Node:
            <input type="checkbox" name="isHackingNode">
        </label>

        <label>Hacking Answer:</label>
        <input type="text" name="hackingAnswer">

        <label>Required Item:</label>
        <input type="text" name="requiredItem">

        <button type="submit">Save Node</button>
    </form>
</div>
</body>
</html>
