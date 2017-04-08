<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Uotel</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<%
  String searchAttribute = request.getParameter("login");
  if( searchAttribute == null )
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


<a href="register.jsp">Register</a><br>

</body>
</html>
