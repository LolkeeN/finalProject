<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (request.getParameter("submit1") != null) {
%>
<jsp:forward page="speakerPage.jsp"></jsp:forward>
<%
        return;
    }
%>
<html>
<head>
    <title>All your meetings</title>
</head>
<body>

<jsp:useBean id="topics" scope="request" type="java.util.List"/>
<c:forEach var="topic" items="${topics}">
    ${topic.name}
    ${topic.date}
    <br/>
</c:forEach>


<form action="speakerMeetings.jsp">
    <input type="submit" name="submit1" value="Go back">
</form>
</body>
</html>
