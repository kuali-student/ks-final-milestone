/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.common.dto.TypeTypeRelationInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;

/**
 * Provides a read-only view of types and type type relations.
 *
 * This service needs to be implemented by any KS service that is
 * going to handle types
 * 
 * Version: 1.0 (Dev)
 * 
 * @author kamal
 */
@WebService(name = "TypeService", targetNamespace = "http://student.kuali.org/wsdl/type")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TypeService {

    /**
     * This method returns the TypeInfo associated for a given type key
     * 
     * @param typeKey Key of the type
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return Information about the Type
     * @throws DoesNotExistException  typeKey not found
     * @throws InvalidParameterException invalid typeKey
     * @throws MissingParameterException missing typeKey
     * @throws OperationFailedException unable to complete request
     */
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of TypeInfo that belong to a RefObjectURI. For e.g all types for CluInfo
     * 
     * @param refObjectURI URI identifying the object e.g http://student.kuali.org/LuService/CluInfo
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return List of TypeInfo objects associated with the object
     * @throws DoesNotExistException refObjectURI not found
     * @throws InvalidParameterException invalid refObjectURI
     * @throws MissingParameterException missing refObjectURI
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of TypeInfo objects that are allowed for another typeKey. This is a convenience method to
     * retrieve TypeTypeRelation with allowed relation type. This will retrieve all the type keys associated with the
     * ObjectURI of the related type. The relationship is captured unidirectionally from ownerType to relatedType.
     * 
     * @param ownerTypeKey Type key of the owner in the relation
     * @param relatedRefObjectURI RefObjectURI of the related type.
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return list of types
     * @throws DoesNotExistException ownerTypeKey or relatedRefObjectURI not found
     * @throws InvalidParameterException invalid ownerTypeKey or relatedRefObjectURI
     * @throws MissingParameterException missing ownerTypeKey or relatedRefObjectURI
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    
    /**
     * 
     * This method retrieves all the TypeTypeRelation objects for a given ownerType and the relationType
     * 
     * @param ownerTypeKey Type key of the owner in the relation
     * @param relationTypeKey Type key of the relation 
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return List of TypeTypeRelations for a given ownerType
     * @throws DoesNotExistException ownerTypeKey or relationTypeKey not found
     * @throws InvalidParameterException invalid ownerTypeKey or relationTypeKey
     * @throws MissingParameterException missing ownerTypeKey or relationTypeKey
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
