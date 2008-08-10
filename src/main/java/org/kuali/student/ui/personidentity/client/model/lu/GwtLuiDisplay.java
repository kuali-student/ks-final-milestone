/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.io.Serializable;


/**
 * @author Garey
 *
 */
public class GwtLuiDisplay implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 988200711090538320L;

	private String luiId;
	
	private String luiCode;
	
	private String luTypeKey;
	
	private GwtCluDisplay cluDisplay;
	
	private GwtAtpDisplay atpDisplay;

	/**
	 * @return the luiId
	 */
	public String getLuiId() {
		return luiId;
	}

	/**
	 * @param luiId the luiId to set
	 */
	public void setLuiId(String luiId) {
		this.luiId = luiId;
	}

	/**
	 * @return the luiCode
	 */
	public String getLuiCode() {
		return luiCode;
	}

	/**
	 * @param luiCode the luiCode to set
	 */
	public void setLuiCode(String luiCode) {
		this.luiCode = luiCode;
	}

	/**
	 * @return the luTypeKey
	 */
	public String getLuTypeKey() {
		return luTypeKey;
	}

	/**
	 * @param luTypeKey the luTypeKey to set
	 */
	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}

	/**
	 * @return the cluDisplay
	 */
	public GwtCluDisplay getCluDisplay() {
		return cluDisplay;
	}

	/**
	 * @param cluDisplay the cluDisplay to set
	 */
	public void setCluDisplay(GwtCluDisplay cluDisplay) {
		this.cluDisplay = cluDisplay;
	}

	/**
	 * @return the atpDisplay
	 */
	public GwtAtpDisplay getAtpDisplay() {
		return atpDisplay;
	}

	/**
	 * @param atpDisplay the atpDisplay to set
	 */
	public void setAtpDisplay(GwtAtpDisplay atpDisplay) {
		this.atpDisplay = atpDisplay;
	}

	
	

}
