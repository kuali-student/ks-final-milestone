/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseofferingset.dao.SocDao;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetServiceBusinessLogic;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import javax.xml.namespace.QName;

public class CourseOfferingSetServiceBusinessLogicImpl implements CourseOfferingSetServiceBusinessLogic {

    private CourseOfferingService coService;
    private CourseService courseService;
    private AcademicCalendarService acalService;
    private CourseOfferingSetService socService;
    private SocDao socDao;

    public SocDao getSocDao() {
        return socDao;
    }

    public void setSocDao(SocDao socDao) {
        this.socDao = socDao;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }
    
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

    private CourseOfferingSetService _getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                                                                                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private SocInfo _findTargetSoc(String targetTermId) {
        try {
            List<String> socIds = this._getSocService().getSocIdsByTerm(targetTermId, new ContextInfo());
            if (socIds != null) {
                if (socIds.isEmpty()) {
                    return null;
                }
                for (String socId: socIds) {
                    SocInfo targetSoc = this._getSocService().getSoc(socId, new ContextInfo());
                    if (targetSoc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                        return targetSoc;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean _hasOfferingsInTargetTerm(TermInfo targetTerm) {
        if (socDao == null) {
            return false; // Mostly to satisfy Norm's mock impls
        }
        Long count = socDao.countLuisByTypeForTermId(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, targetTerm.getId());
        return count > 0;
    }

    @Override
    public SocInfo rolloverSoc(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // validate the target term
        TermInfo targetTerm = this.acalService.getTerm(targetTermId, context);
        if (targetTerm == null) {
            throw new DoesNotExistException("target term id (" + targetTermId + ") does not exist");
        }
        // first create the new soc
        SocInfo sourceSoc = this._getSocService().getSoc(sourceSocId, context);
        if (sourceSoc.getTermId().equals(targetTermId)) {
            throw new InvalidParameterException("The term of the source soc and the target term must be different");
        }
        // DanS says if there are any offerings in the target term, we shouldn't perform rollover.  The implication
        // is that any courses that were copied from canonical prior to a rollover would prevent a rollover from
        // happening. DanS has said this is fine (as of 5/20/2012)
        if (_hasOfferingsInTargetTerm(targetTerm)) {
            throw new OperationFailedException("Can't rollover if course offerings exist in target term");
        }
        // Reuse SOC in target term
        SocInfo targetSoc = _findTargetSoc(targetTermId);
        boolean foundTargetSoc = true;
        if (targetSoc == null) {
            // Did not find target SOC, make a new one
            targetSoc = new SocInfo(sourceSoc);
            foundTargetSoc = false;
            targetSoc.setId(null);
            targetSoc.setTermId(targetTermId);
            targetSoc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
            try {
                targetSoc = this._getSocService().createSoc(targetSoc.getTermId(), targetSoc.getTypeKey(), targetSoc, context);
            } catch (DataValidationErrorException ex) {
                throw new OperationFailedException("Unexpected", ex);
            } catch (ReadOnlyException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
        } else { // There is already a target SOC, so re-use it?
            // TODO: if foundTargetSoc is true, should we do more cleanup?
            targetSoc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY); // Make it draft in the new term
            try {
                // Persist the draft state
                this._getSocService().updateSoc(targetSoc.getId(), targetSoc, context);
            } catch (DataValidationErrorException ex) {
                throw new OperationFailedException("Unexpected", ex);
            } catch (ReadOnlyException ex) {
                throw new OperationFailedException("Unexpected", ex);
            } catch (VersionMismatchException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
        }


        // then build the result so we can track stuff
        SocRolloverResultInfo result = new SocRolloverResultInfo();
        result.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        result.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        result.setSourceSocId(sourceSocId);
        result.setTargetTermId(targetTermId);
        result.setOptionKeys(optionKeys);
        result.setTargetSocId(targetSoc.getId());
        Date now = new Date();
        result.setDateInitiated(now);
        // Although it's not completed, as long as the SocRolloverResultInfo is either in the submitted or running
        // state, the date completed field represents the current time.  It also prevents NPEs when computing
        // duration.
        result.setDateCompleted(now);
        try {
            result = this._getSocService().createSocRolloverResult(result.getTypeKey(), result, context);
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
        runner.setSocService(this._getSocService());
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
    public SocRolloverResultInfo reverseRollover(String rolloverResultId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {// validate the target term
        SocRolloverResultInfo rolloverResult = this._getSocService().getSocRolloverResult(rolloverResultId, context);
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
            reverseResult = this._getSocService().createSocRolloverResult(reverseResult.getTypeKey(), reverseResult, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // create the runner so we can kick it off in another thread
        CourseOfferingReverseRolloverRunner runner = new CourseOfferingReverseRolloverRunner();
        runner.setContext(context);
        runner.setCoService(coService);
        runner.setSocService(this._getSocService());
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
        SocInfo soc = this._getSocService().getSoc(socId, context);
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
                throw new OperationFailedException(e.getMessage());
            }
        }
        return ids.size();
    }

    @Override
    public Boolean isCourseOfferingInSoc(String socId, String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        SocInfo soc = this._getSocService().getSoc(socId, context);
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
}
