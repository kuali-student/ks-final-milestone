/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class VersionEntity extends MetaEntity {

    @Column(name = "VER_IND_ID")
    private String versionIndId;

    @Column(name = "SEQ_NUM")
    private Integer sequenceNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable=false)
    private Date versionCreateTime;

    @Column(name = "VER_CREATE_ID")
    private String versionCreateId;

    @Column(name = "IS_CURR_VER")
    private boolean isCurrentVersion;

    @Column(name = "VER_CMT")
    private String versionComment;
            
    public String getVersionIndId() {
        return versionIndId;
    }

    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Date getVersionCreateTime() {
        return versionCreateTime;
    }

    public void setVersionCreateTime(Date versionCreateTime) {
        this.versionCreateTime = versionCreateTime;
    }

    public String getVersionCreateId() {
        return versionCreateId;
    }

    public void setVersionCreateId(String versionCreateId) {
        this.versionCreateId = versionCreateId;
    }

    public boolean isCurrentVersion() {
        return isCurrentVersion;
    }

    public void setCurrentVersion(boolean isCurrentVersion) {
        this.isCurrentVersion = isCurrentVersion;
    }

    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }
}
