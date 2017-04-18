<%@ page import="com.u58.User" %>
<%@ page import="com.u58.UserManager" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: greybonez
  Date: 4/18/17
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
    <%
        UserManager userManager = new UserManager();
        List<User> users = userManager.getAllUsers();
        User user = new User();
        for (User u : users) {
            if (u.login.equals(request.getParameter("login"))) {
                user = u;
                break;
            }
        }
        out.print(user.login);
    %>
</body>
</html>
