/**
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.student.cm.course.controller;

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.form.CreateCourseForm;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 *
 *  This controller handles all the requests Curriculum Home View  'Create a Course' UI.
 *
 *   @author Kuali Student Team
 *
 */

@Controller
@RequestMapping(value = "/createcourse")
public class CreateCourseInitialController  extends UifControllerBase {

    private final String RETURN_LOCATION_PARAMETER = "cmHome?methodToCall=start&viewId=curriculumHomeView";
    private final String DISPATCH_REQUEST_PARAMETER = "docHandler";
    private final String PAGE_ID = "KS-CourseView-CoursePage";

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        CreateCourseForm createCourseForm = new CreateCourseForm();
        return createCourseForm;  //To change body of implemented methods use File | Settings | File Templates.
    }



    /**
     * After the Craete course initial data is filled call the method to show the navigation panel and
     * setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=continueCreateCourse")
    public ModelAndView continueCreateCourse(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        final CreateCourseForm maintenanceDocForm = (CreateCourseForm) form;
        Boolean isUseReviewProcess = maintenanceDocForm.isUseReviewProcess();
        Properties urlParameters = new Properties();
        if (maintenanceDocForm.isCurriculumSpecialist() && !maintenanceDocForm.isUseReviewProcess()){
            urlParameters.put(KRADConstants.DOCUMENT_TYPE_NAME, CLUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN);
        }
        urlParameters.put("pageId", PAGE_ID);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, DISPATCH_REQUEST_PARAMETER);
        urlParameters.put(KRADConstants.PARAMETER_COMMAND, KewApiConstants.INITIATE_COMMAND);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, RETURN_LOCATION_PARAMETER );
        urlParameters.put("UseReviewProcess",isUseReviewProcess.toString() );
        String uri = request.getRequestURL().toString().replace("createcourse","courses");

        return performRedirect(form, uri, urlParameters);
    }
}
