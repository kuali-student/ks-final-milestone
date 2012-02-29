/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.organization.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.organization.infc.OrgTree;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgTreeInfo", propOrder = {
                "displayName", "orgHierarchyId", "orgId", "parentId",
                "positions", "positionId", "personId", "relationTypeKey",
                "_futureElements" })

public class OrgTreeInfo 
    implements OrgTree, Serializable {

    private static final long serialVersionUID = 7315439355073246895L;
    
    @XmlElement
    private String displayName;

    @XmlElement
    private String orgHierarchyId;
	
    @XmlElement
    private String orgId;
	
    @XmlElement
    private String parentId;
	
    @XmlElement
    private Long positions;

    @XmlElement
    private String positionId;
	
    @XmlElement
    private String personId;
	
    @XmlElement
    private String relationTypeKey;
	
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new OrgTreeInfo.
     */
    public OrgTreeInfo() {
    }
    
    /**
     * Constructs a new OrgTreeInfo from another OrgTree
     *
     * @param orgTree the org tree to copy
     */
    public OrgTreeInfo(OrgTree tree) {
        if (tree != null) {
            this.displayName = tree.getDisplayName();
            this.orgHierarchyId = tree.getOrgHierarchyId();
            this.orgId = tree.getOrgId();
            this.parentId = tree.getParentId();
            this.positions = tree.getPositions();
            this.positionId = tree.getPositionId();
            this.personId = tree.getPersonId();
            this.relationTypeKey = tree.getRelationTypeKey();
        }
    }	
        
    @Override
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getOrgHierarchyId() {
        return orgHierarchyId;
    }
    
    public void setOrgHierarchyId(String orgHierarchyId) {
        this.orgHierarchyId = orgHierarchyId;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }
	
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    @Override
    public Long getPositions(){
        return positions;
    }
    
    public void setPositions(Long positions){
        this.positions = positions;
    }

    @Override
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
    
    @Override
    public String getPersonId() {
        return personId;
    }
    
    public void setPersonId(String personId) {
        this.positionId = personId;
    }
    
    @Override
    public String getRelationTypeKey() {
        return relationTypeKey;
    }

    public void setRelationTypeKey(String relationTypeKey) {
        this.relationTypeKey = relationTypeKey;
    }    


    /*
     * The hashCode() and equals() methodos are here because the
     * current Organization service implementation uses a HashSet for
     * assembling OrgTrees. It isn't really a tree of Orgs but more of
     * a denormalized mash of information.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((orgHierarchyId == null) ? 0 : orgHierarchyId.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((positions == null) ? 0 : positions.hashCode());
        result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        result = prime * result + ((personId == null) ? 0 : personId.hashCode());
        result = prime * result + ((relationTypeKey == null) ? 0 : relationTypeKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        OrgTreeInfo other = (OrgTreeInfo) obj;
        
        if (displayName == null) {
            if (other.getDisplayName() != null) {
                return false;
            }
        } else if (!displayName.equals(other.getDisplayName())) {
            return false;
        }
        
        if (orgHierarchyId == null) {
            if (other.getOrgHierarchyId() != null) {
                return false;
            }
        } else if (!orgHierarchyId.equals(other.getOrgHierarchyId())) {
            return false;
        }

        if (orgId == null) {
            if (other.getOrgId() != null) {
                return false;
            }
        } else if (!orgId.equals(other.getOrgId())) {
            return false;
        }

        if (parentId == null) {
            if (other.getParentId() != null) {
                return false;
            }
        } else if (!parentId.equals(other.getParentId())) {
            return false;
        }

        if (positions == null) {
            if (other.getPositions() != null) {
                return false;
            }
        } else if (!positions.equals(other.getPositions())) {
            return false;
        }

        if (positionId == null) {
            if (other.getPositionId() != null) {
                return false;
            }
        } else if (!positionId.equals(other.getPositionId())) {
            return false;
        }

        if (personId == null) {
            if (other.getPersonId() != null) {
                return false;
            }
        } else if (!personId.equals(other.getPersonId())) {
            return false;
        }

        if (relationTypeKey == null) {
            if (other.getRelationTypeKey() != null) {
                return false;
            }
        } else if (!relationTypeKey.equals(other.getRelationTypeKey())) {
            return false;
        }
        
        return true;
    }
}
