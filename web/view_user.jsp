<%@ page import="u58.User" %>
<%@ page import="u58.UserManager" %>
<%@ page import="java.util.List" %>
<%@ page import="u58.Trust" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
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
    if (request.getParameter("login") == null)
        response.sendRedirect("mainmenu.jsp");

    User selectedUser = new User();

    UserManager userManager = new UserManager();
    List<User> users = userManager.getAllUsers();
    for (User u : users) {
        if (u.login.equals(request.getParameter("login"))) {
            selectedUser = u;
            break;
        }
    }

    out.print("you selected: " + selectedUser.login);

    if (request.getParameter("isTrusted") != null) {
        String isTrusted = request.getParameter("isTrusted");
        User truster = (User) session.getAttribute("user");
        User trustee = selectedUser;
        Trust.addTrustRelationship(truster, trustee, (isTrusted.equals("trusted") ? 1 : 0));
        out.print("<p>" + request.getParameter("isTrusted") + "</p>");
    }
%>
<form action="" method="post">
    <label>
        trusted
        <input type="radio" name="isTrusted" value="trusted">
    </label><br>
    <label>
        untrusted
        <input type="radio" name="isTrusted" value="untrusted">
    </label><br>
    <label>
        <input type="submit" value="update">
    </label>
</form>
</body>
</html>
