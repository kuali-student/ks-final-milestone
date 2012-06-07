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
        "programId", "programTitle", "programTypeKey", "programCode", 
        "admittedDate", "creditsEarned", "classStanding", "childPrograms", "statusKey",
        "meta", "attributes", "_futureElements"})
public class StudentProgramRecordInfo extends IdEntityInfo implements StudentProgramRecord, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String programId;
    @XmlElement
    private String programTitle;
    @XmlElement
    private String programTypeKey;
    @XmlElement
    private String programCode;
    @XmlElement
    private String admittedDate;
    @XmlElement
    private String creditsEarned;
    @XmlElement
    private String classStanding;
    @XmlElement
    private List<StudentProgramRecordInfo> childPrograms;
    @XmlElement
    private String statusKey;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StudentProgramRecordInfo() {

    }

    public StudentProgramRecordInfo(StudentProgramRecord studentProgramRecord) {
        super(studentProgramRecord);
        if (null != studentProgramRecord) {

            this.programId = studentProgramRecord.getProgramId();
            this.programTitle = studentProgramRecord.getProgramTitle();
            this.programTypeKey = studentProgramRecord.getProgramTypeKey();
            this.programCode = studentProgramRecord.getProgramCode();
            this.admittedDate = studentProgramRecord.getAdmittedDate();
            this.creditsEarned = studentProgramRecord.getCreditsEarned();
            this.classStanding = studentProgramRecord.getClassStanding();
            this.childPrograms = new ArrayList<StudentProgramRecordInfo>();
            for (StudentProgramRecord spr : studentProgramRecord.getChildPrograms()) {
                this.childPrograms.add(new StudentProgramRecordInfo(spr));
            }
            this.statusKey = studentProgramRecord.getStatusKey();
        }
    }

    @Override
    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    @Override
    public String getProgramTypeKey() {
        return programTypeKey;
    }

    public void setProgramTypeKey(String programTypeKey) {
        this.programTypeKey = programTypeKey;
    }

    @Override
    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    @Override
    public String getAdmittedDate() {
        return admittedDate;
    }

    public void setAdmittedDate(String admittedDate) {
        this.admittedDate = admittedDate;
    }

    @Override
    public String getClassStanding() {
        return classStanding;
    }

    public void setClassStanding(String classStanding) {
        this.classStanding = classStanding;
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
    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }
}
