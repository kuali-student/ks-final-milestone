package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.impl.repository.AgendaBo;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.rice.krms.impl.ui.AgendaEditor;
import org.kuali.student.enrollment.class1.krms.form.TreeNode;
import org.kuali.student.enrollment.class1.krms.util.AlphaIterator;

import java.util.Date;
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

    private String ruleType;
    private String copyPropositionId;
    private List<String> activeSelections;

    //Course Range Dialog.
    private String searchByCourseRange;
    private String subjectCode;
    private String courseNumberRange;
    private String learningObjective;
    private Date effectiveFrom;
    private Date effectiveTo;

    private RuleBo rule;

    // for Rule editor display
    private transient Tree<RuleEditorTreeNode, String> propositionTree;

    // for Rule Preview display
    private transient LogicRuleViewer rulePreviewer;
    private Map<String, String> propositionAlpha = new HashMap<String, String>();
    private AlphaIterator alpha = new AlphaIterator();
   
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
        this.refreshPropositionTree();
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
        if (this.getAgenda() != null){
            return this.getAgenda().getTypeId();
        }
        return null;
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

    public Map<String, String> getPropositionAlpha() {
        return propositionAlpha;
    }

    public void setPropositionAlpha(Map<String, String> propositionAlpha) {
        this.propositionAlpha = propositionAlpha;
    }

    public String getSearchByCourseRange() {
        return searchByCourseRange;
    }

    public void setSearchByCourseRange(String searchByCourseRange) {
        this.searchByCourseRange = searchByCourseRange;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCourseNumberRange() {
        return courseNumberRange;
    }

    public void setCourseNumberRange(String courseNumberRange) {
        this.courseNumberRange = courseNumberRange;
    }

    public String getLearningObjective() {
        return learningObjective;
    }

    public void setLearningObjective(String learningObjective) {
        this.learningObjective = learningObjective;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
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

    /**
     * This method is used by the RuleEditor to display the proposition in tree form.
     *
     * @return Tree representing a rule proposition.
     */
    public Tree getPropositionTree() {
        if (this.propositionTree == null){
            this.propositionTree = refreshPropositionTree();
        }
        return this.propositionTree;
    }

    public void setPropositionTree(Tree<RuleEditorTreeNode, String> tree) {
        this.propositionTree = tree;
    }

    public Tree refreshPropositionTree(){
        Tree myTree = new Tree<RuleEditorTreeNode, String>();
        
        Node<RuleEditorTreeNode, String> rootNode = new Node<RuleEditorTreeNode, String>();
        myTree.setRootElement(rootNode);        
        
        if (rule != null){
            PropositionBo prop = rule.getProposition();
            buildPropTree( rootNode, prop);
        }

        this.propositionTree = myTree;
        return myTree;
    }

    /**
     * This method builds a propositionTree recursively walking through the children of the proposition.
     * @param sprout - parent tree node
     * @param prop - PropositionBo for which to make the tree node
     */
    private void buildPropTree( Node sprout, PropositionBo prop){
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

                // Simple Proposition add a node for the description display with a child proposition node
                if (prop.getEditMode()){
                    child.setNodeType(KSSimplePropositionEditNode.NODE_TYPE);
                    KSSimplePropositionEditNode pNode = new KSSimplePropositionEditNode(propositionEditor);
                    child.setData(pNode);
                } else {
                    child.setNodeLabel(this.buildNodeLabel(prop));
                    child.setNodeType(KSSimplePropositionNode.NODE_TYPE);
                    KSSimplePropositionNode pNode = new KSSimplePropositionNode(propositionEditor);
                    child.setData(pNode);
                }
                sprout.getChildren().add(child);
            }
            else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){
                // Compound Proposition
                Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();
                
                // editMode has description as an editable field
                if (prop.getEditMode()){
                    aNode.setNodeLabel("");
                    aNode.setNodeType(KSCompoundPropositionEditNode.NODE_TYPE);
                    KSCompoundPropositionEditNode pNode = new KSCompoundPropositionEditNode(propositionEditor);
                    aNode.setData(pNode);
                } else {
                    aNode.setNodeLabel(this.buildNodeLabel(prop));
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
                    buildPropTree(aNode, child);
                }            
            }
        }
    }

    private String buildNodeLabel(PropositionBo prop){
        //Add the proposition with alpha code in the map if it doesn't already exist.
        if(!propositionAlpha.containsKey(prop.getId())) {
            propositionAlpha.put(prop.getId(), (String)alpha.next());
        }

        //Build the node label.
        String prefix = "<b>" + propositionAlpha.get(prop.getId()) + ".</b> ";
        return prefix + StringEscapeUtils.escapeHtml(prop.getDescription());
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
        Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();
        aNode.setNodeLabel("");
        aNode.setNodeType("ruleTreeNode compoundOpCodeNode");
        aNode.setData(new KSCompoundOpCodeNode(prop));
        currentNode.getChildren().add(aNode);
    }

    public Tree<TreeNode, String> getPreviewTree() {
        if (this.rulePreviewer == null){
            rulePreviewer  = new LogicRuleViewer();
            initPreviewTree();
        }

        return this.rulePreviewer.getPreviewTree();
    }

    public void initPreviewTree(){
        this.rulePreviewer.initPreviewTree(this);
    }
}
