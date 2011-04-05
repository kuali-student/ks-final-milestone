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
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.infc.HoldsLprService;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;

/**
 * Wrapper for an lpr services so implementations only have to implement the
 * methods they care about
 * 
 * @author nwright
 */
public class LuiPersonRelationServiceAdapter implements
        LuiPersonRelationService, HoldsLprService {

    private LuiPersonRelationService lprService;

    @Override
    public LuiPersonRelationService getLprService() {
        return lprService;
    }

    @Override
    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    @Override
    public List<ValidationResultInfo> validateLuiPersonRelation(String validationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
    }

    @Override
    public StatusInfo updateRelationState(String luiPersonRelationId, LuiPersonRelationStateInfo relationState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.updateRelationState(luiPersonRelationId, relationState, context);
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return lprService.updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(CriteriaInfo criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.searchForLuiPersonRelationIds(criteria, context);
    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationTypeKey, String relationState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findPersonIdsRelatedToLui(luiId, luiPersonRelationTypeKey, relationState, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelationsForPerson(personId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelationsForLui(luiId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelationsByIdList(luiPersonRelationIdList, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelations(personId, luiId, context);
    }

    @Override
    public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(ContextInfo context) throws OperationFailedException {
        return lprService.findLuiPersonRelationStates(context);
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelationIdsForPerson(personId, context);
    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelationIdsForLui(luiId, context);
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiPersonRelationIds(personId, luiId, context);
    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationTypeKey, String relationState, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findLuiIdsRelatedToPerson(personId, luiPersonRelationTypeKey, relationState, context);
    }

    @Override
    public List<LuiPersonRelationStateInfo> findAllowedRelationStates(String luiPersonRelationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lprService.findAllowedRelationStates(luiPersonRelationTypeKey, context);
    }

    @Override
    public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationTypeKey, String relationState, String atpId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.findAllValidLuisForPerson(personId, luiPersonRelationTypeKey, relationState, atpId, context);
    }

    @Override
    public LuiPersonRelationInfo fetchLUIPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.fetchLUIPersonRelation(luiPersonRelationId, context);
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.deleteLuiPersonRelation(luiPersonRelationId, context);
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
      throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.createLuiPersonRelation(personId, luiId, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprService.createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId,
      List<String> personIdList,
      String relationState, String luiPersonRelationTypeKey,
      LuiPersonRelationInfo luiPersonRelationInfo,
      ContextInfo context)
      throws DataValidationErrorException,
      AlreadyExistsException,
      DoesNotExistException,
      DisabledIdentifierException,
      ReadOnlyException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException,
      PermissionDeniedException {
        return lprService.createBulkRelationshipsForLui(luiId, personIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
      throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return lprService.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return lprService.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lprService.getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lprService.getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lprService.getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lprService.getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }
}

