package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.impl.repository.AgendaBo;
import org.kuali.rice.krms.impl.repository.ContextBo;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.rice.krms.impl.ui.AgendaEditor;
import org.kuali.student.enrollment.class1.krms.form.TreeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleEditor extends AgendaEditor {

    private static final long serialVersionUID = 1L;

    private String cluId;
    private String courseName;
    private String agendaType;
    private String ruleType;
    private String copyPropositionId;
    private List<String> activeSelections;

    private RuleBo rule;

    // for Rule editor display
    Tree<RuleEditorTreeNode, String> propositionTree;

    // for rule editor display
    private StringBuffer propositionSummaryBuffer;

    private  Tree<TreeNode, String> previewTree;

    public RuleEditor() {
        rule = new RuleBo();
    }

    public RuleEditor(RuleBo rule) {
        super();
        this.rule = rule;
    }

    public void clearRule(){
        this.setRule(null);
        this.setPropositionTree(null);
        this.setRuleEditorMessage(null);
        this.setSelectedPropositionId(null);
        this.setCutPropositionId(null);
        this.setCopyPropositionId(null);
    }

    public RuleBo getRule() {
        return rule;
    }

    public void setRule(RuleBo rule) {
        this.rule = rule;
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAgendaType() {
        return agendaType;
    }

    public void setAgendaType(String agendaType) {
        this.agendaType = agendaType;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public List<String> getActiveSelections() {
        return activeSelections;
    }

    public void setActiveSelections(List<String> activeSelections) {
        this.activeSelections = activeSelections;
    }

    /**
     * @return the copyPropositionId
     */
    public String getCopyPropositionId() {
        return copyPropositionId;
    }

    /**
     * @param copyPropositionId the copyPropositionId to set
     */
    public void setCopyPropositionId(String copyPropositionId) {
        this.copyPropositionId = copyPropositionId;
    }

    @Override
    public void setAgenda(AgendaBo agenda) {
        super.setAgenda(agenda);

        // set extra fields on AgendaEditor
        if ((agenda != null) && (agenda.getContext() != null)){
            this.setNamespace(agenda.getContext().getNamespace());
            this.setContextName(agenda.getContext().getName());
        } else {
            this.setNamespace(null);
            this.setContextName(null);
        }
    }

    // Need to override this method since the actual persistable BO is wrapped inside dataObject.
    @Override
    public void refreshNonUpdateableReferences() {
        getPersistenceService().refreshAllNonUpdatingReferences(this.getAgenda());
    }

    public String getPropositionSummary(){
        if (this.propositionTree == null) {
            this.propositionTree = refreshPropositionTree(false);
        }
        return propositionSummaryBuffer.toString();
    }

    /**
     * This method is used by the RuleEditor to display the proposition in tree form.
     *
     * @return Tree representing a rule proposition.
     */
    public Tree getPropositionTree() {
        if (this.propositionTree == null){
            this.propositionTree = refreshPropositionTree(false);
        }
        return this.propositionTree;
    }

    public void setPropositionTree(Tree<RuleEditorTreeNode, String> tree) {
        this.propositionTree = tree;
    }

    public Tree refreshPropositionTree(Boolean editMode){
        Tree myTree = new Tree<RuleEditorTreeNode, String>();

        Node<RuleEditorTreeNode, String> rootNode = new Node<RuleEditorTreeNode, String>();
        myTree.setRootElement(rootNode);

        propositionSummaryBuffer = new StringBuffer();
        if (rule != null){
            PropositionBo prop = rule.getProposition();
            buildPropTree( rootNode, prop, editMode );
        }

        this.propositionTree = myTree;
        return myTree;
    }

    /**
     * This method builds a propositionTree recursively walking through the children of the proposition.
     * @param sprout - parent tree node
     * @param prop - PropositionBo for which to make the tree node
     * @param editMode - Boolean determines the node type used to represent the proposition
     *     false: create a view only node text control
     *     true: create an editable node with multiple controls
     *     null:  use the proposition.editMode property to determine the node type
     */
    private void buildPropTree( Node sprout, PropositionBo prop, Boolean editMode){
        // Depending on the type of proposition (simple/compound), and the editMode,
        // Create a treeNode of the appropriate type for the node and attach it to the
        // sprout parameter passed in.
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        if (prop != null) {
            PropositionEditor propositionEditor = new PropositionEditor(prop);
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                // Simple Proposition
                // add a node for the description display with a child proposition node
                Node<RuleEditorTreeNode, String> child = new Node<RuleEditorTreeNode, String>();
                child.setNodeLabel(StringEscapeUtils.escapeHtml(prop.getDescription()));
                if (prop.getEditMode()){
                    child.setNodeLabel("");
                    child.setNodeType(SimpleStudentPropositionEditNode.NODE_TYPE);
                    SimpleStudentPropositionEditNode pNode = new SimpleStudentPropositionEditNode(propositionEditor);
                    child.setData(pNode);
                } else {
                    child.setNodeType(SimpleStudentPropositionNode.NODE_TYPE);
                    SimpleStudentPropositionNode pNode = new SimpleStudentPropositionNode(propositionEditor);
                    child.setData(pNode);
                }
                sprout.getChildren().add(child);
                propositionSummaryBuffer.append(propositionEditor.getParameterDisplayString());
            }
            else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                // Compound Proposition
                propositionSummaryBuffer.append(" ( ");
                Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();
                aNode.setNodeLabel(StringEscapeUtils.escapeHtml(prop.getDescription()));
                // editMode has description as an editable field
                if (prop.getEditMode()){
                    aNode.setNodeLabel("");
                    aNode.setNodeType(CompoundStudentPropositionEditNode.NODE_TYPE);
                    CompoundStudentPropositionEditNode pNode = new CompoundStudentPropositionEditNode(propositionEditor);
                    aNode.setData(pNode);
                } else {
                    aNode.setNodeType(RuleEditorTreeNode.COMPOUND_NODE_TYPE);
                    RuleEditorTreeNode pNode = new RuleEditorTreeNode(propositionEditor);
                    aNode.setData(pNode);
                }
                sprout.getChildren().add(aNode);

                boolean first = true;
                List <PropositionBo> allMyChildren = prop.getCompoundComponents();
                int compoundSequenceNumber = 0;
                for (PropositionBo child : allMyChildren){
                    child.setCompoundSequenceNumber(++compoundSequenceNumber);  // start with 1
                    // add an opcode node in between each of the children.
                    if (!first){
                        addOpCodeNode(aNode, propositionEditor);
                    }
                    first = false;
                    // call to build the childs node
                    buildPropTree(aNode, child, editMode);
                }
                propositionSummaryBuffer.append(" ) ");
            }
        }
    }

    /**
     *
     * This method adds an opCode Node to separate components in a compound proposition.
     *
     * @param currentNode
     * @param prop
     * @return
     */
    private void addOpCodeNode(Node currentNode, PropositionEditor prop){
        String opCodeLabel = "";

        if (LogicalOperator.AND.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
            opCodeLabel = "AND";
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
            opCodeLabel = "OR";
        }
        propositionSummaryBuffer.append(" "+opCodeLabel+" ");
        Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();
        aNode.setNodeLabel("");
        aNode.setNodeType("ruleTreeNode compoundOpCodeNode");
        aNode.setData(new CompoundStudentOpCodeNode(prop));
        currentNode.getChildren().add(aNode);
    }

    public Tree<TreeNode, String> getPreviewTree() {
        if (this.previewTree == null){
            initPreviewTree();
        }
        return previewTree;
    }

    public void initPreviewTree(){
        Tree myTree = new Tree<TreeNode, String>();

        Node<TreeNode, String> rootNode = new Node<TreeNode, String>();
        rootNode.setNodeLabel("root");
        rootNode.setNodeType("rootNode");
        rootNode.setData(new TreeNode("Rule:"));
        myTree.setRootElement(rootNode);

        if (rule != null){
            PropositionBo prop = rule.getProposition();
            buildPreviewTree( rootNode, prop);
        }

        //Underline the first node in the preview.
        if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)){
            Node<TreeNode, String> firstNode = rootNode.getChildren().get(0);
            firstNode.setNodeType("subruleHeader");
            firstNode.setNodeLabel(firstNode.getNodeLabel() + ":");
        }

        this.previewTree = myTree;
    }

    private void buildPreviewTree(Node<TreeNode, String> currentNode, PropositionBo prop){
        if (prop != null) {
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                Node<TreeNode, String> newNode = new Node<TreeNode, String>();
                newNode.setNodeLabel(StringEscapeUtils.escapeHtml(prop.getDescription()));
                TreeNode tNode = new TreeNode(prop.getDescription());
                newNode.setData(tNode);
                currentNode.getChildren().add(newNode);
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                Node<TreeNode, String> newNode = new Node<TreeNode, String>();
                newNode.setNodeLabel(StringEscapeUtils.escapeHtml(prop.getDescription()));
                TreeNode tNode = new TreeNode(prop.getDescription());
                newNode.setData(tNode);
                currentNode.getChildren().add(newNode);

                boolean first = true;
                List <PropositionBo> nodeChildren = prop.getCompoundComponents();
                int compoundSequenceNumber = 0;
                for (PropositionBo child : nodeChildren){
                    child.setCompoundSequenceNumber(++compoundSequenceNumber);  // start with 1
                    // add an opcode node in between each of the children.
                    if (!first){
                        //addOpCodeNode(newNode, propositionEditor);
                        String opCodeLabel = "";

                        if (LogicalOperator.AND.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
                            opCodeLabel = "AND";
                        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
                            opCodeLabel = "OR";
                        }
                        Node<TreeNode, String> opNode = new Node<TreeNode, String>();
                        opNode.setNodeLabel(opCodeLabel);
                        opNode.setData(new TreeNode(prop.getCompoundOpCode()));
                        newNode.getChildren().add(opNode);
                    }
                    first = false;
                    // call to build the childs node
                    buildPreviewTree(newNode, child);
                }
            }

        }
    }
}
