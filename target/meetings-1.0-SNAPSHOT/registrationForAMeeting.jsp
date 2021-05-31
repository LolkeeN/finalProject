<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Registration for a meeting</title>
</head>
<body>
<jsp:useBean id="meetings" scope="request" type="java.util.List"/>
<c:forEach var="meeting" items="${meetings}">
    ${meeting.id} |
    ${meeting.name} |
    ${meeting.date} |
    ${meeting.language} |
    <br/>
</c:forEach>
<form action="meetingRegistration" method="get">
    <b>Choose meeting id for registration -> </b><input name="meeting_id"/><br/>
    <input type="submit" value="Registration"/>
</form>
<form action="meetingParticipation" method="get">
    <b>Choose meeting id to take part -> </b><input name="meeting_id"/><br/>
    <input type="submit" value="Take part!"/>
</form>
</body>
</html>
