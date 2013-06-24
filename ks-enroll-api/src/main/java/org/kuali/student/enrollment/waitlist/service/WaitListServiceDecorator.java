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
package org.kuali.student.enrollment.waitlist.service;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
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
 * The base decorator for the {@link org.kuali.student.enrollment.waitlist.service.WaitListService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 *
 */
public class WaitListServiceDecorator implements WaitListService {

    private WaitListService nextDecorator;

    public WaitListService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(WaitListService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public WaitListInfo getWaitList(String waitListId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitList(waitListId, contextInfo);
    }

    @Override
    public List<WaitListInfo> getWaitListsByIds(List<String> waitListIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListsByIds(waitListIds, contextInfo);
    }

    @Override
    public List<String> getWaitListIdsByType(String waitListTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListIdsByType(waitListTypeKey, contextInfo);
    }

    @Override
    public List<WaitListInfo> getWaitListsByOffering(String offeringId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListsByOffering(offeringId, contextInfo);
    }

    @Override
    public List<WaitListInfo> getWaitListsByTypeAndOffering(String waitListTypeKey, String offeringId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListsByTypeAndOffering(waitListTypeKey, offeringId, contextInfo);
    }

    @Override
    public List<String> searchForWaitListIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForWaitListIds(criteria, contextInfo);
    }

    @Override
    public List<WaitListInfo> searchForWaitLists(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForWaitLists(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateWaitList(String validationTypeKey, String waitListTypeKey, WaitListInfo waitListInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateWaitList(validationTypeKey, waitListTypeKey, waitListInfo, contextInfo);
    }

    @Override
    public WaitListInfo createWaitList(String waitListTypeKey, WaitListInfo waitListInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createWaitList(waitListTypeKey, waitListInfo, contextInfo);
    }

    @Override
    public WaitListInfo updateWaitList(String waitListId, WaitListInfo waitListInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateWaitList(waitListId, waitListInfo, contextInfo);
    }

    @Override
    public StatusInfo changeWaitListState(String waitListId, String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeWaitListState(waitListId, stateKey, contextInfo);
    }

    @Override
    public StatusInfo deleteWaitList(String waitListId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteWaitList(waitListId, contextInfo);
    }

    @Override
    public WaitListEntryInfo getWaitListEntry(String waitListEntryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListEntry(waitListEntryId, contextInfo);
    }

    @Override
    public List<WaitListEntryInfo> getWaitListEntriesByIds(List<String> waitListEntryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListEntriesByIds(waitListEntryIds, contextInfo);
    }

    @Override
    public List<String> getWaitListEntryIdsByType(String waitListEntryTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListEntryIdsByType(waitListEntryTypeKey, contextInfo);
    }

    @Override
    public List<WaitListEntryInfo> getWaitListEntriesByStudent(String studentId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListEntriesByStudent(studentId, contextInfo);
    }

    @Override
    public List<WaitListEntryInfo> getWaitListEntriesByWaitList(String waitListId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListEntriesByWaitList(waitListId, contextInfo);
    }

    @Override
    public List<WaitListEntryInfo> getWaitListEntriesByWaitListAndStudent(String waitListId, String studentId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListEntriesByWaitListAndStudent(waitListId, studentId, contextInfo);
    }

    @Override
    public List<String> searchForWaitListEntryIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForWaitListEntryIds(criteria, contextInfo);
    }

    @Override
    public List<WaitListEntryInfo> searchForWaitListEntries(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForWaitListEntries(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateWaitListEntry(String validationTypeKey, String waitListId, String studentId, String waitListEntryTypeKey, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateWaitListEntry(validationTypeKey, waitListId, studentId, waitListEntryTypeKey, waitListEntryInfo, contextInfo);
    }

    @Override
    public WaitListEntryInfo createWaitListEntry(String waitListId, String studentId, String waitListEntryTypeKey, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createWaitListEntry(waitListId, studentId, waitListEntryTypeKey, waitListEntryInfo, contextInfo);
    }

    @Override
    public WaitListEntryInfo updateWaitListEntry(String waitListEntryId, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateWaitListEntry(waitListEntryId, waitListEntryInfo, contextInfo);
    }

    @Override
    public StatusInfo changeWaitListEntryState(String waitListEntryId, String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeWaitListEntryState(waitListEntryId, stateKey, contextInfo);
    }

    @Override
    public StatusInfo deleteWaitListEntry(String waitListEntryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteWaitListEntry(waitListEntryId, contextInfo);
    }

    @Override
    public StatusInfo reorderWaitListEntries(String waitListId, List<String> waitListEntryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reorderWaitListEntries(waitListId, waitListEntryIds, contextInfo);
    }

    @Override
    public StatusInfo moveWaitListEntryToPosition(String waitListEntryId, Integer position, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().moveWaitListEntryToPosition(waitListEntryId, position, contextInfo);
    }
}
