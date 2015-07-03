<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="loginval.js" type="text/javascript"></script>
<style type="text/css">
  
  #alerttxt {
     color:red;
     text-align:center;
  }
  
</style>
<title>Login</title>
</head>
<body>
  
        </br>
		</br>  
		  <div class="container">
		  <div class="well">
		  <h5><strong>
		       <div role="alert" id="Info">
		       <p id ="alerttxt">
		        <% String val = (String)request.getAttribute("email");
							          if(val != null) 
							             out.print("Wrong Password !");%>
			   </p>
		       </div>
		  </strong></h5>
		  <form class="form-horizontal" id = "login" method="post" action="/login">
					<div class="clearfix visible-md-block visible-xs-block"></div>
					<div class="form-group" id = "state2">
						<label class="col-xs-2 control-label">Email</label>
						<div class="col-xs-9 col-sm-9 col-md-9">
							<input type="text" id="email" name = "email" class="form-control input-lg" placeholder="Enter your email id" onblur="validateEmail();" 
							 value= <% String email = (String)request.getAttribute("email");
							          if(email != null) 
							             out.print(email);%>>
							<span id = "glyp2"aria-hidden="true"></span>
							<span class="help-block" id = "helpblock2"></span>
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
						 <button type="submit" class="btn btn-primary">Login</button>
					  </div>
                    </div>
                    
				</form>
			</div>
           </div>
  
  
</body>
</html>