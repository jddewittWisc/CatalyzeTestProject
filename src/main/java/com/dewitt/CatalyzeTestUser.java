package com.dewitt;

import org.eclipse.jetty.server.Authentication.User;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.UserIdentity.Scope;

public class CatalyzeTestUser implements User{

	private String username;
	private String fullName;
	private String emailAddress;
	
	@Override
	public String getAuthMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserIdentity getUserIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserInRole(Scope arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getFullName(){
		return this.fullName;
	}
	
	public void setFullName(String fullName){
		this.fullName = fullName;
	}
	
	public String getEmailAddress(){
		return this.emailAddress;
	}
	
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

}
