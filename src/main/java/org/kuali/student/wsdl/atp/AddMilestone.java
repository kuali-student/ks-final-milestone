
package org.kuali.student.wsdl.atp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addMilestone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addMilestone">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="atpKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="milestoneKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="milestoneInfo" type="{http://student.kuali.org/wsdl/atp}milestoneInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addMilestone", propOrder = {
    "atpKey",
    "milestoneKey",
    "milestoneInfo"
})
public class AddMilestone {

    protected String atpKey;
    protected String milestoneKey;
    protected MilestoneInfo milestoneInfo;

    /**
     * Gets the value of the atpKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtpKey() {
        return atpKey;
    }

    /**
     * Sets the value of the atpKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtpKey(String value) {
        this.atpKey = value;
    }

    /**
     * Gets the value of the milestoneKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMilestoneKey() {
        return milestoneKey;
    }

    /**
     * Sets the value of the milestoneKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMilestoneKey(String value) {
        this.milestoneKey = value;
    }

    /**
     * Gets the value of the milestoneInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MilestoneInfo }
     *     
     */
    public MilestoneInfo getMilestoneInfo() {
        return milestoneInfo;
    }

    /**
     * Sets the value of the milestoneInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MilestoneInfo }
     *     
     */
    public void setMilestoneInfo(MilestoneInfo value) {
        this.milestoneInfo = value;
    }

}
