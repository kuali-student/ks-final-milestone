/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.type.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
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

import org.kuali.student.r2.common.util.constants.TypeServiceConstants;

/**
 * Provide lookups of Types and TypeTypRelations as well as operations
 * for the management of the metadata associated with the types.
 * 
 * Version: 1.0 (Dev)
 * 
 * @author kamal
 */

@WebService(name = "TypeService", serviceName = "TypeService", portName = "TypeService", targetNamespace = TypeServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface TypeService {

    /**
     * Gets the Type identified by a type Key.
     * 
     * @param typeKey Key of the type
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return the type requested
     * @throws DoesNotExistException typeKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Types from a list of keys. The returned
     * list may be in any order and if duplicate Ids are supplied, a
     * unique set may or may not be returned.
     * 
     * @param typeKeys a list of Type keys
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Types
     * @throws DoesNotExistException a typeKey in the list not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeKeys, a key in typeKeys,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTypesByKeys(@WebParam(name = "typeKeys") List<String> typeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get a list of refObjectURI's that are known to this implementation of the service. 
     * 
     * A RefObjectURI is how objects are uniquely identified in Kuali STudent.
     * A RefObjectURI is composed of two parts the name space of the service
     * in which the object is defined and then the simple java name (no class path)
     * of the object itself.
     * 
     * For example: http://student.kuali.org/wsdl/luService/CluInfo
     * 
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of string refObjectURIs
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getRefObjectUris(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of TypeInfo that belong to a
     * RefObjectUri. For e.g all types for CluInfo
     * 
     * @param refObjectUri a URI identifying the object e.g http://student.kuali.org/wsdl/luService/CluInfo
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of TypeInfo objects associated with the object
     * @throws DoesNotExistException refObjectURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectURI or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTypesByRefObjectUri(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Get the list of types that are in the group defined by this group type.
     *
     * This is a convenience method to retrieve TypeTypeRelations with a grouping 
     * relation filtering them to see if they are active and then fetching those 
     * types. 
     * 
     * Active is defined two ways: the state must be ACTIVE and today must be
     * between the effective and expiration dates of the relationship.
     * 
     * Note: The type itself may or may not be active but since that is returned 
     * consuming program is free to filter on that object.
     * 
     * Should be functionally equivalent to calling the following:
     * relations = getTypeTypeRelationsForOwnerAndType (type, GROUP)
     * filterRelations (type=ACTIVE and today between effective and
     * expiration return getTypes (filteredRelations)
     * 
     * The relationship is captured unidirectionally from ownerType to relatedType.
     * 
     * @param groupTypeKey typeKey used to do the grouping
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of TypeInfo objects associated with the object
     * @throws DoesNotExistException refObjectURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectURI or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTypesForGroupType(@WebParam(name = "groupTypeKey") String groupTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the list of Types that are allowed for another typeKey.
     * 
     * This is a convenience method to retrieve TypeTypeRelations with allowed 
     * relation filtering them to see if they are active and then fetching those 
     * types. 
     * 
     * Active is defined two ways: the state must be ACTIVE and today must be
     * between the effective and expiration dates of the relationship.
     * 
     * Note: The type itself may or may not be active but since that is returned 
     * consuming program is free to filter on that object.
     * 
     * Should be functionally equivalent to calling the following:
     * relations = getTypeTypeRelationsForOwnerAndType (type, ALLOWED)
     * filterRelations (type=ACTIVE and today between effective and expiration
     * return getTypes (filteredRelations)
     * 
     * The relationship is captured unidirectionally from ownerType to relatedType.
     * 
     * @param ownerTypeKey Type key of the owner in the relation
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @throws DoesNotExistException ownerTypeKey 
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException ownerTypeKey, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Type. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained sub-objects or expanded to
     * perform all tests related to this Type. If an identifier is
     * present for the Type and a record is found for that identifier,
     * the validation checks if the Type can be shifted to the new
     * values. If a an identifier is not present or a record does not
     * exist, the validation checks if the Type with the given data
     * can be created.
     * 
     * @param validationTypeKey the identifier for the validation Type
     * @param typeInfo the identifier for the Type to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException validationTypeKey is not found
     * @throws InvalidParameterException typeInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, typeInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateType(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Type. The Type Key and Meta information may not
     * be set in the supplied data object.
     * 
     * @param typeKey a unique for the new Type
     * @param typeInfo the data with which to create the Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the new Type
     * @throws AlreadyExistsException typeKey already exists
     * @throws DataValidationErrorException supplied data is invalid
     * @throws InvalidParameterException typeInfo or contextInfo is not valid
     * @throws MissingParameterException typeKey, typeInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public TypeInfo createType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Type. The Type Key and Meta information may
     * not be changed.
     * 
     * @param typeKey the identifier for the Type to be updated
     * @param typeInfo the new data for the Type
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated Type
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException typeKey is not found
     * @throws InvalidParameterException typeInfo or contextInfo is not valid
     * @throws MissingParameterException typeKey, typeInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public TypeInfo updateType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing Type.
     * 
     * @param typeKey the identifier for the Type to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException typeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the TypeTypeRelation identified by TypeTypeRelation Id.
     * 
     * @param typeTypeRelationId an identifier of the TypeTypeRelation
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return the type type relation requested
     * @throws DoesNotExistException typeTypeRelationId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeTypeRelationInfo getTypeTypeRelation(@WebParam(name = "typeTypeRelationId") String typeTypeRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of TypeTypeRelations from a list of Idss. The
     * returned list may be in any order and if duplicate Ids are
     * supplied, a unique set may or may not be returned.
     * 
     * @param typeTypeRelationIds a list of TypeTypeRelation Ids
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of TypeTypeRelations
     * @throws DoesNotExistException a typeTypeRelationId in the list
     *         not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationIds, a id
     *         in typeTypeRelationIds, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByIds(@WebParam(name = "typeTypeRelationIds") List<String> typeTypeRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method retrieves all the TypeTypeRelation objects for a
     * given ownerType and the TypeTypeRelationType.
     * 
     * This is the reverse of getTypeTypeRelationsByRelatedTypeAndType.
     * 
     * @param ownerTypeKey Type key of the owner in the relation
     * @param typeTypeRelationTypeKey the identifier for the Type of
     *        the TypeTypeRelation
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return List of TypeTypeRelations for a given ownerType
     * @throws DoesNotExistException ownerTypeKey or
     *         typeTypeRelationTypeKey not found
     * @throws InvalidParameterException invalid ownerTypeKey or
     *         typeTypeRelationTypeKey
     * @throws MissingParameterException missing ownerTypeKey,
     *         typeTypeRelationTypeKey, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerAndType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

     /**
     * This method retrieves all the TypeTypeRelation objects for a
     * given relatedType and the TypeTypeRelationType.
     * 
     * This is the reverse of getTypeTypeRelationsByOwnerAndType.
     * 
     * @param relatedTypeKey Type key of the related type in the relation
     * @param typeTypeRelationTypeKey the identifier for the Type of
     *        the TypeTypeRelation
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return List of TypeTypeRelations for a given ownerType
     * @throws DoesNotExistException ownerTypeKey or
     *         typeTypeRelationTypeKey not found
     * @throws InvalidParameterException invalid ownerTypeKey or
     *         typeTypeRelationTypeKey
     * @throws MissingParameterException missing ownerTypeKey,
     *         typeTypeRelationTypeKey, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByRelatedTypeAndType(@WebParam(name = "relatedTypeKey") String relatedTypeKey, @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a TypeTypeRelation. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current TypeTypeRelation and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * TypeTypeRelation. If an identifier is present for the
     * TypeTypeRelation (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the TypeTypeRelation can be shifted to the new values. If an
     * identifier is not present or a record cannot be found for the
     * identifier, the validation checks if the object with the given
     * data can be created.
     * 
     * @param validationTypeKey the identifier for the validation Type
     * @param typeKey the identifier for a type
     * @param typePeerKey the identifier for the type peer
     * @param typeTypeRelationTypeKey the identifier for the TypeTypeRelation Type
     * @param typeTypeRelationInfo the TypeTypeRelation to be validated
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException validationTypeKey, typeKey,
     *         typePeerKey, or typeTypeRelationTypeKey is not found
     * @throws InvalidParameterException typeTypeRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, typeKey,
     *         typePeerKey, typeTypeRelationTypeKey, typeTypeRelationInfo,
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateTypeTypeRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "typeKey") String typeKey, @WebParam(name = "typePeerKey") String typePeerKey, @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new TypeTypeRelation. The TypeTypeRelation Type
     * indicates the type of relation between the two peer Types.
     * 
     * @param typeTypeRelationTypeKey the identifier for the new TypeTypeRelation
     * @param ownerTypeKey the owner of the relationship
     * @param relatedTypeKey the related type in the relationship
     * @param typeTypeRelationTypeKey the identifier for the Type of
     *        TypeTypeRelation to be created
     * @param typeTypeRelationInfo the relationship to be created
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException typeKey, typePeerKey, or
     *         typeTypeRelationTypeKey is not found
     * @throws InvalidParameterException typeTypeRelationInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException typeTypeRelationId, typeKey,
     *         typePeer, typeTypeRelationTypeKey,
     *         typeTypeRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */ 
    public TypeTypeRelationInfo createTypeTypeRelation(
            @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, 
            @WebParam(name = "ownerTypeKey") String ownerTypeKey, 
            @WebParam(name = "relatedTypeKey") String relatedTypeKey, 
            @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a type Milestone Relationship. The TypeTypeRelation Id,
     * Type, Type Keys, and Meta information may not be changed.
     * 
     * @param typeTypeRelationId the identifier for the TypeTypeRelation updated
     * @param typeTypeRelationInfo the new data for the TypeTypeRelation
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated TypeTypeRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException typeTypeRelationId is not found
     * @throws InvalidParameterException typeTypeRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationId,
     *         typeTypeRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or the action
     *         was attempted on an out of date version
     */
    public TypeTypeRelationInfo updateTypeTypeRelation(@WebParam(name = "typeTypeRelationId") String typeTypeRelationId, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing TypeTypeRelation.
     * 
     * @param typeTypeRelationId the identifier for the TypeTypeRelation
     *        to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the delete operation. This must always be true.
     * @throws DoesNotExistException typeTypeRelationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteTypeTypeRelation(@WebParam(name = "typeTypeRelationId") String typeTypeRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
