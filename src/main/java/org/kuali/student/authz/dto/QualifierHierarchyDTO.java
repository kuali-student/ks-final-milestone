package org.kuali.student.authz.dto;

import java.util.ArrayList;
import java.util.List;

public class QualifierHierarchyDTO {
    
	private String id;
    
    private String name;
    private List<QualifierDTO> qualifiers;
    private List<QualifierTypeDTO> qualifierTypes;
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
	 * @return the qualifiers
	 */
	public List<QualifierDTO> getQualifiers() {
		if(qualifiers==null){
			qualifiers=new ArrayList<QualifierDTO>();
		}
		return qualifiers;
	}
	/**
	 * @param qualifiers the qualifiers to set
	 */
	public void setQualifiers(List<QualifierDTO> qualifiers) {
		this.qualifiers = qualifiers;
	}
	/**
	 * @return the qualifierTypes
	 */
	public List<QualifierTypeDTO> getQualifierTypes() {
		if(qualifierTypes==null){
			qualifierTypes=new ArrayList<QualifierTypeDTO>();
		}
		return qualifierTypes;
	}
	/**
	 * @param qualifierTypes the qualifierTypes to set
	 */
	public void setQualifierTypes(List<QualifierTypeDTO> qualifierTypes) {
		this.qualifierTypes = qualifierTypes;
	}

}
