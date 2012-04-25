package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.CourseOfferingDisplay;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;


/**
 * @author Kuali Student Team (Sambit)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "courseId",
        "termId", "courseOfferingCode", "courseTitle",
        "unitsDeployment", "unitsContentOwner",
        "meta", "attributes", "_futureElements"})
public class CourseOfferingDisplayInfo extends IdEntityInfo implements CourseOfferingDisplay, Serializable {

    @XmlElement
    private String courseId;

    @XmlElement
    private String termId;

    @XmlElement
    private String courseTitle;

    @XmlElement
    private String courseOfferingCode;


    @XmlElement
    private String subjectArea;

    @XmlElement
    private List<String> unitsDeployment;

    @XmlElement
    private List<String> unitsContentOwner;




    public CourseOfferingDisplayInfo() {

    }

    public CourseOfferingDisplayInfo(CourseOfferingDisplay courseOfferingDisplay) {

        super(courseOfferingDisplay);
        if (courseOfferingDisplay != null) {


            this.courseId = courseOfferingDisplay.getCourseId();
            this.termId = courseOfferingDisplay.getTermId();
            this.courseTitle = courseOfferingDisplay.getCourseTitle();
            this.courseOfferingCode = courseOfferingDisplay.getCourseOfferingCode();
            this.subjectArea = courseOfferingDisplay.getSubjectArea();

            this.unitsDeployment = courseOfferingDisplay.getUnitsDeployment();
            this.unitsContentOwner = courseOfferingDisplay.getUnitsContentOwner();
        }
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
    public String getCourseTitle() {
        return this.courseTitle;
    }


    @Override
    public List<String> getUnitsDeployment() {
        return this.unitsDeployment;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        return this.unitsContentOwner;
    }


    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

}
