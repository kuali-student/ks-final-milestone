package org.kuali.student.rules.BRMSCore;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class RuleMetaData {

	String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;

	String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	Date updateDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date effectiveDateStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date effectiveDateEnd;
	
	String version;
	String statusEnum;


	/**
	 * @param createdBy
	 * @param createDate
	 * @param updateBy
	 * @param updateDate
	 * @param effectiveDateStart
	 * @param effectiveDateEnd
	 * @param version
	 * @param statusEnum
	 */
	public RuleMetaData(String createdBy, Date createDate, String updateBy,
			Date updateDate, Date effectiveDateStart, Date effectiveDateEnd,
			String version, String statusEnum) {
		this.createdBy = createdBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.effectiveDateStart = effectiveDateStart;
		this.effectiveDateEnd = effectiveDateEnd;
		this.version = version;
		this.statusEnum = statusEnum;
	}
	
	/**
	 * 
	 */
	public RuleMetaData() {
		this.createdBy = null;
		this.createDate = null;
		this.updateBy = null;
		this.updateDate = null;
		this.effectiveDateStart = null;
		this.effectiveDateEnd = null;
		this.version = null;
		this.statusEnum = null;
	}	

	/**
	 * @return the createdBy
	 */
	public final String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public final void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createDate
	 */
	public final Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateBy
	 */
	public final String getUpdateBy() {
		return updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public final void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * @return the updateDate
	 */
	public final Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public final void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the effectiveDateStart
	 */
	public final Date getEffectiveDateStart() {
		return effectiveDateStart;
	}
	/**
	 * @param effectiveDateStart the effectiveDateStart to set
	 */
	public final void setEffectiveDateStart(Date effectiveDateStart) {
		this.effectiveDateStart = effectiveDateStart;
	}
	/**
	 * @return the effectiveDateEnd
	 */
	public final Date getEffectiveDateEnd() {
		return effectiveDateEnd;
	}
	/**
	 * @param effectiveDateEnd the effectiveDateEnd to set
	 */
	public final void setEffectiveDateEnd(Date effectiveDateEnd) {
		this.effectiveDateEnd = effectiveDateEnd;
	}
	/**
	 * @return the version
	 */
	public final String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public final void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the statusEnum
	 */
	public final String getStatusEnum() {
		return statusEnum;
	}
	/**
	 * @param statusEnum the statusEnum to set
	 */
	public final void setStatusEnum(String statusEnum) {
		this.statusEnum = statusEnum;
	}	
}
