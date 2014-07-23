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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.CourseDetailedViewForm;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.service.CourseDetailedViewHelper;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

/**
 * This controller maps to 'Course Detailed' View.
 * XML: CourseDetailedView.xml
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.COURSE_DETAIL)
public class CourseDetailedViewController extends KsUifControllerBase{

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseDetailedViewForm();
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

        CourseDetailedViewForm detailedViewForm = (CourseDetailedViewForm) form;

        String courseId = request.getParameter("courseId");

        if (StringUtils.isBlank(courseId)) {
            throw new RuntimeException("Missing Course Id");
        }

        try {
            CourseInfoWrapper courseWrapper = new CourseInfoWrapper();
            ((CourseDetailedViewHelper)form.getViewHelperService()).setDataObject(courseWrapper);
            ((CourseDetailedViewHelper)form.getViewHelperService()).populateCourseAndReviewData(courseId, courseWrapper);
            detailedViewForm.setCourseInfoWrapper(courseWrapper);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(form);
    }

    protected CourseService getCourseService() {
        return (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
    }

    protected ProposalService getProposalService() {
        return (ProposalService) GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
    }
}
