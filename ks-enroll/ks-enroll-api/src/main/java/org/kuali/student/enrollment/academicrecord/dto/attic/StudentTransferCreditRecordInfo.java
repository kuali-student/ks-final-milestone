package org.kuali.student.enrollment.academicrecord.dto.attic;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.infc.StudentCourseRecord;
import org.kuali.student.enrollment.academicrecord.infc.StudentTestScoreRecord;
import org.kuali.student.enrollment.academicrecord.infc.attic.StudentTransferCreditRecord;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentTransferCreditRecordInfo", propOrder = {
        "id", "meta", "attributes",
        "awardedCourseId", "awardedCourseTitle", "awardedCourseCode", "awardedCredits", "dateAwarded", "sourceId", "sourceWork", "subComponents",
        "typeKey", "stateKey", "_futureElements"})
public class StudentTransferCreditRecordInfo extends IdNamelessEntityInfo implements StudentTransferCreditRecord, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    public String awardedCourseId;

    @XmlElement
    public String awardedCourseTitle;

    @XmlElement
    public String awardedCourseCode;

    @XmlElement
    public Float awardedCredits;

    @XmlElement
    public Date dateAwarded;

    @XmlElement
    public String sourceId;

    @XmlElement
    public List<StudentCourseRecordInfo> sourceWork;

    @XmlElement
    public List<StudentTestScoreRecordInfo> subComponents;

    @XmlAnyElement
    private List<Element> _futureElements;

    public StudentTransferCreditRecordInfo() {

    }

    public StudentTransferCreditRecordInfo(StudentTransferCreditRecord studentTransferCreditRecord) {
        super(studentTransferCreditRecord);
        if (null != studentTransferCreditRecord) {
            this.awardedCourseId = studentTransferCreditRecord.getAwardedCourseId();
            this.awardedCourseTitle = studentTransferCreditRecord.getAwardedCourseTitle();
            this.awardedCourseCode = studentTransferCreditRecord.getAwardedCourseCode();
            this.awardedCredits = studentTransferCreditRecord.getAwardedCredits();
            this.dateAwarded = (null != studentTransferCreditRecord.getDateAwarded()) ? new Date(studentTransferCreditRecord.getDateAwarded().getTime()) : null;
            this.sourceId = studentTransferCreditRecord.getSourceId();
            this.sourceWork = new ArrayList<StudentCourseRecordInfo>();
            for (StudentCourseRecord studentCourseRecord : studentTransferCreditRecord.getSourceWork()) {
                this.sourceWork.add(new StudentCourseRecordInfo(studentCourseRecord));
            }
            this.subComponents = new ArrayList<StudentTestScoreRecordInfo>();
            for (StudentTestScoreRecord studentTestScoreRecord : studentTransferCreditRecord.getSubComponents()) {
                this.subComponents.add(new StudentTestScoreRecordInfo(studentTestScoreRecord));
            }
        }
    }

    @Override
    public String getAwardedCourseId() {
        return awardedCourseId;
    }

    public void setAwardedCourseId(String awardedCourseId) {
        this.awardedCourseId = awardedCourseId;
    }

    @Override
    public String getAwardedCourseTitle() {
        return awardedCourseTitle;
    }

    public void setAwardedCourseTitle(String awardedCourseTitle) {
        this.awardedCourseTitle = awardedCourseTitle;
    }

    @Override
    public String getAwardedCourseCode() {
        return awardedCourseCode;
    }

    public void setAwardedCourseCode(String awardedCourseCode) {
        this.awardedCourseCode = awardedCourseCode;
    }

    @Override
    public Float getAwardedCredits() {
        return awardedCredits;
    }

    public void setAwardedCredits(Float awardedCredits) {
        this.awardedCredits = awardedCredits;
    }

    @Override
    public Date getDateAwarded() {
        return dateAwarded;
    }

    public void setDateAwarded(Date dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public List<StudentCourseRecordInfo> getSourceWork() {
        return sourceWork;
    }

    public void setSourceWork(List<StudentCourseRecordInfo> sourceWork) {
        this.sourceWork = sourceWork;
    }

    @Override
    public List<StudentTestScoreRecordInfo> getSubComponents() {
        return subComponents;
    }

    public void setSubComponents(List<StudentTestScoreRecordInfo> subComponents) {
        this.subComponents = subComponents;
    }
}
