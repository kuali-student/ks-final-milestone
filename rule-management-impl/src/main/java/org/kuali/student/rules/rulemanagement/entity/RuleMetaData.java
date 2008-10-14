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
package org.kuali.student.rules.rulemanagement.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Contains meta data on version and history of a functional business rule
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Embeddable
public class RuleMetaData {

    String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

    String updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date effectiveDateStart;

    @Temporal(TemporalType.TIMESTAMP)
    Date effectiveDateEnd;

    String version;
    String status;

    /**
     * @return the createdBy
     */
    public final String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public final void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createDate
     */
    public final Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public final void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updateBy
     */
    public final String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     *            the updateBy to set
     */
    public final void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return the updateDate
     */
    public final Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     *            the updateDate to set
     */
    public final void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the effectiveDateStart
     */
    public final Date getEffectiveDateStart() {
        return effectiveDateStart;
    }

    /**
     * @param effectiveDateStart
     *            the effectiveDateStart to set
     */
    public final void setEffectiveDateStart(Date effectiveDateStart) {
        this.effectiveDateStart = effectiveDateStart;
    }

    /**
     * @return the effectiveDateEnd
     */
    public final Date getEffectiveDateEnd() {
        return effectiveDateEnd;
    }

    /**
     * @param effectiveDateEnd
     *            the effectiveDateEnd to set
     */
    public final void setEffectiveDateEnd(Date effectiveDateEnd) {
        this.effectiveDateEnd = effectiveDateEnd;
    }

    /**
     * @return the version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the status
     */
    public final String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the statusEnum to set
     */
    public final void setStatus(String status) {
        this.status = status;
    }
}
