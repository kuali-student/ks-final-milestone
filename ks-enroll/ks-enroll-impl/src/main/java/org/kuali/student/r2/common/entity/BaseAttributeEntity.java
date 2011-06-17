package org.kuali.student.r2.common.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.infc.Attribute;

@MappedSuperclass
public class BaseAttributeEntity extends BaseEntity {
	
	@Column(name = "\"KEY\"")
	private String key;
	
	@Column(name = "VALUE",length=KSEntityConstants.LONG_TEXT_LENGTH)
	private String value;

	public BaseAttributeEntity() {
	}
	
    public BaseAttributeEntity(String key, String value) {
    	this.key = key;
    	this.value = value;
	}

	public BaseAttributeEntity(Attribute att) {
		this.setId(att.getId());
		this.key = att.getKey();
		this.value = att.getValue();
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

	public AttributeInfo toDto() {
	    AttributeInfo attributeInfo = new AttributeInfo();
		attributeInfo.setId(this.getId());
		attributeInfo.setKey(this.getKey());
		attributeInfo.setValue(this.getValue());
		return attributeInfo;
	}

}
