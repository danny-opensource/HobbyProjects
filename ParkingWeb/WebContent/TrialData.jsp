<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.parking.model.TrialData"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.parking.utils.GeneralUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>Trial Number</th>
			<th>Parking Block Id</th>
			<th>Time to Reach Parking Block</th>
			<th>Parking Block Location</th>
			<th>User's Location</th>
		</tr>
		<%
			String congestionRequestParam = request.getParameter("congestion");
			int congestionParam = Integer.parseInt(request.getParameter("congestion"));
			ArrayList<TrialData> trialData = null;
			Iterator<TrialData> trialDataIterator = null;
			switch (congestionParam) {
			case 0:
				trialData = GeneralUtils.getTrialData(0);
				trialDataIterator = trialData.iterator();
				while (trialDataIterator.hasNext()) {
					TrialData zeroCData = trialDataIterator.next();
		%>
		<tr>
			<td><%=zeroCData.getTrianNumber()%></td>
			<td><%=zeroCData.getParkingBlockId()%></td>
			<td><%=zeroCData.getTimeToParkingBlock()%></td>
			<td><%=zeroCData.getParkingBlockLocation()%></td>
			<td><%=zeroCData.getUserLocation()%></td>
		</tr>
		<%
			}
				break;
			case 30:
				trialData = GeneralUtils.getTrialData(30);
				trialDataIterator = trialData.iterator();
				while (trialDataIterator.hasNext()) {
					TrialData thirtyCData = trialDataIterator.next();
		%>
		<tr>
			<td><%=thirtyCData.getTrianNumber()%></td>
			<td><%=thirtyCData.getParkingBlockId()%></td>
			<td><%=thirtyCData.getTimeToParkingBlock()%></td>
			<td><%=thirtyCData.getParkingBlockLocation()%></td>
			<td><%=thirtyCData.getUserLocation()%></td>
		</tr>
		<%
			}
				break;
			case 50:
				trialData = GeneralUtils.getTrialData(50);
				trialDataIterator = trialData.iterator();
				while (trialDataIterator.hasNext()) {
					TrialData fiftyCData = trialDataIterator.next();
		%>
		<tr>
			<td><%=fiftyCData.getTrianNumber()%></td>
			<td><%=fiftyCData.getParkingBlockId()%></td>
			<td><%=fiftyCData.getTimeToParkingBlock()%></td>
			<td><%=fiftyCData.getParkingBlockLocation()%></td>
			<td><%=fiftyCData.getUserLocation()%></td>
		</tr>
		<%
			}
				break;
			case 70:
				trialData = GeneralUtils.getTrialData(70);
				trialDataIterator = trialData.iterator();
				while (trialDataIterator.hasNext()) {
					TrialData seventyCData = trialDataIterator.next();
		%>
		<tr>
			<td><%=seventyCData.getTrianNumber()%></td>
			<td><%=seventyCData.getParkingBlockId()%></td>
			<td><%=seventyCData.getTimeToParkingBlock()%></td>
			<td><%=seventyCData.getParkingBlockLocation()%></td>
			<td><%=seventyCData.getUserLocation()%></td>
		</tr>
		<%
			}
				break;
			case 90:
				trialData = GeneralUtils.getTrialData(90);
				trialDataIterator = trialData.iterator();
				while (trialDataIterator.hasNext()) {
					TrialData ninetyCData = trialDataIterator.next();
		%>
		<tr>
			<td><%=ninetyCData.getTrianNumber()%></td>
			<td><%=ninetyCData.getParkingBlockId()%></td>
			<td><%=ninetyCData.getTimeToParkingBlock()%></td>
			<td><%=ninetyCData.getParkingBlockLocation()%></td>
			<td><%=ninetyCData.getUserLocation()%></td>
		</tr>
		<%
			}
				break;
			}
		%>
	</table>
</body>
</html>