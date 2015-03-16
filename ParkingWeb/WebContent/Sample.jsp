<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="charts/Chart.js"></script>
<script>

$(document).ready(function()
		{
	$.ajax(
		{
			crossDomain: true,
			url: 'http://localhost:8080/ParkingWeb/rest/hello/gravitationaldeterministic/3',
			complete: function(jsXHR, textStatus) {
				var xmlResponse = $.parseXML(jsXHR.responseText),
				$xml = $(xmlResponse),
				$trials = $xml.find('trials');
				$averageTime = $xml.find('averageTime');
				$('h3#id').html("Total Trials conducted: " + $averageTime.text());
			}
		}	
	);
		});
</script>

<style>
    #canvas-holder2 {
        width: 20%;
        margin: 20px 25%;
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
   	.chartjs-tooltip-key{
   		display:inline-block;
   		width:10px;
   		height:10px;
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
										'	<span class="chartjs-tooltip-value">'
												+ tooltip.labels[i] + '</span>',
										'</div>' ].join('');
							}
							tooltipEl.html(innerHtml);
							tooltipEl.css({
								opacity : 1,
						left : tooltip.chart.canvas.offsetLeft
										+ tooltip.x + 'px',
								top : tooltip.chart.canvas.offsetTop
										+ tooltip.y + 'px',
								fontFamily : tooltip.fontFamily,
								fontSize : tooltip.fontSize,
								fontStyle : tooltip.fontStyle,
							});
						};
						var randomScalingFactor = function() {
							return Math.round(Math.random() * 100);
						};
						var lineChartData = {
							labels : [ "0%", "10%", "20%", "30%", "40%"],
							datasets : [
									{
										label : "My First dataset",
										fillColor : "rgba(220,220,220,0.2)",
										strokeColor : "rgba(220,220,220,1)",
										pointColor : "rgba(220,220,220,1)",
										pointStrokeColor : "#fff",
										pointHighlightFill : "#fff",
										pointHighlightStroke : "rgba(220,220,220,1)",
										data : [ randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor() ]
									},
									{
										label : "My Second dataset",
										fillColor : "rgba(151,187,205,0.2)",
										strokeColor : "rgba(151,187,205,1)",
										pointColor : "rgba(151,187,205,1)",
										pointStrokeColor : "#fff",
										pointHighlightFill : "#fff",
										pointHighlightStroke : "rgba(151,187,205,1)",
										data : [ randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor(),
												randomScalingFactor() ]
									} ]
						};

						function populateGraph() {
							var ctx2 = document.getElementById("chart2")
									.getContext("2d");
							window.myLine = new Chart(ctx2).Line(lineChartData,
									{
										responsive : true
									});
						}
					</script>
</head>
<body>
<h3 id="id"></h3>

<input type="button" value="Populate Graph" onclick="populateGraph()"/>

    <div id="canvas-holder2">
        <canvas id="chart2" width="450" height="300" />
    </div>

    <div id="chartjs-tooltip"></div>
    
    
</body>
</html>