package org.kuali.student.ap.framework.course;

import org.kuali.rice.core.api.util.KeyValue;

public class FacetKeyValue implements KeyValue {

	private static final long serialVersionUID = -3107737078457833197L;

	private final String key;
	private final String value;

	public FacetKeyValue(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}
	
}
