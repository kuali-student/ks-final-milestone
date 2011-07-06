package org.kuali.student.enrollment.classII.courseoffering.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.R1ToR2CopyHelper;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

public class CourseOfferingServiceMockImpl implements CourseOfferingService {
    
    private static Map<String, CourseOfferingInfo> courseOfferingCache = new HashMap<String, CourseOfferingInfo>();
    private static Map<String, ActivityOfferingInfo> activityOfferingCache = new HashMap<String, ActivityOfferingInfo>();
    private static Map<String, RegistrationGroupInfo> registrationGroupCache = new HashMap<String, RegistrationGroupInfo>();
    private static Map<String, CourseInfo> courseCache = new HashMap<String, CourseInfo>();
    private static Map<String, TypeInfo> typesCache = new HashMap<String, TypeInfo>();
    private static Map<String, SeatPoolDefinitionInfo> seatPoolDefinitionCache = new HashMap<String, SeatPoolDefinitionInfo>();
    private CourseService courseService;
    
    public CourseService getCourseService() {
        return courseService;
    }
    
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return courseOfferingCache.get(courseOfferingId);
        
    }
    
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(String courseId, String termKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getCourseId().equals(courseId) && courseOffering.getTermKey().equals(termKey)) {
                courseOfferings.add(courseOffering);
            }
        }
        return courseOfferings;
    }
    
    @Override
    public List<String> getCourseOfferingIdsForTerm(String termKey, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        List<String> courseOfferings = new ArrayList<String>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getTermKey().equals(termKey)) {
                courseOfferings.add(courseOffering.getId());
            }
        }
        return courseOfferings;
    }
    
    @Override
    public List<String> getCourseOfferingIdsBySubjectArea(String termKey, String subjectArea, ContextInfo context)
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
    public List<String> getCourseOfferingIdsByUnitContentOwner(String termKey, String unitOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        List<String> courseOfferings = new ArrayList<String>();
        for (CourseOfferingInfo courseOffering : courseOfferingCache.values()) {
            if (courseOffering.getUnitsContentOwner().contains(unitOwnerId)
                    && courseOffering.getTermKey().equals(termKey)) {
                courseOfferings.add(courseOffering.getId());
            }
        }
        return courseOfferings;
    }
    
    @Override
    public CourseOfferingInfo createCourseOfferingFromCanonical(String courseid, String termKey,
            List<String> formatIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
        courseOfferingInfo.setCourseId(courseid);
        courseOfferingInfo.setTermKey(termKey);
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
            courseOfferingInfo.setCreditOptions(new R1ToR2CopyHelper().copyResultComponent(courseInfo.getCreditOptions().get(0)));
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
        courseOfferingInfo.setCourseCode(courseInfo.getCode());
        courseOfferingInfo.setCourseId(courseInfo.getId());
        courseOfferingInfo.setCourseNumberSuffix(courseInfo.getCourseNumberSuffix());
        courseOfferingInfo.setCourseTitle(courseInfo.getCourseTitle());
        // TODO: worry about which credit option to apply.
        if (courseInfo.getCreditOptions() == null) {
            courseOfferingInfo.setCreditOptions(null);
        } else if (courseInfo.getCreditOptions().isEmpty()) {
            courseOfferingInfo.setCreditOptions(null);
        } else {            
            courseOfferingInfo.setCreditOptions(new R1ToR2CopyHelper().copyResultComponent(courseInfo.getCreditOptions().get(0)));
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
        
        return null;
    }
    
    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
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
        CourseOfferingInfo courseOffering = courseOfferingCache.get(courseOfferingId);
        List<String> activityOfferingIds = courseOffering.getActivityOfferingIds();
        List<ActivityOfferingInfo> activityOfferingInfos = new ArrayList<ActivityOfferingInfo>();
        for (String activityOfferingId : activityOfferingIds) {
            activityOfferingInfos.add(activityOfferingCache.get(activityOfferingId));
        }
        return activityOfferingInfos;
    }
    
    @Override
    public List<ActivityOfferingInfo> getActivitiesForRegGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingInfo> activityOfferings = new ArrayList<ActivityOfferingInfo>();
        RegistrationGroupInfo regGroup = registrationGroupCache.get(registrationGroupId);
        List<String> activityOfferingIds = regGroup.getActivityOfferingIds();
        for (String activityOfferingId : activityOfferingIds) {
            activityOfferings.add(activityOfferingCache.get(activityOfferingId));
        }
        
        return activityOfferings;
    }
    
    @Override
    public ActivityOfferingInfo createActivityOffering(List<String> courseOfferingIdList,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        activityOfferingInfo.setId(String.valueOf(Math.random()));
        activityOfferingCache.put(activityOfferingInfo.getId(), activityOfferingInfo);
        
        for (String courseOfferingId : courseOfferingIdList) {
            CourseOfferingInfo courseOffering = courseOfferingCache.get(courseOfferingId);
            List<String> activitiesForCourse = courseOffering.getActivityOfferingIds();
            activitiesForCourse.add(activityOfferingInfo.getId());
            
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
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return calculateInClassContactHoursForTerm(activityOfferingId, context)
                + calculateOutofClassContactHoursForTerm(activityOfferingId, context);
    }
    
    @Override
    public List<ActivityOfferingInfo> copyActivityOffering(String activityOfferingId, Integer numberOfCopies,
            String copyContextTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingInfo> activityOfferings = new ArrayList<ActivityOfferingInfo>(numberOfCopies);
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
        CourseOfferingInfo courseOffering = courseOfferingCache.get(courseOfferingId);
        List<String> regGroupIds = courseOffering.getRegistrationGroupIds();
        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        
        for (String regGroupId : regGroupIds) {
            regGroups.add(registrationGroupCache.get(regGroupId));
        }
        return regGroups;
    }
    
    @Override
    public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(String courseOfferingId, String formatTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        CourseOfferingInfo courseOffering = courseOfferingCache.get(courseOfferingId);
        List<FormatInfo> formatsInfo = new ArrayList<FormatInfo>();
        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        List<String> regGroupIds = courseOffering.getRegistrationGroupIds();
        
        try {
            formatsInfo = courseService.getCourseFormats(courseOffering.getCourseId());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            e.printStackTrace();
        } catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
            e.printStackTrace();
        } catch (org.kuali.student.common.exceptions.MissingParameterException e) {
            e.printStackTrace();
        } catch (org.kuali.student.common.exceptions.OperationFailedException e) {
            e.printStackTrace();
        } catch (org.kuali.student.common.exceptions.PermissionDeniedException e) {
            e.printStackTrace();
        }
        
        for (FormatInfo format : formatsInfo) {
            
            for (String regGroupId : regGroupIds) {
                RegistrationGroupInfo regGroup = registrationGroupCache.get(regGroupId);
                
                if (format.getId().equals(regGroup.getFormatId()) && format.getType().equals(formatTypeKey)) {
                    regGroups.add(regGroup);
                }
            }
        }
        return regGroups;
    }
    
    @Override
    public RegistrationGroupInfo createRegistrationGroup(String courseOfferingId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        registrationGroupInfo.setId(String.valueOf(Math.random()));
        registrationGroupCache.put(registrationGroupInfo.getId(), registrationGroupInfo);
        
        CourseOfferingInfo courseOffering = courseOfferingCache.get(courseOfferingId);
        List<String> regGroupsForCourse = courseOffering.getRegistrationGroupIds();
        regGroupsForCourse.add(registrationGroupInfo.getId());
        
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
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public StatementTreeViewInfo createCourseOfferingRestriction(String courseOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public StatementTreeViewInfo updateCourseOfferingRestriction(String courseOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public StatusInfo deleteCourseOfferingRestriction(String courseOfferingId, String restrictionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public List<StatementTreeViewInfo> getActivityOfferingRestrictions(String activityOfferingId, String nlUsageTypeKey, String language, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public StatementTreeViewInfo createActivityOfferingRestriction(String activityOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public StatementTreeViewInfo updateActivityOfferingRestriction(String activityOfferingId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public StatusInfo deleteActivityOfferingRestriction(String activityOfferingId, String restrictionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
