package org.kuali.student.myplan.academicplan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.BaseEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSPL_LRNG_PLAN_ATTR")
public class LearningPlanAttributeEntity extends BaseEntity implements AttributeEntity {

    @Column(name = "ATTR_KEY")
    private String key;

    @Column(name = "ATTR_VALUE", columnDefinition="LONG")
    private String value;

    @ManyToOne
    @JoinColumn (name="OWNER_ID")
    private LearningPlanEntity owner;

    public LearningPlanAttributeEntity() {}

    public LearningPlanAttributeEntity(Attribute att, LearningPlanEntity owner) {
        this.setId(att.getId());

        this.fromDto(att);

        this.owner = owner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LearningPlanEntity getOwner() {
		return owner;
	}


	public void setOwner(LearningPlanEntity owner) {
		this.owner = owner;
	}

    public AttributeInfo toDto() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setId(this.getId());
        attributeInfo.setKey(this.getKey());
        attributeInfo.setValue(this.getValue());
        return attributeInfo;
    }

    public void fromDto(Attribute info) {
    	setKey(info.getKey());
    	setValue(info.getValue());
	}

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseAttributeEntityNew [id=");
		builder.append(getId());
		builder.append(", key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append(", owner=");
		builder.append(owner);
		builder.append("]");
		return builder.toString();
    }

}
