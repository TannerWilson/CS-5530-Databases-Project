<%@ page import="java.util.ArrayList" %>
<%@ page import="com.u58.UserManager" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/table.css" rel="stylesheet" type="text/css">
<head>
    <title>Admin</title>
</head>
<body>
Search the most trusted or useful users<br>
Select the number of users you wish to limit the search to and press the corresponding button<br>
<form method="post">
    <input name="n" type="number" step="1">
    <input name="type" value="trust" hidden>
    <input type=submit value="Most Trusted">
</form>
<form method="post">
    <input name="n" type="number" step="1">
    <input name="type" value="useful" hidden>
    <input type=submit value="Most Useful">
</form>

<%
    String type = (String) request.getParameter("type");
    if(!type.equals("")){
%>
<br> Selected users: <br>
<table id="t01">
    <tr>
        <th>Login</th>
    </tr>
<%
        int n = (Integer) request.getParameter("n");
        UserManager manager = new UserManager();

        if(type.equals("trust")){
            ArrayList<String> topTrusted = manager.getMostTrustedUsers(n);

            for (String login : topTrusted)
                out.print("<tr><td>"+login+"</td></tr>");

        }else if(type.equals("useful")){
            ArrayList<String> topUsefull =  manager.getMostUsefulUsers(n);

            for (String login : topUsefull)
                out.print("<tr><td>"+login+"</td></tr>");
        }
%>
</table>
<%
    } // Close of if
%>
</body>
</html>
