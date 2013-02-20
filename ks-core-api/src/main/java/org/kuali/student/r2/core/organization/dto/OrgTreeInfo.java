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

import org.kuali.student.r2.core.organization.infc.OrgTree;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;
//import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgTreeInfo", propOrder = {
                "displayName", "org", "parentId",
                "positions", "relationTypeKey", "_futureElements" })

public class OrgTreeInfo 
    implements OrgTree, Serializable {

    private static final long serialVersionUID = 7315439355073246895L;
    
    @XmlElement
    private String displayName;

    @XmlElement
    private OrgInfo org;

    @XmlElement
    private String parentId;

    @XmlElement
    private Long positions;

    @XmlElement
    private String relationTypeKey;
	
    @XmlAnyElement
    private List<Object> _futureElements;  


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
    public OrgTreeInfo(OrgTree orgTree) {
        if (orgTree != null) {
            this.displayName = orgTree.getDisplayName();
            this.org = orgTree.getOrg();
            this.parentId = orgTree.getParentId();
            this.positions = orgTree.getPositions();
            this.relationTypeKey = orgTree.getRelationTypeKey();
        }
    }

    public OrgTreeInfo(OrgInfo organization, String parentId, String displayName) {
        super();
        this.org = organization;
        this.parentId = parentId;
        this.displayName = displayName;
    }
        
    @Override
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public OrgInfo getOrg() {
        return org;
    }
	
    public void setOrg(OrgInfo orgInfo) {
        this.org = orgInfo;
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
        result = prime * result + ((org == null) ? 0 : org.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((positions == null) ? 0 : positions.hashCode());
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

        if (org == null) {
            if (other.getOrg() != null) {
                return false;
            }
        } else if (!org.getId().equals(other.getOrg().getId())) {
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
