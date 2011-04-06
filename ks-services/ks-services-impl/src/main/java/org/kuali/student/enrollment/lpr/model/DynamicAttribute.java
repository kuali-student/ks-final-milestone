package org.kuali.student.enrollment.lpr.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Igor
 */
@Entity
public class DynamicAttribute implements Serializable {
    private static final long serialVersionUID = -8609058228324425537L;

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DynamicType dynamicType;

    @Column(name = "\"key\"")
    private String key;

    private String value;

    public DynamicAttribute() {
    }

    public DynamicAttribute(DynamicType dynamicType, String key, String value) {
        this.dynamicType = dynamicType;
        this.key = key;
        this.value = value;
    }

    public DynamicAttribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DynamicType getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(DynamicType dynamicType) {
        this.dynamicType = dynamicType;
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
