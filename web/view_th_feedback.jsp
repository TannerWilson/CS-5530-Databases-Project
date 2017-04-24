<%@ page import="u58.Feedback" %>
<%@ page import="java.util.Map" %>
<%@ page import="u58.Th" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/20/17
  Time: 9:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feedback</title>
</head>
<link href="css/table.css" rel="stylesheet" type="text/css">
<body>
<%
    String n = request.getParameter("n");
    if (n == null) {
        response.sendRedirect("/");
    } else {
        List<Feedback> feedbacks = new LinkedList<>();

        if (n.equals("")) {
            out.print("<p>Showing all feedback</p>");
            feedbacks = Feedback.getThFeedback((Th) session.getAttribute("chosenTh"));
        } else {
            out.print("<p>Showing at most " + n + " feedbacks for property: " + session.getAttribute("selectedThName") + "</p>");
            int selectedTh = (int) session.getAttribute("selectedTh");
            feedbacks = Feedback.getNMostUsefulFeedbacks(selectedTh, Integer.valueOf(n));
        }

        if (feedbacks.size() == 0) {
            out.print("<p>There are no recorded feedbacks for this TH.</p>");
        } else {
%>
<table class="t01">
    <tr>
        <th>fid</th>
        <th>author</th>
        <th>score</th>
        <th>description</th>
        <th>average usefulness</th>
    </tr>
    <%
                for (Feedback f : feedbacks) {
                    out.print("<tr>");
                    if (f.login.equals((String) session.getAttribute("login"))) { // can't rate own feedback
                        out.print("<td>" + f.fid + "</td>");
                    } else {
                        out.print("<td><a href=\"/view_feedback.jsp?fid=" + f.fid + "\">" + f.fid + "</a></td>");
                    }
                    out.print("<td>" + f.login + "</td>");
                    out.print("<td>" + f.score + "</td>");
                    out.print("<td>" + f.description + "</td>");
                    out.print("<td>" + f.averageUsefulness + "</td>");
                    out.print("</tr>");
                }
            }
        }
    %>
</table>
<%--System.out.format("%s\t|%20s\t|%20s\t|%50s\t|%20s %n",--%>
<%--"fid", "author", "score", "description", "average usefulness");--%>
<%--for (Feedback f : feedbacks.values()) {--%>
<%--System.out.format("%d\t|%20s\t|%20d\t|%50s\t|%20f %n",--%>
<%--f.fid, f.login, f.score, f.description, f.averageUsefulness);--%>

</body>
</html>
