package org.kuali.student.core.statement.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;

@WebService(name = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StatementService {

    /**
	 * <p>Translates and retrieves a statement for a specific usuage type 
	 * (context) and language into natural language.</p>
	 * 
	 * <p>If <code>language</code> is null default language is used.</p>
	 * 
	 * <p>An <code>LuStatementInfo</code> can either have a list of
	 * <code>LuStatementInfo</code>s as children or a list of
	 * <code>ReqComponentInfo</code>s but not both. This means that all leaf 
	 * nodes must be <code>ReqComponentInfo</code>s.</p>
	 * 
	 * @param statementId Statement to translate
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing luStatementId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public String getNaturalLanguageForStatement(@WebParam(name="statementId")String statementId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
	 * <p>Translates and retrieves a requirement component for a specific 
	 * usuage type (context) and language into natural language.</p>
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
}
