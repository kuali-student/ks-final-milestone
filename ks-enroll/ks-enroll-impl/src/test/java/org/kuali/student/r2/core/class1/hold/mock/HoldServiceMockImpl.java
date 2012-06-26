/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.class1.hold.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

public class HoldServiceMockImpl
        implements HoldService {

    @Override
    public HoldInfo getHold(String holdId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.holdMap.containsKey(holdId)) {
            throw new DoesNotExistException(holdId);
        }
        return new HoldInfo(this.holdMap.get(holdId));
    }

    @Override
    public List<HoldInfo> getHoldsByIds(List<String> holdIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (String id : holdIds) {
            list.add(this.getHold(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getHoldIdsByType(String holdTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (HoldInfo info : holdMap.values()) {
            if (holdTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getHoldIdsByIssue(String issueId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (HoldInfo info : holdMap.values()) {
            if (issueId.equals(info.getIssueId())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getHoldsByPerson(String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : holdMap.values()) {
            if (personId.equals(info.getPersonId())) {
                list.add(new HoldInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByPerson(String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Date now = new Date();
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : holdMap.values()) {
            if (personId.equals(info.getPersonId())) {
                if (info.getStateKey().equals(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY)) {
                    if (info.getEffectiveDate().before(now)) {
                        if (info.getReleasedDate() == null || info.getReleasedDate().after(now)) {
                            list.add(new HoldInfo(info));
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getHoldsByIssueAndPerson(String issueId,
            String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : holdMap.values()) {
            if (issueId.equals(info.getIssueId())) {
                if (personId.equals(info.getPersonId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(String issueId,
            String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Date now = new Date();
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : holdMap.values()) {
            if (issueId.equals(info.getIssueId())) {
                if (personId.equals(info.getPersonId())) {
                    if (info.getStateKey().equals(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY)) {
                        if (info.getEffectiveDate().before(now)) {
                            if (info.getReleasedDate() == null || info.getReleasedDate().after(now)) {
                                list.add(info);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForHoldIds(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForHoldIds has not been implemented");
    }

    @Override
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForHolds has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey,
            HoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, HoldInfo> holdMap = new LinkedHashMap<String, HoldInfo>();

    @Override
    public HoldInfo createHold(String personId,
            String issueId,
            String holdTypeKey,
            HoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create 
        if (!holdTypeKey.equals(holdInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        HoldInfo copy = new HoldInfo(holdInfo);
        if (copy.getId() == null) {
            copy.setId(holdMap.size() + "");
        }
        copy.setMeta(newMeta(contextInfo));
        holdMap.put(copy.getId(), copy);
        return new HoldInfo(copy);
    }

    @Override
    public HoldInfo updateHold(String holdId,
            HoldInfo holdInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        if (!holdId.equals(holdInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        HoldInfo copy = new HoldInfo(holdInfo);
        HoldInfo old = this.getHold(holdInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.holdMap.put(holdInfo.getId(), copy);
        return new HoldInfo(copy);
    }

    @Override
    public HoldInfo releaseHold(String holdId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        HoldInfo info = this.getHold(holdId, contextInfo);
        info.setReleasedDate(new Date());
        try {
            return this.updateHold(holdId, info, contextInfo);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public StatusInfo deleteHold(String holdId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.holdMap.remove(holdId) == null) {
            throw new DoesNotExistException(holdId);
        }
        return newStatus();
    }

    @Override
    public IssueInfo getIssue(String issueId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.issueMap.containsKey(issueId)) {
            throw new DoesNotExistException(issueId);
        }
        return new IssueInfo(this.issueMap.get(issueId));
    }

    @Override
    public List<IssueInfo> getIssuesByIds(List<String> issueIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<IssueInfo> list = new ArrayList<IssueInfo>();
        for (String id : issueIds) {
            list.add(this.getIssue(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getIssueIdsByType(String issueTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (IssueInfo info : issueMap.values()) {
            if (issueTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<IssueInfo> list = new ArrayList<IssueInfo>();
        for (IssueInfo info : issueMap.values()) {
            if (organizationId.equals(info.getOrganizationId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<String> searchForIssueIds(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForIssueIds has not been implemented");
    }

    @Override
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForIssues has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey,
            IssueInfo issueInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, IssueInfo> issueMap = new LinkedHashMap<String, IssueInfo>();

    @Override
    public IssueInfo createIssue(String issueTypeKey,
            IssueInfo issueInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create 
        if (!issueTypeKey.equals(issueInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        IssueInfo copy = new IssueInfo(issueInfo);
        if (copy.getId() == null) {
            copy.setId(issueMap.size() + "");
        }
        copy.setMeta(newMeta(contextInfo));
        issueMap.put(copy.getId(), copy);
        return new IssueInfo(copy);
    }

    @Override
    public IssueInfo updateIssue(String issueId,
            IssueInfo issueInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        if (!issueId.equals(issueInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        IssueInfo copy = new IssueInfo(issueInfo);
        IssueInfo old = this.getIssue(issueInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.issueMap.put(issueInfo.getId(), copy);
        return new IssueInfo(copy);
    }

    @Override
    public StatusInfo deleteIssue(String issueId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DependentObjectsExistException {
        if (!this.issueMap.containsKey(issueId)) {
            throw new DoesNotExistException(issueId);
        }
        List<String> list = this.getHoldIdsByIssue(issueId, contextInfo);
        if (!list.isEmpty()) {
            throw new DependentObjectsExistException(list.size() + " hold(s) with this issue");
        }
        if (this.issueMap.remove(issueId) == null) {
            throw new DoesNotExistException(issueId);
        }
        return newStatus();
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old,
            ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
