
package org.kuali.student.wsdl.enumerationmanagementservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.wsdl.enumerationmanagement.EnumeratedValueInfo;


/**
 * <p>Java class for updateEnumeratedValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateEnumeratedValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enumerationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enumeratedValue" type="{http://student.kuali.org/wsdl/enumerationmanagement}enumeratedValueInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateEnumeratedValue", propOrder = {
    "enumerationKey",
    "code",
    "enumeratedValue"
})
public class UpdateEnumeratedValue {

    protected String enumerationKey;
    protected String code;
    protected EnumeratedValueInfo enumeratedValue;

    /**
     * Gets the value of the enumerationKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnumerationKey() {
        return enumerationKey;
    }

    /**
     * Sets the value of the enumerationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnumerationKey(String value) {
        this.enumerationKey = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the enumeratedValue property.
     * 
     * @return
     *     possible object is
     *     {@link EnumeratedValueInfo }
     *     
     */
    public EnumeratedValueInfo getEnumeratedValue() {
        return enumeratedValue;
    }

    /**
     * Sets the value of the enumeratedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumeratedValueInfo }
     *     
     */
    public void setEnumeratedValue(EnumeratedValueInfo value) {
        this.enumeratedValue = value;
    }

}
