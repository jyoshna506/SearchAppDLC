package com.DIC.SearchApp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"url"
})
public class Attribute {
	
	private String type;
	private String url;
	 
	public String getType() {
	return type;
	}
	public void setType(String type) {
	this.type = type;
	}
	public String getUrl() {
	return url;
	}
	public void setUrl(String url) {
	this.url = url;
	}
}
