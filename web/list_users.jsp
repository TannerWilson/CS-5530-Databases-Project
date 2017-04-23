<%@ page import="u58.UserManager" %>
<%@ page import="u58.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/table.css" rel="stylesheet" type="text/css">
<head>
    <title>Users</title>
</head>
<body>
<table class="t01">
    <tr>
        <th>index</th>
        <th>login</th>
        <th>first name</th>
        <th>middle name</th>
        <th>last name</th>
        <th>gender</th>
        <th>favorite th</th>
    </tr>
    <%
        User user = (User) session.getAttribute("user");
        UserManager userMan = new UserManager();
        ArrayList<User> users = userMan.getAllUsers();
        int index = 1;
        for (User u : users) {
            out.print("<tr>");
            out.print("<td><a href=\"view_user.jsp?login=" + u.login + "\">" + index++ + "</a></td>");
            out.print("<td>" + u.login + "</td>");
            out.print("<td>" + u.firstName + "</td>");
            out.print("<td>" + u.middleName + "</td>");
            out.print("<td>" + u.lastName + "</td>");
            out.print("<td>" + u.gender + "</td>");
            out.print("<td>" + u.favorite + "</td>");
            out.print("</tr>");
        }

    %>
</table>
</body>
</html>
