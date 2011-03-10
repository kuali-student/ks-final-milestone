package org.kuali.student.enrollment.lpr.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Igor
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DynamicAttributeInfo implements Serializable {

    @XmlElement
    private String key;

    @XmlElement
    private String value;

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
