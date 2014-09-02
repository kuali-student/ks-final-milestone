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
 */
package org.kuali.student.cm.course.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.ControllerRequestMappings;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.course.form.CourseVersionsForm;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.service.CourseVersionsViewHelper;
import org.kuali.student.cm.course.service.impl.ExportCourseHelperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * This controller maps to 'Course Versions' View.
 * XML: CourseVersionsView.xml
 */
@Controller
@RequestMapping(value = ControllerRequestMappings.COURSE_VERSIONS)
public class CourseVersionsController extends KsUifControllerBase {

    @Override
    protected CourseVersionsForm createInitialForm(HttpServletRequest request) {
        return new CourseVersionsForm();
    }

    /**
     * This method populates the course details model to be displayed at 'course view'.
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                              HttpServletResponse response) {

        CourseVersionsForm courseVersionsForm = (CourseVersionsForm) form;
        CourseVersionsViewHelper viewHelper = (CourseVersionsViewHelper) form.getViewHelperService();

        String versionIndependentId = request.getParameter(CurriculumManagementConstants.UrlParams.VERSION_INDEPENDENT_ID);

        courseVersionsForm.setVersions(viewHelper.getVersions(versionIndependentId));

        /*String courseId = request.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            throw new RuntimeException("Missing Course Id");
        }

        try {
            CourseInfoWrapper courseWrapper = new CourseInfoWrapper();
            ((CourseMaintainable)form.getViewHelperService()).setDataObject(courseWrapper);
            ((CourseMaintainable)form.getViewHelperService()).populateCourseAndReviewData(courseId, courseWrapper, true);
            detailedViewForm.setCourseInfoWrapper(courseWrapper);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        return getUIFModelAndView(form);
    }
}
