
package org.kuali.student.wsdl.atp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addDateRange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addDateRange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="atpKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateRangeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateRangeInfo" type="{http://student.kuali.org/wsdl/atp}dateRangeInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addDateRange", propOrder = {
    "atpKey",
    "dateRangeKey",
    "dateRangeInfo"
})
public class AddDateRange {

    protected String atpKey;
    protected String dateRangeKey;
    protected DateRangeInfo dateRangeInfo;

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
     * Gets the value of the dateRangeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateRangeKey() {
        return dateRangeKey;
    }

    /**
     * Sets the value of the dateRangeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateRangeKey(String value) {
        this.dateRangeKey = value;
    }

    /**
     * Gets the value of the dateRangeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DateRangeInfo }
     *     
     */
    public DateRangeInfo getDateRangeInfo() {
        return dateRangeInfo;
    }

    /**
     * Sets the value of the dateRangeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateRangeInfo }
     *     
     */
    public void setDateRangeInfo(DateRangeInfo value) {
        this.dateRangeInfo = value;
    }

}
