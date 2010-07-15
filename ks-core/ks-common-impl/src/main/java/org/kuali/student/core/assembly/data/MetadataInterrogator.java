/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.assembly.data;

import java.util.Date;
import java.util.List;

import org.kuali.student.common.validator.DateParser;

/**
 * 
 * @author nwright
 */
public class MetadataInterrogator {

	public enum ConstraintIds {
		MULTI_LINE_TEXT("multi.line.text"), RICH_TEXT("rich.text");
		private final String id;

		private ConstraintIds(final String id) {
			this.id = id;
		}

		public String getId() {
			return this.id;
		}
	}

	private Metadata meta;

	public MetadataInterrogator(Metadata meta) {
		this.meta = meta;
	}

	/**
	 * Returns true if the metadata contains the specified constraint
	 */
	public boolean hasConstraint(String id) {
		return hasConstraint(meta, id);
	}


	/**
	 * checks if is required
	 * 
	 * @return true if any minOccurs > 1
	 */
	public boolean isRequired() {
		return isRequired(meta);
	}

	/**
	 * get the largest min occurs value
	 * 
	 * @return null if none specified
	 */
	public Integer getLargestMinOccurs() {
		return getLargestMinOccurs(meta);
	}

	/**
	 * checks if this field is a repeating field
	 * 
	 * @return true if the smallest maxOccurs is > 1
	 */
	public boolean isRepeating() {
		return isRepeating(meta);
	}

	/**
	 * checks if this field is a repeating field
	 * 
	 * @return true if the smallest maxOccurs is > 1
	 */
	public Integer getSmallestMaxOccurs() {
		return getSmallestMaxOccurs(meta);
	}

	/**
	 * get the largest min occurs value
	 * 
	 * @return null if none specified
	 */
	public Integer getLargestMinLength() {
		return getLargestMinLength(meta);
	}

	public Integer getSmallestMaxLength() {
		return getSmallestMaxLength(meta);
	}

	public boolean isMultilined() {
		return isMultilined(meta);
	}

	
	/**
	 * Returns true if the metadata contains the specified constraint
	 */
	public static boolean hasConstraint(Metadata meta, ConstraintIds id) {
		return hasConstraint(meta, id.getId());
	}
	/**
	 * Returns true if the metadata contains the specified constraint
	 */
	public static boolean hasConstraint(Metadata meta, String id) {
		boolean result = false;
		if (meta != null && meta.getConstraints() != null) {
			result = containsConstraint(meta.getConstraints(), id);
		}
		return result;
	}

	private static boolean containsConstraint(List<ConstraintMetadata> constraints,
			String id) {
		boolean result = false;
		if (constraints != null) {
			for (ConstraintMetadata con : constraints) {
				if ((con.getId() != null && con.getId().equals(id))
						|| containsConstraint(con.getChildConstraints(), id)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * checks if is required
	 * 
	 * @return true if any minOccurs > 1
	 */
	public static boolean isRequired(Metadata meta) {
		if (meta == null) {
			return false;
		}
		Integer largestMinOccurs = getLargestMinOccurs(meta);
		// no min occurs specified so not required
		if (largestMinOccurs == null) {
			return false;
		}
		if (largestMinOccurs >= 1) {
			return true;
		}
		return false;
	}

	/**
	 * get the largest min occurs value
	 * 
	 * @return null if none specified
	 */
	public static Integer getLargestMinOccurs(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Integer largestMinOccurs = null;
		// TODO: worry aboutg special validators
		// TODO: worry about how this applies to non-strings?
		for (ConstraintMetadata cons : meta.getConstraints()) {
			if (cons.getMinOccurs() != null) {
				if (largestMinOccurs == null) {
					largestMinOccurs = cons.getMinOccurs();
				} else if (cons.getMinOccurs() > largestMinOccurs) {
					largestMinOccurs = cons.getMinOccurs();
				}
			}
		}
		return largestMinOccurs;
	}

	/**
	 * checks if this field is a repeating field
	 * 
	 * @return true if the smallest maxOccurs is > 1
	 */
	public static boolean isRepeating(Metadata meta) {
		if (meta == null) {
			return false;
		}
		Integer smallestMaxOccurs = getSmallestMaxOccurs(meta);
		if (smallestMaxOccurs != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the smallest max occurs
	 * 
	 * @return 
	 * 
	 * Returns the smallest of all maxOccurs, if maxOccurs defined in constraints. 
	 * Returns -1, indicating unbounded repeating field, if repeating constraint defined & maxOccurs not defined. 
	 * Returns null, indicating that this is a non-repeating field.
	 */
	public static Integer getSmallestMaxOccurs(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Integer smallestMaxOccurs = null;
		boolean isRepeating = false;

		for (ConstraintMetadata cons : meta.getConstraints()) {
			if ("repeating".equals(cons.getId())){
				isRepeating = true;
			}
			if (cons.getMaxOccurs() != null) {
				if (smallestMaxOccurs == null) {
					smallestMaxOccurs = cons.getMaxOccurs();
				} else if (cons.getMaxOccurs() < smallestMaxOccurs) {
					smallestMaxOccurs = cons.getMaxOccurs();
				}
			}
		}
		
		if (isRepeating && smallestMaxOccurs == null){
			smallestMaxOccurs = -1;
		}
		
		return smallestMaxOccurs;
	}

	/**
	 * get the largest min occurs value
	 * 
	 * @return null if none specified
	 */
	public static Integer getLargestMinLength(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Integer largestMinLength = null;
		// TODO: worry aboutg special validators
		// TODO: worry about how this applies to non-strings?
		for (ConstraintMetadata cons : meta.getConstraints()) {
			if (cons.getMinLength() != null) {
				if (largestMinLength == null) {
					largestMinLength = cons.getMinLength();
				} else if (cons.getMinLength() > largestMinLength) {
					largestMinLength = cons.getMinLength();
				}
			}
		}
		return largestMinLength;
	}

	public static Integer getSmallestMaxLength(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Integer smallestMaxLength = null;
		// TODO: worry aboutg special validators
		// TODO: worry about how this applies to non-strings?
		for (ConstraintMetadata cons : meta.getConstraints()) {
			if (cons.getMaxLength() != null) {
				if (smallestMaxLength == null) {
					smallestMaxLength = cons.getMaxLength();
				} else if (cons.getMaxLength() < smallestMaxLength) {
					smallestMaxLength = cons.getMaxLength();
				}
			}
		}
		return smallestMaxLength;
	}

	public static boolean isMultilined(Metadata meta) {
		if (meta == null) {
			return false;
		}
		// TODO: worry about hard coding ids
		boolean result = (hasConstraint(meta, "rich.text") || hasConstraint(meta, "multi.line.text")) 
			&& !(hasConstraint(meta, "single.line.text") || hasConstraint(meta, "code") || hasConstraint(meta, "no.linefeeds"));
		
		// TODO: Worry about hard coding the cut-off point
		if (!result) {
			Integer maxLength = getSmallestMaxLength(meta);
			if (maxLength != null) {
				if (maxLength > 60) {
					result = true;
				}
			}
		}
		return result;
	}

	
	public static Long getLargestMinValue(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Long result = null;
		for (ConstraintMetadata cons : meta.getConstraints()) {
			Long min = tryParseLong(cons.getMinValue());
			if (min != null) {
				if (result == null || min > result) {
					result = min;
				}
			}
		}
		return result;
	}

	public static Long getSmallestMaxValue(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Long result = null;
		for (ConstraintMetadata cons : meta.getConstraints()) {
			Long max = tryParseLong(cons.getMaxValue());
			if (max != null) {
				if (result == null || max < result) {
					result = max;
				}
			}
		}
		return result;
	}

	
	public static Double getLargestMinValueDouble(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Double result = null;
		for (ConstraintMetadata cons : meta.getConstraints()) {
			Double min = tryParseDouble(cons.getMinValue());
			if (min != null) {
				if (result == null || min > result) {
					result = min;
				}
			}
		}
		return result;
	}

	public static Double getSmallestMaxValueDouble(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Double result = null;
		for (ConstraintMetadata cons : meta.getConstraints()) {
			Double max = tryParseDouble(cons.getMaxValue());
			if (max != null) {
				if (result == null || max < result) {
					result = max;
				}
			}
		}
		return result;
	}

	public static Date getLargestMinValueDate(Metadata meta, DateParser parser, Object dataValue) {
		if (meta == null) {
			return null;
		}
		Date result = null;
		for (ConstraintMetadata cons : meta.getConstraints()) {
			Date min = tryParseDate(cons.getMinValue(), parser);
			if (min != null) {
				if (result == null || min.getTime() > result.getTime()) {
					result = min;
				}
			}
		}
		if (dataValue != null && dataValue instanceof Date){
			if (result == null || (((Date)dataValue).getTime() > result.getTime())){
				result = (Date)dataValue;
			}
		}
		return result;
	}

	public static Date getSmallestMaxValueDate(Metadata meta, DateParser parser, Object dataValue) {
		if (meta == null) {
			return null;
		}
		Date result = null;
		for (ConstraintMetadata cons : meta.getConstraints()) {
			Date max = tryParseDate(cons.getMaxValue(), parser);
			if (max != null) {
				if (result == null || max.getTime() < result.getTime()) {
					result = max;
				}
			}
		}
		if (dataValue != null && dataValue instanceof Date){
			if (result==null || (((Date)dataValue).getTime() < result.getTime())){
				result = (Date)dataValue;
			}
		}
		return result;
	}
	
	private static Long tryParseLong(String s) {
		Long result = null;
		
		if (s != null) {
			try {
				result = Long.valueOf(s);
			} catch (Exception e) {
				// do nothing
			}
		}
		
		return result;
	}
	
	private static Double tryParseDouble(String s) {
		Double result = null;
		
		if (s != null) {
			try {
				result = Double.valueOf(s);
			} catch (Exception e) {
				// do nothing
			}
		}
		
		return result;
	}
	
	private static Date tryParseDate(String s, DateParser parser) {
		Date result = null;
		
		if (s != null) {
			try {
				result = parser.parseDate(s);
			} catch (Exception e) {
				// do nothing
			}
		}
		
		return result;
	}
}
