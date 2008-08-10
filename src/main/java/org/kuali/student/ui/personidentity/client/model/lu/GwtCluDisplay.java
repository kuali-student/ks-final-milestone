/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model.lu;

import java.io.Serializable;


/**
 * @author Garey
 *
 */
public class GwtCluDisplay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 33246893276918857L;
	private String cluId;	
	private String luTypeId;	
	private String cluShortName;
		
	private GwtAtpDisplay atpDisplayStart;	
	private GwtAtpDisplay atpDisplayEnd;
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
	 * @return the luTypeId
	 */
	public String getLuTypeId() {
		return luTypeId;
	}
	/**
	 * @param luTypeId the luTypeId to set
	 */
	public void setLuTypeId(String luTypeId) {
		this.luTypeId = luTypeId;
	}
	/**
	 * @return the cluShortName
	 */
	public String getCluShortName() {
		return cluShortName;
	}
	/**
	 * @param cluShortName the cluShortName to set
	 */
	public void setCluShortName(String cluShortName) {
		this.cluShortName = cluShortName;
	}
	/**
	 * @return the atpDisplayStart
	 */
	public GwtAtpDisplay getAtpDisplayStart() {
		return atpDisplayStart;
	}
	/**
	 * @param atpDisplayStart the atpDisplayStart to set
	 */
	public void setAtpDisplayStart(GwtAtpDisplay atpDisplayStart) {
		this.atpDisplayStart = atpDisplayStart;
	}
	/**
	 * @return the atpDisplayEnd
	 */
	public GwtAtpDisplay getAtpDisplayEnd() {
		return atpDisplayEnd;
	}
	/**
	 * @param atpDisplayEnd the atpDisplayEnd to set
	 */
	public void setAtpDisplayEnd(GwtAtpDisplay atpDisplayEnd) {
		this.atpDisplayEnd = atpDisplayEnd;
	}
	
}
