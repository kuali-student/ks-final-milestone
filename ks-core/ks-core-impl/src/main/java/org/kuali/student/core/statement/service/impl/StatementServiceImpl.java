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

package org.kuali.student.core.statement.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.common.validator.old.Validator;
import org.kuali.student.core.dictionary.old.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.old.DictionaryService;
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
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchManager;
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.entity.NlUsageType;
import org.kuali.student.core.statement.entity.ObjectType;
import org.kuali.student.core.statement.entity.RefStatementRelation;
import org.kuali.student.core.statement.entity.RefStatementRelationAttribute;
import org.kuali.student.core.statement.entity.RefStatementRelationType;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentType;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.entity.StatementType;
import org.kuali.student.core.statement.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.statement.service.StatementService", serviceName = "StatementService", portName = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@Transactional(rollbackFor={Throwable.class})
public class StatementServiceImpl implements StatementService {

	private StatementDao statementDao;
	private NaturalLanguageTranslator naturalLanguageTranslator;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private StatementAssembler statementAssembler;
    private Validator validator;

    public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setStatementAssembler(StatementAssembler statementAssembler) {
		this.statementAssembler = statementAssembler;
	}

	public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(final SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(final DictionaryService dictionaryServiceDelegate) {
    	this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    public StatementDao getStatementDao() {
        return statementDao;
    }

    public NaturalLanguageTranslator getNaturalLanguageTranslator() {
        return naturalLanguageTranslator;
    }

    public void setStatementDao(final StatementDao statementDao) {
		this.statementDao = statementDao;
	}

	public void setNaturalLanguageTranslator(final NaturalLanguageTranslator translator) {
		this.naturalLanguageTranslator = translator;
	}

	public NlUsageTypeInfo getNlUsageType(final String nlUsageTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");

		NlUsageType entity = this.statementDao.fetch(NlUsageType.class, nlUsageTypeKey);
		NlUsageTypeInfo info = statementAssembler.toNlUsageTypeInfo(entity);
		return info;
	}

	public List<NlUsageTypeInfo> getNlUsageTypes()
			throws OperationFailedException {

		List<NlUsageType> entities = this.statementDao.find(NlUsageType.class);
		List<NlUsageTypeInfo> infos = statementAssembler.toNlUsageTypeInfos(entities);
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

	public List<String> getRefObjectSubTypes(final String objectTypeKey)
			throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForNullOrEmptyParameter(objectTypeKey, "objectTypeKey");
		checkForEmptyParameter(objectTypeKey, "objectTypeKey");

		ObjectType objectType = this.statementDao.fetch(ObjectType.class, objectTypeKey);
		List<String> ids = statementAssembler.toRefObjectSubTypeIds(objectType);
		return ids;
	}

	public RefStatementRelationInfo getRefStatementRelation(final String refStatementRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForEmptyParameter(refStatementRelationId, "refStatementRelationId");

    	RefStatementRelation entity = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationId);
    	RefStatementRelationInfo dto = statementAssembler.toRefStatementRelationInfo(entity);
		return dto;
	}

	public List<RefStatementRelationInfo> getRefStatementRelationsByRef(final String refObjectTypeKey, final String refObjectId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForNullOrEmptyParameter(refObjectTypeKey, "refObjectTypeKey");
        checkForEmptyParameter(refObjectId, "refObjectId");

        List<RefStatementRelation> references = this.statementDao.getRefStatementRelations(
                refObjectTypeKey, refObjectId);
        List<RefStatementRelationInfo> referenceInfos = null;
        if (references != null) {
            for (RefStatementRelation reference : references) {
                RefStatementRelationInfo dto = statementAssembler.toRefStatementRelationInfo(reference);
                referenceInfos = (referenceInfos == null)? new ArrayList<RefStatementRelationInfo>(7) : referenceInfos;
                referenceInfos.add(dto);
            }
        }
        return referenceInfos;
	}

	public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(final String statementId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(statementId, "statementId");

		Statement statement = this.statementDao.fetch(Statement.class, statementId);
		List<RefStatementRelation> entities = statement.getRefStatementRelations();
		List<RefStatementRelationInfo> dtoList = statementAssembler.toRefStatementRelationInfos(entities);
		return dtoList;
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
	public String getNaturalLanguageForReqComponent(final String reqComponentId, final String nlUsageTypeKey, final String language)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(reqComponentId, "reqComponentId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		// test usage type key exists
		getNlUsageType(nlUsageTypeKey);

		ReqComponent reqComponent = this.statementDao.fetch(ReqComponent.class, reqComponentId);
		String nl = this.naturalLanguageTranslator.translateReqComponent(reqComponent, nlUsageTypeKey, language);
		return nl;
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
	 * <p>An <code>StatementInfo</code> can either have a list of
	 * <code>StatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 *
	 * @param statementId Statement to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException Statement not found or Clu anchor not found in statement
     * @throws InvalidParameterException Invalid nlUsageTypeKey
     * @throws MissingParameterException Missing statementId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public String getNaturalLanguageForStatement(final String statementId, final String nlUsageTypeKey, final String language)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(statementId, "statementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		Statement statement = this.statementDao.fetch(Statement.class, statementId);
		String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey, language);
		return nl;
	}

    public String getNaturalLanguageForRefStatementRelation(final String refStatementRelationId, final String nlUsageTypeKey, final String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		RefStatementRelation refStatementRelation = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationId);
		Statement statement = refStatementRelation.getStatement();
		String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey, language);
		return nl;
	}

	@Override
	public String translateReqComponentToNL(final ReqComponentInfo reqComponentInfo, final String nlUsageTypeKey, final String language)
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(reqComponentInfo, "reqComponentInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		try {
			// test usage type key exists
			getNlUsageType(nlUsageTypeKey);

			ReqComponent req = statementAssembler.toReqComponentRelation(false, reqComponentInfo);

			String nl = this.naturalLanguageTranslator.translateReqComponent(req, nlUsageTypeKey, language);
			return nl;
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Requirement component translation failed: " + e.getMessage());
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Requirement component translation failed: " + e.getMessage());
		}
	}

	@Override
	public String translateStatementTreeViewToNL(final StatementTreeViewInfo statementTreeViewInfo, final String nlUsageTypeKey, final String language)
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		try {
			Statement statement = statementAssembler.toStatement(statementTreeViewInfo);

			String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey, language);
			return nl;
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Statement tree view translation failed: " + e.getMessage());
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Statement tree view translation failed: " + e.getMessage());
		}
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
    public ReqComponentInfo createReqComponent(final String reqComponentType, final ReqComponentInfo reqComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(reqComponentType, "reqComponentType");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        ReqComponent reqComp = null;

        try {
            reqComp = statementAssembler.toReqComponentRelation(false, reqComponentInfo);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Version Mismatch.", e);
        }

        reqComp = statementDao.create(reqComp);

        return statementAssembler.toReqComponentInfo(reqComp, null, null);
    }

    @Override
    public StatementInfo createStatement(final String statementType, final StatementInfo statementInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(statementType, "statementType");
        checkForMissingParameter(statementInfo, "statementInfo");

        Statement statement = null;

        try {
            statement = statementAssembler.toStatementRelation(false, statementInfo);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Version Mismatch.", e);
        }

        statementDao.create(statement);

        StatementInfo info = statementAssembler.toStatementInfo(statement);

        return info;
    }

    @Override
    public StatusInfo deleteReqComponent(final String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public StatusInfo deleteStatement(final String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public ReqComponentInfo getReqComponent(final String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return statementAssembler.toReqComponentInfo(statementDao.fetch(ReqComponent.class, reqComponentId), null, null);
    }

    @Override
    public List<ReqComponentInfo> getReqComponentsByType(final String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(reqComponentTypeKey, "reqComponentTypeKey");

        List<ReqComponent> reqComponents = statementDao.getReqComponentsByType(reqComponentTypeKey);
        return statementAssembler.toReqComponentInfos(reqComponents, null, null);
    }

    @Override
    public StatementInfo getStatement(final String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StatementInfo statementInfo = null;
        checkForMissingParameter(statementId, "statementId");
        statementInfo = statementAssembler.toStatementInfo(statementDao.fetch(Statement.class, statementId));
        return statementInfo;
    }

    @Override
    public List<StatementInfo> getStatementsByType(final String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(statementTypeKey, "statementTypeKey");

        List<Statement> statements = statementDao.getStatementsForStatementType(statementTypeKey);
        return statementAssembler.toStatementInfos(statements);
    }

    @Override
    public List<StatementInfo> getStatementsUsingReqComponent(final String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForNullOrEmptyParameter(reqComponentId, "reqComponentId");

        List<Statement> list = statementDao.getStatementsForReqComponent(reqComponentId);
        return statementAssembler.toStatementInfos(list);
    }

    /**
     * Gets child statements but does no downward recursion of child statements.
     *
     * @param statementId statement identifier
     * @return List of child statements using the specified statement
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException Invalid statementId
     * @throws MissingParameterException statementId not specified
     * @throws OperationFailedException Unable to complete request
     */
	public List<StatementInfo> getStatementsUsingStatement(final String statementId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForNullOrEmptyParameter(statementId, "statementId");

		Statement statement = statementDao.fetch(Statement.class, statementId);
		List<StatementInfo> list = statementAssembler.toStatementInfos(statement.getChildren());
		return list;
	}

    @Override
    public StatementInfo updateStatement(final String statementId, final StatementInfo statementInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        //Check Missing params
        checkForMissingParameter(statementId, "statementId");
        checkForMissingParameter(statementInfo, "statementInfo");

        //Set all the values on statementInfo
        statementInfo.setId(statementId);

        //Update persistence entity from the statementInfo
        Statement stmt = statementAssembler.toStatementRelation(true, statementInfo);

        //Update the statement
        Statement updatedStmt = statementDao.update(stmt);

        //Copy back to an statementInfo and return
        StatementInfo updStatementInfo = statementAssembler.toStatementInfo(updatedStmt);
        return updStatementInfo;
    }

    @Override
    public List<ValidationResultInfo> validateReqComponent(final String validationType, final ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        return validator.validateTypeStateObject(reqComponentInfo, getObjectStructure("org.kuali.student.core.statement.dto.ReqComponentInfo"));
    }

    @Override
    public List<ValidationResultInfo> validateStatement(final String validationType, final StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(statementInfo, "statementInfo");

        return validator.validateTypeStateObject(statementInfo, getObjectStructure("org.kuali.student.core.statement.dto.StatementInfo"));
    }

    @Override
    public ObjectStructure getObjectStructure(final String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(final String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(final String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

    @Override
    public SearchTypeInfo getSearchType(final String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(final String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(final String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
    }

    @Override
    public SearchResult search(final SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, statementDao);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    @Override
    public StatementTypeInfo getStatementType(final String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return statementAssembler.toStatementTypeInfo(statementDao.fetch(StatementType.class, statementTypeKey));
    }

    @Override
    public List<StatementTypeInfo> getStatementTypes() throws OperationFailedException {
        return statementAssembler.toStatementTypeInfos(statementDao.find(StatementType.class));
    }

    public List<String> getStatementTypesForStatementType(final String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	StatementTypeInfo type = statementAssembler.toStatementTypeInfo(statementDao.fetch(StatementType.class, statementTypeKey));
    	return type.getAllowedStatementTypes();
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypes() throws OperationFailedException {
        return statementAssembler.toReqComponentTypeInfos(statementDao.find(ReqComponentType.class));
    }

    @Override
    public ReqComponentTypeInfo getReqComponentType(final String reqComponentTypeKey)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return statementAssembler.toReqComponentTypeInfo(statementDao.fetch(ReqComponentType.class, reqComponentTypeKey));
    }

    @Override
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(final String statementTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(statementTypeKey, "statementTypeKey");

        StatementType stmtType = statementDao.fetch(StatementType.class, statementTypeKey);
        if(null == stmtType) {
            throw new DoesNotExistException("Statement Type: " + statementTypeKey + " does not exist.");
        }

        return statementAssembler.toReqComponentTypeInfos( stmtType.getAllowedReqComponentTypes() );
    }

    @Override
    public ReqComponentInfo updateReqComponent(final String reqComponentId, final ReqComponentInfo reqComponentInfo)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        //Check Missing params
        checkForMissingParameter(reqComponentId, "reqComponentId");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        //Set all the values on reqComponentInfo
        reqComponentInfo.setId(reqComponentId);

        ReqComponent reqComp = null;

        //Update persistence entity from the reqComponentInfo
        reqComp = statementAssembler.toReqComponentRelation(true, reqComponentInfo);

        //Update the reqComponen
        ReqComponent updatedReqComp = statementDao.update(reqComp);

        //Copy back to an reqComponentInfo and return
        ReqComponentInfo updReqCompInfo = statementAssembler.toReqComponentInfo(updatedReqComp, null, null);
        return updReqCompInfo;
    }

	public RefStatementRelationInfo createRefStatementRelation(final RefStatementRelationInfo refStatementRelationInfo)
			throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(refStatementRelationInfo, "refStatementRelationInfo");

		Statement statement = this.statementDao.fetch(Statement.class, refStatementRelationInfo.getStatementId());
		RefStatementRelationType type = this.statementDao.fetch(RefStatementRelationType.class, refStatementRelationInfo.getType());

		RefStatementRelation entity = new RefStatementRelation();

		BeanUtils.copyProperties(refStatementRelationInfo, entity, new String[] {
				"statementId", "attributes", "metaInfo", "type", "id"});

		entity.setRefStatementRelationType(type);
		entity.setStatement(statement);

		List<RefStatementRelationAttribute> attributes = statementAssembler.toGenericAttributes(RefStatementRelationAttribute.class, refStatementRelationInfo.getAttributes(), entity, this.statementDao);
		entity.setAttributes(attributes);

		RefStatementRelation newEntity = this.statementDao.create(entity);

		RefStatementRelationInfo newDto = statementAssembler.toRefStatementRelationInfo(newEntity);

		return newDto;
	}

	@Override
	public RefStatementRelationInfo updateRefStatementRelation(final String refStatementRelationId, final RefStatementRelationInfo refStatementRelationInfo)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForMissingParameter(refStatementRelationInfo, "refStatementRelationInfo");

		refStatementRelationInfo.setId(refStatementRelationId);
		RefStatementRelation refStatementRel = statementAssembler.toRefStatementRelation(true, refStatementRelationInfo);
		RefStatementRelation updatedRefStatementRel = statementDao.update(refStatementRel);

		RefStatementRelationInfo dto = statementAssembler.toRefStatementRelationInfo(updatedRefStatementRel);
		return dto;
	}

	@Override
	public StatusInfo deleteRefStatementRelation(final String refStatementRelationId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		this.statementDao.delete(RefStatementRelation.class, refStatementRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Reference statement relation successfully deleted");
        return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateRefStatementRelation(final String validationType, RefStatementRelationInfo refStatementRelationInfo)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return validator.validateTypeStateObject(refStatementRelationInfo, getObjectStructure("refStatementRelationInfo"));
	}

    @Override
    public StatementTreeViewInfo getStatementTreeView(final String statementId)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	checkForNullOrEmptyParameter("statementId", statementId);
    	
    	return getStatementTreeView(statementId, null, null);
    }
    
    @Override
    public StatementTreeViewInfo getStatementTreeViewForNlUsageType(final String statementId, final String nlUsageTypeKey, final String language) 
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	checkForNullOrEmptyParameter("statementId", statementId);
    	checkForNullOrEmptyParameter("nlUsageTypeKey", nlUsageTypeKey);
    	checkForNullOrEmptyParameter("language", language);

    	return getStatementTreeView(statementId, nlUsageTypeKey, language);
    }

    private StatementTreeViewInfo getStatementTreeView(final String statementId, final String nlUsageTypeKey, final String language)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StatementTreeViewInfo statementTreeViewInfo = null;
        StatementInfo statementInfo = getStatement(statementId);
        if (statementInfo == null) return null;
        statementTreeViewInfo = new StatementTreeViewInfo();
        getStatementTreeViewHelper(statementInfo, statementTreeViewInfo, nlUsageTypeKey, language);
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
    private List<ReqComponentInfo> getReqComponentInfos(final StatementInfo statementInfo, final String nlUsageTypeKey, final String language)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ReqComponentInfo> reqComponentInfos = new ArrayList<ReqComponentInfo>();
        if (statementInfo == null) return null;
        if (statementInfo.getReqComponentIds() != null) {
            for (String reqComponentId : statementInfo.getReqComponentIds()) {
                //ReqComponentInfo reqCompInfo = getReqComponent(reqComponentId);
            	ReqComponentInfo reqCompInfo = statementAssembler.toReqComponentInfo(statementDao.fetch(ReqComponent.class, reqComponentId), nlUsageTypeKey, language);
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
    private void getStatementTreeViewHelper(final StatementInfo statementInfo, final StatementTreeViewInfo statementTreeViewInfo, 
    		final String nlUsageTypeKey, final String language)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (statementInfo == null) return;

        statementAssembler.copyValues(statementTreeViewInfo, statementInfo);
        statementTreeViewInfo.setReqComponents(getReqComponentInfos(statementInfo, nlUsageTypeKey, language));
        // get statements recursively and convert them into statementTreeViewInfo
        if (statementInfo.getStatementIds() != null) {
            for (String statementId : statementInfo.getStatementIds()) {
                StatementInfo subStatement = getStatement(statementId);
                
                List<StatementTreeViewInfo> statements =
                    (statementTreeViewInfo.getStatements() == null) ? new ArrayList<StatementTreeViewInfo>() : statementTreeViewInfo.getStatements();
                StatementTreeViewInfo subStatementTreeViewInfo = new StatementTreeViewInfo();
    	        String nl = translateStatement(statementId, nlUsageTypeKey, language);
                subStatementTreeViewInfo.setNaturalLanguageTranslation(nl);

                // recursive call to get subStatementTreeViewInfo
                getStatementTreeViewHelper(subStatement, subStatementTreeViewInfo, nlUsageTypeKey, language);
                statements.add(subStatementTreeViewInfo);
                statementTreeViewInfo.setStatements(statements);
            }
        }
        String nl = translateStatement(statementTreeViewInfo.getId(), nlUsageTypeKey, language);
        statementTreeViewInfo.setNaturalLanguageTranslation(nl);
    }
    
    private String translateStatement(String statementId, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException {
        Statement stmt = this.statementDao.fetch(Statement.class, statementId);
        if(nlUsageTypeKey != null && language != null) {
	        String nl = this.naturalLanguageTranslator.translateStatement(stmt, nlUsageTypeKey);
	        return nl;
        }
        return null;
    }

    @Override
    public StatementTreeViewInfo updateStatementTreeView(final String statementId, final StatementTreeViewInfo statementTreeViewInfo)
    	throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        StatementTreeViewInfo origTree = null;

        if (statementId != null) {
            try {
                origTree = getStatementTreeView(statementId);
            } catch (DoesNotExistException dnee) {
                origTree = null;
            }
        }

        // insert statements and reqComponents if they do not already exists in database
        updateSTVHelperCreateStatements(statementTreeViewInfo);
        // check the two lists of relationships for ones that need to be deleted/created
        if (origTree != null) {
            List<String> toBeDeleted = notIn(origTree, statementTreeViewInfo);
            for (String delStatementId : toBeDeleted) {
                deleteStatement(delStatementId);
            }
        }
        updateStatementTreeViewHelper(statementTreeViewInfo);
        StatementTreeViewInfo test = getStatementTreeView(statementTreeViewInfo.getId());

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
        StatementInfo updatedStatementInfo = updateStatement(statementTreeViewInfo.getId(), statementAssembler.toStatementInfo(
                statementTreeViewInfo));
        statementAssembler.copyValues(statementTreeViewInfo, updatedStatementInfo);
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
                    // the id here even if it is not null it is the temporary ids assigned by client
                    // so resets the id to null to allow a new id to be generated.
                    reqComponentInfo.setId(null);
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
            // the id here even if it is not null it is the temporary ids assigned by client
            // so resets the id to null to allow a new id to be generated.
            statementTreeViewInfo.setId(null);
            newStatementInfo = statementAssembler.toStatementInfo(statementTreeViewInfo);
            try {
                newStatementInfo = createStatement(newStatementInfo.getType(), newStatementInfo);
            } catch (AlreadyExistsException e) {
                // shouldn't happen because of all the check that has been done up to this point
                // if this exception is thrown it should be an error!
                throw new OperationFailedException("Tried to create a statement that already exists");
            }
            statementAssembler.copyValues(statementTreeViewInfo, newStatementInfo);
        }
    }

	@Override
	public RefStatementRelationTypeInfo getRefStatementRelationType(final String refStatementRelationTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForNullOrEmptyParameter(refStatementRelationTypeKey, "refStatementRelationTypeKey");

		RefStatementRelationType type = this.statementDao.fetch(RefStatementRelationType.class, refStatementRelationTypeKey);

		return statementAssembler.toRefStatementRelationTypeInfo(type);
	}

	@Override
	public List<RefStatementRelationTypeInfo> getRefStatementRelationTypes()
			throws OperationFailedException {
		List<RefStatementRelationType> entities = this.statementDao.find(RefStatementRelationType.class);
		return statementAssembler.toRefStatementRelationTypeInfos(entities);
	}

	@Override
	public List<String> getRefStatementRelationTypesForRefObjectSubType(final String refSubTypeKey)
		throws DoesNotExistException,InvalidParameterException, MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
	public List<String> getStatementTypesForRefStatementRelationType(final String refStatementRelationTypeKey)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}
}