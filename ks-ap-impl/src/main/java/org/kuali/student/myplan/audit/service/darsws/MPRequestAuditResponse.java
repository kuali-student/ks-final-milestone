package org.kuali.student.myplan.audit.service.darsws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MPRequestAuditResult" type="{http://tempuri.org/}MPAuditResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mpRequestAuditResult"
})
@XmlRootElement(name = "MPRequestAuditResponse")
public class MPRequestAuditResponse {

    @XmlElement(name = "MPRequestAuditResult")
    protected MPAuditResponse mpRequestAuditResult;

    /**
     * Gets the value of the mpRequestAuditResult property.
     * 
     * @return
     *     possible object is
     *     {@link MPAuditResponse }
     *     
     */
    public MPAuditResponse getMPRequestAuditResult() {
        return mpRequestAuditResult;
    }

    /**
     * Sets the value of the mpRequestAuditResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MPAuditResponse }
     *     
     */
    public void setMPRequestAuditResult(MPAuditResponse value) {
        this.mpRequestAuditResult = value;
    }

}
