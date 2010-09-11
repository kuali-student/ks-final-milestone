
package org.kuali.student.wsdl.proposal;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for whenConstraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="whenConstraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="values" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="valuePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="constraint" type="{http://student.kuali.org/wsdl/proposal}constraint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "whenConstraint", namespace = "http://student.kuali.org/wsdl/proposal", propOrder = {
    "values",
    "valuePath",
    "constraint"
})
public class WhenConstraint {

    @XmlElement(nillable = true)
    protected List<Object> values;
    protected String valuePath;
    protected Constraint constraint;

    /**
     * Gets the value of the values property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the values property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getValues() {
        if (values == null) {
            values = new ArrayList<Object>();
        }
        return this.values;
    }

    /**
     * Gets the value of the valuePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuePath() {
        return valuePath;
    }

    /**
     * Sets the value of the valuePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuePath(String value) {
        this.valuePath = value;
    }

    /**
     * Gets the value of the constraint property.
     * 
     * @return
     *     possible object is
     *     {@link Constraint }
     *     
     */
    public Constraint getConstraint() {
        return constraint;
    }

    /**
     * Sets the value of the constraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Constraint }
     *     
     */
    public void setConstraint(Constraint value) {
        this.constraint = value;
    }

}
