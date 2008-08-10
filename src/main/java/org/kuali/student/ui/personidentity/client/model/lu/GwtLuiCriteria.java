/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.io.Serializable;

/**
 * Search criteria for Learning Unit Instances.  Description comes from CLU
 * and can have % as wildcard matching.  LuTypeKey is the actual LuType and
 * does not support wildcards.  If both parameters have values then
 * the fields are used as an AND.
 */
public class GwtLuiCriteria implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3905588927841313072L;
	private String luTypeKey;
	private String description;

	public String getLuTypeKey() {
		return luTypeKey;
	}

	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
