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
 */
package org.kuali.student.enrollment.class1.hold.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueInfoForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
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
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holdIssueInfoController")
public class HoldIssueInfoController extends UifControllerBase {

    private transient HoldService holdService;
    private ContextInfo contextInfo;
    private transient OrganizationService organizationService;

    private Map<String, String> actionParameters;

    //Constants
    private static final String HOLD_STATE_ACTIVE = "kuali.hold.issue.state.active";
    private static final String HOLD_STATE_INACTIVE = "kuali.hold.issue.state.inactive";


    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new HoldIssueInfoForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        HoldIssueInfoForm holdIssueInfoForm = (HoldIssueInfoForm)form;
        holdIssueInfoForm.setIsSaveSuccess(false);

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>();
        String name = form.getName();
        String type = form.getTypeKey();
        String state = form.getStateKey();
        String orgId = form.getOrganizationId();
        String descr = form.getDescr();

        try {
            QueryByCriteria.Builder query = buildQueryByCriteria(name,type,state,orgId,descr);
            results = getHoldService().searchForHoldIssues(query.build(), getContextInfo());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Search",e); //To change body of catch statement use File | Settings | File Templates.
        }

        resetForm(form);

        form.setHoldIssueInfoList(results);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=create")
    public ModelAndView create(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssueInfo = new HoldIssueInfo();

        holdIssueInfo.setName(form.getName());
        holdIssueInfo.setTypeKey(form.getTypeKey());
        holdIssueInfo.setStateKey("kuali.hold.issue.state.active");
        holdIssueInfo.setOrganizationId(form.getOrganizationId());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain(form.getDescr());
        holdIssueInfo.setDescr(richTextInfo);

        HoldIssueInfo createHoldIssueInfo;
        try {
            holdService = getHoldService();
            createHoldIssueInfo = holdService.createHoldIssue(holdIssueInfo.getTypeKey(), holdIssueInfo, getContextInfo() );
        } catch (Exception e) {

            return getUIFModelAndView(form);
        }

        if (form.getView() != null){
            form.getView().setApplyDirtyCheck(false);
        } else if (form.getPostedView() != null){
            form.getView().setApplyDirtyCheck(false);
        }

        form.setId(createHoldIssueInfo.getId());
        form.setStateKey(createHoldIssueInfo.getStateKey());
        form.setIsSaveSuccess(true);
        GlobalVariables.getMessageMap().putInfo("Process", "info.enroll.save.success");
        form.setIsSaveSuccess(true);
        return refresh(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=clear")
    public ModelAndView clear(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        clearValues(form);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssue = getSelectedHoldIssue(form, "view");

        try{
            if ((holdIssue.getId() != null) && !holdIssue.getId().trim().isEmpty()) {
                try {
                    OrgInfo orgInfo = getOrganizationService().getOrg(holdIssue.getOrganizationId(),getContextInfo());
                    HoldIssueInfo holdIssueInfo = getHoldService().getHoldIssue(holdIssue.getId(), getContextInfo());
                    form.setName(holdIssueInfo.getName());
                    form.setTypeKey(holdIssueInfo.getTypeKey());
                    form.setDescr(holdIssueInfo.getDescr().getPlain());
                    form.setOrganizationId(holdIssueInfo.getOrganizationId());
                    form.setStateKey(holdIssueInfo.getStateKey());
                    form.setOrgName(orgInfo.getShortName());
                } catch (Exception ex) {
                    throw new RuntimeException("unable to get hold issue");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("organization not found. ", e);
        }

        return getUIFModelAndView(form, "holdView-ViewPage");

    }

    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssue = getSelectedHoldIssue(form, "edit");
        form.setIsSaveSuccess(false);

        if ((holdIssue.getId() != null) && !holdIssue.getId().trim().isEmpty()) {
            try {
                OrgInfo orgInfo = getOrganizationService().getOrg(holdIssue.getOrganizationId(),getContextInfo());
                HoldIssueInfo holdInfo = getHoldService().getHoldIssue(holdIssue.getId(), getContextInfo());
                form.setId(holdInfo.getId());
                form.setName(holdInfo.getName());
                form.setTypeKey(holdInfo.getTypeKey());
                form.setDescr(holdInfo.getDescr().getPlain());
                form.setOrganizationId(holdInfo.getOrganizationId());
                form.setStateKey(holdInfo.getStateKey());
                form.setOrgName(orgInfo.getShortName());
            } catch (Exception ex) {
                throw new RuntimeException("unable to get hold issue");
            }
        }

        return getUIFModelAndView(form, "holdView-EditPage");
    }

    @RequestMapping(params = "methodToCall=modify")
    public ModelAndView modity(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssueInfo = new HoldIssueInfo();

        holdIssueInfo.setId(form.getId());
        holdIssueInfo.setName(form.getName());
        holdIssueInfo.setTypeKey(form.getTypeKey());
        holdIssueInfo.setStateKey(form.getStateKey());
        holdIssueInfo.setOrganizationId(form.getOrganizationId());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain(form.getDescr());
        holdIssueInfo.setDescr(richTextInfo);

        try {
            getHoldService().updateHoldIssue(holdIssueInfo.getId(), holdIssueInfo, getContextInfo());
        } catch (Exception e) {
            return getUIFModelAndView(form);
            /*e.printStackTrace();
            throw new RuntimeException("Modify Hold failed. ", e);*/
        }
        if (form.getView() != null){
            form.getView().setApplyDirtyCheck(false);
        } else if (form.getPostedView() != null){
            form.getView().setApplyDirtyCheck(false);
        }
        GlobalVariables.getMessageMap().putInfo("Hold Issue Info", "info.enroll.save.success");
        form.setIsSaveSuccess(true);
        return refresh(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dialogId = "deleteConfirmationDialog";

        if(!hasDialogBeenDisplayed(dialogId, form)) {
            actionParameters = form.getActionParameters();
            return showDialog(dialogId, form, request, response);
        } else if (form.getActionParamaterValue("resetDialog").equals("true")){
            form.getDialogManager().removeAllDialogs();
            form.setLightboxScript("closeLightbox('" + dialogId + "');");
            return getUIFModelAndView(form);
        }

        form.setActionParameters(actionParameters);
        HoldIssueInfo holdIssue = getSelectedHoldIssue(form, "delete");

        try {
            if(holdIssue.getStateKey().equals(HOLD_STATE_ACTIVE)) {
                holdIssue.setStateKey(HOLD_STATE_INACTIVE);
                getHoldService().updateHoldIssue(holdIssue.getId(), holdIssue, getContextInfo());
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Delete",e);
        }

        form.setLightboxScript("closeLightbox('" + dialogId + "');");
        form.getDialogManager().removeAllDialogs();
        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=back")
    public ModelAndView back(@ModelAttribute("KualiForm") HoldIssueInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        clearValues(form);
        resetForm(form);
        return getUIFModelAndView(form, "holdView-SearchPage");
    }

    private void clearValues(HoldIssueInfoForm form) {
        form.setName("");
        form.setOrganizationId("");
        form.setOrgName("");
        form.setStateKey("");
        form.setTypeKey("");
        form.setDescr("");
        form.setOrgName("");
    }

    private void resetForm(HoldIssueInfoForm form) {
        form.setHoldIssueInfoList(new ArrayList<HoldIssueInfo>());
    }

    private HoldIssueInfo getSelectedHoldIssue(HoldIssueInfoForm form, String actionLink){
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<HoldIssueInfo> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        HoldIssueInfo holdIssue = ((List<HoldIssueInfo>) collection).get(selectedLineIndex);

        return holdIssue;
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
    private static QueryByCriteria.Builder buildQueryByCriteria(String name, String type,String state, String orgId, String descr){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(name)){
            p = like("name", "%" + name + "%");
            pList.add(p);
        }

        if (StringUtils.isNotBlank(type)){
            p = like("holdIssueType", "%" + type + "%");
            pList.add(p);
        }

        if (StringUtils.isNotBlank(state)){
            p = equal("holdIssueState", state);
            pList.add(p);
        }

        if (StringUtils.isNotBlank(orgId)){
            p = equal("organizationId", orgId);
            pList.add(p);
        }

        if (StringUtils.isNotBlank(descr)){
            p = like("descrPlain", "%" + descr + "%");
            pList.add(p);
        }

        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }
        return qBuilder;
    }
}
