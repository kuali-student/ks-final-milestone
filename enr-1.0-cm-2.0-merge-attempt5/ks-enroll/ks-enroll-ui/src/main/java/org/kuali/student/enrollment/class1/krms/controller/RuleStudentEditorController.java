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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.KrmsApiServiceLocator;
import org.kuali.rice.krms.api.engine.expression.ComparisonOperatorService;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.ActionBo;
import org.kuali.rice.krms.impl.repository.AgendaBo;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBo;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.impl.rule.AgendaEditorBusRule;
import org.kuali.rice.krms.impl.ui.CompoundOpCodeNode;
import org.kuali.rice.krms.impl.ui.RuleTreeNode;
import org.kuali.rice.krms.impl.ui.SimplePropositionEditNode;
import org.kuali.rice.krms.impl.ui.SimplePropositionNode;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.rice.krms.impl.util.KrmsImplConstants;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditorTreeNode;
import org.kuali.student.enrollment.class1.krms.dto.StudentAgendaEditor;
import org.kuali.student.enrollment.class1.krms.service.RuleStudentViewHelperService;
import org.kuali.student.enrollment.class1.krms.service.impl.AgendaStudentEditorMaintainableImpl;
import org.kuali.student.enrollment.class1.krms.service.impl.RuleStudentViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.krms.util.PropositionTreeUtil;
import org.kuali.student.krms.KRMSConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * This overridden method does extra work on refresh to update the namespace when the context has been changed.
     *
     * @see org.kuali.rice.krad.web.controller.UifControllerBase#refresh(org.kuali.rice.krad.web.form.UifFormBase, org.springframework.validation.BindingResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(params = "methodToCall=" + "refresh")
    @Override
    public ModelAndView refresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = super.refresh(form, result, request, response);

        // handle return from context lookup
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) form;
        StudentAgendaEditor agendaEditor = ((StudentAgendaEditor) maintenanceForm.getDocument().getNewMaintainableObject().getDataObject());
        AgendaEditorBusRule rule = new AgendaEditorBusRule();
        if (rule.validContext(agendaEditor) && rule.validAgendaName(agendaEditor)) {
            // update the namespace on all agenda related objects if the contest has been changed
            if (!StringUtils.equals(agendaEditor.getOldContextId(), agendaEditor.getAgenda().getContextId())) {
                agendaEditor.setOldContextId(agendaEditor.getAgenda().getContextId());

                String namespace = "";
                if (!StringUtils.isBlank(agendaEditor.getAgenda().getContextId())) {
                    namespace = getContextBoService().getContextByContextId(agendaEditor.getAgenda().getContextId()).getNamespace();
                }

                for (AgendaItemBo agendaItem : agendaEditor.getAgenda().getItems()) {
                    agendaItem.getRule().setNamespace(namespace);
                    for (ActionBo action : agendaItem.getRule().getActions()) {
                        action.setNamespace(namespace);
                    }
                }
            }
        }
        return modelAndView;
    }

    /**
     * Validate the given simple proposition.  Note that this method is side-effecting,
     * when errors are detected with the proposition, errors are added to the error map.
     *
     * @param proposition the proposition to validate
     * @param namespace   the namespace of the parent rule
     * @return true if the proposition is considered valid
     */
    private boolean validateSimpleProposition(PropositionBo proposition, String namespace) {
        boolean result = true;

        String propConstant = null;
        if (proposition.getParameters().get(1) != null) {
            propConstant = proposition.getParameters().get(1).getValue();
        }
        String operator = null;
        if (proposition.getParameters().get(2) != null) {
            operator = proposition.getParameters().get(2).getValue();
        }

        String termId = null;
        if (proposition.getParameters().get(0) != null) {
            termId = proposition.getParameters().get(0).getValue();
        }
        // Simple proposition requires all of propConstant, termId and operator to be specified
        if (StringUtils.isBlank(termId)) {
            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.blankField", proposition.getDescription(), "Term");
            result &= false;
        } else {
            result = validateTerm(proposition, namespace);
        }
        if (StringUtils.isBlank(operator)) {
            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.blankField", proposition.getDescription(), "Operator");
            result &= false;
        }
        if (StringUtils.isBlank(propConstant) && !operator.endsWith("null")) { // ==null and !=null operators have blank values.
            GlobalVariables.getMessageMap().putErrorForSectionId(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.blankField", proposition.getDescription(), "Value");
            result &= false;
        } else if (operator.endsWith("null")) { // ==null and !=null operators have blank values.
            if (propConstant != null) {
                proposition.getParameters().get(1).setValue(null);
            }
        } else if (!StringUtils.isBlank(termId)) {
            // validate that the constant value is comparable against the term
            String termType = lookupTermType(termId);
            ComparisonOperatorService comparisonOperatorService = KrmsApiServiceLocator.getComparisonOperatorService();
            if (comparisonOperatorService.canCoerce(termType, propConstant)) {
                if (comparisonOperatorService.coerce(termType, propConstant) == null) { // HMM, what if we wanted a rule that
                    // checked a null value?
                    GlobalVariables.getMessageMap().putError(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                            "error.rule.proposition.simple.invalidValue", proposition.getDescription(), propConstant);
                    result &= false;
                }
            }
        }

        if (!CollectionUtils.isEmpty(proposition.getCompoundComponents())) {
            GlobalVariables.getMessageMap().putError(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.hasChildren", proposition.getDescription());
            result &= false; // simple prop should not have compound components
        }
        return result;
    }

    /**
     * Validate the term in the given simple proposition.  Note that this method is side-effecting,
     * when errors are detected with the proposition, errors are added to the error map.
     *
     * @param proposition the proposition with the term to validate
     * @param namespace   the namespace of the parent rule
     * @return true if the proposition's term is considered valid
     */
    private boolean validateTerm(PropositionBo proposition, String namespace) {
        boolean result = true;

        String termId = proposition.getParameters().get(0).getValue();
        if (termId.startsWith(KrmsImplConstants.PARAMETERIZED_TERM_PREFIX)) {
            // validate parameterized term

            // is the term name non-blank
            if (StringUtils.isBlank(proposition.getNewTermDescription())) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.emptyTermName", proposition.getDescription());
                result &= false;
            } else { // check if the term name is unique

                Map<String, String> criteria = new HashMap<String, String>();

                criteria.put("description", proposition.getNewTermDescription());
                criteria.put("specification.namespace", namespace);

                Collection<TermBo> matchingTerms =
                        KRADServiceLocator.getBusinessObjectService().findMatching(TermBo.class, criteria);

                if (!CollectionUtils.isEmpty(matchingTerms)) {
                    // this is a Warning -- maybe it should be an error?
                    GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                            "warning.rule.proposition.simple.duplicateTermName", proposition.getDescription());
                }
            }

            String termSpecificationId = termId.substring(KrmsImplConstants.PARAMETERIZED_TERM_PREFIX.length());

            TermResolverDefinition termResolverDefinition =
                    AgendaStudentEditorMaintainableImpl.getSimplestTermResolver(termSpecificationId, namespace);

            if (termResolverDefinition == null) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.invalidTerm", proposition.getDescription());
                result &= false;
            } else {
                List<String> parameterNames = new ArrayList<String>(termResolverDefinition.getParameterNames());
                Collections.sort(parameterNames);
                for (String parameterName : parameterNames) {
                    if (!proposition.getTermParameters().containsKey(parameterName) ||
                            StringUtils.isBlank(proposition.getTermParameters().get(parameterName))) {
                        GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                                "error.rule.proposition.simple.missingTermParameter", proposition.getDescription());
                        result &= false;
                        break;
                    }
                }
            }

        } else {
            //validate normal term
            TermDefinition termDefinition = KrmsRepositoryServiceLocator.getTermBoService().getTerm(termId);
            if (termDefinition == null) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.invalidTerm", proposition.getDescription());
            } else if (!namespace.equals(termDefinition.getSpecification().getNamespace())) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.invalidTerm", proposition.getDescription());
            }
        }
        return result;
    }

    /**
     * Lookup the {@link org.kuali.rice.krms.api.repository.term.TermSpecificationDefinitionContract} type.
     *
     * @param key krms_term_t key
     * @return String the krms_term_spec_t TYP for the given krms_term_t key given
     */
    private String lookupTermType(String key) {
        TermSpecificationDefinition termSpec = null;
        if (key.startsWith(KrmsImplConstants.PARAMETERIZED_TERM_PREFIX)) {
            String termSpecificationId = key.substring(KrmsImplConstants.PARAMETERIZED_TERM_PREFIX.length());
            termSpec = KrmsRepositoryServiceLocator.getTermBoService().getTermSpecificationById(termSpecificationId);
        } else {
            TermDefinition term = KrmsRepositoryServiceLocator.getTermBoService().getTerm(key);
            if (term != null) {
                termSpec = term.getSpecification();
            }
        }
        if (termSpec != null) {
            return termSpec.getType();
        } else {
            return null;
        }
    }

    @RequestMapping(params = "methodToCall=" + "ajaxRefresh")
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
    @RequestMapping(params = "methodToCall=" + "ajaxCategoryChangeRefresh")
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
                        findPropositionTreeNode(ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
                selectedPropositionNode.getData().getProposition().getProposition().setCategoryId(categoryId);
            }
        }

        return ajaxRefresh(form, result, request, response);
    }

    /**
     * return the contextBoService
     */
    private ContextBoService getContextBoService() {
        return KrmsRepositoryServiceLocator.getContextBoService();
    }

    /**
     * return the contextBoService
     */
    private RuleBoService getRuleBoService() {
        return KrmsRepositoryServiceLocator.getRuleBoService();
    }

    //
    // Rule Editor Controller methods
    //
    @RequestMapping(params = "methodToCall=" + "copyRule")
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
    @RequestMapping(params = "methodToCall=" + "goToEditProposition")
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

        resetEditModeOnPropositionTree(root);
        if (propositionToToggleEdit != null) {
            propositionToToggleEdit.setEditMode(newEditMode);
            //refresh the tree
            ruleEditor.refreshPropositionTree(null);
        }

        configureProposition(form, this.getProposition(form));

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "addProposition")
    public ModelAndView addProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        RuleBo rule = ruleEditor.getRule();
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        // find parent
        Node<RuleEditorTreeNode, String> root = ruleEditor.getPropositionTree().getRootElement();
        Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(root, selectedPropId);

        resetEditModeOnPropositionTree(root);

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
                            (SimplePropositionNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                                    SimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()))) {

                        // create a new compound proposition
                        PropositionBo compound = PropositionBo.createCompoundPropositionBoStub(child.getData().getProposition().getProposition(), true);
                        // don't set compound.setEditMode(true) as the Simple Prop in the compound prop is the only prop in edit mode
                        rule.setProposition(compound);
                        ruleEditor.refreshPropositionTree(null);
                    }
                    // handle regular case of adding a simple prop to an existing compound prop
                    else if (SimplePropositionNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                            SimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType())) {

                        // build new Blank Proposition
                        PropositionBo blank = PropositionBo.createSimplePropositionBoStub(child.getData().getProposition().getProposition(), PropositionType.SIMPLE.getCode());
                        //add it to the parent
                        PropositionBo parentProp = parent.getData().getProposition().getProposition();
                        parentProp.getCompoundComponents().add(((index / 2) + 1), blank);

                        ruleEditor.refreshPropositionTree(true);
                    }

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
                ruleEditor.refreshPropositionTree(true);
            }
        }
        return getUIFModelAndView(form);
    }

    /**
     * This method adds an opCode Node to separate components in a compound proposition.
     *
     * @param currentNode
     * @param prop
     * @return
     */
    private void addOpCodeNode(Node currentNode, PropositionBo prop, int index) {
        String opCodeLabel = "";

        if (LogicalOperator.AND.getCode().equalsIgnoreCase(prop.getCompoundOpCode())) {
            opCodeLabel = "AND";
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(prop.getCompoundOpCode())) {
            opCodeLabel = "OR";
        }
        Node<RuleTreeNode, String> aNode = new Node<RuleTreeNode, String>();
        aNode.setNodeLabel("");
        aNode.setNodeType("ruleTreeNode compoundOpCodeNode");
        aNode.setData(new CompoundOpCodeNode(prop));
        currentNode.insertChildAt(index, aNode);
    }


    private boolean propIdMatches(Node<RuleEditorTreeNode, String> node, String propId) {
        if (propId != null && node != null && node.getData() != null && propId.equalsIgnoreCase(node.getData().getProposition().getProposition().getId())) {
            return true;
        }
        return false;
    }

    /**
     * disable edit mode for all Nodes beneath and including the passed in Node
     *
     * @param currentNode
     */
    private void resetEditModeOnPropositionTree(Node<RuleEditorTreeNode, String> currentNode) {
        if (currentNode.getData() != null) {
            RuleEditorTreeNode dataNode = currentNode.getData();
            dataNode.getProposition().getProposition().setEditMode(false);
        }
        List<Node<RuleEditorTreeNode, String>> children = currentNode.getChildren();
        for (Node<RuleEditorTreeNode, String> child : children) {
            resetEditModeOnPropositionTree(child);
        }
    }

    private Node<RuleEditorTreeNode, String> findPropositionTreeNode(Node<RuleEditorTreeNode, String> currentNode, String selectedPropId) {
        Node<RuleEditorTreeNode, String> bingo = null;
        if (currentNode.getData() != null) {
            RuleEditorTreeNode dataNode = currentNode.getData();
            if (selectedPropId.equalsIgnoreCase(dataNode.getProposition().getProposition().getId())) {
                return currentNode;
            }
        }
        List<Node<RuleEditorTreeNode, String>> children = currentNode.getChildren();
        for (Node<RuleEditorTreeNode, String> child : children) {
            bingo = findPropositionTreeNode(child, selectedPropId);
            if (bingo != null) break;
        }
        return bingo;
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

    @RequestMapping(params = "methodToCall=" + "movePropositionUp")
    public ModelAndView movePropositionUp(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        moveSelectedProposition(form, true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "movePropositionDown")
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
                    if (SimplePropositionNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                            SimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()) ||
                            RuleTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(child.getNodeType())) {

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
                            boolean editMode = (SimplePropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()));
                            ruleEditor.refreshPropositionTree(editMode);
                        }
                    }

                    break;
                }
            }
        }
    }

    @RequestMapping(params = "methodToCall=" + "movePropositionLeft")
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
        if ((parent != null) && (RuleTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(parent.getNodeType()))) {
            Node<RuleEditorTreeNode, String> granny = PropositionTreeUtil.findParentPropositionNode(root, parent.getData().getProposition().getProposition().getId());
            if (granny != root) {
                int oldIndex = findChildIndex(parent, selectedPropId);
                int newIndex = findChildIndex(granny, parent.getData().getProposition().getProposition().getId());
                if (oldIndex >= 0 && newIndex >= 0) {
                    PropositionBo prop = parent.getData().getProposition().getProposition().getCompoundComponents().remove(oldIndex / 2);
                    granny.getData().getProposition().getProposition().getCompoundComponents().add((newIndex / 2) + 1, prop);
                    ruleEditor.refreshPropositionTree(false);
                }
            } else {
                // TODO: do we allow moving up to the root?
                // we could add a new top level compound node, with current root as 1st child,
                // and move the node to the second child.
            }
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "movePropositionRight")
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
                if (RuleTreeNode.COMPOUND_NODE_TYPE.equalsIgnoreCase(nextSibling.getNodeType())) {
                    // remove selected node from it's current spot
                    PropositionBo prop = parent.getData().getProposition().getProposition().getCompoundComponents().remove(index / 2);
                    // add it to it's siblings children
                    nextSibling.getData().getProposition().getProposition().getCompoundComponents().add(0, prop);
                    ruleEditor.refreshPropositionTree(false);
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
    @RequestMapping(params = "methodToCall=" + "togglePropositionSimpleCompound")
    public ModelAndView togglePropositionSimpleCompound(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        RuleBo rule = ruleEditor.getRule();
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        resetEditModeOnPropositionTree(ruleEditor.getPropositionTree().getRootElement());

        if (!StringUtils.isBlank(selectedPropId)) {
            // find parent
            Node<RuleEditorTreeNode, String> parent = PropositionTreeUtil.findParentPropositionNode(
                    ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
            if (parent != null) {

                int index = findChildIndex(parent, selectedPropId);

                PropositionBo propBo = parent.getChildren().get(index).getData().getProposition().getProposition();

                // create a new compound proposition
                PropositionBo compound = PropositionBo.createCompoundPropositionBoStub(propBo, true);
                compound.setDescription("New Compound Proposition " + UUID.randomUUID().toString());
                compound.setEditMode(false);

                if (parent.getData() == null) { // SPECIAL CASE: this is the only proposition in the tree
                    rule.setProposition(compound);
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
                }
            }
        }

        ruleEditor.getRule().refreshPropositionTree(true);
        return getUIFModelAndView(form);
    }


    @RequestMapping(params = "methodToCall=" + "cutProposition")
    public ModelAndView cutProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        String selectedPropId = ruleEditor.getSelectedPropositionId();
        ruleEditor.setCutPropositionId(selectedPropId);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "pasteProposition")
    public ModelAndView pasteProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        RuleBo rule = ruleEditor.getRule();

        // get selected id
        String cutPropId = ruleEditor.getCutPropositionId();
        String selectedPropId = ruleEditor.getSelectedPropositionId();

        if (StringUtils.isNotBlank(selectedPropId) && selectedPropId.equals(cutPropId)) {
            // do nothing; can't paste to itself
        } else {

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
                // TODO: determine edit mode.
//                boolean editMode = (SimpleStudentPropositionEditNode.NODE_TYPE.equalsIgnoreCase(child.getNodeType()));
                ruleEditor.refreshPropositionTree(false);
            }
        }
        ruleEditor.setCutPropositionId(null);
        // call the super method to avoid the agenda tree being reloaded from the db
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "deleteProposition")
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

        ruleEditor.getRule().refreshPropositionTree(false);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "updateCompoundOperator")
    public ModelAndView updateCompoundOperator(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RuleEditor ruleEditor = getRuleEditor(form);
        RuleBo rule = ruleEditor.getRule();
        rule.refreshPropositionTree(false);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "updateDescription")
    public ModelAndView updateDescription(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PropositionEditor proposition = this.getProposition(form);
        if (proposition != null) {
            //String desc = proposition.getDescription().replaceAll("\\{[a-z]{2,}\\}", proposition.getTermParameter());
            //proposition.setDescription(desc);
        }

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=" + "updateProposition")
    public ModelAndView updateProposition(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PropositionEditor proposition = this.getProposition(form);
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

            KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeById(propositionTypeId);
            if (type != null) {
                proposition.setType(type.getName());
                proposition.getProposition().setDescription(type.getName());
                setValueForProposition(proposition, "");
            }

            //RuleStudentViewHelperService viewHelper = (RuleStudentViewHelperService) KSControllerHelper.getViewHelperService(form);
            RuleStudentViewHelperService viewHelper = new RuleStudentViewHelperServiceImpl(); //TODO: fix this.


            //Set the term spec
            String termSpecId = viewHelper.getTermSpecificationForType(propositionTypeId);
            TermSpecificationDefinition termSpecification = getTermBoService().getTermSpecificationById(termSpecId);
            setTermForProposition(proposition, termSpecification.getId());

            proposition.setTermSpecId(termSpecId);

            //Set the operation
            setOperationForProposition(proposition, viewHelper.getOperationForType(propositionTypeId));

            //Set the value
            String defaultValue = viewHelper.getValueForType(propositionTypeId);
            if (!"?".equals(defaultValue)) {
                setValueForProposition(proposition, defaultValue);
            }

        }
    }

    private void setTermForProposition(PropositionEditor proposition, String term) {
        proposition.getProposition().getParameters().get(0).setValue(term);
    }

    private void setOperationForProposition(PropositionEditor proposition, String operation) {
        proposition.getProposition().getParameters().get(2).setValue(operation);
    }

    private void setValueForProposition(PropositionEditor proposition, String value) {
        proposition.getProposition().getParameters().get(1).setValue(value);
    }

    /**
     * @param form
     * @return the {@link org.kuali.rice.krms.impl.repository.PropositionBo} from the form
     */
    private PropositionEditor getProposition(UifFormBase form) {
        RuleEditor ruleEditor = getRuleEditor(form);

        if (ruleEditor != null) {
            String selectedPropId = ruleEditor.getSelectedPropositionId();
            return findProposition(ruleEditor.getPropositionTree().getRootElement(), selectedPropId);
        }

        return null;
    }

    private PropositionEditor findProposition(Node<RuleEditorTreeNode, String> currentNode, String selectedPropId) {

        if (selectedPropId == null) {
            return null;
        }

        // if it's in children, we have the parent
        for (Node<RuleEditorTreeNode, String> child : currentNode.getChildren()) {
            PropositionEditor proposition = child.getData().getProposition();
            if (selectedPropId.equalsIgnoreCase(proposition.getProposition().getId())) {
                return proposition;
            } else {
                // if not found check grandchildren
                proposition = findProposition(child, selectedPropId);
                if (proposition != null) {
                    return proposition;
                }
            }
        }

        return null;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    public TermBoService getTermBoService() {
        return KrmsRepositoryServiceLocator.getTermBoService();
    }

}
