package org.kuali.student.core.bo;

import javax.persistence.Column;

public abstract class KsAttributeBusinessObjectBase extends KsBusinessObjectBase {

    private static final long serialVersionUID = 3364161804560338897L;

    @Column(name="ATTR_NAME")
    private String name;
    
    @Column(name="ATTR_VALUE")
    private String value;
    
    private String ownerId;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

}
