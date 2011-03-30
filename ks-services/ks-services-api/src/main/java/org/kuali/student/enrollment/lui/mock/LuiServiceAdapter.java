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
package org.kuali.student.enrollment.lui.mock;

import java.util.List;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.common.dto.TypeTypeRelationInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.infc.HoldsLuiService;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;

/**
 * Provides a wrapper for the LuiService so that layers that extend this 
 * do not have to implement all of the methods
 *
 * @author nwright
 */
public class LuiServiceAdapter implements LuiService, HoldsLuiService {

    private LuiService luiService;

    @Override
    public LuiService getLuiService() {
        return luiService;
    }

    @Override
    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
      throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return luiService.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return luiService.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.validateLuiLuiRelation(validationType, luiLuiRelationInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.validateLui(validationType, luiInfo, context);
    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return luiService.updateLuiState(luiId, luState, context);
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return luiService.updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return luiService.updateLui(luiId, luiInfo, context);
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationType, ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getRelatedLuisByLuiId(luiId, luLuRelationType, context);
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationType, ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getRelatedLuiIdsByLuiId(luiId, luLuRelationType, context);
    }

    @Override
    public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuisInAtpByCluId(cluId, atpKey, context);
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationType, ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuisByRelation(relatedLuiId, luLuRelationType, context);
    }

    @Override
    public List<LuiInfo> getLuisByIdList(List<String> luiIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuisByIdList(luiIdList, context);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuiLuiRelationsByLui(luiId, context);
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuiLuiRelation(luiLuiRelationId, context);
    }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuiIdsInAtpByCluId(cluId, atpKey, context);
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationType, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuiIdsByRelation(relatedLuiId, luLuRelationType, context);
    }

    @Override
    public List<String> getLuiIdsByCluId(String cluId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLuiIdsByCluId(cluId, context);
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getLui(luiId, context);
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return luiService.deleteLuiLuiRelation(luiLuiRelationId, context);
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return luiService.deleteLui(luiId, context);
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return luiService.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationType, luiLuiRelationInfo, context);
    }

    @Override
    public LuiInfo createLui(String cluId, 
      String atpKey,
      LuiInfo luiInfo,
      ContextInfo context)
      throws AlreadyExistsException,
      DataValidationErrorException,
      DoesNotExistException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException,
      PermissionDeniedException {
        return luiService.createLui(cluId, atpKey, luiInfo, context);
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return luiService.getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }

}
