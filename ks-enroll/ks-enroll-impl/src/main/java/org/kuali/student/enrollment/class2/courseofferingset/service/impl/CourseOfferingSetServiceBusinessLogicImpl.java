/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.courseofferingset.dao.SocDao;
import org.kuali.student.enrollment.class2.courseofferingset.service.facade.RolloverAssist;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetServiceBusinessLogic;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseOfferingSetServiceBusinessLogicImpl implements CourseOfferingSetServiceBusinessLogic {

    private CourseOfferingService coService;
    private CourseService courseService;
    private AcademicCalendarService acalService;
    private CourseOfferingSetService socService;
    private RolloverAssist rolloverAssist;
    private SocDao socDao;
    private ExamOfferingServiceFacade examOfferingServiceFacade;
    private SchedulingService schedulingService;

    private boolean generateExamOfferings;

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

    public RolloverAssist getRolloverAssist() {
        return rolloverAssist;
    }

    public void setRolloverAssist(RolloverAssist rolloverAssist) {
        this.rolloverAssist = rolloverAssist;
    }

    public ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        return examOfferingServiceFacade;
    }

    public void setExamOfferingServiceFacade(ExamOfferingServiceFacade examOfferingServiceFacade) {
        this.examOfferingServiceFacade = examOfferingServiceFacade;
    }

    private CourseOfferingSetService _getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                                                                                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public boolean isGenerateExamOfferings() {
        return generateExamOfferings;
    }

    public void setGenerateExamOfferings(boolean generateExamOfferings) {
        this.generateExamOfferings = generateExamOfferings;
    }

    private boolean _hasOfferingsInTargetTerm(TermInfo targetTerm) {
        if (socDao == null) {
            return false; // Mostly to satisfy Norm's mock impls
        }
        Long count = socDao.countLuisByTypeForTermId(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, targetTerm.getId());
        return count > 0;
    }

    /**
     *
     * Checks if all terms and subterms are offical.
     *
     * @param targetTerm
     * @param contextInfo
     * @return If all terms and subterms are offical, return null; else return the termId of the first unoffical term.
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private String _verifyTermsOfficial(TermInfo targetTerm, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        if (!targetTerm.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)) {
            return targetTerm.getId();
        }
        List<TermInfo> childTerms = acalService.getIncludedTermsInTerm(targetTerm.getId(), contextInfo);
        for (TermInfo termInfo: childTerms) {
            if (!termInfo.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)) {
                return termInfo.getId();
            }
        }
        return null;
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
        String termId = null;
        if ((termId = _verifyTermsOfficial(targetTerm, context)) != null) {
            throw new OperationFailedException("Target (sub)term (id=" + termId + ") does not have official state.  Can't rollover");
        } else {
            // That way, rolloverCourseOffering can do less validation
            if (!optionKeys.contains(CourseOfferingSetServiceConstants.TARGET_TERM_VALIDATED_OPTION_KEY)) {
                optionKeys = new ArrayList<String>(optionKeys); // create a shallow copy so original is unmodified
                optionKeys.add(CourseOfferingSetServiceConstants.TARGET_TERM_VALIDATED_OPTION_KEY);
            }
        }
        // DanS says if there are any offerings in the target term, we shouldn't perform rollover.  The implication
        // is that any courses that were copied from canonical prior to a rollover would prevent a rollover from
        // happening. DanS has said this is fine (as of 5/20/2012)
        if (_hasOfferingsInTargetTerm(targetTerm)) {
            throw new OperationFailedException("Can't rollover if course offerings exist in target term");
        }

        // Reuse SOC in target term
        SocInfo targetSoc = CourseOfferingSetUtil.getMainSocForTermId(targetTermId, context);
        boolean foundTargetSoc = true;
        if (targetSoc == null) {
            // Did not find target SOC, make a new one
            targetSoc = new SocInfo(sourceSoc);
            foundTargetSoc = false;
            targetSoc.setId(null);
            targetSoc.getAttributes().clear();
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
            if (!targetSoc.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY))
            // Make it draft in the new term            
            // Persist the draft state
            this._getSocService().changeSocState(targetSoc.getId(), CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY, context);
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
        final CourseOfferingRolloverRunner runner = new CourseOfferingRolloverRunner();
        runner.setContext(context);
        runner.setCoService(coService);
        runner.setCourseService(courseService);
        runner.setAcalService(acalService);
        runner.setSocService(this._getSocService());
        runner.setRolloverAssist(rolloverAssist); // KSENROLL-8062 Add colo to rollover
        runner.setResult(result);

        if (optionKeys.contains(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY)) {
            //Run this thread synchronously
            runner.run();
        } else {
            //Try to run this after the transaction completes
            KSThreadRunnerAfterTransactionSynchronization.runAfterTransactionCompletes(runner);
        }
        return targetSoc;
    }

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
            this.coService.deleteCourseOfferingCascaded(id, context);
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

    @Override
    public StatusInfo startScheduleSoc(String socId, List<String> optionKeys,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //  Validate SOC. Ensure there is a valid Soc for the given id and make sure the state and scheduling state are correct.
        SocInfo socInfo = this._getSocService().getSoc(socId, context);
        if ( ! StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)) {
            throw new OperationFailedException(String.format("SOC state [%s] was invalid for mass schduling.", socInfo.getStateKey()));
        }

        if (! StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS)) {
            throw new OperationFailedException(String.format("SOC scheduling state [%s] was invalid for mass scheduling.", socInfo.getStateKey()));
        }

        // Check if service is configured to generate exam offerings.
        if (!this.isGenerateExamOfferings()){
            optionKeys.add(CourseOfferingSetServiceConstants.CONTINUE_WITHOUT_EXAM_OFFERINGS_OPTION_KEY);
        }

        final CourseOfferingSetSchedulingRunner schedulingRunner = new CourseOfferingSetSchedulingRunner(socInfo.getId(), optionKeys);
        schedulingRunner.setContextInfo(context);
        schedulingRunner.setCoService(coService);
        schedulingRunner.setSchedulingService(schedulingService);
        schedulingRunner.setSocService(this._getSocService());
        schedulingRunner.setExamOfferingServiceFacade(examOfferingServiceFacade);

        //Try to run this after the transaction completes
        KSThreadRunnerAfterTransactionSynchronization.runAfterTransactionCompletes(schedulingRunner);

        StatusInfo status = new StatusInfo();
        status.setMessage("Scheduling runner started successfully");
        return status;
    }
}
