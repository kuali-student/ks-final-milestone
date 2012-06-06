package org.kuali.student.enrollment.academicrecord.dto;

import org.kuali.student.enrollment.academicrecord.infc.StudentProgramRecord;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentProgramRecordInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "programCode", "major", "minor", "admittedDate", "creditsEarned", "childPrograms", "completionStatusTypeKey",
        "meta", "attributes", "_futureElements"})
public class StudentProgramRecordInfo extends IdEntityInfo implements StudentProgramRecord, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String programCode;
    @XmlElement
    private String major;
    @XmlElement
    private String minor;
    @XmlElement
    private String admittedDate;
    @XmlElement
    private String creditsEarned;
    @XmlElement
    private List<StudentProgramRecordInfo> childPrograms;
    @XmlElement
    private String completionStatusTypeKey;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StudentProgramRecordInfo() {

    }

    public StudentProgramRecordInfo(StudentProgramRecord studentProgramRecord) {
        super(studentProgramRecord);
        if (null != studentProgramRecord) {
            this.programCode = studentProgramRecord.getProgramCode();
            this.major = studentProgramRecord.getMajor();
            this.minor = studentProgramRecord.getMinor();
            this.admittedDate = studentProgramRecord.getAdmittedDate();
            this.creditsEarned = studentProgramRecord.getCreditsEarned();
            this.childPrograms = new ArrayList<StudentProgramRecordInfo>();
            for (StudentProgramRecord spr : studentProgramRecord.getChildPrograms()) {
                this.childPrograms.add(new StudentProgramRecordInfo(spr));
            }
            this.completionStatusTypeKey = studentProgramRecord.getCompletionStatusTypeKey();
        }
    }

    @Override
    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    @Override
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    @Override
    public String getAdmittedDate() {
        return admittedDate;
    }

    public void setAdmittedDate(String admittedDate) {
        this.admittedDate = admittedDate;
    }

    @Override
    public List<StudentProgramRecordInfo> getChildPrograms() {
        return childPrograms;
    }

    public void setChildPrograms(List<StudentProgramRecordInfo> childPrograms) {
        this.childPrograms = childPrograms;
    }

    @Override
    public String getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(String creditsEarned) {
        this.creditsEarned = creditsEarned;
    }

    @Override
    public String getCompletionStatusTypeKey() {
        return completionStatusTypeKey;
    }

    public void setCompletionStatusTypeKey(String completionStatusTypeKey) {
        this.completionStatusTypeKey = completionStatusTypeKey;
    }
}
