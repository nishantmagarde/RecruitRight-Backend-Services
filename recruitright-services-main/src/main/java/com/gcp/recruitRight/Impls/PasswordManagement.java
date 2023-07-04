package com.gcp.recruitRight.Impls;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PasswordManagement {
	
	private static HashMap<String,String> otps = new HashMap<String,String>();
	
	static Logger log = LoggerFactory.getLogger(PasswordManagement.class);
	
	public static void addOtp(String userId,String otp)
	{
		log.info("Entering PasswordManagement.addOtp()");
		otps.put(userId,otp);
		log.info("Exiting PasswordManagement.addOtp()");
	}
	
	public static String getOtp(String userId)
	{
		log.info("Entering PasswordManagement.getOtp()");
		String otp = otps.get(userId);
		log.info("Exiting PasswordManagement.getOtp()");
		return otp;
	}
}
