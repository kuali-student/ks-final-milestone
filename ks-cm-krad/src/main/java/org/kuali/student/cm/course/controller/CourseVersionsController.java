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
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.CourseViewIds;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.UrlParams;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.ControllerRequestMappings;
import org.kuali.student.cm.course.form.CourseVersionsForm;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.VersionWrapper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.service.CourseVersionsViewHelper;
import org.kuali.student.cm.course.service.impl.ExportCourseHelperImpl;
import org.kuali.student.common.object.KSObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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

    @MethodAccessible
    @RequestMapping(params = "methodToCall=showVersionHistory")
    public ModelAndView showVersionHistory(@ModelAttribute(UifConstants.DEFAULT_MODEL_NAME) CourseVersionsForm form,
                                     HttpServletRequest request) {

        CourseVersionsForm courseVersionsForm = (CourseVersionsForm) form;

        CourseVersionsViewHelper viewHelper = (CourseVersionsViewHelper) form.getViewHelperService();

        String versionIndependentId = request.getParameter(UrlParams.VERSION_INDEPENDENT_ID);
        courseVersionsForm.setVersions(viewHelper.getVersions(versionIndependentId));

        String courseTitle =  viewHelper.getCourseTitle(versionIndependentId);
        courseVersionsForm.setCourseTitle(courseTitle);

        courseVersionsForm.setVersionIndependentId(versionIndependentId);

        return getUIFModelAndView(form);
    }


    @RequestMapping(params = "methodToCall=showVersions")
    public ModelAndView showVersions(@ModelAttribute(UifConstants.DEFAULT_MODEL_NAME) CourseVersionsForm form,
                                     HttpServletRequest request) {
        /*
         * Get the cluIds of the courses to compare.
         */
        List<String> selectedCluIds = new ArrayList<>();
        for (VersionWrapper versionWrapper : form.getVersions()) {
            if (versionWrapper.isChecked()) {
                selectedCluIds.add(versionWrapper.getCluId());
            }
        }

        Properties urlParameters = new Properties();
        urlParameters.put(UifConstants.UrlParams.VIEW_ID, CourseViewIds.VIEW_COURSE_VIEW);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");

        /*
         * If a single course was selected then we only set the courseId request param. If there were two
         * selections then set the compareCourseId. Otherwise, error.
         */
        int size = selectedCluIds.size();
        if (size >= 1 && size <= 2) {
            urlParameters.put(UrlParams.COURSE_ID, selectedCluIds.get(0));
            if (selectedCluIds.size() == 2) {
               urlParameters.put(UrlParams.COMPARE_COURSE_ID, selectedCluIds.get(1));
            }
        } else {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,
                    "Invalid request parameters.");
            return getUIFModelAndView(form);
        }

        //  Replace the request mapping part of the URL.
        String uri = request.getRequestURL().toString()
                .replace(ControllerRequestMappings.COURSE_VERSIONS, ControllerRequestMappings.VIEW_COURSE);

        String redirectUrl = UrlFactory.parameterizeUrl(uri, urlParameters);
        form.setRedirectUri(redirectUrl);

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @ResponseBody
    @RequestMapping(params = "methodToCall=getRedirectUri")
    public ModelAndView getRedirectUri(@ModelAttribute(UifConstants.DEFAULT_MODEL_NAME) CourseVersionsForm form, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
        String uri = String.format("{\"uri\": \"%s\"}", form.getRedirectUri());
        try {
            response.getWriter().println(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
