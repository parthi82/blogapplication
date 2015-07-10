<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<style type="text/css">

  a {
    text-decoration:none;
    padding:5px;
    color: cornflowerblue;
  }
  
  a:hover {
    color: blue;
  }
  
  
  
</style>
<script src="/makenewpost.js"></script>
</head>
<body>

  <a id="createpost"><strong>Create post</strong></a>  
  <a href="/posts"><strong>View Posts</strong></a>
  <a href="/logout"><strong>Logout</strong></a>
  <hr>
  
  <h2>Welcome <%if(request.getAttribute("userid") != null) out.println(request.getAttribute("userid"));%></h2>
    
   <div id="postform">
	    <input id="title" name="title" placeholder="Choose a title"></input>
	    </br>
	    </br>
	    <textarea id="postTxt" name="postTxt" placeholder="Enter content of the post"></textarea>
	
	    </br>
	    <button id="createbutton">create</button>
	    <button id="cancel">Cancel</button>
	    
   </div>
   <div id="message"></div>
 
</body>
</html>