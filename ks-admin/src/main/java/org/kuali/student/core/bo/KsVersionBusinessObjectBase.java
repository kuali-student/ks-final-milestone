package org.kuali.student.core.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.student.common.util.UUIDHelper;

public abstract class KsVersionBusinessObjectBase extends KsMetaBusinessObjectBase implements KsVersionBusinessObject {

    private static final long serialVersionUID = -1307889585226114528L;

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
    
    
    @Override
    public void prePersist() {
        super.prePersist();
        
        if(this.getSequenceNumber() == null){
            this.setSequenceNumber(Long.valueOf(1));
        }
        
        this.setVersionIndId(UUIDHelper.genStringUUID(this.getVersionIndId()));
    }

    /**
     * This will take properties and create a Version object to return, the value is not persisted.
     * This is to support the KS embedded style Version object for future compatibility.  OJB/KNS
     * do not seem to support embedded entities (called Nested in OJB terms).
     */
    @Override
    public Version getVersion() {
        Version version = new Version();
        
        version.setVersionIndId(versionIndId);
        version.setSequenceNumber(sequenceNumber);
        version.setVersionedFromId(versionedFromId);
        version.setVersionComment(versionComment);
        version.setCurrentVersionStart(currentVersionStart);
        version.setCurrentVersionEnd(currentVersionEnd);
        
        return version;
    }
    
    /**
     * This will set the internal properties from the incoming Version object.
     * This is to support the KS embedded style Version object for future compatibility.  OJB/KNS
     * do not seem to support embedded entities (called Nested in OJB terms).
     * 
     * Uses setter methods to allow behavior from subclass.
     */
    @Override
    public void setVersion(Version version) {
        this.setVersionIndId(version.getVersionIndId());
        this.setSequenceNumber(version.getSequenceNumber());
        this.setVersionedFromId(version.getVersionedFromId());
        this.setVersionComment(version.getVersionComment());
        this.setCurrentVersionStart(version.getCurrentVersionStart());
        this.setCurrentVersionEnd(version.getCurrentVersionEnd());
    }
    
    @Override
    public String getVersionIndId() {
        return versionIndId;
    }
    
    @Override
    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    @Override
    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public Date getCurrentVersionStart() {
        return currentVersionStart;
    }

    @Override
    public void setCurrentVersionStart(Date currentVersionStart) {
        this.currentVersionStart = currentVersionStart;
    }

    @Override
    public Date getCurrentVersionEnd() {
        return currentVersionEnd;
    }

    @Override
    public void setCurrentVersionEnd(Date currentVersionEnd) {
        this.currentVersionEnd = currentVersionEnd;
    }

    @Override
    public String getVersionedFromId() {
        return versionedFromId;
    }

    @Override
    public void setVersionedFromId(String versionedFromId) {
        this.versionedFromId = versionedFromId;
    }

    @Override
    public String getVersionComment() {
        return versionComment;
    }

    @Override
    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

}
