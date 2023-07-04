package com.gcp.recruitRight.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.recruitRight.Impls.LoginServiceImpl;
import com.gcp.recruitRight.Requests.LoginServiceRequest;
import com.gcp.recruitRight.response.BaseResponse;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class LoginServiceController {
	
	@Autowired
	private LoginServiceImpl loginServiceImpl; 
	
	Logger log = LoggerFactory.getLogger(LoginServiceController.class);

	
	@PostMapping("/signup")
	public ResponseEntity<BaseResponse> signup(@RequestBody LoginServiceRequest loginServiceRequest) {
		log.info("Entering LoginServiceController.signup()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setBooleanMsg(loginServiceImpl.signup(loginServiceRequest));
		} catch(Exception e) {
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exitring LoginServiceController.signup()");
		return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
	}
	
	@PostMapping("/login")
	public ResponseEntity<BaseResponse> login(@RequestBody LoginServiceRequest loginServiceRequest) {
		log.info("Entering LoginServiceController.login()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			String jwtToken = loginServiceImpl.login(loginServiceRequest);
			if(jwtToken!=null) {
				baseResponse.setBooleanMsg(true);
				baseResponse.setJwtToken(jwtToken);
				baseResponse.setUser(loginServiceImpl.fetchUserById(loginServiceRequest));
			}
			else
				baseResponse.setBooleanMsg(false); 
		}
		catch(Exception e) {
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false); 
		}
		log.info("Exiting LoginServiceController.login()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@PostMapping("/user/logout")
	public ResponseEntity<BaseResponse> logout(){
		log.info("Entering LoginServiceController.logout()");
		BaseResponse baseResponse = new BaseResponse();
		if(loginServiceImpl.logout())
			baseResponse.setBooleanMsg(true);
		else
			baseResponse.setBooleanMsg(false);
		log.info("Exiting LoginServiceController.logout()");
		return ResponseEntity.ok(baseResponse);
	}
	
}
