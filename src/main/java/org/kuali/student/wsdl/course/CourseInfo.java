
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for courseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="courseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="courseNumberSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="courseTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transcriptTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descr" type="{http://student.kuali.org/wsdl/course}richTextInfo" minOccurs="0"/>
 *         &lt;element name="formats" type="{http://student.kuali.org/wsdl/course}formatInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="termsOffered" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="duration" type="{http://student.kuali.org/wsdl/course}timeAmountInfo" minOccurs="0"/>
 *         &lt;element name="joints" type="{http://student.kuali.org/wsdl/course}courseJointInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="crossListings" type="{http://student.kuali.org/wsdl/course}courseCrossListingInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="variations" type="{http://student.kuali.org/wsdl/course}courseVariationInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="campusLocations" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outOfClassHours" type="{http://student.kuali.org/wsdl/course}amountInfo" minOccurs="0"/>
 *         &lt;element name="primaryInstructor" type="{http://student.kuali.org/wsdl/course}cluInstructorInfo" minOccurs="0"/>
 *         &lt;element name="instructors" type="{http://student.kuali.org/wsdl/course}cluInstructorInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="administeringOrgs" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="feeJustification" type="{http://student.kuali.org/wsdl/course}richTextInfo" minOccurs="0"/>
 *         &lt;element name="curriculumOversightOrgs" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fees" type="{http://student.kuali.org/wsdl/course}courseFeeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="revenues" type="{http://student.kuali.org/wsdl/course}courseRevenueInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="expenditure" type="{http://student.kuali.org/wsdl/course}courseExpenditureInfo" minOccurs="0"/>
 *         &lt;element name="courseSpecificLOs" type="{http://student.kuali.org/wsdl/course}loDisplayInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="gradingOptions" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="creditOptions" type="{http://student.kuali.org/wsdl/course}resultComponentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="specialTopicsCourse" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pilotCourse" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="startTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://student.kuali.org/wsdl/course}jaxbAttributeList" minOccurs="0"/>
 *         &lt;element name="metaInfo" type="{http://student.kuali.org/wsdl/course}metaInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "courseInfo", propOrder = {
    "code",
    "courseNumberSuffix",
    "courseTitle",
    "transcriptTitle",
    "descr",
    "formats",
    "termsOffered",
    "duration",
    "joints",
    "crossListings",
    "variations",
    "subjectArea",
    "campusLocations",
    "outOfClassHours",
    "primaryInstructor",
    "instructors",
    "administeringOrgs",
    "feeJustification",
    "curriculumOversightOrgs",
    "fees",
    "revenues",
    "expenditure",
    "courseSpecificLOs",
    "gradingOptions",
    "creditOptions",
    "specialTopicsCourse",
    "pilotCourse",
    "startTerm",
    "endTerm",
    "effectiveDate",
    "expirationDate",
    "attributes",
    "metaInfo"
})
public class CourseInfo {

    protected String code;
    protected String courseNumberSuffix;
    protected String courseTitle;
    protected String transcriptTitle;
    protected RichTextInfo descr;
    protected List<FormatInfo> formats;
    protected List<String> termsOffered;
    protected TimeAmountInfo duration;
    protected List<CourseJointInfo> joints;
    protected List<CourseCrossListingInfo> crossListings;
    protected List<CourseVariationInfo> variations;
    protected String subjectArea;
    protected List<String> campusLocations;
    protected AmountInfo outOfClassHours;
    protected CluInstructorInfo primaryInstructor;
    protected List<CluInstructorInfo> instructors;
    protected List<String> administeringOrgs;
    protected RichTextInfo feeJustification;
    protected List<String> curriculumOversightOrgs;
    @XmlElement(nillable = true)
    protected List<CourseFeeInfo> fees;
    protected List<CourseRevenueInfo> revenues;
    protected CourseExpenditureInfo expenditure;
    protected List<LoDisplayInfo> courseSpecificLOs;
    protected List<String> gradingOptions;
    protected List<ResultComponentInfo> creditOptions;
    protected boolean specialTopicsCourse;
    protected boolean pilotCourse;
    protected String startTerm;
    protected String endTerm;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    protected JaxbAttributeList attributes;
    protected MetaInfo metaInfo;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "id")
    protected String id;

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
     * Gets the value of the courseNumberSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourseNumberSuffix() {
        return courseNumberSuffix;
    }

    /**
     * Sets the value of the courseNumberSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourseNumberSuffix(String value) {
        this.courseNumberSuffix = value;
    }

    /**
     * Gets the value of the courseTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Sets the value of the courseTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourseTitle(String value) {
        this.courseTitle = value;
    }

    /**
     * Gets the value of the transcriptTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranscriptTitle() {
        return transcriptTitle;
    }

    /**
     * Sets the value of the transcriptTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranscriptTitle(String value) {
        this.transcriptTitle = value;
    }

    /**
     * Gets the value of the descr property.
     * 
     * @return
     *     possible object is
     *     {@link RichTextInfo }
     *     
     */
    public RichTextInfo getDescr() {
        return descr;
    }

    /**
     * Sets the value of the descr property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichTextInfo }
     *     
     */
    public void setDescr(RichTextInfo value) {
        this.descr = value;
    }

    /**
     * Gets the value of the formats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormatInfo }
     * 
     * 
     */
    public List<FormatInfo> getFormats() {
        if (formats == null) {
            formats = new ArrayList<FormatInfo>();
        }
        return this.formats;
    }

    /**
     * Gets the value of the termsOffered property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the termsOffered property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTermsOffered().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTermsOffered() {
        if (termsOffered == null) {
            termsOffered = new ArrayList<String>();
        }
        return this.termsOffered;
    }

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
     * Gets the value of the joints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the joints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJoints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseJointInfo }
     * 
     * 
     */
    public List<CourseJointInfo> getJoints() {
        if (joints == null) {
            joints = new ArrayList<CourseJointInfo>();
        }
        return this.joints;
    }

    /**
     * Gets the value of the crossListings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the crossListings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCrossListings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseCrossListingInfo }
     * 
     * 
     */
    public List<CourseCrossListingInfo> getCrossListings() {
        if (crossListings == null) {
            crossListings = new ArrayList<CourseCrossListingInfo>();
        }
        return this.crossListings;
    }

    /**
     * Gets the value of the variations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseVariationInfo }
     * 
     * 
     */
    public List<CourseVariationInfo> getVariations() {
        if (variations == null) {
            variations = new ArrayList<CourseVariationInfo>();
        }
        return this.variations;
    }

    /**
     * Gets the value of the subjectArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    /**
     * Sets the value of the subjectArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectArea(String value) {
        this.subjectArea = value;
    }

    /**
     * Gets the value of the campusLocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the campusLocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCampusLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>();
        }
        return this.campusLocations;
    }

    /**
     * Gets the value of the outOfClassHours property.
     * 
     * @return
     *     possible object is
     *     {@link AmountInfo }
     *     
     */
    public AmountInfo getOutOfClassHours() {
        return outOfClassHours;
    }

    /**
     * Sets the value of the outOfClassHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountInfo }
     *     
     */
    public void setOutOfClassHours(AmountInfo value) {
        this.outOfClassHours = value;
    }

    /**
     * Gets the value of the primaryInstructor property.
     * 
     * @return
     *     possible object is
     *     {@link CluInstructorInfo }
     *     
     */
    public CluInstructorInfo getPrimaryInstructor() {
        return primaryInstructor;
    }

    /**
     * Sets the value of the primaryInstructor property.
     * 
     * @param value
     *     allowed object is
     *     {@link CluInstructorInfo }
     *     
     */
    public void setPrimaryInstructor(CluInstructorInfo value) {
        this.primaryInstructor = value;
    }

    /**
     * Gets the value of the instructors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instructors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstructors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CluInstructorInfo }
     * 
     * 
     */
    public List<CluInstructorInfo> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<CluInstructorInfo>();
        }
        return this.instructors;
    }

    /**
     * Gets the value of the administeringOrgs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the administeringOrgs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdministeringOrgs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAdministeringOrgs() {
        if (administeringOrgs == null) {
            administeringOrgs = new ArrayList<String>();
        }
        return this.administeringOrgs;
    }

    /**
     * Gets the value of the feeJustification property.
     * 
     * @return
     *     possible object is
     *     {@link RichTextInfo }
     *     
     */
    public RichTextInfo getFeeJustification() {
        return feeJustification;
    }

    /**
     * Sets the value of the feeJustification property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichTextInfo }
     *     
     */
    public void setFeeJustification(RichTextInfo value) {
        this.feeJustification = value;
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
     * {@link String }
     * 
     * 
     */
    public List<String> getCurriculumOversightOrgs() {
        if (curriculumOversightOrgs == null) {
            curriculumOversightOrgs = new ArrayList<String>();
        }
        return this.curriculumOversightOrgs;
    }

    /**
     * Gets the value of the fees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseFeeInfo }
     * 
     * 
     */
    public List<CourseFeeInfo> getFees() {
        if (fees == null) {
            fees = new ArrayList<CourseFeeInfo>();
        }
        return this.fees;
    }

    /**
     * Gets the value of the revenues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the revenues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRevenues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseRevenueInfo }
     * 
     * 
     */
    public List<CourseRevenueInfo> getRevenues() {
        if (revenues == null) {
            revenues = new ArrayList<CourseRevenueInfo>();
        }
        return this.revenues;
    }

    /**
     * Gets the value of the expenditure property.
     * 
     * @return
     *     possible object is
     *     {@link CourseExpenditureInfo }
     *     
     */
    public CourseExpenditureInfo getExpenditure() {
        return expenditure;
    }

    /**
     * Sets the value of the expenditure property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseExpenditureInfo }
     *     
     */
    public void setExpenditure(CourseExpenditureInfo value) {
        this.expenditure = value;
    }

    /**
     * Gets the value of the courseSpecificLOs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the courseSpecificLOs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCourseSpecificLOs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoDisplayInfo }
     * 
     * 
     */
    public List<LoDisplayInfo> getCourseSpecificLOs() {
        if (courseSpecificLOs == null) {
            courseSpecificLOs = new ArrayList<LoDisplayInfo>();
        }
        return this.courseSpecificLOs;
    }

    /**
     * Gets the value of the gradingOptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gradingOptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGradingOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getGradingOptions() {
        if (gradingOptions == null) {
            gradingOptions = new ArrayList<String>();
        }
        return this.gradingOptions;
    }

    /**
     * Gets the value of the creditOptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creditOptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreditOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultComponentInfo }
     * 
     * 
     */
    public List<ResultComponentInfo> getCreditOptions() {
        if (creditOptions == null) {
            creditOptions = new ArrayList<ResultComponentInfo>();
        }
        return this.creditOptions;
    }

    /**
     * Gets the value of the specialTopicsCourse property.
     * 
     */
    public boolean isSpecialTopicsCourse() {
        return specialTopicsCourse;
    }

    /**
     * Sets the value of the specialTopicsCourse property.
     * 
     */
    public void setSpecialTopicsCourse(boolean value) {
        this.specialTopicsCourse = value;
    }

    /**
     * Gets the value of the pilotCourse property.
     * 
     */
    public boolean isPilotCourse() {
        return pilotCourse;
    }

    /**
     * Sets the value of the pilotCourse property.
     * 
     */
    public void setPilotCourse(boolean value) {
        this.pilotCourse = value;
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
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
