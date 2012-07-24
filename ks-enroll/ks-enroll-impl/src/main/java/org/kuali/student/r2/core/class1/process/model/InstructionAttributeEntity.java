package org.kuali.student.r2.core.class1.process.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_PROCESS_INSTRN_ATTR")
public class InstructionAttributeEntity extends BaseAttributeEntityNew<InstructionEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private InstructionEntity owner;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public InstructionAttributeEntity () {
    }

    public InstructionAttributeEntity(String key, String value) {
        super(key, value);
    }

    public InstructionAttributeEntity(Attribute att) {
        super(att);
    }

    public InstructionAttributeEntity(Attribute att, InstructionEntity owner) {
        super(att);
        setOwner(owner);
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    @Override
    public void setOwner(InstructionEntity owner) {
        this.owner = owner;

    }

    @Override
    public InstructionEntity getOwner() {
        return owner;
    }
}
