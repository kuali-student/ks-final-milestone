package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.grading.dto.LoadInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

public class CourseRegistrationServiceDecorator implements CourseRegistrationService {

    private CourseRegistrationService nextDecorator;

    public CourseRegistrationService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(CourseRegistrationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByKey(processKey, context);
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByObjectType(refObjectUri, context);
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getState(processKey, stateKey, context);
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getStatesByProcess(processKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getNextHappyState(processKey, currentStateKey, context);
    }

    @Override
    public Boolean checkStudentEligibility(String studentId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibility(studentId, context);
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibilityForTerm(String studentId,
            String termId, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibilityForTerm(studentId, termId, context);
    }

    @Override
    public List<DateRangeInfo> getAppointmentWindows(String studentId, String termId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAppointmentWindows(studentId, termId, context);
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibiltyForCourseOffering(
            String studentId, String courseOfferingId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, context);
    }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> checkStudentEligibiltyForRegGroup(
            String studentId, String regGroupId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().checkStudentEligibiltyForRegGroup(studentId, regGroupId, context);
    }

    @Override
    public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(String studentId,
            String courseOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEligibleRegGroupsForStudentInCourseOffering(studentId, courseOfferingId, context);
    }

    @Override
    public LoadInfo calculateCreditLoadForTerm(String studentId, String termId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().calculateCreditLoadForTerm(studentId, termId, context);
    }

    @Override
    public LoadInfo calculateCreditLoadForRegRequest(String studentId, RegRequestInfo regRequestInfo,
            ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().calculateCreditLoadForRegRequest(studentId, regRequestInfo, context);
    }

    @Override
    public Integer getAvailableSeatsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public Integer getAvailableSeatsForRegGroup(String regGroupId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsForRegGroup(regGroupId, context);
    }

    @Override
    public Integer getAvailableSeatsForStudentInRegGroup(String studentId, String regGroupId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsForStudentInRegGroup(studentId, regGroupId, context);
    }

    @Override
    public Integer getAvailableSeatsInSeatpool(String seatpoolId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAvailableSeatsInSeatpool(seatpoolId, context);
    }

    @Override
    public RegRequestInfo createRegRequest(RegRequestInfo regRequestInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createRegRequest(regRequestInfo, context);
    }

    @Override
    public RegRequestInfo updateRegRequest(String regRequestId, RegRequestInfo regRequestInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateRegRequest(regRequestId, regRequestInfo, context);
    }

    @Override
    public StatusInfo deleteRegRequest(String regRequestId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().deleteRegRequest(regRequestId, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegRequest(RegRequestInfo regRequestInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateRegRequest(regRequestInfo, context);
    }

    @Override
    public List<ValidationResultInfo> verifyRegRequest(RegRequestInfo regRequestInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().verifyRegRequest(regRequestInfo, context);
    }

    @Override
    public RegResponseInfo verifySavedReqRequest(String regRequestId, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().verifySavedReqRequest(regRequestId, context);
    }

    @Override
    public RegRequestInfo createRegRequestFromExisting(String existingRegRequestId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().createRegRequestFromExisting(existingRegRequestId, context);
    }

    @Override
    public RegResponseInfo submitRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, AlreadyExistsException {
        return getNextDecorator().submitRegRequest(regRequestId, context);
    }

    @Override
    public StatusInfo cancelRegRequest(String regRequestId, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().cancelRegRequest(regRequestId, context);
    }

    @Override
    public List<RegRequestInfo> getRegRequestsByIdList(List<String> regRequestIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequestsByIdList(regRequestIds, context);
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForStudentByTerm(String studentId, String termId,
            List<String> requestStates, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequestsForStudentByTerm(studentId, termId, requestStates, context);
    }

    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitlistEntry(courseWaitlistEntryId, context);
    }

    @Override
    public StatusInfo updateCourseWaitlistEntry(String courseWaitlistEntryId,
            CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().updateCourseWaitlistEntry(courseWaitlistEntryId, courseWaitlistEntryInfo, context);
    }

    @Override
    public StatusInfo reorderCourseWaitlistEntries(List<String> courseWaitlistEntryIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reorderCourseWaitlistEntries(courseWaitlistEntryIds, context);
    }

    @Override
    public StatusInfo insertCourseWaitlistEntryAtPosition(String courseWaitlistEntryId, Integer position,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().insertCourseWaitlistEntryAtPosition(courseWaitlistEntryId, position, context);
    }

    @Override
    public StatusInfo removeCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeCourseWaitlistEntry(courseWaitlistEntryId, context);
    }

    @Override
    public StatusInfo validateCourseWaitlistEntry(String validateTypeKey,
            CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCourseWaitlistEntry(validateTypeKey, courseWaitlistEntryInfo, context);
    }

    @Override
    public RegResponseInfo registerStudentFromWaitlist(String courseWaitlistEntryId, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().registerStudentFromWaitlist(courseWaitlistEntryId, context);
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitlistEntriesForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(String regGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitlistEntriesForRegGroup(regGroupId, context);
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentInCourseOffering(String courseOfferingId,
            String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitlistEntriesForStudentInCourseOffering(courseOfferingId, studentId,
                context);
    }

    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentInRegGroup(String regGroupId, String studentId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitlistEntryForStudentInRegGroup(regGroupId, studentId, context);
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(String studentId, String termId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitlistEntriesForStudentByTerm(studentId, termId, context);
    }

    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseRegistration(courseRegistrationId, context);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIdList(List<String> courseRegistrationIds,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseRegistrationsByIdList(courseRegistrationIds, context);
    }

    @Override
    public CourseRegistrationInfo getActiveCourseRegistrationForStudentByCourseOffering(String studentId,
            String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        return getNextDecorator().getActiveCourseRegistrationForStudentByCourseOffering(studentId, courseOfferingId,
                context);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(String studentId, String termId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        return getNextDecorator().getCourseRegistrationsForStudentByTerm(studentId, termId, context);
    }

    @Override
    public List<CourseRegistrationInfo> getActiveCourseRegistrationsByCourseOfferingId(String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveCourseRegistrationsByCourseOfferingId(courseOfferingId, context);
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForCourseRegistration(String courseRegistrationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequestsForCourseRegistration(courseRegistrationId, context);
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequestsForCourseOffering(courseOfferingId, context);
    }

    @Override
    public List<RegRequestInfo> getRegRequestsForCourseOfferingByStudent(String courseOfferingId, String studentId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequestsForCourseOfferingByStudent(courseOfferingId, studentId, context);
    }

    @Override
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseRegistrations(criteria, context);
    }

    @Override
    public List<String> searchForCourseOfferingRegistrationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseOfferingRegistrationIds(criteria, context);
    }

    @Override
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForActivityRegistrations(criteria, context);
    }

    @Override
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForActivityRegistrationIds(criteria, context);
    }

    @Override
    public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForRegGroupRegistrations(criteria, context);
    }

    @Override
    public List<String> searchForRegGroupRegistrationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForRegGroupRegistrationIds(criteria, context);
    }

    @Override
    public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseWaitlistEntries(criteria, context);
    }

    @Override
    public List<String> searchForCourseWaitlistEntryIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCourseWaitlistEntryIds(criteria, context);
    }

    @Override
    public RegRequestInfo getRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegRequest(regRequestId, context);
    }

    @Override
    public StatusInfo deleteCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteCourseWaitlistEntry(courseWaitlistEntryId, context);
    }

    @Override
    public RegResponseInfo dropStudentsFromRegGroups(List<String> regGroupIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().dropStudentsFromRegGroups(regGroupIdList, context);
    }

    @Override
    public RegResponseInfo moveStudentsBetweenRegGroups(String sourceRegGroupId, String destinationRegGroupId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().moveStudentsBetweenRegGroups(sourceRegGroupId, destinationRegGroupId, context);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByCourseOffering(String studentId,
            String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        return getNextDecorator()
                .getCourseRegistrationsForStudentByCourseOffering(studentId, courseOfferingId, context);
    }

    @Override
    public List<CourseRegistrationInfo> getDroppedCourseRegistrationsByCourseOfferingId(String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getDroppedCourseRegistrationsByCourseOfferingId(courseOfferingId, context);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsForStudent(String studentId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DisabledIdentifierException {
        return getNextDecorator().getCourseRegistrationsForStudent(studentId, context);
    }

}
