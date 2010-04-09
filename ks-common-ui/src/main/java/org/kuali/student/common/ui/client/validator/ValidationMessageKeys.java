package org.kuali.student.common.ui.client.validator;

public enum ValidationMessageKeys {
	REQUIRED("validation.required"),
	MIN_OCCURS("validation.minOccurs", "minOccurs"),
	MAX_OCCURS("validation.maxOccurs", "maxOccurs"),
	REQUIRES_FIELD("validation.requiresField"),
	VALID_CHARS("validation.validCharsFailed"),
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
	MIN_LENGTH("validation.minLengthFailed", "minLength")
	;
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
