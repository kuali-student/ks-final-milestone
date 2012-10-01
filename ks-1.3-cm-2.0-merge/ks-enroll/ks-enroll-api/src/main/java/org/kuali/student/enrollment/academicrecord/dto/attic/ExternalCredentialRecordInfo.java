package org.kuali.student.enrollment.academicrecord.dto.attic;

import org.kuali.student.enrollment.academicrecord.infc.attic.ExternalCredentialRecord;
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
@XmlType(name = "ExternalCredentialRecordInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "programCode", "dateAdmitted", "dateAwarded", "awardingInstitution",
        "meta", "attributes", "_futureElements"})
public class ExternalCredentialRecordInfo extends IdEntityInfo implements ExternalCredentialRecord, Serializable {
    private static final long serialVersionUID = 1L;
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

    public ExternalCredentialRecordInfo() {

    }

    public ExternalCredentialRecordInfo(ExternalCredentialRecord externalCredentialRecord) {
        super(externalCredentialRecord);
        if (null != externalCredentialRecord) {
            this.programCode = externalCredentialRecord.getProgramCode();
            this.dateAdmitted = (null != externalCredentialRecord.getDateAdmitted()) ? new Date(externalCredentialRecord.getDateAdmitted().getTime()) : null;
            this.dateAwarded = (null != externalCredentialRecord.getDateAwarded()) ? new Date(externalCredentialRecord.getDateAwarded().getTime()) : null;
            this.awardingInstitution = externalCredentialRecord.getAwardingInstitution();
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
