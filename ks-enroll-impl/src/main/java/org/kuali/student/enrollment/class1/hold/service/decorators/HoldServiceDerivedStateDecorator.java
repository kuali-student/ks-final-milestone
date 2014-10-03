/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.hold.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;

import java.util.Date;
import java.util.List;

public class HoldServiceDerivedStateDecorator
        extends HoldServiceDecorator {

    private AcademicCalendarService academicCalendarService;

    @Override
    public AppliedHoldInfo createAppliedHold(String personId,
            String issueId,
            String holdTypeKey,
            AppliedHoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {

        AppliedHoldInfo appliedHoldInfo = null;
        if(isStatePersistent(holdInfo.getStateKey())) {
            appliedHoldInfo = getNextDecorator().createAppliedHold(personId, issueId, holdTypeKey, holdInfo, contextInfo);
        } else {
            appliedHoldInfo = getNextDecorator().createAppliedHold(personId, issueId, holdTypeKey, holdInfo, contextInfo);
            appliedHoldInfo.setStateKey(getDerivedState(holdInfo, contextInfo));
        }
        return appliedHoldInfo;
    }

    @Override
    public AppliedHoldInfo updateAppliedHold(String holdId,
            AppliedHoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {

        AppliedHoldInfo appliedHoldInfo = null;
        if(isStatePersistent(holdInfo.getStateKey())) {
            appliedHoldInfo = getNextDecorator().updateAppliedHold(holdId, holdInfo, contextInfo);
        } else {
            appliedHoldInfo = getNextDecorator().updateAppliedHold(holdId, holdInfo, contextInfo);
            appliedHoldInfo.setStateKey(getDerivedState(holdInfo, contextInfo));
        }
        return appliedHoldInfo;
    }

    @Override
    public List<AppliedHoldInfo> searchForAppliedHolds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppliedHoldInfo> appliedHolds = getNextDecorator().searchForAppliedHolds(criteria, contextInfo);
        for(AppliedHoldInfo appliedHoldInfo : appliedHolds) {
            appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        }
        return appliedHolds;
    }

    @Override
    public List<AppliedHoldInfo> getAppliedHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppliedHoldInfo> appliedHolds = getNextDecorator().getAppliedHoldsByPerson(personId, contextInfo);
        for(AppliedHoldInfo appliedHoldInfo : appliedHolds) {
            appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        }
        return appliedHolds;
    }

    @Override
    public List<AppliedHoldInfo> getAppliedHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppliedHoldInfo> appliedHolds = getNextDecorator().getAppliedHoldsByIssueAndPerson(issueId, personId, contextInfo);
        for(AppliedHoldInfo appliedHoldInfo : appliedHolds) {
            appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        }
        return appliedHolds;
    }

    @Override
    public List<AppliedHoldInfo> getAppliedHoldsByIds(List<String> holdIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppliedHoldInfo> appliedHolds = getNextDecorator().getAppliedHoldsByIds(holdIds, contextInfo);
        for(AppliedHoldInfo appliedHoldInfo : appliedHolds) {
            appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        }
        return appliedHolds;
    }

    @Override
    public AppliedHoldInfo getAppliedHold(String holdId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AppliedHoldInfo appliedHoldInfo = getNextDecorator().getAppliedHold(holdId, contextInfo);
        appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        return appliedHoldInfo;
    }

    @Override
    public List<AppliedHoldInfo> getActiveAppliedHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppliedHoldInfo> appliedHolds = getNextDecorator().getActiveAppliedHoldsByPerson(personId, contextInfo);
        for(AppliedHoldInfo appliedHoldInfo : appliedHolds) {
            appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        }
        return appliedHolds;
    }

    @Override
    public List<AppliedHoldInfo> getActiveAppliedHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppliedHoldInfo> appliedHolds = getNextDecorator().getActiveAppliedHoldsByIssueAndPerson(issueId, personId, contextInfo);
        for(AppliedHoldInfo appliedHoldInfo : appliedHolds) {
            appliedHoldInfo.setStateKey(getDerivedState(appliedHoldInfo, contextInfo));
        }
        return appliedHolds;
    }

    /**
     * returns true if the state is not one of the derived AppliedHold states
     */
    protected boolean isStatePersistent(String state) {
        return state.equals(HoldServiceConstants.APPLIED_HOLD_CANCELED_STATE_KEY)
                || state.equals(HoldServiceConstants.APPLIED_HOLD_DELETED_STATE_KEY);
    }

    /*
     * returns the derived state of the AppliedHoldInfo parameter.
     * open, active, expired states are derived lifecycle states based on dates/terms in the applied hold
     * persisted states are cancelled, deleted.
     * KSEN_HOLD.HOLD_STATE will maintain the AppliedHold initial state of 'active' through all the derived lifecycle
     * states of open, active, expired, but in the case of AppliedHolds with persisted states of cancelled or deleted,
     * those will be reflected in KSEN_HOLD.HOLD_STATE (since they are not derived)
     */
    protected String getDerivedState(AppliedHoldInfo appliedHoldInfo, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        if(isStatePersistent(appliedHoldInfo.getStateKey())) {
            return appliedHoldInfo.getStateKey(); // not going to derive if state is persistent
        }

        String effectiveTermId = appliedHoldInfo.getApplicationEffectiveTermId();
        String expirationTermId = appliedHoldInfo.getApplicationExpirationTermId();
        Date effectiveDate = appliedHoldInfo.getEffectiveDate();
        Date expirationDate = appliedHoldInfo.getExpirationDate();

        Date currentDate = contextInfo.getCurrentDate();

        if(effectiveDate != null) { // an effectiveDate means the AppliedHold is based on effectiveDate/expirationDate
            if(currentDate.equals(effectiveDate) || currentDate.after(effectiveDate)) {
                if(expirationDate == null  // if expirationDate is null, assume no expiration date
                        || currentDate.before(expirationDate)) {
                    return HoldServiceConstants.APPLIED_HOLD_OPEN_STATE_KEY;
                } else if(currentDate.equals(expirationDate) || currentDate.after(expirationDate)) {
                    return HoldServiceConstants.APPLIED_HOLD_EXPIRED_STATE_KEY;
                }
            } else {
                return HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY;
            }
        } else if(effectiveTermId != null) { // an effectiveTermId means the AppliedHold
            TermInfo effectiveTermInfo = null; // is based on effectiveTermId/expirationTermId
            TermInfo expirationTermInfo = null;
            try {
                effectiveTermInfo = academicCalendarService.getTerm(effectiveTermId, contextInfo);
            } catch(DoesNotExistException e) {
                throw new InvalidParameterException("Term does not exist with effectiveTermId: " + effectiveTermId + " exception: " + e);
            }
            Date effectiveTermStartDate = effectiveTermInfo.getStartDate();
            Date effectiveTermEndDate = effectiveTermInfo.getEndDate();

            try {
                expirationTermInfo = academicCalendarService.getTerm(expirationTermId, contextInfo);
            } catch(DoesNotExistException e) {
                throw new InvalidParameterException("Term does not exist with expirationTermId: " + expirationTermId + " exception: " + e);
            }
            Date expirationTermStartDate = expirationTermInfo.getStartDate();
            Date expirationTermEndDate = expirationTermInfo.getEndDate();
            if(currentDate.after(effectiveTermStartDate) && currentDate.before(effectiveTermEndDate)
                    && currentDate.before(expirationTermStartDate) && currentDate.before(expirationTermEndDate)) {
                return HoldServiceConstants.APPLIED_HOLD_OPEN_STATE_KEY;
            }
        } else {
            throw new InvalidParameterException("One of effectiveTermId or effectiveDate must not be null");
        }
        return appliedHoldInfo.getStateKey(); // if nothing else, just return the state of the AppliedHold
    }

    public AcademicCalendarService getAcademicCalendarService() {
        return academicCalendarService;
    }

    public void setAcademicCalendarService(AcademicCalendarService academicCalendarService) {
        this.academicCalendarService = academicCalendarService;
    }
}
