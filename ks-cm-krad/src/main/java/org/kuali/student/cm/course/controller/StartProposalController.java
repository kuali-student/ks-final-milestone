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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.StartProposalForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.proposal.util.ProposalUtil;
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
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.START_PROPOSAL)
public class StartProposalController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        StartProposalForm courseForm= new StartProposalForm();
        courseForm.setUseReviewProcess(false);
        // cannot pass in document type here because we do not now if this is a Create or a Modify
        courseForm.setCurriculumSpecialistUser(ProposalUtil.isUserCurriculumSpecialist());
        String versionIndId = httpServletRequest.getParameter(CurriculumManagementConstants.UrlParams.VERSION_IND_ID);
        courseForm.setVersionIndId(versionIndId);
        String courseId = httpServletRequest.getParameter(CurriculumManagementConstants.UrlParams.CLU_ID);
        String isModifyNewVersion = httpServletRequest.getParameter(CurriculumManagementConstants.UrlParams.IS_MODIFY_NEW_VERSION);
        courseForm.setModifiableCourse(new Boolean(isModifyNewVersion));
        String isCourseWithVersion = httpServletRequest.getParameter(CurriculumManagementConstants.UrlParams.IS_COURSE_WITH_VERSION);
        courseForm.setCourseWithVersion(new Boolean(isCourseWithVersion));
        courseForm.setCourseId(courseId);
        return courseForm;
    }


    protected void setUrlParameters(Properties urlParameters,UifFormBase form){

        // cannot pass in document type here because we do not now if this is a Create or a Modify
        if (!ProposalUtil.isUserCurriculumSpecialist()) {
            // if user is not a CS user, then curriculum review must be used because only CS users can disable curriculum review
            urlParameters.put(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW,Boolean.TRUE.toString());
        } else {
            // if user is a CS user, check the checkbox value
            urlParameters.put(CurriculumManagementConstants.UrlParams.USE_CURRICULUM_REVIEW,Boolean.toString(((StartProposalForm) form).isUseReviewProcess()));
        }
        urlParameters.put(UifConstants.UrlParams.PAGE_ID, CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE);
        urlParameters.put(KRADConstants.PARAMETER_COMMAND, KewApiConstants.INITIATE_COMMAND);
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseInfoWrapper.class.getName());
        urlParameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl() );
    }

    /**
     * After the Create course initial data is filled call the method to show the navigation panel and
     * setup the maintenance object
     */
    @RequestMapping(params = "methodToCall=continueCreateCourse")
    public ModelAndView continueCreateCourse(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        setUrlParameters(urlParameters,form);

        String startProposalCourseAction = ((StartProposalForm)form).getStartProposalCourseAction();
        if( StringUtils.equalsIgnoreCase(startProposalCourseAction,CurriculumManagementConstants.ModifyCourseStartOptions.MODIFY_WITH_A_NEW_VERSION)) {

            urlParameters.put(CurriculumManagementConstants.UrlParams.VERSION_IND_ID,((StartProposalForm)form).getVersionIndId());
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, startProposalCourseAction);
            urlParameters.put(CourseController.UrlParams.MODIFY_ACTION, startProposalCourseAction);
        }
        else if(StringUtils.equalsIgnoreCase(startProposalCourseAction,CurriculumManagementConstants.ModifyCourseStartOptions.MODIFY_THIS_VERSION)){
            urlParameters.put(CurriculumManagementConstants.UrlParams.CLU_ID , ((StartProposalForm)form).getCourseId()) ;
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, startProposalCourseAction);
        }

        else{
            setMethodToCall(urlParameters, (StartProposalForm)form);
        }
        String uri = request.getRequestURL().toString().replace(CurriculumManagementConstants.ControllerRequestMappings.START_PROPOSAL,CurriculumManagementConstants.ControllerRequestMappings.COURSE_MAINTENANCE);
        return performRedirect(form, uri, urlParameters);
    }

    /**
     * This methods sets urlParameters with 'methodtocall' property depending on 'ProposalCourseStartOptions'
     *
     * @param urlParameters is properties which needs to be set with 'methodtocall' property.
     * @param form
     */
    protected void setMethodToCall(Properties urlParameters, StartProposalForm form ){

        String startProposalCourseAction = form.getStartProposalCourseAction();

        if(StringUtils.equalsIgnoreCase(startProposalCourseAction,CurriculumManagementConstants.ProposalCourseStartOptions.BLANK_PROPOSAL)) {
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.DOC_HANDLER_METHOD);
            return;
        }

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_COPY);

        if(StringUtils.equalsIgnoreCase(startProposalCourseAction,CurriculumManagementConstants.ProposalCourseStartOptions.COPY_APPROVED_COURSE)) {
            urlParameters.put(CourseController.UrlParams.COPY_CLU_ID, form.getCourseId());
        } else if(StringUtils.equalsIgnoreCase(startProposalCourseAction,CurriculumManagementConstants.ProposalCourseStartOptions.COPY_PROPOSED_COURSE)) {
            urlParameters.put(CourseController.UrlParams.COPY_PROPOSAL_ID, form.getProposalId());
        } else {
            throw new RuntimeException("This should not happen.");
        }
    }

}
