package com.blogapplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CookieValue;

import javax.jdo.PersistenceManager;
import javax.servlet.http.Cookie; 
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.jdo.Query;

import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Controller
public class LoginController {
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutUser(@CookieValue(value = "sessionId", defaultValue = "null") String sessionId, 
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
				System.out.println("memcache deleted...................");
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
    public String showProfilePage(@CookieValue(value = "sessionId", defaultValue = "null") String sessionId){
		
		if(!sessionId.equals("null") && verifySession(sessionId)) {
		   //return "redirect:/pages/userpage.html";
		   return "redirect:pages/userhomepage.html";
		}
		else {
			return "redirect:signupform";
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
    public String showLoginForm(){
        
        return "loginform";
        
    }
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginuser(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
	   String email = request.getParameter("email");
	   String password = request.getParameter("password");
	   System.out.println("running... /login");
	   if(verifyLogin(email,password)) {
            
		   String sessionId = createSession(email);
		 
		   if(!sessionId.equals("null")) {
			   
			   Cookie cookie = new Cookie("sessionId", sessionId);
			   cookie.setMaxAge(10000); 
			   response.addCookie(cookie);
			   return "redirect:profilepage";
			   
		   }
		   else {
			   
			   return "loginform";
			   
		   }
		   
		   
	   }
	   else {
		  
		   System.out.println("emailNotExist(email) : " + emailNotExist(email));
		   
		   if(!emailNotExist(email)){
			   
			   request.setAttribute("email", email);
		   }
		   
		   return "loginform";
		   
	   }
	   
	}
	
	/*
	@RequestMapping(value="/getObj", method=RequestMethod.GET)
	public @ResponseBody SessionObj getObj() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(SessionObj.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		List<SessionObj> lst = (List<SessionObj>) q.execute("admin@admin.com");
		return lst.get(0);
	    
	} 
	*/
	
	 @RequestMapping(value="/add", method=RequestMethod.POST)
	 public String addUser(HttpServletResponse response, HttpServletRequest request) {
	    
       	String name = request.getParameter("name");
       	String email = request.getParameter("email");
       	String phone = request.getParameter("phone");
       	String password = request.getParameter("password");
       	
       	Pattern p1 = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+");
		Matcher m = p1.matcher(email); 
      	
           if(emailNotExist(email) && m.matches()) {     	
           	
       	        PersistenceManager pm = PMF.get().getPersistenceManager();
			    		
		    				User n = new User();
		    	        	n.setName(name);
		    	        	n.setEmail(email);
		    	        	n.setPhone(phone); 
		    	        	n.setPassword(password);
				        	
				    		try {
				    			
				    			pm.makePersistent(n);
				    			
				    			String sessionId = createSession(email);
				    			
				    			if(!sessionId.equals("null")) {
				    				Cookie cookie = new Cookie("sessionId", sessionId);
				    				cookie.setMaxAge(10000); 
				    				response.addCookie(cookie);
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
	    public @ResponseBody HashMap<String, String> getDetails(@CookieValue(value = "sessionId", defaultValue = "null") String sessionId, 
	    		HttpServletRequest request) {
	    	
		    HashMap<String, String> details;
	    	String email = null;
	    	
	    	if(!sessionId.equals("null")) 
	    	    email = getEmailFromSessionId(sessionId);
	    	
	    	if(email != null) {
	    	   details =  getDetailsFromEmail(email);
	    	}
	    	else {
	    		details = new HashMap<String, String>();
	    	}
	    	
	    	return details;
	 }
	 
    
    @RequestMapping(value = "/UserPosts", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody UserPosts createUserPost(@RequestBody  UserPosts post, 
    		@CookieValue(value = "sessionId", defaultValue = "null") String sessionId) {
    	
    	String email = null;
    	//String postTxt = request.getParameter("postTxt");
    	//String title = request.getParameter("title");
    	
    	
    	System.out.println("postTxt : " + post.getpostTxt());
    	
    	if(!sessionId.equals("null")) {
    		
    	     email = getEmailFromSessionId(sessionId);
    	    //HashMap<String, String> details = getDetailsFromEmail(email);
    	    //if(details.get("name") != null)
    	    //author = details.get("name");
    	    
    	    
    	}
    	
    	
    	PersistenceManager pm = PMF.get().getPersistenceManager();
        
    	try {
    		
    		post.setDate();
    		post.setEmail(email);
    		pm.makePersistent(post);
    		
    	}
    	finally {
    		
    		pm.close();
    		
    	}
    	
    	
    	
    	return post;
    }
	
    @RequestMapping(value = "/UserPosts", method=RequestMethod.GET)
    public @ResponseBody List<UserPosts> getUserPosts() {
    	
    	List<UserPosts> posts;
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	Query q = pm.newQuery(UserPosts.class);
    	
    		try {
    			
    			posts = (List<UserPosts>) q.execute();
    		}
    		finally {
    			
    			q.closeAll();
    			
    		}
    		
    		return posts;
    }
    
    @RequestMapping(value="/UserPosts/{id}", method=RequestMethod.GET) 
    public @ResponseBody UserPosts getUserPost(@PathVariable Long id) {
    	
    	PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserPosts.class);
		q.setFilter("id == idParameter");
		q.declareParameters("String idParameter");
    	UserPosts post;
		
    	try {
    		List<UserPosts> result = (List<UserPosts>) q.execute(id);
    		post = result.get(0);
    	}
    	finally {
    		q.closeAll();
    		pm.close();
    	}
    	
    	return post;
    }
    
    @RequestMapping(value="/UserPosts/{id}",  method=RequestMethod.PUT)
    public @ResponseBody UserPosts updateUserPost(@PathVariable Long id, @RequestBody UserPosts post) {
    	
    	PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserPosts.class);
		q.setFilter("id == idParameter");
		q.declareParameters("String idParameter");
    	
    	try {
			List<User> results = (List<User>) q.execute(id);
 
			if (!results.isEmpty()) {
		        
				pm.makePersistent(post);
	            
				
			} 
			
			
		} finally {
			q.closeAll();
			pm.close();
		}
		
    	return post;
    	
    	
    }
    
    @RequestMapping(value="/UserPosts/{id}", method=RequestMethod.DELETE)
    public @ResponseBody void deleteUserPost(@PathVariable Long id) {
    	
    	
    	PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserPosts.class);
		q.setFilter("id == idParameter");
		q.declareParameters("String idParameter");
    	
    	try {
			List<User> results = (List<User>) q.execute(id);
 
			if (!results.isEmpty()) {
		        
				pm.deletePersistent(results.get(0));
	            
				
			} 
			
			
		} finally {
			q.closeAll();
			pm.close();
		}
    	
    }
    
	private static boolean emailNotExist(String email) {
		
		boolean rslt;
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(User.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		
		try {
			List<User> results = (List<User>) q.execute(email);
 
			if (results.isEmpty()) {
				rslt = true;
				System.out.println("email doesn't exist..." +rslt);
			} else {
				rslt = false;
				System.out.println("email exists" +rslt);
			}
			
		} finally {
			q.closeAll();
			pm.close();
		}
		
		System.out.println("email" +rslt);
		return rslt;
	}
	
	private static boolean verifyLogin(String email, String password) {
		
		boolean rslt = false;
		List<User> results;
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(User.class);
		q.setFilter("email == emailParameter");
		q.declareParameters("String emailParameter");
		
      
        
		try {
			
			results = (List<User>) q.execute(email);
			
			
		} finally {
			
			q.closeAll();
			
		}
		
		
		if (!results.isEmpty()) {
			
			   User usr = (User)results.get(0);
			   String pwd = usr.getPassword();
			   if(pwd.equals(password))
				   rslt = true;
			   pm.close();
			   
	    } 
		else {
			
				rslt = false;
				
	    }
		
		
		return rslt;
		
	}
	
	public String createSession(String email) {
		
	   PersistenceManager pm = PMF.get().getPersistenceManager();
	   UUID uuid = UUID.randomUUID();
	   String sessionId = uuid.toString();
	   String returnid = "null";
	   
		try {
			
			SessionObj session = new SessionObj();
			session.setEmail(email);
			session.setSessionId(sessionId);
			pm.makePersistent(session);
			
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			syncCache.put(sessionId, email);
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
		String email;
		
		if(syncCache.contains(sessionId)){
			
			returnval = true;
			System.out.println("memcache checked for sessionId = " + sessionId);
			
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
	        	email = results.get(0).getEmail();
	        	if(!syncCache.contains(sessionId))
					syncCache.put(sessionId,email);
	        }
	        else {
	        	returnval = false;
	        }
	        
	      
		}
		
		return returnval;
		
	}
	
	public String getEmailFromSessionId(String sessionId) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String email = null;
		
		if(syncCache.contains(sessionId)){
			
			email = (String)syncCache.get(sessionId);
			System.out.println("got email form memcache........................... ");
			
		}
		else {
	   
			Query q = pm.newQuery(SessionObj.class);
			q.setFilter("sessionId == sessionIdParameter");
			q.declareParameters("String sessionIdParameter");
			
	        try {
				List<SessionObj>results = (List<SessionObj>) q.execute(sessionId);
				if(!results.isEmpty()) 
			      email = results.get(0).getEmail();
				  if(!syncCache.contains(sessionId))
					  syncCache.put(sessionId,email);
				
			} 
	        finally {
				q.closeAll();
	        }
	    
		}
		
		return email;
	        
	}
	
    public HashMap<String, String> getDetailsFromEmail(String email) {
    	
    	HashMap<String, String> userDetails = new HashMap<String, String>();
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	
    	if(email != null) {
        	
	    	Query q = pm.newQuery(User.class);
	    	q.setFilter("email == emailParameter");
			q.declareParameters("String emailParameter");
			
			try {
				
				List<User> results = (List<User>) q.execute(email);
	
				if (!results.isEmpty()) {
					
				  userDetails.put("email",results.get(0).getEmail());
				  userDetails.put("name",results.get(0).getName());
				  userDetails.put("phone",results.get(0).getPhone());
				  
				}
				
				
			}finally {
				q.closeAll();
				pm.close();
			}
			
    	}
    	
    	return userDetails;
    }
    
}
