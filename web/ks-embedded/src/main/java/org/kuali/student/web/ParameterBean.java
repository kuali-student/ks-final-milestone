package org.kuali.student.web;

import java.io.Serializable;

/**
 * 
 * $Revision: 253 $ $Date: 2006-11-14 15:23:15 -0700 (Tue, 14 Nov 2006) $
 * 
 * @author Jeff Caddel
 * @since Jan 27, 2006 11:30:33 PM
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
