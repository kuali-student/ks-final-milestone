
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for activityInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="activityInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="duration" type="{http://student.kuali.org/wsdl/course}timeAmountInfo" minOccurs="0"/>
 *         &lt;element name="curriculumOversightOrgs" type="{http://student.kuali.org/wsdl/course}adminOrgInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="defaultEnrollmentEstimate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="attributes" type="{http://student.kuali.org/wsdl/course}jaxbAttributeList" minOccurs="0"/>
 *         &lt;element name="metaInfo" type="{http://student.kuali.org/wsdl/course}metaInfo" minOccurs="0"/>
 *         &lt;element name="contactHours" type="{http://student.kuali.org/wsdl/course}amountInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="activityType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="state" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activityInfo", propOrder = {
    "duration",
    "curriculumOversightOrgs",
    "defaultEnrollmentEstimate",
    "attributes",
    "metaInfo",
    "contactHours"
})
public class ActivityInfo {

    protected TimeAmountInfo duration;
    protected List<AdminOrgInfo> curriculumOversightOrgs;
    protected int defaultEnrollmentEstimate;
    protected JaxbAttributeList attributes;
    protected MetaInfo metaInfo;
    protected AmountInfo contactHours;
    @XmlAttribute(name = "activityType")
    protected String activityType;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link TimeAmountInfo }
     *     
     */
    public TimeAmountInfo getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeAmountInfo }
     *     
     */
    public void setDuration(TimeAmountInfo value) {
        this.duration = value;
    }

    /**
     * Gets the value of the curriculumOversightOrgs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the curriculumOversightOrgs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCurriculumOversightOrgs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdminOrgInfo }
     * 
     * 
     */
    public List<AdminOrgInfo> getCurriculumOversightOrgs() {
        if (curriculumOversightOrgs == null) {
            curriculumOversightOrgs = new ArrayList<AdminOrgInfo>();
        }
        return this.curriculumOversightOrgs;
    }

    /**
     * Gets the value of the defaultEnrollmentEstimate property.
     * 
     */
    public int getDefaultEnrollmentEstimate() {
        return defaultEnrollmentEstimate;
    }

    /**
     * Sets the value of the defaultEnrollmentEstimate property.
     * 
     */
    public void setDefaultEnrollmentEstimate(int value) {
        this.defaultEnrollmentEstimate = value;
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
     * Gets the value of the contactHours property.
     * 
     * @return
     *     possible object is
     *     {@link AmountInfo }
     *     
     */
    public AmountInfo getContactHours() {
        return contactHours;
    }

    /**
     * Sets the value of the contactHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountInfo }
     *     
     */
    public void setContactHours(AmountInfo value) {
        this.contactHours = value;
    }

    /**
     * Gets the value of the activityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * Sets the value of the activityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityType(String value) {
        this.activityType = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
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
