
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getProposalDocRelationsByType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getProposalDocRelationsByType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proposalDocRelationTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProposalDocRelationsByType", propOrder = {
    "proposalDocRelationTypeKey"
})
public class GetProposalDocRelationsByType {

    protected String proposalDocRelationTypeKey;

    /**
     * Gets the value of the proposalDocRelationTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalDocRelationTypeKey() {
        return proposalDocRelationTypeKey;
    }

    /**
     * Sets the value of the proposalDocRelationTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalDocRelationTypeKey(String value) {
        this.proposalDocRelationTypeKey = value;
    }

}
