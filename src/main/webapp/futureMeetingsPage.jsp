<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/mytags.tld" prefix="m" %>
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
Current Date and Time is: <m:today/>
<br>
<br>
<jsp:useBean id="futureMeetings" scope="request" type="java.util.List"/>
<c:forEach var="meeting" items="${futureMeetings}">
    ${meeting.id} |
    ${meeting.name} |
    ${meeting.date} |
    ${meeting.language} |
    <br/>
</c:forEach>
<form action="futureMeetingsPage.jsp">
    <input type="submit" name="submit1" value="Go back">
</form>
</body>
</html>
