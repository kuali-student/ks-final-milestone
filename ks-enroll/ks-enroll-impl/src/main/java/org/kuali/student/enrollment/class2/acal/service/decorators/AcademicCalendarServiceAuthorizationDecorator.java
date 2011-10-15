package org.kuali.student.enrollment.class2.acal.service.decorators;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarServiceDecorator;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsPermissionService;

public class AcademicCalendarServiceAuthorizationDecorator extends AcademicCalendarServiceDecorator implements HoldsPermissionService {
    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "AcademicCalendarService.";
    
	private PermissionService permissionService;
	
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}
	
	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}


	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDataDictionaryEntryKeys", null, null)) {
	        return getNextDecorator().getDataDictionaryEntryKeys(context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDataDictionaryEntry", null, null)) {
	        return getNextDecorator().getDataDictionaryEntry(entryKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarType", null, null)) {
	        return getNextDecorator().getAcademicCalendarType(academicCalendarTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarTypes", null, null)) {
	        return getNextDecorator().getAcademicCalendarTypes(context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public StateInfo getAcademicCalendarState(String academicCalendarStateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarState", null, null)) {
	        return getNextDecorator().getAcademicCalendarState(academicCalendarStateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<StateInfo> getAcademicCalendarStates(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarStates", null, null)) {
	        return getNextDecorator().getAcademicCalendarStates(context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendar", null, null)) {
	        return getNextDecorator().getAcademicCalendar(academicCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(
			List<String> academicCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByKeyList", null, null)) {
	        return getNextDecorator().getAcademicCalendarsByKeyList(academicCalendarKeyList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getAcademicCalendarKeysByType(
			String academicCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarKeysByType", null, null)) {
	        return getNextDecorator().getAcademicCalendarKeysByType(academicCalendarTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(
			Integer year, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByStartYear", null, null)) {
	        return getNextDecorator().getAcademicCalendarsByStartYear(year, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(
			String credentialProgramTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByCredentialProgramType", null, null)) {
	        return getNextDecorator().getAcademicCalendarsByCredentialProgramType(credentialProgramTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForStartYear(
			String credentialProgramTypeKey, Integer year, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByCredentialProgramTypeForStartYear", null, null)) {
	        return getNextDecorator().getAcademicCalendarsByCredentialProgramTypeForStartYear(credentialProgramTypeKey, year, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForAcademicCalendarKeys(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAcademicCalendarKeys", null, null)) {
	        return getNextDecorator().searchForAcademicCalendarKeys(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<AcademicCalendarInfo> searchForAcademicCalendars(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAcademicCalendars", null, null)) {
	        return getNextDecorator().searchForAcademicCalendars(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateAcademicCalendar(
			String validationType, AcademicCalendarInfo academicCalendarInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateAcademicCalendar", null, null)) {
	        return getNextDecorator().validateAcademicCalendar(validationType, academicCalendarInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public AcademicCalendarInfo createAcademicCalendar(
			String academicCalendarKey,
			AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAcademicCalendar", null, null)) {
	        return getNextDecorator().createAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public AcademicCalendarInfo updateAcademicCalendar(
			String academicCalendarKey,
			AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAcademicCalendar", null, null)) {
	        return getNextDecorator().updateAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAcademicCalendar", null, null)) {
	        return getNextDecorator().deleteAcademicCalendar(academicCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public AcademicCalendarInfo copyAcademicCalendar(
			String academicCalendarKey, String newAcademicCalendarKey,
			ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "copyAcademicCalendar", null, null)) {
	        return getNextDecorator().copyAcademicCalendar(academicCalendarKey, newAcademicCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public String getAcademicCalendarData(String academicCalendarKey,
			String calendarDataFormatTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarData", null, null)) {
	        return getNextDecorator().getAcademicCalendarData(academicCalendarKey, calendarDataFormatTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TypeInfo getCampusCalendarType(String campusCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarType", null, null)) {
	        return getNextDecorator().getCampusCalendarType(campusCalendarTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getCampusCalendarTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarTypes", null, null)) {
	        return getNextDecorator().getCampusCalendarTypes(context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public StateInfo getCampusCalendarState(String campusCalendarStateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarState", null, null)) {
	        return getNextDecorator().getCampusCalendarState(campusCalendarStateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<StateInfo> getCampusCalendarStates(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarStates", null, null)) {
	        return getNextDecorator().getCampusCalendarStates(context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendar", null, null)) {
	        return getNextDecorator().getCampusCalendar(campusCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByKeyList(
			List<String> campusCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarsByKeyList", null, null)) {
	        return getNextDecorator().getCampusCalendarsByKeyList(campusCalendarKeyList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getCampusCalendarKeysByType(
			String campusCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarKeysByType", null, null)) {
	        return getNextDecorator().getCampusCalendarKeysByType(campusCalendarTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByStartYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarsByStartYear", null, null)) {
	        return getNextDecorator().getCampusCalendarsByStartYear(year, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForCampusCalendarKeys(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCampusCalendarKeys", null, null)) {
	        return getNextDecorator().searchForCampusCalendarKeys(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<CampusCalendarInfo> searchForCampusCalendars(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCampusCalendars", null, null)) {
	        return getNextDecorator().searchForCampusCalendars(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateCampusCalendar(
			String validationType, CampusCalendarInfo campusCalendarInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateCampusCalendar", null, null)) {
	        return getNextDecorator().validateCampusCalendar(validationType, campusCalendarInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createCampusCalendar", null, null)) {
	        return getNextDecorator().createCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateCampusCalendar", null, null)) {
	        return getNextDecorator().updateCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCampusCalendar", null, null)) {
	        return getNextDecorator().deleteCampusCalendar(campusCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TypeInfo getTermType(String termTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermType", null, null)) {
	        return getNextDecorator().getTermType(termTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getTermTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypes", null, null)) {
	        return getNextDecorator().getTermTypes(context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getTermTypesForAcademicCalendarType(
			String academicCalendarTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypesForAcademicCalendarType", null, null)) {
	        return getNextDecorator().getTermTypesForAcademicCalendarType(academicCalendarTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getTermTypesForTermType(String termTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypesForTermType", null, null)) {
	        return getNextDecorator().getTermTypesForTermType(termTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public StateInfo getTermState(String termStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermState", null, null)) {
	        return getNextDecorator().getTermState(termStateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<StateInfo> getTermStates(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermStates", null, null)) {
	        return getNextDecorator().getTermStates(context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public TermInfo getTerm(String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTerm", null, null)) {
	        return getNextDecorator().getTerm(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermsByKeyList", null, null)) {
	        return getNextDecorator().getTermsByKeyList(termKeyList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getTermKeysByType(String termTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermKeysByType", null, null)) {
	        return getNextDecorator().getTermKeysByType(termTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<TermInfo> getTermsForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermsForAcademicCalendar", null, null)) {
	        return getNextDecorator().getTermsForAcademicCalendar(academicCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<TermInfo> getCurrentTerms(String processKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCurrentTerms", null, null)) {
	        return getNextDecorator().getCurrentTerms(processKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<TermInfo> getIncludedTermsInTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getIncludedTermsInTerm", null, null)) {
	        return getNextDecorator().getIncludedTermsInTerm(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<TermInfo> getContainingTerms(String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getContainingTerms", null, null)) {
	        return getNextDecorator().getContainingTerms(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForTermKeys(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForTermKeys", null, null)) {
	        return getNextDecorator().searchForTermKeys(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<TermInfo> searchForTerms(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForTerms", null, null)) {
	        return getNextDecorator().searchForTerms(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateTerm(String validationType,
			TermInfo termInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateTerm", null, null)) {
	        return getNextDecorator().validateTerm(validationType, termInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public TermInfo createTerm(String termKey, TermInfo termInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createTerm", null, null)) {
	        return getNextDecorator().createTerm(termKey, termInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TermInfo updateTerm(String termKey, TermInfo termInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateTerm", null, null)) {
	        return getNextDecorator().updateTerm(termKey, termInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteTerm(String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteTerm", null, null)) {
	        return getNextDecorator().deleteTerm(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo addTermToAcademicCalendar(String academicCalendarKey,
			String termKey, ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addTermToAcademicCalendar", null, null)) {
	        return getNextDecorator().addTermToAcademicCalendar(academicCalendarKey, termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo removeTermFromAcademicCalendar(
			String academicCalendarKey, String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeTermFromAcademicCalendar", null, null)) {
	        return getNextDecorator().removeTermFromAcademicCalendar(academicCalendarKey, termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo addTermToTerm(String termKey, String includedTermKey,
			ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addTermToTerm", null, null)) {
	        return getNextDecorator().addTermToTerm(termKey, includedTermKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo removeTermFromTerm(String termKey,
			String includedTermKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeTermFromTerm", null, null)) {
	        return getNextDecorator().removeTermFromTerm(termKey, includedTermKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateType", null, null)) {
	        return getNextDecorator().getKeyDateType(keyDateTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateTypesForTermType", null, null)) {
	        return getNextDecorator().getKeyDateTypesForTermType(termTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDate", null, null)) {
	        return getNextDecorator().getKeyDate(keyDateKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesByKeyList", null, null)) {
	        return getNextDecorator().getKeyDatesByKeyList(keyDateKeyList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> getKeyDateKeysByType(String keyDateTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateKeysByType", null, null)) {
	        return getNextDecorator().getKeyDateKeysByType(keyDateTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForAcademicCalendar", null, null)) {
	        return getNextDecorator().getKeyDatesForAcademicCalendar(academicCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(
			String academicCalendarKey, Date startDate, Date endDate,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForAcademicCalendarByDate", null, null)) {
	        return getNextDecorator().getKeyDatesForAcademicCalendarByDate(academicCalendarKey, startDate, endDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForTerm", null, null)) {
	        return getNextDecorator().getKeyDatesForTerm(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForTermByDate", null, null)) {
	        return getNextDecorator().getKeyDatesForTermByDate(termKey, startDate, endDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAllKeyDatesForTerm", null, null)) {
	        return getNextDecorator().getAllKeyDatesForTerm(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForAllTermsByDate", null, null)) {
	        return getNextDecorator().getKeyDatesForAllTermsByDate(termKey, startDate, endDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForKeyDateKeys(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForKeyDateKeys", null, null)) {
	        return getNextDecorator().searchForKeyDateKeys(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForKeyDates", null, null)) {
	        return getNextDecorator().searchForKeyDates(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateKeyDate(String validationType,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateKeyDate", null, null)) {
	        return getNextDecorator().validateKeyDate(validationType, keyDateInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createKeyDateForTerm", null, null)) {
	        return getNextDecorator().createKeyDateForTerm(termKey, keyDateKey, keyDateInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public KeyDateInfo updateKeyDate(String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateKeyDate", null, null)) {
	        return getNextDecorator().updateKeyDate(keyDateKey, keyDateInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteKeyDate", null, null)) {
	        return getNextDecorator().deleteKeyDate(keyDateKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayType", null, null)) {
	        return getNextDecorator().getHolidayType(holidayTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getHolidayTypesForCampusCalendarType(
			String campusCalendarTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayTypesForCampusCalendarType", null, null)) {
	        return getNextDecorator().getHolidayTypesForCampusCalendarType(campusCalendarTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<HolidayInfo> getHolidaysForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidaysForAcademicCalendar", null, null)) {
	        return getNextDecorator().getHolidaysForAcademicCalendar(academicCalendarKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<String> searchForHolidayKeys(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidayKeys", null, null)) {
	        return getNextDecorator().searchForHolidayKeys(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidays", null, null)) {
	        return getNextDecorator().searchForHolidays(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateHoliday(String validationType,
			HolidayInfo holidayInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateHoliday", null, null)) {
	        return getNextDecorator().validateHoliday(validationType, holidayInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
			String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createHolidayForCampusCalendar", null, null)) {
	        return getNextDecorator().createHolidayForCampusCalendar(campusCalendarKey, holidayKey, holidayInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public HolidayInfo updateHoliday(String holidayKey,
			HolidayInfo holidayInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateHoliday", null, null)) {
	        return getNextDecorator().updateHoliday(holidayKey, holidayInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteHoliday(String holidayKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteHoliday", null, null)) {
	        return getNextDecorator().deleteHoliday(holidayKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationDateGroup", null, null)) {
	        return getNextDecorator().getRegistrationDateGroup(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateRegistrationDateGroup(
			String validationType,
			RegistrationDateGroupInfo registrationDateGroupInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateRegistrationDateGroup", null, null)) {
	        return getNextDecorator().validateRegistrationDateGroup(validationType, registrationDateGroupInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public RegistrationDateGroupInfo updateRegistrationDateGroup(
			String termKey,
			RegistrationDateGroupInfo registrationDateGroupInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateRegistrationDateGroup", null, null)) {
	        return getNextDecorator().updateRegistrationDateGroup(termKey, registrationDateGroupInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public Integer getInstructionalDaysForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionalDaysForTerm", null, null)) {
	        return getNextDecorator().getInstructionalDaysForTerm(termKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

}
