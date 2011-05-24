package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATPMSOTNE_RELTN_ATTR")
public class AtpMilestoneRelationAttributeEntity extends BaseAttributeEntity {

    public AtpMilestoneRelationAttributeEntity() {
    }
    
    public AtpMilestoneRelationAttributeEntity(Attribute attribute) {
        super(attribute);
    }
    
    public AtpMilestoneRelationAttributeEntity(String name, String value) {
        super(name, value);
    }
}
