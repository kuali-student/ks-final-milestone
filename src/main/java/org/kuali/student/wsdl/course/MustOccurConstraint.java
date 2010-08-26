
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mustOccurConstraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mustOccurConstraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requiredFields" type="{http://student.kuali.org/wsdl/course}requiredConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="occurs" type="{http://student.kuali.org/wsdl/course}mustOccurConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="min" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="max" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mustOccurConstraint", propOrder = {
    "requiredFields",
    "occurs",
    "min",
    "max"
})
public class MustOccurConstraint {

    protected List<RequiredConstraint> requiredFields;
    protected List<MustOccurConstraint> occurs;
    protected Integer min;
    protected Integer max;

    /**
     * Gets the value of the requiredFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requiredFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequiredFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequiredConstraint }
     * 
     * 
     */
    public List<RequiredConstraint> getRequiredFields() {
        if (requiredFields == null) {
            requiredFields = new ArrayList<RequiredConstraint>();
        }
        return this.requiredFields;
    }

    /**
     * Gets the value of the occurs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occurs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccurs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MustOccurConstraint }
     * 
     * 
     */
    public List<MustOccurConstraint> getOccurs() {
        if (occurs == null) {
            occurs = new ArrayList<MustOccurConstraint>();
        }
        return this.occurs;
    }

    /**
     * Gets the value of the min property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMin(Integer value) {
        this.min = value;
    }

    /**
     * Gets the value of the max property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMax(Integer value) {
        this.max = value;
    }

}
