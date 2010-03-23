package org.kuali.student.psrg.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tracks information about an HttpSession
 * 
 * @author Jeff Caddel
 */
@SuppressWarnings("serial")
public class RecordedSession implements Serializable {

	List<RecordedRequest> recordedRequests = Collections.synchronizedList(new ArrayList<RecordedRequest>());
	String remoteAddress;
	String browser;
	String httpSessionId;

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

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	public void setHttpSessionId(String httpSessionId) {
		this.httpSessionId = httpSessionId;
	}
}
