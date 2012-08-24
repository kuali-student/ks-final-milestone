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
package org.kuali.student.enrollment.class1.process.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.process.form.ProcessInfoForm;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.kuali.rice.core.api.criteria.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

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
    private OrganizationService organizationService;
    private OrgInfo orgInfo;

    private boolean isEdit;

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

        if(isEdit) {
            processInfo.setKey(form.getKey());
            processInfo.setName(form.getName());
            processInfo.setTypeKey(form.getTypeKey());
            processInfo.setOwnerOrgId(form.getOwnerOrgId());
            RichTextInfo richTextInfo = new RichTextInfo();
            richTextInfo.setPlain(form.getDescr());
            processInfo.setDescr(richTextInfo);
            processInfo.setStateKey(form.getStateKey());

            try {
                processService = getProcessService();
                processService.updateProcess(processInfo.getKey(),processInfo, getContextInfo());
                isEdit = false;
            } catch (Exception e) {
                return getUIFModelAndView(form);
            }
        } else {
            processInfo.setKey("kuali.process."+ form.getTypeKey() + "."+form.getName() );
            String key =  processInfo.getKey().replaceAll(" ", ".");
            processInfo.setKey(key);
            processInfo.setName(form.getName());
            processInfo.setTypeKey(form.getTypeKey());
            processInfo.setStateKey("kuali.process.process.state.active");
            processInfo.setOwnerOrgId(form.getOwnerOrgId());
            RichTextInfo richTextInfo = new RichTextInfo();
            richTextInfo.setPlain(form.getDescr());
            processInfo.setDescr(richTextInfo);

            try {
                processService = getProcessService();
                ProcessInfo createProcessInfo = processService.createProcess(processInfo.getKey(), processInfo.getTypeKey(), processInfo, getContextInfo());
            } catch (Exception e) {
                return getUIFModelAndView(form);
            }
        }
        form.setValidateDirty(false);
        GlobalVariables.getMessageMap().putInfo("Process", "info.enroll.save.success");

        return refresh(form, result, request, response);
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
    public ModelAndView search(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProcessInfo> results = new ArrayList<ProcessInfo>();
        String name = form.getName();
        String type = form.getTypeKey();
        String state = form.getStateKey();
        String orgId = form.getOwnerOrgId();
        String descr = form.getDescr();

        try {
            QueryByCriteria.Builder query = buildQueryByCriteria(name,type,state,orgId,descr);

            processService = getProcessService();


            List<ProcessInfo> processInfos = processService.searchForProcess(query.build(), getContextInfo());
            if (!processInfos.isEmpty()){
                results.addAll(processInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Search",e); //To change body of catch statement use File | Settings | File Templates.
        }

        resetForm(form);

        form.setProcessInfos(results);

        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=clear")
    public ModelAndView clear(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        clearValues(form);
        return getUIFModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=back")
    public ModelAndView back(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        clearValues(form);
        return getUIFModelAndView(form, "processInfoSearch-SearchPage");
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") ProcessInfoForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProcessInfo processInfo = getSelectedProcessInfo(form, "edit");
        isEdit = true;

        organizationService = getOrganizationService();
        try{
            orgInfo = organizationService.getOrg(processInfo.getOwnerOrgId(),getContextInfo());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("organization not found. ", e);
        }

        if ((processInfo.getKey() != null) && !processInfo.getKey().trim().isEmpty()) {
            try {
                ProcessInfo process = getProcessService().getProcess(processInfo.getKey(), getContextInfo());
                form.setName(process.getName());
                form.setTypeKey(process.getTypeKey());
                form.setDescr(process.getDescr().getPlain());
                form.setOwnerOrgId(process.getOwnerOrgId());
                form.setStateKey(process.getStateKey());
                form.setOrgName(orgInfo.getShortName());
            } catch (Exception ex) {
                throw new RuntimeException("unable to get hold issue");
            }
        }

        return getUIFModelAndView(form, "processInfoSearch-EditPage");
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") ProcessInfoForm form) throws Exception {
        List<ProcessInfo> processInfos = form.getProcessInfos();
        List<InstructionInfo> instructionInfos = new ArrayList<InstructionInfo>();
        List<InstructionInfo> activeInstructions = new ArrayList<InstructionInfo>();
        ProcessInfo processInfo = getSelectedProcessInfo(form, "delete");
        boolean isInstructionActive = false;

        try{
            instructionInfos = getProcessService().getInstructionsByProcess(processInfo.getKey(), getContextInfo());
            for(InstructionInfo instruction : instructionInfos){
                if(instruction.getStateKey().equals("kuali.process.instruction.state.active")){
                    isInstructionActive = true;
                    activeInstructions.add(instruction);
                }
            }

            if(isInstructionActive != true) {
                if(!processInfo.getStateKey().equals("inactive") || !processInfo.getStateKey().equals("disabled")) {
                    processInfo.setStateKey(form.getStateKey());
                    getProcessService().updateProcess(processInfo.getKey(), processInfo, getContextInfo());
                    GlobalVariables.getMessageMap().addGrowlMessage("Saved!", "Save Successful");
                }
            } else if(isInstructionActive == true && form.getStateKey().equals("disabled")){
                processInfo.setStateKey(form.getStateKey());
                getProcessService().updateProcess(processInfo.getKey(), processInfo, getContextInfo());
                GlobalVariables.getMessageMap().addGrowlMessage("Saved!", "Process Disabled but there exists active instructions");
            } else {
                GlobalVariables.getMessageMap().addGrowlMessage("Saved not Possible", "There exists active instructions");
            }
        } catch (Exception ex) {
        throw new RuntimeException("Unable to get process");
    }

        form.setProcessInfos(processInfos);
        return getUIFModelAndView(form);
    }

    private ProcessInfo getSelectedProcessInfo(ProcessInfoForm form, String actionLink){
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

        Collection<ProcessInfo> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        ProcessInfo processInfo = ((List<ProcessInfo>) collection).get(selectedLineIndex);

        return processInfo;
    }

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

    protected OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

    private void resetForm(ProcessInfoForm form) {
        form.setProcessInfos(new ArrayList<ProcessInfo>());
    }

    private void clearValues(ProcessInfoForm form) {
        form.setName("");
        form.setOwnerOrgId("");
        form.setStateKey("");
        form.setTypeKey("");
        form.setDescr("");
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
            p = like("processType", "%" + type + "%");
            pList.add(p);
        }

        if (StringUtils.isNotBlank(state)){
            p = equal("processState", state);
            pList.add(p);
        }

        if (StringUtils.isNotBlank(orgId)){
            p = like("ownerOrgID", orgId);
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
