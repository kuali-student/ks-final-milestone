package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ISSUE_ATTR")
public class IssueAttributeEntity extends BaseAttributeEntity<IssueEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private IssueEntity owner;

    public IssueAttributeEntity () {
    }
    
    public IssueAttributeEntity(String key, String value) {
        super(key, value);
    }

    public IssueAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(IssueEntity owner) {
        this.owner = owner;
    }

    @Override
    public IssueEntity getOwner() {
        return owner;
    }
}
