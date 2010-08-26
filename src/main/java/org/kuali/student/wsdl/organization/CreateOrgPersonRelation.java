
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createOrgPersonRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createOrgPersonRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgPersonRelationTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgPersonRelationInfo" type="{http://student.kuali.org/wsdl/organization}orgPersonRelationInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createOrgPersonRelation", propOrder = {
    "orgId",
    "personId",
    "orgPersonRelationTypeKey",
    "orgPersonRelationInfo"
})
public class CreateOrgPersonRelation {

    protected String orgId;
    protected String personId;
    protected String orgPersonRelationTypeKey;
    protected OrgPersonRelationInfo orgPersonRelationInfo;

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
     * Gets the value of the personId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the value of the personId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonId(String value) {
        this.personId = value;
    }

    /**
     * Gets the value of the orgPersonRelationTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgPersonRelationTypeKey() {
        return orgPersonRelationTypeKey;
    }

    /**
     * Sets the value of the orgPersonRelationTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgPersonRelationTypeKey(String value) {
        this.orgPersonRelationTypeKey = value;
    }

    /**
     * Gets the value of the orgPersonRelationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrgPersonRelationInfo }
     *     
     */
    public OrgPersonRelationInfo getOrgPersonRelationInfo() {
        return orgPersonRelationInfo;
    }

    /**
     * Sets the value of the orgPersonRelationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgPersonRelationInfo }
     *     
     */
    public void setOrgPersonRelationInfo(OrgPersonRelationInfo value) {
        this.orgPersonRelationInfo = value;
    }

}
