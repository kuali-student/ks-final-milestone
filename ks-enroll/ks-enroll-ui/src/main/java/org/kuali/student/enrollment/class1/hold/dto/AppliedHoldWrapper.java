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

import java.io.Serializable;

/**
 * @author Kuali Student Team
 */
public class AppliedHoldWrapper implements Serializable {

    private String holdCode;
    private String effectiveTerm;
    private String expirationTerm;
    private HoldIssueInfo holdIssue;
    private AppliedHoldInfo appliedHold;

    public AppliedHoldWrapper() {
        super();
    }

    public String getHoldCode() {
        return holdCode;
    }

    public void setHoldCode(String holdCode) {
        this.holdCode = holdCode;
    }

    public String getEffectiveTerm() {
        return effectiveTerm;
    }

    public void setEffectiveTerm(String effectiveTerm) {
        this.effectiveTerm = effectiveTerm;
    }

    public String getExpirationTerm() {
        return expirationTerm;
    }

    public void setExpirationTerm(String expirationTerm) {
        this.expirationTerm = expirationTerm;
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

    public Boolean getIsHoldIssueTermBased() {
        if (holdIssue!=null){
            return holdIssue.getIsHoldIssueTermBased();
        }

        return false;
    }
}
