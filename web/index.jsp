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
    <input type="text"  name="login" method=get onsubmit="check_all_fields(this)"/>
    <br>
    Password:
    <input  type="password" name="password" method=get onsubmit="return check_all_fields(this)" action="mainmenu.jsp"/>
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
        }
    }
%>


<a href="register.jsp">Register</a><br>

</body>
</html>
