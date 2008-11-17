package org.kuali.student.lum.atp.service.impl;

import java.util.Date;
import java.util.List;

import org.kuali.student.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.common.ws.exceptions.DataValidationErrorException;
import org.kuali.student.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.common.ws.exceptions.MissingParameterException;
import org.kuali.student.common.ws.exceptions.OperationFailedException;
import org.kuali.student.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.common.ws.exceptions.VersionMismatchException;
import org.kuali.student.core.dto.Status;
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
import org.kuali.student.lum.atp.service.AtpService;


public class AtpServiceImpl extends DictionarySearchServiceImpl implements AtpService {

	private AtpDao atpDao;
	
	@Override
	public DateRangeInfo addDateRange(String atpKey, String dateRangeKey,
			DateRangeInfo dateRangeInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo addMilestone(String atpKey, String milestoneKey,
			MilestoneInfo milestoneInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpInfo createAtp(String atpTypeKey, String atpKey, AtpInfo atpInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAtp(String atpKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AtpInfo fetchAtp(String atpKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpDurationTypeInfo fetchAtpDurationType(String atpDurationTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpSeasonalTypeInfo fetchAtpSeasonalType(String atpSeasonalTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpTypeInfo fetchAtpType(String atpTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateRangeInfo fetchDateRange(String dateRangeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateRangeTypeInfo fetchDateRangeType(String dateRangeTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo fetchMilestone(String milestoneKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneTypeInfo fetchMilestoneType(String milestoneTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpDurationTypeInfo> findAtpDurationTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpSeasonalTypeInfo> findAtpSeasonalTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpTypeInfo> findAtpTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> findAtpsByAtpType(String atpTypeKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> findAtpsByDate(Date searchDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> findAtpsByDates(Date startDate, Date endDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRangeTypeInfo> findDateRangeTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRangeTypeInfo> findDateRangeTypesForAtpType(
			String atpTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRangeInfo> findDateRangesByAtp(String atpKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRangeInfo> findDateRangesByDate(Date searchDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneTypeInfo> findMilestoneTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneTypeInfo> findMilestoneTypesForAtpType(
			String atpTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> findMilestonesByAtp(String atpKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> findMilestonesByDates(Date startDate,
			Date endDate) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> findMilestonesByDatesAndType(
			String milestoneTypeKey, Date startDate, Date endDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeDateRange(String dateRangeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeMilestone(String milestoneKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateRangeInfo updateDateRange(String dateRangeKey,
			DateRangeInfo dateRangeInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo updateMilestone(String milestoneKey,
			MilestoneInfo milestoneInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
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
