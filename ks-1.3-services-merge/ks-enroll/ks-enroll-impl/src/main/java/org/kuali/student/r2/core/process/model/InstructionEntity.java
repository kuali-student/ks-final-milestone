package org.kuali.student.r2.core.process.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.infc.Instruction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_PROCESS_INSTRN")
public class InstructionEntity extends MetaEntity implements AttributeOwner<InstructionAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "PROCESS_INSTRN_TYPE", nullable = false)
    private String instructionType;

    @Column(name = "PROCESS_INSTRN_STATE", nullable = false)
    private String instructionState;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    // Note: in the ERD and DDL this looks like an @ManyToOne but we use a String instead because we don't want JPA to recursively load
    // all of the related data.
    @Column(name = "PROCESS_ID", nullable = false)
    private String processId;

    // Note: in the ERD and DDL this looks like an @ManyToOne but we use a String instead because we don't want JPA to recursively load
    // all of the related data.
    @Column(name = "CHECK_ID", nullable = false)
    private String checkId;

    @Column(name = "APPLD_POPULATION_ID")
    private String appliedPopulationId;

    @Column(name = "MESG_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String messagePlain;

    @Column(name = "MESG_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String messageFormatted;

    @Column(name = "POSITION")
    private int position;

    @Column(name = "WARNING_IND")
    private boolean warning;

    @Column(name = "CONT_ON_FAILED_IND")
    private boolean continueOnFail;

    @Column(name = "EXEMPTIBLE_IND")
    private boolean exemptible;

    @Transient
    private List<String> appliedAtpTypes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<InstructionAttributeEntity> attributes = new HashSet<InstructionAttributeEntity>();

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public InstructionEntity() {}

    public InstructionEntity(Instruction instruction) {
        super(instruction);
        this.setId(instruction.getId());
        this.processId = instruction.getProcessKey();
        this.checkId = instruction.getCheckId();
        this.instructionType = instruction.getTypeKey();
        this.fromDTO (instruction);
    }

    public void fromDTO(Instruction instruction) {
        this.instructionState = instruction.getStateKey();
        this.effectiveDate = instruction.getEffectiveDate();
        this.expirationDate = instruction.getExpirationDate();
        this.appliedPopulationId = instruction.getAppliedPopulationId();
        if (instruction.getMessage() != null) {
            this.messageFormatted = instruction.getMessage().getFormatted();
            this.messagePlain = instruction.getMessage().getPlain();
        } else {
            this.messageFormatted = null;
            this.messagePlain = null;
        }
        this.position = instruction.getPosition();
        this.warning = instruction.getIsWarning();
        this.continueOnFail = instruction.getContinueOnFail();
        this.exemptible = instruction.getIsExemptible();
        this.appliedAtpTypes = new ArrayList<String>();
        for (String atpType : instruction.getAppliedAtpTypeKeys()) {
            this.appliedAtpTypes.add(atpType);
        }
        this.setAttributes(new HashSet<InstructionAttributeEntity>());
        for (Attribute att : instruction.getAttributes()) {
            this.getAttributes().add(new InstructionAttributeEntity(att, this));
        }
    }

    /**
     * @return Process Instruction DTO
     */
    public InstructionInfo toDto() {
        InstructionInfo instructionInfo = new InstructionInfo();
        instructionInfo.setMeta(super.toDTO());
        instructionInfo.setId(getId());
        instructionInfo.setProcessKey(processId);
        instructionInfo.setCheckId(checkId);
        instructionInfo.setTypeKey(instructionType);
        instructionInfo.setStateKey(instructionState);
        instructionInfo.setEffectiveDate(effectiveDate);
        instructionInfo.setExpirationDate(expirationDate);
        instructionInfo.setAppliedPopulationId(appliedPopulationId);
        instructionInfo.setMessage(new RichTextHelper().toRichTextInfo(messagePlain, messageFormatted));
        instructionInfo.setPosition(position);
        instructionInfo.setIsWarning(warning);
        instructionInfo.setContinueOnFail(continueOnFail);
        instructionInfo.setIsExemptible(exemptible);
        List<String> appliedAtpTypeKeys = new ArrayList<String>();
        if (getAppliedAtpTypes() != null) {
            for (String atpType : getAppliedAtpTypes()) {
                appliedAtpTypeKeys.add(atpType);
            }
        }
        instructionInfo.setAppliedAtpTypeKeys(appliedAtpTypeKeys);
        List<AttributeInfo> attributes = instructionInfo.getAttributes();
        if (getAttributes() != null) {
            for (InstructionAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        instructionInfo.setAttributes(attributes);
        return instructionInfo;
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    public String getInstructionState() {
        return instructionState;
    }

    public void setInstructionState(String instructionState) {
        this.instructionState = instructionState;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getAppliedPopulationId() {
        return appliedPopulationId;
    }

    public void setAppliedPopulationId(String appliedPopulationId) {
        this.appliedPopulationId = appliedPopulationId;
    }

    public String getMessagePlain() {
        return messagePlain;
    }

    public void setMessagePlain(String messagePlain) {
        this.messagePlain = messagePlain;
    }

    public String getMessageFormatted() {
        return messageFormatted;
    }

    public void setMessageFormatted(String messageFormatted) {
        this.messageFormatted = messageFormatted;
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

    public boolean isExemptible() {
        return exemptible;
    }

    public void setExemptible(boolean exemptible) {
        this.exemptible = exemptible;
    }

    public List<String> getAppliedAtpTypes() {
        return appliedAtpTypes;
    }

    public void setAppliedAtpTypes(List<String> appliedAtpTypes) {
        this.appliedAtpTypes = appliedAtpTypes;
    }

    @Override
    public void setAttributes(Set<InstructionAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<InstructionAttributeEntity> getAttributes() {
        return attributes;
    }
}
