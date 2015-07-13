package com.blogapplication;

import java.util.Date;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class PostHistory {
    
	@PrimaryKey
	private Key key;
	
	@Persistent
	private String UserPostsKey;
    
	@Persistent
	private int version = 0;
	
	@Persistent
	private Date date;
	
	@Persistent
	private String txtBeforeEdit;
	
	@Persistent
	private String txtAfterEdit;

	public Key getKey() {
		return key;
	}

	public void setKey() {
		UUID uuid = UUID.randomUUID();
    	this.key = KeyFactory.createKey(UserPosts.class.getSimpleName(), uuid.toString());
	}

	public String getUserPostsKey() {
		return  UserPostsKey;
	}

	public void setPostid(String UserPostsKey) {
		this.UserPostsKey = UserPostsKey;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion() {
		this.version = this.version + 1;
	}

	public Date getDate() {
		return date;
	}

	public void setDate() {
		this.date = new Date();
	}

	public String getTxtBeforeEdit() {
		return txtBeforeEdit;
	}

	public void setTxtBeforeEdit(String txtBeforeEdit) {
		this.txtBeforeEdit = txtBeforeEdit;
	}

	public String getTxtAfterEdit() {
		return txtAfterEdit;
	}

	public void setTxtAfterEdit(String txtAfterEdit) {
		this.txtAfterEdit = txtAfterEdit;
	}

	
}
