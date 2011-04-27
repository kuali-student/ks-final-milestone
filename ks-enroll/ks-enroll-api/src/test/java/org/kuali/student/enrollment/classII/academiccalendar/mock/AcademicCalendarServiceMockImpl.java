package org.kuali.student.enrollment.classII.academiccalendar.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.kuali.student.enrollment.classII.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.HolidayInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.TermInfo;
import org.kuali.student.enrollment.classII.academiccalendar.service.AcademicCalendarService;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
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
import org.kuali.student.r2.common.infc.DateRange;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.classI.atp.dto.AtpInfo;
import org.kuali.student.r2.core.classI.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.classI.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.classI.atp.service.AtpService;
import org.kuali.student.test.utilities.MockHelper;

public class AcademicCalendarServiceMockImpl implements AcademicCalendarService {

	//Mock caches, these are just convenient caches which act like databases for the mocks, please note that all of this might not map directly to tables,
	// for example holidaysCache might be just a Milestone table in the DB
	private static Map<String, AcademicCalendarInfo> acCache = new HashMap<String, AcademicCalendarInfo>();
	private static Map<String, CampusCalendarInfo> ccCache = new HashMap<String, CampusCalendarInfo>();
	private static Map<String, TermInfo> termsCache = new HashMap<String, TermInfo>();	
	private static Map<String, KeyDateInfo> keyDateCache = new HashMap<String, KeyDateInfo>();	
	private static Map<String, HolidayInfo> holidaysCache = new HashMap<String, HolidayInfo>();
	private static Map<String,TypeTypeRelationInfo> typeTypeCache = new HashMap<String, TypeTypeRelationInfo>();
	private static Map<String, MilestoneInfo> milestoneCache = new HashMap<String, MilestoneInfo>();


	private DataDictionaryValidator dataDictionaryValidator;

	private AtpService atpService;
	
	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}



	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
	throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		return this.atpService.getDataDictionaryEntry(entryKey, context);
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
	throws OperationFailedException, MissingParameterException, PermissionDeniedException {
		return this.atpService.getDataDictionaryEntryKeys(context);
	}

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.atpService.getState(null , academicCalendarStateKey, context);
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
      
    	return this.atpService.getStatesByProcess(null, context) ;
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
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		try {
			validationResult = this.dataDictionaryValidator.validate(  DataDictionaryValidator.ValidationType.fromString(validationType),academicCalendarInfo, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException(e.getMessage());
		}
		return validationResult;
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
		if(acCache.containsKey(academicCalendarKey)){
			acCache.remove(academicCalendarKey);
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
    public StateInfo getCampusCalendarState(String campusCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<StateInfo> getCampusCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    // why not just check the cache?
		for (AcademicCalendarInfo info : this.acCache.values()) {

		    if ((info.getCampusCalendarKeys() != null) 
			&& info.getCampusCalendarKeys().contains(campusCalendarKey)) {
			CampusCalendarInfo campusCalendar = ccCache.get(campusCalendarKey);
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
		for (String key : campusCalendarKeyList) {
		    if ((info.getCampusCalendarKeys() != null) 
			&& info.getCampusCalendarKeys().contains(key)) {
			campusCalendars.add(ccCache.get(key));
		    }
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
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		try {
			validationResult = this.dataDictionaryValidator.validate(  DataDictionaryValidator.ValidationType.fromString(validationType),campusCalendarInfo, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException(e.getMessage());
		}
		return validationResult;
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
		statusInfo.setSuccess(ccInfo==null);

		return statusInfo.build();

	}

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public TermInfo getTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return termsCache.get(termKey);

	}

	@Override
	public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<TermInfo> matchingTerms =  new ArrayList<TermInfo>();
		for (TermInfo termInfo : this.termsCache.values()) {	
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
		for (TermInfo termInfo : termsCache.values() ) {
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

		return  this.atpService.getMilestoneKeysByType(keyDateTypeKey, context);

	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

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
	
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		try {
			validationResult = this.dataDictionaryValidator.validate(  DataDictionaryValidator.ValidationType.fromString(validationType),keyDateInfo, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException(e.getMessage());
		}
		return validationResult;
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

		AcademicCalendarInfo acInfo = acCache.get(academicCalendarKey);
		CampusCalendarInfo ccInfo =  ccCache.get(acInfo.getKey());
		List<MilestoneInfo> milestonesForAtp = this.atpService.getMilestonesByAtp(ccInfo.getKey(), context);
		List<HolidayInfo> holidays = new ArrayList<HolidayInfo>();
		// TODO convert milestonesForAtp to HolidayInfo list
		return holidays;
	}

	@Override
	public List<ValidationResultInfo> validateHoliday(String validationType,
			HolidayInfo holidayInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		try {
			validationResult = this.dataDictionaryValidator.validate(  DataDictionaryValidator.ValidationType.fromString(validationType),holidayInfo, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException(e.getMessage());
		}
		return validationResult;
	}

	@Override
	public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
			String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {

		if( !holidaysCache.containsKey(holidayKey)){
			holidaysCache.put(holidayKey,holidayInfo );
		}

		CampusCalendarInfo ccInfo = 	ccCache.get(campusCalendarKey);

		AtpMilestoneRelationInfo.Builder atpMilestoneRelationInfoBuilder = new AtpMilestoneRelationInfo.Builder();
		atpMilestoneRelationInfoBuilder.setMilestoneKey(holidayKey);
		atpMilestoneRelationInfoBuilder.setAtpKey(campusCalendarKey);

		AtpMilestoneRelationInfo newAtpInfo = this.atpService.createAtpMilestoneRelation(atpMilestoneRelationInfoBuilder.build(), context);

		return  holidaysCache.get(newAtpInfo.getMilestoneKey()) ;
	}

	@Override
	public HolidayInfo updateHoliday(String holidayKey,
			HolidayInfo holidayInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {

		holidaysCache.remove(holidayKey);
		holidaysCache.put(holidayKey, holidayInfo);
		return  holidayInfo;
	}

	@Override
	public StatusInfo deleteHoliday(String holidayKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {

		holidaysCache.remove(holidayKey);

		List <AtpMilestoneRelationInfo> atpMilestoneRelations =  this.atpService.getAtpMilestoneRelationsByMilestone(holidayKey, context);

		for(AtpMilestoneRelationInfo atpMilestoneRelation: atpMilestoneRelations){
			this.atpService.deleteAtpMilestoneRelation(atpMilestoneRelation.getId(), context);
		}


		StatusInfo.Builder statInfoBuilder = new StatusInfo.Builder();
		statInfoBuilder.setSuccess(true);
		return statInfoBuilder.build();
	}



	@Override
	public Integer getInstructionalDaysForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		DateRange classDates = getRegistrationDateGroup(termKey, context).getClassDateRange();
		int numInstructionalDays = 0;
		Calendar startCalendar = new GregorianCalendar(classDates.getStart().getYear(), classDates.getStart().getMonth(), classDates.getStart().getDate());
		Calendar endCalendar = new GregorianCalendar(classDates.getEnd().getYear(), classDates.getStart().getMonth(), classDates.getEnd().getDate());

		//TODO Use some open source library like joda-time to compute these
		// 
		//TODO  Finally the calendar should remove weekend days, non-instructional holidays - any other days such as exams which are not instructional

		return Integer.valueOf(numInstructionalDays);
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(
			String academicCalendarKey, Date startDate, Date endDate,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<MilestoneInfo> mileStoneInfos =  this.atpService.getMilestonesByAtp(academicCalendarKey, context);
		//convert MileStones to KeyDateInfo 
		return new ArrayList<KeyDateInfo>();



	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		List<MilestoneInfo> milestones =  this.atpService.getMilestonesByAtp(termKey, context);
		for(MilestoneInfo milestoneInfo : milestones){
			if(milestoneInfo.getStartDate().after(startDate)&& milestoneInfo.getEndDate().before(endDate)){
				//convert milestone to keydate and add it to this List
				keyDates.add(null);
			}
		}
		return keyDates;
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		
		//Because KeyDates are Milestones
		List<MilestoneInfo> milestones =  this.atpService.getMilestonesByAtp(termKey, context);
		//Add subterm milestones
		List<TermInfo> allTerms = getTermsForTerm(termKey, context) ;
		for(TermInfo  subTerm: allTerms){
			List<MilestoneInfo> subMilestones =  this.atpService.getMilestonesByAtp(subTerm.getKey(), context);
			milestones.addAll(subMilestones);
			
		}
		
		for(MilestoneInfo milestoneInfo : milestones){
			if(milestoneInfo.getStartDate().equals(startDate)&& milestoneInfo.getEndDate().equals(endDate)){
				keyDates.add(null);
			}
		}
		
		return keyDates;

	}

	@Override
	public List<TermInfo> getTermsForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		TermInfo termInfo = termsCache.get(termKey);
		List <TermInfo> termsToReturn = new ArrayList<TermInfo>();
		List<TypeTypeRelationInfo> typesRelations  = this.atpService.getTypeRelationsByOwnerType(termInfo.getTypeKey(),"kuali.relationtype.contains" , context);
		////Filter out types from typesRelations which are not campus calendars or dates etc
		for(TypeTypeRelationInfo typeRelation:typesRelations){
			
			String relatedTypeKey =  typeRelation.getRelatedTypeKey();
			List<TermInfo> termInfos = (List<TermInfo>) termsCache.values();
			for(TermInfo relatedTermInfo :termInfos){
				if(relatedTermInfo.getTypeKey().equals(relatedTypeKey)){
					termsToReturn.add(relatedTermInfo);
				}
			}
		}
		
		return termsToReturn;
	}

    @Override
    public TermInfo getParentTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
	return null;
    }

	@Override
	public List<ValidationResultInfo> validateTerm(String validationType,
			TermInfo termInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		try {
			validationResult = this.dataDictionaryValidator.validate(  DataDictionaryValidator.ValidationType.fromString(validationType),termInfo, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException(e.getMessage());
		}
		return validationResult;
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
		termsCache.put(termKey, newTermInfo);
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
		termsCache.remove(termKey);
		TermInfo newTerm = termInfoBuilder.build();
		termsCache.put(termKey, newTerm);
		return newTerm;
	}

	@Override
	public StatusInfo deleteTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		termsCache.remove(termKey);

		StatusInfo.Builder ssBuilder =  new StatusInfo.Builder();
		ssBuilder.setSuccess(true);
		return ssBuilder.build();
	}

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
	return null;
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws  DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
	return null;
    }

    @Override
    public StatusInfo addTermToTerm(String parentTermKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
	return null;
    }

    @Override
    public StatusInfo removeTermFromTerm(String parentTermKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		try {
			validationResult = this.dataDictionaryValidator.validate(  DataDictionaryValidator.ValidationType.fromString(validationType),registrationDateGroupInfo, context);
		} catch (PermissionDeniedException e) {
			throw new OperationFailedException(e.getMessage());
		}
		return validationResult;
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

	
		AcademicCalendarInfo acInfo =  this.acCache.get(academicCalendarKey);
		List <TermInfo> termsToReturn = new ArrayList<TermInfo>();
		String typeKey = acInfo.getTypeKey();
		
	
		List<TypeTypeRelationInfo> typesRelations  = this.atpService.getTypeRelationsByOwnerType(typeKey,"kuali.relationtype.contains" , context);
		//Filter out types from typesRelations which are not campus calendars or dates etc
		for(TypeTypeRelationInfo typeRelation: typesRelations){
		
			String relatedTypeKey =  typeRelation.getRelatedTypeKey();
			List<TermInfo> termInfos = (List<TermInfo>) termsCache.values();
			for(TermInfo relatedTermInfo :termInfos){
				if(relatedTermInfo.getTypeKey().equals(relatedTypeKey)){
					termsToReturn.add(relatedTermInfo);
				}
			}
		}
		
;
		return termsToReturn;

	}

	@Override
	public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		AcademicCalendarInfo acInfo = acCache.get(academicCalendarTypeKey);
		return this.atpService.getType(acInfo.getTypeKey() , context);

	}

	@Override
	public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context)
	throws InvalidParameterException, MissingParameterException,
	OperationFailedException {

		//return from DB
		return null;
	}

	@Override
	public TypeInfo getCampusCalendarType(String campusCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		CampusCalendarInfo ccInfo = ccCache.get(campusCalendarTypeKey);
		return this.atpService.getType(ccInfo.getTypeKey() , context);

	}

	@Override
	public List<TypeInfo> getCampusCalendarTypes(ContextInfo context)
	throws InvalidParameterException, MissingParameterException,
	OperationFailedException {

		//Return from DB
		return null;
	}

	@Override
	public TypeInfo getTermType(String termTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
		TermInfo tInfo = termsCache.get(termTypeKey);
		return this.atpService.getType(tInfo.getTypeKey() , context);
	}

	@Override
	public List<TypeInfo> getTermTypes(ContextInfo context)
	throws InvalidParameterException, MissingParameterException,
	OperationFailedException {
		//Return from DB
		return null;
	}

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
	return null;
    }

	@Override
	public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
	
		return null;
	}

	@Override
	public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getHolidayTypesForCampusCalendarType(
			String campusCalendarTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}
}
