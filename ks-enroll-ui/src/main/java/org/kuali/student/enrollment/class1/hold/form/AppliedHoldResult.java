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
package org.kuali.student.enrollment.class1.hold.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.Date;

/**
 *
 * @author Kuali Student Team
 */
public class AppliedHoldResult extends UifFormBase {
    private String id;
    private String holdName;
    private String code;
    private String consequence;
    private String typeKey;
    private Date startDate;
    private Date endDate;
    private String startTerm;
    private String endTerm;
    private HoldIssueInfo holdIssue;

    private boolean isCheckedByCluster;

    private boolean enableExpireButton = true;
    private boolean enableDeleteButton = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoldName() {
        return holdName;
    }

    public void setHoldName(String holdName) {
        this.holdName = holdName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    public HoldIssueInfo getHoldIssue() {
        return holdIssue;
    }

    public void setHoldIssue(HoldIssueInfo holdIssue) {
        this.holdIssue = holdIssue;
    }

    public boolean getIsCheckedByCluster() {
        return isCheckedByCluster;
    }

    public void setIsCheckedByCluster(boolean checkedByCluster) {
        isCheckedByCluster = checkedByCluster;
    }

    public boolean isEnableExpireButton() {
        return enableExpireButton;
    }

    public void setEnableExpireButton(boolean enableExpireButton) {
        this.enableExpireButton = enableExpireButton;
    }

    public boolean isEnableDeleteButton() {
        return enableDeleteButton;
    }

    public void setEnableDeleteButton(boolean enableDeleteButton) {
        this.enableDeleteButton = enableDeleteButton;
    }
}
