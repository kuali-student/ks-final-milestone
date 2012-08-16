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

package org.kuali.student.r1.lum.lrc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r1.lum.lrc.dao.LrcDao;
import org.kuali.student.r1.lum.lrc.entity.ResultComponent;
import org.kuali.student.r1.lum.lrc.entity.ResultComponentType;
import org.kuali.student.r1.lum.lrc.entity.Scale;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchManager;
import org.kuali.student.r1.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.r1.lum.lrc.dto.ScaleInfo;
import org.kuali.student.r1.lum.lrc.service.impl.LrcServiceAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r1.lum.lrc.service.LrcService;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lindholm
 *
 */
@WebService(endpointInterface = "org.kuali.student.r1.lum.lrc.service.LrcService", serviceName = "LrcService", portName = "LRCService", targetNamespace = "http://student.kuali.org/wsdl/lrc")
public class LrcServiceImpl implements LrcService {
    private LrcDao lrcDao;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;


    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#compareGrades(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
      */
//	@Override
//	public String compareGrades(String gradeKey, String scaleKey,
//			String compareGradeKey, String compareScaleKey, ContextInfo contextInfo)
//			throws InvalidParameterException, MissingParameterException,
//			OperationFailedException {
//		throw new UnsupportedOperationException("Method not yet implemented!");
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCredential(java.lang.String)
      */
//	@Override
//	public CredentialInfo getCredential(String credentialKey)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialKeysByCredentialType(java.lang.String)
      */
//	@Override
//	public List<String> getCredentialKeysByCredentialType(
//			String credentialTypeKey) throws DoesNotExistException,
//			InvalidParameterException, MissingParameterException,
//			OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialType(java.lang.String)
      */
//	@Override
//	public CredentialTypeInfo getCredentialType(String credentialTypeKey)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialTypes()
      */
//	@Override
//	public List<CredentialTypeInfo> getCredentialTypes()
//			throws OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialsByKeyList(java.util.List)
      */
//	@Override
//	public List<CredentialInfo> getCredentialsByKeyList(
//			List<String> credentialKeyList) throws DoesNotExistException,
//			InvalidParameterException, MissingParameterException,
//			OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCredit(java.lang.String)
      */
//	@Override
//	public CreditInfo getCredit(String creditKey) throws DoesNotExistException,
//			InvalidParameterException, MissingParameterException,
//			OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCreditKeysByCreditType(java.lang.String)
      */
//	@Override
//	public List<String> getCreditKeysByCreditType(String creditTypeKey)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCreditType(java.lang.String)
      */
//	@Override
//	public CreditTypeInfo getCreditType(String creditTypeKey)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCreditTypes()
      */
//	@Override
//	public List<CreditTypeInfo> getCreditTypes()
//			throws OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getCreditsByKeyList(java.util.List)
      */
//	@Override
//	public List<CreditInfo> getCreditsByKeyList(List<String> creditKeyList)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGrade(java.lang.String)
     */
//    @Override
//    public GradeInfo getGrade(String gradeKey) throws DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException {
//		throw new UnsupportedOperationException();
//    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeKeysByGradeType(java.lang.String)
     */
//    @Override
//    public List<String> getGradeKeysByGradeType(String gradeTypeKey)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeType(java.lang.String)
     */
//    @Override
//    public GradeTypeInfo getGradeType(String gradeTypeKey)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeTypes()
     */
//    @Override
//    public List<GradeTypeInfo> getGradeTypes() throws OperationFailedException {
//		throw new UnsupportedOperationException();
//    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByKeyList(java.util.List)
     */
//    @Override
//    public List<GradeInfo> getGradesByKeyList(List<String> gradeKeyList)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//    }
    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByScale(java.lang.String)
      */
//	@Override
//	public List<GradeInfo> getGradesByScale(String scale)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		throw new UnsupportedOperationException();
//	}

    @Override
    @Transactional(readOnly=true)
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(resultValuesGroupId, "resultComponentId");
        ResultComponent resultComponent = lrcDao.fetch(ResultComponent.class, resultValuesGroupId);

        return LrcServiceAssembler.toResultValuesGroupInfo(resultComponent);
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentType(java.lang.String)
      */
    @Override
    @Transactional(readOnly=true)
    public ResultComponentTypeInfo getResultComponentType(
            String resultComponentTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
        ResultComponentType entity = lrcDao.getResultComponentType(resultComponentTypeKey);
        return LrcServiceAssembler.toResultComponentTypeInfo(entity);
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentTypes()
      */
    @Override
    @Transactional(readOnly=true)
    public List<ResultComponentTypeInfo> getResultComponentTypes(ContextInfo contextInfo)
            throws OperationFailedException {
        List<ResultComponentType> rct = lrcDao.find(ResultComponentType.class);
        return LrcServiceAssembler.toResultComponentTypeInfos(rct);
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getScale(java.lang.String)
      */
    @Override
    @Transactional(readOnly=true)
    public ScaleInfo getScale(String scaleKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(scaleKey, "scaleKey");
        Scale scale = lrcDao.fetch(Scale.class, scaleKey);
        return LrcServiceAssembler.toScaleInfo(scale);
    }

    public ResultScaleInfo getResultScale(String resultScaleKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented - replacing getScale!");
    }

    /**
     * @return the lrcDao
     */
    public LrcDao getLrcDao() {
        return lrcDao;
    }

    /**
     * @param lrcDao the lrcDao to set
     */
    public void setLrcDao(LrcDao lrcDao) {
        this.lrcDao = lrcDao;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param parameter name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);

    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
            throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
            throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes()
            throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
    }

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }
    @Override
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
     * @param validatorFactory the validatorFactory to set
     */
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIds(
            List<String> resultValuesGroupIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(
            String resultValueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(resultValueId, "resultValueId");
        List<ResultValuesGroupInfo> resultValuesGroups = new ArrayList<ResultValuesGroupInfo>();
        List<String> ids = lrcDao.getResultComponentIdsByResult(resultValueId);
        for (String id : ids){
            resultValuesGroups.add(this.getResultValuesGroup(id, context));
        }
        return resultValuesGroups;
    }

    @Override
    @Transactional(readOnly=true)
    public List<String> getResultValuesGroupIdsByType(
            String resultValuesGroupTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(resultValuesGroupTypeKey, "resultComponentTypeKey");
        List<String> ids = lrcDao.getResultComponentIdsByResultComponentType(resultValuesGroupTypeKey);
        return ids;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public ResultValuesGroupInfo createResultValuesGroup(
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(gradeValuesGroupInfo, "gradeValuesGroupInfo");

        // Validate Result component
        ObjectStructureDefinition objStructure = this.getObjectStructure(ResultValuesGroupInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(gradeValuesGroupInfo, objStructure, context);

        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        ResultComponent rc = LrcServiceAssembler.toResultComponent(gradeValuesGroupInfo.getTypeKey(), gradeValuesGroupInfo, lrcDao);
        lrcDao.create(rc);
        return LrcServiceAssembler.toResultValuesGroupInfo(rc);
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public ResultValuesGroupInfo updateResultValuesGroup(
            String resultValuesGroupId,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        checkForMissingParameter(resultValuesGroupId, "resultValuesGroupId");
        checkForMissingParameter(gradeValuesGroupInfo, "gradeValuesGroupInfo");

        // Validate Result component
        ObjectStructureDefinition objStructure = this.getObjectStructure(ResultValuesGroupInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(gradeValuesGroupInfo, objStructure, context);

        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        ResultComponent entity = lrcDao.fetch(ResultComponent.class, resultValuesGroupId);

        if (!String.valueOf(entity.getVersionNumber()).equals(gradeValuesGroupInfo.getMeta().getVersionInd())){
            throw new VersionMismatchException("ResultComponent to be updated is not the current version");
        }

        LrcServiceAssembler.toResultComponent(entity, gradeValuesGroupInfo, lrcDao);
        lrcDao.update(entity);
        return LrcServiceAssembler.toResultValuesGroupInfo(entity);
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public StatusInfo deleteResultValuesGroup(String resultValuesGroupId,
                                              ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(resultValuesGroupId, "resultComponentId");
        lrcDao.delete(ResultComponent.class, resultValuesGroupId);
        StatusInfo statusInfo = new StatusInfo();
        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(
            String validationType, ResultValuesGroupInfo gradeValuesGroupInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public ResultValueInfo getResultValue(String resultValueId,
                                          ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIds(
            List<String> resultValueIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(
            String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public ResultValueInfo createResultValue(ResultValueInfo resultValueInfo,
                                             ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueId,
                                             ResultValueInfo resultValueInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueId,
                                        ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(
            String validationType, ResultValueInfo resultValueInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey,
                                                         ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("R2 Contract Method not yet implemented!");
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, lrcDao);
    }

}
