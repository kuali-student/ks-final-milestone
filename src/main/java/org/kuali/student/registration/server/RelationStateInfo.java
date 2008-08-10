package org.kuali.student.registration.server;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RelationStateInfo implements Serializable {

	/**
	 * 
	 */
	public RelationStateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 8217516385088217534L;

	@XmlElement
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param state
	 */
	public RelationStateInfo(String state) {
		super();
		this.state = state;
	}

}
