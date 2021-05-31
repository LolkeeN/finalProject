<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Registered and Participants count</title>
</head>
<body>
Meeting № <%=request.getParameter("meeting_id")%> participants count <%=request.getSession().getAttribute("participants_count")%>
<br>
Meeting № <%=request.getParameter("meeting_id")%> registered count <%=request.getSession().getAttribute("registered_count")%>
<form action="meetingParticipantsAndRegisteredCount.jsp">
    <input type="submit" name="submit1" value="Go back">
</form>
</body>
</html>
