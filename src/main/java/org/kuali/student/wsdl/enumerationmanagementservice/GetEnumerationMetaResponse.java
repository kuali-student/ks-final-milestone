
package org.kuali.student.wsdl.enumerationmanagementservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.wsdl.enumerationmanagement.EnumerationMetaInfo;


/**
 * <p>Java class for getEnumerationMetaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getEnumerationMetaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://student.kuali.org/wsdl/enumerationmanagement}enumerationMetaInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEnumerationMetaResponse", propOrder = {
    "_return"
})
public class GetEnumerationMetaResponse {

    @XmlElement(name = "return")
    protected EnumerationMetaInfo _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link EnumerationMetaInfo }
     *     
     */
    public EnumerationMetaInfo getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumerationMetaInfo }
     *     
     */
    public void setReturn(EnumerationMetaInfo value) {
        this._return = value;
    }

}
