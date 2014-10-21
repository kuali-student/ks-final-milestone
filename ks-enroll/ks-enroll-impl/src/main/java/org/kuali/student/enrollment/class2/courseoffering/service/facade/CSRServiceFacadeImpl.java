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
 * Created by Charles on 8/9/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.facade;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;

import javax.annotation.Resource;

/**
 * Impl for CSR
 *
 * @author Kuali Student Team
 */
public class CSRServiceFacadeImpl implements CSRServiceFacade {
    @Resource(name="coService")
    private CourseOfferingService coService;

    @Resource(name="typeService")
    private TypeService typeService;

    @Resource(name="socService")
    private CourseOfferingSetService socService;

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    @Override
    public void cancelActivityOffering(String aoId, ContextInfo context) throws Exception {
        StatusInfo statusInfo = coService.changeActivityOfferingState(aoId, LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY, context);
        if (!statusInfo.getIsSuccess()){
            throw new OperationFailedException("Error updating Activity offering state to " + LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY + " " + statusInfo);
        }
    }

    @Override
    public void suspendActivityOffering(String aoId, ContextInfo context) throws Exception {
        StatusInfo statusInfo = coService.changeActivityOfferingState(aoId, LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY, context);
        if (!statusInfo.getIsSuccess()){
            throw new OperationFailedException("Error updating Activity offering state to " + LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY + " " + statusInfo);
        }
    }

    @Override
    public void reinstateActivityOffering(ActivityOfferingInfo aoInfo, String socState, ContextInfo context) throws Exception {
        // Checking if we have ADLs for AO or not. If we have no ADLs (scheduleIDs) -> AO goes into draft state, otherwise will depend on SOC state
        if (aoInfo.getScheduleIds() != null && !aoInfo.getScheduleIds().isEmpty()) {
            if (StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
                StatusInfo statusInfo = coService.changeActivityOfferingState(aoInfo.getId(), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, context);
                if (!statusInfo.getIsSuccess()){
                    throw new OperationFailedException("Error updating Activity offering state to " + LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY + " " + statusInfo);
                }
            } else if (StringUtils.equals(socState, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY) ||
                            StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)) {
                StatusInfo statusInfo = coService.changeActivityOfferingState(aoInfo.getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, context);
                if (!statusInfo.getIsSuccess()){
                    throw new OperationFailedException("Error updating Activity offering state to " + LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY + " " + statusInfo);
                }
            } else {
                throw new RuntimeException("Error reinstating Activity offering in SOC " + socState);
            }
        } else {
            StatusInfo statusInfo = coService.changeActivityOfferingState(aoInfo.getId(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, context);
            if (!statusInfo.getIsSuccess()){
                throw new OperationFailedException("Error updating Activity offering state to " + LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY + " " + statusInfo);
            }
        }
    }
}
