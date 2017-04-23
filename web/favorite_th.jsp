<%@ page import="u58.Th" %>
<%@ page import="u58.Favorite" %>
<%@ page import="u58.User" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/20/17
  Time: 9:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Favorited</title>
</head>
<body>
<%
    Th selected = (Th) session.getAttribute("selectedTh");
    User user = (User) session.getAttribute("user");
    Favorite.insertFavorite(user.login, selected.tid);
    out.print("<p> The property: "+selected.name +" has been marked as your favorite.</p>");
%>
<p>
    <a href="search.jsp">Search properties</a>
</p>
<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
