
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for courseRevenueInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="courseRevenueInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="feeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affiliatedOrgs" type="{http://student.kuali.org/wsdl/course}affiliatedOrgInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://student.kuali.org/wsdl/course}jaxbAttributeList" minOccurs="0"/>
 *         &lt;element name="metaInfo" type="{http://student.kuali.org/wsdl/course}metaInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "courseRevenueInfo", propOrder = {
    "feeType",
    "affiliatedOrgs",
    "attributes",
    "metaInfo"
})
public class CourseRevenueInfo {

    protected String feeType;
    protected List<AffiliatedOrgInfo> affiliatedOrgs;
    protected JaxbAttributeList attributes;
    protected MetaInfo metaInfo;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the feeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * Sets the value of the feeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeType(String value) {
        this.feeType = value;
    }

    /**
     * Gets the value of the affiliatedOrgs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the affiliatedOrgs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAffiliatedOrgs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AffiliatedOrgInfo }
     * 
     * 
     */
    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        if (affiliatedOrgs == null) {
            affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
        }
        return this.affiliatedOrgs;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link JaxbAttributeList }
     *     
     */
    public JaxbAttributeList getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaxbAttributeList }
     *     
     */
    public void setAttributes(JaxbAttributeList value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the metaInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MetaInfo }
     *     
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    /**
     * Sets the value of the metaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaInfo }
     *     
     */
    public void setMetaInfo(MetaInfo value) {
        this.metaInfo = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
