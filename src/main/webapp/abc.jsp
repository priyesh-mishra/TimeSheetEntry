<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
      <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
    <title>Add a project</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
  <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>
    
    </head>
    <body>
          <h2>Welcome ${pageContext.request.userPrincipal.name} | 
             
      
        <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
      
        <form:form method="POST" action="${contextPath}/abc" modelAttribute="projectForm" class="form-signin">
              <h3 class="form-signin-heading"> Enter The Project Details</h3>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="projCode" placeholder="Project Code"/>
                              <form:errors path="projCode"></form:errors>
              
            </div>
               
                 <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="projName" placeholder="Project Name"/>
                </div>
                 <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="location" placeholder="Project Location"/>
                 </div>
                   <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="projDesc" placeholder="Project Description"/>
                
                 </div>
                   <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="projOwner" placeholder="Project Owner"/>
                 </div>
                   <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input  class="form-control" path="startDate" type="text" id="datepicker" placeholder="Start Date"/>
                     </div>
                     <div>              
					     <input class="btn btn-lg btn-primary btn-block" type="submit" value="Submit"/>
                </div>
                
        </form:form>
    </body>
</html>