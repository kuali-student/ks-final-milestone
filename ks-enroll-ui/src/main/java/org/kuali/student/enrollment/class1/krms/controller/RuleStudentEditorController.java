/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.controller.RuleEditorController;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class1.krms.service.impl.EnrolRuleViewHelperServiceImpl;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class1.krms.util.CluSetRangeHelper;
import org.kuali.student.krms.KRMSConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Test UI Page
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Controller
@RequestMapping(value = KRMSConstants.WebPaths.RULE_STUDENT_EDITOR_PATH)
public class RuleStudentEditorController extends RuleEditorController {

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        super.route(form, result, request, response);

        return back(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        return back(form,result,request,response);
    }

    @Override
    @RequestMapping(params = "methodToCall=addRule")
    public ModelAndView addRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();
        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;

        RuleEditor ruleEditor = AgendaUtilities.getSelectedRuleEditor(document);
        EnrolRuleEditor enrolRuleEditor = new EnrolRuleEditor(ruleEditor.getKey(), false, ruleEditor.getRuleTypeInfo());
        AgendaUtilities.getRuleWrapper(document).setRuleEditor(enrolRuleEditor);

        this.getViewHelper(form).refreshInitTrees(enrolRuleEditor);

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KRMS-RuleMaintenance-Page");
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params="methodToCall=viewCourseRange")
    public ModelAndView viewCourseRange(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dialog = "courseRangeLookup";
        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        CORuleManagementWrapper ruleWrapper = (CORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
        EnrolPropositionEditor prop = (EnrolPropositionEditor) ruleWrapper.getEnrolRuleEditor().getProposition();

        ruleWrapper.setClusInRange(this.getViewHelper(form).getCoursesInRange(prop.getCluSet().getMembershipQueryInfo()));

        form.setLightboxScript("showLightboxComponent('" + dialog + "');");
        return getUIFModelAndView(form);
     }

    @RequestMapping(params = "methodToCall=addRange")
    public ModelAndView addRange(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        EnrolRuleEditor rule = (EnrolRuleEditor) getRuleEditor(form);
        EnrolPropositionEditor prop = (EnrolPropositionEditor) PropositionTreeUtil.getProposition(rule);

        if(prop.getCluSet()==null){
            prop.setCluSet(new CluSetInformation());
        }

        if(prop.getCluSet().getMembershipQueryInfo()==null){
            prop.getCluSet().setMembershipQueryInfo(new MembershipQueryInfo());
        }

        MembershipQueryInfo membershipQueryInfo = prop.getCluSet().getMembershipQueryInfo();
        CluSetRangeHelper cluSetRange = prop.getCluSetRange();

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        if(prop.getCluSetRange().getSearchByCourseRange().equals(CluSetRangeHelper.COURSE_RANGE_COURSE_NUMBER)) {

            membershipQueryInfo.setSearchTypeKey("lu.search.mostCurrent.union");

            queryParams.add(createSearchParam("lu.queryParam.luOptionalDivision", cluSetRange.getSubjectCode()));
            queryParams.add(createSearchParam("lu.queryParam.luOptionalCrsNoRange", cluSetRange.getCourseNumberRange()));
            queryParams.add(createSearchParam("lu.queryParam.luOptionalState", "Draft"));
            membershipQueryInfo.setQueryParamValues(queryParams);

            cluSetRange.setCluSetRangeLabel("<b>Subject Code:</b> " + cluSetRange.getSubjectCode() + " <b>Course Number Range:</b> " + cluSetRange.getCourseNumberRange() + " <b>State:</b> Draft");

        } else if(cluSetRange.getSearchByCourseRange().equals(CluSetRangeHelper.COURSE_RANGE_LEARNING_OBJECTIVES)) {

            membershipQueryInfo.setSearchTypeKey("lu.search.loByDescCrossSearch");

            queryParams.add(createSearchParam("lu.queryParam.loDescPlain", cluSetRange.getLearningObjective()));
            membershipQueryInfo.setQueryParamValues(queryParams);

            cluSetRange.setCluSetRangeLabel("<b>Learning Objective:</b> " + cluSetRange.getLearningObjective());

        } else if(cluSetRange.getSearchByCourseRange().equals(CluSetRangeHelper.COURSE_RANGE_EFFECTIVE_DATE)) {

            membershipQueryInfo.setSearchTypeKey("lu.search.generic");

            queryParams.add(createSearchParam("lu.queryParam.luOptionalEffectiveDate1", cluSetRange.getEffectiveFrom().toString()));
            queryParams.add(createSearchParam("lu.queryParam.luOptionalEffectiveDate2", cluSetRange.getEffectiveTo().toString()));

            cluSetRange.setCluSetRangeLabel("<b>Effective From:</b> " + cluSetRange.getEffectiveFrom() + " <b>Effective To:</b> " + cluSetRange.getEffectiveTo());

        }

        membershipQueryInfo.setQueryParamValues(queryParams);

        return getUIFModelAndView(form);
    }

    private SearchParamInfo createSearchParam(String key, String value){
        SearchParamInfo param = new SearchParamInfo();
        param.setKey(key);

        List<String> values = new ArrayList<String>();
        values.add(value);
        param.setValues(values);

        return param;
    }

    protected EnrolRuleViewHelperServiceImpl getViewHelper(UifFormBase form) {
        return (EnrolRuleViewHelperServiceImpl) KSControllerHelper.getViewHelperService(form);
    }
}
