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
        <button type="button" id="send">Send!</button>
    </div>

    <form method="post" action="logout" enctype="application/x-www-form-urlencoded">
        <input type="submit" value="Logout!">
    </form>

    <script src="myscript.js"></script>
<% } else { %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="errors"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="login" enctype="application/x-www-form-urlencoded">
        <div>
            <label for="login">Login</label>
            <input type="text" id="login" name="login" placeholder="Login">
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="pass">
        </div>
        <input type="submit" value="Login!">
    </form>
<% } %>
</body>
</html>
