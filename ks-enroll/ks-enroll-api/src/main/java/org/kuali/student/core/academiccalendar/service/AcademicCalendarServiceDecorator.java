package org.kuali.student.core.academiccalendar.service;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.core.academiccalendar.dto.CampusCalendarInfo;
import org.kuali.student.core.academiccalendar.dto.HolidayInfo;
import org.kuali.student.core.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.core.academiccalendar.dto.RegistrationDateGroupInfo;
import org.kuali.student.core.academiccalendar.dto.TermInfo;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
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


    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
    throws OperationFailedException, MissingParameterException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
            ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}
     */
    @Override
    public List<String> getProcessKeys(String typeKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StateInfo getNextHappyState(String processKey,
            String currentStateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override                                        
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) 
    throws InvalidParameterException, 
    MissingParameterException, 
    OperationFailedException {

        return null;
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(
            List<String> academicCalendarKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getAcademicCalendarKeysByType(
            String academicCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByYear(Integer year,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(
            String credentialProgramTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(
            String credentialProgramTypeKey, Integer year, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(
            String validationType, AcademicCalendarInfo academicCalendarInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(
            String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(
            String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(
            String academicCalendarKey, String newAcademicCalendarKey,
            ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarKey,
            String calendarDataFormatTypeKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeInfo getCampusCalendarType(String campusCalendarTypeKey, ContextInfo context) throws DoesNotExistException,InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeInfo> getCampusCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByKeyList(
            List<String> campusCalendarKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getCampusCalendarKeysByType(
            String campusCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCampusCalendar(
            String validationType, CampusCalendarInfo campusCalendarInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
            CampusCalendarInfo campusCalendarInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
            CampusCalendarInfo campusCalendarInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteCampusCalendar(String campusCalendarKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TermInfo getTerm(String termKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getTermKeysByType(String termTypeKey,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public List<TermInfo> getTermsForTerm(List<String> termCalendar,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationType,
            TermInfo termInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TermInfo createTerm(String termKey, TermInfo termInfo,
            ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TermInfo updateTerm(String termKey, TermInfo termInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteTerm(String termKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getKeyDateKeysByType(String keyDateTypeKey,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
            String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(
            String academicCalendarKey, Date startDate, Date endDate,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
            Date startDate, Date endDate, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey,
            Date startDate, Date endDate, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationType,
            KeyDateInfo keyDateInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
            KeyDateInfo keyDateInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }
    /*
	@Override
	public KeyDateInfo createKeyDateForCampusCalendar(String campusCalendarKey,
			String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
     */
    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey,
            KeyDateInfo keyDateInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, 
            ContextInfo context) 
    throws DoesNotExistException, 
    InvalidParameterException, 
    MissingParameterException, 
    OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeInfo> getHolidayTypesForCampusCalendarType(String campusCalendarTypeKey, 
            ContextInfo context) 
            throws DoesNotExistException, 
            InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<HolidayInfo> getHolidaysForAcademicCalendar(
            String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationType,
            HolidayInfo holidayInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
            String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey,
            HolidayInfo holidayInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationDateGroup(
            String validationType,
            RegistrationDateGroupInfo registrationDateGroupInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(
            String termKey,
            RegistrationDateGroupInfo registrationDateGroupInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termKey,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(
            String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }





}
