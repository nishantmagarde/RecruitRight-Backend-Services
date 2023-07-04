package com.gcp.recruitRight.Impls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.recruitRight.Repository.PostRequirementRepository;
import com.gcp.recruitRight.Requests.PostRequirementRequest;

@Service
public class PostRequirementImpl {
	
	@Autowired
	PostRequirementRepository postRequirementRepository;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	Logger log = LoggerFactory.getLogger(PostRequirementImpl.class);
	
	public int postRequirement(PostRequirementRequest postRequirementRequest) throws Exception{
		try {
			log.info("Entering PoastRequirementImpl.postRequirement()");
			postRequirementRepository.postRequirement(postRequirementRequest);
			int reqId = postRequirementRepository.getReqId();
			log.info("Exiting PostRequirementImpl.postRequirement()");
			return reqId;
		} 
		catch(Exception e) {
			log.error("Exception occured in postRequirementImpl.postRequirement()");
			throw new Exception("Error while posting Requirement");
		}
	}

}
