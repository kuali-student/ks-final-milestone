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

package org.kuali.student.ap.courseregistration.impl;


import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.class2.courseregistration.service.impl.AbstractCourseRegistrationService;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CreditLoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
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
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CourseRegistrationServiceMockImpl2
    extends AbstractCourseRegistrationService
    implements CourseRegistrationService, MockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationServiceMockImpl2.class);

    private final Map<String, CourseRegistrationInfo> crMap   = new LinkedHashMap<String, CourseRegistrationInfo>();
    private final Map<String, ActivityRegistrationInfo> arMap = new LinkedHashMap<String, ActivityRegistrationInfo>();
    private final Map<String, RegistrationRequestInfo> rrMap  = new LinkedHashMap<String, RegistrationRequestInfo>();
    private final Map<String, List<ActivityRegistrationInfo>> regMap        = new LinkedHashMap<String, List<ActivityRegistrationInfo>>(); /* cr, ar */
    private final Map<String, List<RegistrationRequestItemInfo>> xactionMap = new LinkedHashMap<String, List<RegistrationRequestItemInfo>>(); /* cr, ritem */
    private final Map<String, List<RegistrationRequestItemInfo>> studentMap = new LinkedHashMap<String, List<RegistrationRequestItemInfo>>(); /* stu, ritem */
    
    //List of fake student course registrations to facilitate spring loading
    private List<String[]> fakeRegistrations = new ArrayList<String[]>();

    private final static KualiDecimal CREDIT_LIMIT = new KualiDecimal(15);
    
    private static long id = 0;

    private CourseOfferingService coService;

    private boolean crInitialized=false;

    public List<String[]> getFakeRegistrations() {
        return fakeRegistrations;
    }

    public void setFakeRegistrations(List<String[]> fakeRegistrations) {
        this.fakeRegistrations = fakeRegistrations;
    }

    /**
     * Load fake registration records from spring injected list ...if they have been injected
     * @param contextInfo
     */
    private void initializeCourseRegistrationMap(ContextInfo contextInfo) {

        for (String[] fakeReg : fakeRegistrations) {
            if (fakeReg.length<3) {
                LOGGER.warn("fakeRegistration ignored due to missing triplet (required: studentId, term, courseCode), got: {} ", Arrays.toString(fakeReg));
            } else {
                fakeRegister(contextInfo, fakeReg[0], fakeReg[1],fakeReg[2]);
            }
        }
    }

    /**
     * Load fake registration record
     * @param contextInfo
     * @param studentId  ID of student to load fake registration record for
     * @param termId  ID of term (e.g. "kuali.atp.2012Spring") to load fake registration record
     * @param courseCode course code (e.g. BSCI441 )
     */
    private void fakeRegister(ContextInfo contextInfo, String studentId, String termId, String courseCode) {
        CourseRegistrationInfo regInfo = new CourseRegistrationInfo();
        regInfo.setPersonId(studentId);
        regInfo.setCourseOfferingId(getCourseIdByCodeAndTerm(contextInfo, termId, courseCode));
        regInfo.setCredits(new KualiDecimal (3)); ///default credit hours to 3 for all
        regInfo.setGradingOptionId("def");
        try { regInfo.setEffectiveDate(new KSDateTimeFormatter("dd/MM/yyyy").parse("01/01/2001")); } catch(Exception e) {}
        regInfo.setId(UUID.randomUUID().toString()); //uuid generated via. oracle sql: select SYS_GUID() from dual;
        regInfo.setTypeKey(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY);  //seems wrong...but copied from existing code below
        regInfo.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        regInfo.setMeta(newMeta(contextInfo));
        crMap.put(regInfo.getCourseOfferingId(), regInfo);
    }

    /**
     * Lookup courseId for the indicated termId and courseCode
     * @param contextInfo
     * @param termId ID of term (e.g. "kuali.atp.2012Spring")
     * @param courseCode course code (e.g. BSCI441 )
     * @return courseId (may be first of several offerings
     */
    private String getCourseIdByCodeAndTerm(ContextInfo contextInfo, String termId, String courseCode) {
        String courseOfferingId=null;
        List<String> courseOfferingIds = null;
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode),
                PredicateFactory.equalIgnoreCase("atpId", termId)),
                PredicateFactory.equal("luiState", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY));
        QueryByCriteria criteria = qbcBuilder.build();
        try {
            courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);
        } catch (Exception e) {
            String errMsg = String.format("error finding course offering: %s", e.getMessage());
            LOGGER.error(errMsg,e);
            throw new RuntimeException(errMsg);
        }
        try{
            courseOfferingId = KSCollectionUtils.getRequiredZeroElement(courseOfferingIds);
        } catch(OperationFailedException e){
            LOGGER.warn(String.format("Unable to load course %s", courseCode), e);
        }
        return courseOfferingId;
    }

    @Override
	public void clear() {
		this.arMap.clear();
		this.crMap.clear();
		this.regMap.clear();
		this.rrMap.clear();
		this.studentMap.clear();
		this.xactionMap.clear();
	}

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

        return (Collections.unmodifiableList(new ArrayList<CourseRegistrationInfo>(this.crMap.values())));
    }

    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(List<String> courseRegistrationIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (String id : courseRegistrationIds) {
            ret.add(getCourseRegistration(id, contextInfo));
        }
        
        return Collections.unmodifiableList(ret);
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

        return Collections.unmodifiableList(ret);
    }
    
    @Override
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(String studentId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        if (!this.crInitialized)     {
            this.initializeCourseRegistrationMap(contextInfo);
            this.crInitialized=true;
        }

        List<CourseRegistrationInfo> ret = new ArrayList<CourseRegistrationInfo>();
        for (CourseRegistrationInfo cr : getCourseRegistrations(contextInfo)) {
            if (cr.getPersonId().equals(studentId)) {
                ret.add(cr);
            }
        }

        return Collections.unmodifiableList(ret);
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

        return Collections.unmodifiableList(ret);
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

        return Collections.unmodifiableList(ret);
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

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<String> searchForCourseRegistrationIds(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<String> ret = new ArrayList<String>();
        for (CourseRegistrationInfo cr : searchForCourseRegistrations(criteria, contextInfo)) {
            ret.add(cr.getId());
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public ActivityRegistrationInfo getActivityRegistration(String activityRegistrationId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        ActivityRegistrationInfo arInfo = this.arMap.get(activityRegistrationId);
        if (arInfo == null) {
            throw new DoesNotExistException(activityRegistrationId + " does not exist");
        }

        return arInfo;
    }
    
    public List<ActivityRegistrationInfo> getActivityRegistrations(ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return Collections.unmodifiableList(new ArrayList<ActivityRegistrationInfo>(this.arMap.values()));
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByIds(List<String> activityRegistrationIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (String id : activityRegistrationIds) {
            ret.add(getActivityRegistration(id, contextInfo));
        }
        
        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<String> getActivityRegistrationIdsByType(String activityRegistrationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> ret = new ArrayList<String>();
        for (ActivityRegistrationInfo ar : getActivityRegistrations(contextInfo)) {
            if (ar.getType().equals(activityRegistrationTypeKey)) {
                ret.add(ar.getId());
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : this.regMap.get(courseRegistrationId)) {
            ret.add(ar);
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudent(String studentId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrations(contextInfo)) {
            if (ar.getPersonId().equals(studentId)) {
                ret.add(ar);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByActivityOffering(String activityOfferingId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrations(contextInfo)) {
            if (ar.getActivityOfferingId().equals(activityOfferingId)) {
                ret.add(ar);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndActivityOffering(String studentId, String activityOfferingId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrationsByStudent(studentId, contextInfo)) {
            if (ar.getActivityOfferingId().equals(activityOfferingId)) {
                ret.add(ar);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrationsByStudentAndTerm(String studentId, String termId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityRegistrationInfo> ret = new ArrayList<ActivityRegistrationInfo>();
        for (ActivityRegistrationInfo ar : getActivityRegistrationsByStudent(studentId, contextInfo)) {
            try {
                if (getCourseOfferingService().getActivityOffering(ar.getActivityOfferingId(), contextInfo).getTermId().equals(termId)) {
                    ret.add(ar);
                }
            } catch (DoesNotExistException dne) {
                throw new OperationFailedException("bad stuff in reg", dne);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<String> searchForActivityRegistrationIds(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> ret = new ArrayList<String>();
        for (ActivityRegistrationInfo cr : searchForActivityRegistrations(criteria, contextInfo)) {
            ret.add(cr.getId());
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public RegistrationRequestInfo getRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationRequestInfo rrInfo = this.rrMap.get(registrationRequestId);
        if (rrInfo == null) {
            throw new DoesNotExistException(registrationRequestId + " does not exist");
        }

        return rrInfo;
    }

    public List<RegistrationRequestInfo> getRegistrationRequests(ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return Collections.unmodifiableList(new ArrayList<RegistrationRequestInfo>(this.rrMap.values()));
    }

    @Override
    public List<RegistrationRequestInfo> getRegistrationRequestsByIds(List<String> registrationRequestIds, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationRequestInfo> ret = new ArrayList<RegistrationRequestInfo>();
        for (String id : registrationRequestIds) {
            ret.add(getRegistrationRequest(id, contextInfo));
        }
        
        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<String> getRegistrationRequestIdsByType(String registrationRequestTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> ret = new ArrayList<String>();
        for (RegistrationRequestInfo rr : getRegistrationRequests(contextInfo)) {
            if (rr.getType().equals(registrationRequestTypeKey)) {
                ret.add(rr.getId());
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<RegistrationRequestInfo> getRegistrationRequestsByRequestor(String personId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationRequestInfo> ret = new ArrayList<RegistrationRequestInfo>();
        for (RegistrationRequestInfo rr : getRegistrationRequests(contextInfo)) {
            if (rr.getRequestorId().equals(personId)) {
                ret.add(rr);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<RegistrationRequestInfo> getUnsubmittedRegistrationRequestsByRequestorAndTerm(String requestorId, String termId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationRequestInfo> ret = new ArrayList<RegistrationRequestInfo>();
        for (RegistrationRequestInfo rr : getRegistrationRequestsByRequestor(requestorId, contextInfo)) {
            if (rr.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY) && rr.getTermId().equals(termId)) {
                ret.add(rr);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<String> searchForRegistrationRequestIds(QueryByCriteria criteria, ContextInfo contextInfo)
        throws InvalidParameterException,MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> ret = new ArrayList<String>();
        for (RegistrationRequestInfo rr : searchForRegistrationRequests(criteria, contextInfo)) {
            ret.add(rr.getId());
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationRequest(String validationTypeKey, String registrationRequestTypeKey, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        if (!registrationRequestTypeKey.equals(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY)) {
            results.add(makeValidationError("RegistrationRequest.type", "unknown type"));
        }

        if (registrationRequestInfo.getRequestorId() == null) {
            results.add(makeValidationError("RegistrationRequest.requestorId", "cannot be null"));
        }

        if (registrationRequestInfo.getTermId() == null) {
            results.add(makeValidationError("RegistrationRequest.termId", "cannot be null"));
        }

        if (registrationRequestInfo.getRegistrationRequestItems() == null) {
            results.add(makeValidationError("RegistrationRequest.registrationRequestItems", "cannot be null"));
        } else if (registrationRequestInfo.getRegistrationRequestItems().size() == 0) {
            results.add(makeValidationError("RegistrationRequest.registrationRequestItems", "cannot be empty"));
        }

        for (RegistrationRequestItem item : registrationRequestInfo.getRegistrationRequestItems()) {
            if (item.getPersonId() == null) {
                results.add(makeValidationError("RegistrationRequestItem.studentId", "cannot be null"));
            }

            if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {
                if (item.getRegistrationGroupId() == null) {
                    results.add(makeValidationError("RegistrationRequestItem.registrationGroupId", "cannot be null"));
                }

                if (item.getExistingCourseRegistrationId() != null) {
                    results.add(makeValidationError("RegistrationRequestItem.existingCourseRegistrationId", "must be null"));
                }

                if (item.getCredits() == null) {
                    results.add(makeValidationError("RegistrationRequestItem.credits", "cannot be null"));
                }

                if (item.getGradingOptionId() == null) {
                    results.add(makeValidationError("RegistrationRequestItem.gradingOptionId", "cannot be null"));
                }
            } else if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
            	 if (item.getRegistrationGroupId() == null) {
                     results.add(makeValidationError("RegistrationRequestItem.registrationGroupId", "cannot be null"));
                 }

                 if (item.getExistingCourseRegistrationId() != null) {
                     results.add(makeValidationError("RegistrationRequestItem.existingCourseRegistrationId", "must be null"));
                 }

                if (item.getCredits() != null) {
                    results.add(makeValidationError("RegistrationRequestItem.credits", "must be null"));
                }

                if (item.getGradingOptionId() != null) {
                    results.add(makeValidationError("RegistrationRequestItem.gradingOptionId", "must be null"));
                }
            } else if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_SWAP_TYPE_KEY)) {
            	 if (item.getRegistrationGroupId() == null) {
                     results.add(makeValidationError("RegistrationRequestItem.registrationGroupId", "cannot be null"));
                 }

                 if (item.getExistingCourseRegistrationId() != null) {
                     results.add(makeValidationError("RegistrationRequestItem.existingCourseRegistrationId", "must be null"));
                 }


                if (item.getCredits() == null) {
                    results.add(makeValidationError("RegistrationRequestItem.credits", "cannot be null"));
                }

                if (item.getGradingOptionId() == null) {
                    results.add(makeValidationError("RegistrationRequestItem.gradingOptionId", "cannot be null"));
                }
            } else if (item.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_UPDATE_TYPE_KEY)) {
            	 if (item.getRegistrationGroupId() == null) {
                     results.add(makeValidationError("RegistrationRequestItem.registrationGroupId", "cannot be null"));
                 }

                 if (item.getExistingCourseRegistrationId() != null) {
                     results.add(makeValidationError("RegistrationRequestItem.existingCourseRegistrationId", "must be null"));
                 }

            } else {
                results.add(makeValidationError("RegistrationRequestItem.typeKey", "unknown item type"));
            }
        }

        return Collections.unmodifiableList(results);
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequest(String registrationRequestTypeKey, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        List<ValidationResultInfo> results = validateRegistrationRequest("any", registrationRequestTypeKey, registrationRequestInfo, contextInfo);
        if (results.size() > 0) {
            throw new DataValidationErrorException("data validation error, use validateRegistrationRequest()");
        }

        RegistrationRequestInfo rr = new RegistrationRequestInfo(registrationRequestInfo);
        rr.setId(newId());
        rr.setTypeKey(registrationRequestTypeKey);
        rr.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        rr.setMeta(newMeta(contextInfo));
        
        this.rrMap.put(rr.getId(), rr);
        return rr;
    }

    @Override
    public RegistrationRequestInfo createRegistrationRequestFromExisting(String registrationRequestId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationRequestInfo rr = new RegistrationRequestInfo(getRegistrationRequest(registrationRequestId, contextInfo));
        rr.setMeta(null);
        rr.setId(newId());
        rr.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        List<ValidationResultInfo> results = validateRegistrationRequest("any", rr.getTypeKey(), rr, contextInfo);
        if (results.size() > 0) {
            throw new OperationFailedException("data validation error in copy");
        }

        this.rrMap.put(rr.getId(), rr);

        return rr;
    }

    @Override
    public RegistrationRequestInfo updateRegistrationRequest(String registrationRequestId, RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

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
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationRequest rr = getRegistrationRequest(registrationRequestId, contextInfo);
        if (rr == null) {
            throw new DoesNotExistException(registrationRequestId + " not found");
        }

        if (rr.getStateKey().equals(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY) ||
            rr.getStateKey().equals(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY)) {
            throw new OperationFailedException("cannot delete a submitted registration request, sorry");
        }

        this.rrMap.remove(registrationRequestId);
        return new StatusInfo();
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(String registrationRequestId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // it already data validated and we have nothing else to say
        return Collections.EMPTY_LIST;
    }

    @Override
    public RegistrationRequestInfo submitRegistrationRequest(String registrationRequestId, ContextInfo contextInfo)
        throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        /* look up the registration request */
        RegistrationRequestInfo rr = getRegistrationRequest(registrationRequestId, contextInfo);
        if (!rr.getStateKey().equals(LprServiceConstants.LPRTRANS_NEW_STATE_KEY)) {
            throw new AlreadyExistsException(registrationRequestId + " already submitted");
        }

        /* verify the registration request */
        List<ValidationResultInfo> results = verifyRegistrationRequestForSubmission(registrationRequestId, contextInfo);
        if (results.size() > 0) {
            rr.setStateKey(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY);
            throw new OperationFailedException("registration failed, try verifying next time");
        }

        /* check to see if the student is already regsistered in the course offering */
        //...... TODO


        /* cddr through the list of request items */
        for (RegistrationRequestItemInfo item : rr.getRegistrationRequestItems()) {

            /* get the registration groups */
            RegistrationGroup newrg = getCourseOfferingService().getRegistrationGroup(item.getRegistrationGroupId(), contextInfo);
            //RegistrationGroup existingrg = getCourseOfferingService().getRegistrationGroup(item.getExistingRegistrationGroupId(), contextInfo);

            /* an ADD */
            if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {
                
                /* create the course registration */
                CourseRegistrationInfo cr = new CourseRegistrationInfo();
                cr.setId(newId());
                cr.setTypeKey(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY);
                cr.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
                cr.setMeta(newMeta(contextInfo));
                cr.setPersonId(item.getPersonId());
                cr.setCourseOfferingId(newrg.getCourseOfferingId());
                cr.setCredits(item.getCredits());
                cr.setGradingOptionId(item.getGradingOptionId());
                this.crMap.put(cr.getId(), cr);

                /* update the local db */
                List<RegistrationRequestItemInfo> items = this.xactionMap.get(cr.getId());
                if (items == null) {
                    items = new ArrayList<RegistrationRequestItemInfo>();
                    this.xactionMap.put(cr.getId(), items);
                }
                
                items.add(item);

                items = this.studentMap.get(item.getPersonId());
                if (items == null) {
                    items = new ArrayList<RegistrationRequestItemInfo>();
                    this.xactionMap.put(item.getPersonId(), items);
                }
                
                items.add(item);

                /* create the activity registrations */
                for (String activityId : newrg.getActivityOfferingIds()) {
                    ActivityRegistrationInfo ar = new ActivityRegistrationInfo();
                    ar.setId(newId());
                    ar.setTypeKey(LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY);
                    ar.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
                    ar.setMeta(newMeta(contextInfo));
                    ar.setPersonId(item.getPersonId());
                    ar.setActivityOfferingId(activityId);
                    this.arMap.put(ar.getId(), ar);

                    /* update the local db */
                    List<ActivityRegistrationInfo> ars = this.regMap.get(cr.getId());
                    if (ars == null) {
                        ars = new ArrayList<ActivityRegistrationInfo>();
                        this.regMap.put(cr.getId(), ars);
                    }
                    
                    ars.add(ar);
                }
                
                /* set the state for the item */
                item.setStateKey(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY);

            } else if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
                String error = String.format("Empty If Statement: No action defined for %s", LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY);
                LOGGER.debug(error);
                throw new UnsupportedOperationException(error);
            } else if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_SWAP_TYPE_KEY)) {
                String error = String.format("Empty If Statement: No action defined for %s", LprServiceConstants.REQ_ITEM_SWAP_TYPE_KEY);
                LOGGER.debug(error);
                throw new UnsupportedOperationException(error);
            } else if (item.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_UPDATE_TYPE_KEY)) {
                String error = String.format("Empty If Statement: No action defined for %s", LprServiceConstants.LPRTRANS_ITEM_UPDATE_TYPE_KEY);
                LOGGER.debug(error);
                throw new UnsupportedOperationException(error);
            } 
        }

        rr.setStateKey(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY);

        OperationStatusInfo status = new OperationStatusInfo();
        status.setStatus("ok");

        return rr;
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsForCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List <RegistrationRequestItemInfo> items = this.xactionMap.get(courseRegistrationId);
        if (items == null) {
            return Collections.EMPTY_LIST;
        } else {
            return Collections.unmodifiableList(items);
        }
    }

    @Override
    public List<RegistrationRequestItemInfo> getRegistrationRequestItemsByCourseOfferingAndStudent(String courseOfferingId, String studentId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List <RegistrationRequestItemInfo> ret = new ArrayList<RegistrationRequestItemInfo>();

        for (RegistrationRequestItemInfo rri : this.studentMap.get(studentId)) {
            try {
                for (RegistrationGroup rg : getCourseOfferingService().getRegistrationGroupsForCourseOffering(courseOfferingId, contextInfo)) {
                    if (rri.getRegistrationGroupId().equals(rg.getId())) {
                        ret.add(rri);
                    }
                }
            } catch (DoesNotExistException dne) {} // CO should throw this here
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        if (studentId.equals("norm")) {
            results.add(makeValidationError("basic elegibility", "norm can't register"));
        }

        return Collections.unmodifiableList(results);
    }

   @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String studentId, String termId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        if (studentId.equals("norm")) {
            results.add(makeValidationError("elegibility for term", "norm can't register this term"));
        }

        return Collections.unmodifiableList(results);
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        if (studentId.equals("norm") && courseOfferingId.equals("recursion101")) {
            results.add(makeValidationError("elegibility forcourse offering", "norm can't register in this class"));
        }

        return Collections.unmodifiableList(results);
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForRegistrationGroup(String studentId, String registrationGroupId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return Collections.EMPTY_LIST;
    }

    @Override
    public CreditLoadInfo calculateCreditLoadForStudentRegistrationRequest(String registrationRequestId, String studentId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationRequest rr = getRegistrationRequest(registrationRequestId, contextInfo);
        CreditLoadInfo load = new CreditLoadInfo();
        load.setStudentId(studentId);
        load.setCreditLimit(CREDIT_LIMIT);

        for (RegistrationRequestItem item : rr.getRegistrationRequestItems()) {
            if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {
                load.setAdditionalCredits(item.getCredits().add (load.getAdditionalCredits()));
            } else if (item.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
                LOGGER.debug("Empty If Statement: No action defined for {}", LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY);
            }
        }

        return load;
    }

    @Override
	public StatusInfo changeRegistrationRequestState(
			String registrationRequestId, String nextStateKey,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO KSENROLL-8712
		throw new UnsupportedOperationException("not implemented");
	}
    

    private static String newId() {
        return Long.toString(++id);
    }

    private static MetaInfo newMeta(ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(contextInfo.getPrincipalId());
        meta.setCreateTime(contextInfo.getCurrentDate());
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private static StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private static MetaInfo updateMeta(MetaInfo old, ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(contextInfo.getCurrentDate());
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
}
