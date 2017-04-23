<%@ page import="u58.Feedback" %>
<%@ page import="u58.User" %>
<%@ page import="u58.Th" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/20/17
  Time: 9:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Leave Feedback</title>
</head>
<body>

<form method="post">
    Enter score: [0-10]
    <input name="score" type="number" step="1">
    <br>
    Enter Description:
    <textarea name="desc" cols="40" rows="5"></textarea>
    <br>
    <input type="submit" value="Leave Feedback">
</form>
<%
    int score = -1;
    Object scoreTemp = request.getParameter("score");
    if(scoreTemp != null)
        score = Integer.parseInt(request.getParameter("score"));
    String description = request.getParameter("desc");
    User user = (User) session.getAttribute("user");
    Th selected = (Th) session.getAttribute("selectedTh");

    if(score != -1 && description != null) {
        Feedback newFeedback = new Feedback(user.login, score, description, 0, selected.tid);
        newFeedback.insert();
        out.print("<p>Feed back added</p>");
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
