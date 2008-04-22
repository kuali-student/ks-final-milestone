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

public abstract class ItemImpl {
    private String uuid = null;
    private String name = null;
    private String description = null;
    private String format = null;
    private long versionNumber;
    private String versionSnapshotUUID;

    private String status = null;

    private Calendar createdDate = null;
    private Calendar lastModifiedDate = null;

    private String checkinComment = null;

    private boolean archived = false;
    private boolean historical = false;

    public ItemImpl(String name) {
        this.name = name;
    }

    public ItemImpl(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Gets the asset's name.
     * 
     * @return Asset's name
     */
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCheckinComment(String checkinComment) {
        this.checkinComment = checkinComment;
    }

    public String getCheckinComment() {
        return checkinComment;
    }

    public String getUUID() {
        return uuid;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }

    public void setVersionNumber(long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public long getVersionNumber() {
        return versionNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setLastModifiedDate(Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isHistorical() {
        return historical;
    }

    public void setHistorical(boolean historical) {
        this.historical = historical;
    }

    /**
     * Return the UUID of the snapshot version. This will return null if the this is the current version. Only historical
     * version wil return a UUID.
     * 
     * @return UUID of the historical version
     */
    public String getVersionSnapshotUUID() {
        return versionSnapshotUUID;
    }

    public void setVersionSnapshotUUID(String versionSnapshotUUID) {
        this.versionSnapshotUUID = versionSnapshotUUID;
    }

}
