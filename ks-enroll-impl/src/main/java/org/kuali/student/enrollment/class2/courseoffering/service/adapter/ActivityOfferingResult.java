/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 2/20/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.adapter;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;

import java.util.List;

/**
 * Used by the AutogenRegGroupServiceAdapter
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingResult {
    private ActivityOfferingInfo createdActivityOffering;
    private List<BulkStatusInfo> generatedRegistrationGroups;
    private BulkStatusInfo clusterstatus;

    public ActivityOfferingResult() {
        clusterstatus = new BulkStatusInfo();
        clusterstatus.setSuccess(Boolean.TRUE); // By default, set it true
    }

    public ActivityOfferingInfo getCreatedActivityOffering() {
        return createdActivityOffering;
    }

    public void setCreatedActivityOffering(ActivityOfferingInfo createdActivityOffering) {
        this.createdActivityOffering = createdActivityOffering;
    }

    public List<BulkStatusInfo> getGeneratedRegistrationGroups() {
        return generatedRegistrationGroups;
    }

    public void setGeneratedRegistrationGroups(List<BulkStatusInfo> generatedRegistrationGroups) {
        this.generatedRegistrationGroups = generatedRegistrationGroups;
    }

    public BulkStatusInfo getClusterstatus() {
        return clusterstatus;
    }

    public void setClusterstatus(BulkStatusInfo clusterstatus) {
        this.clusterstatus = clusterstatus;
    }
}
