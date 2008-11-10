package edu.umd.ks.poc.lum.web.clu.client;

import java.io.Serializable;

public class CluWorkflowComposite implements Serializable{

	private static final long serialVersionUID = 1L;
	private String cluId;
	private String routeDocId;
	/**
	 * @return the cluId
	 */
	public String getCluId() {
		return cluId;
	}
	/**
	 * @param cluId the cluId to set
	 */
	public void setCluId(String cluId) {
		this.cluId = cluId;
	}
	/**
	 * @return the routeDocId
	 */
	public String getRouteDocId() {
		return routeDocId;
	}
	/**
	 * @param routeDocId the routeDocId to set
	 */
	public void setRouteDocId(String routeDocId) {
		this.routeDocId = routeDocId;
	}
}
