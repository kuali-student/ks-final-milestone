package org.kuali.student.poc.client.login;

import java.io.Serializable;

public class LoginCredentials implements Serializable {
	private static final long serialVersionUID = 1L;
    private String userId = null;
	private String password = null;
	private String locale = null;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
