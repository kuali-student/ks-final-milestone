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
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        super.route(form, result, request, response);

        if( GlobalVariables.getMessageMap().hasErrors() ) {
            return handleRouteForErrors( form );
        }

        return handleRouteForCoEdit(form);
    }

    /* Returns a ModelAndView for the route()-method to return a new view if we are editing a CO */
    private ModelAndView handleRouteForCoEdit( DocumentFormBase form ) {

        String urlToRedirectTo;
        Properties urlParameters = new Properties();

        GlobalVariables.getUifFormManager().removeSessionForm(form);    // clear current form from session

        // create a Growl-message
        CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();

        //display the correct growl message based on the availability of exam period and final exam type
        if (StringUtils.isEmpty(dataObject.getExamPeriodId())) {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_EDIT_SUCCESS_WITH_MISSING_EXAMPERIOD);
        } else if (!StringUtils.equals(dataObject.getCourseOfferingInfo().getFinalExamType(), CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_EDIT_SUCCESS);
        }
        else {
            urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_EDIT_SUCCESS_WITH_EXAMOFFERING_GENERATED);
        }

        urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS, dataObject.getCourseOfferingCode());
        //display the correct Warning  message based on the on the ExamOffering results.
        if (!dataObject.getExamOfferingResult().getMatrixMatchStatus().getIsSuccess()) {

            urlParameters.put(EnrollConstants.WARNING_MESSAGE_SECTION_ID, dataObject.getExamOfferingResult().getMatrixMatchStatus().getId());
            urlParameters.put(EnrollConstants.WARNING_MESSAGE, CourseOfferingConstants.COURSEOFFERING_MSG_WARNING_NO_MATCH_FOUND);

        }


        // determine which url to redirect to
        String returnLocationFromForm = form.getReturnLocation();
        if ( StringUtils.contains(returnLocationFromForm,"viewId=courseOfferingManagementView")
                || StringUtils.contains(returnLocationFromForm,"pageId=manageTheCourseOfferingPage")) {
            if (!returnLocationFromForm.contains("methodToCall=")) {
                // This happens when we display a list of COs and then user click on Manage action
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.TRUE.toString());
            }
            else {
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.FALSE.toString());
            }
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
