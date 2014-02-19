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
package org.kuali.student.cm.course.service.impl;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.core.krms.tree.KSRuleCompareTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.service.impl.LURuleViewHelperServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  This class was created to override {@link LURuleViewHelperServiceImpl} for Curriculum Management KRMS (Course Requisites) purposes. 
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class CourseRuleViewHelperServiceImpl extends LURuleViewHelperServiceImpl {

    private KSRuleCompareTreeBuilder compareTreeBuilder;

    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return LUPropositionEditor.class;
    }

    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, RuleEditor compare) {

        //Set the original nl if not already exists.
        checkNaturalLanguageForTree(original);
        checkNaturalLanguageForTree(compare);

        Tree<CompareTreeNode, String> compareTree = this.getCompareTreeBuilder().buildTree(original, compare);

        return compareTree;
    }

    /**
     * Initializes the proposition, populating the type and terms.
     *
     * @param propositionEditor
     */
    @Override
    public void initPropositionEditor(PropositionEditor propositionEditor) {
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

    /**
     * Create TermEditor from the TermDefinition objects to be used in the ui and return a map of
     * the key and values of the term parameters.
     *
     * @param proposition
     * @return
     */
    @Override
    public Map<String, String> getTermParameters(PropositionEditor proposition) {

        Map<String, String> termParameters = new HashMap<String, String>();
        if (proposition.getTerm() == null) {
            PropositionParameterEditor termParameter = PropositionTreeUtil.getTermParameter(proposition.getParameters());
            if (termParameter != null) {
                String termId = termParameter.getValue();
                TermDefinition termDefinition = this.getTermRepositoryService().getTerm(termId);
                proposition.setTerm(new TermEditor(termDefinition));
            } else {
                return termParameters;
            }
        }

        for (TermParameterEditor parameter : proposition.getTerm().getEditorParameters()) {
            termParameters.put(parameter.getName(), parameter.getValue());
        }

        return termParameters;
    }
    
    @Override
    public void addCustomContainerComponents(Object model, Container container) {
        if (KRMSConstants.KRMS_PROPOSITION_DETAILSECTION_ID.equals(container.getId())) {
            customizePropositionEditSection(((MaintenanceDocumentForm)model).getView(), model, container);
        }
    }
    
    private void customizePropositionEditSection(View view, Object model, Container container) {
        //Retrieve the current editing proposition if exists.
        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        CourseInfoMaintainable courseInfoMaintainable = (CourseInfoMaintainable)maintenanceDocumentForm.getDocument().getNewMaintainableObject();

        RuleEditor ruleEditor = courseInfoMaintainable.getCourseRuleManagementWrapper().getRuleEditor();
        PropositionEditor propEditor = PropositionTreeUtil.getProposition(ruleEditor);

        List<Component> components = new ArrayList<Component>();
        if (propEditor != null) {
            //Retrieve the name of the xml component to display for the proposition type.
            TemplateInfo template = this.getTemplateForType(propEditor.getType());

            if (template != null && template.getComponentId() != null) {
                Component component = ComponentFactory.getNewComponentInstance(template.getComponentId());
//                view.assignComponentIds(component);
                ViewLifecycle.spawnSubLifecyle(model, component, container);
                if(container.getId().equals(maintenanceDocumentForm.getUpdateComponentId())){
                    String nodePath = view.getDefaultBindingObjectPath() + "." + propEditor.getBindingPath();
                    ComponentUtils.pushObjectToContext(component, UifConstants.ContextVariableNames.NODE_PATH, nodePath);
                    ComponentUtils.prefixBindingPathNested(component, propEditor.getBindingPath());
                }

                //Add Proposition Type FieldGroup to Tree Node
                components.add(component);
            }

            if (template != null && template.getConstantComponentId() != null) {
                Component component = ComponentFactory.getNewComponentInstance(template.getConstantComponentId());
//                view.assignComponentIds(component);
                ViewLifecycle.spawnSubLifecyle(model, component, container);

                //Add Proposition Type FieldGroup to Tree Node
                components.add(component);
            }
        }

        //Do not display if there are no components.
        if (components.size() == 0) {
            container.getHeader().setRender(false);
        }

        container.setItems(components);
    }
    
    
    @Override
    public boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    @Override
    public void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine,
            boolean isValidLine) {
        super.processAfterAddLine(view, collectionGroup, model, addLine, isValidLine);
    }
    
    @Override
    protected RuleEditor getRuleEditor(Object model) {
        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
            CourseInfoMaintainable courseInfoMaintainable = (CourseInfoMaintainable)maintenanceDocumentForm.getDocument().getNewMaintainableObject();
            RuleManagementWrapper ruleWrapper = courseInfoMaintainable.getCourseRuleManagementWrapper();
            return ruleWrapper.getRuleEditor();
        }
        return null;
    }

    @Override
    protected RuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new KSRuleCompareTreeBuilder();
            compareTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return compareTreeBuilder;
    }

}
