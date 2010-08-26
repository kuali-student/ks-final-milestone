
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getProposalsByState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getProposalsByState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proposalState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProposalsByState", propOrder = {
    "proposalState",
    "proposalTypeKey"
})
public class GetProposalsByState {

    protected String proposalState;
    protected String proposalTypeKey;

    /**
     * Gets the value of the proposalState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalState() {
        return proposalState;
    }

    /**
     * Sets the value of the proposalState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalState(String value) {
        this.proposalState = value;
    }

    /**
     * Gets the value of the proposalTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalTypeKey() {
        return proposalTypeKey;
    }

    /**
     * Sets the value of the proposalTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalTypeKey(String value) {
        this.proposalTypeKey = value;
    }

}
