package org.kuali.student.myplan.audit.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.myplan.audit.service.DegreeAuditServiceConstants;

public class DegreeAuditForm extends UifFormBase {

    private String auditHtml;
    private String programParamSeattle;
    private String programParamBothell;
    private String programParamTacoma;
    private String campusParam;
    private String auditId;
    private String auditType = DegreeAuditServiceConstants.AUDIT_TYPE_KEY_DEFAULT;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getProgramParamSeattle() {
        return programParamSeattle;
    }

    public void setProgramParamSeattle(String programParamSeattle) {
        this.programParamSeattle = programParamSeattle;
    }

    public String getProgramParamBothell() {
        return programParamBothell;
    }

    public void setProgramParamBothell(String programParamBothell) {
        this.programParamBothell = programParamBothell;
    }

    public String getProgramParamTacoma() {
        return programParamTacoma;
    }

    public void setProgramParamTacoma(String programParamTacoma) {
        this.programParamTacoma = programParamTacoma;
    }

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
