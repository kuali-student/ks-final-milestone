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
package org.kuali.student.enrollment.class1.hold.service.controller;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.hold.service.form.ProcessInfoForm;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
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
import java.util.List;
import java.util.Properties;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/processInfoController")
public class ProcessInfoController extends UifControllerBase {

    private transient ProcessService processService;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ProcessInfoForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        ProcessInfoForm processInfoForm = (ProcessInfoForm)form;

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=save")
    public ModelAndView save(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setKey("kuali.process."+ form.getTypeKey() + "."+form.getName() );
        String key =  processInfo.getKey().replaceAll(" ", ".");
        processInfo.setKey(key);
        processInfo.setName(form.getName());
        processInfo.setTypeKey(form.getTypeKey());
        processInfo.setStateKey(form.getStateKey());
        processInfo.setOwnerOrgId(form.getOwnerOrgId());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain(form.getDescr());
        processInfo.setDescr(richTextInfo);

        try {
            processService = getProcessService();
            // ProcessInfo createProcessInfo = processService.createProcess(processInfo.getKey(), processInfo.getTypeKey(), processInfo, getContextInfo());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Create new failed. ", e);
        }
        form.setProcessInfo(processInfo);
        form.setKey(processInfo.getKey());
        //GlobalVariables.getMessageMap().addGrowlMessage("Saved!", "Save Successful");
        return getUIFModelAndView(form, null);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=create")
    public ModelAndView create(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Properties urlParameters = new Properties();

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
        urlParameters.put(UifParameters.VIEW_ID, "processCreateView");

        return super.performRedirect(form, "processInfoController", urlParameters);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=search")
    public ModelAndView view(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProcessInfo> results = new ArrayList<ProcessInfo>();

        /*List<String> type = getProcessService().getProcessCategoryIdsByType(form.getTypeKey(), getContextInfo());

        try {
            processService = getProcessService();


            List<ProcessInfo> processInfos = processService.getProcessesForProcessCategory(type.get(type.size()-1), getContextInfo());
            if (!processInfos.isEmpty()){
                results.addAll(processInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Search",e); //To change body of catch statement use File | Settings | File Templates.
        }*/

        resetForm(form);

        form.setProcessInfos(results);

        Properties urlParameters = new Properties();

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
        urlParameters.put(UifParameters.VIEW_ID, "processInfoResultView");

        return super.performRedirect(form, "processInfoController", urlParameters);
    }

    /*@RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") HoldIssueInfoSearchForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssue = getSelectedHoldIssue(form, "edit");

        String controllerPath;
        Properties urlParameters = new Properties();

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "view");
        urlParameters.put("id", holdIssue.getId());
        urlParameters.put(UifParameters.VIEW_ID, "holdModifyView");

        controllerPath = "createHold";

        return performRedirect(form, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") HoldIssueInfoSearchForm form, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<HoldIssueInfo> holdIssueInfos = form.getHoldIssueInfo();
        HoldIssueInfo holdIssue = getSelectedHoldIssue(form, "delete");

        try {
            if(holdIssue.getStateKey().equals("active")) {
                holdIssue.setStateKey("inactive");
                getHoldService().updateHoldIssue(holdIssue.getId(), holdIssue, getContextInfo());
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Delete",e);
        }

        form.setHoldIssueInfo(holdIssueInfos);
        return getUIFModelAndView(form);
    }

    private void resetForm(HoldIssueInfoSearchForm form) {
        form.setHoldIssueInfo(new ArrayList<HoldIssueInfo>());
    } */

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

    protected ProcessService getProcessService(){
        if(processService == null) {
            processService = (ProcessService) GlobalResourceLoader.getService(new QName(ProcessServiceConstants.NAMESPACE, ProcessServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return processService;
    }

    private void resetForm(ProcessInfoForm form) {
        form.setProcessInfos(new ArrayList<ProcessInfo>());
    }

    /*private static QueryByCriteria.Builder buildQueryByCriteria(String name, String type,String state, String orgId, String descr){

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
            p = like("organizationId", orgId);
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
    }*/
}
