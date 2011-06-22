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

package org.kuali.student.enrollment.hold.service;

import java.util.List;

import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
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
    public Boolean isPersonRestricted(String restrictionKey, String personId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().isPersonRestricted(restrictionKey, personId, context);
    }

    @Override
    public List<String> getRestrictedPersons(String restrictionKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getRestrictedPersons(restrictionKey, context);
    }

    @Override
    public List<HoldInfo> getHoldsByRestrictionForPerson(String restrictionKey, String personId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsByRestrictionForPerson(restrictionKey, personId, context);
    }
		
    @Override					 
    public List<HoldInfo> getActiveHoldsByRestrictionForPerson(String restrictionKey, String personId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getActiveHoldsByRestrictionForPerson(restrictionKey, personId, context);
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
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getHoldService().getProcessByKey(processKey, context);
    }
    
    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getHoldService().getProcessByObjectType(refObjectUri, context);
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
    public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsByIssue(issueId, context);
    }

    @Override
    public List<HoldInfo> getHoldsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsForPerson(personId, context);
    }

    @Override
    public List<HoldInfo> getActiveHoldsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getActiveHoldsForPerson(personId, context);
    }

    @Override
    public List<HoldInfo> getHoldsByIssueForPerson(String issueId, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getHoldsByIssueForPerson(issueId, personId, context);
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueForPerson(String issueId, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getActiveHoldsByIssueForPerson(issueId, personId, context);
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().validateHold(validationTypeKey, holdInfo, context);
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
    public HoldInfo releaseHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().releaseHold(holdId, context);
    }

    @Override
    public StatusInfo deleteHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().deleteHold(holdId, context);
    }

    @Override
    public IssueInfo getIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getIssue(issueId, context);
    }

    @Override
    public List<IssueInfo> getIssuesByIdList(List<String> issueIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getIssuesByIdList(issueIdList, context);
    }

    @Override
    public List<String> getIssueIdsByType(String issueTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getIssueIdsByType(issueTypeKey, context);
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getIssuesByOrg(organizationId, context);
    }

    @Override
    public List<IssueInfo> getIssuesByRestriction(String restrictionKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getIssuesByRestriction(restrictionKey, context);
    }

    @Override
    public StatusInfo addIssueToRestriction(String restrictionKey, String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().addIssueToRestriction(restrictionKey, issueId, context);
    }

    @Override
    public StatusInfo removeIssueFromRestriction(String restrictionKey, String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().removeIssueFromRestriction(restrictionKey, issueId, context);
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey, IssueInfo issueInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().validateIssue(validationTypeKey, issueInfo, context);
    }

    @Override
    public IssueInfo createIssue(IssueInfo issueInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().createIssue(issueInfo, context);
    }

    @Override
    public IssueInfo updateIssue(String issueId, IssueInfo issueInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
	return getHoldService().updateIssue(issueId, issueInfo, context);
    }

    @Override
    public StatusInfo deleteIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().deleteIssue(issueId, context);
    }

    @Override
    public RestrictionInfo getRestriction(String restrictionKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getRestriction(restrictionKey, context);
    }

    @Override
    public List<RestrictionInfo> getRestrictionsByKeyList(List<String> restrictionKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getRestrictionsByKeyList(restrictionKeyList, context);
    }

    @Override
    public List<String> getRestrictionKeysByType(String restrictionTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getRestrictionKeysByType(restrictionTypeKey, context);
    }

    @Override
    public List<RestrictionInfo> getRestrictionsByIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().getRestrictionsByIssue(issueId, context);
    }

    @Override
    public List<ValidationResultInfo> validateRestriction(String validationTypeKey, RestrictionInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return getHoldService().validateRestriction(validationTypeKey, restrictionInfo, context);
    }

    @Override
    public RestrictionInfo createRestriction(String restrictionKey, RestrictionInfo restrictionInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().createRestriction(restrictionKey, restrictionInfo, context);
    }

    @Override
    public RestrictionInfo updateRestriction(String restrictionKey, RestrictionInfo restrictionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
	return getHoldService().updateRestriction(restrictionKey, restrictionInfo, context);
    }

    @Override
    public StatusInfo deleteRestriction(String restrictionKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return getHoldService().deleteRestriction(restrictionKey, context);
    }
}
