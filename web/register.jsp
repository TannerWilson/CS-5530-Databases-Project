<%@ page import="java.util.Enumeration, u58.*" %><%--
  Created by IntelliJ IDEA.
  User: Tanner
  Date: 4/7/2017
  Time: 10:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="mainmenu.jsp" method="post">
    <label>
        login
        <input name="login" type="text">
    </label><br>
    <label>
        password
        <input name="password" type="text">
    </label><br>
    <label>
        first name
        <input name="first_name" type="text">
    </label><br>
    <label>
        middle name
        <input name="middle_name" type="text">
    </label><br>
    <label>
        last name
        <input name="last_name" type="text">
    </label><br>
    <label>
        gender
        <input name="gender" type="text">
    </label><br>
    <label>
        address
        <input name="address" type="text">
    </label><br>
    <label>
        <input name="type" value="register" hidden>
    </label>
    <input type="submit" value="Register">
</form>
</body>
</html>









