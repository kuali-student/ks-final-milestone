
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteProposalDocRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteProposalDocRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proposalDocRelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteProposalDocRelation", propOrder = {
    "proposalDocRelationId"
})
public class DeleteProposalDocRelation {

    protected String proposalDocRelationId;

    /**
     * Gets the value of the proposalDocRelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalDocRelationId() {
        return proposalDocRelationId;
    }

    /**
     * Sets the value of the proposalDocRelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalDocRelationId(String value) {
        this.proposalDocRelationId = value;
    }

}
