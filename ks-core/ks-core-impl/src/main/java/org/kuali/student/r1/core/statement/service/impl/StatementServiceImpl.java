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

package org.kuali.student.r1.core.statement.service.impl;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r1.core.statement.dao.StatementDao;
import org.kuali.student.r1.core.statement.dto.*;
import org.kuali.student.r1.core.statement.entity.*;
import org.kuali.student.r1.core.statement.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.r1.core.statement.naturallanguage.translators.ReqComponentTranslator;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.*;

@Deprecated
@WebService(endpointInterface = "org.kuali.student.r1.core.statement.service.StatementService", serviceName = "StatementService", portName = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
public class StatementServiceImpl implements StatementService {

	private final static Logger logger = LoggerFactory.getLogger(ReqComponentTranslator.class);

	private static final String SEARCH_KEY_DEPENDENCY_ANALYSIS = "stmt.search.dependencyAnalysis";

	private StatementDao statementDao;
	private NaturalLanguageTranslator naturalLanguageTranslator;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private StatementAssembler statementAssembler;
    private ValidatorFactory validatorFactory;
    // private StatementTreeViewAssembler statementTreeViewAssembler;

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

    public void setStatementDao(final StatementDao statementDao) {
		this.statementDao = statementDao;
	}

    public NaturalLanguageTranslator getNaturalLanguageTranslator() {
        return naturalLanguageTranslator;
    }

	public void setNaturalLanguageTranslator(final NaturalLanguageTranslator translator) {
		this.naturalLanguageTranslator = translator;
	}

    @Transactional(readOnly=true)
	public NlUsageTypeInfo getNlUsageType(final String nlUsageTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");

		NlUsageType entity = this.statementDao.fetch(NlUsageType.class, nlUsageTypeKey);
		NlUsageTypeInfo info = StatementAssembler.toNlUsageTypeInfo(entity);
		return info;
	}

    @Transactional(readOnly=true)
	public List<NlUsageTypeInfo> getNlUsageTypes()
			throws OperationFailedException {

		List<NlUsageType> entities = this.statementDao.find(NlUsageType.class);
		List<NlUsageTypeInfo> infos = StatementAssembler.toNlUsageTypeInfos(entities);
		return infos;
	}

    @Transactional(readOnly=true)
	public List<String> getRefObjectTypes() throws OperationFailedException {
		List<ObjectType> objectTypes = this.statementDao.find(ObjectType.class);
		List<String> ids = new ArrayList<String>();
		for(ObjectType objectType : objectTypes) {
			ids.add(objectType.getId());
		}
		return ids;
	}

    @Transactional(readOnly=true)
	public List<String> getRefObjectSubTypes(final String objectTypeKey)
			throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForNullOrEmptyParameter(objectTypeKey, "objectTypeKey");
		checkForEmptyParameter(objectTypeKey, "objectTypeKey");

		ObjectType objectType = this.statementDao.fetch(ObjectType.class, objectTypeKey);
		List<String> ids = StatementAssembler.toRefObjectSubTypeIds(objectType);
		return ids;
	}

    @Transactional(readOnly=true)
	public RefStatementRelationInfo getRefStatementRelation(final String refStatementRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForEmptyParameter(refStatementRelationId, "refStatementRelationId");

    	RefStatementRelation entity = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationId);
    	RefStatementRelationInfo dto = StatementAssembler.toRefStatementRelationInfo(entity);
		return dto;
	}

    @Transactional(readOnly=true)
	public List<RefStatementRelationInfo> getRefStatementRelationsByRef(final String refObjectTypeKey, final String refObjectId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForNullOrEmptyParameter(refObjectTypeKey, "refObjectTypeKey");
        checkForEmptyParameter(refObjectId, "refObjectId");

        List<RefStatementRelation> references = this.statementDao.getRefStatementRelations(
                refObjectTypeKey, refObjectId);
        List<RefStatementRelationInfo> referenceInfos = null;
        if (references != null) {
            for (RefStatementRelation reference : references) {
                RefStatementRelationInfo dto = StatementAssembler.toRefStatementRelationInfo(reference);
                referenceInfos = (referenceInfos == null)? new ArrayList<RefStatementRelationInfo>(7) : referenceInfos;
                referenceInfos.add(dto);
            }
        }
        return referenceInfos;
	}

    @Transactional(readOnly=true)
	public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(final String statementId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(statementId, "statementId");

		Statement statement = this.statementDao.fetch(Statement.class, statementId);
		List<RefStatementRelation> entities = statement.getRefStatementRelations();
		List<RefStatementRelationInfo> dtoList = StatementAssembler.toRefStatementRelationInfos(entities);
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
    @Transactional(readOnly=true)
	public String getNaturalLanguageForReqComponent(final String reqComponentId, final String nlUsageTypeKey, final String language)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(reqComponentId, "reqComponentId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		// test usage type key exists
		getNlUsageType(nlUsageTypeKey);

		ReqComponent reqComponent = this.statementDao.fetch(ReqComponent.class, reqComponentId);
		String nl = this.naturalLanguageTranslator.translateReqComponent(reqComponent, nlUsageTypeKey, language);

		if(logger.isInfoEnabled()) {
			logger.info("reqComponentId="+reqComponentId);
			logger.info("nlUsageTypeKey="+nlUsageTypeKey);
			logger.info("language="+language);
			logger.info("ReqComponent translation="+nl);
		}

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
    @Transactional(readOnly=true)
	public String getNaturalLanguageForStatement(final String statementId, final String nlUsageTypeKey, final String language)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(statementId, "statementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		Statement statement = this.statementDao.fetch(Statement.class, statementId);
		String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey, language);

		if(logger.isInfoEnabled()) {
			logger.info("statementId="+statementId);
			logger.info("nlUsageTypeKey="+nlUsageTypeKey);
			logger.info("language="+language);
			logger.info("Statement translation="+nl);
		}

		return nl;
	}

    @Transactional(readOnly=true)
    public String getNaturalLanguageForRefStatementRelation(final String refStatementRelationId, final String nlUsageTypeKey, final String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		RefStatementRelation refStatementRelation = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationId);
		Statement statement = refStatementRelation.getStatement();
		String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey, language);

		if(logger.isInfoEnabled()) {
			logger.info("refStatementRelationId="+refStatementRelationId);
			logger.info("nlUsageTypeKey="+nlUsageTypeKey);
			logger.info("language="+language);
			logger.info("Statement translation="+nl);
		}

		return nl;
	}

	@Override
    @Transactional(readOnly=true)
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

			if(logger.isInfoEnabled()) {
    			logger.info("ReqComponent translation="+nl);
    		}

			return nl;
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Requirement component translation failed: " + e.getMessage());
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Requirement component translation failed: " + e.getMessage());
		}
	}

	@Override
    @Transactional(readOnly=true)
	public String translateStatementTreeViewToNL(final StatementTreeViewInfo statementTreeViewInfo, final String nlUsageTypeKey, final String language)
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		try {
			Statement statement = statementAssembler.toStatement(statementTreeViewInfo);

			String nl = this.naturalLanguageTranslator.translateStatement(statement, nlUsageTypeKey, language);

			if(logger.isInfoEnabled()) {
    			logger.info("StatementTreeView translation="+nl);
    		}

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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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

        StatementInfo info = StatementAssembler.toStatementInfo(statement);

        return info;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatementTreeViewInfo createStatementTreeView(final StatementTreeViewInfo statementTreeViewInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, CircularReferenceException {
    	try {
            // insert statements and reqComponents if they do not already exists in database
            updateSTVHelperCreateStatements(statementTreeViewInfo);

            updateStatementTreeViewHelper(statementTreeViewInfo);
            StatementTreeViewInfo test = getStatementTreeView(statementTreeViewInfo.getId());

            return test;
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Create failed.", e);
		}
    }

    
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteStatement(final String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(statementId, "statementId");

        Statement stmt = statementDao.fetch(Statement.class, statementId);
        if(stmt==null){
            throw new DoesNotExistException("Statement does not exist for id: "+statementId);
        }


		try {
			Statement parent = statementDao.getParentStatement(statementId);
        	List<Statement> children = parent.getChildren();
        	for (int i = 0; i < children.size(); i++) {
        		if (children.get(i).getId().equals(statementId)) {
        			children.remove(i);
        			break;
        		}
        	}
        	statementDao.update(parent);
		} catch (DoesNotExistException e) {
			// Ignore in this case
		}

        statementDao.delete(stmt);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Statement successfully deleted");
        return statusInfo;
    }
    
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteStatementTreeView(final String statementId) throws DoesNotExistException{
        Statement stmt = statementDao.fetch(Statement.class, statementId);
        
        try{
        	Statement parent = statementDao.getParentStatement(statementId);
        	
        	//remove the child from the parent
            if(parent.getChildren()!=null){
		        for(Iterator<Statement> iter = parent.getChildren().iterator();iter.hasNext();){
		        	Statement childStmt = iter.next();
		        	if(stmt.getId().equals(childStmt.getId())){
		        		iter.remove();
		        		break;
		        	}
		        }
	        }
	        statementDao.update(parent);
    	}catch(DoesNotExistException e){
    		//Ignore in this case
    	}
        
        //delete the tree hierarchy;
        deleteRecursively(stmt);
        
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        statusInfo.setMessage("Statement Tree successfully deleted");
        return statusInfo;
    }

	private void deleteRecursively(Statement stmt) {
    	if(stmt.getChildren()!=null){
    		List<Statement> childStmts = new ArrayList<Statement>(stmt.getChildren());
	    	stmt.getChildren().clear();
	    	stmt = statementDao.update(stmt);
	    	for(Statement childStmt:childStmts){
	    		deleteRecursively(childStmt);
	    	}
    	}
    	statementDao.delete(stmt);
	}

	@Override
    @Transactional(readOnly=true)
    public ReqComponentInfo getReqComponent(final String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return statementAssembler.toReqComponentInfo(statementDao.fetch(ReqComponent.class, reqComponentId), null, null);
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReqComponentInfo> getReqComponentsByType(final String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(reqComponentTypeKey, "reqComponentTypeKey");

        List<ReqComponent> reqComponents = statementDao.getReqComponentsByType(reqComponentTypeKey);
        return statementAssembler.toReqComponentInfos(reqComponents, null, null);
    }

    @Override
    @Transactional(readOnly=true)
    public StatementInfo getStatement(final String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StatementInfo statementInfo = null;
        checkForMissingParameter(statementId, "statementId");
        statementInfo = StatementAssembler.toStatementInfo(statementDao.fetch(Statement.class, statementId));
        return statementInfo;
    }

    @Override
    @Transactional(readOnly=true)
    public List<StatementInfo> getStatementsByType(final String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(statementTypeKey, "statementTypeKey");

        List<Statement> statements = statementDao.getStatementsForStatementType(statementTypeKey);
        return StatementAssembler.toStatementInfos(statements);
    }

    @Override
    @Transactional(readOnly=true)
    public List<StatementInfo> getStatementsUsingReqComponent(final String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForNullOrEmptyParameter(reqComponentId, "reqComponentId");

        List<Statement> list = statementDao.getStatementsForReqComponent(reqComponentId);
        return StatementAssembler.toStatementInfos(list);
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
    @Transactional(readOnly=true)
	public List<StatementInfo> getStatementsUsingStatement(final String statementId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForNullOrEmptyParameter(statementId, "statementId");

		Statement statement = statementDao.fetch(Statement.class, statementId);
		List<StatementInfo> list = StatementAssembler.toStatementInfos(statement.getChildren());
		return list;
	}

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
        StatementInfo updStatementInfo = StatementAssembler.toStatementInfo(updatedStmt);
        return updStatementInfo;
    }

    @Override
    public List<ValidationResultInfo> validateReqComponent(final String validationType, final ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(ReqComponentInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(reqComponentInfo, objStructure, null);

        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateStatement(final String validationType, final StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(statementInfo, "statementInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(StatementInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(statementInfo, objStructure, null);

        return validationResults;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException, InvalidParameterException {
        checkForMissingParameter(searchRequestInfo, "searchRequest");
        if(SEARCH_KEY_DEPENDENCY_ANALYSIS.equals(searchRequestInfo.getSearchKey())){
        	//Special case for dependency analysis.
        	//Parse out query params and execute custom search
        	List<String> cluVersionIndIds = new ArrayList<String>();
        	List<String> cluSetIds = new ArrayList<String>();
    		for(SearchParamInfo param:searchRequestInfo.getParams()){
    			if("stmt.queryParam.cluSetIds".equals(param.getKey())){
    				cluSetIds.addAll(param.getValues());
    				continue;
    			}else if("stmt.queryParam.cluVersionIndIds".equals(param.getKey())){
    				cluVersionIndIds.addAll(param.getValues());
    			}
    		}
    		if(cluVersionIndIds.isEmpty()){
    			cluVersionIndIds.add("");
    		}
    		if(cluSetIds.isEmpty()){
    			cluSetIds.add("");
    		}
			return doDependencyAnalysisSearch(cluVersionIndIds,cluSetIds);
        }
        
        return searchManager.search(searchRequestInfo, contextInfo);
    }

    private SearchResultInfo doDependencyAnalysisSearch(
			List<String> cluVersionIndIds, List<String> cluSetIds) {
    	//First look up all the statements that have requirement components that reference the 
    	//given cluIds and clusets
    	List<Object[]> results = statementDao.getStatementsWithDependencies(cluVersionIndIds,cluSetIds);
    	
    	//From the Object[], which contains a statement at index 0, and a result component id at index 1
    	//obtain a list of statements and a comma delimited list of requirement component ids for each 
    	//statement which contain the target clu/cluset
    	Map<String,String> statementToResultComponentIds = new HashMap<String,String>();
    	Map<String, Statement> statements = new HashMap<String,Statement>();
    	for(Object[] result:results){
    		Statement statement = (Statement) result[0];
    		statements.put(statement.getId(),statement);
    		String resultComponentIds = statementToResultComponentIds.get(statement.getId());
    		if(resultComponentIds == null){
    			resultComponentIds = (String)result[1];
    		}else{
    			resultComponentIds+="," + (String)result[1];
    		}
    		statementToResultComponentIds.put(statement.getId(), resultComponentIds);
    	}
    	
    	
    	//HashMap of root statements used to store non duplicate root statements 
    	Map<String,Statement> rootStatements = new HashMap<String,Statement>();
    	
    	Map<String,String> rootToRequirementComponentList = new HashMap<String,String>();
    	
    	//Next find the root statements since only the root is related to a clu
    	for(Statement statement:statements.values()){
    		Statement child = statement;
    		Statement parent = child;
    		while(parent!=null){
	    		try{
	    			//Search for parent of this child
	    			parent = statementDao.getParentStatement(child.getId());
	    			child = parent;
	    		}catch(DoesNotExistException e){
	    			//This is the root (no parent) so add to list of roots
	    			rootStatements.put(child.getId(), child);
	    			
	    			//Create a comma delimited mapping of all the requirement components
	    			//ids that contain the trigger clu within this root statement
	        		String childStatementList = rootToRequirementComponentList.get(child.getId());
	        		if(childStatementList==null){
	        			childStatementList = statementToResultComponentIds.get(statement.getId());
	        		}else{
	        			childStatementList += ","+statementToResultComponentIds.get(statement.getId());
	        		}
	        		rootToRequirementComponentList.put(child.getId(), childStatementList);
	    			
	    			//Exit condition(hopefully there are no cyclic statements)
	    			parent = null;
	    		}
    		}
    	}
    	
    	SearchResultInfo searchResult = new SearchResultInfo();
    	
    	//Record each statement's reference id type and reference type as a search result row
    	//Use a hashset of the cell values to remove duplicates
    	Set<String> processed = new HashSet<String>();
    	for(Statement statement:rootStatements.values()){
    		for(RefStatementRelation relation:statement.getRefStatementRelations()){
    			String rowId = relation.getRefObjectId()+"|"+relation.getRefObjectTypeKey();
    			if(!processed.contains(rowId)){
    				//This row does not exist yet so we can add it to the results.
    				processed.add(rowId);
	    			SearchResultRowInfo row = new SearchResultRowInfo();
	    			row.addCell("stmt.resultColumn.refObjId",relation.getRefObjectId());
	    			row.addCell("stmt.resultColumn.rootId",statement.getId());
	    			row.addCell("stmt.resultColumn.requirementComponentIds",rootToRequirementComponentList.get(statement.getId()));
	    			row.addCell("stmt.resultColumn.statementTypeId",statement.getStatementType().getId());
	    			row.addCell("stmt.resultColumn.statementTypeName",statement.getStatementType().getName());
	     			searchResult.getRows().add(row);
    			}
    		}
    	}
    	
		return searchResult;
	}

	@Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    @Override
    @Transactional(readOnly=true)
    public StatementTypeInfo getStatementType(final String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return StatementAssembler.toStatementTypeInfo(statementDao.fetch(StatementType.class, statementTypeKey));
    }

    @Override
    @Transactional(readOnly=true)
    public List<StatementTypeInfo> getStatementTypes() throws OperationFailedException {
        return StatementAssembler.toStatementTypeInfos(statementDao.find(StatementType.class));
    }

    @Transactional(readOnly=true)
    public List<String> getStatementTypesForStatementType(final String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	StatementTypeInfo type = StatementAssembler.toStatementTypeInfo(statementDao.fetch(StatementType.class, statementTypeKey));
    	return type.getAllowedStatementTypes();
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReqComponentTypeInfo> getReqComponentTypes() throws OperationFailedException {
        return StatementAssembler.toReqComponentTypeInfos(statementDao.find(ReqComponentType.class));
    }

    @Override
    @Transactional(readOnly=true)
    public ReqComponentTypeInfo getReqComponentType(final String reqComponentTypeKey)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return StatementAssembler.toReqComponentTypeInfo(statementDao.fetch(ReqComponentType.class, reqComponentTypeKey));
    }

    @Override
    @Transactional(readOnly=true)
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(final String statementTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(statementTypeKey, "statementTypeKey");

        StatementType stmtType = statementDao.fetch(StatementType.class, statementTypeKey);
        if(null == stmtType) {
            throw new DoesNotExistException("Statement Type: " + statementTypeKey + " does not exist.");
        }

        return StatementAssembler.toReqComponentTypeInfosOrdered( stmtType.getAllowedReqComponentTypes() );
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
        ReqComponentInfo updReqCompInfo = StatementAssembler.toReqComponentInfo(updatedReqComp);
        return updReqCompInfo;
    }

	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public RefStatementRelationInfo createRefStatementRelation(final RefStatementRelationInfo refStatementRelationInfo)
			throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(refStatementRelationInfo, "refStatementRelationInfo");

		Statement statement = this.statementDao.fetch(Statement.class, refStatementRelationInfo.getStatementId());
		RefStatementRelationType type = this.statementDao.fetch(RefStatementRelationType.class, refStatementRelationInfo.getType());

        // make sure refObjectType exist
        this.statementDao.fetch(ObjectType.class, refStatementRelationInfo.getRefObjectTypeKey());
		
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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public RefStatementRelationInfo updateRefStatementRelation(final String refStatementRelationId, final RefStatementRelationInfo refStatementRelationInfo)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		checkForNullOrEmptyParameter(refStatementRelationId, "refStatementRelationId");
		checkForMissingParameter(refStatementRelationInfo, "refStatementRelationInfo");

		refStatementRelationInfo.setId(refStatementRelationId);
		RefStatementRelation refStatementRel = statementAssembler.toRefStatementRelation(true, refStatementRelationInfo);
		RefStatementRelation updatedRefStatementRel = statementDao.update(refStatementRel);

		RefStatementRelationInfo dto = StatementAssembler.toRefStatementRelationInfo(updatedRefStatementRel);
		return dto;
	}

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
        ObjectStructureDefinition objStructure = this.getObjectStructure(RefStatementRelationInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(refStatementRelationInfo, objStructure, null);

		return validationResults;
	}

    @Override
    @Transactional(readOnly=true)
    public StatementTreeViewInfo getStatementTreeView(final String statementId)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	checkForNullOrEmptyParameter("statementId", statementId);

    	return getStatementTreeView(statementId, null, null);
    }

    @Override
    @Transactional(readOnly=true)
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


    	/*
    	Map<String, String> configuration = new HashMap<String, String>();
    	configuration.put("USAGE_TYPE_KEY", nlUsageTypeKey);
    	configuration.put("NL_KEY", language);
    	StatementTreeViewInfo result = new StatementTreeViewInfo();
    	try {
			statementTreeViewAssembler.assemble(getStatement(statementId), result, false, configuration);
			return result;
    	} catch (AssemblyException e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
		*/
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

                // recursive call to get subStatementTreeViewInfo
                getStatementTreeViewHelper(subStatement, subStatementTreeViewInfo, nlUsageTypeKey, language);
                statements.add(subStatementTreeViewInfo);
                statementTreeViewInfo.setStatements(statements);
            }
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatementTreeViewInfo updateStatementTreeView(final String statementId, final StatementTreeViewInfo statementTreeViewInfo)
    	throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		
		Statement stmt = this.statementDao.fetch(Statement.class, statementTreeViewInfo.getId());
	    if (stmt == null) {
	        throw new DoesNotExistException("Statement does not exist for id: " + statementTreeViewInfo.getId());
		}
		if (!String.valueOf(stmt.getVersionNumber()).equals(statementTreeViewInfo.getMetaInfo().getVersionInd())) {
		    throw new VersionMismatchException("Statement to be updated is not the current version");
		}
		   
	    Set<String> statementIdsToDelete = new HashSet<String>();
	    List<ReqComponent> requirementComponentsToCreate = new ArrayList<ReqComponent>();
	    List<Statement> statmentsToUpdate = new ArrayList<Statement>();
	    
	    //Transform the tree into a statement with all of its children
	    stmt = statementAssembler.toStatementFromTree(stmt, statementTreeViewInfo, statementIdsToDelete, statmentsToUpdate, requirementComponentsToCreate);
		
	    //Create any new reqComponents 
	    for(ReqComponent reqComponent:requirementComponentsToCreate){
			statementDao.create(reqComponent);
		}
	    
	    //Update the actual statement
	    stmt = statementDao.update(stmt);
	    
	    //Update statements where the join table needs to be cleared
	    
	    //delete orphaned statements
	    for(String statementIdToDelete:statementIdsToDelete){
	    	statementDao.delete(Statement.class, statementIdToDelete);
	    }
	    
	    //Transform back to a dto
	    StatementTreeViewInfo result = statementAssembler.toStatementTreeViewInfo(stmt);
	
		return result;


    }

    /*private List<String> notIn(
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
    }*/

	private void updateStatementTreeViewHelper(StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (statementTreeViewInfo.getStatements() != null) {
            for (StatementTreeViewInfo subStatement : statementTreeViewInfo.getStatements()) {
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
                    // the id here even if it is not null it is the temporary ids assigned by atpService
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
            // the id here even if it is not null it is the temporary ids assigned by atpService
            // so resets the id to null to allow a new id to be generated.
//            statementTreeViewInfo.setId(null);
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

    /**
     *
     * @return a list of relationships in the first list but not in the second
     */
	@Override
    @Transactional(readOnly=true)
	public RefStatementRelationTypeInfo getRefStatementRelationType(final String refStatementRelationTypeKey)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForNullOrEmptyParameter(refStatementRelationTypeKey, "refStatementRelationTypeKey");

		RefStatementRelationType type = this.statementDao.fetch(RefStatementRelationType.class, refStatementRelationTypeKey);

		return StatementAssembler.toRefStatementRelationTypeInfo(type);
	}

	@Override
    @Transactional(readOnly=true)
	public List<RefStatementRelationTypeInfo> getRefStatementRelationTypes()
			throws OperationFailedException {
		List<RefStatementRelationType> entities = this.statementDao.find(RefStatementRelationType.class);
		return StatementAssembler.toRefStatementRelationTypeInfos(entities);
	}

	@Override
    @Transactional(readOnly=true)
	public List<String> getRefStatementRelationTypesForRefObjectSubType(final String refSubTypeKey)
		throws DoesNotExistException,InvalidParameterException, MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
    @Transactional(readOnly=true)
	public List<String> getStatementTypesForRefStatementRelationType(final String refStatementRelationTypeKey)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("Method not yet implemented!");
	}

	/**
	 * 
	 * This method ...
	 * 
	 * @param validatorFactory
	 * @
	 */
	@Deprecated
	public void setValidatorFactory(ValidatorFactory validatorFactory)  {
		this.validatorFactory = validatorFactory;
	}

}