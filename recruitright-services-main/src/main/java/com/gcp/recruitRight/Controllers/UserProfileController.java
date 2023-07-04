package com.gcp.recruitRight.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.recruitRight.Impls.UserProfileImpl;
import com.gcp.recruitRight.Requests.UserProfileRequest;
import com.gcp.recruitRight.models.Feedback;
import com.gcp.recruitRight.models.Requirement;
import com.gcp.recruitRight.models.User;
import com.gcp.recruitRight.response.BaseResponse;
import com.gcp.recruitRight.response.UserProfileResponse;
import com.gcp.recruitRight.response.UserProfileStatus;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UserProfileController {
	
	Logger log = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	UserProfileImpl userProfileImpl;
	
	@GetMapping("/user/getDetails")
	public ResponseEntity<BaseResponse> getUserById()
	{
		log.info("Entering UserProfileController.getUserById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			User user = userProfileImpl.fetchUserDetails();
			baseResponse.setUser(user);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getUserById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@PostMapping("/user/editDetails")
	public ResponseEntity<BaseResponse> editUser(@RequestBody UserProfileRequest userProfileRequest)
	{
		log.info("Entering UserProfileController.editUser()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			boolean msg = userProfileImpl.editUserDetails(userProfileRequest);
			baseResponse.setBooleanMsg(msg);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.editUser()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/userList")
	public ResponseEntity<BaseResponse> getUsers()
	{
		log.info("Entering UserProfileController.getUsers()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			List<User> users = userProfileImpl.fetchAllUsers();
			baseResponse.setUsers(users);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getUsers()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/poc/requirementList")
	public ResponseEntity<BaseResponse> getRequirementById()
	{
		log.info("Entering UserProfileController.getRequirementById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			List<Requirement> requirements = userProfileImpl.fetchRequirementById();
			baseResponse.setRequirements(requirements);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getRequirementById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	
	@GetMapping("/requirementList")
	public ResponseEntity<BaseResponse> getRequirements()
	{
		log.info("Entering UserProfileController.getRequirements()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			List<Requirement> requirements = userProfileImpl.fetchAllRequirements();
			baseResponse.setRequirements(requirements);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getRequirements()");
		return ResponseEntity.ok(baseResponse);
	}
	
	
	@GetMapping("/candidate/profileStatus")
	public ResponseEntity<BaseResponse> getUserProfileStatus()
	{
		log.info("Entering UserProfileController.getUserProfileStatus()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			List<UserProfileStatus> userProfileStatusList = userProfileImpl.fetchUserProfileScores();
			baseResponse.setUserProfileStatusList(userProfileStatusList);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getUserProfileStatus()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/poc/requirement/eligibleProfiles")
	public ResponseEntity<BaseResponse> getInProgressProfileStatusForPOC()
	{
		log.info("Entering UserProfileController.getInProgressProfileStatusForPOC()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			List<Feedback> feedbackList = userProfileImpl.fetchInProgressProfilesForPOC();
			baseResponse.setFeedbackList(feedbackList);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getInProgressProfileStatusForPOC()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/rmg/uploadedProfiles")
	public ResponseEntity<BaseResponse> getUserProfilesForRMG()
	{
		log.info("Entering UserProfileController.getUserProfilesForRMG()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			List<UserProfileResponse> userProfileResponse = userProfileImpl.fetchUserProfilesForRMG();
			baseResponse.setUserProfileResponse(userProfileResponse);
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getUserProfilesForRMG()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/forgotPassword")
	public ResponseEntity<BaseResponse> sendVerificationCode(@RequestBody UserProfileRequest userProfileRequest)
	{
		log.info("Entering UserProfileController.sendVerificationCode()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setBooleanMsg(userProfileImpl.sendVerificationCode(userProfileRequest));
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.sendVerificationCode()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<BaseResponse> changePassword(@RequestBody UserProfileRequest userProfileRequest)
	{
		log.info("Entering UserProfileController.resetPassword()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setBooleanMsg(userProfileImpl.changePassword(userProfileRequest));
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.resetPassword()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@PostMapping("/poc/closeRequirement")
	public ResponseEntity<BaseResponse> closeRequirement(@RequestBody UserProfileRequest userProfileRequest)
	{
		log.info("Entering UserProfileController.closeRequirement()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setBooleanMsg(userProfileImpl.closeRequirement(userProfileRequest));
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.resetPassword()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/getActiveRequirements")
	public ResponseEntity<BaseResponse> getAllActiveRequirements()
	{
		log.info("Entering UserProfileController.getAllActiveRequirements()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setRequirements(userProfileImpl.fetchAllActiveRequirements());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllActiveRequirements()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/getClosedRequirements")
	public ResponseEntity<BaseResponse> getAllClosedRequirements()
	{
		log.info("Entering UserProfileController.getAllClosedRequirements()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setRequirements(userProfileImpl.fetchAllClosedRequirements());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllClosedRequirements()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/getInProgressRequirements")
	public ResponseEntity<BaseResponse> getAllInProgressRequirements()
	{
		log.info("Entering UserProfileController.getAllInProgressRequirements()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setRequirements(userProfileImpl.fetchAllInProgressRequirements());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllInProgressRequirements()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/poc/getActiveRequirements")
	public ResponseEntity<BaseResponse> getAllActiveRequirementsById()
	{
		log.info("Entering UserProfileController.getAllActiveRequirementsById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setRequirements(userProfileImpl.fetchAllActiveRequirementsById());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllActiveRequirementsById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/poc/getClosedRequirements")
	public ResponseEntity<BaseResponse> getAllClosedRequirementsById()
	{
		log.info("Entering UserProfileController.getAllClosedRequirementsById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setRequirements(userProfileImpl.fetchAllClosedRequirementsById());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllClosedRequirementsById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/poc/getInProgressRequirements")
	public ResponseEntity<BaseResponse> getAllInProgressRequirementsById()
	{
		log.info("Entering UserProfileController.getAllInProgressRequirementsById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setRequirements(userProfileImpl.fetchAllInProgressRequirementsById());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllInProgressRequirementsById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/getActiveUserProfiles")
	public ResponseEntity<BaseResponse> getAllActiveUserProfiles()
	{
		log.info("Entering UserProfileController.getAllActiveUserProfiles()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setUserProfiles(userProfileImpl.fetchAllActiveUserProfiles());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllActiveUserProfiles()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/getSelectedUserProfiles")
	public ResponseEntity<BaseResponse> getAllSelectedUserProfiles()
	{
		log.info("Entering UserProfileController.getAllSelectedUserProfiles()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setUserProfiles(userProfileImpl.fetchAllSelectedUserProfiles());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllSelectedUserProfiles()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/getInProgressUserProfiles")
	public ResponseEntity<BaseResponse> getAllInProgressUserProfiles()
	{
		log.info("Entering UserProfileController.getAllInProgressUserProfiles()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setUserProfiles(userProfileImpl.fetchAllInProgressUserProfiles());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllInProgressUserProfiles()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/rmg/getActiveUserProfiles")
	public ResponseEntity<BaseResponse> getAllActiveUserProfilesById()
	{
		log.info("Entering UserProfileController.getAllActiveUserProfilesById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setUserProfiles(userProfileImpl.fetchAllActiveUserProfilesById());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllActiveUserProfilesById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/rmg/getSelectedUserProfiles")
	public ResponseEntity<BaseResponse> getAllSelectedUserProfilesById()
	{
		log.info("Entering UserProfileController.getAllSelectedUserProfilesById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setUserProfiles(userProfileImpl.fetchAllSelectedUserProfilesById());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllSelectedUserProfilesById()");
		return ResponseEntity.ok(baseResponse);
	}
	
	@GetMapping("/rmg/getInProgressUserProfiles")
	public ResponseEntity<BaseResponse> getAllInProgressUserProfilesById()
	{
		log.info("Entering UserProfileController.getAllInProgressUserProfilesById()");
		BaseResponse baseResponse = new BaseResponse();
		try {
			baseResponse.setUserProfiles(userProfileImpl.fetchAllInProgressUserProfilesById());
			baseResponse.setBooleanMsg(true);
		}
		catch(Exception e)
		{
			baseResponse.setExceptionMessage(e.getMessage());
			baseResponse.setBooleanMsg(false);
		}
		log.info("Exiting UserProfileController.getAllInProgressUserProfilesById()");
		return ResponseEntity.ok(baseResponse);
	}

}
