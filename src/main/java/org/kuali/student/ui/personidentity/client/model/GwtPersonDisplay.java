package org.kuali.student.ui.personidentity.client.model;

import java.io.Serializable;

public class GwtPersonDisplay implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7476494933269060450L;

	private String personId;
	
	private GwtPersonNameInfo name;

	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return the name
	 */
	public GwtPersonNameInfo getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(GwtPersonNameInfo name) {
		this.name = name;
	}
}
