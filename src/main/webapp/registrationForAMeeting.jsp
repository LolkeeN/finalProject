<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        button:hover {
            background-color: silver;
        }
    </style>
    <title>Registration for a meeting</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div>
    <nav class="nav navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="loginPage.jsp" class="navbar-brand">Meetings</a>
            </div>

            <div>
                <ul class="nav navbar-nav">
                    <li><a href="CheckRoleAndRedirectServlet">Main page</a> </li>
                    <li><a href="aboutUs.jsp">About us</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<br/>
<jsp:useBean id="meetings" scope="request" type="java.util.List"/>
<c:forEach var="meeting" items="${meetings}">
    ${meeting.id} |
    ${meeting.name} |
    ${meeting.date} |
    ${meeting.language} |
    <br/>
</c:forEach>
<div class="container-fluid">
<form action="meetingRegistration" method="post">
    <b>Choose meeting id for registration -> </b><input name="meeting_id"/><br/><br/>
    <button type="submit" class="btn btn-secondary">Registration</button>
</form>
<form action="meetingParticipation" method="post">
    <b>Choose meeting id to take part -> </b><input name="meeting_id"/><br/><br/>
    <button type="submit" class="btn btn-secondary">Take part!</button>
</form>
</div>
</body>
</html>
