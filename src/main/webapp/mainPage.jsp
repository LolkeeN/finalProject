<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 26.05.2021
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meetings</title>
</head>
<body>
<%
if (request.getParameter("firstName") == null && request.getParameter("lastName") == null){
    out.println("Welcome, " + request.getAttribute("firstName") + " " + request.getAttribute("lastName"));
}else{
out.println("Welcome," + request.getParameter("firstName") + " " + request.getParameter("lastName"));
}
%>
<hr>
<a href="registrationForAMeeting.jsp">Registration for a meeting</a>
</body>
</html>
