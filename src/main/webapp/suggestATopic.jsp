<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 29.05.2021
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <style>
    button:hover {
      background-color: silver;
    }
  </style>
    <title>Suggest a topic</title>
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
<div class="container-fluid">

<form action="createSuggestedTopic" method="post">
  <b>Name -> </b><input name="name"/><br/><br/>
  <b>Date -> </b><input name="date"/> Your date may not be equals to date, that admin will choose<br/><br/>
  <b>language -> </b><input name="language"/><br/><br/>
  <b>description -> </b><input name="description"/><br/><br/>
  <b></b><input hidden name="meeting_name" value="suggested_topics"/><br/><br/>
  <button type="submit" class="btn btn-secondary">Suggest topic</button>
</form>
</div>
</body>
</html>
