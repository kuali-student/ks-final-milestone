package org.kuali.student.core.bo;

import java.util.Date;

public interface KsVersionBusinessObject extends KsMetaBusinessObject {

    public Date getCurrentVersionStart();

    public void setCurrentVersionStart(Date currentVersionStart);

    public Date getCurrentVersionEnd();

    public void setCurrentVersionEnd(Date currentVersionEnd);
            
    public String getVersionIndId();

    public void setVersionIndId(String versionIndId);

    public Long getSequenceNumber();

    public void setSequenceNumber(Long sequenceNumber);

    public String getVersionComment();

    public void setVersionComment(String versionComment);
    
    public String getVersionedFromId();

    public void setVersionedFromId(String versionedFromId);

    public Version getVersion();

    public void setVersion(Version version);
    
}
