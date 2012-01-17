/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.type.service;

import java.util.List;
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
import org.kuali.student.core.type.dto.TypeInfo;
import org.kuali.student.core.type.dto.TypeTypeRelationInfo;

/**
 *
 * @author nwright
 */
public class TypeServiceDecorator implements TypeService {
    
    private TypeService nextDecorator;

    public TypeService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(TypeService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateTypeTypeRelation(String validationTypeKey, String typeKey, String typePeerKey, String typeTypeRelationTypeKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateTypeTypeRelation(validationTypeKey, typeKey, typePeerKey, typeTypeRelationTypeKey, typeTypeRelationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateType(String validationTypeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateType(validationTypeKey, typeInfo, contextInfo);
    }

    @Override
    public TypeTypeRelationInfo updateTypeTypeRelation(String typeTypeRelationKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateTypeTypeRelation(typeTypeRelationKey, typeTypeRelationInfo, contextInfo);
    }

    @Override
    public TypeInfo updateType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateType(typeKey, typeInfo, contextInfo);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getTypesByRefObjectURI(refObjectURI, contextInfo);
    }

    @Override
    public List<TypeInfo> getTypesByKeys(List<String> typeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getTypesByKeys(typeKeys, contextInfo);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getTypeTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, contextInfo);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByKeys(List<String> typeTypeRelationKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getTypeTypeRelationsByKeys(typeTypeRelationKeys, contextInfo);
    }

    @Override
    public TypeTypeRelationInfo getTypeTypeRelation(String typeTypeRelationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getTypeTypeRelation(typeTypeRelationKey, contextInfo);
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getType(typeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, contextInfo);
    }

    @Override
    public StatusInfo deleteTypeTypeRelation(String typeTypeRelationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteTypeTypeRelation(typeTypeRelationKey, contextInfo);
    }

    @Override
    public StatusInfo deleteType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteType(typeKey, contextInfo);
    }

    @Override
    public TypeTypeRelationInfo createTypeTypeRelation(String typeTypeRelationKey, String typeKey, String typePeerKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createTypeTypeRelation(typeTypeRelationKey, typeKey, typePeerKey, typeTypeRelationInfo, contextInfo);
    }

    @Override
    public TypeInfo createType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createType(typeKey, typeInfo, contextInfo);
    }
    
}
