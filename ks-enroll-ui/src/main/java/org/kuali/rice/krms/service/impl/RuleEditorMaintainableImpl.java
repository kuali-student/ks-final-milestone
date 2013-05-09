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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.service.RuleEditorMaintainable;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleManagementWrapper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * {@link org.kuali.rice.krad.maintenance.Maintainable} for the {@link org.kuali.rice.krms.impl.ui.AgendaEditor}
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class RuleEditorMaintainableImpl extends KSMaintainableImpl implements RuleEditorMaintainable {

    private static final long serialVersionUID = 1L;

    private transient RuleManagementService ruleManagementService;
    private transient KrmsTypeRepositoryService krmsTypeRepositoryService;
    private transient TermRepositoryService termRepositoryService;

    private transient ContextInfo contextInfo;
    private transient TemplateRegistry templateRegistry;

    public static final String NEW_AGENDA_EDITOR_DOCUMENT_TEXT = "New Agenda Editor Document";

    /**
     * Get the AgendaEditor out of the MaintenanceDocumentForm's newMaintainableObject
     *
     * @param model the MaintenanceDocumentForm
     * @return the AgendaEditor
     */
    private RuleEditor getRuleEditor(Object model) {
        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        return (RuleEditor) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        EnrolRuleManagementWrapper dataObject = new EnrolRuleManagementWrapper();

        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
        dataObject.setAgendas(agendas);

        String coId = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(coId);

        dataObject.setCompareTree(RuleCompareTreeBuilder.initCompareTree());

        return dataObject;
    }

    protected AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        AgendaEditor agendaEditor = new AgendaEditor(agenda);

        AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(agendaId);
        agendaEditor.setRuleEditors(getRuleEditorsFromTree(agendaTree.getEntries()));

        return agendaEditor;
    }

    protected List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries) {

        RuleViewTreeBuilder viewTreeBuilder = new RuleViewTreeBuilder();
        viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());

                if (agendaItem.getRule() != null) {
                    RuleEditor ruleEditor = new RuleEditor(agendaItem.getRule());
                    ruleEditor.setAgendaItem(agendaItem);
                    this.initPropositionEditor((PropositionEditor) ruleEditor.getProposition());
                    ruleEditor.setViewTree(viewTreeBuilder.buildTree(ruleEditor));
                    rules.add(ruleEditor);
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    rules.addAll(getRuleEditorsFromTree(treeRuleEntry.getIfTrue().getEntries()));
                }
            }
        }
        return rules;
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
    public void saveDataObject() {
        RuleManagementWrapper ruleWrapper = (RuleManagementWrapper) getDataObject();

        for (AgendaEditor agenda : ruleWrapper.getAgendas()) {

            //Check if this agenda has anything to save
            if(agenda.isDummyAgenda()){
                continue;
            }

            //Set the agenda name.
            agenda.setName(ruleWrapper.getNamePrefix() + " " + agenda.getAgendaTypeInfo().getDescription());

            //Retrieve the context and set the id ong the agenda.
            if(agenda.getContextId()==null){
                ContextDefinition context = this.getRuleManagementService().getContextByNameAndNamespace("Course Requirements", ruleWrapper.getNamespace());
                agenda.setContextId(context.getId());
            }

            //Create or update the agenda.
            if (agenda.getId() == null) {
                AgendaDefinition.Builder agendaBldr = AgendaDefinition.Builder.create(agenda);
                AgendaDefinition agendaDfn = this.getRuleManagementService().createAgenda(agendaBldr.build());

                //Set the id and versionnumber for a possible update.
                agenda.setId(agendaDfn.getId());
                agenda.setVersionNumber(agendaDfn.getVersionNumber());

                //Create the reference object binding only on create agenda, no need to update.
                ReferenceObjectBinding.Builder refBuilder = ReferenceObjectBinding.Builder.create("Agenda",
                        agendaDfn.getId(), ruleWrapper.getNamespace(), ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                this.getRuleManagementService().createReferenceObjectBinding(refBuilder.build());
            }

            //Set the first agenda item id and save the agenda items
            String firstItemId = maintainAgendaItems(agenda, ruleWrapper.getNamePrefix(), ruleWrapper.getNamespace());
            if(firstItemId!=null){
                if((agenda.getFirstItemId()==null)||(!agenda.getFirstItemId().equals(firstItemId))){
                    AgendaDefinition.Builder agendaBldr = AgendaDefinition.Builder.create(agenda);
                    agendaBldr.setFirstItemId(firstItemId);
                    this.getRuleManagementService().updateAgenda(agendaBldr.build());
                }
            }

            //Delete rules
            for (RuleEditor deletedRule : agenda.getDeletedRules()) {
                if (deletedRule.getAgendaItem() != null) {
                    this.getRuleManagementService().deleteAgendaItem(deletedRule.getAgendaItem().getId());
                }
                this.getRuleManagementService().deleteRule(deletedRule.getId());
            }

            //If no more rules linked to agenda, delete it.
            if(firstItemId==null){
                List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                for(ReferenceObjectBinding referenceObjectBinding : refObjectsBindings){
                    if(referenceObjectBinding.getKrmsObjectId().equals(agenda.getId())){
                        this.getRuleManagementService().deleteReferenceObjectBinding(referenceObjectBinding.getId());
                    }
                }
                this.getRuleManagementService().deleteAgenda(agenda.getId());
            }

        }

    }

    protected String maintainAgendaItems(AgendaEditor agenda, String namePrefix, String nameSpace) {

        AgendaItemDefinition.Builder rootItemBuilder = null;
        for (int i = agenda.getRuleEditors().size() - 1; i >= 0; i--) {
            RuleEditor rule = agenda.getRuleEditors().get(i);
            if (!rule.isDummy()) {

                //Create / Update the agendaItems
                AgendaItemDefinition.Builder itemBuilder = AgendaItemDefinition.Builder.create(null, agenda.getId());
                if (rule.getAgendaItem() != null) {
                    itemBuilder.setId(rule.getAgendaItem().getId());
                    itemBuilder.setVersionNumber(rule.getAgendaItem().getVersionNumber());
                }
                itemBuilder.setRule(this.finRule(rule, namePrefix, nameSpace));
                itemBuilder.setWhenTrue(rootItemBuilder);

                rootItemBuilder = itemBuilder;

            }
        }

        if(rootItemBuilder == null){
            return null;
        }

        if (rootItemBuilder.getId() == null) {
            return this.getRuleManagementService().createAgendaItem(rootItemBuilder.build()).getId();
        }

        this.getRuleManagementService().updateAgendaItem(rootItemBuilder.build());
        return rootItemBuilder.getId();
    }

    protected RuleDefinition.Builder finRule(RuleEditor rule, String rulePrefix, String namespace) {
        // handle saving new parameterized terms
        PropositionEditor proposition = (PropositionEditor) rule.getProposition();
        if (proposition != null) {
            this.finPropositionEditor(proposition);
        }

        if (rule.getNamespace() == null) {
            rule.setNamespace(namespace);
        }
        rule.setName(rulePrefix + " " + rule.getRuleTypeInfo().getDescription());

        return RuleDefinition.Builder.create(rule);
    }

    protected void finPropositionEditor(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

            //Call onsubmit on the associated builder.
            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                builder.onSubmit(propositionEditor);
            }

            //Set the default operation and value
            TemplateInfo template = this.getTemplateRegistry().getTemplateForType(propositionEditor.getType());
            propositionEditor.getParameters().get(2).setValue(template.getOperator());

            if (!"n".equals(template.getValue())) {
                propositionEditor.getParameters().get(1).setValue(template.getValue());
            }

            if (propositionEditor.getTerm()!=null){
                TermDefinition.Builder termBuilder = TermDefinition.Builder.create(propositionEditor.getTerm());
                propositionEditor.getParameters().get(0).setTermValue(termBuilder.build());
            }

        } else {

            //If not a simple node, recursively finalize the child proposition editors.
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                finPropositionEditor(child);
            }

        }
    }

    protected void initPropositionEditor(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

            if (propositionEditor.getType() == null) {
                KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeById(propositionEditor.getTypeId());
                propositionEditor.setType(type.getName());
            }

            Map<String, String> termParameters = this.getTermParameters(propositionEditor);
            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                builder.resolveTermParameters(propositionEditor, termParameters);
            }
        } else {
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                initPropositionEditor(child);
            }

        }
    }

    protected Map<String, String> getTermParameters(PropositionEditor proposition) {

        Map<String, String> termParameters = new HashMap<String, String>();
        if (proposition.getTerm() == null) {
            if (proposition.getParameters().get(0) != null) {

                PropositionParameterEditor termParameter = proposition.getParameters().get(0);
                if (termParameter.getTermValue() == null) {
                    proposition.setTerm(new TermEditor());
                } else {
                    proposition.setTerm(new TermEditor(termParameter.getTermValue()));
                }

            } else {
                return termParameters;
            }
        }

        for (TermParameterEditor parameter : proposition.getTerm().getEditorParameters()) {
            termParameters.put(parameter.getName(), parameter.getValue());
        }

        return termParameters;
    }

    /**
     * In the case of edit maintenance adds a new blank line to the old side
     * <p/>
     * TODO: should this write some sort of missing message on the old side
     * instead?
     *
     * @see org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl#processAfterAddLine(org.kuali.rice.krad.uif.view.View,
     *      org.kuali.rice.krad.uif.container.CollectionGroup, Object,
     *      Object, boolean)
     */
    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine, boolean isValidLine) {
        // Check for maintenance documents in edit but exclude notes
        if (model instanceof MaintenanceDocumentForm && KRADConstants.MAINTENANCE_EDIT_ACTION.equals(((MaintenanceDocumentForm) model).getMaintenanceAction()) && !(addLine instanceof Note)) {
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
            MaintenanceDocument document = maintenanceDocumentForm.getDocument();

            // Figure out which rule is being edited
            RuleEditor rule = getRuleEditor(model);
            // Figure out which proposition is being edited
            Tree<RuleEditorTreeNode, String> propositionTree = rule.getEditTree();
            Node<RuleEditorTreeNode, String> editedPropositionNode = PropositionTreeUtil.findEditedProposition(propositionTree.getRootElement());

            // get the old object's collection
            Collection<Object> oldCollection = ObjectPropertyUtils
                    .getPropertyValue(editedPropositionNode.getData(),
                            collectionGroup.getPropertyName());

            try {
                Object blankLine = collectionGroup.getCollectionObjectClass().newInstance();
                //Add a blank line to the top of the collection
                if (oldCollection instanceof List) {
                    ((List) oldCollection).add(0, blankLine);
                } else {
                    oldCollection.add(blankLine);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to create new line instance for old maintenance object", e);
            }
        }
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

    public TermRepositoryService getTermRepositoryService() {
        if (termRepositoryService == null) {
            termRepositoryService = (TermRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "termRepositoryService"));
        }
        return termRepositoryService;
    }

    private TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }
}
