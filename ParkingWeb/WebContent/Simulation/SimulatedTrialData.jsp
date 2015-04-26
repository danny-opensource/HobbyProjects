<%@page import="com.parking.constants.AppConstants.ALGORITHM_TYPE"%>
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
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>Trial Data</title>

</head>
<body>
	<table border="1" class="table table-bordered table-condensed">
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
			ALGORITHM_TYPE algoType = ALGORITHM_TYPE.GRAVITATIONAL_DETERMINISTIC;
			String origin = request.getParameter("origin");

			if (origin.equalsIgnoreCase("gd")) {
				algoType = ALGORITHM_TYPE.GRAVITATIONAL_DETERMINISTIC;
			} else if (origin.equalsIgnoreCase("grd")) {
				algoType = ALGORITHM_TYPE.GREEDY_DETERMINISTIC;
			} else if (origin.equalsIgnoreCase("gp")) {
				algoType = ALGORITHM_TYPE.GRAVITATIONAL_PROBABILISTIC;
			} else if (origin.equalsIgnoreCase("grp")) {
				algoType = ALGORITHM_TYPE.GREEDY_PROBABILISTIC;
			}

			ArrayList<TrialData> trialData = null;
			Iterator<TrialData> trialDataIterator = null;
			switch (congestionParam) {
			case 0:
				trialData = GeneralUtils.getSimulatedTrialData(0, algoType);
				trialDataIterator = trialData.iterator();
				while (trialDataIterator.hasNext()) {
					TrialData zeroCData = trialDataIterator.next();
		%>
		<tr>
			<td><%=zeroCData.getTrianNumber()%></td>
			<td><%=zeroCData.getParkingBlockId()%></td>
			<td><%=(double) (zeroCData.getTimeToParkingBlock())/60%></td>
			<td><%=zeroCData.getParkingBlockLocation()%></td>
			<td><%=zeroCData.getUserLocation()%></td>
		</tr>
		<%
			}
				break;
			case 30:
				trialData = GeneralUtils.getSimulatedTrialData(30, algoType);
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
				trialData = GeneralUtils.getSimulatedTrialData(50, algoType);
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
				trialData = GeneralUtils.getSimulatedTrialData(70, algoType);
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
				trialData = GeneralUtils.getSimulatedTrialData(90, algoType);
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