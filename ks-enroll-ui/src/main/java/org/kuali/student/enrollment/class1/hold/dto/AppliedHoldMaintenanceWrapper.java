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
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AppliedHoldMaintenanceWrapper implements Serializable {

    private String action;
    private AppliedHoldWrapper maintenanceHold;
    private List<AppliedHoldWrapper> editingHolds;

    public AppliedHoldMaintenanceWrapper() {
        super();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public AppliedHoldWrapper getMaintenanceHold() {
        if(maintenanceHold==null){
            maintenanceHold = new AppliedHoldWrapper();
        }
        return maintenanceHold;
    }

    public void setMaintenanceHold(AppliedHoldWrapper maintenanceHold) {
        this.maintenanceHold = maintenanceHold;
    }

    public List<AppliedHoldWrapper> getEditingHolds() {
        return editingHolds;
    }

    public void setEditingHolds(List<AppliedHoldWrapper> editingHolds) {
        this.editingHolds = editingHolds;
    }

    public String getHoldCode() {
        return getMaintenanceHold().getHoldCode();
    }

    public void setHoldCode(String holdCode) {
        getMaintenanceHold().setHoldCode(holdCode);
    }

    public String getFirstTerm() {
        return getMaintenanceHold().getFirstTerm();
    }

    public void setFirstTerm(String firstTerm) {
        getMaintenanceHold().setFirstTerm(firstTerm);
    }

    public String getLastTerm() {
        return getMaintenanceHold().getLastTerm();
    }

    public void setLastTerm(String lastTerm) {
        getMaintenanceHold().setLastTerm(lastTerm);
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
}
