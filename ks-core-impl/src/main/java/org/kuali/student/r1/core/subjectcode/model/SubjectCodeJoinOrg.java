package org.kuali.student.r1.core.subjectcode.model;

import org.kuali.student.r2.common.entity.BaseVersionEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "KSSC_SUBJ_CD_JN_ORG")
public class SubjectCodeJoinOrg extends BaseVersionEntity {

	private static final long serialVersionUID = 4071877051631187891L;

    @Column(name = "ORG_ID")
    private String orgId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = SubjectCode.class)
    @JoinColumn(name = "SUBJ_CD_ID")
    private SubjectCode subjectCode;

	@Column(name = "EFF_DT")
    protected Timestamp activeFromDate;

    @Column(name = "EXPIR_DT")
    protected Timestamp activeToDate;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public SubjectCode getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(SubjectCode subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Timestamp getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(Timestamp activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public Timestamp getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(Timestamp activeToDate) {
        this.activeToDate = activeToDate;
    }

    public boolean isActive() {
        long asOfDate = System.currentTimeMillis();

        return (this.getActiveFromDate() == null || asOfDate >= this.getActiveFromDate().getTime())
                && (this.getActiveToDate() == null || asOfDate < this.getActiveToDate().getTime());
    }
}
