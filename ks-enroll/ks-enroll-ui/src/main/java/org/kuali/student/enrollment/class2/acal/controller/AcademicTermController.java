package org.kuali.student.enrollment.class2.acal.controller;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.AcademicTermForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicTermEditViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/academicTerm")
public class AcademicTermController extends UifControllerBase {


    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new AcademicTermForm();
    }

    @RequestMapping(params = "methodToCall=editTerm")
    public ModelAndView editTerm(@ModelAttribute("KualiForm") AcademicTermForm academicTermForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        String termId = request.getParameter("termId");
        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();
        try{
            ((AcademicTermEditViewHelperService)academicTermForm.getView().getViewHelperService()).buildTerm(termId,academicTermForm,context);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(academicTermForm);
    }

    /**
     * Method used to save AcademicCalendar
     */
    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") AcademicTermForm academicTermForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) {

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        try{
            ((AcademicTermEditViewHelperService)academicTermForm.getView().getViewHelperService()).saveTerm(academicTermForm, context);
        }catch (Exception e){
            //TODO:For now, throw RTE, have to look into proper way of handling exceptions.
           throw new RuntimeException(e);
        }

        return getUIFModelAndView(academicTermForm);
    }
}
