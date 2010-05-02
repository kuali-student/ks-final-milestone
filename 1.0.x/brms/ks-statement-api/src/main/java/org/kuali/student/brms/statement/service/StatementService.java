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
import org.kuali.student.brms.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.NlUsageTypeInfo;
import org.kuali.student.brms.statement.dto.RefStatementRelationInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementTreeViewInfo;
import org.kuali.student.brms.statement.dto.StatementTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

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
	public List<String> getRefObjectSubTypes(@WebParam(name="objectTypeKey")String objectTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
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
	 * Creates a relationship between a statement and an object.
	 * 
	 * @param refStatementRelationInfo
	 * @return New object statement relationship
	 * @throws AlreadyExistsException connection between object and statement already exists
	 * @throws DoesNotExistException E.g. cluId, statementId, refStatementRelationType not found
	 * @throws InvalidParameterException One or more parameters invalid
	 * @throws MissingParameterException One or more parameters not specified
	 * @throws OperationFailedException Unable to complete request
	 * @throws PermissionDeniedException Authorization failure
	 */
	public RefStatementRelationInfo createRefStatementRelation(@WebParam(name="refStatementRelationInfo")RefStatementRelationInfo refStatementRelationInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

	/**
	 * 	Updates a relationship between an object and statement.
	 * 
	 * @param refStatementRelationId Identifier of the object statement relationship to be updated
	 * @param refStatementRelationInfo Information about the object statement relationship to be updated
	 * @return Updated object statement relationship information
	 * @throws DataValidationErrorException One or more values invalid for this operation
	 * @throws DoesNotExistException refStatementRelation not found
	 * @throws InvalidParameterException One or more parameters invalid
	 * @throws MissingParameterException One or more parameters missing
	 * @throws OperationFailedException Unable to complete request
	 * @throws PermissionDeniedException Authorization failure
	 * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
	public RefStatementRelationInfo updateRefStatementRelation(@WebParam(name="refStatementRelationId")String refStatementRelationId, @WebParam(name="refStatementRelationInfo")RefStatementRelationInfo refStatementRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

	/**
	 * Removes a relationship between a statement and an object.
	 * 
	 * @param refStatementRelationId Object Statement Relationship identifier
	 * @return Status
	 * @throws DoesNotExistException RefStatementRelation not found
	 * @throws InvalidParameterException Invalid refStatementRelationId
	 * @throws MissingParameterException RefStatementRelationId not specified
	 * @throws OperationFailedException Unable to complete request
	 * @throws PermissionDeniedException Authorization failure
	 */
	public StatusInfo deleteRefStatementRelation(@WebParam(name="refStatementRelationId")String refStatementRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

	/**
	 * Validates a refStatementRelation. Depending on the value of 
	 * validationType, this validation could be limited to tests on just 
	 * the current object and its directly contained sub-objects or expanded 
	 * to perform all tests related to this object. If an identifier is 
	 * present for the relationship (and/or one of its contained sub-objects) 
	 * and a record is found for that identifier, the validation checks if 
	 * the relationship can be shifted to the new values. If an identifier is 
	 * not present or a record cannot be found for the identifier, it is 
	 * assumed that the record does not exist and as such, the checks 
	 * performed will be much shallower, typically mimicking those performed 
	 * by setting the validationType to the current object.
	 * 
	 * @param validationType Identifier of the extent of validation
	 * @param refStatementRelationInfo Object statement relationship information to be tested
	 * @return Results from performing the validation
	 * @throws DoesNotExistException validationTypeKey not found
	 * @throws InvalidParameterException Invalid validationTypeKey, refStatementRelationInfo
	 * @throws MissingParameterException Missing validationTypeKey, refStatementRelationInfo
	 * @throws OperationFailedException Unable to complete request
	 */
	public List<ValidationResultInfo> validateRefStatementRelation(@WebParam(name="validationType")String validationType, @WebParam(name="refStatementRelationInfo")RefStatementRelationInfo refStatementRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
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
	public List<RefStatementRelationInfo> getRefStatementRelationsByRef(@WebParam(name="refObjectTypeKey")String refObjectTypeKey, @WebParam(name="refObjectId")String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
	public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(@WebParam(name="statementId")String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

	/**
	 * <p>Translates and retrieves a statement for a specific usuage type 
	 * (context) and language into natural language.</p>
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
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException Invalid nlUsageTypeKey 
     * @throws MissingParameterException Missing statementId or nlUsageTypeKey
     * @throws OperationFailedException Unable to complete request
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public String getNaturalLanguageForStatement(@WebParam(name="statementId")String statementId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the natural language translation for a particular object 
     * statement relationship in a particular context for a particular language.
     * 
     * @param refStatementRelationId Object statement relationship identifier
     * @param nlUsageTypeKey Context for the natural language translation
     * @param language Language to use for the natural language translation
     * @return Natural language translation for a particular object statement relationship in a particular context
     * @throws DoesNotExistException Object statement relationship not found
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters not specified
     * @throws OperationFailedException Unable to complete request
     */
    public String getNaturalLanguageForRefStatementRelation(@WebParam(name="refStatementRelationId")String refStatementRelationId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
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
     * Translates a statement tree view to natural language for a 
     * particular context in a particular language. This may include 
     * statements and/or requirement components which have not yet been 
     * persisted to the service.
     * 
     * @param statementTreeViewInfo Statement tree view
     * @param nlUsageTypeKey Context for the natural language translation
     * @param language Language to use for the natural language translation
     * @return Natural language translation for a particular statement in a particular context
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters not specified
     * @throws OperationFailedException Unable to complete request
     */
    public String translateStatementTreeViewToNL(@WebParam(name="statementTreeViewInfo")StatementTreeViewInfo statementTreeViewInfo, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Translates a particular requirement component to natural language for 
     * a particular context in a given language. This may be used for 
     * requirement components which have not yet been persisted through 
     * the service.
     * 
     * @param reqComponentInfo Requirement component
     * @param nlUsageTypeKey Context for the natural language translation
     * @param language Language to use for the natural language translation
     * @return Natural language translation for a particular requirement component in a particular context
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters not specified
     * @throws OperationFailedException Unable to complete request
     */
    public String translateReqComponentToNL(@WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /** 
     * Validates a ReqComponent. Depending on the value of validationType, 
     * this validation could be limited to tests on just the current object and 
     * its directly contained sub-objects or expanded to perform all tests 
     * related to this object. If an identifier is present for the 
     * organization (and/or one of its contained sub-objects) and a record 
     * is found for that identifier, the validation checks if the organization 
     * can be shifted to the new values. If an identifier is not present or 
     * a record cannot be found for the identifier, it is assumed that the 
     * record does not exist and as such, the checks performed will be much 
     * shallower, typically mimicking those performed by setting the 
     * validationType to the current object.
     * 
     * @param validationType identifier of the extent of validation
     * @param reqComponentInfo reqComponent information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, reqComponentInfo
     * @throws MissingParameterException missing validationTypeKey, reqComponentInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateReqComponent(@WebParam(name="validationType")String validationType, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a statement. Depending on the value of validationType, 
     * this validation could be limited to tests on just the current object 
     * and its directly contained sub-objects or expanded to perform all 
     * tests related to this object. If an identifier is present for the 
     * statement (and/or one of its contained sub-objects) and a record is 
     * found for that identifier, the validation checks if the statement can 
     * be shifted to the new values. If an identifier is not present or 
     * a record cannot be found for the identifier, it is assumed that the 
     * record does not exist and as such, the checks performed will be much 
     * shallower, typically mimicking those performed by setting the 
     * validationType to the current object.
     * 
     * @param validationType identifier of the extent of validation
     * @param statementInfo statement information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, statementInfo
     * @throws MissingParameterException missing validationTypeKey, statementInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateStatement(@WebParam(name="validationType")String validationType, @WebParam(name="statementInfo")StatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    public List<StatementInfo> getStatementsUsingReqComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of child statements that include a particular statement. 
     * Note: The reference may not be direct, but through an 
     * intermediate object definition (e.g. nested statements).
     * 
     * @param statementId statement identifier
     * @return List of child statements using the specified statement
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException Invalid statementId
     * @throws MissingParameterException statementId not specified
     * @throws OperationFailedException Unable to complete request
     */
    public List<StatementInfo> getStatementsUsingStatement(@WebParam(name="statementId")String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
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
     * 	Retrieves the list of all types of statements.
     * 
     * @return List of types of statements
     * @throws OperationFailedException Unable to complete request
     */
    public List<StatementTypeInfo> getStatementTypes() throws OperationFailedException;

    /**
     * Retrieves the list of statement types which are allowed to be used in 
     * a statement type. This controls the nesting of statements.
     * 
     * @param statementTypeKey Identifier for a type of statement
     * @return List of statement type
     * @throws DoesNotExistException statementTypeKey not found
     * @throws InvalidParameterException Invalid statementTypeKey
     * @throws MissingParameterException Missing statementTypeKey
     * @throws OperationFailedException Unable to complete request
     */
    public List<String> getStatementTypesForStatementType(@WebParam(name="statementTypeKey")String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
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
     * @param statementTypeKey identifier for a type of statement
     * @return list of types of requirement components
     * @throws DoesNotExistException statementTypeKey not found
     * @throws InvalidParameterException invalid statementTypeKey
     * @throws MissingParameterException missing statementTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(@WebParam(name="statementTypeKey")String statementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of all types of relationships between statements and 
     * other objects.
     * 
     * @return List of object statement relation types
     * @throws OperationFailedException Unable to complete request
     */
    public List<RefStatementRelationTypeInfo> getRefStatementRelationTypes() throws OperationFailedException;

    /**
     * Retrieves information for a specified type of relationship between 
     * a statement and object.
     * 
     * @param refStatementRelationTypeKey Object statement relation type identifier
     * @return Object statement relation type information
     * @throws DoesNotExistException refStatementRelationTypeKey not found
     * @throws InvalidParameterException Invalid refStatementRelationTypeKey
     * @throws MissingParameterException Missing refStatementRelationTypeKey
     * @throws OperationFailedException Unable to complete request
     */
    public RefStatementRelationTypeInfo getRefStatementRelationType(@WebParam(name="refStatementRelationTypeKey")String refStatementRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of statement types which are allowed to be used for 
     * a specified type of object statement relationship.
     * 
     * @param refStatementRelationTypeKey Identifier for a type of object statement relationship
     * @return List of statement types
     * @throws DoesNotExistException refStatementRelationTypeKey not found
     * @throws InvalidParameterException Invalid refStatementRelationTypeKey
     * @throws MissingParameterException Missing refStatementRelationTypeKey
     * @throws OperationFailedException Unable to complete request
     */
    public List<String> getStatementTypesForRefStatementRelationType(@WebParam(name="refStatementRelationTypeKey")String refStatementRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of types of object statement relationships which 
     * are allowed to be used for a subtype of object.
     * 
     * @param refSubTypeKey Identifier for the subtype of object
     * @return
     * @throws DoesNotExistException refSubType not found
     * @throws InvalidParameterException Invalid refSubTypeKey
     * @throws MissingParameterException Missing refSubTypeKey
     * @throws OperationFailedException Unable to complete request
     */
    public List<String> getRefStatementRelationTypesForRefObjectSubType(@WebParam(name="refSubTypeKey")String refSubTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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

    /**
     * Retrieves a view of a statement by its identifier with its referenced statements and requirement components expanded
     * @param statementId statement identifier
     * @return view of statement information with the referenced statements and requirement components expanded
     * @throws DoesNotExistException statement not found
     * @throws InvalidParameterException invalid statementId
     * @throws MissingParameterException statementId not specified
     * @throws OperationFailedException unable to complete request
     */
    public StatementTreeViewInfo getStatementTreeView(@WebParam(name="statementId")String statementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /**
     * Updates an entire Statement Tree. Fails unless everything can be done. Updates Statements, RequirementComponents and any relations between them. If there are "deletes", the relations are removed, but the object is not deleted unless used no where else
     * @param statementId identifier of the statement to be updated
     * @param statementTreeViewInfo The StatementTreeInfo to be updated
     * @return the updated StatementTree information
     * @throws CircularRelationshipException    included statement references the current statement
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Statement not found
     * @throws InvalidParameterException invalid statementId, statementTreeViewInfo
     * @throws MissingParameterException missing statementId, statementTreeViewInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public StatementTreeViewInfo updateStatementTreeView(@WebParam(name="statementId")String statementId, @WebParam(name="statementTreeViewInfo")StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;
}
