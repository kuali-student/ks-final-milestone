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

import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.infc.HoldIssue;

import java.io.Serializable;

/**
 * @author Kuali Student Team
 */
public class AppliedHoldMaintenanceWrapper implements Serializable {

    private String holdCode;
    private String firstTerm;
    private String lastTerm;
    private HoldIssueInfo holdIssue;
    private AppliedHoldInfo appliedHold;

    public AppliedHoldMaintenanceWrapper() {
        super();
    }

    public String getHoldCode() {
        return holdCode;
    }

    public void setHoldCode(String holdCode) {
        this.holdCode = holdCode;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public String getLastTerm() {
        return lastTerm;
    }

    public void setLastTerm(String lastTerm) {
        this.lastTerm = lastTerm;
    }

    public HoldIssueInfo getHoldIssue() {
        return holdIssue;
    }

    public void setHoldIssue(HoldIssueInfo holdIssue) {
        this.holdIssue = holdIssue;
    }

    public AppliedHoldInfo getAppliedHold() {
        return appliedHold;
    }

    public void setAppliedHold(AppliedHoldInfo appliedHold) {
        this.appliedHold = appliedHold;
    }
}
