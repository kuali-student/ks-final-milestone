package org.kuali.student.core.academiccalendar.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.core.academiccalendar.dto.CampusCalendarInfo;

import org.kuali.student.core.academiccalendar.dto.HolidayInfo;
import org.kuali.student.core.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.core.academiccalendar.dto.RegistrationDateGroupInfo;
import org.kuali.student.core.academiccalendar.dto.TermInfo;

import org.kuali.student.core.academiccalendar.service.AcademicCalendarService;
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
import org.kuali.student.test.utilities.MockHelper;

public class AcademicCalendarServiceMockImpl implements AcademicCalendarService {

	private Map<String, AcademicCalendarInfo> acCache = new HashMap<String, AcademicCalendarInfo>();
	private Map<String, CampusCalendarInfo> ccCache = new HashMap<String, CampusCalendarInfo>();
	
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
	public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		AcademicCalendarInfo acObject = this.acCache.get(academicCalendarKey);
		if (acObject == null) {
			throw new DoesNotExistException(academicCalendarKey);
		}
		return acObject;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(
			List<String> academicCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<AcademicCalendarInfo> infos = new ArrayList<AcademicCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {
			// TODO: consider speading up the list search by converting to a hashmap
			if (academicCalendarKeyList.contains(info.getKey())) {
				infos.add(info);
			}
		}
		return infos;

	}

	@Override
	public List<String> getAcademicCalendarKeysByType(
			String academicCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List <String> academicCalendarTypes = new ArrayList<String>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getTypeKey().equals(academicCalendarTypeKey)) {
				academicCalendarTypes.add(info.getKey());
			}
		}
		return academicCalendarTypes;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List <AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getStartDate().getYear() == year.intValue() ) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(
			String credentialProgramTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List <AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCredentialProgramTypeKey().equals(credentialProgramTypeKey) ) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(
			String credentialProgramTypeKey, Integer year, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List <AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCredentialProgramTypeKey().equals(credentialProgramTypeKey) && info.getStartDate().getYear()==year.intValue() ) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
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
		AcademicCalendarInfo.Builder builder = new AcademicCalendarInfo.Builder(academicCalendarInfo);
		MockHelper helper = new MockHelper();
		builder.setKey (academicCalendarKey);
		builder.setMetaInfo(helper.createMeta(context));
		AcademicCalendarInfo copy = builder.build();
		this.acCache.put(copy.getKey(), copy);
		return copy;
	}

	@Override
	public AcademicCalendarInfo updateAcademicCalendar(
			String academicCalendarKey,
			AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {
		AcademicCalendarInfo existingAC = this.acCache.get(academicCalendarKey);
		if (existingAC == null) {
			throw new DoesNotExistException(academicCalendarKey);
		}
		if (!academicCalendarInfo.getMetaInfo().getVersionInd().equals(
				existingAC.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"Updated by " + existingAC.getMetaInfo().getUpdateId() + " on "
					+ existingAC.getMetaInfo().getUpdateId() + " with version of "
					+ existingAC.getMetaInfo().getVersionInd());
		}
		MockHelper helper = new MockHelper();
		AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(academicCalendarInfo);
		acBuilder.setMetaInfo(helper.updateMeta(existingAC.getMetaInfo(), context));
		AcademicCalendarInfo copy = acBuilder.build();
		this.acCache.put(academicCalendarKey, copy);
		// mirroring what was done before immutable DTO's; why returning copy of copy?
		return new AcademicCalendarInfo.Builder(copy).build();
	}


	@Override
	public StatusInfo deleteAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo.Builder sBuilder = new StatusInfo.Builder();
		if(this.acCache.containsKey(academicCalendarKey)){
			this.acCache.remove(academicCalendarKey);
			sBuilder.setSuccess(Boolean.TRUE);
		}else{
			throw new DoesNotExistException(academicCalendarKey);
		}

		return sBuilder.build();

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
		// TODO sambit complete the impl later
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getKey().equals(academicCalendarKey) ) {

			}
		}
		return null;
	}

	@Override
	public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCampusCalendarKey().equals(campusCalendarKey)) {

				CampusCalendarInfo campusCalendar  =  ccCache.get(campusCalendarKey) ;
				return campusCalendar;
			}
		}
		throw new DoesNotExistException(campusCalendarKey);
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByKeyList(
			List<String> campusCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (   campusCalendarKeyList.contains(info.getCampusCalendarKey())    ) {

				campusCalendars.add( ccCache.get(info.getCampusCalendarKey()) );

			}
		}
		return campusCalendars;
	}

	@Override
	public List<String> getCampusCalendarKeysByType(
			String campusCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {


		return null;
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {
//			if (info.getCampusCalendar().getStartDate().getYear()==(year.intValue())) {
//				campusCalendars.add(info.getCampusCalendar());
//			}
		}
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
	
		if(! campusCalendarInfo.getKey().equals(campusCalendarKey)){
			throw new DataValidationErrorException(campusCalendarKey);
		}
		
		for (AcademicCalendarInfo info : this.acCache.values()) {
			if (info.getCampusCalendarKey().equals(campusCalendarKey)) {
				
				CampusCalendarInfo.Builder campusCalendarBuilder = new CampusCalendarInfo.Builder(campusCalendarInfo);
				AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(info);
				acBuilder.setCampusCalendarKey(campusCalendarKey) ;
				AcademicCalendarInfo newAcademicCalendar= acBuilder.build();
				acCache.remove(info.getKey());
				acCache.put(newAcademicCalendar.getKey(), newAcademicCalendar );
				ccCache.remove(campusCalendarKey);
				ccCache.put(campusCalendarKey, campusCalendarBuilder.build());
				
			}
		}
		return campusCalendarInfo;
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
	public TermInfo getTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return null;
		
	}

	@Override
	public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		List<TermInfo> matchingTerms =  new ArrayList<TermInfo>();
//		for (AcademicCalendarInfo info : this.acCache.values()) {
//			 List<TermInfo>  terms = info.getTerms();
//			 for (TermInfo termInfo : terms ) {
//				
//				 if ( termKeyList.contains(termInfo.getKey()) ){
//					 matchingTerms.add (termInfo);
//				}
//			}
//		}
		
		return null;
	}

	@Override
	public List<String> getTermKeysByType(String termTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	
		
		List<String> matchingTermKeys =  new ArrayList<String>();
//		for (AcademicCalendarInfo info : this.acCache.values()) {
//			 List<TermInfo>  terms = info.getTerms();
//			 for (TermInfo termInfo : terms ) {
//				
//				 if ( termInfo.getTypeKey().equals(termTypeKey) ){
//					 matchingTermKeys.add(termInfo.getKey());
//				}
//			}
//		}
		
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
	public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
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

	@Override
	public KeyDateInfo createKeyDateForCampusCalendar(String campusCalendarKey,
			String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

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
	public Integer getInstructionalDaysForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
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
	public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
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
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TermInfo> getTermsForAcademicCalendar(
			List<String> academicCalendar, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
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

}
