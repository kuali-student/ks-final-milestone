package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;

import java.io.Serializable;

public class CourseOfferingCreateWrapper implements Serializable{

    private String targetTermCode;
    private String catalogCourseCode;
    private String courseCodeSuffix;
    private boolean createFromCatalog;

    private String creditCount;

    private boolean showTermOfferingLink;
    private boolean showCatalogLink;

    private CourseInfo course;
    private CourseOfferingInfo coInfo;

    public CourseOfferingCreateWrapper(){
        showCatalogLink = true;
        showTermOfferingLink = true;
    }

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

    public String getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }

    public boolean isShowTermOfferingLink() {
        return showTermOfferingLink;
    }

    public void setShowTermOfferingLink(boolean showTermOfferingLink) {
        this.showTermOfferingLink = showTermOfferingLink;
    }

    public boolean isShowCatalogLink() {
        return showCatalogLink;
    }

    public void setShowCatalogLink(boolean showCatalogLink) {
        this.showCatalogLink = showCatalogLink;
    }
}
