package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.CourseOfferingAdminDisplay;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

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
@XmlType(name = "CourseOfferingAdminDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "descr", "courseId",
        "termId", "courseOfferingCode", "courseTitle",
        "unitsDeployment", "unitsContentOwner",
        "meta", "attributes", "_futureElements"})
public class CourseOfferingAdminDisplayInfo extends IdNamelessEntityInfo implements CourseOfferingAdminDisplay, Serializable {

    @XmlElement
    private String courseId;

    @XmlElement
    private String termId;

    @XmlElement
    private String termName;


    @XmlElement
    private String termCode;

    @XmlElement
    private String courseOfferingTitle;

    @XmlElement
    private String courseOfferingCode;

    @XmlElement
    private String subjectArea;

    @XmlElement
    private List<String> unitsDeployment;

    @XmlElement
    private List<String> unitsDeploymentOrgIds;

    @XmlElement
    private List<String> unitsContentOwner;

    @XmlElement
    private List<String> unitsContentOwnerOrgIds;

    @XmlElement
    private String displayGrading;

    @XmlElement
    private String displayCredit;

    @XmlElement
    private String typeName;

    @XmlElement
    private String stateName;

    @XmlElement
    private List<String> activtyOfferingTypes;


    public CourseOfferingAdminDisplayInfo() {

    }

    public CourseOfferingAdminDisplayInfo(CourseOfferingAdminDisplay courseOfferingAdminDisplay) {

        super(courseOfferingAdminDisplay);
        if (courseOfferingAdminDisplay != null) {


            this.courseId = courseOfferingAdminDisplay.getCourseId();
            this.termId = courseOfferingAdminDisplay.getTermId();
            this.courseOfferingTitle = courseOfferingAdminDisplay.getCourseOfferingTitle();
            this.courseOfferingCode = courseOfferingAdminDisplay.getCourseOfferingCode();
            this.subjectArea = courseOfferingAdminDisplay.getSubjectArea();

            this.unitsDeployment = courseOfferingAdminDisplay.getUnitsDeployment();
            this.unitsContentOwner = courseOfferingAdminDisplay.getUnitsContentOwner();
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
    public String getCourseOfferingTitle() {
        return this.courseOfferingTitle;
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

    public void setCourseOfferingTitle(String courseTitle) {
        this.courseOfferingTitle = courseTitle;
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
