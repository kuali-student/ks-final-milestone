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
package org.kuali.rice.krms.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.rule.AgendaEditorBusRule;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.enrollment.class1.krms.tree.node.KSSimplePropositionEditNode;
import org.kuali.student.enrollment.class1.krms.tree.node.KSSimplePropositionNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.util.RuleLogicExpressionParser;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
import org.kuali.student.krms.KRMSConstants;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Test UI Page
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class RuleEditorController extends MaintenanceDocumentController {

    /**
     * This method updates the existing rule in the agenda.
     */
    @RequestMapping(params = "methodToCall=editRule")
    public ModelAndView editRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);

        RuleViewHelperService viewHelper = (RuleViewHelperService) KSControllerHelper.getViewHelperService(form);
        if (!viewHelper.validateProposition((PropositionEditor) ruleEditor.getProposition(), ruleEditor.getNamespace())) {
            form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "AgendaStudentEditorView-EditRule-Page");
            // NOTICE short circuit method on invalid proposition
            return super.navigate(form, result, request, response);
        }

        AgendaEditorBusRule rule = new AgendaEditorBusRule();
        MaintenanceDocumentForm MaintenanceDocumentForm = (MaintenanceDocumentForm) form;
        MaintenanceDocument document = MaintenanceDocumentForm.getDocument();
        if (rule.processAgendaItemBusinessRules(document)) {
            form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "AgendaStudentEditorView-Agenda-Page");
        } else {
            form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "AgendaStudentEditorView-EditRule-Page");
        }
        return super.navigate(form, result, request, response);
    }

    @RequestMapping(params = "methodToCall=ajaxRefresh")
    public ModelAndView ajaxRefresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // call the super method to avoid the agenda tree being reloaded from the db
        return getUIFModelAndView(form);
    }

    /**
     * @param form
     * @return the {@link org.kuali.rice.krms.impl.ui.AgendaEditor} from the form
     */
    private RuleEditor getRuleEditor(UifFormBase form) {
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) form;
        return ((RuleEditor) maintenanceForm.getDocument().getDocumentDataObject());
    }

    //
    // Rule Editor Controller methods
    //
    @RequestMapping(params = "methodToCall=copyRule")
    public ModelAndView copyRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        //TODO: Copy rule to a different Co or AO

        return super.refresh(form, result, request, response);
    }

    /**
     * This method starts an edit proposition.
     */
    @RequestMapping(params = "methodToCall=goToEditProposition")
    public ModelAndView goToEditProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleViewHelperService viewHelper = this.getViewHelper(form);

        // open the selected node for editing
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedpropKey = ruleEditor.getSelectedKey();

        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
        PropositionEditor propositionToToggleEdit = null;
        boolean newEditMode = true;

        // find parent
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedpropKey);
        if (parent != null) {
            List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
            for (int index = 0; index < children.size(); index++) {
                Node<RuleEditorTreeNode, String> child = children.get(index);
                if (propKeyMatches(child, selectedpropKey)) {
                    PropositionEditor prop = child.getData().getProposition();
                    propositionToToggleEdit = prop;
                    newEditMode = !prop.isEditMode();
                    break;
                } else {
                    child.getData().getProposition().setEditMode(false);
                }
            }
        }

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        if (propositionToToggleEdit != null) {
            propositionToToggleEdit.setEditMode(newEditMode);
            //refresh the tree
            viewHelper.refreshInitTrees(ruleEditor, false);
            viewHelper.setLogicSection(ruleEditor);
        }

        PropositionEditor proposition = PropositionTreeUtil.getProposition(ruleEditor);
        if (!PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {

            String propositionTypeId = proposition.getTypeId();
            if (propositionTypeId == null) {
                proposition.setType(null);
            } else {

                KrmsTypeDefinition type = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeById(propositionTypeId);
                if (type != null) {
                    proposition.setType(type.getName());
                }
            }

        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addProposition")
    public ModelAndView addProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropKey = ruleEditor.getSelectedKey();

        // find parent
        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedPropKey);

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        RuleViewHelperService viewHelper = this.getViewHelper(form);

        // add new child at appropriate spot
        if (parent != null) {
            List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
            for (int index = 0; index < children.size(); index++) {
                Node<RuleEditorTreeNode, String> child = children.get(index);

                // if our selected node is a simple proposition, add a new one after
                if (propKeyMatches(child, selectedPropKey)) {
                    // handle special case of adding to a lone simple proposition.
                    // in this case, we need to change the root level proposition to a compound proposition
                    // move the existing simple proposition as the first compound component,
                    // then add a new blank simple prop as the second compound component.
                    if (parent.equals(root) &&
                            (isSimpleNode(child.getNodeType()))) {

                        // create a new compound proposition
                        PropositionEditor compound = viewHelper.createCompoundPropositionBoStub(child.getData().getProposition(), true);
                        compound.setDescription(KRMSConstants.PROP_COMP_DEFAULT_DESCR);
                        // don't set compound.setEditMode(true) as the Simple Prop in the compound prop is the only prop in edit mode
                        ruleEditor.setProposition(compound);
                    }
                    // handle regular case of adding a simple prop to an existing compound prop
                    else if (isSimpleNode(child.getNodeType())) {

                        // build new Blank Proposition
                        PropositionEditor blank = viewHelper.createSimplePropositionBoStub(child.getData().getProposition());
                        //add it to the parent
                        PropositionEditor parentProp = parent.getData().getProposition();
                        parentProp.getCompoundEditors().add(((index / 2) + 1), blank);
                    }
                    this.getViewHelper(form).refreshInitTrees(ruleEditor, false);
                    this.getViewHelper(form).setLogicSection(ruleEditor);
                    break;
                }
            }
        } else {
            // special case, if root has no children, add a new simple proposition
            // todo: how to add compound proposition. - just add another to the firs simple
            if (root.getChildren().isEmpty()) {
                PropositionEditor blank = viewHelper.createSimplePropositionBoStub(null);
                blank.setRuleId(ruleEditor.getId());
                ruleEditor.setPropId(blank.getId());
                ruleEditor.setProposition(blank);
                this.getViewHelper(form).refreshInitTrees(ruleEditor, false);
                this.getViewHelper(form).setLogicSection(ruleEditor);
            }
        }
        return getUIFModelAndView(form);
    }

    private boolean propKeyMatches(Node<RuleEditorTreeNode, String> node, String propKey) {
        if (propKey != null && node != null && node.getData() != null && propKey.equalsIgnoreCase(node.getData().getProposition().getKey())) {
            return true;
        }
        return false;
    }

    /**
     * This method return the index of the position of the child that matches the id
     *
     * @param parent
     * @param propKey
     * @return index if found, -1 if not found
     */
    private int findChildIndex(Node<RuleEditorTreeNode, String> parent, String propKey) {
        int index;
        List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
        for (index = 0; index < children.size(); index++) {
            Node<RuleEditorTreeNode, String> child = children.get(index);
            // if our selected node is a simple proposition, add a new one after
            if (propKeyMatches(child, propKey)) {
                return index;
            }
        }
        return -1;
    }

    @RequestMapping(params = "methodToCall=movePropositionUp")
    public ModelAndView movePropositionUp(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        moveSelectedProposition(form, true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=movePropositionDown")
    public ModelAndView movePropositionDown(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        moveSelectedProposition(form, false);

        return getUIFModelAndView(form);
    }

    private void moveSelectedProposition(UifFormBase form, boolean up) {

        /* Rough algorithm for moving a node up.
         *
         * find the following:
         *   node := the selected node
         *   parent := the selected node's parent, its containing node (via when true or when false relationship)
         *   parentsOlderCousin := the parent's level-order predecessor (sibling or cousin)
         *
         */
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropKey = ruleEditor.getSelectedKey();

        // find parent
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(ruleEditor.getEditTree().getRootElement(), selectedPropKey);

        // add new child at appropriate spot
        if (parent != null) {
            List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
            for (int index = 0; index < children.size(); index++) {
                Node<RuleEditorTreeNode, String> child = children.get(index);
                // if our selected node is a simple proposition, add a new one after
                if (propKeyMatches(child, selectedPropKey) &&
                        (isSimpleNode(child.getNodeType()) || (RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(child.getNodeType())))) {

                    //remove it from its current spot
                    PropositionEditor parentProp = parent.getData().getProposition();
                    PropositionEditor workingProp = parentProp.getCompoundEditors().remove(index / 2);
                    if ((index > 0) && up) {
                        parentProp.getCompoundEditors().add((index / 2) - 1, workingProp);
                    } else if ((index < (children.size() - 1) && !up)) {
                        parentProp.getCompoundEditors().add((index / 2) + 1, workingProp);
                    }
                    // redisplay the tree (editMode = true)
                    this.getViewHelper(form).refreshInitTrees(ruleEditor, false);
                    this.getViewHelper(form).setLogicSection(ruleEditor);
                    break;
                }
            }
        }
    }

    public boolean isSimpleNode(String nodeType) {
        if (KSSimplePropositionNode.NODE_TYPE.equalsIgnoreCase(nodeType) ||
                KSSimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(nodeType)) {
            return true;
        }
        return false;
    }

    @RequestMapping(params = "methodToCall=movePropositionLeft")
    public ModelAndView movePropositionLeft(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        /* Rough algorithm for moving a node up.
         *
         * find the following:
         *   node := the selected node
         *   parent := the selected node's parent, its containing node (via when true or when false relationship)
         *   parentsOlderCousin := the parent's level-order predecessor (sibling or cousin)
         *
         */
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedpropKey = ruleEditor.getSelectedKey();

        // find agendaEditor.getAgendaItemLine().getRule().getPropositionTree().getRootElement()parent
        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedpropKey);
        if ((parent != null) && (RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(parent.getNodeType()))) {
            Node<RuleEditorTreeNode, String> granny = PropositionTreeUtil.findParentPropositionNode(root, parent.getData().getProposition().getKey());
            if (!granny.equals(root)) {
                int oldIndex = findChildIndex(parent, selectedpropKey);
                int newIndex = findChildIndex(granny, parent.getData().getProposition().getKey());
                if (oldIndex >= 0 && newIndex >= 0) {
                    PropositionEditor prop = parent.getData().getProposition().getCompoundEditors().remove(oldIndex / 2);
                    granny.getData().getProposition().getCompoundEditors().add((newIndex / 2) + 1, prop);
                    this.getViewHelper(form).refreshInitTrees(ruleEditor, false);
                    this.getViewHelper(form).setLogicSection(ruleEditor);
                }
            }
            // TODO: do we allow moving up to the root?
            // we could add a new top level compound node, with current root as 1st child,
            // and move the node to the second child.

        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=movePropositionRight")
    public ModelAndView movePropositionRight(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /* Rough algorithm for moving a node Right
         * if the selected node is above a compound proposition, move it into the compound proposition as the first child
         * if the node is above a simple proposition, do nothing.
         * find the following:
         *   node := the selected node
         *   parent := the selected node's parent, its containing node
         *   nextSibling := the node after the selected node
         *
         */
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedpropKey = ruleEditor.getSelectedKey();

        // find parent
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(
                ruleEditor.getEditTree().getRootElement(), selectedpropKey);
        if (parent != null) {
            int index = findChildIndex(parent, selectedpropKey);
            // if we are the last child, do nothing, otherwise
            if (index >= 0 && index + 1 < parent.getChildren().size()) {
                Node<RuleEditorTreeNode, String> nextSibling = parent.getChildren().get(index + 2);
                // if selected node above a compound node, move it into it as first child
                if (RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(nextSibling.getNodeType())) {
                    // remove selected node from it's current spot
                    PropositionEditor prop = parent.getData().getProposition().getCompoundEditors().remove(index / 2);
                    // add it to it's siblings children
                    nextSibling.getData().getProposition().getCompoundEditors().add(0, prop);
                    this.getViewHelper(form).refreshInitTrees(ruleEditor, false);
                    this.getViewHelper(form).setLogicSection(ruleEditor);
                }
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * introduces a new compound proposition between the selected proposition and its parent.
     * Additionally, it puts a new blank simple proposition underneath the compound proposition
     * as a sibling to the selected proposition.
     */
    @RequestMapping(params = "methodToCall=togglePropositionSimpleCompound")
    public ModelAndView togglePropositionSimpleCompound(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropKey = ruleEditor.getSelectedKey();

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        RuleViewHelperService viewHelper = this.getViewHelper(form);

        if (!StringUtils.isBlank(selectedPropKey)) {
            // find parent
            Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(
                    ruleEditor.getEditTree().getRootElement(), selectedPropKey);
            if (parent != null) {

                int index = findChildIndex(parent, selectedPropKey);

                PropositionEditor propBo = parent.getChildren().get(index).getData().getProposition();

                // create a new compound proposition
                PropositionEditor compound = viewHelper.createCompoundPropositionBoStub(propBo, true);
                compound.setDescription(KRMSConstants.PROP_COMP_DEFAULT_DESCR);
                compound.setEditMode(false);

                if (parent.getData() == null) { // SPECIAL CASE: this is the only proposition in the tree
                    ruleEditor.setProposition(compound);
                } else {
                    PropositionEditor parentBo = parent.getData().getProposition();
                    List<PropositionEditor> siblings = parentBo.getCompoundEditors();

                    int propIndex = -1;
                    for (int i = 0; i < siblings.size(); i++) {
                        if (propBo.getId().equals(siblings.get(i).getId())) {
                            propIndex = i;
                            break;
                        }
                    }

                    parentBo.getCompoundEditors().set(propIndex, compound);
                    compound.getCompoundEditors().get(1).setEditMode(true);
                    ruleEditor.setSelectedKey(compound.getCompoundComponents().get(1).getId());
                }
            }
        }

        viewHelper.refreshInitTrees(ruleEditor, false);
        viewHelper.setLogicSection(ruleEditor);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=pasteProposition")
    public ModelAndView pasteProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        boolean cutAction = true;
        RuleEditor ruleEditor = getRuleEditor(form);
        RuleViewHelperService viewHelper = this.getViewHelper(form);

        // get selected id
        String selectedPropKey = ruleEditor.getSelectedKey();
        if (StringUtils.isBlank(selectedPropKey)) {
            return getUIFModelAndView(form);
        }

        // get the id to move
        String movePropKey = ruleEditor.getCutKey();
        if (StringUtils.isBlank(movePropKey)) {
            movePropKey = ruleEditor.getCopyKey();
            cutAction = false;
        }

        // check if selected and move is not the same
        if (StringUtils.isNotBlank(movePropKey) && !selectedPropKey.equals(movePropKey)) {

            Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
            PropositionEditor newParent = getNewParent(viewHelper, ruleEditor, selectedPropKey, root);
            PropositionEditor oldParent = PropositionTreeUtil.findParentPropositionNode(root, movePropKey).getData().getProposition();
            PropositionEditor workingProp = null;

            // cut or copy from old
            if (oldParent != null) {
                List<PropositionEditor> children = oldParent.getCompoundEditors();
                for (int index = 0; index < children.size(); index++) {
                    if (movePropKey.equalsIgnoreCase(children.get(index).getKey())) {
                        if (cutAction) {
                            workingProp = oldParent.getCompoundEditors().remove(index);
                        } else {
                            workingProp = viewHelper.copyProposition(oldParent.getCompoundEditors().get(index));
                        }
                        break;
                    }
                }
            }

            // add to new and refresh the tree
            addProposition(selectedPropKey, newParent, workingProp);
            viewHelper.refreshInitTrees(ruleEditor, false);
            viewHelper.setLogicSection(ruleEditor);
        }

        // call the super method to avoid the agenda tree being reloaded from the db
        return getUIFModelAndView(form);
    }

    private PropositionEditor getNewParent(RuleViewHelperService viewHelper, RuleEditor ruleEditor, String selectedpropKey, Node<RuleEditorTreeNode, String> root) {
        Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(root, selectedpropKey);
        PropositionEditor newParent;
        if (parentNode.equals(root)) {
            // special case
            // build new top level compound proposition,
            // add existing as first child
            // then paste cut node as 2nd child
            newParent = viewHelper.createCompoundPropositionBoStub(
                    root.getChildren().get(0).getData().getProposition(), false);
            newParent.setEditMode(true);
            ruleEditor.setProposition(newParent);
        } else {
            newParent = parentNode.getData().getProposition();
        }
        return newParent;
    }

    private void addProposition(String selectedpropKey, PropositionEditor newParent, PropositionEditor workingProp) {
        // add to new
        if (newParent != null && workingProp != null) {
            List<PropositionEditor> children = newParent.getCompoundEditors();
            for (int index = 0; index < children.size(); index++) {
                if (selectedpropKey.equalsIgnoreCase(children.get(index).getKey())) {
                    children.add(index + 1, workingProp);
                    break;
                }
            }
        }
    }

    @RequestMapping(params = "methodToCall=deleteProposition")
    public ModelAndView deleteProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedpropKey = ruleEditor.getSelectedKey();
        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();

        Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(root, selectedpropKey);

        // what if it is the root?
        if (parentNode != null && parentNode.getData() != null) { // it is not the root as there is a parent w/ a prop
            PropositionEditor parent = parentNode.getData().getProposition();
            if (parent != null) {
                List<PropositionEditor> children = (List<PropositionEditor>) parent.getCompoundComponents();
                for (int index = 0; index < children.size(); index++) {
                    if (selectedpropKey.equalsIgnoreCase(children.get(index).getKey())) {
                        parent.getCompoundComponents().remove(index);
                        break;
                    }
                }
            }
        } else { // no parent, it is the root
            parentNode.getChildren().clear();
            ruleEditor.getEditTree().setRootElement(null);
            ruleEditor.setPropId(null);
            ruleEditor.setProposition(null);
        }

        this.getViewHelper(form).refreshInitTrees(ruleEditor, false);
        this.getViewHelper(form).setLogicSection(ruleEditor);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=updateCompoundOperator")
    public ModelAndView updateCompoundOperator(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleViewHelperService viewHelper = this.getViewHelper(form);
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedpropKey = ruleEditor.getSelectedKey();
        Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(ruleEditor.getEditTree().getRootElement(), selectedpropKey);
        PropositionEditor parent = parentNode.getData().getProposition();

        PropositionEditor proposition = PropositionTreeUtil.findProposition(parentNode, selectedpropKey);
        PropositionTreeUtil.setTypeForCompoundOpCode(parent, proposition.getCompoundOpCode());
        parent.setDescription(viewHelper.getNaturalLanguageDescription(parent));

        viewHelper.refreshInitTrees(ruleEditor, false);
        viewHelper.setLogicSection(ruleEditor);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=updateChanges")
    public ModelAndView updateChanges(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);
        PropositionEditor proposition = (PropositionEditor) ruleEditor.getProposition();
        PropositionTreeUtil.resetNewProp(proposition);

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        this.getViewHelper(form).setLogicSection(ruleEditor);

        return getUIFModelAndView(form);
    }



    @RequestMapping(params = "methodToCall=updatePreview")
    public ModelAndView updatePreview(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleLogicExpressionParser ruleLogicExpressionParser = new RuleLogicExpressionParser();

        RuleEditor ruleEditor = getRuleEditor(form);
        ruleLogicExpressionParser.setExpression(ruleEditor.getLogicArea());
        List<String> propsAlpha = this.getPropositionKeys(new ArrayList<String>(), (PropositionEditor) ruleEditor.getProposition());

        //validate the expression
        List<String> errorMessages = new ArrayList<String>();
        boolean validExpression = ruleLogicExpressionParser.validateExpression(errorMessages, propsAlpha);

        //show errors and don't change anything else
        if (!validExpression) {
            for (int i = 0; i < errorMessages.size(); i++) {
                GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.logicArea", errorMessages.get(i));
            }
            // reload page1
            ruleEditor.setSelectedTab("1");
            return getUIFModelAndView(form);
        }

        ruleEditor.setSelectedTab("1");

        ruleEditor.setProposition(ruleLogicExpressionParser.parseExpressionIntoRule(ruleEditor));
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);

        return getUIFModelAndView(form);
    }

    private List<String> getPropositionKeys(List<String> propositionKeys, PropositionEditor propositionEditor) {
        propositionKeys.add(propositionEditor.getKey());
        if (propositionEditor.getCompoundComponents() != null) {
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                this.getPropositionKeys(propositionKeys, child);
            }
        }
        return propositionKeys;
    }

    @RequestMapping(params = "methodToCall=cancelEdit")
    public ModelAndView cancelEdit(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        PropositionEditor  proposition = (PropositionEditor) ruleEditor.getProposition();

        //Reset the editing tree.
        PropositionTreeUtil.cancelNewProp(proposition);
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        this.getViewHelper(form).refreshInitTrees(ruleEditor, false);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=updateProposition")
    public ModelAndView updateProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PropositionEditor proposition = PropositionTreeUtil.getProposition(this.getRuleEditor(form));
        configureProposition(form, proposition);

        return getUIFModelAndView(form);
    }

    private void configureProposition(UifFormBase form, PropositionEditor proposition) {

        if (proposition != null) {

            if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
                return;
            }

            String propositionTypeId = proposition.getTypeId();
            if (propositionTypeId == null) {
                proposition.setType(null);
                return;
            }

            RuleViewHelperService viewHelper = this.getViewHelper(form);

            KrmsTypeDefinition type = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeById(propositionTypeId);
            if (type != null) {

                proposition.setType(type.getName());

                //Set the default operation and value
                TemplateInfo template = viewHelper.getTemplateForType(type.getName());
                setOperationForProposition(proposition, template.getOperator());

                if (!"n".equals(template.getValue())) {
                    setValueForProposition(proposition, template.getValue());
                } else {
                    setValueForProposition(proposition, "");
                }
            }
        }
    }

    /**
     * Test method for a controller that invokes a dialog lightbox.
     *
     * @param form     - test form
     * @param result   - Spring form binding result
     * @param request  - http request
     * @param response - http response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=compareRules")
    public ModelAndView compareRules(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);

        //Build the compare rule tree
        ruleEditor.setCompareTree(this.getViewHelper(form).buildCompareTree(ruleEditor));

        // redirect back to client to display lightbox
        return showDialog("compareRuleLightBox", form, request, response);
    }

    private void setOperationForProposition(PropositionEditor proposition, String operation) {
        proposition.getParameters().get(2).setValue(operation);
    }

    private void setValueForProposition(PropositionEditor proposition, String value) {
        proposition.getParameters().get(1).setValue(value);
    }

    private RuleViewHelperService getViewHelper(UifFormBase form) {
        return (RuleViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

}
