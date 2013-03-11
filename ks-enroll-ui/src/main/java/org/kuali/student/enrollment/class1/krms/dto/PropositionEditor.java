package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.impl.ui.TermParameter;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionEditor implements PropositionDefinitionContract, Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String id;
    private String description;
    private String ruleId;
    private String compoundOpCode;
    private String typeId;
    private String propositionTypeCode;
    private Long versionNumber;

    private List<PropositionParameterEditor> parameters;
    private List<PropositionEditor> compoundEditors;

    private TermDefinition term;
    private String termParameter;
    private List<TermParameter> termParameterList = new ArrayList<TermParameter>();
    private String type;
    private boolean editMode = false;

    private CourseInfo courseInfo;
    private CluSetInformation cluSet;
    private String multipleCourseType;
    private String gradeScale;

    private String newTermDescription = "new term " + UUID.randomUUID().toString();

    public PropositionEditor() {
        super();
    }

    /**
     * Converts a immutable object to it's mutable bo counterpart
     * @param definition immutable object
     * @return the mutable bo
     */
    public PropositionEditor(PropositionDefinitionContract definition) {
        this.id = definition.getId();
        this.description = definition.getDescription();
        this.ruleId = definition.getRuleId();

        this.typeId = definition.getTypeId();
        this.propositionTypeCode = definition.getPropositionTypeCode();
        this.parameters = new ArrayList<PropositionParameterEditor>();
        for (PropositionParameterContract parm : definition.getParameters()){
            this.parameters.add (new PropositionParameterEditor(parm));
        }
        this.compoundOpCode = definition.getCompoundOpCode();
        this.compoundEditors = new ArrayList<PropositionEditor>();
        for (PropositionDefinitionContract prop : definition.getCompoundComponents()){
            this.compoundEditors.add(new PropositionEditor(prop));
        }
        this.versionNumber = definition.getVersionNumber();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public String getCompoundOpCode(){
        return compoundOpCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public void setCompoundOpCode(String compoundOpCode) {
        this.compoundOpCode = compoundOpCode;
    }

    public void setParameters(List<PropositionParameterEditor> parameters) {
        this.parameters = parameters;
    }

    public void setCompoundEditors(List<PropositionEditor> compoundEditors) {
        this.compoundEditors = compoundEditors;
    }

    public List<PropositionEditor> getCompoundEditors() {
        return compoundEditors;
    }

    @Override
    public List<? extends PropositionDefinitionContract> getCompoundComponents() {
        return compoundEditors;
    }

    public List<PropositionParameterEditor> getParameters(){
        return parameters;
    }

    public String getTypeId(){
        return typeId;
    }

    @Override
    public String getRuleId() {
        return this.ruleId;
    }

    public void setPropositionTypeCode(String propositionTypeCode) {
        this.propositionTypeCode = propositionTypeCode;
    }

    @Override
    public String getPropositionTypeCode() {
        return propositionTypeCode;
    }

    public void setTypeId(String typeId){
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public TermDefinition getTerm() {
        return term;
    }

    public void setTerm(TermDefinition term) {
        this.term = term;
    }

    public String getTermParameter() {
        return termParameter;
    }

    public void setTermParameter(String termParameter) {
        this.termParameter = termParameter;
    }

    public List<TermParameter> getTermParameterList() {
        return termParameterList;
    }

    public void setTermParameterList(List<TermParameter> termParameterList) {
        this.termParameterList = termParameterList;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getMultipleCourseType() {
        return multipleCourseType;
    }

    public void setMultipleCourseType(String multipleCourseType) {
        this.multipleCourseType = multipleCourseType;
    }

    public String getGradeScale() {
        return gradeScale;
    }

    public void setGradeScale(String gradeScale) {
        this.gradeScale = gradeScale;
    }

    public CluSetInformation getCluSet() {
        return cluSet;
    }

    public void setCluSet(CluSetInformation cluSet) {
        this.cluSet = cluSet;
    }

    public int getCluListSize(){
        if ((this.getCluSet() != null) && (this.getCluSet().getClus() != null)){
            return this.getCluSet().getClus().size();
        } else {
            return 0;
        }
    }

    public int getCluSetListSize(){
        if ((this.getCluSet() != null) && (this.getCluSet().getCluSets() != null)){
            return this.getCluSet().getCluSets().size();
        } else {
            return 0;
        }
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    /**
     * This method creates a partially populated Simple PropositionBo with
     * three parameters:  a term type paramter (value not assigned)
     *                    a operation parameter
     *                    a constant parameter (value set to empty string)
     * The returned PropositionBo has an generatedId. The type code and ruleId properties are assigned the
     * same value as the sibling param passed in.
     * Each PropositionParameter has the id generated, and type, sequenceNumber,
     * propId default values set. The value is set to "".
     * @param sibling -
     * @param pType
     * @return  a PropositionBo partially populated.
     */
    public static PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling, String pType){
        // create a simple proposition Bo
        PropositionEditor prop = null;
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(pType)){
            prop = new PropositionEditor();
            //prop.setId(getNewPropId());
            prop.setPropositionTypeCode(pType);
            prop.setEditMode(true);
            if (sibling != null){
                prop.setRuleId(sibling.getRuleId());
            }

            // create blank proposition parameters
            PropositionParameterEditor pTerm = new PropositionParameterEditor();
            //pTerm.setId(getNewPropParameterId());
            pTerm.setParameterType("T");
            pTerm.setPropId(prop.getId());
            pTerm.setSequenceNumber(new Integer("0"));
            pTerm.setVersionNumber(new Long(1));
            pTerm.setValue("");

            // create blank proposition parameters
            PropositionParameterEditor pOp = new PropositionParameterEditor();
            //pOp.setId(getNewPropParameterId());
            pOp.setParameterType("O");
            pOp.setPropId(prop.getId());
            pOp.setSequenceNumber(new Integer("2"));
            pOp.setVersionNumber(new Long(1));

            // create blank proposition parameters
            PropositionParameterEditor pConst = new PropositionParameterEditor();
            //pConst.setId(getNewPropParameterId());
            pConst.setParameterType("C");
            pConst.setPropId(prop.getId());
            pConst.setSequenceNumber(new Integer("1"));
            pConst.setVersionNumber(new Long(1));
            pConst.setValue("");

            List<PropositionParameterEditor> paramList = Arrays.asList(pTerm, pConst, pOp);
            prop.setParameters(paramList);
        }
        return prop;
    }

    public static PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild){
        // create a simple proposition Bo
        PropositionEditor prop = new PropositionEditor();
        //prop.setId(getNewPropId());
        prop.setPropositionTypeCode(PropositionType.COMPOUND.getCode());
        prop.setCompoundOpCode(LogicalOperator.AND.getCode());  // default to and
        prop.setDescription("");
        prop.setEditMode(true);
        if (existing != null){
            prop.setRuleId(existing.getRuleId());
        }

        List <PropositionEditor> components = new ArrayList<PropositionEditor>(2);
        components.add(existing);

        if (addNewChild) {
            PropositionEditor newProp = createSimplePropositionBoStub(existing, PropositionType.SIMPLE.getCode());
            components.add(newProp);
            prop.setEditMode(false); // set the parent edit mode back to null or we end up with 2 props in edit mode
        }

        prop.setCompoundEditors(components);
        return prop;
    }

    public static PropositionEditor createCompoundPropositionBoStub2(PropositionEditor existing){
        // create a simple proposition Bo
        PropositionEditor prop = new PropositionEditor();
        //prop.setId(getNewPropId());
        prop.setPropositionTypeCode(PropositionType.COMPOUND.getCode());
        prop.setRuleId(existing.getRuleId());
        prop.setCompoundOpCode(LogicalOperator.AND.getCode());  // default to and
        prop.setDescription("");
        prop.setEditMode(true);

        List <PropositionEditor> components = new ArrayList<PropositionEditor>();
        components.add(existing);
        prop.setCompoundEditors(components);
        return prop;
    }

    public static PropositionEditor copyProposition(PropositionDefinitionContract existing){
        // Note: RuleId is not set
        PropositionEditor newProp = new PropositionEditor();
        //newProp.setId( getNewPropId() );
        newProp.setDescription(existing.getDescription());
        newProp.setPropositionTypeCode(existing.getPropositionTypeCode());
        newProp.setTypeId(existing.getTypeId());
        newProp.setCompoundOpCode(existing.getCompoundOpCode());
        // parameters
        List<PropositionParameterEditor> newParms = new ArrayList<PropositionParameterEditor>();
        for (PropositionParameterContract parm : existing.getParameters()){
            PropositionParameterEditor p = new PropositionParameterEditor();
            //p.setId(getNewPropParameterId());
            p.setParameterType(parm.getParameterType());
            p.setPropId(parm.getPropId());
            p.setSequenceNumber(parm.getSequenceNumber());
            p.setValue(parm.getValue());
            newParms.add(p);
        }
        newProp.setParameters(newParms);
        // compoundComponents
        List<PropositionEditor>  newCompoundComponents = new ArrayList<PropositionEditor>();
        for (PropositionDefinitionContract component : existing.getCompoundComponents()){
            PropositionEditor newComponent = copyProposition(component);
            newCompoundComponents.add(newComponent);
        }
        newProp.setCompoundEditors(newCompoundComponents);
        return newProp;
    }

    public String getNewTermDescription() {
        return newTermDescription;
    }
}
