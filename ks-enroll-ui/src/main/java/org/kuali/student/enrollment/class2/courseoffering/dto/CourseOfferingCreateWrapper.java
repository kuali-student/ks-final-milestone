package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingCreateWrapper extends CourseOfferingWrapper {

    private String targetTermCode;
    private String catalogCourseCode;
    private boolean createFromCatalog;

    private String creditCount;

    private boolean showTermOfferingLink;
    private boolean showCatalogLink;
    private boolean showAllSections;
    private boolean enableCreateButton;

    private String courseOfferingSuffix;

    private int noOfTermOfferings;

    private List<FormatOfferingInfo> formatOfferingList;
    private List<ExistingCourseOffering> existingOfferingsInCurrentTerm;
    private List<ExistingCourseOffering> existingTermOfferings;

    private boolean excludeCancelledActivityOfferings;
    private boolean excludeSchedulingInformation;
    private boolean excludeInstructorInformation;

    public CourseOfferingCreateWrapper(){
        super();
        showTermOfferingLink = true;
        formatOfferingList = new ArrayList<FormatOfferingInfo>();
        existingOfferingsInCurrentTerm = new ArrayList<ExistingCourseOffering>();
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

    public boolean isCreateFromCatalog() {
        return createFromCatalog;
    }

    public void setCreateFromCatalog(boolean createFromCatalog) {
        this.createFromCatalog = createFromCatalog;
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

    public String getCourseOfferingSuffix() {
        return courseOfferingSuffix;
    }

    public void setCourseOfferingSuffix(String courseOfferingSuffix) {
        this.courseOfferingSuffix = courseOfferingSuffix;
    }

    public boolean isShowAllSections() {
        return showAllSections;
    }

    public void setShowAllSections(boolean showAllSections) {
        this.showAllSections = showAllSections;
    }

    public List<ExistingCourseOffering> getExistingOfferingsInCurrentTerm() {
        return existingOfferingsInCurrentTerm;
    }

    public void setExistingOfferingsInCurrentTerm(List<ExistingCourseOffering> existingOfferingsInCurrentTerm) {
        this.existingOfferingsInCurrentTerm = existingOfferingsInCurrentTerm;
    }

    public List<ExistingCourseOffering> getExistingTermOfferings() {
        return existingTermOfferings;
    }

    public void setExistingTermOfferings(List<ExistingCourseOffering> existingTermOfferings) {
        this.existingTermOfferings = existingTermOfferings;
    }

    public int getNoOfTermOfferings() {
        return getExistingTermOfferings().size();
    }

    public boolean isEnableCreateButton() {
        return enableCreateButton;
    }

    public void setEnableCreateButton(boolean enableCreateButton) {
        this.enableCreateButton = enableCreateButton;
    }

    public List<FormatOfferingInfo> getFormatOfferingList() {
        return formatOfferingList;
    }

    public void setFormatOfferingList(List<FormatOfferingInfo> formatOfferingList) {
        this.formatOfferingList = formatOfferingList;
    }

    public boolean isExcludeCancelledActivityOfferings() {
        return excludeCancelledActivityOfferings;
    }

    public void setExcludeCancelledActivityOfferings(boolean excludeCancelledActivityOfferings) {
        this.excludeCancelledActivityOfferings = excludeCancelledActivityOfferings;
    }

    public boolean isExcludeSchedulingInformation() {
        return excludeSchedulingInformation;
    }

    public void setExcludeSchedulingInformation(boolean excludeSchedulingInformation) {
        this.excludeSchedulingInformation = excludeSchedulingInformation;
    }

    public boolean isExcludeInstructorInformation() {
        return excludeInstructorInformation;
    }

    public void setExcludeInstructorInformation(boolean excludeInstructorInformation) {
        this.excludeInstructorInformation = excludeInstructorInformation;
    }

    public void clear(){
        setCourse(null);
        setShowAllSections(false);
        setCreditCount("");
        getExistingTermOfferings().clear();
        getExistingOfferingsInCurrentTerm().clear();
        setEnableCreateButton(false);
        setExcludeCancelledActivityOfferings(false);
        setExcludeSchedulingInformation(false);
        setExcludeInstructorInformation(false);
    }
}
