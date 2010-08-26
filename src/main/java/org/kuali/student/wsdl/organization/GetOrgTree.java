
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getOrgTree complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getOrgTree">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rootOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgHierarchyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxLevels" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgTree", propOrder = {
    "rootOrgId",
    "orgHierarchyId",
    "maxLevels"
})
public class GetOrgTree {

    protected String rootOrgId;
    protected String orgHierarchyId;
    protected int maxLevels;

    /**
     * Gets the value of the rootOrgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootOrgId() {
        return rootOrgId;
    }

    /**
     * Sets the value of the rootOrgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootOrgId(String value) {
        this.rootOrgId = value;
    }

    /**
     * Gets the value of the orgHierarchyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgHierarchyId() {
        return orgHierarchyId;
    }

    /**
     * Sets the value of the orgHierarchyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgHierarchyId(String value) {
        this.orgHierarchyId = value;
    }

    /**
     * Gets the value of the maxLevels property.
     * 
     */
    public int getMaxLevels() {
        return maxLevels;
    }

    /**
     * Sets the value of the maxLevels property.
     * 
     */
    public void setMaxLevels(int value) {
        this.maxLevels = value;
    }

}
