/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;

import org.kuali.student.r2.common.dto.BulkStatusInfo;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

public class CourseRegistrationServiceMockImpl
    extends AbstractCourseRegistrationService
    implements CourseRegistrationService {

    private final Map<String, CourseRegistrationInfo> crMap   = new LinkedHashMap<String, CourseRegistrationInfo>();
    private final Map<String, ActivityRegistrationInfo> arMap = new LinkedHashMap<String, ActivityRegistrationInfo>();

    private CourseOfferingService coService;

    public CourseOfferingService getCourseOfferingService() {
        return coService;
    }

    public void setCourseOfferingService(CourseOfferingService coService) {
        this.coService = coService;
    }

    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        CourseRegistrationInfo crInfo = this.crMap.get(courseRegistrationId);
        if (crInfo == null) {
            throw new DoesNotExistException(courseRegistrationId + " does not exist");
        }

        return (crInfo);
    }

    public List<CourseRegistrationInfo> getCourseRegistrations(ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (new ArrayList<CourseRegistrationInfo>(this.crMap.values()));
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(List<String> courseRegistrationIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (String id : courseRegistrationIds) {
            ret.add(getCourseRegistration(id, contextInfo));
        }
        
        return (ret);
    }

    @Override
    public List<String> getCourseRegistrationIdsByType (String courseRegistrationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<String> ret = new ArrayList<String>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getType().equals(courseRegistrationTypeKey)) {
                ret.add(cr.getId());
            }
        }

        return (ret);
    }
    
    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(String studentId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getStudentId().equals(studentId)) {
                ret.add(cr);
            }
        }

        return (ret);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(String courseOfferingId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getCourseOfferingId().equals(courseOfferingId)) {
                ret.add(cr);
            }
        }

        return (ret);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(String studentId, String courseOfferingId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrationsByStudent(studentId, contextInfo)) {
            if (cr.getCourseOfferingId().equals(courseOfferingId)) {
                ret.add(cr);
            }
        }

        return (ret);
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(String studentId, String termId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrationsByStudent(studentId, contextInfo)) {
            try {
                if (getCourseOfferingService().getCourseOffering(cr.getCourseOfferingId(), contextInfo).getTermId().equals(termId)) {
                    ret.add(cr);
                }
            } catch (DoesNotExistException dne) {
                throw new OperationFailedException("bad stuff in reg", dne);
            }
        }

        return (ret);
    }

    @Override
    public List<String> searchForCourseRegistrationIds(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<String> ret = new ArrayList<String>();
        for (CourseRegistrationInfo cr : searchForCourseRegistrations(criteria, contextInfo)) {
            ret.add(cr.getId());
        }

        return (ret);
    }

    @Override
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        return (getCourseRegistrations(contextInfo));
    }

    private MetaInfo newMeta(ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(contextInfo.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
