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
        <th>House Name</th>
        <th>From</th>
        <th>To</th>
        <th>Cost</th>
    </tr>
    <%
        User user = session.getAttribute("user");
        user.getReservations(); // Pull all reservations this user has made

        for (int i = 0; i < user.currentReservations.size(); i++) {
            Reservation r = user.currentReservations.get(i);
            out.print("<tr>");
            out.print("<td><a href=\"/index="+ i+1 +"></a></td>");
            out.print("<td>" + r.houseName + "</td>");
            out.print("<td>" + r.from.toString() + "</td>");
            out.print("<td>" + r.to.toString() + "</td>");
            out.print("<td>" + r.cost + "</td>");
            out.print("</tr>");
        }
    %>
</table>
<%
    int index = Integer.parseInt(request.getParameter("index"));

    if(index != 0){
        Reservation selected =  user.currentReservations.get(index-1);
        out.print("<p>Record a visit on selected reservation</p>");
        out.print("<p>Selected reservation: "+selected.from.toString()+" - "+ selected.to.toString()+"</p>");

        Visit visit = new Visit(user.login, selected.tid, selected.pid, selected.from, selected.to);
        user.pendingVisits.add(visit);

        out.print("<p>Visit at " + selected.houseName + " during the selected reservation has been\n" +
                "added to your cart. Select another reservation to record a visit or press commit confirm and exit.\"</p>");
        out.print("<form method=\"post\">" +
                "<input name=\"type\" value=\"checkout\" hidden>" +
                "<input type=submit value=\"Add\">" +
                "</form>");
    }
    if(((String)request.getParameter("type")).equals("checkout")){

        // User has no reservations. Return
        if(user.pendingReservations.size() == 0){
            out.print("<p>You have no reservations to confirm.</p>");
        }

        // Print pending reservations table
        for (int i = 0; i < user.pendingReservations.size(); i++) {
            Reservation r = user.pendingReservations.get(i);
            out.print("<table id=\"t01\">\n" +
                    "    <tr>\n" +
                    "        <th>House Name</th>\n" +
                    "        <th>From</th>\n" +
                    "        <th>To</th>\n" +
                    "        <th>Total Cost</th>\n" +
                    "    </tr>");
            out.print("<tr>");
            out.print("<td><a href=\"/choice="+ i+1 +"></a></td>");
            out.print("<td>" + r.houseName + "</td>");
            out.print("<td>" + r.from.toString() + "</td>");
            out.print("<td>" + r.to.toString() + "</td>");
            out.print("<td>" + r.cost + "</td>");
            out.print("</tr>");
        }
        out.print("</table>");

        // Button to commit visits
        out.print("<form method = \"post\"" +
                  "      <input name=submit value=\"yes\" hidden" +
                "   <input type=submit value=\"Commit\"" +
                "   </form>");

        int choice = Integer.parseInt(request.getParameter("choice"));
        if(choice != 0){
            user.pendingReservations.remove(choice - 1);
            out.print("<p>Reservation removed.</p>");
        }

        String commit = (String) request.getParameter("submit");

        if (commit.equals("yes")) { // Insert all reservations
            for(Reservation res : user.pendingReservations)
                res.insert();
            out.print("<p>Your reservations have been confirmed.</p>");

            // Get suggested properties to print
            ThManager manager = new ThManager();
            ArrayList<String> suggestedProperties = manager.getSuggestedProperties(user.pendingReservations, this);

            out.print("<br>You may also enjoy a stay at the folowing properties:<br>");
            for(String property : suggestedProperties)
                out.print("<p>"+property+"</p>");
            out.print("Try searching for these to get more information!");

            user.pendingReservations.clear();
        }
    }
%>
</body>
</html>
