package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.impl.repository.ContextBo;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.RuleBo;

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
public class RuleEditor extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    private RuleBo rule;
    private ContextBo context;
    private String namespace;
    private String contextName;
    private String selectedPropositionId;
    private String cutPropositionId;
    private String copyRuleName;
    private String oldContextId;
    private String ruleEditorMessage;
    private boolean addRuleInProgress = false;
    private Map<String, String> customAttributesMap = new HashMap<String, String>();
    private Map<String, String> customRuleAttributesMap = new HashMap<String, String>();
    private Map<String, String> customRuleActionAttributesMap = new HashMap<String, String>();

    // for Rule editor display
    Tree<RuleEditorTreeNode, String> propositionTree;

    // for rule editor display
    String propositionSummary;
    private StringBuffer propositionSummaryBuffer;

    public RuleEditor() {
        rule = new RuleBo();
    }

    public RuleEditor(RuleBo rule) {
        super();
        this.rule = rule;
    }

    public RuleBo getRule() {
        return rule;
    }

    public void setRule(RuleBo rule) {
        this.rule = rule;
    }

    /**
     * @return the context
     */
    public ContextBo getContext() {
        return this.context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ContextBo context) {
        this.context = context;
    }

    public Map<String, String> getCustomAttributesMap() {
        return customAttributesMap;
    }

    public void setCustomAttributesMap(Map<String, String> customAttributesMap) {
        this.customAttributesMap = customAttributesMap;
    }

    public Map<String, String> getCustomRuleAttributesMap() {
        return customRuleAttributesMap;
    }

    public void setCustomRuleAttributesMap(Map<String, String> customRuleAttributesMap) {
        this.customRuleAttributesMap = customRuleAttributesMap;
    }

    public Map<String, String> getCustomRuleActionAttributesMap() {
        return customRuleActionAttributesMap;
    }

    public void setCustomRuleActionAttributesMap(Map<String, String> customRuleActionAttributesMap) {
        this.customRuleActionAttributesMap = customRuleActionAttributesMap;
    }

    /**
     * @param copyRuleName the rule name from which to copy
     */
    public void setCopyRuleName(String copyRuleName) {
        this.copyRuleName = copyRuleName;
    }

    /**
     * @return the rule name from which to copy
     */
    public String getCopyRuleName() {
        return copyRuleName;
    }

    /**
     * @param oldContextId the contextId that the agenda currently has (i.e. before the next context change)
     */
    public void setOldContextId(String oldContextId) {
        this.oldContextId = oldContextId;
    }

    /**
     * @return the contextId that the agenda had before the context change
     */
    public String getOldContextId() {
        return oldContextId;
    }

    /**
     * @return the selectedPropositionId
     */
    public String getSelectedPropositionId() {
        return this.selectedPropositionId;
    }

    /**
     * @param selectedPropositionId the selectedPropositionId to set
     */
    public void setSelectedPropositionId(String selectedPropositionId) {
        this.selectedPropositionId = selectedPropositionId;
    }

    /**
     * @return the cutPropositionId
     */
    public String getCutPropositionId() {
        return cutPropositionId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getRuleEditorMessage() {
        return this.ruleEditorMessage;
    }

    public void setRuleEditorMessage(String message) {
        this.ruleEditorMessage = message;
    }

    public boolean isAddRuleInProgress() {
        return addRuleInProgress;
    }

    public void setAddRuleInProgress(boolean addRuleInProgress) {
        this.addRuleInProgress = addRuleInProgress;
    }

    /**
     * @param cutPropositionId the cutPropositionId to set
     */
    public void setCutPropositionId(String cutPropositionId) {
        this.cutPropositionId = cutPropositionId;
    }

    // Need to override this method since the actual persistable BO is wrapped inside dataObject.
    @Override
    public void refreshNonUpdateableReferences() {
        getPersistenceService().refreshAllNonUpdatingReferences(this.getRule());
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
        PropositionBo prop = rule.getProposition();
        buildPropTree( rootNode, prop, editMode );
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
                child.setNodeLabel(prop.getDescription());
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
                propositionSummaryBuffer.append(prop.getParameterDisplayString());
            }
            else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                // Compound Proposition
                propositionSummaryBuffer.append(" ( ");
                Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();
                aNode.setNodeLabel(prop.getDescription());
                // editMode has description as an editable field
                if (prop.getEditMode()){
                    aNode.setNodeLabel("");
                    aNode.setNodeType("ruleTreeNode compoundNode editNode");
                    CompoundStudentPropositionEditNode pNode = new CompoundStudentPropositionEditNode(propositionEditor);
                    aNode.setData(pNode);
                } else {
                    aNode.setNodeType("ruleTreeNode compoundNode");
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
}
