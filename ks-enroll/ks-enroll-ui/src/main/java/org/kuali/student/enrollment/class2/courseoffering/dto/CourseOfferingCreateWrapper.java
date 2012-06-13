package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;

import java.io.Serializable;

public class CourseOfferingCreateWrapper implements Serializable{

    private String targetTermCode;
    private String catalogCourseCode;
    private String courseCodeSuffix;
    private boolean createFromCatalog;

    private CourseInfo course;
    private CourseOfferingInfo coInfo;

    public String getTargetTermCode() {
        return targetTermCode;
    }

    public void setTargetTermCode(String targetTermCode) {
        this.targetTermCode = targetTermCode;
    }

    public String getCatalogCourseCode() {
        return catalogCourseCode;
    }

    public void setCatalogCourseCode(String catalogCourseCode) {
        this.catalogCourseCode = catalogCourseCode;
    }

    public String getCourseCodeSuffix() {
        return courseCodeSuffix;
    }

    public void setCourseCodeSuffix(String courseCodeSuffix) {
        this.courseCodeSuffix = courseCodeSuffix;
    }

    public boolean isCreateFromCatalog() {
        return createFromCatalog;
    }

    public void setCreateFromCatalog(boolean createFromCatalog) {
        this.createFromCatalog = createFromCatalog;
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public CourseOfferingInfo getCoInfo() {
        return coInfo;
    }

    public void setCoInfo(CourseOfferingInfo coInfo) {
        this.coInfo = coInfo;
    }

}
