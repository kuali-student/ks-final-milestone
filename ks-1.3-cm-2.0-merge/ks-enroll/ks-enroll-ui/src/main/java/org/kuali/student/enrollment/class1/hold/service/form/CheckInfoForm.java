/* Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class1.hold.service.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.process.dto.CheckInfo;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

public class CheckInfoForm extends UifFormBase {
    private static final long serialVersionUID = 4898118410378641665L;


    private String id;
    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String holdIssueId;
    private String milestoneTypeKey;
    private String agendaId;
    private String childProcessKey;
    private String rightComparisonValue;
    private String leftComparisonAgendaId;
    private String rightComparisonAgendaId;
    private CheckInfo  checkInfo;
    private List<CheckInfo> checkInfoList;

    public CheckInfoForm() {
        super();
        checkInfo = new CheckInfo();

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getRightComparisonValue() {
        return rightComparisonValue;
    }

    public void setRightComparisonValue(String rightComparisonValue) {
        this.rightComparisonValue = rightComparisonValue;
    }
    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public CheckInfo getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(CheckInfo chechInfo) {
        this.checkInfo = chechInfo;
    }

    public String getChildProcessKey() {
        return childProcessKey;
    }

    public void setChildProcessKey(String childProcessKey) {
        this.childProcessKey = childProcessKey;
    }

    public String getHoldIssueId() {
        return holdIssueId;
    }

    public void setHoldIssueId(String holdIssueId) {
        this.holdIssueId = holdIssueId;
    }

    public String getLeftComparisonAgendaId() {
        return leftComparisonAgendaId;
    }

    public void setLeftComparisonAgendaId(String leftComparisonAgendaId) {
        this.leftComparisonAgendaId = leftComparisonAgendaId;
    }

    public String getMilestoneTypeKey() {
        return milestoneTypeKey;
    }

    public void setMilestoneTypeKey(String milestoneTypeKey) {
        this.milestoneTypeKey = milestoneTypeKey;
    }

    public String getRightComparisonAgendaId() {
        return rightComparisonAgendaId;
    }

    public void setRightComparisonAgendaId(String rightComparisonAgendaId) {
        this.rightComparisonAgendaId = rightComparisonAgendaId;
    }


    public List<CheckInfo> getCheckInfoList() {
        return checkInfoList;
    }

    public void setCheckInfoList(List<CheckInfo> checkInfoList) {
        this.checkInfoList = checkInfoList;
    }

}
