package com.blogapplication;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
@PersistenceCapable
public class SessionObj {
  
	@PrimaryKey
    private String sessionId;
	@Persistent
	private String userid;
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId)  {
		this.sessionId = sessionId;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid)  {
		this.userid = userid;
	}
}
