/*
 * Copyright 2012 The Kuali Foundation
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

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.*;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.util.*;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import static org.kuali.student.poc.rules.credit.limit.ActionEnum.CREATE;
import static org.kuali.student.poc.rules.credit.limit.ActionEnum.UPDATE;
import org.kuali.student.poc.rules.credit.limit.ActivityRegistrationTransaction;
import org.kuali.student.poc.rules.credit.limit.CourseRegistrationTransaction;
import org.kuali.student.poc.rules.credit.limit.RegistrationRequestMerger;

public class CourseRegistrationServiceMapImpl
        implements CourseRegistrationService, MockService {

    private static final Logger LOGGER = Logger.getLogger(CourseRegistrationServiceMapImpl.class);
    private final Map<String, CourseRegistrationInfo> crMap = new LinkedHashMap<String, CourseRegistrationInfo>();
    private final Map<String, ActivityRegistrationInfo> arMap = new LinkedHashMap<String, ActivityRegistrationInfo>();
    private final Map<String, RegistrationRequestInfo> rrMap = new LinkedHashMap<String, RegistrationRequestInfo>();
    private CourseOfferingService courseOfferingService;
    private RegistrationRequestMerger merger;

    @Override
    public void clear() {
        this.arMap.clear();
        this.crMap.clear();
        this.rrMap.clear();
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService coService) {
        this.courseOfferingService = coService;
    }

    public RegistrationRequestMerger getMerger() {
        return merger;
    }

    public void setMerger(RegistrationRequestMerger merger) {
        this.merger = merger;
    }

    @Override
    public CourseRegistrationInfo getCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        CourseRegistrationInfo crInfo = this.crMap.get(courseRegistrationId);
        if (crInfo == null) {
            throw new DoesNotExistException(courseRegistrationId + " does not exist");
        }
        return new CourseRegistrationInfo(crInfo);
    }

    public List<CourseRegistrationInfo> getCourseRegistrations(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<CourseRegistrationInfo> list = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo info : this.crMap.values()) {
            list.add(new CourseRegistrationInfo(info));
        }
        return new ArrayList<CourseRegistrationInfo>(this.crMap.values());
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(List<String> courseRegistrationIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (String id : courseRegistrationIds) {
            ret.add(getCourseRegistration(id, contextInfo));
        }

        return ret;
    }

    @Override
    public List<String> getCourseRegistrationIdsByType(String courseRegistrationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> ret = new ArrayList<String>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getTypeKey().equals(courseRegistrationTypeKey)) {
                ret.add(cr.getId());
            }
        }
        return ret;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(String studentId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getPersonId().equals(studentId)) {
                ret.add(cr);
            }
        }
        return ret;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(String courseOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getCourseOfferingId().equals(courseOfferingId)) {
                ret.add(cr);
            }
        }
        return ret;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(String studentId,
            String courseOfferingId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrationsByStudent(studentId, contextInfo)) {
            if (cr.getCourseOfferingId().equals(courseOfferingId)) {
                ret.add(new CourseRegistrationInfo(cr));
            }
        }
        return ret;
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(String studentId, String termId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrationsByStudent(studentId, contextInfo)) {
            try {
                if (getCourseOfferingService().getCourseOffering(cr.getCourseOfferingId(), contextInfo).getTermId().equals(termId)) {
                    ret.add(new CourseRegistrationInfo(cr));
                }
            } catch (DoesNotExistException dne) {
                throw new OperationFailedException("bad stuff in reg", dne);
            }
        }
        return ret;
    }

    @Override
    public List<String> searchForCourseRegistrationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<String> ret = new ArrayList<String>();
        for (CourseRegistrationInfo cr : searchForCourseRegistrations(criteria, contextInfo)) {
            ret.add(cr.getId());
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public ActivityRegistrationInfo getActivityRegistration(String activityRegistrationId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ActivityRegistrationInfo arInfo = this.arMap.get(activityRegistrationId);
        if (arInfo == null) {
            throw new DoesNotExistException(activityRegistrationId + " does not exist");
        }
        return new ActivityRegistrationInfo(arInfo);
    }

    public List<ActivityRegistrationInfo> getActivityRegistrations(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ActivityRegistrationInfo> list = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo info : this.arMap.values()) {
            list.add(new ActivityRegistrationInfo(info));
        }
        return list;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByIds(List<String> activityRegistrationIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (String id : activityRegistrationIds) {
            ret.add(getActivityRegistration(id, contextInfo));
        }
        return ret;
    }

    @Override
    public List<String> getActivityRegistrationIdsByType(String activityRegistrationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> ret = new ArrayList<String>();
        for (ActivityRegistrationInfo ar : getActivityRegistrations(contextInfo)) {
            if (ar.getTypeKey().equals(activityRegistrationTypeKey)) {
                ret.add(ar.getId());
            }
        }
        return ret;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegistration(String courseRegistrationId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudent(String studentId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrations(contextInfo)) {
            if (ar.getPersonId().equals(studentId)) {
                ret.add(new ActivityRegistrationInfo(ar));
            }
        }
        return ret;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByActivityOffering(String activityOfferingId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrations(contextInfo)) {
            if (ar.getActivityOfferingId().equals(activityOfferingId)) {
                ret.add(ar);
            }
        }
        return ret;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndActivityOffering(String studentId,
            String activityOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrationsByStudent(studentId, contextInfo)) {
            if (ar.getActivityOfferingId().equals(activityOfferingId)) {
                ret.add(ar);
            }
        }
        return ret;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndTerm(String studentId, String termId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrationsByStudent(studentId, contextInfo)) {
            try {
                if (getCourseOfferingService().getActivityOffering(ar.getActivityOfferingId(), contextInfo).getTermId().equals(
                        termId)) {
                    ret.add(ar);
                }
            } catch (DoesNotExistException dne) {
                throw new OperationFailedException("bad stuff in reg", dne);
            }
        }
        return ret;
    }

    @Override
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> ret = new ArrayList<String>();
        for (ActivityRegistrationInfo cr : searchForActivityRegistrations(criteria, contextInfo)) {
            ret.add(cr.getId());
        }
        return ret;
    }

    @Override
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        RegistrationRequestInfo rrInfo = this.rrMap.get(registrationRequestId);
        if (rrInfo == null) {
            throw new DoesNotExistException(registrationRequestId + " does not exist");
        }
        return new RegistrationRequestInfo(rrInfo);
    }

    public List<RegistrationRequestInfo> getRegistrationRequests(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<RegistrationRequestInfo> list = new ArrayList<RegistrationRequestInfo>();
        for (RegistrationRequestInfo info : this.rrMap.values()) {
            list.add(new RegistrationRequestInfo(info));
        }
        return list;
    }

    @Override
    public List<RegistrationRequestInfo> getRegistrationRequestsByIds(List<String> registrationRequestIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<RegistrationRequestInfo> ret = new ArrayList<RegistrationRequestInfo>();
        for (String id : registrationRequestIds) {
            ret.add(getRegistrationRequest(id, contextInfo));
        }
        return ret;
    }

    @Override
    public List<String> getRegistrationRequestIdsByType(String registrationRequestTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> ret = new ArrayList<String>();
        for (RegistrationRequestInfo rr : getRegistrationRequests(contextInfo)) {
            if (rr.getTypeKey().equals(registrationRequestTypeKey)) {
                ret.add(rr.getId());
            }
        }
        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<RegistrationRequestInfo> getRegistrationRequestsByRequestor(String personId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<RegistrationRequestInfo> ret = new ArrayList<RegistrationRequestInfo>();
        for (RegistrationRequestInfo rr : getRegistrationRequests(contextInfo)) {
            if (rr.getRequestorId().equals(personId)) {
                ret.add(new RegistrationRequestInfo(rr));
            }
        }
        return ret;
    }

    @Override
    public List<RegistrationRequestInfo> getUnsubmittedRegistrationRequestsByRequestorAndTerm(String requestorId, String termId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<RegistrationRequestInfo> ret = new ArrayList<RegistrationRequestInfo>();
        for (RegistrationRequestInfo rr : getRegistrationRequestsByRequestor(requestorId, contextInfo)) {
            if (rr.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY) && rr.getTermId().equals(termId)) {
                ret.add(new RegistrationRequestInfo(rr));
            }
        }
        return ret;
    }

    @Override
    public List<String> searchForRegistrationRequestIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> ret = new ArrayList<String>();
        for (RegistrationRequestInfo rr : searchForRegistrationRequests(criteria, contextInfo)) {
            ret.add(rr.getId());
        }
        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationRequest(String validationTypeKey, String registrationRequestTypeKey,
            RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequest(String registrationRequestTypeKey,
            RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        List<ValidationResultInfo> results = validateRegistrationRequest("any", registrationRequestTypeKey,
                registrationRequestInfo, contextInfo);
        if (results.size() > 0) {
            throw new DataValidationErrorException("data validation error, use validateRegistrationRequest()");
        }
        RegistrationRequestInfo rr = new RegistrationRequestInfo(registrationRequestInfo);
        rr.setId(newId());
        rr.setTypeKey(registrationRequestTypeKey);
        rr.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        rr.setMeta(newMeta(contextInfo));
        this.rrMap.put(rr.getId(), rr);
        return new RegistrationRequestInfo(rr);
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequestFromExisting(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        RegistrationRequestInfo rr = new RegistrationRequestInfo(getRegistrationRequest(registrationRequestId, contextInfo));
        rr.setMeta(null);
        rr.setId(newId());
        rr.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        List<ValidationResultInfo> results = validateRegistrationRequest("any", rr.getTypeKey(), rr, contextInfo);
        if (results.size() > 0) {
            throw new OperationFailedException("data validation error in copy");
        }
        this.rrMap.put(rr.getId(), rr);
        return new RegistrationRequestInfo(rr);
    }

    @Override
    public RegistrationRequestInfo updateRegistrationRequest(String registrationRequestId,
            RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        if (!registrationRequestId.equals(registrationRequestInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        RegistrationRequestInfo copy = new RegistrationRequestInfo(registrationRequestInfo);
        RegistrationRequestInfo old = getRegistrationRequest(registrationRequestId, contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.rrMap.put(registrationRequestId, copy);
        return new RegistrationRequestInfo(copy);
    }

    @Override
    public StatusInfo deleteRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        RegistrationRequest rr = getRegistrationRequest(registrationRequestId, contextInfo);
        if (rr.getStateKey().equals(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY)
                || rr.getStateKey().equals(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY)) {
            throw new OperationFailedException("cannot delete a submitted registration request, sorry");
        }
        this.rrMap.remove(registrationRequestId);
        return new StatusInfo();
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        // done in verification decorator
        return Collections.EMPTY_LIST;
    }

    @Override
    public RegistrationResponseInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        // look up the registration request
        RegistrationRequestInfo rr = getRegistrationRequest(registrationRequestId, contextInfo);
        if (!rr.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY)) {
            throw new AlreadyExistsException(registrationRequestId + " already submitted");
        }

        // verify the registration request
        List<ValidationResultInfo> results = verifyRegistrationRequestForSubmission(registrationRequestId, contextInfo);
        if (results.size() > 0) {
            rr.setStateKey(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY);
            throw new OperationFailedException("registration failed, try verifying next time");
        }
        // 
        //TODO: investigate how the requestor id is not the same as the student id on each of the items!
        //THIS only supports the reg. requests for the same student.  
//        TODO: support bulk registration changes to multiple students.
        List<CourseRegistrationInfo> registrations = this.getCourseRegistrationsByStudentAndTerm(rr.getRequestorId(),
                rr.getTermId(), contextInfo);
        List<CourseRegistrationTransaction> trans = this.getMerger().merge(rr, registrations, false, contextInfo);
        for (CourseRegistrationTransaction tran : trans) {
            CourseRegistrationInfo reg = tran.getRegistration();
            switch (tran.getAction()) {
                case CREATE:
                    reg.setId(this.newId());
                    reg.setMeta(this.newMeta(contextInfo));
                    this.crMap.put(tran.getRegistration().getId(), reg);
                    this._processActivityOfferingTransactions(tran.getActivityRegistrationTransactions(), contextInfo);
                    break;
                case UPDATE:
                    CourseRegistrationInfo old = this.getCourseRegistration(reg.getId(), contextInfo);
                    if (!old.getMeta().getVersionInd().equals(reg.getMeta().getVersionInd())) {
                        throw new OperationFailedException("versionMismatch" + old.getMeta().getVersionInd());
                    }
                    reg.setMeta(updateMeta(reg.getMeta(), contextInfo));
                    this.crMap.put(tran.getRegistration().getId(), reg);
                    this._processActivityOfferingTransactions(tran.getActivityRegistrationTransactions(), contextInfo);
                    break;
                case DELETE:
                    this.crMap.remove(tran.getRegistration().getId());
                    this._processActivityOfferingTransactions(tran.getActivityRegistrationTransactions(), contextInfo);
                    break;
            }
        }

        /* create the response */
        RegistrationResponseInfo response = new RegistrationResponseInfo();
        response.setRegistrationRequestId(rr.getId());

        /* cddr through the list of request items */
        for (RegistrationRequestItemInfo item : rr.getRegistrationRequestItems()) {
        }

        rr.setStateKey(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY);

        OperationStatusInfo status = new OperationStatusInfo();
        status.setStatus("ok");

        return response;
    }

    private void _processActivityOfferingTransactions(List<ActivityRegistrationTransaction> arts, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        for (ActivityRegistrationTransaction tran : arts) {
            ActivityRegistrationInfo reg = tran.getRegistration();
            switch (tran.getAction()) {
                case CREATE:
                    reg.setId(this.newId());
                    reg.setMeta(this.newMeta(contextInfo));
                    this.arMap.put(tran.getRegistration().getId(), reg);
                    break;
                case UPDATE:
                    ActivityRegistrationInfo old = this.getActivityRegistration(reg.getId(), contextInfo);
                    if (!old.getMeta().getVersionInd().equals(reg.getMeta().getVersionInd())) {
                        throw new OperationFailedException("versionMismatch" + old.getMeta().getVersionInd());
                    }
                    reg.setMeta(updateMeta(reg.getMeta(), contextInfo));
                    this.arMap.put(tran.getRegistration().getId(), reg);
                    break;
                case DELETE:
                    this.arMap.remove(tran.getRegistration().getId());
                    break;
            }
        }
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsForCourseRegistration(String courseRegistrationId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<RegistrationRequestItemInfo> list = new ArrayList<RegistrationRequestItemInfo>();
        for (RegistrationRequestInfo rr : this.rrMap.values()) {
        }
        return list;
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsByCourseOfferingAndStudent(String courseOfferingId,
            String studentId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<RegistrationRequestItemInfo> ret = new ArrayList<RegistrationRequestItemInfo>();

        for (RegistrationRequestInfo rri : this.rrMap.values()) {
            for (RegistrationRequestItemInfo item : rri.getRegistrationRequestItems()) {
                RegistrationGroupInfo rg = this.getRegGroup(item.getRegistrationGroupId(), contextInfo);
                if (rg.getCourseOfferingId().equals(courseOfferingId)) {
                    // TODO: shouldn't this also select the items a student is swapping out of so we chould 
                    // check use course registration id to get the CR and course offering and include that item?
                    if (item.getPersonId().equals(studentId)) {
                        ret.add(new RegistrationRequestItemInfo(item));
                    }
                }
            }
        }

        return ret;
    }

    private RegistrationGroupInfo getRegGroup(String regGroupId, ContextInfo contextInfo) throws OperationFailedException {
        try {
            return this.courseOfferingService.getRegistrationGroup(regGroupId, contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(ex);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException(ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException(ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException(ex);
        }
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String studentId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForRegistrationGroup(String studentId, String registrationGroupId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public CreditLoadInfo calculateCreditLoadForStudentRegistrationRequest(String registrationRequestId, String studentId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO KSENROLL-8712
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public StatusInfo changeRegistrationRequestState(
            String registrationRequestId, String nextStateKey,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO KSENROLL-8712
        throw new UnsupportedOperationException("not implemented");
    }

    private String newId() {
        return UUIDHelper.genStringUUID();
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

    private static MetaInfo updateMeta(MetaInfo old, ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    private static ValidationResultInfo makeValidationError(String element, String message) {
        ValidationResultInfo vr = new ValidationResultInfo();
        vr.setLevel(ValidationResult.ErrorLevel.ERROR);
        vr.setElement(element);
        vr.setMessage(message);
        return vr;
    }

    @Override
    public List<CourseRegistrationInfo> searchForCourseRegistrations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationRequestInfo> searchForRegistrationRequests(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("unimplemented");
    }

    @Override
    public List<RegistrationGroupInfo> getEligibleRegistrationGroupsForStudentInCourseOffering(String studentId,
            String courseOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("unimplemented");

    }
}
