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
package org.kuali.student.enrollment.class1.hold.controller;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueInfoCreateForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Properties;

/**
 * This controller handles all the request from Academic calendar UI.
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/createHold")
public class HoldIssueInfoCreateController extends UifControllerBase {

    private transient HoldService holdService;
    private transient OrganizationService organizationService;
    private ContextInfo contextInfo;
    private HoldIssueInfo holdIssueInfo;
    private OrgInfo orgInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new HoldIssueInfoCreateForm();
    }

    /**
     * This GET method loads an academic calendar based on the parameters passed into the request.
     *
     * These are the supported request parameters
     * 1. id - Academic Calendar Id to load in to UI
     * 2. readOnlyView - If true, sets the view as read only
     * 3. selectTab - can be 'info' or 'term'
     *
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        HoldIssueInfoCreateForm holdForm = (HoldIssueInfoCreateForm) form;

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=create")
    public ModelAndView create(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfoCreateForm createForm = (HoldIssueInfoCreateForm) form;
        holdIssueInfo = new HoldIssueInfo();
        holdIssueInfo.setName(createForm.getName());
        holdIssueInfo.setTypeKey(createForm.getTypeKey());
        holdIssueInfo.setStateKey("kuali.hold.issue.state.active");
        holdIssueInfo.setOrganizationId(createForm.getOrganizationId());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain(createForm.getDescr());
        holdIssueInfo.setDescr(richTextInfo);

        HoldIssueInfo createHoldIssueInfo;
        try {
            holdService = getHoldService();

                createHoldIssueInfo = holdService.createHoldIssue(holdIssueInfo.getTypeKey(), holdIssueInfo, getContextInfo() );
        } catch (Exception e) {

           return getUIFModelAndView(createForm);
            //throw new RuntimeException("Create new failed. ", e);
        }
        createForm.setValidateDirty(false);
        createForm.setId(createHoldIssueInfo.getId());
        createForm.setStateKey(createHoldIssueInfo.getStateKey());
        return close(createForm, result, request, response);
    }

  @RequestMapping(params = "methodToCall=modify")
 public ModelAndView modity(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
      HoldIssueInfoCreateForm modifyForm = (HoldIssueInfoCreateForm) form;

      holdIssueInfo = new HoldIssueInfo();
      holdIssueInfo.setId(modifyForm.getId());
      holdIssueInfo.setName(modifyForm.getName());
      holdIssueInfo.setTypeKey(modifyForm.getTypeKey());
      holdIssueInfo.setStateKey(modifyForm.getStateKey());
      holdIssueInfo.setOrganizationId(modifyForm.getOrganizationId());
      RichTextInfo richTextInfo = new RichTextInfo();
      richTextInfo.setPlain(modifyForm.getDescr());
      holdIssueInfo.setDescr(richTextInfo);

      try {
          holdService = getHoldService();
          holdService.updateHoldIssue(holdIssueInfo.getId(), holdIssueInfo, getContextInfo() );
      } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException("Modify Hold failed. ", e);
      }
      form.setValidateDirty(false);
      GlobalVariables.getMessageMap().putInfo("Hold Issue Info", "info.enroll.save.success");
      return getUIFModelAndView(form);
 }

    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        HoldIssueInfoCreateForm holdIssueForm = (HoldIssueInfoCreateForm) form;
        String holdIssueId = request.getParameter("id");
        String viewType = request.getParameter(UifParameters.VIEW_ID);
        String orgName = request.getParameter("orgName");
        if ((holdIssueId != null) && !holdIssueId.trim().isEmpty()) {
            try {
                HoldIssueInfo holdIssueInfo = getHoldService().getHoldIssue(holdIssueId, getContextInfo());
                holdIssueForm.setName(holdIssueInfo.getName());
                holdIssueForm.setTypeKey(holdIssueInfo.getTypeKey());
                holdIssueForm.setDescr(holdIssueInfo.getDescr().getPlain());
                holdIssueForm.setOrganizationId(holdIssueInfo.getOrganizationId());
                holdIssueForm.setStateKey(holdIssueInfo.getStateKey());
                holdIssueForm.setOrgName(orgName);
            } catch (Exception ex) {
                throw new RuntimeException("unable to get hold issue");
            }
        }

/*        if(viewType.equals("holdView")) {
            holdIssueForm.getView().setReadOnly(true);
        }*/

        return super.start(holdIssueForm, result, request, response);
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

    protected HoldService getHoldService(){
        if(holdService == null) {
            holdService = (HoldService) GlobalResourceLoader.getService(new QName(HoldServiceConstants.NAMESPACE, HoldServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return holdService;
    }
    protected OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }
}
