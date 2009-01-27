package org.kuali.student.core.ws.binding;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attribute", propOrder = {
        "key", "value" })
public class JaxbAttribute implements Serializable{
	private static final long serialVersionUID = 1L;
	@XmlAttribute
      String key;
      @XmlElement
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
    public JaxbAttribute(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }
}