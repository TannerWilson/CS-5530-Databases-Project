<%@ page import="u58.FeedbackRating" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/24/17
  Time: 8:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feedback</title>

</head>
<body>
<%
    if (request.getParameter("fid") != null) {
        session.setAttribute("selectedFid", request.getParameter("fid"));
    }

    if (request.getParameter("usefulness") != null) {
        FeedbackRating.insertFeedbackRating((String) session.getAttribute("login"), Integer.valueOf((String) session.getAttribute("selectedFid")),
                Integer.valueOf(request.getParameter("usefulness")));
        String rating = "";
        int rValue = Integer.valueOf(request.getParameter("usefulness"));
        switch (rValue) {
            case 0:
                rating = "useless";
                break;
            case 1:
                rating = "useful";
                break;
            case 2:
                rating = "very useful";
                break;
        }
        out.print("<p>Marked feedback as " + rating + "<p>");
    }
%>
<p>Rate the feedback</p>
<form>
    <label>
        <input type="radio" name="usefulness" value="0"> useless <br>
        <input type="radio" name="usefulness" value="1"> useful <br>
        <input type="radio" name="usefulness" value="2"> very useful
    </label><br>
    <label>
        <input type="submit" value="submit rating">
    </label>
</form>

</body>
</html>
