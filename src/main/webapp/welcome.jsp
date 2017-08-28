<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | 
       <span>${success}</span>
        <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
<c:url value="/fileUpload" var="fileUpload" />

  <h1>Please upload a file</h1>
        <form method="post" action="${contextPath}/fileUpload" enctype="multipart/form-data">
         
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="text" name="name"/>
            <input type="file" name="file"/>
            <input type="submit"/>
        </form>
        
         <form action="${contextPath}/readPOI">
           <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
           <input type="text" name="name"/>
        <input type="submit" value="Display file content" />
    </form>
        
        <c:if test="${not empty data}">
    <table style="border: 1px solid black; border-collapse: collapse;">
        <c:forEach items="${data}" var="row">
            <tr>
                <c:forEach items="${row.value}" var="cell">
                    <td style="border:1px solid black;height:20px;width:100px;
                      background-color:${cell.bgColor};color:${cell.textColor};
                      font-weight:${cell.textWeight};font-size:${cell.textSize}pt;">
                      ${cell.content}
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>
        
        
        
        
        
        
        
        
        
        
        
    </c:if>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
