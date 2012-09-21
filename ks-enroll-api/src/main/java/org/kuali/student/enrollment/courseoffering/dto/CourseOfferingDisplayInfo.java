package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.CourseOfferingDisplay;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.KeyName;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Kuali Student Team (Sambit)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "descr", "courseId",
        "termId", "courseOfferingCode", "courseOfferingTitle", "subjectArea", "termName", "termCode", "studentRegistrationGradingOptions",
        "gradingOption", "creditOption", "typeName", "stateName", "isHonorsOffering", "meta", "attributes", "_futureElements"})
public class CourseOfferingDisplayInfo extends IdNamelessEntityInfo implements CourseOfferingDisplay, Serializable {

    @XmlElement
    RichTextInfo descr;

    @XmlElement
    private String courseId;

    @XmlElement
    private String termId;

    @XmlElement
    private String courseOfferingTitle;

    @XmlElement
    private String courseOfferingCode;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private String termName;

    @XmlElement
    private String termCode;

    @XmlElement
    private List<KeyNameInfo> studentRegistrationGradingOptions;

    @XmlElement
    private KeyNameInfo gradingOption;

    @XmlElement
    private KeyNameInfo creditOption;


    @XmlElement
    private String typeName;


    @XmlElement
    private String stateName;

    @XmlElement
    private Boolean isHonorsOffering;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseOfferingDisplayInfo() {

    }

    public CourseOfferingDisplayInfo(CourseOfferingDisplay courseOfferingDisplay) {

        super(courseOfferingDisplay);
        if (courseOfferingDisplay != null) {
            this.descr = (null != courseOfferingDisplay.getDescr()) ? new RichTextInfo(courseOfferingDisplay.getDescr()) : null;
            this.termCode = courseOfferingDisplay.getTermCode();
            this.termName = courseOfferingDisplay.getTermName();
            this.termId = courseOfferingDisplay.getTermId();
            this.creditOption = new KeyNameInfo(courseOfferingDisplay.getCreditOption());
            this.gradingOption = new KeyNameInfo(courseOfferingDisplay.getGradingOption());
            this.studentRegistrationGradingOptions = new ArrayList<KeyNameInfo>();
            for(KeyName studentRegGradingOption: courseOfferingDisplay.getStudentRegistrationGradingOptions()) {
                this.studentRegistrationGradingOptions.add(new KeyNameInfo(studentRegGradingOption));
            }
            this.typeName = courseOfferingDisplay.getTypeName();
            this.stateName = courseOfferingDisplay.getStateName();
            this.courseId = courseOfferingDisplay.getCourseId();
            this.termId = courseOfferingDisplay.getTermId();
            this.courseOfferingTitle = courseOfferingDisplay.getCourseOfferingTitle();
            this.courseOfferingCode = courseOfferingDisplay.getCourseOfferingCode();
            this.subjectArea = courseOfferingDisplay.getSubjectArea();
        }
    }

    @Override
    public RichText getDescr() {
        return this.descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }
    
    @Override
    public String getCourseId() {
        return this.courseId;
    }

    @Override
    public String getTermId() {
        return this.termId;
    }

    @Override
    public String getCourseOfferingCode() {
        return this.courseOfferingCode;
    }

    @Override
    public String getSubjectArea() {
        return this.subjectArea;
    }

    @Override
    public String getCourseOfferingTitle() {
        return this.courseOfferingTitle;
    }


    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setCourseOfferingTitle(String courseTitle) {
        this.courseOfferingTitle = courseTitle;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }


    @Override
    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    @Override
    public List<KeyNameInfo> getStudentRegistrationGradingOptions() {
        if (this.studentRegistrationGradingOptions == null) {
            return new ArrayList<KeyNameInfo>();
        } else {
            return studentRegistrationGradingOptions;
        }
    }

    public void setStudentRegistrationGradingOptions(List<KeyNameInfo> studentRegistrationGradingOptions) {
        this.studentRegistrationGradingOptions = studentRegistrationGradingOptions;
    }


    @Override
    public KeyNameInfo getCreditOption() {
        return this.creditOption;
    }

    public void setCreditOption(KeyNameInfo creditOption) {
        this.creditOption = creditOption;
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public Boolean getIsHonorsOffering() {
        return this.isHonorsOffering;
    }

    public void setHonorsOffering(Boolean honorsOffering) {
        this.isHonorsOffering = honorsOffering;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public KeyName getGradingOption() {
        return this.gradingOption;
    }

    public void setGradingOption(KeyNameInfo gradingOption) {
        this.gradingOption = gradingOption;
    }
}
