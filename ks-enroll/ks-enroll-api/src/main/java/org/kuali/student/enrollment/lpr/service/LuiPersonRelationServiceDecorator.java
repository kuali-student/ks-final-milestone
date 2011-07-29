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
public class LuiPersonRelationServiceDecorator implements LuiPersonRelationService {

    private LuiPersonRelationService nextDecorator;

    public LuiPersonRelationService getNextDecorator()
            throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(LuiPersonRelationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLuiPersonRelationIds(criteria, context);
    }

    @Override
    public List<LuiPersonRelationInfo> searchForLuiPersonRelations(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLuiPersonRelations(criteria, context);
    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationTypeKey, String relationState,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findPersonIdsRelatedToLui(luiId, luiPersonRelationTypeKey, relationState, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationsForPerson(personId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationsForLui(luiId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationsByIdList(luiPersonRelationIdList, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelations(personId, luiId, context);
    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationIdsForLui(luiId, context);
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationIds(personId, luiId, context);
    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationTypeKey,
            String relationState, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiIdsRelatedToPerson(personId, luiPersonRelationTypeKey, relationState, context);
    }

    @Override
    public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationTypeKey,
            String relationState, String atpId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findAllValidLuisForPerson(personId, luiPersonRelationTypeKey, relationState, atpId,
                    context);
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLuiPersonRelation(luiPersonRelationId, context);
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationTypeKey,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLuiPersonRelation(personId, luiId, luiPersonRelationTypeKey,
                luiPersonRelationInfo, context);
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState,
            String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createBulkRelationshipsForPerson(personId, luiIdList, relationState,
                    luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState,
            String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createBulkRelationshipsForLui(luiId, personIdList, relationState,
                    luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        return getNextDecorator().getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        return getNextDecorator().getDataDictionaryEntry(entryKey, context);
    }

    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByKey(processKey, context);
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByObjectType(refObjectUri, context);
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getState(processKey, stateKey, context);
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getStatesByProcess(processKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getNextHappyState(processKey, currentStateKey, context);
    }

    @Override
    public LuiPersonRelationInfo fetchLuiPersonRelation(String luiPersonRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().fetchLuiPersonRelation(luiPersonRelationId, context);
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationIdsForPerson(personId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPersonAndAtp(String personId, String atpId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().findLuiPersonRelationsForPersonAndAtp(personId, atpId, context);
    }

    @Override
    public String createLprRoster(LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprRoster(lprRosterInfo, context);
    }

    @Override
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprRoster(lprRosterId, context);
    }

    

    @Override
    public LPRTransactionInfo updateLuiPersonRelationTransaction(String lprTransactionId,
            LPRTransactionItemInfo luiPersonRelationRequestInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().updateLuiPersonRelationTransaction(lprTransactionId, luiPersonRelationRequestInfo, context);
    }

    @Override
    public LPRTransactionInfo getLprRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRelationTransaction(lprTransactionId, context);
    }

   

    @Override
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprTransaction(lprTransactionId, context);
    }

   

    @Override
    public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateLprRoster(lprRosterId, lprRosterInfo, context);
    }

    @Override
    public List<LprRosterEntryInfo> getEntriesForLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEntriesForLprRoster(lprRosterId, context);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLuiAndRosterType(luiId, lprRosterTypeKey, context);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLui(luiId, context);
    }

    @Override
    public List<LuiInfo> getLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRoster(lprRosterId, context);
    }

    @Override
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprRosterEntry(lprRosterEntryInfo, context);
    }

    @Override
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprRosterEntry(lprRosterEntryId, context);
    }


    @Override
    public StatusInfo insertLprRosterEntryInPosition(String lprRosterEntryId, Integer position, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().insertLprRosterEntryInPosition(lprRosterEntryId, position, context);
    }

    @Override
    public StatusInfo reorderLprRosterEntries(List<String> lprRosterEntryIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reorderLprRosterEntries(lprRosterEntryIds, context);
    }

    @Override
    public LPRTransactionInfo createLuiPersonRelationTransaction(LPRTransactionInfo lprTransactionInfo,
            ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createLuiPersonRelationTransaction(lprTransactionInfo, context);
    }

    @Override
    public LPRTransactionInfo createLuiPersonRelationTransactionFromExisting(String lprTransactionId,
            ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLuiPersonRelationTransactionFromExisting(lprTransactionId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLprTransactionsForPersonByLui(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForPersonByLui(personId, luiId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLprTransactionsByPerson(String personId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsByPerson(personId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLprTransactionsForPersonByAtp(String atpKey, String personId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForPersonByAtp(atpKey, personId, context);
    }

    @Override
    public List<LPRTransactionInfo> getLprTransactionsForLuiByAtp(String luiId, String atpKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForLuiByAtp(luiId, atpKey, context);
    }

    @Override
    public LPRTransactionInfo validateLprTransaction(String lprTransactionId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLprTransaction(lprTransactionId, context);
    }

    @Override
    public LPRTransactionInfo verifyLprTransaction(String lprTransactionId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().verifyLprTransaction(lprTransactionId, context);
    }

    @Override
    public LPRTransactionInfo submitLprTransaction(String lprTransactionId, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().submitLprTransaction(lprTransactionId, context);
    }

}
