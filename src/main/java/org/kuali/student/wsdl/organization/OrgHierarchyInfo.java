
package org.kuali.student.wsdl.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orgHierarchyInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orgHierarchyInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://student.kuali.org/wsdl/organization}typeInfo">
 *       &lt;sequence>
 *         &lt;element name="rootOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orgHierarchyInfo", propOrder = {
    "rootOrgId"
})
public class OrgHierarchyInfo
    extends TypeInfo
{

    protected String rootOrgId;

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

}
