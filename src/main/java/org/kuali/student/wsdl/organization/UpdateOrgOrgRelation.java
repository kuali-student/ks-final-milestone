
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateOrgOrgRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateOrgOrgRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgOrgRelationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgOrgRelationInfo" type="{http://student.kuali.org/wsdl/organization}orgOrgRelationInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOrgOrgRelation", propOrder = {
    "orgOrgRelationId",
    "orgOrgRelationInfo"
})
public class UpdateOrgOrgRelation {

    protected String orgOrgRelationId;
    protected OrgOrgRelationInfo orgOrgRelationInfo;

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

    /**
     * Gets the value of the orgOrgRelationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrgOrgRelationInfo }
     *     
     */
    public OrgOrgRelationInfo getOrgOrgRelationInfo() {
        return orgOrgRelationInfo;
    }

    /**
     * Sets the value of the orgOrgRelationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgOrgRelationInfo }
     *     
     */
    public void setOrgOrgRelationInfo(OrgOrgRelationInfo value) {
        this.orgOrgRelationInfo = value;
    }

}
