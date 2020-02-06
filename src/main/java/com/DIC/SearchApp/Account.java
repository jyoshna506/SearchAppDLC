package com.DIC.SearchApp;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"attributes",
"Id",
"Name",
"Path",
"Fax",
"Type",
"Industry",
"Phone"
})
public class Account {
	
	@JsonProperty("attributes")
	private Attribute attributes;
	@JsonProperty("Id")
	private String id;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Path")
	private String path;	
	@JsonProperty("Fax")
	private String fax;
	
	@JsonProperty("Path")
	public String getPath() {
		return path;
	}

	@JsonProperty("Path")
	public void setPath(String path) {
		this.path = path;
	}

	@JsonProperty("Type")
	private String type;
	@JsonProperty("Industry")
	private String industry;
	@JsonProperty("Phone")
	private String phone;
	
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	 
	@JsonProperty("attributes")
	public Attribute getAttributes() {
	return attributes;
	}
	
	@JsonProperty("Fax") 
	public String getFax() {
		return fax;
	}
	
	@JsonProperty("Fax")
	public void setFax(String fax) {
		this.fax = fax;
	}

	@JsonProperty("Type")
	public String getType() {
		return type;
	}

	@JsonProperty("Type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("Industry")
	public String getIndustry() {
		return industry;
	}

	@JsonProperty("Industry")
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@JsonProperty("Phone")
	public String getPhone() {
		return phone;
	}

	@JsonProperty("Phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty("attributes")
	public void setAttributes(Attribute attributes) {
	this.attributes = attributes;
	}
	 
	@JsonProperty("Id")
	public String getId() {
	return id;
	}
	 
	@JsonProperty("Id")
	public void setId(String id) {
	this.id = id;
	}
	 
	@JsonProperty("Name")
	public String getName() {
	return name;
	}
	 
	@JsonProperty("Name")
	public void setName(String name) {
	this.name = name;
	}
	 
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}
	 
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
}
