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
package org.kuali.student.r2.lum.coursebundle.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.lum.coursebundle.dto.CourseBundleInfo;
import org.kuali.student.lum.coursebundle.service.CourseBundleService;
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


public class CourseBundleServiceMapImpl implements MockService, CourseBundleService {
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, CourseBundleInfo> courseBundleMap = new LinkedHashMap<String, CourseBundleInfo>();

    @Override
    public void clear() {
        this.courseBundleMap.clear();
    }


    @Override
    public CourseBundleInfo getCourseBundle(String courseBundleId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_ID
        if (!this.courseBundleMap.containsKey(courseBundleId)) {
            throw new DoesNotExistException(courseBundleId);
        }
        return new CourseBundleInfo(this.courseBundleMap.get(courseBundleId));
    }

    @Override
    public List<CourseBundleInfo> getCourseBundlesByIds(List<String> courseBundleIds, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_IDS
        List<CourseBundleInfo> list = new ArrayList<CourseBundleInfo>();
        for (String id : courseBundleIds) {
            list.add(this.getCourseBundle(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getCourseBundleIdsByType(String courseBundleTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (CourseBundleInfo info : courseBundleMap.values()) {
            if (courseBundleTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForCourseBundleIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCourseBundleIds has not been implemented");
    }

    @Override
    public List<CourseBundleInfo> searchForCourseBundles(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCourseBundles has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateCourseBundle(String validationTypeKey, String courseBundleTypeKey, CourseBundleInfo courseBundleInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CourseBundleInfo createCourseBundle(String courseBundleTypeKey, CourseBundleInfo courseBundleInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // CREATE
        if (!courseBundleTypeKey.equals(courseBundleInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        CourseBundleInfo copy = new CourseBundleInfo(courseBundleInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        courseBundleMap.put(copy.getId(), copy);
        return new CourseBundleInfo(copy);
    }

    @Override
    public CourseBundleInfo updateCourseBundle(String courseBundleId, CourseBundleInfo courseBundleInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // UPDATE
        if (!courseBundleId.equals(courseBundleInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CourseBundleInfo copy = new CourseBundleInfo(courseBundleInfo);
        CourseBundleInfo old = this.getCourseBundle(courseBundleInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.courseBundleMap.put(courseBundleInfo.getId(), copy);
        return new CourseBundleInfo(copy);
    }

    @Override
    public StatusInfo changeCourseBundleState(String courseBundleId, String stateKey, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        CourseBundleInfo info = getCourseBundle(courseBundleId, contextInfo);
        info.setStateKey(stateKey);
        try {
            updateCourseBundle(courseBundleId, info, contextInfo);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Failed to update course bundle with new state", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Failed to update course bundle with new state", e);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Failed to update course bundle with new state", e);
        }
        return new StatusInfo();
    }

    @Override
    public StatusInfo deleteCourseBundle(String courseBundleId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // DELETE
        if (this.courseBundleMap.remove(courseBundleId) == null) {
            throw new OperationFailedException(courseBundleId);
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

