package org.kuali.rice.krms.dto;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.impl.ui.TermParameter;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private String newTermDescription = "new term " + UUID.randomUUID().toString();

    public PropositionEditor() {
        super();
    }

    /**
     * Converts a immutable object to it's mutable bo counterpart
     *
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
        for (PropositionParameterContract parm : definition.getParameters()) {
            this.parameters.add(new PropositionParameterEditor(parm));
        }
        this.compoundOpCode = definition.getCompoundOpCode();
        this.compoundEditors = new ArrayList<PropositionEditor>();
        for (PropositionDefinitionContract prop : definition.getCompoundComponents()) {
            this.compoundEditors.add(createPropositionEditor(prop));
        }
        this.versionNumber = definition.getVersionNumber();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCompoundOpCode() {
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

    public List<PropositionParameterEditor> getParameters() {
        return parameters;
    }

    public String getTypeId() {
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

    public void setTypeId(String typeId) {
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

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition) {
        return new PropositionEditor(definition);
    }

    public String getNewTermDescription() {
        return newTermDescription;
    }
}
