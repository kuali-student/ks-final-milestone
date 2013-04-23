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
import org.kuali.rice.krms.api.repository.agenda.*;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.dto.*;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.service.RuleEditorMaintainable;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleManagementWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                if (agendaItem.getRuleId() != null) {
                    RuleDefinition rule = this.getRuleManagementService().getRule(treeRuleEntry.getRuleId());
                    RuleEditor ruleEditor = new RuleEditor(rule);
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

        for (AgendaEditor agenda : ruleWrapper.getAgendas()){

            List<RuleEditor> deleteRuleList = new ArrayList<RuleEditor>();
            for(RuleEditor rule : agenda.getRuleEditors()) {
                if(!rule.isDummy()) {
                    this.finRule(rule);
                } else {
                    deleteRuleList.add(rule);
                }
            }
            agenda.getRuleEditors().removeAll(deleteRuleList);

            AgendaDefinition.Builder agendaBuilder = null;
            AgendaDefinition agendaDefinition = null;
            if(agenda.getId() == null) {
                agendaBuilder = AgendaDefinition.Builder.create(agenda);
                agendaDefinition = agendaBuilder.build();
            }

            if(agenda.getRuleEditors().isEmpty()) {
                this.getRuleManagementService().deleteAgenda(agenda.getId());
            } else {
                if(agendaDefinition == null) {
                    agendaBuilder = AgendaDefinition.Builder.create(agenda);
                    agendaDefinition = agendaBuilder.build();
                }
                this.getRuleManagementService().updateAgenda(agendaDefinition);
            }
        }

    }

    protected void finRule(RuleEditor rule) {
        // handle saving new parameterized terms
        PropositionEditor proposition = (PropositionEditor) rule.getProposition();
        if (proposition != null) {
            this.finPropositionEditor(proposition);
        }

        RuleDefinition.Builder ruleBuilder = RuleDefinition.Builder.create(rule);
        RuleDefinition ruleDefinition = ruleBuilder.build();
        if (ruleDefinition.getId() == null) {
            this.getRuleManagementService().createRule(ruleDefinition);
        } else {
            this.getRuleManagementService().updateRule(ruleDefinition);
        }
    }

    protected void finPropositionEditor(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

            //Save term and set termid.
            String termId = this.saveTerm(propositionEditor);
            if (propositionEditor.getParameters().get(0) != null) {
                propositionEditor.getParameters().get(0).setValue(termId);
            }

            //Set the default operation and value
            TemplateInfo template = this.getTemplateRegistry().getTemplateForType(propositionEditor.getType());
            propositionEditor.getParameters().get(2).setValue(template.getOperator());

            if (!"n".equals(template.getValue())) {
                propositionEditor.getParameters().get(1).setValue(template.getValue());
            }

        } else {

            //If not a simple node, recursively finalize the child proposition editors.
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                finPropositionEditor(child);
            }

        }
    }

    protected String saveTerm(PropositionEditor propositionEditor) {

        //Set the termSpecification based on current type.
        TermEditor term = propositionEditor.getTerm();
        term.setSpecification(this.getTermSpecForType(propositionEditor.getType()));

        TermDefinition.Builder termBuilder = TermDefinition.Builder.create(term);
        TermDefinition termDefinition = termBuilder.build();
        if (term.getId() == null) {
            termDefinition = this.getTermRepositoryService().createTerm(termDefinition);

        } else {
            this.getTermRepositoryService().updateTerm(termDefinition);
        }

        return termDefinition.getId();
    }

    protected TermSpecificationDefinition getTermSpecForType(String type) {

        //Get the term output name for this type.
        String termSpecName = this.getTemplateRegistry().getTermSpecNameForType(type);

        List<TermResolverDefinition> matchingTermResolvers = this.getTermRepositoryService().findTermResolversByNamespace(PermissionServiceConstants.KS_SYS_NAMESPACE);
        for (TermResolverDefinition termResolver : matchingTermResolvers) {
            TermSpecificationDefinition termSpec = termResolver.getOutput();
            if (termSpec.getName().equals(termSpecName)) {
                return termSpec;
            }
        }

        return null;
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
                if (termParameter.getTermValue() == null){
                    String termId = proposition.getParameters().get(0).getValue();
                    termParameter.setTermValue(this.getTermRepositoryService().getTerm(termId));
                }
                proposition.setTerm(new TermEditor(termParameter.getTermValue()));
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
     *      Object)
     */
    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
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
