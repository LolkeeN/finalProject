<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getParameter("submit1") != null) {
        if ((int)request.getSession().getAttribute("role") == 1){
%>
<jsp:forward page="mainPage.jsp"></jsp:forward>
<% }else if ((int)request.getSession().getAttribute("role") == 3){
            %>
<jsp:forward page="speakerPage.jsp"></jsp:forward>
<% }else{
%>
<jsp:forward page="adminPage.jsp"></jsp:forward>
<%
        return;
    }
    }
%>
<html>
<head>
    <title>Whoops</title>
</head>
<body>
<%= request.getSession().getAttribute("errorMessage") %>
<form action="errorPage.jsp">
    <input type="submit" name="submit1" value="Go back">
</form>
</body>
</html>
