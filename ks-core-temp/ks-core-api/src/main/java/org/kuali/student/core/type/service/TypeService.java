/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.type.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.type.dto.TypeInfo;
import org.kuali.student.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.core.dto.ContextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.dto.ValidationResultInfo;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.exceptions.VersionMismatchException;

import org.kuali.student.core.util.constants.TypeServiceConstants;

/**
 * Provide lookups of Types and TypeTypRelations as well as operations
 * for the management of the metadata associated with the types.
 * 
 * Version: 1.0 (Dev)
 * 
 * @author kamal
 */
@WebService(name = "TypeService", targetNamespace = TypeServiceConstants.NAMESPACE)
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
     * list may be in any order and if duplicate ids are supplied, a
     * unique set may or may not be returned.
     * 
     * @param atpIds a list of Type keys
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
     * This method returns a list of TypeInfo that belong to a
     * RefObjectURI. For e.g all types for CluInfo
     * 
     * @param refObjectURI URI identifying the object e.g http://student.kuali.org/wsdl/luService/CluInfo
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of TypeInfo objects associated with the object
     * @throws DoesNotExistException refObjectURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectURI or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of TypeInfo objects that are allowed
     * for another typeKey. This is a convenience method to retrieve
     * TypeTypeRelation with allowed relation type. This will retrieve
     * all the type keys associated with the ObjectURI of the related
     * type. The relationship is captured unidirectionally from
     * ownerType to relatedType.
     * 
     * @param ownerTypeKey Type key of the owner in the relation
     * @param relatedRefObjectURI RefObjectURI of the related type.
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return list of types
     * @throws DoesNotExistException ownerTypeKey or relatedRefObjectURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException ownerTypeKey, relatedRefObjectURI, or 
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public TypeInfo createType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * Gets the TypeTypeRelation identified by Key.
     * 
     * @param typeTypeRelationKey Key of the type
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return the type type relation requested
     * @throws DoesNotExistException typeTypeRelationKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeTypeRelationInfo getTypeTypeRelation(@WebParam(name = "typeTypeRelationKey") String typeTypeRelationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of TypeTypeRelations from a list of keys. The
     * returned list may be in any order and if duplicate ids are
     * supplied, a unique set may or may not be returned.
     * 
     * @param atpIds a list of TypeTypeRelation keys
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of TypeTypeRelations
     * @throws DoesNotExistException a typeTypeRelationKey in the list
     *         not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationKeys, a key
     *         in typeTypeRelationKeys, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByKeys(@WebParam(name = "typeTypeRelationKeys") List<String> typeTypeRelationKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method retrieves all the TypeTypeRelation objects for a
     * given ownerType and the relationType
     * 
     * @param ownerTypeKey Type key of the owner in the relation
     * @param relationTypeKey Type key of the relation 
     * @param contextInfo Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return List of TypeTypeRelations for a given ownerType
     * @throws DoesNotExistException ownerTypeKey or relationTypeKey not found
     * @throws InvalidParameterException invalid ownerTypeKey or relationTypeKey
     * @throws MissingParameterException missing ownerTypeKey,
     *         relationTypeKey, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<ValidationResultInfo> validateTypeTypeRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "typeKey") String typeKey, @WebParam(name = "typePeerKey") String typePeerKey, @WebParam(name = "typeTyperelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new TypeTypeRelation. The TypeTypeRelation Type
     * indicates the type of relation between the two peer Types.
     * 
     * @param typeTypeRelationKey the identifier for the new TypeTypeRelation
     * @param typeKey a peer of the relationship
     * @param typePeerKey a peer of the relationship
     * @param typeTypeRelationInfo the relationship to be created
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the new TypeTypeRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException typeKey, typePeerKey, or
     *         typeTypeRelationTypeKey is not found
     * @throws InvalidParameterException typeTypeRelationInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException typeTypeRelationKey, typeKey,
     *         typePeer, typeTypeRelationTypeKey,
     *         typeTypeRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public TypeTypeRelationInfo createTypeTypeRelation(@WebParam(name = "typeTypeRelationKey") String typeTypeRelationKey, @WebParam(name = "typeKey") String typeKey, @WebParam(name = "typePeerKey") String typePeerKey, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a type Milestone Relationship. The TypeTypeRelation Key,
     * Type, Type Keys, and Meta information may not be changed.
     * 
     * @param typeTypeRelationKey the identifier for the TypeTypeRelation updated
     * @param typeTypeRelationInfo the new data for the TypeTypeRelation
     * @param contextInfo information containing the principalId and locale
     *        information about the caller of service operation
     * @return the updated TypeTypeRelation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException typeTypeRelationKey is not found
     * @throws InvalidParameterException typeTypeRelationInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationKey,
     *         typeTypeRelationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException optimistic locking failure or the action
     *         was attempted on an out of date version
     */
    public TypeTypeRelationInfo updateTypeTypeRelation(@WebParam(name = "typeTypeRelationKey") String typeTypeRelationKey, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing TypeTypeRelation.
     * 
     * @param typeTypeRelationKey the identifier for the TypeTypeRelation
     *        to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the delete operation. This must always be true.
     * @throws DoesNotExistException typeTyperelationKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException typeTypeRelationKey or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteTypeTypeRelation(@WebParam(name = "typeTypeRelationKey") String typeTypeRelationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
