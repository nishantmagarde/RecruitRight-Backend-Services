package com.gcp.recruitRight.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.gcp.recruitRight.Requests.PostRequirementRequest;
import com.gcp.recruitRight.Util.UsernameStorage;

@Repository
public class PostRequirementRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int postRequirement(PostRequirementRequest postRequirementRequest) {
		
		String sql = "INSERT INTO REQUIREMENTS(userId,isu,subIsu,projectName,jobRole,jobRoleType,techStack,experience) values(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,UsernameStorage.getUserId(),postRequirementRequest.getIsu(),postRequirementRequest.getSubIsu(),postRequirementRequest.getProjectName(),postRequirementRequest.getJobRole(),postRequirementRequest.getJobRoleType(),postRequirementRequest.getTechStack(),postRequirementRequest.getExperience());
	}
	
	public int getReqId()
	{
		String sql = "SELECT MAX(reqId) from REQUIREMENTS";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
}
