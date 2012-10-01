/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.versionmanagement.dto;

import org.kuali.student.r2.core.versionmanagement.infc.Version;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VersionInfo", propOrder = {"versionIndId", "sequenceNumber", "currentVersionStart", "currentVersionEnd", "versionComment", "versionedFromId"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
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

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public VersionInfo() {

    }

    public VersionInfo(Version version) {
        if (version != null) {
            this.versionIndId = version.getVersionIndId();
            this.sequenceNumber = version.getSequenceNumber();
            this.currentVersionStart = (null != version.getCurrentVersionStart()) ? new Date(version.getCurrentVersionStart().getTime()) : null;
            this.currentVersionEnd = (null != version.getCurrentVersionEnd()) ? new Date(version.getCurrentVersionEnd().getTime()) : null;
            this.versionComment = version.getVersionComment();
            this.versionedFromId = version.getVersionedFromId();
        }
    }

    @Override
    public String getVersionIndId() {
        return versionIndId;
    }

    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    @Override
    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public Date getCurrentVersionStart() {
        return currentVersionStart;
    }

    public void setCurrentVersionStart(Date currentVersionStart) {
        this.currentVersionStart = currentVersionStart;
    }

    @Override
    public Date getCurrentVersionEnd() {
        return currentVersionEnd;
    }

    public void setCurrentVersionEnd(Date currentVersionEnd) {
        this.currentVersionEnd = currentVersionEnd;
    }

    @Override
    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

    @Override
    public String getVersionedFromId() {
        return versionedFromId;
    }

    public void setVersionedFromId(String versionedFromId) {
        this.versionedFromId = versionedFromId;
    }
}
