package org.kuali.student.r2.lum.lrc.infc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

public interface ResultValueGroup extends Serializable {

	/**
	 * List of result values contained within the group. Examples are grades
	 * like A, B,C etc. A valid ResultValueGroup would contain either a range or
	 * more than one result value or a combination of both range and value(s)
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
	 * Unique identifier for a result component. This is optional, due to the
	 * identifier being set at the time of creation. Once the result component
	 * has been created, this should be seen as required.
	 */
	public String getId();

	/**
	 * The key of the range contained within this result value group. This is
	 * optional and might not be present for some Result Value Groups
	 * 
	 * @return
	 */
	public String getResultValueRangeInfo();

	/**
	 * contains the possible discrete results in this group with their rankings,
	 * the values in this group would be the ultimate set of results possible.
	 * The key of the map is the result value, either from the ResultValue Range
	 * or discrete results
	 * 
	 * @return
	 */
	public Map<String, String> getResultRankings();
}
