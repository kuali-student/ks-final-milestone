
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateProposalDocRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateProposalDocRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalDocRelationInfo" type="{http://student.kuali.org/wsdl/proposal}proposalDocRelationInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateProposalDocRelation", propOrder = {
    "validationType",
    "proposalDocRelationInfo"
})
public class ValidateProposalDocRelation {

    protected String validationType;
    protected ProposalDocRelationInfo proposalDocRelationInfo;

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
     * Gets the value of the proposalDocRelationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProposalDocRelationInfo }
     *     
     */
    public ProposalDocRelationInfo getProposalDocRelationInfo() {
        return proposalDocRelationInfo;
    }

    /**
     * Sets the value of the proposalDocRelationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProposalDocRelationInfo }
     *     
     */
    public void setProposalDocRelationInfo(ProposalDocRelationInfo value) {
        this.proposalDocRelationInfo = value;
    }

}
