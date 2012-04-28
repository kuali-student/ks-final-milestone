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
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
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

        this.skipIfAlreadyExists = getBooleanOption(CourseOfferingSetServiceConstants.SKIP_IF_ALREADY_EXISTS_OPTION_KEY, false);
        this.logSuccesses = getBooleanOption(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY, false);
        this.progressFrequency = getIntOption(CourseOfferingSetServiceConstants.LOG_FREQUENCY_OPTION_KEY_PREFIX, 10);
        this.haltErrorsMax = getIntOption(CourseOfferingSetServiceConstants.HALT_ERRORS_MAX_OPTION_KEY_PREFIX, 10);

    }
    // TODO: implement these options
    private boolean skipIfAlreadyExists = false; 
    private boolean logSuccesses = false; 
    private int progressFrequency = 100; 
    private int haltErrorsMax = -1; 

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
                SocRolloverResultItemInfo item = rolloverOneCourseOfferingReturningItem(sourceCoId);
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
        result.setItemsProcessed(i);
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
            this.socService.createSocRolloverResultItems(result.getId(),
                    CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY,
                    items,
                    context);
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
    private SocRolloverResultItemInfo rolloverOneCourseOfferingReturningItem(String sourceCoId) throws Exception {
        CourseOfferingInfo targetCo = null;
        String error = null;
        try {
            targetCo = this.coService.rolloveCourseOffering(sourceCoId, targetTermId, optionKeys, context);
        } catch (AlreadyExistsException ex) {
            error = ex.getMessage();
        } catch (DataValidationErrorException ex) {
            error = ex.getMessage();
        }
        SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
        item.setSocRolloverResultId(result.getId());
        item.setSourceCourseOfferingId(sourceCoId);
        item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
        if (error == null) {
            item.setStateKey(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY);
            item.setTargetCourseOfferingId(targetCo.getId());
            return item;
        }
        item.setStateKey(CourseOfferingSetServiceConstants.ERROR_RESULT_ITEM_STATE_KEY);
        item.setTargetCourseOfferingId(null);
        item.setMessage(new RichTextHelper().fromPlain(error));
        return item;
    }
   
}
