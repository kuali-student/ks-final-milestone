/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.hold.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.hold.dao.HoldDao;
import org.kuali.student.enrollment.class1.hold.dao.IssueDao;
import org.kuali.student.enrollment.class1.hold.model.HoldEntity;
import org.kuali.student.enrollment.class1.hold.model.IssueEntity;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.transaction.annotation.Transactional;

@WebService(name = "HoldService", serviceName = "HoldService", portName = "HoldService", targetNamespace = "http://student.kuali.org/wsdl/hold")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class HoldServiceImpl implements HoldService {

    private IssueDao issueDao;
    private HoldDao holdDao;

    public IssueDao getIssueDao() {
        return issueDao;
    }

    public void setIssueDao(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    public HoldDao getHoldDao() {
        return holdDao;
    }

    public void setHoldDao(HoldDao holdDao) {
        this.holdDao = holdDao;
    }

    @Override
    public HoldInfo getHold(String holdId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        HoldEntity entity = holdDao.find(holdId);

        if (entity == null) {
            throw new DoesNotExistException(holdId);
        }

        return entity.toDto();
    }

    @Override
    public List<String> searchForHoldIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<HoldInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public HoldInfo createHold(String personId,
            String issueId,
            String holdTypeKey,
            HoldInfo holdInfo,
            ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!personId.equals(holdInfo.getPersonId())) {
            throw new InvalidParameterException(personId + " does not match the person Id in the object " + holdInfo.getPersonId());
        }
        if (!issueId.equals(holdInfo.getIssueId())) {
            throw new InvalidParameterException(issueId + " does not match the issueId in the object " + holdInfo.getIssueId());
        }
        if (!holdTypeKey.equals(holdInfo.getTypeKey())) {
            throw new InvalidParameterException(holdTypeKey + " does not match the hold type key in the object " + holdInfo.getTypeKey());
        }
        HoldEntity entity = new HoldEntity(holdInfo);
        entity.setIssue(issueDao.find(issueId));
        if (entity.getIssue() == null) {
            throw new InvalidParameterException(issueId);
        }
        entity.setCreateId(context.getPrincipalId());
        entity.setCreateTime(context.getCurrentDate());
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        holdDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (!holdId.equals(holdInfo.getId())) {
            throw new InvalidParameterException(holdId + " does not match the id in the object " + holdInfo.getId());
        }
        HoldEntity entity = holdDao.find(holdId);
        if (null == entity) {
            throw new DoesNotExistException(holdId);
        }
        entity.fromDto(holdInfo);
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        holdDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public HoldInfo releaseHold(String holdId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        HoldInfo info = this.getHold(holdId, context);
        info.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
        try {
            return updateHold(holdId, info, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteHold(String holdId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        HoldEntity entity = holdDao.find(holdId);
        if (null == entity) {
            throw new DoesNotExistException(holdId);
        }
        holdDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public IssueInfo getIssue(String issueId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueEntity entity = issueDao.find(issueId);
        if (entity == null) {
            throw new DoesNotExistException(issueId);
        }
        return entity.toDto();
    }

    @Override
    public List<String> getIssueIdsByType(String issueTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<String>();
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<IssueEntity> issues = issueDao.getByOrganizationId(organizationId);

        List<IssueInfo> results = new ArrayList<IssueInfo>(issues.size());

        for (IssueEntity issue : issues) {
            results.add(issue.toDto());
        }

        return results;
    }

    @Override
    public List<String> searchForIssueIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<IssueInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey, IssueInfo issueInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public IssueInfo createIssue(String issueTypeKey, IssueInfo issueInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!issueTypeKey.equals(issueInfo.getTypeKey())) {
            throw new InvalidParameterException(issueTypeKey + " does not match type in object " + issueInfo.getTypeKey());
        }
        IssueEntity entity = new IssueEntity(issueInfo);
        entity.setCreateId(context.getPrincipalId());
        entity.setCreateTime(context.getCurrentDate());
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        issueDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public IssueInfo updateIssue(String issueId, IssueInfo issueInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (!issueId.equals(issueInfo.getId())) {
            throw new InvalidParameterException(issueId + " does not match the id in the object " + issueInfo.getId());
        }
        IssueEntity entity = issueDao.find(issueId);
        if (null == entity) {
            throw new DoesNotExistException(issueId);
        }
        entity.fromDto(issueInfo);
        entity.setUpdateId(context.getPrincipalId());
        entity.setUpdateTime(context.getCurrentDate());
        issueDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteIssue(String issueId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueEntity entity = issueDao.find(issueId);
        if (null == entity) {
            throw new DoesNotExistException(issueId);
        }
        issueDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<HoldInfo> getHoldsByIds(List<String> holdIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getHoldIdsByType(String holdTypeKey, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByPerson(String personId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(String issueId, String personId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<IssueInfo> getIssuesByIds(List<String> issueIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<IssueEntity> issues = issueDao.findByIds(issueIds);

        if (issues == null) {
            throw new DoesNotExistException();
        }

        List<IssueInfo> result = new ArrayList<IssueInfo>(issues.size());
        for (IssueEntity entity : issues) {
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null, then one of the keys in the list was not found
                throw new DoesNotExistException();
            }
            result.add(entity.toDto());
        }

        return result;
    }
}
