package com.gcp.recruitRight.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gcp.recruitRight.models.Feedback;
import com.gcp.recruitRight.models.Requirement;
import com.gcp.recruitRight.models.UserProfile;

@Repository
public class ProcessProfileRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Requirement> fetchActiveRequirements()	{
		
		String sql = "SELECT * FROM REQUIREMENTS where status='active'";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Requirement.class));
	}
	
	public List<UserProfile> fetchActiveUserProfiles()	{
		String sql = "SELECT * FROM USERPROFILES where status='active'";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserProfile.class));
	}
	
	public int updateProfileScore(int reqId, String userId, double profileScore)
	{	
		
		String sql = "INSERT INTO PROFILESCORES(reqId,userId,profileScore) values(?,?,?)";
		int result = jdbcTemplate.update(sql,reqId,userId,profileScore);
		return result;
	}
	
	public int updateProfileScoreStatus(int reqId, int limit)
	{
		String sql = "UPDATE PROFILESCORES SET status = ? where reqId=? and profileScore>=50 order by profileScore desc limit ?";
		return jdbcTemplate.update(sql,"InProgress",reqId,limit);
	}
	
	public int updateUserProfileStatus()
	{
		String sql = "update userprofiles set status = \"InProgress\" where userId in (select userId from profileScores where status = \"InProgress\")";
		return jdbcTemplate.update(sql);
	}
	
	public List<UserProfile> fetchTopProfiles(int reqId){
		
		String sql = "select * from userprofiles where userId in (select userId from profilescores where status = \"InProgress\" and reqId = ?)";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserProfile.class),reqId);
	}
	
	public int updateRequirementStatus(int reqId)
	{
		String sql = "Update requirements set status = \"InProgress\" where reqId = ?";
		return jdbcTemplate.update(sql,reqId);
		
	}
	
	public void insertIntoFeedback(int reqId,String userId, String name, String contact, double profileScore, String status)
	{
		String sql1 = "SELECT * FROM FEEDBACK where reqId=? and userId=?";
		List<Feedback> feedbackList = jdbcTemplate.query(sql1,new BeanPropertyRowMapper(Feedback.class),reqId,userId);
		if(feedbackList.size()==0)
		{
			String sql2 = "INSERT INTO FEEDBACK(reqId,userId,name,contact,profileScore,status) values(?,?,?,?,?,?)";
			jdbcTemplate.update(sql2,reqId,userId,name,contact,profileScore,status);
		}
	}
	
	public double fetchProfileScores(int reqId, String userId) {
		
		String sql = "select profileScore from profilescores where reqId = ? and userId = ?";
		return jdbcTemplate.queryForObject(sql,double.class,reqId,userId);
		
	}
	
	
}
