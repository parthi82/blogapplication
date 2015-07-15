<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Post History</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/viewposthistory.js"></script>


</head>
<body>
 <span><a href="/profilepage"><strong>Home</strong></a></span>
 <span><a href="/posts"><strong>View Posts</strong></a></span>
 <hr>
<a id="UserPostsKey" style="display:none"><%if(request.getAttribute("UserPostsKey") != null)out.print(request.getAttribute("UserPostsKey"));%></a>
   <table class="table table-hover">
			<thead>
			  <tr>
				<th>Version</th>
				<th>Date</th>
				<th>Editor</th>
				<th>Content of the post</th>
				<th>Action</th>
			  </tr>
			</thead>
			<tbody id = "tbdy">
			  <tr>
			  </tr>
			</tbody>
		  </table>
</body>
</html>