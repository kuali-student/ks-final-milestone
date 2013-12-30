package org.kuali.student.r2.core.process.context;

/**
 * This is the context that is required to evaluate course registration rules
 */
public class ProcessContextInfo implements ProcessContext {

    private String processKey;
    private String personId;
    private String atpId;

    public ProcessContextInfo() {
    }

    public ProcessContextInfo(String processKey, String personId, String atpId) {
        this.processKey = processKey;
        this.personId = personId;
        this.atpId = atpId;
    }

    public ProcessContextInfo(ProcessContext orig) {
        this.setProcessKey(orig.getProcessKey());
        this.setPersonId(orig.getPersonId());
        this.setAtpId(orig.getAtpId());
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }
}
