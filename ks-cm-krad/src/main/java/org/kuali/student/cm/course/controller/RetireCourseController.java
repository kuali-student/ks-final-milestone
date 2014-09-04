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
 * Created by prasannag on 1/9/14
 */
package org.kuali.student.cm.course.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.RecentlyViewedDocsUtil;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.service.RetireCourseMaintainable;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller handles all the requests from the 'Retire Course' UI
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.CM_RETIRE_COURSE)
public class RetireCourseController extends CourseController {

    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        MaintenanceDocumentForm form = new MaintenanceDocumentForm();

            // set the doc type name based on the whether the user is CS and if they have chosen to use curriculum review
            form.setDocTypeName(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_RETIRE);

        return form;
    }

    /**
     * Digs the CourseInfoWrapper out of DocumentFormBase.
     *
     * @param form The DocumentFormBase.
     * @return The CourseInfoWrapper.
     */
    protected RetireCourseWrapper getRetireCourseWrapper(DocumentFormBase form) {
        return ((RetireCourseWrapper) ((MaintenanceDocumentForm) form).getDocument().getNewMaintainableObject().getDataObject());
    }

    /**
     * Overridden because we needed a point at which the form is loaded with the document before we can initialize some
     * form fields. This method is used for when a new document is created.
     *
     * @param form - form instance that contains the doc type parameter and where
     *             the new document instance should be set
     */
    @Override
    protected void createDocument(DocumentFormBase form) throws WorkflowException {
        super.createDocument(form);
        // at this point we have the document type name set on the form so we use it to update the Course specific fields
        updateCourseForm(form);
    }

    /**
     * Overridden because we needed a point at which the form is loaded with the document before we can initialize some
     * form fields. This method is used for loading an existing document.
     *
     * @param form - form instance that contains the doc id parameter and where
     *             the retrieved document instance should be set
     */
    @Override
    protected void loadDocument(DocumentFormBase form) throws WorkflowException {
        super.loadDocument(form);
        // at this point we have the document type name set on the form so we use it to update the Course specific fields
        updateCourseForm(form);
    }

    /**
     * Updates the MaintenanceDocumentForm to set the 'useReviewProcess' property based on the document type name. If
     * the document type name is CurriculumManagementConstants#DocumentTypeNames#CourseProposal#COURSE_CREATE_ADMIN or
     * CurriculumManagementConstants#DocumentTypeNames#CourseProposal#COURSE_MODIFY_ADMIN then set 'useReviewProcss' to
     * false.
     *
     * @param form the DocumentFormBase object to update
     */
    protected void updateCourseForm(DocumentFormBase form) {
        RetireCourseWrapper wrapper = getRetireCourseWrapper(form);
        wrapper.getUiHelper()
                .setCurriculumSpecialistUser(CourseProposalUtil.isUserCurriculumSpecialist());

        String courseId = StringUtils.join(form.getInitialRequestParameters().get(CurriculumManagementConstants.UrlParams.CLU_ID));

        //String courseId = Arrays.toString(form.getInitialRequestParameters().get(CurriculumManagementConstants.UrlParams.CLU_ID));
        if (StringUtils.isBlank(courseId)) {
            throw new RuntimeException("Missing Course Id");
        }
        try{
            ((RetireCourseMaintainable)form.getViewHelperService()).populateCourseData(courseId, wrapper);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is being overridden
     * <p/>
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(params = "methodToCall=docHandler")
    public ModelAndView docHandler(@ModelAttribute("KualiForm") DocumentFormBase formBase, BindingResult result, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = super.docHandler(formBase, result, request, response);
        return modelAndView;
    }

    @Override
    public ModelAndView saveProposal(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RetireCourseWrapper wrapper = (RetireCourseWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        form.getDocument().getDocumentHeader().setDocumentDescription(wrapper.getProposalInfo().getName());

        ModelAndView modelAndView;

        modelAndView = save(form, result, request, response);

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return modelAndView;
        }

        RecentlyViewedDocsUtil.addRecentDoc(form.getDocument().getDocumentHeader().getDocumentDescription(),
                form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentHandlerUrl() + "&"
                        + KewApiConstants.COMMAND_PARAMETER + "="
                        + KewApiConstants.DOCSEARCH_COMMAND + "&"
                        + KewApiConstants.DOCUMENT_ID_PARAMETER + "="
                        + form.getDocument().getDocumentHeader().getWorkflowDocument().getDocumentId());

        String nextOrCurrentPage = form.getActionParameters().get("displayPage");

        if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, "NEXT")) {
            CurriculumManagementConstants.CourseRetireSections currentSection = (CurriculumManagementConstants.CourseRetireSections)wrapper.getUiHelper().getSelectedSection();
            if (currentSection.ordinal() < CurriculumManagementConstants.CourseRetireSections.values().length) {
                CurriculumManagementConstants.CourseRetireSections nextSection = CurriculumManagementConstants.CourseRetireSections.values()[currentSection.ordinal() + 1];
                wrapper.getUiHelper().setSelectedSection(nextSection);
            }
            return getUIFModelAndView(form);
        } else if (StringUtils.equalsIgnoreCase(nextOrCurrentPage, "CM-Proposal-Course-View-ReviewProposalLink")) {
            return getUIFModelAndView(form, CurriculumManagementConstants.CoursePageIds.REVIEW_COURSE_PROPOSAL_PAGE);
        } else {
            return getUIFModelAndView(form);
        }
    }


    }
