/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.core.atp.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.DictionaryService;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.atp.dao.AtpDao;
import org.kuali.student.core.atp.dto.AtpAtpRelationInfo;
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
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.atp.service.AtpService", serviceName = "AtpService", portName = "AtpService", targetNamespace = "http://student.kuali.org/wsdl/atp")
@Transactional(readOnly=true,noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class AtpServiceImpl implements AtpService {

    private AtpDao atpDao;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;

    // @Override
    @Transactional(readOnly=false)
    public DateRangeInfo addDateRange(String atpKey, String dateRangeKey, DateRangeInfo dateRangeInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CheckMissingParameters(new String[]{"atpKey", "dateRangeKey", "dateRangeInfo"}, new Object[]{atpKey, dateRangeKey, dateRangeInfo});

        dateRangeInfo.setAtpId(atpKey);
        dateRangeInfo.setId(dateRangeKey);
        
        // Validate Daterange
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = validateDateRange("OBJECT", dateRangeInfo);
        } catch (DoesNotExistException e1) {
            throw new OperationFailedException("Validation call failed." + e1.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        
        DateRange dateRange = null;

        try {
            dateRange = AtpAssembler.toDateRange(false, dateRangeInfo, atpDao);
        } catch (DoesNotExistException e) {} catch (VersionMismatchException e) {}

        atpDao.create(dateRange);

        return AtpAssembler.toDateRangeInfo(dateRange);
    }

    private void CheckMissingParameters(String[] paramNames, Object[] params) throws MissingParameterException {
        String errors = null;
        int i = 0;
        for (Object param : params) {
            if (param == null) {
                errors = errors == null ? paramNames[i] : errors + ", " + paramNames[i];
            }
            i++;
        }
        if (errors != null) {
            throw new MissingParameterException("Missing Parameters: " + errors);
        }
    }

    // @Override
    @Transactional(readOnly=false)
    public MilestoneInfo addMilestone(String atpKey, String milestoneKey, MilestoneInfo milestoneInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CheckMissingParameters(new String[]{"atpKey", "milestoneKey", "milestoneInfo"}, new Object[]{atpKey, milestoneKey, milestoneInfo});

     // TODO KSCM        milestoneInfo.setAtpId(atpKey);
        milestoneInfo.setId(milestoneKey);

        // Validate Milestone
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = validateMilestone("OBJECT", milestoneInfo);
        } catch (DoesNotExistException e1) {
            throw new OperationFailedException("Validation call failed." + e1.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
                
        Milestone milestone = null;
        try {
            milestone = AtpAssembler.toMilestone(false, milestoneInfo, atpDao);
        } catch (DoesNotExistException e) {} catch (VersionMismatchException e) {}

        atpDao.create(milestone);

        return AtpAssembler.toMilestoneInfo(milestone);
    }

    // @Override
    @Transactional(readOnly=false)
    public AtpInfo createAtp(String atpTypeKey, String atpKey, AtpInfo atpInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CheckMissingParameters(new String[]{"atpTypeKey", "atpKey", "atpInfo"}, new Object[]{atpTypeKey, atpKey, atpInfo});

     // TODO KSCM        atpInfo.setType(atpTypeKey);
        atpInfo.setId(atpKey);

        // Validate Atp
        List<ValidationResultInfo> validationResults;
        try {
            validationResults = validateAtp("OBJECT", atpInfo);
        } catch (DoesNotExistException e1) {
            throw new OperationFailedException("Validation call failed." + e1.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        Atp atp = null;
        try {
            atp = AtpAssembler.toAtp(false, atpInfo, atpDao);
        } catch (DoesNotExistException e) {} catch (VersionMismatchException e) {}

        atpDao.create(atp);

        AtpInfo result = AtpAssembler.toAtpInfo(atp);
        return result;
    }

    // @Override
    @Transactional(readOnly=false)
    public StatusInfo deleteAtp(String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        atpDao.delete(Atp.class, atpKey);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    // @Override
    public AtpInfo getAtp(String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        Atp atp = atpDao.fetch(Atp.class, atpKey);

        return AtpAssembler.toAtpInfo(atp);
    }

    // @Override
    public AtpDurationTypeInfo getAtpDurationType(String atpDurationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        AtpDurationType atpDurationType = atpDao.fetch(AtpDurationType.class, atpDurationTypeKey);

        return AtpAssembler.toGenericTypeInfo(AtpDurationTypeInfo.class, atpDurationType);
    }

    // @Override
    public AtpSeasonalTypeInfo getAtpSeasonalType(String atpSeasonalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        AtpSeasonalType atpSeasonalType = atpDao.fetch(AtpSeasonalType.class, atpSeasonalTypeKey);

        return AtpAssembler.toGenericTypeInfo(AtpSeasonalTypeInfo.class, atpSeasonalType);
    }

    // @Override
    public AtpTypeInfo getAtpType(String atpTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        AtpType atpType = atpDao.fetch(AtpType.class, atpTypeKey);

        return AtpAssembler.toAtpTypeInfo(atpType);
    }

    // @Override
    public DateRangeInfo getDateRange(String dateRangeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        DateRange dateRange = atpDao.fetch(DateRange.class, dateRangeKey);

        return AtpAssembler.toDateRangeInfo(dateRange);

    }

    // @Override
    public DateRangeTypeInfo getDateRangeType(String dateRangeTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        DateRangeType dateRangeType = atpDao.fetch(DateRangeType.class, dateRangeTypeKey);

        return AtpAssembler.toGenericTypeInfo(DateRangeTypeInfo.class, dateRangeType);
    }

    // @Override
    public MilestoneInfo getMilestone(String milestoneKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        Milestone milestone = atpDao.fetch(Milestone.class, milestoneKey);

        return AtpAssembler.toMilestoneInfo(milestone);

    }

    // @Override
    public MilestoneTypeInfo getMilestoneType(String milestoneTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        MilestoneType milestoneType = atpDao.fetch(MilestoneType.class, milestoneTypeKey);

        return AtpAssembler.toGenericTypeInfo(MilestoneTypeInfo.class, milestoneType);
    }

    // @Override
    public List<AtpDurationTypeInfo> getAtpDurationTypes() throws OperationFailedException {

        List<AtpDurationType> atpDurationTypes = atpDao.find(AtpDurationType.class);

        return AtpAssembler.toGenericTypeInfoList(AtpDurationTypeInfo.class, atpDurationTypes);
    }

    // @Override
    public List<AtpSeasonalTypeInfo> getAtpSeasonalTypes() throws OperationFailedException {

        List<AtpSeasonalType> atpSeasonalTypes = atpDao.find(AtpSeasonalType.class);

        return AtpAssembler.toGenericTypeInfoList(AtpSeasonalTypeInfo.class, atpSeasonalTypes);
    }

    // @Override
    public List<AtpTypeInfo> getAtpTypes() throws OperationFailedException {

        List<AtpType> atpTypes = atpDao.find(AtpType.class);

        return AtpAssembler.toAtpTypeInfoList(atpTypes);
    }

    // @Override
    public List<AtpInfo> getAtpsByAtpType(String atpTypeKey) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<Atp> atps = atpDao.findAtpsByAtpType(atpTypeKey);

        return AtpAssembler.toAtpInfoList(atps);
    }

    // @Override
    public List<AtpInfo> getAtpsByDate(Date searchDate) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<Atp> atps = atpDao.findAtpsByDate(searchDate);

        return AtpAssembler.toAtpInfoList(atps);
    }

    // @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<Atp> atps = atpDao.findAtpsByDates(startDate, endDate);

        return AtpAssembler.toAtpInfoList(atps);
    }

    // @Override
    public List<DateRangeTypeInfo> getDateRangeTypes() throws OperationFailedException {

        List<DateRangeType> dateRangeTypes = atpDao.find(DateRangeType.class);

        return AtpAssembler.toGenericTypeInfoList(DateRangeTypeInfo.class, dateRangeTypes);
    }

    // @Override
    public List<DateRangeTypeInfo> getDateRangeTypesForAtpType(String atpTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<DateRangeType> dateRangeTypes = atpDao.findDateRangeTypesForAtpType(atpTypeKey);

        return AtpAssembler.toGenericTypeInfoList(DateRangeTypeInfo.class, dateRangeTypes);
    }

    // @Override
    public List<DateRangeInfo> getDateRangesByAtp(String atpKey) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<DateRange> dateRanges = atpDao.findDateRangesByAtp(atpKey);

        return AtpAssembler.toDateRangeInfoList(dateRanges);
    }

    // @Override
    public List<DateRangeInfo> getDateRangesByDate(Date searchDate) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<DateRange> dateRanges = atpDao.findDateRangesByDate(searchDate);

        return AtpAssembler.toDateRangeInfoList(dateRanges);
    }

    // @Override
    public List<MilestoneTypeInfo> getMilestoneTypes() throws OperationFailedException {

        List<MilestoneType> milestoneTypes = atpDao.find(MilestoneType.class);

        return AtpAssembler.toGenericTypeInfoList(MilestoneTypeInfo.class, milestoneTypes);
    }

    // @Override
    public List<MilestoneTypeInfo> getMilestoneTypesForAtpType(String atpTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        List<MilestoneType> milestoneTypes = atpDao.findMilestoneTypesForAtpType(atpTypeKey);

        return AtpAssembler.toGenericTypeInfoList(MilestoneTypeInfo.class, milestoneTypes);
    }

    // @Override
    public List<MilestoneInfo> getMilestonesByAtp(String atpKey) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<Milestone> milestones = atpDao.findMilestonesByAtp(atpKey);

        return AtpAssembler.toMilestoneInfoList(milestones);
    }

    // @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<Milestone> milestones = atpDao.findMilestonesByDates(startDate, endDate);

        return AtpAssembler.toMilestoneInfoList(milestones);
    }

    // @Override
    public List<MilestoneInfo> getMilestonesByDatesAndType(String milestoneTypeKey, Date startDate, Date endDate) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<Milestone> milestones = atpDao.findMilestonesByDatesAndType(milestoneTypeKey, startDate, endDate);

        return AtpAssembler.toMilestoneInfoList(milestones);
    }

    // @Override
    @Transactional(readOnly=false)
    public StatusInfo removeDateRange(String dateRangeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        atpDao.delete(DateRange.class, dateRangeKey);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    // @Override
    @Transactional(readOnly=false)
    public StatusInfo removeMilestone(String milestoneKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        atpDao.delete(Milestone.class, milestoneKey);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    // @Override
    @Transactional(readOnly=false)
    public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        CheckMissingParameters(new String[]{"atpKey", "atpInfo"}, new Object[]{atpKey, atpInfo});

        atpInfo.setId(atpKey);

        List<ValidationResultInfo> validationResults = validateAtp("OBJECT", atpInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        Atp atp = AtpAssembler.toAtp(true, atpInfo, atpDao);
        if (atp == null) {
            throw new DoesNotExistException("Atp does not exist for key: " + atpKey);
        }

        Atp updatedAtp = atpDao.update(atp);

        AtpInfo result = AtpAssembler.toAtpInfo(updatedAtp);
        return result;
    }

    // @Override
    @Transactional(readOnly=false)
    public DateRangeInfo updateDateRange(String dateRangeKey, DateRangeInfo dateRangeInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        CheckMissingParameters(new String[]{"dateRangeKey", "dateRangeInfo"}, new Object[]{dateRangeKey, dateRangeInfo});

        dateRangeInfo.setId(dateRangeKey);

        List<ValidationResultInfo> validationResults = validateDateRange("OBJECT", dateRangeInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        DateRange dateRange = AtpAssembler.toDateRange(true, dateRangeInfo, atpDao);

        DateRange updatedDateRange = atpDao.update(dateRange);

        DateRangeInfo result = AtpAssembler.toDateRangeInfo(updatedDateRange);
        return result;
    }

    // @Override
    @Transactional(readOnly=false)
    public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        CheckMissingParameters(new String[]{"milestoneKey", "milestoneInfo"}, new Object[]{milestoneKey, milestoneInfo});

        milestoneInfo.setId(milestoneKey);

        // Validate MilestoneInfo
        List<ValidationResultInfo> validationResults = validateMilestone("OBJECT", milestoneInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        
        Milestone milestone = AtpAssembler.toMilestone(true, milestoneInfo, atpDao);
        if (milestone == null) {
            throw new DoesNotExistException("Milestone does not exist for key: " + milestoneKey);
        }

        Milestone updatedMilestone = atpDao.update(milestone);

        MilestoneInfo result = AtpAssembler.toMilestoneInfo(updatedMilestone);
        return result;
    }

    // @Override
    public List<ValidationResultInfo> validateAtp(String validationType, AtpInfo atpInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(atpInfo, "dateRangeInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(AtpInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = null;
     // TODO KSCM        		validationResults = defaultValidator.validateObject(atpInfo, objStructure);
        return validationResults;
    }

    // @Override
    public List<ValidationResultInfo> validateDateRange(String validationType, DateRangeInfo dateRangeInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(dateRangeInfo, "dateRangeInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(DateRangeInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = null;
     // TODO KSCM        validationResults = defaultValidator.validateObject(dateRangeInfo, objStructure);
        return validationResults;
    }

    // @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(milestoneInfo, "milestoneInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(MilestoneInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = null;
     // TODO KSCM        validationResults = defaultValidator.validateObject(milestoneInfo, objStructure);
        return validationResults;
    }

    /**
     * Check for missing parameter and thow localized exception if missing
     * 
     * @param param
     * @param parameter
     *            name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    public AtpDao getAtpDao() {
        return atpDao;
    }

    public void setAtpDao(AtpDao atpDao) {
        this.atpDao = atpDao;
    }

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    // @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey, contextInfo);
    }

    // @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo) throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes(contextInfo);
    }

    // @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey, contextInfo);
    }

    // @Override
    public List<SearchResultTypeInfo> getSearchResultTypes(ContextInfo contextInfo) throws OperationFailedException {
        return searchManager.getSearchResultTypes(contextInfo);
    }

    // @Override
    public SearchTypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    // @Override
    public List<SearchTypeInfo> getSearchTypes(ContextInfo contextInfo) throws OperationFailedException {
        return searchManager.getSearchTypes(contextInfo);
    }

    // @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    // @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    // @Override
    public SearchResult search(SearchRequest searchRequest, ContextInfo contextInfo) throws MissingParameterException {
        return searchManager.search(searchRequest, atpDao, contextInfo);
    }

    // @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    // @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    /**
     * @return the validatorFactory
     */
    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    /**
     * @param validatorFactory
     *            the validatorFactory to set
     */
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    /**
     * @return the dictionaryServiceDelegate
     */
    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    /**
     * @param dictionaryServiceDelegate the dictionaryServiceDelegate to set
     */
    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

	@Override
	public AtpInfo getAtp(String atpId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByIds(List<String> atpIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAtpIdsByType(String atpTypeKey,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByDate(Date date, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByDateAndType(Date date, String atpTypeKey,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByDatesAndType(Date startDate, Date endDate,
			String atpTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByStartDateRange(Date dateRangeStart,
			Date dateRangeEnd, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> getAtpsByStartDateRangeAndType(Date dateRangeStart,
			Date dateRangeEnd, String atpTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForAtpIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpInfo> searchForAtps(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<org.kuali.student.common.dto.ValidationResultInfo> validateAtp(
			String validationTypeKey, String atpTypeKey, AtpInfo atpInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpInfo createAtp(String atpId, AtpInfo atpInfo,
			ContextInfo contextInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpInfo updateAtp(String atpId, AtpInfo atpInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteAtp(String atpId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(
			List<String> atpAtpRelationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAtpAtpRelationIdsByType(
			String atpAtpRelationTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(String atpId,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(
			String atpId, String atpAtpRelationTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AtpAtpRelationInfo> searchForAtpAtpRelations(
			QueryByCriteria criteria, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<org.kuali.student.common.dto.ValidationResultInfo> validateAtpAtpRelation(
			String validationTypeKey, String atpId, String atpPeerId,
			String atpAtpRelationTypeKey,
			AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpAtpRelationInfo createAtpAtpRelation(String atpId,
			String atpPeerId, AtpAtpRelationInfo atpAtpRelationInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId,
			AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo getMilestone(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMilestoneIdsByType(String milestoneTypeKey,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDates(Date startDate,
			Date endDate, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> getMilestonesForAtp(String atpId,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDatesForAtp(String atpId,
			Date startDate, Date endDate, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> getMilestonesByTypeForAtp(String atpId,
			String milestoneTypeKey, ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> getImpactedMilestones(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForMilestoneIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<org.kuali.student.common.dto.ValidationResultInfo> validateMilestone(
			String validationTypeKey, MilestoneInfo milestoneInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo createMilestone(MilestoneInfo milestoneInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo updateMilestone(String milestoneId,
			MilestoneInfo milestoneInfo, ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteMilestone(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MilestoneInfo calculateMilestone(String milestoneId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addMilestoneToAtp(String milestoneId, String atpId,
			ContextInfo contextInfo) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeMilestoneFromAtp(String milestoneId, String atpId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
    
}
