package com.blogapplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CookieValue;

import javax.jdo.PersistenceManager;
import javax.servlet.http.Cookie; 
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.jdo.Query;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

@Controller
public class BlogController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showdefaultpage(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId) {
		
		if(!sessionId.equals("invalid") && verifySession(sessionId)) {
			return "redirect:profilepage";
		}
		
		return "redirect:login";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutUser(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId, 
			HttpServletResponse response) {
		
		String redirectPage = "redirect:profilepage";
		PersistenceManager pm = PMF.get().getPersistenceManager();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		
		try {
			
			Query q = pm.newQuery(SessionObj.class);
			q.setFilter("sessionId == sessionIdParameter");
			q.declareParameters("String sessionIdParameter");
			List<SessionObj> results = (List<SessionObj>) q.execute(sessionId);
			if(!results.isEmpty()) {
				SessionObj session = (SessionObj)results.get(0);
				pm.deletePersistent(session);
			}
			
			if(syncCache.contains(sessionId)) {
				syncCache.delete(sessionId);
			}
			
		    Cookie cookie = new Cookie("sessionId","");
		    cookie.setMaxAge(0);
		    response.addCookie(cookie);
		    
		}
		finally {
			
			pm.close();
			redirectPage = "redirect:login";
			
		}
		
		return redirectPage;
		//response.sendRedirect("login");
		
	}
	
	@RequestMapping(value="/profilepage",method=RequestMethod.GET)
    public String showProfilePage(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId, ModelMap model){
		
		if(!sessionId.equals("invalid") && verifySession(sessionId)) {
		   String userid = getUseridFromSessionId(sessionId);
		   model.addAttribute("userid", userid);
		   return "userhomepage";
		}
		else {
		   return "redirect:login";
		}
        
    }
	
	@RequestMapping(value="/signupform",method=RequestMethod.GET)
    public String showSignupForm(){
		
		return "signupform";
        
    }
	
	@RequestMapping(value="/landingpage",method=RequestMethod.GET)
    public String showLandingPage(){
		return "landingpage";
    
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
    public String showLoginForm(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId){
		
		if(!sessionId.equals("invalid") && verifySession(sessionId)) {
			return "redirect:profilepage";
		}
		
        return "loginform";
        
    }
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginuser(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
       
	   String redirectpage = "loginform";
	   String login = request.getParameter("login");
	   String password = request.getParameter("password");
	   
	   Pattern email_pattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
	   Matcher email_matcher = email_pattern.matcher(login);
	   PersistenceManager pm = PMF.get().getPersistenceManager();
		
	   if(email_matcher.matches()) {
           
		    Query q = pm.newQuery(User.class);
			q.setFilter("email == emailParameter");
			q.declareParameters("String emailParameter");
	        
			try {
				
				List<User> results = (List<User>) q.execute(login);
				if (!results.isEmpty()) {
					   User user = (User)results.get(0);
					   String pwd = user.getPassword();
					   if(pwd.equals(password)) {
						   String userid = user.getUserid();
						   String sessionId = createSession(userid);   
						   Cookie cookie = new Cookie("sessionId", sessionId);
						   cookie.setMaxAge(100000);
						   response.addCookie(cookie);
						   redirectpage =  "redirect:profilepage";				   
					    }
					    else {
					        redirectpage = "loginform";
		                    request.setAttribute("login", login);
					    }
	   
			    } 
				
				
			}
			finally {
				q.closeAll();
			}
			   
		 }
		 else {
			
			    Query q = pm.newQuery(User.class);
				q.setFilter("userid == useridParameter");
				q.declareParameters("String useridParameter");
		        
				try {
					
					List<User> results = (List<User>) q.execute(login);
					if (!results.isEmpty()) {
						   User user = (User)results.get(0);
						   String pwd = user.getPassword();
						   if(pwd.equals(password)) {
							   String sessionId = createSession(login);   
							   Cookie cookie = new Cookie("sessionId", sessionId);
							   cookie.setMaxAge(100000);
							   response.addCookie(cookie);
							   redirectpage =  "redirect:profilepage";				   
						    }
						    else {
						        redirectpage = "loginform"; 
			                    request.setAttribute("login", login);
						    }
				     } 
					
					
				}
				finally {
					q.closeAll();
					pm.close();
				}
			   
			   
		 }
		   
	          return redirectpage;
	   
	}
		
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addUser(HttpServletResponse response, HttpServletRequest request) {
	    
       	String name = request.getParameter("name");
       	String userid = request.getParameter("userid");
       	String email = request.getParameter("email");
       	String phone = request.getParameter("phone");
       	String password = request.getParameter("password");
       	boolean nonullvalue = name != null && userid != null && email != null && phone != null && password  != null;
        boolean condition = false;
        
       	if (nonullvalue) {
       	Pattern name_pattern = Pattern.compile("^[a-zA-Z]{4,39}$");
		Matcher name_matcher = name_pattern.matcher(name);
       	Pattern userid_pattern = Pattern.compile("^\\w{4,39}$");
		Matcher userid_matcher = userid_pattern.matcher(userid);
       	Pattern email_pattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
		Matcher email_matcher = email_pattern.matcher(email); 
		Pattern password_pattern = Pattern.compile("^[0-9a-zA-Z]{5,39}$");
		Matcher password_matcher = password_pattern.matcher(password);
		condition = name_matcher.matches() && userid_matcher.matches() && email_matcher.matches() && password_matcher.matches();
       	}
       	
        if(nonullvalue && condition && !emailExists(email) && !useridExists(userid)) {     	
           	
       	        PersistenceManager pm = PMF.get().getPersistenceManager();
			    		
		    				User user = new User();
		    	        	user.setName(name);
		    	        	user.setUserid(userid);
		    	        	user.setEmail(email);
		    	            user.setPhone(phone); 
		    	        	user.setPassword(password);
		    	        	
				        	
				    		try {
				    			
				    			pm.makePersistent(user);
				    			
				    			String sessionId = createSession(userid);
				    			
				    			if(!sessionId.equals("invalid")) {
				    				Cookie cookie = new Cookie("sessionId", sessionId);
				    				cookie.setMaxAge(10000); 
				    				response.addCookie(cookie);;
				    				return "redirect:profilepage";
				    			}
				    			else {
				    				return "redirect:landingpage";
				    			}
				    			
				    		} 
				    		finally {
				    			pm.close();
				    		}
				        
             }
             else {
            	 
            	 return "redirect:landingpage";
            	
             }
       
	}
	 
	
	 
	 @RequestMapping(value="/getUserDetails", method=RequestMethod.GET)
	    public @ResponseBody HashMap<String, String> getDetails(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId, 
	    		HttpServletRequest request) {
	    	
		    HashMap<String, String> details;
	    	String userid = null;
	    	
	    	if(!sessionId.equals("invalid")) 
	    	    userid = getUseridFromSessionId(sessionId);
	    	
	    	if(userid != null) {
	    	   details =  getDetailsFromUserid(userid);
	    	}
	    	else {
	    		details = new HashMap<String, String>();
	    	}
	    	
	    	return details;
	 }
	 
    

    
    @RequestMapping(value = "/posts", method=RequestMethod.GET)
    public String getPosts(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId, ModelMap model) {
    	
    	String returnpage = "loginform";
    	
    	if(!sessionId.equals("invalid") && verifySession(sessionId)) {
    		
		    	List<UserPosts> posts = null;
		    	PersistenceManager pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(UserPosts.class);
		    	q.setOrdering("dateOfCreation desc");
		    	q.setRange(0, 10);
		    	
		    	   try {
		
		    			posts = (List<UserPosts>) q.execute();
		    			
		    			if(!posts.isEmpty()) {
			    			model.addAttribute("posts", posts);
		    			}
		    			
		    			Cursor cursor = JDOCursorHelper.getCursor(posts);
		    			String cursorString = cursor.toWebSafeString();
		    			model.addAttribute("cursorString",cursorString);
		    			returnpage = "postspage";
		    			
		    			
		    		}
		    		finally {
		    			
		    			q.closeAll();
		    			pm.close();
		    		}
		    		
    	  }
    		
          return returnpage;
  
    }
    
    @RequestMapping(value = "/posts/{cursorString}", method=RequestMethod.GET)
    public String getMorePosts(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId,
    		@PathVariable String cursorString, ModelMap model) {
        
    	String returnpage = "loginform"; 	
    	
		if(!sessionId.equals("invalid") && verifySession(sessionId)) {
			
		    	Cursor cursor = Cursor.fromWebSafeString(cursorString);
		    	List<UserPosts> posts = null;
		    	PersistenceManager pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(UserPosts.class);
		    	q.setOrdering("dateOfCreation desc");
		    	Map<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				q.setExtensions(extensionMap);
		    	q.setRange(0, 2);
		    	
		    	
		    	
		    		try {
		    			
		    			posts = (List<UserPosts>) q.execute();
		    			if(!posts.isEmpty()) {
			    			model.addAttribute("posts", posts);
		    			}
		    			
		    			cursor = JDOCursorHelper.getCursor(posts);
		    			cursorString = cursor.toWebSafeString();
		    			model.addAttribute("cursorString",cursorString);
		    		    returnpage = "postspage";
		    			
		    			
		    		}
		    		finally {
		    			
		    			q.closeAll();
		    			pm.close();
		    		}
		    		
		}
		
        return returnpage;
  
    }
	
    
    
    @RequestMapping(value="/post/{key}", method=RequestMethod.GET) 
    public String showpost (@RequestParam(required = false) Integer version, 
    		@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId, 
    		@PathVariable String key, ModelMap model) {	
        
        String returnpage = "loginform";
    	
    	if(!sessionId.equals("invalid") && verifySession(sessionId)) {
    		
		    	PersistenceManager pm = PMF.get().getPersistenceManager();
		    	UserPosts post = null;
		    	List<PostHistory> posthistoryList = null;
	    		Query q = pm.newQuery(PostHistory.class);
	    		q.setFilter("UserPostsKey == UserPostsKeyParameter");
				q.declareParameters("String UserPostsKeyParameter");
		    	q.setOrdering("version asc");
		    	
		    	try {
		    		
		    		
		    		//Key k = KeyFactory.createKey(UserPosts.class.getSimpleName(), key);
		    		post = (UserPosts)pm.getObjectById(UserPosts.class, key);
		    	    posthistoryList = (List<PostHistory>) q.execute(key);
		            
		    	    if(!posthistoryList.isEmpty()) {
		    	      int index = (version == null) ? (posthistoryList.size() - 1) : version; 
		    	      model.addAttribute("posthistory", posthistoryList.get(index));
		    		  model.addAttribute("post", post);
		    	    }
		    		returnpage = "viewpost";
		    		
		    	}
		    	finally {
		    		pm.close();
		    	}
		    	
    	}
    	
    	return returnpage;
    	
    }
    
    @RequestMapping(value="/post", method=RequestMethod.POST) 
    public @ResponseBody String makeNewpost (@CookieValue(value = "sessionId", defaultValue = "invalid") 
                         String sessionId, HttpServletRequest request) {	
    	
    	String key = "invalid";
    	if(!sessionId.equals("invalid") && verifySession(sessionId)) {
    		  
		    	
		        String title = request.getParameter("title");
		        String postTxt = request.getParameter("postTxt");
		    	String userid = getUseridFromSessionId(sessionId);
		    	PersistenceManager pm = PMF.get().getPersistenceManager();
		    	UserPosts post = new UserPosts();
		    	PostHistory posthistory = new PostHistory();
		    	
		    	try {
		    		
		    	   post.setAuthor(userid);
		    	   post.setTitle(title);
				   post.setDateOfCreation();
				   post.setKey();
				   
				   posthistory.setDateOfEdit();
				   posthistory.setEditor(userid);
				   posthistory.setKey();
				   posthistory.setVersion(0);
				   posthistory.setPostTxt(postTxt);
				   
				   pm.makePersistent(post);
				   key = KeyFactory.keyToString(post.getKey());
				   posthistory.setUserPostsKey(key);
				   pm.makePersistent(posthistory);
				   
		    	}
		    	finally {
		    		pm.close();
		    	}
		    	
    	  }
    	
    	  return key;
    		
      }
    
    @RequestMapping(value="/post", method=RequestMethod.PUT) 
    public @ResponseBody void updatepost (@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId,
    		HttpServletRequest request, ModelMap model) {	
    	
    	if(!sessionId.equals("invalid") && verifySession(sessionId)) {
    		
		        String UserPostsKey = request.getParameter("id");
		        String postTxt = request.getParameter("postTxt");
		    	
		    	String userid = getUseridFromSessionId(sessionId);
		    	List<PostHistory> posthistoryList = null;
		    	
		    	PersistenceManager pm = PMF.get().getPersistenceManager();
	    		Query q = pm.newQuery(PostHistory.class);
	    		q.setFilter("UserPostsKey == UserPostsKeyParameter");
				q.declareParameters("String UserPostsKeyParameter");
		
		    	try {
		    		
		    		posthistoryList = (List<PostHistory>) q.execute(UserPostsKey);
				    if(!posthistoryList.isEmpty()) {
				    	
				    	int version = posthistoryList.size();
				    	PostHistory posthistory = new PostHistory();
				    	posthistory.setEditor(userid);
				    	posthistory.setDateOfEdit();
				    	posthistory.setKey();
				    	posthistory.setUserPostsKey(UserPostsKey);
				    	posthistory.setPostTxt(postTxt);
				    	posthistory.setVersion(version);
				    	pm.makePersistent(posthistory);
				    	
				    }
		    		
		    	}
		    	finally {
		    		q.closeAll();
		    		pm.close();
		    	}
		    	
		    
		    	
    	}
    	
    }
    
    @RequestMapping(value="/viewposthistory/{key}", method=RequestMethod.GET)
    public String viewposthistory(@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId, 
    		@PathVariable String key, ModelMap model){
    	
    	
        String returnpage = "loginform";
    	
    	if(!sessionId.equals("invalid") && verifySession(sessionId)) {
    		
    		returnpage = "viewposthistory";
    		model.addAttribute("UserPostsKey", key);
    		
    	}
    	
    	return returnpage;
  
    }
    
    @RequestMapping(value="/edithistory", method=RequestMethod.POST)
    public @ResponseBody List<PostHistory> edithistory(@CookieValue(value = "sessionId", defaultValue = "invalid") 
           String sessionId, HttpServletRequest request) {
    	
    	String userpostskey = request.getParameter("UserPostsKey");
    	List<PostHistory> posthistory = null;
    	if(!sessionId.equals("invalid") && verifySession(sessionId)) {
          
    		PersistenceManager pm = PMF.get().getPersistenceManager();
    		Query q = pm.newQuery(PostHistory.class);
    		
    		q.setFilter("UserPostsKey == UserPostsKeyParameter");
    		q.declareParameters("String UserPostsKeyParameter");
    		q.setOrdering("version desc");
    		
    		try {
        		posthistory = (List<PostHistory>) q.execute(userpostskey);
        	}
            finally {
            	q.closeAll();
            	//pm.close();
            }
		
  	     }
    	
    	
    	return posthistory;
    	
    }
    
    
	public static boolean emailExists(String email) {
		boolean result;
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(User.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		
		try {
			List<User> results = (List<User>) q.execute(email);
			if (results.isEmpty()) {
				result = false;
			} else {
				result = true;
			}
			
		} finally {
			q.closeAll();
			pm.close();
		}
		
		return result;
	}
	
	public static boolean useridExists(String userid) {
		
		boolean result;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(User.class);
		q.setFilter("userid == useridParameter");
		q.declareParameters("String useridParameter");
		
		try {
			
			List<User> results = (List<User>) q.execute(userid);
			
			if(results.isEmpty()) {
				result = false;
				
			}
			else {
				result = true;
			}
			
		}
		finally {
			
			q.closeAll();
			pm.close();
			
		}
		
		return result;
	}
	
	public String createSession(String userid) {
		
	   PersistenceManager pm = PMF.get().getPersistenceManager();
	   UUID uuid = UUID.randomUUID();
	   String sessionId = uuid.toString();
	   String returnid = "invalid";
	   
		try {
			
			SessionObj session = new SessionObj();
			session.setUserid(userid);
			session.setSessionId(sessionId);
			pm.makePersistent(session);
			
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			syncCache.put(sessionId, userid);
			returnid = sessionId;
			
		}
		finally {
			
			pm.close();
			
		}
		
		return returnid;
	}
	
	public boolean verifySession(String sessionId) {
		
		boolean returnval = false;
		List<SessionObj> results;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		
		if(syncCache.contains(sessionId)){
			
			returnval = true;
			
		}
		else {
		
		
			Query q = pm.newQuery(SessionObj.class);
			q.setFilter("sessionId == sessionIdParameter");
			q.declareParameters("String sessionIdParameter");
			
	        try {
				results = (List<SessionObj>) q.execute(sessionId);
				
			} finally {
				q.closeAll();
			}
	        
	        if(!results.isEmpty()){
	        	returnval = true;
	        	String userid = results.get(0).getUserid();
	        	if(!syncCache.contains(sessionId))
					syncCache.put(sessionId,userid);
	        }
	        else {
	        	returnval = false;
	        }
	        
	      
		}
		
		return returnval;
		
	}

	public String getUseridFromSessionId(String sessionId) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String userid = null;
		
		if(syncCache.contains(sessionId)){
			
			userid = (String)syncCache.get(sessionId);
			
		}
		else {
	   
			Query q = pm.newQuery(SessionObj.class);
			q.setFilter("sessionId == sessionIdParameter");
			q.declareParameters("String sessionIdParameter");
			
	        try {
				List<SessionObj>results = (List<SessionObj>) q.execute(sessionId);
				if(!results.isEmpty()) 
			      userid = results.get(0).getUserid();
				  if(!syncCache.contains(sessionId))
					  syncCache.put(sessionId,userid);
				
			} 
	        finally {
				q.closeAll();
	        }
	    
		}
		
		return userid;
	        
	}

    public HashMap<String, String> getDetailsFromUserid(String userid) {
    	
    	HashMap<String, String> userDetails = new HashMap<String, String>();
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	
    	if(userid != null) {
        	
	    	Query q = pm.newQuery(User.class);
	    	q.setFilter("userid == useridParameter");
			q.declareParameters("String useridParameter");
			
			try {
				
				List<User> results = (List<User>) q.execute(userid);
	
				if (!results.isEmpty()) {
				  
				  userDetails.put("name",results.get(0).getName());
				  userDetails.put("userid",results.get(0).getUserid());
				  userDetails.put("email",results.get(0).getEmail());
				  userDetails.put("phone",results.get(0).getPhone());
				  
				}
				
				
			}
			finally {
				q.closeAll();
				pm.close();
			}
			
    	}
    	
    	return userDetails;
    }
    
    
//  @RequestMapping(value = "/UserPosts", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
//  public @ResponseBody UserPosts createUserPost(@RequestBody  UserPosts post, 
//  		@CookieValue(value = "sessionId", defaultValue = "invalid") String sessionId) {
//  	
//  	String userid = null;
//  	
//  	
//  	if(!sessionId.equals("invalid")) {
//  		
//  	        userid = getUseridFromSessionId(sessionId);    	
//  	
//		    	PersistenceManager pm = PMF.get().getPersistenceManager();
//		        
//		    	try {
//		    		
//		    		//post.setKey();
//		    		post.setDate();
//		    		post.setAuthor(userid);
//		    		pm.makePersistent(post);
//		    		
//		    	}
//		    	finally {
//		    		
//		    		pm.close();
//		    		
//		    	}
//		    	
//  	}
//		    	
//		    	return post;
//  }
    
    
//    @RequestMapping(value = "/UserPosts", method=RequestMethod.GET)
//    public String getUserPosts(@CookieValue(value = "sessionId", defaultValue = "invalid") 
//                  String sessionId,ModelMap model) {
//    	
//    	List<UserPosts> posts = null;
//    	PersistenceManager pm = PMF.get().getPersistenceManager();
//		Query q = pm.newQuery(UserPosts.class);
//    	q.setOrdering("date desc");
//    	q.setRange(0, 2);
//    	
//    		try {
//    			
//    			posts = (List<UserPosts>) q.execute();
//    			if(!posts.isEmpty()) {
//	    			model.addAttribute("posts", posts);
//    			}
//    			
//    			Cursor cursor = JDOCursorHelper.getCursor(posts);
//    			String cursorString = cursor.toWebSafeString();
//    			model.addAttribute("cursorString",cursorString);
//    		
//    			
//    			
//    		}
//    		finally {
//    			
//    			q.closeAll();
//    			pm.close();
//    		}
//    		
//          return "postspage";
//  
//    }
    
//    @RequestMapping(value="/UserPosts/{id}",  method=RequestMethod.PUT)
//    public @ResponseBody UserPosts updateUserPost(@PathVariable Long id, @RequestBody UserPosts post) {
//    	
//    	PersistenceManager pm = PMF.get().getPersistenceManager();
//		Query q = pm.newQuery(UserPosts.class);
//		q.setFilter("id == idParameter");
//		q.declareParameters("String idParameter");
//    	
//    	try {
//			List<User> results = (List<User>) q.execute(id);
// 
//			if (!results.isEmpty()) {
//		        
//				pm.makePersistent(post);
//	           
//			} 
//			
//			
//		} finally {
//			q.closeAll();
//			pm.close();
//		}
//		
//    	return post;
//    	
//    	
//    }
//    
//    @RequestMapping(value="/UserPosts/{id}", method=RequestMethod.DELETE)
//    public @ResponseBody void deleteUserPost(@PathVariable String id) {
//    	
//    	PersistenceManager pm = PMF.get().getPersistenceManager();
//		Query q = pm.newQuery(UserPosts.class);
//		q.setFilter("id == idParameter");
//		q.declareParameters("String idParameter");
//    	
//    	try {
//			List<User> results = (List<User>) q.execute(id);
// 
//			if (!results.isEmpty()) {
//		        
//				pm.deletePersistent(results.get(0));
//	            
//				
//			} 
//			
//			
//		} finally {
//			q.closeAll();
//			pm.close();
//		}
//    	
//    }
//    
    
}
