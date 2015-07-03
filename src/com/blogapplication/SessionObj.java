package com.blogapplication;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;
@PersistenceCapable
public class SessionObj {
  
	@PrimaryKey
    private String sessionId;
	@Persistent
	private String email;
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId)  {
		this.sessionId = sessionId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email)  {
		this.email = email;
	}
	
}
