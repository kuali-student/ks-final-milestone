package org.kuali.student.r2.core.process.model;


import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpTypeEntity;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.infc.Instruction;
import org.kuali.student.r2.core.process.model1.CheckEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstructionEntity extends MetaEntity implements AttributeOwner<InstructionAttributeEntity> {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private InstructionTypeEntity instructionType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity instructionState;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PROCESS_ID")
    private ProcessEntity process;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "CHECK_ID")
//    private CheckEntity check;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MESSAGE")
    private InstructionMessageEntity message;

    @Column(name = "POSITION")
    private int position;

    @Column(name = "IS_WARNING")
    private boolean warning;

    @Column(name = "CONTINUE_ON_FAIL")
    private boolean continueOnFail;

    @Column(name = "IS_EXEMPTABLE")
    private boolean exemptable;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "KSEN_INSTR_PRSN_RELTN", joinColumns = @JoinColumn(name = "INSTR_ID"), inverseJoinColumns = @JoinColumn(name = "PERSON_ID"))
    private List<PersonImpl> appliedPopulation;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "KSEN_INSTR_PRSN_RELTN", joinColumns = @JoinColumn(name = "INSTR_ID"), inverseJoinColumns = @JoinColumn(name = "ATP_TYPE_ID"))
    private List<AtpTypeEntity> appliedAtpTypes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval = true)
    private List<InstructionAttributeEntity> attributes = new ArrayList<InstructionAttributeEntity>();


    public InstructionEntity() {
    }

    public InstructionEntity(Instruction dto) {
        super(dto);

        setId(dto.getId());
        setContinueOnFail(dto.getContinueOnFail());
        setExemptable(dto.getIsExemptable());
        setPosition(dto.getPosition());

        if (dto.getExpirationDate() != null){
            setExpirationDate(dto.getExpirationDate());
        }

        if (dto.getEffectiveDate() != null){
            setEffectiveDate(dto.getEffectiveDate());
        }

        if (dto.getMessage() != null) {
            this.setMessage(new InstructionMessageEntity(dto.getMessage()));
        }
        this.setAttributes(new ArrayList<InstructionAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new InstructionAttributeEntity(att, this));
            }
        }

    }

    public InstructionInfo toDto() {

        InstructionInfo dto = new InstructionInfo();

        dto.setId(getId());
        dto.setContinueOnFail(isContinueOnFail());
        dto.setPosition(getPosition());
        dto.setIsExemptable(isExemptable());

        if (getEffectiveDate() != null){
            dto.setEffectiveDate(getEffectiveDate());
        }

        if (getExpirationDate() != null){
            dto.setExpirationDate(getExpirationDate());
        }

        if (getInstructionState() != null){
            dto.setStateKey(getInstructionState().getId());
        }

        if (getProcess() != null){
            dto.setProcessKey(getProcess().getId());
        }

//        if (getCheck() != null){
//            dto.setCheckKey(getCheck().getId());
//        }

        if (getInstructionType() != null){
            dto.setTypeKey(getInstructionType().getId());
        }

        List<String> appliedPopulation = new ArrayList<String>();
        if (getAppliedPopulation() != null){
            for (PersonImpl person : getAppliedPopulation()) {
                appliedPopulation.add(person.getEntityId());
            }
        }
        dto.setAppliedPopulationKeys(appliedPopulation);

        List<String> appliedAtpTypeKeys = new ArrayList<String>();
        if (getAppliedAtpTypes() != null){
            for (AtpTypeEntity atpTypeEntity : getAppliedAtpTypes()) {
                appliedAtpTypeKeys.add(atpTypeEntity.getId());
            }
        }
        dto.setAppliedAtpTypeKeys(appliedAtpTypeKeys);

        dto.setMeta(super.toDTO());
        if (getMessage() != null){
            dto.setMessage(message.toDto());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (InstructionAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        dto.setAttributes(atts);

        return dto;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public InstructionTypeEntity getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(InstructionTypeEntity instructionType) {
        this.instructionType = instructionType;
    }

    public StateEntity getInstructionState() {
        return instructionState;
    }

    public void setInstructionState(StateEntity instructionState) {
        this.instructionState = instructionState;
    }

    public ProcessEntity getProcess() {
        return process;
    }

    public void setProcess(ProcessEntity process) {
        this.process = process;
    }

//    public CheckEntity getCheck() {
//        return check;
//    }
//
//    public void setCheck(CheckEntity check) {
//        this.check = check;
//    }

    public InstructionMessageEntity getMessage() {
        return message;
    }

    public void setMessage(InstructionMessageEntity message) {
        this.message = message;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public boolean isContinueOnFail() {
        return continueOnFail;
    }

    public void setContinueOnFail(boolean continueOnFail) {
        this.continueOnFail = continueOnFail;
    }

    public boolean isExemptable() {
        return exemptable;
    }

    public void setExemptable(boolean exemptable) {
        this.exemptable = exemptable;
    }

    public List<PersonImpl> getAppliedPopulation() {
        return appliedPopulation;
    }

    public void setAppliedPopulation(List<PersonImpl> appliedPopulation) {
        this.appliedPopulation = appliedPopulation;
    }

    public List<AtpTypeEntity> getAppliedAtpTypes() {
        return appliedAtpTypes;
    }

    public void setAppliedAtpTypes(List<AtpTypeEntity> appliedAtpTypes) {
        this.appliedAtpTypes = appliedAtpTypes;
    }

    @Override
    public void setAttributes(List<InstructionAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<InstructionAttributeEntity> getAttributes() {
        return attributes;
    }
}
