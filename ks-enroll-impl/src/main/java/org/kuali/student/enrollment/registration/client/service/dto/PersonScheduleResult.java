package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonScheduleResult", propOrder = {
        "userId", "studentScheduleTermResults"})
public class PersonScheduleResult {

    private String userId;
    private List<StudentScheduleTermResult> studentScheduleTermResults;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<StudentScheduleTermResult> getStudentScheduleTermResults() {
        return studentScheduleTermResults;
    }

    public void setStudentScheduleTermResults(List<StudentScheduleTermResult> studentScheduleTermResults) {
        this.studentScheduleTermResults = studentScheduleTermResults;
    }
}
