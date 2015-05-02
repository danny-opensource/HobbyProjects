<%@page import="com.parking.constants.AppConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Total Simulated Results</title>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="charts/Chart.js"></script>
<link rel="stylesheet"
	href="../css/jquery.mobile-1.4.5.min.css">
<script src="../js/jquery-1.11.2.min.js"></script>
<script
	src="../js/jquery.mobile-1.4.5.min.js"></script>

<style>
#canvas-holder2 {
	width: 80%;
	height: 15%;
	margin: 5px 10%;
}

#chartjs-tooltip {
	opacity: 1;
	position: absolute;
	background: rgba(0, 0, 0, .7);
	color: white;
	padding: 3px;
	border-radius: 3px;
	-webkit-transition: all .1s ease;
	transition: all .1s ease;
	pointer-events: none;
	-webkit-transform: translate(-50%, 0);
	transform: translate(-50%, 0);
}

.chartjs-tooltip-key {
	display: inline-block;
	width: 10px;
	height: 10px;
}
</style>



<script>
	Chart.defaults.global.pointHitDetectionRadius = 1;
	Chart.defaults.global.customTooltips = function(tooltip) {
		var tooltipEl = $('#chartjs-tooltip');
		if (!tooltip) {
			tooltipEl.css({
				opacity : 0
			});
			return;
		}
		tooltipEl.removeClass('above below');
		tooltipEl.addClass(tooltip.yAlign);
		var innerHtml = '';
		for (var i = tooltip.labels.length - 1; i >= 0; i--) {
			innerHtml += [
					'<div class="chartjs-tooltip-section">',
					'	<span class="chartjs-tooltip-key" style="background-color:' + tooltip.legendColors[i].fill + '"></span>',
					'	<span class="chartjs-tooltip-value">' + tooltip.labels[i]
							+ '</span>', '</div>' ].join('');
		}
		tooltipEl.html(innerHtml);
		tooltipEl.css({
			opacity : 1,
			left : tooltip.chart.canvas.offsetLeft + tooltip.x + 'px',
			top : tooltip.chart.canvas.offsetTop + tooltip.y + 'px',
			fontFamily : tooltip.fontFamily,
			fontSize : tooltip.fontSize,
			fontStyle : tooltip.fontStyle,
		});
	};

	function populateGraph() {
		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};

		var gravDeter = readGravitationalCookies();
		var greedyDeter = readGreedyDeterministicCookies();
		var gravProb = readGravitationalProbabilisticCookies();
		var greedyProb = readGreedyProbabilisticCookies();
		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [
					{
						label : "Gravitational Deterministic (Blue)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(1,17,205,1)",
						pointColor : "rgba(150,180,205,1)",
						pointStrokeColor : "#000",
						pointHighlightFill : "#ddf",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_50")%>,
						         <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_90")%> ]
					},
					{
						label : "Greedy Deterministic (Red)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(255,0,0,0.3)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_50")%>,
						         <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_90")%> ]
					},
					{
						label : "Gravitational Probabilistic (Yellow)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(255,255,0,0.3)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_50")%>,
						         <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_90")%> ]
					},
					{

						label : "Greedy Probabilistic (Green)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(0,255,0,0.3)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_50")%>,
						         <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_90")%> ]
					} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		if (window.myLine != null) {
			window.myLine.destroy();
		}
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}

	function updatePercent(percent) {
		var oneprcnt = 4.15;
		var prcnt = document.getElementById('prcnt');
		prcnt.style.width = percent * oneprcnt;
		prcnt.innerHTML = percent + " %";

	}

	function readGravitationalCookies() {
		var name = "gravitationaldeterministic" + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			if (c.indexOf(name) == 0) {
				var vals = c.substring(name.length, c.length);
				var arrVal = vals.split(",");
				return arrVal;
			}
			readGreedyDeterministicCookies();
		}
		return "";
	}

	function readGreedyDeterministicCookies() {
		var name = "greedydeterministic" + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			if (c.indexOf(name) == 0) {
				var vals = c.substring(name.length, c.length);
				var arrVal = vals.split(",");
				return arrVal;
			}
			readGravitationalProbabilisticCookies();
		}
		return "";
	}

	function testSplit() {
		var testStr = "1,2,3,4";
		var splitStr = testStr.split(",");
		alert(splitStr[0]);
	}

	function readGravitationalProbabilisticCookies() {
		var name = "gravitationalprobabilistic" + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			var vals = c.substring(name.length, c.length);
			var arrVal = vals.split(",");
			if (c.indexOf(name) == 0) {
				return arrVal;
			}
			//	readGreedyProbabilisticCookies();
		}
		return "";
	}

	function readGreedyProbabilisticCookies() {
		var name = "greedyprobabilistic" + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			var vals = c.substring(name.length, c.length);
			var arrVal = vals.split(",");
			if (c.indexOf(name) == 0) {
				return arrVal;
			}
		}
	}

	function populateGravDeterministic() {

		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};
		var gravDeter = readGravitationalCookies();
		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [ {
				label : "Gravitational Deterministic (Blue)",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(1,17,205,1)",
				pointColor : "rgba(150,180,205,1)",
				pointStrokeColor : "#000",
				pointHighlightFill : "#ddf",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_50")%>,
				         <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_90")%> ]
			} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		if (window.myLine != null) {
			window.myLine.destroy();
		}
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}

	function populateGreedyDeterministic() {
		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};

		var greedyDeter = readGreedyDeterministicCookies();
		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [ {
				label : "Greedy Deterministic (Red)",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(255,0,0,0.3)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_50")%>,
				         <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_det_90")%> ]
			} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		if (window.myLine != null) {
			window.myLine.destroy();
		}
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}

	function populateGravProbabilistic() {
		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};

		var gravProb = readGravitationalProbabilisticCookies();

		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [ {
				label : "Gravitational Probabilistic (Yellow)",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(255,255,0,0.3)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_50")%>,
				         <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("grav_prob_90")%> ]
			} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		if (window.myLine != null) {
			window.myLine.destroy();
		}
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}

	function populateGreedyProbabilistic() {
		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};

		var greedyProb = readGreedyProbabilisticCookies();

		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [ {

				label : "Greedy Probabilistic (Green)",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(0,255,0,0.3)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_0")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_30")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_50")%>,
				         <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_70")%>, <%=AppConstants.sSimulatedDataForSevenAM.get("greedy_prob_90")%> ]
			} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		if (window.myLine != null) {
			window.myLine.destroy();
		}
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}
</script>


</head>
<body>
	<h1 align="center">Analysis for Data computed at 7:00 AM on
		04/23/2012</h1>
	<h3 id="id"></h3>

	<div data-role="main" class="ui-content">

		<div id="canvas-holder2">
			<canvas id="chart2" width="450" height="200" />
		</div>
	</div>

	<div align="center">Congestion Level</div>
	<table align="center">
		<tr>
			<td bgcolor="blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>Gravitational Deterministic</td>
			<td bgcolor="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>Greedy Deterministic&nbsp;&nbsp;</td>
			<td bgcolor="yellow">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>Gravitational Probabilistic&nbsp;&nbsp;</td>
			<td bgcolor="green">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>Greedy Probabilistic&nbsp;&nbsp;</td>

		</tr>
	</table>

	<table cellspacing="10px" cellpadding="3px">
		<tr>

			<td><input type="button"
				value="Gravitational Deterministic Results"
				onclick="populateGravDeterministic();" data-theme="c" /></td>
			<td><input type="button" value="Greedy Deterministic Results"
				onclick="populateGreedyDeterministic();" /></td>
			<td><input type="button" value="Simulation Results"
				onclick="populateGraph();" data-theme="b" /></td>
			<td><input type="button"
				value="Gravitational Probabilistic Results"
				onclick="populateGravProbabilistic();" /></td>
			<td><input type="button" value="Greedy Probabilistic Results"
				onclick="populateGreedyProbabilistic();" /></td>
		</tr>
	</table>


</body>
</html>