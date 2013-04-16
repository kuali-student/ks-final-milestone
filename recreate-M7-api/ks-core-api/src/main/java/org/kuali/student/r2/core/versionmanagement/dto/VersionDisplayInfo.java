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

import org.kuali.student.r2.core.versionmanagement.infc.VersionDisplay;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VersionDisplayInfo", propOrder = {"id",
        "versionIndId",
        "refObjectUri",
        "sequenceNumber",
        "currentVersionEnd",
        "currentVersionStart",
        "versionComment",
        "versionedFromId", "_futureElements" }) 
public class VersionDisplayInfo implements VersionDisplay, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private String id;
    @XmlElement
    private String versionIndId;
    @XmlElement
    private String refObjectUri;
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
    private List<Object> _futureElements;  

    public VersionDisplayInfo() {
    }

    public VersionDisplayInfo(String id, String versionIndId,
                              Long sequenceNumber,
                              Date currentVersionStart, Date currentVersionEnd,
                              String versionComment, String versionedFromId) {
        super();
        this.id = id;
        this.versionIndId = versionIndId;
        this.sequenceNumber = sequenceNumber;
        this.currentVersionStart = currentVersionStart;
        this.currentVersionEnd = currentVersionEnd;
        this.versionComment = versionComment;
        this.versionedFromId = versionedFromId;
    }


    public VersionDisplayInfo(VersionDisplay versionDisplay) {
        super();

        if (null != versionDisplay) {
            this.id = versionDisplay.getId();
            this.versionIndId = versionDisplay.getVersionIndId();
            this.sequenceNumber = versionDisplay.getSequenceNumber();
            this.currentVersionStart = (null != versionDisplay.getCurrentVersionStart()) ? new Date(versionDisplay.getCurrentVersionStart().getTime()) : null;
            this.currentVersionEnd = (null != versionDisplay.getCurrentVersionEnd()) ? new Date(versionDisplay.getCurrentVersionEnd().getTime()) : null;
            this.versionComment = versionDisplay.getVersionComment();
            this.versionedFromId = versionDisplay.getVersionedFromId();
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getVersionedFromId() {
        return versionedFromId;
    }

    public void setVersionedFromId(String versionedFromId) {
        this.versionedFromId = versionedFromId;
    }

    @Override
    public String getRefObjectUri() {
        return refObjectUri;
    }

    public void setRefObjectUri(String refObjectUri) {
        this.refObjectUri = refObjectUri;
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
}
