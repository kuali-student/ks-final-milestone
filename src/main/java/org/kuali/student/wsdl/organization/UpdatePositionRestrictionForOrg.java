
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updatePositionRestrictionForOrg complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updatePositionRestrictionForOrg">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgPersonRelationTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgPositionRestrictionInfo" type="{http://student.kuali.org/wsdl/organization}orgPositionRestrictionInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePositionRestrictionForOrg", propOrder = {
    "orgId",
    "orgPersonRelationTypeKey",
    "orgPositionRestrictionInfo"
})
public class UpdatePositionRestrictionForOrg {

    protected String orgId;
    protected String orgPersonRelationTypeKey;
    protected OrgPositionRestrictionInfo orgPositionRestrictionInfo;

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
     * Gets the value of the orgPositionRestrictionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrgPositionRestrictionInfo }
     *     
     */
    public OrgPositionRestrictionInfo getOrgPositionRestrictionInfo() {
        return orgPositionRestrictionInfo;
    }

    /**
     * Sets the value of the orgPositionRestrictionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgPositionRestrictionInfo }
     *     
     */
    public void setOrgPositionRestrictionInfo(OrgPositionRestrictionInfo value) {
        this.orgPositionRestrictionInfo = value;
    }

}
