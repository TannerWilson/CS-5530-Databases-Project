<%--
  Created by IntelliJ IDEA.
  User: Gradey
  Date: 4/11/17
  Time: 4:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search for TH</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/search_results.jsp" method="get">
        <label>
            min price
            <%--<input type="checkbox" name="min_price_filter">--%>
            <input type="number" name="min_price" min="0" max="10000">
        </label><br>
        <label>
            max price
            <%--<input type="checkbox" name="max_price_filter">--%>
            <input type="number" name="max_price" min="0" max="10000">
        </label><br>
        <label>
            city
            <%--<input type="checkbox" name="city_filter">--%>
            <input type="text" name="city">
        </label><br>
        <label>
            state
            <%--<input type="checkbox" name="state_filter">--%>
            <input type="text" name="state">
        </label><br>
        <label>
            keywords
            <%--<input type="checkbox" name="keywords_filter">--%>
            <input type="text" name="keywords">
        </label><br>
        <label>
            category
            <%--<input type="checkbox" name="category_filter">--%>
            <input type="text" name="category">
        </label><br>

        <%--result orderings --%>
        <label>
            <input type="radio" name="ordering" value="1" checked>descending price<br>
            <input type="radio" name="ordering" value="2">ascending price<br>
            <input type="radio" name="ordering" value="3">
            descending average feedback rating<br>
            <input type="radio" name="ordering" value="4">
            ascending average feedback rating<br>
            <input type="radio" name="ordering" value="5">
            descending average feedback rating by trusted users<br>
            <input type="radio" name="ordering" value="6">
            ascending average feedback rating by trusted users
        </label><br>
        <label>
            <input type="submit" value="search">
        </label>
    </form>
</body>
</html>
