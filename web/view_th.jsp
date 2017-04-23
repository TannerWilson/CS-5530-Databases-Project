<%@ page import="u58.ThManager" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="u58.Th" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/19/17
  Time: 3:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%
        out.print(request.getParameter("tid"));
    %></title>
</head>
<link href="css/table.css" rel="stylesheet" type="text/css">
<body>
<table class="t01">
    <tr>
        <th>tid</th>
        <th>name</th>
        <th>owner</th>
        <th>lowest price</th>
        <th>address</th>
        <th>average score</th>
    </tr>
    <%
        ThManager thManager = new ThManager();
        thManager.getTh(-1, -1, "", "", "", "", new LinkedList<String>(), "", -1);
        Th currentTh = thManager.properties.get(Integer.valueOf(request.getParameter("tid")));
        out.print("<tr>");
        out.print("<td>" + currentTh.tid + "</td>");
        out.print("<td>" + currentTh.name + "</td>");
        out.print("<td>" + currentTh.owner + "</td>");
        out.print("<td>" + currentTh.lowestPrice + "</td>");
        out.print("<td>" + currentTh.address + "</td>");
        out.print("<td>" + currentTh.averageScore + "</td>");
        out.print("</tr>");

        session.setAttribute("selectedTh", currentTh);
    %>
</table>
<p><a href="${pageContext.request.contextPath}/make_reservation.jsp?tid=<%= currentTh.tid %>">make reservation</a></p>
<p><a href="${pageContext.request.contextPath}/leave_feedback.jsp?tid=<%= currentTh.tid %>">leave feedback</a></p>
<p><a href="${pageContext.request.contextPath}/favorite_th.jsp?tid=<%= currentTh.tid %>">favorite this property</a></p>
<p><a href="${pageContext.request.contextPath}/view_th_feedback.jsp?tid=<%= currentTh.tid %>">view feedback for this property</a></p>
</body>
</html>
