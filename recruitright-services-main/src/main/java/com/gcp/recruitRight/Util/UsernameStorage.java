package com.gcp.recruitRight.Util;

public class UsernameStorage {
	
	private static String userId;

	public static String getUserId() {
		return userId;
	}

	public static void setUserId(String userId) {
		UsernameStorage.userId = userId;
	}
	
	
}
