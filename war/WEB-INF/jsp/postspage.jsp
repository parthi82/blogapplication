<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.blogapplication.UserPosts" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <% 
       
        if(request.getAttribute("posts") != null){
        	
    	  List<UserPosts> postlist = (List<UserPosts>)request.getAttribute("posts");
    	   if(!postlist.isEmpty()) {
    		   for(UserPosts post: postlist) { 
               
    %>
    
         <ul>
            <li><a href="/post/<%=post.getId()%>" ><%=post.getTitle()%></a></li>
         </ul> 
        
         
         
    <%
    		   }
    	   }
    	   
        }
    %>  
    <br>
    <br>
    <a href="/posts/<%if(request.getAttribute("cursorString") != null)out.println(request.getAttribute("cursorString"));%>">Next</a>   
</body>
</html>