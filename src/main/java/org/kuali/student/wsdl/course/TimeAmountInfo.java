
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for timeAmountInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="timeAmountInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="atpDurationTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeQuantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timeAmountInfo", propOrder = {
    "atpDurationTypeKey",
    "timeQuantity"
})
public class TimeAmountInfo {

    protected String atpDurationTypeKey;
    protected Integer timeQuantity;

    /**
     * Gets the value of the atpDurationTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtpDurationTypeKey() {
        return atpDurationTypeKey;
    }

    /**
     * Sets the value of the atpDurationTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtpDurationTypeKey(String value) {
        this.atpDurationTypeKey = value;
    }

    /**
     * Gets the value of the timeQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeQuantity() {
        return timeQuantity;
    }

    /**
     * Sets the value of the timeQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeQuantity(Integer value) {
        this.timeQuantity = value;
    }

}
