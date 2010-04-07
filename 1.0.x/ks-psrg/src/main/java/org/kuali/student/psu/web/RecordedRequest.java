package org.kuali.student.psu.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * POJO that contains information about one HttpServletRequest.
 */
@SuppressWarnings("serial")
public class RecordedRequest implements Serializable {
	int sequence;

	/**
	 * URL for this request
	 */
	String path;

	/**
	 * Time of this request
	 */
	Date startTime;
	Date finishTime;

	/**
	 * Request parameters (if any)
	 */
	List<NameValuesBean> parameters;
	List<NameValuesBean> headers;
	List<NameValuesBean> attributes;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public List<NameValuesBean> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuesBean> parameters) {
		this.parameters = parameters;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public List<NameValuesBean> getHeaders() {
		return headers;
	}

	public void setHeaders(List<NameValuesBean> headers) {
		this.headers = headers;
	}

	public List<NameValuesBean> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<NameValuesBean> attributes) {
		this.attributes = attributes;
	}

}
