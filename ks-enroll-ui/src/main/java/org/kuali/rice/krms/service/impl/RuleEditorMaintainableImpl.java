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
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.service.RuleEditorMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;

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
    private RuleEditor getRuleEditor(Object model) {
        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        return (RuleEditor) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        Object dataObject = null;

        String ruleId = dataObjectKeys.get("id");
        RuleDefinition rule = this.getRuleManagementService().getRule(ruleId);

        // Since the dataObject is a wrapper class we need to build it and populate with the agenda bo.
        RuleEditor ruleEditor = new RuleEditor(rule);

        //Initialize the PropositionEditors
        if ((ruleEditor != null) && (ruleEditor.getProposition() != null)) {
            this.initPropositionEditor((PropositionEditor) ruleEditor.getProposition());
        }

        //Initialize the compare tree
        ruleEditor.setCompareTree(this.initCompareTree());

        dataObject = ruleEditor;

        return dataObject;
    }

    public Tree<CompareTreeNode, String> initCompareTree() {
        Tree<CompareTreeNode, String> myTree = new Tree<CompareTreeNode, String>();

        Node<CompareTreeNode, String> rootNode = new Node<CompareTreeNode, String>();
        rootNode.setNodeType("subruleElement");
        rootNode.setData(new CompareTreeNode());
        myTree.setRootElement(rootNode);

        Node<CompareTreeNode, String> firstNode = new Node<CompareTreeNode, String>();
        firstNode.setNodeType("subruleElement");
        firstNode.setData(new CompareTreeNode());
        rootNode.getChildren().add(firstNode);

        return myTree;
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
        RuleEditor rule = (RuleEditor) getDataObject();

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

            //Build the new termParamters with the matching component builder.
            Map<String, String> termParameters = null;
            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                termParameters = builder.buildTermParameters(propositionEditor);
            }

            //Save term and set termid.
            String termId = this.saveTerm(propositionEditor, termParameters);
            if (propositionEditor.getParameters().get(0) != null) {
                propositionEditor.getParameters().get(0).setValue(termId);
            }

        } else {

            //If not a simple node, recursively finalize the child proposition editors.
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                finPropositionEditor(child);
            }

        }
    }

    protected String saveTerm(PropositionEditor propositionEditor, Map<String, String> termParameters) {
        TermEditor term;
        if (propositionEditor.getTerm() != null) {
            term = new TermEditor(propositionEditor.getTerm());
        } else {
            term = new TermEditor();
        }

        //Set the termSpecification based on current type.
        term.setSpecification(this.getTermSpecForType(propositionEditor.getType()));

        List<TermParameterEditor> parameters = new ArrayList<TermParameterEditor>();
        if (termParameters != null) {
            for (Map.Entry<String, String> entry : termParameters.entrySet()) {

                TermParameterEditor parameterEditor = null;
                if (term.getParameters() != null) {
                    for (TermParameterEditor parameter : term.getEditorParameters()) {
                        if (entry.getKey().equals(parameter.getName())) {
                            parameterEditor = parameter;
                            parameterEditor.setValue(entry.getValue());
                            break;
                        }
                    }
                }

                //Create a new parameter if not exist.
                if (parameterEditor == null) {
                    parameterEditor = new TermParameterEditor();
                    parameterEditor.setName(entry.getKey());
                    parameterEditor.setValue(entry.getValue());
                }
                parameters.add(parameterEditor);
            }
        }
        term.setParameters(parameters);

        TermDefinition.Builder termBuilder = TermDefinition.Builder.create(term);
        TermDefinition termDefinition = termBuilder.build();
        if (term.getId() == null) {
            termDefinition = this.getTermRepositoryService().createTerm(termDefinition);

        } else {
            this.getTermRepositoryService().updateTerm(termDefinition);
        }
        propositionEditor.setTerm(termDefinition);

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

            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                Map<String, String> termParameters = this.getTermParameters(propositionEditor);
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
                String termId = proposition.getParameters().get(0).getValue();
                proposition.setTerm(this.getTermRepositoryService().getTerm(termId));
            } else {
                return termParameters;
            }
        }

        for (TermParameterDefinition parameter : proposition.getTerm().getParameters()) {
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
