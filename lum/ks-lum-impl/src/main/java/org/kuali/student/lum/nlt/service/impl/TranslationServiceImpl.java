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
package org.kuali.student.lum.nlt.service.impl;

import javax.jws.WebService;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.DtoAdapter;
import org.kuali.student.lum.nlt.service.TranslationService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.nlt.service.TranslationService", serviceName = "TranslationService", portName = "TranslationService", targetNamespace = "http://student.kuali.org/wsdl/nlt")
@Transactional(rollbackFor={Throwable.class})
public class TranslationServiceImpl implements TranslationService {

	private LuService luService;
	private DtoAdapter dtoAdapter = new DtoAdapter();
    private NaturalLanguageTranslator naturalLanguageTranslator;

	public void setLuService(LuService luService) {
		this.luService = luService;
		this.dtoAdapter.setLuService(luService);
	}

	public void setNaturalLanguageTranslator(NaturalLanguageTranslator translator) {
		this.naturalLanguageTranslator = translator;
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
	 * @param cluId Clu id anchor for statement
	 * @param luStatementId Statement to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException Statement not found or Clu anchor not found in statement
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing luStatementId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public String getNaturalLanguageForLuStatement(String cluId, String luStatementId, String nlUsageTypeKey, String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForNullOrEmptyParameter(luStatementId, "luStatementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			LuStatementInfo luStatementInfo = this.luService.getLuStatement(luStatementId);
			checkCluExistsInLuStatementInfo(cluId, luStatementInfo);
			CustomLuStatementInfo customInfo = dtoAdapter.toCustomLuStatementInfo(luStatementInfo);
			String nl = this.naturalLanguageTranslator.translateStatement(cluId, customInfo, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}
	
	/**
	 * <p>Translates and retrieves a statement directly attached to a CLU 
	 * for a specific usuage type (context) into natural language.</p>
	 * If <code>cluId</code> is null or empty then statement header is not
	 * generated</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuNlStatementInfo</code> can either have a list of
	 * <code>LuNlStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * <p>Note: LuNlStatementInfo has no id since it is only used for 
	 * on-the-fly translations and is not persisted.</p>
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

		checkForMissingParameter(statementInfo, "statementInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		CustomLuStatementInfo customInfo = this.dtoAdapter.toCustomLuStatementInfo(statementInfo);

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			String nl = this.naturalLanguageTranslator.translateStatement(cluId, customInfo, nlUsageTypeKey);
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
			ReqComponentInfo reqComponentInfo = this.luService.getReqComponent(reqComponentId);
			CustomReqComponentInfo customReq = this.dtoAdapter.toCustomReqComponentInfo(reqComponentInfo);
			String nl = this.naturalLanguageTranslator.translateReqComponent(customReq, nlUsageTypeKey);
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
	public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {

		checkForMissingParameter(reqComponentInfo, "reqComponentInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		if(reqComponentInfo.getRequiredComponentType() == null) {
			ReqComponentTypeInfo type = this.luService.getReqComponentType(reqComponentInfo.getType());
			reqComponentInfo.setRequiredComponentType(type);
		}
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			CustomReqComponentInfo customReq = this.dtoAdapter.toCustomReqComponentInfo(reqComponentInfo);
			String nl = this.naturalLanguageTranslator.translateReqComponent(customReq, nlUsageTypeKey);
			return nl;
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	/**
	 * <p>Translates and retrieves a statement for a specific natural language 
	 * usuage type (context) and language into a natural language 
	 * tree structure.</p> If <code>cluId</code> is null or empty then 
	 * statement header is not generated</p>
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
	 * @throws DoesNotExistException CLU or statement does not exist or Clu anchor not found in statement
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo getNaturalLanguageForStatementAsTree(String cluId, String luStatementId, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException {
		checkForNullOrEmptyParameter(luStatementId, "luStatementId");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");

		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			LuStatementInfo luStatementInfo = this.luService.getLuStatement(luStatementId);
			checkCluExistsInLuStatementInfo(cluId, luStatementInfo);
			CustomLuStatementInfo customInfo = this.dtoAdapter.toCustomLuStatementInfo(luStatementInfo);
			return this.naturalLanguageTranslator.translateToTree(cluId, customInfo, nlUsageTypeKey);
		} finally {
			this.naturalLanguageTranslator.setLanguage(lang);
		}
	}

	/**
	 * Translates and retrieves a statement for a specific natural language 
	 * usuage type (context) into a natural language tree structure.
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
	 * @param cluId Clu anchor
	 * @param statement Statement to translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist or Clu anchor not found in statement
	 * @throws OperationFailedException Translation fails
     * @throws MissingParameterException Missing reqComponentId or nlUsageTypeKey
     * @throws InvalidParameterException Invalid CLU id or nlUsageTypeKey 
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public NLTranslationNodeInfo getNaturalLanguageForStatementInfoAsTree(String cluId, LuStatementInfo statementInfo, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException {
		checkForMissingParameter(statementInfo, "statementInfo");
		checkForNullOrEmptyParameter(nlUsageTypeKey, "nlUsageTypeKey");
		checkForEmptyParameter(language, "language");
		checkCluExistsInLuStatementInfo(cluId, statementInfo);

		CustomLuStatementInfo customInfo = this.dtoAdapter.toCustomLuStatementInfo(statementInfo);
		
		final String lang = this.naturalLanguageTranslator.getLanguage();
		try {
			if(language != null) {
				this.naturalLanguageTranslator.setLanguage(language);
			}
			return this.naturalLanguageTranslator.translateToTree(cluId, customInfo, nlUsageTypeKey);
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

	/**
	 * Checks whether <code>cluId</code> is in the 
	 * <code>luStatementInfo</code>'s list of of anchor CLU ids.
	 * 
	 * @param cluId CLU id
	 * @param luStatementInfo LU statement info
	 * @throws DoesNotExistException Thrown if cluId is not an anchor for LU statement
	 */
	private void checkCluExistsInLuStatementInfo(String cluId, LuStatementInfo luStatementInfo) throws DoesNotExistException {
		if(cluId == null || cluId.isEmpty()) {
			return;
		}
		if (luStatementInfo.getCluIds() == null || luStatementInfo.getCluIds().isEmpty()) {
			throw new DoesNotExistException("LU statement has no anchor CLUs: luStatementId=" + luStatementInfo.getId());
		}
		if (!luStatementInfo.getCluIds().contains(cluId)) {
			throw new DoesNotExistException("Anchor CLU does not exists in LU statement: CluId=" + cluId);
		}
	}
}
