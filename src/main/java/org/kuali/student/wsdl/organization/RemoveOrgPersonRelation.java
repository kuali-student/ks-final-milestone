
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeOrgPersonRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeOrgPersonRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgPersonRelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeOrgPersonRelation", propOrder = {
    "orgPersonRelationId"
})
public class RemoveOrgPersonRelation {

    protected String orgPersonRelationId;

    /**
     * Gets the value of the orgPersonRelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgPersonRelationId() {
        return orgPersonRelationId;
    }

    /**
     * Sets the value of the orgPersonRelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgPersonRelationId(String value) {
        this.orgPersonRelationId = value;
    }

}
