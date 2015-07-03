package com.blogapplication;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class UserPosts {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
	@Persistent
	private String title;
	@Persistent
    private Date date;
	@Persistent
	private String username;
	@Persistent
	private String email;
	@Persistent
	private Text postTxt;
	
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getpostTxt() {
    	return postTxt.getValue();
    }
    
    public void setPostTxt(String postTxt) {
    	this.postTxt = new Text(postTxt);
    }
	
    public String getTitle() {
    	return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public long getId() {
    	return id;
    }
    
    
    public String getDate() {
    	return date.toString();
    }
    
    public void setDate() {
    	date = new Date();
    }

    public void setUsername(String title) {
    	this.title = title;
    }
   
    public String getUsername(String title) {
    	return username;
    }
    
}
