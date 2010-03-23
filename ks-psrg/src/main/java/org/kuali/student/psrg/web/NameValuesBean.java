package org.kuali.student.web;

import java.io.Serializable;

/**
 * Represents a named set of data where there may be multiple values associated
 * with the same name.
 * 
 * @author Jeff Caddel
 */
@SuppressWarnings("serial")
public class NameValuesBean implements Comparable<NameValuesBean>, Serializable {

	String name;
	String[] values;

	public int compareTo(NameValuesBean bean) {
		return name.compareTo(bean.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String parameter) {
		this.name = parameter;
	}

	public String getValue() {
		if (values == null || values.length == 0) {
			return null;
		} else {
			return values[0];
		}
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

}
