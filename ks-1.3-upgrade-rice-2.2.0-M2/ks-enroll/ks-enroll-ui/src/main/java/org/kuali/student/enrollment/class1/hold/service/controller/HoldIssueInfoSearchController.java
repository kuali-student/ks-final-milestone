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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.widget.LightBox;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class1.hold.service.form.HoldIssueInfoCreateForm;
import org.kuali.student.enrollment.class1.hold.service.form.HoldIssueInfoSearchForm;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;
import static org.kuali.rice.core.api.criteria.PredicateFactory.notIn;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/holdIssueInfoSearch")
public class HoldIssueInfoSearchController extends UifControllerBase {

    private transient HoldService holdService;
    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new HoldIssueInfoSearchForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        HoldIssueInfoSearchForm holdIssueInfoSearchForm = (HoldIssueInfoSearchForm)form;

        return super.start(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=search")
    public ModelAndView search(@ModelAttribute("KualiForm") HoldIssueInfoSearchForm searchForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>();

        String name = searchForm.getName();
        String type = searchForm.getTypeKey();
        String state = searchForm.getStateKey();
        String orgId = searchForm.getOrganizationId();
        String descr = searchForm.getDescr();

        try {
            QueryByCriteria.Builder query = buildQueryByCriteria(name,type,state,orgId,descr);

            holdService = getHoldService();


            List<HoldIssueInfo> holdIssueInfos = holdService.searchForHoldIssues(query.build(), getContextInfo());
            if (!holdIssueInfos.isEmpty()){
                results.addAll(holdIssueInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Search",e); //To change body of catch statement use File | Settings | File Templates.
        }

        resetForm(searchForm);

        searchForm.setHoldIssueInfo(results);

        return getUIFModelAndView(searchForm, null);
    }

    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") HoldIssueInfoSearchForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssue = getSelectedHoldIssue(searchForm, "view");

        String controllerPath;
        Properties urlParameters = new Properties();

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "view");
        urlParameters.put("id", holdIssue.getId());
        urlParameters.put(UifParameters.VIEW_ID, "holdView");

        controllerPath = "createHold";

        return performRedirect(searchForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") HoldIssueInfoSearchForm searchForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoldIssueInfo holdIssue = getSelectedHoldIssue(searchForm, "edit");

        String controllerPath;
        Properties urlParameters = new Properties();

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "view");
        urlParameters.put("id", holdIssue.getId());
        urlParameters.put(UifParameters.VIEW_ID, "holdModifyView");

        controllerPath = "createHold";

        return performRedirect(searchForm, controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=delete")
    public ModelAndView delete(@ModelAttribute("KualiForm") HoldIssueInfoSearchForm searchForm, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<HoldIssueInfo> holdIssueInfos = searchForm.getHoldIssueInfo();
        HoldIssueInfo holdIssue = getSelectedHoldIssue(searchForm, "delete");

        try {
            if(holdIssue.getStateKey().equals("active")) {
                holdIssue.setStateKey("inactive");
                getHoldService().updateHoldIssue(holdIssue.getId(), holdIssue, getContextInfo());
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Delete",e);
        }

        searchForm.setHoldIssueInfo(holdIssueInfos);
        return getUIFModelAndView(searchForm);
    }

    private void resetForm(HoldIssueInfoSearchForm searchForm) {
        searchForm.setHoldIssueInfo(new ArrayList<HoldIssueInfo>());
    }

    private HoldIssueInfo getSelectedHoldIssue(HoldIssueInfoSearchForm searchForm, String actionLink){
        String selectedCollectionPath = searchForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = searchForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<HoldIssueInfo> collection = ObjectPropertyUtils.getPropertyValue(searchForm, selectedCollectionPath);
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
    }
}
