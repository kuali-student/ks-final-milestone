package org.kuali.student.myplan.audit.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.myplan.audit.service.DegreeAuditServiceConstants;

public class DegreeAuditForm extends UifFormBase {

    private String auditHtml;
    private String campusParam;
    private String programParam;
    private String auditId;
    private String auditType = DegreeAuditServiceConstants.AUDIT_TYPE_KEY_DEFAULT;

    private String howToUseDegreeAudit;
    private String theAuditFeatureDisabled;
    private String trackYourProgress;
    private String stayOnTopOfUrRequired;
    private String selectYourProgram;
    private String exploreProgramsAndMinors;
    private String stillDeciding;
    private String selectAProgramFrom;
    private String selectYourCampus;
    private String someProgramsNotIncluded;

    public String getProgramParam() {
        return programParam;
    }

    public void setProgramParam(String programParam) {
        this.programParam = programParam;
    }

    public String getTheAuditFeatureDisabled() {
        return theAuditFeatureDisabled;
    }

    public void setTheAuditFeatureDisabled(String theAuditFeatureDisabled) {
        this.theAuditFeatureDisabled = theAuditFeatureDisabled;
    }

    public String getTrackYourProgress() {
        return trackYourProgress;
    }

    public void setTrackYourProgress(String trackYourProgress) {
        this.trackYourProgress = trackYourProgress;
    }

    public String getStayOnTopOfUrRequired() {
        return stayOnTopOfUrRequired;
    }

    public void setStayOnTopOfUrRequired(String stayOnTopOfUrRequired) {
        this.stayOnTopOfUrRequired = stayOnTopOfUrRequired;
    }

    public String getSelectYourProgram() {
        return selectYourProgram;
    }

    public void setSelectYourProgram(String selectYourProgram) {
        this.selectYourProgram = selectYourProgram;
    }

    public String getExploreProgramsAndMinors() {
        return exploreProgramsAndMinors;
    }

    public void setExploreProgramsAndMinors(String exploreProgramsAndMinors) {
        this.exploreProgramsAndMinors = exploreProgramsAndMinors;
    }

    public String getStillDeciding() {
        return stillDeciding;
    }

    public void setStillDeciding(String stillDeciding) {
        this.stillDeciding = stillDeciding;
    }

    public String getSelectAProgramFrom() {
        return selectAProgramFrom;
    }

    public void setSelectAProgramFrom(String selectAProgramFrom) {
        this.selectAProgramFrom = selectAProgramFrom;
    }

    public String getSelectYourCampus() {
        return selectYourCampus;
    }

    public void setSelectYourCampus(String selectYourCampus) {
        this.selectYourCampus = selectYourCampus;
    }

    public String getSomeProgramsNotIncluded() {
        return someProgramsNotIncluded;
    }

    public void setSomeProgramsNotIncluded(String someProgramsNotIncluded) {
        this.someProgramsNotIncluded = someProgramsNotIncluded;
    }

    public String getHowToUseDegreeAudit() {
        return howToUseDegreeAudit;
    }

    public void setHowToUseDegreeAudit(String howToUseDegreeAudit) {
        this.howToUseDegreeAudit = howToUseDegreeAudit;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

//    public String getProgramParamSeattle() {
//        return programParamSeattle;
//    }
//
//    public void setProgramParamSeattle(String programParamSeattle) {
//        this.programParamSeattle = programParamSeattle;
//    }

    public String getCampusParam() {
        return campusParam;
    }

    public void setCampusParam(String campusParam) {
        this.campusParam = campusParam;
    }

    public String getAuditHtml() {
        return auditHtml;
    }

    public void setAuditHtml(String auditHtml) {
        this.auditHtml = auditHtml;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

}
