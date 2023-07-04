package com.gcp.recruitRight.Impls;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.recruitRight.Repository.FeedbackRepository;
import com.gcp.recruitRight.Requests.FeedbackRequest;
import com.gcp.recruitRight.models.Feedback;

@Service
public class FeedbackImpl {
	
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	Logger log = LoggerFactory.getLogger(FeedbackImpl.class);
	
	public boolean postFeedback(FeedbackRequest feedbackRequest) throws Exception
	{
		log.info("Entering FeedbackImpl.postFeedback()");
		boolean flag = false;
		try
		{
			List<Feedback> feedbackList = feedbackRepository.fetchFeedbackById(feedbackRequest.getReqId(),feedbackRequest.getUserId());
			if(feedbackList.size()==0)
				throw new Exception("Enter valid details (ReqId, UserId)");
			else {
					String feedbackStatus = feedbackRequest.getStatus();
					feedbackRepository.updateFeedback(feedbackRequest.getReqId(),
							  feedbackRequest.getUserId(),
							  feedbackRequest.getStatus(),
							  feedbackRequest.getRemarks());
					
					if(feedbackStatus.equals("Reject"))
					{
						feedbackRepository.updateProfileScoreStatus(
								feedbackRequest.getReqId(),
								feedbackRequest.getUserId(),
							    "Rejected");
						int rows = feedbackRepository.getInprogressProfileScores(feedbackRequest.getUserId());
						if(rows==0)
							feedbackRepository.updateUserProfileStatus(feedbackRequest.getUserId(),"active");
					}	
					else
					{
						feedbackRepository.updateProfileScoreStatus(
								feedbackRequest.getReqId(),
								feedbackRequest.getUserId(),
							    "Selected");
						int rows = feedbackRepository.getInprogressProfileScores(feedbackRequest.getUserId());
						if(rows>0)
							feedbackRepository.updateProfileScoreStatusToUnavailable(feedbackRequest.getReqId(), feedbackRequest.getUserId(), "Unavailable");
						feedbackRepository.updateUserProfileStatus(feedbackRequest.getUserId(),"Selected");
					}
					flag=true;
				}
		} 
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		log.info("Exiting FeedbackImpl.postFeedback()");
		return flag;
	}
	

}
