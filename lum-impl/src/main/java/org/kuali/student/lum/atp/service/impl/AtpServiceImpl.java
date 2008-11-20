package org.kuali.student.lum.atp.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.service.impl.DictionarySearchServiceImpl;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.lum.atp.dao.AtpDao;
import org.kuali.student.lum.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.lum.atp.dto.AtpInfo;
import org.kuali.student.lum.atp.dto.AtpSeasonalTypeInfo;
import org.kuali.student.lum.atp.dto.AtpTypeInfo;
import org.kuali.student.lum.atp.dto.DateRangeInfo;
import org.kuali.student.lum.atp.dto.DateRangeTypeInfo;
import org.kuali.student.lum.atp.dto.MilestoneInfo;
import org.kuali.student.lum.atp.dto.MilestoneTypeInfo;
import org.kuali.student.lum.atp.entity.Atp;
import org.kuali.student.lum.atp.entity.AtpDurationType;
import org.kuali.student.lum.atp.entity.AtpSeasonalType;
import org.kuali.student.lum.atp.entity.AtpType;
import org.kuali.student.lum.atp.entity.DateRange;
import org.kuali.student.lum.atp.entity.DateRangeType;
import org.kuali.student.lum.atp.entity.Milestone;
import org.kuali.student.lum.atp.entity.MilestoneType;
import org.kuali.student.lum.atp.service.AtpService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.atp.service.AtpService", serviceName = "AtpService", portName = "AtpService", targetNamespace = "http://org.kuali.student/lum/atp")
@Transactional
public class AtpServiceImpl extends DictionarySearchServiceImpl implements AtpService {

	private AtpDao atpDao;
	
	@Override
	public DateRangeInfo addDateRange(String atpKey, String dateRangeKey,
			DateRangeInfo dateRangeInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		CheckMissingParameters(new String[]{"atpKey","dateRangeKey","dateRangeInfo"},
				               new Object[]{ atpKey,  dateRangeKey,  dateRangeInfo});
		

		dateRangeInfo.setAtpKey(atpKey);
		dateRangeInfo.setKey(dateRangeKey);
		DateRange dateRange = AtpAssembler.toDateRange(dateRangeInfo,atpDao);
		
		atpDao.create(dateRange);
		
		return AtpAssembler.toDateRangeInfo(dateRange);
	}

	private void CheckMissingParameters(String[] paramNames, Object[] params) throws MissingParameterException {
		String errors = null;
		int i = 0;
		for(Object param:params){
			if(param==null){
				errors = errors==null?paramNames[i]:errors+", " + paramNames[i];
			}
			i++;
		}
		if(errors!=null){
			throw new MissingParameterException("Missing Parameters: " + errors);
		}
	}

	@Override
	public MilestoneInfo addMilestone(String atpKey, String milestoneKey,
			MilestoneInfo milestoneInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		CheckMissingParameters(new String[]{"atpKey","milestoneKey","milestoneInfo"},
	               			   new Object[]{ atpKey,  milestoneKey,  milestoneInfo});


		milestoneInfo.setAtpKey(atpKey);
		milestoneInfo.setKey(milestoneKey);
		Milestone milestone = AtpAssembler.toMilestone(milestoneInfo,atpDao);
		
		atpDao.create(milestone);
		
		return AtpAssembler.toMilestoneInfo(milestone);
	}

	@Override
	public AtpInfo createAtp(String atpTypeKey, String atpKey, AtpInfo atpInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		CheckMissingParameters(new String[]{"atpTypeKey","atpKey","atpInfo"},
	                           new Object[]{ atpTypeKey,  atpKey,  atpInfo});

		atpInfo.setType(atpTypeKey);
		atpInfo.setKey(atpKey);
		
		Atp atp = AtpAssembler.toAtp(atpInfo, atpDao);
		
		atpDao.create(atp);
		
		AtpInfo result = AtpAssembler.toAtpInfo(atp);
		return result;
	}

	@Override
	public StatusInfo deleteAtp(String atpKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		atpDao.delete(Atp.class,atpKey);
		
		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		
		return statusInfo;
	}

	@Override
	public AtpInfo fetchAtp(String atpKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		Atp atp = atpDao.fetch(Atp.class, atpKey);
		
		return AtpAssembler.toAtpInfo(atp);
	}

	@Override
	public AtpDurationTypeInfo fetchAtpDurationType(String atpDurationTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		AtpDurationType atpDurationType = atpDao.fetch(AtpDurationType.class, atpDurationTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(AtpDurationTypeInfo.class, atpDurationType);
	}

	@Override
	public AtpSeasonalTypeInfo fetchAtpSeasonalType(String atpSeasonalTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		AtpSeasonalType atpSeasonalType = atpDao.fetch(AtpSeasonalType.class, atpSeasonalTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(AtpSeasonalTypeInfo.class, atpSeasonalType);
	}

	@Override
	public AtpTypeInfo fetchAtpType(String atpTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		AtpType atpType = atpDao.fetch(AtpType.class, atpTypeKey);
		
		return AtpAssembler.toAtpTypeInfo(atpType);
	}

	@Override
	public DateRangeInfo fetchDateRange(String dateRangeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		DateRange dateRange = atpDao.fetch(DateRange.class, dateRangeKey);
		
		return AtpAssembler.toDateRangeInfo(dateRange);

	}

	@Override
	public DateRangeTypeInfo fetchDateRangeType(String dateRangeTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		DateRangeType dateRangeType = atpDao.fetch(DateRangeType.class, dateRangeTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(DateRangeTypeInfo.class, dateRangeType);
	}

	@Override
	public MilestoneInfo fetchMilestone(String milestoneKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		Milestone milestone = atpDao.fetch(Milestone.class, milestoneKey);
		
		return AtpAssembler.toMilestoneInfo(milestone);

	}

	@Override
	public MilestoneTypeInfo fetchMilestoneType(String milestoneTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		MilestoneType milestoneType = atpDao.fetch(MilestoneType.class, milestoneTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(MilestoneTypeInfo.class, milestoneType);
	}

	@Override
	public List<AtpDurationTypeInfo> findAtpDurationTypes()
			throws OperationFailedException {
		
		List<AtpDurationType> atpDurationTypes = atpDao.find(AtpDurationType.class);
		
		return AtpAssembler.toGenericTypeInfoList(AtpDurationTypeInfo.class, atpDurationTypes);
	}

	@Override
	public List<AtpSeasonalTypeInfo> findAtpSeasonalTypes()
			throws OperationFailedException {
		
		List<AtpSeasonalType> atpSeasonalTypes = atpDao.find(AtpSeasonalType.class);
		
		return AtpAssembler.toGenericTypeInfoList(AtpSeasonalTypeInfo.class, atpSeasonalTypes);
	}

	@Override
	public List<AtpTypeInfo> findAtpTypes() throws OperationFailedException {
		
		List<AtpType> atpTypes = atpDao.find(AtpType.class);
		
		return AtpAssembler.toAtpTypeInfoList(atpTypes);
	}

	@Override
	public List<AtpInfo> findAtpsByAtpType(String atpTypeKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<Atp> atps = atpDao.findAtpsByAtpType(atpTypeKey);
		
		return AtpAssembler.toAtpInfoList(atps);
	}

	@Override
	public List<AtpInfo> findAtpsByDate(Date searchDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<Atp> atps = atpDao.findAtpsByDate(searchDate);
		
		return AtpAssembler.toAtpInfoList(atps);
	}

	@Override
	public List<AtpInfo> findAtpsByDates(Date startDate, Date endDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<Atp> atps = atpDao.findAtpsByDates(startDate, endDate);
		
		return AtpAssembler.toAtpInfoList(atps);
	}

	@Override
	public List<DateRangeTypeInfo> findDateRangeTypes()
			throws OperationFailedException {
		
		List<DateRangeType> dateRangeTypes = atpDao.find(DateRangeType.class);
		
		return AtpAssembler.toGenericTypeInfoList(DateRangeTypeInfo.class, dateRangeTypes);
	}

	@Override
	public List<DateRangeTypeInfo> findDateRangeTypesForAtpType(
			String atpTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<DateRangeType> dateRangeTypes = atpDao.findDateRangeTypesForAtpType(atpTypeKey);
		
		return AtpAssembler.toGenericTypeInfoList(DateRangeTypeInfo.class, dateRangeTypes);
	}

	@Override
	public List<DateRangeInfo> findDateRangesByAtp(String atpKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<DateRange> dateRanges = atpDao.findDateRangesByAtp(atpKey);
		
		return AtpAssembler.toDateRangeInfoList(dateRanges);
	}

	@Override
	public List<DateRangeInfo> findDateRangesByDate(Date searchDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<DateRange> dateRanges = atpDao.findDateRangesByDate(searchDate);
		
		return AtpAssembler.toDateRangeInfoList(dateRanges);
	}

	@Override
	public List<MilestoneTypeInfo> findMilestoneTypes()
			throws OperationFailedException {
		
		List<MilestoneType> milestoneTypes = atpDao.find(MilestoneType.class);
		
		return AtpAssembler.toGenericTypeInfoList(MilestoneTypeInfo.class, milestoneTypes);
	}

	@Override
	public List<MilestoneTypeInfo> findMilestoneTypesForAtpType(
			String atpTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<MilestoneType> milestoneTypes = atpDao.findMilestoneTypesForAtpType(atpTypeKey);
		
		return AtpAssembler.toGenericTypeInfoList(MilestoneTypeInfo.class, milestoneTypes);
	}

	@Override
	public List<MilestoneInfo> findMilestonesByAtp(String atpKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<Milestone> milestones = atpDao.findMilestonesByAtp(atpKey);
		
		return AtpAssembler.toMilestoneInfoList(milestones);
	}

	@Override
	public List<MilestoneInfo> findMilestonesByDates(Date startDate,
			Date endDate) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		List<Milestone> milestones = atpDao.findMilestonesByDates(startDate, endDate);
		
		return AtpAssembler.toMilestoneInfoList(milestones);
	}

	@Override
	public List<MilestoneInfo> findMilestonesByDatesAndType(
			String milestoneTypeKey, Date startDate, Date endDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<Milestone> milestones = atpDao.findMilestonesByDatesAndType(milestoneTypeKey, startDate, endDate);
		
		return AtpAssembler.toMilestoneInfoList(milestones);
	}

	@Override
	public StatusInfo removeDateRange(String dateRangeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		atpDao.delete(DateRange.class, dateRangeKey);
		
		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		
		return statusInfo;
	}

	@Override
	public StatusInfo removeMilestone(String milestoneKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		atpDao.delete(Milestone.class, milestoneKey);
		
		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		
		return statusInfo;
	}

	@Override
	public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		
		CheckMissingParameters(new String[]{"atpKey","atpInfo"},
                               new Object[]{ atpKey,  atpInfo});

		atpInfo.setKey(atpKey);
		
		Atp atp = AtpAssembler.toAtp(atpInfo, atpDao);
		
		Atp updatedAtp = atpDao.update(atp);
		
		AtpInfo result = AtpAssembler.toAtpInfo(updatedAtp);
		return result;
	}

	@Override
	public DateRangeInfo updateDateRange(String dateRangeKey,
			DateRangeInfo dateRangeInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		
		CheckMissingParameters(new String[]{"dateRangeKey","dateRangeInfo"},
                               new Object[]{ dateRangeKey,  dateRangeInfo});

		dateRangeInfo.setKey(dateRangeKey);
		
		DateRange dateRange = AtpAssembler.toDateRange(dateRangeInfo, atpDao);
		
		DateRange updatedDateRange = atpDao.update(dateRange);
		
		DateRangeInfo result = AtpAssembler.toDateRangeInfo(updatedDateRange);
		return result;
	}

	@Override
	public MilestoneInfo updateMilestone(String milestoneKey,
			MilestoneInfo milestoneInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		
		CheckMissingParameters(new String[]{"milestoneKey","milestoneInfo"},
                               new Object[]{ milestoneKey,  milestoneInfo});
		
		milestoneInfo.setKey(milestoneKey);
		
		Milestone milestone = AtpAssembler.toMilestone(milestoneInfo, atpDao);
		
		Milestone updatedMilestone = atpDao.update(milestone);
		
		MilestoneInfo result = AtpAssembler.toMilestoneInfo(updatedMilestone);
		return result;
	}

	@Override
	public List<ValidationResult> validateAtp(String validationType,
			AtpInfo atpInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateDateRange(String validationType,
			DateRangeInfo dateRangeInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateMilestone(String validationType,
			MilestoneInfo milestoneInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public AtpDao getAtpDao() {
		return atpDao;
	}

	public void setAtpDao(AtpDao atpDao) {
		this.atpDao = atpDao;
	}


}
