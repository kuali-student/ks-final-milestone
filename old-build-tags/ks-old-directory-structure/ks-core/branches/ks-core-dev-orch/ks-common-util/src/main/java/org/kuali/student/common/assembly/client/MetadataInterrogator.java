/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.assembly.client;

import java.util.List;

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
		// not specified so unbounded
		if (smallestMaxOccurs == null) {
			return true;
		}
		if (smallestMaxOccurs > 1) {
			return true;
		}
		return false;
	}

	/**
	 * checks if this field is a repeating field
	 * 
	 * @return true if the smallest maxOccurs is > 1
	 */
	public static Integer getSmallestMaxOccurs(Metadata meta) {
		if (meta == null) {
			return null;
		}
		Integer smallestMaxOccurs = null;
		// TODO: worry aboutg special validators
		// TODO: worry about how this applies to non-strings?
		for (ConstraintMetadata cons : meta.getConstraints()) {
			if (cons.getMaxOccurs() != null) {
				if (smallestMaxOccurs == null) {
					smallestMaxOccurs = cons.getMaxOccurs();
				} else if (cons.getMaxOccurs() < smallestMaxOccurs) {
					smallestMaxOccurs = cons.getMaxOccurs();
				}
			}
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
				} else if (cons.getMaxOccurs() > largestMinLength) {
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

	
}