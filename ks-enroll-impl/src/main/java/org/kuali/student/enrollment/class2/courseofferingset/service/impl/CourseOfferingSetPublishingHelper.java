package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import javax.xml.namespace.QName;
import java.util.Date;
import java.util.List;

/**
 *  SOC mass publishing event helper.
 *
 *  This code needs to move to services layer ... probably to CourseOfferingSetServiceBuisnessLogic.
 *  Concurrency not addressed since it is just a stop-gap.
 */
public class CourseOfferingSetPublishingHelper {
    final static Logger LOG = Logger.getLogger(CourseOfferingSetPublishingHelper.class);

    private CourseOfferingService coService;
    private CourseOfferingSetService socService;

    /**
     * Kicks off SOC lifecycle mass publishing event. Runs asychronously by default.
     *
     * @param socId The ID of the SOC to publish.
     * @param optionKeys List of options.
     * @param context
     * @return A StatusInfo on success. Otherwise, throws and exception.
     */
    public StatusInfo startMassPublishingEvent(String socId, List<String> optionKeys, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        //  Validate the SOC. Should exist, state should be "publishing" and scheduling state "completed" .
        SocInfo soc = getSocService().getSoc(socId, context);
        if ( ! StringUtils.equals(soc.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)) {
            throw new OperationFailedException(String.format("SOC state [%s] was invalid for mass publishing.", soc.getStateKey()));
        }

        if (! StringUtils.equals(soc.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED)) {
            throw new OperationFailedException(String.format("SOC scheduling state [%s] was invalid for mass publishing.", soc.getStateKey()));
        }

        //  Initialize the runner
        final SocMassPublishingRunner runner = new SocMassPublishingRunner();
        runner.setCoService(getCourseOfferingService());
        runner.setSocService(getSocService());
        runner.setSocId(socId);
        runner.setContext(context);

        if (optionKeys.contains(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY)) {
            //  Run in the existing thread.
            runner.run();
        } else {
            //  Run asynchronously after any transactions clear
            KSThreadRunnerAfterTransactionSynchronization.runAfterTransactionCompletes(runner);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Success");

        return statusInfo;
    }

    //  For unit testing
    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    private CourseOfferingSetService getSocService() {
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    /**
     * Performs publishing state changes on COs, FOs, and AOs.
     *
     * !!! This code should set the SOC state to a sane value: "published" on success or back to "final edits" if there is a problem. !!!
     */
    public class SocMassPublishingRunner implements Runnable {
        private ContextInfo context;
        private CourseOfferingService coService;
        private CourseOfferingSetService socService;

        private String socId;

        @Override
        public void run() {
            boolean success = true;
            try {
                doMpe();
            } catch(Exception e) {
                LOG.error("The Mass Publishing Event did not complete successfully.", e);
                success = false;
            }

            if (! success) {
                LOG.warn(String.format("Changing SOC state back to [%s].", CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY));
                StatusInfo statusInfo = null;
                try {
                    statusInfo = socService.updateSocState(socId, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, context);
                } catch (Exception e) {
                    LOG.error(String.format("Unable to change SOC state back to [%s]. The SOC state will have to be manually updated to recover.",
                        CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY));
                }
                if (statusInfo == null || ! statusInfo.getIsSuccess()) {
                    throw new RuntimeException(String.format("State changed failed for SOC [%s]: %s", socId, statusInfo.getMessage()));
                }
            }
        }

        private void doMpe() throws Exception {
            LOG.warn(String.format("Beginning Mass Publishing Event for SOC [%s].", socId));
            context.setCurrentDate(new Date());

            //  Set SOC scheduling state to "published".
            LOG.warn(String.format("Updating SOC [%s] state to [%s].", socId, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY));
            context.setCurrentDate(new Date());
            StatusInfo statusInfo = socService.updateSocState(socId, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY, context);
            if ( ! statusInfo.getIsSuccess()) {
                throw new RuntimeException(String.format("State changed failed for SOC [%s]: %s", socId, statusInfo.getMessage()));
            }

            LOG.warn(String.format("Mass Publishing Event for SOC [%s] completed.", socId));
        }

        public String getSocId() {
            return socId;
        }

        public void setSocId(String socId) {
            this.socId = socId;
        }

        public void setContext(ContextInfo context) {
            this.context = context;
        }

        public void setCoService(CourseOfferingService coService) {
            this.coService = coService;
        }

        public void setSocService(CourseOfferingSetService socService) {
            this.socService = socService;
        }
    }
}
