package org.kuali.student.core.academiccalendar.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.constants.AcademicCalendarServiceConstants;
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

	private static Map<String, AcademicCalendarInfo> acCache = new HashMap<String, AcademicCalendarInfo>();
	private static Map<String, CampusCalendarInfo> ccCache = new HashMap<String, CampusCalendarInfo>();
	private static Map<String, TermInfo> termCache = new HashMap<String, TermInfo>();	
	private static Map<String, KeyDateInfo> keyDateCache = new HashMap<String, KeyDateInfo>();	


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
			// TODO: consider speeding up the list search by converting to a hashmap
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

		if(this.acCache.containsKey(academicCalendarKey)){
			AcademicCalendarInfo dupCalendar = this.acCache.get(academicCalendarKey);

			AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(dupCalendar);
			acBuilder.setEndDate(null);
			acBuilder.setStartDate(null);
			acBuilder.setKey(newAcademicCalendarKey);

			AcademicCalendarInfo  copiedCalendar  = acBuilder.build();
			acCache.put(newAcademicCalendarKey, copiedCalendar);
			return copiedCalendar;
		}else{
			throw new DoesNotExistException(academicCalendarKey);
		}

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
		List<String> campusCalendarKeys = new ArrayList<String>();

		for (CampusCalendarInfo ccInfo : ccCache.values()){
			if(ccInfo.getTypeKey().equals(campusCalendarTypeKey)){
				campusCalendarKeys.add(ccInfo.getKey());
			}
		}

		return campusCalendarKeys;
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (CampusCalendarInfo info : this.ccCache.values()) {
			if(info.getStartDate().getYear()== year.intValue()){
				campusCalendars.add(info);
			}
		}

		return campusCalendars;
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
		CampusCalendarInfo.Builder newCampusCalendarBuilder = new CampusCalendarInfo.Builder(campusCalendarInfo);
		newCampusCalendarBuilder.setKey(campusCalendarKey);
		CampusCalendarInfo newCampusCalendar = newCampusCalendarBuilder.build();
		ccCache.put(campusCalendarKey,newCampusCalendar);
		return newCampusCalendar;
	}

	@Override
	public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {

		CampusCalendarInfo.Builder campusCalendarBuilder = new CampusCalendarInfo.Builder(campusCalendarInfo);
		campusCalendarBuilder.setKey(campusCalendarKey);
		ccCache.remove(campusCalendarKey);
		ccCache.put(campusCalendarKey, campusCalendarBuilder.build());



		return campusCalendarInfo;
	}

	@Override
	public StatusInfo deleteCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo.Builder statusInfo = new StatusInfo.Builder();
		CampusCalendarInfo ccInfo=  ccCache.remove(campusCalendarKey);
		statusInfo.setSuccess(ccInfo.equals(null));

		return statusInfo.build();

	}

	@Override
	public TermInfo getTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return termCache.get(termKey);

	}

	@Override
	public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<TermInfo> matchingTerms =  new ArrayList<TermInfo>();
		for (TermInfo termInfo : this.termCache.values()) {	
			if ( termKeyList.contains(termInfo.getKey()) ){
				matchingTerms.add (termInfo);
			}
		}

		return matchingTerms;
	}

	@Override
	public List<String> getTermKeysByType(String termTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> matchingTermKeys =  new ArrayList<String>();
		for (TermInfo termInfo : termCache.values() ) {
			if ( termInfo.getTypeKey().equals(termTypeKey) ){
				matchingTermKeys.add(termInfo.getKey());
			}
		}
		return matchingTermKeys;
	}

	@Override
	public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return keyDateCache.get(keyDateKey);

	}

	@Override
	public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		for(String keyDate:keyDateKeyList){
			if(keyDateCache.containsKey(keyDate)){
				keyDates.add(keyDateCache.get(keyDate) );
			}else{
				throw new DoesNotExistException(keyDate);
			}
		}
		return keyDates;
	}

	@Override
	public List<String> getKeyDateKeysByType(String keyDateTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> keyDates = new ArrayList<String>();
		for(KeyDateInfo keyDate:keyDateCache.values()){
			if(keyDate.getTypeKey().equals(keyDateTypeKey) )
				keyDates.add(keyDate.getKey());
		}
		return keyDates;
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateKeyDate(String validationType,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public KeyDateInfo createKeyDateForCampusCalendar(String campusCalendarKey,
			String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public KeyDateInfo updateKeyDate(String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		// TODO implement after DateRange changes 
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
		//TODO after gettimg Toms feedback
		CampusCalendarInfo ccInfo = 	ccCache.get(campusCalendarKey);
		CampusCalendarInfo.Builder ccBuilder = new CampusCalendarInfo.Builder(ccInfo);
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

		TermInfo.Builder newTermInfoBuilder =  new TermInfo.Builder(termInfo);   
		newTermInfoBuilder.setKey(termKey);
		TermInfo newTermInfo = newTermInfoBuilder.build(); 
		termCache.put(termKey, newTermInfo);
		return newTermInfo;



	}

	@Override
	public TermInfo updateTerm(String termKey, TermInfo termInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
	
		TermInfo.Builder termInfoBuilder = new TermInfo.Builder(termInfo);
		termInfoBuilder.setKey(termKey);
		termCache.remove(termKey);
		TermInfo newTerm = termInfoBuilder.build();
		termCache.put(termKey, newTerm);
		return newTerm;
	}

	@Override
	public StatusInfo deleteTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		termCache.remove(termKey);
		
		StatusInfo.Builder ssBuilder =  new StatusInfo.Builder();
		ssBuilder.setSuccess(true);
		return ssBuilder.build();
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
	public List<TermInfo> getTermsForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		//TODO terms not there?
		
		AcademicCalendarInfo acInfo =  acCache.get(academicCalendarKey);
		return null;
		
	}

}
