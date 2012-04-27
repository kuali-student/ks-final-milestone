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
public class CourseOfferingRolloverRunner implements Runnable {
    
    private CourseOfferingService coService;
    private CourseOfferingSetService socService;
    private CourseService courseService;
    private AcademicCalendarService acalService;
    private String sourceSocId;
    private String targetSocId;
    private String targetTermId;
    private List<String> optionKeys;
    private ContextInfo context;
    private SocRolloverResultInfo result;
    
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
    
    public SocRolloverResultInfo getResult() {
        return result;
    }
    
    public void setResult(SocRolloverResultInfo result) {
        this.result = result;
        loadResult();
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
        
        if (result == null) {
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

        this.stillOfferable = getBooleanOption(CourseOfferingSetServiceConstants.STILL_OFFERABLE_OPTION_KEY, false);
        this.ifNoNewVersionExists = getBooleanOption(CourseOfferingSetServiceConstants.IF_NO_NEW_VERSION_OPTION_KEY, false);
        this.ignoreCancelled = getBooleanOption(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_OPTION_KEY, false);
        this.skipIfAlreadyExists = getBooleanOption(CourseOfferingSetServiceConstants.SKIP_IF_ALREADY_EXISTS_OPTION_KEY, false);
        this.useCanonical = getBooleanOption(CourseOfferingSetServiceConstants.USE_CANNONICAL_OPTION_KEY, false);
        this.noSchedule = getBooleanOption(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY, false);
        this.noInstructors = getBooleanOption(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY, false);
        this.logSuccesses = getBooleanOption(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY, false);
        this.progressFrequency = getIntOption(CourseOfferingSetServiceConstants.LOG_FREQUENCY_OPTION_KEY_PREFIX, 10);
        this.haltErrorsMax = getIntOption(CourseOfferingSetServiceConstants.HALT_ERRORS_MAX_OPTION_KEY_PREFIX, 10);
        
    }
    // TODO: implement these options
    private boolean stillOfferable = false; // implemented
    private boolean ifNoNewVersionExists = false; // implemented
    private boolean ignoreCancelled = false; // implemented
    private boolean skipIfAlreadyExists = false; // partially implemented
    private boolean useCanonical = false; // implemented
    private boolean noSchedule = false; // implemented
    private boolean noInstructors = false; // implemented
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
                this.result.setStateKey(CourseOfferingSetServiceConstants.ABORTED_RESULT_STATE_KEY);
                this.result.setMessage(new RichTextHelper().fromPlain("Got an unexpected exception running rolloever:\n" +
                        ex.toString()));
                this.socService.updateSocRolloverResult(result.getId(), result, context);
            } catch (Exception ex1) {
                Logger.getLogger(CourseOfferingRolloverRunner.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(CourseOfferingRolloverRunner.class.getName()).log(Level.SEVERE, null, ex1);
                throw new RuntimeException(ex1);
            }
        }
    }
    
    private void runInternal() throws Exception {
        // mark running
        result.setStateKey(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY);
        this.socService.updateSocRolloverResult(result.getId(), result, context);
        // Check if there are any course in the target soc
        if (!skipIfAlreadyExists) {
            List<String> targetCoIds = socService.getCourseOfferingIdsBySoc(targetSocId, context);
            if (!targetCoIds.isEmpty()) {
                throw new OperationFailedException(targetCoIds.size() + " course offerings already exist in the target soc");
            }
        }
        // mark the number expected
        List<String> sourceCoIds = socService.getCourseOfferingIdsBySoc(sourceSocId, context);
        result.setItemsProcessed(0);
        result.setItemsExpected(sourceCoIds.size());
        this.socService.updateSocRolloverResult(result.getId(), result, context);

        // Start processing
        int i = 0;
        int errors = 0;
        List<SocRolloverResultItemInfo> items = new ArrayList<SocRolloverResultItemInfo>();
        for (String sourceCoId : sourceCoIds) {
            try {
                SocRolloverResultItemInfo item = rolloverOneCourseOffering(sourceCoId);
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
                Logger.getLogger(CourseOfferingRolloverRunner.class.getName()).log(Level.SEVERE, "failed while processing the " +
                        i + "th course offering " + sourceCoId, ex);
                throw ex;
            }
            i++;
            this.socService.updateSocRolloverProgress(result.getId(), i, context);
        }
        // mark finished
        result.setItemsProcessed (i);
        result.setStateKey(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY);
        this.socService.updateSocRolloverResult(result.getId(), result, context);
    }
    
    private void reportProgress(List<SocRolloverResultItemInfo> items, int i) throws Exception {
        int modulo = i % progressFrequency;
        if (modulo != 0) {
            return;
        }
        this.socService.updateSocRolloverProgress(result.getId(), i, context);
        if (!this.logSuccesses) {
            stripSuccesses(items);
        }
        if (!items.isEmpty()) {
            this.socService.createSocRolloverResultItems(result.getId(), items, context);
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
    private SocRolloverResultItemInfo rolloverOneCourseOffering(String sourceCoId) throws Exception {
        CourseOfferingInfo sourceCo = this.coService.getCourseOffering(sourceCoId, context);
        if (this.ignoreCancelled) {
            if (sourceCo.getStateKey().equals(LuiServiceConstants.LUI_CANCELED_STATE_KEY)) {
                SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
                item.setSocRolloverResultId(result.getId());
                item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
                item.setStateKey(CourseOfferingSetServiceConstants.INFO_RESULT_ITEM_STATE_KEY);
                item.setSourceCourseOfferingId(sourceCo.getId());
                item.setTargetCourseOfferingId(null);
                item.setMessage(new RichTextHelper ().fromPlain("Skipped because course offering was cancelled in source term"));
                return item;
            }
        }
        CourseOfferingInfo targetCo = new CourseOfferingInfo(sourceCo);
        targetCo.setId(null);
        // clear out the ids on the internal sub-objects too
        for (OfferingInstructorInfo instr : targetCo.getInstructors()) {
            instr.setId(null);
        }

        targetCo.setFeeIds(sourceCo.getFeeIds());
        for (AttributeInfo attr : targetCo.getAttributes()) {
            attr.setId(null);
        }
        targetCo.setTermId(targetTermId);
        targetCo.setMeta(null);
        if (this.noInstructors) {
            targetCo.getInstructors().clear();
        }
        CourseInfo sourceCourse = this.getCourse(sourceCo.getCourseId());
        CourseInfo targetCourse = this.getCurrentVersionOfCourse(sourceCourse.getVersionInfo().getVersionIndId());
        targetCo.setCourseId(targetCourse.getId());
        if (this.skipIfAlreadyExists) {
            String existingCoId = this.findFirstExistingCourseOfferingIdInTargetTerm (targetCourse.getId());
            if (existingCoId != null) {
                SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
                item.setSocRolloverResultId(result.getId());
                item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
                item.setStateKey(CourseOfferingSetServiceConstants.INFO_RESULT_ITEM_STATE_KEY);
                item.setSourceCourseOfferingId(sourceCo.getId());
                item.setTargetCourseOfferingId(existingCoId);
                item.setMessage(new RichTextHelper ().fromPlain("Skipped because course offering already exists in target term"));
                return item;  
            }
        }
        // TODO: Not hard code "Active" but use a constant ... except these are R1 States
        if (this.stillOfferable) {
            if (!targetCourse.getState().equals("Active")) {
                SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
                item.setSocRolloverResultId(result.getId());
                item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
                item.setStateKey(CourseOfferingSetServiceConstants.INFO_RESULT_ITEM_STATE_KEY);
                item.setSourceCourseOfferingId(sourceCo.getId());
                item.setMessage(new RichTextHelper().fromPlain("skipped because canonical course is no longer active"));
                return item;
            }
        }
        if (this.ifNoNewVersionExists) {
            if (!sourceCourse.getId().equals(targetCourse.getId())) {
                SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
                item.setSocRolloverResultId(result.getId());
                item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
                item.setStateKey(CourseOfferingSetServiceConstants.INFO_RESULT_ITEM_STATE_KEY);
                item.setSourceCourseOfferingId(sourceCo.getId());
                item.setMessage(new RichTextHelper().fromPlain("skipped because there is a new version of the canonical course"));
                return item;
            }
        }
        if (this.useCanonical) {
            // copy from cannonical
            CourseOfferingTransformer coTransformer = new CourseOfferingTransformer();
            coTransformer.copyFromCanonical(targetCourse, targetCo);
        }
        targetCo = this.coService.createCourseOffering(targetCo.getCourseId(), targetCo.getTermId(), targetCo.getTypeKey(),
                targetCo, context);
        for (FormatOfferingInfo sourceFo : this.coService.getFormatOfferingByCourseOfferingIds(sourceCo.getId(), context)) {
            FormatOfferingInfo targetFo = new FormatOfferingInfo(sourceFo);
            targetFo.setId(null);
            // clear out the ids on the internal sub-objects
            for (AttributeInfo attr : targetFo.getAttributes()) {
                attr.setId(null);
            }
            targetFo.setCourseOfferingId(targetCo.getId());
            targetFo.setTermId(targetTermId);
            targetFo.setMeta(null);
            targetFo = this.coService.createFormatOffering(targetFo.getCourseOfferingId(), targetFo.getFormatId(),
                    targetFo.getTypeKey(), targetFo, context);
            for (ActivityOfferingInfo sourceAo : this.coService.getActivityOfferingsByFormatOffering(sourceFo.getId(), context)) {
                ActivityOfferingInfo targetAo = new ActivityOfferingInfo(sourceAo);
                targetAo.setId(null);
                // clear out the ids on the internal sub-objects
                for (AttributeInfo attr : targetAo.getAttributes()) {
                    attr.setId(null);
                }
                for (OfferingInstructorInfo instr : targetAo.getInstructors()) {
                    instr.setId(null);
                }
                targetAo.setFormatOfferingId(targetFo.getId());
                targetAo.setTermId(targetTermId);
                targetAo.setMeta(null);
                if (this.noSchedule) {
                    targetAo.setScheduleId(null);
                    // TODO: set the schedule request to null as well
                }
                if (this.noInstructors) {
                    targetAo.getInstructors().clear();
                }
                targetAo = this.coService.createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(),
                        targetAo.getTypeKey(), targetAo, context);
            }
        }
        SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
        item.setSocRolloverResultId(result.getId());
        item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
        item.setStateKey(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY);
        item.setSourceCourseOfferingId(sourceCo.getId());
        item.setTargetCourseOfferingId(targetCo.getId());
        return item;
    }

    private String findFirstExistingCourseOfferingIdInTargetTerm (String targetCourseId) throws Exception {
        List<CourseOfferingInfo> list = this.coService.getCourseOfferingsByCourseAndTerm(targetCourseId, targetTermId, context);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0).getId();
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
