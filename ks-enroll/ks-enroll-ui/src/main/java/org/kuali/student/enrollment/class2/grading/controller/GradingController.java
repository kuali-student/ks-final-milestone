package org.kuali.student.enrollment.class2.grading.controller;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.entity.services.IdentityService;
import org.kuali.rice.kim.bo.entity.dto.KimEntityInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNameInfo;
import org.kuali.rice.kns.uif.UifParameters;
import org.kuali.rice.kns.uif.container.CollectionGroup;
import org.kuali.rice.kns.uif.util.ObjectPropertyUtils;
import org.kuali.rice.kns.web.spring.controller.UifControllerBase;
import org.kuali.rice.kns.web.spring.form.UifFormBase;
import org.kuali.student.enrollment.class2.grading.dataobject.GradeStudent;
import org.kuali.student.enrollment.class2.grading.form.GradingForm;
import org.kuali.student.enrollment.class2.grading.service.GradingViewHelperService;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/grading")
public class GradingController extends UifControllerBase{

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new GradingForm();
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=" + GradingConstants.LOAD_GRADES_ROSTER_METHOD)
    public ModelAndView loadGradeRoster(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String selectedCourse = gradingForm.getSelectedCourse();
        List<GradeStudent> students = ((GradingViewHelperService)gradingForm.getView().getViewHelperService()).loadStudents(selectedCourse);
        gradingForm.setStudents(students);

        return getUIFModelAndView(gradingForm, gradingForm.getViewId(),"page2");
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=unassignGrade")
	public ModelAndView unassignGrade(@ModelAttribute("KualiForm") GradingForm gradingForm, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

        String selectedCollectionPath = gradingForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for unassign action, cannot unassign grade");
        }

        int selectedLineIndex = -1;
        String selectedLine = gradingForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set for delete unassign action, cannot unassign grade");
        }

        ((GradingViewHelperService)gradingForm.getView().getViewHelperService()).unAssignGrade(gradingForm.getView(),gradingForm,selectedCollectionPath,selectedLineIndex);

         return getUIFModelAndView(gradingForm, gradingForm.getViewId(),"page2");
    }

}