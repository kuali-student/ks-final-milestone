package org.kuali.student.common.ui.client.validator;

public enum ValidationMessageKeys {
	REQUIRED("validation.required"),
	MIN_OCCURS("validation.minOccurs"),
	MAX_OCCURS("validation.maxOccurs"),
	REQUIRES_FIELD("validation.requiresField"),
	VALID_CHARS("validation.validCharsFailed"),
	OCCURS("validation.occurs"),
	BOOLEAN("validation.mustBeBoolean"),
	DOUBLE("validation.mustBeDouble"),
	OUT_OF_RANGE("validation.outOfRange"),
	MAX_VALUE("validation.maxValueFailed"),
	MIN_VALUE("validation.minValueFailed"),
	FLOAT("validation.mustBeFloat"),
	LONG("validation.mustBeLong"),
	INTEGER("validation.mustBeInteger"),
	DATE("validation.mustBeDate"),
	LENGTH_OUT_OF_RANGE("validation.lengthOutOfRange"),
	MAX_LENGTH("validation.maxLengthFailed"),
	MIN_LENGTH("validation.minLengthFailed")
	;
	private final String key;
	private ValidationMessageKeys(final String key) {
		this.key = key;
	}
	public String getKey() {
		return this.key;
	}
}
