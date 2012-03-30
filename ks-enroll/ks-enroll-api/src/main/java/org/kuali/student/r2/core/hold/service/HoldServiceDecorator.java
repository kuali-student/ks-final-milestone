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
package org.kuali.student.r2.core.hold.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

public class HoldServiceDecorator
        implements HoldService {

    private HoldService nextDecorator;

    public HoldService getNextDecorator()
            throws OperationFailedException {

        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(HoldService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public HoldInfo getHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHold(holdId, contextInfo);
    }

    @Override
    public List<HoldInfo> getHoldsByIds(List<String> holdIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldsByIds(holdIds, contextInfo);
    }

    @Override
    public List<String> getHoldIdsByType(String holdTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldIdsByType(holdTypeKey, contextInfo);
    }

    @Override
    public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldsByIssue(issueId, contextInfo);
    }

    @Override
    public List<HoldInfo> getHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldsByPerson(personId, contextInfo);
    }

    @Override
    public List<HoldInfo> getActiveHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveHoldsByPerson(personId, contextInfo);
    }

    @Override
    public List<HoldInfo> getHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldsByIssueAndPerson(issueId, personId, contextInfo);
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveHoldsByIssueAndPerson(issueId, personId, contextInfo);
    }

    @Override
    public List<String> searchForHoldIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForHoldIds(criteria, contextInfo);
    }

    @Override
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForHolds(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateHold(validationTypeKey, holdInfo, contextInfo);
    }

    @Override
    public HoldInfo createHold(String personId,
            String issueId,
            String holdTypeKey,
            HoldInfo holdInfo,
            ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createHold(personId, issueId, holdTypeKey, holdInfo, contextInfo);
    }

    @Override
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateHold(holdId, holdInfo, contextInfo);
    }

    @Override
    public HoldInfo releaseHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().releaseHold(holdId, contextInfo);
    }

    @Override
    public StatusInfo deleteHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteHold(holdId, contextInfo);
    }

    @Override
    public IssueInfo getIssue(String issueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getIssue(issueId, contextInfo);
    }

    @Override
    public List<IssueInfo> getIssuesByIds(List<String> issueIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getIssuesByIds(issueIds, contextInfo);
    }

    @Override
    public List<String> getIssueIdsByType(String issueTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getIssueIdsByType(issueTypeKey, contextInfo);
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getIssuesByOrg(organizationId, contextInfo);
    }

    @Override
    public List<String> searchForIssueIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForIssueIds(criteria, contextInfo);
    }

    @Override
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForIssues(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey, IssueInfo issueInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateIssue(validationTypeKey, issueInfo, contextInfo);
    }

    @Override
    public IssueInfo createIssue(String issueTypeKey, IssueInfo issueInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createIssue(issueTypeKey, issueInfo, contextInfo);
    }

    @Override
    public IssueInfo updateIssue(String issueId, IssueInfo issueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateIssue(issueId, issueInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteIssue(String issueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteIssue(issueId, contextInfo);
    }
}
