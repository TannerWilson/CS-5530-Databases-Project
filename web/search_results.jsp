<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="u58.ThManager" %>
<%@ page import="u58.Th" %><%--
  Created by IntelliJ IDEA.
  User: Gradey Cullins
  Date: 4/19/17
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
</head>
<link href="css/table.css" rel="stylesheet" type="text/css">
<body>
<%
    int minPrice = -1;
    int maxPrice = -1;
    String city = "";
    String state = "";
    List<String> keywords = new LinkedList();
    String category = "";
    int order = 0;

    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
        String paramName = paramNames.nextElement();
        String paramValue = request.getParameter(paramName);

        // parse the filter arguments
        if (paramValue != "") {
            switch (paramName) {
                case "min_price":
                    minPrice = Integer.parseInt(paramValue);
                    break;
                case "max_price":
                    maxPrice = Integer.parseInt(paramValue);
                    break;
                case "city":
                    city = paramValue;
                    break;
                case "state":
                    state = paramValue;
                    break;
                case "keywords":
                    String[] words = paramValue.split(" ");
                    keywords.addAll(Arrays.asList(words));
                    break;
                case "category":
                    category = paramValue;
                    break;
                case "ordering":
                    order = Integer.parseInt(paramValue);
                    break;
            }
        }
    }

    ThManager thManager = new ThManager();
    thManager.getTh(minPrice, maxPrice, "", "", city, state, keywords, category, order);

    if (thManager.properties.isEmpty()) {
        out.print("<p>No housing exists that matches your query</p>");
    } else {
%>
<table class="t01">
    <tr>
        <th>tid</th>
        <th>name</th>
        <th>owner</th>
        <th>lowest price</th>
        <th>address</th>
        <th>average score</th>
    </tr>
    <%
            for (Integer i : thManager.order) {
                Th currentTh = thManager.properties.get(i);
                int lowestPrice = currentTh.lowestPrice;
                out.print("<tr>");
                out.print("<td><a href=\"view_th.jsp?tid=" + currentTh.tid + "\">" + currentTh.tid + "</a></td>");
                out.print("<td>" + currentTh.name + "</td>");
                out.print("<td>" + currentTh.owner + "</td>");
                out.print("<td>" + currentTh.lowestPrice + "</td>");
                out.print("<td>" + currentTh.address + "</td>");
                out.print("<td>" + currentTh.averageScore + "</td>");
                out.print("</tr>");
            }
        }
    %>
</table>

</body>
</html>
