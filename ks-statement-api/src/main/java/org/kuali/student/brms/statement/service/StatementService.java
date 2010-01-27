package org.kuali.student.brms.statement.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

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
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.NlUsageTypeInfo;
import org.kuali.student.brms.statement.dto.RefStatementRelationInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

@WebService(name = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StatementService extends DictionaryService, SearchService {

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
    
    /** 
     * Validates a ReqComponent. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the organization (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the organization can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param reqComponentInfo reqComponent information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, reqComponentInfo
     * @throws MissingParameterException missing validationTypeKey, reqComponentInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultContainer> validateReqComponent(@WebParam(name="validationType")String validationType, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a statement. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the statement (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the statement can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param statementInfo statement information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, statementInfo
     * @throws MissingParameterException missing validationTypeKey, statementInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultContainer> validateStatement(@WebParam(name="validationType")String validationType, @WebParam(name="statementInfo")StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a statement by its identifier
     * @param  statementId statement identifier
     * @return statementInfo statement information
     * @throws DoesNotExistException statement not found
     * @throws InvalidParameterException invalid statementId
     * @throws MissingParameterException statementId not specified
     * @throws OperationFailedException unable to complete request
     */
    public StatementInfo getStatement(@WebParam(name="statementId")String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /** 
     * Retrieves a list of statements of a particular Type
     * @param statementTypeKey statementType identifier
     * @return list of statements using the specified type
     * @throws DoesNotExistException statementTypeKey  not found
     * @throws InvalidParameterException invalid statementTypeKey 
     * @throws MissingParameterException statementTypeKey  not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<StatementInfo> getStatementsByType(@WebParam(name="statementTypeKey")String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a requirement component by its identifier
     * @param reqComponentId requirement component identifier
     * @return reqComponentInfo requirement component information
     * @throws DoesNotExistException reqComponent not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException reqComponentId not specified
     * @throws OperationFailedException unable to complete request
     */
    public ReqComponentInfo getReqComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of requirement components of a particular type.
     * @param reqComponentTypeKey identifier for a type of requirement component
     * @return reqComponentInfoList A list of requirementComponents
     * @throws DoesNotExistException reqComponentTypeKey not found
     * @throws InvalidParameterException invalid reqComponentTypeKey
     * @throws MissingParameterException reqComponentTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<ReqComponentInfo> getReqComponentsByType(@WebParam(name="reqComponentTypeKey")String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of statements that use a particular requirement component. Note: The reference may not be direct, but through an intermediate object definition (ex. nested statements).
     * @param reqComponentId requirement component identifier
     * @return statementInfoList list of statements using the specified requirement component
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException reqComponentId not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<StatementInfo> getStatementsUsingComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a requirement component.
     * @param reqComponentType identifier of the type of requirement component
     * @param reqComponentInfo information about the requirement component
     * @return information about the newly created requirement component
     * @throws AlreadyExistsException Requirement Component already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException reqComponentType not found
     * @throws InvalidParameterException invalid reqComponentType, reqComponentInfo
     * @throws MissingParameterException missing reqComponentType, reqComponentInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ReqComponentInfo createReqComponent(@WebParam(name="reqComponentType")String reqComponentType, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /** 
     * Deletes a requirement component
     * @param reqComponentId identifier of the requirement component to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException missing reqComponentId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteReqComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a statement.
     * @param statementType identifier of the type of statement
     * @param statementInfo information about the statement
     * @return information about the newly created statement
     * @throws AlreadyExistsException statement already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException statementType not found
     * @throws InvalidParameterException invalid statementType, statementInfo
     * @throws MissingParameterException missing statementType, statementInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatementInfo createStatement(@WebParam(name="statementType")String statementType, @WebParam(name="statementInfo")StatementInfo statementInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a statement
     * @param statementId identifier of the statement to be updated
     * @param statementInfo information about the statement to be updated
     * @return the updated statement information
     * @throws CircularReferenceException included statement references the current statement
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException statement not found
     * @throws InvalidParameterException invalid statementId, statementInfo
     * @throws MissingParameterException missing statementId, statementInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public StatementInfo updateStatement(@WebParam(name="statementId")String statementId, @WebParam(name="statementInfo")StatementInfo statementInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a statement
     * @param statementId identifier of the Statement to delete
     * @return statusInfo status of the operation (success or failure)
     * @throws DoesNotExistException statement not found
     * @throws InvalidParameterException invalid statementId
     * @throws MissingParameterException missing statementId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteStatement(@WebParam(name="statementId")String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information for a specified type of statement
     * @param statementTypeKey statement type identifier
     * @return statement type information
     * @throws DoesNotExistException statementTypeKey not found
     * @throws InvalidParameterException invalid statementTypeKey
     * @throws MissingParameterException missing statementTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public StatementTypeInfo getStatementType(@WebParam(name="statementTypeKey")String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /** 
     * Retrieves the list of requirement component types known by this service.
     * @return list of requirement component types
     * @throws OperationFailedException unable to complete request
     */
    public List<ReqComponentTypeInfo> getReqComponentTypes() throws OperationFailedException;

    /** 
     * Retrieves information for a specified fetchReqComponent Types
     * @param reqComponentTypeKey reqComponent Type Key
     * @return Requirement component type information
     * @throws DoesNotExistException reqComponentTypeKey not found
     * @throws InvalidParameterException invalid reqComponentTypeKey
     * @throws MissingParameterException missing reqComponentTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public ReqComponentTypeInfo getReqComponentType(@WebParam(name="reqComponentTypeKey")String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of types of requirement components which are allowed to be used in a type of statement.
     * @param luStatementTypeKey identifier for a type of statement
     * @return list of types of requirement components
     * @throws DoesNotExistException statementTypeKey not found
     * @throws InvalidParameterException invalid statementTypeKey
     * @throws MissingParameterException missing statementTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(@WebParam(name="statementTypeKey")String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Updates a requirement component
     * @param reqComponentId identifier of the requirement component to be updated
     * @param reqComponentInfo information about the requirement component to be updated
     * @return the updated requirement component information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Requirement Component not found
     * @throws InvalidParameterException invalid reqComponentId, reqComponentInfo
     * @throws MissingParameterException missing reqComponentId, reqComponentInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public ReqComponentInfo updateReqComponent(@WebParam(name="reqComponentId")String reqComponentId, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;
}
