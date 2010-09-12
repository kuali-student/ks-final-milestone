/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.atp.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.atp.dao.AtpDao;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.AtpSeasonalTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.dto.DateRangeInfo;
import org.kuali.student.core.atp.dto.DateRangeTypeInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.atp.dto.MilestoneTypeInfo;
import org.kuali.student.core.atp.entity.Atp;
import org.kuali.student.core.atp.entity.AtpDurationType;
import org.kuali.student.core.atp.entity.AtpSeasonalType;
import org.kuali.student.core.atp.entity.AtpType;
import org.kuali.student.core.atp.entity.DateRange;
import org.kuali.student.core.atp.entity.DateRangeType;
import org.kuali.student.core.atp.entity.Milestone;
import org.kuali.student.core.atp.entity.MilestoneType;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.atp.service.AtpService", serviceName = "AtpService", portName = "AtpService", targetNamespace = "http://student.kuali.org/wsdl/atp")
@Transactional(rollbackFor={Throwable.class})
public class AtpServiceImpl implements AtpService {

	private AtpDao atpDao;
	
	@Override
	public DateRangeInfo addDateRange(String atpKey, String dateRangeKey,
			DateRangeInfo dateRangeInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		CheckMissingParameters(new String[]{"atpKey","dateRangeKey","dateRangeInfo"},
				               new Object[]{ atpKey,  dateRangeKey,  dateRangeInfo});
		

		dateRangeInfo.setAtpId(atpKey);
		dateRangeInfo.setId(dateRangeKey);
		DateRange dateRange = null;
		
		try {
			dateRange = AtpAssembler.toDateRange(false, dateRangeInfo,atpDao);
		} catch (DoesNotExistException e) {
		} catch (VersionMismatchException e) {
		}
		
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


		milestoneInfo.setAtpId(atpKey);
		milestoneInfo.setId(milestoneKey);
		Milestone milestone = null;
		try {
			milestone = AtpAssembler.toMilestone(false, milestoneInfo,atpDao);
		} catch (DoesNotExistException e) {
		} catch (VersionMismatchException e) {
		}
		
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
		atpInfo.setId(atpKey);
		
		Atp atp = null;
		try {
			atp = AtpAssembler.toAtp(false, atpInfo, atpDao);
		} catch (DoesNotExistException e) {
		} catch (VersionMismatchException e) {
		}
		
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
	public AtpInfo getAtp(String atpKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		Atp atp = atpDao.fetch(Atp.class, atpKey);
		
		return AtpAssembler.toAtpInfo(atp);
	}

	@Override
	public AtpDurationTypeInfo getAtpDurationType(String atpDurationTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		AtpDurationType atpDurationType = atpDao.fetch(AtpDurationType.class, atpDurationTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(AtpDurationTypeInfo.class, atpDurationType);
	}

	@Override
	public AtpSeasonalTypeInfo getAtpSeasonalType(String atpSeasonalTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		AtpSeasonalType atpSeasonalType = atpDao.fetch(AtpSeasonalType.class, atpSeasonalTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(AtpSeasonalTypeInfo.class, atpSeasonalType);
	}

	@Override
	public AtpTypeInfo getAtpType(String atpTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		AtpType atpType = atpDao.fetch(AtpType.class, atpTypeKey);
		
		return AtpAssembler.toAtpTypeInfo(atpType);
	}

	@Override
	public DateRangeInfo getDateRange(String dateRangeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		DateRange dateRange = atpDao.fetch(DateRange.class, dateRangeKey);
		
		return AtpAssembler.toDateRangeInfo(dateRange);

	}

	@Override
	public DateRangeTypeInfo getDateRangeType(String dateRangeTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		DateRangeType dateRangeType = atpDao.fetch(DateRangeType.class, dateRangeTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(DateRangeTypeInfo.class, dateRangeType);
	}

	@Override
	public MilestoneInfo getMilestone(String milestoneKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		Milestone milestone = atpDao.fetch(Milestone.class, milestoneKey);
		
		return AtpAssembler.toMilestoneInfo(milestone);

	}

	@Override
	public MilestoneTypeInfo getMilestoneType(String milestoneTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		MilestoneType milestoneType = atpDao.fetch(MilestoneType.class, milestoneTypeKey);
		
		return AtpAssembler.toGenericTypeInfo(MilestoneTypeInfo.class, milestoneType);
	}

	@Override
	public List<AtpDurationTypeInfo> getAtpDurationTypes()
			throws OperationFailedException {
		
		List<AtpDurationType> atpDurationTypes = atpDao.find(AtpDurationType.class);
		
		return AtpAssembler.toGenericTypeInfoList(AtpDurationTypeInfo.class, atpDurationTypes);
	}

	@Override
	public List<AtpSeasonalTypeInfo> getAtpSeasonalTypes()
			throws OperationFailedException {
		
		List<AtpSeasonalType> atpSeasonalTypes = atpDao.find(AtpSeasonalType.class);
		
		return AtpAssembler.toGenericTypeInfoList(AtpSeasonalTypeInfo.class, atpSeasonalTypes);
	}

	@Override
	public List<AtpTypeInfo> getAtpTypes() throws OperationFailedException {
		
		List<AtpType> atpTypes = atpDao.find(AtpType.class);
		
		return AtpAssembler.toAtpTypeInfoList(atpTypes);
	}

	@Override
	public List<AtpInfo> getAtpsByAtpType(String atpTypeKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<Atp> atps = atpDao.findAtpsByAtpType(atpTypeKey);
		
		return AtpAssembler.toAtpInfoList(atps);
	}

	@Override
	public List<AtpInfo> getAtpsByDate(Date searchDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<Atp> atps = atpDao.findAtpsByDate(searchDate);
		
		return AtpAssembler.toAtpInfoList(atps);
	}

	@Override
	public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<Atp> atps = atpDao.findAtpsByDates(startDate, endDate);
		
		return AtpAssembler.toAtpInfoList(atps);
	}

	@Override
	public List<DateRangeTypeInfo> getDateRangeTypes()
			throws OperationFailedException {
		
		List<DateRangeType> dateRangeTypes = atpDao.find(DateRangeType.class);
		
		return AtpAssembler.toGenericTypeInfoList(DateRangeTypeInfo.class, dateRangeTypes);
	}

	@Override
	public List<DateRangeTypeInfo> getDateRangeTypesForAtpType(
			String atpTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<DateRangeType> dateRangeTypes = atpDao.findDateRangeTypesForAtpType(atpTypeKey);
		
		return AtpAssembler.toGenericTypeInfoList(DateRangeTypeInfo.class, dateRangeTypes);
	}

	@Override
	public List<DateRangeInfo> getDateRangesByAtp(String atpKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<DateRange> dateRanges = atpDao.findDateRangesByAtp(atpKey);
		
		return AtpAssembler.toDateRangeInfoList(dateRanges);
	}

	@Override
	public List<DateRangeInfo> getDateRangesByDate(Date searchDate)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<DateRange> dateRanges = atpDao.findDateRangesByDate(searchDate);
		
		return AtpAssembler.toDateRangeInfoList(dateRanges);
	}

	@Override
	public List<MilestoneTypeInfo> getMilestoneTypes()
			throws OperationFailedException {
		
		List<MilestoneType> milestoneTypes = atpDao.find(MilestoneType.class);
		
		return AtpAssembler.toGenericTypeInfoList(MilestoneTypeInfo.class, milestoneTypes);
	}

	@Override
	public List<MilestoneTypeInfo> getMilestoneTypesForAtpType(
			String atpTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<MilestoneType> milestoneTypes = atpDao.findMilestoneTypesForAtpType(atpTypeKey);
		
		return AtpAssembler.toGenericTypeInfoList(MilestoneTypeInfo.class, milestoneTypes);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByAtp(String atpKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		List<Milestone> milestones = atpDao.findMilestonesByAtp(atpKey);
		
		return AtpAssembler.toMilestoneInfoList(milestones);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDates(Date startDate,
			Date endDate) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
		List<Milestone> milestones = atpDao.findMilestonesByDates(startDate, endDate);
		
		return AtpAssembler.toMilestoneInfoList(milestones);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDatesAndType(
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

		atpInfo.setId(atpKey);
		
		Atp atp = AtpAssembler.toAtp(true, atpInfo, atpDao);
		if(atp==null){
			throw new DoesNotExistException("Atp does not exist for key: " + atpKey);
		}
		
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
		
		dateRangeInfo.setId(dateRangeKey);
		
		DateRange dateRange = AtpAssembler.toDateRange(true, dateRangeInfo, atpDao);
		
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
		

		
		milestoneInfo.setId(milestoneKey);
		
		Milestone milestone = AtpAssembler.toMilestone(true, milestoneInfo, atpDao);
		if(milestone==null){
			throw new DoesNotExistException("Milestone does not exist for key: " + milestoneKey);
		}
		
		Milestone updatedMilestone = atpDao.update(milestone);
		
		MilestoneInfo result = AtpAssembler.toMilestoneInfo(updatedMilestone);
		return result;
	}

	@Override
	public List<ValidationResultContainer> validateAtp(String validationType,
			AtpInfo atpInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateDateRange(String validationType,
			DateRangeInfo dateRangeInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateMilestone(String validationType,
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
