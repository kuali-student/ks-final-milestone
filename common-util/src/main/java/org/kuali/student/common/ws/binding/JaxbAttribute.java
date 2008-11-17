package org.kuali.student.common.ws.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attribute", propOrder = {
        "type", "value" })
public class JaxbAttribute {
      @XmlAttribute
      String type;
      @XmlAttribute
      String value;
    /**
     *
     */
    public JaxbAttribute() {
        super();
    }
    /**
     * @param name
     * @param value
     */
    public JaxbAttribute(String type, String value) {
        super();
        this.type = type;
        this.value = value;
    }
}