/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.io.Serializable;


/**
 * @author Garey
 *
 */
public class GwtAtpDisplay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6149921214737644137L;
	private String atpId;	
	private String atpName;
	
	/**
	 * @return the atpId
	 */
	public String getAtpId() {
		return atpId;
	}
	/**
	 * @param atpId the atpId to set
	 */
	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}
	/**
	 * @return the atpName
	 */
	public String getAtpName() {
		return atpName;
	}
	/**
	 * @param atpName the atpName to set
	 */
	public void setAtpName(String atpName) {
		this.atpName = atpName;
	}
}
