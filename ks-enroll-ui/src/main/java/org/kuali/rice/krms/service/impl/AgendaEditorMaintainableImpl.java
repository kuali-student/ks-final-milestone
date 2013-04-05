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
package org.kuali.rice.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinitionContract;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.AgendaEditorMaintainable;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.student.enrollment.class1.krms.dto.EnrolAgendaEditor;
import org.kuali.student.enrollment.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link org.kuali.rice.krad.maintenance.Maintainable} for the {@link org.kuali.rice.krms.impl.ui.AgendaEditor}
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class AgendaEditorMaintainableImpl extends KSMaintainableImpl implements AgendaEditorMaintainable {

    private static final long serialVersionUID = 1L;

    private transient RuleManagementService ruleManagementService;
    private transient KrmsTypeRepositoryService krmsTypeRepositoryService;

    private transient ContextInfo contextInfo;
    private transient TemplateRegistry templateRegistry;

    public static final String NEW_AGENDA_EDITOR_DOCUMENT_TEXT = "New Agenda Editor Document";

    protected ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }

    /**
     * Get the AgendaEditor out of the MaintenanceDocumentForm's newMaintainableObject
     *
     * @param model the MaintenanceDocumentForm
     * @return the AgendaEditor
     */
    private AgendaEditor getAgendaEditor(Object model) {
        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        return (AgendaEditor) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        Object dataObject = null;

        String agendaId = dataObjectKeys.get("id");
        EnrolAgendaEditor form = new EnrolAgendaEditor();
        form.setId(agendaId);
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();

        AgendaManagementViewHelperServiceImpl viewHelperService = new AgendaManagementViewHelperServiceImpl();

        //TODO: get all agendas linked to a course offering
        agendas.addAll(viewHelperService.getAgendaEditors());

        form.setAgendas(agendas);

        dataObject = form;

        return dataObject;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterNew(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(NEW_AGENDA_EDITOR_DOCUMENT_TEXT);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterEdit(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription("Modify Agenda Editor Document");
    }

    @Override
    public void prepareForSave() {
        // set agenda attributes
        //EnrolRuleEditor ruleEditor = (EnrolRuleEditor) getDataObject();
        //ruleEditor.initPreviewTree();
    }

    @Override
    public void saveDataObject() {
        EnrolAgendaEditor enrolAgendaEditor = (EnrolAgendaEditor) getDataObject();

        if (!enrolAgendaEditor.getDeletedRuleIds().isEmpty()) {
            for(String ruleId : enrolAgendaEditor.getDeletedRuleIds()) {
                AgendaDefinition agendaDefinition = getAgenda(enrolAgendaEditor, ruleId);
                this.getRuleManagementService().deleteAgendaItem(ruleId);
                this.getRuleManagementService().deleteRule(ruleId);
                this.getRuleManagementService().updateAgenda(agendaDefinition);
            }
        }
    }

    private AgendaDefinition getAgenda(EnrolAgendaEditor enrolAgendaEditor, String ruleId) {
        AgendaDefinition.Builder agendaBuilder = null;
        AgendaDefinition agendaDefinition = null;
        List<AgendaEditor> agendas = enrolAgendaEditor.getAgendas();
        for(AgendaEditor agenda : agendas) {
            if(agenda.getFirstItemId().equals(ruleId)) {
                List<RuleEditor> rules = agenda.getRuleEditors();
                for(RuleEditor rule : rules) {
                    if(rule.getId() != null) {
                        agenda.setFirstItemId(rule.getId());
                        agendaBuilder = AgendaDefinition.Builder.create((AgendaDefinitionContract) agenda);
                    } else {
                        agenda.setFirstItemId(null);
                        agendaBuilder = AgendaDefinition.Builder.create((AgendaDefinitionContract) agenda);
                    }
                }
            }
        }
        agendaDefinition = agendaBuilder.build();
        return agendaDefinition;
    }

    /**
     * In the case of edit maintenance adds a new blank line to the old side
     * <p/>
     * TODO: should this write some sort of missing message on the old side
     * instead?
     *
     * @see org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl#processAfterAddLine(org.kuali.rice.krad.uif.view.View,
     *      org.kuali.rice.krad.uif.container.CollectionGroup, Object,
     *      Object)
     */
    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {

    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = (KrmsTypeRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }

    private TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }
}
