
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeOrgOrgRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeOrgOrgRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgOrgRelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeOrgOrgRelation", propOrder = {
    "orgOrgRelationId"
})
public class RemoveOrgOrgRelation {

    protected String orgOrgRelationId;

    /**
     * Gets the value of the orgOrgRelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgOrgRelationId() {
        return orgOrgRelationId;
    }

    /**
     * Sets the value of the orgOrgRelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgOrgRelationId(String value) {
        this.orgOrgRelationId = value;
    }

}
