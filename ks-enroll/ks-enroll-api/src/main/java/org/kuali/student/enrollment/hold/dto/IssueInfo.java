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
package org.kuali.student.enrollment.hold.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.kuali.student.enrollment.hold.infc.Issue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "organizationId", "metaInfo", "attributes", "_futureElements"})

public class IssueInfo extends IdEntityInfo implements Issue, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String organizationId;

    @XmlAnyElement
    private final List<Element> _futureElements;

    private IssueInfo() {
	organizationId = null;
        _futureElements = null;
    }

    /**
     * Constructs a new IssueInfo from another Issue.
     *
     * @param issue the Issue to copy
     */
    public IssueInfo(Issue issue) {
        super(issue);
	this.organizationId = issue.getOrganizationId();
        _futureElements = null;
    }

    @Override
    public String getOrganizationId() {
        return organizationId;
    }


    /**
     * The builder class for this IssueInfo.
     */
    public static class Builder extends IdEntityInfo.Builder implements ModelBuilder<IssueInfo>, Issue {

        private String organizationId;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         * Constructs a new builder initialized from another 
	 * Issue.
         */
        public Builder(Issue issue) {
            super(issue);
	    this.organizationId = issue.getOrganizationId();
        }

        @Override
        public IssueInfo build() {
            return new IssueInfo(this);
        }

        @Override
        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }
    }
}
