<%@ page import="com.u58.Th" %>
<%@ page import="com.u58.ThManager" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Properties</title>
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
        <th>ID</th>
        <th>Property Name</th>
        <th>Address</th>
        <th>Owner</th>
        <th>Category</th>
        <th>Phone Number</th>
        <th>URL</th>
        <th>Year Built</th>
    </tr>

    <%
        ThManager thManager = new ThManager();
        thManager.getUserProperties((String)session.getAttribute("login"));
        for (Th th : thManager.properties.values()) {
            out.print("<tr>");
            out.print("<td><a href=\"#\">" + th.tid + "</a></td>");
            out.print("<td>" + th.name + "</td>");
            out.print("<td>" + th.address + "</td>");
            out.print("<td>" + th.owner + "</td>");
            out.print("<td>" + th.category + "</td>");
            out.print("<td>" + th.phoneNum + "</td>");
            out.print("<td>" + th.url + "</td>");
            out.print("<td>" + th.yearBuilt + "</td>");
            out.print("</tr>");
        }
    %>
</table>

<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
