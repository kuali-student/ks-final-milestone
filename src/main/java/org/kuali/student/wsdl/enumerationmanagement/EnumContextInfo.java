
package org.kuali.student.wsdl.enumerationmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for enumContextInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="enumContextInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contextValueDescriptor" type="{http://student.kuali.org/wsdl/enumerationmanagement}fieldDescriptorInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enumContextInfo", propOrder = {
    "contextValueDescriptor"
})
public class EnumContextInfo {

    protected FieldDescriptorInfo contextValueDescriptor;
    @XmlAttribute(name = "type")
    protected String type;

    /**
     * Gets the value of the contextValueDescriptor property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptorInfo }
     *     
     */
    public FieldDescriptorInfo getContextValueDescriptor() {
        return contextValueDescriptor;
    }

    /**
     * Sets the value of the contextValueDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptorInfo }
     *     
     */
    public void setContextValueDescriptor(FieldDescriptorInfo value) {
        this.contextValueDescriptor = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
