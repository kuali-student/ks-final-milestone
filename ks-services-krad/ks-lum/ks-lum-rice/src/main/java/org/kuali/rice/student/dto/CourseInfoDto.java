package org.kuali.rice.student.dto;

import java.io.Serializable;

public class CourseInfoDto implements Serializable {

    private static final long serialVersionUID = 6385381349983978326L;
    
    private String courseId; 
    private String courseTitle;
    private String courseCode;
    private String subjectArea;  
    
    //Do we need the following two fields??
    private String objectId;
    private Long versionNumber;
    
    public CourseInfoDto() {
        super();
    }

    /**
     * Gets the courseId
     * 
     * @return courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the courseId
     * 
     * @param courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the courseTitle
     * 
     * @return courseTitle
     */
    public String getCourseTitle() {
         return courseTitle;
    }

    /**
     * Sets the courseTitle
     * 
     * @param courseTitle
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    
    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    /**
     * Gets the subjectArea
     * 
     * @return subjectArea
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    /**
     * Sets the subjectArea
     * 
     * @param subjectArea
     */
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }
    
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Long getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }
}
