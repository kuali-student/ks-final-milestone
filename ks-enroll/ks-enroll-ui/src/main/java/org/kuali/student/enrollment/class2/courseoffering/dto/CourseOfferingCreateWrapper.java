package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseOfferingCreateWrapper implements Serializable{

    private String targetTermCode;
    private String catalogCourseCode;
    private String courseCodeSuffix;
    private boolean createFromCatalog;

    private String creditCount;

    private boolean showTermOfferingLink;
    private boolean showCatalogLink;
    private boolean showAllSections;

    private String addCourseOfferingSuffix;

    private CourseInfo course;
    private CourseOfferingInfo coInfo;

    private TermInfo term;

    private List<FormatOfferingInfo> formatOfferingList;
    private List<ExistingCourseOffering> existingCourseOfferings;
    private List<ExistingCourseOffering> existingTermOfferings;

    public CourseOfferingCreateWrapper(){
        showCatalogLink = false;
        showTermOfferingLink = true;
        showAllSections = false;
        formatOfferingList = new ArrayList<FormatOfferingInfo>();
        existingCourseOfferings = new ArrayList<ExistingCourseOffering>();
        existingTermOfferings = new ArrayList<ExistingCourseOffering>();
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

    public String getAddCourseOfferingSuffix() {
        return addCourseOfferingSuffix;
    }

    public void setAddCourseOfferingSuffix(String addCourseOfferingSuffix) {
        this.addCourseOfferingSuffix = addCourseOfferingSuffix;
    }

    public List<FormatOfferingInfo> getFormatOfferingList() {
        return formatOfferingList;
    }

    public void setFormatOfferingList(List<FormatOfferingInfo> formatOfferingList) {
        this.formatOfferingList = formatOfferingList;
    }

    public boolean isShowAllSections() {
        return showAllSections;
    }

    public void setShowAllSections(boolean showAllSections) {
        this.showAllSections = showAllSections;
    }

    public List<ExistingCourseOffering> getExistingCourseOfferings() {
        return existingCourseOfferings;
    }

    public void setExistingCourseOfferings(List<ExistingCourseOffering> existingCourseOfferings) {
        this.existingCourseOfferings = existingCourseOfferings;
    }

    public TermInfo getTerm() {
        return term;
    }

    public void setTerm(TermInfo term) {
        this.term = term;
    }

    public List<ExistingCourseOffering> getExistingTermOfferings() {
        return existingTermOfferings;
    }

    public void setExistingTermOfferings(List<ExistingCourseOffering> existingTermOfferings) {
        this.existingTermOfferings = existingTermOfferings;
    }

}
