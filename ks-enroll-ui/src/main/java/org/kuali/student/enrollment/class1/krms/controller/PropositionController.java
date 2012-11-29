/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.student.enrollment.class1.krms.service.PropositionViewHelperService;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
import org.kuali.student.krms.KRMSConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for the Test UI Page
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Controller
@RequestMapping(value = KRMSConstants.WebPaths.PROPOSITION_PATH)
public class PropositionController extends MaintenanceDocumentController {

    @RequestMapping(params = "methodToCall=" + "updateDescription")
    public ModelAndView updateDescription(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PropositionBo proposition = this.getProposition(form);
        if (proposition != null){
            proposition.setDescription("updated");
        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "updateProposition")
    public ModelAndView updateProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PropositionBo proposition = this.getProposition(form);
        if (proposition != null){
            KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeById(proposition.getTypeId());
            proposition.setDescription(type.getName());

            setValueForProposition(this.getProposition(form), "");
        }

        configureProposition(form);

        return getUIFModelAndView(form);
    }

    /**
     * Setup a new <code>MaintenanceView</code> with the edit maintenance
     * action
     */
    @Override
    @RequestMapping(params = "methodToCall=" + KRADConstants.Maintenance.METHOD_TO_CALL_EDIT)
    public ModelAndView maintenanceEdit(@ModelAttribute("KualiForm") MaintenanceForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);

        configureProposition(form);

        return getUIFModelAndView(form);
    }

    private void configureProposition(UifFormBase form) {

        PropositionViewHelperService viewHelper = (PropositionViewHelperService) KSControllerHelper.getViewHelperService(form);

        Component customGroup = ComponentUtils.findNestedComponentById(form.getView(), "Default-Term-parameters");
        FieldGroup fieldGroup = (FieldGroup) customGroup;
        for (Component component : fieldGroup.getItems()){
            component.setRender(false);
        }

        PropositionBo proposition = this.getProposition(form);
        if (proposition != null){

            //Set the term spec
            String termSpecId = viewHelper.getTermSpecificationForType(proposition.getTypeId());
            TermSpecificationDefinition termSpecification = getTermBoService().getTermSpecificationById(termSpecId);
            setTermForProposition(proposition, termSpecification.getDescription());

            for (Component component : fieldGroup.getItems()){
                if (viewHelper.getComponentForTermSpec(termSpecId).equals(component.getId())){
                    component.setRender(true);
                }
            }

            //Set the operation
            setOperationForProposition(proposition, viewHelper.getOperationForType(proposition.getTypeId()));

            //Set the value
            String defaultValue = viewHelper.getValueForType(proposition.getTypeId());
            if ("?".equals(defaultValue)){
                //proposition.setShowCustomValue(true);
            } else {
                //proposition.setShowCustomValue(false);
                setValueForProposition(proposition, defaultValue);
            }


        }
    }

    private void setTermForProposition(PropositionBo proposition, String term){
        proposition.getParameters().get(0).setValue(term);
    }

    private void setOperationForProposition(PropositionBo proposition, String operation){
        proposition.getParameters().get(2).setValue(operation);
    }

    private void setValueForProposition(PropositionBo proposition, String value){
        proposition.getParameters().get(1).setValue(value);
    }

    /**
     * @param form
     * @return the {@link org.kuali.rice.krms.impl.repository.PropositionBo} from the form
     */
    private PropositionBo getProposition(UifFormBase form) {
        MaintenanceForm maintenanceForm = (MaintenanceForm) form;
        return ((PropositionBo)maintenanceForm.getDocument().getDocumentDataObject());
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    public TermBoService getTermBoService() {
        return KrmsRepositoryServiceLocator.getTermBoService();
    }

}
