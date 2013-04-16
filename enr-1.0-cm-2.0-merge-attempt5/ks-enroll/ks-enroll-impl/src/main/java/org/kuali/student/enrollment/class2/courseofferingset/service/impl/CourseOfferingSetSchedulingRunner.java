/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.Date;
import java.util.List;

/**
 * This class handles the asynchronous process of submitting schedule requests from activity offerings
 * within a SoC to the scheduler
 *
 * @author andrewlubbers
 */
public class CourseOfferingSetSchedulingRunner implements Runnable {

    final static Logger logger = Logger.getLogger(CourseOfferingSetSchedulingRunner.class);

    private final StringBuffer logBuffer = new StringBuffer();

    private final String socId;

    private CourseOfferingSetService socService;

    private SchedulingService schedulingService;

    private CourseOfferingService coService;

    private ContextInfo contextInfo;

    public CourseOfferingSetSchedulingRunner (String socId) {
        this.socId = socId;
    }

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    /**
     * !!! This method has to state change the SOC back to a sane value for success ("completed") or fail ("notstarted").
     */
    @Override
    public void run() {
        try {
            SocInfo soc = socService.getSoc(socId, contextInfo);
            List<String> coIds = socService.getCourseOfferingIdsBySoc(soc.getId(), contextInfo);

            log("Submitting ", coIds.size(), " course offerings for scheduling");

            for (String id : coIds) {
                CourseOfferingInfo coInfo = coService.getCourseOffering(id, contextInfo);
                log("Processing schedule requests in AOs for CO, id=", coInfo.getId(), " , coCode=",coInfo.getCourseOfferingCode());

                List<ActivityOfferingInfo> activityOfferings = coService.getActivityOfferingsByCourseOffering(id, contextInfo);

                for (ActivityOfferingInfo aoInfo : activityOfferings) {
                    log("\tProcessing schedule requests in AO, id=", aoInfo.getId(), " , code=",aoInfo.getActivityCode(), " type=", aoInfo.getTypeKey());

                    if(aoInfo.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)) {
                        StatusInfo status = coService.scheduleActivityOffering(aoInfo.getId(), contextInfo);
                        log("\t...scheduleActivityOffering returned with a status of ", status.getIsSuccess(), " , message=", status.getMessage());
                    }
                    else {
                        log("\t...Activity Offering not sent to scheduler, not in a valid state to schedule: ", aoInfo.getStateKey());
                    }
                }
            }

            // set the scheduling status of the SoC to completed
            contextInfo.setCurrentDate(new Date());
            socService.updateSocState(socId, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED, contextInfo);

        } catch (Exception e) {
            // When SoC failed state is created, set SoC scheduling state as failed here
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    private void log(Object ... params) {
        logBuffer.setLength(0);
        for (Object o : params) {
            logBuffer.append(o);
        }
        logger.info(logBuffer.toString());
        logBuffer.setLength(0);
    }

}
