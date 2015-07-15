<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.blogapplication.UserPosts" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POSTS</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
$(document).ready(function(){
		
	$('#back').click(function() {
	    window.history.back();
	});
		
});

</script>
</head>
<body>
    
    <a href="/profilepage"><Strong>Home</Strong></a>
    <hr>
    
    <% 
       
        if(request.getAttribute("posts") != null){
        	
    	  List<UserPosts> postlist = (List<UserPosts>)request.getAttribute("posts");
    	   if(!postlist.isEmpty()) {
    		   for(UserPosts post: postlist) { 
               
    %>
    
         <ul>
            <li><a href="/post/<%=KeyFactory.keyToString(post.getKey())%>"><%=post.getTitle()%></a></li>
         </ul> 
        
         
         
     <%
    		   }
    	   }
        }
    %> 
    <br>
    <br>
    
    <a href id="back">Back</a>  
    <a href="/posts/<%if(request.getAttribute("cursorString") != null)out.println(request.getAttribute("cursorString"));%>">Next</a> 
    
</body>
</html>