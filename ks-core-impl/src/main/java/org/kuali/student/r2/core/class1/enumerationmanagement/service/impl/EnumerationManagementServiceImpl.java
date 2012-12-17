/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.class1.enumerationmanagement.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumContextValueDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumeratedValueDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumerationDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumeratedValueEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationEntity;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.EnumerationManagementServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Enumeration Management Service implementation class.
 *
 * @Version 2.0
 */
@WebService(name = "EnumerationManagementService", serviceName = "EnumerationManagementService", portName = "EnumerationManagementService", targetNamespace = EnumerationManagementServiceConstants.NAMESPACE)
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class EnumerationManagementServiceImpl implements EnumerationManagementService {

    final static Logger logger = LoggerFactory.getLogger(EnumerationManagementServiceImpl.class);

    private SearchManager searchManager;

    private EnumerationDao enumDao;
    private EnumeratedValueDao enumValueDao;
    private EnumContextValueDao enumContextValueDao;

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public EnumerationDao getEnumDao() {
        return enumDao;
    }

    public void setEnumDao(EnumerationDao enumDao) {
        this.enumDao = enumDao;
    }

    public EnumeratedValueDao getEnumValueDao() {
        return enumValueDao;
    }

    public void setEnumValueDao(EnumeratedValueDao enumValueDao) {
        this.enumValueDao = enumValueDao;
    }

    public EnumContextValueDao getEnumContextValueDao() {
        return enumContextValueDao;
    }

    public void setEnumContextValueDao(EnumContextValueDao enumContextValueDao) {
        this.enumContextValueDao = enumContextValueDao;
    }

    @Override
    @Transactional(readOnly=true)
    public List<EnumerationInfo> getEnumerations(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<EnumerationEntity> enumEntities =  this.enumDao.findAll();
        List<EnumerationInfo> enumInfos = new ArrayList<EnumerationInfo>(enumEntities.size());

        for (EnumerationEntity enumeration : enumEntities){
            enumInfos.add(enumeration.toDto());
        }

        return enumInfos;

    }

    @Override
    public EnumerationInfo getEnumeration(String enumerationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        EnumerationEntity entity = enumDao.find(enumerationKey);

        if(entity == null)
            throw new DoesNotExistException(enumerationKey);

        return entity.toDto();
    }

    @Override
    public List<EnumeratedValueInfo> getEnumeratedValues(String enumerationKey, String contextTypeKey, String contextValue, Date contextDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<EnumeratedValueEntity> enumeratedValues = null;

        if(enumerationKey != null && contextTypeKey != null && contextValue != null && contextDate != null){
            enumeratedValues = enumValueDao.getByContextAndDate(enumerationKey, contextTypeKey, contextValue, contextDate);
        }
        else if(enumerationKey != null && contextTypeKey != null && contextValue != null){
            enumeratedValues = enumValueDao.getByContextTypeAndValue(enumerationKey, contextTypeKey, contextValue);
        }
        else if(enumerationKey != null && contextDate != null){
            enumeratedValues = enumValueDao.getByDate(enumerationKey, contextDate);
        }
        else if(enumerationKey != null){
            enumeratedValues = enumValueDao.getByEnumerationKey(enumerationKey);
        }

        if(enumeratedValues == null)
            throw new DoesNotExistException(enumerationKey);

        List<EnumeratedValueInfo> enumeratedValueInfos = new ArrayList<EnumeratedValueInfo>(enumeratedValues.size());
        for (EnumeratedValueEntity enumeratedValue : enumeratedValues){
            enumeratedValueInfos.add(enumeratedValue.toDto());
        }

        return enumeratedValueInfos;
    }

    @Override
    public List<ValidationResultInfo> validateEnumeratedValue(String validationTypeKey, String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; //FIXME need real validation
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public EnumeratedValueInfo updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        EnumerationEntity enumerationEntity = enumDao.find(enumeratedValueInfo.getEnumerationKey());
        if(enumerationEntity == null)
            throw new DoesNotExistException(enumeratedValueInfo.getEnumerationKey());

        EnumeratedValueEntity modifiedEntity = new EnumeratedValueEntity(enumeratedValueInfo);
        modifiedEntity.setEnumeration(enumerationEntity);

        try {
            modifiedEntity.setId(enumValueDao.getByEnumerationKeyAndCode(enumerationKey, code).getId());
        }catch (NoResultException e) {
            throw new DoesNotExistException(enumerationKey + code);
        }

        enumValueDao.merge(modifiedEntity);

        return enumValueDao.find(modifiedEntity.getId()).toDto();

    }

    @Override
    public StatusInfo deleteEnumeratedValue(String enumerationKey, String code, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        EnumeratedValueEntity enumeratedValue = enumValueDao.getByEnumerationKeyAndCode(enumerationKey, code);
        if (null != enumeratedValue) {
            enumValueDao.remove(enumeratedValue);
        } else
            status.setSuccess(Boolean.FALSE);

        return status;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public EnumeratedValueInfo addEnumeratedValue(String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        EnumerationEntity enumerationEntity = enumDao.find(enumeratedValueInfo.getEnumerationKey());
        if(enumerationEntity == null)
            throw new DoesNotExistException(enumeratedValueInfo.getEnumerationKey());

        EnumeratedValueEntity entity = null;
        try {

            enumValueDao.getByEnumerationKeyAndCode(enumerationKey, code);
            throw new AlreadyExistsException();

        } catch (NoResultException e) {

            entity = new EnumeratedValueEntity(enumeratedValueInfo);
            entity.setEnumeration(enumerationEntity);

            enumValueDao.persist(entity);
        }

        return enumValueDao.find(entity.getId()).toDto();

    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchCriteriaTypes(contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchResultTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchTypes(contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(
            String searchResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<EnumeratedValueEntity> returnvalues = new ArrayList<EnumeratedValueEntity>();
        if(searchRequest.getSearchKey().equals("enumeration.management.search")){
            List<String> enumTypes = null;
            List<String> enumCodes = null;
            for(SearchParamInfo parm : searchRequest.getParams()){
                if((parm.getKey().equals("enumeration.queryParam.enumerationType")) && (parm.getValues() != null)){
                    enumTypes = parm.getValues();
                } else if ((parm.getKey().equals("enumeration.queryParam.enumerationCode") && (parm.getValues() != null))){
                    enumCodes = parm.getValues();
                } else if ((parm.getKey().equals("enumeration.queryParam.enumerationOptionalCode") && (parm.getValues() != null))){
                    enumCodes = parm.getValues();
                }
            }

            for (String type : enumTypes){
                List<EnumeratedValueEntity> enumvalues = enumValueDao.getByEnumerationKey(type);
                if ((enumCodes != null) && (enumCodes.size() > 0)){
                    for(EnumeratedValueEntity enumValue : enumvalues){
                        for(String code : enumCodes){
                            if (enumValue.getCode().equals(code)){
                                returnvalues.add(enumValue);
                                break;
                            } else if (enumValue.getCode().startsWith(code)){
                                returnvalues.add(enumValue);
                                break;
                            }
                        }
                    }
                } else {
                    returnvalues.addAll(enumvalues);
                }
            }

        }

        if (returnvalues == null){
            return null;
        }

        SearchResultInfo searchResult = new SearchResultInfo();

        //Use a hashset of the cell values to remove duplicates
        for(EnumeratedValueEntity enumValue : returnvalues){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell("enumeration.resultColumn.code", enumValue.getCode());
            row.addCell("enumeration.resultColumn.abbrevValue", enumValue.getAbbrevValue());
            row.addCell("enumeration.resultColumn.value", enumValue.getValue());
            //row.addCell("enumeration.resultColumn.effectiveDate", enumValue.getEffectiveDate());
            //row.addCell("enumeration.resultColumn.expirationDate", enumValue.getExpirationDate());
            row.addCell("enumeration.resultColumn.sortKey", enumValue.getSortKey());
            searchResult.getRows().add(row);
        }

        return searchResult;
//        return searchManager.search(searchRequest, contextInfo);
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

}
