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
import javax.xml.bind.annotation.XmlElement;

/**
 * Detailed information about a proposal.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Thu May 28 10:25:28 EDT 2009
 * @See <a href="https://wiki.kuali.org/display/KULSTU/versionDisplayInfo+Structure">VersionInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VersionDisplayInfo implements Serializable  {

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

	public VersionDisplayInfo() {
	}

	private static final long serialVersionUID = 1L;

    @XmlElement 
    private String id;
    
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


    public String getVersionedFromId() {
		return versionedFromId;
	}

	public void setVersionedFromId(String versionedFromId) {
		this.versionedFromId = versionedFromId;
	}

	/**
     * Version specific id of the entity
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectTypeURI() {
        return objectTypeURI;
    }

    public void setObjectTypeURI(String objectTypeURI) {
        this.objectTypeURI = objectTypeURI;
    }

    /*
     * Version independent Id that remains the same across all versions
     */
    public String getVersionIndId() {
        return versionIndId;
    }

    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    /**
     * The sequence number of the version
     */
    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    /**
     * 	The date and time this version became current.
     */
    public Date getCurrentVersionStart() {
		return currentVersionStart;
	}

	public void setCurrentVersionStart(Date currentVersionStart) {
		this.currentVersionStart = currentVersionStart;
	}
	
    /**
     * 	The date and time when this version stopped being current.
     */
	public Date getCurrentVersionEnd() {
		return currentVersionEnd;
	}

	public void setCurrentVersionEnd(Date currentVersionEnd) {
		this.currentVersionEnd = currentVersionEnd;
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
