package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_SOC_ROR_ATTR")
public class SocRolloverResultAttributeEntity extends BaseAttributeEntity<SocRolloverResultEntity> {

	public SocRolloverResultAttributeEntity() {
		super();
	}

	public SocRolloverResultAttributeEntity(Attribute att,
			SocRolloverResultEntity owner) {
		super(att, owner);
	}

    public SocRolloverResultAttributeEntity(String key, String value, SocRolloverResultEntity owner) {
        super(key, value);
        setOwner(owner);
    }

    public SocRolloverResultAttributeEntity(Attribute att, SocRolloverResultEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(SocRolloverResultEntity owner) {
        this.owner = owner;

    }

    @Override
    public SocRolloverResultEntity getOwner() {
        return owner;
    }
}
