<%@ page import="com.gradeycullins.User" %><%--
  Created by IntelliJ IDEA.
  User: Tanner
  Date: 4/7/2017
  Time: 10:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Menu</title>
</head>
<body>
<div>
    <p>
        Welcome to Uotel!
    </p>
</div>
<%
    String type = request.getParameter("type");

    if (type.equals("login")) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User tempUser = new User(login, password);

        if (!tempUser.login(login, password)) {
            response.sendRedirect("/");
        } else {
            out.print("Login successful! Welcome back, " + tempUser.firstName);
        }

    } else if (type.equals("register")) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String middle_name = request.getParameter("middle_name");
        String last_name = request.getParameter("last_name");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        User tempUser = new User(login, password, first_name, middle_name, last_name, gender, address);

        if (tempUser.register()) { // successful reg
            out.print("<p>Register successful!</p>");
        } else { // failed reg
            response.sendRedirect(response.encodeRedirectURL("/"));
            out.print("<p>Register failed. Try again.</p>");
        }
    }
%>
</body>
</html>
