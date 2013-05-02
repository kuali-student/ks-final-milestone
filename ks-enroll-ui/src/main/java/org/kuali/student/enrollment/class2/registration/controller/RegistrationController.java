/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.registration.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.cache.RuntimeCacheException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.enrollment.class2.registration.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.registration.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.registration.dto.MeetingScheduleWrapper;
import org.kuali.student.enrollment.class2.registration.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.registration.form.RegistrationForm;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.Context;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/registration")
//Needs to clean up the core slice codes
@Deprecated
public class RegistrationController extends UifControllerBase {

    private transient CourseOfferingService courseOfferingService;
    private transient StatementService statementService;
    private transient CourseService courseService;
    private transient CourseRegistrationService courseRegistrationService;

    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
            return new RegistrationForm();
    }

    protected RegistrationRequestInfo generateNewRegRequestInfo(ContextInfo context, RegistrationForm regForm){
        String id = context.getPrincipalId();
        RegistrationRequestInfo info = new RegistrationRequestInfo();
        info.setTermId(regForm.getTermId());
        info.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        info.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        info.setRequestorId(id);
        info.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        return info;
    }

    protected RegistrationRequestItemInfo generateRegRequestItem(RegistrationGroupWrapper regGroupWrapper, Context context){
        RegistrationRequestItemInfo regRequestItem = new RegistrationRequestItemInfo();
        regRequestItem.setTypeKey(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);
        regRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        regRequestItem.setStudentId(context.getPrincipalId());
        regRequestItem.setNewRegistrationGroupId(regGroupWrapper.getRegistrationGroup().getId());
        //        regRequestItem.setCreditOptionKey("kuali.credit.option.RVG1"); TODO: fix
        regRequestItem.setGradingOptionId("kuali.grading.option.RVG1");
        regRequestItem.setName(regGroupWrapper.getRegistrationGroup().getName());
        regRequestItem.setOkToHoldUntilList(false);
        regRequestItem.setOkToWaitlist(true);
        return regRequestItem;
    }

    protected RegistrationRequestItemInfo generateDropRegRequestItem(RegistrationGroupWrapper regGroupWrapper, Context context){
        RegistrationRequestItemInfo regRequestItem = new RegistrationRequestItemInfo();
        regRequestItem.setTypeKey(LprServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY);
        regRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        regRequestItem.setStudentId(context.getPrincipalId());
        regRequestItem.setExistingRegistrationGroupId(regGroupWrapper.getRegistrationGroup().getId());
        //        regRequestItem.setCredits("kuali.credit.option.RVG1");
        regRequestItem.setGradingOptionId("kuali.grading.option.RVG1");
        regRequestItem.setName(regGroupWrapper.getRegistrationGroup().getName());
        regRequestItem.setOkToHoldUntilList(false);
        regRequestItem.setOkToWaitlist(true);
        return regRequestItem;
    }

    protected RegistrationGroupWrapper findRegGroupByIndex(RegistrationForm registrationForm){
        // Code copied roughly from UifControllerBase.deleteLine() method
        String selectedCollectionPath = registrationForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for registration line action, cannot register line");
        }

        int selectedLineIndex = -1;
        String selectedLine = registrationForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set for registration line action, cannot register line");
        }

        // get the collection instance for adding the new line
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(registrationForm, selectedCollectionPath);
        if (collection == null) {
            throw new RuntimeException("Unable to get registration group collection property from RegistrationForm for path: " + selectedCollectionPath);
        }

        if (collection instanceof List) {
            return (RegistrationGroupWrapper) ((List<Object>) collection).get(selectedLineIndex);
        }
        else {
            throw new RuntimeException("Only List collection implementations are supported for findRegGroup by index method");
        }
    }

    protected List<CourseRegistrationInfo> getCourseRegistrations(String studentId, String termKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, DisabledIdentifierException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        if (getCourseRegistrationService() != null) {
            return getCourseRegistrationService().getCourseRegistrationsByStudentAndTerm(studentId, termKey, context);
        }
        return new ArrayList<CourseRegistrationInfo>();                                    }



    /**
     * Initial method called when requesting a new view instance which forwards
     * the view for rendering
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=showRegistration")
    public ModelAndView showRegistration(@ModelAttribute("KualiForm") UifFormBase formBase, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        ContextInfo context = new ContextInfo();
        RegistrationForm regForm = (RegistrationForm) formBase;
        try {
            regForm.setCourseRegistrations(getCourseRegistrations(context.getPrincipalId(), regForm.getTermId(), context));

            //Pull any existing 'new' cart out
            //List<String> states = new ArrayList<String>();
            // FIXME
//            List<RegistrationRequestInfo> regRequestInfos = getCourseRegistrationService().getRegRequestsByStudentAndTerm(context.getPrincipalId(), regForm.getTermId(), states, context);
            List<RegistrationRequestInfo> regRequestInfos = new ArrayList<RegistrationRequestInfo>();
            RegistrationRequestInfo regRequest = null;
            if(regRequestInfos != null){
                for(RegistrationRequestInfo info: regRequestInfos){
                    if(regRequest != null && regRequest.getMeta().getCreateTime().before(info.getMeta().getCreateTime())){
                        regRequest = info;
                    }
                    else if(regRequest == null){
                        regRequest = info;
                    }
                }
            }

            regForm.setRegRequest(regRequest);
            if(regRequest != null && regRequest.getRegistrationRequestItems() != null){
                for(RegistrationRequestItemInfo item: regRequest.getRegistrationRequestItems()){
                    if(StringUtils.isNotBlank(item.getNewRegistrationGroupId())){
                        RegistrationGroupInfo regGroup = getCourseOfferingService().getRegistrationGroup(item.getNewRegistrationGroupId(), context);
                        CourseOfferingInfo courseOffering = getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), context);
                        RegistrationGroupWrapper registrationGroupWrapper = new RegistrationGroupWrapper();
                        registrationGroupWrapper.setRegistrationGroup(regGroup);
                        registrationGroupWrapper.setCourseOffering(courseOffering);
                        registrationGroupWrapper.setActivityOfferingWrappers(getActivityOfferingInfos(regGroup, courseOffering, context));
                        regForm.getRegistrationGroupWrappersById().put(registrationGroupWrapper.getRegistrationGroup().getId(), registrationGroupWrapper);
                    }
                    if(StringUtils.isNotBlank(item.getExistingRegistrationGroupId())){
                        RegistrationGroupInfo regGroup = getCourseOfferingService().getRegistrationGroup(item.getExistingRegistrationGroupId(), context);
                        CourseOfferingInfo courseOffering = getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), context);
                        RegistrationGroupWrapper registrationGroupWrapper = new RegistrationGroupWrapper();
                        registrationGroupWrapper.setRegistrationGroup(regGroup);
                        registrationGroupWrapper.setCourseOffering(courseOffering);
                        registrationGroupWrapper.setActivityOfferingWrappers(getActivityOfferingInfos(regGroup, courseOffering, context));
                        regForm.getRegistrationGroupWrappersById().put(registrationGroupWrapper.getRegistrationGroup().getId(), registrationGroupWrapper);
                    }
                }
            }

//            return getUIFModelAndView(regForm, regForm.getViewId(), "registrationPage");
            return getUIFModelAndView(regForm, "registrationPage");
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        } catch (DisabledIdentifierException e) {
            throw new RuntimeException(e);
        } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used to search for course offerings based on criteria entered
     */
    @RequestMapping(params = "methodToCall=searchCourseOfferings")
    public ModelAndView searchCourseOfferings(@ModelAttribute("KualiForm") RegistrationForm registrationForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) {
        ContextInfo context = new ContextInfo();

        try {
            List<String> courseOfferingIds = getCourseOfferingIds(registrationForm, context);
            registrationForm.setCourseOfferingWrappers(new ArrayList<CourseOfferingWrapper>(courseOfferingIds.size()));

            for (String coId : courseOfferingIds) {
                CourseOfferingWrapper courseOfferingWrapper = new CourseOfferingWrapper();
                String prereq = "none";
                courseOfferingWrapper.setCourseOffering(getCourseOfferingService().getCourseOffering(coId, context));
//    TODO statement service wasnt working correctly when tested, commented out for now
                List<StatementTreeViewInfo> statements = getCourseService().getCourseStatements(courseOfferingWrapper.getCourseOffering().getCourseId(),"KUALI.RULE", "en", context);
                if(statements != null && !statements.isEmpty()){
                    for(StatementTreeViewInfo statement: statements){
                        if(statement.getType().equals("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq")){
                            prereq = getStatementService().getNaturalLanguageForStatement(statement.getId(),"KUALI.RULE","en");
                            break;
                        }
                    }
                }
                courseOfferingWrapper.setPrereq(prereq);
                List<RegistrationGroupInfo> regGroups = getRegistrationGroupInfos(coId, context);

                List<RegistrationGroupWrapper> registrationGroupWrappers = new ArrayList<RegistrationGroupWrapper>(regGroups.size());
                for (RegistrationGroupInfo regGroup : regGroups) {
                    RegistrationGroupWrapper registrationGroupWrapper = new RegistrationGroupWrapper();
                    registrationGroupWrapper.setRegistrationGroup(regGroup);
                    registrationGroupWrapper.setCourseOffering(courseOfferingWrapper.getCourseOffering());
                    registrationGroupWrapper.setActivityOfferingWrappers(getActivityOfferingInfos(regGroup, courseOfferingWrapper.getCourseOffering(), context));
                    registrationGroupWrappers.add(registrationGroupWrapper);
                }
                courseOfferingWrapper.setRegistrationGroupWrappers(registrationGroupWrappers);
                registrationForm.getCourseOfferingWrappers().add(courseOfferingWrapper);
            }

        } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getUIFModelAndView(registrationForm);
    }

    protected List<String> getCourseOfferingIds(RegistrationForm registrationForm, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        // if the coures offering code is not blank... assume the value in the selectbox is inconsequential
        if (StringUtils.isNotBlank(registrationForm.getCourseOfferingCode())) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Searching by Course Offering Code is not yet implemented");
            return new ArrayList<String>();
        } else {
            return getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(registrationForm.getTermId(), registrationForm.getSubjectArea(), context);
        }
    }

    protected List<RegistrationGroupInfo> getRegistrationGroupInfos(String coId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        return getCourseOfferingService().getRegistrationGroupsForCourseOffering(coId, context);
    }

    protected List<ActivityOfferingWrapper> getActivityOfferingInfos(RegistrationGroup regGroup, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        // TODO right now getOfferingsByIds throws a not supported exception
//        return getCourseOfferingService().getActivityOfferingsByIds(regGroup.getActivityOfferingIds(), context);
        List<ActivityOfferingWrapper> activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>(regGroup.getActivityOfferingIds().size());
        for (String activityId : regGroup.getActivityOfferingIds()) {
            ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(activityId, context);
            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper();
            wrapper.setActivityOffering(activityOfferingInfo);
            wrapper.setMeetingScheduleWrappers(setupMeetingScheduleInfos(courseOfferingInfo, activityOfferingInfo));
            activityOfferingWrappers.add(wrapper);
        }

        //generate js object that represents the times of the entire reg group
        StringBuilder builder = new StringBuilder();
        for (ActivityOfferingWrapper a : activityOfferingWrappers) {
            for (MeetingScheduleWrapper m : a.getMeetingScheduleWrappers()) {
                if (StringUtils.isNotBlank(builder.toString())) {
                    builder.append(",");
                }
                builder.append(m.getJsScheduleObject());
            }
        }
        String times = "[" + builder.toString() + "]";
        for (ActivityOfferingWrapper a : activityOfferingWrappers) {
            for (MeetingScheduleWrapper m : a.getMeetingScheduleWrappers()) {
                m.setRegGroupTimesJsObject(times);
            }
        }
        return activityOfferingWrappers;
    }

    protected List<MeetingScheduleWrapper> setupMeetingScheduleInfos(CourseOfferingInfo courseOfferingInfo, ActivityOfferingInfo activityOfferingInfo) {
        List<MeetingScheduleWrapper> wrappers = new ArrayList<MeetingScheduleWrapper>();
        // TODO: fix this to get the meeting schedule from the schedule Id and the schedule service
        List<MeetingScheduleInfo> list = new ArrayList<MeetingScheduleInfo> ();        
        for (MeetingScheduleInfo meetingScheduleInfo : list) {
            MeetingScheduleWrapper wrapper = new MeetingScheduleWrapper(meetingScheduleInfo);
            wrapper.setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
            wrapper.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
            wrappers.add(wrapper);
        }
        return wrappers;
    }

        @RequestMapping(params = "methodToCall=dropClass")
    public ModelAndView dropClass(@ModelAttribute("KualiForm") RegistrationForm registrationForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        ContextInfo context = new ContextInfo();
        RegistrationGroupWrapper regGroupWrapper = findRegGroupByIndex(registrationForm);

        try {
            RegistrationRequestInfo regRequest = generateNewRegRequestInfo(context, registrationForm);
            RegistrationRequestItemInfo regRequestItem = generateDropRegRequestItem(regGroupWrapper, context);

            regRequest.getRegistrationRequestItems().add(regRequestItem);

            String validationTypeKey = "FIXME";
			String regRequestTypeKey = "FIXME";
			
            List<ValidationResultInfo> validationResultInfos = getCourseRegistrationService().validateRegistrationRequest(validationTypeKey, regRequestTypeKey, regRequest, context);
            if (CollectionUtils.isEmpty(validationResultInfos)) {
                regRequest = getCourseRegistrationService().createRegistrationRequest(regRequestTypeKey, regRequest, context);
                registrationForm.getRegistrationGroupWrappersById().put(regGroupWrapper.getRegistrationGroup().getId(), regGroupWrapper);
            } else {
                StringBuilder builder = new StringBuilder("Found multiple ValidationResultInfo objects after Registration Request validation:\n");
                for (ValidationResultInfo resultInfo : validationResultInfos) {
                    builder.append(resultInfo.getMessage()).append("\n");
                }
                throw new RuntimeException(builder.toString());
            }
            //immediately submit the regRequest that was just created
            processSubmitRegRequest(regRequest, registrationForm, true);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (DataValidationErrorException e) {
            throw new RuntimeException(e);
        } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (ReadOnlyException e) {
            throw new RuntimeException(e);
        }


        return getUIFModelAndView(registrationForm);
    }

    @RequestMapping(params = "methodToCall=registerClass")
    public ModelAndView registerClass(@ModelAttribute("KualiForm") RegistrationForm registrationForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        ContextInfo context = new ContextInfo();
        RegistrationGroupWrapper regGroupWrapper = findRegGroupByIndex(registrationForm);

        try {
            RegistrationRequestInfo regRequest = generateNewRegRequestInfo(context, registrationForm);
            RegistrationRequestItemInfo regRequestItem = generateRegRequestItem(regGroupWrapper, context);

            regRequest.getRegistrationRequestItems().add(regRequestItem);
            
            String validationTypeKey = "FIXME";
            String regRequestTypeKey = "FIXME";

            List<ValidationResultInfo> validationResultInfos = getCourseRegistrationService().validateRegistrationRequest(validationTypeKey, regRequestTypeKey, regRequest, context);
            if (CollectionUtils.isEmpty(validationResultInfos)) {
                regRequest = getCourseRegistrationService().createRegistrationRequest(regRequestTypeKey, regRequest, context);
                registrationForm.getRegistrationGroupWrappersById().put(regGroupWrapper.getRegistrationGroup().getId(), regGroupWrapper);
            } else {
                StringBuilder builder = new StringBuilder("Found multiple ValidationResultInfo objects after Registration Request validation:\n");
                for (ValidationResultInfo resultInfo : validationResultInfos) {
                    builder.append(resultInfo.getMessage()).append("\n");
                }
                throw new RuntimeException(builder.toString());
            }
            //immediately submit the regRequest that was just created
            processSubmitRegRequest(regRequest, registrationForm, true);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (DataValidationErrorException e) {
            throw new RuntimeException(e);
     } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (ReadOnlyException e) {
        	 throw new RuntimeException(e);
		}


        return getUIFModelAndView(registrationForm);
    }

    @RequestMapping(params = "methodToCall=submitRegistration")
    public ModelAndView submitRegistration(@ModelAttribute("KualiForm") RegistrationForm registrationForm, BindingResult result,
                                           HttpServletRequest request, HttpServletResponse response) {
        processSubmitRegRequest(registrationForm.getRegRequest(), registrationForm, false);
        return getUIFModelAndView(registrationForm);
    }

    protected void processSubmitRegRequest(RegistrationRequestInfo regRequest, RegistrationForm registrationForm, boolean oneClick) {
       ContextInfo context = new ContextInfo();
       try {
           String validationTypeKey = "FIXME";
           String regRequestTypeKey = "FIXME";

           
           List<ValidationResultInfo> validationResultInfos = getCourseRegistrationService().validateRegistrationRequest(validationTypeKey, regRequestTypeKey, regRequest, context);
           if (CollectionUtils.isEmpty(validationResultInfos)) {
               //RegRequestInfo regRequest = saveRegRequest(registrationForm.getRegRequest(), context);
               RegistrationResponseInfo regResponse = getCourseRegistrationService().submitRegistrationRequest(regRequest.getId(), context);
               
               if (!regResponse.getHasFailed()) {
                   GlobalVariables.getMessageMap().putInfo("GLOBAL_INFO", "enroll.registrationSuccessful");
                   //TODO check this logic
                   //Assuming registration successful if no errors returned
                   if (!oneClick) {
                       registrationForm.setRegRequest(null);
                   }
                   registrationForm.setCourseRegistrations(getCourseRegistrations(context.getPrincipalId(), registrationForm.getTermId(), context));
               } else {
                   if (regResponse.getMessages().isEmpty()) {
                       GlobalVariables.getMessageMap().putError("GLOBAL_ERRORS", "enroll.registrationUnsuccessful");
                   }
               }
               
               if (!regResponse.getMessages().isEmpty()) {
                   for (String message : regResponse.getMessages()) {
                       GlobalVariables.getMessageMap().putError("GLOBAL_ERRORS", "error.enroll.requirementsNotMet", message);
                   }
               }
           } else {
               GlobalVariables.getMessageMap().putError("GLOBAL_ERRORS", "enroll.registrationUnsuccessful");
               StringBuilder builder = new StringBuilder("Found multiple ValidationResultInfo objects after Registration Request validation:\n");
               for (ValidationResultInfo resultInfo : validationResultInfos) {
                   builder.append(resultInfo.getMessage()).append("\n");
               }
               throw new RuntimeException(builder.toString());
           }
       } catch (DoesNotExistException e) {
           throw new RuntimeException(e);
       } catch (InvalidParameterException e) {
           throw new RuntimeException(e);
       } catch (MissingParameterException e) {
           throw new RuntimeException(e);
       } catch (OperationFailedException e) {
           throw new RuntimeException(e);
       } catch (PermissionDeniedException e) {
           throw new RuntimeException(e);
       } catch (AlreadyExistsException e) {
           throw new RuntimeException(e);
       } catch (DisabledIdentifierException e) {
           throw new RuntimeException(e);
       }
    }
    
    /**
     * After the document is loaded calls method to setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=removeFromCart")
    public ModelAndView removeFromCart(@ModelAttribute("KualiForm") RegistrationForm registrationForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) {
        ContextInfo context = new ContextInfo();

        RegistrationRequest regRequest = registrationForm.getRegRequest();
        String id = registrationForm.getActionParamaterValue("itemId");
        String regGroupId = "";
        //Must be being called from course list if blank
        if(StringUtils.isBlank(id)){
            RegistrationGroupWrapper regGroupWrapper = findRegGroupByIndex(registrationForm);
            regGroupId = regGroupWrapper.getRegistrationGroup().getId();
        }

        if(regRequest != null){
            List<? extends RegistrationRequestItem> items = regRequest.getRegistrationRequestItems();
            if(items != null && !items.isEmpty()){
                Iterator<? extends RegistrationRequestItem> it = items.iterator();
                while(it.hasNext()){
                    RegistrationRequestItem item = it.next();
                    if(StringUtils.isNotBlank(id) && item.getId().equals(id)){
                        it.remove();
                        break;
                    }
                    else if(StringUtils.isNotBlank(regGroupId) && StringUtils.isNotBlank(item.getNewRegistrationGroupId())
                        && item.getNewRegistrationGroupId().equals(regGroupId)){
                        it.remove();
                        break;
                }
            }
        }
        }

        try {
        	  String validationTypeKey = "FIXME";
             	String regRequestTypeKey = "FIXME";

            List<ValidationResultInfo> validationResultInfos = getCourseRegistrationService().validateRegistrationRequest(validationTypeKey, regRequestTypeKey, registrationForm.getRegRequest(), context);
            if (CollectionUtils.isEmpty(validationResultInfos)) {
                RegistrationRequestInfo regRequestInfo = getCourseRegistrationService().updateRegistrationRequest(registrationForm.getRegRequest().getId(), registrationForm.getRegRequest(), context);
                registrationForm.setRegRequest(getCourseRegistrationService().getRegistrationRequest(regRequestInfo.getId(),context));
            } else {
                StringBuilder builder = new StringBuilder("Found multiple ValidationResultInfo objects after Registration Request validation:\n");
                for (ValidationResultInfo resultInfo : validationResultInfos) {
                    builder.append(resultInfo.getMessage()).append("\n");
                }
                throw new RuntimeException(builder.toString());
            }
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (DataValidationErrorException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        } catch (VersionMismatchException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (ReadOnlyException e) {
        	 throw new RuntimeException(e);
		}

        return getUIFModelAndView(registrationForm);
    }

    /**
     * After the document is loaded calls method to setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=addToCart")
    public ModelAndView addToCart(@ModelAttribute("KualiForm") RegistrationForm registrationForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) {
        ContextInfo context = new ContextInfo();

        RegistrationGroupWrapper regGroupWrapper = findRegGroupByIndex(registrationForm);

        try {
            //Create if no reg request or if there is a reg request with an id yet for the Cart
            if (registrationForm.getRegRequest() == null ||
                    (registrationForm.getRegRequest() != null && StringUtils.isBlank(registrationForm.getRegRequest().getId()))) {
                RegistrationRequestInfo regRequest = generateNewRegRequestInfo(context, registrationForm);
                registrationForm.setRegRequest(regRequest);
            }

            RegistrationRequestItemInfo regRequestItem = generateRegRequestItem(regGroupWrapper, context);
            registrationForm.getRegRequest().getRegistrationRequestItems().add(regRequestItem);

            String validationTypeKey = "FIXME";
			String regRequestTypeKey = "FIXME";
			List<ValidationResultInfo> validationResultInfos = getCourseRegistrationService().validateRegistrationRequest(validationTypeKey, regRequestTypeKey, registrationForm.getRegRequest(), context);
            if (CollectionUtils.isEmpty(validationResultInfos)) {
                if (StringUtils.isBlank(registrationForm.getRegRequest().getId())) {
                    RegistrationRequestInfo regRequestInfo = getCourseRegistrationService().createRegistrationRequest(regRequestTypeKey, registrationForm.getRegRequest(), context);
                    regRequestInfo = getCourseRegistrationService().getRegistrationRequest(regRequestInfo.getId(),context);
                    registrationForm.setRegRequest(regRequestInfo);
                } else {
                    getCourseRegistrationService().updateRegistrationRequest(registrationForm.getRegRequest().getId(), registrationForm.getRegRequest(), context);
                    RegistrationRequestInfo regRequestInfo = getCourseRegistrationService().getRegistrationRequest(registrationForm.getRegRequest().getId(),context);
                    registrationForm.setRegRequest(regRequestInfo);
                }

                registrationForm.getRegistrationGroupWrappersById().put(regGroupWrapper.getRegistrationGroup().getId(), regGroupWrapper);
            } else {
                StringBuilder builder = new StringBuilder("Found multiple ValidationResultInfo objects after Registration Request validation:\n");
                for (ValidationResultInfo resultInfo : validationResultInfos) {
                    builder.append(resultInfo.getMessage()).append("\n");
                }
                throw new RuntimeException(builder.toString());
            }
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (DataValidationErrorException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        } catch (VersionMismatchException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (ReadOnlyException e) {
			throw new RuntimeException(e);
		}

        return getUIFModelAndView(registrationForm);
    }

    protected void processValidationResults(List<ValidationResultInfo> validationResultInfos, String operation) {
        GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Found errors while trying to " + operation);
        for (ValidationResultInfo resultInfo : validationResultInfos) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, resultInfo.getMessage());
        }
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "CourseOfferingService"));
        }

        return courseOfferingService;
    }

    protected CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
             courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseRegistrationService", "CourseRegistrationService"));
        }
        return courseRegistrationService;
    }

    protected StatementService getStatementService() {
        if (statementService == null) {
             statementService = (StatementService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/statement", "StatementService"));
        }
        return statementService;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return courseService;
    }
}
