<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>Insert title here</title>
 <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>
</head>
<body>

    <form align="center" method="GET">
        <input type="submit" id="mysubmit" value="Submit" onClick="addMoreRows(); return false;">
    </form>
<form action="${contextPath}/timesheetEntry" class="w3-container" >
    <table id="tbl_id" name="tbl_id" style="text-align:center" align="center" valign:"top">
        <thead>
            <tr>
                <th>Employee Id</th>
                <th>Date</th>
                <th>Project Code</th>
                <th>Task Description</th>
                <th>Logged Hours</th>
                <th>Remarks</th>
            </tr>
            
        </thead>
       <tr>
		       <td><input type="text" name="empId"></td>
		       <td><input type="text" id="datepicker" name="date"></td>
		       <td><input type="text" name="projectCode"></td>
		       <td><input type="text" name="taskDescription"></td>
		       <td><input type="text" name="loggedHours"></td>
		       <td><input type="text" name="remarks"></td>
       </tr>
    </table>
      <input type="submit"  value="submit" />
    </form>

    <script type="text/javascript">

      function addMoreRows() {

          var newRow = document.getElementById('tbl_id').insertRow();

          var newCell = newRow.insertCell();
          newCell.innerHTML="<tr><td><input type='text' name='empId'></td></tr>";

          newCell = newRow.insertCell();
          newCell.innerHTML="<tr><td><input type='text' id='datepicker' name='date'></td></tr>";

          newCell = newRow.insertCell();
          newCell.innerHTML="<tr><td><input type='text' name='projectCode'></td></tr>";

          newCell = newRow.insertCell();
          newCell.innerHTML="<tr><td><input type='text' name='taskDescription'></td></tr>";
          
          newCell = newRow.insertCell();
          newCell.innerHTML="<tr><td><input type='text' name='loggedHours'></td></tr>";
          
          newCell = newRow.insertCell();
          newCell.innerHTML="<tr><td><input type='text' name='remarks'></td></tr>";

        

      }
    </script>
    
    <script type="text/javascript">
function GetCellValues(tbl_id) 
{
    var jsonObj = [];
    var jsonString;
    var table = document.getElementById(tbl_id);
    alert(table.rows.length);
    for (var r = 0, n = table.rows.length; r < n; r++) {
        var item = {};
        for (var c = 0, m = table.rows[r].cells.length; c < m; c++){        
            if(c == 1){
                item ["data1"] =table.rows[r].cells[c].innerHTML;}
            else if(c==2){
                item ["data2"] =table.rows[r].cells[c].innerHTML;}
            else if(c==3){
                item ["data3"] =table.rows[r].cells[c].innerHTML;}
            else if(c==4){
                 item ["data4"] = table.rows[r].cells[c].innerHTML;} 
            else if(c==5){
                item ["data5"] =table.rows[r].cells[c].innerHTML;}

        }
        alert(item);
        jsonObj.push(item);
    }
    jsonString = JSON.stringify(jsonObj);
   alert("Save your data "+ jsonString);

    $.ajax({
        type: "POST",
        url : "tableData.htm?jsonData="+jsonString,
        success: function(data){
            $("#").html(data);      
        },
        error:function(data){
            console.log("failure"+data);
            alert("failure"+data);
        }
    });  
}
</script>

    </body>
</html>