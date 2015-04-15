<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">



</head>
<body>

	<div class="panel panel-default"
		style="vertical-align: middle; margin-top: 2%; background: rgba(213, 224, 230, .4)">
		<div class="panel-heading" style="background: black; color: white;">
			<h3 class="panel-title">Spatio-Temporal Parking Resources:
				Algorithms Analysis</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-sm-6 col-md-4"
					style="box-shadow: 5px 5px 5px #888888;">
					<div class="thumbnail">
						<img src="images/gravity.jpg">
						<div class="caption">
							<h3>Gravitational (Deterministic)</h3>
							<p>We can define the gravitational force of a parking block b
								towards a vehicle using the Gravitational formula. Variants to
								this approach is by introducing congestion</p>
							<p>
								<a href="GravitationalDeterministic.jsp" class="btn btn-primary"
									role="button" style="width: 100%">Launch Analysis</a>
							</p>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-md-4"
					style="box-shadow: 5px 5px 5px #888888; margin-left: 5px">
					<div class="thumbnail">
						<img src="images/greedy.jpg">
						<div class="caption">
							<h3>Greedy (Deterministic)</h3>
							<p>In a two-dimensional road network, assuming that time is
								discrete and the increment is a unit time, at every increment,
								move the vehicle in the direction of the closest block</p>
							<p>
								<a href="GravitationalDeterministic.jsp" class="btn btn-primary"
									role="button" style="width: 100%">Launch Analysis</a>
							</p>
						</div>
					</div>
				</div>


			</div>
		</div>


	</div>



</body>
</html>