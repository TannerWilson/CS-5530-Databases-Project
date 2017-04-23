<%@ page import="u58.Period" %>
<%@ page import="u58.Th" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="u58.Reservation" %>
<%@ page import="u58.User" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/20/17
  Time: 9:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make Reservation</title>
</head>
<body>
<%
    Th selected = (Th) session.getAttribute("selectedTh");
    selected.getAvailPeriods();
    User user = (User) session.getAttribute("user");

    out.print("<table id=\"t01\">\n" +
            "    <tr>\n" +
            "        <th>Index</th>\n" +
            "        <th>From</th>\n" +
            "        <th>To</th>\n" +
            "        <th>Price</th>\n" +
            "    </tr>");

    for (int i = 0; i < selected.periods.size(); i++) {
        Period p = selected.periods.get(i);
        out.print("<tr>");
        out.print("<td><a href=\"?index="+ (i+1) +"\">"+(i+1)+"</a></td>");
        out.print("<td>" + p.from.toString() + "</td>");
        out.print("<td>" + p.to.toString() + "</td>");
        out.print("<td>" + p.price + "</td>");
        out.print("</tr>");
    }
%>
</table>
<%
    String indexString = (String) request.getParameter("index");
    int index = 0;
    if(indexString != null && !indexString.equals(""))
        index = Integer.parseInt(indexString);

    Period selectedPeriod = new Period(-1, new Date(), new Date(), -1);
    if(index != 0 && index != -1) {
        selectedPeriod = selected.periods.get(index - 1);
        index = -1;
        out.print("<p>Make reservation during selected Period</p>");
        out.print("<p>Selected reservation: " + selectedPeriod.sdf.format(selectedPeriod.from) + " - " + selectedPeriod.sdf.format(selectedPeriod.to) + "</p>");
        out.print("<form>\n" +
                "    <label>\n" +
                "        Desired Check In Date: Format: YYYY-MM-DD\n" +
                "        <input name=\"start\" type=\"text\">\n" +
                "    </label><br>\n" +
                "    <label>\n" +
                "        Desired Check Out Date: Format: YYYY-MM-DD\n" +
                "        <input name=\"end\" type=\"text\">\n" +
                "    </label><br>\n" +
                "    <input name=\"index\" value=\"-1\" hidden>" +
                "    <input type=\"submit\" value=\"Add\">\n" +
                "</form>");
    }
    else {
        selectedPeriod = null;
    }

    String startDate = request.getParameter("start");
    String endDate = request.getParameter("end");

    if((startDate != null && endDate != null) && (!startDate.equals("") && !endDate.equals("")) ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDate += " 00:00:00";
        endDate += " 00:00:00";

        Date from = null;
        Date to = null;
        try {
            from = sdf.parse(startDate);
            to = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Reservation res = new Reservation(user.login, selected.tid, selectedPeriod.pid,
                from, to, selectedPeriod.price, selected.name);

        if (selectedPeriod.checkReservations(res)) {
            user.pendingReservations.add(res); // Add to cart
            out.print("<p>A reservation for this available period has been created and added to you cart.<br>" +
                    "Continue browsing if you wish to record more reservations.</p>\n");
        } else {
            out.print("<p>Sorry there is a reservation on this period that collides with your" +
                    " check in or check out date. Please try again.</p>");
        }
    }
%>
<p>
    <a href="search.jsp">Search Properties</a>
</p>
<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
