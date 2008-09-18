package org.kuali.student.embedded.beans;

import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;

public class CourseParams {
    private String userName;
    private String documentId;
    private boolean acknowledgeRequested;
    private boolean approvalRequested;
    
    private CluCreateInfo clu;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public CluCreateInfo getClu() {
        return clu;
    }
    public void setClu(CluCreateInfo clu) {
        this.clu = clu;
    }
    public boolean isAcknowledgeRequested() {
        return acknowledgeRequested;
    }
    public void setAcknowledgeRequested(boolean acknowledgeRequested) {
        this.acknowledgeRequested = acknowledgeRequested;
    }
    public boolean isApprovalRequested() {
        return approvalRequested;
    }
    public void setApprovalRequested(boolean approvalRequested) {
        this.approvalRequested = approvalRequested;
    }

}
