/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.roster.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.List;

/**
 * The base decorator for the {@link LprRosterService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 *
 */
public class LprRosterServiceDecorator implements LprRosterService {

    private LprRosterService nextDecorator;

    public LprRosterService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(LprRosterService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public LprRosterInfo getLprRoster(String lprRosterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRoster(lprRosterId, contextInfo);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByIds(List<String> lprRosterIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByIds(lprRosterIds, contextInfo);
    }

    @Override
    public List<String> getLprRosterIdsByType(String lprRosterTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterIdsByType(lprRosterTypeKey, contextInfo);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByLui(luiId, contextInfo);
    }

    @Override
    public List<LprRosterInfo> getLprRostersByTypeAndLui(String lprRosterTypeKey, String luiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRostersByTypeAndLui(lprRosterTypeKey, luiId, contextInfo);
    }

    @Override
    public List<String> searchForLprRosterIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprRosterIds(criteria, contextInfo);
    }

    @Override
    public List<LprRosterInfo> searchForLprRosters(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprRosters(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLprRoster(String validationTypeKey, String lprRosterTypeKey, LprRosterInfo lprRosterInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLprRoster(validationTypeKey, lprRosterTypeKey, lprRosterInfo, contextInfo);
    }

    @Override
    public LprRosterInfo createLprRoster(String lprRosterTypeKey, LprRosterInfo lprRosterInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLprRoster(lprRosterTypeKey, lprRosterInfo, contextInfo);
    }

    @Override
    public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateLprRoster(lprRosterId, lprRosterInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprRoster(lprRosterId, contextInfo);
    }

    @Override
    public LprRosterEntryInfo getLprRosterEntry(String lprRosterEntryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntry(lprRosterEntryId, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByIds(List<String> lprRosterEntryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesByIds(lprRosterEntryIds, contextInfo);
    }

    @Override
    public List<String> getLprRosterEntryIdsByType(String lprRosterEntryTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntryIdsByType(lprRosterEntryTypeKey, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByLprRoster(String lprRosterId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesByLprRoster(lprRosterId, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByLpr(String lprId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesByLpr(lprId, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByLprRosterAndLpr(String lprRosterId, String lprId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprRosterEntriesByLprRosterAndLpr(lprRosterId, lprId, contextInfo);
    }

    @Override
    public List<String> searchForLprRosterEntryIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprRosterEntryIds(criteria, contextInfo);
    }

    @Override
    public List<LprRosterEntryInfo> searchForLprRosterEntries(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprRosterEntries(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLprRosterEntry(String validationTypeKey, String lprRosterId, String lprId, String lprRosterTypeKey, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLprRosterEntry(validationTypeKey, lprRosterId, lprId, lprRosterTypeKey, lprRosterEntryInfo, contextInfo);
    }

    @Override
    public LprRosterEntryInfo createLprRosterEntry(String lprRosterId, String lprId, String lprRosterEntryTypeKey, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLprRosterEntry(lprRosterId, lprId, lprRosterEntryTypeKey, lprRosterEntryInfo, contextInfo);
    }

    @Override
    public LprRosterEntryInfo updateLprRosterEntry(String lprRosterEntryId, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateLprRosterEntry(lprRosterEntryId, lprRosterEntryInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprRosterEntry(lprRosterEntryId, contextInfo);
    }

    @Override
    public StatusInfo moveLprRosterEntryToPosition(String lprRosterEntryId, Integer position, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().moveLprRosterEntryToPosition(lprRosterEntryId, position, contextInfo);
    }

    @Override
    public StatusInfo reorderLprRosterEntries(String lprRosterId, List<String> lprRosterEntryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reorderLprRosterEntries(lprRosterId, lprRosterEntryIds, contextInfo);
    }
}
