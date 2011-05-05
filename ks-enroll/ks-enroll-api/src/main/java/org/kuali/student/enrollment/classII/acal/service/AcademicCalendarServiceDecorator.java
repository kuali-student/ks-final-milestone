package org.kuali.student.enrollment.classII.acal.service;

import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.classII.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.classII.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.classII.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.classII.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.classII.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.classII.acal.dto.TermInfo;
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



public abstract class AcademicCalendarServiceDecorator implements
AcademicCalendarService {
	protected AcademicCalendarService nextDecorator;
	
    public AcademicCalendarService getNextDecorator() {
        return nextDecorator;
    }
   
    public void setNextDecorator(AcademicCalendarService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return this.nextDecorator.getAcademicCalendarState(academicCalendarStateKey, context);
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
		return this.nextDecorator.getAcademicCalendarStates(context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
    throws OperationFailedException, MissingParameterException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
    	return this.nextDecorator.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
            ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
       return this.getDataDictionaryEntry(entryKey, context);
    }

          @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return this.nextDecorator.getAcademicCalendarType(academicCalendarTypeKey, context);
        
    }

    @Override                                        
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) 
    throws InvalidParameterException, 
    MissingParameterException, 
    OperationFailedException {
    	return this.nextDecorator.getAcademicCalendarTypes(context);
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.getAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(
            List<String> academicCalendarKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
    	return this.nextDecorator.getAcademicCalendarsByKeyList(academicCalendarKeyList, context);
    }

    @Override
    public List<String> getAcademicCalendarKeysByType(
            String academicCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
    	return this.nextDecorator.getAcademicCalendarKeysByType(academicCalendarTypeKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByYear(Integer year,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getAcademicCalendarsByYear(year, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(
            String credentialProgramTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getAcademicCalendarsByCredentialProgramType(credentialProgramTypeKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(
            String credentialProgramTypeKey, Integer year, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
     	return this.nextDecorator.getAcademicCalendarsByCredentialProgramTypeForYear(credentialProgramTypeKey,year, context);
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(
            String validationType, AcademicCalendarInfo academicCalendarInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
    	return this.nextDecorator.validateAcademicCalendar(validationType,academicCalendarInfo, context);
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(
            String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createAcademicCalendar(academicCalendarKey,academicCalendarInfo, context);
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(
            String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
    	return this.nextDecorator.updateAcademicCalendar(academicCalendarKey,academicCalendarInfo, context);
    }

    @Override
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteAcademicCalendar(academicCalendarKey, context);
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(
            String academicCalendarKey, String newAcademicCalendarKey,
            ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.copyAcademicCalendar(academicCalendarKey,newAcademicCalendarKey, context);
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarKey,
            String calendarDataFormatTypeKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
    	return this.nextDecorator.getAcademicCalendarData(academicCalendarKey,calendarDataFormatTypeKey, context);
    }

    @Override
    public TypeInfo getCampusCalendarType(String campusCalendarTypeKey, ContextInfo context) throws DoesNotExistException,InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getCampusCalendarType(campusCalendarTypeKey, context);
    }

    @Override
    public List<TypeInfo> getCampusCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getCampusCalendarTypes( context);
    }

    @Override
    public StateInfo getCampusCalendarState(String campusCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return this.nextDecorator.getCampusCalendarState(campusCalendarStateKey, context);
    }

    @Override
    public List<StateInfo> getCampusCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
		return this.nextDecorator.getCampusCalendarStates(context);
    }

    @Override
    public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
    	return this.nextDecorator.getCampusCalendar(campusCalendarKey, context);
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByKeyList(
            List<String> campusCalendarKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getCampusCalendarsByKeyList(campusCalendarKeyList, context);
    }

    @Override
    public List<String> getCampusCalendarKeysByType(
            String campusCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getCampusCalendarKeysByType(campusCalendarTypeKey, context);
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getCampusCalendarsByYear(year, context);
    }

    @Override
    public List<ValidationResultInfo> validateCampusCalendar(
            String validationType, CampusCalendarInfo campusCalendarInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
    	return this.nextDecorator.validateCampusCalendar(validationType, campusCalendarInfo, context);
    }

    @Override
    public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
            CampusCalendarInfo campusCalendarInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
    }

    @Override
    public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
            CampusCalendarInfo campusCalendarInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
    	return this.nextDecorator.updateCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
    }

    @Override
    public StatusInfo deleteCampusCalendar(String campusCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.deleteCampusCalendar(campusCalendarKey, context);
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getTermType(termTypeKey, context);
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getTermTypes(context);
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getTermTypesForAcademicCalendarType(academicCalendarTypeKey, context);
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getTermTypesForTermType(termTypeKey, context);
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return this.nextDecorator.getTermState(termStateKey, context);
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
		return this.nextDecorator.getTermStates(context);
    }

    @Override
    public TermInfo getTerm(String termKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
    	return this.nextDecorator.getTerm(termKey, context);
    }

    @Override
    public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getTermsByKeyList(termKeyList, context);
    }

    @Override
    public List<String> getTermKeysByType(String termTypeKey,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getTermKeysByType(termTypeKey, context);
    }

    @Override
    public List<TermInfo> getTermsForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getTermsForTerm(termKey, context);
    }

    @Override
    public TermInfo getParentTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getParentTerm(termKey, context);
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationType,
            TermInfo termInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateTerm(validationType,termInfo, context);
    }

    @Override
    public TermInfo createTerm(String termKey, TermInfo termInfo,
            ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.createTerm(termKey,termInfo, context);
    }

    @Override
    public TermInfo updateTerm(String termKey, TermInfo termInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateTerm(termKey,termInfo, context);
    }

    @Override
    public StatusInfo deleteTerm(String termKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
    	return this.nextDecorator.deleteTerm(termKey,context);
    }

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException{
    	return this.nextDecorator.addTermToAcademicCalendar(academicCalendarKey, termKey, context);
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws  DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.removeTermFromAcademicCalendar(academicCalendarKey, termKey, context);
    }

    @Override
    public StatusInfo addTermToTerm(String parentTermKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException {
    	return this.nextDecorator.addTermToTerm(parentTermKey, termKey, context);
    }

    @Override
    public StatusInfo removeTermFromTerm(String parentTermKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.removeTermFromTerm(parentTermKey, termKey, context);
    }
				    
    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getKeyDateType(keyDateTypeKey,context);
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.nextDecorator.getKeyDateTypesForTermType(termTypeKey,context);
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
    	return this.nextDecorator.getKeyDate(keyDateKey,context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getKeyDatesByKeyList(keyDateKeyList,context);
    }

    @Override
    public List<String> getKeyDateKeysByType(String keyDateTypeKey,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getKeyDateKeysByType(keyDateTypeKey,context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
            String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getKeyDatesForAcademicCalendar(academicCalendarKey,context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(
            String academicCalendarKey, Date startDate, Date endDate,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getKeyDatesForAcademicCalendarByDate(academicCalendarKey,startDate,endDate, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getKeyDatesForTerm(termKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
            Date startDate, Date endDate, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getKeyDatesForTermByDate(termKey,startDate, endDate, context);
    }

    @Override
    public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getAllKeyDatesForTerm(termKey, context);
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey,
            Date startDate, Date endDate, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getKeyDatesForAllTermsByDate(termKey,startDate, endDate, context);
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationType,
            KeyDateInfo keyDateInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateKeyDate(validationType,keyDateInfo , context);
    }

    @Override
    public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
            KeyDateInfo keyDateInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.createKeyDateForTerm(termKey,keyDateKey, keyDateInfo , context);
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey,
            KeyDateInfo keyDateInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
    	return this.nextDecorator.updateKeyDate(keyDateKey, keyDateInfo , context);
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
    	return this.nextDecorator.deleteKeyDate(keyDateKey,  context);
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, 
            ContextInfo context) 
    throws DoesNotExistException, 
    InvalidParameterException, 
    MissingParameterException, 
    OperationFailedException {
    	return this.nextDecorator.getHolidayType(holidayTypeKey,  context);
    }

    @Override
    public List<TypeInfo> getHolidayTypesForCampusCalendarType(String campusCalendarTypeKey, 
            ContextInfo context) 
            throws DoesNotExistException, 
            InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException {
    	return this.nextDecorator.getHolidayTypesForCampusCalendarType(campusCalendarTypeKey,  context);
    }

    @Override
    public List<HolidayInfo> getHolidaysForAcademicCalendar(
            String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getHolidaysForAcademicCalendar(academicCalendarKey,  context);
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationType,
            HolidayInfo holidayInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
    	return this.nextDecorator.validateHoliday(validationType,holidayInfo,  context);
    }

    @Override
    public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
            String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
      	return this.nextDecorator.createHolidayForCampusCalendar(campusCalendarKey,holidayKey,holidayInfo,  context);
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey,
            HolidayInfo holidayInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
    	return this.nextDecorator.updateHoliday(holidayKey,holidayInfo,  context);
    }

    @Override
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
    	return this.nextDecorator.deleteHoliday(holidayKey,context);
    }

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getRegistrationDateGroup(termKey,context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationDateGroup(
            String validationType,
            RegistrationDateGroupInfo registrationDateGroupInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
    	return this.nextDecorator.validateRegistrationDateGroup(validationType,registrationDateGroupInfo, context);
    }

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(
            String termKey,
            RegistrationDateGroupInfo registrationDateGroupInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
    	return this.nextDecorator.updateRegistrationDateGroup(termKey,registrationDateGroupInfo, context);
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	return this.nextDecorator.getInstructionalDaysForTerm(termKey, context);
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(
            String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	return this.nextDecorator.getTermsForAcademicCalendar(academicCalendarKey, context);
    }





}
