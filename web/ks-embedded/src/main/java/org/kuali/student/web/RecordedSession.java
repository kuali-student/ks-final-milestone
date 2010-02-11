package org.kuali.student.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class RecordedSession {
	private static final long serialVersionUID = 1L;

	List<RecordedRequest> recordedRequests = Collections.synchronizedList(new ArrayList<RecordedRequest>());
	String remoteAddress;
	String remoteHost;
	String browser;
	Date destroyed;
	String login;

	public List<RecordedRequest> getRecordedRequests() {
		return recordedRequests;
	}

	public void setRecordedRequests(List<RecordedRequest> recordedRequests) {
		this.recordedRequests = recordedRequests;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Date getDestroyed() {
		return destroyed;
	}

	public void setDestroyed(Date destroyed) {
		this.destroyed = destroyed;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
