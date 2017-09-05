<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Add a project</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
 <form:form method="POST" modelAttribute="projectForm" class="form-signin">
         <h2 class="form-signin-heading">Add new project</h2>
        <spring:bind path="projName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="projName" class="form-control" placeholder="Project Name"
                            autofocus="true"></form:input>
                <form:errors path="projName"></form:errors>
            </div>
        </spring:bind>
 		<spring:bind path="projCode">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="projCode" class="form-control" placeholder="Project Code"
                            autofocus="true"></form:input>
                <form:errors path="projCode"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="projDesc">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="projDesc" path="projDesc" class="form-control" placeholder="Project Description"></form:input>
                <form:errors path="projDesc"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="location">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="location" path="location" class="form-control"
                            placeholder="Project Location"></form:input>
                <form:errors path="location"></form:errors>
            </div>
        </spring:bind>
        
         <spring:bind path="projOwner">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="projOwner" path="projOwner" class="form-control"
                            placeholder="Project Owner"></form:input>
                <form:errors path="projOwner"></form:errors>
            </div>
        </spring:bind>
        
        <spring:bind path="startDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="startDate" path="startDate" class="form-control"
                            placeholder="Project Location"></form:input>
                <form:errors path="startDate"></form:errors>
            </div>
        </spring:bind>
        
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
