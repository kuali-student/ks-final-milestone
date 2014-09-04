/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by prasannag on 2/9/14
 */
package org.kuali.student.cm.course.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.StartRetireCourseForm;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.util.CourseProposalUtil;
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
 *  This controller handles the request from course view when user retires a course.
 *
 *   @author Kuali Student Team
 *
 */


@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.START_RETIRE_COURSE)
public class StartRetireCourseController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        StartRetireCourseForm retireCourseForm= new StartRetireCourseForm();
        retireCourseForm.setCurriculumSpecialistUser(CourseProposalUtil.isUserCurriculumSpecialist());
        retireCourseForm.setCourseId(httpServletRequest.getParameter(CurriculumManagementConstants.UrlParams.CLU_ID));
        return retireCourseForm;
    }

    /**
     * After the Retire course initial data is filled call the method to show the navigation panel and
     * setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=continueRetireCourse")
    public ModelAndView continueRetireCourse(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();

        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.RETIRE_COURSE_PAGE);
        urlParameters.put(UifConstants.UrlParams.VIEW_ID, CurriculumManagementConstants.CourseViewIds.RETIRE_COURSE_VIEW);
        urlParameters.put(KRADConstants.PARAMETER_COMMAND, KewApiConstants.INITIATE_COMMAND);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, RetireCourseWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CourseProposalUtil.getViewCourseUrl() );
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.DOC_HANDLER_METHOD);
        urlParameters.put(CurriculumManagementConstants.UrlParams.CLU_ID, ((StartRetireCourseForm)form).getCourseId());
        String uri = request.getRequestURL().toString().replace(CurriculumManagementConstants.ControllerRequestMappings.START_RETIRE_COURSE,CurriculumManagementConstants.ControllerRequestMappings.CM_RETIRE_COURSE);

        return performRedirect(form, uri, urlParameters);
    }

}
