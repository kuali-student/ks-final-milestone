package org.kuali.student.commons.ui.viewmetadata.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.validators.client.ClientValidatorFactory;
import org.kuali.student.commons.ui.validators.client.Validator;

public class FieldMetaData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2530340875044427566L;
	private String fieldName = null;
	private Map<String, String> attributes = new HashMap<String, String>();
	private ViewMetaData parentView = null;
	
	public ViewMetaData getParentView() {
		return parentView;
	}

	public void setParentView(ViewMetaData parentView) {
		this.parentView = parentView;
	}

	public FieldMetaData() {
		super();
	}
	
	public FieldMetaData(String fieldName, Map<String, String> attributes) {
		super();
		this.fieldName = fieldName;
		this.attributes = attributes;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDisplayLabel() {
		return parentView.getMessages().get(fieldName);
	}
	public String getWidgetClassName() {
		return attributes.get("widgetClassName");
	}
	public int getTabIndex() {
		int result = 0;
		String s = attributes.get("tabIndex");
		if (s != null && s.trim().length() > 0) {
			result = Integer.parseInt(s);
		}
		return result;
	}
	public String getValidatorClassName() {
		return attributes.get("validatorClassName");
	}
	
	public Validator getValidatorInstance() {
		Validator result = null;
		String className = attributes.get("validatorClassName");
		result = ClientValidatorFactory.getValidator(className);
		if (result != null) {
			result.setAttributes(attributes);
			result.setMessages(parentView.getMessages());
		}
		return result;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public String getStyleName() {
		return attributes.get("styleName");
	}

	// TODO remove this
	public String toString() {
		String result = fieldName;
		for (String key : attributes.keySet()) {
			result += "\n* " + key + " = " + attributes.get(key);
		}
		return result;
	}
	
}
