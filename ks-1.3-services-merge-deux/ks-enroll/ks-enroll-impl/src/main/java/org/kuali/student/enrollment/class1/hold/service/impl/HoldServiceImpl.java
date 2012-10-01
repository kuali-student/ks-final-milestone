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

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.hold.dao.AppliedHoldDao;
import org.kuali.student.enrollment.class1.hold.dao.HoldIssueDao;
import org.kuali.student.enrollment.class1.hold.model.AppliedHoldEntity;
import org.kuali.student.enrollment.class1.hold.model.HoldIssueEntity;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
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
    private AppliedHoldDao appliedHoldDao;
    private CriteriaLookupService criteriaLookupService;

    public HoldIssueDao getHoldIssueDao() {
        return holdIssueDao;
    }

    public void setHoldIssueDao(HoldIssueDao holdIssueDao) {
        this.holdIssueDao = holdIssueDao;
    }

    public AppliedHoldDao getAppliedHoldDao() {
        return appliedHoldDao;
    }

    public void setAppliedHoldDao(AppliedHoldDao appliedHoldDao) {
        this.appliedHoldDao = appliedHoldDao;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    @Override
    @Transactional(readOnly = true)
    public AppliedHoldInfo getAppliedHold(String holdId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        AppliedHoldEntity entity = appliedHoldDao.find(holdId);
        if (entity == null) {
            throw new DoesNotExistException(holdId);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForAppliedHoldIds(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<AppliedHoldEntity> appliedHolds = criteriaLookupService.lookup(AppliedHoldEntity.class, criteria);
        if (null != appliedHolds && appliedHolds.getResults().size() > 0) {
            for (AppliedHoldEntity appliedHold : appliedHolds.getResults()) {
                results.add(appliedHold.getId());
            }
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppliedHoldInfo> searchForAppliedHolds(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AppliedHoldInfo> results = new ArrayList<AppliedHoldInfo>();
        GenericQueryResults<AppliedHoldEntity> appliedHolds = criteriaLookupService.lookup(AppliedHoldEntity.class, criteria);
        if (null != appliedHolds && appliedHolds.getResults().size() > 0) {
            for (AppliedHoldEntity appliedHold : appliedHolds.getResults()) {
                results.add(appliedHold.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateAppliedHold(String validationTypeKey,
            AppliedHoldInfo holdInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public AppliedHoldInfo createAppliedHold(String personId,
            String issueId,
            String holdTypeKey,
            AppliedHoldInfo holdInfo,
            ContextInfo context)
            throws
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        holdInfo.setPersonId(personId);
        holdInfo.setHoldIssueId(issueId);
        holdInfo.setTypeKey(holdTypeKey);

        HoldIssueEntity holdIssueEntity = holdIssueDao.find(issueId);
        if (holdIssueEntity == null) {
            throw new InvalidParameterException(issueId);
        }
        AppliedHoldEntity entity = new AppliedHoldEntity(holdInfo);
        entity.setHoldIssue(holdIssueEntity);
        entity.setEntityCreated(context);
        appliedHoldDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional
    public AppliedHoldInfo updateAppliedHold(String holdId,
            AppliedHoldInfo holdInfo,
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
        AppliedHoldEntity entity = appliedHoldDao.find(holdId);
        if (null == entity) {
            throw new DoesNotExistException(holdId);
        }
        entity.fromDto(holdInfo);
        entity.setEntityUpdated(context);
        entity = appliedHoldDao.merge(entity);
        appliedHoldDao.getEm().flush(); // need to flush to get the version indicator updated
        return entity.toDto();
    }

    @Override
    @Transactional
    public AppliedHoldInfo releaseAppliedHold(String holdId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        AppliedHoldInfo info = this.getAppliedHold(holdId, context);
        info.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
        info.setReleasedDate(new Date());
        try {
            return updateAppliedHold(holdId, info, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteAppliedHold(String holdId,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        AppliedHoldEntity entity = appliedHoldDao.find(holdId);
        if (null == entity) {
            throw new DoesNotExistException(holdId);
        }
        appliedHoldDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public HoldIssueInfo getHoldIssue(String issueId,
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
    public List<String> getHoldIssueIdsByType(String issueTypeKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return holdIssueDao.getIdsByType(issueTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldIssueInfo> getHoldIssuesByOrg(String organizationId,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldIssueEntity> holdIssues = holdIssueDao.getByOrganizationId(organizationId);
        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>(holdIssues.size());
        for (HoldIssueEntity holdIssue : holdIssues) {
            results.add(holdIssue.toDto());
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForHoldIssueIds(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<HoldIssueEntity> holdIssues = criteriaLookupService.lookup(HoldIssueEntity.class, criteria);
        if (null != holdIssues && holdIssues.getResults().size() > 0) {
            for (HoldIssueEntity holdIssue : holdIssues.getResults()) {
                results.add(holdIssue.getId());
            }
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoldIssueInfo> searchForHoldIssues(QueryByCriteria criteria,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>();
        GenericQueryResults<HoldIssueEntity> holdIssues = criteriaLookupService.lookup(HoldIssueEntity.class, criteria);
        if (null != holdIssues && holdIssues.getResults().size() > 0) {
            for (HoldIssueEntity holdIssue : holdIssues.getResults()) {
                results.add(holdIssue.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateHoldIssue(String validationTypeKey,
            HoldIssueInfo issueInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public HoldIssueInfo createHoldIssue(String issueTypeKey,
            HoldIssueInfo issueInfo,
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
    public HoldIssueInfo updateHoldIssue(String issueId,
            HoldIssueInfo issueInfo,
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
    public StatusInfo deleteHoldIssue(String issueId,
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
        List<String> list = this.getAppliedHoldIdsByIssue(issueId, contextInfo);
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
    public List<AppliedHoldInfo> getAppliedHoldsByIds(List<String> holdIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AppliedHoldEntity> entities = appliedHoldDao.findByIds(holdIds);
        List<AppliedHoldInfo> result = new ArrayList<AppliedHoldInfo>(entities.size());
        for (AppliedHoldEntity entity : entities) {
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
    public List<String> getAppliedHoldIdsByType(String holdTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return appliedHoldDao.getIdsByType(holdTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppliedHoldIdsByIssue(String issueId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return appliedHoldDao.getIdsByIssue(issueId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppliedHoldInfo> getAppliedHoldsByPerson(String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AppliedHoldEntity> entities = this.appliedHoldDao.getByPerson(personId);
        List<AppliedHoldInfo> result = new ArrayList<AppliedHoldInfo>(entities.size());
        for (AppliedHoldEntity entity : entities) {
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppliedHoldInfo> getActiveAppliedHoldsByPerson(String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Date now = new Date();
        List<AppliedHoldEntity> entities = this.appliedHoldDao.getByPersonAndState(personId, HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        List<AppliedHoldInfo> result = new ArrayList<AppliedHoldInfo>(entities.size());
        for (AppliedHoldEntity entity : entities) {
            AppliedHoldInfo info = entity.toDto();
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
    public List<AppliedHoldInfo> getAppliedHoldsByIssueAndPerson(String issueId,
            String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<AppliedHoldEntity> entities = this.appliedHoldDao.getByIssueAndPerson(issueId, personId);
        List<AppliedHoldInfo> result = new ArrayList<AppliedHoldInfo>(entities.size());
        for (AppliedHoldEntity entity : entities) {
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppliedHoldInfo> getActiveAppliedHoldsByIssueAndPerson(String issueId,
            String personId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Date now = new Date();
        List<AppliedHoldEntity> entities = this.appliedHoldDao.getByIssuePersonAndState(issueId, personId,
                HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        List<AppliedHoldInfo> result = new ArrayList<AppliedHoldInfo>(entities.size());
        for (AppliedHoldEntity entity : entities) {
            AppliedHoldInfo info = entity.toDto();
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
    public List<HoldIssueInfo> getHoldIssuesByIds(List<String> issueIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<HoldIssueEntity> holdIssues = holdIssueDao.findByIds(issueIds);
        List<HoldIssueInfo> result = new ArrayList<HoldIssueInfo>(holdIssues.size());
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
