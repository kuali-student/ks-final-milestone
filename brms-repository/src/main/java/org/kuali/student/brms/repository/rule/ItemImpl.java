/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository.rule;

import java.util.Calendar;

/**
 * This is the base class that 
 * <code>org.kuali.student.brms.repository.rule.RuleImpl</code> and 
 * <code>org.kuali.student.brms.repository.rule.RuleSetImpl<code> extends.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public abstract class ItemImpl implements java.io.Serializable, Item {
    
    private static final long serialVersionUID = 1L;
    
    /** Item UUID */
    private String uuid = null;
    /** Item name */
    private String name = null;
    /** Item description */
    private String description = null;
    /** Item format */
    private String format = null;
    /** Item version number */
    private long versionNumber;
    /** Item status */
    private String status = null;

    /** Item snapshot version UUID */
    private String versionSnapshotUUID;

    /** Item created date */
    private Calendar createdDate = null;
    /** Item last modified date */
    private Calendar lastModifiedDate = null;
    /** Item checkin comment */
    private String checkinComment = null;

    /** Determines whether item is archived or not */
    private boolean archived = false;
    /** Determines whether item is historical or not */
    private boolean historical = false;

    /**
     * Constructs a new item.
     * 
     * @param name Item name
     */
    public ItemImpl(String name) {
        this.name = name;
    }

    /**
     * Constructs a new item.
     * 
     * @param uuid Item UUID
     * @param name Item name
     */
    public ItemImpl(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
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
     * @see org.kuali.student.brms.repository.rule.Item#setDescription(java.lang.String)
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
     * @see org.kuali.student.brms.repository.rule.Item#setFormat(java.lang.String)
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
     * @param versionNumber
     */
    public void setVersionNumber(long versionNumber) {
        this.versionNumber = versionNumber;
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
     * @param lastModifiedDate
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
     * @see org.kuali.student.brms.repository.rule.Item#setArchived(boolean)
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
     * @param historical
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
     * @param versionSnapshotUUID
     */
    public void setVersionSnapshotUUID(String versionSnapshotUUID) {
        this.versionSnapshotUUID = versionSnapshotUUID;
    }

}
