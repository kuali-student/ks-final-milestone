package org.kuali.student.authz.dto;

import java.util.ArrayList;
import java.util.List;

public class QualifierTypeDTO {
    private String id;
    private String name;
    private List<QualifierTypeDTO> pkQualifierTypes;
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the pkQualifierTypes
	 */
	public List<QualifierTypeDTO> getPkQualifierTypes() {
    	if(pkQualifierTypes==null){
    		pkQualifierTypes = new ArrayList<QualifierTypeDTO>();
    	}
		return pkQualifierTypes;
	}
	/**
	 * @param pkQualifierTypes the pkQualifierTypes to set
	 */
	public void setPkQualifierTypes(List<QualifierTypeDTO> pkQualifierTypes) {
		this.pkQualifierTypes = pkQualifierTypes;
	}

}
