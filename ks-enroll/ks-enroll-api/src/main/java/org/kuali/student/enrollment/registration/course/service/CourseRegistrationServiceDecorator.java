package org.kuali.student.enrollment.registration.course.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.registration.course.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.registration.course.dto.RegRequestInfo;
import org.kuali.student.enrollment.registration.course.dto.RegResponseInfo;
import org.kuali.student.enrollment.waitlist.course.dto.CourseWaitlistEntryInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class CourseRegistrationServiceDecorator implements CourseRegistrationService {
    protected CourseRegistrationServiceDecorator nextDecorator;

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public StateProcessInfo getProcessByKey(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> getProcessByObjectType(String refObjectUri,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public Boolean checkStudentEligibility(String studentId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ValidationResultInfo> checkStudentEligibilityForTerm(
			String studentId, String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<DateRangeInfo> getAppointmentWindow(String studentId,
			String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ValidationResultInfo> checkStudentEligibiltyForRegGroup(
			String studentId, String regGroupId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentAndCourseOffering(
			String studentId, String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public String calculateCreditLoadForTerm(String studentId, String termId,
			ContextInfo context) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public String getCreditLoadForRequest(String studentId,
			RegRequestInfo regRequestInfo, ContextInfo context) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public Integer getAvailableSeatsForRegGroupAndStudent(String regGroupId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public Integer getAvailableSeatsForCourseOffering(String courseOfferingId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public Integer getAvailableSeatCountForStudent(String studentId,
			String regGroupId, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public Integer getAvailableSeatsInSeatpool(String seatpoolId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegRequestInfo createRegRequest(RegRequestInfo reqRequestInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegRequestInfo updateRegRequest(RegRequestInfo reqRequestInfo,
			ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public StatusInfo deleteRegRequest(String reqRequestId, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public StatusInfo cancelRegRequest(String reqRequestId, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegRequestInfo getRegRequest(String reqRequestId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForStuByTerm(String studentId,
			String termId, List<String> requestStates, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateReqRequest(
			RegRequestInfo reqRequestInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegResponseInfo verifyReqRequest(RegRequestInfo reqRequestInfo,
			ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegResponseInfo verifyReqRequestForSubmission(String reqRequestId,
			ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegRequestInfo createReqRequestFromExisting(
			String existingRegRequestId, ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegResponseInfo submitReqRequest(String reqRequestId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public CourseWaitlistEntryInfo getCourseWaitlistEntryById(
			String courseWaitlistEntryId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegResponseInfo updateCourseWaitlistEntry(
			CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegResponseInfo deleteCourseWaitlistEntry(
			String courseWaitlistEntryId, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegResponseInfo registerStudentFromWaitlist(
			String courseWaitlistEntryId, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(
			String regGroupId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentAndCourseOffering(
			String courseOfferingId, String studentId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}



	@Override
	public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(
			String studentId, String termId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public CourseRegistrationInfo getCourseRegistration(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public CourseRegistrationInfo getCourseRegistrationsByIdList(
			List<String> courseRegistrationIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsForStudentByTerm(
			String studentId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOfferingId(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForCourseRegn(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegRequestInfo> getRegRequestsForCourseOfferingByStudent(
			String courseOfferingId, String studentId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegn(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public ActivityRegistrationInfo getActivityRegistration(
			String activityRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public ActivityRegistrationInfo getActivityRegistrationsByIdList(
			List<String> activityRegistrationIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ActivityRegistrationInfo> getActivityRegistrationsForActivityOffering(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ActivityRegistrationInfo> getActivityRegistrationsForStudentByTerm(
			String studentId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegGroupRegistrationInfo getRegGroupRegistration(
			String regGroupRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegGroupRegistrationInfo> getRegGroupRegistrationsForCourseRegistration(
			String courseRegistrationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegGroupRegistrationInfo> getRegGroupRegistrationsByIdList(
			List<String> regGroupIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegGroupRegistrationInfo> getRegGroupRegistrationsByRegGroupId(
			String regGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public RegGroupRegistrationInfo getRegGroupRegistrationsForStudentByTerm(
			String studentId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseRegistrationInfo> searchForCourseRegistrations(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> searchForCourseOfferingRegistrationIds(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ActivityRegistrationInfo> searchForActivityRegistrations(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> searchForActivityRegistrationIds(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<RegGroupRegistrationInfo> searchForRegGroupRegistrations(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> searchForRegGroupRegistrationIds(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(
			QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> searchForCourseWaitlistEntryIds(QueryByCriteria criteria) {
		// TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
		return null;
	}

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.registration.course.service.CourseRegistrationService#getCourseWaitlistEntryForStudentAndRegGroup(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentAndRegGroup(String regGroupId, String studentId,
            ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

	
	
}
