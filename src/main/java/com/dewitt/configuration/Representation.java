package com.dewitt.configuration;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Representation {

	@Length(max = 4)
	private String content;
	
	public Representation(){
		
	}
	
	@JsonProperty
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public Representation(String content){
		this.content = content;
	}
}
