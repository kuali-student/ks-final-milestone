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
package org.kuali.student.brms.statement.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.student.brms.statement.dao.StatementDao;
import org.kuali.student.brms.statement.dto.NlUsageTypeInfo;
import org.kuali.student.brms.statement.dto.RefStatementRelationInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementTreeViewInfo;
import org.kuali.student.brms.statement.dto.StatementTypeInfo;
import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.entity.ReqComponentType;
import org.kuali.student.brms.statement.entity.Statement;
import org.kuali.student.brms.statement.entity.StatementType;
import org.kuali.student.brms.statement.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.brms.statement.service.StatementService;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.brms.statement.dao.StatementDao;
import org.kuali.student.brms.statement.dto.NlUsageTypeInfo;
import org.kuali.student.brms.statement.dto.RefStatementRelationInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementTypeInfo;
import org.kuali.student.brms.statement.entity.NlUsageType;
import org.kuali.student.brms.statement.entity.ObjectType;
import org.kuali.student.brms.statement.entity.RefStatementRelation;
import org.kuali.student.brms.statement.entity.RefStatementRelationAttribute;
import org.kuali.student.brms.statement.entity.RefStatementRelationType;
import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.entity.ReqComponentType;
import org.kuali.student.brms.statement.entity.Statement;
import org.kuali.student.brms.statement.entity.StatementType;
import org.kuali.student.brms.statement.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.brms.statement.service.StatementService;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.brms.statement.service.StatementService", serviceName = "StatementService", portName = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@Transactional(rollbackFor={Throwable.class})
public class StatementServiceImpl implements StatementService {

    private StatementDao statementDao;
	private NaturalLanguageTranslator naturalLanguageTranslator;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    
	public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    public StatementDao getStatementDao() {
        return statementDao;
    }

    public NaturalLanguageTranslator getNaturalLanguageTranslator() {
        return naturalLanguageTranslator;
    }

    public void setStatementDao(StatementDao statementDao) {
		this.statementDao = statementDao;
	}
	
	public void setNaturalLanguageTranslator(NaturalLanguageTranslator translator) {
		this.naturalLanguageTranslator = translator;
	}

	public NlUsageTypeInfo getNlUsageType(String nlUsageTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");

		NlUsageType entity = this.statementDao.fetch(NlUsageType.class, nlUsageTypeKey);
		NlUsageTypeInfo info = StatementAssembler.toNlUsageTypeInfo(entity);
		return info;
	}

	public List<NlUsageTypeInfo> getNlUsageTypes()
			throws OperationFailedException {

		List<NlUsageType> entities = this.statementDao.find(NlUsageType.class);
		List<NlUsageTypeInfo> infos = StatementAssembler.toNlUsageTypeInfos(entities);
		return infos;
	}

	public List<String> getRefObjectTypes() throws OperationFailedException {
		List<ObjectType> objectTypes = this.statementDao.find(ObjectType.class);
		List<String> ids = new ArrayList<String>();
		for(ObjectType objectType : objectTypes) {
			ids.add(objectType.getId());
		}
		return ids;
	}

	public List<String> getRefObjectSubTypes(String objectTypeKey)
			throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		
		checkForNullOrEmptyParameter(objectTypeKey, "objectTypeKey");
		checkForEmptyParameter(objectTypeKey, "objectTypeKey");

		ObjectType objectType = this.statementDao.fetch(ObjectType.class, objectTypeKey);
		List<String> ids = StatementAssembler.toRefObjectSubTypeIds(objectType);
		return ids;
	}

	public RefStatementRelationInfo getRefStatementRelation(String refStatementRelationId) 
			throws DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForEmptyParameter(refStatementRelationId, "refStatementRelationId");

    	RefStatementRelation entity = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationId);
    	RefStatementRelationInfo dto = StatementAssembler.toRefStatementRelationInfo(entity);
		return dto;
	}

	public List<RefStatementRelationInfo> getRefStatementRelationsByRef(String refObjectTypeKey, String refObjectId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(String statementId) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>Translates and retrieves a requirement component for a specific 
	 * usuage type (context) into natural language.</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * @param reqComponentId Requirement component to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException ReqComponent not found
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing reqComponentId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(reqComponentId, "reqComponentId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		// test usage type key exists
		getNlUsageType(nlUsageTypeKey);
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
//			ReqComponentInfo reqComponentInfo = this.statementDao.getReqComponent(reqComponentId);
//			CustomReqComponentInfo customReq = this.dtoAdapter.toCustomReqComponentInfo(reqComponentInfo);
			ReqComponent reqComponent = this.statementDao.fetch(ReqComponent.class, reqComponentId);
			String nl = this.naturalLanguageTranslator.translateReqComponent(reqComponent, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	/**
	 * <p>Translates and retrieves a statement directly attached to a CLU 
	 * for a specific usuage type (context) into natural language. 
	 * 
	 * If <code>cluId</code> is null or empty then statement header is not
	 * generated</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuStatementInfo</code> can either have a list of
	 * <code>LuStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * @param luStatementId Statement to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException Statement not found or Clu anchor not found in statement
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing luStatementId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public String getNaturalLanguageForStatement(String statementId, String nlUsageTypeKey, String language)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(statementId, "statementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
//			LuStatementInfo luStatementInfo = this.luService.getLuStatement(luStatementId);
//			checkCluExistsInLuStatementInfo(cluId, luStatementInfo);
//			CustomLuStatementInfo customInfo = dtoAdapter.toCustomLuStatementInfo(luStatementInfo);
			Statement statement = this.statementDao.fetch(Statement.class, statementId);
			String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

    public String getNaturalLanguageForRefStatementRelation(String refStatementRelationId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			RefStatementRelation refStatementRelation = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationId);
			Statement statement = refStatementRelation.getStatement();
			String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	@Override
	public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language)
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(reqComponentInfo, "reqComponentInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			// test usage type key exists
			getNlUsageType(nlUsageTypeKey);
			
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			
			ReqComponent req = StatementAssembler.toReqComponentRelation(false, reqComponentInfo, this.statementDao);
			
			String nl = this.naturalLanguageTranslator.translateReqComponent(req, nlUsageTypeKey);
			return nl;
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Requirement component translation failed: " + e.getMessage());
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Requirement component translation failed: " + e.getMessage());
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	@Override
	public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) 
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
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

	/**
	 * Check for missing or empty parameter and 
	 * throw localized exception if missing or empty
	 *
	 * @param param
	 * @param parameter name
	 * @throws MissingParameterException
	 */
	private void checkForNullOrEmptyParameter(String param, String paramName)
			throws MissingParameterException, InvalidParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		} else if (param.trim().isEmpty()) {
			throw new InvalidParameterException(paramName + " can not be empty");
		}
	}

	/**
	 * Check for empty parameter and throw localized exception if empty
	 *
	 * @param param
	 * @param parameter name
	 * @throws MissingParameterException
	 */
	private void checkForEmptyParameter(String param, String paramName)
			throws MissingParameterException, InvalidParameterException {
		if (param != null && param.trim().isEmpty()) {
			throw new InvalidParameterException(paramName + " can not be empty");
		}
	}

    @Override
    public ReqComponentInfo createReqComponent(String reqComponentType, ReqComponentInfo reqComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(reqComponentType, "reqComponentType");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        ReqComponent reqComp = null;

        try {
            reqComp = StatementAssembler.toReqComponentRelation(false, reqComponentInfo, statementDao);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Version Mismatch.", e);
        }

        reqComp = statementDao.create(reqComp);

        return StatementAssembler.toReqComponentInfo(reqComp);
    }

    @Override
    public StatementInfo createStatement(String statementType, StatementInfo statementInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(statementType, "statementType");
        checkForMissingParameter(statementInfo, "statementInfo");

        Statement statement = null;

        try {
            statement = StatementAssembler.toStatementRelation(false, statementInfo, statementDao);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Version Mismatch.", e);
        }

        statementDao.create(statement);

        StatementInfo info = StatementAssembler.toStatementInfo(statement);

        return info;
    }

    @Override
    public StatusInfo deleteReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(reqComponentId, "reqComponentId");

        ReqComponent reqComp = statementDao.fetch(ReqComponent.class, reqComponentId);

        if(reqComp==null){
            throw new DoesNotExistException("ReqComponent does not exist for id: "+reqComponentId);
        }

        statementDao.delete(reqComp);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Requirement component successfully deleted");
        return statusInfo;
    }

    @Override
    public StatusInfo deleteStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(statementId, "statementId");

        Statement stmt = statementDao.fetch(Statement.class, statementId);

        if(stmt==null){
            throw new DoesNotExistException("Statement does not exist for id: "+statementId);
        }

        statementDao.delete(stmt);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Statement successfully deleted");
        return statusInfo;
    }

    @Override
    public ReqComponentInfo getReqComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return StatementAssembler.toReqComponentInfo(statementDao.fetch(ReqComponent.class, reqComponentId));
    }

    @Override
    public List<ReqComponentInfo> getReqComponentsByType(String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(reqComponentTypeKey, "reqComponentTypeKey");

        List<ReqComponent> reqComponents = statementDao.getReqComponentsByType(reqComponentTypeKey);
        return StatementAssembler.toReqComponentInfos(reqComponents);
    }

    @Override
    public StatementInfo getStatement(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StatementInfo statementInfo = null;
        checkForMissingParameter(statementId, "statementId");
        statementInfo = StatementAssembler.toStatementInfo(statementDao.fetch(Statement.class, statementId));
        return statementInfo;
    }

    @Override
    public List<StatementInfo> getStatementsByType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(statementTypeKey, "statementTypeKey");

        List<Statement> statements = statementDao.getStatementsForStatementType(statementTypeKey);
        return StatementAssembler.toStatementInfos(statements);
    }

    @Override
    public List<StatementInfo> getStatementsUsingComponent(String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatementInfo updateStatement(String statementId, StatementInfo statementInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        //Check Missing params
        checkForMissingParameter(statementId, "statementId");
        checkForMissingParameter(statementInfo, "statementInfo");

        //Set all the values on statementInfo
        statementInfo.setId(statementId);

        Statement stmt = null;

        //Update persistence entity from the statementInfo
        stmt = StatementAssembler.toStatementRelation(true, statementInfo, statementDao);

        //Update the statement
        Statement updatedStmt = statementDao.update(stmt);

        //Copy back to an statementInfo and return
        StatementInfo updStatementInfo = StatementAssembler.toStatementInfo(updatedStmt);
        return updStatementInfo;
    }

    @Override
    public List<ValidationResultContainer> validateReqComponent(String validationType, ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        return createValidator().validateTypeStateObject(reqComponentInfo, getObjectStructure("reqComponentInfo"));
    }

    @Override
    public List<ValidationResultContainer> validateStatement(String validationType, StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(statementInfo, "statementInfo");

        return createValidator().validateTypeStateObject(statementInfo, getObjectStructure("statementInfo"));
    }
    
    private Validator createValidator() {
//      Validator validator = new Validator();
//      validator.setDateParser(new ServerDateParser());
////    validator.addMessages(null); //TODO this needs to be loaded somehow
//      return validator;
        return null;
    }

    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, statementDao);
    }

    @Override
    public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        checkForMissingParameter(queryParamValues, "queryParamValues");

        return searchManager.searchForResults(searchTypeKey, queryParamValues, statementDao);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    @Override
    public boolean validateObject(String objectTypeKey, String stateKey, String info) {
        return dictionaryServiceDelegate.validateObject(objectTypeKey, stateKey, info);
    }

    @Override
    public boolean validateStructureData(String objectTypeKey, String stateKey, String info) {
        return dictionaryServiceDelegate.validateStructureData(objectTypeKey, stateKey, info);
    }

    @Override
    public StatementTypeInfo getStatementType(String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return StatementAssembler.toStatementTypeInfo(statementDao.fetch(StatementType.class, statementTypeKey));
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypes()
    throws OperationFailedException {

        return StatementAssembler.toReqComponentTypeInfos(statementDao.find(ReqComponentType.class));
    }

    @Override
    public ReqComponentTypeInfo getReqComponentType(String reqComponentTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return StatementAssembler.toReqComponentTypeInfo(statementDao.fetch(ReqComponentType.class, reqComponentTypeKey));
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(
            String statementTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(statementTypeKey, "statementTypeKey");

        StatementType stmtType = statementDao.fetch(StatementType.class, statementTypeKey);
        if(null == stmtType) {
            throw new DoesNotExistException("Statement Type: " + statementTypeKey + " does not exist.");
        }

        return StatementAssembler.toReqComponentTypeInfos( stmtType.getAllowedReqComponentTypes() );
    }

    @Override
    public ReqComponentInfo updateReqComponent(String reqComponentId, ReqComponentInfo reqComponentInfo)
    	throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        //Check Missing params
        checkForMissingParameter(reqComponentId, "reqComponentId");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        //Set all the values on reqComponentInfo
        reqComponentInfo.setId(reqComponentId);

        ReqComponent reqComp = null;

        //Update persistence entity from the reqComponentInfo
        reqComp = StatementAssembler.toReqComponentRelation(true, reqComponentInfo, statementDao);

        //Update the reqComponen
        ReqComponent updatedReqComp = statementDao.update(reqComp);

        //Copy back to an reqComponentInfo and return
        ReqComponentInfo updReqCompInfo = StatementAssembler.toReqComponentInfo(updatedReqComp);
        return updReqCompInfo;
    }
    
	public RefStatementRelationInfo createRefStatementRelation(RefStatementRelationInfo refStatementRelationInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(refStatementRelationInfo, "refStatementRelationInfo");

		Statement statement = this.statementDao.fetch(Statement.class, refStatementRelationInfo.getStatementId());
		RefStatementRelationType type = this.statementDao.fetch(RefStatementRelationType.class, refStatementRelationInfo.getType());

		RefStatementRelation entity = new RefStatementRelation();

		BeanUtils.copyProperties(refStatementRelationInfo, entity, new String[] {
				"statementId", "attributes", "metaInfo", "type", "id"});

		entity.setRefStatementRelationType(type);
		entity.setStatement(statement);

		List<RefStatementRelationAttribute> attributes = StatementAssembler.toGenericAttributes(RefStatementRelationAttribute.class, refStatementRelationInfo.getAttributes(), entity, this.statementDao);
		entity.setAttributes(attributes);

		RefStatementRelation newEntity = this.statementDao.create(entity);
		
		RefStatementRelationInfo newDto = StatementAssembler.toRefStatementRelationInfo(newEntity);
		
		return newDto;
	}

	@Override
	public RefStatementRelationInfo updateRefStatementRelation(String refStatementRelationId, RefStatementRelationInfo refStatementRelationInfo)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForMissingParameter(refStatementRelationInfo, "refStatementRelationInfo");

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteRefStatementRelation(String refStatementRelationId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		this.statementDao.delete(RefStatementRelation.class, refStatementRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Reference statement relation successfully deleted"); 
        return statusInfo;
	}

    @Override
    public StatementTreeViewInfo getStatementTreeView(String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StatementTreeViewInfo statementTreeViewInfo = null;
        StatementInfo statementInfo = getStatement(statementId);
        if (statementInfo == null) return null;
        statementTreeViewInfo = new StatementTreeViewInfo();
        getStatementTreeViewHelper(statementInfo, statementTreeViewInfo);
        return statementTreeViewInfo;
    }
    
    
    /**
     * Goes through the list of reqComponentIds in statementInfo and retrieves the reqComponentInfos being referenced
     * @param statementInfo
     * @return list of reqComponentInfo referenced by the list of reqComponentIds in statementInfo
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    private List<ReqComponentInfo> getReqComponentInfos(final StatementInfo statementInfo) 
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
    OperationFailedException {
        List<ReqComponentInfo> reqComponentInfos = null;
        if (statementInfo == null) return null;
        if (statementInfo.getReqComponentIds() != null) {
            for (String reqComponentId : statementInfo.getReqComponentIds()) {
                ReqComponentInfo reqCompInfo = getReqComponent(reqComponentId);
                reqComponentInfos = (reqComponentInfos == null)? new ArrayList<ReqComponentInfo>(5) : 
                    reqComponentInfos;
                reqComponentInfos.add(reqCompInfo);
            }
        }
        return reqComponentInfos;
    }
    
    /**
     * Goes through the list of statementIds in statementInfo and retrieves all 
     * information regarding to the current statementInfo and all the 
     * sub-statements referenced by statementIds.  Data will be populated into
     * statementTreeViewInfo
     * @param statementInfo
     * @return void
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    private void getStatementTreeViewHelper(
            final StatementInfo statementInfo, final StatementTreeViewInfo statementTreeViewInfo) 
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (statementInfo == null) return;
        
        StatementAssembler.copyValues(statementTreeViewInfo, statementInfo);
        statementTreeViewInfo.setReqComponents(getReqComponentInfos(statementInfo));
        // get statements recursively and convert them into statementTreeViewInfo
        if (statementInfo.getStatementIds() != null) {
            for (String statementId : statementInfo.getStatementIds()) {
                StatementInfo subStatement = getStatement(statementId);
                List<StatementTreeViewInfo> statements = 
                    (statementTreeViewInfo.getStatements() == null)? 
                            new ArrayList<StatementTreeViewInfo>(5) : statementTreeViewInfo.getStatements();
                StatementTreeViewInfo subStatementTreeViewInfo = new StatementTreeViewInfo();
                // recursive call to get subStatementTreeViewInfo
                getStatementTreeViewHelper(subStatement, subStatementTreeViewInfo);
                statements.add(subStatementTreeViewInfo);
                statementTreeViewInfo.setStatements(statements);
            }
        }
    }

    @Override
    public StatementTreeViewInfo updateStatementTreeView(final String statementId, final StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        StatementTreeViewInfo origTree = getStatementTreeView(statementId);
        
        if (origTree == null) {
            throw new DoesNotExistException("Statement " + statementId + " does not exist!");
        }
        // insert statements and reqComponents if they do not already exists in database
        updateSTVHelperCreateStatements(statementTreeViewInfo);
        // check the two lists of relationships for ones that need to be deleted/created
        List<String> toBeDeleted = notIn(origTree, statementTreeViewInfo);
        for (String delStatementId : toBeDeleted) {
            deleteStatement(delStatementId);
        }
        updateStatementTreeViewHelper(statementTreeViewInfo);
        StatementTreeViewInfo test = getStatementTreeView(statementId);
        
        return test;
    }
    
    /**
     * 
     * @return a list of relationships in the first list but not in the second
     */
    private List<String> notIn(
            StatementTreeViewInfo oldTree,
            StatementTreeViewInfo newTree) {
        List<String> results = new ArrayList<String>(17);
        List<String> oldStatementIds = new ArrayList<String>(17);
        List<String> newStatementIds = new ArrayList<String>(17);
        getStatementIds(oldTree, oldStatementIds);
        getStatementIds(newTree, newStatementIds);
        if (oldStatementIds != null) {
            for (String oldStatementId : oldStatementIds) {
                boolean inNewStatementIds = false;
                if (newStatementIds != null) {
                    for (String newStatementId : newStatementIds) {
                        if (oldStatementId.equals(newStatementId)) {
                            inNewStatementIds = true;
                        }
                    }
                }
                if (!inNewStatementIds) {
                    results.add(oldStatementId);
                }
            }
        }
        return results;
    }
    
    private void getStatementIds(StatementTreeViewInfo statementTreeViewInfo, List<String> statementIds) {
        if (statementTreeViewInfo.getStatements() != null) {
            for (StatementTreeViewInfo subTree : statementTreeViewInfo.getStatements()) {
                getStatementIds(subTree, statementIds);
            }
        }
        statementIds.add(statementTreeViewInfo.getId());
    }
    
    private void updateStatementTreeViewHelper(StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (statementTreeViewInfo.getStatements() != null) {
            for (StatementTreeViewInfo subStatement : statementTreeViewInfo.getStatements()) {
                subStatement.setParentId(statementTreeViewInfo.getId());
                updateStatementTreeViewHelper(subStatement);
            }
        }
        if (statementTreeViewInfo.getReqComponents() != null) {
            List<ReqComponentInfo> updatedReqComponentInfos = new ArrayList<ReqComponentInfo>(7);
            for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
                ReqComponentInfo updatedReqComponentInfo = updateReqComponent(reqComponentInfo.getId(), reqComponentInfo);
                updatedReqComponentInfos.add(updatedReqComponentInfo);
            }
            statementTreeViewInfo.setReqComponents(updatedReqComponentInfos);
        }
        StatementInfo updatedStatementInfo = updateStatement(statementTreeViewInfo.getId(), StatementAssembler.toStatementInfo(
                statementTreeViewInfo));
        StatementAssembler.copyValues(statementTreeViewInfo, updatedStatementInfo);
    }
    
    private void updateSTVHelperCreateStatements(StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        String statementId = null;
        StatementInfo origStatementInfo = null;
        StatementInfo newStatementInfo = null;
        if (statementTreeViewInfo.getStatements() != null) {
            for (StatementTreeViewInfo subTreeInfo : statementTreeViewInfo.getStatements()) {
                subTreeInfo.setParentId(statementTreeViewInfo.getId());
                updateSTVHelperCreateStatements(subTreeInfo);
            }
        }
        if (statementTreeViewInfo.getReqComponents() != null) {
            List<ReqComponentInfo> rcsAfterInserts = new ArrayList<ReqComponentInfo>(7);
            for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
                String reqComponentId = reqComponentInfo.getId();
                ReqComponentInfo origReqComponentInfo = null;
                ReqComponentInfo rcAfterInsert = null;
                // determine the original reqComponentInfo
                if (reqComponentId != null) {
                    try {
                        origReqComponentInfo = getReqComponent(reqComponentId);
                    } catch (DoesNotExistException dnee) {
                        origReqComponentInfo = null;
                    }
                }
                if (origReqComponentInfo == null) {
                    // The reqComponentInfo is a new one so create it
                    try {
                        rcAfterInsert = createReqComponent(reqComponentInfo.getType(), reqComponentInfo);
                    } catch (AlreadyExistsException e) {
                        // shouldn't happen because of all the check that has been done up to this point
                        // if this exception is thrown it should be an error!
                        throw new OperationFailedException("Tried to create a reqComponent that already exists");
                    }
                } else {
                    rcAfterInsert = reqComponentInfo;
                }
                rcsAfterInserts.add(rcAfterInsert);
            }
            statementTreeViewInfo.setReqComponents(rcsAfterInserts);
        }
        // check if statementTreeViewInfo already exist if not create it.
        statementId = statementTreeViewInfo.getId();
        if (statementId != null) {
            try {
                origStatementInfo = getStatement(statementId);
            } catch(DoesNotExistException dnee) {
                origStatementInfo = null;
            }
        }
        if (origStatementInfo == null) {
            newStatementInfo = StatementAssembler.toStatementInfo(statementTreeViewInfo);
            try {
                newStatementInfo = createStatement(newStatementInfo.getType(), newStatementInfo);
            } catch (AlreadyExistsException e) {
                // shouldn't happen because of all the check that has been done up to this point
                // if this exception is thrown it should be an error!
                throw new OperationFailedException("Tried to create a statement that already exists");
            }
            StatementAssembler.copyValues(statementTreeViewInfo, newStatementInfo);
        }
    }
}