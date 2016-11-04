package com.dewitt.configuration;


import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class CatalyzeTestConfig extends Configuration{
	

	
	@NotEmpty
	private String user;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String fullName;
	
	@NotEmpty
	private String emailAddress;
	
	@JsonProperty
	public String getUser(){
		return user;
	}
	
	@JsonProperty
	public void setUser(String user){
		this.user = user;
	}
	
	@JsonProperty
	public String getPassword(){
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password){
		this.password = password;
	}
	
	@JsonProperty
	public String getFullName(){
		return fullName;
	}
	
	@JsonProperty
	public void setFullName(String fullName){
		this.fullName = fullName;
	}
	
	@JsonProperty
	public String getEmailAddress(){
		return emailAddress;
	}
	
	@JsonProperty
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

}
