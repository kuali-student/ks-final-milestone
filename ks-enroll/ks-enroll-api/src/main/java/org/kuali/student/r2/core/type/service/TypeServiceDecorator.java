/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.r2.core.type.service;

import java.util.List;

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

import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;

/**
 * @author tom
 */
public class TypeServiceDecorator implements TypeService {
    
    private TypeService nextDecorator;

    public TypeService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(TypeService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    public TypeInfo getType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getType(typeKey, contextInfo);
    }

    public List<TypeInfo> getTypesByKeys(List<String> typeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTypesByKeys(typeKeys, contextInfo);
    }

    public List<TypeInfo> getTypesByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTypesByRefObjectUri(refObjectUri, contextInfo);
    }

    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectUri, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectUri, contextInfo);
    }

    public List<ValidationResultInfo> validateType(String validationTypeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateType(validationTypeKey, typeInfo, contextInfo);
    }

    public TypeInfo createType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createType(typeKey, typeInfo, contextInfo);
    }

    public TypeInfo updateType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateType(typeKey, typeInfo, contextInfo);
    }

    public StatusInfo deleteType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteType(typeKey, contextInfo);
    }

    public TypeTypeRelationInfo getTypeTypeRelation(String typeTypeRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTypeTypeRelation(typeTypeRelationId, contextInfo);
    }

    public List<TypeTypeRelationInfo> getTypeTypeRelationsByIds(List<String> typeTypeRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTypeTypeRelationsByIds(typeTypeRelationIds, contextInfo);
    }

    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerType(String ownerTypeKey, String typeTypeRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTypeTypeRelationsByOwnerType(ownerTypeKey, typeTypeRelationTypeKey, contextInfo);
    }

    public List<ValidationResultInfo> validateTypeTypeRelation(String validationTypeKey, String typeKey, String typePeerKey, String typeTypeRelationTypeKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateTypeTypeRelation(validationTypeKey, typeKey, typePeerKey, typeTypeRelationTypeKey, typeTypeRelationInfo, contextInfo);
    }

    public TypeTypeRelationInfo createTypeTypeRelation(String typeKey, String typePeerKey, String typeTypeRelationTypeKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createTypeTypeRelation(typeKey, typePeerKey, typeTypeRelationTypeKey, typeTypeRelationInfo, contextInfo);
    }

    public TypeTypeRelationInfo updateTypeTypeRelation(String typeTypeRelationId, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateTypeTypeRelation(typeTypeRelationId, typeTypeRelationInfo, contextInfo);
    }

    public StatusInfo deleteTypeTypeRelation(String typeTypeRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteTypeTypeRelation(typeTypeRelationId, contextInfo);
    }
}