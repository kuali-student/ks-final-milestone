package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.CourseOfferingAdminDisplay;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Kuali Student Team (Sambit)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingAdminDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "courseId",
        "termId", "courseOfferingCode", "courseOfferingTitle", "subjectArea","termName", "termCode", "displayGrading",
         "displayCredit","typeName", "stateName", "activtyOfferingTypes", "meta", "attributes", "_futureElements"})
public class CourseOfferingAdminDisplayInfo extends IdNamelessEntityInfo implements CourseOfferingAdminDisplay, Serializable {

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
    private String displayGrading;

    @XmlElement
    private String displayCredit;


    @XmlElement
    private String typeName;


    @XmlElement
    private String stateName;

    @XmlElement
    private List<String> activtyOfferingTypes;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseOfferingAdminDisplayInfo() {

    }

    public CourseOfferingAdminDisplayInfo(CourseOfferingAdminDisplay courseOfferingAdminDisplay) {

        super(courseOfferingAdminDisplay);
        if (courseOfferingAdminDisplay != null) {

            this.termCode = courseOfferingAdminDisplay.getTermCode();
            this.termName = courseOfferingAdminDisplay.getTermName();
            this.termId = courseOfferingAdminDisplay.getTermId();
            this.displayCredit = courseOfferingAdminDisplay.getDisplayCredit();
            this.displayGrading = courseOfferingAdminDisplay.getDisplayGrading();
            this.activtyOfferingTypes = new ArrayList<String>(courseOfferingAdminDisplay.getActivtyOfferingTypes());
            this.typeName = courseOfferingAdminDisplay.getTypeName();
            this.stateName = courseOfferingAdminDisplay.getStateName();
            this.courseId = courseOfferingAdminDisplay.getCourseId();
            this.termId = courseOfferingAdminDisplay.getTermId();
            this.courseOfferingTitle = courseOfferingAdminDisplay.getCourseOfferingTitle();
            this.courseOfferingCode = courseOfferingAdminDisplay.getCourseOfferingCode();
            this.subjectArea = courseOfferingAdminDisplay.getSubjectArea();
        }
    }

    @Override
    public RichText getDescr() {
        return this.getDescr();
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

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public List<String> getActivtyOfferingTypes() {
        return activtyOfferingTypes;
    }

    public void setActivtyOfferingTypes(List<String> activtyOfferingTypes) {
        this.activtyOfferingTypes = activtyOfferingTypes;
    }
     @Override
    public String getTermCode() {
        return termCode;
    }

    @Override
    public String getDisplayCredit() {
        return displayCredit;
    }

    public void setDisplayCredit(String displayCredit) {
        this.displayCredit = displayCredit;
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }


    @Override
    public String getDisplayGrading() {
        return displayGrading;
    }

    public void setDisplayGrading(String displayGrading) {
        this.displayGrading = displayGrading;
    }
}
