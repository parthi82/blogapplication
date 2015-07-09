package com.blogapplication;
import java.util.Date;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class UserPosts {
	
	@PrimaryKey
    private String id;
	@Persistent
	private String title;
	@Persistent
    private Date date;
	@Persistent
	private String author;
	@Persistent
	private Text postTxt;
	
    public String getId() {
		return id;
	}
    
    public void setId() {
    	UUID uuid = UUID.randomUUID();
    	this.id = uuid.toString();
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
