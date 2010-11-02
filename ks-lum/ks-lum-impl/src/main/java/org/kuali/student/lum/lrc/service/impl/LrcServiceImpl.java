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

import javax.jws.WebService;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchManager;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
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
@Transactional(noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class LrcServiceImpl implements LrcService {
	private LrcDao lrcDao;
    private SearchManager searchManager;

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#compareGrades(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String compareGrades(String gradeKey, String scaleKey,
			String compareGradeKey, String compareScaleKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#createResultComponent(java.lang.String, org.kuali.student.lum.lrc.dto.ResultComponentInfo)
	 */
	@Override
	public ResultComponentInfo createResultComponent(
			String resultComponentTypeKey,
			ResultComponentInfo resultComponentInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
	    checkForMissingParameter(resultComponentInfo, "resultComponentInfo");

	    ResultComponent rc = LrcServiceAssembler.toResultComponent(resultComponentTypeKey, resultComponentInfo, lrcDao);
	    lrcDao.create(rc);
	    return LrcServiceAssembler.toResultComponentInfo(rc);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#deleteResultComponent(java.lang.String)
	 */
	@Override
	public StatusInfo deleteResultComponent(String resultComponentId)
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
	public CredentialInfo getCredential(String credentialKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialKeysByCredentialType(java.lang.String)
	 */
	@Override
	public List<String> getCredentialKeysByCredentialType(
			String credentialTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialType(java.lang.String)
	 */
	@Override
	public CredentialTypeInfo getCredentialType(String credentialTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialTypes()
	 */
	@Override
	public List<CredentialTypeInfo> getCredentialTypes()
			throws OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialsByKeyList(java.util.List)
	 */
	@Override
	public List<CredentialInfo> getCredentialsByKeyList(
			List<String> credentialKeyList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredit(java.lang.String)
	 */
	@Override
	public CreditInfo getCredit(String creditKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditKeysByCreditType(java.lang.String)
	 */
	@Override
	public List<String> getCreditKeysByCreditType(String creditTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditType(java.lang.String)
	 */
	@Override
	public CreditTypeInfo getCreditType(String creditTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditTypes()
	 */
	@Override
	public List<CreditTypeInfo> getCreditTypes()
			throws OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditsByKeyList(java.util.List)
	 */
	@Override
	public List<CreditInfo> getCreditsByKeyList(List<String> creditKeyList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGrade(java.lang.String)
     */
    @Override
    public GradeInfo getGrade(String gradeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeKeysByGradeType(java.lang.String)
     */
    @Override
    public List<String> getGradeKeysByGradeType(String gradeTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeType(java.lang.String)
     */
    @Override
    public GradeTypeInfo getGradeType(String gradeTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradeTypes()
     */
    @Override
    public List<GradeTypeInfo> getGradeTypes() throws OperationFailedException {
		throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByKeyList(java.util.List)
     */
    @Override
    public List<GradeInfo> getGradesByKeyList(List<String> gradeKeyList)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
    }
	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByScale(java.lang.String)
	 */
	@Override
	public List<GradeInfo> getGradesByScale(String scale)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponent(java.lang.String)
	 */
	@Override
	public ResultComponentInfo getResultComponent(String resultComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(resultComponentId, "resultComponentId");
	    ResultComponent resultComponent = lrcDao.fetch(ResultComponent.class, resultComponentId);

	    return LrcServiceAssembler.toResultComponentInfo(resultComponent);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentIdsByResult(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getResultComponentIdsByResult(String resultValueId,
			String resultComponentTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(resultValueId, "resultValueId");
	    checkForMissingParameter(resultComponentTypeKey, "resultComponentTypeKey");
	    List<String> ids = lrcDao.getResultComponentIdsByResult(resultValueId, resultComponentTypeKey);
	    return ids;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentIdsByResultComponentType(java.lang.String)
	 */
	@Override
	public List<String> getResultComponentIdsByResultComponentType(
			String resultComponentTypeKey) throws DoesNotExistException,
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
	public ResultComponentTypeInfo getResultComponentType(
			String resultComponentTypeKey) throws DoesNotExistException,
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
	public List<ResultComponentTypeInfo> getResultComponentTypes()
			throws OperationFailedException {
		List<ResultComponentType> rct = lrcDao.find(ResultComponentType.class);
		return LrcServiceAssembler.toResultComponentTypeInfos(rct);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getScale(java.lang.String)
	 */
	@Override
	public ScaleInfo getScale(String scaleKey) throws DoesNotExistException,
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
			String translateScaleKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#updateResultComponent(java.lang.String, org.kuali.student.lum.lrc.dto.ResultComponentInfo)
	 */
	@Override
	public ResultComponentInfo updateResultComponent(String resultComponentId,
			ResultComponentInfo resultComponentInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
	    checkForMissingParameter(resultComponentId, "resultComponentId");
        checkForMissingParameter(resultComponentInfo, "resultComponentInfo");

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
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, lrcDao);
	}

}
