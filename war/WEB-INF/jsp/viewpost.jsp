<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.blogapplication.UserPosts" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POST</title>
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
                 <span><strong>Author: <%=postobj.getAuthor()%>  Title: <%=postobj.getTitle()%>  </strong></span>
                 <hr>
                 <span><strong>Date: <%=postobj.getDate()%></strong></span>
               </div>
               <p id="postTxt"><%=postobj.getpostTxt()%></p>
          </div>
          <button id="edit">Edit</button>
          <a id="posthistory">View History</a>
          <a id="postid" style="display:none"><%=KeyFactory.keyToString(postobj.getKey())%></a>
          <button type="submit" id="save">Save</button>
          <button id="cancel">Cancel</button>
          
       <%
          }
       %>
       
       <br>
       <br>
       <a href id="back" onclick="goBack()">Back</a>  
       
</body>
</html>