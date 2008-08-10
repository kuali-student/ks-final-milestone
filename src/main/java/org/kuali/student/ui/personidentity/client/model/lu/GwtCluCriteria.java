/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.io.Serializable;
import java.util.Map;



/**
 * @author Garey
 *
 */
public class GwtCluCriteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2954113024982481920L;
	private String luTypeKey;
	private Map<String, String> searchKeyValue;

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
	 * @return the searchKeyValue
	 */
	public Map<String, String> getSearchKeyValue() {
		return searchKeyValue;
	}

	/**
	 * @param searchKeyValue
	 *            the searchKeyValue to set
	 */
	public void setSearchKeyValue(Map<String, String> searchKeyValue) {
		this.searchKeyValue = searchKeyValue;
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
				+ ((luTypeKey == null) ? 0 : luTypeKey.hashCode());
		result = prime * result
				+ ((searchKeyValue == null) ? 0 : searchKeyValue.hashCode());
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
		final GwtCluCriteria other = (GwtCluCriteria) obj;
		if (luTypeKey == null) {
			if (other.getLuTypeKey() != null)
				return false;
		} else if (!luTypeKey.equals(other.getLuTypeKey()))
			return false;
		if (searchKeyValue == null) {
			if (other.getSearchKeyValue() != null)
				return false;
		} else if (!searchKeyValue.equals(other.getSearchKeyValue()))
			return false;
		return true;
	}
}
