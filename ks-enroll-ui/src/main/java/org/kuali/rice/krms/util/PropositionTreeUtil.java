package org.kuali.rice.krms.util;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionTreeUtil {

    public static void setTypeForCompoundOpCode(PropositionEditor proposition, String compoundOpCode) {
        proposition.setCompoundOpCode(compoundOpCode);
        if (LogicalOperator.AND.getCode().equalsIgnoreCase(compoundOpCode)) {
            proposition.setType("kuali.krms.proposition.type.compound.and");
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(compoundOpCode)) {
            proposition.setType("kuali.krms.proposition.type.compound.or");
        }
        try{
            KrmsTypeDefinition type = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeByName(PermissionServiceConstants.KS_SYS_NAMESPACE, proposition.getType());
            proposition.setTypeId(type.getId());
        } catch (Exception e){
            //ignore if service not available.
        }

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

        if (selectedPropKey == null) {
            return null;
        } else if (selectedPropKey.isEmpty()) {
            return currentNode.getChildren().get(0).getData().getProposition();
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

    public static void resetEditModeOnPropositionTree(RuleEditor ruleEditor) {
        Node<RuleEditorTreeNode, String> root = ruleEditor.getEditTree().getRootElement();
        resetEditModeOnPropositionTree(root);
    }

    /**
     * disable edit mode for all Nodes beneath and including the passed in Node
     *
     * @param currentNode
     */
    public static void resetEditModeOnPropositionTree(Node<RuleEditorTreeNode, String> currentNode) {
        if (currentNode.getData() != null) {
            RuleEditorTreeNode dataNode = currentNode.getData();
            dataNode.getProposition().setEditMode(false);
        }
        List<Node<RuleEditorTreeNode, String>> children = currentNode.getChildren();
        for (Node<RuleEditorTreeNode, String> child : children) {
            resetEditModeOnPropositionTree(child);
        }
    }

    public static Node<RuleEditorTreeNode, String> findPropositionTreeNode(Node<RuleEditorTreeNode, String> currentNode, String selectedPropId) {
        Node<RuleEditorTreeNode, String> bingo = null;
        if (currentNode.getData() != null) {
            RuleEditorTreeNode dataNode = currentNode.getData();
            if (selectedPropId.equalsIgnoreCase(dataNode.getProposition().getId())) {
                return currentNode;
            }
        }

        for (Node<RuleEditorTreeNode, String> child : currentNode.getChildren()) {
            bingo = findPropositionTreeNode(child, selectedPropId);
            if (bingo != null) break;
        }
        return bingo;
    }

    public static String configureLogicExpression(PropositionEditor proposition) {
        // Depending on the type of proposition (simple/compound), and the editMode,
        // Create a treeNode of the appropriate type for the node and attach it to the
        // sprout parameter passed in.
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        String logicExpression = proposition.getKey();
        if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
            logicExpression += "(";
            boolean first = true;
            for (PropositionEditor child : proposition.getCompoundEditors()) {
                // add an opcode node in between each of the children.
                if (!first) {
                    if (LogicalOperator.AND.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
                        logicExpression += " AND ";
                    } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
                        logicExpression += " OR ";
                    }
                }
                first = false;
                // call to build the childs node
                logicExpression += configureLogicExpression(child);
            }
            logicExpression += ")";
        }
        return logicExpression;
    }

    public static PropositionEditor copyProposition(PropositionDefinitionContract existing, Class<? extends PropositionEditor> propClass) throws IllegalAccessException, InstantiationException {
        // Note: RuleId is not set
        PropositionEditor newProp = propClass.newInstance();
        newProp.setDescription(existing.getDescription());
        newProp.setPropositionTypeCode(existing.getPropositionTypeCode());
        newProp.setTypeId(existing.getTypeId());
        newProp.setCompoundOpCode(existing.getCompoundOpCode());
        // parameters
        List<PropositionParameterEditor> newParms = new ArrayList<PropositionParameterEditor>();
        for (PropositionParameterContract parm : existing.getParameters()) {
            PropositionParameterEditor p = new PropositionParameterEditor(parm.getParameterType(), parm.getSequenceNumber());
            p.setValue(parm.getValue());
            newParms.add(p);
        }
        newProp.setParameters(newParms);
        // compoundComponents
        List<PropositionEditor> newCompoundComponents = new ArrayList<PropositionEditor>();
        for (PropositionDefinitionContract component : existing.getCompoundComponents()) {
            newCompoundComponents.add(copyProposition(component, propClass));
        }
        newProp.setCompoundEditors(newCompoundComponents);
        return newProp;
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
        PropositionParameterEditor pTerm = new PropositionParameterEditor("T", new Integer("0"));
        PropositionParameterEditor pOp = new PropositionParameterEditor("O", new Integer("2"));
        PropositionParameterEditor pConst = new PropositionParameterEditor("C", new Integer("1"));

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
        prop.setCompoundOpCode(LogicalOperator.AND.getCode());  // default to and
        prop.setDescription("");
        prop.setEditMode(true);

        List<PropositionEditor> components = new ArrayList<PropositionEditor>();
        components.add(existing);
        prop.setCompoundEditors(components);
        return prop;
    }

}
