package org.kuali.student.r2.common.entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.infc.Attribute;

@MappedSuperclass
// 9/06/2012 nwright commented this out as the contract DOES specify that you can have 2 entries with the same key
// This was a deliberate change from how R1 Dynamic Attributes worked at the request of an implementing institution 
// The design change was to allow dynamic attributes could implement a list of values more naturally by repeating keys
//
// Also constraints defined here as annotations have no effect except on unit tests because this 
// constraint does not exist in the schemas created by DBAs and generated via IMPEX
// this would make the unit tests be out of sync with the actual implementation
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ATTR_KEY", "OWNER_ID"})})
public abstract class BaseAttributeEntity<T extends AttributeOwner<?>> extends BaseEntity {

    @Column(name = "ATTR_KEY")
    private String key;

    @Column(name = "ATTR_VALUE", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String value;

    @ManyToOne
    @JoinColumn (name="OWNER_ID")
    private T owner;

    public BaseAttributeEntity() {}


    public BaseAttributeEntity(Attribute att, T owner) {
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



    public T getOwner() {
		return owner;
	}


	public void setOwner(T owner) {
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
