<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/menu_style.css" rel="stylesheet">
     <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
     <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>Search records</title>
 <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  
  $( function() {
	    $( "#datepicker1" ).datepicker();
	  } );
  
  function fnExcelReport()
  {
      var tab_text = '<table border="1px" style="font-size:20px" ">';
      var textRange; 
      var j = 0;
      var tab = document.getElementById('recordTable'); // id of table
      var lines = tab.rows.length;

      // the first headline of the table
      if (lines > 0) {
          tab_text = tab_text + '<tr bgcolor="#DFDFDF">' + tab.rows[0].innerHTML + '</tr>';
      }

      // table data lines, loop starting from 1
      for (j = 1 ; j < lines; j++) {     
          tab_text = tab_text + "<tr>" + tab.rows[j].innerHTML + "</tr>";
      }

      tab_text = tab_text + "</table>";
      tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");             //remove if u want links in your table
      tab_text = tab_text.replace(/<img[^>]*>/gi,"");                 // remove if u want images in your table
      tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, "");    // reomves input params
      // console.log(tab_text); // aktivate so see the result (press F12 in browser)

      var ua = window.navigator.userAgent;
      var msie = ua.indexOf("MSIE "); 

       // if Internet Explorer
      if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
          txtArea1.document.open("txt/html","replace");
          txtArea1.document.write(tab_text);
          txtArea1.document.close();
          txtArea1.focus(); 
          sa = txtArea1.document.execCommand("SaveAs", true, "DataTableExport.xls");
      }  
      else // other browser not tested on IE 11
          sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  

      return (sa);
  }   
  </script>
</head>
<body>
<div class="container">
<table class="nav_table"><tr><td><div class="main_menu"><ul>
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
 
 <li><a href="#">Contact Us</a></li></ul></div></td></tr>
 </table>
 </div>
 </br>
<div class="container">
       <form action="${contextPath}/searchRecord" method="post" class="w3-container" >
           <input class="w3-input" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
 <input type="text" id="datepicker" name="fromDate" placeholder="From Date">&nbsp;&nbsp;
<input type="text" id="datepicker1" name="toDate" placeholder="To Date">
        <input type="submit" value="Search Records" />
       
    </form>
    <span>${norecord}</span>
        <c:if test="${not empty data}">
        <br>
            
         <iframe id="txtArea1" style="display:none"></iframe>
         <button id="btnExport" onclick="fnExcelReport();"> EXPORT </button>
         
         
    <table id="recordTable">
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
</body>
</html>