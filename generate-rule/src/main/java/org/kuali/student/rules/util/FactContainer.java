package org.kuali.student.rules.util;

import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;

public class FactContainer {
    private String id;
    private PropositionContainer propositionContainer;
    private CourseEnrollmentRequest request;
    
    public FactContainer() { }

    public FactContainer(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setRequest(CourseEnrollmentRequest request) {
        this.request = request;
    }

    public CourseEnrollmentRequest getRequest() {
        return request;
    }
    
    public void setPropositionContainer(PropositionContainer propositionContainer) {
        this.propositionContainer = propositionContainer;
    }

    public PropositionContainer getPropositionContainer() {
        return propositionContainer;
    }
}
