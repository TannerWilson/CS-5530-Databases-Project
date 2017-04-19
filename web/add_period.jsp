<%@ page import="com.u58.Period" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.StringJoiner" %><%--
  Created by IntelliJ IDEA.
  User: Tanner
  Date: 4/17/2017
  Time: 12:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Avail Period</title>
</head>
<body>
Add available period <br>
Enter all of the feilds bellow
<form action="add_period.jsp" method="post">
    <label>
        Start Date: Format: YYYY-MM-DD
        <input name="start" type="text">
    </label><br>
    <label>
        End Date: Format: YYYY-MM-DD
        <input name="end" type="text">
    </label><br>
    <label>
        Price Per Night:
        <input name="price" type="text">
    </label><br>
    <input type="submit" value="Add">
</form>
<%
    String startDate = request.getParameter("start");
    String endDate = request.getParameter("end");
    String price = (String) request.getParameter("price");
    int tid = (Integer) session.getAttribute("selectedTid");

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

    if(price != null && !startDate.equals("")){
        Period newPeriod = new Period(tid, from, to, Integer.parseInt(price));
        newPeriod.insert();
        out.print("<br> Period Added!");
    }
%>
<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
