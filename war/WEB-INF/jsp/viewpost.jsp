<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.blogapplication.UserPosts" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/viewpost.js"></script>
</head>
<body>
       <% 
          if(request.getAttribute("post") != null) {
        	
        	  UserPosts postobj = (UserPosts)request.getAttribute("post");
          
       %>
          <div class="container">
               <div>
                 <span><strong>Author: <%=postobj.getAuthor()%> <hr> </strong></span>
                 <span><strong>Date: <%=postobj.getDate()%></strong></span>
               </div>
               <p id="postTxt"><%=postobj.getpostTxt()%></p>
          </div>
          <button id="edit">Edit</button>
          <a id="postid" style="display:none"><%=postobj.getId()%></a>
          <button type="submit" on id="save">Save</button>
          <button id="cancel">Cancel</button>
       <%
          }
       %>
</body>
</html>