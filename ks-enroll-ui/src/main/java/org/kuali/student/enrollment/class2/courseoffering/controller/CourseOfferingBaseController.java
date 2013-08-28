package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingEditMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class CourseOfferingBaseController extends MaintenanceDocumentController {

    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        return new KSUifMaintenanceDocumentForm();
    }

    /* Returns a ModelAndView for the route()-method to return to original view if there were errors.
     * Should only be called if there are indeed errors.
     */
    protected ModelAndView handleRouteForErrors(DocumentFormBase form) {

        assert ( GlobalVariables.getMessageMap().hasErrors() );

        if (((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
            CourseOfferingEditMaintainableImpl viewHelper = (CourseOfferingEditMaintainableImpl)KSControllerHelper.getViewHelperService(form);
            //Make the format type drop down readonly.. otherwise, we run into display issue when the server returns back error
            CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            viewHelper.populateFormatNames(dataObject);
        }

        return getUIFModelAndView(form);    // because there were errors, return a MAV to re-nav back to
    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        String urlToRedirectTo = "";
        Properties urlParameters = new Properties();

        // determine which url to redirect to
        String returnLocationFromForm = form.getReturnLocation();
        if( StringUtils.contains( returnLocationFromForm,"viewId=courseOfferingManagementView" )
                || StringUtils.contains( returnLocationFromForm,"pageId=manageTheCourseOfferingPage" ) )
        {
            if ( !returnLocationFromForm.contains("methodToCall=") ) {  // This happens when we display a list of COs and then user click on Manage action
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.TRUE.toString());
            }
            else {
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.FALSE.toString());
            }
            urlToRedirectTo = returnLocationFromForm.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
        }
        else {
            urlToRedirectTo = returnLocationFromForm;
        }

        String loadNewCO = form.getActionParameters().get( "coId" );
        if( StringUtils.isNotBlank( loadNewCO ) ) {

            urlParameters.put( KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT );
            urlParameters.put( "courseOfferingInfo.id", loadNewCO );
            urlParameters.put( KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName() );
            urlParameters.put( "returnLocation", urlToRedirectTo );

            urlToRedirectTo = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_EDIT_MAINTENANCE;
            GlobalVariables.getUifFormManager().removeSessionForm(form);
            return performRedirect(form, urlToRedirectTo, urlParameters);
        }

        form.setReturnLocation( urlToRedirectTo );
        return back(form,result,request,response);
    }


    /**
     * This override is specifically for making sure users selected the format types before adding new lines (there is no
     * util method available at view helper).
     *
     * @param form
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addBlankLine")
    public ModelAndView addBlankLine(@ModelAttribute("KualiForm") UifFormBase form) {

        boolean validAction = true;
        if (((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
            CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
            if (StringUtils.endsWith(selectedCollectionPath, "formatOfferingList")) {
                for (FormatOfferingWrapper foWrapper : dataObject.getFormatOfferingList()){
                    if (StringUtils.isBlank(foWrapper.getFormatOfferingInfo().getFormatId())){
                        GlobalVariables.getMessageMap().putError("KS-CourseOfferingEdit-DeliveryFormats",CourseOfferingConstants.COURSEOFFERING_FORMAT_REQUIRED);
                        validAction = false;
                        break;
                    }
                }
            }
        }

        if (validAction){
            return super.addBlankLine(form);
        } else {
            return getUIFModelAndView(form);
        }
    }
}
