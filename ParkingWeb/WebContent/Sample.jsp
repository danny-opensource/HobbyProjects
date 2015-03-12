<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="js/jquery-1.11.2.min.js"></script>
<script>

$(document).ready(function()
		{
	$.ajax(
		{
			crossDomain: true,
			url: 'http://localhost:8080/ParkingWeb/rest/hello/gravitationaldeterministic/1',
			complete: function(jsXHR, textStatus) {
				var xmlResponse = $.parseXML(jsXHR.responseText),
				$xml = $(xmlResponse),
				$trials = $xml.find('trials');
				$averageTime = $xml.find('averageTime');
				$('h3#id').html("Total Trials conducted: " + $xml);
			}
		}		
	);
		});
</script>
</head>
<body>
<h3 id="id"></h3>
</body>
</html>