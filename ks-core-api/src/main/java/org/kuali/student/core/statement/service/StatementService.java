package org.kuali.student.core.statement.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;

@WebService(name = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StatementService {

	/**
	 * Retrieves the list of base types which can be connected to a document.
	 * 
	 * @return The list of types which can be connected to a document
	 * @throws OperationFailedException Unable to complete request
	 */
	public List<String> getRefObjectTypes() throws OperationFailedException;

	/**
	 * Retrieves the list of types for a given base type which can be connected to a document.
	 * 
	 * @param objectTypeKey Reference Type Identifier
	 * @return The list of types for the given base type which can be connected to a document
	 * @throws OperationFailedException Unable to complete request
	 */
	public List<String> getRefObjectSubTypes(@WebParam(name="objectTypeKey")String objectTypeKey) throws OperationFailedException;
	
	/**
	 * Retrieves the list of natural language usage types known by the service.
	 * 
	 * @return List of natural language usage type information
	 * @throws OperationFailedException Unable to complete request
	 */
	public List<NlUsageTypeInfo> getNlUsageTypes() throws OperationFailedException;

	/**
	 * Retrieves information about the specified natural language usage type.
	 * 
	 * @param nlUsageTypeKey Natural language usage type identifier
	 * @return Information about a type of natural language usage
	 * @throws DoesNotExistException nlUsageType not found
	 * @throws InvalidParameterException Invalid nlUsageTypeKey
	 * @throws MissingParameterException Missing nlUsageTypeKey
	 * @throws OperationFailedException Unable to complete request
	 */
	public NlUsageTypeInfo getNlUsageType(@WebParam(name="nlUsageTypeKey")String nlUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
	/**
	 * Retrieves a object statement relationship by its identifier.
	 * 
	 * @param refStatementRelationId Object statement relationship identifier
	 * @return Object statement relationship information
	 * @throws DoesNotExistException RefStatementRelation not found
	 * @throws InvalidParameterException Invalid refStatementRelationId
	 * @throws MissingParameterException RefStatementRelationId not specified
	 * @throws OperationFailedException Unable to complete request
	 */
	public RefStatementRelationInfo getRefStatementRelation(@WebParam(name="refStatementRelationId")String refStatementRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

	/**
	 * Retrieves a list of object statement relationships for a particular object.
	 * 
	 * @param refObjectTypeKey Reference type
	 * @param refObjectId Reference identifier
	 * @return List of object statement relationships for a particular object
	 * @throws DoesNotExistException Object not found
	 * @throws InvalidParameterException One or more parameters invalid
	 * @throws MissingParameterException One or more parameters not specified
	 * @throws OperationFailedException Unable to complete request
	 */
	public List<RefStatementRelationInfo> getRefStatementRelationsForRef(@WebParam(name="refObjectTypeKey")String refObjectTypeKey, @WebParam(name="refObjectId")String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

	/**
	 * Retrieves a list of object statement relationships for a particular statement.
	 * 
	 * @param statementId Statement identifier
	 * @return List of object statement relationships for a particular statement
	 * @throws DoesNotExistException Statement not found
	 * @throws InvalidParameterException One or more parameters invalid
	 * @throws MissingParameterException One or more parameters not specified
	 * @throws OperationFailedException Unable to complete request
	 */
	public List<RefStatementRelationInfo> getRefStatementRelationsForStatement(@WebParam(name="statementId")String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
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
