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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.registration.dto.CourseOfferingInfoWrapper;
import org.kuali.student.enrollment.class2.registration.dto.RegistrationGroupInfoWrapper;
import org.kuali.student.enrollment.class2.registration.form.RegistrationForm;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
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
import java.util.List;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController extends UifControllerBase {

    private transient CourseOfferingService courseOfferingService;

    public UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new RegistrationForm();
    }

    /**
     * Initial method called when requesting a new view instance which forwards
     * the view for rendering
     */
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase formBase, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        return getUIFModelAndView(formBase);
    }

    /**
     * Method used to search for course offerings based on criteria entered
     */
    @RequestMapping(params = "methodToCall=searchCourseOfferings")
    public ModelAndView searchCourseOfferings(@ModelAttribute("KualiForm") UifFormBase formBase, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        RegistrationForm registrationForm = (RegistrationForm) formBase;
        ContextInfo context = ContextInfo.newInstance();

//        List<CourseOfferingInfoWrapper> courseOfferingInfoWrappers;

        try {
            List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(registrationForm.getTermKey(), registrationForm.getSubjectArea(), context);
            registrationForm.setCourseOfferings(new ArrayList<CourseOfferingInfoWrapper>(courseOfferingIds.size()));

            for(String coId : courseOfferingIds) {
                CourseOfferingInfoWrapper courseOfferingInfoWrapper = new CourseOfferingInfoWrapper();
                courseOfferingInfoWrapper.setCourseOffering(getCourseOfferingService().getCourseOffering(coId, context));
                List<RegistrationGroupInfo> regGroups = getCourseOfferingService().getRegGroupsForCourseOffering(coId, context);

                List<RegistrationGroupInfoWrapper> registrationGroupInfoWrappers = new ArrayList<RegistrationGroupInfoWrapper>(regGroups.size());
                for(RegistrationGroupInfo regGroup : regGroups) {
                    RegistrationGroupInfoWrapper registrationGroupInfoWrapper = new RegistrationGroupInfoWrapper();
                    registrationGroupInfoWrapper.setRegistrationGroupInfo(regGroup);

                    // TODO right now getOfferingsByIdList throws a not supported exception
//                    registrationGroupInfoWrapper.setActivityOfferingInfos(getCourseOfferingService().getActivityOfferingsByIdList(regGroup.getActivityOfferingIds(), context));

                    for(String activityId : regGroup.getActivityOfferingIds()) {
                        registrationGroupInfoWrapper.getActivityOfferingInfos().add(getCourseOfferingService().getActivityOffering(activityId, context));
                    }
                    registrationGroupInfoWrappers.add(registrationGroupInfoWrapper);

                }
                courseOfferingInfoWrapper.setRegistrationGroupInfoWrappers(registrationGroupInfoWrappers);
                registrationForm.getCourseOfferings().add(courseOfferingInfoWrapper);
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
        }

        return getUIFModelAndView(registrationForm);
    }

    /**
     * After the document is loaded calls method to setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=registerClass")
    public ModelAndView register(@ModelAttribute("KualiForm") UifFormBase formBase, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RegistrationForm registrationForm = (RegistrationForm) formBase;
        return getUIFModelAndView(registrationForm);
    }


    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "CourseOfferingService"));
        }

        return courseOfferingService;
    }
}
