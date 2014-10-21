/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by dietrich on 2014/08/05
 */
package org.kuali.student.enrollment.class1.hold.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AppliedHoldMaintenanceWrapper implements Serializable {

    /**
     * APPLY, EDIT, EXPIRE or DELETE.
     */
    private String action;

    /**
     * Contains the personal information for the selected student.
     */
    private PersonInfo person;

    private AppliedHoldWrapper maintenanceHold;

    public AppliedHoldMaintenanceWrapper() {
        super();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PersonInfo getPerson() {
        return person;
    }

    public void setPerson(PersonInfo person) {
        this.person = person;
    }

    public AppliedHoldWrapper getMaintenanceHold() {
        if (maintenanceHold == null) {
            maintenanceHold = new AppliedHoldWrapper();
        }
        return maintenanceHold;
    }

    public void setMaintenanceHold(AppliedHoldWrapper maintenanceHold) {
        this.maintenanceHold = maintenanceHold;
    }

    public String getHoldCode() {
        return getMaintenanceHold().getHoldCode();
    }

    public void setHoldCode(String holdCode) {
        getMaintenanceHold().setHoldCode(holdCode.toUpperCase());
    }

    public String getEffectiveTerm() {
        return getMaintenanceHold().getEffectiveTerm();
    }

    public void setEffectiveTerm(String effectiveTerm) {
        getMaintenanceHold().setEffectiveTerm(effectiveTerm);
    }

    public String getExpirationTerm() {
        return getMaintenanceHold().getExpirationTerm();
    }

    public void setExpirationTerm(String expirationTerm) {
        getMaintenanceHold().setExpirationTerm(expirationTerm);
    }

    public HoldIssueInfo getHoldIssue() {
        return getMaintenanceHold().getHoldIssue();
    }

    public void setHoldIssue(HoldIssueInfo holdIssue) {
        getMaintenanceHold().setHoldIssue(holdIssue);
    }

    public AppliedHoldInfo getAppliedHold() {
        return getMaintenanceHold().getAppliedHold();
    }

    public void setAppliedHold(AppliedHoldInfo appliedHold) {
        getMaintenanceHold().setAppliedHold(appliedHold);
    }

    public String getViewHeaderInfo() {

        if (this.getPerson() != null) {
            return this.getPerson().getName() + " (" + this.getPerson().getId() + ")";
        }

        return StringUtils.EMPTY;
    }

    public String getPageHeaderInfo() {

        if (HoldsConstants.APPLIED_HOLDS_ACTION_APPLY.equals(this.getAction())) {
            return "Apply Hold";
        } else if (HoldsConstants.APPLIED_HOLDS_ACTION_EDIT.equals(this.getAction())) {
            return "Edit Hold";
        } else if (HoldsConstants.APPLIED_HOLDS_ACTION_EXPIRE.equals(this.getAction())) {
            return "Expire Hold";
        } else if (HoldsConstants.APPLIED_HOLDS_ACTION_DELETE.equals(this.getAction())) {
            return "Delete Hold";
        } else {
            return "Maintain Hold";
        }

    }
}
