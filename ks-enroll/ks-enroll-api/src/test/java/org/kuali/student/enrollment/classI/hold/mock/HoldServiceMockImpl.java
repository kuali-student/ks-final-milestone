package org.kuali.student.enrollment.classI.hold.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

public class HoldServiceMockImpl implements HoldService {

    private Map<String, HoldInfo> holds = new HashMap<String, HoldInfo>();
    private Map<String, IssueInfo> issues = new HashMap<String, IssueInfo>();

    @Override
    public HoldInfo getHold(String holdId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return holds.get(holdId);
    }

    @Override
    public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo hold : this.holds.values()) {
            if (hold.getIssueId().equals(issueId)) {
                list.add(hold);
            }
        }
        return list;
    }

    @Override
    public List<String> searchForHoldIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<String>();
    }

    @Override
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<HoldInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey,
            HoldInfo holdInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
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

    @Override
    public HoldInfo createHold(String personId, 
            String issueId,
            String holdTypeKey,
            HoldInfo holdInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        HoldInfo copy = new HoldInfo(holdInfo);
        copy.setId(holds.size() + "");
        copy.setMeta(newMeta(context));
        holds.put(copy.getId(), copy);
        return new HoldInfo(copy);
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    @Override
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        HoldInfo copy = new HoldInfo(holdInfo);
        HoldInfo old = this.getHold(holdId, context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.holds.put(holdInfo.getId(), copy);
        return new HoldInfo(copy);
    }

    @Override
    public HoldInfo releaseHold(String holdId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        HoldInfo hold = holds.get(holdId);
        hold.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
        hold.setReleasedDate(new Date());
        return hold;

    }

    @Override
    public StatusInfo deleteHold(String holdId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.holds.remove(holdId) == null) {
            throw new DoesNotExistException(holdId);
        }
        return newStatus();
    }

    @Override
    public IssueInfo getIssue(String issueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return issues.get(issueId);
    }

    @Override
    public List<String> getIssueIdsByType(String issueTypeKey,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> issueList = new ArrayList<String>();

        for (IssueInfo issue : issues.values()) {
            if (issue.getTypeKey().equals(issueTypeKey)) {
                issueList.add(issue.getId());
            }
        }
        return issueList;
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<IssueInfo> issueList = new ArrayList<IssueInfo>();

        for (IssueInfo issue : issues.values()) {
            if (issue.getOrganizationId().equals(organizationId)) {
                issueList.add(issue);
            }
        }
        return issueList;
    }

    @Override
    public List<String> searchForIssueIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<String>();
    }

    @Override
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<IssueInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey,
            IssueInfo issueInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public IssueInfo createIssue(String issueTypeKey, IssueInfo issueInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        IssueInfo copy = new IssueInfo(issueInfo);
        copy.setMeta(newMeta(context));
        issues.put(copy.getId(), copy);
        return new IssueInfo(copy);
    }

    @Override
    public IssueInfo updateIssue(String issueId, IssueInfo issueInfo,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        IssueInfo copy = new IssueInfo(issueInfo);
        IssueInfo old = this.getIssue(issueId, context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.issues.put(issueInfo.getId(), copy);
        return new IssueInfo(copy);
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteIssue(String issueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.issues.remove(issueId) == null) {
            throw new DoesNotExistException(issueId);
        }
        return newStatus();
    }

    @Override
    public List<HoldInfo> getHoldsByIds(List<String> holdIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getHoldIdsByType(String holdTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : this.holds.values()) {
            if (info.getPersonId().equals(personId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : this.getHoldsByPerson(personId, contextInfo)) {
            if (info.getStateKey().equals(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : this.getHoldsByPerson(personId, contextInfo)) {
            if (info.getIssueId().equals(issueId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<HoldInfo> list = new ArrayList<HoldInfo>();
        for (HoldInfo info : this.getHoldsByIssueAndPerson(issueId, personId, contextInfo)) {
            if (info.getStateKey().equals(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<IssueInfo> getIssuesByIds(List<String> issueIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
