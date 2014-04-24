<%--
  Created by IntelliJ IDEA.
  User: undancer
  Date: 14-4-19
  Time: 下午12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%= request.getRequestURI()%>
<form method="POST">
    <input name="username" type="text" value="admin"/>
    <input name="password" type="text" value="admin"/>
    <button type="submit"/>
</form>
</body>
</html>
