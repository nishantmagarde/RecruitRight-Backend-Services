package com.gcp.recruitRight.Impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcp.recruitRight.Repository.LoginServiceRepository;
import com.gcp.recruitRight.Repository.UserProfileRepository;
import com.gcp.recruitRight.Requests.UserProfileRequest;
import com.gcp.recruitRight.Util.UsernameStorage;
import com.gcp.recruitRight.models.Feedback;
import com.gcp.recruitRight.models.ProfileScore;
import com.gcp.recruitRight.models.Requirement;
import com.gcp.recruitRight.models.User;
import com.gcp.recruitRight.models.UserProfile;
import com.gcp.recruitRight.response.UserProfileData;
import com.gcp.recruitRight.response.UserProfileResponse;
import com.gcp.recruitRight.response.UserProfileStatus;

@Service
public class UserProfileImpl {
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	LoginServiceRepository loginServiceRepository;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	Logger log = LoggerFactory.getLogger(UserProfileImpl.class);
	
	public User fetchUserDetails() throws Exception
	{
		log.info("Entering UserProfileImpl.fetchUserDetails()");
		try {
			User u= userProfileRepository.fetchUser(UsernameStorage.getUserId());
			log.info("Exiting UserProfileImpl.fetchUserDetails()");
			return u;
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public boolean editUserDetails(UserProfileRequest userProfileRequest) throws Exception
	{
		log.info("Entering UserProfileImpl.editUserDetails()");
		try {
			int status = userProfileRepository.updateUser(
												UsernameStorage.getUserId(),
												userProfileRequest.getFirstName(),
												userProfileRequest.getLastName(),
												userProfileRequest.getContact()
												);
			log.info("Exiting UserProfileImpl.editUserDetails()");
			if(status > 0)
				return true;
			return false;
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<User> fetchAllUsers() throws Exception
	{	
		log.info("Entering UserProfileImpl.fetchAllUsers()");
		try
		{
			List<User> users = userProfileRepository.fetchAllUsers();
			log.info("Exiting UserProfileImpl.ftechAllUsers()");
			return users;
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<Requirement> fetchRequirementById() throws Exception
	{
		log.info("Entering UserProfileImpl.fetchRequirementById()");
		try
		{
			String userId = UsernameStorage.getUserId();
			List<Requirement> requirements = userProfileRepository.fetchRequirementById(userId);
				log.info("Exiting UserProfileImpl.fetchRequirementById()");
				return requirements;
		} 
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
		
		
	public List<Requirement> fetchAllRequirements() throws Exception
	{
		log.info("Entering UserProfileImpl.fetchAllrequirements()");
		try
		{
			List<Requirement> requirements = userProfileRepository.fetchAllRequirements();
				log.info("Exiting UserProfileImpl.fetchAllRequirements()");
				return requirements;
		} 
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<UserProfileStatus> fetchUserProfileScores() throws Exception
	{
		log.info("Entering UserProfileImpl.fetchUserProfileScores()");
		ArrayList<UserProfileStatus> userProfileStatusList = new ArrayList<UserProfileStatus>();
		try
		{
			String userId = UsernameStorage.getUserId();
				List<ProfileScore> profileScores = userProfileRepository.fetchProfileScoresById(userId);
				for(ProfileScore profileScore : profileScores)
				{
					UserProfileStatus ups = new UserProfileStatus();
					ups.setProfileScore(profileScore.getProfileScore());
					ups.setStatus(profileScore.getStatus());
					List<Requirement> requirements = userProfileRepository.fetchRequirementByReqId(profileScore.getReqId());
					for(Requirement req: requirements)
					{
						ups.setIsu(req.getIsu());
						ups.setSubIsu(req.getSubIsu());
						ups.setProjectName(req.getProjectName());
						ups.setJobRole(req.getJobRole());
						ups.setJobRoleType(req.getJobRoleType());
					}
					userProfileStatusList.add(ups);
				}
				log.info("Exiting UserProfileImpl.fetchUserProfileScores()");
				return userProfileStatusList;
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	public List<Feedback> fetchInProgressProfilesForPOC() throws Exception
	{
		log.info("Entering UserProfileImpl.fetchInProgressProfilesForPOC()");
		String userId = UsernameStorage.getUserId();
		try {
			List<Feedback> feedbackList = userProfileRepository.fetchInProgressProfilesForPOC(userId);
			log.info("Exiting UserProfileImpl.fetchInProgressProfilesForPOC()");
			return feedbackList;
		} 
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	public List<UserProfileResponse> fetchUserProfilesForRMG() throws Exception
	{
		log.info("Entering UserProfileImpl.fetchUserProfilesForRMG()");
		
		ArrayList<UserProfileResponse> userProfileResponse = new ArrayList<UserProfileResponse>();
		
		String uploader = UsernameStorage.getUserId();
		try {
			List<UserProfile> userProfiles =  userProfileRepository.fetchUserProfilesForRMG(uploader);
			
			for(UserProfile userProfile:userProfiles)
			{
				UserProfileResponse upr = new UserProfileResponse();
				
				upr.setUserId(userProfile.getUserId());
				upr.setName(userProfile.getName());
				upr.setContact(userProfile.getContact());
				upr.setStatus(userProfile.getStatus());
				if(userProfile.getStatus().equals("Selected"))
				{
					Requirement req = userProfileRepository.fetchRequirementForSelectedUser(userProfile.getUserId());
					upr.setReqId(req.getReqId());
					upr.setProjectName(req.getProjectName());
				}
				else
				{
					upr.setProjectName("Unallocated");
				}
				userProfileResponse.add(upr);
			}
			log.info("Exiting UserProfileImpl.fetchUserProfilesForRMG()");
			return userProfileResponse;
		} 
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void sendEmail(String name, String toMailId, String OTP)
	{
		log.info("Entering UserProfileImpl.sendEmail()");
		try {
        	
        	String User="recruitright.team@gmail.com";
            String Pass="recruitright";
            String Subject="Verification Code to Reset Password";
            String mssg = "Hello "+name+",\n\nVerification Code to Reset your Password is: "+OTP+"\n\nThanks,\nRecruitRight-Team";     
            
            Properties props = new Properties();
    		props.put("mail.smtp.host", "smtp.gmail.com");
    		props.put("mail.smtp.port", "587");		
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.starttls.enable", "true");
    		Session session = Session.getInstance
    				(
    					props,new javax.mail.Authenticator()
    					{
    						protected PasswordAuthentication getPasswordAuthentication() 
    						{
    						return new PasswordAuthentication(User,Pass);
    						}
    					}
    				); 
    			MimeMessage message = new MimeMessage(session);
    			message.setFrom(new InternetAddress(User));
    			message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMailId));
    			message.setSubject(Subject);
    			MimeBodyPart messagePart = new MimeBodyPart();
    			messagePart.setText(mssg);
    			Multipart multipart = new MimeMultipart();
    			multipart.addBodyPart(messagePart);
    			message.setContent(multipart);
    			Transport.send(message); 
    			log.info("Exiting UserProfileImpl.sendEmail()");
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
	public boolean sendVerificationCode(UserProfileRequest userProfileRequest) throws Exception
	{
		log.info("Entering UserProfileImpl.sendVerificationCode()");
		String userId = userProfileRequest.getUserId();
		User user = userProfileRepository.fetchUser(userId);
		try 
		{	
			if(user == null)
				throw new Exception("Enter valid email");
			else
			{
				Random random = new Random();
				String OTP = String.format("%06d", random.nextInt(1000000));
				PasswordManagement.addOtp(userId, OTP);
				sendEmail(user.getFirstName(),userId, OTP);
				log.info("Exiting UserProfileImpl.sendVerificationCode()");
			}
			
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return true;
	}
	
	public boolean changePassword(UserProfileRequest userProfileRequest) throws Exception
	{
		log.info("Entering UserProfileImpl.changePassword()");
		String userId = userProfileRequest.getUserId();
		User user = userProfileRepository.fetchUser(userId);
		int flag=0;
		try 
		{	
			if(user == null)
				throw new Exception("Enter valid email");
			else
			{
				if(userProfileRequest.getVerificationCode().equals(PasswordManagement.getOtp(userId)))
					flag = userProfileRepository.updateUserPassword(userId,userProfileRequest.getPassword());
				else
					throw new Exception("Enter valid Verification code");
			}
			if(flag>0)
			{
				log.info("Exiting UserProfileImpl.changePassword()---true");
				return true;
			}
			
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		log.info("Exiting UserProfileImpl.changePassword()---false");
		return false;
	}	
	
	
	public boolean closeRequirement(UserProfileRequest userProfileRequest) throws Exception
	{
		try {
		int result = userProfileRepository.updateRequirementStatus(userProfileRequest.getReqId(),"Closed");
		if(result>0)
			return true;
		return false;
		}
		catch(Exception e) {
			throw new Exception("Error while updating status");
		}
	}
	
	public List<Requirement> fetchAllActiveRequirements() throws Exception
	{
		try {
		List<Requirement> activeRequirements = userProfileRepository.fetchAllActiveRequirements();
		return activeRequirements;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<Requirement> fetchAllClosedRequirements() throws Exception
	{
		try {
		List<Requirement> closedRequirements = userProfileRepository.fetchAllClosedRequirements();
		return closedRequirements;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<Requirement> fetchAllInProgressRequirements() throws Exception
	{
		try {
		List<Requirement> inProgressRequirements = userProfileRepository.fetchAllInProgressRequirements();
		return inProgressRequirements;
		}
		catch(Exception e) {
			throw new Exception("cannot fetch data");
		}
	}
	
	public List<Requirement> fetchAllActiveRequirementsById() throws Exception
	{
		try {
			String userId = UsernameStorage.getUserId();
			List<Requirement> activeRequirements = userProfileRepository.fetchAllActiveRequirementsById(userId);
			return activeRequirements;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<Requirement> fetchAllClosedRequirementsById() throws Exception
	{
		try {
			String userId = UsernameStorage.getUserId();
			List<Requirement> closedRequirements = userProfileRepository.fetchAllClosedRequirementsById(userId);
			return closedRequirements;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<Requirement> fetchAllInProgressRequirementsById() throws Exception
	{
		try {
			String userId = UsernameStorage.getUserId();
			List<Requirement> inProgressRequirements = userProfileRepository.fetchAllInProgressRequirementsById(userId);
			return inProgressRequirements;
		}
		catch(Exception e) {
			throw new Exception("cannot fetch data");
		}
	}
	
	public List<UserProfileData> fetchAllActiveUserProfiles() throws Exception
	{
		List<UserProfileData> userProfiles = new ArrayList<UserProfileData>();
		try {
		List<UserProfile> activeUserProfiles = userProfileRepository.fetchAllActiveUserProfiles();
		for(UserProfile active:activeUserProfiles)
		{
			UserProfileData upd = new UserProfileData();
			upd.setUserId(active.getUserId());
			upd.setName(active.getName());
			upd.setContact(active.getContact());
			upd.setUploader(active.getUploader());
			upd.setStatus(active.getStatus());
			userProfiles.add(upd);
		}
		return userProfiles;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<UserProfileData> fetchAllSelectedUserProfiles() throws Exception
	{
		List<UserProfileData> userProfiles = new ArrayList<UserProfileData>();
		try {
		List<UserProfile> closedUserProfiles = userProfileRepository.fetchAllSelectedUserProfiles();
		for(UserProfile closed:closedUserProfiles)
		{
			UserProfileData upd = new UserProfileData();
			upd.setUserId(closed.getUserId());
			upd.setName(closed.getName());
			upd.setContact(closed.getContact());
			upd.setUploader(closed.getUploader());
			upd.setStatus(closed.getStatus());
			userProfiles.add(upd);
		}
		return userProfiles;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<UserProfileData> fetchAllInProgressUserProfiles() throws Exception
	{
		List<UserProfileData> userProfiles = new ArrayList<UserProfileData>();
		try {
		List<UserProfile> inProgressUserProfiles = userProfileRepository.fetchAllInProgressUserProfiles();
		for(UserProfile inProgress:inProgressUserProfiles)
		{
			UserProfileData upd = new UserProfileData();
			upd.setUserId(inProgress.getUserId());
			upd.setName(inProgress.getName());
			upd.setContact(inProgress.getContact());
			upd.setUploader(inProgress.getUploader());
			upd.setStatus(inProgress.getStatus());
			userProfiles.add(upd);
		}
		return userProfiles;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<UserProfileData> fetchAllActiveUserProfilesById() throws Exception
	{
		List<UserProfileData> userProfiles = new ArrayList<UserProfileData>();
		try {
			String uploader = UsernameStorage.getUserId();
			List<UserProfile> activeUserProfiles = userProfileRepository.fetchAllActiveUserProfilesById(uploader);
			for(UserProfile active:activeUserProfiles)
			{
				UserProfileData upd = new UserProfileData();
				upd.setUserId(active.getUserId());
				upd.setName(active.getName());
				upd.setContact(active.getContact());
				upd.setUploader(active.getUploader());
				upd.setStatus(active.getStatus());
				userProfiles.add(upd);
			}
			return userProfiles;
			}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<UserProfileData> fetchAllSelectedUserProfilesById() throws Exception
	{
		List<UserProfileData> userProfiles = new ArrayList<UserProfileData>();
		try {
			String uploader = UsernameStorage.getUserId();
			List<UserProfile> closedUserProfiles = userProfileRepository.fetchAllSelectedUserProfilesById(uploader);
			for(UserProfile closed:closedUserProfiles)
			{
				UserProfileData upd = new UserProfileData();
				upd.setUserId(closed.getUserId());
				upd.setName(closed.getName());
				upd.setContact(closed.getContact());
				upd.setUploader(closed.getUploader());
				upd.setStatus(closed.getStatus());
				userProfiles.add(upd);
			}
			return userProfiles;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}
	
	public List<UserProfileData> fetchAllInProgressUserProfilesById() throws Exception
	{
		List<UserProfileData> userProfiles = new ArrayList<UserProfileData>();
		try {
			String uploader = UsernameStorage.getUserId();
			List<UserProfile> inProgressUserProfiles = userProfileRepository.fetchAllInProgressUserProfilesById(uploader);
			for(UserProfile inProgress:inProgressUserProfiles)
			{
				UserProfileData upd = new UserProfileData();
				upd.setUserId(inProgress.getUserId());
				upd.setName(inProgress.getName());
				upd.setContact(inProgress.getContact());
				upd.setUploader(inProgress.getUploader());
				upd.setStatus(inProgress.getStatus());
				userProfiles.add(upd);
			}
			return userProfiles;
		}
		catch(Exception e) {
			throw new Exception("Cannot fetch data");
		}
	}

}
