package com.gcp.recruitRight.response;

import java.util.List;

import com.gcp.recruitRight.models.Feedback;
import com.gcp.recruitRight.models.Requirement;
import com.gcp.recruitRight.models.User;
import com.gcp.recruitRight.models.UserProfile;

public class BaseResponse {
	
	private int reqId;
	private User user;
	private Boolean booleanMsg;
	private String exceptionMessage;
	private String jwtToken;
	private Requirement requirement;
	private List<User> users;
	private List<Requirement> requirements;
	private List<UserProfileData> userProfiles;
	private List<UserProfileStatus> userProfileStatusList;
	private List<Feedback> feedbackList;
	private List<UserProfileResponse> userProfileResponse;
	private List<String> incorrectProfiles;
	
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Boolean getBooleanMsg() {
		return booleanMsg;
	}
	public void setBooleanMsg(Boolean booleanMsg) {
		this.booleanMsg = booleanMsg;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Requirement> getRequirements() {
		return requirements;
	}
	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}
	public List<UserProfileData> getUserProfiles() {
		return userProfiles;
	}
	public void setUserProfiles(List<UserProfileData> userProfiles) {
		this.userProfiles = userProfiles;
	}
	public List<UserProfileStatus> getUserProfileStatusList() {
		return userProfileStatusList;
	}
	public void setUserProfileStatusList(List<UserProfileStatus> userProfileStatusList) {
		this.userProfileStatusList = userProfileStatusList;
	}
	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}
	public List<UserProfileResponse> getUserProfileResponse() {
		return userProfileResponse;
	}
	public void setUserProfileResponse(List<UserProfileResponse> userProfileResponse) {
		this.userProfileResponse = userProfileResponse;
	}
	public List<String> getIncorrectProfiles() {
		return incorrectProfiles;
	}
	public void setIncorrectProfiles(List<String> incorrectProfiles) {
		this.incorrectProfiles = incorrectProfiles;
	}
	
	
	
}
