/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.decorators;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingReverseRolloverRunner;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingRolloverRunner;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class CourseOfferingSetServiceCalculationDecorator extends CourseOfferingSetServiceDecorator {

    private CourseOfferingService coService;
    private CourseService courseService;
    private AcademicCalendarService acalService;

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
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

    @Override
    @Transactional(readOnly = false)
    public StatusInfo scheduleSoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("scheduleSoc has not been implemented");
    }

    @Override
    @Transactional(readOnly = false)
    public SocInfo rolloverSoc(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // validate the target term
        TermInfo targetTerm = this.acalService.getTerm(targetTermId, context);
        // first create the new soc
        SocInfo sourceSoc = this.getSoc(sourceSocId, context);
        if (sourceSoc.getTermId().equals(targetTermId)) {
            throw new InvalidParameterException("The term of the source soc and the target term must be different");
        }
        // TODO: try to find the soc in the target term and use it instead of just creating a new one
        SocInfo targetSoc = new SocInfo(sourceSoc);
        targetSoc.setId(null);
        targetSoc.setTermId(targetTermId);
        try {
            targetSoc = this.createSoc(targetSoc.getTermId(), targetSoc.getTypeKey(), targetSoc, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // then build the result so we can track stuff
        SocRolloverResultInfo result = new SocRolloverResultInfo();
        result.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        result.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        result.setSourceSocId(sourceSocId);
        result.setTargetTermId(targetTermId);
        result.setOptionKeys(optionKeys);
        result.setTargetSocId(targetSoc.getId());
        try {
            result = this.createSocRolloverResult(result.getTypeKey(), result, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // create the runner so we can kick it off in another thread
        CourseOfferingRolloverRunner runner = new CourseOfferingRolloverRunner();
        runner.setContext(context);
        runner.setCoService(coService);
        runner.setCourseService(courseService);
        runner.setAcalService(acalService);
        runner.setSocService(this);
        runner.setResult(result);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY)) {
            runner.run();
        } else {
            Thread thread = new Thread(runner);
            thread.start();
        }
        return targetSoc;
    }

    // this is for setting parameters if using the general batch job result service
//    private AttributeInfo conv2Attr(String key, String value) {
//        AttributeInfo attr = new AttributeInfo();
//        attr.setKey(key);
//        attr.setValue(value);
//        return attr;
//    }
    @Override
    @Transactional(readOnly = false)
    public SocRolloverResultInfo reverseRollover(String rolloverResultId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {// validate the target term
        SocRolloverResultInfo rolloverResult = this.getSocRolloverResult(rolloverResultId, context);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.REVERSE_JUST_CREATES_OPTION_KEY)) {
            if (!rolloverResult.getOptionKeys().contains(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY)) {
                throw new InvalidParameterException(
                        "You cannot reverse just the creates if the original rollover did not log the course offerings that it successfully created");
            }
        }
        SocRolloverResultInfo reverseResult = new SocRolloverResultInfo(rolloverResult);
        reverseResult.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        reverseResult.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        reverseResult.setOptionKeys(optionKeys);

        try {
            reverseResult = this.createSocRolloverResult(reverseResult.getTypeKey(), reverseResult, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // create the runner so we can kick it off in another thread
        CourseOfferingReverseRolloverRunner runner = new CourseOfferingReverseRolloverRunner();
        runner.setContext(context);
        runner.setCoService(coService);
        runner.setSocService(this);
        runner.setCourseService(courseService);
        runner.setAcalService(acalService);
        runner.setRolloverResult(rolloverResult);
        runner.setReverseResult(reverseResult);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY)) {
            runner.run();
        } else {
            Thread thread = new Thread(runner);
            thread.start();
        }
        return reverseResult;
    }

    @Override
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // THIS IS BASICALLY A SWITCH STATEMENT BASED ON THE TYPE OF THE SOC
        SocInfo soc = this.getSoc(socId, context);
        // main
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
            return coService.getCourseOfferingIdsByTerm(soc.getTermId(), Boolean.TRUE, context);
        }
        // subject area
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.SUBJECT_AREA_SOC_TYPE_KEY)) {
            return coService.getCourseOfferingIdsByTermAndSubjectArea(soc.getTermId(), soc.getSubjectArea(), context);
        }
        // units content owner
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.UNITS_CONTENT_OWNER_SOC_TYPE_KEY)) {
            return coService.getCourseOfferingIdsByTermAndUnitsContentOwner(soc.getTermId(), soc.getUnitsContentOwnerId(), context);
        }
        throw new OperationFailedException(soc.getTypeKey() + " is an unsupported type for this implementation");
//        List<String> list = new ArrayList<String>();
//        for (CourseOfferingInfo info : courseOfferingMap.values()) {
//            if (socId.equals(info.getSocId())) {
//                list.add(info.getId());
//            }
//        }
//        return list;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteCourseOfferingsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO: add bulk ops to CourseOfferingService so this can call them 
        // to delete all for a term or delete all for a subject area intead of doing it one by one
        List<String> ids = this.getCourseOfferingIdsBySoc(socId, context);
        for (String id : ids) {
            try {
                this.coService.deleteCourseOffering(socId, context);
            } catch (DependentObjectsExistException e) {
                throw new OperationFailedException( e.getMessage());
            }
        }
        return ids.size();
    }

    @Override
    public Boolean isCourseOfferingInSoc(String socId, String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        SocInfo soc = this.getSoc(socId, context);
        CourseOfferingInfo co = this.coService.getCourseOffering(courseOfferingId, context);
        // main
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
            if (co.getTermId().equals(soc.getTermId())) {
                return true;
            }
            // TODO: handle sub-terms  before returning false
            return false;
        }
        // subject area
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.SUBJECT_AREA_SOC_TYPE_KEY)) {
            if (co.getTermId().equals(soc.getTermId())) {
                if (co.getSubjectArea().equals(soc.getSubjectArea())) {
                    return true;
                }
            }
            // TODO: handle sub-terms  before returning false
            return false;
        }
        // units content owner
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.UNITS_CONTENT_OWNER_SOC_TYPE_KEY)) {
            if (co.getTermId().equals(soc.getTermId())) {
                if (co.getUnitsContentOwnerOrgIds().equals(soc.getUnitsContentOwnerId())) {
                    return true;
                }
            }
            // TODO: handle sub-terms before returning false
            return false;
        }
        // else get all of them and check if in the list
        List<String> ids = this.getCourseOfferingIdsBySoc(socId, context);
        return ids.contains(courseOfferingId);
    }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (CourseOfferingInfo info : this.coService.getCourseOfferingsByIds(list2, context)) {
            // TODO: add the published course offering state to the constants 
//            if (info.getStateKey().equals(CourseOfferingServiceConstants.PUBLISHED_STATE_KEY) {
            list.add(info.getId());
//            }
        }
        return list;
    }

    @Override
    public List<String> getUnpublishedCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (CourseOfferingInfo info : this.coService.getCourseOfferingsByIds(list2, context)) {
            // TODO: add the published course offering state to the constants 
//            if (info.getStateKey().equals(CourseOfferingServiceConstants.PUBLISHED_STATE_KEY) {
            list.add(info.getId());
//            }
        }
        return list;
    }

    @Override
    public List<String> getUnpublishedActivityOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (String coId : list2) {
            for (ActivityOfferingInfo ao : this.coService.getActivityOfferingsByCourseOffering(coId, context)) {
                // TODO: add the published course offering state to the constants 
//            if (!ao.getStateKey().equals(CourseOfferingServiceConstants.PUBLISHED_STATE_KEY) {
                list.add(ao.getId());
//            }
            }
        }
        return list;
    }

    @Override
    public List<String> getUnscheduledActivityOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (String coId : list2) {
            for (ActivityOfferingInfo ao : this.coService.getActivityOfferingsByCourseOffering(coId, context)) {
                // TODO: add the published course offering state to the constants 
//            if (!ao.getStateKey().equals(CourseOfferingServiceConstants.SCHEDULED_STATE_KEY) {
                list.add(ao.getId());
//            }
            }
        }
        return list;
    }

    @Override
    public SocRolloverResultInfo getSocRolloverResult(String rolloverResultId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SocRolloverResultInfo result = this.getNextDecorator().getSocRolloverResult(rolloverResultId, context);
        this.updateCalculatedFields(result, context);
        return result;
    }

    // TODO: push this logic down into the JPA layer for efficiency once the logic for the counts gets settled on 
    // My GUT says that they may want more counts than just the 2 we are getting now... I.e. count of warnings?
    private void updateCalculatedFields(SocRolloverResultInfo info, ContextInfo context) throws OperationFailedException {
        try {
            if (info.getSourceSocId() != null) {
                SocInfo sourceSoc = this.getSoc(info.getSourceSocId(), context);
                info.setSourceTermId(sourceSoc.getTermId());
            }
            // only do the calc once finished or the querying while running will be too long
            if (info.getStateKey().equals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY)) {
                List<SocRolloverResultItemInfo> items = this.getSocRolloverResultItemsByResultId(info.getId(), context);
                int success = 0;
                int failure = 0;
                for (SocRolloverResultItemInfo item : items) {
                    if (item.getStateKey().equals(CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY)) {
                        success++;
                    } else {
                        failure++;
                    }
                }
                info.setCourseOfferingsCreated(success);
                info.setCourseOfferingsSkipped(failure);
            }
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }
}
