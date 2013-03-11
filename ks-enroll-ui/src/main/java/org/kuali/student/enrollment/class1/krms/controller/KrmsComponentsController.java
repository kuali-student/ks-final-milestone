/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by bobhurt on 8/7/12
 */
package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.impl.ui.TermParameter;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.form.KrmsComponentsForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/krmsComponents")
public class KrmsComponentsController extends UifControllerBase {

    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#createInitialForm(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected KrmsComponentsForm createInitialForm(HttpServletRequest request) {
        return new KrmsComponentsForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        KrmsComponentsForm uiTestForm = (KrmsComponentsForm) form;

//        uiTestForm.setProposition(new PropositionEditor());
//        uiTestForm.setRuleEditor(new RuleEditor());
//        uiTestForm.setRulePreviewer(new RuleCompareTreeBuilder());
        return getUIFModelAndView(uiTestForm);
    }
    @RequestMapping(params = "methodToCall=addRange")
    public ModelAndView addRange(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dialog1 = "courseRangeLightBox";
        KrmsComponentsForm krmsComponentsForm = (KrmsComponentsForm) form;
        TermParameter termParameter = new TermParameter();
        if (krmsComponentsForm.getSearchByCourseRange().equals("1")) {
            termParameter.setParameter("Subject Code: " + krmsComponentsForm.getSubjectCode() + " Course Number Range: " + krmsComponentsForm.getCourseNumberRange());
        } else if (krmsComponentsForm.getSearchByCourseRange().equals("2")) {
//            termParameter.setParameter("SubjectCode: " + krmsComponentsForm.getSubjectCode() + "CourseNumberRange: " + krmsComponentsForm.getCourseNumberRange());
        } else if (krmsComponentsForm.getSearchByCourseRange().equals("3")) {
//            termParameter.setParameter("SubjectCode: " + krmsComponentsForm.getSubjectCode() + "CourseNumberRange: " + krmsComponentsForm.getCourseNumberRange());
        } else {
            return showDialog(dialog1, form, request, response);
        }

        krmsComponentsForm.getProposition().getTermParameterList().add(termParameter);
        form.getDialogManager().addDialog(dialog1,"start" );
        // clear dialog history so they can press the button again
        form.getDialogManager().removeDialog(dialog1);

        // reload page1
        return getUIFModelAndView(krmsComponentsForm);

    }

    @RequestMapping(params = "methodToCall=updateChanges")
    public ModelAndView updateChanges(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        KrmsComponentsForm krmsComponentsForm = (KrmsComponentsForm) form;
        PropositionEditor proposition = krmsComponentsForm.getProposition();
        proposition.getTermParameter();

        return getUIFModelAndView(form);
    }
}
