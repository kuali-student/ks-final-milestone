
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createProposalDocRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createProposalDocRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proposalDocRelationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "createProposalDocRelation", propOrder = {
    "proposalDocRelationType",
    "documentId",
    "proposalId",
    "proposalDocRelationInfo"
})
public class CreateProposalDocRelation {

    protected String proposalDocRelationType;
    protected String documentId;
    protected String proposalId;
    protected ProposalDocRelationInfo proposalDocRelationInfo;

    /**
     * Gets the value of the proposalDocRelationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalDocRelationType() {
        return proposalDocRelationType;
    }

    /**
     * Sets the value of the proposalDocRelationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalDocRelationType(String value) {
        this.proposalDocRelationType = value;
    }

    /**
     * Gets the value of the documentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentId(String value) {
        this.documentId = value;
    }

    /**
     * Gets the value of the proposalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalId() {
        return proposalId;
    }

    /**
     * Sets the value of the proposalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalId(String value) {
        this.proposalId = value;
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
