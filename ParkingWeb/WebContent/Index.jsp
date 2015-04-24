<%@page import="com.parking.constants.AppConstants"%>
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
						data : [ gravDeter[0], gravDeter[1], gravDeter[2],
								gravDeter[3], gravDeter[4] ]
					},
					{
						label : "Greedy Deterministic (Red)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(255,0,0,0.3)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ greedyDeter[0], greedyDeter[1],
								greedyDeter[2], greedyDeter[3], greedyDeter[4] ]
					},
					{
						label : "Gravitational Probabilistic (Yellow)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(255,255,0,0.3)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ gravProb[0], gravProb[1], gravProb[2],
								gravProb[3], gravProb[4] ]
					},
					{

						label : "Greedy Probabilistic (Green)",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(0,255,0,0.3)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ greedyProb[0], greedyProb[1], greedyProb[2],
								greedyProb[3], greedyProb[4] ]
					} ]
		};
		var ctx2 = document.getElementById("chart2").getContext("2d");
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
</script>


</head>
<body>

	<h3 id="id"></h3>

	<div data-role="main" class="ui-content">
		<div id="canvas-holder2">
			<canvas id="chart2" width="450" height="100" />
		</div>
	</div>


	<input type="button" value="Compute" onclick="populateGraph();" />

</body>
</html>