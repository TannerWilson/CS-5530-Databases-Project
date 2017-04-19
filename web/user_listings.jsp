<%@ page import="com.u58.Th" %>
<%@ page import="com.u58.ThManager" %><%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/table.css" rel="stylesheet" type="text/css">
<head>
    <title>My Properties</title>
</head>
<body>
<%
    ThManager thManager = new ThManager();
    thManager.getUserProperties((String)session.getAttribute("login"));
%>
<table class="t01">
    <tr>
        <th>ID</th>
        <th>Property Name</th>
        <th>Address</th>
        <th>Owner</th>
        <th>Category</th>
        <th>Phone Number</th>
        <th>URL</th>
        <th>Year Built</th>
    </tr>
    <%
            for (Th th : thManager.properties.values()) {
            out.print("<tr>");
            out.print("<td><a href=\"?selection="+th.tid+"\">" + th.tid + "</a></td>");
            out.print("<td>" + th.name + "</td>");
            out.print("<td>" + th.address + "</td>");
            out.print("<td>" + th.owner + "</td>");
            out.print("<td>" + th.category + "</td>");
            out.print("<td>" + th.phoneNum + "</td>");
            out.print("<td>" + th.url + "</td>");
            out.print("<td>" + th.yearBuilt + "</td>");
            out.print("</tr>");
        }
    %>
</table>
<%
    String id = request.getParameter("selection");
    Th selected;
    if(id != null) {
        selected = thManager.properties.get(Integer.parseInt(id));
%>
<br> Edit Property information
<br> Selected Property:
<%
        out.print(selected.name + "<br>");
        out.print("<form method=\"post\">");
        out.print(" <label>\n" +
                "        New Name\n" +
                "        <input name=\"newName\" type=\"text\">\n" +
                "    </label><br>");
        out.print(" <label>\n" +
                "        New Category:\n" +
                "        <input name=\"newCat\" type=\"text\">\n" +
                "    </label><br>");
        out.print(" <label>\n" +
                "        New Address:\n" +
                "        <input name=\"newAdd\" type=\"text\">\n" +
                "    </label><br>");
        out.print(" <label>\n" +
                "        New Phone Number:\n" +
                "        <input name=\"newPhone\" type=\"text\">\n" +
                "    </label><br>");
        out.print("<input type=submit value=\"Update\">");
        out.print("</form>");

        session.setAttribute("selectedTid", selected.tid); // Store selected tid for if t user uses this link
        out.print("<p><a href=\"add_period.jsp\">Add Avaliable period for this property</a></p>");

        String name = request.getParameter("newName");
        String category = request.getParameter("newCat");
        String address = request.getParameter("newAdd");
        String phone = request.getParameter("newPhone");

        // User has subitted an update
        if(name != null || category != null || address != null || phone != null){
            if(name != null && !name.equals(""))
                selected.updateField("name", name, selected.tid);
            if(category != null && !category.equals(""))
                selected.updateField("category", category, selected.tid);
            if(address != null && !address.equals(""))
                selected.updateField("address", address, selected.tid);
            if(phone != null && !phone.equals(""))
                selected.updateField("phone_num", phone, selected.tid);
         out.print("<br>Update Sucessful!");
        }
    }
%>
<p>
    <a href="mainmenu.jsp">Main Menu</a>
</p>
</body>
</html>
