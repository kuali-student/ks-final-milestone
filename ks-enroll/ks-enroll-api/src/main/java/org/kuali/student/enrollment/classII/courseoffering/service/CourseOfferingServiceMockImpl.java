package org.kuali.student.enrollment.classII.courseoffering.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.R1ToR2CopyHelper;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.type.dto.TypeInfo;

public class CourseOfferingServiceMockImpl implements CourseOfferingService {

    private static Map<String, CourseOfferingInfo> courseOfferingCache = new HashMap<String, CourseOfferingInfo>();
    private static Map<String, ActivityOfferingInfo> activityOfferingCache = new HashMap<String, ActivityOfferingInfo>();
    private static Map<String, List<ActivityOfferingInfo>> courseToActivityOfferingCache = new HashMap<String, List<ActivityOfferingInfo>>();
    private static Map<String, List<RegistrationGroupInfo>> courseToRegGroupCache = new HashMap<String, List<RegistrationGroupInfo>>();
    private static Map<String, RegistrationGroupInfo> registrationGroupCache = new HashMap<String, RegistrationGroupInfo>();
    private static Map<String, CourseInfo> courseCache = new HashMap<String, CourseInfo>();
    private static Map<String, TypeInfo> typesCache = new HashMap<String, TypeInfo>();
    private static Map<String, SeatPoolDefinitionInfo> seatPoolDefinitionCache = new HashMap<String, SeatPoolDefinitionInfo>();
    private CourseService courseService;

    public CourseOfferingServiceMockImpl() {
        loadCaches();
    }

    private void loadCaches() {
        CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
        courseOfferingInfo.setCourseId("101");
        courseOfferingInfo.setId("PHYS121");
        courseOfferingInfo.setCourseOfferingCode("PHYS121");
        courseOfferingInfo.setSubjectArea("PHYS");
        courseOfferingInfo.setCourseTitle("Fundamentals of Physics I");
        org.kuali.student.r2.common.dto.RichTextInfo desc = new org.kuali.student.r2.common.dto.RichTextInfo();
        desc.setPlain("Fundamentals of Physics");
        courseOfferingInfo.setDescr(desc);
        courseOfferingInfo.setCourseNumberSuffix("PHY");
        courseOfferingInfo.setHasFinalExam(Boolean.TRUE);
        courseOfferingInfo.setIsHonorsOffering(Boolean.TRUE);
        courseOfferingInfo.setTermId("201108");

        courseOfferingCache.put(courseOfferingInfo.getId(), courseOfferingInfo);

        CourseOfferingInfo courseOfferingInfo1 = new CourseOfferingInfo();
        courseOfferingInfo1.setCourseId("102");
        courseOfferingInfo1.setId("PHYS122");
        courseOfferingInfo1.setCourseOfferingCode("PHYS122");
        courseOfferingInfo.setSubjectArea("PHYS");
        courseOfferingInfo1.setCourseTitle("Fundamentals of Physics II");
        desc = new org.kuali.student.r2.common.dto.RichTextInfo();
        desc.setPlain("Fundamentals of Physics");
        courseOfferingInfo1.setDescr(desc);
        courseOfferingInfo1.setCourseNumberSuffix("PHY");
        courseOfferingInfo1.setHasFinalExam(Boolean.TRUE);
        courseOfferingInfo1.setIsHonorsOffering(Boolean.TRUE);
        courseOfferingInfo1.setTermId("201108");

        courseOfferingCache.put(courseOfferingInfo1.getId(), courseOfferingInfo1);

        CourseOfferingInfo courseOfferingInfo2 = new CourseOfferingInfo();
        courseOfferingInfo2.setCourseId("103");
        courseOfferingInfo2.setId("PHYS123");
        courseOfferingInfo2.setCourseOfferingCode("PHYS123");
        courseOfferingInfo.setSubjectArea("PHYS");
        courseOfferingInfo2.setCourseTitle("Advanced Physics I");
        desc = new org.kuali.student.r2.common.dto.RichTextInfo();
        desc.setPlain("Advanced Physics");
        courseOfferingInfo2.setDescr(desc);
        courseOfferingInfo2.setCourseNumberSuffix("PHY");
        courseOfferingInfo2.setHasFinalExam(Boolean.TRUE);
        courseOfferingInfo2.setIsHonorsOffering(Boolean.TRUE);
        courseOfferingInfo2.setTermId("201108");

        courseOfferingCache.put(courseOfferingInfo2.getId(), courseOfferingInfo2);
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return courseOfferingCache.get(courseOfferingId);

    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(String courseId, String termId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getCourseId().equals(courseId) && courseOffering.getTermId().equals(termId)) {
                courseOfferings.add(courseOffering);
            }
        }
        return courseOfferings;
    }

    @Override
    public List<String> getCourseOfferingIdsForTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<String> courseOfferings = new ArrayList<String>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getTermId().equals(termId)) {
                courseOfferings.add(courseOffering.getId());
            }
        }
        return courseOfferings;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<String> courseOfferings = new ArrayList<String>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getSubjectArea().equals(subjectArea)) {
                courseOfferings.add(courseOffering.getId());
            }
        }
        return courseOfferings;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitContentOwner(String termId, String unitOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<String> courseOfferings = new ArrayList<String>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getUnitsContentOwner().contains(unitOwnerId)
                    && courseOffering.getTermId().equals(termId)) {
                courseOfferings.add(courseOffering.getId());
            }
        }
        return courseOfferings;
    }

    @Override
    public CourseOfferingInfo createCourseOfferingFromCanonical(String courseid, String termId,
            List<String> formatIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
        courseOfferingInfo.setCourseId(courseid);
        courseOfferingInfo.setTermId(termId);
        courseOfferingInfo.setId(String.valueOf(Math.random()));
        CourseInfo courseInfo = courseCache.get(courseid);
        courseOfferingInfo.setCourseId(courseInfo.getId());
        courseOfferingInfo.setCourseNumberSuffix(courseInfo.getCourseNumberSuffix());
        courseOfferingInfo.setCourseTitle(courseInfo.getCourseTitle());
        // TODO: worry about which credit option to apply.
        if (courseInfo.getCreditOptions() == null) {
            courseOfferingInfo.setCreditOptions(null);
        } else if (courseInfo.getCreditOptions().isEmpty()) {
            courseOfferingInfo.setCreditOptions(null);
        } else {
            courseOfferingInfo.setCreditOptions(new R1ToR2CopyHelper().copyResultValuesGroup(courseInfo.getCreditOptions().get(0)));
        }
        courseOfferingInfo.setDescr(new R1ToR2CopyHelper().copyRichText(courseInfo.getDescr()));
        courseOfferingInfo.setExpenditure(new R1ToR2CopyHelper().copyCourseExpenditure(courseInfo.getExpenditure()));
        courseOfferingInfo.setFees(new R1ToR2CopyHelper().copyCourseFeeList(courseInfo.getFees()));
        //courseOfferingInfo.setFormats(canicalCourseo.getFormats());

        courseOfferingCache.put(courseOfferingInfo.getId(), courseOfferingInfo);

        return courseOfferingInfo;
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        courseOfferingCache.put(courseOfferingId, courseOfferingInfo);
        return courseOfferingInfo;
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        CourseOfferingInfo courseOfferingInfo = courseOfferingCache.get(courseOfferingId);
        CourseInfo courseInfo = courseCache.get(courseOfferingInfo.getCourseId());
        courseOfferingInfo.setCourseOfferingCode(courseInfo.getCode());
        courseOfferingInfo.setCourseId(courseInfo.getId());
        courseOfferingInfo.setCourseNumberSuffix(courseInfo.getCourseNumberSuffix());
        courseOfferingInfo.setCourseTitle(courseInfo.getCourseTitle());
        // TODO: worry about which credit option to apply.
        if (courseInfo.getCreditOptions() == null) {
            courseOfferingInfo.setCreditOptions(null);
        } else if (courseInfo.getCreditOptions().isEmpty()) {
            courseOfferingInfo.setCreditOptions(null);
        } else {
            courseOfferingInfo.setCreditOptions(new R1ToR2CopyHelper().copyResultValuesGroup(courseInfo.getCreditOptions().get(0)));
        }
        courseOfferingInfo.setDescr(new R1ToR2CopyHelper().copyRichText(courseInfo.getDescr()));
        courseOfferingInfo.setExpenditure(new R1ToR2CopyHelper().copyCourseExpenditure(courseInfo.getExpenditure()));
        courseOfferingInfo.setFees(new R1ToR2CopyHelper().copyCourseFeeList(courseInfo.getFees()));
        //courseOfferingInfo.setFormats(courseInfo.getFormats());
        return courseOfferingInfo;
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(courseOfferingCache.remove(courseOfferingId) != null);
        return status;
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        TypeInfo typeInfo = typesCache.get(activityOfferingTypeKey);
        return typeInfo;
    }

    @Override
    public List<TypeInfo> getAllActivityOfferingTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<TypeInfo>();
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return new ArrayList<TypeInfo>();
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return activityOfferingCache.get(activityOfferingId);
    }

    @Override
    public List<ActivityOfferingInfo> getActivitiesForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return courseToActivityOfferingCache.get(courseOfferingId);
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(List<String> courseOfferingIdList,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        activityOfferingInfo.setId(String.valueOf(Math.random()));
        activityOfferingCache.put(activityOfferingInfo.getId(), activityOfferingInfo);

        for (String courseOfferingId : courseOfferingIdList) {
            List<ActivityOfferingInfo> activities = courseToActivityOfferingCache.get(courseOfferingId);
            if (null == activities) {
                activities = new ArrayList<ActivityOfferingInfo>();
            }

            activities.add(activityOfferingInfo);

            courseToActivityOfferingCache.put(courseOfferingId, activities);
        }

        return activityOfferingInfo;
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        activityOfferingCache.put(activityOfferingId, activityOfferingInfo);
        return activityOfferingInfo;
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(activityOfferingCache.remove(activityOfferingId) != null);
        return status;
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo activity = activityOfferingCache.get(activityOfferingId);
        // approximate number of weeks, in real impl calculate class weeks from
        // TermInfo
        return activity.getWeeklyInclassContactHours() * 16;

    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo activity = activityOfferingCache.get(activityOfferingId);

        // approximate number of weeks, in real impl calculate class weeks from
        // TermInfo
        return activity.getWeeklyOutofclassContactHours() * 16;
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return calculateInClassContactHoursForTerm(activityOfferingId, context)
                + calculateOutofClassContactHoursForTerm(activityOfferingId, context);
    }

    @Override
    public List<ActivityOfferingInfo> copyActivityOffering(String activityOfferingId, Integer numberOfCopies,
            String copyContextTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingInfo> activityOfferings = new ArrayList<ActivityOfferingInfo>(numberOfCopies);
        // TODO - implement activityOfferingInf & activityOffering
        ActivityOfferingInfo activityOfferingInf = activityOfferingCache.get(activityOfferingId);
        for (ActivityOfferingInfo activityOffering : activityOfferings) {
            // activityOffering = new ActivityOfferingInfo(activityOfferingInf);
        }

        return activityOfferings;
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return registrationGroupCache.get(registrationGroupId);
    }

    @Override
    public List<RegistrationGroupInfo> getRegGroupsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return courseToRegGroupCache.get(courseOfferingId);
    }

    @Override
    public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(String courseOfferingId, String formatTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        CourseOfferingInfo courseOffering = courseOfferingCache.get(courseOfferingId);
        List<FormatInfo> formatsInfo = new ArrayList<FormatInfo>();

        List<RegistrationGroupInfo> regGroups = courseToRegGroupCache.get(courseOfferingId);

        if (null == regGroups) {
            regGroups = new ArrayList<RegistrationGroupInfo>();
        }

        List<RegistrationGroupInfo> result = new ArrayList<RegistrationGroupInfo>();

        try {
            formatsInfo = courseService.getCourseFormats(courseOffering.getCourseId());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new OperationFailedException(courseOffering.getCourseId(), e);
        } catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
            throw new OperationFailedException(courseOffering.getCourseId(), e);
        } catch (org.kuali.student.common.exceptions.MissingParameterException e) {
            throw new OperationFailedException(courseOffering.getCourseId(), e);
        } catch (org.kuali.student.common.exceptions.OperationFailedException e) {
            throw new OperationFailedException(courseOffering.getCourseId(), e);
        } catch (org.kuali.student.common.exceptions.PermissionDeniedException e) {
            throw new OperationFailedException(courseOffering.getCourseId(), e);
        }

        for (FormatInfo format : formatsInfo) {

            for (RegistrationGroupInfo regGroup : regGroups) {
                if (format.getId().equals(regGroup.getFormatId()) && format.getType().equals(formatTypeKey)) {
                    result.add(regGroup);
                }
            }
        }
        return result;
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String courseOfferingId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        registrationGroupInfo.setId(String.valueOf(Math.random()));
        registrationGroupCache.put(registrationGroupInfo.getId(), registrationGroupInfo);

        List<RegistrationGroupInfo> regGroups = courseToRegGroupCache.get(courseOfferingId);

        if (null == regGroups) {
            regGroups = new ArrayList<RegistrationGroupInfo>();
        }

        regGroups.add(registrationGroupInfo);

        courseToRegGroupCache.put(courseOfferingId, regGroups);

        return registrationGroupInfo;
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        registrationGroupCache.put(registrationGroupId, registrationGroupInfo);
        return registrationGroupInfo;
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(registrationGroupCache.remove(registrationGroupId) != null);

        return status;
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return seatPoolDefinitionCache.get(seatPoolDefinitionId);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<SeatPoolDefinitionInfo> seatPoolsForCourseOff = new ArrayList<SeatPoolDefinitionInfo>();
        for (SeatPoolDefinitionInfo seatPoolInfo : seatPoolDefinitionCache.values()) {
            if (seatPoolInfo.getCourseOfferingId().equals(courseOfferingId)) {
                seatPoolsForCourseOff.add(seatPoolInfo);
            }
        }
        return seatPoolsForCourseOff;
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<SeatPoolDefinitionInfo> seatPoolsForRegGroup = new ArrayList<SeatPoolDefinitionInfo>();
        for (SeatPoolDefinitionInfo seatPoolInfo : seatPoolDefinitionCache.values()) {
            if (seatPoolInfo.getRegistrationGroupIds().contains(registrationGroupId)) {
                seatPoolsForRegGroup.add(seatPoolInfo);
            }
        }
        return seatPoolsForRegGroup;
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        seatPoolDefinitionInfo.setId(String.valueOf(Math.random()));
        seatPoolDefinitionCache.put(seatPoolDefinitionInfo.getId(), seatPoolDefinitionInfo);
        return seatPoolDefinitionInfo;
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        seatPoolDefinitionCache.put(seatPoolDefinitionId, seatPoolDefinitionInfo);
        return seatPoolDefinitionInfo;
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(seatPoolDefinitionCache.remove(seatPoolDefinitionId) != null);
        return status;
    }

    @Override
    public List<StatementTreeViewInfo> getCourseOfferingRestrictions(String courseOfferingId, String nlUsageTypeKey, String language, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<StatementTreeViewInfo>();
    }

    @Override
    public StatementTreeViewInfo createCourseOfferingRestriction(String courseOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;
    }

    @Override
    public StatementTreeViewInfo updateCourseOfferingRestriction(String courseOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteCourseOfferingRestriction(String courseOfferingId, String restrictionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<StatementTreeViewInfo> getActivityOfferingRestrictions(String activityOfferingId, String nlUsageTypeKey, String language, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<StatementTreeViewInfo>();
    }

    @Override
    public StatementTreeViewInfo createActivityOfferingRestriction(String activityOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;
    }

    @Override
    public StatementTreeViewInfo updateActivityOfferingRestriction(String activityOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteActivityOfferingRestriction(String activityOfferingId, String restrictionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIdList(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo> cList = new ArrayList<CourseOfferingInfo>();

        for (String cId : courseOfferingIds) {
            CourseOfferingInfo cOffering = courseOfferingCache.get(cId);
            if (null == cOffering) {
                throw new DoesNotExistException("Course Offering not found for: " + cId);
            }
            cList.add(cOffering);
        }

        return cList;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIdList(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingInfo> aList = new ArrayList<ActivityOfferingInfo>();

        for (String aId : activityOfferingIds) {
            ActivityOfferingInfo activity = activityOfferingCache.get(aId);
            if (null == activity) {
                throw new DoesNotExistException("Activity Offering not found for: " + aId);
            }
            aList.add(activity);
        }

        return aList;
    }

    @Override
    public StatusInfo assignActivityToCourseOffering(String activityOfferingId, List<String> courseOfferingIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo activity = activityOfferingCache.get(activityOfferingId);
        if (null == activity) {
            throw new DoesNotExistException("Activity offering not found for: " + activityOfferingId);
        }

        for (String cId : courseOfferingIdList) {
            CourseOfferingInfo cInfo = courseOfferingCache.get(cId);
            if (null == cInfo) {
                throw new DoesNotExistException("Course offering not found for: " + cId);
            }
        }

        for (String cId : courseOfferingIdList) {
            List<ActivityOfferingInfo> aList = courseToActivityOfferingCache.get(cId);
            if (null == aList) {
                throw new OperationFailedException("Inconsistent data!");
            }

            for (ActivityOfferingInfo aInfo : aList) {
                if (aInfo.getId().equals(activityOfferingId)) {
                    throw new AlreadyExistsException("activity offering " + activityOfferingId + " already mapped to course offering " + cId);
                }
            }

            aList.add(activity);
            courseToActivityOfferingCache.put(cId, aList);
        }


        StatusInfo s = new StatusInfo();
        s.setSuccess(true);
        return s;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIdList(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegistrationGroupInfo> regList = new ArrayList<RegistrationGroupInfo>();

        for (String regId : registrationGroupIds) {
            RegistrationGroupInfo reg = registrationGroupCache.get(regId);
            if (null == reg) {
                throw new DoesNotExistException("Reg group not found for: " + regId);
            }
            regList.add(reg);
        }

        return regList;
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseRegistrationInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForSeatpoolDefintionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndInstructorId(String termId, String instructorId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo courseOfferingInfo : this.courseOfferingCache.values()) {
            if (termId.equals(courseOfferingInfo.getTermId())) {
                if (courseOfferingInfo.getInstructors() != null) {
                    for (OfferingInstructorInfo offeringInstructorInfo : courseOfferingInfo.getInstructors()) {
                        if (instructorId.equals(offeringInstructorInfo.getPersonId())) {
                            list.add(courseOfferingInfo.getId());
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }
}
