package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LearningPlanItemResult", propOrder = {
        "refObjectId", "refObjectType", "learningPlanId", "planItemTermId",
        "category", "cluId", "courseId", "courseCode", "creditOptions",
        "regGroupId", "regGroupCode", "state"})
public class LearningPlanItemResult implements Serializable {

    private String refObjectId;
    private String refObjectType;
    private String learningPlanId;
    private String planItemTermId;
    private String category;
    private String cluId;
    private String courseId;
    private String courseCode;
    private List<String> creditOptions;
    private String regGroupId;
    private String regGroupCode;
    private String state;

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public String getRefObjectType() {
        return refObjectType;
    }

    public void setRefObjectType(String refObjectType) {
        this.refObjectType = refObjectType;
    }

    public String getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(String learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public String getPlanItemTermId() {
        return planItemTermId;
    }

    public void setPlanItemTermId(String planItemTermId) {
        this.planItemTermId = planItemTermId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public String getRegGroupCode() {
        return regGroupCode;
    }

    public void setRegGroupCode(String regGroupCode) {
        this.regGroupCode = regGroupCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getCreditOptions() {
        return creditOptions;
    }

    public void setCreditOptions(List<String> creditOptions) {
        this.creditOptions = creditOptions;
    }
}
