<%@ page import="u58.User" %><%--
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
<h1>Homepage</h1>
<%
    if (session.getAttribute("user") != null) { // user is logged in for a session
        User user = (User) session.getAttribute("user");
        out.print("<p>Login successful!</p><p>Welcome back, " + user.firstName + "</p>");
    } else { // user needs to login or register
        // register or login
        String type = request.getParameter("type");

        // session object
        User user = new User();

        if (type != null && type.equals("login")) { // user is logging in
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            User tempUser = new User(login, password);

            if (!tempUser.login(login, password)) {
                response.sendRedirect("/");
                return;
            } else {
                user = tempUser;
                out.print("<p>Login successful!</p><p>Welcome back, " + user.firstName + "</p>");
            }
        } else if (type != null && type.equals("register")) { // user is registering
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

        // after login / register, store the session object
        session.setAttribute("user", user);
        session.setAttribute("login", user.login);// Save user login to session
    }
%>
<!-- main menu -->
<div>
    <%
        out.print("<a href=\"logout.jsp\">logout</a>");
    %>
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
        if (session.getAttribute("user") != null && ((String) session.getAttribute("login")).equals("admin")) {
            out.print("<p><a href=\"admin.jsp\">Admin control panel</a></p>");
        }
    %>
</div>
</body>
</html>
