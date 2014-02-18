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
package org.kuali.rice.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class with common functionality on the proposition tree.
 *
 * @author Kuali Student Team
 */
public class PropositionTreeUtil {

    public static final String DOC_NEW_DATAOBJECT_PATH = "document.newMaintainableObject.dataObject";
    public static final String EDIT_TREE_NEW_COLLECTION_LINE = DOC_NEW_DATAOBJECT_PATH + ".editTree";

    public static String getBindingPath(PropositionEditor proposition, String propertyName) {
        return getBindingPrefix(proposition) + "proposition." + propertyName;
    }

    public static String getBindingPrefix(PropositionEditor proposition) {
        return DOC_NEW_DATAOBJECT_PATH + "." + proposition.getBindingPath() + ".";
    }

    public static Node<RuleEditorTreeNode, String> findParentPropositionNode(Node<RuleEditorTreeNode, String> currentNode, String selectedPropKey) {
        Node<RuleEditorTreeNode, String> bingo = null;
        if (selectedPropKey != null) {
            // if it's in children, we have the parent
            List<Node<RuleEditorTreeNode, String>> children = currentNode.getChildren();
            for (Node<RuleEditorTreeNode, String> child : children) {
                RuleEditorTreeNode dataNode = child.getData();
                if (selectedPropKey.equalsIgnoreCase(dataNode.getProposition().getKey())) {
                    return currentNode;
                }
            }

            // if not found check grandchildren
            for (Node<RuleEditorTreeNode, String> kid : children) {
                bingo = findParentPropositionNode(kid, selectedPropKey);
                if (bingo != null) {
                    break;
                }
            }
        }
        return bingo;
    }

    /**
     * @return the {@link org.kuali.rice.krms.impl.repository.PropositionBo} from the form
     */
    public static PropositionEditor getProposition(RuleEditor ruleEditor) {

        if (ruleEditor != null) {
            String selectedPropKey = ruleEditor.getSelectedKey();
            return findProposition(ruleEditor.getEditTree().getRootElement(), selectedPropKey);
        }

        return null;
    }

    public static PropositionEditor findProposition(Node<RuleEditorTreeNode, String> currentNode, String selectedPropKey) {

        if ((selectedPropKey == null) || (selectedPropKey.isEmpty())) {
            return null;
        }

        // if it's in children, we have the parent
        for (Node<RuleEditorTreeNode, String> child : currentNode.getChildren()) {
            PropositionEditor proposition = child.getData().getProposition();
            if (selectedPropKey.equalsIgnoreCase(proposition.getKey())) {
                return proposition;
            } else if ("S".equals(proposition.getPropositionTypeCode()) && proposition.isEditMode()) {
                return proposition;
            } else if (!proposition.isEditMode()) {
                // if not found check grandchildren
                proposition = findProposition(child, selectedPropKey);
                if (proposition != null) {
                    return proposition;
                }
            }
        }

        return null;
    }

    /**
     * Find and return the node containing the proposition that is in currently in edit mode
     *
     * @param node the node to start searching from (typically the root)
     * @return the node that is currently being edited, if any.  Otherwise, null.
     */
    public static Node<RuleEditorTreeNode, String> findEditedProposition(Node<RuleEditorTreeNode, String> node) {
        Node<RuleEditorTreeNode, String> result = null;
        if (node.getData() != null && node.getData().getProposition() != null && node.getData().getProposition()
                .isEditMode()) {
            result = node;
        } else {
            for (Node<RuleEditorTreeNode, String> child : node.getChildren()) {
                result = findEditedProposition(child);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * disable edit mode for all Nodes beneath and including the passed in Node
     *
     * @param proposition
     */
    public static boolean resetEditModeOnPropositionTree(PropositionEditor proposition) {
        if(proposition==null){
            return false;
        }

        boolean editMode = proposition.isEditMode();
        proposition.setEditMode(false);
        if (proposition.getCompoundEditors() != null) {
            for (PropositionEditor childProp : proposition.getCompoundEditors()) {
                if(resetEditModeOnPropositionTree(childProp)) {
                    editMode = true;
                }
            }
        }
        return editMode;
    }

    /**
     * Builds a logical string expression from the proposition tree.
     *
     * @param proposition
     * @return
     */
    public static String configureLogicExpression(PropositionEditor proposition) {
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
            StringBuilder logicExpression = new StringBuilder();
            boolean first = true;
            for (PropositionEditor child : proposition.getCompoundEditors()) {
                // add an opcode node in between each of the children.
                if (!first) {
                    logicExpression.append(" " + getLabelForOperator(proposition.getCompoundOpCode()) + " ");
                }
                first = false;
                // call to build the childs node
                String compoundExpression = configureLogicExpression(child);
                if (compoundExpression.length() > 1) {
                    logicExpression.append("(" + compoundExpression + ")");
                } else {
                    logicExpression.append(compoundExpression);
                }
            }
            return logicExpression.toString();
        } else {
            return proposition.getKey();
        }
    }

    /**
     * This method creates a partially populated Simple PropositionBo with
     * three parameters:  a term type paramter (value not assigned)
     * a operation parameter
     * a constant parameter (value set to empty string)
     * The returned PropositionBo has an generatedId. The type code and ruleId properties are assigned the
     * same value as the sibling param passed in.
     * Each PropositionParameter has the id generated, and type, sequenceNumber,
     * propId default values set. The value is set to "".
     *
     * @param sibling -
     * @return a PropositionBo partially populated.
     */
    public static PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling, Class<? extends PropositionEditor> propClass) throws IllegalAccessException, InstantiationException {
        // create a simple proposition Bo
        PropositionEditor prop = propClass.newInstance();
        prop.setPropositionTypeCode(PropositionType.SIMPLE.getCode());
        prop.setNewProp(true);
        prop.setEditMode(true);
        if (sibling != null) {
            prop.setRuleId(sibling.getRuleId());
        }

        prop.setParameters(createParameterList());

        return prop;
    }

    public static List<PropositionParameterEditor> createParameterList() {
        // create blank proposition parameters
        PropositionParameterEditor pTerm = new PropositionParameterEditor(PropositionParameterType.TERM.getCode(), Integer.valueOf("0"));
        PropositionParameterEditor pOp = new PropositionParameterEditor(PropositionParameterType.OPERATOR.getCode(), Integer.valueOf("2"));
        PropositionParameterEditor pConst = new PropositionParameterEditor(PropositionParameterType.CONSTANT.getCode(), Integer.valueOf("1"));

        return Arrays.asList(pTerm, pConst, pOp);
    }

    public static PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild, Class<? extends PropositionEditor> propClass) throws InstantiationException, IllegalAccessException {
        // create a simple proposition Bo
        PropositionEditor prop = createCompoundPropositionBoStub(existing, propClass);

        if (addNewChild) {
            PropositionEditor newProp = createSimplePropositionBoStub(existing, propClass);
            prop.getCompoundEditors().add(newProp);
            prop.setEditMode(false); // set the parent edit mode back to null or we end up with 2 props in edit mode
        }

        return prop;
    }

    public static PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, Class<? extends PropositionEditor> propClass) throws IllegalAccessException, InstantiationException {
        // create a simple proposition Bo
        PropositionEditor prop = propClass.newInstance();
        prop.setNewProp(true);
        prop.setPropositionTypeCode(PropositionType.COMPOUND.getCode());
        prop.setRuleId(existing.getRuleId());
        prop.setEditMode(false);

        List<PropositionEditor> components = new ArrayList<PropositionEditor>();
        components.add(existing);
        prop.setCompoundEditors(components);
        return prop;
    }

    public static void cancelNewProp(PropositionEditor proposition) {
        int i = 0;
        if (proposition.getCompoundEditors() != null) {
            while (i < proposition.getCompoundEditors().size()) {
                PropositionEditor child = proposition.getCompoundEditors().get(i);
                if (child.isNewProp() && child.isEditMode()) {
                    proposition.getCompoundEditors().remove(child);
                    continue;
                } else {
                    cancelNewProp(child);
                }
                i++;
            }
        }
    }

    /**
     * This method walks through the proposition tree including the root node on the tree
     * and remove all parent compound propositions that only have one child.
     *
     * @param rule
     */
    public static void removeCompoundProp(RuleEditor rule) {
        //Check if the root only has one child, if so set the child as the root proposition.
        PropositionEditor root = rule.getPropositionEditor();
        if (root.getCompoundEditors() != null) {
            if(root.getCompoundEditors().size() == 1) {
                rule.setProposition(root.getCompoundEditors().get(0));
            }

            //Remove single parent from proposition tree.
            removeCompoundProp(rule.getPropositionEditor());
        }

    }

    /**
     * This method walks through the proposition tree and remove all parent compound propositions
     * that only have one child.
     *
     * Note: This does not handle the root as the root need to be set on the rule.
     *
     * @param proposition
     */
    public static void removeCompoundProp(PropositionEditor proposition){
        if (proposition.getCompoundEditors() != null) {

            // Handle the scenario if the inpust proposition only have one child.
            if(proposition.getCompoundEditors().size()==1){
                PropositionEditor child = proposition.getCompoundEditors().get(0);
                if(PropositionType.COMPOUND.getCode().equalsIgnoreCase(child.getPropositionTypeCode())) {
                    proposition.setCompoundEditors(child.getCompoundEditors());
                }
            }

            // Now handle the children.
            for(int i=proposition.getCompoundEditors().size()-1;i>=0;i--){
                PropositionEditor child = proposition.getCompoundEditors().get(i);
                if(PropositionType.COMPOUND.getCode().equalsIgnoreCase(child.getPropositionTypeCode())) {
                    if (child.getCompoundEditors().isEmpty()) {
                        proposition.getCompoundEditors().remove(i);
                    } else {
                        if (child.getCompoundEditors().size() == 1) {
                            proposition.getCompoundEditors().set(i, child.getCompoundEditors().get(0));
                        }
                        removeCompoundProp(child);
                    }
                }
            }
        }
    }

    public static void resetNewProp(PropositionEditor proposition) {
        proposition.setNewProp(false);
        if (proposition.getCompoundEditors() != null) {
            for (PropositionEditor child : proposition.getCompoundEditors()) {
                resetNewProp(child);
            }
        }
    }

    /**
     * Returns the first parameter of type term in the list.
     *
     * @param parameters
     * @return
     */
    public static PropositionParameterEditor getTermParameter(List<PropositionParameterEditor> parameters) {
        return getParameterForType(parameters, PropositionParameterType.TERM);
    }

    /**
     * Returns the first parameter of type constant in the list.
     *
     * @param parameters
     * @return
     */
    public static PropositionParameterEditor getConstantParameter(List<PropositionParameterEditor> parameters) {
        return getParameterForType(parameters, PropositionParameterType.CONSTANT);
    }

    /**
     * Returns the first parameter of type operator in the list.
     *
     * @param parameters
     * @return
     */
    public static PropositionParameterEditor getOperatorParameter(List<PropositionParameterEditor> parameters) {
        return getParameterForType(parameters, PropositionParameterType.OPERATOR);
    }

    /**
     * Returns the first parameter of the given type in the list.
     *
     * @param parameters
     * @param type
     * @return
     */
    public static PropositionParameterEditor getParameterForType(List<PropositionParameterEditor> parameters, PropositionParameterType type) {

        if (parameters == null) {
            return null;
        }

        for (PropositionParameterEditor parameter : parameters) {
            if (type.getCode().equals(parameter.getParameterType())) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Returns a label based on the operator code.
     *
     * @param opCode
     * @return
     */
    public static String getLabelForOperator(String opCode) {
        if (LogicalOperator.AND.getCode().equalsIgnoreCase(opCode)) {
            return "AND";
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(opCode)) {
            return "OR";
        }
        return StringUtils.EMPTY;
    }

    public static boolean isSimpleCompounds(PropositionEditor propositionEditor) {
        for (int index = 0; index < propositionEditor.getCompoundEditors().size(); index++) {
            if (propositionEditor.getCompoundEditors().get(index).getPropositionTypeCode().equals("C")) {
                return false;
            }
        }
        return true;
    }
}
