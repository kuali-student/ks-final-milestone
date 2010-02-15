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
package org.kuali.student.lum.nlt.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;

@WebService(name = "TranslationService", targetNamespace = "http://student.kuali.org/wsdl/nlt")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TranslationService {
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
    public String getNaturalLanguageForLuStatement(@WebParam(name="cluId")String cluId, @WebParam(name="luStatementId")String luStatementId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
	public String getNaturalLanguageForLuStatementInfo(@WebParam(name="cluId")String cluId, @WebParam(name="statementInfo")LuNlStatementInfo statementInfo, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException;
    
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
    public String getNaturalLanguageForReqComponent(@WebParam(name="reqComponentId")String reqComponentId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
	public String getNaturalLanguageForReqComponentInfo(@WebParam(name="reqComponent")ReqComponentInfo reqCompInfo, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException;
    
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
	public NLTranslationNodeInfo getNaturalLanguageForStatementAsTree(@WebParam(name="cluId")String cluId, @WebParam(name="statementId")String statementId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException ;

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
	public NLTranslationNodeInfo getNaturalLanguageForStatementInfoAsTree(@WebParam(name="cluId")String cluId, @WebParam(name="statementInfo")LuStatementInfo statementInfo, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, OperationFailedException, MissingParameterException, InvalidParameterException, VersionMismatchException;
}
