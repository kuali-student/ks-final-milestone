package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.registration.course.infc.RegResponse;
import org.kuali.student.r2.common.dto.IdEntityInfo;


public class RegResponseInfo extends IdEntityInfo implements RegResponse, Serializable {

    private static final long serialVersionUID = 1L;

    private String overallRegStatus;

    private List<String> regMessages;

    private List<String> regWarnings;

    private List<String> regErrors;

    @Override
    public List<String> getRegMessages() {
        return regMessages;
    }

    @Override
    public String getOverallRegStatus() {
        return overallRegStatus;
    }

    @Override
    public List<String> getRegWarnings() {
        return regWarnings;
    }

 
    public void setOverallRegStatus(String overallregStatus) {
        this.overallRegStatus = overallregStatus;
    }

    public void setRegMessages(List<String> regMessages) {
        this.regMessages = regMessages;
    }

    public void setRegWarnings(List<String> regWarnings) {
        this.regWarnings = regWarnings;
    }

    public void setRegErrors(List<String> regErrors) {
        this.regErrors = regErrors;
    }

    @Override
    public List<String> getRegErrors() {
        return regErrors;
    }

}
