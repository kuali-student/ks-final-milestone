/**
 * Copyright 2005-2013 The Kuali Foundation
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
import org.apache.ojb.broker.OptimisticLockException;
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
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.common.krms.exceptions.KRMSOptimisticLockingException;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.springmodules.orm.ojb.OjbOperationException;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * {@link org.kuali.rice.krad.maintenance.Maintainable}
 *
 * @author Kuali Student Team (rice.collab@kuali.org)
 */
public class RuleEditorMaintainableImpl extends KSMaintainableImpl implements RuleEditorMaintainable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RuleEditorMaintainableImpl.class);

    private transient RuleManagementService ruleManagementService;
    private transient KrmsTypeRepositoryService krmsTypeRepositoryService;
    private transient TermRepositoryService termRepositoryService;

    private transient TemplateRegistry templateRegistry;
    private AlphaIterator alphaIterator = new AlphaIterator(StringUtils.EMPTY);

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
        RuleManagementWrapper dataObject = new RuleManagementWrapper();

        String refObjectId = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(refObjectId);

        dataObject.setAgendas(this.getAgendasForRef("", refObjectId, null));

        dataObject.setCompareTree(RuleCompareTreeBuilder.initCompareTree());

        return dataObject;
    }

    protected List<AgendaEditor> getAgendasForRef(String discriminatorType, String refObjectId, String parentRefObjectId) {
        // Initialize new array lists.
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
        List<AgendaEditor> sortedAgendas = new ArrayList<AgendaEditor>();
        List<AgendaEditor> parentAgendas = new ArrayList<AgendaEditor>();

        // Get the list of existing agendas
        LOG.info("Retrieving reference object binding for refobjectid: " + refObjectId);
        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(discriminatorType, refObjectId);
        for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
            LOG.info("Retrieved reference object binding with id: " + referenceObjectBinding);
            agendas.add(this.getAgendaEditor(referenceObjectBinding.getKrmsObjectId()));
        }

        // Get the list of parent agendas
        List<ReferenceObjectBinding> parentRefObjects = this.getParentRefOjbects(parentRefObjectId);
        for (ReferenceObjectBinding referenceObject : parentRefObjects) {
            parentAgendas.add(this.getAgendaEditor(referenceObject.getKrmsObjectId()));
        }

        // Lookup existing agenda by type
        for (AgendaTypeInfo agendaTypeInfo : this.getTypeRelationships()) {
            AgendaEditor agenda = null;
            for (AgendaEditor existingAgenda : agendas) {
                if (existingAgenda.getTypeId().equals(agendaTypeInfo.getId())) {
                    agenda = existingAgenda;
                    break;
                }
            }
            if (agenda == null) {
                agenda = new AgendaEditor();
                agenda.setTypeId(agendaTypeInfo.getId());
            }

            //Set the parent agenda.
            for (AgendaEditor parent : parentAgendas) {
                if (agenda.getTypeId().equals(agenda.getTypeId())) {
                    agenda.setParent(parent);
                    break;
                }
            }

            agenda.setAgendaTypeInfo(agendaTypeInfo);
            agenda.setRuleEditors(this.getRulesForAgendas(agenda));
            sortedAgendas.add(agenda);
        }

        return sortedAgendas;
    }

    protected AgendaEditor getAgendaEditor(String agendaId) {
        LOG.info("Retrieving agenda for id: " + agendaId);
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        return new AgendaEditor(agenda);
    }

    public Map<String, RuleEditor> getRulesForAgendas(AgendaEditor agenda) {

        //Get all existing rules.
        List<RuleEditor> existingRules = null;
        if (agenda.getId() != null) {
            LOG.info("Retrieving agenda item for id: " + agenda.getFirstItemId());
            AgendaItemDefinition firstItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
            existingRules = getRuleEditorsFromTree(firstItem, true);
        }

        //Get the parent rules
        List<RuleEditor> parentRules = null;
        if (agenda.getParent() != null) {
            AgendaItemDefinition parentItem = this.getRuleManagementService().getAgendaItem(agenda.getParent().getFirstItemId());
            parentRules = getRuleEditorsFromTree(parentItem, false);
        }

        //Add dummy RuleEditors for empty rule types.
        Map<String, RuleEditor> ruleEditors = new LinkedHashMap<String, RuleEditor>();
        for (RuleTypeInfo ruleType : agenda.getAgendaTypeInfo().getRuleTypes()) {
            RuleEditor ruleEditor = null;

            // Add all existing rules of this type.
            if (existingRules != null) {
                for (RuleEditor rule : existingRules) {
                    if (rule.getTypeId().equals(ruleType.getId()) && (!rule.isDummy())) {
                        ruleEditor = rule;
                    }
                }
            }

            // If the ruletype does not exist, add an empty rule section
            if (ruleEditor == null) {
                ruleEditor = createDummyRuleEditor(ruleType.getId());
            }

            ruleEditor.setKey((String) alphaIterator.next());
            ruleEditor.setRuleTypeInfo(ruleType);
            ruleEditors.put(ruleEditor.getKey(), ruleEditor);

            //Set the parent agenda.
            if (parentRules != null) {
                for (RuleEditor parent : parentRules) {
                    if (ruleEditor.getTypeId().equals(parent.getTypeId())) {
                        ruleEditor.setParent(parent);
                        break;
                    }
                }
            }
        }

        return ruleEditors;
    }

    protected RuleEditor createDummyRuleEditor(String ruleTypeId) {
        RuleEditor ruleEditor = new RuleEditor();
        ruleEditor.setDummy(true);
        ruleEditor.setTypeId(ruleTypeId);
        return ruleEditor;
    }

    protected List<RuleEditor> getRuleEditorsFromTree(AgendaItemDefinition agendaItem, boolean initProps) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        if (agendaItem.getRule() != null) {
            RuleEditor ruleEditor = new RuleEditor(agendaItem.getRule());
            if (initProps) {
                this.initPropositionEditor(ruleEditor.getPropositionEditor());
                ruleEditor.setViewTree(this.getViewTreeBuilder().buildTree(ruleEditor));
            }
            rules.add(ruleEditor);
        }

        if (agendaItem.getWhenTrue() != null) {
            rules.addAll(getRuleEditorsFromTree(agendaItem.getWhenTrue(), initProps));
        }

        return rules;
    }

    /**
     * Override this method to return the reference object id of the parent object.
     *
     * @param parentRefObjectId
     * @return
     */
    @Override
    public List<ReferenceObjectBinding> getParentRefOjbects(String parentRefObjectId) {
        return null;
    }

    protected RuleViewTreeBuilder getViewTreeBuilder() {
        return new RuleViewTreeBuilder();
    }

    /**
     * Setup a map with all the type information required to build an agenda management page.
     *
     * @return
     */
    protected List<AgendaTypeInfo> getTypeRelationships() {
        List<AgendaTypeInfo> agendaTypeInfos = new ArrayList<AgendaTypeInfo>();

        // Get Instruction Usage Id
        String instructionUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_TYPE_INSTRUCTION,
                StudentIdentityConstants.KS_NAMESPACE_CD).getId();

        // Get Description Usage Id
        String descriptionUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_TYPE_DESCRIPTION,
                StudentIdentityConstants.KS_NAMESPACE_CD).getId();

        // Get the super type.
        KrmsTypeDefinition requisitesType = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, this.getViewTypeName());

        // Get all agenda types linked to super type.
        List<TypeTypeRelation> agendaRelationships = this.getSortedTypeRelationshipsForTypeId(requisitesType.getId());
        for (TypeTypeRelation agendaRelationship : agendaRelationships) {
            AgendaTypeInfo agendaTypeInfo = new AgendaTypeInfo();
            agendaTypeInfo.setId(agendaRelationship.getToTypeId());
            KrmsTypeDefinition agendaType = this.getKrmsTypeRepositoryService().getTypeById(agendaTypeInfo.getId());
            agendaTypeInfo.setType(agendaType.getName());
            agendaTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(agendaRelationship.getToTypeId(), descriptionUsageId));

            List<RuleTypeInfo> ruleTypes = new ArrayList<RuleTypeInfo>();
            List<TypeTypeRelation> sortedRuleRelationships = this.getSortedTypeRelationshipsForTypeId(agendaRelationship.getToTypeId());
            for (TypeTypeRelation ruleRelationship : sortedRuleRelationships) {
                RuleTypeInfo ruleTypeInfo = new RuleTypeInfo();
                ruleTypeInfo.setId(ruleRelationship.getToTypeId());
                KrmsTypeDefinition ruleType = this.getKrmsTypeRepositoryService().getTypeById(ruleTypeInfo.getId());
                ruleTypeInfo.setType(ruleType.getName());
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

    private List<TypeTypeRelation> getSortedTypeRelationshipsForTypeId(String typeId){
        // Get all rule types for each agenda type
        List<TypeTypeRelation> relationships = new ArrayList<TypeTypeRelation>();
        relationships.addAll(this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(typeId));

        // order rules
        Collections.sort(relationships, new Comparator<TypeTypeRelation>() {
            @Override
            public int compare(TypeTypeRelation typeTypeRelation1, TypeTypeRelation typeTypeRelation2) {
                return typeTypeRelation1.getSequenceNumber().compareTo(typeTypeRelation2.getSequenceNumber());
            }
        });
        return relationships;
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
            agenda.setName(ruleWrapper.getRefObjectId() + ":" + agenda.getAgendaTypeInfo().getId() + ":1");

            //Retrieve the context and set the id on the agenda.
            if (agenda.getContextId() == null) {
                ContextDefinition context = this.getRuleManagementService().getContextByNameAndNamespace("Course Requirements", ruleWrapper.getNamespace());
                agenda.setContextId(context.getId());
            }

            //Create or update the agenda.
            if (agenda.getId() == null) {

                //Check if someone else has not created an agenda while this one was created.
                if(this.getRuleManagementService().getAgendaByNameAndContextId(agenda.getName(), agenda.getContextId())!=null){
                    throw new KRMSOptimisticLockingException();
                }

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

            //Update the root item.
            try {

                //Set the first agenda item id and save the agenda items
                AgendaItemDefinition firstItem = maintainAgendaItems(agenda, ruleWrapper.getRefObjectId() + ":", ruleWrapper.getNamespace());

                //If no more rules linked to agenda, delete it.
                if (firstItem.getRule() == null) {
                    List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                    for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
                        if (referenceObjectBinding.getKrmsObjectId().equals(agenda.getId())) {
                            LOG.info("Deleting reference object binding for id: " + referenceObjectBinding.getId());
                            this.getRuleManagementService().deleteReferenceObjectBinding(referenceObjectBinding.getId());
                        }
                    }
                    this.getRuleManagementService().deleteAgenda(agenda.getId());
                }


            } catch (OjbOperationException e) {
                //OptimisticLockException
                if (e.getCause() instanceof OptimisticLockException) {
                    throw new KRMSOptimisticLockingException("RuleEditorMaintainableImpl Optimistic Lock exception",e);
                } else {
                    throw e;
                }
            }

        }

    }

    public AgendaItemDefinition maintainAgendaItems(AgendaEditor agenda, String namePrefix, String nameSpace) {

        Queue<RuleDefinition.Builder> rules = new LinkedList<RuleDefinition.Builder>();
        for (RuleEditor rule : agenda.getRuleEditors().values()) {
            if (!rule.isDummy()) {
                rules.add(this.finRule(rule, namePrefix, nameSpace));
            }
        }

        // Clear the first item and update.
        AgendaItemDefinition firstItem = manageFirstItem(agenda);

        //Delete rules
        for (RuleEditor deletedRule : agenda.getDeletedRules()) {
            this.getRuleManagementService().deleteRule(deletedRule.getId());
        }

        AgendaItemDefinition.Builder rootItemBuilder = AgendaItemDefinition.Builder.create(firstItem);
        AgendaItemDefinition.Builder itemBuilder = rootItemBuilder;
        while (rules.peek() != null) {
            itemBuilder.setRule(rules.poll());
            itemBuilder.setRuleId(itemBuilder.getRule().getId());
            if (rules.peek() != null) {
                itemBuilder.setWhenTrue(AgendaItemDefinition.Builder.create(null, agenda.getId()));
                itemBuilder = itemBuilder.getWhenTrue();
            }
        }

        //Update the root item.
        AgendaItemDefinition updateItem = rootItemBuilder.build();
        this.getRuleManagementService().updateAgendaItem(updateItem);

        return updateItem;
    }

    protected AgendaItemDefinition manageFirstItem(AgendaEditor agenda) {
        AgendaItemDefinition firstItem;
        AgendaItemDefinition.Builder firstItemBuilder = AgendaItemDefinition.Builder.create(agenda.getFirstItemId(), agenda.getId());
        if (agenda.getFirstItemId() != null) {
            //Retrieve the first item from the database.
            firstItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
            if(firstItem==null){
                throw new KRMSOptimisticLockingException("Rule was deleted by another user.");
            }

            firstItemBuilder.setVersionNumber(firstItem.getVersionNumber());
            this.getRuleManagementService().updateAgendaItem(firstItemBuilder.build());

            //Delete current agenda items to rebuild the tree.
            this.deleteAgendaItems(firstItem.getWhenTrue());
            this.deleteAgendaItems(firstItem.getWhenFalse());
            this.deleteAgendaItems(firstItem.getAlways());
        } else {
            firstItem = this.getRuleManagementService().createAgendaItem(firstItemBuilder.build());
            agenda.setFirstItemId(firstItem.getId());
            AgendaDefinition.Builder agendaBldr = AgendaDefinition.Builder.create(agenda);
            this.getRuleManagementService().updateAgenda(agendaBldr.build());
        }
        return this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
    }

    public void deleteAgendaItems(AgendaItemDefinition agendaItem) {
        if (agendaItem != null) {
            this.getRuleManagementService().deleteAgendaItem(agendaItem.getId());
            deleteAgendaItems(agendaItem.getWhenFalse());
            deleteAgendaItems(agendaItem.getWhenTrue());
            deleteAgendaItems(agendaItem.getAlways());
        }
    }

    public RuleDefinition.Builder finRule(RuleEditor rule, String rulePrefix, String namespace) {
        // handle saving new parameterized terms
        if (rule.getPropositionEditor() != null) {
            this.onSubmit(rule.getPropositionEditor());
        }

        if (rule.getNamespace() == null) {
            rule.setNamespace(namespace);
        }
        rule.setName(rulePrefix + rule.getRuleTypeInfo().getId() + ":1");

        //Check if someone else has not created a rule while this one was created.
        if(rule.getId()==null){
            if(this.getRuleManagementService().getRuleByNameAndNamespace(rule.getName(), rule.getNamespace())!=null){
                throw new KRMSOptimisticLockingException();
            }
        }

        return RuleDefinition.Builder.create(rule);
    }

    public void onSubmit(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

            //Call onsubmit on the associated builder.
            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                builder.onSubmit(propositionEditor);
            }

        } else {

            //If not a simple node, recursively finalize the child proposition editors.
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                onSubmit(child);
            }

        }
    }

    public void initPropositionEditor(PropositionEditor propositionEditor) {
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

    public Map<String, String> getTermParameters(PropositionEditor proposition) {

        Map<String, String> termParameters = new HashMap<String, String>();
        if (proposition.getTerm() == null) {
            PropositionParameterEditor termParameter = PropositionTreeUtil.getTermParameter(proposition.getParameters());
            if (termParameter != null) {

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
     *
     * @see org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl#processAfterAddLine(org.kuali.rice.krad.uif.view.View,
     *      org.kuali.rice.krad.uif.container.CollectionGroup, Object,
     *      Object, boolean)
     */
    @Override
    public void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine, boolean isValidLine) {
        // Check for maintenance documents in edit but exclude notes
        if (model instanceof MaintenanceDocumentForm && KRADConstants.MAINTENANCE_EDIT_ACTION.equals(((MaintenanceDocumentForm) model).getMaintenanceAction()) && !(addLine instanceof Note)) {

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
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/templateResolverService", "templateResolverService"));
        }
        return templateRegistry;
    }
}
