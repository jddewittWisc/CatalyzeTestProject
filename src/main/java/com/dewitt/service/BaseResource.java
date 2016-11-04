package com.dewitt.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dewitt.configuration.Representation;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class BaseResource {

	public String content;
	public final String user;
	public final String fullName;
	public final String emailAddress;
	
	@GET
	public Representation sessionCheck(){
		if(user == null){
			content = "Hello world";
		}else{
			content = String.format(user,  fullName, emailAddress);
		}
		
		return new Representation(content);
	}
	
	public BaseResource(String user, String fullName, String emailAddress){
		this.user = user;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
	}
}
