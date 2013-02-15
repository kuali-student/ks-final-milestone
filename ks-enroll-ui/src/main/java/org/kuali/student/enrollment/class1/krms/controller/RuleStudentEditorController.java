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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.rule.AgendaEditorBusRule;
import org.kuali.student.enrollment.class1.krms.dto.KSSimplePropositionEditNode;
import org.kuali.student.enrollment.class1.krms.dto.KSSimplePropositionNode;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditorTreeNode;
import org.kuali.student.enrollment.class1.krms.service.RuleViewHelperService;
import org.kuali.student.enrollment.class1.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
import org.kuali.student.krms.KRMSConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/**
 * Controller for the Test UI Page
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Controller
@RequestMapping(value = KRMSConstants.WebPaths.RULE_STUDENT_EDITOR_PATH)
public class RuleStudentEditorController extends MaintenanceDocumentController {

    private SequenceAccessorService sequenceAccessorService;

    /**
     * This method updates the existing rule in the agenda.
     */
    @RequestMapping(params = "methodToCall=editRule")
    public ModelAndView editRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);

        RuleViewHelperService viewHelper = (RuleViewHelperService) KSControllerHelper.getViewHelperService(form);
        if (!viewHelper.validateProposition(ruleEditor.getRule().getProposition(), ruleEditor.getRule().getNamespace())) {
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

    /**
     * Updates to the category call back to this method to set the categoryId appropriately
     * TODO: shouldn't this happen automatically?  We're taking it out of the form by hand here
     */
    @RequestMapping(params = "methodToCall=ajaxCategoryChangeRefresh")
    public ModelAndView ajaxCategoryChangeRefresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String categoryParamName = null;
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            if (paramName.endsWith("categoryId")) {
                categoryParamName = paramName;
                break;
            }
        }

        if (categoryParamName != null) {
            String categoryId = request.getParameter(categoryParamName);

            if (StringUtils.isBlank(categoryId)) {
                categoryId = null;
            }

            RuleEditor ruleEditor = getRuleEditor(form);
            String selectedPropId = ruleEditor.getSelectedPropositionId();

            // TODO: This should work even if the prop isn't selected!!!  Find the node in edit mode?
            if (!StringUtils.isBlank(selectedPropId)) {
                Node<RuleEditorTreeNode, String> selectedPropositionNode =
                        PropositionTreeUtil.findPropositionTreeNode(ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
                selectedPropositionNode.getData().getProposition().getProposition().setCategoryId(categoryId);
            }
        }

        return ajaxRefresh(form, result, request, response);
    }

    //
    // Rule Editor Controller methods
    //
    @RequestMapping(params = "methodToCall=copyRule")
    public ModelAndView copyRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        String name = ruleEditor.getCopyRuleName();
        String namespace = ruleEditor.getNamespace();
        // fetch existing rule and copy fields to new rule
        RuleDefinition oldRuleDefinition = getRuleBoService().getRuleByNameAndNamespace(name, namespace);
        RuleBo oldRule = RuleBo.from(oldRuleDefinition);
        RuleBo newRule = RuleBo.copyRule(oldRule);
        ruleEditor.setRule(newRule);

        return super.refresh(form, result, request, response);
    }

    /**
     * This method starts an edit proposition.
     */
    @RequestMapping(params = "methodToCall=goToEditProposition")
    public ModelAndView goToEditProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // open the selected node for editing
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();
        PropositionBo propositionToToggleEdit = null;
        boolean newEditMode = true;

        // find parent
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);
        if (parent != null) {
            List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
            for (int index = 0; index < children.size(); index++) {
                Node<RuleEditorTreeNode, String> child = children.get(index);
                if (propIdMatches(child, selectedPropId)) {
                    PropositionBo prop = child.getData().getProposition().getProposition();
                    propositionToToggleEdit = prop;
                    newEditMode = !prop.getEditMode();
                    break;
                } else {
                    child.getData().getProposition().getProposition().setEditMode(false);
                }
            }
        }

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        if (propositionToToggleEdit != null) {
            propositionToToggleEdit.setEditMode(newEditMode);
            //refresh the tree
            ruleEditor.refreshPropositionTree();
        }

        PropositionEditor proposition = PropositionTreeUtil.getProposition(ruleEditor);
        if (!PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getProposition().getPropositionTypeCode())) {

            String propositionTypeId = proposition.getProposition().getTypeId();
            if (propositionTypeId == null) {
                proposition.setType(null);
                proposition.setTermSpecId(null);
            } else {

                RuleViewHelperService viewHelper = (RuleViewHelperService) KSControllerHelper.getViewHelperService(form);

                KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeById(propositionTypeId);
                if (type != null) {
                    proposition.setType(type.getName());

                    //Set the term spec
                    String termSpecId = viewHelper.getTermSpecIdForType(type.getName());
                    proposition.setTermSpecId(termSpecId);

                }
            }

        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addProposition")
    public ModelAndView addProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        RuleBo rule = ruleEditor.getRule();
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        // find parent
        Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);

        // add new child at appropriate spot
        if (parent != null) {
            List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
            for (int index = 0; index < children.size(); index++) {
                Node<RuleEditorTreeNode, String> child = children.get(index);

                // if our selected node is a simple proposition, add a new one after
                if (propIdMatches(child, selectedPropId)) {
                    // handle special case of adding to a lone simple proposition.
                    // in this case, we need to change the root level proposition to a compound proposition
                    // move the existing simple proposition as the first compound component,
                    // then add a new blank simple prop as the second compound component.
                    if (parent == root &&
                            (KSSimplePropositionNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                                    KSSimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()))) {

                        // create a new compound proposition
                        PropositionBo compound = PropositionBo.createCompoundPropositionBoStub(child.getData().getProposition().getProposition(), true);
                        compound.setDescription(KRMSConstants.PROP_COMP_DEFAULT_DESCR);
                        // don't set compound.setEditMode(true) as the Simple Prop in the compound prop is the only prop in edit mode
                        rule.setProposition(compound);
                    }
                    // handle regular case of adding a simple prop to an existing compound prop
                    else if (KSSimplePropositionNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                            KSSimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType())) {

                        // build new Blank Proposition
                        PropositionBo blank = PropositionBo.createSimplePropositionBoStub(child.getData().getProposition().getProposition(), PropositionType.SIMPLE.getCode());
                        //add it to the parent
                        PropositionBo parentProp = parent.getData().getProposition().getProposition();
                        parentProp.getCompoundComponents().add(((index / 2) + 1), blank);
                    }
                    ruleEditor.refreshPropositionTree();
                    break;
                }
            }
        } else {
            // special case, if root has no children, add a new simple proposition
            // todo: how to add compound proposition. - just add another to the firs simple
            if (root.getChildren().isEmpty()) {
                PropositionBo blank = PropositionBo.createSimplePropositionBoStub(null, PropositionType.SIMPLE.getCode());
                blank.setRuleId(rule.getId());
                rule.setPropId(blank.getId());
                rule.setProposition(blank);
                ruleEditor.refreshPropositionTree();
            }
        }
        return getUIFModelAndView(form);
    }

    private boolean propIdMatches(Node<RuleEditorTreeNode, String> node, String propId) {
        if (propId != null && node != null && node.getData() != null && propId.equalsIgnoreCase(node.getData().getProposition().getProposition().getId())) {
            return true;
        }
        return false;
    }

    /**
     * This method return the index of the position of the child that matches the id
     *
     * @param parent
     * @param propId
     * @return index if found, -1 if not found
     */
    private int findChildIndex(Node<RuleEditorTreeNode, String> parent, String propId) {
        int index;
        List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
        for (index = 0; index < children.size(); index++) {
            Node<RuleEditorTreeNode, String> child = children.get(index);
            // if our selected node is a simple proposition, add a new one after
            if (propIdMatches(child, propId)) {
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
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        // find parent
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(ruleEditor.getPropositionTree().getRootElement(), selectedPropId);

        // add new child at appropriate spot
        if (parent != null) {
            List<Node<RuleEditorTreeNode, String>> children = parent.getChildren();
            for (int index = 0; index < children.size(); index++) {
                Node<RuleEditorTreeNode, String> child = children.get(index);
                // if our selected node is a simple proposition, add a new one after
                if (propIdMatches(child, selectedPropId)) {
                    if (KSSimplePropositionNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                            KSSimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                            RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(child.getNodeType())) {

                        if (((index > 0) && up) || ((index < (children.size() - 1) && !up))) {
                            //remove it from its current spot
                            PropositionBo parentProp = parent.getData().getProposition().getProposition();
                            PropositionBo workingProp = parentProp.getCompoundComponents().remove(index / 2);
                            if (up) {
                                parentProp.getCompoundComponents().add((index / 2) - 1, workingProp);
                            } else {
                                parentProp.getCompoundComponents().add((index / 2) + 1, workingProp);
                            }

                            // insert it in the new spot
                            // redisplay the tree (editMode = true)
                            ruleEditor.refreshPropositionTree();
                        }
                    }

                    break;
                }
            }
        }
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
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        // find agendaEditor.getAgendaItemLine().getRule().getPropositionTree().getRootElement()parent
        Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);
        if ((parent != null) && (RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(parent.getNodeType()))) {
            Node<RuleEditorTreeNode, String> granny = PropositionTreeUtil.findParentPropositionNode(root, parent.getData().getProposition().getProposition().getId());
            if (granny != root) {
                int oldIndex = findChildIndex(parent, selectedPropId);
                int newIndex = findChildIndex(granny, parent.getData().getProposition().getProposition().getId());
                if (oldIndex >= 0 && newIndex >= 0) {
                    PropositionBo prop = parent.getData().getProposition().getProposition().getCompoundComponents().remove(oldIndex / 2);
                    granny.getData().getProposition().getProposition().getCompoundComponents().add((newIndex / 2) + 1, prop);
                    ruleEditor.refreshPropositionTree();
                }
            } else {
                // TODO: do we allow moving up to the root?
                // we could add a new top level compound node, with current root as 1st child,
                // and move the node to the second child.
            }
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
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        // find parent
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(
                ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
        if (parent != null) {
            int index = findChildIndex(parent, selectedPropId);
            // if we are the last child, do nothing, otherwise
            if (index >= 0 && index + 1 < parent.getChildren().size()) {
                Node<RuleEditorTreeNode, String> child = parent.getChildren().get(index);
                Node<RuleEditorTreeNode, String> nextSibling = parent.getChildren().get(index + 2);
                // if selected node above a compound node, move it into it as first child
                if (RuleEditorTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(nextSibling.getNodeType())) {
                    // remove selected node from it's current spot
                    PropositionBo prop = parent.getData().getProposition().getProposition().getCompoundComponents().remove(index / 2);
                    // add it to it's siblings children
                    nextSibling.getData().getProposition().getProposition().getCompoundComponents().add(0, prop);
                    ruleEditor.refreshPropositionTree();
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
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);

        if (!StringUtils.isBlank(selectedPropId)) {
            // find parent
            Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(
                    ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
            if (parent != null) {

                int index = findChildIndex(parent, selectedPropId);

                PropositionBo propBo = parent.getChildren().get(index).getData().getProposition().getProposition();

                // create a new compound proposition
                PropositionBo compound = PropositionBo.createCompoundPropositionBoStub(propBo, true);
                compound.setDescription(KRMSConstants.PROP_COMP_DEFAULT_DESCR);
                compound.setEditMode(false);

                if (parent.getData() == null) { // SPECIAL CASE: this is the only proposition in the tree
                    ruleEditor.getRule().setProposition(compound);
                } else {
                    PropositionBo parentBo = parent.getData().getProposition().getProposition();
                    List<PropositionBo> siblings = parentBo.getCompoundComponents();

                    int propIndex = -1;
                    for (int i = 0; i < siblings.size(); i++) {
                        if (propBo.getId().equals(siblings.get(i).getId())) {
                            propIndex = i;
                            break;
                        }
                    }

                    parentBo.getCompoundComponents().set(propIndex, compound);
                    compound.getCompoundComponents().get(1).setEditMode(true);
                    ruleEditor.setSelectedPropositionId(compound.getCompoundComponents().get(1).getId());
                }
            }
        }

        ruleEditor.refreshPropositionTree();
        ruleEditor.initPreviewTree();
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=cutProposition")
    public ModelAndView cutProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropId = ruleEditor.getSelectedPropositionId();
        ruleEditor.setCutPropositionId(selectedPropId);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=copyProposition")
    public ModelAndView copyProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropId = ruleEditor.getSelectedPropositionId();
        ruleEditor.setCopyPropositionId(selectedPropId);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=pasteProposition")
    public ModelAndView pasteProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        RuleBo rule = ruleEditor.getRule();

        // get selected id
        String cutPropId = ruleEditor.getCutPropositionId();
        String copyPropId = ruleEditor.getCopyPropositionId();
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        if (StringUtils.isNotBlank(selectedPropId) && selectedPropId.equals(cutPropId)) {
            // do nothing; can't paste to itself
        } else if(StringUtils.isNotBlank(cutPropId)) {

            // proposition tree root
            Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();

            if (StringUtils.isNotBlank(selectedPropId) && StringUtils.isNotBlank(cutPropId)) {
                Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);
                PropositionBo newParent;
                if (parentNode == root) {
                    // special case
                    // build new top level compound proposition,
                    // add existing as first child
                    // then paste cut node as 2nd child
                    newParent = PropositionBo.createCompoundPropositionBoStub2(
                            root.getChildren().get(0).getData().getProposition().getProposition());
                    newParent.setEditMode(true);
                    rule.setProposition(newParent);
                } else {
                    newParent = parentNode.getData().getProposition().getProposition();
                }
                PropositionBo oldParent = PropositionTreeUtil.findParentPropositionNode(root, cutPropId).getData().getProposition().getProposition();

                PropositionBo workingProp = null;
                // cut from old
                if (oldParent != null) {
                    List<PropositionBo> children = oldParent.getCompoundComponents();
                    for (int index = 0; index < children.size(); index++) {
                        if (cutPropId.equalsIgnoreCase(children.get(index).getId())) {
                            workingProp = oldParent.getCompoundComponents().remove(index);
                            break;
                        }
                    }
                }

                // add to new
                if (newParent != null && workingProp != null) {
                    List<PropositionBo> children = newParent.getCompoundComponents();
                    for (int index = 0; index < children.size(); index++) {
                        if (selectedPropId.equalsIgnoreCase(children.get(index).getId())) {
                            children.add(index + 1, workingProp);
                            break;
                        }
                    }
                }
                ruleEditor.refreshPropositionTree();
            }
        }  else if(StringUtils.isNotBlank(copyPropId)) {

            // proposition tree root
            Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();

            if (StringUtils.isNotBlank(selectedPropId) && StringUtils.isNotBlank(copyPropId)) {
                Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);
                PropositionBo newParent;
                if (parentNode == root) {
                    // special case
                    // build new top level compound proposition,
                    // add existing as first child
                    // then paste cut node as 2nd child
                    newParent = PropositionBo.createCompoundPropositionBoStub2(
                            root.getChildren().get(0).getData().getProposition().getProposition());
                    newParent.setEditMode(true);
                    rule.setProposition(newParent);
                } else {
                    newParent = parentNode.getData().getProposition().getProposition();
                }
                PropositionBo oldParent = PropositionTreeUtil.findParentPropositionNode(root, copyPropId).getData().getProposition().getProposition();

                PropositionBo workingProp = null;
                // get from old
                if (oldParent != null) {
                    List<PropositionBo> children = oldParent.getCompoundComponents();
                    for (int index = 0; index < children.size(); index++) {
                        if (copyPropId.equalsIgnoreCase(children.get(index).getId())) {
                            workingProp = oldParent.getCompoundComponents().get(index);
                            break;
                        }
                    }
                }

                // add to new
                if (newParent != null && workingProp != null) {
                    List<PropositionBo> children = newParent.getCompoundComponents();
                    for (int index = 0; index < children.size(); index++) {
                        if (selectedPropId.equalsIgnoreCase(children.get(index).getId())) {
                            children.add(index + 1, workingProp);
                            break;
                        }
                    }
                }
                ruleEditor.refreshPropositionTree();
            }
        }
        ruleEditor.setCutPropositionId(null);
        ruleEditor.setCopyPropositionId(null);
        // call the super method to avoid the agenda tree being reloaded from the db
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=deleteProposition")
    public ModelAndView deleteProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropId = ruleEditor.getSelectedPropositionId();
        Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();

        Node<RuleEditorTreeNode, String> parentNode = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);

        // what if it is the root?
        if (parentNode != null && parentNode.getData() != null) { // it is not the root as there is a parent w/ a prop
            PropositionEditor parent = parentNode.getData().getProposition();
            if (parent != null) {
                List<PropositionBo> children = parent.getProposition().getCompoundComponents();
                for (int index = 0; index < children.size(); index++) {
                    if (selectedPropId.equalsIgnoreCase(children.get(index).getId())) {
                        parent.getProposition().getCompoundComponents().remove(index);
                        break;
                    }
                }
            }
        } else { // no parent, it is the root
            parentNode.getChildren().clear();
            ruleEditor.getPropositionTree().setRootElement(null);
            ruleEditor.getRule().setPropId(null);
            ruleEditor.getRule().setProposition(null);
        }

        ruleEditor.refreshPropositionTree();
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=updateCompoundOperator")
    public ModelAndView updateCompoundOperator(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        ruleEditor.refreshPropositionTree();

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=retrieveRule")
    public ModelAndView retrieveRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleBo rule = null;
        RuleEditor ruleEditor = getRuleEditor(form);

        ruleEditor.clearRule();
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);

        if (ruleEditor.getAgenda() != null) {
            List<AgendaItemBo> agendaItems = ruleEditor.getAgenda().getItems();
            for (AgendaItemBo agendaItem : agendaItems) {

                if ((agendaItem.getRule() != null) && (agendaItem.getRule().getTypeId().equals(ruleEditor.getRuleType()))) {
                    rule = agendaItem.getRule();
                    break;
                }

            }
        }

        if (rule == null) {
            rule = new RuleBo();
            rule.setTypeId(ruleEditor.getRuleType());
        }

        ruleEditor.setRule(rule);
        ruleEditor.initPreviewTree();
        return getUIFModelAndView(form);

    }

    @RequestMapping(params = "methodToCall=updatePreview")
    public ModelAndView updatePreview(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);

        //Update the rule preview
        ruleEditor.initPreviewTree();

        //Reset the editing tree.
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        ruleEditor.refreshPropositionTree();

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=cancelEdit")
    public ModelAndView cancelEdit(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);

        //Reset the editing tree.
        PropositionTreeUtil.resetEditModeOnPropositionTree(ruleEditor);
        ruleEditor.refreshPropositionTree();

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

        if ((proposition != null) && (proposition.getProposition() != null)) {

            if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getProposition().getPropositionTypeCode())) {
                return;
            }

            String propositionTypeId = proposition.getProposition().getTypeId();
            if (propositionTypeId == null) {
                proposition.setType(null);
                proposition.setTermSpecId(null);
                return;
            }

            RuleViewHelperService viewHelper = (RuleViewHelperService) KSControllerHelper.getViewHelperService(form);

            KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeById(propositionTypeId);
            if (type != null) {

                proposition.setType(type.getName());
                proposition.getProposition().setDescription(viewHelper.getTranslatedNaturalLanguage(propositionTypeId));

                //Set the term spec
                String termSpecId = viewHelper.getTermSpecIdForType(type.getName());
                proposition.setTermSpecId(termSpecId);

                //Set the operation
                setOperationForProposition(proposition, viewHelper.getOperationForType(type.getName()));

                //Set the value
                String defaultValue = viewHelper.getValueForType(type.getName());
                if (!"n".equals(defaultValue)) {
                    setValueForProposition(proposition, defaultValue);
                } else {
                    setValueForProposition(proposition, "");
                }

            }

        }
    }

    private void setOperationForProposition(PropositionEditor proposition, String operation) {
        proposition.getProposition().getParameters().get(2).setValue(operation);
    }

    private void setValueForProposition(PropositionEditor proposition, String value) {
        proposition.getProposition().getParameters().get(1).setValue(value);
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    /**
     * return the contextBoService
     */
    private RuleBoService getRuleBoService() {
        return KrmsRepositoryServiceLocator.getRuleBoService();
    }

}
