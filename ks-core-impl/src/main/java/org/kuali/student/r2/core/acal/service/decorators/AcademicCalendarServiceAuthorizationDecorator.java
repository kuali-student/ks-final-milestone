package org.kuali.student.r2.core.acal.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.Date;
import java.util.List;

public class AcademicCalendarServiceAuthorizationDecorator 
    extends AcademicCalendarServiceDecorator 
    implements HoldsPermissionService {

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
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarType", null)) {
            return getNextDecorator().getAcademicCalendarType(academicCalendarTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarTypes", null)) {
            return getNextDecorator().getAcademicCalendarTypes(context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarState", null)) {
            return getNextDecorator().getAcademicCalendarState(academicCalendarStateKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarStates", null)) {
            return getNextDecorator().getAcademicCalendarStates(context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendar", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByKeyList", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarIdsByType", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarsByStartYear", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAcademicCalendarIds", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAcademicCalendars", null)) {
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

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAcademicCalendar", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAcademicCalendar", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAcademicCalendar", null)) {
            return getNextDecorator().deleteAcademicCalendar(academicCalendarKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == contextInfo) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "copyAcademicCalendar", null)) {
            return getNextDecorator().copyAcademicCalendar(academicCalendarId, startDate, endDate, contextInfo);
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAcademicCalendarData", null)) {
            return getNextDecorator().getAcademicCalendarData(academicCalendarKey, calendarDataFormatTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarType", null)) {
            return getNextDecorator().getHolidayCalendarType(holidayCalendarTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarTypes", null)) {
            return getNextDecorator().getHolidayCalendarTypes(context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StateInfo getHolidayCalendarState(String holidayCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarState", null)) {
            return getNextDecorator().getHolidayCalendarState(holidayCalendarStateKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarStates", null)) {
            return getNextDecorator().getHolidayCalendarStates(context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public HolidayCalendarInfo getHolidayCalendar(String holidayCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendar", null)) {
            return getNextDecorator().getHolidayCalendar(holidayCalendarId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(List<String> holidayCalendarIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarsByKeyList", null)) {
            return getNextDecorator().getHolidayCalendarsByIds(holidayCalendarIds, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getHolidayCalendarIdsByType(String holidayCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarIdsByType", null)) {
            return getNextDecorator().getHolidayCalendarIdsByType(holidayCalendarTypeKey, context);
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayCalendarsByStartYear", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidayCalendarIds", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidayCalendars", null)) {
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

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateHolidayCalendar", null)) {
            return getNextDecorator().validateHolidayCalendar(validationTypeKey, holidayCalendarTypeKey, holidayCalendarInfo, contextInfo);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createHolidayCalendar", null)) {
            return getNextDecorator().createHolidayCalendar(holidayCalendarTypeKey, holidayCalendarInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public HolidayCalendarInfo updateHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateHolidayCalendar", null)) {
            return getNextDecorator().updateHolidayCalendar(holidayCalendarTypeKey, holidayCalendarInfo, context);
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteHolidayCalendar", null)) {
            return getNextDecorator().deleteHolidayCalendar(holidayCalendarId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermType", null)) {
            return getNextDecorator().getTermType(termTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypes", null)) {
            return getNextDecorator().getTermTypes(context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypesForAcademicCalendarType", null)) {
            return getNextDecorator().getTermTypesForAcademicCalendarType(academicCalendarTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermTypesForTermType", null)) {
            return getNextDecorator().getTermTypesForTermType(termTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermState", null)) {
            return getNextDecorator().getTermState(termStateKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, 
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermStates", null)) {
            return getNextDecorator().getTermStates(context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public TermInfo getTerm(String termId, ContextInfo context) throws DoesNotExistException, 
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTerm", null)) {
            return getNextDecorator().getTerm(termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getTermsByIds(List<String> termIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermsByKeyList", null)) {
            return getNextDecorator().getTermsByIds(termIds, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getTermIdsByType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermIdsByType", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTermsForAcademicCalendar", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getCurrentTerms", null)) {
            return getNextDecorator().getCurrentTerms(processKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getIncludedTermsInTerm", null)) {
            return getNextDecorator().getIncludedTermsInTerm(termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<TermInfo> getContainingTerms(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getContainingTerms", null)) {
            return getNextDecorator().getContainingTerms(termId, context);
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForTermIds", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForTerms", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createTerm", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateTerm", null)) {
            return getNextDecorator().updateTerm(termId, termInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteTerm", null)) {
            return getNextDecorator().deleteTerm(termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addTermToAcademicCalendar", null)) {
            return getNextDecorator().addTermToAcademicCalendar(academicCalendarKey, termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeTermFromAcademicCalendar", null)) {
            return getNextDecorator().removeTermFromAcademicCalendar(academicCalendarKey, termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo addTermToTerm(String termId, String includedTermId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addTermToTerm", null)) {
            return getNextDecorator().addTermToTerm(termId, includedTermId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo removeTermFromTerm(String termId, String includedTermId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "removeTermFromTerm", null)) {
            return getNextDecorator().removeTermFromTerm(termId, includedTermId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, 
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateType", null)) {
            return getNextDecorator().getKeyDateType(keyDateTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateTypesForTermType", null)) {
            return getNextDecorator().getKeyDateTypesForTermType(termTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDate", null)) {
            return getNextDecorator().getKeyDate(keyDateId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(List<String> keyDateIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesByKeyList", null)) {
            return getNextDecorator().getKeyDatesByIds(keyDateIds, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getKeyDateIdsByType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDateIdsByType", null)) {
            return getNextDecorator().getKeyDateIdsByType(keyDateTypeKey, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForTerm", null)) {
            return getNextDecorator().getKeyDatesForTerm(termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termId, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getKeyDatesForTermByDate", null)) {
            return getNextDecorator().getKeyDatesForTermByDate(termId, startDate, endDate, context);
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForKeyDateIds", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForKeyDates", null)) {
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

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateKeyDate", null)) {
            return getNextDecorator().validateKeyDate(validationTypeKey, termId, keyDateTypeKey, keyDateInfo, contextInfo);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateId, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateKeyDate", null)) {
            return getNextDecorator().updateKeyDate(keyDateId, keyDateInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteKeyDate", null)) {
            return getNextDecorator().deleteKeyDate(keyDateId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHolidayType", null)) {
            return getNextDecorator().getHolidayType(holidayTypeKey, context);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public List<String> searchForHolidayIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidayKeys", null)) {
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

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolidays", null)) {
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

        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateHoliday", null)) {
            return getNextDecorator().validateHoliday(validationTypeKey, holidayCalendarId, holidayTypeKey, holidayInfo, contextInfo);
        } else {
            throw new PermissionDeniedException ();
        }
    }

    @Override
    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateHoliday", null)) {
            return getNextDecorator().updateHoliday(holidayId, holidayInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteHoliday(String holidayId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteHoliday", null)) {
            return getNextDecorator().deleteHoliday(holidayId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionalDaysForTerm", null)) {
            return getNextDecorator().getInstructionalDaysForTerm(termId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

}
