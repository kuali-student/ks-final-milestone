
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateProposal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateProposal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalInfo" type="{http://student.kuali.org/wsdl/proposal}proposalInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateProposal", propOrder = {
    "validationType",
    "proposalInfo"
})
public class ValidateProposal {

    protected String validationType;
    protected ProposalInfo proposalInfo;

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
     * Gets the value of the proposalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProposalInfo }
     *     
     */
    public ProposalInfo getProposalInfo() {
        return proposalInfo;
    }

    /**
     * Sets the value of the proposalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProposalInfo }
     *     
     */
    public void setProposalInfo(ProposalInfo value) {
        this.proposalInfo = value;
    }

}
