<%@page import="com.parking.constants.AppConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gravitational Deterministic</title>
<script src="../js/jquery-1.11.2.min.js"></script>
<script src="../charts/Chart.js"></script>
<link rel="stylesheet"
	href="../css/jquery.mobile-1.4.5.min.css">
<script src="../js/jquery-1.11.2.min.js"></script>
<script
	src="../js/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
	$(document).on("ready", function() {
		//	runTrials(0);
	});

	function startAnalysis() {
		$('#btnStartAnalysis').closest('.ui-btn').hide();
		runTrials(0);
	}

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

	function mockTestGraph() {
		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};
		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [ {
				label : "My First dataset",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				axisLabel : "Congestion Level",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ randomScalingFactor() * 100,
						randomScalingFactor() * 100,
						randomScalingFactor() * 100,
						randomScalingFactor() * 100,
						randomScalingFactor() * 100 ]
			} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}
	function populateGraph(plotTimes) {
		var data = plotTimes;
		//alert("Avg Time: " + jsAvgTime.text());
		var randomScalingFactor = function() {
			return Math.round(Math.random() * 100);
		};
		var lineChartData = {
			labels : [ "0", "30", "50", "70", "90" ],
			datasets : [ {
				label : "My First dataset",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ data[0], data[1], data[2], data[3], data[4] ]
			} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
		window.myLine = new Chart(ctx2).Line(lineChartData, {
			responsive : true
		});
	}

	var plotTimes = [];
	var congestionCounter = 0;
	function runTrials(congestion) {
		var averageTimes = [];
		var counter = 0;
		var averageTime = 0;
		
		plotTimes[0] = <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_0")%>;
		plotTimes[1] = <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_30")%>;
		plotTimes[2] = <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_50")%>;
		plotTimes[3] = <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_70")%>;
		plotTimes[4] = <%=AppConstants.sSimulatedDataForSevenAM.get("grav_det_90")%>;
		populateGraph(plotTimes);
	}
</script>

<style>


#canvas-holder2 {
	width: 80%;
	height: 15%;
	margin: 30px 10%;
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
</head>
<body>

	<%
		AppConstants.sGravitationalTraialData.clear();
	%>


	<h3 id="id"></h3>

	<div data-role="main" class="ui-content">
		<button class="ui-btn-inline" data-icon="gear" data-theme="b"
			onclick="startAnalysis();" id="btnStartAnalysis">Start
			Analyzing</button>
		<img src="../images/ajax-loader.gif"
			style="display: none; margin-left: 50%;" id="imgProgress" />
		<div id="canvas-holder2">
			<canvas id="chart2" width="450" height="100" />
		</div>
	</div>


	<div data-role="main" class="ui-content">
		<div data-role="collapsibleset">
			<div data-role="collapsible">
				<h3>Analysis for 0% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountZero"></label> <a
						class="ui-btn ui-btn-inline" rel="external" id="zeroNavigation"
						target="_blank"
						href="../GeoLocationAltered.jsp?userLocation=37.795752,-122.39434&blockLocation=37.807015691,-122.4106376987">Show
						Navigation</a> <a href="SimulatedTrialData.jsp?congestion=0&origin=gd"
						class="ui-btn ui-btn-inline" data-rel="dialog">Show Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 30% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountTen"></label> <a
						class="ui-btn ui-btn-inline" id="thirtyNavigation" target="_blank"
						href="../GeoLocationAltered.jsp?userLocation=37.8065670891,-122.4206920137&blockLocation=37.804912,-122.419391">Show
						Navigation</a> <a
						href="SimulatedTrialData.jsp?congestion=30&origin=gd"
						class="ui-btn ui-btn-inline" data-rel="dialog">Show Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 50% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountTwenty"></label> <a
						class="ui-btn ui-btn-inline" id="fiftyNavigation" target="_blank"
						href="../GeoLocationAltered.jsp?userLocation=37.8055461678,-122.4221474552&blockLocation=37.80559,-122.417857">Show
						Navigation</a> <a
						href="SimulatedTrialData.jsp?congestion=50&origin=gd"
						class="ui-btn ui-btn-inline" data-rel="dialog">Show Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 70% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountThirty"></label> <a
						class="ui-btn ui-btn-inline" id="seventyNavigation"
						target="_blank"
						href="../GeoLocationAltered.jsp?userLocation=37.8055461678,-122.4221474552&blockLocation=37.804785,-122.425195">Show
						Navigation</a> <a
						href="SimulatedTrialData.jsp?congestion=70&origin=gd"
						class="ui-btn ui-btn-inline" data-rel="dialog">Show Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 90% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountFourty"></label> <a
						class="ui-btn ui-btn-inline" id="ninetyNavigation" target="_blank"
						href="../GeoLocationAltered.jsp?userLocation=37.8055461678,-122.4221474552&blockLocation=37.756042,-122.423314">Show
						Navigation</a> <a
						href="SimulatedTrialData.jsp?congestion=90&origin=gd"
						class="ui-btn ui-btn-inline" data-rel="dialog">Show Trial Data</a>
				</p>
			</div>
		</div>
	</div>
	<div id="chartjs-tooltip"></div>
</body>
</html>