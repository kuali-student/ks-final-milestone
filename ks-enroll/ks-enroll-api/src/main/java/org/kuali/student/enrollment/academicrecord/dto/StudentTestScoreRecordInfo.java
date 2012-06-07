package org.kuali.student.enrollment.academicrecord.dto;

import org.kuali.student.enrollment.academicrecord.infc.StudentTestScoreRecord;
import org.kuali.student.r2.common.dto.IdEntityInfo;

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
@XmlType(name = "StudentTestScoreRecordInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "testTitle", "testCode", "testTypeKey", "dateTaken", "scoreValue", "scorePercent", "subComponents",
        "meta", "attributes", "_futureElements"})
public class StudentTestScoreRecordInfo extends IdEntityInfo implements StudentTestScoreRecord, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    public String testTitle;
    @XmlElement
    public String testCode;
    @XmlElement
    public String testTypeKey;
    @XmlElement
    public Date dateTaken;
    @XmlElement
    public String scoreValue;
    @XmlElement
    public String scorePercent;
    @XmlElement
    public List<StudentTestScoreRecordInfo> subComponents;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StudentTestScoreRecordInfo() {

    }

    public StudentTestScoreRecordInfo(StudentTestScoreRecord studentTestScoreRecord) {
        super(studentTestScoreRecord);
        if (null != studentTestScoreRecord) {
            this.testCode = studentTestScoreRecord.getTestCode();
            this.testTypeKey = studentTestScoreRecord.getTestTypeKey();
            this.dateTaken = (null != studentTestScoreRecord.getDateTaken()) ? new Date(studentTestScoreRecord.getDateTaken().getTime()) : null;
            this.scoreValue = studentTestScoreRecord.getScoreValue();
            this.scorePercent = studentTestScoreRecord.getScorePercent();
            this.subComponents = new ArrayList<StudentTestScoreRecordInfo>();
            for (StudentTestScoreRecord stsr : studentTestScoreRecord.getSubComponents()) {
                this.subComponents.add(new StudentTestScoreRecordInfo(stsr));
            }
        }
    }

    @Override
    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    @Override
    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    @Override
    public String getTestTypeKey() {
        return testTypeKey;
    }

    public void setTestTypeKey(String testTypeKey) {
        this.testTypeKey = testTypeKey;
    }

    @Override
    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public String getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(String scoreValue) {
        this.scoreValue = scoreValue;
    }

    @Override
    public String getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(String scorePercent) {
        this.scorePercent = scorePercent;
    }

    @Override
    public List<StudentTestScoreRecordInfo> getSubComponents() {
        return subComponents;
    }

    public void setSubComponents(List<StudentTestScoreRecordInfo> subComponents) {
        this.subComponents = subComponents;
    }
}
