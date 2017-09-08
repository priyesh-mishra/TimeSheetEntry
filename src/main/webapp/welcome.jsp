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
    <link href="${contextPath}/resources/css/menu_style.css" rel="stylesheet">
     <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
     <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
     
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
      
       <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
         </c:if>
 </div>
<div id="wrap"> <ul class="navbar">
<li>
 <a href="${contextPath}/welcome">Home</a></li>
 <li> <a href="#" >Projects</a>
 <ul>
	 <li> <a href="${contextPath}/abc" >Add Project</a></li>
	 <li> <a href="#">Edit Project</a></li>
 </ul>
 </li>
 <li> <a href="#" >Timesheet</a>
 <ul>
	 <li> <a href="${contextPath}/timesheet" >Add Record</a></li>
	 <li> <a href="${contextPath}/searchRecord">Search Record</a></li>
 </ul>
 </li>
 <li> 
 <a href="#" >Modify Record</a></li>
 
 <li><a href="#">Contact Us</a></li></ul></div>        
 <div class="container">
<c:url value="/fileUpload" var="fileUpload" />

  <h3>Please upload a file</h3>
        <form class="w3-container" method="post" action="${contextPath}/fileUpload" enctype="multipart/form-data" >
          <span>${success}</span>
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="text" name="name"/>
                 
            
            <input type="file" name="file"/>
            <input type="submit"/>
        </form>
        <br>
       
         <form action="${contextPath}/readPOI" class="w3-container" >
           <input class="w3-input" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<!--            <input type="text" name="name"/> -->
  <select name="selectedFile">
				    <c:forEach var="line" items="${fileList}">
				        <option><c:out value="${line}"/></option>
				    </c:forEach>
 		</select>
        <input type="submit" value="Display file content" />
    </form>
    
        <c:if test="${not empty data}">
        <br>
    <table>
          <c:forEach items="${data}" var="row" varStatus="status">
          <c:choose>
 			<c:when test="${status.first}">
           <thead style="background-color: #4CAF50; color: white;">
                <tr>
                <c:forEach items="${row.value}" var="cell">
                    <td>
                       ${cell.content}
                    </td>
                </c:forEach>
            </tr>
            </thead>
             </c:when>
         <c:otherwise>
            <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
                <c:forEach items="${row.value}" var="cell">
		     		  <td>
                       ${cell.content}
                   	 </td>
                   	 
                </c:forEach>
            </tr>
         </c:otherwise>
        </c:choose>
       </c:forEach>
        
    </table>
</c:if>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
