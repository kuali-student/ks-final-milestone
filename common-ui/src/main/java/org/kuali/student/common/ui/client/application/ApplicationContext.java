package org.kuali.student.common.ui.client.application;

import java.util.ArrayList;
import java.util.List;

// TODO find out what we'll really be storing here, and where to get it
// for now this is just a mockup
public class ApplicationContext {
	private boolean loggedIn = true;
	private String userId = "testuser";
	private List<String> roles = new ArrayList<String>();
	
	public ApplicationContext() {
		roles.add("role1");
		roles.add("role2");
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String getUserId() {
		return userId;
	}

	public List<String> getRoles() {
		return roles;
	}
}
