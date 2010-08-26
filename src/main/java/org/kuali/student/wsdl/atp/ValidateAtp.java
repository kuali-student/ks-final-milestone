
package org.kuali.student.wsdl.atp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateAtp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateAtp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="atpInfo" type="{http://student.kuali.org/wsdl/atp}atpInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateAtp", propOrder = {
    "validationType",
    "atpInfo"
})
public class ValidateAtp {

    protected String validationType;
    protected AtpInfo atpInfo;

    /**
     * Gets the value of the validationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Sets the value of the validationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationType(String value) {
        this.validationType = value;
    }

    /**
     * Gets the value of the atpInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AtpInfo }
     *     
     */
    public AtpInfo getAtpInfo() {
        return atpInfo;
    }

    /**
     * Sets the value of the atpInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AtpInfo }
     *     
     */
    public void setAtpInfo(AtpInfo value) {
        this.atpInfo = value;
    }

}
