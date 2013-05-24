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
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.util.ComponentFactory;
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
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.dto.*;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.service.RuleEditorMaintainable;
import org.kuali.rice.krms.util.AlphaIterator;
import org.kuali.rice.krms.util.ExpressionToken;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleManagementWrapper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
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
    private AlphaIterator alphaIterator = new AlphaIterator();

    public static final String NEW_AGENDA_EDITOR_DOCUMENT_TEXT = "New Agenda Editor Document";

    public String getViewTypeName() {
        return "kuali.krms.agenda.type";
    }

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

        String refObjectId = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(refObjectId);

        dataObject.setAgendas(this.getAgendasForRef("", refObjectId));

        dataObject.setCompareTree(RuleCompareTreeBuilder.initCompareTree());

        return dataObject;
    }

    protected List<AgendaEditor> getAgendasForRef(String discriminatorType, String refObjectId){
        // Initialize new array lists.
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
        List<AgendaEditor> sortedAgendas = new ArrayList<AgendaEditor>();

        // Get the list of existing agendas
        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(discriminatorType, refObjectId);
        for(ReferenceObjectBinding referenceObjectBinding : refObjectsBindings){
            agendas.add(this.getAgendaEditor(referenceObjectBinding.getKrmsObjectId()));
        }

        // Lookup existing agenda by type
        for (AgendaTypeInfo agendaTypeInfo : this.getTypeRelationships()) {
            AgendaEditor agenda = null;
            for (AgendaEditor existingAgenda : agendas) {
                if (existingAgenda.getTypeId().equals(agendaTypeInfo.getId())) {
                    agenda=existingAgenda;
                    break;
                }
            }
            if (agenda==null) {
                agenda = new AgendaEditor();
                agenda.setTypeId(agendaTypeInfo.getId());
            }
            agenda.setAgendaTypeInfo(agendaTypeInfo);
            agenda.setRuleEditors(this.getRulesForAgendas(agenda));
            sortedAgendas.add(agenda);
        }

        return sortedAgendas;
    }

    protected AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        AgendaEditor agendaEditor = new AgendaEditor(agenda);

        return agendaEditor;
    }

    public Map<String, RuleEditor> getRulesForAgendas(AgendaEditor agenda) {

        //Get all existing rules.
        List<RuleEditor> existingRules = null;
        if(agenda.getId()!=null){
            AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(agenda.getId());
            existingRules = getRuleEditorsFromTree(agendaTree.getEntries());
        }

        //Add dummy RuleEditors for empty rule types.
        Map<String, RuleEditor> ruleEditors = new LinkedHashMap<String, RuleEditor>();
        for (RuleTypeInfo ruleType : agenda.getAgendaTypeInfo().getRuleTypes()) {

            // Add all existing rules of this type.
            boolean exist = false;
            if (existingRules != null) {
                for (RuleEditor rule : existingRules) {
                    if (rule.getTypeId().equals(ruleType.getId()) && (!rule.isDummy())) {
                        rule.setKey((String)alphaIterator.next());
                        rule.setRuleTypeInfo(ruleType);
                        exist = true;

                        ruleEditors.put(rule.getKey(), rule);
                    }
                }
            }

            // If the ruletype does not exist, add an empty rule section
            if (!exist) {
                RuleEditor ruleEditor = new RuleEditor();
                ruleEditor.setKey((String)alphaIterator.next());
                ruleEditor.setDummy(true);
                ruleEditor.setTypeId(ruleType.getId());
                ruleEditor.setRuleTypeInfo(ruleType);
                ruleEditors.put(ruleEditor.getKey(), ruleEditor);
            }

        }

        return ruleEditors;
    }

    protected List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());

                if (agendaItem.getRule() != null) {
                    RuleEditor ruleEditor = new RuleEditor(agendaItem.getRule());
                    this.initPropositionEditor((PropositionEditor) ruleEditor.getProposition());
                    ruleEditor.setViewTree(this.getViewTreeBuilder().buildTree(ruleEditor));
                    rules.add(ruleEditor);
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    rules.addAll(getRuleEditorsFromTree(treeRuleEntry.getIfTrue().getEntries()));
                }
            }
        }
        return rules;
    }

    protected RuleViewTreeBuilder getViewTreeBuilder(){
        RuleViewTreeBuilder viewTreeBuilder = new RuleViewTreeBuilder();
        viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        return viewTreeBuilder;
    }

    /**
     * Setup a map with all the type information required to build an agenda management page.
     *
     * @return
     */
    protected List<AgendaTypeInfo> getTypeRelationships() {
        List<AgendaTypeInfo> agendaTypeInfos = new ArrayList<AgendaTypeInfo>();

        // Get Instruction Usage Id
        String instructionUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_TYPE_INSTRUCTION,
                PermissionServiceConstants.KS_SYS_NAMESPACE).getId();

        // Get Description Usage Id
        String descriptionUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION,
                PermissionServiceConstants.KS_SYS_NAMESPACE).getId();

        // Get the super type.
        KrmsTypeDefinition requisitesType = this.getKrmsTypeRepositoryService().getTypeByName(PermissionServiceConstants.KS_SYS_NAMESPACE, this.getViewTypeName());

        // Get all agenda types linked to super type.
        List<TypeTypeRelation> agendaRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(requisitesType.getId());
        for (TypeTypeRelation agendaRelationship : agendaRelationships) {
            AgendaTypeInfo agendaTypeInfo = new AgendaTypeInfo();
            agendaTypeInfo.setId(agendaRelationship.getToTypeId());
            agendaTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(agendaRelationship.getToTypeId(), descriptionUsageId));

            // Get all rule types for each agenda type
            List<TypeTypeRelation> ruleRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(agendaRelationship.getToTypeId());
            List<RuleTypeInfo> ruleTypes = new ArrayList<RuleTypeInfo>();
            for (TypeTypeRelation ruleRelationship : ruleRelationships) {
                RuleTypeInfo ruleTypeInfo = new RuleTypeInfo();
                ruleTypeInfo.setId(ruleRelationship.getToTypeId());
                ruleTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(ruleRelationship.getToTypeId(), descriptionUsageId));
                if (ruleTypeInfo.getDescription().isEmpty()) {
                    ruleTypeInfo.setDescription("Description is unset rule type");
                }
                ruleTypeInfo.setInstruction(this.getDescriptionForTypeAndUsage(ruleRelationship.getToTypeId(), instructionUsageId));
                if (ruleTypeInfo.getInstruction().isEmpty()) {
                    ruleTypeInfo.setInstruction("Instruction is unset for rule type");
                }
                // Add rule types to list.
                ruleTypes.add(ruleTypeInfo);
            }
            agendaTypeInfo.setRuleTypes(ruleTypes);
            agendaTypeInfos.add(agendaTypeInfo);
        }

        return agendaTypeInfos;
    }

    private String getDescriptionForTypeAndUsage(String typeId, String usageId) {
        NaturalLanguageTemplate template = null;
        try {
            template = getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeId, usageId);
            return template.getTemplate();
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
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
            if (agenda.isDummyAgenda()) {
                continue;
            }

            //Set the agenda name.
            agenda.setName(ruleWrapper.getNamePrefix() + " " + agenda.getAgendaTypeInfo().getDescription());

            //Retrieve the context and set the id ong the agenda.
            if (agenda.getContextId() == null) {
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
                agenda.setFirstItemId(agendaDfn.getFirstItemId());

                //Create the reference object binding only on create agenda, no need to update.
                ReferenceObjectBinding.Builder refBuilder = ReferenceObjectBinding.Builder.create("Agenda",
                        agendaDfn.getId(), ruleWrapper.getNamespace(), ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                this.getRuleManagementService().createReferenceObjectBinding(refBuilder.build());
            }

            //Set the first agenda item id and save the agenda items
            AgendaItemDefinition firstItem = maintainAgendaItems(agenda, ruleWrapper.getNamePrefix(), ruleWrapper.getNamespace());

            //Delete rules
            for (RuleEditor deletedRule : agenda.getDeletedRules()) {
                this.getRuleManagementService().deleteRule(deletedRule.getId());
            }

            //If no more rules linked to agenda, delete it.
            if (firstItem.getRule() == null) {
                List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
                    if (referenceObjectBinding.getKrmsObjectId().equals(agenda.getId())) {
                        this.getRuleManagementService().deleteReferenceObjectBinding(referenceObjectBinding.getId());
                    }
                }
                this.getRuleManagementService().deleteAgendaItem(firstItem.getId());
                this.getRuleManagementService().deleteAgenda(agenda.getId());
            }

        }

    }

    protected AgendaItemDefinition maintainAgendaItems(AgendaEditor agenda, String namePrefix, String nameSpace) {

        Stack<RuleEditor> rules = new Stack<RuleEditor>();
        for (RuleEditor rule : agenda.getRuleEditors().values()) {
            if (!rule.isDummy()) {
                rules.push(rule);
            }
        }

        AgendaItemDefinition rootItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
        AgendaItemDefinition.Builder rootItemBuilder = AgendaItemDefinition.Builder.create(rootItem);
        AgendaItemDefinition.Builder deleteItem = null;
        AgendaItemDefinition.Builder itemBuilder = rootItemBuilder;

        if (rules.empty()) {
            rootItemBuilder.setRule(null);
            rootItemBuilder.setRuleId(null);
        }

        while (!rules.empty()) {
            RuleEditor rule = rules.pop();
            itemBuilder.setRule(this.finRule(rule, namePrefix, nameSpace));
            itemBuilder.setRuleId(itemBuilder.getRule().getId());
            if (!rules.empty()) {
                if (itemBuilder.getWhenTrue() == null) {
                    itemBuilder.setWhenTrue(AgendaItemDefinition.Builder.create(null, agenda.getId()));
                }
                itemBuilder = itemBuilder.getWhenTrue();
            }
        }

        //Keep the when true if already exist so that we can delete it.
        deleteItem = itemBuilder.getWhenTrue();

        //Set the last leaf item's when true to null;
        itemBuilder.setWhenTrue(null);
        itemBuilder.setWhenTrueId(null);

        //Update the root item.
        AgendaItemDefinition updateItem = rootItemBuilder.build();
        this.getRuleManagementService().updateAgendaItem(updateItem);

        //Recursively delete all orphans.
        deleteAgendaItems(deleteItem);

        return updateItem;
    }

    protected void deleteAgendaItems(AgendaItemDefinition.Builder agendaItem) {
        if (agendaItem != null) {
            this.getRuleManagementService().deleteAgendaItem(agendaItem.getId());
            this.deleteAgendaItems(agendaItem.getWhenTrue());
        }
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

            if (propositionEditor.getTerm() != null) {
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
        if (propositionEditor == null) {
            return;
        }

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
