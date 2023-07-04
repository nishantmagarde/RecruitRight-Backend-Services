package com.gcp.recruitRight.Repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gcp.recruitRight.models.Feedback;
import com.gcp.recruitRight.models.ProfileScore;
import com.gcp.recruitRight.models.Requirement;
import com.gcp.recruitRight.models.User;
import com.gcp.recruitRight.models.UserProfile;

@Repository
public class UserProfileRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Logger log = LoggerFactory.getLogger(UserProfileRepository.class);
	
	public User fetchUser(String userId)
	{
		String sql = "SELECT * FROM USER where userId=?";
		List<User> users = jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class),userId);
		for(User user:users)
			return user;
		return null;
	}
	
	public int updateUser(String userId, String firstName, String lastName, String contact)
	{
		String sql = "UPDATE USER SET firstName = ?, lastName = ?, contact = ? where userId = ?";
		return jdbcTemplate.update(sql,firstName, lastName, contact, userId);
	}
	
	public List<User> fetchAllUsers()
	{
		String sql = "SELECT * FROM USER";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
		return users;
	}
	
	public List<Requirement> fetchRequirementById(String userId)
	{
		String sql = "SELECT * FROM REQUIREMENTS where userId=?";
		List<Requirement> requirements = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Requirement.class),userId);
		return requirements;
	}
	
	public List<Requirement> fetchAllRequirements()
	{
		String sql = "SELECT * FROM REQUIREMENTS";
		List<Requirement> requirements = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class));
		return requirements;
	}
	
	public List<ProfileScore> fetchProfileScoresById(String userId)
	{
		String sql = "SELECT * FROM PROFILESCORES WHERE userId=? and status is NOT NULL";
		List<ProfileScore> profileScores = jdbcTemplate.query(sql,new BeanPropertyRowMapper(ProfileScore.class),userId);
		return profileScores;
	}
	
	public List<Requirement> fetchRequirementByReqId(int reqId)
	{
		String sql = "SELECT * FROM REQUIREMENTS where reqId=?";
		List<Requirement> requirements = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Requirement.class),reqId);
		return requirements;
	}
	
	public List<Feedback> fetchInProgressProfilesForPOC(String userId)
	{
		String sql = "SELECT * FROM FEEDBACK where reqId in (SELECT reqId from REQUIREMENTS where userId=?)";
		List<Feedback> feedbackList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Feedback.class),userId);
		return feedbackList;
	}
	
	public List<UserProfile> fetchUserProfilesForRMG(String uploader)
	{
		log.info("Entering UserProfileRepository.fetchUserProfilesForRMG()");
		String sql = "SELECT * FROM USERPROFILES where uploader=?";
		List<UserProfile> userProfiles = jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),uploader);
		log.info("Exiting UserProfileRepository.fetchUserProfilesForRMG()");
		return userProfiles;
	}
	
	public int updateUserPassword(String userId,String password)
	{
		String sql = "UPDATE USER SET password=? where userId=?";
		return jdbcTemplate.update(sql,password,userId);
	}
	
	public Requirement fetchRequirementForSelectedUser(String userId)
	{
		String sql = "SELECT * FROM REQUIREMENTS where reqId in (SELECT reqId FROM PROFILESCORES where userId=? and status=?)";
		List<Requirement> reqs = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),userId,"Selected");
		for(Requirement r:reqs)
		{
			System.out.println(r.getReqId()+" "+r.getProjectName());
			return r;
		}
		return null;
	}
	
	public int updateRequirementStatus(int reqId, String status)
	{
		String sql = "UPDATE REQUIREMENTS SET status=? where reqId=?";
		return jdbcTemplate.update(sql,status,reqId);
	}
	
	public List<Requirement> fetchAllActiveRequirements()
	{
		String sql = "SELECT * FROM REQUIREMENTS where status=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),"active");
	}
	
	public List<Requirement> fetchAllClosedRequirements()
	{
		String sql = "SELECT * FROM REQUIREMENTS where status=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),"Closed");
	}
	
	public List<Requirement> fetchAllInProgressRequirements()
	{
		String sql = "SELECT * FROM REQUIREMENTS where status=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),"InProgress");
	}
	
	public List<Requirement> fetchAllActiveRequirementsById(String userId)
	{
		String sql = "SELECT * FROM REQUIREMENTS where status=? and userId=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),"active",userId);
	}
	
	public List<Requirement> fetchAllClosedRequirementsById(String userId)
	{
		String sql = "SELECT * FROM REQUIREMENTS where status=? and userId=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),"Closed",userId);
	}
	
	public List<Requirement> fetchAllInProgressRequirementsById(String userId)
	{
		String sql = "SELECT * FROM REQUIREMENTS where status=? and userId=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Requirement.class),"InProgress",userId);
	}
	
	public List<UserProfile> fetchAllActiveUserProfiles()
	{
		String sql = "SELECT * FROM USERPROFILES where status=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),"active");
	}
	
	public List<UserProfile> fetchAllSelectedUserProfiles()
	{
		String sql = "SELECT * FROM USERPROFILES where status=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),"Selected");
	}
	
	public List<UserProfile> fetchAllInProgressUserProfiles()
	{
		String sql = "SELECT * FROM USERPROFILES where status=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),"InProgress");
	}
	
	public List<UserProfile> fetchAllActiveUserProfilesById(String uploader)
	{
		String sql = "SELECT * FROM USERPROFILES where status=? and uploader=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),"active",uploader);
	}
	
	public List<UserProfile> fetchAllSelectedUserProfilesById(String uploader)
	{
		String sql = "SELECT * FROM USERPROFILES where status=? and uploader=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),"Selected",uploader);
	}
	
	public List<UserProfile> fetchAllInProgressUserProfilesById(String uploader)
	{
		String sql = "SELECT * FROM USERPROFILES where status=? and uploader=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserProfile.class),"InProgress",uploader);
	}
}
