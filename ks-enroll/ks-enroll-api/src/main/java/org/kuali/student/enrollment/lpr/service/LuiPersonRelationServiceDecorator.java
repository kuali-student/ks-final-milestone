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
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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

    public LuiPersonRelationService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(LuiPersonRelationService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLpr(validationType, luiPersonRelationInfo, context);
    }

    @Override
    public LuiPersonRelationInfo updateLpr(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateLpr(luiPersonRelationId, luiPersonRelationInfo, context);
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLprIds(criteria, context);
    }

    @Override
    public List<LuiPersonRelationInfo> searchForLprs(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLprs(criteria, context);
    }

    @Override
    public List<String> getPersonIdsByLui(String luiId, String luiPersonRelationTypeKey, String relationState,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPersonIdsByLui(luiId, luiPersonRelationTypeKey, relationState, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPerson(personId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByLui(luiId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByIdList(List<String> luiPersonRelationIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByIdList(luiPersonRelationIdList, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByLuiAndPerson(personId, luiId, context);
    }

    @Override
    public List<String> getLprIdsByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprIdsByLui(luiId, context);
    }

    @Override
    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprIdsByLuiAndPerson(personId, luiId, context);
    }

    @Override
    public List<String> getLuiIdsByPerson(String personId, String luiPersonRelationTypeKey, String relationState,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiIdsByPerson(personId, luiPersonRelationTypeKey, relationState, context);
    }

    @Override
    public StatusInfo deleteLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLpr(luiPersonRelationId, context);
    }

    @Override
    public String createLpr(String personId, String luiId, String luiPersonRelationTypeKey,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLpr(personId, luiId, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
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
    public LuiPersonRelationInfo getLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLpr(luiPersonRelationId, context);
    }

    @Override
    public List<String> getLprIdsByPerson(String personId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprIdsByPerson(personId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtp(personId, atpId, context);
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
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransaction(lprTransactionId, context);
    }

    @Override
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<LprRosterEntryInfo> getLprRosterEntriesByIdList(List<String> lprRosterEntryIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesByIdList(lprRosterEntryIdList, context);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLuiAndRosterType(luiId, lprRosterTypeKey, context);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLui(luiId, context);
    }

    @Override
    public LprRosterInfo getLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRoster(lprRosterId, context);
    }

    @Override
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprRosterEntry(lprRosterEntryInfo, context);
    }

    @Override
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo context) throws DoesNotExistException,
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
    public LprTransactionInfo createLprTransaction(LprTransactionInfo lprTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprTransaction(lprTransactionInfo, context);
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprTransactionFromExisting(lprTransactionId, context);
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByLui(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForPersonByLui(personId, luiId, context);
    }

    @Override
    public List<ValidationResultInfo> validateLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLprTransaction(lprTransactionId, context);
    }

    @Override
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().processLprTransaction(lprTransactionId, context);
    }

    @Override
    public List<String> getLprTransactionIdsForPerson(String personId, List<String> lprTypes, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionIdsForPerson(personId, lprTypes, context);
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIdList(List<String> lprIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsByIdList(lprIds, context);
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByAtp(String atpId, String personId,
            List<String> lprTypes, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForPersonByAtp(atpId, personId, lprTypes, context);
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLpr(String lprId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForLpr(lprId, context);

    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsForLui(luiId, context);

    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().updateLprTransaction(lprTransactionId, lprTransactionInfo, context);
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLprTransactions(criteria, context);
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLprIds(criteria, context);
    }

    @Override
    public List<LprRosterInfo> searchForLprRosters(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().searchForLprRosters(criteria, context);
    }

    @Override
    public List<String> searchForLprRosterIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLprRosterIds(criteria, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(String personId, String atpId, String typeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndTypeForAtp(personId, atpId, typeKey, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpId,
            String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtpAndLuiType(personId, atpId, luiTypeKey, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndPersonType(String personId, String atpId,
            String personTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtpAndPersonType(personId, atpId, personTypeKey, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByTypeAndLui(String typeKey, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByTypeAndLui(typeKey, luiId, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndLuiType(personId, luiTypeKey, context);
    }

}
