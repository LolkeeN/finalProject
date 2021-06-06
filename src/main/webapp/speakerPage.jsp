<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%if ((int)request.getSession().getAttribute("role") != 3){
%>
<jsp:forward page="accessDeniedPage.jsp"></jsp:forward>
<% }
%>
<html>
<head>
    <title>Speaker Page</title>
</head>
<body>
<%="Speaker page"%>
<hr>
<form action="speakerMeetings" method="get">
    <input type="submit" value="get your topics"/>
</form>
<hr>
<form action="getFreeTopics" method="get">
    <input type="submit" value="show free topics"/>
</form>
<hr>
<form action="suggestATopic.jsp" method="get">
    <input type="submit" value="suggest a topic"/>
</form>
<hr>
</body>
</html>
