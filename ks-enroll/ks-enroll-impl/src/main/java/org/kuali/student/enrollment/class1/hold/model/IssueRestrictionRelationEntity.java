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
package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.MetaEntity;

/**
 * Entity class to hold an instance of a Issue-Restriction relationship
 *
 */
@Entity
@Table(name = "KSEN_ISSRESTRCTN_RELTN")
public class IssueRestrictionRelationEntity extends MetaEntity {

    @ManyToOne
    @JoinColumn(name = "ISSUE_ID")
    private IssueEntity issue;
    
    @ManyToOne
    @JoinColumn(name = "RESTRICTION_ID")
    private RestrictionEntity restriction;

    public IssueEntity getIssue() {
        return issue;
    }

    public void setIssue(IssueEntity issue) {
        this.issue = issue;
    }

    public RestrictionEntity getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionEntity restriction) {
        this.restriction = restriction;
    }
    
}
