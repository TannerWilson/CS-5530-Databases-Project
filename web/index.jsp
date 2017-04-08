<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.gradeycullins.*" %>
<html>
<head>
<title>Uotel</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<%
    String login = request.getParameter("login");
    String password = request.getParameter("password");

    if( login == null && password == null ){
%>
<form>
    Login:
    <input type=text  name="login"  />
    <br>
    Password:
    <input  type=password name="password" />
    <br>
    <input type=submit>
    </form>

<%
    }else {
        User tempUser = new User(login, password);
        if (!tempUser.login(login, password))
            System.out.println("Login failed. Incorrect username or password");
        else {
            // Go to menu page
//            String redirectURL = "http://localhost:8080/mainmenu.jsp";
//            response.sendRedirect(redirectURL);
            response.setStatus(301);
            response.setHeader("Location", "http://localhost:8080/mainmenu.jsp/");
            response.setHeader("Connection", "close");
        }
    }
%>


<a href="register.jsp">Register</a><br>

</body>
</html>
