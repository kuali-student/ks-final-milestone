package org.kuali.student.registration.client.model;

import java.io.Serializable;

public class GwtRelationStateInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5031070627956279509L;
	protected String state;

	public GwtRelationStateInfo(){};
	
	public GwtRelationStateInfo(String state)
	{
		this.state = state;
	};
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
}
