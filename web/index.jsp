<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Uotel</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form action="mainmenu.jsp">
    <label>
        Login:
        <input type=text name="login"/>
    </label>
    <br>
    <label>
        Password:
        <input type=password name="password"/>
    </label>
    <label>
        <input name="type" value="login" hidden>
    </label>
    <br>
    <input type=submit value="Login">
</form>

<a href="register.jsp">Register</a><br>

</body>
</html>
