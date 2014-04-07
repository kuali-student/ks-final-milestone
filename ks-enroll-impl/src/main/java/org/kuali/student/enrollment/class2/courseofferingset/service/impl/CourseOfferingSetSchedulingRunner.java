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

import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacadeImpl;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class handles the asynchronous process of submitting schedule requests from activity offerings
 * within a SoC to the scheduler
 *
 * @author andrewlubbers
 */
public class CourseOfferingSetSchedulingRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CourseOfferingSetSchedulingRunner.class);

    private final String socId;

    private CourseOfferingSetService socService;

    private SchedulingService schedulingService;

    private CourseOfferingService coService;

    private ExamOfferingServiceFacade examOfferingServiceFacade;

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

    public ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        return examOfferingServiceFacade;
    }

    public void setExamOfferingServiceFacade(ExamOfferingServiceFacade examOfferingServiceFacade) {
        this.examOfferingServiceFacade = examOfferingServiceFacade;
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

            logger.info("Submitting {} course offerings for scheduling", coIds.size());

            for (String id : coIds) {
                CourseOfferingInfo coInfo = coService.getCourseOffering(id, contextInfo);
                logger.info("Processing schedule requests in AOs for CO, id={} , coCode={}", coInfo.getId(), coInfo.getCourseOfferingCode());

                List<ActivityOfferingInfo> activityOfferings = coService.getActivityOfferingsByCourseOffering(id, contextInfo);

                for (ActivityOfferingInfo aoInfo : activityOfferings) {
                    logger.info("\tProcessing schedule requests in AO, id={}, code={} type={}", aoInfo.getId(), aoInfo.getActivityCode(), aoInfo.getTypeKey());

                    if(aoInfo.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)) {
                        StatusInfo status = coService.scheduleActivityOffering(aoInfo.getId(), contextInfo);
                        logger.info("\t...scheduleActivityOffering returned with a status of {}, message={}", status.getIsSuccess(), status.getMessage());

                        //perform AO state change
                        if (status.getIsSuccess()){
                            StatusInfo statusInfo = coService.changeActivityOfferingState(aoInfo.getId(), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, contextInfo);
                            if (!statusInfo.getIsSuccess()){
                                logger.info("\t...Error updating Activity offering state to {} {}", LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, statusInfo);
                            }
                        } else {
                            logger.info("\t...Error scheduling Activity offering: {} {}", aoInfo.getActivityCode(), status);
                        }
                    }
                    else {
                        logger.info("\t...Activity Offering not sent to scheduler, not in a valid state to schedule: {}", aoInfo.getStateKey());
                    }
                }
                examOfferingServiceFacade.generateFinalExamOffering(coInfo, coInfo.getTermId(), examOfferingServiceFacade.getExamPeriodId(coInfo.getTermId(), contextInfo), new ArrayList<String>(), contextInfo);
                logger.info("Generating exam offerings for CO, id={} , coCode={}", coInfo.getId(), coInfo.getCourseOfferingCode());
            }

            // set the scheduling status of the SoC to completed
            contextInfo.setCurrentDate(new Date());
            socService.changeSocState(socId, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED, contextInfo);

        } catch (Exception e) {
            // When SoC failed state is created, set SoC scheduling state as failed here
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }
}
