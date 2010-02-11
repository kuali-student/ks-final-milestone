package org.kuali.student.web;

import java.io.Serializable;

/**
 * 
 * @author Jeff Caddel
 */
public class ParameterBean implements Comparable<ParameterBean>, Serializable {
	private static final long serialVersionUID = 1L;

	String name;
	String[] values;

	public int compareTo(ParameterBean bean) {
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
