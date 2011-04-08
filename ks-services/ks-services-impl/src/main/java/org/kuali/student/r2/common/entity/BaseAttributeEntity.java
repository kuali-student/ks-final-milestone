package org.kuali.student.r2.common.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.kuali.student.common.infc.Attribute;
import org.kuali.student.core.entity.BaseEntity;
import org.kuali.student.core.entity.KSEntityConstants;

@MappedSuperclass
public class BaseAttributeEntity extends BaseEntity {
	
	@Column(name = "KEY")
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
}
