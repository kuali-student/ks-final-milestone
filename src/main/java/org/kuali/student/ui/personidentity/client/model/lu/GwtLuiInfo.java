/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * @author Garey
 *
 */
public class GwtLuiInfo implements Serializable, ModelObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6311238436240230892L;

	private String luiId;
	
	private String luiCode;
	
	private String luTypeKey;
	
	private GwtCluDisplay cluDisplay;
	
	private GwtAtpDisplay atpDisplay;
	
	private GwtCluInfo cluInfo;
	
	private int maxSeats;
	
	
	private Map<String, String> attributes;
	
	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		if(attributes==null){
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the luiId
	 */
	public String getLuiId() {
		return luiId;
	}

	/**
	 * @param luiId
	 *            the luiId to set
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
	 * @param luiCode
	 *            the luiCode to set
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
	 * @param luTypeKey
	 *            the luTypeKey to set
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
	 * @param cluDisplay
	 *            the cluDisplay to set
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
	 * @param atpDisplay
	 *            the atpDisplay to set
	 */
	public void setAtpDisplay(GwtAtpDisplay atpDisplay) {
		this.atpDisplay = atpDisplay;
	}

	/**
	 * @return the maxSeats
	 */
	public int getMaxSeats() {
		return maxSeats;
	}

	/**
	 * @param maxSeats
	 *            the maxSeats to set
	 */
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atpDisplay == null) ? 0 : atpDisplay.hashCode());
		result = prime * result
				+ ((cluDisplay == null) ? 0 : cluDisplay.hashCode());
		result = prime * result
				+ ((luTypeKey == null) ? 0 : luTypeKey.hashCode());
		result = prime * result + ((luiCode == null) ? 0 : luiCode.hashCode());
		result = prime * result + ((luiId == null) ? 0 : luiId.hashCode());
		result = prime * result + maxSeats;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GwtLuiInfo other = (GwtLuiInfo) obj;
		if (atpDisplay == null) {
			if (other.atpDisplay != null)
				return false;
		} else if (!atpDisplay.equals(other.atpDisplay))
			return false;
		if (cluDisplay == null) {
			if (other.cluDisplay != null)
				return false;
		} else if (!cluDisplay.equals(other.cluDisplay))
			return false;
		if (luTypeKey == null) {
			if (other.luTypeKey != null)
				return false;
		} else if (!luTypeKey.equals(other.luTypeKey))
			return false;
		if (luiCode == null) {
			if (other.luiCode != null)
				return false;
		} else if (!luiCode.equals(other.luiCode))
			return false;
		if (luiId == null) {
			if (other.luiId != null)
				return false;
		} else if (!luiId.equals(other.luiId))
			return false;
		if (maxSeats != other.maxSeats)
			return false;
		return true;
	}

	/**
	 * @return the cluInfo
	 */
	public GwtCluInfo getCluInfo() {
		return cluInfo;
	}

	/**
	 * @param cluInfo the cluInfo to set
	 */
	public void setCluInfo(GwtCluInfo cluInfo) {
		this.cluInfo = cluInfo;
	}

    
    public String getUniqueId() {     
        return this.luiId;
    }
}
