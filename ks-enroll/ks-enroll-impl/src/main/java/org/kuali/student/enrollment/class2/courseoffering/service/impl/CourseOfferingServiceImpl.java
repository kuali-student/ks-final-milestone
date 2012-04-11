package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.FormatOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.RegistrationGroupAssembler;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.lum.course.dto.FormatInfo;

public class CourseOfferingServiceImpl implements CourseOfferingService {

    private LuiService luiService;
    private TypeService typeService;
    private CourseService courseService;
    private AcademicCalendarService acalService;
    private RegistrationGroupAssembler registrationGroupAssembler;
    private StateService stateService;
    private LuiPersonRelationService lprService;
    // TODO - remove when KSENROLL-247 is resolved
    private static final Integer TEMP_MAX_ENROLLMENT_DEFAULT = 50;

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }
    private CriteriaLookupService criteriaLookupService;

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
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

    public RegistrationGroupAssembler getRgAssembler() {
        return registrationGroupAssembler;
    }

    public void setRgAssembler(RegistrationGroupAssembler rgAssembler) {
        this.registrationGroupAssembler = rgAssembler;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public LuiPersonRelationService getLprService() {
        return lprService;
    }

    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(courseOfferingId, context);
        CourseOfferingInfo co = new CourseOfferingInfo();
        new CourseOfferingTransformer().lui2CourseOffering(lui, co, context);
        return co;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO: speed this up by calling the bulk lui methods?
        List<CourseOfferingInfo> results = new ArrayList<CourseOfferingInfo>();
        for (String id : courseOfferingIds) {
            CourseOfferingInfo co = getCourseOffering(id, context);
            results.add(co);
        }
        return results;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> luiIds = luiService.getLuiIdsByCluId(courseId, context);
        List<CourseOfferingInfo> results = new ArrayList<CourseOfferingInfo>();
        for (String luiId : luiIds) {
            CourseOfferingInfo co = getCourseOffering(luiId, context);
            results.add(co);
        }
        return results;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check the term is valid
        acalService.getTerm(termId, context);
        List<String> luiIds = luiService.getLuiIdsByAtpAndType(termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);
        List<CourseOfferingInfo> results = new ArrayList<CourseOfferingInfo>();

        for (String luiId : luiIds) {
            CourseOfferingInfo co = getCourseOffering(luiId, context);

            if (StringUtils.equals(co.getCourseId(), courseId)) {
                results.add(co);
            }
        }
        return results;
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        this.acalService.getTerm(termId, context); // check term exists
        List<String> luiIds = luiService.getLuiIdsByAtpAndType(termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);
        return luiIds;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> luiIds = luiService.getLuiIdsByAtpAndType(termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);
        List<String> results = new ArrayList<String>();

        for (String luiId : luiIds) {
            CourseOfferingInfo co = getCourseOffering(luiId, context);

            if (StringUtils.equals(co.getSubjectArea(), subjectArea)) {
                results.add(luiId);
            }
        }

        return results;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(String termId, String instructorId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprInfos = lprService.getLprsByPersonAndTypeForAtp(instructorId, termId, LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, context);
        List<CourseOfferingInfo> cos = new ArrayList<CourseOfferingInfo>();
        for (LuiPersonRelationInfo lprInfo : lprInfos) {
            cos.add(getCourseOffering(lprInfo.getLuiId(), context));
        }
        return cos;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<String> getCourseOfferingIdsByType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    public List<String> getCourseOfferingIdsByTermAndUnitContentOwner(String termKey, String unitOwnerId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    public List<CourseOfferingInfo> getCourseOfferingsBySoc(String s, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    public List<CourseOfferingInfo> getPublishedCourseOfferingsBySoc(String s, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey,
            CourseOfferingInfo coInfo, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate params
        if (!courseId.equals(coInfo.getCourseId())) {
            throw new InvalidParameterException(courseId + " does not match the corresponding value in the object " + coInfo.getCourseId());
        }
        if (!termId.equals(coInfo.getTermId())) {
            throw new InvalidParameterException(termId + " does not match the corresponding value in the object " + coInfo.getTermId());
        }
        if (!courseOfferingTypeKey.equals(coInfo.getTypeKey())) {
            throw new InvalidParameterException(courseOfferingTypeKey + " does not match the corresponding value in the object " + coInfo.getTypeKey());
        }
        // check the term and course
        TermInfo term = acalService.getTerm(termId, context);
        CourseInfo courseInfo = getCourse(courseId);
        // copy from cannonical
        CourseOfferingTransformer coTransformer = new CourseOfferingTransformer();
        coTransformer.copyFromCanonical(courseInfo, coInfo);
        // copy to lui
        LuiInfo lui = new LuiInfo();
        coTransformer.courseOffering2Lui(coInfo, lui, context);
        // create it
        lui = luiService.createLui(courseId, termId, lui.getTypeKey(), lui, context);
        // transform it back to a course offering
        CourseOfferingInfo createdCo = new CourseOfferingInfo();
        new CourseOfferingTransformer().lui2CourseOffering(lui, createdCo, context);
        return createdCo;
    }

    private CourseInfo getCourse(String courseId) throws DoesNotExistException, OperationFailedException {
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

    @Override
    @Transactional
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
            CourseOfferingInfo coInfo,
            ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        if (!courseOfferingId.equals(coInfo.getId())) {
            throw new InvalidParameterException(courseOfferingId + " does not match the corresponding value in the object " + coInfo.getId());
        }

        // get the backing lui
        LuiInfo lui = luiService.getLui(courseOfferingId, context);
        CourseOfferingTransformer transformer = new CourseOfferingTransformer();
        // copy fields and update            
        transformer.courseOffering2Lui(coInfo, lui, context);
        lui = luiService.updateLui(courseOfferingId, lui, context);
        // convert back to co and return
        CourseOfferingInfo co = new CourseOfferingInfo();
        transformer.lui2CourseOffering(lui, co, context);
        return co;
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        CourseOfferingInfo co = this.getCourseOffering(courseOfferingId, context);
        CourseInfo course = this.getCourse(co.getCourseId());
        new CourseOfferingTransformer().copyFromCanonical(course, co);
        co = this.updateCourseOffering(courseOfferingId, co, context);
        return co;
    }

    private void processFinalExam(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        String cluId = co.getCourseId();
        String atpId = co.getTermId();
        LuiInfo finalExam = new LuiInfo();
        finalExam.setCluId(cluId);
        finalExam.setAtpId(atpId);
        finalExam.setStateKey(co.getStateKey());
        // TODO: not sure what type
        finalExam.setTypeKey("kuali.lui.type.course.finalExam");
        // TODO: what else inherit or fill into finalExam?
        LuiInfo created;
        try {
            created = luiService.createLui(cluId, atpId, finalExam.getTypeKey(), finalExam, context);
        } catch (AlreadyExistsException e1) {
            throw new OperationFailedException("AlreadyExistsException when createLui. cluId: " + cluId + ", atpId: " + atpId);
        }

        if (created != null) {
            try {
                createLuiLuiRelationForRegGroups(co.getId(), created.getId(), LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, context);
            } catch (AlreadyExistsException e1) {
                throw new OperationFailedException();
            }
        }
    }

    private void processInstructors(String courseOfferingId, List<OfferingInstructorInfo> instructors, String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {

        List<String> currrentInstructors = lprService.getPersonIdsByLuiAndTypeAndState(courseOfferingId, LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY,
                LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY, context);

        if (instructors != null && !instructors.isEmpty()) {
            for (OfferingInstructorInfo instructor : instructors) {
                try {
                    if (currrentInstructors.contains(instructor.getPersonId())) {
                        LuiPersonRelationInfo existingLpr = getLpr(instructor.getPersonId(), courseOfferingId, context);
                        if (existingLpr != null) {
                            existingLpr.setCommitmentPercent(instructor.getPercentageEffort());
                            lprService.updateLpr(existingLpr.getId(), existingLpr, context);
                            currrentInstructors.remove(instructor.getPersonId());
                        }
                    } else {
                        lprService.createLpr(instructor.getPersonId(), courseOfferingId, instructor.getTypeKey(), getNewLpr(instructor, courseOfferingId), context);
                    }
                } catch (AlreadyExistsException e) {
                    throw new OperationFailedException();
                } catch (DisabledIdentifierException e) {
                    throw new OperationFailedException();
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException();
                }
            }
        }

        if (currrentInstructors != null && currrentInstructors.size() > 0) {
            if (atpId != null) {
                for (String instructorId : currrentInstructors) {
                    LuiPersonRelationInfo lpr = getLpr(instructorId, courseOfferingId, context);
                    if (lpr != null) {
                        lprService.deleteLpr(lpr.getId(), context);
                    }
                }
            }
        }
    }

    private void processFinalRoster(String courseOfferingId, Integer maxEnrollment, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException {
        List<LprRosterInfo> rosters = lprService.getLprRostersByLuiAndType(courseOfferingId, LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY, context);
        if (rosters != null && !rosters.isEmpty()) {
            for (LprRosterInfo roster : rosters) {
                roster.setMaximumCapacity(null != maxEnrollment ? maxEnrollment : TEMP_MAX_ENROLLMENT_DEFAULT);
                try {
                    lprService.updateLprRoster(roster.getId(), roster, context);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException();
                }
            }
        }
    }

    private LuiPersonRelationInfo getNewLpr(OfferingInstructorInfo instructor, String courseOfferingId) {
        LuiPersonRelationInfo lpr = new LuiPersonRelationInfo();
        lpr.setPersonId(instructor.getPersonId());
        lpr.setCommitmentPercent(instructor.getPercentageEffort());
        lpr.setId(UUIDHelper.genStringUUID());
        lpr.setLuiId(courseOfferingId);
        lpr.setTypeKey(instructor.getTypeKey());
        lpr.setStateKey(instructor.getStateKey());
        return lpr;
    }

    private LuiPersonRelationInfo getLpr(String instructor, String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiPersonRelationInfo lpr = null;
        try {
            List<LuiPersonRelationInfo> lprs = lprService.getLprsByPersonAndLui(instructor, courseOfferingId, context);

            if (lprs != null && !lprs.isEmpty()) {
                for (LuiPersonRelationInfo lpri : lprs) {
                    if (lpri.getTypeKey().equals(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                        lpr = lpri;
                    }
                }
            }
        } catch (DisabledIdentifierException e) {
            throw new OperationFailedException();
        }

        return lpr;
    }

    @Override
    @Transactional
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            return luiService.deleteLui(courseOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting course offering", e);
        }
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(formatOfferingId, context);
        FormatOfferingInfo fo = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, fo);
        LuiInfo coLui = this.findCourseOfferingLui(lui.getId(), context);
        fo.setCourseOfferingId(coLui.getId());
        return fo;
    }

    private LuiInfo findCourseOfferingLui(String formatOfferingId, ContextInfo context)
            throws OperationFailedException {
        List<LuiInfo> rels;
        try {
            rels = luiService.getLuisByRelation(formatOfferingId,
                    LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, context);
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        for (LuiInfo lui : rels) {
            if (lui.getTypeKey().equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)) {
                return lui;
            }
        }
        throw new OperationFailedException("format offering is not associated with a course offering " + formatOfferingId + " among " + rels.size());
    }

    private LuiInfo findFormatOfferingLui(String activityOfferingId, ContextInfo context)
            throws OperationFailedException {
        List<LuiInfo> rels;
        try {
            rels = luiService.getLuisByRelation(activityOfferingId, LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, context);
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        for (LuiInfo lui : rels) {
            if (lui.getTypeKey().equals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY)) {
                return lui;
            }
        }
        throw new OperationFailedException("format offering is not associated with a course offering " + activityOfferingId + " among " + rels.size());
    }

    @Override
    public List<FormatOfferingInfo> getFormatOfferingByCourseOfferingId(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {

        List<LuiLuiRelationInfo> formatOfferingRelations = luiService.getLuiLuiRelationsByLui(formatOfferingId, context);
        for (LuiLuiRelationInfo luiLuiRelation : formatOfferingRelations) {
            luiService.deleteLuiLuiRelation(luiLuiRelation.getId(), context);
        }
        return luiService.deleteLui(formatOfferingId, context);

    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        // get the existing
        LuiInfo lui = this.luiService.getLui(formatOfferingId, context);
        // transform and update
        new FormatOfferingTransformer().format2Lui(formatOfferingInfo, lui);
        lui = luiService.updateLui(formatOfferingId, lui, context);
        // rebuild the fo to return it
        FormatOfferingInfo fo = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, fo);
        LuiInfo coLui = this.findCourseOfferingLui(formatOfferingId, context);
        fo.setCourseOfferingId(coLui.getId());
        return fo;
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId,
            String formatId, String formatOfferingType, FormatOfferingInfo foInfo,
            ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // validate params
        if (!courseOfferingId.equals(foInfo.getCourseOfferingId())) {
            throw new InvalidParameterException(courseOfferingId + " does not match the corresponding value in the object " + foInfo.getCourseOfferingId());
        }
        if (!formatId.equals(foInfo.getFormatId())) {
            throw new InvalidParameterException(formatId + " does not match the corresponding value in the object " + foInfo.getFormatId());
        }
        if (!formatOfferingType.equals(foInfo.getTypeKey())) {
            throw new InvalidParameterException(formatOfferingType + " does not match the corresponding value in the object " + foInfo.getTypeKey());
        }
        // get the course offering
        CourseOfferingInfo co = this.getCourseOffering(courseOfferingId, context);
        if (foInfo.getTermId() != null) {
            if (co.getTermId().equals(foInfo.getTermId())) {
                throw new InvalidParameterException(foInfo.getTermId() + " term in the format offering does not match the one in the course offering " + co.getTermId());
            }
        }
        foInfo.setTermId(co.getTermId());

        // get formatId out of the course
        CourseInfo course = this.getCourse(co.getCourseId()); // make sure it exists
        FormatInfo format = null;
        for (FormatInfo info : course.getFormats()) {
            if (info.getId().equals(formatId)) {
                format = info;
                break;
            }
        }
        if (format == null) {
            throw new DoesNotExistException(formatId);
        }
        // copy to lui
        LuiInfo lui = new LuiInfo();
        new FormatOfferingTransformer().format2Lui(foInfo, lui);


        try {
            lui = luiService.createLui(lui.getCluId(), lui.getAtpId(), lui.getTypeKey(), lui, context);
        } catch (Exception aee) {
            throw new OperationFailedException("Unexpected", aee);
        }
        // now connect it to the course offering lui
        LuiLuiRelationInfo rel = new LuiLuiRelationInfo();
        rel.setLuiId(courseOfferingId);
        rel.setRelatedLuiId(lui.getId());
        rel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        rel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY);
        rel.setEffectiveDate(new Date());
        try {
            rel = luiService.createLuiLuiRelation(rel.getLuiId(), rel.getRelatedLuiId(), rel.getTypeKey(), rel, context);
        } catch (Exception aee) {
            throw new OperationFailedException("Unexpected", aee);
        }
        // reubild to return it
        FormatOfferingInfo formatOffering = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, formatOffering);
        formatOffering.setCourseOfferingId(rel.getLuiId());
        return formatOffering;
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return typeService.getAllowedTypesForType(activityTypeKey, context);
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(activityOfferingId, context);
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        new ActivityOfferingTransformer().lui2Activity(ao, lui);
        LuiInfo foLui = this.findFormatOfferingLui(activityOfferingId, context);
        ao.setFormatOfferingId(foLui.getId());
        return ao;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> strings, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingInfo> list = new ArrayList<ActivityOfferingInfo>();
        List<FormatOfferingInfo> formats = this.getFormatOfferingByCourseOfferingId(courseOfferingId, context);
        for (FormatOfferingInfo fo : formats) {
            List<ActivityOfferingInfo> activities = this.getActivityOfferingsByFormatOffering(courseOfferingId, context);
            list.addAll(activities);
        }
        return list;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOfferingWithoutRegGroup(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
            String activityId,
            String activityOfferingTypeKey,
            ActivityOfferingInfo aoInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate params
        if (!formatOfferingId.equals(aoInfo.getFormatOfferingId())) {
            throw new InvalidParameterException(formatOfferingId + " does not match the corresponding value in the object " + aoInfo.getFormatOfferingId());
        }
        if (!activityId.equals(aoInfo.getActivityId())) {
            throw new InvalidParameterException(activityId + " does not match the corresponding value in the object " + aoInfo.getActivityId());
        }
        if (!activityOfferingTypeKey.equals(aoInfo.getTypeKey())) {
            throw new InvalidParameterException(activityOfferingTypeKey + " does not match the corresponding value in the object " + aoInfo.getTypeKey());
        }
        // get the required objects checking they exist
        FormatOfferingInfo fo = this.getFormatOffering(formatOfferingId, context);
        CourseOfferingInfo co = this.getCourseOffering(fo.getCourseOfferingId(), context);
        if (aoInfo.getTermId() != null) {
            if (!aoInfo.getTermId().equals(fo.getTermId())) {
                throw new InvalidParameterException(aoInfo.getTermId() + " term in the activity offering does not match the one in the format offering " + fo.getTermId());
            }
        }
        aoInfo.setTermId(fo.getTermId());
        // copy to the lui
        LuiInfo lui = new LuiInfo();
        new ActivityOfferingTransformer().activity2Lui(aoInfo, lui);
        try {
            lui = luiService.createLui(lui.getCluId(), lui.getAtpId(), lui.getTypeKey(), lui, context);
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        // now build the lui lui relation
        LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
        luiRel.setLuiId(formatOfferingId);
        luiRel.setRelatedLuiId(lui.getId());
        luiRel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY);
        luiRel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        luiRel.setEffectiveDate(new Date());
        try {
            luiRel = luiService.createLuiLuiRelation(luiRel.getLuiId(), luiRel.getRelatedLuiId(), luiRel.getTypeKey(), luiRel, context);
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        new ActivityOfferingTransformer().lui2Activity(ao, lui);
        ao.setFormatOfferingId(luiRel.getLuiId());
        return ao;

    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferingsForFormatOffering(String formatOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // validate params
        if (!activityOfferingId.equals(activityOfferingInfo.getId())) {
            throw new InvalidParameterException(activityOfferingId + " does not match the corresponding value in the object " + activityOfferingInfo.getId());
        }
        // get it
        LuiInfo lui = luiService.getLui(activityOfferingId, context);
        // TODO: check that the lui being updated is an activity not another kind of lui
        // copy to lui
        new ActivityOfferingTransformer().activity2Lui(activityOfferingInfo, lui);
        // update lui
        lui = luiService.updateLui(activityOfferingId, lui, context);
        // rebuild activity to return it
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        new ActivityOfferingTransformer().lui2Activity(ao, lui);
        LuiInfo foLui = this.findFormatOfferingLui(lui.getId(), context);
        ao.setFormatOfferingId(foLui.getId());
        return ao;
    }

    @Override
    @Transactional
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO: check that the lui being deleted is an activity not another kind of lui
        try {
            return luiService.deleteLui(activityOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting dependent objects", e);
        }
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(registrationGroupId, context);

        return registrationGroupAssembler.assemble(lui, context);

    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupsIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();

        for (String registrationGroupId : registrationGroupsIds) {

            regGroups.add(registrationGroupAssembler.assemble(luiService.getLui(registrationGroupId, contextInfo), contextInfo));
        }

        return regGroups;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO: implement LuiService.getLuiIdsByRelation and call it instead
        List<RegistrationGroupInfo> rgs = new ArrayList<RegistrationGroupInfo>();
        List<String> rgIds = new ArrayList<String>();
        List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(courseOfferingId, context);
        if (rels != null && !rels.isEmpty()) {
            for (LuiLuiRelationInfo rel : rels) {
                if (rel.getRelatedLuiId().equals(courseOfferingId)) {
                    if (rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY)) {
                        String luiId = rel.getLuiId();
                        LuiInfo lui = luiService.getLui(luiId, context);
                        if (lui != null && lui.getTypeKey().equals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY) && !rgIds.contains(luiId)) {
                            rgIds.add(luiId);
                            rgs.add(getRegistrationGroup(luiId, context));
                        }
                    }
                }
            }
        }

        return rgs;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(List<String> activityOfferingIds,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public RegistrationGroupInfo createRegistrationGroup(String registrationTypeKey, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        registrationGroupInfo.setTypeKey(registrationTypeKey);
        LuiInfo lui = registrationGroupAssembler.disassemble(registrationGroupInfo, context);
        LuiInfo created;
        try {
            created = luiService.createLui(registrationGroupInfo.getFormatId(), registrationGroupInfo.getTermId(), lui.getTypeKey(), lui, context);
            for (String activityOfferingId : registrationGroupInfo.getActivityOfferingIds()) {

                LuiLuiRelationInfo activtyRegGroupRelation = new LuiLuiRelationInfo();
                activtyRegGroupRelation.setEffectiveDate(new Date());

                luiService.createLuiLuiRelation(activityOfferingId, lui.getId(), LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, activtyRegGroupRelation, context);
            }
        } catch (AlreadyExistsException e) {
            throw new OperationFailedException(e.getMessage());
        } catch (CircularRelationshipException cre) {
            throw new OperationFailedException(cre.getMessage());

        }


        return registrationGroupAssembler.assemble(created, context);
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    private String getTermkeyByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        String termId = null;

        LuiInfo co = luiService.getLui(courseOfferingId, context);
        if (co != null) {
            termId = co.getAtpId();
        }

        return termId;
    }

    private void processRelationsForRegGroup(String courseOfferingId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        createLuiLuiRelationForRegGroups(registrationGroupInfo.getId(), courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, context);

        if (registrationGroupInfo.getActivityOfferingIds() != null && !registrationGroupInfo.getActivityOfferingIds().isEmpty()) {
            for (String activityOfferingId : registrationGroupInfo.getActivityOfferingIds()) {
                createLuiLuiRelationForRegGroups(registrationGroupInfo.getId(), activityOfferingId, LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, context);
            }
        }
    }

    @Override
    @Transactional
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        Set<String> existingRelatedLuiIds = new HashSet<String>();
        Set<String> newRelatedLuiIds = new HashSet<String>(registrationGroupInfo.getActivityOfferingIds());
        newRelatedLuiIds.add(registrationGroupInfo.getCourseOfferingId());

        // Delete relations for removed Activity Offerings or Course Offering
        List<LuiLuiRelationInfo> llrs = luiService.getLuiLuiRelationsByLui(registrationGroupId, context);
        for (LuiLuiRelationInfo llr : llrs) {
            if (registrationGroupId.equals(llr.getLuiId()) && LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY.equals(llr.getTypeKey())) {
                String relatedLuiId = llr.getRelatedLuiId();
                existingRelatedLuiIds.add(relatedLuiId);
                if (!newRelatedLuiIds.contains(relatedLuiId)) {
                    luiService.deleteLuiLuiRelation(llr.getId(), context);
                }
            }
        }

        // Create relations for added Activity Offerings or Course Offering
        for (String luiId : newRelatedLuiIds) {
            if (!existingRelatedLuiIds.contains(luiId)) {
                try {
                    createLuiLuiRelationForRegGroups(registrationGroupId, luiId, LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, context);
                } catch (AlreadyExistsException e) {
                    throw new OperationFailedException("Could not create Lui-Lui Relation '" + registrationGroupId + "'-'" + luiId + "'", e);
                }
            }
        }

        LuiInfo regGroupLui = registrationGroupAssembler.disassemble(registrationGroupInfo, context);
        LuiInfo updatedRegGroupLui = luiService.updateLui(regGroupLui.getId(), regGroupLui, context);
        return registrationGroupAssembler.assemble(updatedRegGroupLui, context);

    }

    @Override
    @Transactional
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            return luiService.deleteLui(registrationGroupId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Could not delete LUI '" + registrationGroupId + "'", e);
        }
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(String registrationGroupTemplateId,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(String registrationGroupTemplateId,
            RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo deleteRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForRegGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<LuiEntity> results = criteriaLookupService.lookup(LuiEntity.class, criteria);
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>(results.getResults().size());
        for (LuiEntity lui : results.getResults()) {
            try {
                CourseOfferingInfo co = this.getCourseOffering(lui.getId(), context);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException(lui.getId(), ex);
            }
        }
        return courseOfferings;
    }

    private void createLuiLuiRelationForRegGroups(String luiId, String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
            luiRel.setLuiId(luiId);
            luiRel.setRelatedLuiId(relatedLuiId);
            luiRel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY);
            luiRel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
            luiRel.setEffectiveDate(new Date());
            luiService.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiRel, context);
        } catch (CircularRelationshipException e) {
            throw new OperationFailedException();
        } catch (DoesNotExistException e) {
            throw new OperationFailedException();
        }
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> searchForSeatpoolDefintionIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }
}
