
package org.kuali.student.wsdl.dictionary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for field complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="field">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fieldDescriptor" type="{http://student.kuali.org/wsdl/dictionary}fieldDescriptor" minOccurs="0"/>
 *         &lt;element name="constraintDescriptor" type="{http://student.kuali.org/wsdl/dictionary}constraintDescriptor" minOccurs="0"/>
 *         &lt;element name="selector" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dynamic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "field", propOrder = {
    "fieldDescriptor",
    "constraintDescriptor",
    "selector",
    "dynamic"
})
public class Field {

    protected FieldDescriptor fieldDescriptor;
    protected ConstraintDescriptor constraintDescriptor;
    protected boolean selector;
    protected boolean dynamic;
    @XmlAttribute(required = true)
    protected String key;
    @XmlAttribute
    protected String id;

    /**
     * Gets the value of the fieldDescriptor property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    /**
     * Sets the value of the fieldDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setFieldDescriptor(FieldDescriptor value) {
        this.fieldDescriptor = value;
    }

    /**
     * Gets the value of the constraintDescriptor property.
     * 
     * @return
     *     possible object is
     *     {@link ConstraintDescriptor }
     *     
     */
    public ConstraintDescriptor getConstraintDescriptor() {
        return constraintDescriptor;
    }

    /**
     * Sets the value of the constraintDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConstraintDescriptor }
     *     
     */
    public void setConstraintDescriptor(ConstraintDescriptor value) {
        this.constraintDescriptor = value;
    }

    /**
     * Gets the value of the selector property.
     * 
     */
    public boolean isSelector() {
        return selector;
    }

    /**
     * Sets the value of the selector property.
     * 
     */
    public void setSelector(boolean value) {
        this.selector = value;
    }

    /**
     * Gets the value of the dynamic property.
     * 
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * Sets the value of the dynamic property.
     * 
     */
    public void setDynamic(boolean value) {
        this.dynamic = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
