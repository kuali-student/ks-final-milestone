package org.kuali.student.enrollment.class2.acal.service.decorators;

import java.util.Date;
import java.util.List;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
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
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDataDictionaryEntryKeys", null, null)) {
            return getNextDecorator().getDataDictionaryEntryKeys(context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDataDictionaryEntry", null, null)) {
            return getNextDecorator().getDataDictionaryEntry(entryKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarType", null, null)) {
            return getNextDecorator().getAcademicCalendarType(academicCalendarTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarTypes", null, null)) {
            return getNextDecorator().getAcademicCalendarTypes(context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarState", null, null)) {
            return getNextDecorator().getAcademicCalendarState(academicCalendarStateKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarStates", null, null)) {
            return getNextDecorator().getAcademicCalendarStates(context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendar", null, null)) {
            return getNextDecorator().getAcademicCalendar(academicCalendarKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByIds(List<String> academicCalendarKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByKeyList", null, null)) {
            return getNextDecorator().getAcademicCalendarsByIds(academicCalendarKeyList, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getAcademicCalendarIdsByType(String academicCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarKeysByType", null, null)) {
            return getNextDecorator().getAcademicCalendarIdsByType(academicCalendarTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByStartYear", null, null)) {
            return getNextDecorator().getAcademicCalendarsByStartYear(year, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> searchForAcademicCalendarIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAcademicCalendarKeys", null, null)) {
            return getNextDecorator().searchForAcademicCalendarIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<AcademicCalendarInfo> searchForAcademicCalendars(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAcademicCalendars", null, null)) {
            return getNextDecorator().searchForAcademicCalendars(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == contextInfo) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAcademicCalendar", null, null)) {
            return getNextDecorator().createAcademicCalendar(academicCalendarTypeKey, academicCalendarInfo, contextInfo);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarId, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAcademicCalendar", null, null)) {
            return getNextDecorator().updateAcademicCalendar(academicCalendarId, academicCalendarInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAcademicCalendar", null, null)) {
            return getNextDecorator().deleteAcademicCalendar(academicCalendarKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Integer startYear, Integer endYear, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == contextInfo) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "copyAcademicCalendar", null, null)) {
            return getNextDecorator().copyAcademicCalendar(academicCalendarId, startYear, endYear, contextInfo);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarKey, String calendarDataFormatTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarData", null, null)) {
            return getNextDecorator().getAcademicCalendarData(academicCalendarKey, calendarDataFormatTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getHolidayCalendarType(String campusCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarType", null, null)) {
            return getNextDecorator().getHolidayCalendarType(campusCalendarTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarTypes", null, null)) {
            return getNextDecorator().getHolidayCalendarTypes(context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public StateInfo getHolidayCalendarState(String campusCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarState", null, null)) {
            return getNextDecorator().getHolidayCalendarState(campusCalendarStateKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarStates", null, null)) {
            return getNextDecorator().getHolidayCalendarStates(context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public HolidayCalendarInfo getHolidayCalendar(String campusCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendar", null, null)) {
            return getNextDecorator().getHolidayCalendar(campusCalendarKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(List<String> campusCalendarKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarsByKeyList", null, null)) {
            return getNextDecorator().getHolidayCalendarsByIds(campusCalendarKeyList, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getHolidayCalendarIdsByType(String campusCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarKeysByType", null, null)) {
            return getNextDecorator().getHolidayCalendarIdsByType(campusCalendarTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByStartYear(Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCampusCalendarsByStartYear", null, null)) {
            return getNextDecorator().getHolidayCalendarsByStartYear(year, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> searchForHolidayCalendarIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCampusCalendarKeys", null, null)) {
            return getNextDecorator().searchForHolidayCalendarIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HolidayCalendarInfo> searchForHolidayCalendars(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForCampusCalendars", null, null)) {
            return getNextDecorator().searchForHolidayCalendars(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(String validationTypeKey, String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == contextInfo) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateCampusCalendar", null, null)) {
            return getNextDecorator().validateHolidayCalendar(validationTypeKey, holidayCalendarTypeKey, holidayCalendarInfo, contextInfo);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createCampusCalendar", null, null)) {
            return getNextDecorator().createHolidayCalendar(holidayCalendarTypeKey, holidayCalendarInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public HolidayCalendarInfo updateHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo campusCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateCampusCalendar", null, null)) {
            return getNextDecorator().updateHolidayCalendar(holidayCalendarTypeKey, campusCalendarInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteHolidayCalendar(String holidayCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCampusCalendar", null, null)) {
            return getNextDecorator().deleteHolidayCalendar(holidayCalendarId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermType", null, null)) {
            return getNextDecorator().getTermType(termTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypes", null, null)) {
            return getNextDecorator().getTermTypes(context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypesForAcademicCalendarType", null, null)) {
            return getNextDecorator().getTermTypesForAcademicCalendarType(academicCalendarTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypesForTermType", null, null)) {
            return getNextDecorator().getTermTypesForTermType(termTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermState", null, null)) {
            return getNextDecorator().getTermState(termStateKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermStates", null, null)) {
            return getNextDecorator().getTermStates(context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public TermInfo getTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTerm", null, null)) {
            return getNextDecorator().getTerm(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getTermsByIds(List<String> termKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermsByKeyList", null, null)) {
            return getNextDecorator().getTermsByIds(termKeyList, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getTermIdsByType(String termTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermKeysByType", null, null)) {
            return getNextDecorator().getTermIdsByType(termTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermsForAcademicCalendar", null, null)) {
            return getNextDecorator().getTermsForAcademicCalendar(academicCalendarKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getCurrentTerms(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCurrentTerms", null, null)) {
            return getNextDecorator().getCurrentTerms(processKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getIncludedTermsInTerm", null, null)) {
            return getNextDecorator().getIncludedTermsInTerm(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getContainingTerms(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getContainingTerms", null, null)) {
            return getNextDecorator().getContainingTerms(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> searchForTermIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForTermKeys", null, null)) {
            return getNextDecorator().searchForTermIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForTerms", null, null)) {
            return getNextDecorator().searchForTerms(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TermInfo createTerm(String termTypeKey, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createTerm", null, null)) {
            return getNextDecorator().createTerm(termTypeKey, termInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TermInfo updateTerm(String termId, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateTerm", null, null)) {
            return getNextDecorator().updateTerm(termId, termInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteTerm", null, null)) {
            return getNextDecorator().deleteTerm(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addTermToAcademicCalendar", null, null)) {
            return getNextDecorator().addTermToAcademicCalendar(academicCalendarKey, termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeTermFromAcademicCalendar", null, null)) {
            return getNextDecorator().removeTermFromAcademicCalendar(academicCalendarKey, termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo addTermToTerm(String termKey, String includedTermKey, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addTermToTerm", null, null)) {
            return getNextDecorator().addTermToTerm(termKey, includedTermKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo removeTermFromTerm(String termKey, String includedTermKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeTermFromTerm", null, null)) {
            return getNextDecorator().removeTermFromTerm(termKey, includedTermKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateType", null, null)) {
            return getNextDecorator().getKeyDateType(keyDateTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateTypesForTermType", null, null)) {
            return getNextDecorator().getKeyDateTypesForTermType(termTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDate", null, null)) {
            return getNextDecorator().getKeyDate(keyDateKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(List<String> keyDateKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesByKeyList", null, null)) {
            return getNextDecorator().getKeyDatesByIds(keyDateKeyList, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getKeyDateIdsByType(String keyDateTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateKeysByType", null, null)) {
            return getNextDecorator().getKeyDateIdsByType(keyDateTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForTerm", null, null)) {
            return getNextDecorator().getKeyDatesForTerm(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForTermByDate", null, null)) {
            return getNextDecorator().getKeyDatesForTermByDate(termKey, startDate, endDate, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> searchForKeyDateIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForKeyDateKeys", null, null)) {
            return getNextDecorator().searchForKeyDateIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForKeyDates", null, null)) {
            return getNextDecorator().searchForKeyDates(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationTypeKey, String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == contextInfo) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateKeyDate", null, null)) {
            return getNextDecorator().validateKeyDate(validationTypeKey, termId, keyDateTypeKey, keyDateInfo, contextInfo);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateKeyDate", null, null)) {
            return getNextDecorator().updateKeyDate(keyDateKey, keyDateInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteKeyDate", null, null)) {
            return getNextDecorator().deleteKeyDate(keyDateKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayType", null, null)) {
            return getNextDecorator().getHolidayType(holidayTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<String> searchForHolidayIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidayKeys", null, null)) {
            return getNextDecorator().searchForHolidayIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidays", null, null)) {
            return getNextDecorator().searchForHolidays(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationTypeKey, String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == contextInfo) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateHoliday", null, null)) {
            return getNextDecorator().validateHoliday(validationTypeKey, holidayCalendarId, holidayTypeKey, holidayInfo, contextInfo);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateHoliday", null, null)) {
            return getNextDecorator().updateHoliday(holidayKey, holidayInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteHoliday", null, null)) {
            return getNextDecorator().deleteHoliday(holidayKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRegistrationDateGroup", null, null)) {
            return getNextDecorator().getRegistrationDateGroup(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(String termKey, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateRegistrationDateGroup", null, null)) {
            return getNextDecorator().updateRegistrationDateGroup(termKey, registrationDateGroupInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionalDaysForTerm", null, null)) {
            return getNextDecorator().getInstructionalDaysForTerm(termKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

}
