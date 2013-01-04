package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.HashMap;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

//import org.kuali.rice.kim.service.PermissionService;
public class CourseOfferingServiceAuthorizationDecorator extends CourseOfferingServiceDecorator {

    private PermissionService permissionService;
    private CourseService courseService;

    public PermissionService getPermissionService() {
        if (permissionService == null) {
            permissionService = (PermissionService) GlobalResourceLoader.getService(new QName(PermissionServiceConstants.PERMISSION_SERVICE_NAMESPACE,
                    PermissionServiceConstants.PERMISSION_SERVICE_NAME_LOCAL_PART));
        }
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE,
                    CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    

    ////
    //// course offering permission methods
    ////
    /**
     * Add permission details that are needed to decide if user can access this particular object
     * 
     * This method is intended to be overridden so implementing institutions can
     * add different information to the details, for example by Department instead 
     * of (or in addition to) subject area
     */
    protected void addCourseOfferingPermissionDetails(Map<String, String> details, CourseOfferingInfo info, ContextInfo context)
            throws OperationFailedException {
        details.put(PermissionServiceConstants.SUBJECT_AREA_ATTR_DEFINITION, info.getSubjectArea());
    }

    /**
     * Check course offering permission
     * 
     * Not sure if this needs to ever be overridden but made it protected just in case
     * 
     * @param permService
     * @param info if null then does basic permission checking
     * @param permission
     * @param context
     * @throws PermissionDeniedException 
     */
    protected void checkCourseOfferingPermission(PermissionService permService,
            CourseOfferingInfo info,
            String permission,
            ContextInfo context) throws PermissionDeniedException, OperationFailedException {
        Map<String, String> details = null;
        if (info != null) {
            details = new HashMap<String, String>();
            this.addCourseOfferingPermissionDetails(details, info, context);
        }
        if (!permService.isAuthorized(context.getPrincipalId(),
                PermissionServiceConstants.KS_ENRL_NAMESPACE, permission,
                details)) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPrincipalId());
            sb.append(" does not have ");
            sb.append(permission);
            if (info != null) {
                sb.append(info.getId());
                sb.append(" because permission details don't match: ");
                sb.append(details);
            }
            throw new PermissionDeniedException(sb.toString());
        }
    }

    /**
     * Not sure if this needs to ever be overridden but made it protected just in case
     * @param permService
     * @param info
     * @param permission
     * @param context
     * @throws PermissionDeniedException 
     */
    protected void checkEachCourseOfferingPermission(PermissionService permService,
            List<CourseOfferingInfo> infos,
            String permission,
            ContextInfo context) throws PermissionDeniedException, OperationFailedException {
        // Add a caching decorator to speed processing of all the permission calls keep them really local!
        PermissionService permServCached = new PermissionServiceIsAuthorizedCachingDecorator(this.getPermissionService());
        for (CourseOfferingInfo info : infos) {
            this.checkCourseOfferingPermission(permServCached, info, permission, context);
        }
    }

    ////
    //// activity offering permission methods
    //// 
    /**
     * Add permission details that are needed to decide if user can access this particular object
     * 
     * This method is intended to be overridden so implementing institutions can
     * add different information to the details, for example by Department instead 
     * of (or in addition to) subject area
     */
    protected void addActivityOfferingPermissionDetails(Map<String, String> details, ActivityOfferingInfo info, CourseOfferingInfo coInfo, ContextInfo context)
            throws OperationFailedException {
        if (coInfo == null) {
            try {
                // TODO: make sure this is cached?
                coInfo = this.getNextDecorator().getCourseOffering(info.getCourseOfferingId(), context);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("Unexpected", ex);
            } catch (InvalidParameterException ex) {
                throw new OperationFailedException("Unexpected", ex);
            } catch (MissingParameterException ex) {
                throw new OperationFailedException("Unexpected", ex);
            } catch (PermissionDeniedException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
        }
        this.addCourseOfferingPermissionDetails(details, coInfo, context);
    }

    /**
     * Check course offering permission
     * 
     * Not sure if this needs to ever be overridden but made it protected just in case
     * 
     * @param permService
     * @param info if null then does basic permission checking
     * @param permission
     * @param context
     * @throws PermissionDeniedException 
     */
    protected void checkActivityOfferingPermission(PermissionService permService,
            ActivityOfferingInfo info,
            CourseOfferingInfo coInfo,
            String permission,
            ContextInfo context) throws PermissionDeniedException, OperationFailedException {
        Map<String, String> details = null;
        if (info == null) {
            details = new HashMap<String, String>();
            this.addActivityOfferingPermissionDetails(details, info, coInfo, context);
        }
        if (!permService.isAuthorized(context.getPrincipalId(),
                PermissionServiceConstants.KS_ENRL_NAMESPACE, permission,
                details)) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPrincipalId());
            sb.append(" does not have ");
            sb.append(permission);
            if (info != null) {
                sb.append(info.getId());
                sb.append(" because permission details don't match: ");
                sb.append(details);
            }
            throw new PermissionDeniedException(sb.toString());
        }
    }

    /**
     * Not sure if this needs to ever be overridden but made it protected just in case
     * @param permService
     * @param info
     * @param permission
     * @param context
     * @throws PermissionDeniedException 
     */
    protected void checkEachActivityOfferingPermission(PermissionService permService,
            List<ActivityOfferingInfo> infos,
            String permission,
            ContextInfo context) throws PermissionDeniedException, OperationFailedException {
        // Add a caching decorator to speed processing of all the permission calls keep them really local!
        PermissionService permServCached = new PermissionServiceIsAuthorizedCachingDecorator(this.getPermissionService());
        for (ActivityOfferingInfo info : infos) {
            this.checkActivityOfferingPermission(permServCached, info, null, permission, context);
        }
    }

    ////
    //// authz checks and then delegate
    ////
    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        CourseOfferingInfo info = getNextDecorator().getCourseOffering(courseOfferingId, context);
        // now check if the person has permission to the retrieved object based on matching the details
        this.checkCourseOfferingPermission(this.getPermissionService(), info,
                PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        return info;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(
            List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        // now check if the person has permission to this retrieved objects based on matching the details
        List<CourseOfferingInfo> infos = getNextDecorator().getCourseOfferingsByIds(courseOfferingIds, context);
        this.checkEachCourseOfferingPermission(this.getPermissionService(), infos, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        return infos;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(
            String courseId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        // now check if the person has permission to this retrieved objects based on matching the details
        List<CourseOfferingInfo> infos = getNextDecorator().getCourseOfferingsByCourseAndTerm(courseId, termId, context);
        this.checkEachCourseOfferingPermission(this.getPermissionService(), infos, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        return infos;
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId,
            Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        // Don't have to check if the person has permission to the retrieved ids since ids are not sensitive
        List<String> ids = getNextDecorator().getCourseOfferingIdsByTerm(termId, useIncludedTerm, context);
        return ids;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(
            String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        // Don't have to check if the person has permission to the retrieved ids since ids are not sensitive
        List<String> ids = getNextDecorator().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectArea, context);
        return ids;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(
            String termId, String instructorId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        // now check if the person has permission to the retrieved objects based on matching the details
        List<CourseOfferingInfo> infos = getNextDecorator().getCourseOfferingsByTermAndInstructor(termId, instructorId, context);
        this.checkEachCourseOfferingPermission(this.getPermissionService(), infos, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        return infos;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(
            String termId, String unitsContentOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, null, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        // Don't have to check if the person has permission to the retrieved ids since ids are not sensitive
        List<String> ids = getNextDecorator().getCourseOfferingIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId, context);
        return ids;
    }

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId,
            String termId,
            String courseOfferingTypeKey,
            CourseOfferingInfo courseOfferingInfo,
            List<String> optionKeys,
            ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        // check if person has permission to create this object
        // Have to get the course now because the subject area is not copied from cannonical
        // until later by the transformer and that is used to do the auth check!
        CourseInfo course = this.courseService.getCourse(courseId, context);
        courseOfferingInfo.setSubjectArea(course.getSubjectArea());
        this.checkCourseOfferingPermission(permissionService, courseOfferingInfo, PermissionServiceConstants.CREATE_COURSEOFFERING_PERMISSION, context);
        CourseOfferingInfo info = getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys, context);
        return info;
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
            CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        // now check if the person has permission to the original object based on matching the details
        // need to match against the original object to disallow the user from grabbing another department's CO
        // and changing it to their own!
        CourseOfferingInfo orig = this.getNextDecorator().getCourseOffering(courseOfferingId, context);
        this.checkCourseOfferingPermission(this.getPermissionService(), orig,
                PermissionServiceConstants.UPDATE_COURSEOFFERING_PERMISSION, context);
        CourseOfferingInfo info = getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
        return info;
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(
            String courseOfferingId,
            List<String> optionKeys, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        // now check if the person has permission to the original object based on matching the details
        // need to match against the original object to disallow the user from grabbing another department's CO
        // and changing it to their own!
        CourseOfferingInfo orig = this.getNextDecorator().getCourseOffering(courseOfferingId, context);
        this.checkCourseOfferingPermission(this.getPermissionService(), orig,
                PermissionServiceConstants.UPDATE_COURSEOFFERING_PERMISSION, context);
        CourseOfferingInfo info = getNextDecorator().updateCourseOfferingFromCanonical(courseOfferingId, optionKeys, context);
        return info;
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        // now check if the person has permission to the original object based on matching the details
        CourseOfferingInfo orig = this.getNextDecorator().getCourseOffering(courseOfferingId, context);
        this.checkCourseOfferingPermission(this.getPermissionService(), orig,
                PermissionServiceConstants.DELETE_COURSEOFFERING_PERMISSION, context);
        StatusInfo statusInfo = getNextDecorator().deleteCourseOffering(courseOfferingId, context);
        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(
            String validationType, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkCourseOfferingPermission(permissionService, courseOfferingInfo, PermissionServiceConstants.READ_COURSEOFFERING_PERMISSION, context);
        return getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check if person has even basic permission
        this.checkActivityOfferingPermission(permissionService, null, null, PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        return getNextDecorator().getActivityOfferingType(activityOfferingTypeKey, context);
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check if person has even basic permission
        this.checkActivityOfferingPermission(permissionService, null, null, PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        ActivityOfferingInfo info = getNextDecorator().getActivityOffering(activityOfferingId, context);
        // now check if the person has permission to the retrieved object based on matching the details
        this.checkActivityOfferingPermission(this.getPermissionService(), info, null,
                PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        return info;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(
            List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkActivityOfferingPermission(permissionService, null, null, PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        List<ActivityOfferingInfo> infos = getNextDecorator().getActivityOfferingsByIds(activityOfferingIds, context);
        // now check if the person has permission to the retrieved object based on matching the details
        this.checkEachActivityOfferingPermission(this.getPermissionService(), infos,
                PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        return infos;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(
            String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkActivityOfferingPermission(permissionService, null, null, PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        List<ActivityOfferingInfo> infos = getNextDecorator().getActivityOfferingsByCourseOffering(courseOfferingId, context);
        // now check if the person has permission to the retrieved object based on matching the details
        this.checkEachActivityOfferingPermission(this.getPermissionService(), infos,
                PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        return infos;
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
            String activityId,
            String activityOfferingTypeKey,
            ActivityOfferingInfo activityOfferingInfo,
            ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        // check if person has the right to create
        FormatOfferingInfo foInfo = this.getNextDecorator().getFormatOffering(formatOfferingId, context);
        CourseOfferingInfo coInfo = this.getNextDecorator().getCourseOffering(foInfo.getCourseOfferingId(), context);
        activityOfferingInfo.setCourseOfferingId(coInfo.getId());
        this.checkActivityOfferingPermission(permissionService, activityOfferingInfo, coInfo, PermissionServiceConstants.CREATE_ACTIVITYOFFERING_PERMISSION, context);
        return getNextDecorator().createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(
            String activityOfferingId,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        // check if person has the right to update
        FormatOfferingInfo foInfo = this.getNextDecorator().getFormatOffering(activityOfferingInfo.getFormatOfferingId(), context);
        CourseOfferingInfo coInfo = this.getNextDecorator().getCourseOffering(foInfo.getCourseOfferingId(), context);
        activityOfferingInfo.setCourseOfferingId(coInfo.getId());
        this.checkActivityOfferingPermission(permissionService, activityOfferingInfo, coInfo, PermissionServiceConstants.UPDATE_ACTIVITYOFFERING_PERMISSION, context);
        return getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        // check if person has the right to update
        ActivityOfferingInfo info = this.getNextDecorator().getActivityOffering(activityOfferingId, context);
        this.checkActivityOfferingPermission(permissionService, info, null, PermissionServiceConstants.DELETE_ACTIVITYOFFERING_PERMISSION, context);
        return getNextDecorator().deleteActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(
            String validationType, ActivityOfferingInfo activityOfferingInfo,
            ContextInfo context) 
            throws DoesNotExistException,
            InvalidParameterException, 
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // check if person has even basic permission
        this.checkActivityOfferingPermission(permissionService, activityOfferingInfo, null, PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        return getNextDecorator().validateActivityOffering(validationType, activityOfferingInfo, context);
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check if person has even basic permission
        ActivityOfferingInfo aoInfo = this.getNextDecorator().getActivityOffering(activityOfferingId, context);
        this.checkActivityOfferingPermission(permissionService, aoInfo, null, PermissionServiceConstants.READ_ACTIVITYOFFERING_PERMISSION, context);
        return getNextDecorator().calculateInClassContactHoursForTerm(activityOfferingId, context);
    }
//    @Override
//    public Float calculateOutofClassContactHoursForTerm(
//            String activityOfferingId, ContextInfo context)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateOutofClassContactHoursForTerm", null)) {
//            return getNextDecorator().calculateOutofClassContactHoursForTerm(activityOfferingId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public Float calculateTotalContactHoursForTerm(String activityOfferingId,
//            ContextInfo context) throws DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "calculateTotalContactHoursForTerm", null)) {
//            return getNextDecorator().calculateTotalContactHoursForTerm(activityOfferingId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public RegistrationGroupInfo getRegistrationGroup(
//            String registrationGroupId, ContextInfo context)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationGroup", null)) {
//            return getNextDecorator().getRegistrationGroup(registrationGroupId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(
//            List<String> registrationGroupIds, ContextInfo context)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationGroupsByIds", null)) {
//            return getNextDecorator().getRegistrationGroupsByIds(registrationGroupIds, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(
//            String courseOfferingId, ContextInfo context)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationGroupsForCourseOffering", null)) {
//            return getNextDecorator().getRegistrationGroupsForCourseOffering(courseOfferingId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public RegistrationGroupInfo updateRegistrationGroup(
//            String registrationGroupId,
//            RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
//            throws DataValidationErrorException, DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException,
//            ReadOnlyException, VersionMismatchException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateRegistrationGroup", null)) {
//            return getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public StatusInfo deleteRegistrationGroup(String registrationGroupId,
//            ContextInfo context) throws DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteRegistrationGroup", null)) {
//            return getNextDecorator().deleteRegistrationGroup(registrationGroupId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<ValidationResultInfo> validateRegistrationGroup(
//            String validationType, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo,
//            ContextInfo context) throws DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateRegistrationGroup", null)) {
//            return getNextDecorator().validateRegistrationGroup(validationType, activityOfferingClusterId, registrationGroupType, registrationGroupInfo, context);
//        } else {
//            throw new OperationFailedException("Permission Denied");
//        }
//    }
//
//    @Override
//    public SeatPoolDefinitionInfo getSeatPoolDefinition(
//            String seatPoolDefinitionId, ContextInfo context)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getSeatPoolDefinition", null)) {
//            return getNextDecorator().getSeatPoolDefinition(seatPoolDefinitionId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public SeatPoolDefinitionInfo createSeatPoolDefinition(
//            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
//            throws DataValidationErrorException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException,
//            ReadOnlyException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createSeatPoolDefinition", null)) {
//            return getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public SeatPoolDefinitionInfo updateSeatPoolDefinition(
//            String seatPoolDefinitionId,
//            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
//            throws DataValidationErrorException, DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException,
//            ReadOnlyException,
//            VersionMismatchException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateSeatPoolDefinition", null)) {
//            return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId,
//            ContextInfo context) throws DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteSeatPoolDefinition", null)) {
//            return getNextDecorator().deleteSeatPoolDefinition(seatPoolDefinitionId, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<CourseOfferingInfo> searchForCourseOfferings(
//            QueryByCriteria criteria, ContextInfo context)
//            throws InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseOfferings", null)) {
//            return getNextDecorator().searchForCourseOfferings(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria,
//            ContextInfo context) throws InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCourseOfferingIds", null)) {
//            return getNextDecorator().searchForCourseOfferingIds(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<ActivityOfferingInfo> searchForActivityOfferings(
//            QueryByCriteria criteria, ContextInfo context)
//            throws InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityOfferings", null)) {
//            return getNextDecorator().searchForActivityOfferings(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria,
//            ContextInfo context) throws InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForActivityOfferingIds", null)) {
//            return getNextDecorator().searchForActivityOfferingIds(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<RegistrationGroupInfo> searchForRegistrationGroups(
//            QueryByCriteria criteria, ContextInfo context)
//            throws InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegistrationGroups", null)) {
//            return getNextDecorator().searchForRegistrationGroups(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria,
//            ContextInfo context) throws InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForRegistrationGroupIds", null)) {
//            return getNextDecorator().searchForRegistrationGroupIds(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(
//            QueryByCriteria criteria, ContextInfo context)
//            throws InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForSeatpoolDefintions", null)) {
//            return getNextDecorator().searchForSeatpoolDefinitions(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public List<String> searchForSeatpoolDefinitionIds(QueryByCriteria criteria,
//            ContextInfo context) throws InvalidParameterException,
//            MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForSeatpoolDefintionIds", null)) {
//            return getNextDecorator().searchForSeatpoolDefinitionIds(criteria, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
//
//    @Override
//    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType,
//            FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
//            MissingParameterException, OperationFailedException, PermissionDeniedException,
//            ReadOnlyException {
//        if (null == context) {
//            throw new MissingParameterException();
//        }
//
//        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForSeatpoolDefintionIds", null)) {
//            return getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
//        } else {
//            throw new PermissionDeniedException();
//        }
//    }
}
