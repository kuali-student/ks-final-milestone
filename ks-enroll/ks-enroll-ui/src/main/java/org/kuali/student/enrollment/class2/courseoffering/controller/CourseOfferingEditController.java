/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 8/20/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class provides controller methods for Course Offering Edit ui
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/courseOfferingEdit")
public class CourseOfferingEditController extends CourseOfferingBaseController {

    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        return new KSUifMaintenanceDocumentForm();
    }

    @Override
    public ModelAndView maintenanceEdit(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
            String crossListedAlias = request.getParameter("editCrossListedCoAlias");
            if(StringUtils.equals(crossListedAlias, "true")) {
                Object selectedObject =  form.getDocument().getNewMaintainableObject().getDataObject();
                if(selectedObject instanceof CourseOfferingEditWrapper) {
                    ((CourseOfferingEditWrapper) selectedObject).setEditCrossListedCoAlias(true);
                }
            }
        }

        return getUIFModelAndView(form);
    }

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        try {
            ControllerTransactionHelper helper = CourseOfferingManagementUtil.getControllerTransactionHelper();
            helper.routeSuper(form, result, request, response, this);
        } catch (RuntimeException ex) {
            /*
            Version mismatch exceptions are caught in the KEW and sent back as a RuntimeException -- check the
            root cause, and if it's a version mismatch, display a user-friendly error message.
             */
            String rootCause = ExceptionUtils.getRootCause(ex).getClass().getName();
            if (rootCause.equals(VersionMismatchException.class.getName())) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_ERROR_VERSION_MISMATCH);
            } else {
                throw ex;
            }
        }

        if (GlobalVariables.getMessageMap().hasErrors()) {
            return handleRouteForErrors(form);
        }

        return handleRouteForCoEdit(form);
    }

    /**
     * This method is called by a helper service to perform the super.route method. It's indirect, but we need a new
     * transaction boundary around this call to support handling errors without causing a rollback exception in the UI
     *
     * @return model and view
     */
    public ModelAndView routeSuper(DocumentFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response) {
        return super.route(form, result, request, response);
    }

    /* Returns a ModelAndView for the route()-method to return a new view if we are editing a CO */
    private ModelAndView handleRouteForCoEdit( DocumentFormBase form ) {

        String urlToRedirectTo;
        Properties urlParameters = new Properties();

        GlobalVariables.getUifFormManager().removeSessionForm(form);    // clear current form from session

        // determine which url to redirect to
        String returnLocationFromForm = form.getReturnLocation();
        if ( StringUtils.contains(returnLocationFromForm,"viewId=courseOfferingManagementView")
                || StringUtils.contains(returnLocationFromForm,"pageId=manageTheCourseOfferingPage")) {
            // wrap with HashMap since viewRequestParameters is set with Collections.unmodifiableMap()
            // in org.kuali.rice.krad.uif.view.View.setViewRequestParameters()
            Map<String, String> additionalParameters = new HashMap<String, String>(form.getViewRequestParameters());
            if (!returnLocationFromForm.contains("methodToCall=")) {
                // This happens when we display a list of COs and then user click on Manage action
                additionalParameters.put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.TRUE.toString());
            }
            else {
                additionalParameters.put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.FALSE.toString());
            }
            form.setViewRequestParameters(additionalParameters);
            urlToRedirectTo = returnLocationFromForm.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
        } else if (StringUtils.contains(returnLocationFromForm,"pageId=manageCourseOfferingsPage")
                && !returnLocationFromForm.contains("methodToCall=")) {
            urlToRedirectTo = returnLocationFromForm + "&methodToCall=show";
        } else {
            urlToRedirectTo = returnLocationFromForm;
        }

        //remove growl message param from request url
        urlToRedirectTo = urlToRedirectTo.replaceAll("growl.[^&]+&","");

        // special handling if navigating to a specific CO
        String loadNewCO = form.getActionParameters().get( "coId" );
        if( StringUtils.isNotBlank( loadNewCO ) ) {

            urlParameters.put( KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT );
            urlParameters.put( "courseOfferingInfo.id", loadNewCO );
            urlParameters.put( KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName() );
            urlParameters.put( "returnLocation", urlToRedirectTo );

            urlToRedirectTo = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_EDIT_MAINTENANCE;
        }

        return performRedirect(form, urlToRedirectTo, urlParameters);
    }

}
