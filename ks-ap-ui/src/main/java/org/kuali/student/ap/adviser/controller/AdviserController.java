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
package org.kuali.student.ap.adviser.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/advise/**")
public class AdviserController extends UifControllerBase {

    private static final Logger logger = LoggerFactory.getLogger(AdviserController.class);

    private transient PersonService personService;

    private transient PermissionService permissionService;

    private transient String ADVISE_NM_CODE;

    private transient List<String> advisePermNames;

    private transient AcademicPlanService academicPlanService;

    public synchronized PersonService getPersonService() {

        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;

    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public synchronized PermissionService getPermissionService() {
        if (permissionService == null) {

            ADVISE_NM_CODE = ConfigContext.getCurrentContextConfig().getProperty("ksap.advise.namespacecode");
            advisePermNames =   Arrays.asList(ConfigContext.getCurrentContextConfig().getProperty("ksap.advise.permissionname").split(","));

            permissionService = KimApiServiceLocator.getPermissionService();
        }

        return this.permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public AcademicPlanService getAcademicPlanService() {
        if (academicPlanService == null) {
            academicPlanService = (AcademicPlanService)
                    GlobalResourceLoader.getService(new QName(PlanConstants.NAMESPACE, PlanConstants.SERVICE_NAME));
        }
        return academicPlanService;
    }

    public void setAcademicPlanService(AcademicPlanService academicPlanService) {
        this.academicPlanService = academicPlanService;
    }

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new UifFormBase();
    }

     @RequestMapping(value = "/advise", method = RequestMethod.GET)
    public String doGet(@ModelAttribute("KualiForm") UifFormBase form) {
        UserSession session = GlobalVariables.getUserSession();
        clearSession(session);
        form.setView(getViewService().getViewById("PlannedCourses-FormView"));
        form.setRequestRedirected(true);
        GlobalVariables.getMessageMap().putErrorForSectionId(PlanConstants.PLAN_PAGE_ID, PlanConstants.ERROR_KEY_NO_STUDENT_PROXY_ID);

        return "redirect:/kr-krad/unauthorized";
    }

    @RequestMapping(value = "/advise/", method = RequestMethod.GET)
    public String get(@ModelAttribute("KualiForm") UifFormBase form) {
        UserSession session = GlobalVariables.getUserSession();
        clearSession(session);
        form.setView(getViewService().getViewById("PlannedCourses-FormView"));
        form.setRequestRedirected(true);
        GlobalVariables.getMessageMap().putErrorForSectionId(PlanConstants.PLAN_PAGE_ID, PlanConstants.ERROR_KEY_NO_STUDENT_PROXY_ID);

        return "redirect:/kr-krad/unauthorized";
    }

    /**
     * This URL will be authenticated using a two-factor method (via an .htaccess file). This
     * method will then check for the existence of an "adviser" role and if the authenticated
     * user has an adviser role a flag will be set in the session which can be referenced from
     * other pages to indicate that adviser contextual behavior should be applied.
     *
     * @return A redirect to the start page.
     */
    @RequestMapping(value = "/advise/{studentId}", method = RequestMethod.GET)
    public String get(@PathVariable("studentId") String studentId, @ModelAttribute("KualiForm") UifFormBase form) {
        form.setView(getViewService().getViewById("PlannedCourses-FormView"));
        form.setRequestRedirected(true);
        LearningPlanInfo plan = null;
        try {
            //  Throws RuntimeException is there is a problem. Otherwise, returns a plan or null.
            plan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
        } catch (Exception e) {
            logger.error("Query for learning plan failed.", e);
        }
        if (plan != null) {
            if (plan.getShared().toString().equalsIgnoreCase(PlanConstants.LEARNING_PLAN_ITEM_SHARED_FALSE_KEY)) {
                return "redirect:/kr-krad/unauthorized";
            }
        }
        UserSession session = GlobalVariables.getUserSession();

        //Initialize the permission service and name space codes
        getPermissionService();
        boolean authorized = false;
        for(String adviseNm : advisePermNames) {
            if (getPermissionService().hasPermission(session.getPrincipalId(), ADVISE_NM_CODE, adviseNm.trim())) {
                authorized = true;
                break;
            }

            logger.info("Adviser authz failed for {} Data|{}|{}|{}", session.getPrincipalName(), session.getPrincipalId(), ADVISE_NM_CODE, adviseNm);
        }

        if (!authorized) {
            GlobalVariables.getMessageMap().putErrorForSectionId(PlanConstants.PLAN_PAGE_ID, PlanConstants.ERROR_KEY_ILLEGAL_ADVISER_ACCESS);
            return "redirect:/kr-krad/unauthorized";
        }


        //  Set the adviser session flag. (The value isn't important)
        session.addObject(PlanConstants.SESSION_KEY_IS_ADVISER, true);

        //   Validate the student id
        if (StringUtils.isEmpty(studentId)) {
            GlobalVariables.getMessageMap().putErrorForSectionId(PlanConstants.PLAN_PAGE_ID, PlanConstants.ERROR_KEY_NO_STUDENT_PROXY_ID);
            studentId = "unset";
        } else {
            //  Check the session flag.
            String oldId = (String) session.retrieveObject(PlanConstants.SESSION_KEY_STUDENT_ID);
            if (!StringUtils.isEmpty(oldId) && !studentId.equals(oldId)) {
                GlobalVariables.getMessageMap().putWarningForSectionId(PlanConstants.PLAN_PAGE_ID, PlanConstants.WARNING_STUDENT_CONTEXT_SWITCH, oldId, studentId);
            }
        }

        //   Put the student Id in the session.
        session.addObject(PlanConstants.SESSION_KEY_STUDENT_ID, studentId);

        Person person = getPersonService().getPerson(studentId);
        if (person != null) {
            session.addObject(PlanConstants.SESSION_KEY_STUDENT_NAME, person.getFirstName().substring(0, 1).toUpperCase() + person.getFirstName().substring(1, person.getFirstName().length()) + " " + person.getLastName().substring(0, 1).toUpperCase() + person.getLastName().substring(1, person.getLastName().length()));
            return "redirect:/kr-krad/plan?methodToCall=start&viewId=PlannedCourses-FormView";

        } else {
            clearSession(session);
            return "redirect:/kr-krad/unauthorized";

        }
    }

    private void clearSession(UserSession session) {
        session.removeObject(PlanConstants.SESSION_KEY_STUDENT_ID);
        session.addObject(PlanConstants.SESSION_KEY_STUDENT_ID, "");
        session.removeObject(PlanConstants.SESSION_KEY_STUDENT_NAME);
        session.addObject(PlanConstants.SESSION_KEY_STUDENT_NAME, "");
    }
}


