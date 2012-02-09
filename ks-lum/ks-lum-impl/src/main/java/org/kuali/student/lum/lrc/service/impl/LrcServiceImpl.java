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

package org.kuali.student.lum.lrc.service.impl;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

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
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.dto.*;
import org.kuali.student.lum.lrc.entity.ResultComponent;
import org.kuali.student.lum.lrc.entity.ResultComponentType;
import org.kuali.student.lum.lrc.entity.Scale;
import org.kuali.student.lum.lrc.service.LrcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lindholm
 *
 */
@WebService(endpointInterface = "org.kuali.student.lum.lrc.service.LrcService", serviceName = "LrcService", portName = "LrcService", targetNamespace = "http://student.kuali.org/wsdl/lrc")
// TODO KSCM-251
public class LrcServiceImpl implements LrcService {
	private LrcDao lrcDao;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#compareGrades(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String compareGrades(String gradeKey, String scaleKey,
			String compareGradeKey, String compareScaleKey,ContextInfo contextInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#createResultComponent(java.lang.String, org.kuali.student.lum.lrc.dto.ResultComponentInfo)
	 */
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ResultComponentInfo createResultComponent(
			String resultComponentTypeKey,
			ResultComponentInfo resultComponentInfo,ContextInfo contextInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
	    checkForMissingParameter(resultComponentInfo, "resultComponentInfo");

	    // Validate Result component
        ObjectStructureDefinition objStructure = this.getObjectStructure(ResultComponentInfo.class.getName(),contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(resultComponentInfo, objStructure,contextInfo);

        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
                
	    ResultComponent rc = LrcServiceAssembler.toResultComponent(resultComponentTypeKey, resultComponentInfo, lrcDao);
	    lrcDao.create(rc);
	    return LrcServiceAssembler.toResultComponentInfo(rc);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#deleteResultComponent(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteResultComponent(String resultComponentId,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    checkForMissingParameter(resultComponentId, "resultComponentId");
		lrcDao.delete(ResultComponent.class, resultComponentId);
		StatusInfo statusInfo = new StatusInfo();
		return statusInfo;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredential(java.lang.String)
	 */
	@Override
	public CredentialInfo getCredential(String credentialKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialKeysByCredentialType(java.lang.String)
	 */
	@Override
	public List<String> getCredentialKeysByCredentialType(
			String credentialTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialType(java.lang.String)
	 */
	@Override
	public CredentialTypeInfo getCredentialType(String credentialTypeKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialTypes()
	 */
	@Override
	public List<CredentialTypeInfo> getCredentialTypes(ContextInfo contextInfo)
			throws OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialsByKeyList(java.util.List)
	 */
	@Override
	public List<CredentialInfo> getCredentialsByKeyList(
			List<String> credentialKeyList,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredit(java.lang.String)
	 */
	@Override
	public CreditInfo getCredit(String creditKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditKeysByCreditType(java.lang.String)
	 */
	@Override
	public List<String> getCreditKeysByCreditType(String creditTypeKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditType(java.lang.String)
	 */
	@Override
	public CreditTypeInfo getCreditType(String creditTypeKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditTypes()
	 */
	@Override
	public List<CreditTypeInfo> getCreditTypes(ContextInfo contextInfo)
			throws OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditsByKeyList(java.util.List)
	 */
	@Override
	public List<CreditInfo> getCreditsByKeyList(List<String> creditKeyList,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGrade(java.lang.String)
     */
    @Override
    public GradeInfo getGrade(String gradeKey,ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeKeysByGradeType(java.lang.String)
     */
    @Override
    public List<String> getGradeKeysByGradeType(String gradeTypeKey,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeType(java.lang.String)
     */
    @Override
    public GradeTypeInfo getGradeType(String gradeTypeKey,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeTypes()
     */
    @Override
    public List<GradeTypeInfo> getGradeTypes(ContextInfo contextInfo) throws OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByKeyList(java.util.List)
     */
    @Override
    public List<GradeInfo> getGradesByKeyList(List<String> gradeKeyList,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
    }
	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByScale(java.lang.String)
	 */
	@Override
	public List<GradeInfo> getGradesByScale(String scale,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponent(java.lang.String)
	 */
	@Override
    @Transactional(readOnly=true)
	public ResultComponentInfo getResultComponent(String resultComponentId,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(resultComponentId, "resultComponentId");
	    ResultComponent resultComponent = lrcDao.fetch(ResultComponent.class, resultComponentId);

	    return LrcServiceAssembler.toResultComponentInfo(resultComponent);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentIdsByResult(java.lang.String, java.lang.String)
	 */
	//@Override
    @Transactional(readOnly=true)
	public List<String> getResultComponentIdsByResult(String resultValueId,
			String resultComponentTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(resultValueId, "resultValueId");
	    checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
	    List<String> ids = lrcDao.getResultComponentIdsByResult(resultValueId, resultComponentTypeKey);
	    return ids;
	}

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIdList(@WebParam(name = "resultValuesGroupIdList") List<String> resultValuesGroupIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getResultValuesGroupIdsByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(@WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<org.kuali.student.common.dto.ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIdList(@WebParam(name = "resultValueIdList") List<String> resultValueIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValueInfo createResultValue(@WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<org.kuali.student.common.dto.ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentIdsByResultComponentType(java.lang.String)
      */
	@Override
    @Transactional(readOnly=true)
	public List<String> getResultComponentIdsByResultComponentType(
			String resultComponentTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
        List<String> ids = lrcDao.getResultComponentIdsByResultComponentType(resultComponentTypeKey);
        return ids;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentType(java.lang.String)
	 */
	@Override
    @Transactional(readOnly=true)
	public ResultComponentTypeInfo getResultComponentType(
			String resultComponentTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
		ResultComponentType entity = lrcDao.getResultComponentType(resultComponentTypeKey);
		return LrcServiceAssembler.toResultComponentTypeInfo(entity);
	}

    @Override
    public List<ResultComponentTypeInfo> getResultComponentTypes() throws OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
	public ScaleInfo getScale(String scaleKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(scaleKey, "scaleKey");
		Scale scale = lrcDao.fetch(Scale.class, scaleKey);
		return LrcServiceAssembler.toScaleInfo(scale);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#translateGrade(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<GradeInfo> translateGrade(String gradeKey, String scaleKey,
			String translateScaleKey,ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#updateResultComponent(java.lang.String, org.kuali.student.lum.lrc.dto.ResultComponentInfo)
	 */
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ResultComponentInfo updateResultComponent(String resultComponentId,
			ResultComponentInfo resultComponentInfo,ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
	    checkForMissingParameter(resultComponentId, "resultComponentId");
        checkForMissingParameter(resultComponentInfo, "resultComponentInfo");
        
        // Validate Result component
        ObjectStructureDefinition objStructure = this.getObjectStructure(ResultComponentInfo.class.getName(),contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(resultComponentInfo, objStructure,contextInfo);

        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        ResultComponent entity = lrcDao.fetch(ResultComponent.class, resultComponentId);
        
		if (!String.valueOf(entity.getVersionNumber()).equals(resultComponentInfo.getMetaInfo().getVersionInd())){
			throw new VersionMismatchException("ResultComponent to be updated is not the current version");
		}
        
        LrcServiceAssembler.toResultComponent(entity, resultComponentInfo, lrcDao);
        lrcDao.update(entity);
        return LrcServiceAssembler.toResultComponentInfo(entity);
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
     * @param paramName
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
			String searchCriteriaTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		return searchManager.getSearchCriteriaType(searchCriteriaTypeKey, contextInfo);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo)
			throws OperationFailedException {
		return searchManager.getSearchCriteriaTypes(contextInfo);
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchResultType(searchResultTypeKey, contextInfo);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes(ContextInfo contextInfo)
			throws OperationFailedException {
		return searchManager.getSearchResultTypes(contextInfo);
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchTypeKey, "searchTypeKey");
		return searchManager.getSearchType(searchTypeKey, contextInfo);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes(ContextInfo contextInfo)
			throws OperationFailedException {
		return searchManager.getSearchTypes(contextInfo);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
		return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchTypesByResult(searchResultTypeKey, contextInfo);
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest,ContextInfo contextInfo) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, lrcDao, contextInfo);
	}

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey,ContextInfo contextInfo) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }
    @Override
    public List<String> getObjectTypes(ContextInfo contextInfo) {
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
}
