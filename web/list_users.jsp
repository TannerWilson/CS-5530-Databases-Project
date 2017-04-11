<%@ page import="com.u58.UserManager" %>
<%@ page import="com.u58.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<style>
    table#t01 {
        width: 75%;
        background-color: #f1f1c1;
    }

    table, th, td {
    }

    table {
        border-spacing: 5px;
    }

    th, td {
        padding: 15px;
    }

    table#t01 tr:nth-child(even) {
        background-color: gainsboro;
    }
    table#t01 tr:nth-child(odd) {
        background-color: lightgreen;
    }
    table#t01 th {
        color: white;
        background-color: mediumseagreen;
    }
</style>
<table id="t01">
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
    UserManager userMan = new UserManager();
    ArrayList<User> users = userMan.getAllUsers();
    int index = 1;
    for (User u : users) {
        out.print("<tr>");
        out.print("<td><a href=\"#\">" + index++ + "</a></td>");
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
