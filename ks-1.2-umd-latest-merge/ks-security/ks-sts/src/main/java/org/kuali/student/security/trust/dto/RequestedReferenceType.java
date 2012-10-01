
package org.kuali.student.security.trust.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.security.wssecurity.secext.dto.SecurityTokenReferenceType;


/**
 * <p>Java class for RequestedReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestedReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}SecurityTokenReference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestedReferenceType", propOrder = {
    "securityTokenReference"
})
public class RequestedReferenceType {

    @XmlElement(name = "SecurityTokenReference", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", required = true)
    protected SecurityTokenReferenceType securityTokenReference;

    /**
     * Gets the value of the securityTokenReference property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityTokenReferenceType }
     *     
     */
    public SecurityTokenReferenceType getSecurityTokenReference() {
        return securityTokenReference;
    }

    /**
     * Sets the value of the securityTokenReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityTokenReferenceType }
     *     
     */
    public void setSecurityTokenReference(SecurityTokenReferenceType value) {
        this.securityTokenReference = value;
    }

}
