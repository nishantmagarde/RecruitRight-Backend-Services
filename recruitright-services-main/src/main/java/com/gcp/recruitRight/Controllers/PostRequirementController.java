package com.gcp.recruitRight.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.recruitRight.Impls.PostRequirementImpl;
import com.gcp.recruitRight.Requests.PostRequirementRequest;
import com.gcp.recruitRight.response.BaseResponse;


@RestController
@CrossOrigin(origins="http://localhost:3000")
public class PostRequirementController {
	
	@Autowired
	PostRequirementImpl postRequirementImpl;
	
	Logger log = LoggerFactory.getLogger(PostRequirementController.class);
	
	@PostMapping("/postRequirement")
	public ResponseEntity<BaseResponse> postRequirement(@RequestBody PostRequirementRequest postRequirementRequest){	
		log.info("Entering PoastRequirementController.postRequirement()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setReqId(postRequirementImpl.postRequirement(postRequirementRequest));
			baseResponse.setBooleanMsg(true);
		} catch (Exception e) {
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting PoastRequirementController.postRequirement()");
		return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
	}

}
