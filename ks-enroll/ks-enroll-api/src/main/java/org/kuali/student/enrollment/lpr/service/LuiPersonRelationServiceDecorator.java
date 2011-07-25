/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * The base decorator for the {@link LuiPersonRelationService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 * 
 * @author nwright
 */
public abstract class LuiPersonRelationServiceDecorator implements LuiPersonRelationService {

    protected LuiPersonRelationService nextDecorator;

    public LuiPersonRelationService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(LuiPersonRelationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (null != nextDecorator) {
            return nextDecorator.updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.searchForLuiPersonRelationIds(criteria, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    @Override
    public List<LuiPersonRelationInfo> searchForLuiPersonRelations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.searchForLuiPersonRelations(criteria, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationTypeKey, String relationState,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findPersonIdsRelatedToLui(luiId, luiPersonRelationTypeKey, relationState, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiPersonRelationsForPerson(personId, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiPersonRelationsForLui(luiId, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiPersonRelationsByIdList(luiPersonRelationIdList, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiPersonRelations(personId, luiId, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiPersonRelationIdsForLui(luiId, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiPersonRelationIds(personId, luiId, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationTypeKey,
            String relationState, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findLuiIdsRelatedToPerson(personId, luiPersonRelationTypeKey, relationState, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationTypeKey,
            String relationState, String atpId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.findAllValidLuisForPerson(personId, luiPersonRelationTypeKey, relationState, atpId,
                    context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.deleteLuiPersonRelation(luiPersonRelationId, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationTypeKey,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.createLuiPersonRelation(personId, luiId, luiPersonRelationTypeKey,
                    luiPersonRelationInfo, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState,
            String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.createBulkRelationshipsForPerson(personId, luiIdList, relationState,
                    luiPersonRelationTypeKey, luiPersonRelationInfo, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState,
            String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.createBulkRelationshipsForLui(luiId, personIdList, relationState,
                    luiPersonRelationTypeKey, luiPersonRelationInfo, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        if (null != nextDecorator) {
            return nextDecorator.getDataDictionaryEntryKeys(context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {

        if (null != nextDecorator) {
            return nextDecorator.getDataDictionaryEntry(entryKey, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        if (null != nextDecorator) {
            return nextDecorator.getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }

    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {

        if (null != nextDecorator) {
            return nextDecorator.getType(typeKey, context);
        } else {
            throw new OperationFailedException("Decorators not properly configured");
        }
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return nextDecorator.getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return nextDecorator.getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getProcessByKey(processKey, context);
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getProcessByObjectType(refObjectUri, context);
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getState(processKey, stateKey, context);
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getStatesByProcess(processKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return nextDecorator.getNextHappyState(processKey, currentStateKey, context);
    }

    @Override
    public LuiPersonRelationInfo fetchLuiPersonRelation(String luiPersonRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.fetchLuiPersonRelation(luiPersonRelationId, context);
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.findLuiPersonRelationIdsForPerson(personId, context);

    }

    @Override
    public List<LuiInfo> getLprRosterById(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLprRosterById(lprRosterId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPersonAndAtp(String personId, String atpId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.findLuiPersonRelationsForPersonAndAtp(personId, atpId, context);
    }

    @Override
    public String createLprRoster(LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.createLprRoster(lprRosterInfo, context);
    }

    @Override
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteLprRoster(lprRosterId, context);
    }

    @Override
    public List<LprRosterEntryInfo> getLprEntriesForLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLprEntriesForLprRoster(lprRosterId, context);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLprRostersByLuiAndRosterType(luiId, lprRosterTypeKey, context);

    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLprRostersByLui(luiId, context);

    }

    @Override
    public List<LuiInfo> getAssociatedLuisForRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getAssociatedLuisForRoster(lprRosterId, context);

    }

    @Override
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntry, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.createLprRosterEntry(lprRosterEntry, context);

    }

    @Override
    public StatusInfo moveLprRosterEntryToNewRoster(String lprRosterEntryId, String newLprRosterId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.moveLprRosterEntryToNewRoster(lprRosterEntryId, newLprRosterId, context);
    }

    @Override
    public StatusInfo removeLprRosterEntryFromLprRoster(String lprRosterEntryId, String newLprRosterId,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.removeLprRosterEntryFromLprRoster(lprRosterEntryId, newLprRosterId, context);
    }

    @Override
    public StatusInfo addLprEntiesToLprRoster(String lprRosterId, List<String> lprRosterEntryIds, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.addLprEntiesToLprRoster(lprRosterId, lprRosterEntryIds, context);
    }

    @Override
    public StatusInfo removeLprEntriesFromLprRoster(String lprRosterId, List<String> lprRosterEntryIds,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.removeLprEntriesFromLprRoster(lprRosterId, lprRosterEntryIds, context);

    }

    @Override
    public StatusInfo removeAllLprEntriesFromLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.removeAllLprEntriesFromLprRoster(lprRosterId, context);
    }

    @Override
    public LPRTransactionInfo createLuiPersonRelationTransaction(LPRTransactionInfo lPRTransactionInfo,
            ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.createLuiPersonRelationTransaction(lPRTransactionInfo, context);
    }

    @Override
    public LPRTransactionInfo createLuiPersonRelationTransactionFromExisting(String luiPersonRelationTransactionId,
            ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.createLuiPersonRelationTransactionFromExisting(luiPersonRelationTransactionId, context);
    }

    @Override
    public LPRTransactionInfo updateLuiPersonRelationTransaction(String lprTransactionId,
            LPRTransactionItemInfo luiPersonRelationRequestInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator
                .updateLuiPersonRelationTransaction(lprTransactionId, luiPersonRelationRequestInfo, context);
    }

    @Override
    public LPRTransactionInfo getLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLuiPersonRelationTransaction(lprTransactionId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByPersonAndLui(String personId, String luiId,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLuiPersonRelationTransactionsByPersonAndLui(personId, luiId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLuiPersonRelationTransactionsByPerson(personId, context);

    }

    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByAtpAndPerson(String atpId, String personId,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLuiPersonRelationTransactionsByAtpAndPerson(atpId, personId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByAtpAndLui(String luiId, String atpId,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLuiPersonRelationTransactionsByAtpAndLui(luiId, atpId, context);
    }

    @Override
    public StatusInfo deleteLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteLuiPersonRelationTransaction(lprTransactionId, context);
    }

    @Override
    public LPRTransactionInfo submitLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.submitLuiPersonRelationTransaction(lprTransactionId, context);

    }

    @Override
    public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        return nextDecorator.updateLprRoster(lprRosterId, lprRosterInfo, context);

    }

}
