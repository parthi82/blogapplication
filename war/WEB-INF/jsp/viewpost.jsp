<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.blogapplication.UserPosts" %>
<%@ page import="com.blogapplication.PostHistory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POST</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/viewpost.js"></script>

<style type="text/css">
   
   #txtarea {
       font-family:Times New Roman;
       font-size:15px;
       color:black;
       white-space:pre-wrap";
   }
   
   span {
      padding: 5px;
   }
  
   
</style>

</head>
<body>
      
       
       <% 
          if(request.getAttribute("post") != null && request.getAttribute("posthistory") != null) {
        	
        	  UserPosts postobj = (UserPosts)request.getAttribute("post");
        	  PostHistory posthistory = (PostHistory)request.getAttribute("posthistory");
          
       %>
          <div class="container">
               <div>
                 <span><strong>Title: <%=postobj.getTitle()%></strong></span>
                 <span><a href="/viewposthistory/<%=KeyFactory.keyToString(postobj.getKey())%>" id="posthistory">View History</a></span>
                 <span><a href="/profilepage">Home</a></span>
                 <span><a href="/posts">View Posts</a></span>
                 <hr>
                 <span><strong>Created: <%=postobj.getAuthor()%></strong> <span id="creationdate"><%=postobj.getDateOfCreation()%></span></span>
                 <span><strong>Last Edit: <%=posthistory.getEditor()%></strong> <span id="editeddate"><%=posthistory.getDateOfEdit()%></span></span>
                 <span>Version: <%=posthistory.getVersion()%></span>
               </div>
               <div id="postTxt"><%=posthistory.getPostTxt() %></div>
          </div>
          
          <button id="edit">Edit</button>
          <a id="postid" style="display:none"><%=KeyFactory.keyToString(postobj.getKey())%></a>
          <textarea rows="4" cols="50" id="txtarea"></textarea>
          <button id="save">Save</button>
          <button id="cancel">Cancel</button>
          
       <%
          }
       %>
       
       <br>
       <br>
       <a href id="back">Back</a>  
       
</body>
</html>