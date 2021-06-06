<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="WEB-INF/mytags.tld" prefix="m" %>

<html>
<head>
    <title>Create meeting</title>
</head>
<body>
Current Date and Time is: <m:today/>
<br>
<br>
<form action="createMeeting" method="post">
    <b>Name -> </b><input name="meeting_name"/><br/>
    <b>Date -> </b><input name="date"/><br/>
    <b>Location id -> </b><input name="location_id"/><br/>
    <b>language -> </b><input name="language"/><br/>
    <input type="submit" value="Create meeting"/>
</form>
</body>
</html>
