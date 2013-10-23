package org.kuali.student.cm.course.form;

import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

public class LoDisplayInfoWrapper extends LoDisplayInfo {
    
    private static final long serialVersionUID = 8232176748014317444L;
    
    private String searchBy;
    
    private String code;
    
    private String courseTitle;
    
    private String programTitle;
    
    private String typeName;
    
    private String orgName;
    
    private String orgType;
    
    private String courseNumber;
    
    private String loCategory;
    
    private String loDescription;
    
    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getLoCategory() {
        return loCategory;
    }

    public void setLoCategory(String loCategory) {
        this.loCategory = loCategory;
    }

    public String getLoDescription() {
        return loDescription;
    }

    public void setLoDescription(String loDescription) {
        this.loDescription = loDescription;
    }
    
}
