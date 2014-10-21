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
 * Created by David Yin on 12/4/12
 */
package org.kuali.student.common.uif.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.UifControllerHelper;
import org.kuali.rice.krad.web.controller.UifHandlerExceptionResolver;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.IncidentReportForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.krms.exceptions.KRMSOptimisticLockingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class extends the UifHandlerExceptionResolver with KS specific handling
 *
 * @author Kuali Student Team
 */
public class KsHandlerExceptionResolver extends UifHandlerExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(KsHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ex instanceof AuthorizationException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("/ks-common/WEB-INF/ftl/permissiondenied");
        }

        if(ex instanceof RiceRuntimeException) {
            if (isCausedByOptimisticLockingError(5,ex)){
                return this.resolveOptimisticLockException(request, response, handler, ex);
            }
        }

        return super.resolveException(request, response, handler, ex);
    }

    public ModelAndView resolveOptimisticLockException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        String incidentDocId = request.getParameter(KRADConstants.DOCUMENT_DOCUMENT_NUMBER);
        String incidentViewId = "";

        UifFormBase form = (UifFormBase)request.getAttribute(UifConstants.REQUEST_FORM);
        if (form instanceof DocumentFormBase) {
            if (((DocumentFormBase) form).getDocument() != null) {
                incidentDocId = ((DocumentFormBase) form).getDocument().getDocumentNumber();
            }
            incidentViewId = ((DocumentFormBase) form).getViewId();
        }

        GlobalVariables.getUifFormManager().removeSessionForm(form);

        UserSession userSession = (UserSession) request.getSession().getAttribute(KRADConstants.USER_SESSION_KEY);
        IncidentReportForm incidentReportForm = new IncidentReportForm();

        // Set the post url map to the incident report controller and not
        // the one the exception occurred on
        String postUrl = request.getRequestURL().toString();
        postUrl = postUrl.substring(0, postUrl.lastIndexOf("/")) + "/incidentReport";
        incidentReportForm.setFormPostUrl(postUrl);

        incidentReportForm.setException(ex);
        incidentReportForm.setIncidentDocId(incidentDocId);
        incidentReportForm.setIncidentViewId(incidentViewId);
        incidentReportForm.setController(handler.getClass().toString());

        incidentReportForm.setReturnLocation(form.getReturnLocation());

        if (userSession != null) {
            incidentReportForm.setUserId(userSession.getPrincipalId());
            incidentReportForm.setUserName(userSession.getPrincipalName());
            incidentReportForm.setUserEmail(userSession.getPerson().getEmailAddress());
        }

        incidentReportForm.setDevMode(!KRADUtils.isProductionEnvironment());
        incidentReportForm.setViewId("Uif-IncidentReportView");

        if (form != null) {
            incidentReportForm.setAjaxRequest(form.isAjaxRequest());
        } else {
            String ajaxRequestParm = request.getParameter(UifParameters.AJAX_REQUEST);
            if (StringUtils.isNotBlank(ajaxRequestParm)) {
                incidentReportForm.setAjaxRequest(Boolean.parseBoolean(ajaxRequestParm));
            }
        }

        // Set the view object
        incidentReportForm.setView(getViewService().getViewById("Uif-IncidentReportView"));

        // Set the ajax return type
        incidentReportForm.setAjaxReturnType(UifConstants.AjaxReturnTypes.UPDATEVIEW.getKey());

        ModelAndView modelAndView = UifControllerHelper.getUIFModelAndView(incidentReportForm, "page2");
        try {
            UifControllerHelper.postControllerHandle(request, response, handler, modelAndView);
        } catch (Exception e) {
            LOG.error("An error stopped the incident form from loading", e);
        }

        return modelAndView;
    }

    private boolean isCausedByOptimisticLockingError(int depth, Throwable t) {
        if ((depth > 0) && (t != null)) {
            if (t instanceof KRMSOptimisticLockingException) {
                return true;
            } else {
                Throwable cause = t.getCause();
                if (cause != t) {
                    return isCausedByOptimisticLockingError(--depth, cause);
                }
            }
        }
        return false;
    }
}
