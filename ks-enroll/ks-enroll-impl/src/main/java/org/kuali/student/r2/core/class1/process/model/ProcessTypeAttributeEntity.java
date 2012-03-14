package org.kuali.student.r2.core.class1.process.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_PROCESS_TYPE_ATTR")
public class ProcessTypeAttributeEntity extends BaseAttributeEntity<ProcessTypeEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private ProcessTypeEntity owner;

    public ProcessTypeAttributeEntity () {
    }

    public ProcessTypeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public ProcessTypeAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(ProcessTypeEntity owner) {
        this.owner = owner;
    }

    @Override
    public ProcessTypeEntity getOwner() {
        return owner;
    }
}