
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateOrgPersonRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateOrgPersonRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "validateOrgPersonRelation", propOrder = {
    "validationType",
    "orgPersonRelationInfo"
})
public class ValidateOrgPersonRelation {

    protected String validationType;
    protected OrgPersonRelationInfo orgPersonRelationInfo;

    /**
     * Gets the value of the validationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Sets the value of the validationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationType(String value) {
        this.validationType = value;
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
