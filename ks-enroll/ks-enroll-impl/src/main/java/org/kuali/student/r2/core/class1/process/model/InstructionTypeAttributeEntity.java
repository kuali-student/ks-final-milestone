package org.kuali.student.r2.core.class1.process.model;


import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_INSTR_TYPE_ATTR")
public class InstructionTypeAttributeEntity extends BaseAttributeEntity<InstructionTypeEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private InstructionTypeEntity owner;

    public InstructionTypeAttributeEntity () {
    }

    public InstructionTypeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public InstructionTypeAttributeEntity(Attribute att, InstructionTypeEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(InstructionTypeEntity owner) {
        this.owner = owner;

    }

    @Override
    public InstructionTypeEntity getOwner() {
        return owner;
    }
}
