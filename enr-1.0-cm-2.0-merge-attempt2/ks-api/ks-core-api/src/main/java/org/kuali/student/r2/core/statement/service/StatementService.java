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

package org.kuali.student.r2.core.statement.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r1.common.search.service.SearchService;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;


/**
 * Statement Service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@WebService(name = "StatementService", targetNamespace = org.kuali.student.r2.core.constants.StatementServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StatementService extends SearchService {

    /**
     * Retrieves the list of base types which can be connected to a document.
     *
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return Object statement relationship information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getRefObjectTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of types for a given base type which can be connected
     * to a document.
     *
     * @param refObjectTypeKey reference Type Identifier
     * @param contextInfo      context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of types for the given base type which can be connected to a
     *         document
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing refObjectTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getRefObjectSubTypes(@WebParam(name = "refObjectTypeKey") ContextInfo refObjectTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a object statement relationship by its identifier.
     *
     * @param refStatementRelationId object statement relationship identifier
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return Object statement relationship information
     * @throws DoesNotExistException     refStatementRelationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing refStatementRelationId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RefStatementRelationInfo getRefStatementRelation(@WebParam(name = "refStatementRelationId") String refStatementRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of object statement relationships for a particular
     * object.
     *
     * @param refStatementRelationIds object statement relationship identifiers
     * @param contextInfo             context information containing the
     *                                principalId and locale information about
     *                                the caller of service operation
     * @return List of object statement relationships
     * @throws DoesNotExistException     a refStatementRelationId in the list
     *                                   was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing refStatementRelationId, a
     *                                   refStatementRelationId in the
     *                                   refStatementRelationIds or contextInfo
     *                                   is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RefStatementRelationInfo> getRefStatementRelationsByIds(@WebParam(name = "refStatementRelationIds") List<String> refStatementRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of object statement relationships for a particular
     * object.
     *
     * @param refStatementRelationTypeKey type of statement relation
     * @param contextInfo                 context information containing the
     *                                    principalId and locale information
     *                                    about the caller of service operation
     * @return List of object statement relationships for a particular object
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing refStatementRelationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RefStatementRelationInfo> getRefStatementRelationsByType(@WebParam(name = "refStatementRelationTypeKey") String refStatementRelationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of object statement relationships for a particular
     * statement.
     *
     * @param statementId Statement identifier
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of object statement relationships for a particular
     *         statement
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementId or contextInfo
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(@WebParam(name = "statementId") String statementId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a refStatementRelation. Depending on the value of
     * validationTypeKey, this validation could be limited to tests on just the
     * current object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the relationship (and/or one of its contained sub-objects) and a record
     * is found for that identifier, the validation checks if the relationship
     * can be shifted to the new values. If an identifier is not present or a
     * record cannot be found for the identifier, it is assumed that the record
     * does not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationTypeKey to
     * the current object.
     *
     * @param validationTypeKey           identifier of the extent of
     *                                    validation
     * @param statementId                 statement identifier
     * @param refStatementRelationTypeKey type of statement relation
     * @param refStatementRelationInfo    object statement relationship
     *                                    information to be validated
     * @param contextInfo                 context information containing the
     *                                    principalId and locale information
     *                                    about the caller of service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException     statementId or refStatementRelationId
     *                                   not found
     * @throws InvalidParameterException invalid refStatementRelationInfo or
     *                                   contextInfo
     * @throws MissingParameterException missing validationTypeKey, statementId,
     *                                   refStatementRelationId, refStatementRelationTypeKey,
     *                                   refStatementRelationInfo or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateRefStatementRelation(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "statementId") String statementId,
            @WebParam(name = "refStatementRelationTypeKey") String refStatementRelationTypeKey,
            @WebParam(name = "refStatementRelationInfo") RefStatementRelationInfo refStatementRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a relationship between a statement and an object.
     *
     * @param refObjectTypeKey         Unique identifier for an object type. 
     * @param refObjectId              Identifier to the object "anchor" that is associated with this statement
     * @param statementId              statement identifier
     * @param refStatmentRelationTypeKey  type key of this statement relation
     * @param refStatementRelationInfo information about the object statement
     *                                 relationship
     * @param contextInfo              context information containing the
     *                                 principalId and locale information about
     *                                 the caller of service operation
     * @return New object statement relationship
     * @throws AlreadyExistsException       connection between object and
     *                                      statement already exists
     * @throws DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        refStatementRelationId, statementId,
     *                                      refStatementRelationTypeKey not
     *                                      found
     * @throws InvalidParameterException    invalid refStatementRelationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing refStatementRelationId,
     *                                      statementId, refStatementRelationTypeKey,
     *                                      refStatementRelationInfo or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public RefStatementRelationInfo createRefStatementRelation(
            @WebParam(name = "refObjectTypeKey") String refObjectTypeKey,
            @WebParam(name = "refObjectId") String refObjectId,
            @WebParam(name = "statementId") String statementId,
            @WebParam(name = "refStatmentRelationTypeKey") String refStatementRelationTypeKey,
            @WebParam(name = "refStatementRelationInfo") RefStatementRelationInfo refStatementRelationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a relationship between an object and statement.
     *
     * @param refStatementRelationId   identifier of the object statement
     *                                 relationship to be updated
     * @param refStatementRelationInfo information about the object statement
     *                                 relationship to be updated
     * @param contextInfo              context information containing the
     *                                 principalId and locale information about
     *                                 the caller of service operation
     * @return updated object statement relationship information
     * @throws DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        refStatementRelationId not found
     * @throws InvalidParameterException    invalid refStatementRelationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing refStatementRelationId,
     *                                      refStatementRelationInfo or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     the action was attempted on an out
     *                                      of date version.
     */
    public RefStatementRelationInfo updateRefStatementRelation(@WebParam(name = "refStatementRelationId") String refStatementRelationId, @WebParam(name = "refStatementRelationInfo") RefStatementRelationInfo refStatementRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Removes a relationship between a statement and an object.
     *
     * @param refStatementRelationId object Statement Relationship identifier
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     refStatementRelationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing refStatementRelationId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRefStatementRelation(@WebParam(name = "refStatementRelationId") String refStatementRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about the specified natural language usage type.
     *
     * @param nlUsageTypeKey natural language usage type identifier
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Information about a type of natural language usage
     * @throws DoesNotExistException     nlUsageTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing nlUsageTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getNaturalLanguageUsageByType(@WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Translates and retrieves a statement for a specific usage type
     * (context) and language into natural language.
     * 
     * If <code>language</code> is null default language is used.</p> <p/> <p>An
     * <code>StatementInfo</code> can either have a list of
     * <code>StatementInfo</code>s as children or a list of
     * <code>ReqComponentInfo</code>s but not both. This means that all leaf
     * nodes must be <code>ReqComponentInfo</code>s.</p>
     *
     * @param statementId    identifier of the statement to be translated
     * @param nlUsageTypeKey natural language usage type key (context)
     * @param language       translation language
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Natural language translation for a specific usage type and
     *         language
     * @throws DoesNotExistException     statementId or nlUsageTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid language or contextInfo
     * @throws MissingParameterException missing statementId, nlUsageTypeKey,
     *                                   language or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String getNaturalLanguageForStatement(@WebParam(name = "statementId") String statementId,
            @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, 
            @WebParam(name = "language") String language, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the natural language translation for a particular object
     * statement relationship in a particular context for a particular
     * language.
     *
     * @param refStatementRelationId object statement relationship identifier
     * @param nlUsageTypeKey         context for the natural language
     *                               translation
     * @param language               language to use for the natural language
     *                               translation
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return Natural language translation for a particular object statement
     *         relationship in a particular context
     * @throws DoesNotExistException     refStatementRelationId or nlUsageTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid language or contextInfo
     * @throws MissingParameterException missing refStatementRelationId,
     *                                   nlUsageTypeKey, language or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String getNaturalLanguageForRefStatementRelation(@WebParam(name = "refStatementRelationId") String refStatementRelationId,
            @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey,
            @WebParam(name = "language") String language, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * <p>Translates and retrieves a requirement component for a specific usuage
     * type (context) and language into natural language.</p> <p/> <p>If
     * <code>language</code> is null, default language is used.</p>
     *
     * @param reqComponentId requirement component to translate
     * @param nlUsageTypeKey natural language usage type key (context)
     * @param language       language to use for the natural language
     *                       translation
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Natural language translation for a particular object statement
     *         relationship in a particular context
     * @throws DoesNotExistException     reqComponentId or nlUsageTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid language or contextInfo
     * @throws MissingParameterException missing reqComponentId, nlUsageTypeKey,
     *                                   language or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String getNaturalLanguageForReqComponent(@WebParam(name = "reqComponentId") String reqComponentId, 
            @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, 
            @WebParam(name = "language") String language, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Translates a statement tree view to natural language for a particular
     * context in a particular language. This may include statements and/or
     * requirement components which have not yet been persisted to the service.
     *
     * @param statementTreeViewInfo statement tree view
     * @param nlUsageTypeKey        context for the natural language
     *                              translation
     * @param language              language to use for the natural language
     *                              translation
     * @param contextInfo           context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return Natural language translation for a particular statement in a
     *         particular context
     * @throws DoesNotExistException     nlUsageTypeKey not found
     * @throws InvalidParameterException invalid statementTreeViewInfo, language
     *                                   or contextInfo
     * @throws MissingParameterException missing statementTreeViewInfo,
     *                                   nlUsageTypeKey, language or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String translateStatementTreeViewToNL(@WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, @WebParam(name = "language") String language, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Translates a particular requirement component to natural language for a
     * particular context in a given language. This may be used for requirement
     * components which have not yet been persisted through the service.
     *
     * @param reqComponentInfo requirement component
     * @param nlUsageTypeKey   context for the natural language translation
     * @param language         language to use for the natural language
     *                         translation
     * @param contextInfo      context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return Natural language translation for a particular requirement
     *         component in a particular context
     * @throws DoesNotExistException     nlUsageTypeKey not found
     * @throws InvalidParameterException invalid reqComponentInfo, language or
     *                                   contextInfo
     * @throws MissingParameterException missing reqComponentInfo, nlUsageTypeKey,
     *                                   language or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String translateReqComponentToNL(@WebParam(name = "reqComponentInfo") ReqComponentInfo reqComponentInfo, @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, @WebParam(name = "language") String language, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a statement by its identifier
     *
     * @param statementId statement identifier
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return statementInfo                statement information
     * @throws DoesNotExistException     statementId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatementInfo getStatement(@WebParam(name = "statementId") String statementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of statements that use a particular requirement
     * component. Note: The reference may not be direct, but through an
     * intermediate object definition (ex. nested statements).
     *
     * @param reqComponentId requirement component identifier
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return List of statements using the specified requirement component
     * @throws DoesNotExistException     reqComponentId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing reqComponentId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementInfo> getStatementsByReqComponent(
            @WebParam(name = "reqComponentId") String reqComponentId, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of statements of a particular Type
     *
     * @param statementTypeKey statement type identifier
     * @param contextInfo      context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of statements using the specified type
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementInfo> getStatementsByType(@WebParam(name = "statementTypeKey") String statementTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of child statements that include a particular statement.
     * Note: The reference may not be direct, but through an intermediate object
     * definition (e.g. nested statements).
     *
     * @param statementId statement identifier
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of child statements using the specified statement
     * @throws DoesNotExistException     statementId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementInfo> getStatementsForStatement(@WebParam(name = "statementId") String statementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of all types of statements.
     *
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of types of statements
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getStatementTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the list of statement types which are allowed to be used in a
     * statement type. This controls the nesting of statements.
     *
     * @param statementTypeKey Identifier for a type of statement
     * @param contextInfo      context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return List of statement type info
     * @throws DoesNotExistException     statementTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getStatementTypesForStatementType(@WebParam(name = "statementTypeKey") String statementTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of statement types which are allowed to be used for a
     * specified type of object statement relationship.
     *
     * @param refStatementRelationTypeKey identifier for a type of object
     *                                    statement relationship
     * @param contextInfo                 context information containing the
     *                                    principalId and locale information
     *                                    about the caller of service operation
     * @return List of statement type info
     * @throws DoesNotExistException     refStatementRelationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing refStatementRelationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getStatementTypesForRefStatementRelationType(@WebParam(name = "refStatementRelationTypeKey") String refStatementRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of type infos for object statement relationships which
     * are allowed to be used for a subtype of object.
     *
     * @param refSubTypeKey identifier for the subtype of object
     * @param contextInfo   context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return List of type infos for object statement relationships which are
     *         allowed to be used for a subtype of object
     * @throws DoesNotExistException     refSubTypeKey not found
     * @throws InvalidParameterException invalid refSubTypeKey
     * @throws MissingParameterException missing refSubTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getRefStatementRelationTypesForRefObjectSubType(@WebParam(name = "refSubTypeKey") String refSubTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a statement. Depending on the value of validationTypeKey, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the statement (and/or one
     * of its contained sub-objects) and a record is found for that identifier,
     * the validation checks if the statement can be shifted to the new values.
     * If an identifier is not present or a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking those
     * performed by setting the validationTypeKey to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param statementTypeKey  identifier for the statement Type to be
     *                          validated
     * @param statementInfo     statement information to be validated
     * @param contextInfo       context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException     validationTypeKey or statementTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid statementInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey, statementTypeKey,
     *                                   statementInfo or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateStatement(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "statementTypeKey") String statementTypeKey, @WebParam(name = "statementInfo") StatementInfo statementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Create a statement.
     *
     * @param statementTypeKey identifier of the type of statement
     * @param statementInfo    information about the statement
     * @param contextInfo      context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return Information about the newly created statement
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        statementTypeKey not found
     * @throws InvalidParameterException    invalid statementInfo or contextInfo
     * @throws MissingParameterException    missing statementTypeKey,
     *                                      statementInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public StatementInfo createStatement(@WebParam(name = "statementTypeKey") String statementTypeKey, @WebParam(name = "statementInfo") StatementInfo statementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a statement
     *
     * @param statementId   identifier of the statement to be updated
     * @param statementInfo information about the statement to be updated
     * @param contextInfo   context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return the updated statement information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        statementId not found
     * @throws InvalidParameterException    invalid statementInfo or contextInfo
     * @throws MissingParameterException    missing statementId, statementInfo
     *                                      or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public StatementInfo updateStatement(@WebParam(name = "statementId") String statementId, @WebParam(name = "statementInfo") StatementInfo statementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes a statement
     *
     * @param statementId identifier of the Statement to delete
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return statusInfo status of the operation (success or failure)
     * @throws DoesNotExistException     statementId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteStatement(@WebParam(name = "statementId") String statementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of ReqCompField types which are allowed to be used in
     * an ReqComponent type
     *
     * @param reqComponentTypeKey identifier for a type of requirement
     *                            component
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return A list of required components
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing reqComponentTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getReqCompFieldTypesForReqComponentType(@WebParam(name = "reqComponentTypeKey") String reqComponentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a requirement component by its identifier
     *
     * @param reqComponentId required component identifier
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return Required component information
     * @throws DoesNotExistException     reqComponentId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing reqComponentId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ReqComponentInfo getReqComponent(@WebParam(name = "reqComponentId") String reqComponentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of requirement components of a particular type.
     *
     * @param reqComponentTypeKey identifier for a type of requirement
     *                            component
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return A list of required components
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing reqComponentTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ReqComponentInfo> getReqComponentsByType(@WebParam(name = "reqComponentTypeKey") String reqComponentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves type information for a specified ReqComponent Type
     *
     * @param reqComponentTypeKey reqComponent type key
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return Requirement component type information
     * @throws DoesNotExistException     reqComponentTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing reqComponentTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getReqComponentType(@WebParam(name = "reqComponentTypeKey") String reqComponentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of all types of ReqComponent.
     *
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of types of ReqComponent
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getReqComponentTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of types of requirement components which are allowed
     * to be used in a type of statement.
     *
     * @param statementTypeKey identifier for a type of statement
     * @return list of types of requirement components
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getReqComponentTypesForStatementType(@WebParam(name = "statementTypeKey") String statementTypeKey) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Validates a ReqComponent. Depending on the value of validationTypeKey,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the organization
     * (and/or one of its contained sub-objects) and a record is found for that
     * identifier, the validation checks if the organization can be shifted to
     * the new values. If an identifier is not present or a record cannot be
     * found for the identifier, it is assumed that the record does not exist
     * and as such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationTypeKey to the current
     * object.
     *
     * @param validationTypeKey   identifier of the extent of validation
     * @param reqComponentTypeKey identifier for the ReqComponent Type to be
     *                            validated
     * @param reqComponentInfo    reqComponent information to be tested
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey or reqComponentTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid reqComponentInfo or
     *                                   contextInfo
     * @throws MissingParameterException missing validationTypeKey, reqComponentTypeKey,
     *                                   reqComponentInfo or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateReqComponent(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "reqComponentTypeKey") String reqComponentTypeKey, @WebParam(name = "reqComponentInfo") ReqComponentInfo reqComponentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a requirement component.
     *
     * @param reqComponentTypeKey identifier of the type of requirement
     *                            component
     * @param reqComponentInfo    information about the requirement component
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return information about the newly created requirement component
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        reqComponentTypeKey not found
     * @throws InvalidParameterException    invalid reqComponentInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing reqComponentTypeKey,
     *                                      reqComponentInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public ReqComponentInfo createReqComponent(@WebParam(name = "reqComponentTypeKey") String reqComponentTypeKey, @WebParam(name = "reqComponentInfo") ReqComponentInfo reqComponentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;


    /**
     * Updates a requirement component
     *
     * @param reqComponentId   identifier of the requirement component to be
     *                         updated
     * @param reqComponentInfo information about the requirement component to be
     *                         updated
     * @param contextInfo      context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return the updated requirement component information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        reqComponentId not found
     * @throws InvalidParameterException    invalid reqComponentInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing reqComponentId, reqComponentInfo
     *                                      or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public ReqComponentInfo updateReqComponent(@WebParam(name = "reqComponentId") String reqComponentId, @WebParam(name = "reqComponentInfo") ReqComponentInfo reqComponentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes a requirement component
     *
     * @param reqComponentId identifier of the requirement component to delete
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     reqComponentId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing reqComponentId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteReqComponent(@WebParam(name = "reqComponentId") String reqComponentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a view of a statement by its identifier with its referenced
     * statements and requirement components expanded
     *
     * @param statementId statement identifier
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return view of statement information with the referenced statements and
     *         requirement components expanded
     * @throws DoesNotExistException     statementId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatementTreeViewInfo getStatementTreeView(@WebParam(name = "statementId") String statementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a view of a statement by its identifier with its referenced
     * statements and requirement components expanded and translated
     *
     * @param statementId    statement identifier
     * @param nlUsageTypeKey natural language usage type identifier
     * @param language       Translation language
     * @param contextInfo    context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return view of statement information with the referenced statements and
     *         requirement components expanded
     * @throws DoesNotExistException     statementId or nlUsageTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid language or contextInfo
     * @throws MissingParameterException missing statementId, nlUsageTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatementTreeViewInfo getStatementTreeViewForNlUsageType(final String statementId, final String nlUsageTypeKey, String language, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates an entire Statement Tree. Fails unless everything can be done.
     * Updates Statements, RequirementComponents and any relations between them.
     * If there are "deletes", the relations are removed, but the object is not
     * deleted unless used nowhere else
     *
     * @param statementTreeViewInfo view of statement information with the
     *                              referenced statements and requirement
     *                              components expanded
     * @param contextInfo           context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return Statement tree view
     * @throws DataValidationErrorException supplied data is invalid
     * @throws InvalidParameterException    invalid statementTreeViewInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing statementTreeViewInfo or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     */
    public StatementTreeViewInfo createStatementTreeView(@WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an entire Statement Tree. Fails unless everything can be done.
     * Updates Statements, RequirementComponents and any relations between them.
     * If there are "deletes", the relations are removed, but the object is not
     * deleted unless used no where else
     *
     * @param statementId           identifier of the statement to be updated
     * @param statementTreeViewInfo he StatementTreeInfo to be updated
     * @param contextInfo           context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return The updated StatementTree information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        statementId not found
     * @throws InvalidParameterException    invalid statementTreeViewInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing statementId, statementTreeViewInfo
     *                                      or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public StatementTreeViewInfo updateStatementTreeView(@WebParam(name = "statementId") String statementId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes the entire statement tree
     *
     * @param statementId identifier of the statement to be deleted
     * @param contextInfo context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return Status of the operation (success or failure)
     * @throws DoesNotExistException     statementId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing statementId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteStatementTreeView(@WebParam(name = "statementId") String statementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @Deprecated
	public String getNaturalLanguageForReqComponent(String reqComponentId,
			String string, String string2);
    
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
    @Deprecated
    public List<RefStatementRelationInfo> getRefStatementRelationsByRef(@WebParam(name="refObjectTypeKey")String refObjectTypeKey, @WebParam(name="refObjectId")String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}
