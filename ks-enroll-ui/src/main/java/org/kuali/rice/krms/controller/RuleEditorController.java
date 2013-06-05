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
package org.kuali.rice.krms.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.AlphaIterator;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.tree.node.KSSimplePropositionEditNode;
import org.kuali.student.enrollment.class1.krms.tree.node.KSSimplePropositionNode;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.util.RuleLogicExpressionParser;
import org.kuali.student.common.uif.util.KSControllerHelper;
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
 * Controller for the KS KRMS page.
 *
 * @author Kuali Student Team
 */
public class RuleEditorController extends MaintenanceDocumentController {

    /**
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=goToRuleView")
    public ModelAndView goToRuleView(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();

        RuleEditor ruleEditor = AgendaUtilities.retrieveSelectedRuleEditor((MaintenanceDocumentForm) form);
        this.getViewHelper(form).refreshInitTrees(ruleEditor);

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KRMS-RuleMaintenance-Page");
        return super.navigate(form, result, request, response);
    }

    /**
     * Deletes selected rule from agenda on Manage Course Requistes page
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=deleteRule")
    public ModelAndView deleteRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper(document);
        String ruleKey = AgendaUtilities.getRuleKey(document);

        AgendaEditor agenda = AgendaUtilities.getSelectedAgendaEditor(ruleWrapper, ruleKey);
        if (agenda != null) {
            RuleEditor ruleEditor = agenda.getRuleEditors().get(ruleKey);

            //Only add rules to delete list that are already persisted.
            if (ruleEditor.getId() != null) {
                agenda.getDeletedRules().add(ruleEditor);
            }

            RuleEditor dummyRule = new RuleEditor(ruleEditor.getKey(), true, ruleEditor.getRuleTypeInfo());
            agenda.getRuleEditors().put(ruleEditor.getKey(), dummyRule);
        }

        return getUIFModelAndView(document);
    }

    /**
     * Navigates to rule maintenance page with rule type to initialize adding of new rule.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=addRule")
    public ModelAndView addRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //Clear the client state on new edit rule.
        form.getClientStateForSyncing().clear();

        RuleEditor ruleEditor = AgendaUtilities.retrieveSelectedRuleEditor((MaintenanceDocumentForm) form);
        ruleEditor.setDummy(false);

        this.getViewHelper(form).refreshInitTrees(ruleEditor);

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KRMS-RuleMaintenance-Page");
        return super.navigate(form, result, request, response);
    }

    /**
     * Call the super method to avoid the agenda tree being reloaded from the db.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=ajaxRefresh")
    public ModelAndView ajaxRefresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return getUIFModelAndView(form);
    }

    /**
     * Retrieves selected rule editor from data object.
     *
     * @param form
     * @return the current rule editor
     */
    protected RuleEditor getRuleEditor(UifFormBase form) {
        if (form instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) form;
            Object dataObject = maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

            if (dataObject instanceof RuleEditor) {
                return (RuleEditor) dataObject;
            } else if (dataObject instanceof RuleManagementWrapper) {
                RuleManagementWrapper wrapper = (RuleManagementWrapper) dataObject;
                return wrapper.getRuleEditor();
            }
        }

        return null;
    }

    //
    // Rule Editor Controller methods
    //

    /**
     * Method to copy rule.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=copyRule")
    public ModelAndView copyRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        //TODO: Copy rule to a different Co or AO

        return super.refresh(form, result, request, response);
    }

    /**
     * This method starts an edit on proposition.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
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
            viewHelper.refreshInitTrees(ruleEditor);
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

    /**
     * Method to initialize the adding of proposition to rule.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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

        //Special case when only one proposition in tree or no proposition selected
        if(selectedPropKey.isEmpty() && parent == null && root.getChildren().size() > 0) {
            //Special case when now proposition selected and more than one proposition in tree
            if(root.getChildren().get(root.getChildren().size() - 1).getNodeType().contains("compoundNode")) {
                parent = root.getChildren().get(root.getChildren().size() - 1);
                selectedPropKey = parent.getChildren().get(parent.getChildren().size() - 1).getData().getProposition().getKey();
            } //Special case when one proposition in tree and no proposition selected
            else {
                parent = root;
                selectedPropKey = root.getChildren().get(root.getChildren().size() - 1).getData().getProposition().getKey();
            }
        } //If root compound proposition selected
        else if(parent != null) {
            if(parent.getNodeType().equals("treeRoot")) {
                parent = root.getChildren().get(root.getChildren().size() - 1);
                selectedPropKey = parent.getChildren().get(parent.getChildren().size() - 1).getData().getProposition().getKey();
            }
        }

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
                    PropositionEditor blank = null;
                    if (parent.equals(root) &&
                            (isSimpleNode(child.getNodeType()))) {

                        // create a new compound proposition
                        blank = viewHelper.createCompoundPropositionBoStub(child.getData().getProposition(), true);
                        blank.setDescription(KRMSConstants.PROP_COMP_DEFAULT_DESCR);
                        // don't set compound.setEditMode(true) as the Simple Prop in the compound prop is the only prop in edit mode
                        ruleEditor.setProposition(blank);
                    }
                    // handle regular case of adding a simple prop to an existing compound prop
                    else if (!parent.equals(root)) {

                        // build new Blank Proposition
                        blank = viewHelper.createSimplePropositionBoStub(child.getData().getProposition());
                        //add it to the parent
                        PropositionEditor parentProp = parent.getData().getProposition();
                        parentProp.getCompoundEditors().add(((index / 2) + 1), blank);
                    } else {
                        return getUIFModelAndView(form);
                    }
                    this.getViewHelper(form).refreshInitTrees(ruleEditor);
                    if (blank != null) {
                        ruleEditor.setSelectedKey(blank.getKey());
                    } else {
                        ruleEditor.setSelectedKey(null);
                    }
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
            }
            this.getViewHelper(form).refreshInitTrees(ruleEditor);
        }
        return getUIFModelAndView(form);
    }

    /**
     * Returns <code>true</code> if proposition key matches tree node key
     *
     * @param node
     * @param propKey
     * @return if proposition key matches tree node key; <code>false</code> otherwise
     */
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

    /**
     * Moves proposition up in tree structure.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=movePropositionUp")
    public ModelAndView movePropositionUp(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        moveSelectedProposition(form, true);

        return getUIFModelAndView(form);
    }

    /**
     * Moves proposition down in tree structure.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=movePropositionDown")
    public ModelAndView movePropositionDown(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        moveSelectedProposition(form, false);

        return getUIFModelAndView(form);
    }

    /**
     * Moves proposition up or down.
     *
     * @param form
     * @param up whether the desired move is in an up direction
     * @throws Exception
     */
    private void moveSelectedProposition(UifFormBase form, boolean up) throws Exception {

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
                        (isSimpleNode(child.getNodeType()) ||
                                (RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(child.getNodeType())) ||
                                (child.getNodeType().contains(RuleEditorTreeNode.FIRST_IN_GROUP)) ||
                                (child.getNodeType().contains(RuleEditorTreeNode.LAST_IN_GROUP)))) {

                    //remove it from its current spot
                    PropositionEditor parentProp = parent.getData().getProposition();
                    PropositionEditor workingProp = null;
                    if (index != 0 && up) {
                        workingProp = parentProp.getCompoundEditors().remove(index / 2);
                    } else if (!up && index != (children.size() - 1)) {
                        workingProp = parentProp.getCompoundEditors().remove(index / 2);
                    }
                    if ((index > 0) && up) {
                        parentProp.getCompoundEditors().add((index / 2) - 1, workingProp);
                    } else if ((index < (children.size() - 1) && !up)) {
                        parentProp.getCompoundEditors().add((index / 2) + 1, workingProp);
                    }
                    // redisplay the tree (editMode = true)
                    this.getViewHelper(form).refreshInitTrees(ruleEditor);
                    break;
                }
            }
        }
        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);

    }

    /**
     * Returns <code>true</code> if node is of type simple
     *
     * @param nodeType
     * @return if node is of type simple; <code>false</code> otherwise
     */
    public boolean isSimpleNode(String nodeType) {
        if (nodeType.contains(KSSimplePropositionNode.NODE_TYPE) ||
                KSSimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(nodeType)) {
            return true;
        }
        return false;
    }

    /**
     * Moves proposition left in tree structure.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
        if ((parent != null) && (!parent.getNodeType().contains(RuleEditorTreeNode.ROOT_TYPE))) {
            Node<RuleEditorTreeNode, String> granny = PropositionTreeUtil.findParentPropositionNode(root, parent.getData().getProposition().getKey());
            if (!granny.equals(root)) {
                int oldIndex = findChildIndex(parent, selectedpropKey);
                int newIndex = findChildIndex(granny, parent.getData().getProposition().getKey());
                if (oldIndex >= 0 && newIndex >= 0) {
                    PropositionEditor prop = parent.getData().getProposition().getCompoundEditors().remove(oldIndex / 2);
                    if(parent.getChildren().size() == 1) {
                        PropositionTreeUtil.removeCompoundProp((PropositionEditor) ruleEditor.getProposition());
                    } else if(parent.getChildren().size() == 3) {
                        GlobalVariables.getMessageMap().putWarning("editWithObjectTree", "warning.krms.tree.compound.single.simple", parent.getData().getProposition().getKey());
                    }
                    granny.getData().getProposition().getCompoundEditors().add((newIndex / 2) + 1, prop);
                    this.getViewHelper(form).refreshInitTrees(ruleEditor);
                }
            }
            // TODO: do we allow moving up to the root?
            // we could add a new top level compound node, with current root as 1st child,
            // and move the node to the second child.

        }
        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);
        return getUIFModelAndView(form);
    }

    /**
     * Move proposition right in tree structure.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
                if (nextSibling.getNodeType().contains(RuleEditorTreeNode.COMPOUND_NODE_TYPE)) {
                    // remove selected node from it's current spot
                    PropositionEditor prop = parent.getData().getProposition().getCompoundEditors().remove(index / 2);
                    // add it to it's siblings children
                    nextSibling.getData().getProposition().getCompoundEditors().add(0, prop);
                    this.getViewHelper(form).refreshInitTrees(ruleEditor);
                }
            }
        }
        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);
        return getUIFModelAndView(form);
    }

    /**
     * Introduces a new compound proposition between the selected proposition and its parent.
     * Additionally, it puts a new blank simple proposition underneath the compound proposition
     * as a sibling to the selected proposition.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
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
                        if (propBo.getKey().equals(siblings.get(i).getKey())) {
                            propIndex = i;
                            break;
                        }
                    }

                    parentBo.getCompoundEditors().set(propIndex, compound);
                    compound.getCompoundEditors().get(1).setEditMode(true);
                }

                viewHelper.refreshInitTrees(ruleEditor);
                ruleEditor.setSelectedKey(compound.getCompoundEditors().get(1).getKey());

            }
        }

        return getUIFModelAndView(form);
    }

    /**
     * Paste proposition in selected position in tree structure.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
                            if (oldParent.getCompoundEditors().size() == 1) {
                                int i = ruleEditor.getPropositionEditor().getCompoundEditors().indexOf(oldParent);
                                ruleEditor.getPropositionEditor().getCompoundEditors().set(i, oldParent.getCompoundEditors().get(0));
                            }
                        } else {
                            workingProp = viewHelper.copyProposition(oldParent.getCompoundEditors().get(index));
                        }
                        break;
                    }
                }
            }

            // add to new and refresh the tree
            addProposition(selectedPropKey, newParent, workingProp);
            viewHelper.refreshInitTrees(ruleEditor);
        } else if (StringUtils.isNotBlank(movePropKey) && !cutAction && ruleEditor.getPropositionEditor().getCompoundEditors() != null) {
            Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
            PropositionEditor newParent = getNewParent(viewHelper, ruleEditor, selectedPropKey, root);
            PropositionEditor oldParent = PropositionTreeUtil.findParentPropositionNode(root, movePropKey).getData().getProposition();

            PropositionEditor workingProp = null;

            // copy from old
            if (oldParent != null) {
                List<PropositionEditor> children = oldParent.getCompoundEditors();
                for (int index = 0; index < children.size(); index++) {
                    if (movePropKey.equalsIgnoreCase(children.get(index).getKey())) {
                        workingProp = viewHelper.copyProposition(oldParent.getCompoundEditors().get(index));
                        break;
                    }
                }
            }

            // add to new and refresh the tree
            addProposition(selectedPropKey, newParent, workingProp);
            viewHelper.refreshInitTrees(ruleEditor);
        } else if (StringUtils.isNotBlank(movePropKey) && !cutAction && ruleEditor.getProposition().getPropositionTypeCode().equals("S")) {
            PropositionEditor newParent = viewHelper.createCompoundPropositionBoStub(ruleEditor.getPropositionEditor(), false);
            newParent.setDescription(KRMSConstants.PROP_COMP_DEFAULT_DESCR);
            newParent.setEditMode(false);
            PropositionEditor workingProp = viewHelper.copyProposition(ruleEditor.getPropositionEditor());

            // add to new and refresh the tree
            addProposition(selectedPropKey, newParent, workingProp);
            ruleEditor.setProposition(newParent);
            viewHelper.refreshInitTrees(ruleEditor);
        }

        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);

        // call the super method to avoid the agenda tree being reloaded from the db
        return getUIFModelAndView(form);
    }

    /**
     * Returns new parent for selected proposition in tree structure.     *
     *
     * @param viewHelper
     * @param ruleEditor
     * @param selectedpropKey
     * @param root
     * @return
     */
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

    /**
     * Adds proposition at selected position.
     *
     * @param selectedpropKey
     * @param newParent
     * @param workingProp
     */
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

    /**
     * Removes proposition.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=deleteProposition")
    public ModelAndView deleteProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedpropKey = ruleEditor.getSelectedKey();
        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();

        Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(root, selectedpropKey);

        // what if it is the root?
        if(parentNode.getNodeType().contains("treeRoot") && parentNode.getChildren().size() == 1) {
            parentNode.getChildren().clear();
            ruleEditor.getEditTree().setRootElement(null);
            ruleEditor.setPropId(null);
            ruleEditor.setProposition(null);
            ruleEditor.setAlpha(new AlphaIterator());
            ruleEditor.setSelectedKey(StringUtils.EMPTY);
        } else if (parentNode != null && parentNode.getData() != null) { // it is not the root as there is a parent w/ a prop
            PropositionEditor parent = parentNode.getData().getProposition();
            if (parent != null) {
                List<PropositionEditor> children = (List<PropositionEditor>) parent.getCompoundComponents();
                for (int index = 0; index < children.size(); index++) {
                    if (selectedpropKey.equalsIgnoreCase(children.get(index).getKey())) {
                        parent.getCompoundComponents().remove(index);
                        if(parent.getCompoundEditors().isEmpty()) {
                            PropositionTreeUtil.removeCompoundProp(ruleEditor.getPropositionEditor());
                        } else if(parent.getCompoundEditors().size() == 1) {
                            GlobalVariables.getMessageMap().putWarning("editWithObjectTree", "warning.krms.tree.compound.single.simple", parent.getKey());
                        }
                        break;
                    }
                }
            }
        } else { // no parent, it is the root
            parentNode.getChildren().clear();
            ruleEditor.getEditTree().setRootElement(null);
            ruleEditor.setPropId(null);
            ruleEditor.setProposition(null);
            ruleEditor.setAlpha(new AlphaIterator());
            ruleEditor.setSelectedKey(StringUtils.EMPTY);
        }

        this.getViewHelper(form).refreshInitTrees(ruleEditor);
        return getUIFModelAndView(form);
    }

    /**
     * Updates compound operator in tree structure.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
        parent.setDescription(viewHelper.resetDescription(parent));

        viewHelper.refreshInitTrees(ruleEditor);

        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);

        return getUIFModelAndView(form);
    }

    /**
     * Updates rule with new or changed propositions.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updateProposition")
    public ModelAndView updateProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);

        if (ruleEditor.getProposition() != null) {
            PropositionTreeUtil.resetNewProp(ruleEditor.getPropositionEditor());
        }

        //Reset the description on current selected proposition
        PropositionEditor proposition = PropositionTreeUtil.getProposition(ruleEditor);
        if (proposition != null) {
            this.getViewHelper(form).resetDescription(proposition);
        }
        if (!GlobalVariables.getMessageMap().getErrorMessages().isEmpty()) {
            return getUIFModelAndView(form);
        }
        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);

        //Remove the edit mode
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        this.getViewHelper(form).refreshInitTrees(ruleEditor);

            return getUIFModelAndView(form);
    }

    private void compareRulePropositions(MaintenanceDocumentForm form, RuleEditor ruleEditor) throws Exception {

        RuleManagementWrapper ruleWrapper = (RuleManagementWrapper) form.getDocument().getNewMaintainableObject().getDataObject();

        //Compare CO to CLU and display info message
        if(ruleEditor.getProposition() != null) {
            if(!this.getViewHelper(form).compareRules(ruleWrapper.getRuleEditor(), ruleWrapper.getRefObjectId())) {
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, "info.krms.tree.rule.changed");
            } else if(GlobalVariables.getMessageMap().containsMessageKey(KRADConstants.GLOBAL_INFO)){
                GlobalVariables.getMessageMap().removeAllInfoMessagesForProperty(KRADConstants.GLOBAL_INFO);
            }
        }
    }

    /**
     * Updates rule and redirects to agenda maintenance page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updateRule")
    public ModelAndView updateRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        if (!(ruleEditor.getProposition() == null && ruleEditor.getPropId() == null)) {
            PropositionTreeUtil.resetNewProp(ruleEditor.getPropositionEditor());

            if (PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor)) {
                PropositionEditor proposition = PropositionTreeUtil.getProposition(ruleEditor);
                this.getViewHelper(form).resetDescription(proposition);
            }
        }
        this.getViewHelper(form).refreshViewTree(ruleEditor);

        //Replace edited rule with existing rule.
        RuleManagementWrapper ruleWrapper = AgendaUtilities.getRuleWrapper((MaintenanceDocumentForm) form);
        AgendaEditor agendaEditor = AgendaUtilities.getSelectedAgendaEditor(ruleWrapper, ruleEditor.getKey());
        agendaEditor.getRuleEditors().put(ruleEditor.getKey(), ruleEditor);

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KRMS-AgendaMaintenance-Page");
        return super.navigate(form, result, request, response);
    }

    /**
     * Updates view with changed logic expressions.
     * Also does validation and displays necessary messages on view.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updatePreview")
    public ModelAndView updatePreview(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);
        parseRuleExpression(ruleEditor);

        this.getViewHelper(form).refreshInitTrees(ruleEditor);
        return getUIFModelAndView(form);
    }

    /**
     * Validation for logic expression.
     *
     * @param ruleEditor
     */
    private void parseRuleExpression(RuleEditor ruleEditor) {
        RuleLogicExpressionParser ruleLogicExpressionParser = new RuleLogicExpressionParser();
        ruleLogicExpressionParser.setExpression(ruleEditor.getLogicArea());
        List<String> propsAlpha = this.getPropositionKeys(new ArrayList<String>(), ruleEditor.getPropositionEditor());

        //validate the expression
        List<String> errorMessages = new ArrayList<String>();
        boolean validExpression = ruleLogicExpressionParser.validateExpression(errorMessages, propsAlpha);

        //show errors and don't change anything else
        if (!validExpression) {
            for (int i = 0; i < errorMessages.size(); i++) {
                GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.logicArea", "error.krms.rule.logic", errorMessages.get(i));
            }
            // reload page1
            return;
        }

        ruleEditor.setProposition(ruleLogicExpressionParser.parseExpressionIntoRule(ruleEditor));
    }

    /**
     * Returns list of proposition keys.
     *
     * @param propositionKeys
     * @param propositionEditor
     * @return
     */
    private List<String> getPropositionKeys(List<String> propositionKeys, PropositionEditor propositionEditor) {
        propositionKeys.add(propositionEditor.getKey());
        if (propositionEditor.getCompoundComponents() != null) {
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                this.getPropositionKeys(propositionKeys, child);
            }
        }
        return propositionKeys;
    }

    /**
     * Reverts rule to previous state and refreshes view.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=cancelEditProposition")
    public ModelAndView cancelEditProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        PropositionEditor proposition = ruleEditor.getPropositionEditor();

        //If first proposition and not yet updated, clear rule proposition
        if(proposition.getPropositionTypeCode().equals("S") && proposition.getCompoundEditors() == null && proposition.getTerm().getParameters() == null) {
            ruleEditor.setProposition(null);
            ruleEditor.setSelectedKey(StringUtils.EMPTY);
            ruleEditor.setAlpha(new AlphaIterator());
            form.getView().setOnDocumentReadyScript("loadControlsInit();");
        } else {
            //Reset the editing tree.
            ruleEditor.setSelectedKey(StringUtils.EMPTY);
            PropositionTreeUtil.cancelNewProp(proposition);
            PropositionTreeUtil.removeCompoundProp(proposition);
        }

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        this.getViewHelper(form).refreshInitTrees(ruleEditor);

        //Compare rule with parent rule.
        compareRulePropositions((MaintenanceDocumentForm) form, ruleEditor);

        return getUIFModelAndView(form);
    }

    /**
     * Reverts rule to previous state and navigates to agenda maintenance page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=cancelEditRule")
    public ModelAndView cancelEditRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        PropositionEditor proposition = ruleEditor.getPropositionEditor();

        //Reset the editing tree.
        if (proposition != null) {
            PropositionTreeUtil.cancelNewProp(proposition);
        }
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, "KRMS-AgendaMaintenance-Page");
        return super.navigate(form, result, request, response);
    }

    /**
     * Updates proposition type and reloads view.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updatePropositionType")
    public ModelAndView updatePropositionType(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PropositionEditor proposition = PropositionTreeUtil.getProposition(this.getRuleEditor(form));
        this.getViewHelper(form).configurePropositionForType(proposition);

        return getUIFModelAndView(form);
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

        MaintenanceDocumentForm document = (MaintenanceDocumentForm) form;
        Object dataObject = document.getDocument().getNewMaintainableObject().getDataObject();
        if (dataObject instanceof RuleManagementWrapper) {
            RuleManagementWrapper ruleWrapper = (RuleManagementWrapper) dataObject;
            String ruleId = document.getActionParamaterValue("ruleKey");
            RuleEditor ruleEditor = null;
            if ((ruleId != null) && (StringUtils.isNotBlank(ruleId))) {
                //Get a specific ruleEditor based on the ruleId.
                ruleEditor = AgendaUtilities.getSelectedRuleEditor(ruleWrapper, ruleId);
            } else {
                //Get the current editing ruleEditor.
                ruleEditor = ruleWrapper.getRuleEditor();
            }

            //Build the compare rule tree
            ruleWrapper.setCompareTree(this.getViewHelper(form).buildCompareTree(ruleEditor, ruleWrapper.getRefObjectId()));
            ruleWrapper.setCompareLightBoxHeader(ruleEditor.getRuleTypeInfo().getDescription());
        }

        // redirect back to client to display lightbox
        return showDialog("compareRuleLightBox", form, request, response);
    }

    /**
     * Returns form's view helper serivce.
     *
     * @param form
     * @return
     */
    protected RuleViewHelperService getViewHelper(UifFormBase form) {
        return (RuleViewHelperService) KSControllerHelper.getViewHelperService(form);
    }

    /**
     * Retrieves selected proposition key and initializes edit on propostion.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getSelectedKey")
    public ModelAndView getSelectedKey(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleViewHelperService viewHelper = this.getViewHelper(form);
        String selectedKey = request.getParameter("selectedKey");

        RuleEditor ruleEditor = getRuleEditor(form);
        ruleEditor.setSelectedKey(selectedKey);
        //this.goToEditProposition()
        return this.goToEditProposition(form, result, request, response);
    }

}
