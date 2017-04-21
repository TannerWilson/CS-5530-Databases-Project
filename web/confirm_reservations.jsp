<%@ page import="com.u58.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.u58.ThManager" %>
<%@ page import="com.u58.Reservation" %><%--
  Created by IntelliJ IDEA.
  User: Tanner
  Date: 4/20/2017
  Time: 8:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirm Reservations</title>
</head>
<body>
<%
    out.print("<table id=\"t01\">\n" +
            "    <tr>\n" +
            "        <th>From</th>\n" +
            "        <th>To</th>\n" +
            "        <th>Cost</th>\n" +
            "    </tr>");
    for(Reservation res : user.pendingReservations){
        out.print("<tr>");
        out.print("<td>" + res.from.toString() + "</td>");
        out.print("<td>" + res.to.toString() + "</td>");
        out.print("<td>" + res.cost + "</td>");
        out.print("</tr>");
    }
%>
<form method="post">
    <input name="commit" value="yes" hidden>
    <input type="submit" value="Commit Reservations">
</form>
<%
    String commit = request.getAttribute("commit");

    if(commit != null && commit.equals("yes")) {
        User user = (User) session.getAttribute("user");

        for (Reservation res : user.pendingReservations)
            res.insert();
        out.print("<p>Your reservations have been confirmed.</p>");

        // Get suggested properties to print
        ThManager manager = new ThManager();
        ArrayList<String> suggestedProperties = manager.getSuggestedProperties(user.pendingReservations, user);

        out.print("<br>You may also enjoy a stay at the folowing properties:<br>");
        out.print("Property Name:");
        for (String property : suggestedProperties)
            out.print("<p>" + property + "</p>");
        out.print("Try searching for these to get more information!");

        user.pendingReservations.clear();
        //user.commitReservations();
    }
%>
<p>
    <a href="search.jsp">Search properties</a>
</p>
<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
