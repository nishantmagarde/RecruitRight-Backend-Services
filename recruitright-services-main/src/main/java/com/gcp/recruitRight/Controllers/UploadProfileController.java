package com.gcp.recruitRight.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gcp.recruitRight.Impls.UploadProfileImpl;
import com.gcp.recruitRight.response.BaseResponse;


@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UploadProfileController {

	@Autowired
	UploadProfileImpl uploadProfileImpl;
	
	Logger log = LoggerFactory.getLogger(UploadProfileController.class);
	
	@PostMapping("/uploadProfile")
	public ResponseEntity<BaseResponse> uploadProfile(@RequestParam("resumeList") MultipartFile[] resumeList){
		log.info("Entering UploadProfileController.uploadProfile()");
		BaseResponse baseResponse = new BaseResponse();
		
		try {
			List<String> incorrectProfiles = uploadProfileImpl.uploadProfile(resumeList);
			if(incorrectProfiles.size()==0)
				baseResponse.setBooleanMsg(true);
			else
			{
				baseResponse.setBooleanMsg(true);
				baseResponse.setIncorrectProfiles(incorrectProfiles);
			}
		} catch (Exception e) {
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UploadProfileControlelr.uploadProfile()");
		return ResponseEntity.ok(baseResponse);
	}
}
