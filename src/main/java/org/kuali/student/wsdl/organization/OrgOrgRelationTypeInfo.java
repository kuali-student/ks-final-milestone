
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orgOrgRelationTypeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orgOrgRelationTypeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://student.kuali.org/wsdl/organization}typeInfo">
 *       &lt;sequence>
 *         &lt;element name="revName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="revDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgHierarchyKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orgOrgRelationTypeInfo", propOrder = {
    "revName",
    "revDesc",
    "orgHierarchyKey"
})
public class OrgOrgRelationTypeInfo
    extends TypeInfo
{

    protected String revName;
    protected String revDesc;
    protected String orgHierarchyKey;

    /**
     * Gets the value of the revName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevName() {
        return revName;
    }

    /**
     * Sets the value of the revName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevName(String value) {
        this.revName = value;
    }

    /**
     * Gets the value of the revDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevDesc() {
        return revDesc;
    }

    /**
     * Sets the value of the revDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevDesc(String value) {
        this.revDesc = value;
    }

    /**
     * Gets the value of the orgHierarchyKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgHierarchyKey() {
        return orgHierarchyKey;
    }

    /**
     * Sets the value of the orgHierarchyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgHierarchyKey(String value) {
        this.orgHierarchyKey = value;
    }

}
