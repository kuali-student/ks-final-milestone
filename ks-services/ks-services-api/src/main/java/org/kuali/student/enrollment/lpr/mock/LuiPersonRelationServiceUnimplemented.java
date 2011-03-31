/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.mock;

import java.util.List;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.CriteriaInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.common.dto.TypeTypeRelationInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.common.infc.HoldsLprService;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.VersionMismatchException;

/**
 * Used for the bottom layer of a stack to catch and throw an error instead of
 * a null pointer
 * 
 * @author nwright
 */
public class LuiPersonRelationServiceUnimplemented implements
        LuiPersonRelationService {

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public LuiPersonRelationInfo fetchLUIPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationType, String relationState, String atpId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<LuiPersonRelationStateInfo> findAllowedRelationStates(String luiPersonRelationType, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationType, String relationState, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(ContextInfo context) throws OperationFailedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationType, String relationState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(CriteriaInfo criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public StatusInfo updateRelationState(String luiPersonRelationId, LuiPersonRelationStateInfo relationState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<ValidationResultInfo> validateLuiPersonRelation(String validationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Not implemented -- probable configuration error.");
    }
}

