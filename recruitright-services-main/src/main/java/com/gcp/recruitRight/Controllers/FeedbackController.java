package com.gcp.recruitRight.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.recruitRight.Impls.FeedbackImpl;
import com.gcp.recruitRight.Requests.FeedbackRequest;
import com.gcp.recruitRight.response.BaseResponse;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class FeedbackController {
	
	@Autowired
	private FeedbackImpl feedbackImpl;
	
	Logger log = LoggerFactory.getLogger(FeedbackImpl.class);
	
	@PostMapping("/submitFeedback")
	public ResponseEntity<BaseResponse> submitFeedback(@RequestBody FeedbackRequest feedbackRequest)
	{
		log.info("Entering FeedbackController.submitFeedback()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setBooleanMsg(feedbackImpl.postFeedback(feedbackRequest));
		}catch(Exception e) {
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting FeedbackController.submitFeedback()");
		return ResponseEntity.ok(baseResponse);
	}
}
