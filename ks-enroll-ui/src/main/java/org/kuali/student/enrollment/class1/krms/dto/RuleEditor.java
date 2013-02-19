package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.action.ActionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.impl.repository.KrmsAttributeDefinitionBo;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class1.krms.form.TreeNode;
import org.kuali.student.enrollment.class1.krms.util.AlphaIterator;

import java.io.Serializable;
import java.util.ArrayList;
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
public class RuleEditor implements RuleDefinitionContract, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String namespace;
    private String description;
    private String name;
    private String typeId;
    private String propId;
    private boolean active = true;
    private Long versionNumber;

    private PropositionEditor proposition;

    private String cluId;
    private String courseName;

    private String ruleType;
    private String copyPropositionId;
    private String selectedPropositionId;
    private String cutPropositionId;
    private List<String> activeSelections;

    //Course Range Dialog.
    private String searchByCourseRange;
    private String subjectCode;
    private String courseNumberRange;
    private String learningObjective;
    private Date effectiveFrom;
    private Date effectiveTo;

    public String getLogicArea() {
        return logicArea;
    }

    public void setLogicArea(String logicArea) {
        this.logicArea = logicArea;
    }

    private String logicArea;

    // for Rule editor display
    private transient Tree<RuleEditorTreeNode, String> propositionTree;

    // for Rule Preview display
    private transient LogicRuleViewer rulePreviewer;
    private Map<String, String> propositionAlpha = new HashMap<String, String>();
    private AlphaIterator alpha = new AlphaIterator();

    public RuleEditor() {
        super();
    }

    public RuleEditor(RuleDefinition definition) {
        this.id = definition.getId();
        this.namespace = definition.getNamespace();
        this.name = definition.getName();
        this.description = definition.getDescription();
        this.typeId = definition.getTypeId();
        this.propId = definition.getPropId();
        this.active = definition.isActive();
        this.proposition = new PropositionEditor(definition.getProposition());
        this.versionNumber = definition.getVersionNumber();

        //TODO: Actions
        //this.actions = new ArrayList<ActionBo>();
        //for (ActionDefinition action : im.getActions()){
        //this.actions.add( ActionBo.from(action) );
        //}

        //TODO: build the set of agenda attribute BOs
        //List<RuleAttributeBo> attrs = new ArrayList<RuleAttributeBo>();
        //this.setAttributeBos(attrs);
    }

    public void clearRule() {
        this.setPropositionTree(null);
        this.setSelectedPropositionId(null);
        this.setCutPropositionId(null);
        this.setCopyPropositionId(null);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProposition(PropositionEditor proposition) {
        this.proposition = proposition;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public void setCutPropositionId(String cutPropositionId) {
        this.cutPropositionId = cutPropositionId;
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

    //@Override
    //public void setAgenda(AgendaBo agenda) {
    //    super.setAgenda(agenda);

    // set extra fields on AgendaEditor
    //    if ((agenda != null) && (agenda.getContext() != null)){
    //        this.setNamespace(agenda.getContext().getNamespace());
    //        this.setContextName(agenda.getContext().getName());
    //    } else {
    //        this.setNamespace(null);
    //        this.setContextName(null);
    //    }
    //}

    public Tree<TreeNode, String> getPreviewTree() {
        if (this.rulePreviewer == null) {
            rulePreviewer = new LogicRuleViewer();
            initPreviewTree();
        }

        return this.rulePreviewer.getPreviewTree();
    }

    public void initPreviewTree() {
        this.rulePreviewer.initPreviewTree(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public String getTypeId() {
        return this.typeId;
    }

    @Override
    public String getPropId() {
        return this.propId;
    }

    @Override
    public PropositionDefinitionContract getProposition() {
        return proposition;
    }

    @Override
    public List<? extends ActionDefinitionContract> getActions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> getAttributes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public Long getVersionNumber() {
        return versionNumber;
    }

    /**
     * This method is used by the RuleEditor to display the proposition in tree form.
     *
     * @return Tree representing a rule proposition.
     */
    public Tree getPropositionTree() {
        if (this.propositionTree == null) {
            this.propositionTree = refreshPropositionTree();
        }
        return this.propositionTree;
    }

    public void setPropositionTree(Tree<RuleEditorTreeNode, String> tree) {
        this.propositionTree = tree;
    }

    public Tree refreshPropositionTree() {
        Tree myTree = new Tree<RuleEditorTreeNode, String>();

        Node<RuleEditorTreeNode, String> rootNode = new Node<RuleEditorTreeNode, String>();
        myTree.setRootElement(rootNode);

        PropositionEditor prop = (PropositionEditor) this.getProposition();
        buildPropTree(rootNode, prop);

        this.propositionTree = myTree;
        return myTree;
    }

    /**
     * This method builds a propositionTree recursively walking through the children of the proposition.
     *
     * @param sprout - parent tree node
     * @param prop   - PropositionBo for which to make the tree node
     */
    private void buildPropTree(Node sprout, PropositionEditor prop) {
        // Depending on the type of proposition (simple/compound), and the editMode,
        // Create a treeNode of the appropriate type for the node and attach it to the
        // sprout parameter passed in.
        // If the prop is a compound proposition, calls itself for each of the compoundComponents
        if (prop != null) {
            if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                // Simple Proposition
                // add a node for the description display with a child proposition node
                Node<RuleEditorTreeNode, String> child = new Node<RuleEditorTreeNode, String>();

                // Simple Proposition add a node for the description display with a child proposition node
                if (prop.isEditMode()) {
                    child.setNodeType(KSSimplePropositionEditNode.NODE_TYPE);
                    KSSimplePropositionEditNode pNode = new KSSimplePropositionEditNode(prop);
                    child.setData(pNode);
                } else {
                    child.setNodeLabel(this.buildNodeLabel(prop));
                    child.setNodeType(KSSimplePropositionNode.NODE_TYPE);
                    KSSimplePropositionNode pNode = new KSSimplePropositionNode(prop);
                    child.setData(pNode);
                }
                sprout.getChildren().add(child);
            } else if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
                // Compound Proposition
                Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();

                // editMode has description as an editable field
                if (prop.isEditMode()) {
                    aNode.setNodeLabel("");
                    aNode.setNodeType(KSCompoundPropositionEditNode.NODE_TYPE);
                    KSCompoundPropositionEditNode pNode = new KSCompoundPropositionEditNode(prop);
                    aNode.setData(pNode);
                } else {
                    aNode.setNodeLabel(this.buildNodeLabel(prop));
                    aNode.setNodeType(RuleEditorTreeNode.COMPOUND_NODE_TYPE);
                    RuleEditorTreeNode pNode = new RuleEditorTreeNode(prop);
                    aNode.setData(pNode);
                }
                sprout.getChildren().add(aNode);

                boolean first = true;
                List<PropositionEditor> allMyChildren = (List<PropositionEditor>) prop.getCompoundComponents();
                for (PropositionEditor child : allMyChildren) {
                    // add an opcode node in between each of the children.
                    if (!first) {
                        addOpCodeNode(aNode, prop);
                    }
                    first = false;
                    // call to build the childs node
                    buildPropTree(aNode, child);
                }
            }
        }
    }

    private String buildNodeLabel(PropositionEditor prop) {
        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (!propositionAlpha.containsKey(prop.getId())) {
            propositionAlpha.put(prop.getId(), (String) alpha.next());
        }

        //Build the node label.
        String prefix = "<b>" + propositionAlpha.get(prop.getId()) + ".</b> ";
        return prefix + StringEscapeUtils.escapeHtml(prop.getDescription());
    }

    /**
     * This method adds an opCode Node to separate components in a compound proposition.
     *
     * @param currentNode
     * @param prop
     * @return
     */
    private void addOpCodeNode(Node currentNode, PropositionEditor prop) {
        Node<RuleEditorTreeNode, String> aNode = new Node<RuleEditorTreeNode, String>();
        aNode.setNodeLabel("");
        aNode.setNodeType("ruleTreeNode compoundOpCodeNode");
        aNode.setData(new KSCompoundOpCodeNode(prop));
        currentNode.getChildren().add(aNode);
    }
}
