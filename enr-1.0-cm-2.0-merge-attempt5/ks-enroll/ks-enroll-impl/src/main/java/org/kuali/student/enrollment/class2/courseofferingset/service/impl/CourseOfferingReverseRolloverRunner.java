/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

/**
 *
 * @author nwright
 */
public class CourseOfferingReverseRolloverRunner implements Runnable {

    final static Logger logger = Logger.getLogger(CourseOfferingRolloverRunner.class);
    private CourseOfferingService coService;
    private CourseOfferingSetService socService;
    private CourseService courseService;
    private AcademicCalendarService acalService;
    private ContextInfo context;
    private SocRolloverResultInfo rolloverResult;
    private SocRolloverResultInfo reverseResult;

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public ContextInfo getContext() {
        return context;
    }

    public void setContext(ContextInfo context) {
        this.context = context;
    }

    public SocRolloverResultInfo getReverseResult() {
        return reverseResult;
    }

    public void setReverseResult(SocRolloverResultInfo reverseResult) {
        this.reverseResult = reverseResult;
    }

    public SocRolloverResultInfo getRolloverResult() {
        return rolloverResult;
    }

    public void setRolloverResult(SocRolloverResultInfo rolloverResult) {
        this.rolloverResult = rolloverResult;
    }

    private void loadOptions() {
        this.justCreates = getBooleanOption(CourseOfferingSetServiceConstants.REVERSE_JUST_CREATES_OPTION_KEY, false);
        this.logSuccesses = getBooleanOption(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY, false);
        this.progressFrequency = getIntOption(CourseOfferingSetServiceConstants.LOG_FREQUENCY_OPTION_KEY_PREFIX, 10);
        this.haltErrorsMax = getIntOption(CourseOfferingSetServiceConstants.HALT_ERRORS_MAX_OPTION_KEY_PREFIX, 10);

    }
    // TODO: implement these options
    private boolean justCreates = false;
    private boolean logSuccesses = false; // implemented
    private int progressFrequency = 100; // implemented
    private int haltErrorsMax = -1; // implemented

    private boolean getBooleanOption(String key, boolean defValue) {
        for (String optionKey : this.reverseResult.getOptionKeys()) {
            if (optionKey.equals(key)) {
                return true;
            }
        }
        // default
        return defValue;
    }

    private int getIntOption(String keyPrefix, int defValue) {
        for (String optionKey : this.reverseResult.getOptionKeys()) {
            if (optionKey.startsWith(keyPrefix)) {
                return Integer.parseInt(optionKey);
            }
        }
        // default
        return defValue;
    }

    ////
    //// implement the run method
    ////  
    @Override
    public void run() {
        try {
            runInternal();
        } catch (Exception ex) {
            try {
                reverseResult = this.socService.getSocRolloverResult(reverseResult.getId(), context);
                this.reverseResult.setStateKey(CourseOfferingSetServiceConstants.ABORTED_RESULT_STATE_KEY);
                this.reverseResult.setMessage(new RichTextHelper().fromPlain("Got an unexpected exception running rolloever:\n" +
                        ex.toString()));
                this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);
            } catch (Exception ex1) {
                logger.fatal(reverseResult, ex);
                throw new RuntimeException(ex1);
            }
        }
    }

    private void runInternal() throws Exception {
        this.loadOptions();
        // mark running
        reverseResult.setStateKey(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY);
        this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);
        // Check if there are any course in the target soc
        List<String> targetCoIds = socService.getCourseOfferingIdsBySoc(this.reverseResult.getTargetSocId(), context);
        reverseResult.setItemsProcessed(0);
        reverseResult.setItemsExpected(targetCoIds.size());
        this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);

        // Start processing
        int i = 0;
        int errors = 0;
        List<SocRolloverResultItemInfo> items = new ArrayList<SocRolloverResultItemInfo>();
        for (String targetCoId : targetCoIds) {
            logger.info("Processing" + targetCoId);
            try {
                SocRolloverResultItemInfo item = reverseOneCourseOffering(targetCoId);
                items.add(item);
                reportProgressIfModulo(items, i);
                if (!CourseOfferingSetServiceConstants.SUCCESSFUL_RESULT_ITEM_STATES.contains(item.getStateKey())) {
                    errors++;
                    if (this.haltErrorsMax != -1) {
                        if (errors > this.haltErrorsMax) {
                            throw new OperationFailedException("Too many errors, exceeded the halt threshold: " + errors +
                                    " out of " + i + " course offerings rolled over");
                        }
                    }
                }
            } catch (Exception ex) {
                // log some conetxt for the exception
                logger.fatal("failed while processing the " + i + "th course offering " + targetCoId, ex);
                throw ex;
            }
            i++;
        }
        reportProgress(items, i);
        // mark finished
        reverseResult = this.socService.getSocRolloverResult(reverseResult.getId(), context);
        reverseResult.setStateKey(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY);
        this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);
    }

    private void reportProgressIfModulo(List<SocRolloverResultItemInfo> items, int i) throws Exception {
        int modulo = i % progressFrequency;
        if (modulo != 0) {
            return;
        }
        this.reportProgress(items, i);
    }

    private void reportProgress(List<SocRolloverResultItemInfo> items, int i) throws Exception {
        this.socService.updateSocRolloverProgress(reverseResult.getId(), i, context);
        if (!this.logSuccesses) {
            stripSuccesses(items);
        }
        if (!items.isEmpty()) {
            this.socService.createSocRolloverResultItems(reverseResult.getId(),
                    CourseOfferingSetServiceConstants.DELETE_RESULT_ITEM_TYPE_KEY,
                    items,
                    context);
        }
        items.clear();
    }

    private void stripSuccesses(List<SocRolloverResultItemInfo> items) {
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>();
        for (SocRolloverResultItemInfo item : items) {
            if (!CourseOfferingSetServiceConstants.SUCCESSFUL_RESULT_ITEM_STATES.contains(item.getStateKey())) {
                list.add(item);
            }
        }
        if (items.size() != list.size()) {
            items.clear();
            items.addAll(list);
        }
    }

    //
    // TODO: push this logic into the course offering service 
    private SocRolloverResultItemInfo reverseOneCourseOffering(String coId) throws Exception {
        if (this.justCreates) {
            if (!this.wasCreatedInRollover(coId)) {
                SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
                item.setSocRolloverResultId(reverseResult.getId());
                item.setTypeKey(CourseOfferingSetServiceConstants.DELETE_RESULT_ITEM_TYPE_KEY);
                item.setStateKey(CourseOfferingSetServiceConstants.INFO_RESULT_ITEM_STATE_KEY);
                item.setSourceCourseOfferingId(coId);
                item.setTargetCourseOfferingId(coId);
                item.setMessage(new RichTextHelper().fromPlain(
                        "Skipped because course offering was not created in the original rollover"));
                return item;
            }
        }
        // TODO: add a cascading delete for course offferings
        for (FormatOfferingInfo fo : this.coService.getFormatOfferingsByCourseOffering(coId, context)) {
            for (ActivityOfferingInfo ao : this.coService.getActivityOfferingsByFormatOffering(fo.getId(), context)) {
                coService.deleteActivityOffering(ao.getId(), context);
            }
            coService.deleteFormatOffering(fo.getId(), context);
        }
        this.coService.deleteCourseOffering(coId, context);
        //
        SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
        item.setSocRolloverResultId(reverseResult.getId());
        item.setTypeKey(CourseOfferingSetServiceConstants.DELETE_RESULT_ITEM_TYPE_KEY);
        item.setStateKey(CourseOfferingSetServiceConstants.DELETED_RESULT_ITEM_STATE_KEY);
        item.setSourceCourseOfferingId(coId);
        item.setTargetCourseOfferingId(coId);
        return item;
    }

    private boolean wasCreatedInRollover(String coId) throws Exception {
        List<SocRolloverResultItemInfo> list = this.socService.getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(
                rolloverResult.getId(), coId, context);
        if (list.isEmpty()) {
            return false;
        }
        for (SocRolloverResultItemInfo item : list) {
            if (coId.equals(item.getTargetCourseOfferingId())) {
                if (CourseOfferingSetServiceConstants.SUCCESSFUL_RESULT_ITEM_STATES.contains(item.getStateKey())) {
                    if (item.getTypeKey().equals(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // deal with R1 exception converting them to R2 exceptions

    private CourseInfo getCourse(String courseId) throws Exception {
        CourseInfo course = null;
        try {
            course = courseService.getCourse(courseId, context);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("The course does not exist. course: " + courseId, e);
        } catch (Exception e) {
            throw new OperationFailedException("unxpected trying to get course " + courseId, e);
        }
        return course;
    }
}
