package com.gcp.recruitRight.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gcp.recruitRight.models.Feedback;
import com.gcp.recruitRight.models.ProfileScore;

@Repository
public class FeedbackRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int updateFeedback(int reqId,String userId,String status,String remarks)
	{
		String sql = "UPDATE FEEDBACK SET status=?,remarks=? where reqId=? and userId=?";
		return jdbcTemplate.update(sql,status,remarks,reqId,userId);
	}
	
	public List<Feedback> fetchFeedbackById(int reqId, String userId)
	{
		String sql = "SELECT * FROM FEEDBACK where reqId=? and userId=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Feedback.class),reqId,userId);
	}
	
	public int updateProfileScoreStatus(int reqId, String userId,String status)
	{
		String sql = "UPDATE PROFILESCORES SET status=? where reqId=? and userId=?";
		return jdbcTemplate.update(sql, status,reqId,userId);
	}
	
	public int getInprogressProfileScores(String userId)
	{
		String sql = "SELECT * FROM PROFILESCORES where userId=? and status = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProfileScore.class),userId,"InProgress").size();
	}
	
	public int updateUserProfileStatus(String userId,String status)
	{
		String sql = "UPDATE USERPROFILES SET status=? where userId=?";
		return jdbcTemplate.update(sql, status,userId);
	}
	
	public int updateProfileScoreStatusToUnavailable(int reqId, String userId,String status)
	{
		String sql = "UPDATE PROFILESCORES SET status=? where reqId!=? and userId=?";
		return jdbcTemplate.update(sql, status,reqId,userId);
	}
	
}
