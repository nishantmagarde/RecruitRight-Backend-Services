package com.gcp.recruitRight.Requests;


public class PostRequirementRequest {
	
	private String isu;
	private String subIsu;
	private String projectName;
	private String jobRole;
	private String jobRoleType;
	private String techStack;
	private double experience;
	

	public String getIsu() {
		return isu;
	}

	public void setIsu(String isu) {
		this.isu = isu;
	}

	public String getSubIsu() {
		return subIsu;
	}

	public void setSubIsu(String subIsu) {
		this.subIsu = subIsu;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getJobRoleType() {
		return jobRoleType;
	}

	public void setJobRoleType(String jobRoleType) {
		this.jobRoleType = jobRoleType;
	}

	public String getTechStack() {
		return techStack;
	}

	public void setTechStack(String techStack) {
		this.techStack = techStack;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}
	

}
