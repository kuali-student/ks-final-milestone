package edu.umd.ks.poc.lum.util.adapter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attributes", propOrder = {
        "type", "values" })
public class JaxbAttributes {
      @XmlAttribute
      String type;
      @XmlElement
      List<String> values;
    /**
     *
     */
    public JaxbAttributes() {
        super();
    }
    /**
     * @param name
     * @param value
     */
    public JaxbAttributes(String type, List<String> values) {
        super();
        this.type = type;
        this.values = values;
    }
}