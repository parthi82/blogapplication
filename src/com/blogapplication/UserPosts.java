package com.blogapplication;
import java.util.Date;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class UserPosts {
	
	@PrimaryKey
    private Key key;
	@Persistent
	private String title;
	@Persistent
    private Date date;
	@Persistent
	private String author;
	@Persistent
	private Text postTxt;
	
    public Key getKey() {
		return key;
	}
    
    public void setKey() {
    	UUID uuid = UUID.randomUUID();
    	this.key = KeyFactory.createKey(UserPosts.class.getSimpleName(), uuid.toString());
    }
    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date.toString();
	}

	public void setDate() {
		date = new Date();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
    
    public String getpostTxt() {
    	return postTxt.getValue();
    }
    
    public void setPostTxt(String postTxt) {
    	this.postTxt = new Text(postTxt);
    }
    
}
