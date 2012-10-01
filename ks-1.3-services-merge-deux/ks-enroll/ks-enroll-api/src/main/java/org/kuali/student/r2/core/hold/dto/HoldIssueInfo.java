/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.hold.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.hold.infc.HoldIssue;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueInfo", propOrder = {
    "id", "typeKey", "stateKey", "name",
    "descr", "organizationId", "meta",
    "attributes", "_futureElements"})
public class HoldIssueInfo extends IdEntityInfo implements HoldIssue, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String organizationId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public HoldIssueInfo() {
    }

    /**
     * Constructs a new IssueInfo from another Issue.
     * 
     * @param issue the Issue to copy
     */
    public HoldIssueInfo(HoldIssue issue) {
        super(issue);
        if (null != issue) {
            this.organizationId = issue.getOrganizationId();
        }
    }

    @Override
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String orgId) {
        this.organizationId = orgId;
    }
}
