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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.hold.dao.HoldDao;
import org.kuali.student.enrollment.class1.hold.dao.HoldIssueDao;
import org.kuali.student.enrollment.class1.hold.model.HoldEntity;
import org.kuali.student.enrollment.class1.hold.model.HoldIssueEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(name = "HoldService", serviceName = "HoldService", portName = "HoldService",
targetNamespace = "http://student.kuali.org/wsdl/hold")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class HoldServiceImpl
        implements HoldService {

    private HoldIssueDao holdIssueDao;
    private HoldDao holdDao;

    public HoldIssueDao getHoldIssueDao() {
        return holdIssueDao;
    }

    public void setHoldIssueDao(HoldIssueDao holdIssueDao) {
        this.holdIssueDao = holdIssueDao;
    }

    public HoldDao getHoldDao() {
        return holdDao;
    }

    public void setHoldDao(HoldDao holdDao) {
        this.holdDao = holdDao;
    }

    @Override
    @Transactional(readOnly = true)
    public HoldInfo getHold(String holdId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        HoldEntity entity = holdDao.find(holdId);
        if (entity == null) {
            throw new DoesNotExistException(holdId);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForHoldIds(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey,
            HoldInfo holdInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public HoldInfo createHold(String personId,
            String issueId,
            String holdTypeKey,
            HoldInfo holdInfo,
            ContextInfo context)
            throws
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        holdInfo.setPersonId(personId);
        holdInfo.setIssueId(issueId);
        holdInfo.setTypeKey(holdTypeKey);

        HoldIssueEntity holdIssueEntity = holdIssueDao.find(issueId);
        if (holdIssueEntity == null) {
            throw new InvalidParameterException(issueId);
        }
        HoldEntity entity = new HoldEntity(holdInfo);
        entity.setHoldIssue(holdIssueEntity);
        entity.setEntityCreated(context);
        holdDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public HoldInfo updateHold(String holdId,
            HoldInfo holdInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        if (!holdId.equals(holdInfo.getId())) {
            throw new InvalidParameterException(holdId + " does not match the id in the object " + holdInfo.getId());
        }
        HoldEntity entity = holdDao.find(holdId);
        if (null == entity) {
            throw new DoesNotExistException(holdId);
        }
        entity.fromDto(holdInfo);
        entity.setEntityUpdated(context);
        entity = holdDao.merge(entity);
        holdDao.getEm().flush(); // need to flush to get the version indicator updated
        return entity.toDto();
    }

    @Override
    @Transactional
    public HoldInfo releaseHold(String holdId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        HoldInfo info = this.getHold(holdId, context);
        info.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
        info.setReleasedDate(new Date());
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
    public StatusInfo deleteHold(String holdId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
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
    @Transactional(readOnly = true)
    public IssueInfo getIssue(String issueId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        HoldIssueEntity entity = holdIssueDao.find(issueId);
        if (entity == null) {
            throw new DoesNotExistException(issueId);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getIssueIdsByType(String issueTypeKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return holdIssueDao.getIdsByType(issueTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueInfo> getIssuesByOrg(String organizationId,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldIssueEntity> holdIssues = holdIssueDao.getByOrganizationId(organizationId);
        List<IssueInfo> results = new ArrayList<IssueInfo>(holdIssues.size());
        for (HoldIssueEntity holdIssue : holdIssues) {
            results.add(holdIssue.toDto());
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForIssueIds(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey,
            IssueInfo issueInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public IssueInfo createIssue(String issueTypeKey,
            IssueInfo issueInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        issueInfo.setTypeKey(issueTypeKey);
        HoldIssueEntity entity = new HoldIssueEntity(issueInfo);
        entity.setEntityCreated(context);
        holdIssueDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public IssueInfo updateIssue(String issueId,
            IssueInfo issueInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        if (!issueId.equals(issueInfo.getId())) {
            throw new InvalidParameterException(issueId + " does not match the id in the object " + issueInfo.getId());
        }
        HoldIssueEntity entity = holdIssueDao.find(issueId);
        if (null == entity) {
            throw new DoesNotExistException(issueId);
        }
        entity.fromDto(issueInfo);
        entity.setEntityUpdated(context);
        entity = holdIssueDao.merge(entity);
        holdIssueDao.getEm().flush(); // need to flush to get the version indicator updated
        return entity.toDto();
    }

    @Override
    @Transactional
    public StatusInfo deleteIssue(String issueId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DependentObjectsExistException  {
        HoldIssueEntity entity = holdIssueDao.find(issueId);
        if (null == entity) {
            throw new DoesNotExistException(issueId);
        }
        List<String> list = this.getHoldIdsByIssue(issueId, contextInfo);
        if (!list.isEmpty()) {
            throw new DependentObjectsExistException(list.size() + " hold(s) with this issue");
        }
        holdIssueDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldInfo> getHoldsByIds(List<String> holdIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldEntity> entities = holdDao.findByIds(holdIds);
        List<HoldInfo> result = new ArrayList<HoldInfo>(entities.size());
        for (HoldEntity entity : entities) {
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null, then one of the keys in the list was not found
                throw new DoesNotExistException();
            }
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getHoldIdsByType(String holdTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return holdDao.getIdsByType(holdTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getHoldIdsByIssue(String issueId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return holdDao.getIdsByIssue(issueId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldInfo> getHoldsByPerson(String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldEntity> entities = this.holdDao.getByPerson(personId);
        List<HoldInfo> result = new ArrayList<HoldInfo>(entities.size());
        for (HoldEntity entity : entities) {
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldInfo> getActiveHoldsByPerson(String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Date now = new Date();
        List<HoldEntity> entities = this.holdDao.getByPersonAndState(personId, HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        List<HoldInfo> result = new ArrayList<HoldInfo>(entities.size());
        for (HoldEntity entity : entities) {
            HoldInfo info = entity.toDto();
            if (info.getEffectiveDate().before(now)) {
                if (info.getReleasedDate() == null || info.getReleasedDate().after(now)) {
                    result.add(info);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldInfo> getHoldsByIssueAndPerson(String issueId,
            String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldEntity> entities = this.holdDao.getByIssueAndPerson(issueId, personId);
        List<HoldInfo> result = new ArrayList<HoldInfo>(entities.size());
        for (HoldEntity entity : entities) {
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(String issueId,
            String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Date now = new Date();
        List<HoldEntity> entities = this.holdDao.getByIssuePersonAndState(issueId, personId,
                HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        List<HoldInfo> result = new ArrayList<HoldInfo>(entities.size());
        for (HoldEntity entity : entities) {
            HoldInfo info = entity.toDto();
            if (info.getEffectiveDate().before(now)) {
                if (info.getReleasedDate() == null || info.getReleasedDate().after(now)) {
                    result.add(info);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueInfo> getIssuesByIds(List<String> issueIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldIssueEntity> holdIssues = holdIssueDao.findByIds(issueIds);
        List<IssueInfo> result = new ArrayList<IssueInfo>(holdIssues.size());
        for (HoldIssueEntity entity : holdIssues) {
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null, then one of the keys in the list was not found
                throw new DoesNotExistException();
            }
            result.add(entity.toDto());
        }
        return result;
    }
}
