package org.kuali.student.enrollment.lpr.dto;

import org.kuali.student.core.dto.Idable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Igor
 */
public class TypeStateInfo implements Serializable, Idable {

    @XmlAttribute(name = "key")
    private String id;

    @XmlElement
    private String name;

    @XmlElement(name = "desc")
    private String descr;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private List<DynamicAttributeInfo> dynamicAttributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<DynamicAttributeInfo> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(List<DynamicAttributeInfo> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }
}
