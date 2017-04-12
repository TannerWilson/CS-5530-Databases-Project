<%@ page import="com.u58.Th" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create TH</title>
</head>
<body>
<%

        if(request.getParameter("type") != null && request.getParameter("type").equals("insert")){
            String login = (String) session.getAttribute("login");
            String name = request.getParameter("name");
            String propType = request.getParameter("propType");
            String phoneNum = request.getParameter("phone");
            String addr = request.getParameter("address");
            String url = request.getParameter("url");
            int year = Integer.parseInt(request.getParameter("year"));

            // Make new Th object for insertion
            Th newTh = new Th(login, name, propType, phoneNum, addr, url, year);

            if (newTh.insert()) {
                out.print("<p>New property registered to your account!</p>");
            } else {
                out.print("<p>Something went wrong. Unable to register property.</p>");
            }

        }else {
            %>
Lets register you a new property to manage. <br/>
<form action="create_th.jsp">
    <label>
        Name
        <input name="name" type="text">
    </label><br>
    <label>
        Property Type
        <input name="propType" type="text">
    </label><br>
    <label>
        Phone Number
        <input name="phone" type="text">
    </label><br>
    <label>
        Address
        <input name="address" type="text">
    </label><br>
    <label>
        URL
        <input name="url" type="text">
    </label><br>
    <label>
        Year Built
        <input name="year" type="text">
    </label><br>
    <label>
        <input name="type" value="insert" hidden>
    </label>
    <input type="submit" value="Insert">
</form>

<%} // end of else%>

</body>
</html>
