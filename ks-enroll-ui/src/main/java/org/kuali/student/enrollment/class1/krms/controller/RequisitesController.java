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
 * Created by Paul on 2012/11/22
 */
package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.krms.form.RequisitesForm;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/requisitesController")
public class RequisitesController extends UifControllerBase {

    private ContextInfo contextInfo;

    private CluService cluService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new RequisitesForm();
    }

    @Override
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        StringBuilder courseNameBuilder = new StringBuilder();
        String cluId = request.getParameter("cluId");
        CluInfo cluInfo = null;
        try {
            cluInfo = getCluService().getClu(cluId, getContextInfo());
        } catch (Exception e) {
            //do something
        }
        CluIdentifierInfo cluIdentInfo = cluInfo.getOfficialIdentifier();
        courseNameBuilder.append(cluIdentInfo.getDivision());
        courseNameBuilder.append(" ");
        courseNameBuilder.append(cluIdentInfo.getSuffixCode());
        courseNameBuilder.append(" - ");
        courseNameBuilder.append(cluIdentInfo.getLongName());
        // TODO: add course credits
        RequisitesForm requisitesForm = (RequisitesForm)form;
        requisitesForm.setCourseName(courseNameBuilder.toString());
        return super.start(requisitesForm, result, request, response);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected CluService getCluService(){
        if(cluService == null) {
            cluService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }


}
