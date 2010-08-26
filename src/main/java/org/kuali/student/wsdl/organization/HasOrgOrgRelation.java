
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hasOrgOrgRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hasOrgOrgRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comparisonOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgOrgRelationTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hasOrgOrgRelation", propOrder = {
    "orgId",
    "comparisonOrgId",
    "orgOrgRelationTypeKey"
})
public class HasOrgOrgRelation {

    protected String orgId;
    protected String comparisonOrgId;
    protected String orgOrgRelationTypeKey;

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
     * Gets the value of the comparisonOrgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComparisonOrgId() {
        return comparisonOrgId;
    }

    /**
     * Sets the value of the comparisonOrgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComparisonOrgId(String value) {
        this.comparisonOrgId = value;
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

}
