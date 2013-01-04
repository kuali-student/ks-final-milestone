package org.kuali.student.r2.core.process.context;

public class HoldCheckContext extends CheckContext {

    private String atpKey;
    private String studentId;

    public HoldCheckContext() {
    }

    public String getAtpKey() {
        return atpKey;
    }

    public void setAtpKey(String atpKey) {
        this.atpKey = atpKey;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    
}
