package org.kuali.student.registration.client.model;

import java.io.Serializable;

public class GwtLuiPersonRelationTypeInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8035002772161422663L;
	protected String name;		
	
	
	public GwtLuiPersonRelationTypeInfo(){};
	
	public GwtLuiPersonRelationTypeInfo(String name){
		this.name = name;
	}    	
	
	public String getName() {return name;}		
	public void setName(String name) {
		this.name = name;
	}
	
}
