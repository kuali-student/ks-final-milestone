/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 *Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String versionInd;

    @XmlElement
    private Date createTime;

    @XmlElement
    private String createId;

    @XmlElement
    private Date updateTime;

    @XmlElement
    private String updateId;

    /**
     * An indicator of the version of the thing being described with this meta information. This is set by the service implementation and will be used to determine conflicts in updates.
     */
    public String getVersionInd() {
        return versionInd;
    }

    public void setVersionInd(String versionInd) {
        this.versionInd = versionInd;
    }

    /**
     * The date and time the thing being described with this meta information was last updated
     */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * The principal who created the thing being described with this meta information
     */
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * The date and time the thing being described with this meta information was last updated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * The principal who last updated the thing being described with this meta information
     */
    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}
