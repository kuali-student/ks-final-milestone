package org.kuali.student.myplan.audit.service.darsws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studentNo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="major" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineNo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="systemKey" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="origin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "studentNo",
    "major",
    "lineNo",
    "systemKey",
    "origin"
})
@XmlRootElement(name = "MPRequestAudit")
public class MPRequestAudit {

    protected int studentNo;
    protected String major;
    protected int lineNo;
    protected int systemKey;
    protected String origin;

    /**
     * Gets the value of the studentNo property.
     * 
     */
    public int getStudentNo() {
        return studentNo;
    }

    /**
     * Sets the value of the studentNo property.
     * 
     */
    public void setStudentNo(int value) {
        this.studentNo = value;
    }

    /**
     * Gets the value of the major property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the value of the major property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajor(String value) {
        this.major = value;
    }

    /**
     * Gets the value of the lineNo property.
     * 
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Sets the value of the lineNo property.
     * 
     */
    public void setLineNo(int value) {
        this.lineNo = value;
    }

    /**
     * Gets the value of the systemKey property.
     * 
     */
    public int getSystemKey() {
        return systemKey;
    }

    /**
     * Sets the value of the systemKey property.
     * 
     */
    public void setSystemKey(int value) {
        this.systemKey = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

}
