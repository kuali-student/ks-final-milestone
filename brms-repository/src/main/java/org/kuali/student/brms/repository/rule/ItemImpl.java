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
    
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Item UUID */
    private String uuid;
    /** Item name */
    private String name;
    /** Item description */
    private String description;
    /** Item format */
    private String format;
    /** Item version number */
    private long versionNumber;
    /** Item status */
    private String status;

    /** Item snapshot version UUID */
    private String versionSnapshotUUID;

    /** Item created date */
    private Calendar createdDate;
    /** Item last modified date */
    private Calendar lastModifiedDate;
    /** Item checkin comment */
    private String checkinComment;

    /** Determines whether item is archived or not */
    private boolean archived = false;
    /** Determines whether item is historical or not */
    private boolean historical = false;

    /**
     * Private constructor.
     */
    private ItemImpl() {
    }
    
    /**
     * Constructs a new item.
     * 
     * @param name Item name
     */
    ItemImpl(final String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Constructs a new item.
     * 
     * @param uuid Item UUID
     * @param name Item name
     */
    ItemImpl(final String uuid, final String name) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        else if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.uuid = uuid;
        this.name = name;
    }

    ItemImpl(final String uuid, final String name, final long versionNumber) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        else if (name == null || name.trim().isEmpty()) {
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
     * Sets the item version number.
     * 
     * @param versionNumber Version number
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

    /**
     * Overrides hashCode
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( name == null ? 0 : name.hashCode() );
        result = prime * result + ( uuid == null ? 0 : uuid.hashCode() );
        result = prime * result + (int) ( versionNumber ^ ( versionNumber >>> 32 ) );
        return result;
    }

    /**
     * Overrides equals
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        if ( name == null ) {
            return false;
        }

        final ItemImpl item = (ItemImpl) obj;
        
        if ( item.getName() == null ) {
            return false;
        }
        
        if ( !getName().equals( item.getName() ) ) {
            return false;
        }
        if ( getUUID() != null && !item.getUUID().equals( this.getUUID() ) ) {
            return false;
        }
        if ( item.getVersionNumber() != this.getVersionNumber() ) {
            return false;
        }
        
        return true;
    }
    
    public String toString() {
        return "Name=" + this.name + ", UUID=" + this.uuid;
    }

}
