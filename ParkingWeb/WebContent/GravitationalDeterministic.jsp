<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="charts/Chart.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
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
			labels : [ "0", "10", "20", "30", "40" ],
			datasets : [ {
				label : "My First dataset",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
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
			labels : [ "0", "10", "20", "30", "40" ],
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

	function runTrials() {
		var averageTimes = [];
		var counter = 0;
		var averageTime = 0;
		var plotTimes = [];
		$("#imgProgress").show();
		$
				.ajax({
					crossDomain : true,
					url : 'http://localhost:8080/ParkingWeb/rest/parking/greedydeterministic/2/0',
					complete : function(jsXHR, textStatus) {
						$("#imgProgress").hide();
						var xmlResponse = $.parseXML(jsXHR.responseText), $xml = $(xmlResponse);
						$($xml).find('trial').each(
								function() {
									averageTimes[counter] = $(this).find(
											'averageTime').text();
									alert('AverageTimes[counter]'
											+ averageTimes[counter]);
									counter++;
								});

						for (var i = 0; i < averageTimes.length; i++) {
							averageTime = averageTime
									+ parseFloat(averageTimes[i]);
							alert('iteration is: ' + i);
							alert('averageTime is: ' + averageTime);
						}
						var jsAvgTime = averageTime / counter;
						alert('Total AvgTime: ' + jsAvgTime);
						//alert('Hi' + jsAvgTime.text());
						plotTimes[0] = jsAvgTime;
						plotTimes[1] = jsAvgTime;
						plotTimes[2] = jsAvgTime;
						plotTimes[3] = jsAvgTime;
						plotTimes[4] = jsAvgTime;
						populateGraph(plotTimes);
						populateTable(trialNumbers, userLocation, averageTime,
								congestionLevel);
					}
				});
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
	<img src="images/ajax-loader.gif" style="display: none;"
		id="imgProgress" />
	<h3 id="id"></h3>

	<div data-role="main" class="ui-content">
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
						href="#navigationDialog" class="ui-btn ui-btn-inline">Show
						Navigation</a> <a href="#trialData" class="ui-btn ui-btn-inline">Show
						Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 10% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountTen"></label> <a
						href="#navigationDialog" class="ui-btn ui-btn-inline">Show
						Navigation</a> <a href="#trialData" class="ui-btn ui-btn-inline">Show
						Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 20% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountTwenty"></label> <a
						href="#navigationDialog" class="ui-btn ui-btn-inline">Show
						Navigation</a> <a href="#trialData" class="ui-btn ui-btn-inline">Show
						Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 30% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountThirty"></label> <a
						href="#navigationDialog" class="ui-btn ui-btn-inline">Show
						Navigation</a> <a href="#trialData" class="ui-btn ui-btn-inline">Show
						Trial Data</a>
				</p>
			</div>
			<div data-role="collapsible">
				<h3>Analysis for 40% Congestion</h3>
				<p>
					Total Trials Conducted: <label id="trialsCountFourty"></label> <a
						href="#navigationDialog" class="ui-btn ui-btn-inline">Show
						Navigation</a> <a href="#trialData" class="ui-btn ui-btn-inline">Show
						Trial Data</a>
				</p>
			</div>
		</div>
	</div>
	<div id="chartjs-tooltip"></div>
</body>
</html>