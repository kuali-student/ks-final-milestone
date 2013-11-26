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
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.bundledoffering.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.bundledoffering.dto.BundledOfferingInfo;
import org.kuali.student.enrollment.bundledoffering.service.BundledOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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


public class BundledOfferingServiceMapImpl implements MockService, BundledOfferingService {
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, BundledOfferingInfo> bundledOfferingMap = new LinkedHashMap<String, BundledOfferingInfo>();

    @Override
    public void clear() {
        this.bundledOfferingMap.clear();
    }


    @Override
    public BundledOfferingInfo getBundledOffering(String bundledOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_ID
        if (!this.bundledOfferingMap.containsKey(bundledOfferingId)) {
            throw new DoesNotExistException(bundledOfferingId);
        }
        return new BundledOfferingInfo(this.bundledOfferingMap.get(bundledOfferingId));
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByIds(List<String> bundledOfferingIds, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_IDS
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (String id : bundledOfferingIds) {
            list.add(this.getBundledOffering(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getBundledOfferingIdsByType(String bundledOfferingTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if (bundledOfferingTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByCourseBundle(String courseBundleId, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if (courseBundleId.equals(info.getCourseBundleId())) {
                list.add(new BundledOfferingInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByTerm(String termId, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                list.add(new BundledOfferingInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByCourseBundleAndTerm(String courseBundleId, String termId, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if (courseBundleId.equals(info.getCourseBundleId())) {
                if (termId.equals(info.getTermId())) {
                    list.add(new BundledOfferingInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByRegistrationGroup(String registrationGroupId, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if(info.getRegistrationGroupIds().contains(registrationGroupId)) {
                list.add(new BundledOfferingInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByTermAndCode(String termId, String code, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (code.equals(info.getBundledOfferingCode())) {
                    list.add(new BundledOfferingInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<BundledOfferingInfo> getBundledOfferingsByTermAndSubjectAreaOrg(String termId, String subjectAreaOrgId, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<BundledOfferingInfo> list = new ArrayList<BundledOfferingInfo>();
        for (BundledOfferingInfo info : bundledOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (subjectAreaOrgId.equals(info.getSubjectAreaOrgId())) {
                    list.add(new BundledOfferingInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForBundledOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForBundledOfferingIds has not been implemented");
    }

    @Override
    public List<BundledOfferingInfo> searchForBundledOfferings(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForBundledOfferings has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateBundledOffering(String validationTypeKey, String courseBundleId, String termId, String bundledOfferingTypeKey, BundledOfferingInfo bundledOfferingInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public BundledOfferingInfo createBundledOffering(String courseBundleId, String termId, String bundledOfferingTypeKey, BundledOfferingInfo bundledOfferingInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // CREATE
        if (!bundledOfferingTypeKey.equals(bundledOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        if (!courseBundleId.equals(bundledOfferingInfo.getCourseBundleId())) {
            throw new InvalidParameterException("The course bundle id parameter does not match the value in the info object");
        }
        if (!termId.equals(bundledOfferingInfo.getTermId())) {
            throw new InvalidParameterException("The term id parameter does not match the value in the info object");
        }
        BundledOfferingInfo copy = new BundledOfferingInfo(bundledOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        bundledOfferingMap.put(copy.getId(), copy);
        return new BundledOfferingInfo(copy);
    }

    @Override
    public BundledOfferingInfo updateBundledOffering(String bundledOfferingId, BundledOfferingInfo bundledOfferingInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // UPDATE
        if (!bundledOfferingId.equals(bundledOfferingInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        BundledOfferingInfo copy = new BundledOfferingInfo(bundledOfferingInfo);
        BundledOfferingInfo old = this.getBundledOffering(bundledOfferingInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.bundledOfferingMap.put(bundledOfferingInfo.getId(), copy);
        return new BundledOfferingInfo(copy);
    }

    @Override
    public StatusInfo changeBundledOfferingState(String bundledOfferingId, String stateKey, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
      BundledOfferingInfo info = getBundledOffering(bundledOfferingId, contextInfo);
      info.setStateKey(stateKey);
        try {
            updateBundledOffering(bundledOfferingId, info, contextInfo);
        } catch (DataValidationErrorException e) {
           throw new OperationFailedException("Failed to update bundled offering with new state", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Failed to update bundled offering with new state", e);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Failed to update bundled offering with new state", e);
        }
        return new StatusInfo();
    }

    @Override
    public StatusInfo deleteBundledOffering(String bundledOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // DELETE
        if (this.bundledOfferingMap.remove(bundledOfferingId) == null) {
            throw new OperationFailedException(bundledOfferingId);
        }
        return new StatusInfo();
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

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

}

