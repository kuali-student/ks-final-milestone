package org.kuali.student.core.dictionary.poc.dto;

import java.io.Serializable;

/**
 * Defines interface for a class that mask data values.
 * 
 * 
 */
public interface MaskFormatter extends Serializable {

	/**
	 * Takes in the real data value and returns a masked string.
	 * 
	 * @param value
	 *            - data value
	 * @return String - masked value
	 */
	public String maskValue(Object value);

}
