package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.action.ActionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.student.enrollment.class1.krms.tree.node.CompareTreeNode;
import org.kuali.student.enrollment.class1.krms.tree.node.RuleEditorTreeNode;
import org.kuali.student.enrollment.class1.krms.tree.node.TreeNode;
import org.kuali.student.enrollment.class1.krms.util.AlphaIterator;

import java.io.Serializable;
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
    private transient Tree<RuleEditorTreeNode, String> editTree;

    // for Rule Preview display
    private transient Tree<TreeNode, String> previewTree;
    private Map<String, String> propositionAlpha = new HashMap<String, String>();
    private AlphaIterator alpha = new AlphaIterator();

    // for Compare
    private transient Tree<CompareTreeNode, String> compareTree;

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

    public AlphaIterator getAlpha() {
        return alpha;
    }

    public void setAlpha(AlphaIterator alpha) {
        this.alpha = alpha;
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

    public Tree<RuleEditorTreeNode, String> getEditTree() {
        return editTree;
    }

    public void setEditTree(Tree<RuleEditorTreeNode, String> editTree) {
        this.editTree = editTree;
    }

    public Tree<TreeNode, String> getPreviewTree() {
        return previewTree;
    }

    public void setPreviewTree(Tree<TreeNode, String> previewTree) {
        this.previewTree = previewTree;
    }

    public Tree<CompareTreeNode, String> getCompareTree() {
        return compareTree;
    }

    public void setCompareTree(Tree<CompareTreeNode, String> compareTree) {
        this.compareTree = compareTree;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public Long getVersionNumber() {
        return versionNumber;
    }
}
