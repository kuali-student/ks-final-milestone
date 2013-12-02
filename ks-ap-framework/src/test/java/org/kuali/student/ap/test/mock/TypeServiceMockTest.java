package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class TypeServiceMockTest implements TypeService {
    /**
     * Gets the Type identified by a type Key.
     *
     * @param typeKey     Key of the type
     * @param contextInfo Context information containing the principalId
     *                    and locale information about the caller of service
     *                    operation
     * @return the type requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of Types from a list of keys. The returned
     * list may be in any order and if duplicate Ids are supplied, a
     * unique set may or may not be returned.
     *
     * @param typeKeys    a list of Type keys
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of Types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a typeKey in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeKeys, a key in typeKeys,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getTypesByKeys(@WebParam(name = "typeKeys") List<String> typeKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a list of refObjectURI's that are known to this implementation of the service.
     * <p/>
     * A RefObjectURI is how objects are uniquely identified in Kuali STudent.
     * A RefObjectURI is composed of two parts the name space of the service
     * in which the object is defined and then the simple java name (no class path)
     * of the object itself.
     * <p/>
     * For example: http://student.kuali.org/wsdl/cluService/CluInfo
     *
     * @param contextInfo Context information containing the principalId
     *                    and locale information about the caller of service
     *                    operation
     * @return a list of string refObjectURIs
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getRefObjectUris(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns a list of TypeInfo that belong to a
     * RefObjectUri. For e.g all types for CluInfo
     *
     * @param refObjectUri a URI identifying the object e.g http://student.kuali.org/wsdl/cluService/CluInfo
     * @param contextInfo  Context information containing the principalId
     *                     and locale information about the caller of service
     *                     operation
     * @return a list of TypeInfo objects associated with the object
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          refObjectURI not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectURI or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getTypesByRefObjectUri(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the list of types that are in the group defined by this group type.
     * <p/>
     * This is a convenience method to retrieve TypeTypeRelations with a grouping
     * relation filtering them to see if they are active and then fetching those
     * types.
     * <p/>
     * Active is defined two ways: the state must be ACTIVE and today must be
     * between the effective and expiration dates of the relationship.
     * <p/>
     * Note: The type itself may or may not be active but since that is returned
     * consuming program is free to filter on that object.
     * <p/>
     * Should be functionally equivalent to calling the following:
     * relations = getTypeTypeRelationsForOwnerAndType (type, GROUP)
     * filterRelations (type=ACTIVE and today between effective and
     * expiration return getTypes (filteredRelations)
     * <p/>
     * The relationship is captured unidirectionally from ownerType to relatedType.
     *
     * @param groupTypeKey typeKey used to do the grouping
     * @param contextInfo  Context information containing the principalId
     *                     and locale information about the caller of service
     *                     operation
     * @return a list of TypeInfo objects associated with the object
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          refObjectURI not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectURI or contextInfo
     *          is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getTypesForGroupType(@WebParam(name = "groupTypeKey") String groupTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the list of Types that are allowed for another typeKey.
     * <p/>
     * This is a convenience method to retrieve TypeTypeRelations with allowed
     * relation filtering them to see if they are active and then fetching those
     * types.
     * <p/>
     * Active is defined two ways: the state must be ACTIVE and today must be
     * between the effective and expiration dates of the relationship.
     * <p/>
     * Note: The type itself may or may not be active but since that is returned
     * consuming program is free to filter on that object.
     * <p/>
     * Should be functionally equivalent to calling the following:
     * relations = getTypeTypeRelationsForOwnerAndType (type, ALLOWED)
     * filterRelations (type=ACTIVE and today between effective and expiration
     * return getTypes (filteredRelations)
     * <p/>
     * The relationship is captured unidirectionally from ownerType to relatedType.
     *
     * @param ownerTypeKey Type key of the owner in the relation
     * @param contextInfo  Context information containing the principalId
     *                     and locale information about the caller of service
     *                     operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          ownerTypeKey
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          ownerTypeKey, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @param typeInfo          the identifier for the Type to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          typeInfo or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, typeInfo,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<ValidationResultInfo> validateType(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Type. The Type Key and Meta information may not
     * be set in the supplied data object.
     *
     * @param typeKey     a unique for the new Type
     * @param typeInfo    the data with which to create the Type
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the new Type
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          typeKey already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          typeInfo or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeKey, typeInfo, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public TypeInfo createType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing Type. The Type Key and Meta information may
     * not be changed.
     *
     * @param typeKey     the identifier for the Type to be updated
     * @param typeInfo    the new data for the Type
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the updated Type
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          typeInfo or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeKey, typeInfo, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure
     *          or the action was attempted on an out of date version
     */
    @Override
    public TypeInfo updateType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "typeInfo") TypeInfo typeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing Type.
     *
     * @param typeKey     the identifier for the Type to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo deleteType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the TypeTypeRelation identified by TypeTypeRelation Id.
     *
     * @param typeTypeRelationId an identifier of the TypeTypeRelation
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return the type type relation requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeTypeRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeTypeRelationId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public TypeTypeRelationInfo getTypeTypeRelation(@WebParam(name = "typeTypeRelationId") String typeTypeRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of TypeTypeRelations from a list of Idss. The
     * returned list may be in any order and if duplicate Ids are
     * supplied, a unique set may or may not be returned.
     *
     * @param typeTypeRelationIds a list of TypeTypeRelation Ids
     * @param contextInfo         information containing the principalId and
     *                            locale information about the caller of service operation
     * @return a list of TypeTypeRelations
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          a typeTypeRelationId in the list
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeTypeRelationIds, a id
     *          in typeTypeRelationIds, or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByIds(@WebParam(name = "typeTypeRelationIds") List<String> typeTypeRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method retrieves all the TypeTypeRelation objects for a
     * given ownerType and the TypeTypeRelationType.
     * <p/>
     * This is the reverse of getTypeTypeRelationsByRelatedTypeAndType.
     *
     * @param ownerTypeKey            Type key of the owner in the relation
     * @param typeTypeRelationTypeKey the identifier for the Type of
     *                                the TypeTypeRelation
     * @param contextInfo             Context information containing the principalId
     *                                and locale information about the caller of service
     *                                operation
     * @return List of TypeTypeRelations for a given ownerType
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          ownerTypeKey or
     *          typeTypeRelationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid ownerTypeKey or
     *          typeTypeRelationTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing ownerTypeKey,
     *          typeTypeRelationTypeKey, or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerAndType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method retrieves all the TypeTypeRelation objects for a
     * given relatedType and the TypeTypeRelationType.
     * <p/>
     * This is the reverse of getTypeTypeRelationsByOwnerAndType.
     *
     * @param relatedTypeKey          Type key of the related type in the relation
     * @param typeTypeRelationTypeKey the identifier for the Type of
     *                                the TypeTypeRelation
     * @param contextInfo             Context information containing the principalId
     *                                and locale information about the caller of service
     *                                operation
     * @return List of TypeTypeRelations for a given ownerType
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          ownerTypeKey or
     *          typeTypeRelationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid ownerTypeKey or
     *          typeTypeRelationTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing ownerTypeKey,
     *          typeTypeRelationTypeKey, or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByRelatedTypeAndType(@WebParam(name = "relatedTypeKey") String relatedTypeKey, @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @param validationTypeKey       the identifier for the validation Type
     * @param typeKey                 the identifier for a type
     * @param typePeerKey             the identifier for the type peer
     * @param typeTypeRelationTypeKey the identifier for the TypeTypeRelation Type
     * @param typeTypeRelationInfo    the TypeTypeRelation to be validated
     * @param contextInfo             information containing the principalId and locale
     *                                information about the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, typeKey,
     *          typePeerKey, or typeTypeRelationTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          typeTypeRelationInfo or
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, typeKey,
     *          typePeerKey, typeTypeRelationTypeKey, typeTypeRelationInfo,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateTypeTypeRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "typeKey") String typeKey, @WebParam(name = "typePeerKey") String typePeerKey, @WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new TypeTypeRelation. The TypeTypeRelation Type
     * indicates the type of relation between the two peer Types.
     *
     * @param typeTypeRelationTypeKey the identifier for the new TypeTypeRelation
     * @param ownerTypeKey            the owner of the relationship
     * @param relatedTypeKey          the related type in the relationship
     * @param typeTypeRelationTypeKey the identifier for the Type of
     *                                TypeTypeRelation to be created
     * @param typeTypeRelationInfo    the relationship to be created
     * @param contextInfo             information containing the principalId and locale
     *                                information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeKey, typePeerKey, or
     *          typeTypeRelationTypeKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          typeTypeRelationInfo or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeTypeRelationId, typeKey,
     *          typePeer, typeTypeRelationTypeKey,
     *          typeTypeRelationInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public TypeTypeRelationInfo createTypeTypeRelation(@WebParam(name = "typeTypeRelationTypeKey") String typeTypeRelationTypeKey, @WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedTypeKey") String relatedTypeKey, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a type Milestone Relationship. The TypeTypeRelation Id,
     * Type, Type Keys, and Meta information may not be changed.
     *
     * @param typeTypeRelationId   the identifier for the TypeTypeRelation updated
     * @param typeTypeRelationInfo the new data for the TypeTypeRelation
     * @param contextInfo          information containing the principalId and locale
     *                             information about the caller of service operation
     * @return the updated TypeTypeRelation
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeTypeRelationId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          typeTypeRelationInfo or
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeTypeRelationId,
     *          typeTypeRelationInfo, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          optimistic locking failure or the action
     *          was attempted on an out of date version
     */
    @Override
    public TypeTypeRelationInfo updateTypeTypeRelation(@WebParam(name = "typeTypeRelationId") String typeTypeRelationId, @WebParam(name = "typeTypeRelationInfo") TypeTypeRelationInfo typeTypeRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing TypeTypeRelation.
     *
     * @param typeTypeRelationId the identifier for the TypeTypeRelation
     *                           to be deleted
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service operation
     * @return status of the delete operation. This must always be true.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeTypeRelationId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          typeTypeRelationId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo deleteTypeTypeRelation(@WebParam(name = "typeTypeRelationId") String typeTypeRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Type keys that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of type identifiers matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForTypeKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Types that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of types matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeInfo> searchForTypes(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Type type relation ids that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of type type relation identifiers matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> searchForTypeTypeRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Type Type Relations that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of type type relations matching the criteria
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<TypeTypeRelationInfo> searchForTypeTypeRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
