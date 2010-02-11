package org.kuali.student.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Tracks information about an HttpServletRequest.
 * 
 * @author Jeff Caddel
 * 
 * @since Apr 26, 2005 3:34:31 PM
 * @version $Revision: 19450 $ $Date: 2009-06-05 08:50:09 -0700 (Fri, 05 Jun
 *          2009) $
 */
public class RecordedRequest implements Serializable {
	private static final long serialVersionUID = 1L;

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
	List<ParameterBean> parameters;

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

	public List<ParameterBean> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterBean> parameters) {
		this.parameters = parameters;
	}

}
