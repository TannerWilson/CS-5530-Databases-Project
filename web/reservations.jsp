<%@ page import="com.u58.User" %>
<%@ page import="com.u58.Reservation" %>
<%@ page import="com.u58.Visit" %>
<%@ page import="com.u58.ThManager" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/table.css" rel="stylesheet" type="text/css">
<head>
    <title>Reservations</title>
</head>
<body>
<table id="t01">
    <tr>
        <th>Index</th>
        <th>House Name</th>
        <th>From</th>
        <th>To</th>
        <th>Cost</th>
    </tr>
    <%
        User user = (User) session.getAttribute("user");
        if(user.currentReservations.size() == 0)
            user.getReservations(); // Pull all reservations this user has made

        for (int i = 0; i < user.currentReservations.size(); i++) {
            Reservation r = user.currentReservations.get(i);
            out.print("<tr>");
            out.print("<td><a href=\"?index="+ (i+1) +"\">"+(i+1)+"</a></td>");
            out.print("<td>" + r.houseName + "</td>");
            out.print("<td>" + r.from.toString() + "</td>");
            out.print("<td>" + r.to.toString() + "</td>");
            out.print("<td>" + r.cost + "</td>");
            out.print("</tr>");
        }
    %>
</table>
<%
    String indexString = (String) request.getParameter("index");
    int index = 0;
    if(indexString != null && !indexString.equals(""))
        index = Integer.parseInt(indexString);

    if(index != 0){
        Reservation selected =  user.currentReservations.get(index-1);
        out.print("<p>Record a visit on selected reservation</p>");
        out.print("<p>Selected reservation: "+selected.sdf.format(selected.from)+" - "+ selected.sdf.format(selected.to)+"</p>");

        Visit visit = new Visit(user.login, selected.tid, selected.pid, selected.from, selected.to);
        user.pendingVisits.add(visit);

        out.print("<p>Visit at " + selected.houseName + " during the selected reservation has been\n" +
                "added to your cart. Select another reservation to record a visit or press commit confirm and exit.\"</p>");
        out.print("<form method=\"get\">" +
                "<input name=\"type\" value=\"checkout\" hidden>" +
                "<input name=\"index\" value=\"0\" hidden>" +
                "<input type=submit value=\"Checkout\">" +
                "</form>");
    }
    String type = (String)request.getParameter("type");
    if(type != null && type.equals("checkout")){

        // User has no reservations. Return
        if(user.pendingVisits.size() == 0){
            out.print("<p>You have no visits to confirm.</p>");
        }

        out.print("<p>Pending visits:</p>");
        // Start new table
        out.print("<table id=\"t01\">\n" +
                "    <tr>\n" +
//                "        <th>Index</th>\n" +
                "        <th>From</th>\n" +
                "        <th>To</th>\n" +
                "    </tr>");

        // Print pending reservations table
        for (int i = 0; i < user.pendingVisits.size(); i++) {
            Visit v = user.pendingVisits.get(i);
            out.print("<tr>");
//            out.print("<td><a href=\"?choice="+ (i+1) +"\">"+(i+1)+"</a></td>");
            out.print("<td>" + v.from.toString() + "</td>");
            out.print("<td>" + v.to.toString() + "</td>");
            out.print("</tr>");
        }
        out.print("</table>");

        // Button to commit visits
        out.print("<form method = \"post\">" +
                  "      <input name=submit value=\"yes\" hidden>" +
                "   <input type=submit value=\"Commit\">" +
                "   </form>");

//        int choice = 0;
//        if(request.getParameter("choice") != null)
//            choice = Integer.parseInt(request.getParameter("choice"));
//
//        if(choice != 0){
//            user.pendingReservations.remove(choice - 1);
//            out.print("<p>Reservation removed.</p>");
//        }

        String commit = (String) request.getParameter("submit");

        if (commit != null && commit.equals("yes")) { // Insert all reservations
            for(Visit visit : user.pendingVisits)
                visit.insert();
            out.print("<p>Your reservations have been confirmed.</p>");

            user.pendingVisits.clear();
        }
    }
%>
<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
