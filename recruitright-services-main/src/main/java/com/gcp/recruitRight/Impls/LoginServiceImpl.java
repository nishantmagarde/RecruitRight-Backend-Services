package com.gcp.recruitRight.Impls;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.recruitRight.Repository.LoginServiceRepository;
import com.gcp.recruitRight.Requests.LoginServiceRequest;
import com.gcp.recruitRight.Util.JwtUtil;
import com.gcp.recruitRight.models.User;

@Service
public class LoginServiceImpl {
	
	@Autowired
	private LoginServiceRepository loginServiceRepository;

	@Autowired
	private JwtUtil jwtUtil;
	
	Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	public boolean signup(LoginServiceRequest loginServiceRequest) throws Exception
	{
		log.info("Entering LoginServiceImpl.signup()");
		List<User> users = loginServiceRepository.findUsers();
		if(users.size()!=0) {
			for(User u : users)
			{
				if(u.getUserId().equals(loginServiceRequest.getUserId()))
					throw new Exception("Email already Registered....Please SignIn to continue");
			}	
		}
		int status = loginServiceRepository.insertIntoUser(loginServiceRequest);
		if(status == 1)
		{
			log.info("Exiting LoginServiceImpl.signup()");
 			return true;
		}
		log.info("Exiting LoginServiceImpl.signup()");
 		return false;
	}

	
	public String login(LoginServiceRequest loginServiceRequest) throws Exception
	{
		log.info("Entering LoginServiceImpl.login()");
		if(loginServiceRepository.verifyUser(loginServiceRequest))
		{
			User userDetails = loginServiceRepository.fetchUserById(loginServiceRequest.getUserId());
			return jwtUtil.generateToken(userDetails);
			
		}
		throw new Exception("Can't login");
	}
	
	public User fetchUserById(LoginServiceRequest loginServiceRequest) {
		log.info("Entering LoginServiceImpl.fetchUserById()");
		User user = loginServiceRepository.fetchUserById(loginServiceRequest.getUserId());
		log.info("Exiting LoginServiceImpl.fetchUserById()");
		return user;
		
	}
	
	public boolean logout()
	{
		return true;
	}
	
}
