package org.kuali.student.r2.common.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseAttribute extends BaseEntity{
	@Column(name = "KEY")
	private String key;
	
	@Column(name = "VALUE",length=KSEntityConstants.LONG_TEXT_LENGTH)
	private String value;

	@Column(name="REF_OBJ_URI")
	private String refObjectURI;

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

    public String getRefObjectURI() {
        return refObjectURI;
    }

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }
		
}
