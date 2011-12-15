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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.versionmanagement.infc.Version;
import org.w3c.dom.Element;

@XmlType(name = "VersionInfo", propOrder = {"versionIndId", "sequenceNumber", "currentVersionStart", "currentVersionEnd", "versionComment", "versionedFromId", "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class VersionInfo implements Version, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String versionIndId;

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

    @XmlAnyElement
    private List<Element> _futureElements;

    public VersionInfo() {

    }

    public VersionInfo(Version version) {
        if (version != null) {
            this.versionIndId = version.getVersionIndId();
            this.sequenceNumber = version.getSequenceNumber();
            this.currentVersionStart = version.getCurrentVersionStart();
            this.currentVersionEnd = version.getCurrentVersionEnd();
            this.versionComment = version.getVersionComment();
            this.versionedFromId = version.getVersionedFromId();
        }
    }

    /**
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

    public void setVersionedFromId(String versionedFromId) {
        this.versionedFromId = versionedFromId;
    }

    @Override
    public String getVersionedFromId() {
        return versionedFromId;
    }
}
