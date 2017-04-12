<%@ page import="com.u58.User" %><%--
  Created by IntelliJ IDEA.
  User: Tanner
  Date: 4/7/2017
  Time: 10:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Menu</title>
</head>
<body>
<p>
    Homepage
</p>
<%
    String type = request.getParameter("type");
    User user = new User();

    if (type.equals("login")) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User tempUser = new User(login, password);

        if (!tempUser.login(login, password)) {
            response.sendRedirect("/");
            return;
        } else {
            user = tempUser;
            out.print("<p>Login successful!</p><p>Welcome back, " + tempUser.firstName + "</p>");
        }
    } else if (type.equals("register")) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String middle_name = request.getParameter("middle_name");
        String last_name = request.getParameter("last_name");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        User tempUser = new User(login, password, first_name, middle_name, last_name, gender, address);

        if (tempUser.register()) { // successful reg
            user = tempUser;
            out.print("<p>Register successful!</p>");
        } else { // failed reg
            response.sendRedirect(response.encodeRedirectURL("/"));
            out.print("<p>Register failed. Try again.</p>");
        }
    }
    session.setAttribute("login", user.login); // Save user login to session
%>
<!-- main menu -->
<div>
    <p>
        <a href="search.jsp">Search properties</a>
    </p>
    <p>
        <a href="create_th.jsp">Add property</a>
    </p>
    <p>
        <a href="user_listings.jsp">Show my listed properties</a>
    </p>
    <p>
        <a href="list_users.jsp">List users</a>
    </p>
    <p>
        <a href="reservations.jsp">Show my reservations</a>
    </p>
    <%
        if (user.login.equals("admin")) {
            out.print("<p><a href=\"admin.jsp\">Admin control panel</a></p>");
        }
    %>
</div>
</body>
</html>
