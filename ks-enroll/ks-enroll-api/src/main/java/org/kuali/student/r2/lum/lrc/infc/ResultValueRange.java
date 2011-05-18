package org.kuali.student.r2.lum.lrc.infc;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @author sambit
 *
 */
public interface ResultValueRange extends Serializable{
	

	/**
	 * Min Result Value string Lower end of the value range. Typically
	 * corresponds with the short coded form of the result(ex. "1.0", "25.0"
	 * etc.) Should the data resultTypeKey of values (min/max) be numbers and
	 * not Strings in the value range?
	 */
	public String getMinValue();
	
	
	/**
	 * Max Result Value string Upper end of the value range. Typically
	 * corresponds with the short coded form of the result(ex. "3.0", "100.0"
	 * etc.). Upper end can be left empty to indicate unbounded upper end.
	 */
	public String getMaxValue();
	
	/**
	 * Increment number Legal increments in the result values. This has to be a
	 * decimal e.g 0.5)
	 * 
	 */
	public float getIncrement();
	
	
	
	/**
	 * Effective Date dateTime Date and time that this result value range became
	 * effective. This is a similar concept to the effective date on enumerated
	 * values. When an expiration date has been specified, this field must be
	 * less than or equal to the expiration date.
	 */
	public Date getEffectiveDate();
	/**
	 * Expiration Date dateTime Date and time that this result value range
	 * expires. This is a similar concept to the expiration date on enumerated
	 * values. If specified, this should be greater than or equal to the
	 * effective date. If this field is not specified, then no expiration date
	 * has been currently defined and should automatically be considered greater
	 * than the effective date.
	 */
	public Date getExpirationDate();

	/**
	 * 	
	 * @return
	 */
	public String getResultTypeKey();
	
	/**

	 */
	public String getId();

}
