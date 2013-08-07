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

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.dto.CreditLoadInfo;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

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

public abstract class AbstractCourseRegistrationService
    implements CourseRegistrationService {


    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(List<String> courseRegistrationIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> getCourseRegistrationIdsByType(String courseRegistrationTypeKey,ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(String studentId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(String courseOfferingId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(String studentId, String courseOfferingId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(String studentId, String termId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<String> searchForCourseRegistrationIds(QueryByCriteria criteria,ContextInfo contextInfo)
        throws InvalidParameterException,MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException,MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public ActivityRegistrationInfo getActivityRegistration(String activityRegistrationId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByIds(List<String> activityRegistrationIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> getActivityRegistrationIdsByType(String activityRegistrationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudent(String studentId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByActivityOffering(String courseOfferingId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndActivityOffering(String studentId, String courseOfferingId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndTerm(String studentId, String termId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }


    @Override
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestInfo> getRegistrationRequestsByIds(List<String> registrationRequestIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> getRegistrationRequestIdsByType(String registrationRequestTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestInfo> getRegistrationRequestsByRequestor(String personId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestInfo> getUnsubmittedRegistrationRequestsByRequestorAndTerm(String requestorId, String termId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<String> searchForRegistrationRequestIds(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException,MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestInfo> searchForRegistrationRequests(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationRequest(String validationTypeKey, String registrationRequestTypeKey, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        throw new OperationFailedException("unimplemented");
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequest(String registrationRequestTypeKey, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequestFromExisting(String registrationRequestId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public RegistrationRequestInfo updateRegistrationRequest(String registrationRequestId, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public StatusInfo deleteRegistrationRequest(String registrationRequestId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(String registrationRequestId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public RegistrationResponseInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo) 
        throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsForCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsByCourseOfferingAndStudent(String courseOfferingId, String studentId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String studentId, String termId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForRegistrationGroup(String studentId, String registrationGroupId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationGroupInfo> getEligibleRegistrationGroupsForStudentInCourseOffering(String studentId, String courseOfferingId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");

    }

    @Override
    public CreditLoadInfo calculateCreditLoadForStudentRegistrationRequest(String registrationRequestId, String studentId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }
}
