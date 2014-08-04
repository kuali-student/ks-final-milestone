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
 * Created by venkat on 7/18/14
 */
package org.kuali.student.cm.course.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.CourseInitialForm;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * This controller maps to 'View Course' View.
 * XML: ViewCourseView.xml
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.VIEW_COURSE)
public class ViewCourseController extends KsUifControllerBase{

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ViewCourseForm();
    }

    /**
     * This method populates the course details model to be displayed at 'course view'.
     *
     * @param form
     * @param request
     * @return
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                              HttpServletResponse response) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;

        String courseId = request.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            throw new RuntimeException("Missing Course Id");
        }

        try {
            CourseInfoWrapper courseWrapper = new CourseInfoWrapper();
            ((CourseMaintainable)form.getViewHelperService()).setDataObject(courseWrapper);
            ((CourseMaintainable)form.getViewHelperService()).populateCourseAndReviewData(courseId, courseWrapper, true);
            detailedViewForm.setCourseInfoWrapper(courseWrapper);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=copyCourse")
    public ModelAndView copyCourse(@ModelAttribute("KualiForm") UifFormBase form) {

        ViewCourseForm detailedViewForm = (ViewCourseForm) form;

        Properties urlParameters = new Properties();
        /**
         * It should be always 'curriculum review' for both CS and faculty users for copy.
         */
        urlParameters.put(CourseController.UrlParams.USE_CURRICULUM_REVIEW,Boolean.TRUE.toString());
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CourseViewPageIds.CREATE_COURSE);
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_COPY);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
        urlParameters.put(CourseController.UrlParams.COPY_CLU_ID, detailedViewForm.getCourseInfoWrapper().getCourseInfo().getId());

        return performRedirect(form, CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE, urlParameters);

    }

}
