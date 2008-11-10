package edu.umd.ks.poc.lum.web.core.client;


public class Authorization {
	public enum User { admin, user };

	private static String user = User.admin.toString();
	
	
	public static void setUser(String setUser){
		user = setUser;
	}
	
	public static String getUser(){
		return user;
	}
	
	public static boolean isAuthorized(String requiredUser){
		if(User.admin.toString().equals(getUser())||getUser().equals(requiredUser)){
			return true;
		}
		return false;
	}
	
}
