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
import org.kuali.student.enrollment.lpr.dto.*;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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

import javax.jws.WebParam;

/**
 * The base decorator for the {@link LprService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 * 
 * @author nwright
 */
public class LprServiceDecorator implements LprService {

    private LprService nextDecorator;

    public LprService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(LprService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public LuiPersonRelationInfo getLpr(@WebParam(name = "lprId") String lprId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLpr(lprId, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByIds(@WebParam(name = "lprIds") List<String> lprIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByIds(lprIds, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByPersonAndTypeAndState(@WebParam(name = "personId") String personId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "relationState") String relationState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiIdsByPersonAndTypeAndState(personId, lprTypeKey, relationState, contextInfo);
    }

    @Override
    public List<String> getPersonIdsByLuiAndTypeAndState(@WebParam(name = "luiId") String luiId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "relationState") String relationState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPersonIdsByLuiAndTypeAndState(luiId, lprTypeKey, relationState, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndLui(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndLui(personId, luiId, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPerson(personId, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByLui(luiId, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLuiAndType(@WebParam(name = "luiId") String luiId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByLuiAndType(luiId, lprTypeKey, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtp(personId, atpId, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndTypeForAtp(personId, atpId, lprTypeKey, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndLuiType(@WebParam(name = "personId") String personId, @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndLuiType(personId, luiTypeKey, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId, @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtpAndLuiType(personId, atpId, luiTypeKey, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLpr(@WebParam(name = "validationType") String validationType, @WebParam(name = "luiId") String luiId, @WebParam(name = "personId") String personId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "lprInfo") LuiPersonRelationInfo lprInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLpr(validationType, luiId, personId, lprTypeKey, lprInfo, contextInfo);
    }

    @Override
    public List<String> searchForLprIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprIds(criteria, contextInfo);
    }

    @Override
    public List<LuiPersonRelationInfo> searchForLprs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprs(criteria, contextInfo);
    }

    @Override
    public String createLpr(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "lprInfo") LuiPersonRelationInfo lprInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLpr(personId, luiId, lprTypeKey, lprInfo, contextInfo);
    }

    @Override
    public List<BulkStatusInfo> createLprsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "lprInfos") List<LuiPersonRelationInfo> lprInfos, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLprsForPerson(personId, lprTypeKey, lprInfos, contextInfo);
    }

    @Override
    public List<String> createLprsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "lprTypeKey") String lprTypeKey, @WebParam(name = "lprInfos") List<LuiPersonRelationInfo> lprInfos, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLprsForLui(luiId, lprTypeKey, lprInfos, contextInfo);
    }

    @Override
    public LuiPersonRelationInfo updateLpr(@WebParam(name = "lprId") String lprId, @WebParam(name = "lprInfo") LuiPersonRelationInfo lprInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateLpr(lprId, lprInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLpr(@WebParam(name = "lprId") String lprId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLpr(lprId, contextInfo);
    }

    @Override
    public LprRosterInfo updateLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateLprRoster(lprRosterId, lprRosterInfo, contextInfo);
    }

    @Override
    public String createLprRoster(@WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprRoster(lprRosterInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprRoster(lprRosterId, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesForRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesForRoster(lprRosterId, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByIds(@WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesByIds(lprRosterEntryIds, contextInfo);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndType(@WebParam(name = "luiId") String luiId, @WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLuiAndType(luiId, lprRosterTypeKey, contextInfo);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLui(luiId, contextInfo);
    }

    @Override
    public LprRosterInfo getLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRoster(lprRosterId, contextInfo);
    }

    @Override
    public String createLprRosterEntry(@WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprRosterEntry(lprRosterEntryInfo, contextInfo);
    }

    @Override
    public LprRosterInfo updateLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId, @WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateLprRosterEntry(lprRosterEntryId, lprRosterEntryInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprRosterEntry(lprRosterEntryId, contextInfo);
    }

    @Override
    public StatusInfo insertLprRosterEntryInPosition(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId, @WebParam(name = "absolutePosition") Integer absolutePosition, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().insertLprRosterEntryInPosition(lprRosterEntryId, absolutePosition, contextInfo);
    }

    @Override
    public StatusInfo reorderLprRosterEntries(@WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reorderLprRosterEntries(lprRosterEntryIds, contextInfo);
    }

    @Override
    public LprTransactionInfo createLprTransaction(@WebParam(name = "lprTransactionType") String lprTransactionType, @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprTransaction(lprTransactionType, lprTransactionInfo, contextInfo);
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprTransactionFromExisting(lprTransactionId, contextInfo);
    }

    @Override
    public LprTransactionInfo updateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateLprTransaction(lprTransactionId, lprTransactionInfo, contextInfo);
    }

    @Override
    public LprTransactionInfo getLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionsItemsByPersonAndLui(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsItemsByPersonAndLui(personId, luiId, contextInfo);
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIds(@WebParam(name = "lprTransactionIds") List<String> lprTransactionIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsByIds(lprTransactionIds, contextInfo);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionsWithItemsByResultingLpr(@WebParam(name = "lprId") String lprId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsWithItemsByResultingLpr(lprId, contextInfo);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionItemsByLui(luiId, contextInfo);
    }

    @Override
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(@WebParam(name = "personId") String requestingPersonId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getUnsubmittedLprTransactionsByRequestingPersonAndAtp(requestingPersonId, atpId, contextInfo);
    }

    @Override
    public StatusInfo deleteLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public LprTransactionInfo processLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().processLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> verifyLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().verifyLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprTransactions(criteria, contextInfo);
    }

    @Override
    public List<String> searchForLprTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprTransactionIds(criteria, contextInfo);
    }

    @Override
    public List<LprRosterInfo> searchForLprRosters(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprRosters(criteria, contextInfo);
    }

    @Override
    public List<String> searchForLprRosterIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprRosterIds(criteria, contextInfo);
    }
}
