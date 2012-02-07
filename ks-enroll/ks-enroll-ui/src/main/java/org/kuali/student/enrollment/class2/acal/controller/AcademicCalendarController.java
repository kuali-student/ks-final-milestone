/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.enrollment.class2.acal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/academicCalendar")
public class AcademicCalendarController extends UifControllerBase {
    /**
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#createInitialForm(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new AcademicCalendarForm();
    }

    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        AcademicCalendarInfo academicCalendarInfo = academicCalendarForm.getAcademicCalendarInfo();

        if(academicCalendarInfo.getId() != null && !academicCalendarInfo.getId().trim().isEmpty()){
            // edit ac
            AcademicCalendarInfo acalInfo = getAcademicCalendarViewHelperService(academicCalendarForm).updateAcademicCalendar(academicCalendarForm);
            academicCalendarForm.setAcademicCalendarInfo(getAcademicCalendarViewHelperService(academicCalendarForm).getAcademicCalendar(acalInfo.getId()));
        }
        else {
            // create ac
            AcademicCalendarInfo acalInfo = getAcademicCalendarViewHelperService(academicCalendarForm).createAcademicCalendar(academicCalendarForm);
            academicCalendarForm.setAcademicCalendarInfo(acalInfo);
        }
        return getUIFModelAndView(academicCalendarForm);
    }

    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=saveTerm")
    public ModelAndView saveTerm(@ModelAttribute("KualiForm") AcademicCalendarForm academicCalendarForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        try{
            ((AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService()).saveTerm(academicCalendarForm, context);
            GlobalVariables.getMessageMap().putInfo("name","info.enroll.term.saved");
        }catch (Exception e){
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
           throw new RuntimeException(e);
        }

        return getUIFModelAndView(academicCalendarForm);
    }

    private AcademicCalendarViewHelperService getAcademicCalendarViewHelperService(AcademicCalendarForm academicCalendarForm){
        return (AcademicCalendarViewHelperService)academicCalendarForm.getView().getViewHelperService();
    }

}
