<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (request.getParameter("submit1") != null) {
%>
<jsp:forward page="adminPage.jsp"></jsp:forward>
<%
        return;
    }
%>
<html>
<head>
    <title>Future meetings</title>
</head>
<body>
<jsp:useBean id="pastMeetings" scope="request" type="java.util.List"/>
<c:forEach var="meeting" items="${pastMeetings}">
    ${meeting.id} |
    ${meeting.name} |
    ${meeting.date} |
    ${meeting.language} |
    <br/>
</c:forEach>
<form action="pastMeetingsPage.jsp">
    <input type="submit" name="submit1" value="Go back">
</form>
</body>
</html>
