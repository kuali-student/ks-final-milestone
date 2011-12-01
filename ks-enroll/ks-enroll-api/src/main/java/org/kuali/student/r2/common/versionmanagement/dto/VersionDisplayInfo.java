/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.versionmanagement.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.versionmanagement.infc.VersionDisplay;

@XmlAccessorType(XmlAccessType.FIELD)
public class VersionDisplayInfo implements VersionDisplay, Serializable {

    private static final long serialVersionUID = 1L;

   

    public VersionDisplayInfo() {}

    @XmlElement
    private String versionIndId;

    @XmlElement
    private String objectTypeURI;

    @XmlElement
    private Long sequenceNumber;

    @XmlElement
    private Date currentVersionStart;

    @XmlElement
    private Date currentVersionEnd;

    @XmlElement
    private String versionComment;

    @XmlElement
    private String versionedFromId;
    
    public VersionDisplayInfo(VersionDisplay versionDisplay) {
        super();

        this.versionIndId = versionDisplay.getVersionIndId();
        this.sequenceNumber = versionDisplay.getSequenceNumber();
        this.currentVersionStart = versionDisplay.getCurrentVersionStart();
        this.currentVersionEnd = versionDisplay.getCurrentVersionEnd();
        this.versionComment = versionDisplay.getVersionComment();
        this.versionedFromId = versionDisplay.getVersionedFromId();
    }
    
    @Override
    public String getVersionedFromId() {
        return versionedFromId;
    }

    public void setVersionedFromId(String versionedFromId) {
        this.versionedFromId = versionedFromId;
    }

    @Override
    public String getObjectTypeURI() {
        return objectTypeURI;
    }

    public void setObjectTypeURI(String objectTypeURI) {
        this.objectTypeURI = objectTypeURI;
    }

    /*
     * Version independent Id that remains the same across all versions
     */
    @Override
    public String getVersionIndId() {
        return versionIndId;
    }

    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    /**
     * The sequence number of the version
     */
    @Override
    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * The date and time this version became current.
     */
    @Override
    public Date getCurrentVersionStart() {
        return currentVersionStart;
    }

    public void setCurrentVersionStart(Date currentVersionStart) {
        this.currentVersionStart = currentVersionStart;
    }

    /**
     * The date and time when this version stopped being current.
     */
    @Override
    public Date getCurrentVersionEnd() {
        return currentVersionEnd;
    }

    public void setCurrentVersionEnd(Date currentVersionEnd) {
        this.currentVersionEnd = currentVersionEnd;
    }

    /**
     * Comments associated with the verison
     */
    @Override
    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }
}
