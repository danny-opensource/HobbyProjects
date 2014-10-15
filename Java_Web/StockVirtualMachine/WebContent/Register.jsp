<!DOCTYPE html>
<head>
<title>Registration Page</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body class="LoginBackGround-Image">
	<div class="container">
		<div class="navbar navbar-default" role="navigation">
			<div class="navbar-center">
				<ul class="nav navbar-nav">
					<li><a href="Welcome.jsp"><i class="fa fa-home"></i> Home</a></li>
					<li class="active"><a href="Register.jsp"><i
							class="fa fa-key"></i> Register</a></li>
					<li><a href="Login.jsp"><i class="fa fa-sign-in"></i>
							Login</a></li>
					<li><a href="Help.jsp"><i class="fa fa-question"></i> Help</a></li>
					<li><a href="Contact.jsp"><i class="fa fa-envelope-o"></i>
							Contact Us</a></li>
				</ul>
			</div>
		</div>
		<div class="col-md-12">
			<form class="form-horizontal FormDesign" role="form"
				action="/StockVirtualMachine/RegisterServlet" method="post">
				<div class="row">
					<div class="col-md-12">
						<h1>Create Account</h1>
						<div class="container">
							<div class="col-md-12">
								<form class="FormDesign" role="form"
									action="/StockVirtualMachine/RegisterServlet" method="post">
									<div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="first_name" class="control-label">First
													Name</label> <input type="text" class="form-control"
													id="first_name" name="firstName" placeholder="">
											</div>
											<div class="col-md-3">
												<label for="last_name" class="control-label">Last
													Name</label> <input type="text" class="form-control" id="last_name"
													name="lastName" placeholder="">
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-6">
												<label for="username" class="control-label">Email</label> <input
													type="email" class="form-control" id="email" name="email"
													placeholder="">
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="username" class="control-label">Username</label>
												<input type="text" class="form-control" id="username"
													name="userName" placeholder="">
											</div>
											<div class="col-md-3 templatemo-radio-group">
												<label class="radio-inline"> <input type="radio"
													name="gender" id="optionsRadios1" value="m"> Male
												</label> <label class="radio-inline"> <input type="radio"
													name="gender" id="optionsRadios2" value="f"> Female
												</label>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="password" class="control-label">Password</label>
												<input type="password" class="form-control" id="password"
													name="password" placeholder="">
											</div>
											<div class="col-md-3">
												<label for="password" class="control-label">Confirm
													Password</label> <input type="password" class="form-control"
													name="confirmPassword" id="password_confirm" placeholder="">
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<label><input type="checkbox" name="terms">
													I agree to Terms of Service and Privacy Policy.</label>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-6">
												<input type="submit" value="Create account"
													class="btn btn-info"> <a href="Login.jsp"
													class="pull-right">LOGIN</a>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>