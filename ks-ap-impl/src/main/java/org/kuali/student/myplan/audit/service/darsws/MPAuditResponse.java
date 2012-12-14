package org.kuali.student.myplan.audit.service.darsws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MPAuditResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MPAuditResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SystemKey" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StudentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StudentNo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Major" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LineNo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AuditLaser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuditText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuditLine" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ErrorMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MPAuditResponse", propOrder = {
    "success",
    "systemKey",
    "studentName",
    "studentNo",
    "major",
    "lineNo",
    "auditLaser",
    "auditText",
    "auditLine",
    "errorCode",
    "errorMsg"
})
public class MPAuditResponse {

    @XmlElement(name = "Success")
    protected boolean success;
    @XmlElement(name = "SystemKey")
    protected int systemKey;
    @XmlElement(name = "StudentName")
    protected String studentName;
    @XmlElement(name = "StudentNo")
    protected int studentNo;
    @XmlElement(name = "Major")
    protected String major;
    @XmlElement(name = "LineNo")
    protected int lineNo;
    @XmlElement(name = "AuditLaser")
    protected String auditLaser;
    @XmlElement(name = "AuditText")
    protected String auditText;
    @XmlElement(name = "AuditLine")
    protected int auditLine;
    @XmlElement(name = "ErrorCode")
    protected int errorCode;
    @XmlElement(name = "ErrorMsg")
    protected String errorMsg;

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
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
     * Gets the value of the studentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the value of the studentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudentName(String value) {
        this.studentName = value;
    }

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
     * Gets the value of the auditLaser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditLaser() {
        return auditLaser;
    }

    /**
     * Sets the value of the auditLaser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditLaser(String value) {
        this.auditLaser = value;
    }

    /**
     * Gets the value of the auditText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditText() {
        return auditText;
    }

    /**
     * Sets the value of the auditText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditText(String value) {
        this.auditText = value;
    }

    /**
     * Gets the value of the auditLine property.
     * 
     */
    public int getAuditLine() {
        return auditLine;
    }

    /**
     * Sets the value of the auditLine property.
     * 
     */
    public void setAuditLine(int value) {
        this.auditLine = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets the value of the errorMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

}
