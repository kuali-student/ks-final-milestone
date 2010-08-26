
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createOrgOrgRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createOrgOrgRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relatedOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgOrgRelationTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "createOrgOrgRelation", propOrder = {
    "orgId",
    "relatedOrgId",
    "orgOrgRelationTypeKey",
    "orgOrgRelationInfo"
})
public class CreateOrgOrgRelation {

    protected String orgId;
    protected String relatedOrgId;
    protected String orgOrgRelationTypeKey;
    protected OrgOrgRelationInfo orgOrgRelationInfo;

    /**
     * Gets the value of the orgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgId(String value) {
        this.orgId = value;
    }

    /**
     * Gets the value of the relatedOrgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedOrgId() {
        return relatedOrgId;
    }

    /**
     * Sets the value of the relatedOrgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedOrgId(String value) {
        this.relatedOrgId = value;
    }

    /**
     * Gets the value of the orgOrgRelationTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgOrgRelationTypeKey() {
        return orgOrgRelationTypeKey;
    }

    /**
     * Sets the value of the orgOrgRelationTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgOrgRelationTypeKey(String value) {
        this.orgOrgRelationTypeKey = value;
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
