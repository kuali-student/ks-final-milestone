/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.organization.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.person.dto.PersonRelationInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgTreeInfo implements Serializable {

	private static final long serialVersionUID = 7315439355073246895L;

	@XmlAttribute
	private String orgId;
	@XmlAttribute
	private String parentId;
	@XmlAttribute
	private String displayName;

	private String positionId;
	
	private String personId;
	
	private String relationType;
	
	private String orgHierarchyId;
	
	private long positions=0;
	
	
	public OrgTreeInfo() {
		super();
	}
	public OrgTreeInfo(String orgId, String parentId, String displayName) {
		super();
		this.orgId = orgId;
		this.parentId = parentId;
		this.displayName = displayName;
	}	
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
    
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.positionId = personId;
    }
    
    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
    
    public long getPositions(){
        return positions;
    }
    
    public void setPositions(long positions){
        this.positions=positions;
    }
    
    
	public String getOrgHierarchyId() {
        return orgHierarchyId;
    }
    public void setOrgHierarchyId(String orgHierarchyId) {
        this.orgHierarchyId = orgHierarchyId;
    }
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrgTreeInfo other = (OrgTreeInfo) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		return true;
	}
	
}
