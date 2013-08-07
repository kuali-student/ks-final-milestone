package org.kuali.student.enrollment.academicrecord.dto;

import org.kuali.student.enrollment.academicrecord.infc.StudentCredentialRecord;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentCredentialRecordInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "programId", "programTitle", "programCode", "dateAdmitted", "dateAwarded", "awardingInstitution",
        "meta", "attributes", "_futureElements"})
public class StudentCredentialRecordInfo extends IdEntityInfo implements StudentCredentialRecord, Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement
    private String programId;
    @XmlElement
    private String programTitle;
    @XmlElement
    private String programCode;
    @XmlElement
    private Date dateAdmitted;
    @XmlElement
    private Date dateAwarded;
    @XmlElement
    private String awardingInstitution;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StudentCredentialRecordInfo() {

    }

    public StudentCredentialRecordInfo(StudentCredentialRecord studentCredentialRecord) {
        super(studentCredentialRecord);
        if (null != studentCredentialRecord) {
            this.programId = studentCredentialRecord.getProgramId();
            this.programTitle = studentCredentialRecord.getProgramTitle();
            this.programCode = studentCredentialRecord.getProgramCode();
            this.dateAdmitted = (null != studentCredentialRecord.getDateAdmitted()) ?
                    new Date(studentCredentialRecord.getDateAdmitted().getTime()) : null;
            this.dateAwarded = (null != studentCredentialRecord.getDateAwarded()) ?
                    new Date(studentCredentialRecord.getDateAwarded().getTime()) : null;
            this.awardingInstitution = studentCredentialRecord.getAwardingInstitution();
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
    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    @Override
    public Date getDateAdmitted() {
        return dateAdmitted;
    }

    public void setDateAdmitted(Date dateAdmitted) {
        this.dateAdmitted = dateAdmitted;
    }

    @Override
    public Date getDateAwarded() {
        return dateAwarded;
    }

    public void setDateAwarded(Date dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    @Override
    public String getAwardingInstitution() {
        return awardingInstitution;
    }

    public void setAwardingInstitution(String awardingInstitution) {
        this.awardingInstitution = awardingInstitution;
    }
}
