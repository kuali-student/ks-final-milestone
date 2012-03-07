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

package org.kuali.student.common.ui.client.validator;

@Deprecated
public enum ValidationMessageKeys {
	REQUIRED("validation.required"),
	MIN_OCCURS("validation.minOccurs", "minOccurs"),
	MAX_OCCURS("validation.maxOccurs", "maxOccurs"),
	REQUIRES_FIELD("validation.requiresField"),
	VALID_CHARS("validation.validCharsFailed", "validChars"),
	OCCURS("validation.occurs"),
	BOOLEAN("validation.mustBeBoolean"),
	DOUBLE("validation.mustBeDouble"),
	OUT_OF_RANGE("validation.outOfRange"),
	MAX_VALUE("validation.maxValueFailed", "maxValue"),
	MIN_VALUE("validation.minValueFailed", "minValue"),
	FLOAT("validation.mustBeFloat"),
	LONG("validation.mustBeLong"),
	INTEGER("validation.mustBeInteger"),
	DATE("validation.mustBeDate"),
	LENGTH_OUT_OF_RANGE("validation.lengthOutOfRange"),
	MAX_LENGTH("validation.maxLengthFailed", "maxLength"),
	MIN_LENGTH("validation.minLengthFailed", "minLength"),
	INVALID_VALUE("validation.invalid");
    
	private final String key;
	private final String property;
	
	private ValidationMessageKeys(final String key) {	
		this.key = key;
		this.property = "";
	}

	private ValidationMessageKeys(final String key, final String property) {	
		this.key = key;
		this.property = property;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getProperty(){
		return this.property;
	}
}
