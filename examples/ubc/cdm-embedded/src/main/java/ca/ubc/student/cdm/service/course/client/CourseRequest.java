
package ca.ubc.student.cdm.service.course.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for courseRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="courseRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chgCreditFlg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="chgDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chgGradeFlg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="chgLtitleFlg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="chgStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chgStitleFlg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="coReq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coReqNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="courseReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="courseType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="credit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditDelimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditVariable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditVector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsCoReqJemId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="crsDetailSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsEquivalent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsMainSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsMaxCredit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsMaxCreditLifetime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsPreReqJemId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="crsRestRefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crsVersionEquiv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="curricCrsReqErrMsgS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="curricCrsReqErrMsgW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duplNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duplicates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enterBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enterDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="facApprDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gradeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isPublish" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxCdt" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="minCdt" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="preReq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preReqNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shortTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="snteApprDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "courseRequest", propOrder = {
    "chgCreditFlg",
    "chgDescription",
    "chgGradeFlg",
    "chgLtitleFlg",
    "chgStatus",
    "chgStitleFlg",
    "coReq",
    "coReqNote",
    "code",
    "comments",
    "courseReference",
    "courseType",
    "credit",
    "creditDelimit",
    "creditVariable",
    "creditVector",
    "crsCoReqJemId",
    "crsDetailSeq",
    "crsEquivalent",
    "crsMainSeq",
    "crsMaxCredit",
    "crsMaxCreditLifetime",
    "crsPreReqJemId",
    "crsRestRefNo",
    "crsVersionEquiv",
    "curricCrsReqErrMsgS",
    "curricCrsReqErrMsgW",
    "description",
    "duplNote",
    "duplicates",
    "endTerm",
    "enterBy",
    "enterDt",
    "facApprDt",
    "gradeType",
    "isPublish",
    "level",
    "location",
    "longTitle",
    "maxCdt",
    "minCdt",
    "preReq",
    "preReqNote",
    "shortTitle",
    "snteApprDt",
    "startTerm"
})
public class CourseRequest {

    protected boolean chgCreditFlg;
    protected String chgDescription;
    protected boolean chgGradeFlg;
    protected boolean chgLtitleFlg;
    protected String chgStatus;
    protected boolean chgStitleFlg;
    protected String coReq;
    protected String coReqNote;
    protected String code;
    protected String comments;
    protected String courseReference;
    protected String courseType;
    protected String credit;
    protected String creditDelimit;
    protected String creditVariable;
    protected String creditVector;
    protected long crsCoReqJemId;
    protected String crsDetailSeq;
    protected String crsEquivalent;
    protected String crsMainSeq;
    protected String crsMaxCredit;
    protected String crsMaxCreditLifetime;
    protected long crsPreReqJemId;
    protected String crsRestRefNo;
    protected String crsVersionEquiv;
    protected String curricCrsReqErrMsgS;
    protected String curricCrsReqErrMsgW;
    protected String description;
    protected String duplNote;
    protected String duplicates;
    protected String endTerm;
    protected String enterBy;
    protected String enterDt;
    protected String facApprDt;
    protected String gradeType;
    protected boolean isPublish;
    protected String level;
    protected String location;
    protected String longTitle;
    protected float maxCdt;
    protected float minCdt;
    protected String preReq;
    protected String preReqNote;
    protected String shortTitle;
    protected String snteApprDt;
    protected String startTerm;

    /**
     * Gets the value of the chgCreditFlg property.
     * 
     */
    public boolean isChgCreditFlg() {
        return chgCreditFlg;
    }

    /**
     * Sets the value of the chgCreditFlg property.
     * 
     */
    public void setChgCreditFlg(boolean value) {
        this.chgCreditFlg = value;
    }

    /**
     * Gets the value of the chgDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChgDescription() {
        return chgDescription;
    }

    /**
     * Sets the value of the chgDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChgDescription(String value) {
        this.chgDescription = value;
    }

    /**
     * Gets the value of the chgGradeFlg property.
     * 
     */
    public boolean isChgGradeFlg() {
        return chgGradeFlg;
    }

    /**
     * Sets the value of the chgGradeFlg property.
     * 
     */
    public void setChgGradeFlg(boolean value) {
        this.chgGradeFlg = value;
    }

    /**
     * Gets the value of the chgLtitleFlg property.
     * 
     */
    public boolean isChgLtitleFlg() {
        return chgLtitleFlg;
    }

    /**
     * Sets the value of the chgLtitleFlg property.
     * 
     */
    public void setChgLtitleFlg(boolean value) {
        this.chgLtitleFlg = value;
    }

    /**
     * Gets the value of the chgStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChgStatus() {
        return chgStatus;
    }

    /**
     * Sets the value of the chgStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChgStatus(String value) {
        this.chgStatus = value;
    }

    /**
     * Gets the value of the chgStitleFlg property.
     * 
     */
    public boolean isChgStitleFlg() {
        return chgStitleFlg;
    }

    /**
     * Sets the value of the chgStitleFlg property.
     * 
     */
    public void setChgStitleFlg(boolean value) {
        this.chgStitleFlg = value;
    }

    /**
     * Gets the value of the coReq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoReq() {
        return coReq;
    }

    /**
     * Sets the value of the coReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoReq(String value) {
        this.coReq = value;
    }

    /**
     * Gets the value of the coReqNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoReqNote() {
        return coReqNote;
    }

    /**
     * Sets the value of the coReqNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoReqNote(String value) {
        this.coReqNote = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the courseReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourseReference() {
        return courseReference;
    }

    /**
     * Sets the value of the courseReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourseReference(String value) {
        this.courseReference = value;
    }

    /**
     * Gets the value of the courseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * Sets the value of the courseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourseType(String value) {
        this.courseType = value;
    }

    /**
     * Gets the value of the credit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredit() {
        return credit;
    }

    /**
     * Sets the value of the credit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredit(String value) {
        this.credit = value;
    }

    /**
     * Gets the value of the creditDelimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditDelimit() {
        return creditDelimit;
    }

    /**
     * Sets the value of the creditDelimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditDelimit(String value) {
        this.creditDelimit = value;
    }

    /**
     * Gets the value of the creditVariable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditVariable() {
        return creditVariable;
    }

    /**
     * Sets the value of the creditVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditVariable(String value) {
        this.creditVariable = value;
    }

    /**
     * Gets the value of the creditVector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditVector() {
        return creditVector;
    }

    /**
     * Sets the value of the creditVector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditVector(String value) {
        this.creditVector = value;
    }

    /**
     * Gets the value of the crsCoReqJemId property.
     * 
     */
    public long getCrsCoReqJemId() {
        return crsCoReqJemId;
    }

    /**
     * Sets the value of the crsCoReqJemId property.
     * 
     */
    public void setCrsCoReqJemId(long value) {
        this.crsCoReqJemId = value;
    }

    /**
     * Gets the value of the crsDetailSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsDetailSeq() {
        return crsDetailSeq;
    }

    /**
     * Sets the value of the crsDetailSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsDetailSeq(String value) {
        this.crsDetailSeq = value;
    }

    /**
     * Gets the value of the crsEquivalent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsEquivalent() {
        return crsEquivalent;
    }

    /**
     * Sets the value of the crsEquivalent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsEquivalent(String value) {
        this.crsEquivalent = value;
    }

    /**
     * Gets the value of the crsMainSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsMainSeq() {
        return crsMainSeq;
    }

    /**
     * Sets the value of the crsMainSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsMainSeq(String value) {
        this.crsMainSeq = value;
    }

    /**
     * Gets the value of the crsMaxCredit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsMaxCredit() {
        return crsMaxCredit;
    }

    /**
     * Sets the value of the crsMaxCredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsMaxCredit(String value) {
        this.crsMaxCredit = value;
    }

    /**
     * Gets the value of the crsMaxCreditLifetime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsMaxCreditLifetime() {
        return crsMaxCreditLifetime;
    }

    /**
     * Sets the value of the crsMaxCreditLifetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsMaxCreditLifetime(String value) {
        this.crsMaxCreditLifetime = value;
    }

    /**
     * Gets the value of the crsPreReqJemId property.
     * 
     */
    public long getCrsPreReqJemId() {
        return crsPreReqJemId;
    }

    /**
     * Sets the value of the crsPreReqJemId property.
     * 
     */
    public void setCrsPreReqJemId(long value) {
        this.crsPreReqJemId = value;
    }

    /**
     * Gets the value of the crsRestRefNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsRestRefNo() {
        return crsRestRefNo;
    }

    /**
     * Sets the value of the crsRestRefNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsRestRefNo(String value) {
        this.crsRestRefNo = value;
    }

    /**
     * Gets the value of the crsVersionEquiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrsVersionEquiv() {
        return crsVersionEquiv;
    }

    /**
     * Sets the value of the crsVersionEquiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrsVersionEquiv(String value) {
        this.crsVersionEquiv = value;
    }

    /**
     * Gets the value of the curricCrsReqErrMsgS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurricCrsReqErrMsgS() {
        return curricCrsReqErrMsgS;
    }

    /**
     * Sets the value of the curricCrsReqErrMsgS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurricCrsReqErrMsgS(String value) {
        this.curricCrsReqErrMsgS = value;
    }

    /**
     * Gets the value of the curricCrsReqErrMsgW property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurricCrsReqErrMsgW() {
        return curricCrsReqErrMsgW;
    }

    /**
     * Sets the value of the curricCrsReqErrMsgW property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurricCrsReqErrMsgW(String value) {
        this.curricCrsReqErrMsgW = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the duplNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplNote() {
        return duplNote;
    }

    /**
     * Sets the value of the duplNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplNote(String value) {
        this.duplNote = value;
    }

    /**
     * Gets the value of the duplicates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplicates() {
        return duplicates;
    }

    /**
     * Sets the value of the duplicates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplicates(String value) {
        this.duplicates = value;
    }

    /**
     * Gets the value of the endTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTerm() {
        return endTerm;
    }

    /**
     * Sets the value of the endTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTerm(String value) {
        this.endTerm = value;
    }

    /**
     * Gets the value of the enterBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterBy() {
        return enterBy;
    }

    /**
     * Sets the value of the enterBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterBy(String value) {
        this.enterBy = value;
    }

    /**
     * Gets the value of the enterDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterDt() {
        return enterDt;
    }

    /**
     * Sets the value of the enterDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterDt(String value) {
        this.enterDt = value;
    }

    /**
     * Gets the value of the facApprDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacApprDt() {
        return facApprDt;
    }

    /**
     * Sets the value of the facApprDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacApprDt(String value) {
        this.facApprDt = value;
    }

    /**
     * Gets the value of the gradeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGradeType() {
        return gradeType;
    }

    /**
     * Sets the value of the gradeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGradeType(String value) {
        this.gradeType = value;
    }

    /**
     * Gets the value of the isPublish property.
     * 
     */
    public boolean isIsPublish() {
        return isPublish;
    }

    /**
     * Sets the value of the isPublish property.
     * 
     */
    public void setIsPublish(boolean value) {
        this.isPublish = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the longTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongTitle() {
        return longTitle;
    }

    /**
     * Sets the value of the longTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongTitle(String value) {
        this.longTitle = value;
    }

    /**
     * Gets the value of the maxCdt property.
     * 
     */
    public float getMaxCdt() {
        return maxCdt;
    }

    /**
     * Sets the value of the maxCdt property.
     * 
     */
    public void setMaxCdt(float value) {
        this.maxCdt = value;
    }

    /**
     * Gets the value of the minCdt property.
     * 
     */
    public float getMinCdt() {
        return minCdt;
    }

    /**
     * Sets the value of the minCdt property.
     * 
     */
    public void setMinCdt(float value) {
        this.minCdt = value;
    }

    /**
     * Gets the value of the preReq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreReq() {
        return preReq;
    }

    /**
     * Sets the value of the preReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreReq(String value) {
        this.preReq = value;
    }

    /**
     * Gets the value of the preReqNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreReqNote() {
        return preReqNote;
    }

    /**
     * Sets the value of the preReqNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreReqNote(String value) {
        this.preReqNote = value;
    }

    /**
     * Gets the value of the shortTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * Sets the value of the shortTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortTitle(String value) {
        this.shortTitle = value;
    }

    /**
     * Gets the value of the snteApprDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnteApprDt() {
        return snteApprDt;
    }

    /**
     * Sets the value of the snteApprDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnteApprDt(String value) {
        this.snteApprDt = value;
    }

    /**
     * Gets the value of the startTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTerm() {
        return startTerm;
    }

    /**
     * Sets the value of the startTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTerm(String value) {
        this.startTerm = value;
    }

}
