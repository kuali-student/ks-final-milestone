package org.kuali.student.enrollment.class2.appointment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.enrollment.class2.appointment.service.AppointmentViewHelperService;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentConstants;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/registrationWindows")
public class RegistrationWindowsController extends UifControllerBase {
    
    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new RegistrationWindowsManagementForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        RegistrationWindowsManagementForm theForm = (RegistrationWindowsManagementForm)form;
        String termId = request.getParameter("termId");

        if (StringUtils.isNotBlank(termId)){
            try {
                getViewHelperService(theForm).loadTermAndPeriods(termId, theForm);
                return getUIFModelAndView(theForm);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return super.start(theForm, result, request, response);
    }

    /**
     * Method used to search atps
     */
    @RequestMapping(params = "methodToCall=searchForTerm")
    public ModelAndView searchForTerm(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm searchForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termType = searchForm.getTermType();
        String termYear = searchForm.getTermYear();

        // resetForm(searchForm);
        getViewHelperService(searchForm).searchForTerm(termType, termYear, searchForm);
        return getUIFModelAndView(searchForm, AppointmentConstants.REGISTRATION_WINDOWS_EDIT_PAGE);
    }

    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") RegistrationWindowsManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {


        String periodId = form.getPeriodId();
        String periodInfoDetails = new String();
        if (periodId != "all" && !periodId.isEmpty()) {
            // display the period start/end time in details and the period name in the AddLine
            AppointmentWindowWrapper addLine= (AppointmentWindowWrapper)form.getNewCollectionLines().get("appointmentWindows");
            KeyDateInfo period = getAcalService().getKeyDate(periodId,getContextInfo());
            if (period.getName() != null) {
                periodInfoDetails = period.getName()+" Start Date: "+period.getStartDate()+ "<br>"
                                   + period.getName()+" End Date: "+period.getEndDate();
                form.setPeriodName(period.getName());
                addLine.setPeriodName(period.getName());
            } else {
                periodInfoDetails = period.getId()+" Start Date: "+period.getStartDate()+ "<br>"
                        + period.getId()+" End Date: "+period.getEndDate();
                form.setPeriodName(period.getId());
                addLine.setPeriodName(period.getId());
            }
            form.setPeriodInfoDetails(periodInfoDetails);

            //TODO: pull out all windows for that period and add to the collection
        }
        return getUIFModelAndView(form);    
    }

    private AppointmentViewHelperService getViewHelperService(RegistrationWindowsManagementForm appointmentForm){
        if (appointmentForm.getView().getViewHelperServiceClassName() != null){
            return (AppointmentViewHelperService)appointmentForm.getView().getViewHelperService();
        }else{
            return (AppointmentViewHelperService)appointmentForm.getPostedView().getViewHelperService();
        }
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

}
