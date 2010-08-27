/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.versionmanagement.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Detailed information about a proposal.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Thu May 28 10:25:28 EDT 2009
 * @See <a href="https://wiki.kuali.org/display/KULSTU/versionInfo+Structure">VersionInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VersionInfo implements Serializable  {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String versionSpecificId;
    
    @XmlElement
    private Integer sequenceNumber;

    @XmlElement
    private Date versionCreateTime;

    @XmlElement
    private String versionCreateId;
    
    @XmlElement
    private boolean isCurrentVersion;

    @XmlAttribute
    private String versionComment;

    /**
     * Version specific Id
     */
    public String getVersionSpecificId() {
        return versionSpecificId;
    }

    public void setVersionSpecificId(String versionSpecificId) {
        this.versionSpecificId = versionSpecificId;
    }

    /**
     * The sequence number of the version
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * The date and time this version was created
     */
    public Date getVersionCreateTime() {
        return versionCreateTime;
    }

    public void setVersionCreateTime(Date versionCreateTime) {
        this.versionCreateTime = versionCreateTime;
    }

    /**
     * The principal who created this version
     */
    public String getVersionCreateId() {
        return versionCreateId;
    }

    public void setVersionCreateId(String versionCreateId) {
        this.versionCreateId = versionCreateId;
    }

    /**
     * Flag set to true if this version is the current version. There should be only one version for a given objectId of a objectType marked current at any given point in time.
     */
    public boolean isCurrentVersion() {
        return isCurrentVersion;
    }

    public void setCurrentVersion(boolean isCurrentVersion) {
        this.isCurrentVersion = isCurrentVersion;
    }

    /**
     * Comments associated with the verison
     */
    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }
}
