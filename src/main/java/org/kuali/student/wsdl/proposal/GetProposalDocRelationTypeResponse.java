
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getProposalDocRelationTypeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getProposalDocRelationTypeResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://student.kuali.org/wsdl/proposal}proposalDocRelationTypeInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProposalDocRelationTypeResponse", propOrder = {
    "_return"
})
public class GetProposalDocRelationTypeResponse {

    @XmlElement(name = "return")
    protected ProposalDocRelationTypeInfo _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ProposalDocRelationTypeInfo }
     *     
     */
    public ProposalDocRelationTypeInfo getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProposalDocRelationTypeInfo }
     *     
     */
    public void setReturn(ProposalDocRelationTypeInfo value) {
        this._return = value;
    }

}
