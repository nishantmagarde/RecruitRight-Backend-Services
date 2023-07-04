package com.gcp.recruitRight.response;

import java.io.ByteArrayInputStream;

public class UserProfileData {

	private String userId;
	private String name;
	private String contact;
	private String uploader;
	private String status;
	
	public UserProfileData()
	{
		
	}
	public UserProfileData(String userId, String name, String contact, String uploader, String status) {
		super();
		this.userId = userId;
		this.name = name;
		this.contact = contact;
		this.uploader = uploader;
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
