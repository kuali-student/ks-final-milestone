package org.kuali.student.r2.core.class1.process.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_INSTR_ATTR")
public class InstructionAttributeEntity extends BaseAttributeEntity<InstructionEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private InstructionEntity owner;

    public InstructionAttributeEntity () {
    }

    public InstructionAttributeEntity(String key, String value) {
        super(key, value);
    }

    public InstructionAttributeEntity(Attribute att, InstructionEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(InstructionEntity owner) {
        this.owner = owner;

    }

    @Override
    public InstructionEntity getOwner() {
        return owner;
    }
}
