
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateOrgPositionRestriction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateOrgPositionRestriction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "validateOrgPositionRestriction", propOrder = {
    "validationType",
    "orgPositionRestrictionInfo"
})
public class ValidateOrgPositionRestriction {

    protected String validationType;
    protected OrgPositionRestrictionInfo orgPositionRestrictionInfo;

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
