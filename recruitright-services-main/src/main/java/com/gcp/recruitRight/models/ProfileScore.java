package com.gcp.recruitRight.models;

public class ProfileScore {
	
	private int reqId;
	private String userId;
	private double profileScore;
	private String status;
	
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getProfileScore() {
		return profileScore;
	}
	public void setProfileScore(double profileScore) {
		this.profileScore = profileScore;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
