package org.kuali.student.r2.core.process.context;

/**
 * 
 * This is a description of what this class does - sambit don't forget to fill this in. 
 * 
 * @author Kuali Student(sambitpa@usc.edu)
 *
 */
public class CourseRegistrationProcessContextInfo extends ProcessContext {

    private String studentId;

    private String termKey;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTermKey() {
        return termKey;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    private CourseRegistrationProcessContextInfo(String studentId, String termKey) {
        this.studentId = studentId;
        this.termKey = termKey;
    }

    public static CourseRegistrationProcessContextInfo createForRegistrationEligibility(String studentId, String termKey) {
        return new CourseRegistrationProcessContextInfo(studentId, termKey);

    }

}
