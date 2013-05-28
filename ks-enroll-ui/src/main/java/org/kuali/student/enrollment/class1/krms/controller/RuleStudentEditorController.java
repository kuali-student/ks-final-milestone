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
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class1.krms.service.impl.EnrolRuleViewHelperServiceImpl;
import org.kuali.student.common.uif.util.KSControllerHelper;
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

        super.route(form,result,request, response);

        return back(form,result,request,response);
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
        EnrolRuleEditor rule = ruleWrapper.getEnrolRuleEditor();
        EnrolPropositionEditor prop = (EnrolPropositionEditor) rule.getProposition();

        ruleWrapper.setClusInRange(this.getViewHelper(form).getCoursesInRange(prop.getCluSet().getMembershipQueryInfo()));
        rule.setClusInRange(ruleWrapper.getClusInRange());
        prop.getCluSet().setClusInRange(ruleWrapper.getClusInRange());

        form.setLightboxScript("showLightboxComponent('" + dialog + "');");
        return getUIFModelAndView(form);
     }

    @RequestMapping(params = "methodToCall=addRange")
    public ModelAndView addRange(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        CORuleManagementWrapper ruleWrapper = (CORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
        EnrolRuleEditor rule = ruleWrapper.getEnrolRuleEditor();
        EnrolPropositionEditor prop = (EnrolPropositionEditor) rule.getProposition();

        MembershipQueryInfo membershipQueryInfo = new MembershipQueryInfo();
        membershipQueryInfo.setId(null);

        if(rule.getSearchByCourseRange().equals("1")) {
            membershipQueryInfo.setSearchTypeKey("lu.search.mostCurrent.union");
            List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();

            SearchParamInfo courseCode = new SearchParamInfo();
            courseCode.setKey("lu.queryParam.luOptionalDivision");
            List<String> code = new ArrayList<String>();
            code.add(ruleWrapper.getCluSetRange().getSubjectCode());
            courseCode.setValues(code);
            queryParams.add(courseCode);

            SearchParamInfo courseRange = new SearchParamInfo();
            courseRange.setKey("lu.queryParam.luOptionalCrsNoRange");
            List<String> range = new ArrayList<String>();
            range.add(ruleWrapper.getCluSetRange().getCourseNumberRange());
            courseRange.setValues(range);
            queryParams.add(courseRange);

            SearchParamInfo courseState = new SearchParamInfo();
            courseState.setKey("lu.queryParam.luOptionalState");
            List<String> state = new ArrayList<String>();
            state.add("Draft");
            courseState.setValues(state);
            queryParams.add(courseState);

            membershipQueryInfo.setQueryParamValues(queryParams);

            rule.getCluSetRange().setCluSetRangeLabel("<b>Subject Code:</b> " + rule.getCluSetRange().getSubjectCode() + " <b>Course Number Range:</b> " + rule.getCluSetRange().getCourseNumberRange() + " <b>State:</b> Draft");
            ruleWrapper.setClusInRange(this.getViewHelper(form).getCoursesInRange(prop.getCluSet().getMembershipQueryInfo()));
        } else if(rule.getSearchByCourseRange().equals("2")) {
            membershipQueryInfo.setSearchTypeKey("lu.search.loByDescCrossSearch");
            List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();

            SearchParamInfo lo = new SearchParamInfo();
            lo.setKey("lu.queryParam.loDescPlain");
            List<String> desc = new ArrayList<String>();
            desc.add(ruleWrapper.getCluSetRange().getLearningObjective());
            lo.setValues(desc);
            queryParams.add(lo);

            membershipQueryInfo.setQueryParamValues(queryParams);

            rule.getCluSetRange().setCluSetRangeLabel("<b>Learning Objective:</b> " + rule.getCluSetRange().getLearningObjective());
        } else if(rule.getSearchByCourseRange().equals("3")) {
            membershipQueryInfo.setSearchTypeKey("lu.search.generic");
            List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();

            SearchParamInfo effectiveDate1 = new SearchParamInfo();
            effectiveDate1.setKey("lu.queryParam.luOptionalEffectiveDate1");
            List<String> date1 = new ArrayList<String>();
            date1.add(ruleWrapper.getCluSetRange().getEffectiveFrom().toString());
            effectiveDate1.setValues(date1);
            queryParams.add(effectiveDate1);

            SearchParamInfo effectiveDate2 = new SearchParamInfo();
            effectiveDate2.setKey("lu.queryParam.luOptionalEffectiveDate2");
            List<String> date2 = new ArrayList<String>();
            date2.add(ruleWrapper.getCluSetRange().getEffectiveTo().toString());
            effectiveDate2.setValues(date2);
            queryParams.add(effectiveDate2);

            membershipQueryInfo.setQueryParamValues(queryParams);

            rule.getCluSetRange().setCluSetRangeLabel("<b>Effective From:</b> " + rule.getCluSetRange().getEffectiveFrom() + " <b>Effective To:</b> " + rule.getCluSetRange().getEffectiveTo());
        }

        if(prop.getCluSet() == null) {
            CluSetInformation cluSet = new CluSetInformation();
            cluSet.setMembershipQueryInfo(membershipQueryInfo);
            cluSet.getCluSetRange().add(ruleWrapper.getCluSetRange());
            prop.setCluSet(cluSet);
        } else {
            prop.getCluSet().getCluSetRange().add(ruleWrapper.getCluSetRange());
            prop.getCluSet().setMembershipQueryInfo(membershipQueryInfo);
        }

        form.setLightboxScript("jQuery.fancybox.close();");
        return getUIFModelAndView(form);
    }

    protected EnrolRuleViewHelperServiceImpl getViewHelper(UifFormBase form) {
        return (EnrolRuleViewHelperServiceImpl) KSControllerHelper.getViewHelperService(form);
    }
}
