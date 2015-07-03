<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="myscript.js" type="text/javascript"></script>
<title>Login Form</title>
</head>
<body>

		</br>
		</br>  
		  <div class="container">
		  <div class="well">
		  <h5><strong>
		       <div role="alert" id="Info"></div>
		  </strong></h5>
		  <form class="form-horizontal" id = "signup" method="post" action="/add">
					<div class = "form-group" id = "state1">
						<label class="control-label col-xs-2">Name</label>
						<div class = "col-md-9 col-xs-9 col-sm-9">
							<input type="text" id="name" name="name" class="form-control input-lg" placeholder="Enter your name" onblur="validateName();">
							<span id = "glyp1" aria-hidden="true"></span>
							<span class="help-block" id = "helpblock1"></span>
						</div>
					</div>
					<div class="clearfix visible-md-block visible-xs-block"></div>
					<div class="form-group" id = "state2">
						<label class="col-xs-2 control-label">Email</label>
						<div class="col-xs-9 col-sm-9 col-md-9">
							<input type="text" id="email" name = "email" class="form-control input-lg" placeholder="Enter your email id" onblur="validateEmail();">
							<span id = "glyp2"aria-hidden="true"></span>
							<span class="help-block" id = "helpblock2"></span>
						</div>
					</div>
					<div class="clearfix visible-md-block visible-xs-block"></div>
					<div class="form-group" id = "state3">
						<label class="col-xs-2 control-label">Phone</label>
						<div class="col-xs-9 col-sm-9 col-md-9">
							<input type="text" id = "phone" name = "phone" class="form-control input-lg" placeholder="Enter your phone no" onblur="validatePhone();">
							<span id = "glyp3"aria-hidden="true"></span>
							<span class="help-block" id = "helpblock3"></span>
						</div>
					</div>
					<div class="clearfix visible-md-block visible-xs-block"></div>
					<div class="form-group" id = "state4">
						<label class="col-xs-2 control-label">Password</label>
						<div class="col-xs-9 col-sm-9 col-md-9">
							<input type="password" id = "password" name = "password" class="form-control input-lg" placeholder="Enter a password" onblur="validatePassword();">
							<span id = "glyp4"aria-hidden="true"></span>
							<span class="help-block" id = "helpblock4"></span>
						</div>
					</div>
					<div class="form-group">
					  <div class="col-sm-offset-2 col-sm-10">
						 <button type="submit" class="btn btn-primary">SignUp</button>
					  </div>
                    </div>
				</form>
			</div>
           </div>

</body>
</html>