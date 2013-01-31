package org.kuali.student.myplan.audit.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.myplan.audit.service.DegreeAuditServiceConstants;

public class DegreeAuditForm extends UifFormBase {

    private String auditHtml;
    private String programParam_for_campus_310;
    private String programParam_for_campus_311;
    private String programParam_for_campus_312;
    private String programParam_for_campus_313;
    private String programParam_for_campus_314;
    private String programParam_for_campus_315;
    private String programParam_for_campus_316;
    private String programParam_for_campus_317;

    //private String programParamSeattle;
//    private String programParamBothell;
//    private String programParamTacoma;
    private String campusParam;
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

    public String getProgramParam_for_campus_310() {
        return programParam_for_campus_310;
    }

    public void setProgramParam_for_campus_310(String programParam_for_campus_310) {
        this.programParam_for_campus_310 = programParam_for_campus_310;
    }

    public String getProgramParam_for_campus_311() {
        return programParam_for_campus_311;
    }

    public void setProgramParam_for_campus_311(String programParam_for_campus_311) {
        this.programParam_for_campus_311 = programParam_for_campus_311;
    }

    public String getProgramParam_for_campus_312() {
        return programParam_for_campus_312;
    }

    public void setProgramParam_for_campus_312(String programParam_for_campus_312) {
        this.programParam_for_campus_312 = programParam_for_campus_312;
    }

    public String getProgramParam_for_campus_313() {
        return programParam_for_campus_313;
    }

    public void setProgramParam_for_campus_313(String programParam_for_campus_313) {
        this.programParam_for_campus_313 = programParam_for_campus_313;
    }

    public String getProgramParam_for_campus_314() {
        return programParam_for_campus_314;
    }

    public void setProgramParam_for_campus_314(String programParam_for_campus_314) {
        this.programParam_for_campus_314 = programParam_for_campus_314;
    }

    public String getProgramParam_for_campus_315() {
        return programParam_for_campus_315;
    }

    public void setProgramParam_for_campus_315(String programParam_for_campus_315) {
        this.programParam_for_campus_315 = programParam_for_campus_315;
    }

    public String getProgramParam_for_campus_316() {
        return programParam_for_campus_316;
    }

    public void setProgramParam_for_campus_316(String programParam_for_campus_316) {
        this.programParam_for_campus_316 = programParam_for_campus_316;
    }

    public String getProgramParam_for_campus_317() {
        return programParam_for_campus_317;
    }

    public void setProgramParam_for_campus_317(String programParam_for_campus_317) {
        this.programParam_for_campus_317 = programParam_for_campus_317;
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
