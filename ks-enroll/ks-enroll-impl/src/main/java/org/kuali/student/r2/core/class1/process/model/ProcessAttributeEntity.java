package org.kuali.student.r2.core.class1.process.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_PROCESS_ATTR")
public class ProcessAttributeEntity extends BaseAttributeEntityNew<ProcessEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private ProcessEntity owner;

    public ProcessAttributeEntity () {}

    public ProcessAttributeEntity(String key, String value) {
        super(key, value);
    }

    public ProcessAttributeEntity(Attribute att) {
        super(att);
    }

    public ProcessAttributeEntity(Attribute att, ProcessEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(ProcessEntity owner) {
        this.owner = owner;
    }

    @Override
    public ProcessEntity getOwner() {
        return owner;
    }
}
