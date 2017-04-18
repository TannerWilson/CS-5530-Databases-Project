<%--
  Created by IntelliJ IDEA.
  User: greybonez
  Date: 4/18/17
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<%
    session.removeAttribute("user");
    session.invalidate();
    response.sendRedirect("/");
%>
</body>
</html>
