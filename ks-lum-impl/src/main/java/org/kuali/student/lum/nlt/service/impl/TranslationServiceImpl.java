package org.kuali.student.lum.nlt.service.impl;

import javax.jws.WebService;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.service.impl.LuServiceAssembler;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.lum.nlt.service.TranslationService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.nlt.service.TranslationService", serviceName = "TranslationService", portName = "TranslationService", targetNamespace = "http://student.kuali.org/wsdl/nlt")
@Transactional(rollbackFor={Throwable.class})
public class TranslationServiceImpl implements TranslationService {

	private LuDao luDao;
    private NaturalLanguageTranslator naturalLanguageTranslator;

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	public void setNaturalLanguageTranslator(NaturalLanguageTranslator translator) {
		this.naturalLanguageTranslator = translator;
	}
	
	/**
	 * <p>Translates and retrieves a statement directly attached to a CLU 
	 * for a specific usuage type (context) into natural language.</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuStatementInfo</code> can either have a list of
	 * <code>LuStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * @param cluId Clu id anchor for statement
	 * @param luStatementId Statement to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing luStatementId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public String getNaturalLanguageForLuStatement(String cluId, String luStatementId, String nlUsageTypeKey, String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(cluId, "cluId");
		checkForNullOrEmptyParameter(luStatementId, "luStatementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			String nl = this.naturalLanguageTranslator.translateStatement(cluId, luStatementId, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}
	
	/**
	 * <p>Translates and retrieves a statement directly attached to a CLU 
	 * for a specific usuage type (context) into natural language.</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuNlStatementInfo</code> can either have a list of
	 * <code>LuNlStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * @param cluId Clu id anchor for statement
	 * @param statementInfo Statement to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing statementInfo or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public String getNaturalLanguageForLuStatementInfo(String cluId, LuNlStatementInfo statementInfo, String nlUsageTypeKey, String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {

		checkForNullOrEmptyParameter(cluId, "cluId");
		checkForMissingParameter(statementInfo, "statementInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		LuStatement luStatement = LuServiceAssembler.toLuStatementRelation(statementInfo, luDao);

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			String nl = this.naturalLanguageTranslator.translateStatement(cluId, luStatement, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
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
			String nl = this.naturalLanguageTranslator.translateReqComponent(reqComponentId, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
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
	public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo reqCompInfo, String nlUsageTypeKey, String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {

		checkForMissingParameter(reqCompInfo, "reqCompInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		ReqComponent reqComponent = LuServiceAssembler.toReqComponentRelation(false, reqCompInfo, luDao);

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			String nl = this.naturalLanguageTranslator.translateReqComponent(reqComponent, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	/**
	 * <p>Translates and retrieves a statement for a specific natural language 
	 * usuage type (context) and language into a natural language 
	 * tree structure.</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuStatementInfo</code> can either have a list of
	 * <code>LuStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * @param cluId Clu anchor
	 * @param statementId Statement to translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo getNaturalLanguageForStatementAsTree(String cluId, String statementId, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		checkForNullOrEmptyParameter(cluId, "cluId");
		checkForNullOrEmptyParameter(statementId, "statementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			return this.naturalLanguageTranslator.translateToTree(cluId, statementId, nlUsageTypeKey);
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	/**
	 * Translates and retrieves a statement for a specific natural language 
	 * usuage type (context) into a natural language tree structure.
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuStatementInfo</code> can either have a list of
	 * <code>LuStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * @param cluId Clu anchor
	 * @param statement Statement to translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
     * @throws MissingParameterException Missing reqComponentId or nlUsageTypeKey
     * @throws InvalidParameterException Invalid LCU id or nlUsageTypeKey 
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public NLTranslationNodeInfo getNaturalLanguageForStatementInfoAsTree(String cluId, LuStatementInfo statementInfo, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		checkForNullOrEmptyParameter(cluId, "cluId");
		checkForMissingParameter(statementInfo, "statementInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		LuStatement statement = LuServiceAssembler.toLuStatementRelation(false, statementInfo, luDao);

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			return this.naturalLanguageTranslator.translateToTree(cluId, statement, nlUsageTypeKey);
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
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

}
