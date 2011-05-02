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

package org.kuali.student.enrollment.classI.hold.service;

import java.util.List;

import org.kuali.student.enrollment.classI.hold.dto.HoldInfo;
import org.kuali.student.enrollment.classI.hold.dto.HoldCategoryInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;


public class HoldServiceDecorator implements HoldService {

    private HoldService service;
	
    public HoldService getHoldService() {
        return service;
    }
   
    public void setService(HoldService service) {
        this.service = service;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
	return getHoldService().getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
	return getHoldService().getDataDictionaryEntry(entryKey, context);
    }
    
    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }
    
    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getType(typeKey,  context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<String> getProcessKeys(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getProcessKeys(typeKey, context);
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getState(processKey, stateKey, context); 
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getStatesByProcess(processKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().getNextHappyState(processKey, currentStateKey, context);
    }
    
    @Override
    public HoldInfo getHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHold(holdId, context);
    }

    @Override
    public List<HoldInfo> getHoldsByIdList(List<String> holdIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsByIdList(holdIdList, context);
    }

    @Override
    public List<HoldInfo> getHoldsByCategory(String holdCategoryId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsByCategory(holdCategoryId, context);
    }

    @Override
    public List<HoldInfo> getHoldsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsForPerson(personId, context);
    }

    @Override
    public List<HoldInfo> getHoldsByCategoryForPerson(String holdCategoryId, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsByCategoryForPerson(holdCategoryId, personId, context);
    }

    @Override
    public List<HoldInfo> getActiveHoldsByCategoryForPerson(String holdCategoryId, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getActiveHoldsByCategoryForPerson(holdCategoryId, personId, context);
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationType, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().validateHold(validationType, holdInfo, context);
    }

    @Override
    public HoldInfo createHold(HoldInfo holdInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().createHold(holdInfo, context);
    }

    @Override
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
	return getHoldService().updateHold(holdId, holdInfo, context);
    }

    @Override
    public HoldInfo releaseHold(String holdId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().releaseHold(holdId, context);
    }

    @Override
    public StatusInfo deleteHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().deleteHold(holdId, context);
    }

    @Override
    public HoldCategoryInfo getHoldCategory(String holdCategoryId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldCategory(holdCategoryId, context);
    }

    @Override
    public List<HoldCategoryInfo> getHoldCategoriesByIdList(List<String> holdCategoryIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldCategoriesByIdList(holdCategoryIdList, context);
    }

    @Override
    public List<String> getHoldCategoryIdsByType(String holdCategoryTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldCategoryIdsByType(holdCategoryTypeKey, context);
    }

    @Override
    public List<HoldCategoryInfo> getHoldCategoriesByOrg(String organizationId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldCategoriesByOrg(organizationId, context);
    }

    @Override
    public List<ValidationResultInfo> validateHoldCategory(String validationType, HoldCategoryInfo holdCategoryInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().validateHoldCategory(validationType, holdCategoryInfo, context);
    }

    @Override
    public HoldCategoryInfo createHoldCategory(HoldCategoryInfo holdCategoryInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().createHoldCategory(holdCategoryInfo, context);
    }

    @Override
    public HoldCategoryInfo updateHoldCategory(String holdCategoryId, HoldCategoryInfo holdCategoryInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
	return getHoldService().updateHoldCategory(holdCategoryId, holdCategoryInfo, context);
    }

    @Override
    public StatusInfo deleteHoldCategory(String holdCategoryId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().deleteHoldCategory(holdCategoryId, context);
    }
}
