package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ISSUE_ATTR")
public class IssueAttributeEntity extends BaseAttributeEntity<IssueEntity> {
    public IssueAttributeEntity () {
    }
    
    public IssueAttributeEntity(String key, String value) {
        super(key, value);
    }

    public IssueAttributeEntity(Attribute att) {
        super(att);
    }
}
