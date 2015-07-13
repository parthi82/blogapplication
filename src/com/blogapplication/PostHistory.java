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
	private Date date;
	
	@Persistent
	private String editor;

	@Persistent
	private Text txtBeforeEdit;
	
	@Persistent
	private Text txtAfterEdit;
	
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Key getKey() {
		return key;
	}

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

	public String getDate() {
		return date.toString();
	}

	public void setDate() {
		this.date = new Date();
	}

	public String getTxtBeforeEdit() {
		return txtBeforeEdit.getValue();
	}

	public void setTxtBeforeEdit(String txtBeforeEdit) {
		this.txtBeforeEdit = new Text(txtBeforeEdit);
	}

	public String getTxtAfterEdit() {
		return txtAfterEdit.getValue();
	}

	public void setTxtAfterEdit(String txtAfterEdit) {
		this.txtAfterEdit = new Text(txtAfterEdit);
	}

	
}
