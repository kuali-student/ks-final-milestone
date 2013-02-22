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

import org.kuali.student.r2.core.organization.infc.OrgTreeView;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgTreeViewInfo", propOrder = {
                "org", "parents", "children", "_futureElements" })
public class OrgTreeViewInfo
    implements OrgTreeView, Serializable {

    private static final long serialVersionUID = 7315439355073246895L;

    @XmlElement
    private OrgInfo org;

    @XmlElement
    private List<OrgTreeViewInfo> parents;

    @XmlElement
    private List<OrgTreeViewInfo> children;
	
    @XmlAnyElement
    private List<Object> _futureElements;  


    /**
     * Constructs a new OrgTreeViewInfo.
     */
    public OrgTreeViewInfo() {
    }
    
    /**
     * Constructs a new OrgTreeViewInfo from another OrgTreeView
     *
     * @param orgTreeView the org tree to copy
     */
    public OrgTreeViewInfo(OrgTreeView orgTreeView) {
        if (orgTreeView != null) {
            if(orgTreeView.getOrg() != null) {
                this.org = new OrgInfo(orgTreeView.getOrg());
            }

            if(orgTreeView.getParents() != null) {
                parents = new ArrayList<OrgTreeViewInfo>();
                for(OrgTreeView parent : orgTreeView.getParents()) {
                    parents.add(new OrgTreeViewInfo(parent));
                }
            }

            if(orgTreeView.getChildren() != null) {
                children = new ArrayList<OrgTreeViewInfo>();
                for(OrgTreeView child : orgTreeView.getChildren()) {
                    children.add(new OrgTreeViewInfo(child));
                }
            }
        }
    }

    @Override
    public OrgInfo getOrg() {
        return org;
    }
	
    public void setOrg(OrgInfo orgInfo) {
        this.org = orgInfo;
    }

    @Override
    public List<OrgTreeViewInfo> getParents() {
        return parents;
    }

    public void setParents(List<OrgTreeViewInfo> parents) {
        this.parents = parents;
    }

    @Override
    public List<OrgTreeViewInfo> getChildren() {
        return children;
    }

    public void setChildren(List<OrgTreeViewInfo> children) {
        this.children = children;
    }
}
