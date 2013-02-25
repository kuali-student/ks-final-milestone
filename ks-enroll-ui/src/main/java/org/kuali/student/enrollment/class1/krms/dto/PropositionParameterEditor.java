package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/15
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionParameterEditor implements PropositionParameterContract, Serializable {

    private String id;
    private String propId;
    private String value;
    private String parameterType;
    private Integer sequenceNumber;
    private Long versionNumber;

    public PropositionParameterEditor(){
        super();
    }

    /**
     * Converts a immutable object to it's mutable bo counterpart
     * @param definition immutable object
     * @return the mutable bo
     */
    public PropositionParameterEditor(PropositionParameterContract definition) {
        this.id = definition.getId();
        this.propId = definition.getPropId();
        this.value = definition.getValue();
        this.parameterType = definition.getParameterType();
        this.sequenceNumber = definition.getSequenceNumber();
        this.versionNumber = definition.getVersionNumber();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    @Override
    public String getPropId() {
        return propId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public String getParameterType() {
        return parameterType;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }
}
