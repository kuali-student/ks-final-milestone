package org.kuali.student.r2.lum.classI.lrc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.common.dto.RichTextInfo;

public interface ResultValueGroup extends Serializable {
	public String getName();

	/**
	 * Narrative description of the result component
	 */
	public RichTextInfo getDesc();

	/**
	 * List of result values
	 */
	public List<String> getResultValues();

	/**
	 * Date and time that this result component became effective. This is a
	 * similar concept to the effective date on enumerated values. When an
	 * expiration date has been specified, this field must be less than or equal
	 * to the expiration date.
	 */
	public Date getEffectiveDate();

	/**
	 * Date and time that this result component expires. This is a similar
	 * concept to the expiration date on enumerated values. If specified, this
	 * should be greater than or equal to the effective date. If this field is
	 * not specified, then no expiration date has been currently defined and
	 * should automatically be considered greater than the effective date.
	 */
	public Date getExpirationDate();

	/**
	 * Unique identifier for a result component type.
	 */
	public String getType();

	/**
	 * The current status of the result component. The values for this field are
	 * constrained to those in the state enumeration. A separate setup operation
	 * does not exist for retrieval of the meta data around this value.
	 */
	public String getState();

	/**
	 * Unique identifier for a result component. This is optional, due to the
	 * identifier being set at the time of creation. Once the result component
	 * has been created, this should be seen as required.
	 */
	public String getId();
}
