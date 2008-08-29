/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.authz.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class QualifierDTO {
    @XmlElement
	private String id;
    @XmlElement
    private String name;
    @XmlElement
    private QualifierTypeDTO qualifierType;
    
    @XmlElement
    private QualifierHierarchyDTO qualifierHierarchy;
    
    
    @XmlElement
    private List<QualifierDTO> qualifiers;
    
    private String parentId;
    
    private Map<String, String> pkQualifiers;
    
    private int leftVisit;
    private int rightVisit;
    
    /**
	 * @return the leftVisit
	 */
	public int getLeftVisit() {
		return leftVisit;
	}

	/**
	 * @param leftVisit the leftVisit to set
	 */
	public void setLeftVisit(int leftVisit) {
		this.leftVisit = leftVisit;
	}

	/**
	 * @return the rightVisit
	 */
	public int getRightVisit() {
		return rightVisit;
	}

	/**
	 * @param rightVisit the rightVisit to set
	 */
	public void setRightVisit(int rightVisit) {
		this.rightVisit = rightVisit;
	}

	/**
	 * @return the pkQualifiers
	 */
	public Map<String, String> getPkQualifiers() {
		if(pkQualifiers==null)
			pkQualifiers=new HashMap<String, String>();
		return pkQualifiers;
	}

	/**
	 * @param pkQualifiers the pkQualifiers to set
	 */
	public void setPkQualifiers(Map<String, String> pkQualifiers) {
		this.pkQualifiers = pkQualifiers;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
     * 
     * This method gets the role name associated with this qualfied role.
     * 
     * @return
     */    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<QualifierDTO> getQualifiers() {
//    	if(qualifiers != null){
//    		qualifiers = new ArrayList<QualifierDTO>();
//    	}
        return qualifiers;
    }
    
    public void setQualifiers(List<QualifierDTO> qualifiers) {
        this.qualifiers = qualifiers;
    }


	/**
	 * @return the qualifierType
	 */
	public QualifierTypeDTO getQualifierType() {
		return qualifierType;
	}

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
	 * @return the qualifierHierarchy
	 */
	public QualifierHierarchyDTO getQualifierHierarchy() {
		return qualifierHierarchy;
	}

	/**
	 * @param qualifierType the qualifierType to set
	 */
	public void setQualifierType(QualifierTypeDTO qualifierType) {
		this.qualifierType = qualifierType;
	}

	/**
	 * @param qualifierHierarchy the qualifierHierarchy to set
	 */
	public void setQualifierHierarchy(QualifierHierarchyDTO qualifierHierarchy) {
		this.qualifierHierarchy = qualifierHierarchy;
	}   
}
