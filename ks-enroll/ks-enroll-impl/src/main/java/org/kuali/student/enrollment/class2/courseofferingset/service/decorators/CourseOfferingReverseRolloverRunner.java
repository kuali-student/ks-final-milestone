/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.decorators;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.service.LuServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;

/**
 *
 * @author nwright
 */
public class CourseOfferingReverseRolloverRunner implements Runnable {

    private CourseOfferingService coService;
    private CourseOfferingSetService socService;
    private CourseService courseService;
    private AcademicCalendarService acalService;
    private String sourceSocId;
    private String targetSocId;
    private String targetTermId;
    private List<String> optionKeys;
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
        loadResult();
    }

    public SocRolloverResultInfo getRolloverResult() {
        return rolloverResult;
    }

    public void setRolloverResult(SocRolloverResultInfo rolloverResult) {
        this.rolloverResult = rolloverResult;
    }

    private void loadResult() {
        this.context = null;
        this.sourceSocId = null;
        this.targetTermId = null;
        this.targetSocId = null;
        this.optionKeys = new ArrayList<String>();
        this.progressFrequency = 100;
        this.logSuccesses = false;
        this.haltErrorsMax = -1;

        if (reverseResult == null) {
            return;
        }
        // this is how you would translate them from the general batch job parameter into specific ones for rollover
//        for (AttributeInfo attr : this.result.getParameters()) {
//            if (attr.getKey().equals(CourseOfferingSetServiceConstants.PARAMETER_SOURCE_SOC_ID_ATTR_KEY)) {
//                this.sourceSocId = attr.getValue();
//            }
//            if (attr.getKey().equals(CourseOfferingSetServiceConstants.PARAMETER_TARGET_TERM_ID_ATTR_KEY)) {
//                this.targetTermId = attr.getValue();
//            }
//            if (attr.getKey().equals(CourseOfferingSetServiceConstants.PARAMETER_OPTION_KEY_ATTR_KEY)) {
//                this.optionKeys.add(attr.getValue());
//            }
//        }
//        for (AttributeInfo attr : this.result.getGlobalResults()) {
//            if (attr.getKey().equals(CourseOfferingSetServiceConstants.GLOBAL_RESULT_TARGET_SOC_ID_ATTR_KEY)) {
//                this.targetSocId = attr.getValue();
//            }
//        }

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
        for (String optionKey : this.optionKeys) {
            if (optionKey.equals(key)) {
                return true;
            }
        }
        // default
        return defValue;
    }

    private int getIntOption(String keyPrefix, int defValue) {
        for (String optionKey : this.optionKeys) {
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
                this.reverseResult.setStateKey(CourseOfferingSetServiceConstants.ABORTED_RESULT_STATE_KEY);
                this.reverseResult.setMessage(new RichTextHelper().fromPlain("Got an unexpected exception running rolloever:\n" +
                        ex.toString()));
                this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);
            } catch (Exception ex1) {
                Logger.getLogger(CourseOfferingReverseRolloverRunner.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(CourseOfferingReverseRolloverRunner.class.getName()).log(Level.SEVERE, null, ex1);
                throw new RuntimeException(ex1);
            }
        }
    }

    private void runInternal() throws Exception {
        // mark running
        reverseResult.setStateKey(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY);
        this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);
        // Check if there are any course in the target soc
        List<String> targetCoIds = socService.getCourseOfferingIdsBySoc(targetSocId, context);
        reverseResult.setItemsProcessed(0);
        reverseResult.setItemsExpected(targetCoIds.size());
        this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);

        // Start processing
        int i = 0;
        int errors = 0;
        List<SocRolloverResultItemInfo> items = new ArrayList<SocRolloverResultItemInfo>();
        for (String sourceCoId : targetCoIds) {
            try {
                SocRolloverResultItemInfo item = reverseOneCourseOffering(sourceCoId);
                items.add(item);
                reportProgress(items, i);
                if (!item.getStateKey().equals(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY)) {
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
                Logger.getLogger(CourseOfferingReverseRolloverRunner.class.getName()).log(Level.SEVERE,
                        "failed while processing the " +
                        i + "th course offering " + sourceCoId, ex);
                throw ex;
            }
            i++;
            this.socService.updateSocRolloverProgress(reverseResult.getId(), i, context);
        }
        // mark finished
        reverseResult.setItemsProcessed (i);
        reverseResult.setStateKey(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY);
        this.socService.updateSocRolloverResult(reverseResult.getId(), reverseResult, context);
    }

    private void reportProgress(List<SocRolloverResultItemInfo> items, int i) throws Exception {
        int modulo = i % progressFrequency;
        if (modulo != 0) {
            return;
        }
        this.socService.updateSocRolloverProgress(reverseResult.getId(), i, context);
        if (!this.logSuccesses) {
            stripSuccesses(items);
        }
        if (!items.isEmpty()) {
            this.socService.createSocRolloverResultItems(reverseResult.getId(), items, context);
        }
        items.clear();
    }

    private void stripSuccesses(List<SocRolloverResultItemInfo> items) {
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>();
        for (SocRolloverResultItemInfo item : items) {
            if (!item.getStateKey().equals(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY)) {
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
            if (!this.wasCreatedInRollover (coId)) {
                SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
                item.setSocRolloverResultId(reverseResult.getId());
                item.setTypeKey(CourseOfferingSetServiceConstants.DELETE_RESULT_ITEM_TYPE_KEY);
                item.setStateKey(CourseOfferingSetServiceConstants.INFO_RESULT_ITEM_STATE_KEY);
                item.setSourceCourseOfferingId(coId);
                item.setTargetCourseOfferingId(coId);
                item.setMessage(new RichTextHelper().fromPlain("Skipped because course offering was not created in the original rollover"));
                return item; 
            }
        }
        // TODO: add a cascading delete for course offferings
        for (FormatOfferingInfo fo : this.coService.getFormatOfferingByCourseOfferingId(coId, context)) {
            for (ActivityOfferingInfo ao : this.coService.getActivityOfferingsByFormatOffering(fo.getId(), context)) {
                coService.deleteActivityOffering(ao.getId(), context);
            }
            coService.deleteFormatOffering(fo.getId(), context);
        }
        this.coService.deleteCourseOffering(sourceSocId, context);
        //
        SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
        item.setSocRolloverResultId(reverseResult.getId());
        item.setTypeKey(CourseOfferingSetServiceConstants.DELETE_RESULT_ITEM_TYPE_KEY);
        item.setStateKey(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY);
        item.setSourceCourseOfferingId(coId);
        item.setTargetCourseOfferingId(coId);
        return item;
    }
    
    
    private boolean wasCreatedInRollover(String coId) throws Exception {
        List<SocRolloverResultItemInfo> list = this.socService.getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(rolloverResult.getId(), coId, context);
        if (list.isEmpty()) {
            return false;
        }
        for (SocRolloverResultItemInfo item: list) {
            if (coId.equals(item.getTargetCourseOfferingId())) {
                if (item.getStateKey().equals(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY)) {
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
            course = courseService.getCourse(courseId);
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("The course does not exist. course: " + courseId, e);
        } catch (Exception e) {
            throw new OperationFailedException("unxpected trying to get course " + courseId, e);
        }
        return course;
    }

    private CourseInfo getCurrentVersionOfCourse(String versionIndCourseId)
            throws Exception {
        TermInfo targetTerm = acalService.getTerm(targetTermId, context);
        CourseInfo course = null;
        VersionDisplayInfo version;
        try {
            version = courseService.getCurrentVersionOnDate(LuServiceConstants.CLU_NAMESPACE_URI, versionIndCourseId,
                    targetTerm.getStartDate());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            // TODO: if no version exists for the target term should we return null instead?
            throw new DoesNotExistException("The course does not exist. course: " + versionIndCourseId, e);
        } catch (Exception e) {
            throw new OperationFailedException("unxpected trying to get course " + versionIndCourseId, e);
        }
        course = this.getCourse(version.getId());
        return course;
    }
}
