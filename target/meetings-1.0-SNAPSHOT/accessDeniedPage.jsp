<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("submit1") != null) {
%>
<jsp:forward page="loginPage.jsp"></jsp:forward>
<%
        return;
    }
%>
<html>
<head>
    <title>ACCESS DENIED</title>
</head>
<body>
ACCESS DENIED!
<form action="loginPage.jsp">
    <input type="submit" name="submit1" value="Go back">
</form>
</body>
</html>
