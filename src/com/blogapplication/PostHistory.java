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
public class PostHistory {
    
	@PrimaryKey
	private Key key;
	
	@Persistent
	private String UserPostsKey;
  
	@Persistent
	private Date dateOfEdit;
	
	@Persistent
	private String editor;

	@Persistent
	private Text postTxt;
	
	@Persistent
	private int version;
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

//	public Key getKey() {
//		return key;
//	}

	public void setKey() {
		UUID uuid = UUID.randomUUID();
    	this.key = KeyFactory.createKey(PostHistory.class.getSimpleName(), uuid.toString());
	}

	public String getUserPostsKey() {
		return  UserPostsKey;
	}

	public void setUserPostsKey(String UserPostsKey) {
		this.UserPostsKey = UserPostsKey;
	}

	public long getDateOfEdit() {
		return dateOfEdit.getTime();
	}

	public void setDateOfEdit() {
		this.dateOfEdit = new Date();
	}

	public String getPostTxt() {
		return postTxt.getValue();
	}

	public void setPostTxt(String postTxt) {
		this.postTxt = new Text(postTxt);
	}
	
}
