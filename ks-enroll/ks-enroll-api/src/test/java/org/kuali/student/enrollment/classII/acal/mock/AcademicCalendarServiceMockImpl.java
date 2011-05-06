package org.kuali.student.enrollment.classII.acal.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.classII.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.classII.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.classII.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.classII.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.classII.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.classII.acal.dto.TermInfo;
import org.kuali.student.enrollment.classII.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
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
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.classI.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.classI.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.classI.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.classI.atp.service.AtpService;
import org.kuali.student.test.utilities.MockHelper;


/**
 * Academic Calendar Service Mock Implementation
 *
 * This service implementation class is a mock which could be used for 
 * testing the service functionalities when the real implementation 
 * is not ready.
 * 
 * It contains many of the CRUD impl using local HashMap cache. Any test case 
 * should first populate all these caches with data and do the Spring configuration
 * so that the AtpService and dataDictionaryValidator are tied to the respective 
 * mock implementations with consistent data
 * 
 *  The suggestion on how to implement each of these methods/operations are 
 *  documented in the javadoc for each method (wherever applicable)
 *  
 *  
 * Version: 1.0 (Dev)
 *
 * @Author sambit
 * @Since Sun Apr 12 14:22:34 EDT 2011
 */


public class AcademicCalendarServiceMockImpl implements AcademicCalendarService {

	/** Mock caches, these are just convenient caches which act like databases
	* for the mocks, please note that all of this might not map directly to
	* tables, for example holidaysCache might be just a Milestone table in the DB
	**/
	
	private static Map<String, AcademicCalendarInfo> acCache = new HashMap<String, AcademicCalendarInfo>();
	private static Map<String, CampusCalendarInfo> ccCache = new HashMap<String, CampusCalendarInfo>();
	private static Map<String, TermInfo> termsCache = new HashMap<String, TermInfo>();
	private static Map<String, KeyDateInfo> keyDateCache = new HashMap<String, KeyDateInfo>();
	private static Map<String, RegistrationDateGroupInfo> registrationDateGroupInfoCache =  new HashMap<String, RegistrationDateGroupInfo>();
	
	


	private AtpService atpService;

	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		return this.atpService.getDataDictionaryEntry(entryKey, context);
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		return this.atpService.getDataDictionaryEntryKeys(context);
	}

	@Override
	public StateInfo getAcademicCalendarState(String academicCalendarStateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return this.atpService
				.getState(null, academicCalendarStateKey, context);
	}

	@Override
	public List<StateInfo> getAcademicCalendarStates(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, DoesNotExistException {

		return this.atpService.getStatesByProcess(null, context);
	}

	@Override
	/**
	 * Simplified  mock impl of getAcademicCalendar (). 
	 * For the real impl: 
	 * 1. get ATP by key from AtpService
	 * 2. Get Associated ATPs for ATP from AtpService 
	 * 4. Get Milestones for ATP
	 * 3. convert them to Campus Calendars, Terms, HolidayInfo etc
	 * 4. Transform and create AcademicCalendar  out of those constituents
	 */
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

	/**
	 * 
	 * Simplified mock impl of getAcademicCalendarsByKeyList(). For the real
	 * impl: get ATP for each key academicCalendarKey in the key list by
	 * invoking getAcademicCalendar() method
	 * 
	 * */
	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(
			List<String> academicCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<AcademicCalendarInfo> infos = new ArrayList<AcademicCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {
			// TODO: consider speeding up the list search by converting to a
			// hashmap
			if (academicCalendarKeyList.contains(info.getKey())) {
				infos.add(info);
			}
		}
		return infos;

	}

	/**
	 * Simplified mock impl of getAcademicCalendarKeysByType(). For the real
	 * impl: 1. get ATPs by type by calling AtpService.getAtpsByAtpType() 2.
	 * Filter the AcademicCalendar ATPs 3. Get id of each Atp and invoke
	 * getAcademicCalendar() method
	 * 
	 * */

	@Override
	public List<String> getAcademicCalendarKeysByType(
			String academicCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<String> academicCalendarTypes = new ArrayList<String>();
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getTypeKey().equals(academicCalendarTypeKey)) {
				academicCalendarTypes.add(info.getKey());
			}
		}
		return academicCalendarTypes;
	}

	/**
	 * Simplified mock impl of getAcademicCalendarsByYear(). For the real impl:
	 * 1. get ATPs by date by calling AtpService.getAtpsByDate() 2. Filter the
	 * AcademicCalendar ATPs 3. Get id of each Atp and invoke
	 * getAcademicCalendar() method to get the ACInfo by id
	 * 
	 * */

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getStartDate().getYear() == year.intValue()) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	/**
	 * Simplified mock impl of getAcademicCalendarsByCredentialProgramType().
	 * For the real impl: 1. get ATPs of AC type 2. Get the ACInfo object by
	 * getting id of each ATP and calling getAcademicCalendar() 3. Filter the
	 * ACs of the particular credentialProgramTypeKey
	 */
	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(
			String credentialProgramTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCredentialProgramTypeKey().equals(
					credentialProgramTypeKey)) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	/**
	 * Simplified mock impl of getAcademicCalendarsByCredentialProgramType().
	 * For the real impl: 
	 * 1. Get ATPs for the particular year 
	 * 2. filter ATPs of AC type 
	 * 3. Get the ACInfo object by getting id of each ATP and calling
	 * getAcademicCalendar() 
	 * 4. Filter the ACs of the particular
	 * credentialProgramTypeKey
	 */
	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(
			String credentialProgramTypeKey, Integer year, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCredentialProgramTypeKey().equals(
					credentialProgramTypeKey)
					&& info.getStartDate().getYear() == year.intValue()) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	/**
	 * Real impl should:
	 * 
	 * 1. Use Validation decorator 
	 * 2. implement the logic for this method in the decor
	 * 3. let this method  be just a pass-through 
	 * 
	 */
	@Override
	public List<ValidationResultInfo> validateAcademicCalendar(
			String validationType, AcademicCalendarInfo academicCalendarInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		
		return validationResult;
	}

	/**
	 * Simplified Impl of createAcademicCalendar() For real Impl : Basic i/p
	 * validations: 1. validate the campus calendar exists in the DB or else
	 * throw validation exception 2. validate the credential program exists
	 * 
	 * Impl: 1. Transform and create ATPInfo out of the ACInfo 2. Go through the
	 * AtpService.createAtp() to create an Atp for the AcademicCalendar
	 */
	@Override
	public AcademicCalendarInfo createAcademicCalendar(
			String academicCalendarKey,
			AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		AcademicCalendarInfo.Builder builder = new AcademicCalendarInfo.Builder(
				academicCalendarInfo);
		MockHelper helper = new MockHelper();
		builder.setKey(academicCalendarKey);
		builder.setMetaInfo(helper.createMeta(context));
		AcademicCalendarInfo copy = builder.build();
		this.acCache.put(copy.getKey(), copy);
		return copy;
	}

	/**
	 * A simplified Impl of updateAcademicCalendar() For real Impl : Basic i/p
	 * validations: 1. validate the campus calendar exists in the DB or else
	 * throw validation exception 2. validate the credential program exists
	 * 
	 * Then for impl: 1. Transform and create ATPInfo out of the ACInfo 2. Go
	 * through the AtpService.updateAtp() to update an Atp for the
	 * AcademicCalendar
	 * 
	 */
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
		if (!academicCalendarInfo.getMetaInfo().getVersionInd()
				.equals(existingAC.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException("Updated by "
					+ existingAC.getMetaInfo().getUpdateId() + " on "
					+ existingAC.getMetaInfo().getUpdateId()
					+ " with version of "
					+ existingAC.getMetaInfo().getVersionInd());
		}
		MockHelper helper = new MockHelper();
		AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(
				academicCalendarInfo);
		acBuilder.setMetaInfo(helper.updateMeta(existingAC.getMetaInfo(),
				context));
		AcademicCalendarInfo copy = acBuilder.build();
		this.acCache.put(academicCalendarKey, copy);
		// mirroring what was done before immutable DTO's; why returning copy of
		// copy?
		return new AcademicCalendarInfo.Builder(copy).build();
	}

	/**
	 * A simplified mock impl 
	 * 
	 * For real impl: 1. Get the Atp by using the acKey
	 * and calling getAcademicCalendar() 2. Delete the Atp first by invoking
	 * AtpService.deleteAtp() 3. Delete any AtpAtpRelation for CampusCalendar,
	 * terms, CredentialProgram etc in the AcademicCalendar
	 * 
	 */
	@Override
	public StatusInfo deleteAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo.Builder sBuilder = new StatusInfo.Builder();
		if (acCache.containsKey(academicCalendarKey)) {
			acCache.remove(academicCalendarKey);
			sBuilder.setSuccess(Boolean.TRUE);
		} else {
			throw new DoesNotExistException(academicCalendarKey);
		}

		return sBuilder.build();

	}

	/**
	 * A simplified mock impl For real impl: 1. Convert to Atp and Copy ATp 2.
	 * Also modify any relations 3. Delete any AtpAtpRelation for
	 * CampusCalendar, terms, CredentialProgram etc in the AcademicCalendar
	 */
	@Override
	public AcademicCalendarInfo copyAcademicCalendar(
			String academicCalendarKey, String newAcademicCalendarKey,
			ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		if (this.acCache.containsKey(academicCalendarKey)) {
			AcademicCalendarInfo dupCalendar = this.acCache
					.get(academicCalendarKey);

			AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(
					dupCalendar);
			acBuilder.setEndDate(null);
			acBuilder.setStartDate(null);
			acBuilder.setKey(newAcademicCalendarKey);

			AcademicCalendarInfo copiedCalendar = acBuilder.build();
			acCache.put(newAcademicCalendarKey, copiedCalendar);
			return copiedCalendar;
		} else {
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

			if (info.getKey().equals(academicCalendarKey)) {
				
			}
		}
		return null;
	}

	@Override
	public StateInfo getCampusCalendarState(String campusCalendarStateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		String processKey = null;
		return this.atpService.getState(processKey, campusCalendarStateKey, context);
	}

	@Override
	public List<StateInfo> getCampusCalendarStates(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, DoesNotExistException {
		String processKey = null;
		return this.atpService.getStatesByProcess(processKey, context);
	}

	@Override
	/**
	 * Simplified mock impl.
	 * 
	 * For real impl:
	 * 1. get the ATP from Atp Service by calling AtpService.getAtp()  
	 */
	public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		CampusCalendarInfo campusCalendar = ccCache.get(campusCalendarKey);
		return campusCalendar;
	}
	/**
	 * Simplified mock impl.
	 * 
	 * For real impl:
	 * 1. get the ATP for each key in campusCalendarKeyList  from Atp Service by calling AtpService.getAtp()  
	 */
	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByKeyList(
			List<String> campusCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (CampusCalendarInfo info : this.ccCache.values()) {
			if (campusCalendarKeyList.contains(info.getKey() )) {
				campusCalendars.add(info);
			}
		}
		return campusCalendars;
	}
	/**
	 * Simplified mock impl.
	 * 
	 * For real impl:
	 * 1. get the ATP for each key in campusCalendarKeyList  from Atp Service by calling AtpService.getAtp()  
	 */
	@Override
	public List<String> getCampusCalendarKeysByType(
			String campusCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<String> campusCalendarKeys = new ArrayList<String>();

		for (CampusCalendarInfo ccInfo : ccCache.values()) {
			if (ccInfo.getTypeKey().equals(campusCalendarTypeKey)) {
				campusCalendarKeys.add(ccInfo.getKey());
			}
		}

		return campusCalendarKeys;
	}
	/**
	 * Simplified mock
	 * 
	 * For real impl: 
	 * 1. get the campus calendars by calling AtpService.getAtpByDate() 
	 * 2. Convert to CampusCalendarInfo object
	 */
	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (CampusCalendarInfo info : this.ccCache.values()) {
			if (info.getStartDate().getYear() == year.intValue()) {
				campusCalendars.add(info);
			}
		}

		return campusCalendars;
	}
	
	/**
	 *	1. Use Validation decorator 
	 * 2. implement the logic for this method in the decor
	 * 3. let this method  be just a pass-through 
	 */
	@Override
	public List<ValidationResultInfo> validateCampusCalendar(
			String validationType, CampusCalendarInfo campusCalendarInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
	
		return validationResult;
	}
	
	/**
	 * Simplified mock Impl of createCampusCalendar()
	 * 
	 *  For real Impl : 
	 *  
	 * Impl: 1. Transform and create ATPInfo out of the CampusCalendarInfo
	 * 		 2. Go through the AtpService.createAtp() to create an Atp for the AcademicCalendar
	 * 
	 */
	@Override
	public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		CampusCalendarInfo.Builder newCampusCalendarBuilder = new CampusCalendarInfo.Builder(
				campusCalendarInfo);
		newCampusCalendarBuilder.setKey(campusCalendarKey);
		CampusCalendarInfo newCampusCalendar = newCampusCalendarBuilder.build();
		ccCache.put(campusCalendarKey, newCampusCalendar);
		return newCampusCalendar;
	}
	
	/**
	 * A simplified Impl of updateCampusCalendar() 
	 * 
	 * Then for impl: 1. Transform and create ATPInfo out of the CampusCalendarInfo
	 * 2. Invoke the AtpService.updateAtp() to update an Atp for the
	 * AcademicCalendar
	 * 
	 */
	@Override
	public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

		CampusCalendarInfo.Builder campusCalendarBuilder = new CampusCalendarInfo.Builder(
				campusCalendarInfo);
		campusCalendarBuilder.setKey(campusCalendarKey);
		ccCache.remove(campusCalendarKey);
		ccCache.put(campusCalendarKey, campusCalendarBuilder.build());
		return campusCalendarInfo;
	}
	
	/**
	 * A simplified Impl of deleteCampusCalendar() 
	 * 
	 *  for real impl: 
	 * 
	 * 1. lookup, the AtpInfo by the campusCalendarKey
	 * 2. Go through the AtpService.deleteAtp() to delete the campus calendar
	 * 3. Make sure all AtpAtpRelationInfo is deleted as well when
	 *  the campus calendar is related to other AcademicCalendar Info
	 * 4. Delete all AtpMilestone relations to delete CampusCalendar-KeyDate relation 
	 *  
	 */
	@Override
	public StatusInfo deleteCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo.Builder statusInfo = new StatusInfo.Builder();
		CampusCalendarInfo ccInfo = ccCache.remove(campusCalendarKey);
		statusInfo.setSuccess(ccInfo == null);

		return statusInfo.build();

	}
	
	
	/**
	 * A simplified Impl of deleteCampusCalendar() 
	 *  for real impl: 
	 * 
	 * 1. lookup, the AtpInfo by the campusCalendarKey
	 * 2. Go through the AtpService.deleteAtp() to delete the campus calendar
	 * 3. Make sure all AtpAtpRelationInfo is deleted as well when
	 *  the campus calendar is related to other AcademicCalendar Info
	 * 4. Delete all AtpMilestone relations to delete CampusCalendar-KeyDate relation 
	 */
	@Override
	public StateInfo getTermState(String termStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		String termProcessKey = null;
		return this.atpService.getState(termProcessKey, termStateKey, context);
	}

	
	/**
	 * 
	 */
	@Override
	public List<StateInfo> getTermStates(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, DoesNotExistException {
		String termProcessKey = null;
		return this.atpService.getStatesByProcess(termProcessKey, context);
	}
	
	
	
	/**
	 * For real impl - call AtpSerive.getAtp () then convert to TermInfo
	 */
	@Override
	public TermInfo getTerm(String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return termsCache.get(termKey);

	}
	
	/**
	 * For real impl - for each key in the list 
	 * call getTerm() 
	 */
	@Override
	public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<TermInfo> matchingTerms = new ArrayList<TermInfo>();
		for (TermInfo termInfo : this.termsCache.values()) {
			if (termKeyList.contains(termInfo.getKey())) {
				matchingTerms.add(termInfo);
			}
		}
		return matchingTerms;
	}
	/**
	 * 
	 */
	@Override
	public List<String> getTermKeysByType(String termTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> matchingTermKeys = new ArrayList<String>();
		for (TermInfo termInfo : termsCache.values()) {
			if (termInfo.getTypeKey().equals(termTypeKey)) {
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
		for (String keyDate : keyDateKeyList) {
			if (keyDateCache.containsKey(keyDate)) {
				keyDates.add(keyDateCache.get(keyDate));
			} else {
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

		return this.atpService.getMilestoneKeysByType(keyDateTypeKey, context);

	}

	/**
	 * 
	 * Real impl:
	 * 
	 * 1. Academic Calendars contain campusCalendar (Get AcademicCalendar from ATPService and then find the linked campus calendar)
	 * 2.CampusCalendar contains key dates (Get key dates from campus calendar by calling getMilestonesByAtp())
	 * 
	 *  
	 */
	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		AcademicCalendarInfo acInfo = this.acCache.get(academicCalendarKey);
		
		List<MilestoneInfo> milestoneInfoDates =  this.atpService.getMilestonesByAtp(academicCalendarKey, context);
		//map milestone to keydateinfo
		List <KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		
		return keyDates;
		 
		
	}
	/**
	 * 1. 
	 */
	@Override
	public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<MilestoneInfo> termMilestones  = this.atpService.getMilestonesByAtp(termKey, context);
		//convert milstones to keydates
		List<KeyDateInfo> keyDatesForTerm = new ArrayList<KeyDateInfo>();
		
		
		return keyDatesForTerm;
	}
	/**
	 * 
	 */
	@Override
	public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		List <KeyDateInfo> initialKeyDates = getKeyDatesForTerm(termKey, context);
		List<TermInfo> allTermsForTerm = getTermsForTerm(termKey, context);
		for(TermInfo subTerm : allTermsForTerm){
			initialKeyDates.addAll(getKeyDatesForTerm(subTerm.getKey(), context));
		}
		return initialKeyDates;
	}
	/**
	 * 	   1. Use Validation decorator 
		 * 2. implement the logic for this method in the decor
		 * 3. let this method  be just a pass-through 
	 */
	@Override
	public List<ValidationResultInfo> validateKeyDate(String validationType,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		
		return validationResult;
	}

	@Override
	public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		AtpMilestoneRelationInfo.Builder  atpMileRelBuilder = new AtpMilestoneRelationInfo.Builder();
		atpMileRelBuilder.setAtpKey(termKey);
		atpMileRelBuilder.setMilestoneKey(keyDateKey);
		AtpMilestoneRelationInfo atpInfo =	atpMileRelBuilder.build();
		AtpMilestoneRelationInfo atpMilestoneInfo =  this.atpService.createAtpMilestoneRelation(atpInfo, context);
		// convert atpMilestoneInfo to KeyDateInfo
		KeyDateInfo.Builder builder = new KeyDateInfo.Builder();
		builder.setKey(atpMilestoneInfo.getMilestoneKey());
		return builder.build();
		
		
	}

	@Override
	public KeyDateInfo updateKeyDate(String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		
		MilestoneInfo.Builder keyDate = new MilestoneInfo.Builder();
		keyDate.setKey(keyDate.getKey());
		keyDate.setName(keyDate.getName());
		keyDate.setDateRange(keyDateInfo.getIsDateRange()); 
		this.atpService.updateMilestone(keyDateKey, keyDate.build(), context);
		return keyDateInfo;
	}

	@Override
	public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return this.atpService.deleteMilestone(keyDateKey, context);
		
	}

	@Override
	public List<HolidayInfo> getHolidaysForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		AcademicCalendarInfo acInfo = acCache.get(academicCalendarKey);
		CampusCalendarInfo ccInfo = ccCache.get(acInfo.getKey());
		List<MilestoneInfo> milestonesForAtp = this.atpService
				.getMilestonesByAtp(ccInfo.getKey(), context);
		List<HolidayInfo> holidays = new ArrayList<HolidayInfo>();
		return holidays;
	}
	/**
	 * 
	 * 1. Use Validation decorator 
	 * 2. implement the logic for this method in the decor
	 * 3. let this method  be just a pass-through 
	 */
	@Override
	public List<ValidationResultInfo> validateHoliday(String validationType,
			HolidayInfo holidayInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();
		
		return validationResult;
	}

	@Override
	public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
			String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		AtpMilestoneRelationInfo.Builder atpMilestoneRelationInfoBuilder = new AtpMilestoneRelationInfo.Builder();
		atpMilestoneRelationInfoBuilder.setMilestoneKey(holidayKey);
		atpMilestoneRelationInfoBuilder.setAtpKey(campusCalendarKey);
		
		AtpMilestoneRelationInfo atMileInfo = atpMilestoneRelationInfoBuilder.build();
		this.atpService.createAtpMilestoneRelation(atMileInfo, context);
		
		MilestoneInfo mInfo = createMilestoneFromHoliday(holidayInfo);
		
		MilestoneInfo mInfoNew =  this.atpService.createMilestone(holidayKey, mInfo, context);
		
		HolidayInfo.Builder hBuilder =new HolidayInfo.Builder();
		hBuilder.setAttributes(mInfoNew.getAttributes());
		hBuilder.setDescr(mInfoNew.getDescr());
		hBuilder.setIsDateRange(mInfo.getIsDateRange());
		hBuilder.setKey(mInfo.getKey());
		
		return  hBuilder.build();
		
	}

	@Override
	public HolidayInfo updateHoliday(String holidayKey,
			HolidayInfo holidayInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

			MilestoneInfo mInfo = createMilestoneFromHoliday(holidayInfo);
			
			
			MilestoneInfo returnMInfo = this.atpService.updateMilestone(holidayKey, mInfo, context);
			HolidayInfo.Builder hInfo = new HolidayInfo.Builder(); 
			hInfo.setAttributes(returnMInfo.getAttributes());
			hInfo.setName(returnMInfo.getName());
			hInfo.setDescr (returnMInfo.getDescr());
			hInfo.setIsAllDay(returnMInfo.getIsAllDay());
			hInfo.setKey (returnMInfo.getKey());
			return hInfo.build();
	}

	@Override
	public StatusInfo deleteHoliday(String holidayKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {


		List<AtpMilestoneRelationInfo> atpMilestoneRelations = this.atpService
				.getAtpMilestoneRelationsByMilestone(holidayKey, context);

		for (AtpMilestoneRelationInfo atpMilestoneRelation : atpMilestoneRelations) {
			this.atpService.deleteAtpMilestoneRelation(
					atpMilestoneRelation.getId(), context);
		}

		return  this.atpService.deleteMilestone(holidayKey, context);
	}

	@Override
	public Integer getInstructionalDaysForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		DateRange classDates = getRegistrationDateGroup(termKey, context)
				.getClassDateRange();
		int numInstructionalDays = 0;
		Calendar startCalendar = new GregorianCalendar(classDates.getStart()
				.getYear(), classDates.getStart().getMonth(), classDates
				.getStart().getDate());
		Calendar endCalendar = new GregorianCalendar(classDates.getEnd()
				.getYear(), classDates.getStart().getMonth(), classDates
				.getEnd().getDate());

		// TODO Use some open source library like joda-time to compute these
		//
		// TODO Finally the calendar should remove weekend days,
		// non-instructional holidays - any other days such as exams which are
		// not instructional

		return Integer.valueOf(numInstructionalDays);
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(
			String academicCalendarKey, Date startDate, Date endDate,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<MilestoneInfo> mileStoneInfos = this.atpService
				.getMilestonesByAtp(academicCalendarKey, context);
		// convert MileStones to KeyDateInfo
		return new ArrayList<KeyDateInfo>();

	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		List<MilestoneInfo> milestones = this.atpService.getMilestonesByAtp(
				termKey, context);
		for (MilestoneInfo milestoneInfo : milestones) {
			if (milestoneInfo.getStartDate().after(startDate)
					&& milestoneInfo.getEndDate().before(endDate)) {
				// convert milestone to keydate and add it to this List
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

		// Because KeyDates are Milestones
		List<MilestoneInfo> milestones = this.atpService.getMilestonesByAtp(
				termKey, context);
		// Add subterm milestones
		List<TermInfo> allTerms = getTermsForTerm(termKey, context);
		for (TermInfo subTerm : allTerms) {
			List<MilestoneInfo> subMilestones = this.atpService
					.getMilestonesByAtp(subTerm.getKey(), context);
			milestones.addAll(subMilestones);

		}

		for (MilestoneInfo milestoneInfo : milestones) {
			if (milestoneInfo.getStartDate().equals(startDate)
					&& milestoneInfo.getEndDate().equals(endDate)) {
				keyDates.add(null);
			}
		}

		return keyDates;

	}

	@Override
	public List<TermInfo> getTermsForTerm(String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		TermInfo termInfo = termsCache.get(termKey);
		List<TermInfo> termsToReturn = new ArrayList<TermInfo>();
		List<TypeTypeRelationInfo> typesRelations = this.atpService
				.getTypeRelationsByOwnerType(termInfo.getTypeKey(),
						"kuali.relationtype.contains", context);
	
		for (TypeTypeRelationInfo typeRelation : typesRelations) {

			String relatedTypeKey = typeRelation.getRelatedTypeKey();
			List<TermInfo> termInfos = (List<TermInfo>) termsCache.values();
			for (TermInfo relatedTermInfo : termInfos) {
				if (relatedTermInfo.getTypeKey().equals(relatedTypeKey)) {
					termsToReturn.add(relatedTermInfo);
				}
			}
		}

		return termsToReturn;
	}

	@Override
	public TermInfo getParentTerm(String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 
	 * 1. Use Validation decorator 
	 * 2. implement the logic for this method in the decor
	 * 3. let this method  be just a pass-through 
	 */
	@Override
	public List<ValidationResultInfo> validateTerm(String validationType,
			TermInfo termInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();

		return validationResult;
	}

	@Override
	public TermInfo createTerm(String termKey, TermInfo termInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		TermInfo.Builder newTermInfoBuilder = new TermInfo.Builder(termInfo);
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

		StatusInfo.Builder ssBuilder = new StatusInfo.Builder();
		ssBuilder.setSuccess(true);
		return ssBuilder.build();
	}

	@Override
	public StatusInfo addTermToAcademicCalendar(String academicCalendarKey,
			String termKey, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, AlreadyExistsException {
		AtpAtpRelationInfo.Builder atpAtpRelation = new AtpAtpRelationInfo.Builder();
		atpAtpRelation.setAtpKey(termKey);
		atpAtpRelation.setAtpKey(academicCalendarKey);
		AtpAtpRelationInfo atpAtpInfo =  this.atpService.createAtpAtpRelation(atpAtpRelation.build(), context);
		return new StatusInfo.Builder().build();
	}

	@Override
	public StatusInfo removeTermFromAcademicCalendar(
			String academicCalendarKey, String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		List<AtpAtpRelationInfo> atpRelInfos = this.atpService.getAtpAtpRelationsByAtp(academicCalendarKey, context);
		for(AtpAtpRelationInfo atpRelInfo : atpRelInfos){
			if(atpRelInfo.getRelatedAtpKey().equals(termKey)){ 
				this.atpService.deleteAtpAtpRelation(atpRelInfo.getId(), context);
			}
		}
		
		return new StatusInfo.Builder().build();
	}

	@Override
	public StatusInfo addTermToTerm(String parentTermKey, String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, AlreadyExistsException {
		AtpAtpRelationInfo.Builder atpBuilder =  new AtpAtpRelationInfo.Builder();
		atpBuilder.setAtpKey(parentTermKey);
		atpBuilder.setRelatedAtpKey(termKey);
		atpBuilder.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY);
		AtpAtpRelationInfo atpAtpRelationInfo = atpBuilder.build();
		this.atpService.createAtpAtpRelation(atpAtpRelationInfo, context);
		return new StatusInfo.Builder().build();
	}

	@Override
	public StatusInfo removeTermFromTerm(String parentTermKey, String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		List<AtpAtpRelationInfo> atpAtpRelations = this.atpService.getAtpAtpRelationsByAtp(parentTermKey, context);
		String atpAtpRelationId = null;
		for(AtpAtpRelationInfo atpRelationInfo : atpAtpRelations){
			if(atpRelationInfo.getRelatedAtpKey().equals(termKey)){
				atpAtpRelationId= atpRelationInfo.getId();
			}
		}
		
		this.atpService.deleteAtpAtpRelation(atpAtpRelationId, context);
		return new StatusInfo.Builder().build();
	}

	@Override
	public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return this.registrationDateGroupInfoCache.get(termKey);
	}
	/**
	 * 1. Use Validation decorator 
	 * 2. implement the logic for this method in the decor
	 * 3. let this method  be just a pass-through 
	 */
	@Override
	public List<ValidationResultInfo> validateRegistrationDateGroup(
			String validationType,
			RegistrationDateGroupInfo registrationDateGroupInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<ValidationResultInfo> validationResult = new ArrayList<ValidationResultInfo>();

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
		 this.registrationDateGroupInfoCache.remove(termKey);
		 this.registrationDateGroupInfoCache.put(termKey, registrationDateGroupInfo);
		 return registrationDateGroupInfo;
	}
	/** 
	 * This is a simplified mock Impl
	 * For a real impl:
	 * 1.Lookup the AtAtpRelationInfo table and find related atpKey for 
	 * all the AtpAtpRelationInfo with atpKey equals academicCalendarKey
	 * 2. Call getTerm() for the list of related keys
	 *  
	 * 
	 */
	@Override
	public List<TermInfo> getTermsForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		
		List<TermInfo> termsToReturn = new ArrayList<TermInfo>();
		
		List <AtpAtpRelationInfo > termACRelationInfos = this.atpService.getAtpAtpRelationsByAtp(academicCalendarKey, context);
		
		for (AtpAtpRelationInfo termACRelationInfo : termACRelationInfos) {

			String relatedTypeKey =termACRelationInfo.getAtpKey();
			TermInfo termInfo = termsCache.get(relatedTypeKey);
			termsToReturn.add(termInfo);
		}

		
		return termsToReturn;

	}

	@Override
	public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		AcademicCalendarInfo acInfo = acCache.get(academicCalendarTypeKey);
		return this.atpService.getType(acInfo.getTypeKey(), context);

	}

	@Override
	public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<TypeInfo> types = new ArrayList<TypeInfo>(); 
		TypeInfo.Builder typeBuilder = new TypeInfo.Builder();
		typeBuilder.setKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
		types.add(typeBuilder.build());
		return types;
	}

	@Override
	public TypeInfo getCampusCalendarType(String campusCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		CampusCalendarInfo ccInfo = ccCache.get(campusCalendarTypeKey);
		return this.atpService.getType(ccInfo.getTypeKey(), context);

	}

	@Override
	public List<TypeInfo> getCampusCalendarTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<TypeInfo> types = new ArrayList<TypeInfo>(); 
		TypeInfo.Builder typeBuilder = new TypeInfo.Builder();
		typeBuilder.setKey(AcademicCalendarServiceConstants.CAMPUS_CALENDAR_TYPE_KEY);
		types.add(typeBuilder.build());
		return types;
	}

	@Override
	public TypeInfo getTermType(String termTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		TermInfo tInfo = termsCache.get(termTypeKey);
		return this.atpService.getType(tInfo.getTypeKey(), context);
	}

	@Override
	public List<TypeInfo> getTermTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<TypeInfo> types = new ArrayList<TypeInfo>(); 
		TypeInfo.Builder typeBuilder1 = new TypeInfo.Builder();
		typeBuilder1.setKey(AtpServiceConstants.SEASON_TERM_1_TYPE_KEY );
		types.add(typeBuilder1.build());
		TypeInfo.Builder typeBuilder2 = new TypeInfo.Builder();
		typeBuilder2.setKey(AtpServiceConstants.SEASON_TERM_2_TYPE_KEY );
		types.add(typeBuilder2.build());
		return types;
	}

	@Override
	public List<TypeInfo> getTermTypesForAcademicCalendarType(
			String academicCalendarTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		return getTermTypes(context);
	}

	@Override
	public List<TypeInfo> getTermTypesForTermType(String termTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		return getTermTypes(context);
	}

	@Override
	public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		return	this.atpService.getType(keyDateTypeKey, context);
	}

	@Override
	public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return this.atpService.getAllowedTypesForType(termTypeKey, AtpServiceConstants.REF_OBJECT_URI_ATP, context);
	}

	@Override
	public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return	this.atpService.getType(holidayTypeKey, context);
	}

	@Override
	public List<TypeInfo> getHolidayTypesForCampusCalendarType(
			String campusCalendarTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private MilestoneInfo createMilestoneFromHoliday(HolidayInfo holidayInfo){
		MilestoneInfo.Builder mInfo = new MilestoneInfo.Builder();
		mInfo.setAttributes(holidayInfo.getAttributes());
		mInfo.setName(holidayInfo.getName());
		mInfo.setDateRange(holidayInfo.getIsDateRange() );
		mInfo.setDescr(holidayInfo.getDescr());
		mInfo.setKey(holidayInfo.getKey());
		
		return mInfo.build();
	}
}
