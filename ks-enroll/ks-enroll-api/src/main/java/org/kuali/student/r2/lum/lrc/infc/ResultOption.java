package org.kuali.student.r2.lum.lrc.infc;

import java.io.Serializable;
import java.util.Date;


public interface ResultOption extends Serializable {
	

	/**
	 * Unique identifier for a result usage type.
	 */
	public String getResultUsageTypeKey();
	public void setResultUsageTypeKey(String resultUsageTypeKey);

	/**
	 * Unique identifier for a result component.
	 */
	public String getResultComponentId();
	public void setResultComponentId(String resultComponentId);

	/**
	 * Date and time that this result option became effective. This is a similar
	 * concept to the effective date on enumerated values. When an expiration
	 * date has been specified, this field must be less than or equal to the
	 * expiration date.
	 */
	public Date getEffectiveDate();
	public void setEffectiveDate(Date effectiveDate);

	/**
	 * Date and time that this result option expires. This is a similar concept
	 * to the expiration date on enumerated values. If specified, this must be
	 * greater than or equal to the effective date. If this field is not
	 * specified, then no expiration date has been currently defined and should
	 * automatically be considered greater than the effective date.
	 */
	public Date getExpirationDate();
	public void setExpirationDate(Date expirationDate);

	
	/**
	 * Unique identifier for a result option. This is optional, due to the
	 * identifier being set at the time of creation. Once the result option has
	 * been created, this should be seen as required.
	 */
	public String getId();
	public void setId(String id);
}
