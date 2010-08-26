
package org.kuali.student.wsdl.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for occursConstraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occursConstraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="require" type="{http://student.kuali.org/wsdl/dictionary}requireConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="occurs" type="{http://student.kuali.org/wsdl/dictionary}occursConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occursConstraint", propOrder = {
    "require",
    "occurs"
})
public class OccursConstraint {

    protected List<RequireConstraint> require;
    protected List<OccursConstraint> occurs;
    @XmlAttribute
    protected Integer min;
    @XmlAttribute
    protected Integer max;

    /**
     * Gets the value of the require property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the require property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequire().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequireConstraint }
     * 
     * 
     */
    public List<RequireConstraint> getRequire() {
        if (require == null) {
            require = new ArrayList<RequireConstraint>();
        }
        return this.require;
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
     * {@link OccursConstraint }
     * 
     * 
     */
    public List<OccursConstraint> getOccurs() {
        if (occurs == null) {
            occurs = new ArrayList<OccursConstraint>();
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
