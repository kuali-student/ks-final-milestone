package org.kuali.student.core.statement.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.core.statement.service.StatementService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.statement.service.StatementService", serviceName = "StatementService", portName = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@Transactional(rollbackFor={Throwable.class})
public class StatementServiceImpl implements StatementService {

    private StatementDao statementDao;
	private NaturalLanguageTranslator naturalLanguageTranslator;

	public void setStatementDao(StatementDao statementDao) {
		this.statementDao = statementDao;
	}
	
	public void setNaturalLanguageTranslator(NaturalLanguageTranslator translator) {
		this.naturalLanguageTranslator = translator;
	}

	public NlUsageTypeInfo getNlUsageType(String nlUsageTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<NlUsageTypeInfo> getNlUsageTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getRefObjectSubTypes(String objectTypeKey)
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getRefObjectTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public RefStatementRelationInfo getRefStatementRelation(
			String refStatementRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RefStatementRelationInfo> getRefStatementRelationsForRef(
			String refObjectTypeKey, String refObjectId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RefStatementRelationInfo> getRefStatementRelationsForStatement(
			String statementId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
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
	public String getNaturalLanguageForStatement(String statementId,
			String nlUsageTypeKey, String language)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

/*		checkForNullOrEmptyParameter(statementId, "statementId");
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
		}*/
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
}