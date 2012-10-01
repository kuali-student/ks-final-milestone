package org.kuali.student.enrollment.class2.grading.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class2.grading.assembler.GradeValuesGroupAssembler;
import org.kuali.student.enrollment.class2.grading.service.assembler.GradeRosterAssembler;
import org.kuali.student.enrollment.class2.grading.service.assembler.GradeRosterEntryAssembler;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LrrServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.jws.WebParam;
import java.util.*;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.service.LprRosterService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

public class GradingServiceImpl implements GradingService {
    private LprService lprService;
    private LprRosterService lprRosterService;
    private CourseOfferingService courseOfferingService;
    private LRCService lrcService;
    private LearningResultRecordService lrrService;
    private LuiService luiService;
    private GradeRosterAssembler gradeRosterAssembler;
    private GradeRosterEntryAssembler gradeRosterEntryAssembler;
    private GradeValuesGroupAssembler gradeValuesGroupAssembler;
    private CourseRegistrationService courseRegistrationService;

    /**
     * This method returns the TypeInfo for a given grade roster type key.
     *
     * @param gradeRosterTypeKey
     *            Key of the type
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Information about the Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *             gradeRosterTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *             invalid gradeRosterTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *             missing gradeRosterTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *             unable to complete request
     */
    @Override
    public TypeInfo getGradeRosterType(@WebParam(name = "gradeRosterTypeKey") String gradeRosterTypeKey,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return null; // TODO implement method.
    }

    /**
     * Retrieve information about a grade roster
     *
     * @param gradeRosterId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public GradeRosterInfo getGradeRoster(@WebParam(name = "gradeRosterId") String gradeRosterId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Retrieve information about grade rosters by grader and term
     *
     * @param graderId
     * @param termId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public List<GradeRosterInfo> getGradeRostersByGraderAndTerm(@WebParam(name = "graderId") String graderId,
            @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Retrieves rosters of final grades for a course offering
     *
     * @param courseOfferingId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public List<GradeRosterInfo> getFinalGradeRostersForCourseOffering(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<GradeRosterInfo> gradeRosterInfos = new ArrayList<GradeRosterInfo>();

        List<LprRosterInfo> lprRosters = lprRosterService.getLprRostersByTypeAndLui(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY, courseOfferingId, context);
        for (LprRosterInfo lprRoster : lprRosters) {
            GradeRosterInfo gradeRosterInfo = assembleGradeRoster(lprRoster, context);
            gradeRosterInfos.add(gradeRosterInfo);
        }

        return gradeRosterInfos;
    }

    /**
     * Retrieves rosters of final grade by actvity offerings
     *
     * @param activityOfferingId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public List<GradeRosterInfo> getFinalGradeRostersForActivityOffering(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Retrieves all rosters for an activity offering
     *
     * @param activityOfferingId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public List<GradeRosterInfo> getGradeRostersForActivityOffering(
            @WebParam(name = "activityOfferingId") String activityOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Build an interim roster of given type. Roster type should be used to
     * figure out which students from the activity offerings will be in the
     * roster
     *
     * @param activityOfferingIds
     * @param rosterTypeKey
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public GradeRosterInfo buildInterimGradeRosterByType(
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "activityOfferingIds") List<String> activityOfferingIds,
            @WebParam(name = "rosterTypeKey") String rosterTypeKey, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Update interim grade roster information
     *
     * @param gradeRoster
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     */
    @Override
    public GradeRosterInfo updateInterimGradeRoster(@WebParam(name = "gradeRoster") GradeRosterInfo gradeRoster,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        return null; // TODO implement method.
    }

    /**
     * Delete an interim grade roster
     *
     * @param gradeRosterId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public StatusInfo deleteInterimGradeRoster(@WebParam(name = "gradeRosterId") String gradeRosterId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Update state of final grade roster. Only state can be changed for the
     * final grade roster. Final grade submission is tracked through state
     * change on the roster.
     *
     * @param gradeRosterId
     * @param stateKey
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     */
    @Override
    public GradeRosterInfo updateFinalGradeRosterState(@WebParam(name = "gradeRosterId") String gradeRosterId,
            @WebParam(name = "newStateKey") String stateKey, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        LprRosterInfo lprRosterInfo = lprRosterService.getLprRoster(gradeRosterId,context);
        lprRosterInfo.setStateKey(stateKey);

        GradeRosterInfo returnRoster;
        try {
            LprRosterInfo newRoster = lprRosterService.updateLprRoster(gradeRosterId,lprRosterInfo,context);
            returnRoster = assembleGradeRoster(newRoster,context);
        } catch (ReadOnlyException e) {
            throw new RuntimeException(e);
        }

        //Update LRR State
        List<LprRosterEntryInfo> rosterEntryInfoList = lprRosterService.getLprRosterEntriesByLprRoster(lprRosterInfo.getId(),context);
        List<String> lprIds = new ArrayList();
        for (LprRosterEntryInfo entryInfo : rosterEntryInfoList) {
            lprIds.add(entryInfo.getLprId());
        }

        List<LearningResultRecordInfo> learningResultRecordInfoList = lrrService.getLearningResultRecordsForLprIds(lprIds,context);
        for (LearningResultRecordInfo learningResultRecordInfo : learningResultRecordInfoList) {
            //process only saved state entries (Skipping deleted state)
            if (StringUtils.equals(learningResultRecordInfo.getStateKey(),LrrServiceConstants.RESULT_RECORD_SAVED_STATE_KEY)){
                learningResultRecordInfo.setStateKey(LrrServiceConstants.RESULT_RECORD_SUBMITTED_STATE_KEY);
                lrrService.updateLearningResultRecord(learningResultRecordInfo.getId(),learningResultRecordInfo,context);
            }
        }

        return returnRoster;
    }

    /**
     * Validate a grade roster information
     *
     * @param gradeRoster
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    @Override
    public List<ValidationResultInfo> validateGradeRoster(@WebParam(name = "gradeRoster") GradeRosterInfo gradeRoster,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return null; // TODO implement method.
    }

    /**
     * Retrieve information about a grade roster entry
     *
     * @param gradeRosterEntryId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public GradeRosterEntryInfo getGradeRosterEntry(@WebParam(name = "gradeRosterEntryId") String gradeRosterEntryId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * Retrieve a list of grade roster entries based on their Ids. The method
     * should fail if there is an error in retrieving any id from the list.
     *
     * @param gradeRosterEntryIds
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByIds(
            @WebParam(name = "gradeRosterEntryIds") List<String> gradeRosterEntryIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {


        List<LprRosterEntryInfo> entries = lprRosterService.getLprRosterEntriesByIds(gradeRosterEntryIds, context);
        return assembleGradeRosterEntries(entries, context);
    }

    /**
     * Retrieve grade roster entries by roster
     *
     * @param gradeRosterId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByRosterId(
            @WebParam(name = "gradeRosterId") String gradeRosterId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LprRosterEntryInfo> entries = lprRosterService.getLprRosterEntriesByLprRoster(gradeRosterId, context);
        return assembleGradeRosterEntries(entries, context);
    }

    /**
     * This method ...
     *
     * @param studentId
     * @param courseOfferingId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public GradeRosterEntryInfo getFinalGradeForStudentInCourseOffering(@WebParam(name = "studentId") String studentId,
            @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException {


        List<LprRosterInfo> lprRosters = lprRosterService.getLprRostersByTypeAndLui(LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY, courseOfferingId, context);
        Map<String, LprRosterEntryInfo> lprIdToRosterEntryMap = new HashMap<String, LprRosterEntryInfo>();
        for (LprRosterInfo lprRoster : lprRosters) {
            String rosterId = lprRoster.getId();
            List<LprRosterEntryInfo> rosterEntries = lprRosterService.getLprRosterEntriesByLprRoster(rosterId, context);
            for (LprRosterEntryInfo rosterEntry : rosterEntries) {
                String lprId = rosterEntry.getLprId();
                lprIdToRosterEntryMap.put(lprId, rosterEntry);
            }
        }

        List<LprInfo> lprs = lprService.getLprsByPersonAndLui(studentId, courseOfferingId, context);
        LprInfo lpr = lprs.get(0); // TODO throw exception if null?
        LprRosterEntryInfo entry = lprIdToRosterEntryMap.get(lpr.getId());

        return assembleGradeRosterEntry(entry, context);
    }

    /**
     * This method ...
     *
     * @param gradeRosterEntry
     * @param gradeRosterId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public GradeRosterEntryInfo addEntrytoInterimRoster(
            @WebParam(name = "gradeRosterEntry") GradeRosterEntryInfo gradeRosterEntry,
            @WebParam(name = "gradeRosterId") String gradeRosterId, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * This method ...
     *
     * @param gradeRosterEntryId
     * @param gradeRosterId
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     */
    @Override
    public StatusInfo removeEntryFromInterimRoster(@WebParam(name = "gradeRosterEntryId") String gradeRosterEntryId,
            @WebParam(name = "gradeRosterId") String gradeRosterId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null; // TODO implement method.
    }

    /**
     * This method ...
     *
     * @param gradeRosterEntryId
     * @param assignedGradeKey
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     */
    @Override
    public boolean updateGrade(@WebParam(name = "gradeRosterEntryId") String gradeRosterEntryId,
            @WebParam(name = "assignedGrade") String assignedGradeKey, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        List<String> lprRosterEntryIds = new ArrayList();
        lprRosterEntryIds.add(gradeRosterEntryId);

        List<LprRosterEntryInfo> entryInfoList = lprRosterService.getLprRosterEntriesByIds(lprRosterEntryIds,context);
        if (entryInfoList.isEmpty()){
            throw new DoesNotExistException("Lpr Roster Entry not exists for the id " + gradeRosterEntryId);
        }

        LprRosterEntryInfo entryInfo = entryInfoList.get(0);
        List<String> lprIds = new ArrayList();
        lprIds.add(entryInfo.getLprId());

        List<LearningResultRecordInfo> learningResultRecordInfoList = lrrService.getLearningResultRecordsForLprIds(lprIds,context);

        //If an entry not exists for a student, create one
        if ((learningResultRecordInfoList == null || learningResultRecordInfoList.isEmpty()) && StringUtils.isNotBlank(assignedGradeKey)){
            LearningResultRecordInfo lrrInfo = constructLearningResultRecordInfo(entryInfo, assignedGradeKey);
            try{
                lrrService.createLearningResultRecord(lrrInfo,context);
                return true;
            }catch(AlreadyExistsException e){
                throw new OperationFailedException("Someone updated the grade. Please refresh to get the latest",e);
            }
        }

        for (LearningResultRecordInfo lrrInfo : learningResultRecordInfoList){
            if (StringUtils.equals(LrrServiceConstants.RESULT_RECORD_FINAL_GRADE_ASSIGNED_TYPE_KEY, lrrInfo.getTypeKey())){
                if (!StringUtils.equals(LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY,lrrInfo.getStateKey())){
                     if (StringUtils.isBlank(assignedGradeKey)){
                        StatusInfo status = lrrService.deleteLearningResultRecord(lrrInfo.getId(),context);
                        return status.getIsSuccess();
                    }else{
                        lrrInfo.setStateKey(LrrServiceConstants.RESULT_RECORD_SAVED_STATE_KEY);//If cancelled before
                        lrrInfo.setResultValueKey(assignedGradeKey);
                        lrrService.updateLearningResultRecord(lrrInfo.getId(),lrrInfo,context);
                        return true;
                    }
                }
            }
        }

        //Create a new LRR as there are no active LRRs found
        if (StringUtils.isNotBlank(assignedGradeKey)){
            LearningResultRecordInfo newLrr = constructLearningResultRecordInfo(entryInfo, assignedGradeKey);
            try{
                lrrService.createLearningResultRecord(newLrr,context);
                return true;
            }catch(AlreadyExistsException e){
                throw new OperationFailedException("Someone updated the grade. Please refresh to get the latest",e);
            }
        }

        return false;
    }

    private LearningResultRecordInfo constructLearningResultRecordInfo(LprRosterEntryInfo entryInfo, String assignedGradeKey){
        LearningResultRecordInfo lrrInfo = new LearningResultRecordInfo();
        lrrInfo.setId(UUIDHelper.genStringUUID());
        lrrInfo.setLprId(entryInfo.getLprId());
        lrrInfo.setName("LRR for " + entryInfo.getLprId());
        lrrInfo.setStateKey(LrrServiceConstants.RESULT_RECORD_SAVED_STATE_KEY);
        lrrInfo.setTypeKey(LrrServiceConstants.RESULT_RECORD_FINAL_GRADE_ASSIGNED_TYPE_KEY);

        //FIXME: Hardcoded for core slice
        List<String> resultSource = new ArrayList();
        resultSource.add("ResultSource-Sample1");
        lrrInfo.setResultSourceIds(resultSource);

        lrrInfo.setResultValueKey(assignedGradeKey);
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(GlobalVariables.getUserSession().getPrincipalId());
        meta.setCreateTime(new Date());
        lrrInfo.setMeta(meta);

        return lrrInfo;
    }

    /**
     * This method ...
     *
     * @param gradeRosterEntryId
     * @param assignedGradeKey
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *             authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     */
    @Override
    public boolean updateCredit(@WebParam(name = "gradeRosterEntryId") String gradeRosterEntryId,
            @WebParam(name = "creditsEarned") String assignedGradeKey, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return false; // TODO implement method.
    }

    @Override
    public List<GradeValuesGroupInfo> getGradeGroupsByKeyList(List<String> gradeGroupKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<ResultValuesGroupInfo> resultValuesGroupInfos = lrcService.getResultValuesGroupsByKeys(gradeGroupKeyList, context);
        List<GradeValuesGroupInfo> gradeValuesGroupInfos = new ArrayList<GradeValuesGroupInfo>();

        for(ResultValuesGroupInfo resultValuesGroupInfo : resultValuesGroupInfos){
            List<ResultValueInfo> resultValueInfos = lrcService.getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(),context);
            try {
                gradeValuesGroupInfos.add(gradeValuesGroupAssembler.assemble(resultValuesGroupInfo,resultValueInfos,context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return gradeValuesGroupInfos;
    }

    @Override
    public boolean updateNumberGrade(String gradeRosterEntryId, String numberGradeValue, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    private GradeRosterInfo assembleGradeRoster(LprRosterInfo lprRosterInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        Map<String, LprRosterEntryInfo> lprIdToRosterEntriesMap = new HashMap<String, LprRosterEntryInfo>();
        List<String> graderIds = new ArrayList<String>();
        List<String> lprRosterEntryIds = new ArrayList<String>();

        List<LprRosterEntryInfo> lprRosterEntries = lprRosterService.getLprRosterEntriesByLprRoster(lprRosterInfo.getId(), context);
        for (LprRosterEntryInfo lprRosterEntry : lprRosterEntries) {
            String lprId = lprRosterEntry.getLprId();
            lprIdToRosterEntriesMap.put(lprId, lprRosterEntry);
        }

        List<String> lprIds = new ArrayList<String>(lprIdToRosterEntriesMap.keySet());
        List<LprInfo> lprInfos = lprService.getLprsByIds(lprIds, context);
        for (LprInfo lprInfo : lprInfos) {
            String lprInfoType = lprInfo.getTypeKey();
            if (LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY.equals(lprInfoType)) {
                graderIds.add(lprInfo.getPersonId());
            } else if (LprServiceConstants.REGISTRANT_TYPE_KEY.equals(lprInfoType)) {
                LprRosterEntryInfo lprRosterEntryInfo = lprIdToRosterEntriesMap.get(lprInfo.getId());
                lprRosterEntryIds.add(lprRosterEntryInfo.getId());
            }
        }

        String courseOfferingId = null;
        List<String> activityOfferingIds = null;

        List<String> associatedLuiIds = lprRosterInfo.getAssociatedLuiIds();

        if (associatedLuiIds == null || associatedLuiIds.size() ==  0) {
            throw new OperationFailedException("LPR Roster does not contain any associated LUI IDs.");
        } else if (associatedLuiIds.size() ==  1) {
            CourseOfferingInfo courseOffering = courseOfferingService.getCourseOffering(associatedLuiIds.get(0), context);
            courseOfferingId = courseOffering.getId();
            activityOfferingIds = new ArrayList<String>();
        } else if (associatedLuiIds.size() >  1) {
            activityOfferingIds = associatedLuiIds;

            List<String> relatedLuis = luiService.getLuiIdsByLuiAndRelationType(activityOfferingIds.get(0), LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, context);
            if (relatedLuis == null || relatedLuis.size() != 1) {
                throw new OperationFailedException("The provided activity offering is not related to 1 and only 1 course offering.");
            }
            courseOfferingId = relatedLuis.get(0);
        }

        GradeRosterInfo gradeRosterInfo = gradeRosterAssembler.assemble(lprRosterInfo, lprRosterEntryIds, graderIds, courseOfferingId, activityOfferingIds, context);
        return gradeRosterInfo;
    }

    private List<GradeRosterEntryInfo> assembleGradeRosterEntries(List<LprRosterEntryInfo> entries, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //FIXME : This method needs some code refactoring

        final String STUDENT_ID = "studentId";
        final String ACTIVITY_OFFERING_ID = "activityOfferingId";
        final String ASSIGNED_GRADE = "assignedGrade";
        final String ADMINISTRATIVE_GRADE = "administrativeGrade";
        final String CREDITS_EARNED = "creditsEarned";
        final String CALCULATED_GRADE = "calculatedGrade";
        final String GRADING_OPTION = "gradingOption";

        List<GradeRosterEntryInfo> gradeRosterEntryInfos = new ArrayList<GradeRosterEntryInfo>();
        Map<LprRosterEntryInfo, Map<String, String>> entryKeysMap = new HashMap<LprRosterEntryInfo, Map<String, String>>();

        Map<String, LprRosterEntryInfo> lprIdToEntryMap = new HashMap<String, LprRosterEntryInfo>();
        for (LprRosterEntryInfo lprRosterEntryInfo : entries) {
            lprIdToEntryMap.put(lprRosterEntryInfo.getLprId(), lprRosterEntryInfo);
            entryKeysMap.put(lprRosterEntryInfo, new HashMap<String, String>());
        }

        List<String> lprIds = new ArrayList(lprIdToEntryMap.keySet());
        List<LprInfo> lprs = lprService.getLprsByIds(lprIds, context);

        Map<String,List> lprToGradeOptions = new HashMap();

        for (LprInfo lpr : lprs) {
            Map<String, String> entryAttributes = entryKeysMap.get(lprIdToEntryMap.get(lpr.getId()));
            entryAttributes.put(STUDENT_ID, lpr.getPersonId());
            entryAttributes.put(ACTIVITY_OFFERING_ID, lpr.getLuiId());

            CourseOfferingInfo courseOfferingInfo = courseOfferingService.getCourseOffering(lpr.getLuiId(),context);

            if (courseOfferingInfo == null){
                throw new OperationFailedException("Could not find course offering " + lpr.getLuiId());
            }
            List<String> gradingOptionIds = new ArrayList<String>(1);
            gradingOptionIds.add(courseOfferingInfo.getGradingOptionId());
            lprToGradeOptions.put(lpr.getId(),gradingOptionIds);

            lprToGradeOptions.put(lpr.getId(),courseOfferingInfo.getStudentRegistrationGradingOptions());

        }

        List<LearningResultRecordInfo> lrrs = lrrService.getLearningResultRecordsForLprIds(lprIds, context);
        Map<LearningResultRecordInfo, String> lrrToLprIdMap = new HashMap<LearningResultRecordInfo, String>();
        List<String> resultValueKeys = new ArrayList<String>();
        List<LearningResultRecordInfo> filteredLrrs = new ArrayList();

        for (LearningResultRecordInfo lrr : lrrs) {
            //Skip deleted LRR
            if (!StringUtils.equals(lrr.getStateKey(),LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY)){
                lrrToLprIdMap.put(lrr, lrr.getLprId());
                if (StringUtils.isNotBlank(lrr.getResultValueKey())){
                    resultValueKeys.add(lrr.getResultValueKey());
                    filteredLrrs.add(lrr);
                }
            }
        }

        List<ResultValueInfo> resultValues = lrcService.getResultValuesByKeys(resultValueKeys, context);
        for (int i = 0; i < resultValues.size(); i++) {
            LearningResultRecordInfo lrr = filteredLrrs.get(i);
            ResultValueInfo resultValue = resultValues.get(i);
            String lprId = lrrToLprIdMap.get(lrr);
            LprRosterEntryInfo entry = lprIdToEntryMap.get(lprId);
            Map<String, String> entryAttributes = entryKeysMap.get(entry);

            String resultValuetypeKey = resultValue.getTypeKey();
            String entryAttributesKey = null;
            if (LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE.equals(resultValuetypeKey)) {
                entryAttributesKey = ASSIGNED_GRADE;
            } else if (LrcServiceConstants.RESULT_SCALE_TYPE_KEY_ADMIN_GRADE.equals(resultValuetypeKey)) {
                entryAttributesKey = ADMINISTRATIVE_GRADE;
            } else if (LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CREDIT.equals(resultValuetypeKey)) {
                entryAttributesKey = CREDITS_EARNED;
            } else if (false) { //"".equals(resultValuetypeKey)) { // TODO need type value for calculated grade
                entryAttributesKey = CALCULATED_GRADE;
            }

            if (entryAttributesKey != null) {
                entryAttributes.put(entryAttributesKey, resultValue.getKey());
            }
        }

        for (LprRosterEntryInfo lprRosterEntry : entryKeysMap.keySet()) {
            Map<String, String> entryAttributes = entryKeysMap.get(lprRosterEntry);
            String studentId = entryAttributes.get(STUDENT_ID);
            String activityOfferingId = entryAttributes.get(ACTIVITY_OFFERING_ID);
            String assignedGradeKey = entryAttributes.get(ASSIGNED_GRADE);
            String calculatedGradeKey = entryAttributes.get(CALCULATED_GRADE);
            String administrativeGradeKey = entryAttributes.get(ADMINISTRATIVE_GRADE);
            String creditsEarnedKey = entryAttributes.get(CREDITS_EARNED);
            List gradingOptionKey = lprToGradeOptions.get(lprRosterEntry.getLprId());

            GradeRosterEntryInfo gradeRosterEntry = gradeRosterEntryAssembler.assemble(lprRosterEntry, studentId, activityOfferingId, assignedGradeKey, calculatedGradeKey, administrativeGradeKey, creditsEarnedKey, gradingOptionKey , context);
            gradeRosterEntryInfos.add(gradeRosterEntry);
        }

        return gradeRosterEntryInfos;
    }

    private GradeRosterEntryInfo assembleGradeRosterEntry(LprRosterEntryInfo lprRosterEntry, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        String studentId;
        String activityOfferingId;
        String assignedGradeKey = null;
        String administrativeGradeKey = null;
        String creditsEarnedKey = null;
        String calculatedGradeKey = null;
        List<String> gradingOptionKeys = new ArrayList<String>();

        String lprId = lprRosterEntry.getLprId();
        LprInfo lpr = lprService.getLpr(lprId, context);

        studentId = lpr.getPersonId();
        activityOfferingId = lpr.getLuiId();

        // TODO Get grading options from courseRegistration instead of courseOffering.
        CourseOfferingInfo courseOfferingInfo = courseOfferingService.getCourseOffering(lpr.getLuiId(),context);
        gradingOptionKeys.add(courseOfferingInfo.getGradingOptionId());

        List<LearningResultRecordInfo> lrrs = lrrService.getLearningResultRecordsForLpr(lprId);
        List<String> resultValueKeys = new ArrayList<String>();

        for (LearningResultRecordInfo lrr : lrrs) {
            //Skip deleted LRR
            if (StringUtils.isBlank(lrr.getResultValueKey()) ||
                    StringUtils.equals(lrr.getStateKey(),LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY)) {
                lrrs.remove(lrr);
            } else {
                resultValueKeys.add(lrr.getResultValueKey());
            }
        }

        List<ResultValueInfo> resultValues = lrcService.getResultValuesByKeys(resultValueKeys, context);
        for (int i = 0; i < resultValues.size(); i++) {
            ResultValueInfo resultValue = resultValues.get(i);
            String resultValuetypeKey = resultValue.getTypeKey();
            if (LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE.equals(resultValuetypeKey)) {
                assignedGradeKey = resultValue.getKey();
            } else if (LrcServiceConstants.RESULT_SCALE_TYPE_KEY_ADMIN_GRADE.equals(resultValuetypeKey)) {
                administrativeGradeKey = resultValue.getKey();
            } else if (LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CREDIT.equals(resultValuetypeKey)) {
                creditsEarnedKey = resultValue.getKey();
            } else if (false) { //"".equals(resultValuetypeKey)) { // TODO need type value for calculated grade
                calculatedGradeKey = resultValue.getKey();
            }
        }

        GradeRosterEntryInfo gradeRosterEntry = gradeRosterEntryAssembler.assemble(lprRosterEntry, studentId, activityOfferingId, assignedGradeKey, calculatedGradeKey, administrativeGradeKey, creditsEarnedKey, gradingOptionKeys , context);

        return gradeRosterEntry;
    }

    public LprRosterService getLprRosterService() {
        return lprRosterService;
    }

    public void setLprRosterService(LprRosterService lprRosterService) {
        this.lprRosterService = lprRosterService;
    }

    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public LearningResultRecordService getLrrService() {
        return lrrService;
    }

    public void setLrrService(LearningResultRecordService lrrService) {
        this.lrrService = lrrService;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public GradeRosterAssembler getGradeRosterAssembler() {
        return gradeRosterAssembler;
    }

    public void setGradeRosterAssembler(GradeRosterAssembler gradeRosterAssembler) {
        this.gradeRosterAssembler = gradeRosterAssembler;
    }

    public GradeRosterEntryAssembler getGradeRosterEntryAssembler() {
        return gradeRosterEntryAssembler;
    }

    public void setGradeRosterEntryAssembler(GradeRosterEntryAssembler gradeRosterEntryAssembler) {
        this.gradeRosterEntryAssembler = gradeRosterEntryAssembler;
    }

    public GradeValuesGroupAssembler getGradeValuesGroupAssembler() {
        return gradeValuesGroupAssembler;
    }

    public void setGradeValuesGroupAssembler(GradeValuesGroupAssembler gradeValuesGroupAssembler) {
        this.gradeValuesGroupAssembler = gradeValuesGroupAssembler;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
