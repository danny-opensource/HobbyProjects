<%@page import="com.parking.constants.AppConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script>

function updatePercent(percent) 
{ 
var oneprcnt = 4.15; 
var prcnt = document.getElementById('prcnt'); 
prcnt.style.width = percent*oneprcnt; 
prcnt.innerHTML = percent + " %"; 

} 

	
</script>


</head>
<body>

<div style="width: 415px; height: 20px;background-color:white;	padding:0px;" id="status">
<div id="prcnt" style="height:18px;width:30px;overflow:hidden;background-color:lightgreen" align="center">
0%</div></div> 

	<form action="/ParkingWeb/GeoLocationController" method="post">
		Type of Algorithm: 
		
		<input type="radio" name="algorithmType" value="0">Gravitational Deterministic</>
		<input type="radio" name="algorithmType" value="1">Greedy Deterministic</>
		 <br /> <input type="submit" value="Compute" />
	</form>
	
<%
		while(AppConstants.dynamicProgress != 100 && AppConstants.dynamicProgress != 0)
			{
			System.out.println("*** In While");
		out.println("<script>updatePercent(" + AppConstants.dynamicProgress + ")</script>\n"); 
		out.flush(); 
			}
		out.flush();
	%>

</body>
</html>