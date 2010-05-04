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

package org.kuali.student.brms.repository.dto;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractItemInfo implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Item UUID */
	@XmlAttribute
    private String uuid;
    /** Item name */
	@XmlElement(required = true)
    private String name;
    /** Item description */
	@XmlElement(required = true)
    private String description;
    /** Item format */
	@XmlElement(required = true)
    private String format;
    /** Item version number */
	@XmlElement
    private long versionNumber;
    /** Item status */
	@XmlElement
    private String status;

    /** Item snapshot version UUID */
	@XmlElement
    private String versionSnapshotUUID;

    /** Item created date */
	@XmlElement
    private Calendar createdDate;
    /** Item last modified date */
	@XmlElement
    private Calendar lastModifiedDate;
    /** Item checkin comment */
	@XmlElement
    private String checkinComment;

    /** Determines whether item is archived or not */
	@XmlElement
    private boolean archived = false;
    /** Determines whether item is historical or not */
	@XmlElement
    private boolean historical = false;

    /**
     * Constructor.
     */
    protected AbstractItemInfo() {
    }

    /**
     * Constructs a new item.
     * 
     * @param name Item name
     * @param name Item description
     * @param name Item format
     */
    public AbstractItemInfo(final String name, 
                   final String description,
                   final String format) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        else if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("description cannot be null or empty");
        }
        else if (format == null || format.trim().isEmpty()) {
            throw new IllegalArgumentException("format cannot be null or empty");
        }
        this.uuid = null;
        this.name = name;
        this.description = description;
        this.format = format;
        this.versionNumber = -1L;
    }

    /**
     * Constructs a new item.
     * 
     * @param uuid Item UUID
     * @param name Item name
     * @param versionNumber Item version number
     */
    public AbstractItemInfo(final String uuid, final String name, final long versionNumber) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.uuid = uuid;
        this.name = name;
        this.versionNumber = versionNumber;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getDescription()
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this item.
     * 
     * @param description Item description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#setCheckinComment(java.lang.String)
     */
    public void setCheckinComment(String checkinComment) {
        this.checkinComment = checkinComment;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getCheckinComment()
     */
    public String getCheckinComment() {
        return checkinComment;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getUUID()
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Sets the item source format. 
     * E.g. XML (xml) or Drools DRL (drl).
     * 
     * @param format Item format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getFormat()
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getVersionNumber()
     */
    public long getVersionNumber() {
        return versionNumber;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#setStatus(java.lang.String)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getStatus()
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the item created date.
     * 
     * @param createdDate
     */
    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getCreatedDate()
     */
    public Calendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date this item was last modified. 
     * 
     * @param lastModifiedDate Item last modified date
     */
    public void setLastModifiedDate(Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getLastModifiedDate()
     */
    public Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#isArchived()
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Sets this item to archived if <code>archived</code> is true. 
     * 
     * @param archived True if this item is archived, otherwise false
     */
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#isHistorical()
     */
    public boolean isHistorical() {
        return historical;
    }

    /**
     * Sets this item historical to true or false.
     * 
     * @param historical True equals historical, otherwise false
     */
    public void setHistorical(boolean historical) {
        this.historical = historical;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Item#getVersionSnapshotUUID()
     */
    public String getVersionSnapshotUUID() {
        return versionSnapshotUUID;
    }

    /**
     * Sets the UUID of this version's snapshot
     * 
     * @param versionSnapshotUUID
     */
    public void setVersionSnapshotUUID(String versionSnapshotUUID) {
        this.versionSnapshotUUID = versionSnapshotUUID;
    }

}
