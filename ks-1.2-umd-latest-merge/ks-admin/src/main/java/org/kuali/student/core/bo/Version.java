package org.kuali.student.core.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Version {

    @Column(name = "VER_IND_ID")
    private String versionIndId;

    @Column(name = "SEQ_NUM")
    private Long sequenceNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CURR_VER_START")
    private Date currentVersionStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CURR_VER_END")
    private Date currentVersionEnd;

    @Column(name = "VER_FROM_ID")
    private String versionedFromId;

    @Column(name = "VER_CMT")
    private String versionComment;

    
    public String getVersionIndId() {
        return versionIndId;
    }

    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Date getCurrentVersionStart() {
        return currentVersionStart;
    }

    public void setCurrentVersionStart(Date currentVersionStart) {
        this.currentVersionStart = currentVersionStart;
    }

    public Date getCurrentVersionEnd() {
        return currentVersionEnd;
    }

    public void setCurrentVersionEnd(Date currentVersionEnd) {
        this.currentVersionEnd = currentVersionEnd;
    }

    public String getVersionedFromId() {
        return versionedFromId;
    }

    public void setVersionedFromId(String versionedFromId) {
        this.versionedFromId = versionedFromId;
    }

    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

}
