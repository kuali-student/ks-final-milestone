package org.kuali.student.enrollment.acal.service;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
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

public abstract class AcademicCalendarServiceDecorator implements AcademicCalendarService {
    private AcademicCalendarService nextDecorator;

    public AcademicCalendarService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(AcademicCalendarService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getAcademicCalendarState(academicCalendarStateKey, context);
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return getNextDecorator().getAcademicCalendarStates(context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        return getNextDecorator().getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        return this.getDataDictionaryEntry(entryKey, context);
    } 

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getAcademicCalendarType(academicCalendarTypeKey, context);

    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return getNextDecorator().getAcademicCalendarTypes(context);
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(List<String> academicCalendarKeyList,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAcademicCalendarsByKeyList(academicCalendarKeyList, context);
    }

    @Override
    public List<String> getAcademicCalendarKeysByType(String academicCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAcademicCalendarKeysByType(academicCalendarTypeKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(Integer year, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAcademicCalendarsByStartYear(year, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(String credentialProgramTypeKey,
            ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAcademicCalendarsByCredentialProgramType(credentialProgramTypeKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForStartYear(
            String credentialProgramTypeKey, Integer year, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAcademicCalendarsByCredentialProgramTypeForStartYear(credentialProgramTypeKey,
                year, context);
    }

    @Override
    public List<String> searchForAcademicCalendarKeys(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForAcademicCalendarKeys(criteria, context);
    }

    @Override
    public List<AcademicCalendarInfo> searchForAcademicCalendars(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForAcademicCalendars(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationType,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateAcademicCalendar(validationType, academicCalendarInfo, context);
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
    }

    @Override
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarKey, String newAcademicCalendarKey,
            ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().copyAcademicCalendar(academicCalendarKey, newAcademicCalendarKey, context);
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarKey, String calendarDataFormatTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAcademicCalendarData(academicCalendarKey, calendarDataFormatTypeKey, context);
    }

    @Override
    public TypeInfo getCampusCalendarType(String campusCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getCampusCalendarType(campusCalendarTypeKey, context);
    }

    @Override
    public List<TypeInfo> getCampusCalendarTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return getNextDecorator().getCampusCalendarTypes(context);
    }

    @Override
    public StateInfo getCampusCalendarState(String campusCalendarStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getCampusCalendarState(campusCalendarStateKey, context);
    }

    @Override
    public List<StateInfo> getCampusCalendarStates(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return getNextDecorator().getCampusCalendarStates(context);
    }

    @Override
    public CampusCalendarInfo getCampusCalendar(String campusCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCampusCalendar(campusCalendarKey, context);
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByKeyList(List<String> campusCalendarKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCampusCalendarsByKeyList(campusCalendarKeyList, context);
    }

    @Override
    public List<String> getCampusCalendarKeysByType(String campusCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCampusCalendarKeysByType(campusCalendarTypeKey, context);
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByStartYear(Integer year, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCampusCalendarsByStartYear(year, context);
    }

    @Override
    public List<String> searchForCampusCalendarKeys(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCampusCalendarKeys(criteria, context);
    }

    @Override
    public List<CampusCalendarInfo> searchForCampusCalendars(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForCampusCalendars(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateCampusCalendar(String validationType,
            CampusCalendarInfo campusCalendarInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateCampusCalendar(validationType, campusCalendarInfo, context);
    }

    @Override
    public CampusCalendarInfo createCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
    }

    @Override
    public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
    }

    @Override
    public StatusInfo deleteCampusCalendar(String campusCalendarKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCampusCalendar(campusCalendarKey, context);
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getTermType(termTypeKey, context);
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return getNextDecorator().getTermTypes(context);
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getTermTypesForAcademicCalendarType(academicCalendarTypeKey, context);
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getTermTypesForTermType(termTypeKey, context);
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getTermState(termStateKey, context);
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return getNextDecorator().getTermStates(context);
    }

    @Override
    public TermInfo getTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTerm(termKey, context);
    }

    @Override
    public List<TermInfo> getTermsByKeyList(List<String> termKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTermsByKeyList(termKeyList, context);
    }

    @Override
    public List<String> getTermKeysByType(String termTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTermKeysByType(termTypeKey, context);
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTermsForAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getIncludedTermsInTerm(termKey, context);
    }

    @Override
    public List<TermInfo> getContainingTerms(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getContainingTerms(termKey, context);
    }

    @Override
    public List<String> searchForTermKeys(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForTermKeys(criteria, context);
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForTerms(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationType, TermInfo termInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateTerm(validationType, termInfo, context);
    }

    @Override
    public TermInfo createTerm(String termKey, TermInfo termInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createTerm(termKey, termInfo, context);
    }

    @Override
    public TermInfo updateTerm(String termKey, TermInfo termInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateTerm(termKey, termInfo, context);
    }

    @Override
    public StatusInfo deleteTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteTerm(termKey, context);
    }

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        return getNextDecorator().addTermToAcademicCalendar(academicCalendarKey, termKey, context);
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeTermFromAcademicCalendar(academicCalendarKey, termKey, context);
    }

    @Override
    public StatusInfo addTermToTerm(String termKey, String includedTermKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        return getNextDecorator().addTermToTerm(termKey, includedTermKey, context);
    }

    @Override
    public StatusInfo removeTermFromTerm(String termKey, String includedTermKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeTermFromTerm(termKey, includedTermKey, context);
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getKeyDateType(keyDateTypeKey, context);
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getKeyDateTypesForTermType(termTypeKey, context);
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getKeyDate(keyDateKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getKeyDatesByKeyList(keyDateKeyList, context);
    }

    @Override
    public List<String> getKeyDateKeysByType(String keyDateTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getKeyDateKeysByType(keyDateTypeKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getKeyDatesForAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(String academicCalendarKey, Date startDate,
            Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator()
                .getKeyDatesForAcademicCalendarByDate(academicCalendarKey, startDate, endDate, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getKeyDatesForTerm(termKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey, Date startDate, Date endDate, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getKeyDatesForTermByDate(termKey, startDate, endDate, context);
    }

    @Override
    public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllKeyDatesForTerm(termKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey, Date startDate, Date endDate,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getKeyDatesForAllTermsByDate(termKey, startDate, endDate, context);
    }

    @Override
    public List<String> searchForKeyDateKeys(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForKeyDateKeys(criteria, context);
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForKeyDates(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationType, KeyDateInfo keyDateInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateKeyDate(validationType, keyDateInfo, context);
    }

    @Override
    public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey, KeyDateInfo keyDateInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createKeyDateForTerm(termKey, keyDateKey, keyDateInfo, context);
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateKeyDate(keyDateKey, keyDateInfo, context);
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteKeyDate(keyDateKey, context);
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getHolidayType(holidayTypeKey, context);
    }

    @Override
    public List<TypeInfo> getHolidayTypesForCampusCalendarType(String campusCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getHolidayTypesForCampusCalendarType(campusCalendarTypeKey, context);
    }

    @Override
    public List<HolidayInfo> getHolidaysForAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHolidaysForAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public List<String> searchForHolidayKeys(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForHolidayKeys(criteria, context);
    }

    @Override
    public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForHolidays(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationType, HolidayInfo holidayInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateHoliday(validationType, holidayInfo, context);
    }

    @Override
    public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey, String holidayKey,
            HolidayInfo holidayInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createHolidayForCampusCalendar(campusCalendarKey, holidayKey, holidayInfo, context);
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateHoliday(holidayKey, holidayInfo, context);
    }

    @Override
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteHoliday(holidayKey, context);
    }

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRegistrationDateGroup(termKey, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationDateGroup(String validationType,
            RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateRegistrationDateGroup(validationType, registrationDateGroupInfo, context);
    }

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(String termKey,
            RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateRegistrationDateGroup(termKey, registrationDateGroupInfo, context);
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionalDaysForTerm(termKey, context);
    }

    @Override
    public List<TermInfo> getCurrentTerms(String processKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCurrentTerms( processKey, context);
    }
}
