<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <link href="mystyle.css" rel="stylesheet">
</head>
<body>
<% if (request.getSession(false) != null && request.getSession().getAttribute("user") != null) { %>
    <div id="log"></div>
    <div id="msg">
        <input type="text" id="message">
        <button type="button" id="send"></button>
    </div>

    <form method="post" action="logout" enctype="application/x-www-form-urlencoded">
        <input type="submit" value="Logout!" class="buttons">
    </form>

    <script src="myscript.js"></script>
<% } else { %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="errors"><%= request.getAttribute("error") %></div>
    <% } %>
<div id="logform">
    <form method="post" action="login" enctype="application/x-www-form-urlencoded">
        <div>

            <input type="text" id="login" name="login" placeholder="Login">
        </div>
        <div>

            <input type="password" id="password" name="password" placeholder="Password">
        </div>
        <input type="submit" value="Login!" class="buttons">
    </form>
</div>
<% } %>
</body>
</html>
