/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.hold.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.*;

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
    public List<ValidationResultInfo> validateAppliedHold(String validationTypeKey, AppliedHoldInfo holdInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateAppliedHold(validationTypeKey, holdInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateHoldIssue(String validationTypeKey, HoldIssueInfo issueInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateHoldIssue(validationTypeKey, issueInfo, contextInfo);
    }

    @Override
    public AppliedHoldInfo updateAppliedHold(String holdId, AppliedHoldInfo holdInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateAppliedHold(holdId, holdInfo, contextInfo);
    }

    @Override
    public HoldIssueInfo updateHoldIssue(String issueId, HoldIssueInfo issueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateHoldIssue(issueId, issueInfo, contextInfo);
    }

    @Override
    public List<AppliedHoldInfo> searchForAppliedHolds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppliedHolds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForAppliedHoldIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppliedHoldIds(criteria, contextInfo);
    }

    @Override
    public List<HoldIssueInfo> searchForHoldIssues(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForHoldIssues(criteria, contextInfo);
    }

    @Override
    public List<String> searchForHoldIssueIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForHoldIssueIds(criteria, contextInfo);
    }

    @Override
    public AppliedHoldInfo releaseAppliedHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().releaseAppliedHold(holdId, contextInfo);
    }

    @Override
    public List<AppliedHoldInfo> getAppliedHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppliedHoldsByPerson(personId, contextInfo);
    }

    @Override
    public List<AppliedHoldInfo> getAppliedHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppliedHoldsByIssueAndPerson(issueId, personId, contextInfo);
    }

    @Override
    public List<AppliedHoldInfo> getAppliedHoldsByIds(List<String> holdIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppliedHoldsByIds(holdIds, contextInfo);
    }

    @Override
    public List<String> getAppliedHoldIdsByType(String holdTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppliedHoldIdsByType(holdTypeKey, contextInfo);
    }

    @Override
    public List<String> getAppliedHoldIdsByIssue(String issueId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppliedHoldIdsByIssue(issueId, contextInfo);
    }

    @Override
    public AppliedHoldInfo getAppliedHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppliedHold(holdId, contextInfo);
    }

    @Override
    public List<HoldIssueInfo> getHoldIssuesByOrg(String organizationId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldIssuesByOrg(organizationId, contextInfo);
    }

    @Override
    public List<HoldIssueInfo> getHoldIssuesByIds(List<String> issueIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldIssuesByIds(issueIds, contextInfo);
    }

    @Override
    public List<String> getHoldIssueIdsByType(String issueTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldIssueIdsByType(issueTypeKey, contextInfo);
    }

    @Override
    public HoldIssueInfo getHoldIssue(String issueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getHoldIssue(issueId, contextInfo);
    }

    @Override
    public List<AppliedHoldInfo> getActiveAppliedHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveAppliedHoldsByPerson(personId, contextInfo);
    }

    @Override
    public List<AppliedHoldInfo> getActiveAppliedHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveAppliedHoldsByIssueAndPerson(issueId, personId, contextInfo);
    }

    @Override
    public StatusInfo deleteAppliedHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppliedHold(holdId, contextInfo);
    }

    @Override
    public StatusInfo deleteHoldIssue(String issueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        return getNextDecorator().deleteHoldIssue(issueId, contextInfo);
    }

    @Override
    public AppliedHoldInfo createAppliedHold(String personId, String issueId, String holdTypeKey, AppliedHoldInfo holdInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createAppliedHold(personId, issueId, holdTypeKey, holdInfo, contextInfo);
    }

    @Override
    public HoldIssueInfo createHoldIssue(String issueTypeKey, HoldIssueInfo issueInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createHoldIssue(issueTypeKey, issueInfo, contextInfo);
    }
}
