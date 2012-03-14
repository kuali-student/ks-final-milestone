package org.kuali.student.r2.core.class1.process.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_PROCESS_ATTR")
public class ProcessAttributeEntity extends BaseAttributeEntity<ProcessEntity> {

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

    @Override
    public void setOwner(ProcessEntity owner) {
        this.owner = owner;
    }

    @Override
    public ProcessEntity getOwner() {
        return owner;
    }
}
